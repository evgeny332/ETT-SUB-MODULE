package com.rh.main;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.rh.config.ConfigHolder;
import com.rh.interfaces.APIInterface;
import com.rh.interfaces.PushInterface;
import com.rh.interfaces.RHServiceInterface;
import com.rh.interfaces.Impl.PushServiceImpl;
import com.rh.interfaces.Impl.RHServiceImpl;
import com.rh.persistence.DBPersister;
import com.rh.persistence.domain.DeviceToken;
import com.rh.persistence.domain.PendingRedeems;
import com.rh.persistence.domain.PopUpSheduled;
import com.rh.persistence.domain.User;
import com.rh.persistence.domain.UserAccountSummary;
import com.rh.persistence.domain.UserRedeem;
import com.rh.remote.IndoSendGetRequest;
import com.rh.utility.Utility;

public class IndoAPIImpl implements APIInterface {
	private PushInterface pushServiceImpl;
	private DBPersister dbPersister;
	private ConfigHolder configHolder;
	private JmsTemplate jmsTemplate;
	private RHServiceInterface rhServiceImpl;
	private int locale;
	private static Log log = LogFactory.getLog(IndoAPIImpl.class);

	public IndoAPIImpl(DBPersister paramDBPersister, JmsTemplate paramJmsTemplate) throws IOException {
		this.dbPersister = paramDBPersister;
		this.configHolder = new ConfigHolder();
		this.jmsTemplate = paramJmsTemplate;
		this.pushServiceImpl = new PushServiceImpl();
		this.rhServiceImpl = new RHServiceImpl();
	}

