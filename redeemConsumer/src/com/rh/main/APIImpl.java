package com.rh.main;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
import com.rh.persistence.domain.PopUpSheduled;
import com.rh.persistence.domain.User;
import com.rh.persistence.domain.UserAccountSummary;
import com.rh.persistence.domain.UserRedeem;
import com.rh.persistence.domain.UserRedeem.RedeemType;
import com.rh.remote.SendGetRequest;
import com.rh.utility.FlipkartEGV;

/*
 * Implementation class of APIInterface and SessionHolder interfaces.
 * Initialize various managers related to various services that simulator provides.
 * Implementation of session handlers.
 */
public class APIImpl implements APIInterface {

	private PushInterface pushServiceImpl;
	private DBPersister dbPersister;
	private ConfigHolder configHolder;
	private JmsTemplate jmsTemplate;
	private RHServiceInterface rhServiceImpl;
//	BlockingQueue<String> fifo = null;
	
	 //new sendSMS(fifo);
	private static Log log = LogFactory.getLog(APIImpl.class);

	/*
	 * Constructor use to initialize various managers and DBPersister variables.
	 */
	public APIImpl(DBPersister dp, JmsTemplate jmsTemplate) throws IOException {
		this.dbPersister = dp;
		this.configHolder = new ConfigHolder();
		this.jmsTemplate = jmsTemplate;
		this.pushServiceImpl = new PushServiceImpl();
		this.rhServiceImpl = new RHServiceImpl();
		/*if (configHolder.getProperties().getProperty("FLIPKART_EGV_ENABLED").equals("true")) {
			if (configHolder.getProperties().getProperty("FLIPKART_EGV_SMS_ENABLED").equals("true")) {
				fifo =new LinkedBlockingQueue<String>();
				new sendSMS(fifo);
			}
		}*/
	}

