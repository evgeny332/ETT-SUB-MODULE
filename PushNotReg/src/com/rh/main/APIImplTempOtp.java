package com.rh.main;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import com.rh.persistence.domain.UserAccountSummary;
import com.rh.persistence.domain.UserRedeem;
import com.rh.persistence.domain.UserSource;
import com.rh.remote.SendGetRequest;

/*
 * Implementation class of APIInterface and SessionHolder interfaces.
 * Initialize various managers related to various services that simulator provides.
 * Implementation of session handlers.
 */
public class APIImplTempOtp implements APIInterface {
	private DBPersister dbPersister;
	private ConfigHolder configHolder;
	private JmsTemplate jmsTemplate;
	private static Log log = LogFactory.getLog(APIInterface.class);
	private static Log oLog = LogFactory.getLog(APIImplTempOtp.class);
	int dayLimit ;
	/*
	 * Constructor use to initialize various managers and DBPersister variables.
	 */
	public APIImplTempOtp(DBPersister dp, JmsTemplate jmsTemplate) throws IOException {
		this.dbPersister = dp;
		this.configHolder = new ConfigHolder();
		this.jmsTemplate = jmsTemplate;
		dayLimit = Integer.parseInt(configHolder.getProps().getProperty("DayLimit"));
		log.info("dayLimit="+dayLimit);
	}

	@Override
	public void updateUnReg(String id) {
		try {
			Long ettId = Long.parseLong(id);
			//User user = dbPersister.getUser(ettId);
			//boolean checkDate  = checkUpdateTimeUser(user.getUpdatedTime());
			if(ettId<16492620){
				
			}
			else {
				log.error("Rec. ettId:"+id);
				dbPersister.getTempOtpRegId(ettId);
			}
		}
		catch(Exception ex) {
			log.error("error ettId:"+id);
			ex.printStackTrace();
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

	@Override
	public void updatePokktInfo(String id) {
		// TODO Auto-generated method stub
		
	}

	
}