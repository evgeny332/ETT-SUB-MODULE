package com.rh.main;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.rh.config.DBConfigHolder;
import com.rh.interfaces.APIInterface;
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
import com.rh.utility.FlipkartEGV;
import com.rh.utility.PineLabsEGV;
import com.rh.utility.Utility;

/*
 * Implementation class of APIInterface and SessionHolder interfaces.
 * Initialize various managers related to various services that simulator provides.
 * Implementation of session handlers.
 */
public class APIImpl implements APIInterface {

	private PushInterface pushServiceImpl;
	private DBPersister dbPersister;
	private DBConfigHolder configHolder;
	private JmsTemplate jmsTemplate;
	// private RHServiceInterface rhServiceImpl;

	private static Log log = LogFactory.getLog(APIImpl.class);

	/*
	 * Constructor use to initialize various managers and DBPersister variables.
	 */

	public APIImpl(DBPersister dp, JmsTemplate jmsTemplate) throws IOException {
		this.dbPersister = dp;
		this.configHolder = new DBConfigHolder(dp);
		this.jmsTemplate = jmsTemplate;
		this.pushServiceImpl = new PushServiceImpl();
		// this.rhServiceImpl = new RHServiceImpl();
	}

	@Override
	public void updatePokktInfo(String id) {
		UserRedeem userRedeem = dbPersister.getUserRedeem(id);

		if (userRedeem.getType() == null || userRedeem.getType().equals("")) {
			userRedeem.setType("PREPAID");
		}

		if (userRedeem.getEttId() == null) {
			log.warn("userRedeem not found |" + id);
			return;
		}

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

		/************* BARRED REDEEM CHECK **************/
		if (configHolder.getProperties().get("BARRED_USER_CHECK") != null && configHolder.getProperties().get("BARRED_USER_CHECK").equals("true")) {
			if (configHolder.getBarredStatus(userRedeem.getEttId())) {
				dbPersister.updateBarredRedeem(userRedeem);
				if (userActivityBooster) {
					dbPersister.updateUserActivityBooster(userActivityBoosterId, 1);
				}
				log.info("[Redeem Barred] for the ettId=" + userRedeem.getEttId() + " | id=" + userRedeem.getId());
				return;
			}
		}

		/**********************************/
		String isSuccess = "";
		log.info("UserRedeem|" + userRedeem.toString());
		User user = dbPersister.getUser(userRedeem.getEttId());
		DeviceToken dToken = dbPersister.getDeviceId(user.getDeviceId(), user.getEttId());

		/************** DAILY LIMIT *****************/
		int toDayAmount = dbPersister.getTodayAmount(userRedeem.getEttId());
		if ((toDayAmount + userRedeem.getAmount()) >= Integer.parseInt(configHolder.getProperties().get("DAY_LIMIT"))) {
			log.info("Day limit [" + configHolder.getProperties().get("DAY_LIMIT") + "] crossed for ettId=" + userRedeem.getEttId());
			dbPersister.updateFailRedeem(user, userRedeem);
			if (userActivityBooster) {
				dbPersister.updateUserActivityBooster(userActivityBoosterId, 1);
			}
			if (dToken != null && !dToken.getDeviceToken().equals("")) {
				String pushText = configHolder.getProperties().get("DAY_LIMIT_CROSS_NOTF");
				if (!pushText.equals(""))
					pushText = pushText.replaceFirst("#MOBILE_NUMBER#", userRedeem.getMsisdn() + "");
				sendPush(pushText, dToken);
			}
			return;
		}

		/************** MONTHLY LIMIT *****************/
		// considering that UserRedeem contains data of this year only//
		if (configHolder.getProperties().get("IS_MONTHLY_LIMIT_ENABLE").equalsIgnoreCase("true")) {
			int monthlyLimit = dbPersister.getMonthlyAmount(userRedeem.getEttId());
			if (monthlyLimit >= Integer.parseInt(configHolder.getProperties().get("MONTHLY_LIMIT"))) {
				log.info("Monthly limit [" + configHolder.getProperties().get("MONTHLY_LIMIT") + "] for recharge crossed for ettId=" + userRedeem.getEttId());
				dbPersister.updateFailRedeem(user, userRedeem);
				if (userActivityBooster) {
					dbPersister.updateUserActivityBooster(userActivityBoosterId, 1);
				}
				if (dToken != null && !dToken.getDeviceToken().equals("")) {
					String pushText = configHolder.getProperties().get("MONTHLY_LIMIT_CROSS_NOTF");
					if (!pushText.equals(""))
						pushText = pushText.replaceFirst("#MOBILE_NUMBER#", userRedeem.getMsisdn() + "");
					sendPush(pushText, dToken);
				}
				return;
			}
		}

		/*********** EGV MONTHLY LIMIT **************/

		if (userRedeem.getType().equalsIgnoreCase("EGV") && configHolder.getProperties().get("IS_EGV_MONTHLY_LIMIT_ENABLE").equalsIgnoreCase("true")) {
			int egvmonthlyLimit = dbPersister.getMonthlyEGV(userRedeem.getEttId());
			if (egvmonthlyLimit >= Integer.parseInt(configHolder.getProperties().get("EGV_MONTHLY_LIMIT"))) {
				log.info("Monthly limit [" + configHolder.getProperties().get("EGV_MONTHLY_LIMIT") + "] of EGV crossed for ettId=" + userRedeem.getEttId());
				dbPersister.updateFailRedeem(user, userRedeem);
				if (userActivityBooster) {
					dbPersister.updateUserActivityBooster(userActivityBoosterId, 1);
				}
				if (dToken != null && !dToken.getDeviceToken().equals("")) {
					String pushText = configHolder.getProperties().get("EGV_MONTHLY_LIMIT_CROSS_NOTF");
					if (!pushText.equals(""))
						pushText = pushText.replaceFirst("#MOBILE_NUMBER#", userRedeem.getMsisdn() + "");
					sendPush(pushText, dToken);
				}
				return;
			}
		}

		// DeviceToken dToken = dbPersister.getDeviceId(user.getDeviceId(), user.getEttId());

		/************ BSNL JK CHECK ************/
		/*
		 * if (userRedeem.getOperator().equalsIgnoreCase("BSNL") && userRedeem.getCircle().equalsIgnoreCase("JK")){ userRedeem.setStatus("FAILED"); dbPersister.updateFailRedeem(user, userRedeem);
		 * sendPush("service down on this operator, please try tomorrow", dToken); log.info("redeem not valid BSNL JK ettId=" + user.getEttId()); return; }
		 */

		/************ MSISDN LENGTH CHECK ************/
		if (user.getMsisdn().length() != 10) {
			log.info("user msisdn length is not equal to 10 so fail ettId=" + userRedeem.getEttId());
			dbPersister.updateFailRedeem(user, userRedeem);
			if (userActivityBooster) {
				dbPersister.updateUserActivityBooster(userActivityBoosterId, 1);
			}
			return;
		}

		/************** FIRST REDEEM ***************/
		boolean firstRedeemPay = false;
		// int diffInDays = (int) ((userRedeem.getCreatedTime().getTime() - user.getRegDate().getTime()) / (1000 * 60 * 60 * 24));
		// log.info("Date diff=" + diffInDays);
		// if (diffInDays < 7) {
		// if (configHolder.getProperties().get("FIRSTREDEEMPAY").equals("true")) {
		// if (configHolder.getAmountfirstRedeemBonus().containsKey((int) userRedeem.getAmount())) {
		// if (user.getRedeemCount() == 0) {
		// firstRedeemPay = true;
		// }
		// }
		// }
		// }

		/* ####################################################LOAN HANDLING########################################################################### */

		if (userRedeem.getRedeemType().equals(RedeemType.LOAN)) {

			if (configHolder.getProperties().get("LOAN_SERVICE_ENABLE").equals("true")) {
				isSuccess = giveBalanceApt(userRedeem);
				if (!isSuccess.equalsIgnoreCase("SUCCESS") && !isSuccess.equalsIgnoreCase("TIMEOUT")) {
					isSuccess = giveBalanceOxygen(userRedeem);
				}
			} else {
				isSuccess = "FAILED";
			}
		}

		/* ####################################################LOAN HANDLING########################################################################### */

		switch (userRedeem.getType().toUpperCase()) {

		case "EGV": {
			isSuccess = EGV(userRedeem);
			if (!isSuccess.equalsIgnoreCase("SUCCESS") && !isSuccess.equalsIgnoreCase("TIMEOUT")
					&& (configHolder.getProperties().get("PINELABS_EGV_ENABLED").equals("true") || configHolder.getProperties().get("FLIPKART_EGV_ENABLED").equals("true"))) {
				isSuccess = "FAILED";
			}
		}
			break;

		case "TVANDMUSIC": {
			isSuccess = NEXGTV(userRedeem);
			if (!isSuccess.equalsIgnoreCase("SUCCESS") && !isSuccess.equalsIgnoreCase("TIMEOUT") && configHolder.getProperties().get("IS_NEXGTV_ENABLE").equals("true")) {
				isSuccess = "FAILED";
			}
		}
			break;

		case "":
		case "PREPAID": {

			if (userRedeem.getRedeemType().equals(RedeemType.BROWSEPLAN)) {
				isSuccess = giveBalanceOxygen(userRedeem);
				break;
			}

			if (configHolder.getProperties().get("IS_GLOBAL_URL").equals("true")) {
				isSuccess = giveGlobalBalance(userRedeem.getMsisdn(), (int) userRedeem.getAmount(), userRedeem);
			}

			// temp code for airtel

			if (userRedeem.getOperator().equalsIgnoreCase("AIRTEL")) {
				isSuccess = giveBalanceApt(userRedeem);
				if (!isSuccess.equalsIgnoreCase("SUCCESS") && !isSuccess.equalsIgnoreCase("TIMEOUT") && configHolder.getProperties().get("AIRTEL_MCARBON_ENABLE").equals("true")) {
					isSuccess = giveAirtelBalance(userRedeem.getMsisdn(), (int) userRedeem.getAmount(), userRedeem);
				}
				if (!isSuccess.equalsIgnoreCase("SUCCESS") && !isSuccess.equalsIgnoreCase("TIMEOUT")) {
					isSuccess = giveBalanceOxygen(userRedeem);
				}
			} else {
				if (!isSuccess.equalsIgnoreCase("SUCCESS") && configHolder.getProperties().get("PREPAID_PRIORITY").equals("OXYGEN")) {
					isSuccess = giveBalanceOxygen(userRedeem);
				} else {
					isSuccess = giveBalanceApt(userRedeem);
				}
				if (!isSuccess.equalsIgnoreCase("SUCCESS") && !isSuccess.equalsIgnoreCase("TIMEOUT")) {
					if (!configHolder.getProperties().get("PREPAID_PRIORITY").equals("OXYGEN")) {
						isSuccess = giveBalanceOxygen(userRedeem);
					} else {
						isSuccess = giveBalanceApt(userRedeem);
					}
				}
			}

			if (configHolder.getProperties().get("AIRTEL_MCARBON_FALLBACK").equals("true") && userRedeem.getOperator().equalsIgnoreCase("AIRTEL") && !isSuccess.equalsIgnoreCase("SUCCESS")
					&& !isSuccess.equalsIgnoreCase("TIMEOUT")) {
				isSuccess = giveAirtelBalance(userRedeem.getMsisdn(), (int) userRedeem.getAmount(), userRedeem);
			}

		}
			break;

		case "POSTPAID": {
			isSuccess = giveBalanceOxygenPostPaid(userRedeem);
			if ((!isSuccess.equalsIgnoreCase("SUCCESS")) && !isSuccess.equalsIgnoreCase("TIMEOUT")) {
				isSuccess = giveBalanceAptPostPaid(userRedeem);
			}
		}
			break;

		case "DTH": {
			isSuccess = giveBalanceOxygenDth(userRedeem);
			if (!isSuccess.equalsIgnoreCase("SUCCESS") && !isSuccess.equalsIgnoreCase("TIMEOUT")) {
				isSuccess = giveBalanceAptDth(userRedeem);
			}
		}
			break;

		default:
			log.error("ERROR !! Redeem type has not recognised the process for id : " + userRedeem.getId());
			break;
		}

		if (isSuccess.equalsIgnoreCase("TIMEOUT")) {
			userRedeem.setStatus(isSuccess);
			dbPersister.updateRedeemStatus(userRedeem);
			return;
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
				if (userRedeem.getRedeemType().equals(RedeemType.BROWSEPLAN)) {
					userAccountSummary.setRemarks("Recharged Browseplan");
				} else {
					userAccountSummary.setRemarks("Recharged");
				}
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
				userAccountSummary.setOfferName("REDEEM_" + user.getMsisdn());
				int amount1 = configHolder.getfirstRedeemBonus((int) userRedeem.getAmount()).intValue();
				userAccountSummary.setAmount(amount1);
				userAccountSummary.setRemarks(userRedeem.getEttId() + "");
				dbPersister.firstRedeemOffer(userAccountSummary);
				log.info("Invite Extra Recharge Bonus [ ettId=" + userAccountSummary.getEttId() + " ] [amount = " + amount1 + "]");
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
			return;
		}

		if (isSuccess.equalsIgnoreCase("SUBSCRIBED")) {
			if (dToken != null && !dToken.getDeviceToken().equals("")) {
				sendPush(configHolder.getProperties().get("NEXGTV_SUBSCRIBED_PUSH"), dToken);
				return;
			}
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

	// API for RECHARGE BONUS
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

	// NEXGTV creation
	private String NEXGTV(UserRedeem userRedeem) {

		try {
			if (!configHolder.getProperties().get("IS_NEXGTV_ENABLE").equals("true")) {
				return "";
			}
			long tId = System.currentTimeMillis() + userRedeem.getEttId();
			userRedeem.setVender("NEXGTV");
			userRedeem.setTrans_id_ett(String.valueOf(tId));
			String url = configHolder.getProperties().get("NEXGTV_URL").replace("#MSISDN#", userRedeem.getMsisdn() + "").replace("#AMOUNT#", ((int) userRedeem.getAmount()) + "").replace("#TID#", tId + "")
					.replace("#VALIDITY#", configHolder.getValidityPriceMapNexgenTV((int) userRedeem.getAmount()) + "");
			SendGetRequest sendGetRequest = new SendGetRequest();
			long start = System.currentTimeMillis();
			String resp = sendGetRequest.sendRequest(url);
			log.info("RECHARGE URL|" + url + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));

			String resp1 = "";
			if (resp.equalsIgnoreCase("TIMEOUT")) {
				return resp;
			}

			String respArray[] = resp.split("\\|");
			if (respArray[0].trim().equalsIgnoreCase("00x1")) {
				resp1 = "SUCCESS";
			} else if (respArray[0].trim().equalsIgnoreCase("00z3")) {
				resp1 = "SUBSCRIBED";
			} else {
				resp1 = "FAILED";
			}
			return resp1;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.info("[error in giveGlobleBalance][" + ex + "]");
			return "FAILED";
		}
	}

	// EGV creation
	private String EGV(UserRedeem userRedeem) {
		if (userRedeem.getVender().equals("PineLabs") && configHolder.getProperties().get("PINELABS_EGV_ENABLED").equals("true")) {

			PineLabsEGV egv = new PineLabsEGV(userRedeem, dbPersister, configHolder);
			String resp = egv.createEGV();
			return resp;

		} else if (userRedeem.getVender().equals("Flipkart") && configHolder.getProperties().get("FLIPKART_EGV_ENABLED").equals("true")) {
			FlipkartEGV egv = new FlipkartEGV(userRedeem, dbPersister, configHolder);
			String resp = egv.createEGV();
			return resp;

		} else {
			return "";
		}

	}

	private String giveBalanceAptDth(UserRedeem userRedeem) {
		if (!configHolder.getProperties().get("IS_ATP_DTH_ENABLE").equals("true")) {
			return "";
		}
		long tId = System.currentTimeMillis() + userRedeem.getEttId();
		userRedeem.setVender("ATP");
		userRedeem.setTrans_id_ett(String.valueOf(tId));
		String url = "http://aptrecharge.in/api/recharge.php?uid=616e7572616731393832&pin=5328526938345&number=" + userRedeem.getMsisdn() + "&operator="
				+ configHolder.getDthOperatorId(userRedeem.getOperator().toUpperCase()) + "&amount=" + ((int) userRedeem.getAmount()) + "&usertx=" + tId + "&format=csv&version=4";
		SendGetRequest sendGetRequest = new SendGetRequest();
		long start = System.currentTimeMillis();
		String resp = sendGetRequest.sendRequest(url);
		log.info("RECHARGE URL|" + url + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));
		if (resp.equalsIgnoreCase("TIMEOUT")) {
			return resp;
		}
		String respArray[] = resp.split(",");
		userRedeem.setTrans_id(respArray[0]);
		if (respArray.length < 3) {
			return "INVALID_RESP_" + resp;
		}
		if (respArray[2].equalsIgnoreCase("PENDING")) {
			return "TIMEOUT";
		}
		return respArray[2];
	}

