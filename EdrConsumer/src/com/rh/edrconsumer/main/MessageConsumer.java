package com.rh.edrconsumer.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageConsumer implements MessageListener {
	private static Log log = LogFactory.getLog(MessageConsumer.class);
	
	private APIImpl apiImpl;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onMessage(Message message) {
		int result = 1;
		if (message instanceof MapMessage) {
			try {
		         MapMessage mapMessage = (MapMessage) message;
		         //log.info("hi|"+mapMessage);
		         System.out.println("inSide MessageConsumer");
		         String type=mapMessage.getString("type");
					if(type.equals("UNINSTALLAPPS"))
					{
						long ettId=mapMessage.getLong("ettId");
						List cInstalledApps = (List)mapMessage.getObject("installedApps");
						//apiImpl.unInstalledAppHandle(ettId,type,cInstalledApps);
					}
					else{
						log.info("wrong type|"+type+"|msg|"+mapMessage);
					}
		        
					
				/*if(mapMessage.getString("type").equals("INSTALL_EDR")){
		        	 apiImpl.installAppEdr(mapMessage.getLong("ettId"), mapMessage.getString("appKey"));
		         }else if(mapMessage.getString("type").equals("UNINSTALL_EDR")){
		        	 apiImpl.unInstallAppEdr(mapMessage.getLong("ettId"), mapMessage.getString("appKeys"));
		         }*/
               
			} catch (Exception e) {
				log.error("error ocurred in receiving msg from mq " + e + " for message "+message);
				e.printStackTrace();
			}
		} else {
			log.error("msg in mq must be map type");
		}
	}


	public APIImpl getApiImpl() {
		return apiImpl;
	}

	public void setApiImpl(APIImpl apiImpl) {
		this.apiImpl = apiImpl;
	}	

}
