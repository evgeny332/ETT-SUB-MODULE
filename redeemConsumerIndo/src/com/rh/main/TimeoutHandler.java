package com.rh.main;

import java.io.IOException;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.rh.config.DBConfigHolder;
import com.rh.interfaces.PushInterface;
import com.rh.interfaces.Impl.PushServiceImpl;
import com.rh.persistence.DBPersister;
import com.rh.persistence.domain.DeviceToken;
import com.rh.persistence.domain.PopUpSheduled;
import com.rh.persistence.domain.User;
import com.rh.persistence.domain.UserAccountSummary;
import com.rh.persistence.domain.UserRedeem;
import com.rh.persistence.domain.UserRedeem.RedeemType;
import com.rh.remote.SendGetRequest;
import com.rh.utility.Utility;

public class TimeoutHandler {

	private PushInterface pushServiceImpl;
	private DBPersister dbPersister;
	private DBConfigHolder configHolder;
	private JmsTemplate jmsTemplate;

	private static Log log = LogFactory.getLog(TimeoutHandler.class);

	public TimeoutHandler(DBPersister dp, JmsTemplate jmsTemplate) throws IOException {
		this.dbPersister = dp;
		this.configHolder = new DBConfigHolder(dp);
		this.jmsTemplate = jmsTemplate;
		this.pushServiceImpl = new PushServiceImpl();
	}