	@Override
	public void updatePokktInfo(String id) {
		UserRedeem userRedeem = dbPersister.getUserRedeem(id);

		// if(configHolder.getProperties().getProperty("PROCESS_FEE_DEDUCT").equals("true")&&
		// userRedeem.getFee()<=0.0f && userRedeem.getEttId()==17) {
		if (userRedeem.getType() == null || userRedeem.getType().equals("")) {
			userRedeem.setType("PREPAID");
		}
		if (configHolder.getProperties().getProperty("PROCESS_FEE_DEDUCT").equals("true") && userRedeem.getFee() <= 0.0f) {
			rhServiceImpl.setConvenienceCharge(userRedeem, dbPersister);
		}
		if (userRedeem.getEttId() == null) {
			log.warn("userRedeem not found |" + id);
			return;
		}
		/*** Barred Redeem Check ***/
		if (configHolder.getProperties().getProperty("BARRED_USER_CHECK") != null && configHolder.getProperties().getProperty("BARRED_USER_CHECK").equals("true")) {
			if (configHolder.getBarredStatus(userRedeem.getEttId())) {
				dbPersister.updateBarredRedeem(userRedeem);
				log.info("[Redeem Barred] for the ettId=" + userRedeem.getEttId() + " | id=" + userRedeem.getId());
				return;
			}
		}
		/*************************/
		String isSuccess = "";
		log.info("UserRedeem|" + userRedeem.toString());
		User user = dbPersister.getUser(userRedeem.getEttId());
		int toDayAmount = dbPersister.getTodayAmount(userRedeem.getEttId());
		if ((toDayAmount + userRedeem.getAmount()) >= Integer.parseInt(configHolder.getProperties().getProperty("DAY_LIMIT"))) {
			log.info("Day limit [" + configHolder.getProperties().getProperty("DAY_LIMIT") + "] crossed for ettId=" + userRedeem.getEttId());
			dbPersister.updateFailRedeem(user, userRedeem);
			DeviceToken dToken = dbPersister.getDeviceId(user.getDeviceId(), user.getEttId());
			if (dToken != null && !dToken.getDeviceToken().equals("")) {
				String pushText = configHolder.getProperties().getProperty("DAY_LIMIT_CROSS_NOTF");
				if (!pushText.equals(""))
					pushText = pushText.replaceFirst("#MOBILE_NUMBER#", userRedeem.getMsisdn() + "");
				sendPush(pushText, dToken);
			}
			return;
		}
		if (user.getMsisdn().length() != 10) {
			log.info("user msisdn length is not equal to 10 so fail ettId=" + userRedeem.getEttId());
			dbPersister.updateFailRedeem(user, userRedeem);
			return;

		}
		/*******************************/
		/*
		 * UserSource usersource = dbPersister.getUserSource(user.getEttId()); if(usersource.getUtmMedium().equals("INVITE")){ Long inviteeEttId = usersource.getUtmSource(); User userInvitee =
		 * dbPersister.getUser(inviteeEttId); if(user.getDeviceId().equals(userInvitee.getDeviceId())){ log.info( "Device Id same for both invitee and inviter so request dump for "); } }
		 */
		/*******************************/

		// if(configHolder.getProperties().getProperty("LOAN_SERVICE_ENABLE").equals("true"))
		// {

		/* ####################################################LOAN HANDLING########################################################################### */

		if (userRedeem.getRedeemType().equals(RedeemType.LOAN)) {

			if (configHolder.getProperties().getProperty("LOAN_SERVICE_ENABLE").equals("true")) {
				isSuccess = giveBalanceApt(userRedeem);
				if (isSuccess.equalsIgnoreCase("SUCCESS") || isSuccess.equalsIgnoreCase("PENDING")) {

				} else {
					isSuccess = giveBalanceOxygen(userRedeem);
				}
			} else {
				isSuccess = "FAIL";
			}
		}

		/* ####################################################LOAN HANDLING########################################################################### */

		switch (userRedeem.getType().toUpperCase()) {

		case "EGV": {
			isSuccess = EGV(userRedeem);
			if (!isSuccess.equalsIgnoreCase("SUCCESS") && configHolder.getProperties().getProperty("FLIPKART_EGV_ENABLED").equals("true")) {
				isSuccess = "FAIL";
			}
		}
			break;

		case "NEXGTV": {
			isSuccess = NEXGTV(userRedeem);
			if (!isSuccess.equalsIgnoreCase("SUCCESS") && configHolder.getProperties().getProperty("FLIPKART_EGV_ENABLED").equals("true")) {
				isSuccess = "FAIL";
			}
		}
			break;

		case "":
		case "PREPAID": {
			if (configHolder.getProperties().getProperty("IS_GLOBAL_URL").equals("true")) {
				isSuccess = giveGlobalBalance(userRedeem.getMsisdn(), (int) userRedeem.getAmount(), userRedeem);
			}

			// temp code for airtel
			else if (configHolder.getProperties().getProperty("AIRTEL_MCARBON_ENABLE").equals("true") && userRedeem.getOperator().equalsIgnoreCase("AIRTEL")) {
				// userRedeem.setVender("MC");
				isSuccess = giveAirtelBalance(userRedeem.getMsisdn(), (int) userRedeem.getAmount(), userRedeem);
			} else {
				isSuccess = giveBalanceApt(userRedeem);
			}

			if (configHolder.getProperties().getProperty("AIRTEL_MCARBON_FALLBACK").equals("true") && userRedeem.getOperator().equalsIgnoreCase("AIRTEL") && !isSuccess.equalsIgnoreCase("SUCCESS")
					&& !isSuccess.equalsIgnoreCase("PENDING")) {

				isSuccess = giveAirtelBalance(userRedeem.getMsisdn(), (int) userRedeem.getAmount(), userRedeem);
			}

			if (isSuccess.equalsIgnoreCase("SUCCESS") || isSuccess.equalsIgnoreCase("PENDING")) {
			} else {
				isSuccess = giveBalanceOxygen(userRedeem);
			}
		}
			break;

		case "POSTPAID": {
			isSuccess = giveBalanceOxygenPostPaid(userRedeem);
			if ((!isSuccess.equalsIgnoreCase("SUCCESS")) && configHolder.getProperties().getProperty("APT_POSTPAID_ENABLE").equals("true")) {
				isSuccess = giveBalanceAptPostPaid(userRedeem);
			}
		}
			break;

		case "DTH": {
			isSuccess = giveBalanceOxygenDth(userRedeem);
			if (!isSuccess.equalsIgnoreCase("SUCCESS") && configHolder.getProperties().getProperty("APT_DTH_ENABLE").equals("true")) {
				isSuccess = giveBalanceAptDth(userRedeem);
			}
		}
			break;

		default:
			log.error("ERROR !! Redeem type has not recognised the process for id : " + userRedeem.getId());
			break;
		}

		if (isSuccess.equalsIgnoreCase("SUCCESS") || isSuccess.equalsIgnoreCase("PENDING")) {
			String status = isSuccess.equalsIgnoreCase("SUCCESS") ? isSuccess.toUpperCase() : "SUCESS_P";
			userRedeem.setStatus(status);
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
			} else {
				userAccountSummary.setOfferId(0l);
				userAccountSummary.setOfferName("redemption");
				userAccountSummary.setAmount(-(userRedeem.getAmount()));
				userAccountSummary.setRemarks("Recharged");
			}
			dbPersister.updateSuccessRedeem(user, userAccountSummary, userRedeem);
			log.info("redeem successful ettId=" + user.getEttId());
			DeviceToken dToken = dbPersister.getDeviceId(user.getDeviceId(), user.getEttId());
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

		DeviceToken dToken = dbPersister.getDeviceId(user.getDeviceId(), user.getEttId());
		if (dToken != null && !dToken.getDeviceToken().equals("")) {
			String txt = pushServiceImpl.getFailurePush(isSuccess, userRedeem);
			sendPush(txt, dToken);
		}
		userRedeem.setStatus("FAILED");
		dbPersister.updateFailRedeem(user, userRedeem);
		log.info("redeem failed ettId=" + user.getEttId());
	}

