package com.rh.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.rh.persistence.DBPersister;
import com.rh.persistence.domain.DeviceToken;
import com.rh.persistence.domain.SchedulePush;

public class APIImpl {

	/**
	 * @author Ankit Singh
	 */
	private DBPersister dbPersister;
	private JmsTemplate jmsTemplate;
	static Properties configFile = new Properties();
	public int sleep=0;
	private static Log log = LogFactory.getLog(APIImpl.class);
	
	public APIImpl(DBPersister dp, JmsTemplate jmsTemplate) {
		this.dbPersister = dp;
		this.jmsTemplate = jmsTemplate;	
		try {
			configFile.load(APIImpl.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sleep=Integer.parseInt(configFile.getProperty("SLEEP_MILLIS"));
		log.info("sleep millis is: "+sleep);
	}


	public void SendPush() {
		Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	    String dateNow = ft.format(dNow);
	    
	    List<SchedulePush> pushList = new ArrayList<>();
	    
	    dbPersister.getList(pushList,dateNow);
	    
	    log.info("Date Time "+dateNow+" pushLis size : "+pushList.size());
	    
	    if(pushList.size()>0){
	    	List<Long> id = new ArrayList<>(pushList.size());
	    	int count = 0;
	    	for(SchedulePush schPush : pushList){
	    		log.info("Processing on ettId="+schPush.getEttId());
	    		DeviceToken deviceToken = dbPersister.getDeviceToken(schPush.getEttId());
	    		if(deviceToken!=null && !"".equals(deviceToken.getDeviceToken())){
	    			String msg = schPush.getMessage();
	    			log.info(msg+" | deviceToken : "+deviceToken.getDeviceToken());
	    			enqueuePush(msg, deviceToken, schPush.getEttId(), schPush.getType());
	    		}
	    		id.add(schPush.getId());
	    		count++;
	    		if(count>=1000){
	    			dbPersister.deletePush(id);
	    			id.clear();
	    			count = 0;
	    		}
	    	}
	    	if(count>0){
	    		dbPersister.deletePush(id);
	    		id.clear();
	    		count = 0;
	    	}
	    	pushList.clear();
	    }
	    
	    
	}
	
	private void enqueuePush(final String txt, final DeviceToken deviceToken, final Long ettId, final String type) {
		try{
  			jmsTemplate.send(new MessageCreator() {
  	            @Override
  	            public Message createMessage(Session session) throws JMSException {
  	            	
  	                MapMessage mapMessage = session.createMapMessage();
  	                mapMessage.setString("ID", deviceToken.getDeviceToken());
  	                mapMessage.setString("DISPLAY_STRING", txt);
  	                mapMessage.setInt("BADGE_COUNT", 1);
  	                mapMessage.setString("DEVICE_TYPE", "ANDROID");
  	                mapMessage.setLong("ettId", ettId);
  	                if(type!=null && type.equalsIgnoreCase("Silent")){
  	                	mapMessage.setString("type", "Silent");
  	                }
  	                //log.info("Msg : "+txt+" sent to : "+deviceToken.getDeviceToken());
  	                return mapMessage;
  	            }
  	        });
  		}catch(Exception e){
  			log.error("error in redeem push "+e);
  			e.printStackTrace();
  		}
	}
}
