package com.rh.push.notifications.server.spring.root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.sun.org.apache.commons.logging.Log;



/**
 * @author ankit
 */
@Configuration("listenerAppConfig")
//@ImportResource({"file:D:\\RH_Project\\Ett Project\\ett-push-server\\application-context.xml"})
//@PropertySource({"classpath:com/rh/push/notification/server/spring/root/push-notifications.properties","file:D:\\RH_Project\\Ett Project\\ett-push-server\\config.properties"})
//@PropertySource({"classpath:../config.properties"})

@ImportResource({"file:/home/RationalHeads/ett-push-server/application-context.xml"})
@PropertySource({"classpath:com/rh/push/notification/server/spring/root/push-notifications.properties","file:/home/RationalHeads/ett-push-server/config.properties"})
public class ListenerApplicationConfig {

    @Autowired
    private Environment environment;

    @Bean
    public PushNotificationConfig getPushConfig(){
        PushNotificationConfig pushNotificationConfig = new PushNotificationConfig();
        pushNotificationConfig.setAndroidAPiKey(environment.getProperty("android-api-key"));
        String pushNotRegUpdateSend = environment.getProperty("IsPushNotRegUpdateSend");
        System.out.println("pushNotRegUpdateSend="+pushNotRegUpdateSend);
        if(pushNotRegUpdateSend.equals("true")) {
        	
        	pushNotificationConfig.setPushNotRegUpdateSend(true);
        }
        else {
        	
        	pushNotificationConfig.setPushNotRegUpdateSend(false);
        }
        String pushQueueName = environment.getProperty("pushQueueName");
        pushNotificationConfig.setPushQueueName(pushQueueName);
     /*   pushNotificationConfig.setAppleCertificate(environment.getProperty("apple-client-certificate"));
        pushNotificationConfig.setAppleCertificatePassword(environment.getProperty("apple-client-certificate-password"));*/
        pushNotificationConfig.setDev(Boolean.parseBoolean(environment.getProperty("certificate-type-dev")));
        return pushNotificationConfig;
    }
}