	// NEXGTV creation
	private String NEXGTV(UserRedeem userRedeem) {

		try {
			if (!configHolder.getProperties().getProperty("IS_NEXGTV_ENABLE").equals("true")) {
				return "";
			}
			long tId = System.currentTimeMillis() + userRedeem.getEttId();
			userRedeem.setVender("NEXGTV");
			userRedeem.setTrans_id_ett(String.valueOf(tId));
			String url = configHolder.getProperties().getProperty("NEXGTV_URL").replace("#MSISDN#", userRedeem.getMsisdn() + "").replace("#AMOUNT#", ((int) userRedeem.getAmount()) + "").replace("#TID#", tId + "")
					.replace("#VALIDITY#", configHolder.getValidityPriceMapNexgenTV((int) userRedeem.getAmount()) + "");
			SendGetRequest sendGetRequest = new SendGetRequest();
			long start = System.currentTimeMillis();
			String resp = sendGetRequest.sendRequest(url);
			log.info("RECHARGE URL|" + url + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));
			userRedeem.setTrans_id_ett(String.valueOf(tId));

			String respArray[] = resp.split("|");
			if (respArray[0].equals("00x1")) {
				return "SUCCESS";
			} else {
				return "FAILED";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.info("[error in giveGlobleBalance][" + ex + "]");
			return "FAILED";
		}
	}

	// Flipkart EGV creation
	private String EGV(UserRedeem userRedeem) {
		if (!configHolder.getProperties().getProperty("FLIPKART_EGV_ENABLED").equals("true")) {
			return "";
		}
		FlipkartEGV egv = new FlipkartEGV(userRedeem, dbPersister,configHolder,jmsTemplate);
		String resp = egv.createEGV();
		return resp;
	}

