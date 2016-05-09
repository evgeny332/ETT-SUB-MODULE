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
import com.rh.persistence.DBPersister;
import com.rh.persistence.domain.DeviceToken;
import com.rh.persistence.domain.User;
import com.rh.persistence.domain.UserAccount;
import com.rh.persistence.domain.UserAccountSummary;
import com.rh.persistence.domain.UserRedeem;
import com.rh.remote.SendGetRequest;

/*
 * Implementation class of APIInterface and SessionHolder interfaces.
 * Initialize various managers related to various services that simulator provides.
 * Implementation of session handlers.
 */
public class APIImpl implements APIInterface {
	private DBPersister dbPersister;
	private ConfigHolder configHolder;
	private JmsTemplate jmsTemplate;
	private static Log log = LogFactory.getLog(APIInterface.class);
	private static Log oLog = LogFactory.getLog(APIImpl.class);

	/*
	 * Constructor use to initialize various managers and DBPersister variables.
	 */
	public APIImpl(DBPersister dp, JmsTemplate jmsTemplate) throws IOException {
		this.dbPersister = dp;
		this.configHolder = new ConfigHolder();
		this.jmsTemplate = jmsTemplate;
		
	}

	@Override
	public void updatePokktInfo(String id) {
		UserRedeem userRedeem = dbPersister.gerUserRedeem(id);
		if(userRedeem.getEttId() == null){
			log.warn("userRedeem not found |"+id);
			return;
		}
		
		
		
		String isSuccess = "";
		
		log.info("UserRedeem|"+userRedeem.toString());
		
		User user = dbPersister.getUser(userRedeem.getEttId());
		
		//check user account balance
		UserAccount currentBalance = dbPersister.getUserAccount(userRedeem.getEttId());
		if(currentBalance.getCurrentBalance() < -10){
			log.warn("current balance is less than 0 : ettId="+user.getEttId());
			userRedeem.setStatus("FAILED");
	    	dbPersister.updateFailRedeem(userRedeem);
	    	log.info("redeem failed ettId="+user.getEttId());
			return;
		}
		
		//temp code for airtel		
		if(configHolder.getProperties().getProperty("AIRTEL_MCARBON_ENABLE").equals("true") && userRedeem.getOperator().equalsIgnoreCase("AIRTEL")){
			isSuccess = giveAirtelBalance(userRedeem.getMsisdn(), (int)userRedeem.getAmount());			
		}else{
			isSuccess = giveBalance(userRedeem);			
		}		
		
		
		if(isSuccess.equalsIgnoreCase("SUCCESS") || isSuccess.equalsIgnoreCase("PENDING")){
			String status = isSuccess.equalsIgnoreCase("SUCCESS") ? isSuccess.toUpperCase() : "SUCESS_P";
    		userRedeem.setStatus(status); 	
    		UserAccountSummary userAccountSummary = new UserAccountSummary();
			userAccountSummary.setEttId(user.getEttId());
			userAccountSummary.setOfferId(118l);
			userAccountSummary.setOfferName("Recharge via loan");
			userAccountSummary.setAmount(-userRedeem.getAmount());
			userAccountSummary.setRemarks("Recharge via loan");
			dbPersister.updateSuccessRedeem(user, userAccountSummary, userRedeem);
			log.info("loan redeem successful ettId="+user.getEttId());
			DeviceToken dToken = dbPersister.getDeviceId(user.getDeviceId());
			if(dToken != null && !dToken.getDeviceToken().equals("")){
				String pushText = configHolder.getProperties().getProperty("REDEEM_SUCCESS_PUSH").replaceFirst("#AMOUNT#", userRedeem.getAmount()+"");
	        	pushText = pushText.replaceFirst("#MOBILE_NUMBER#", userRedeem.getMsisdn()+"");
	        	pushText = pushText.replaceFirst("#LOAN_AMOUNT#", userRedeem.getLoanAmount()+"");
	        	sendPush(pushText, dToken);
			}        	        	
        	return;      
    	}
		DeviceToken dToken = dbPersister.getDeviceId(user.getDeviceId());
		if(dToken!=null && !dToken.getDeviceToken().equals("")){
			String pushText = configHolder.getProperties().getProperty("REDEEM_FAIL_PUSH").replaceFirst("#AMOUNT#", userRedeem.getAmount()+"");
			/*if(userRedeem.getOperator().equalsIgnoreCase("AIRTEL")){
				pushText = configHolder.getProperties().getProperty("AIRTEL_REDEEM_FAIL_PUSH").replaceFirst("#AMOUNT#", userRedeem.getAmount()+"");
			}*/
			pushText = pushText.replaceFirst("#MOBILE_NUMBER#", userRedeem.getMsisdn()+"");
			sendPush(pushText, dToken);
		}
    	userRedeem.setStatus("FAILED");
    	dbPersister.updateFailRedeem(user, userRedeem);
    	log.info("redeem failed ettId="+user.getEttId());
	}
	