	public void handler() {

		String isSuccess = "";
		List<UserRedeem> list = dbPersister.getTimeOuts();

		if (list.size() == 0 || list == null) {
			log.info("No transactions found !!");
			return;
		}
		log.info("list size:" + list.size());
		for (UserRedeem userRedeem : list) {

			System.out.println("id : " + userRedeem.getId());
			User user = dbPersister.getUser(userRedeem.getEttId());
			DeviceToken dToken = dbPersister.getDeviceId(user.getDeviceId(), user.getEttId());

			/******************** SOS OFFER ****************************/

			boolean sos = false;
			if (configHolder.getProperties().get("SOS_OFFER").equals("true")) {
				sos = true;
			}

			/************** USER ACTIVITY BOOSTER ***************/
			boolean userActivityBooster = false;
			long userActivityBoosterId = 0;
			if (configHolder.getProperties().get("USERACTIVITYBOOSTER").equals("true") && userRedeem.getFee() == 0.0f) {
				userActivityBoosterId = dbPersister.getIDUserActivityBooster(userRedeem.getEttId());
				if (userActivityBoosterId != 0) {
					userActivityBooster = true;
				}
			}

			/************** FIRST REDEEM ***************/
			boolean firstRedeemPay = false;
			/*int diffInDays = (int) ((userRedeem.getCreatedTime().getTime() - user.getRegDate().getTime()) / (1000 * 60 * 60 * 24));
			log.info("Date diff=" + diffInDays);
			if (diffInDays < 7) {
				if (configHolder.getProperties().get("FIRSTREDEEMPAY").equals("true")) {
					if (configHolder.getAmountfirstRedeemBonus().containsKey((int) userRedeem.getAmount())) {
						int count = dbPersister.getRedeemCount(userRedeem.getEttId());
						if (count == 0) {
							firstRedeemPay = true;
						}
					}
				}
			}*/

			if (userRedeem.getVender().equalsIgnoreCase("OXYGEN")) {
				isSuccess = checkStatusOxygen(userRedeem);
			} else if (userRedeem.getVender().equalsIgnoreCase("ATP")) {
				isSuccess = checkStatusApt(userRedeem);
			} else if (userRedeem.getVender().equalsIgnoreCase("FLIPKART")) {
				isSuccess = checkStatusFlipkart(userRedeem);
			} else {
				isSuccess = "FAILED";
			}

			if (isSuccess.equalsIgnoreCase("TIMEOUT")) {
				userRedeem.setStatus(isSuccess);
				dbPersister.updateRedeemStatus(userRedeem);
				continue;
			}

			if (isSuccess.equalsIgnoreCase("SUCCESS")) {
				userRedeem.setStatus(isSuccess);
				UserAccountSummary userAccountSummary = new UserAccountSummary();
				userAccountSummary.setEttId(user.getEttId());
				if (userRedeem.getRedeemType().equals(RedeemType.LOAN)) {
					userAccountSummary.setOfferId(118l);
					userAccountSummary.setOfferName("LOAN");
					userAccountSummary.setAmount(-(userRedeem.getAmount()));
					userAccountSummary.setRemarks("LOAN");
				} else if (userRedeem.getType().equals("EGV")) {
					userAccountSummary.setOfferId(0l);
					userAccountSummary.setOfferName("GIFTVOUCHER");
					userAccountSummary.setAmount(-(userRedeem.getAmount()));
					userAccountSummary.setRemarks("FLIPKART");
				} else if (userRedeem.getType().equals("TVandMusic")) {
					userAccountSummary.setOfferId(0l);
					userAccountSummary.setOfferName("TVandMusic");
					userAccountSummary.setAmount(-(userRedeem.getAmount()));
					userAccountSummary.setRemarks("NexGTV");
				} else {
					userAccountSummary.setOfferId(0l);
					userAccountSummary.setOfferName("redemption");
					userAccountSummary.setAmount(-(userRedeem.getAmount()));
					if(userRedeem.getRedeemType().equals(RedeemType.BROWSEPLAN)){
						userAccountSummary.setRemarks("Recharged Browseplan");
					}else{
						userAccountSummary.setRemarks("Recharged");
					}
					userAccountSummary.setRemarks("Recharged");
					CheckMsisdn(user, userRedeem);
				}
				dbPersister.updateSuccessRedeem(user, userAccountSummary, userRedeem);

				if (user.getRedeemCount() == 0) {
					String usaId = rechargeBonus(user);
					if (!usaId.equals("")) {
						if (configHolder.getProperties().get("FIRSTREDEEMPAY").equals("true")) {
							if (configHolder.getAmountfirstRedeemBonus().containsKey((int) userRedeem.getAmount())) {
								firstRedeemPay = true;
								userAccountSummary.setEttId(dbPersister.getUserAccountSummaryEttId(usaId));
							}
						}
					}
				}

				if (sos) {
					dbPersister.updateThreashHoldSpecial(userRedeem.getEttId(), 0);
				}

				if (userActivityBooster) {
					dbPersister.updateUserActivityBooster(userActivityBoosterId, 2);
				}

				if (firstRedeemPay) {
					userAccountSummary.setOfferId(7005l);
					userAccountSummary.setOfferName("First Redeem Bonus");
					int amount1 = configHolder.getfirstRedeemBonus((int) userRedeem.getAmount()).intValue();
					userAccountSummary.setAmount(amount1);
					userAccountSummary.setRemarks("First Redeem Bonus");
					dbPersister.firstRedeemOffer(userAccountSummary);
					log.info("Invite Extra Recharge Bonus [ ettId=" + userAccountSummary.getEttId() + " ] [amount = "+amount1+"]");
				}

				log.info("redeem successful ettId=" + user.getEttId());

				if (dToken != null && !dToken.getDeviceToken().equals("")) {
					String txt = pushServiceImpl.getSuccessPush(isSuccess, userRedeem);
					sendPush(txt, dToken);
				}
				try {
					if (Float.parseFloat(user.getAppVersion()) >= 1.3f) {
						PopUpSheduled popUpSheduled = dbPersister.getPopUpSheduled(userRedeem.getEttId());
						if (popUpSheduled == null) {
							dbPersister.setPopuSheduled(userRedeem.getEttId());
						}
					}
				} catch (Exception ex) {
					log.error("ERROR !! in checking the popUpSchedule: " + ex);
					ex.printStackTrace();
				}
				continue;
			}

			if (dToken != null && !dToken.getDeviceToken().equals("")) {
				String txt = pushServiceImpl.getFailurePush(isSuccess, userRedeem);
				sendPush(txt, dToken);
			}

			userRedeem.setStatus("FAILED");
			dbPersister.updateFailRedeem(user, userRedeem);
			log.info("redeem failed ettId=" + user.getEttId());

			if (userActivityBooster) {
				dbPersister.updateUserActivityBooster(userActivityBoosterId, 1);
			}
		}
	}

