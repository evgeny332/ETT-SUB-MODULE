package com.rh.main;

import java.io.IOException;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.rh.config.ConfigHolder;
import com.rh.interfaces.CallBack;
import com.rh.interfaces.PushInterface;
import com.rh.interfaces.Impl.PushServiceImpl;
import com.rh.persistence.DBPersister;
import com.rh.persistence.domain.DeviceToken;
import com.rh.persistence.domain.PendingRedeems;
import com.rh.persistence.domain.PopUpSheduled;
import com.rh.persistence.domain.User;
import com.rh.persistence.domain.UserAccountSummary;
import com.rh.persistence.domain.UserRedeem;
import com.rh.remote.IndoSendGetRequest;
import com.rh.utility.Utility;

public class CallBackImpl implements CallBack {
	private PushInterface pushServiceImpl;
	private DBPersister dbPersister;
	private ConfigHolder configHolder;
	private JmsTemplate jmsTemplate;
	private int locale;
	private static Log log = LogFactory.getLog(CallBackImpl.class);

	public CallBackImpl(DBPersister paramDBPersister, JmsTemplate paramJmsTemplate) throws IOException {
		this.dbPersister = paramDBPersister;
		this.configHolder = new ConfigHolder();
		this.jmsTemplate = paramJmsTemplate;
		this.pushServiceImpl = new PushServiceImpl();
	}