	private String giveBalanceAptDth(UserRedeem userRedeem) {
		if (!configHolder.getProperties().getProperty("IS_ATP_DTH_ENABLE").equals("true")) {
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
		String respArray[] = resp.split(",");
		if (respArray.length < 3)
			return "INVALID_RESP_" + resp;

		userRedeem.setTrans_id(respArray[0]);
		userRedeem.setTrans_id_ett(String.valueOf(tId));
		return respArray[2];
	}

	private String giveBalanceAptPostPaid(UserRedeem userRedeem) {
		if (!configHolder.getProperties().getProperty("IS_ATP_POSTPAID_ENABLE").equals("true")) {
			return "";
		}
		long tId = System.currentTimeMillis() + userRedeem.getEttId();
		if (userRedeem.getOperator().equals("Reliance"))
			userRedeem.setOperator("Reliance GSM");
		userRedeem.setVender("ATP");
		String url = "http://aptrecharge.in/api/recharge.php?uid=616e7572616731393832&pin=5328526938345&number=" + userRedeem.getMsisdn() + "&operator="
				+ configHolder.getPostPaidOperatorId(userRedeem.getOperator().toUpperCase()) + "&circle=" + configHolder.getCircleId(userRedeem.getCircle().toUpperCase()) + "&amount=" + ((int) userRedeem.getAmount())
				+ "&usertx=" + tId + "&format=csv&version=4";
		SendGetRequest sendGetRequest = new SendGetRequest();
		long start = System.currentTimeMillis();
		String resp = sendGetRequest.sendRequest(url);
		log.info("RECHARGE URL|" + url + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));
		String respArray[] = resp.split(",");

		if (respArray.length < 3)
			return "INVALID_RESP_" + resp;

		userRedeem.setTrans_id(respArray[0]);
		userRedeem.setTrans_id_ett(String.valueOf(tId));
		return respArray[2];
	}

