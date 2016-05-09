package com.rh.push.notifications.server.pushmanager;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.JMSException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.rh.push.notifications.server.gcm.Message;
import com.rh.push.notifications.server.gcm.Result;
import com.rh.push.notifications.server.gcm.Sender;
import com.rh.push.notifications.server.spring.root.PushNotificationConfig;

/**
 * Created with IntelliJ IDEA.
 * User: Sanjay
 * Date: 5/9/12
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */

public class AndroidPushNotificationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AndroidPushNotificationManager.class);

    @Inject
    PushNotificationConfig pushNotificationConfig;

    @Inject
    JmsTemplate jmsTemplate;
    
    public void sendNotification(Long ettId,String registrationId,String message,String deviceType, int badgeCount,String SOUND,String URL,String isOpenApp,String offerId,String imageUrl,String type, String packageName) throws Exception {

        Sender sender = new Sender(pushNotificationConfig.getAndroidAPiKey());
        Message.Builder builder = new Message.Builder();

        Result result = sender.send(builder.collapseKey("1").addData("price", message).addData("deviceType", deviceType).addData("badgeCount", badgeCount+"").addData("SOUND", SOUND).addData("URL", URL).addData("isOpenApp", isOpenApp).addData("offerId", offerId).addData("imageUrl", imageUrl).addData("type", type).addData("packageName", packageName).build(), registrationId, 1);
        LOGGER.error("[ettId] ["+ettId+"][message]["+message+"][deviceType]["+deviceType+"][badgeCount]["+badgeCount+"][SOUND]["+SOUND+"][URL]["+URL+"][isOpenApp]["+isOpenApp+"][offerId]["+offerId+"][imageUrl]["+imageUrl+"][type]["+type+"][packageName]["+packageName+"][registrationId]["+registrationId+"] [RESULT][ErrorCodeName]["+result.getErrorCodeName()+"][MessageId]["+result.getMessageId()+"][CanonicalMessageId["+result.getCanonicalRegistrationId()+"]");
        if( pushNotificationConfig.isPushNotRegUpdateSend() && result.getErrorCodeName()!=null && (result.getErrorCodeName().equals("NotRegistered") ||result.getErrorCodeName().equals("MissingRegistration")||result.getErrorCodeName().equals("InvalidRegistration")||result.getErrorCodeName().equals("MismatchSenderId"))) {
        	
            LOGGER.error("ettId{} not registered pushing in queue",ettId+"|"+pushNotificationConfig.isPushNotRegUpdateSend()+"");
        	enqueue(ettId);
        }
        
    }
    
    private void enqueue(final Long id) {
		try{
  			jmsTemplate.send(pushNotificationConfig.getPushQueueName(),new MessageCreator() {
  	            @Override
  	            public javax.jms.Message createMessage(Session session) throws JMSException {
  	            	
  	                TextMessage textMessage=session.createTextMessage();
  	                textMessage.setText(id+"");  	               
  	                return textMessage;
  	            }
  	        });
  		}catch(Exception e){
  			LOGGER.error("error in redeem push "+e);
  			e.printStackTrace();
  		}
		
	}   
}