	private String rechargeBonus(User user) {
		String resp = "";
		if (configHolder.getProperties().get("APRIL_FOOL_OFFER").equals("true")) {
			long start = System.currentTimeMillis();
			String url = configHolder.getProperties().get("APRIL_FOOL_OFFER_API").replace("#ETTID#", user.getEttId() + "").replace("#OTP#", user.getOtp() + "");
			SendGetRequest sendGetRequest = new SendGetRequest();
			resp = sendGetRequest.sendRequest(url);

			log.info("APRIL FOOL OFFER API|" + url + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));
			if (!resp.equals("")) {
				return resp;
			}
		}
		return resp;
	}

	// CHECK MSISDN
	private void CheckMsisdn(User user, UserRedeem userRedeem) {

		if (user.getMsisdn().equalsIgnoreCase(userRedeem.getMsisdn() + "")) {
			log.info("Msisdn same : " + user.getMsisdn());
		} else {
			log.info("Msisdn different : " + userRedeem.getMsisdn());

			boolean isAvailable = dbPersister.checkMsisdn(userRedeem.getMsisdn());

			if (!isAvailable) {

				log.info("Msisdn Not Available : " + userRedeem.getMsisdn());
				if (configHolder.getProperties().get("REDEEM_SMS_ENABLE").equals("true")) {

					Utility utility = new Utility();
					String msg = userRedeem.getMsisdn() + "#congratulations you received FREE recharge of Rs. " + userRedeem.getAmount()
							+ " from earn talktime, you can also get FREE money and recharge Mobile for FREE by downloading the Earn Talktime App, click http://ri.earntalktime.com";
					utility.SendUDP(msg, "54.209.220.78", "7171");
				}
			}
		}
	}

	private String checkStatusFlipkart(UserRedeem redeem) {

		long start = System.currentTimeMillis();
		String url = "https://egv.flipkart.net/gcms/api/1.0/egv/view/tid/" + redeem.getTrans_id();
		SendGetRequest request = new SendGetRequest();
		String resp = request.sendPost(url);
		log.info("RECHARGE URL|" + url + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));
		if (resp.equalsIgnoreCase("TIMEOUT")) {
			return resp;
		}
		return getStatusCode(resp);
	}

	private String getStatusCode(String resp) {

		try {
			JSONObject object = new JSONObject(resp);
			String statusCode = object.getString("statusCode");

			if (statusCode.equalsIgnoreCase("SUCCESS")) {
				JSONArray list = object.getJSONArray("egvList");
				JSONObject object2 = list.getJSONObject(0);
				String status = object2.getString("status");
				return status;
			}
			return "FAILED";
		} catch (JSONException e) {
			e.printStackTrace();
			log.info("Error!! in getTansactionId");
			return "FAILED";
		}
	}

	private String checkStatusOxygen(UserRedeem redeem) {

		long start = System.currentTimeMillis();
		String url = "https://oximall.com/EnquiryAppnew/Default.aspx";
		String postData = "transid=" + redeem.getTrans_id_ett() + "&merchantrefno=CHECKSTATUS," + redeem.getMsisdn();
		SendGetRequest request = new SendGetRequest();
		String resp = request.sendRequestOxygen(url, postData);
		log.info("RECHARGE URL|" + url + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));
		if (resp.equalsIgnoreCase("TIMEOUT")) {
			return resp;
		}
		String resp1[] = resp.split("\\|");
		if (resp1[0].equals("0") || resp1[0].equals("1") || resp1[0].equals("31")) {
			try {
				String resp2[] = resp1[2].split("-");
				redeem.setTrans_id(resp2[1].trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "SUCCESS";
		} else if (Integer.parseInt(resp1[0].trim()) < 0) {
			return "TIMEOUT";
		} else {
			return "FAILED";
		}
	}

	private String checkStatusApt(UserRedeem redeem) {

		/*
		 * int diffInDays = (int) ((redeem.getCreatedTime().getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24)); if(diffInDays<=1){ log.info("Older than one day considering failed"); return
		 * "FAILED"; }
		 */
		long start = System.currentTimeMillis();
		String url = "http://aptrecharge.in/api/rechargestatus.php?uid=616e7572616731393832&pin=5328526938345&usertx=" + redeem.getTrans_id_ett() + "&version=4";
		SendGetRequest request = new SendGetRequest();
		String resp = request.sendRequest(url);
		log.info("RECHARGE URL|" + url + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));
		if (resp.equalsIgnoreCase("TIMEOUT")) {
			return resp;
		}
		String respArray[] = resp.split(",");
		redeem.setTrans_id(respArray[0]);
		if (respArray.length < 3) {
			return "INVALID_RESP_" + resp;
		}
		if (respArray[2].equalsIgnoreCase("PENDING")) {
			return "TIMEOUT";
		}
		return respArray[2];
	}

	private void sendPush(final String pushText, final DeviceToken dToken) {
		try {
			jmsTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {

					MapMessage mapMessage = session.createMapMessage();
					mapMessage.setString("ID", dToken.getDeviceToken());
					mapMessage.setString("DISPLAY_STRING", pushText);
					mapMessage.setInt("BADGE_COUNT", 1);
					mapMessage.setString("DEVICE_TYPE", "ANDROID");
					mapMessage.setString("type", "BALANCE");
					return mapMessage;
				}
			});
		} catch (Exception e) {
			log.error("error in redeem push " + e);
			e.printStackTrace();
		}
	}
}