	private String giveGlobalBalance(Long msisdn, int amount, UserRedeem userRedeem) {
		String resp = null;
		SendGetRequest sendGetRequest = new SendGetRequest();
		String amount1 = "0";
		String validResp = dbPersister.getValidResp(msisdn);
		if (validResp == null || validResp.equals("")) {
			String validateUrl = configHolder.getProperties().getProperty("GLOBAL_VALIDATION_URL");
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
				String url = configHolder.getProperties().getProperty("GLOBAL_RECHARGE_URL");
				String postData = configHolder.getProperties().getProperty("GLOBAL_RECHARGE_DATA");
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
		String url = configHolder.getProperties().getProperty("AIRTEL_RECHARGE_URL") + "?msisdn=" + msisdn + "&units=" + amount;
		userRedeem.setVender("MC");
		long start = System.currentTimeMillis();
		String resp = sendGetRequest.sendRequest(url);
		log.info("RECHARGE URL|" + url + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));

		if ((!resp.equalsIgnoreCase("SUCCESS")) && configHolder.getIsGetApi()) {
			long tId = System.currentTimeMillis() + userRedeem.getEttId();
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
			if (!configHolder.getProperties().getProperty("IS_JOLO_ENABLE").equals("true")) {
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
		String resp1[] = resp.split("\\|");
		// if(resp1[0].equals("0") || resp1[0].equals("1") ||
		// resp1[0].equals("31")) {
		if (resp1[0].equals("0")) {
			userRedeem.setVender("OXYGEN");
			return "SUCCESS";
		} else if (Integer.parseInt(resp1[0].trim()) < 0) {
			userRedeem.setVender("OXYGEN");
			return "PENDING";
		} else {
			return "FAIL";
		}
	}

	private String giveBalanceOxygen(UserRedeem userRedeem) {
		try {
			if (configHolder.getProperties().getProperty("IS_OXYGEN_PREPAID_ENABLE").equals("true")) {
				if (userRedeem.getOperator().equalsIgnoreCase("AIRTEL") && (!configHolder.getProperties().getProperty("IS_AIRTEL_ON_OXYGEN").equals("true"))) {
					log.info("Airtel Oxygen close id=" + userRedeem.getId());
					return "";
				}

				String url = configHolder.getProperties().getProperty("OXYGEN_RECHARGE_URL");
				String postData = configHolder.getProperties().getProperty("OXYGEN_RECHARGE_PREPAID_PARAMETER").replace("#TID#", getTidOxygen("yyyyMMDDHHmmssSSS") + userRedeem.getEttId())
						.replace("#MSISDN#", userRedeem.getMsisdn() + "").replace("#OPERATOR#", configHolder.getOperatorKeyMapOxygenPrepaid(userRedeem.getOperator()))
						.replace("#AMOUNT#", ((int) userRedeem.getAmount()) + "").replace("#REQUESTDATE#", getTidOxygen("yyyyMMddHHmmss"));
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
		if (configHolder.getProperties().getProperty("IS_OXYGEN_POSTPAID_ENABLE").equals("true")) {
			if (userRedeem.getOperator().equalsIgnoreCase("AIRTEL") && (!configHolder.getProperties().getProperty("IS_AIRTEL_ON_OXYGEN").equals("true"))) {
				log.info("Airtel Oxygen close id=" + userRedeem.getId());
				return "";
			}
			String url = configHolder.getProperties().getProperty("OXYGEN_RECHARGE_URL");
			String postData = configHolder.getProperties().getProperty("OXYGEN_RECHARGE_PREPAID_PARAMETER").replace("#TID#", getTidOxygen("yyyyMMDDHHmmssSSS") + userRedeem.getEttId())
					.replace("#MSISDN#", userRedeem.getMsisdn() + "").replace("#OPERATOR#", configHolder.getOperatorKeyMapOxygenPostpaid(userRedeem.getOperator())).replace("#AMOUNT#", ((int) userRedeem.getAmount()) + "")
					.replace("#REQUESTDATE#", getTidOxygen("yyyyMMddHHmmss"));
			SendGetRequest sendGetRequest = new SendGetRequest();
			long start = System.currentTimeMillis();
			String resp = sendGetRequest.sendRequestOxygen(url, postData);
			log.info("RECHARGE URL OXYGEN|" + url + "?" + postData + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));

			return getOxygenResponse(userRedeem, resp);
		}
		return "";
	}

	private String giveBalanceOxygenDth(UserRedeem userRedeem) {
		if (configHolder.getProperties().getProperty("IS_OXYGEN_DTH_ENABLE").equals("true")) {
			if (userRedeem.getOperator().equalsIgnoreCase("AIRTEL") && (!configHolder.getProperties().getProperty("IS_AIRTEL_ON_OXYGEN").equals("true"))) {
				log.info("Airtel Oxygen close id=" + userRedeem.getId());
				return "";
			}
			String url = configHolder.getProperties().getProperty("OXYGEN_RECHARGE_URL");
			String postData = configHolder.getProperties().getProperty("OXYGEN_RECHARGE_DTH_PARAMETER").replace("#TID#", getTidOxygen("yyyyMMDDHHmmssSSS") + userRedeem.getEttId())
					.replace("#MSISDN#", userRedeem.getMsisdn() + "").replace("#OPERATOR#", configHolder.getOperatorKeyMapOxygenDth(userRedeem.getOperator())).replace("#AMOUNT#", ((int) userRedeem.getAmount()) + "")
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
		if (!configHolder.getProperties().getProperty("IS_JOLO_ENABLE").equals("true")) {
			return "";
		}
		long tId = System.currentTimeMillis() + userRedeem.getEttId();
		SendGetRequest sendGetRequest = new SendGetRequest();
		userRedeem.setVender("JOLO");
		String url2 = "http://joloapi.com/api/recharge.php?mode=1&userid=anurag&key=405552127516438&operator=" + configHolder.getOperatorKey(userRedeem.getOperator().toUpperCase()) + "&service=" + userRedeem.getMsisdn()
				+ "&amount=" + ((int) userRedeem.getAmount()) + "&orderid=" + tId;
		// 273680092955545
		long start = System.currentTimeMillis();
		String resp2 = sendGetRequest.sendRequest(url2);
		String respArray2[] = resp2.split(",");
		log.info("RECHARGE2 URL|" + url2 + "|RESP|" + resp2 + "|TIME|" + (System.currentTimeMillis() - start));
		if (respArray2.length < 2)
			return "INVALID_RESP_" + resp2;

		userRedeem.setTrans_id(respArray2[0]);
		userRedeem.setTrans_id_ett(String.valueOf(tId));
		return respArray2[1];
	}

	private String giveBalanceApt(UserRedeem userRedeem) {
		/*
		 * userAccount.setCurrentBalance((userAccount.getCurrentBalance()- userRedeem.getAmount())); userAccountRepository.save(userAccount);
		 */
		if (!configHolder.getProperties().getProperty("IS_ATP_PREPAID_ENABLE").equals("true")) {
			return "";
		}
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

		userRedeem.setTrans_id(respArray[0]);
		userRedeem.setTrans_id_ett(String.valueOf(tId));
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