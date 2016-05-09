package com.rh.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PromotionStartup {
	
	/**
	 * All the implementation are in APIImpl.java class
	 * 
	 * @author Ankit Singh
	 */
	
	private static Log log = LogFactory.getLog(PromotionStartup.class);
	
	public static void main(String[] args){
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		APIImpl apiImpl=(APIImpl) context.getBean("restAPI");
		log.info("Promotion started..");
		
		apiImpl.targetUser();
	}
	
}
