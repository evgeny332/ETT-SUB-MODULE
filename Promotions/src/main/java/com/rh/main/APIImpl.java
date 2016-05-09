package com.rh.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.filters.TokenFilter.IgnoreBlank;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.rh.persistence.DBPersister;
import com.rh.persistence.domain.DeviceToken;
import com.rh.persistence.domain.Promotions;
import com.rh.persistence.domain.PromotionsData;

public class APIImpl {
	
	/**
	 * @author Ankit Singh
	 */
	
	private DBPersister dbPersister;
	private JmsTemplate jmsTemplate;
	private static Log log = LogFactory.getLog(APIImpl.class);
	static Properties configFile = new Properties();
	static int maxThread = 0;
	private boolean defaultMessageCheck = false;
	static String defaultMessage = "";
	static int promotionsSize = 0;
	static int offerLimit = 0;
	static int addAmount = 0;
	private String pushHeader = "";
	private String pushFooter = "";
	private String currentDate = "";
	boolean isTesting = false;
	private String query = "";
	ExecutorService executor = Executors.newFixedThreadPool(10);
	
	List<Promotions> promoList = new ArrayList<>();
	List<String> appKeyList = new ArrayList<>();
	private String type ="";
	private String rateurl="";
	private String offerId="";
	private String imageUrl = "";
	private String packageName = "";
	private int msgSleepLimit;
	private long sleepTime;
	
	
	
	public APIImpl(DBPersister dp, JmsTemplate jmsTemplate) {
		this.dbPersister = dp;
		this.jmsTemplate = jmsTemplate;
		try {
			configFile.load(APIImpl.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			log.error("Error in getting config file : "+e);
			System.exit(0);
		}
		maxThread = Integer.valueOf(configFile.getProperty("POOL_SIZE"));
		defaultMessageCheck = Boolean.parseBoolean(configFile.getProperty("USE_DEFAULT"));
		type = configFile.getProperty("type");
		rateurl = configFile.getProperty("rateurl");
		offerId = configFile.getProperty("offerId");
		imageUrl = configFile.getProperty("imageUrl");
		packageName = configFile.getProperty("packageName");
		msgSleepLimit = Integer.parseInt(configFile.getProperty("msgSleepLimit"));
		sleepTime = Long.parseLong(configFile.getProperty("sleepTime"));
		this.jmsTemplate.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		if(configFile.getProperty("isTesting").equals("true")) {
			isTesting=true;
		}
		query = configFile.getProperty("query");
	}

	public void targetUser() {
		List<Long> ids = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, 19800);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		currentDate = formatter.format(calendar.getTime());
		if(defaultMessageCheck){
			defaultMessage = configFile.getProperty("DEFAULT_MESSAGE");
			log.info("Default message for all Users is : "+defaultMessage);
		}
		else {
			offerLimit = Integer.valueOf(configFile.getProperty("OFFER_LIMIT"));
			pushHeader = configFile.getProperty("PUSH_HEADER");
			pushFooter = configFile.getProperty("PUSH_FOOTER");
			dbPersister.getMessage(promoList,appKeyList);
			promotionsSize = promoList.size();
			if(promotionsSize == 0){
				log.error("ERROR!! No Entry in Promotions table");
				System.exit(-1);
			}
			log.info("Promotion List Size is : "+promoList.size());
			String extraAmount = configFile.getProperty("EXTRA_ADD_IN_AMOUNT");
			if(extraAmount != null && !extraAmount.equals("")){
				addAmount = Integer.parseInt(extraAmount);
				log.info("Extra amount : "+addAmount);
			}
		}
		dbPersister.getUser(ids,query);
		log.info("Total targetd Users are : "+ids.size());
		if(ids.size()>0){
			startPromotion(ids);
			//dbPersister.dropTempTable();
		}
	}