	private String giveAirtelBalance(Long msisdn, int amount) {
		SendGetRequest sendGetRequest = new SendGetRequest();
		String url = configHolder.getProperties().getProperty("AIRTEL_RECHARGE_URL")+"?msisdn="+msisdn+"&units="+amount;
		String resp = sendGetRequest.sendRequest(url);	
		log.info("RECHARGE URL|"+url+"|RESP|"+resp);
		if(resp==null){
			return "ERROR";
		}
		return resp;
	}

	private String giveBalance(UserRedeem userRedeem) {
		/*userAccount.setCurrentBalance((userAccount.getCurrentBalance()-userRedeem.getAmount()));
		userAccountRepository.save(userAccount);*/
		long tId= System.currentTimeMillis();
		if(userRedeem.getOperator().equals("Reliance"))
			userRedeem.setOperator("Reliance GSM");
		String url = "http://aptrecharge.in/api/recharge.php?uid=616e7572616731393832&pin=5328526938345&number="+userRedeem.getMsisdn()+"&operator="
						+configHolder.getOperatorId(userRedeem.getOperator())+
						"&circle="+configHolder.getCircleId(userRedeem.getCircle())
						+"&amount="+(int)userRedeem.getAmount()+"&usertx="
						+tId+"&format=csv&version=4";
		SendGetRequest sendGetRequest = new SendGetRequest();
		String resp = sendGetRequest.sendRequest(url);
		log.info("RECHARGE URL|"+url+"|RESP|"+resp);
		if(resp==null){
			return "ERROR";
		}
		String respArray[] = resp.split(",");
		if(respArray.length<3)
			return "INVALID_RESP_"+resp;
		
		if(!(respArray[2].equalsIgnoreCase("SUCCESS") || respArray[2].equalsIgnoreCase("PENDING"))){
			//try by another vendor
			String url2 = "http://jolo.in/api/recharge.php?mode=1&key=crationalheads399&operator="+configHolder.getOperatorKey(userRedeem.getOperator())+"&service="+userRedeem.getMsisdn()+"&amount="+(int)userRedeem.getAmount()+"&orderid="+tId;
			String resp2 = sendGetRequest.sendRequest(url2);
			log.info("RECHARGE2 URL|"+url2+"|RESP|"+resp2);
			if(resp2==null){
				return "ERROR";
			}
			String respArray2[] = resp2.split(",");
			if(respArray2.length<2)
				return "INVALID_RESP_"+resp2;
			return respArray2[1];
			
		}else{
			return respArray[2];
		}
	}
	
	  private void sendPush(final String pushText, final DeviceToken dToken) {
	  	   try{
	  			jmsTemplate.send(new MessageCreator() {
	  	            @Override
	  	            public Message createMessage(Session session) throws JMSException {
	  	            	
	  	                MapMessage mapMessage = session.createMapMessage();
	  	                mapMessage.setString("ID", dToken.getDeviceToken());
	  	                mapMessage.setString("DISPLAY_STRING", pushText);
	  	                mapMessage.setInt("BADGE_COUNT", 1);
	  	                mapMessage.setString("DEVICE_TYPE", "ANDROID");
	  	                return mapMessage;
	  	            }
	  	        });
	  		}catch(Exception e){
	  			log.error("error in redeem push "+e);
	  			e.printStackTrace();
	  		}
	  		
	  	}
	
}