	public void handleCallBack(String transId) {

		PendingRedeems pendingRedeems = null;
		try {
			pendingRedeems = this.dbPersister.getTransIdPendingRedeems(transId);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Tranglo CallBack came but it's already processed [trans_Id=" + transId + "]");
		}

		try {
			if (pendingRedeems != null) {

				locale = dbPersister.getUserLocale(pendingRedeems.getEttId());
				String str1 = this.dbPersister.getTrangloCallBackStatus(pendingRedeems);
				String str2 = Utility.getTrangloResponse(str1);

				log.info("Tranglo Callback received for [EttId=" + pendingRedeems.getEttId() + "] [transId=" + transId + "] [Status=" + str1 + "]");

				User user = this.dbPersister.getUser(pendingRedeems.getEttId());
				DeviceToken deviceToken = this.dbPersister.getDeviceId(user.getDeviceId(), user.getEttId());

				if ((str2.equalsIgnoreCase("PENDING")) || (str2.equalsIgnoreCase("TIMEOUT")))
					return;
				if (str2.equalsIgnoreCase("SUCCESS")) {
					onSuccess(user, pendingRedeems, deviceToken);
					return;
				}
				onFailure(user, pendingRedeems, deviceToken);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Error in Tranglo CallBack !! =" + e.getMessage());
		}
	}

	public void clearPendingList() {

		while (true) {

			long t1 = System.currentTimeMillis();

			try {
				List<Long> list = dbPersister.getPendingRedeemList();

				for (int i = 0; i < list.size(); i++) {
					handlePendingStatus(list.get(i));
				}

			} catch (Exception e) {
				e.getMessage();
//				log.warn("Error in clearPendingList | " + e.getMessage());
			}

			long t2 = System.currentTimeMillis() - t1;

			try {
				Thread.sleep(120000 - t2);
			} catch (Exception e) {
				e.getMessage();
			}
		}
	}

	public void handlePendingStatus(Long paramString) {

		try {

			PendingRedeems pendingRedeems = this.dbPersister.getPendingRedeems(paramString);
			User user = this.dbPersister.getUser(pendingRedeems.getEttId());
			DeviceToken deviceToken = this.dbPersister.getDeviceId(user.getDeviceId(), user.getEttId());

			int i = pendingRedeems.getCount();
			String str = "";

			if (i > 0) {
				pendingRedeems.setCount(i - 1);

				if (pendingRedeems.getVender().equals("MOBILE_PULSA")) {
					str = checkStatusMobilePulsa(pendingRedeems);
				}
				// else if (pendingRedeems.getVender().equals("TRANGLO")) {
				// str = checkStatusTranglo(pendingRedeems);
				// }

				pendingRedeems.setStatus(str);
				this.dbPersister.updateCount(pendingRedeems);

				if (str.equalsIgnoreCase("PENDING") || str.equalsIgnoreCase("TIMEOUT")) {
					return;
				}
				if (str.equalsIgnoreCase("SUCCESS")) {
					onSuccess(user, pendingRedeems, deviceToken);
					return;
				}
				onFailure(user, pendingRedeems, deviceToken);
				return;
			}
			onFailure(user, pendingRedeems, deviceToken);
			return;

		} catch (Exception e) {
			e.printStackTrace();
			log.info("Error in handlePendingStatus !! =" + e.getMessage());
			return;
		}
	}

	private String checkStatusMobilePulsa(PendingRedeems pendingRedeems) {

		try {
			// Thread.sleep(10000);
			long l = System.currentTimeMillis();
			String url = this.configHolder.getProperties().getProperty("MOBILE_PULSA_RECHARGE_URL");
			String postData = configHolder.getProperties().getProperty("MOBILE_PULSA_POSTDATA_CHECKSTATUS").replace("#REF_ID#", pendingRedeems.getTrans_id()).replace("#MD5#",
					Utility.getMD5(configHolder.getProperties().getProperty("MOBILE_PULSA_USERNAME"), configHolder.getProperties().getProperty("MOBILE_PULSA_PASSWORD"), "cs"));
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

	@SuppressWarnings("unused")
	private String checkStatusTranglo(PendingRedeems pendingRedeems) {

		try {
			if (configHolder.getProperties().getProperty("IS_TRANGLO_PREPAID_ENABLE").equals("true")) {

				long l1 = System.currentTimeMillis();
				IndoSendGetRequest sendGetRequest = new IndoSendGetRequest();
				String url = configHolder.getProperties().getProperty("TRANGLO_RECHARGE_URL");
				String resp = Utility.getTrangloResponse(sendGetRequest.checkStatusTranglo(url, pendingRedeems.getTrans_id()));
				log.info("CHECK STATUS TRANGLO | [" + url + "] | [trans_id=" + pendingRedeems.getTrans_id() + ",ettId=" + pendingRedeems.getEttId() + "] | [RESP=" + resp + "] | [TIME=" + (System.currentTimeMillis() - l1)
						+ "] |");
				return resp;
			}
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Error in checkStatusTranglo | " + e.getMessage());
			return "";
		}

	}

	private void onSuccess(User user, PendingRedeems pendingRedeems, DeviceToken deviceToken) {

		UserAccountSummary userAccountSummary = new UserAccountSummary();
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
		log.info("redeem successful ettId=" + user.getEttId());

		PopUpSheduled popUpSheduled;
		if ((deviceToken != null) && (!deviceToken.getDeviceToken().equals(""))) {
			String txt = this.pushServiceImpl.getSuccessPush(locale, pendingRedeems);
			sendPush(txt, deviceToken);
		}
		try {
			if (Float.parseFloat(user.getAppVersion()) >= 1.3F) {
				popUpSheduled = this.dbPersister.getPopUpSheduled(pendingRedeems.getEttId());
				if (popUpSheduled == null)
					this.dbPersister.setPopuSheduled(pendingRedeems.getEttId());
			}
		} catch (Exception ex) {
			log.error("ERROR !! in checking the popUpSchedule: " + ex);
			ex.printStackTrace();
		}
	}

	private void onFailure(User user, PendingRedeems pendingRedeems, DeviceToken deviceToken) {
		if ((deviceToken != null) && (!deviceToken.getDeviceToken().equals(""))) {
			String str = this.pushServiceImpl.getFailurePush(locale, pendingRedeems);
			sendPush(str, deviceToken);
		}
		this.dbPersister.updateFailRedeem(user, pendingRedeems);
		this.dbPersister.deletePendingRedeems(pendingRedeems.getId() + "");
		log.info("redeem failed ettId=" + user.getEttId());
	}

	private void sendPush(final String paramString, final DeviceToken deviceToken) {
		try {
			this.jmsTemplate.send(new MessageCreator() {
				public Message createMessage(Session anonymousSession) throws JMSException {
					MapMessage message = anonymousSession.createMapMessage();
					message.setString("ID", deviceToken.getDeviceToken());
					message.setString("DISPLAY_STRING", paramString);
					message.setInt("BADGE_COUNT", 1);
					message.setString("DEVICE_TYPE", "ANDROID");
					message.setString("type", "BALANCE");
					return message;
				}
			});
		} catch (Exception ex) {
			log.error("error in redeem push " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}