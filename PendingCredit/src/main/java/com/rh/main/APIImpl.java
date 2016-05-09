package com.rh.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.rh.persistence.domain.PendingCredits;
import com.rh.persistence.domain.PendingCreditsReport;
import com.rh.persistence.domain.UnInstalledApps;
import com.rh.persistence.domain.User;
import com.rh.persistence.domain.UserAccountSummary;
import com.rh.service.DateService;

public class APIImpl {

	/**
	 * @author Ankit Singh
	 */
	
	private static Log log = LogFactory.getLog(APIImpl.class);
	
	private DBPersister dbPersister;
	private JmsTemplate jmsTemplate;
	
	static Properties configFile = new Properties();
	
	private DateService dateService;
	
	List<PendingCredits> creditList=new ArrayList<PendingCredits>();
	List<PendingCredits> validList=new ArrayList<PendingCredits>();
	String pushTxt="";
	int limit = 0;
	private String startDate = "";
	private String endDate = "";
	
	public APIImpl(DBPersister dp, JmsTemplate jmsTemplate, DateService dateService) {
		this.dbPersister = dp;
		this.jmsTemplate = jmsTemplate;
		this.dateService = dateService;
		try {
			configFile.load(APIImpl.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		pushTxt=configFile.getProperty("SUCCESS_PUSH");
		log.info("pushTxt is :"+pushTxt);
		limit = Integer.valueOf(configFile.getProperty("UNINSTALL_CHECK_LIMIT"));
		
		startDate = this.dateService.getDBDate();
		endDate = this.dateService.getDateAfter24Hour(startDate);
		if(startDate.equals("") || endDate.equals("")){
			log.error("error while getting dates.");
			System.exit(-1);
		}
		log.info("startDate:"+startDate+" | endDate:"+endDate);
	}
	
	public void getTodayList() {
		creditList=dbPersister.getTodayList(creditList,startDate,endDate);
		log.info("Total size of list before unInstall check is : "+creditList.size());
	}

	public void unInstalledAppsCheck() {
		List<String> uninstallUpdateQuery=new ArrayList<String>();
		int count=0;
		String query="";
		for(PendingCredits pendingCredit : creditList){
			UnInstalledApps unInstalledApps=dbPersister.unInstallCheck(pendingCredit);
			if(unInstalledApps == null){
				validList.add(pendingCredit);
				log.info("ettId :"+pendingCredit.getEttId()+" amount :"+pendingCredit.getAmount()+" added for app "+pendingCredit.getAppKey());
			} else {
				//query="update PendingCredits set eligibleStatus=1,notEligibleDate=date(date_add(now(),INTERVAL 19800 SECOND)) where ettId="+pendingCredit.getEttId()+" and offerId="+pendingCredit.getOfferId()+" and eligibleStatus=0";
				query="update PendingCredits set eligibleStatus=1,notEligibleDate=now() where ettId="+pendingCredit.getEttId()+" and autoId="+pendingCredit.getAutoId();
				uninstallUpdateQuery.add(query);
				count++;
				log.info("ettId :"+pendingCredit.getEttId()+" has unInstalled the app :"+pendingCredit.getAppKey());
				if(count>=1000){
					dbPersister.batchInsert(uninstallUpdateQuery);
					log.info("tatal uninstall on counter is "+uninstallUpdateQuery.size());
					uninstallUpdateQuery.clear();
					count=0;
				}
			}
			dbPersister.updateStartedOffers(pendingCredit);
		}
		if(uninstallUpdateQuery.size()>0){
			dbPersister.batchInsert(uninstallUpdateQuery);
			log.info("tatal uninstall is "+uninstallUpdateQuery.size());
		}
		log.info("Total size of list after unInstall check is : "+validList.size());
		creditList.clear();
		log.info("main list cleared !!");
	}

	public void creditAccount() {
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
		for(PendingCredits penCredits : validList){
			/*User user=dbPersister.getUser(penCredits.getEttId());
			if(user==null){
				log.warn("No User details for ettId : "+penCredits.getEttId());
				continue;
			}*/
			if("223".equals(String.valueOf(penCredits.getOfferId()))){
				penCredits.setOfferId((long) 2223);
			}
			UserAccountSummary userAccountSummary = new UserAccountSummary();
			userAccountSummary.setEttId(penCredits.getEttId());
			userAccountSummary.setOfferId(penCredits.getOfferId());
			userAccountSummary.setOfferName(penCredits.getAppKey());
			userAccountSummary.setAmount(penCredits.getAmount());
			userAccountSummary.setRemarks("PENDING_CREDIT");
			userAccountSummary.setVendor(penCredits.getVendor());
			try{
				String msg=pushTxt.replaceFirst("#AMOUNT#", penCredits.getAmount()+"");
				msg=msg.replaceFirst("#APP#", penCredits.getAppKey()+"");
				String installedDate=sdf.format(penCredits.getCreatedTime());
				msg=msg.replaceFirst("#DATE#", installedDate+"");
				
				dbPersister.creditBalance(userAccountSummary,penCredits,msg);
				log.info("ettId :"+penCredits.getEttId()+" credited amount :"+penCredits.getAmount());
			}catch(Exception e){
				log.error("error while crediting amount to ettId="+penCredits.getEttId()+" "+e);
				e.printStackTrace();
			}
			
			/*DeviceToken deviceToken=dbPersister.getDeviceToken(user.getDeviceId());
			if(deviceToken != null && !deviceToken.getDeviceToken().equals("")){
				String txt=pushTxt.replaceFirst("#AMOUNT#", penCredits.getAmount()+"");
				txt=txt.replaceFirst("#APP#", penCredits.getAppKey()+"");
				String installedDate=sdf.format(penCredits.getCreatedTime());
				txt=txt.replaceFirst("#DATE#", installedDate+"");
				sendPush(txt,deviceToken);
			}*/
		}
		
	}

	private void sendPush(final String txt, final DeviceToken deviceToken) {
		try{
  			jmsTemplate.send(new MessageCreator() {
  	            @Override
  	            public Message createMessage(Session session) throws JMSException {
  	            	
  	                MapMessage mapMessage = session.createMapMessage();
  	                mapMessage.setString("ID", deviceToken.getDeviceToken());
  	                mapMessage.setString("DISPLAY_STRING", txt);
  	                mapMessage.setInt("BADGE_COUNT", 1);
  	                mapMessage.setString("DEVICE_TYPE", "ANDROID");
  	                log.info("Msg : "+txt+" sent to : "+deviceToken.getDeviceToken());
  	                return mapMessage;
  	            }
  	        });
  		}catch(Exception e){
  			log.error("error in redeem push "+e);
  			e.printStackTrace();
  		}
	}

	public void makeReport() {
		List<PendingCreditsReport> reportList=new ArrayList<>(5);
		dbPersister.getAppKey(reportList);
		log.info("No of appkey in PendingCredit for today is : "+reportList.size());
		for(PendingCreditsReport report : reportList){
			dbPersister.getReport(report);
		}
		for(PendingCreditsReport report : reportList){
			if(report.getTotalAmountCredited()==null){
				log.warn("null value of totalAmountCredited in UserAccountSummary for offerName : "+report.getAppKey());
				report.setTotalAmountCredited((double) 0);
			}
			dbPersister.insertReport(report);
			log.info("inserted the entry for "+report.getAppKey()+" app.");
		}
	}

	public void allUnInstallCheck() {
		List<PendingCredits> allCreditList = new ArrayList<PendingCredits>();
		List<String> unInstallUpdateQuery = new ArrayList<String>();
		int count=0;
		String query="";
		int offset = 0;
		boolean isNext = true;
		do{
			dbPersister.getAllCreditList(allCreditList,startDate,limit,offset);
			log.info("total size of AllCreditList before uninstallCheck is "+allCreditList.size());
			for(PendingCredits penCredits : allCreditList){
				UnInstalledApps unInstalledApps=dbPersister.unInstallCheck(penCredits);
				if(unInstalledApps==null){
					continue;
				}else{
					//query="update PendingCredits set eligibleStatus=1,notEligibleDate=date(date_add(now(),INTERVAL 19800 SECOND)) where ettId="+penCredits.getEttId()+" and offerId="+penCredits.getOfferId()+" and eligibleStatus=0";
					query="update PendingCredits set eligibleStatus=1,notEligibleDate=now() where ettId="+penCredits.getEttId()+" and autoId="+penCredits.getAutoId();
					unInstallUpdateQuery.add(query);
					count++;
					dbPersister.updateStartedOffers(penCredits);
					log.info("ettId :"+penCredits.getEttId()+" has unInstalled the app :"+penCredits.getAppKey());
					if(count>=1000){
						dbPersister.batchInsert(unInstallUpdateQuery);
						log.info("tatal uninstall on counter is "+unInstallUpdateQuery.size());
						unInstallUpdateQuery.clear();
						count=0;
					}
				}
			}
			if(unInstallUpdateQuery.size()>0){
				dbPersister.batchInsert(unInstallUpdateQuery);
				log.info("total uninstall of all list is "+unInstallUpdateQuery.size());
			}
			if(allCreditList.size() < limit){
				isNext = false;
				allCreditList.clear();
			} else{
				offset = offset + limit;
				allCreditList.clear();
			}
		} while (isNext);
		
		log.info("all Uninstall insert completed !");
	}

	public void handleStatus5Users() {
		List<PendingCredits> todaysStatus5Users = dbPersister.getTodaysStatus5Users(startDate,endDate);
		
		for(PendingCredits pendingCredit : todaysStatus5Users){
			UnInstalledApps unInstalledApps=dbPersister.unInstallCheck(pendingCredit);
			if(unInstalledApps == null){
				dbPersister.insertIntoPendingCreditsStatus5(pendingCredit);
				log.info("ettId="+pendingCredit.getEttId()+" offerId="+pendingCredit.getOfferId()+" | inserted into PendingCreditsStatus5User");
			}
			else{
				log.info("ettId="+pendingCredit.getEttId()+" offerId="+pendingCredit.getOfferId()+" | unInstalled the App");
			}
		}
	}
}
