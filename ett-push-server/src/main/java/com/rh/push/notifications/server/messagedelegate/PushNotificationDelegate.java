package com.rh.push.notifications.server.messagedelegate;


import javax.inject.Inject;
import javax.jms.MapMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rh.push.notifications.server.pushmanager.AndroidPushNotificationManager;

/**
 * Created with IntelliJ IDEA.
 * User: Sanjay
 * Date: 5/9/12
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */

public class PushNotificationDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushNotificationDelegate.class);

   
    @Inject
    private AndroidPushNotificationManager androidPushNotificationManager;

    public void processMessage(MapMessage mapMessage) throws Exception {
        LOGGER.info("Got new message for push");
        String token = mapMessage.getString("ID");
        String displayData = mapMessage.getString("DISPLAY_STRING");
        String deviceType=mapMessage.getString("DEVICE_TYPE");
        int badgeCount = mapMessage.getInt("BADGE_COUNT");
       String SOUND = mapMessage.getString("SOUND");
        String URL = mapMessage.getString("URL");
        String isOpenApp = mapMessage.getString("isOpenApp");
        Long ettId = mapMessage.getLong("ettId");
        String offerId = mapMessage.getString("offerId");
        String imageUrl = mapMessage.getString("imageUrl");
        String packageName = mapMessage.getString("packageName");
        String type = mapMessage.getString("type");
       // LOGGER.info("Message data: token:"+ token + "displayData:"+displayData+"|Device type:"+deviceType+"|isOpenApp:"+isOpenApp);

        if(deviceType.equals("ANDROID")){
            androidPushNotificationManager.sendNotification(ettId,token,displayData,deviceType,badgeCount,SOUND,URL,isOpenApp,offerId,imageUrl,type,packageName);
        }/*else{
            int retryCount = 0;
            while(retryCount<2){
                try{
                    applePushNotificationManager.sendNotification(token,displayData,badgeCount);
                    break;
                }catch (Exception e){
                    retryCount++;
                    LOGGER.error("Error in sending push retrying:"+ e);
                }
            }
        }*/
       // LOGGER.info("Push Sent");
    }
}