	public void updatePokktInfo(String id) {

		UserRedeem userRedeem = this.dbPersister.getUserRedeem(id);
		locale = dbPersister.getUserLocale(userRedeem.getEttId());
		log.info("User= [RdeeemType:" + userRedeem.getRedeemType() + "][locale:" + locale + "]");
		PendingRedeems pendingRedeems = new PendingRedeems();
		pendingRedeems.setUserRedeem(userRedeem);


		// CONVENIENCE CHARGE HANDLING//
		
		this.dbPersister.insertPendingRedeems(pendingRedeems);
		if ((pendingRedeems.getType() == null) || (pendingRedeems.getType().equals(""))) {
			pendingRedeems.setType("PREPAID");
		}
		if (configHolder.getProperties().getProperty("PROCESS_FEE_DEDUCT").equals("true") && (pendingRedeems.getFee() <= 0.0F)) {
			rhServiceImpl.setConvenienceCharge(pendingRedeems, this.dbPersister);
		}
		if (pendingRedeems.getEttId() == null) {
			log.warn("PendingRedeems not found | [id=" + id + "]");
			return;
		}


		// BARRED USER CHECK//
		
		if ((this.configHolder.getProperties().getProperty("BARRED_USER_CHECK") != null) && (this.configHolder.getProperties().getProperty("BARRED_USER_CHECK").equals("true"))
				&& (this.configHolder.getBarredStatus(pendingRedeems.getEttId()))) {
			this.dbPersister.updateBarredRedeem(pendingRedeems);
			log.info("Redeem Barred | [ettId=" + pendingRedeems.getEttId() + "] | [id=" + pendingRedeems.getId() + "]");
			return;
		}


		// DAILY LIMIT HANDLING//
		
		String isSuccess = "";
		log.info("PendingRedeems|" + pendingRedeems.toString());
		User user = this.dbPersister.getUser(pendingRedeems.getEttId());
		int i = this.dbPersister.getTodayAmount(pendingRedeems.getEttId());
		if (i + pendingRedeems.getAmount() >= Integer.parseInt(this.configHolder.getProperties().getProperty("DAY_LIMIT"))) {
			log.info("Day limit [" + this.configHolder.getProperties().getProperty("DAY_LIMIT") + "] crossed for ettId=" + pendingRedeems.getEttId());
			this.dbPersister.updateFailRedeem(user, pendingRedeems);
			DeviceToken dToken = this.dbPersister.getDeviceId(user.getDeviceId(), user.getEttId());
			if ((dToken != null) && (!dToken.getDeviceToken().equals(""))) {
				String pushText = "";
				if (locale == 0) {
					pushText = this.configHolder.getProperties().getProperty("DAY_LIMIT_CROSS_NOTF");
				} else {
					pushText = this.configHolder.getProperties().getProperty("BHASHA_DAY_LIMIT_CROSS_NOTF");
				}

				if (!(pushText).equals("")) {
					pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
					sendPush(pushText, dToken);
					return;
				}
			}
			return;
		}


		// REDEEM PRIORITY HERE//
		
		if ((pendingRedeems.getType().equalsIgnoreCase("prepaid")) || (pendingRedeems.getType().equals(""))) {
			isSuccess = giveBalanceTranglo(pendingRedeems);
			if (!isSuccess.equalsIgnoreCase("SUCCESS") && !isSuccess.equalsIgnoreCase("PENDING")) {
				isSuccess = giveBalanceMobilePulsa(pendingRedeems);
			}
		} else {
			log.error("ERROR !! Redeem type has not recognised the process | [id=" + pendingRedeems.getId() + "]");
		}


		// TIMEOUT HANDLING//
		
		if (isSuccess.equalsIgnoreCase("TIMEOUT") || isSuccess.equalsIgnoreCase("PENDING")) {
			if (pendingRedeems.getVender().equalsIgnoreCase("MOBILE_PULSA")) {
				isSuccess = checkStatusMobilePulsa(pendingRedeems);
			}
		}


		// REPLICATE TO PENDINGREDEEMS//
		
		DeviceToken dToken = dbPersister.getDeviceId(user.getDeviceId(), user.getEttId());
		UserAccountSummary userAccountSummary;
		if ((isSuccess.equalsIgnoreCase("PENDING")) || (isSuccess.equalsIgnoreCase("TIMEOUT"))) {
			pendingRedeems.setStatus("PENDING");
			dbPersister.updatePendingRedeems(pendingRedeems);
			if ((dToken != null) && (!dToken.getDeviceToken().equals(""))) {
				String txt = this.pushServiceImpl.getProcessingPush(locale, pendingRedeems);
				sendPush(txt, dToken);
			}
			return;
		}


		// SUCCESS OR FAILURE HANDLING//

		if (isSuccess.equalsIgnoreCase("SUCCESS")) {

			userAccountSummary = new UserAccountSummary();
			userAccountSummary.setEttId(user.getEttId());
			if (pendingRedeems.getRedeemType().equals(UserRedeem.RedeemType.LOAN)) {
				userAccountSummary.setOfferId(Long.valueOf(118L));
				userAccountSummary.setOfferName("LOAN");
				userAccountSummary.setAmount(-pendingRedeems.getAmount());
				userAccountSummary.setRemarks("LOAN");
			} else {
				userAccountSummary.setOfferId(Long.valueOf(0L));
				userAccountSummary.setOfferName("redemption");
				userAccountSummary.setAmount(-pendingRedeems.getAmount());
				userAccountSummary.setRemarks("Recharged");
			}
			this.dbPersister.updateSuccessRedeem(user, userAccountSummary, pendingRedeems);
			this.dbPersister.deletePendingRedeems(pendingRedeems.getId() + "");
			log.info("redeem successful [ettId=" + user.getEttId() + "]");
			PopUpSheduled popUpSheduled;
			if ((dToken != null) && (!dToken.getDeviceToken().equals(""))) {
				String txt = this.pushServiceImpl.getSuccessPush(locale, pendingRedeems);
				sendPush(txt, dToken);
			}
			try {
				if (Float.parseFloat(user.getAppVersion()) >= 1.3f) {
					popUpSheduled = this.dbPersister.getPopUpSheduled(pendingRedeems.getEttId());
					if (popUpSheduled == null)
						this.dbPersister.setPopuSheduled(pendingRedeems.getEttId());
				}
			} catch (Exception e) {
				log.error("ERROR !! in checking the popUpSchedule: " + e);
				e.printStackTrace();
			}
			return;
		}
		if ((dToken != null) && (!dToken.getDeviceToken().equals(""))) {
			String txt = this.pushServiceImpl.getFailurePush(locale, pendingRedeems);
			sendPush(txt, dToken);
		}

		this.dbPersister.updateFailRedeem(user, pendingRedeems);
		this.dbPersister.deletePendingRedeems(pendingRedeems.getId() + "");
		log.info("redeem failed ettId=" + user.getEttId());
	}

	///// ####### Methods Used Here #######/////

