package com.rh.edrconsumer.main;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.rh.edrconsumer.interfaces.APIInterface;
import com.rh.edrconsumer.persistence.DBPersister;
import com.rh.edrconsumer.spring.scheduler.ConfigSheduler;
import com.rh.persistence.edrconsumer.domain.DeviceToken;
import com.rh.persistence.edrconsumer.domain.OfferDetails;
import com.rh.persistence.edrconsumer.domain.UserPushInfo;
/*
 * Implementation class of APIInterface and SessionHolder interfaces.
 * Initialize various managers related to various services that simulator provides.
 * Implementation of session handlers.
 */
public class APIImpl implements APIInterface  {
	private DBPersister dbPersister;
	private ConfigSheduler configSheduler;
	private JmsTemplate jmsTemplate;
	
	static Properties configFile = new Properties();
	private static int noOfNotification = 0;
	private static int noOfDays = 0;
	private static boolean isLIECheckOn = false;
	
	private static Log oLog = LogFactory.getLog(APIImpl.class);

	/*
	 * Constructor use to initialize various managers and DBPersister variables.
	 */
	public APIImpl(DBPersister dp, ConfigSheduler configSheduler, JmsTemplate jmsTemplate) throws IOException {
		this.dbPersister = dp;
		this.configSheduler = configSheduler;
		this.jmsTemplate = jmsTemplate;
		try {
			//configFile.load(APIImpl.class.getClassLoader().getResourceAsStream("config.properties"));
			org.springframework.core.io.Resource resource = new ClassPathResource("config.properties");
			this.configFile = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			oLog.error("Error in getting config file : "+e);
			System.exit(-1);
		}
		noOfNotification = Integer.parseInt(configFile.getProperty("NO_OF_NOTIFICATION"));
		noOfDays = Integer.parseInt(configFile.getProperty("NO_OF_DAY"));
		if(configFile.getProperty("LOSS_IN_EARNING_CHECK").equalsIgnoreCase("true")){
			isLIECheckOn = true;
		}
		this.configSheduler.updateOffers();
	}
	
	public void unInstalledAppHandle(long ettId,String type,List InstalledApps) {
		
		try {
		//List<String> cInstalledApps = new ArrayList<String>(Arrays.asList(InstalledApps.split(",")));
			List<String> cInstalledApps = InstalledApps;
		List<String> dbUnInstalledApps = dbPersister.dbUnInstallAppEdr(ettId);
		List<String> dbInstalledApps = dbPersister.dbInstallAppEdr(ettId);
		oLog.info("cInstalledApps|"+cInstalledApps);
		oLog.info("dbUnInstalledApps|"+dbUnInstalledApps);
		oLog.info("dbInstalledApps|"+dbInstalledApps);
		dbInstalledApps.removeAll(cInstalledApps);
		dbInstalledApps.removeAll(dbUnInstalledApps);
		oLog.info("dbInstalledApps final for inserting|"+dbInstalledApps);
		dbPersister.persistUnInstallEdr(ettId, dbInstalledApps);
		}catch(Exception ex){
			oLog.error("error in unInstalledAppHandle|"+ex+"|ettId|"+ettId+"|type|"+type+"|InstalledApps|"+InstalledApps);
			ex.printStackTrace();
		}
	}
	
	@Override
	public void lossInEarningCheck(long ettId, List<String> appKeys){
		if(!isLIECheckOn)
			return;
		
		appKeys.retainAll(configSheduler.getOfferList());
		if(appKeys.size() <= 0)
			return;
		
		boolean isEligibleForPush = true;
		List<UserPushInfo> pushList = dbPersister.getUserPushList(ettId);
		
		if(pushList.size() > 0){
			isEligibleForPush = pushDateValidation(pushList,noOfDays,noOfNotification);
		}
		if(!isEligibleForPush)
			return;
		
		OfferDetails offerDetails;
		UserPushInfo pushObj = new UserPushInfo();
		for(String cApp : appKeys){
			offerDetails = configSheduler.getOfferDetails(cApp);
			pushObj.setOfferId(offerDetails.getOfferId());
			if(pushList.contains(pushObj))
				continue;
			
			boolean edrCheck = dbPersister.checkInEdr(ettId,cApp);
			if(edrCheck)
				continue;
			
			DeviceToken dToken = dbPersister.getDeviceToken(ettId);
			if(dToken != null && !dToken.getDeviceToken().equals("")){
				String pushText = configFile.getProperty("INSTALL_LOSS_NOTIFICATION").replaceFirst("#APP_Name#", cApp);        	
	        	sendPush(pushText, dToken);
	        	oLog.info("lossInEarning offerInstall | ettId="+ettId+" | offerId="+offerDetails.getOfferId());
	        	dbPersister.addInUserPushInfo(ettId,offerDetails.getOfferId());
			}
			break;
		}
	}

	private void sendPush(final String pushText, final DeviceToken dToken) {
		try{
  			jmsTemplate.send("PUSH_NOTIFICATION",new MessageCreator() {
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
  			oLog.error("error in edr push "+e);
  			e.printStackTrace();
  		}
	}


	private boolean pushDateValidation(List<UserPushInfo> pushList, int noOfDays, int noOfNotification) {
		DateTime dateToCompare = new DateTime().plusSeconds(19800).toDateMidnight().toDateTime();
		UserPushInfo pushInfo = pushList.get(0);
		
		DateTime lastPushDate = new DateTime(pushInfo.getCreatedTime()).plusSeconds(19800);
		
		if(dateToCompare.isBefore(lastPushDate))
			return false;
		
		int pushCounter = 0;
		dateToCompare = dateToCompare.minusDays(noOfDays);
		for(UserPushInfo pushData : pushList){
			if(dateToCompare.isBefore(new DateTime(pushData.getCreatedTime()).plusSeconds(19800)))
				pushCounter++;
			
			if(pushCounter >= noOfNotification)
				return false;
		}
		return true;
	}


	@Override
	public void updatePokktInfo(String json) {
		// TODO Auto-generated method stub
		
	}
	
}