	private void startPromotion(List<Long> ids) {
		int msgSleepLimit1=msgSleepLimit;
		long sleepTime1=sleepTime;
		long msgSleepCounter=0l;
		for(final long id : ids){
			
			Runnable work = new Runnable() {
				
				
				@Override
				public void run() {
					boolean status = false;
					int ttCount = 0;
					
					if(defaultMessageCheck){
						List<String> ingoreList = new ArrayList<>(1);
						sendPush(id, defaultMessage, ingoreList,"",offerId);
					}
					else {
						List<String> promotionsAppKey = new ArrayList<>(appKeyList);
						//List<String> promotedAppKey = dbPersister.getPromotedAppKey(id);
						List<String> promotionsData = new ArrayList<>(offerLimit);
						StringBuilder offerMessage = new StringBuilder();
						String defaultOpenOfferId="";
						int offerAmount = 0;
						int counter = 0;
						
						/*if(promotedAppKey.size()>0){
							promotionsAppKey.removeAll(promotedAppKey);
						}*/
						List<String> cAppKey = new ArrayList<>();
						dbPersister.getInstalledApps(cAppKey,id);
						for(int i=0; i<promotionsAppKey.size(); i++){
							status = cAppKey.contains(promotionsAppKey.get(i));
							if(!status){
								int targetIndex = appKeyList.indexOf(promotionsAppKey.get(i));
								ttCount = dbPersister.transactionTrackerCheck(id+"_"+promoList.get(targetIndex).getOfferId());
								if (ttCount > 0) continue;
								offerMessage.append(promoList.get(targetIndex).getMessage()+"\n");
								if(defaultOpenOfferId.length()<=0) {
									defaultOpenOfferId=promoList.get(targetIndex).getOfferId()+"";
								}
								
								offerAmount = offerAmount+promoList.get(targetIndex).getAmount();
								promotionsData.add(promoList.get(targetIndex).getAppKey());
								counter++;
								log.info(","+id+"#"+promoList.get(targetIndex).getOfferId()+"#"+promoList.get(targetIndex).getAppKey()+"#"+promoList.get(targetIndex).getMessage());
							}
							if(counter>=offerLimit) break;
						}
						if(counter==0) {
							log.info("no offer for id="+id);
							return;
						}
						String header = pushHeader.replaceFirst("#AMOUNT#", String.valueOf(offerAmount + addAmount));
						String msg = currentDate+"\n"+header+"\n"+offerMessage+"\n"+pushFooter;
						sendPush(id, msg, promotionsData,currentDate+"\n"+header+"\n"+"\n",defaultOpenOfferId);
					}
				}
			}; 
			
			executor.execute(work);
			msgSleepCounter++;
			if(msgSleepCounter%msgSleepLimit1==0) {
				try {
				Thread.sleep(sleepTime1);
				log.info("Going to sleep[time]["+sleepTime1+"]");
				}catch(Exception ex1) {}
				
			}
		}
		log.info("All Ids Entered in Pool.");
		executor.shutdown();
		try {
			while (!executor.awaitTermination(10, TimeUnit.MINUTES)) {
				log.info("Inside awaitTermination");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("Executer interrupted.");
		} 
		System.exit(-1);
	}
	
	private void sendPush(long id, String msg, List<String> promotionsData,String tempText,String defaultOpenOfferId) {
		if(msg==null || msg.equals("") || msg.equals(tempText))
		{
			log.info("msg blank for ettId : "+id);
			return ;
		}
		DeviceToken deviceToken = dbPersister.getDeviceToken(id);
		if(deviceToken!=null && !"".equals(deviceToken.getDeviceToken())){
			if(isTesting){
			}else {
			enqueuePush(msg, deviceToken,id,defaultOpenOfferId);
			}
			/*if(!defaultMessageCheck){
				dbPersister.insertInPromotionsData(id,promotionsData);
			}*/
			log.info("ettId : "+id+" | Msg : "+msg);
		}
		
	}

	private void enqueuePush(final String txt, final DeviceToken deviceToken, final long id,final String defaultOpenOfferId) {
		try{
  			jmsTemplate.send(new MessageCreator() {
  	            @Override
  	            public Message createMessage(Session session) throws JMSException {
  	            	
  	                MapMessage mapMessage = session.createMapMessage();
  	                mapMessage.setString("ID", deviceToken.getDeviceToken());
  	                mapMessage.setString("DISPLAY_STRING", txt);
  	                mapMessage.setInt("BADGE_COUNT", 1);
  	                mapMessage.setString("DEVICE_TYPE", "ANDROID");
  	                mapMessage.setLong("ettId", id);
  	                mapMessage.setString("URL", rateurl);
  	                mapMessage.setString("imageUrl",imageUrl);
  	                mapMessage.setString("packageName",packageName);
  	              mapMessage.setString("type", type);
  	                if(defaultOpenOfferId.length()>0) {
  	                	mapMessage.setLong("offerId", Long.parseLong(defaultOpenOfferId));
  	                	
  	                }
  	                else {
  	                	
  	                }
  	                return mapMessage;
  	            }
  	        });
  		}catch(Exception e){
  			log.error("error in redeem push "+e);
  			e.printStackTrace();
  		}
	}
	

}
