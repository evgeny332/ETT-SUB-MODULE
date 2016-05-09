package com.rh.edrconsumer.main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.rh.edrconsumer.interfaces.APIInterface;
import com.rh.edrconsumer.persistence.DBPersister;
import com.rh.persistence.edrconsumer.domain.DeviceToken;
import com.rh.persistence.edrconsumer.domain.User;
//import com.domain.entity.InstalledApps;
//import com.rh.interfaces.APIInterface;

public class Receiver implements Runnable{
	/**
	 * @param args
	 */
	private static Log log = LogFactory.getLog(Receiver.class);
	private String queueName;
	private String url;
	APIInterface api;
	List <Long>clickOfferId = new ArrayList<Long>();
	HashMap<String, Long> competitorApps = new HashMap<>();
	List<Long> competitorAppPointList = new ArrayList<>(20);
	List<String> competitorAppList = new ArrayList<String>(20);
	private DBPersister dbPersister;
	boolean isClickEnable;
	private String clickNotification;
	private JmsTemplate jmsTemplate;
	public Receiver(String url, String queueName, APIInterface api,DBPersister dbPersister,String clickOfferId,String isClickEnable,String clickNotification, JmsTemplate jmsTemplate) {
		this.url = url;
		this.queueName = queueName;
		this.api = api;
		this.dbPersister = dbPersister;
		this.clickNotification=clickNotification;
		this.jmsTemplate = jmsTemplate;
		String clickOfferId1[]=clickOfferId.split(",");
		for(String a:clickOfferId1){
			
			this.clickOfferId.add(Long.parseLong(a));
		}
		if(isClickEnable.equals("true")){
			this.isClickEnable=true;
		}
		System.out.println("url|"+url+"|queuename|"+queueName+"|api|"+api+"|clickOfferList|"+clickOfferId);
		new Thread(this).start();
	}

