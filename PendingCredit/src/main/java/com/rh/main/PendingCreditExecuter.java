package com.rh.main;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class PendingCreditExecuter {
	
	/**
	 * @author Ankit Singh
	 */
	private static Log log = LogFactory.getLog(PendingCreditExecuter.class);
	
	public static void main(String[] args){
		try{
			ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
			APIImpl apiImpl=(APIImpl) context.getBean("restAPI");
			log.info("Process started..");
			
			apiImpl.getTodayList();
			log.info("Get today's total users.");
			
			apiImpl.unInstalledAppsCheck();
			log.info("UnInstalledApps check for today has completed.");
			
			apiImpl.creditAccount();
			log.info("creditAccount done !");
			
//			apiImpl.makeReport();
//			log.info("PendingCreditsReport done !!");
			
//			apiImpl.handleStatus5Users();
//			log.info("all status 5 Users Credited.....");
			
			apiImpl.allUnInstallCheck();
			log.info("Process done for today !!!");
		}catch(Exception e){
			log.error("Error in main : "+ e);
		}
		
			
	}
	
}
