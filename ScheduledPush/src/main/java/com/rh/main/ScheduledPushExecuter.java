package com.rh.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScheduledPushExecuter {

	/**
	 * @author ankit singh
	 */
	private static Log log = LogFactory.getLog(ScheduledPushExecuter.class);
	
	public static void main(String[] args){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		int sleepMillis=0;
		APIImpl apiImpl = (APIImpl) context.getBean("restAPI");
		sleepMillis = apiImpl.sleep;
		while(true){
			apiImpl.SendPush();
			
			//log.info("Schedule Push Started ...");
			
			try {
				Thread.sleep(sleepMillis);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error("Error in Processing SchedulePush : "+e);
				e.printStackTrace();
			}
		}
		
	}
}