	public void run() {
		log.info("In receiver.java: starting app");
		System.out.println("In receiver.java: starting app");
		
		//Get the competitor apps from the DB.
		getCompetitorApps(competitorApps,competitorAppPointList,competitorAppList);
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		
		Connection connection = null;
		try {
			connection = connectionFactory.createConnection();
		
			connection.start();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
		
			Destination destination = session.createQueue(queueName);
			MessageConsumer consumer = session.createConsumer(destination);
		
			while (true) {
				Message message = consumer.receive();
				//log.info("message rec|"+message);
				if (message instanceof javax.jms.MapMessage) {
					
					try {					
						//log.info("redeem_id=" + textMessage.getText());
						MapMessage mapMessage = (MapMessage)message;
						String type=mapMessage.getString("type");
						if(type.equals("UNINSTALLAPPS"))
						{
							long ettId=mapMessage.getLong("ettId");
							List cInstalledApps = (List) mapMessage.getObject("installedApps");
							log.info("EDR/UNINSTALLAPPS ettId="+ettId);
							unInstalledAppHandle(ettId,type,cInstalledApps);
						}
						else if(type.equals("INSTALLAPPS")){
							long ettId = mapMessage.getLong("ettId");
							List appKey = (List) mapMessage.getObject("appKey");
							//log.info("EDR/INSTALLAPPS ettId="+ettId+" appKey="+appKey);
							List<String> tempAppKey = new ArrayList<>(appKey);
							tempAppKey.retainAll(competitorAppList);
							if(tempAppKey.size()>0){
								installCompAppHandle(ettId,tempAppKey);
							}
							installHandle(ettId,appKey);
							api.lossInEarningCheck(ettId, appKey);
						}
						else if(type.equals("UNAUTHORIZED")){
							long ettId = mapMessage.getLong("ettId");
							int blackListType = mapMessage.getInt("blackListType");
							dbPersister.blackListHandle(ettId,blackListType);
						}
						else if(type.equals("REGISTERAPPS")){
							long ettId = mapMessage.getLong("ettId");
							List cInstalledApps = (List) mapMessage.getObject("installedApps");
							log.info("EDR/REGISTERAPPS ettId="+ettId);
							if(cInstalledApps.size()>0){
								updateUserServiceClass(ettId, cInstalledApps);
							}
						}
						else if(type.equals("UNINSTALL_APP_MOVE")) {
							long ettId =  mapMessage.getLong("ettId");
							List<String> appKey = (List) mapMessage.getObject("appKey");
							log.info("UNINSTALL_APP_MOVE ettId="+ettId);
							dbPersister.moveUnInstalledApp(ettId,(String)(appKey.get(0)));
						}
						else if(type.equals("CLICK") && isClickEnable==true) 
						{
							long ettId=mapMessage.getLong("ettId");
							long offerId = mapMessage.getLong("offerId");
							String appKey = mapMessage.getString("appKey");
							log.info("EDR/CLICK ettId="+ettId+" offerId="+offerId+" appKey="+appKey);
							//unInstalledAppHandle(ettId,type,cInstalledApps);
							if(clickOfferId.contains(offerId)){
								dbPersister.insertClickCDR(ettId,offerId,appKey);
								int countDay=dbPersister.getDayCount(ettId);
								if(countDay>=10){
									//give amount
									dbPersister.deleteClickDayRecord(ettId);
									boolean par = dbPersister.updateClickBalance(ettId,offerId,true);
									if(par) {
									//send notification
										User user = dbPersister.getUser(ettId);
										DeviceToken dToken = dbPersister.getDeviceToken(user.getEttId());
										if(dToken != null && !dToken.getDeviceToken().equals("")){
											//String pushText = configHolder.getProperties().getProperty("DAY_LIMIT_CROSS_NOTF");
											String pushText = clickNotification;
								        	if(!pushText.equals(""))
											//pushText = pushText.replaceFirst("#MOBILE_NUMBER#", userRedeem.getMsisdn()+"");        	
								        	sendPush(pushText, dToken);
										}
									}
									
								}
								else {
									dbPersister.updateClickBalance(ettId,offerId,false);
								}
							}
						}
						else{
							log.info("wrong type|"+type+"|msg|"+mapMessage);
						}
						
					} catch (Exception e) {
						log.error(message+"|error in proceesing  "+e);
						e.printStackTrace();
					}
				}
			}
		}catch (Exception e) {
			log.error("########"+e);
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void installHandle(long ettId, List<String> appKey) {
		
		dbPersister.insertInstalledApps(ettId,appKey);
		log.info("ettId="+ettId+" | appKey="+appKey+" | inserted into installedApps");
	}

	private void installCompAppHandle(long ettId, List<String> appKey) {
		
		dbPersister.insertCompInstall(ettId,appKey);
		log.info("inserted CompApps for ettId="+ettId+" appKey="+appKey);
	}

	private void getCompetitorApps(HashMap<String, Long> competitorApps2, List<Long> competitorAppPointList2, List<String> competitorAppList2) {
		dbPersister.getCompetitorApps(competitorApps2, competitorAppPointList2, competitorAppList2);
		System.out.println(competitorApps);
		System.out.println(competitorAppPointList);
	}

	public void unInstalledAppHandle(long ettId,String type,List InstalledApps) {
		try {
			List<String> cInstalledApps = InstalledApps;
			List<String> dbUnInstalledApps = dbPersister.dbUnInstallAppEdr(ettId);
			List<String> dbInstalledApps = dbPersister.dbInstallAppEdr(ettId);

			dbInstalledApps.removeAll(cInstalledApps);
			dbInstalledApps.removeAll(dbUnInstalledApps);
			dbPersister.persistUnInstallEdr(ettId, dbInstalledApps);
			log.info("UnInstalledApps final inserted|" + dbInstalledApps+ " for ettId=" + ettId);
		} catch (Exception ex) {
			log.error("error in unInstalledAppHandle|" + ex + "|ettId|" + ettId+ "|type|" + type + "|InstalledApps|" + InstalledApps);
			ex.printStackTrace();
		}
		// Competitor apps recalculation
		updateUserServiceClass(ettId, InstalledApps);
	}

	private void updateUserServiceClass(long ettId, List installedApps) {
		List<String> cInstalledApps = new ArrayList<>(installedApps);
		List<String> cInstalledApps2 = new ArrayList<>(installedApps);
		cInstalledApps.retainAll(competitorAppList);
		if (cInstalledApps.size() <= 0)
			return;
			
		List<String> cUserCompApps = dbPersister.getcCompetitorApps(ettId);
		if (cUserCompApps.size() > 0) {
			cInstalledApps.removeAll(cUserCompApps);
			if (cInstalledApps.size() > 0) {
				dbPersister.insertUserCompApps(ettId, cInstalledApps);
				log.info("inserted into UserCompetitorApss ettId=" + ettId+ " | CompApps :" + cInstalledApps);
			}
			cUserCompApps.removeAll(cInstalledApps2);
			if (cUserCompApps.size() > 0) {
				dbPersister.updateUserCompApps(ettId, cUserCompApps);
				log.info("UserCompApss ettId" + ettId + " deleted appKey :"+ cUserCompApps);
			}
		} else {
			dbPersister.insertUserCompApps(ettId, cInstalledApps);
			log.info("New entry for ettId=" + ettId + " | compApps : "+ cInstalledApps);
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
	  			log.error("error in edr push "+e);
	  			e.printStackTrace();
	  		}
	  		
	  	}
}