	private String giveBalanceTranglo(PendingRedeems pendingRedeems) {
		try {
			if (configHolder.getProperties().getProperty("IS_TRANGLO_PREPAID_ENABLE").equals("true")) {
				String url = this.configHolder.getProperties().getProperty("TRANGLO_RECHARGE_URL");
				IndoSendGetRequest sendGetRequest = new IndoSendGetRequest();
				long l1 = System.currentTimeMillis();
				long l2 = l1 + pendingRedeems.getEttId().longValue();
				pendingRedeems.setTrans_id(l2 + "");
				pendingRedeems.setVender("TRANGLO");
				String resp = sendGetRequest.reloadTranglo(url, pendingRedeems.getMsisdn() + "", pendingRedeems.getAmount(), l2 + "");
				log.info("RECHARGE URL TRANGLO | [" + url + "] | [msisdn=" + pendingRedeems.getMsisdn() + ",amount=" + pendingRedeems.getAmount() + "] | [RESP=" + resp + "] | [TIME=" + (System.currentTimeMillis() - l1)
						+ "] |");
				return Utility.getTrangloResponse(resp);
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Error in giveBalanceTranglo | " + e.getMessage());
			return "";
		}
	}

	private String giveBalanceMobilePulsa(PendingRedeems pendingRedeems) {

		try {
			if (this.configHolder.getProperties().getProperty("IS_MOBILE_PULSA_ENABLE").equals("true")) {
				long l1 = System.currentTimeMillis();
				long l2 = l1 + pendingRedeems.getEttId().longValue();
				String url = configHolder.getProperties().getProperty("MOBILE_PULSA_RECHARGE_URL");
				String postData = configHolder.getProperties().getProperty("MOBILE_PULSA_POSTDATA").replace("#MSISDN#", pendingRedeems.getMsisdn() + "").replace("#REF_ID#", l2 + "")
						.replace("#AMOUNT#", (int) pendingRedeems.getAmount() + "")
						.replace("#MD5#", Utility.getMD5(configHolder.getProperties().getProperty("MOBILE_PULSA_USERNAME"), configHolder.getProperties().getProperty("MOBILE_PULSA_PASSWORD"), l2 + ""));
				IndoSendGetRequest sendGetRequest = new IndoSendGetRequest();
				pendingRedeems.setVender("MOBILE_PULSA");
				pendingRedeems.setTrans_id(l2 + "");
				String resp = Utility.getMobilePulsaResponse(sendGetRequest.reloadMobilePulsa(url, postData));
				log.info("RECHARGE URL MOBILE_PULSA| [" + url + "][" + postData + "] | [RESP=" + resp + "] | [TIME=" + (System.currentTimeMillis() - l1) + "] |");
				return resp;
			}

			return "";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error in giveBalance| ", e);
		}
		return "";
	}

	private String checkStatusMobilePulsa(PendingRedeems pendingRedeems) {
		
		try {
			Thread.sleep(10000);
			long l = System.currentTimeMillis();
			String url = this.configHolder.getProperties().getProperty("MOBILE_PULSA_RECHARGE_URL");
			String postData = this.configHolder.getProperties().getProperty("MOBILE_PULSA_POSTDATA_CHECKSTATUS").replace("#REF_ID#", pendingRedeems.getTrans_id()).replace("#MD5#",
					Utility.getMD5(this.configHolder.getProperties().getProperty("MOBILE_PULSA_USERNAME"), this.configHolder.getProperties().getProperty("MOBILE_PULSA_PASSWORD"), "cs"));
			pendingRedeems.setVender("MOBILE_PULSA");
			IndoSendGetRequest sendGetRequest = new IndoSendGetRequest();
			String resp = Utility.getMobilePulsaResponse(sendGetRequest.reloadMobilePulsa(url, postData));
			log.info("CHECK_STATUS MOBILE_PULSA| [" + url + "][" + postData + "] | [RESP=" + resp + "] | [TIME=" + (System.currentTimeMillis() - l) + "] |");
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error in CheckStatus| [trans_Id=" + pendingRedeems.getTrans_id() + "]");
		}
		return "";
	}

	private void sendPush(final String id, final DeviceToken deviceToken) {
		try {
			this.jmsTemplate.send(new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					MapMessage mapMessage = session.createMapMessage();
					mapMessage.setString("ID", deviceToken.getDeviceToken());
					mapMessage.setString("DISPLAY_STRING", id);
					mapMessage.setInt("BADGE_COUNT", 1);
					mapMessage.setString("DEVICE_TYPE", "ANDROID");
					mapMessage.setString("type", "BALANCE");
					return mapMessage;
				}
			});
		} catch (Exception e) {
			log.error("error in redeem push " + e.getMessage());
			e.printStackTrace();
		}
	}
}