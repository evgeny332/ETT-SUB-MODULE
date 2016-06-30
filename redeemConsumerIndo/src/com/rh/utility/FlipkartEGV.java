package com.rh.utility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.rh.config.DBConfigHolder;
import com.rh.persistence.DBPersister;
import com.rh.persistence.domain.GiftVoucher;
import com.rh.persistence.domain.UserRedeem;
import com.rh.remote.SendGetRequest;

public class FlipkartEGV {

	private static Log log = LogFactory.getLog(FlipkartEGV.class);
	private UserRedeem userRedeem;
	private DBPersister dbPersister;
	private DBConfigHolder configHolder;
//	private JmsTemplate jmsTemplate;
//	private BlockingQueue<String> fifo = null;

	public FlipkartEGV(UserRedeem userRedeem, DBPersister dbPersister, DBConfigHolder configHolder2) {
		try {
			this.dbPersister = dbPersister;
			this.userRedeem = userRedeem;
			this.configHolder = configHolder2;
//			this.jmsTemplate = jmsTemplate;
//			this.fifo = fifo;

		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR !! in FlipkartEGV: " + e);
		}
	}

	public String createEGV() {

		long start = System.currentTimeMillis();
		long tId = System.currentTimeMillis() + userRedeem.getEttId();
		userRedeem.setVender("Flipkart");
		userRedeem.setTrans_id_ett(String.valueOf(tId));
		String trans = createTranasaction();
		if (!trans.equals("FAILED")) {
			userRedeem.setTrans_id(trans);
			String url = configHolder.getProperties().get("FLIPKART_EGV_URL") + "egv";
			String resp = CreateGiftVoucher(url);
			log.info("RECHARGE URL|" + url + "|RESP|" + resp + "|TIME|" + (System.currentTimeMillis() - start));
			
			if(resp.equalsIgnoreCase("TIMEOUT")){
				return resp;
			}
			if (resp.equals("ACTIVE")) {
				return "SUCCESS";
			}
		}
		return "FAILED";
	}

	private String createTranasaction() {
		String url = configHolder.getProperties().get("FLIPKART_EGV_URL") + "transaction";
		SendGetRequest sendGetRequest = new SendGetRequest();
		String resp = sendGetRequest.sendPost(url);
		String trans = getTansactionId(resp);
		userRedeem.setTrans_id(trans);
		return trans;
	}

	private String CreateGiftVoucher(String url) {

		String postData = null;
		try {
			SendGetRequest sendGetRequest = new SendGetRequest();

			JSONObject jsonObject = new JSONObject();
			JSONObject jsonObject1 = new JSONObject();

			jsonObject.put("transactionId", userRedeem.getTrans_id() + "");
			jsonObject.put("denomination", (int) userRedeem.getAmount() + "");
			jsonObject1.put("medium", "INLINE");
			jsonObject1.put("format", "JSON");
			jsonObject.put("recipient", jsonObject1);

			postData = jsonObject.toString();
			String resp = sendGetRequest.sendPost(url, postData);

			// appendResult();
			// return "FAILED";
			if(resp.equalsIgnoreCase("TIMEOUT")){
				return resp;
			}
			return JsonInline(resp);

		} catch (JSONException e) {

			log.error("Error !! in CreateGiftVoucher | Json" + postData + "", e);
			e.printStackTrace();
			return "FAILED";
		}
	}

	private String getTansactionId(String resp) {

		try {
			JSONObject object = new JSONObject(resp);
			String status = object.getString("statusCode");
			String transactionId = object.getString("transactionId");
			String transactionStatus = object.getString("transactionStatus");

			if (status.equalsIgnoreCase("SUCCESS") && transactionStatus.equalsIgnoreCase("AVAILABLE")) {
				return transactionId;
			}
			return "FAILED";
		} catch (JSONException e) {
			e.printStackTrace();
			log.info("Error!! in getTansactionId");
			return "FAILED";
		}
	}

	private String JsonInline(String resp) {
		try {

			GiftVoucher giftVoucher = new GiftVoucher();
			JSONObject object = new JSONObject(resp);

			giftVoucher.setEttId(userRedeem.getEttId());

			if (object.getString("statusCode").equals("CREATION_SUCCESSFUL")) {

				JSONObject object2 = object.getJSONObject("egv");
				JSONObject object3 = object2.getJSONObject("recipient");
				String status = object2.getString("status");
				String code = object2.getString("code");
				String pin = object2.getString("pin");
				int amount = object2.getInt("balance");
				String expiryDate = object2.getString("expiryDate");
				String medium = object3.getString("medium");
				String dispatchStatus = object3.getString("status");

				giftVoucher.setName("Flipkart Gift Voucher");
				giftVoucher.setAmount(amount);
				giftVoucher.setCode(code);
				giftVoucher.setExpiryDate(expiryDate);
				giftVoucher.setMedium(medium);
				giftVoucher.setPin(pin);
				giftVoucher.setStatus(status);
				giftVoucher.setTransactionId(userRedeem.getTrans_id());
				giftVoucher.setDispatchStatus(dispatchStatus);

				try {

					dbPersister.insertGiftVoucher(giftVoucher);
					if (configHolder.getProperties().get("FLIPKART_EGV_SMS_ENABLED").equals("true")) {

						final String msg = userRedeem.getMsisdn() + "#" + configHolder.getProperties().get("FLIPKART_EGV_SMS").replace("#AMOUNT#", amount + "").replace("#CODE#", code).replace("#PIN#", pin)
								.replace("#EXPIRYDATE#", expiryDate).replace("#STATUS#", status);
						// final String url = configHolder.getProperties().get("FLIPKART_SMS_URL").replace("#MSISDN#", userRedeem.getMsisdn() + "").replace("#MSG#", msg);

						try {

							Utility utility = new Utility();
							utility.SendUDP(msg, "54.209.220.78", "7171");

							// jmsTemplate.send("GiftSMS", new MessageCreator() {
							// @Override public Message createMessage(Session session) throws JMSException { return session.createTextMessage(url); } });
							// if (fifo == null) {
							// return "";
							// }
							// final String url = configHolder.getProperties().get("FLIPKART_SMS_URL").replace("#MSISDN#", userRedeem.getMsisdn() + "");
							// fifo.add(url);

						} catch (Exception e) {
							log.error("error in redeem push " + e);
							e.printStackTrace();
						}

					}

					return status;

				} catch (Exception e) {
					e.printStackTrace();
					log.info("Error!! in insertGiftVoucher ");
					return "FAILED";
				}
			}

			return "FAILED";
		} catch (JSONException e) {
			e.printStackTrace();
			log.info("Error!! in ParsingJSON: ");
			return "FAILED";
		}
	}

//	@SuppressWarnings("unused")
	/*private void appendResult() {
		try {
			if (fifo == null) {
				return;
			}
			final String url = configHolder.getProperties().get("FLIPKART_SMS_URL").replace("#MSISDN#", userRedeem.getMsisdn() + "");
			fifo.add(url);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Error!! in insertGiftVoucher ");
		}
	}*/
}