	private String giveBalanceAptPostPaid(UserRedeem userRedeem) {
		if (!configHolder.getProperties().get("IS_ATP_POSTPAID_ENABLE").equals("true")) {
			return "";
		}
		long tId = System.currentTimeMillis() + userRedeem.getEttId();
		if (userRedeem.getOperator().equals("Reliance"))
			userRedeem.setOperator("Reliance GSM");
		userRedeem.setVender("ATP");
		userRedeem.setTrans_id_ett(String.valueOf(tId));
		String url = "http://aptrecharge.in/api/recharge.php?uid=616e7572616731393832&pin=5328526938345&number=" + userRedeem.getMsisdn() + "&operator="
				+ configHolder.getPostPaidOperatorId(userRedeem.getOperator().toUpperCase()) + "&circle=" + configHolder.getCircleId(userRedeem.getCircle().toUpperCase()) + "&amount=" + ((int) userRedeem.getAmount())
				+ "&usertx=" + tId + "&format=csv&version=4";
		SendGetRequest sendGetRequest = new SendGetRequest();
		long start = System.currentTimeMillis();
		String resp = sendGetRequest.sendRequest(url);
		log.info("RECHARGE URL|" + url + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));

		if (resp.equalsIgnoreCase("TIMEOUT")) {
			return resp;
		}
		String respArray[] = resp.split(",");
		userRedeem.setTrans_id(respArray[0]);
		if (respArray.length < 3) {
			return "INVALID_RESP_" + resp;
		}
		if (respArray[2].equalsIgnoreCase("PENDING")) {
			return "TIMEOUT";
		}
		return respArray[2];
	}

	private String giveGlobalBalance(Long msisdn, int amount, UserRedeem userRedeem) {
		String resp = null;
		SendGetRequest sendGetRequest = new SendGetRequest();
		String amount1 = "0";
		String validResp = dbPersister.getValidResp(msisdn);
		if (validResp == null || validResp.equals("")) {
			String validateUrl = configHolder.getProperties().get("GLOBAL_VALIDATION_URL");
			validateUrl = validateUrl.replaceFirst("<ettId>", "17");
			validateUrl = validateUrl.replaceFirst("<msisdn>", msisdn + "");
			sendGetRequest.sendRequest(validateUrl);
			validResp = dbPersister.getValidResp(msisdn);

		}
		try {
			String validOkResp = validResp;
			String validOkResp1[] = validOkResp.split(";");

			String validOkResp2[] = validOkResp1[2].split(",");
			String validOkResp3[] = validOkResp1[7].split(",");
			for (int i = 0; i < validOkResp3.length; i++) {
				if (validOkResp3[i].equals(amount + "")) {
					amount1 = validOkResp2[i];
				}
			}
			if (!amount1.equals("0")) {
				String url = configHolder.getProperties().get("GLOBAL_RECHARGE_URL");
				String postData = configHolder.getProperties().get("GLOBAL_RECHARGE_DATA");
				postData = postData.replaceFirst("<msisdn>", validOkResp1[5]);
				postData = postData.replaceFirst("<AMOUNT>", amount1);
				userRedeem.setVender("ccard");
				long start = System.currentTimeMillis();
				resp = sendGetRequest.sendRequest(url, postData);
				log.info("RECHARGE URL|" + url + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));
			}
			if (resp.indexOf("TOPUP OK") >= 0) {
				resp = "SUCCESS";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.info("[error in giveGlobleBalance][" + ex + "]");
			resp = "";
		}
		return resp;
	}

	private String giveAirtelBalance(Long msisdn, int amount, UserRedeem userRedeem) {
		SendGetRequest sendGetRequest = new SendGetRequest();
		String url = configHolder.getProperties().get("AIRTEL_RECHARGE_URL") + "?msisdn=" + msisdn + "&units=" + amount;
		userRedeem.setVender("MC");
		long start = System.currentTimeMillis();
		String resp = sendGetRequest.sendRequest(url);
		log.info("RECHARGE URL|" + url + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));

		if ((!resp.equalsIgnoreCase("SUCCESS")) && configHolder.getIsGetApi()) {
			if (resp.equalsIgnoreCase("TIMEOUT")) {
				return resp;
			}
			long tId = System.currentTimeMillis() + userRedeem.getEttId();
			userRedeem.setVender("GETAPI");
			userRedeem.setTrans_id_ett(tId + "");
			String url3 = "http://www.getapi.in/api/recharge.php?utid=" + tId + "&sender=rationalheads&pin=98103&keyword=" + configHolder.getOperatorKeyGETAPI(userRedeem.getOperator().toUpperCase()) + "&mobile="
					+ userRedeem.getMsisdn() + "&amount=" + ((int) userRedeem.getAmount());
			start = System.currentTimeMillis();
			String resp3 = sendGetRequest.sendRequest(url3);
			log.info("RECHARGE3 URL|" + url3 + "|RESP|" + resp3 + "|TIME|" + (System.currentTimeMillis() - start));
			if (resp3.equalsIgnoreCase("TIMEOUT")) {
				return resp3;
			}
			String respArray3[] = resp3.split(",");
			if (respArray3.length >= 3) {
				return respArray3[3];
			} else {
				return resp3;
			}
		} else {
			return resp;
		}
	}

	@SuppressWarnings("unused")
	private String giveBalance(UserRedeem userRedeem) {
		/*
		 * userAccount.setCurrentBalance((userAccount.getCurrentBalance()- userRedeem.getAmount())); userAccountRepository.save(userAccount);
		 */
		long tId = System.currentTimeMillis() + userRedeem.getEttId();
		if (userRedeem.getOperator().equals("Reliance"))
			userRedeem.setOperator("Reliance GSM");
		userRedeem.setVender("ATP");
		String url = "http://aptrecharge.in/api/recharge.php?uid=616e7572616731393832&pin=5328526938345&number=" + userRedeem.getMsisdn() + "&operator="
				+ configHolder.getOperatorId(userRedeem.getOperator().toUpperCase()) + "&circle=" + configHolder.getCircleId(userRedeem.getCircle().toUpperCase()) + "&amount=" + ((int) userRedeem.getAmount())
				+ "&usertx=" + tId + "&format=csv&version=4";
		SendGetRequest sendGetRequest = new SendGetRequest();
		long start = System.currentTimeMillis();
		String resp = sendGetRequest.sendRequest(url);
		log.info("RECHARGE URL|" + url + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));
		String respArray[] = resp.split(",");
		if (respArray.length < 3)
			return "INVALID_RESP_" + resp;

		if (!(respArray[2].equalsIgnoreCase("SUCCESS") || respArray[2].equalsIgnoreCase("PENDING"))) {
			if (!configHolder.getProperties().get("IS_JOLO_ENABLE").equals("true")) {
				return resp;
			}
			// try by another vendor
			userRedeem.setVender("JOLO");
			String url2 = "http://joloapi.com/api/recharge.php?mode=1&userid=anurag&key=405552127516438&operator=" + configHolder.getOperatorKey(userRedeem.getOperator().toUpperCase()) + "&service="
					+ userRedeem.getMsisdn() + "&amount=" + ((int) userRedeem.getAmount()) + "&orderid=" + tId;
			// 273680092955545
			start = System.currentTimeMillis();
			String resp2 = sendGetRequest.sendRequest(url2);
			log.info("RECHARGE2 URL|" + url2 + "|RESP|" + resp2 + "|TIME|" + (System.currentTimeMillis() - start));
			String respArray2[] = resp2.split(",");
			if ((!(respArray2[1].equalsIgnoreCase("SUCCESS"))) && configHolder.getIsGetApi()) {
				String url3 = "http://www.getapi.in/api/recharge.php?utid=" + tId + "&sender=rationalheads&pin=98103&keyword=" + configHolder.getOperatorKeyGETAPI(userRedeem.getOperator().toUpperCase()) + "&mobile="
						+ userRedeem.getMsisdn() + "&amount=" + ((int) userRedeem.getAmount());
				start = System.currentTimeMillis();
				userRedeem.setVender("GETAPI");
				String resp3 = sendGetRequest.sendRequest(url3);
				log.info("RECHARGE3 URL|" + url3 + "|RESP|" + resp3 + "|TIME|" + (System.currentTimeMillis() - start));
				String respArray3[] = resp3.split(",");
				if (respArray3.length >= 3) {
					return respArray3[3];
				} else {
					return resp3;
				}
			} else {
				return respArray2[1];
			}

			/*
			 * if(respArray2.length<2) return "INVALID_RESP_"+resp2;
			 */
		} else {
			return respArray[2];
		}
	}

	private String getTidOxygen(String format) {
		DateFormat dateFormat1 = new SimpleDateFormat(format);
		Date date1 = new Date();
		return dateFormat1.format(date1);
	}

	private String getOxygenResponse(UserRedeem userRedeem, String resp) {
		if (resp.equalsIgnoreCase("TIMEOUT")) {
			return resp;
		}
		String resp1[] = resp.split("\\|");
		// if(resp1[0].equals("0") || resp1[0].equals("1") ||
		// resp1[0].equals("31")) {
		if (resp1[0].equals("0")) {
			try {
				String resp2[] = resp1[1].split("-");
				userRedeem.setTrans_id(resp2[1].trim());
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

	private String giveBalanceOxygen(UserRedeem userRedeem) {
		try {
			if (configHolder.getProperties().get("IS_OXYGEN_PREPAID_ENABLE").equals("true")) {
				if (userRedeem.getOperator().equalsIgnoreCase("AIRTEL") && (!configHolder.getProperties().get("IS_AIRTEL_ON_OXYGEN").equals("true"))) {
					log.info("Airtel Oxygen close id=" + userRedeem.getId());
					return "";
				}

				String url = configHolder.getProperties().get("OXYGEN_RECHARGE_URL");
				userRedeem.setVender("OXYGEN");
				String tid = getTidOxygen("yyyyMMDDHHmmssSSS") + userRedeem.getEttId();
				userRedeem.setTrans_id_ett(tid);
				String postData = "";
				if (userRedeem.getCircle() == null || userRedeem.getCircle().equals("")) {
					postData = "transid=" + tid + "&merchantrefno=Topup," + userRedeem.getMsisdn() + "," + configHolder.getOperatorKeyMapOxygenPrepaid_1(userRedeem.getOperator()) + "&amount="
							+ ((int) userRedeem.getAmount()) + "&requestdate=" + getTidOxygen("yyyyMMddHHmmss") + "&status=0&bankrefno=8";
				} else {
					postData = configHolder.getProperties().get("OXYGEN_RECHARGE_PREPAID_PARAMETER").replace("#TID#", tid).replace("#MSISDN#", userRedeem.getMsisdn() + "")
							.replace("#OPERATOR#", configHolder.getOperatorKeyMapOxygenPrepaid(userRedeem.getOperator())).replace("#AMOUNT#", ((int) userRedeem.getAmount()) + "")
							.replace("#CIRCLE#", configHolder.getCircleKeyMapOxygen(userRedeem.getCircle().toUpperCase())).replace("#REQUESTDATE#", getTidOxygen("yyyyMMddHHmmss"));
				}

				SendGetRequest sendGetRequest = new SendGetRequest();
				long start = System.currentTimeMillis();
				String resp = sendGetRequest.sendRequestOxygen(url, postData);
				log.info("RECHARGE URL OXYGEN|" + url + "?" + postData + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));

				return getOxygenResponse(userRedeem, resp);
			}
			return "";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	private String giveBalanceOxygenPostPaid(UserRedeem userRedeem) {
		if (configHolder.getProperties().get("IS_OXYGEN_POSTPAID_ENABLE").equals("true")) {
			if (userRedeem.getOperator().equalsIgnoreCase("AIRTEL") && (!configHolder.getProperties().get("IS_AIRTEL_ON_OXYGEN").equals("true"))) {
				log.info("Airtel Oxygen close id=" + userRedeem.getId());
				return "";
			}
			String url = configHolder.getProperties().get("OXYGEN_RECHARGE_URL");
			userRedeem.setVender("OXYGEN");
			String tid = getTidOxygen("yyyyMMDDHHmmssSSS") + userRedeem.getEttId();
			userRedeem.setTrans_id_ett(tid);
			String postData = "";
			if (userRedeem.getCircle() == null || userRedeem.getCircle().equals("")) {
				postData = "transid=" + tid + "&merchantrefno=Topup," + userRedeem.getMsisdn() + "," + configHolder.getOperatorKeyMapOxygenPostpaid_1(userRedeem.getOperator()) + "&amount="
						+ ((int) userRedeem.getAmount()) + "&requestdate=" + getTidOxygen("yyyyMMddHHmmss") + "&status=0&bankrefno=8";
			} else {
				postData = configHolder.getProperties().get("OXYGEN_RECHARGE_PREPAID_PARAMETER").replace("#TID#", tid).replace("#MSISDN#", userRedeem.getMsisdn() + "")
						.replace("#OPERATOR#", configHolder.getOperatorKeyMapOxygenPostpaid(userRedeem.getOperator())).replace("#AMOUNT#", ((int) userRedeem.getAmount()) + "")
						.replace("#CIRCLE#", configHolder.getCircleKeyMapOxygen(userRedeem.getCircle().toUpperCase())).replace("#REQUESTDATE#", getTidOxygen("yyyyMMddHHmmss"));
			}
			SendGetRequest sendGetRequest = new SendGetRequest();
			long start = System.currentTimeMillis();
			String resp = sendGetRequest.sendRequestOxygen(url, postData);
			log.info("RECHARGE URL OXYGEN|" + url + "?" + postData + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));

			return getOxygenResponse(userRedeem, resp);
		}
		return "";
	}

	private String giveBalanceOxygenDth(UserRedeem userRedeem) {
		if (configHolder.getProperties().get("IS_OXYGEN_DTH_ENABLE").equals("true")) {
			if (userRedeem.getOperator().equalsIgnoreCase("AIRTEL") && (!configHolder.getProperties().get("IS_AIRTEL_ON_OXYGEN").equals("true"))) {
				log.info("Airtel Oxygen close id=" + userRedeem.getId());
				return "";
			}
			String url = configHolder.getProperties().get("OXYGEN_RECHARGE_URL");
			userRedeem.setVender("OXYGEN");
			String tid = getTidOxygen("yyyyMMDDHHmmssSSS") + userRedeem.getEttId();
			userRedeem.setTrans_id_ett(tid);
			String postData = configHolder.getProperties().get("OXYGEN_RECHARGE_DTH_PARAMETER").replace("#TID#", tid).replace("#MSISDN#", userRedeem.getMsisdn() + "")
					.replace("#OPERATOR#", configHolder.getOperatorKeyMapOxygenDth(userRedeem.getOperator())).replace("#AMOUNT#", ((int) userRedeem.getAmount()) + "")
					.replace("#REQUESTDATE#", getTidOxygen("yyyyMMddHHmmss"));
			SendGetRequest sendGetRequest = new SendGetRequest();
			long start = System.currentTimeMillis();
			String resp = sendGetRequest.sendRequestOxygen(url, postData);
			log.info("RECHARGE URL OXYGEN|" + url + "?" + postData + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));
			return getOxygenResponse(userRedeem, resp);
		}
		return "";
	}

	@SuppressWarnings("unused")
	private String giveBalanceJolo(UserRedeem userRedeem) {
		if (!configHolder.getProperties().get("IS_JOLO_ENABLE").equals("true")) {
			return "";
		}
		long tId = System.currentTimeMillis() + userRedeem.getEttId();
		userRedeem.setTrans_id_ett(String.valueOf(tId));
		SendGetRequest sendGetRequest = new SendGetRequest();
		userRedeem.setVender("JOLO");
		String url2 = "http://joloapi.com/api/recharge.php?mode=1&userid=anurag&key=405552127516438&operator=" + configHolder.getOperatorKey(userRedeem.getOperator().toUpperCase()) + "&service=" + userRedeem.getMsisdn()
				+ "&amount=" + ((int) userRedeem.getAmount()) + "&orderid=" + tId;
		// 273680092955545
		long start = System.currentTimeMillis();
		String resp2 = sendGetRequest.sendRequest(url2);
		if (resp2.equalsIgnoreCase("TIMEOUT")) {
			return resp2;
		}
		String respArray2[] = resp2.split(",");
		log.info("RECHARGE2 URL|" + url2 + "|RESP|" + resp2 + "|TIME|" + (System.currentTimeMillis() - start));
		if (respArray2.length < 2)
			return "INVALID_RESP_" + resp2;

		userRedeem.setTrans_id(respArray2[0]);
		return respArray2[1];
	}

	private String giveBalanceApt(UserRedeem userRedeem) {
		/*
		 * userAccount.setCurrentBalance((userAccount.getCurrentBalance()- userRedeem.getAmount())); userAccountRepository.save(userAccount);
		 */
		if (!configHolder.getProperties().get("IS_ATP_PREPAID_ENABLE").equals("true")) {
			return "";
		}
		long tId = System.currentTimeMillis() + userRedeem.getEttId();
		if (userRedeem.getOperator().equals("Reliance"))
			userRedeem.setOperator("Reliance GSM");
		userRedeem.setVender("ATP");
		userRedeem.setTrans_id_ett(String.valueOf(tId));
		String url = "http://aptrecharge.in/api/recharge.php?uid=616e7572616731393832&pin=5328526938345&number=" + userRedeem.getMsisdn() + "&operator="
				+ configHolder.getOperatorId(userRedeem.getOperator().toUpperCase()) + "&circle=" + configHolder.getCircleId(userRedeem.getCircle().toUpperCase()) + "&amount=" + ((int) userRedeem.getAmount())
				+ "&usertx=" + tId + "&format=csv&version=4";
		SendGetRequest sendGetRequest = new SendGetRequest();
		long start = System.currentTimeMillis();
		String resp = sendGetRequest.sendRequest(url);
		log.info("RECHARGE URL|" + url + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));
		if (resp.equalsIgnoreCase("TIMEOUT")) {
			return resp;
		}
		String respArray[] = resp.split(",");
		userRedeem.setTrans_id(respArray[0]);
		if (respArray.length < 3)
			return "INVALID_RESP_" + resp;
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