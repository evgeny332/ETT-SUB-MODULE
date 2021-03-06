package com.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.domain.entity.DataUsagePendingCredits;
import com.domain.entity.DeviceToken;
import com.domain.entity.Edr;
import com.domain.entity.InstallEdrOnHold;
import com.domain.entity.OfferDetails;
import com.domain.entity.OfferDetails.PayoutType;
import com.domain.entity.OffersStarted;
import com.domain.entity.User;
import com.google.gson.Gson;
import com.repository.jpa.DataUsagePendingCreditsRepository;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.InstallEdrOnHoldRepository;
import com.repository.jpa.InstalledAppsRepository;
import com.repository.jpa.OffersStartedRepository;
import com.service.EDRService;
import com.service.RechargeService;
import com.web.rest.dto.DataUsagePushDto;
import com.web.rest.dto.OfferInstructionDto;

@Service
public class EDRServiceImpl implements EDRService {

	private static Logger LOGGER = LoggerFactory.getLogger(EDRServiceImpl.class);

	@Resource
	private RechargeService rechargeService;
	
	@Autowired 
    InstalledAppsRepository installedAppsRepository;
	
	@Autowired
    private DeviceTokenRepository deviceTokenRepository;
    
	@Resource
	private OffersStartedRepository offersStartedRepository;
	
	@Resource
	private InstallEdrOnHoldRepository installEdrOnHoldRepository;
	
	@Resource
	private JmsTemplate jmsTemplate;
	 
	@Autowired
	private DataUsagePendingCreditsRepository dataUsagePendingCreditsRepository;
	 
	@Override
	public boolean checkAmazonInstallStaus(Edr edr, User user,long offerId) {
		//Time check
		if((edr.getInstalledTime().getTime()-edr.getClickedTime().getTime())/(1000*60) > Integer.parseInt(rechargeService.getAppConfig().get("AMAZON_TIME_LIMIT_"+user.getLocale()))) {
			LOGGER.info("[Amount not credit as the time diff high of limit ettId={},offerId={} ",user.getEttId(),offerId+"]");
			//DeviceToken deviceToken = deviceTokenRepository.findByDeviceId(user.getDeviceId());
			DeviceToken deviceToken = deviceTokenRepository.findByEttId(user.getEttId());
			if(deviceToken!=null && !deviceToken.getDeviceToken().equals("") && !rechargeService.getAppConfig().get("AMAZON_TIME_LIMT_FAIL_PUSH_FLAG_"+user.getLocale()).equals("false")){
 			   sendPush(rechargeService.getLocaleTextTemplate().get("AMAZON_TIME_LIMT_FAIL_PUSH_"+user.getLocale()), deviceToken,user.getEttId());
 		   }
			return true;
		}
		/*List<InstalledApps> oldInstalledApps = installedAppsRepository.findByEttId(user.getEttId());
		
		//LOGGER.info("[Installed app list]["+oldInstalledApps+"]");
		InstalledApps checkInApp = new InstalledApps();
		checkInApp.setId(user.getEttId()+"_Amazon");
		if(oldInstalledApps.contains(checkInApp)) {
			LOGGER.info("[Amazon Shoping not credited as allready downloaded Amazon ettId{} "+user.getEttId()+"]");
			DeviceToken deviceToken = deviceTokenRepository.findByDeviceId(user.getDeviceId());
			if(deviceToken!=null && !deviceToken.getDeviceToken().equals("") && !rechargeService.getProperties().getProperty("AMAZON_APP_INSTALL_FAIL_PUSH_FLAG").equals("false")){
 			   sendPush(rechargeService.getProperties().getProperty("AMAZON_APP_INSTALL_FAIL_PUSH"), deviceToken);
 		   }
			return true;
		}*/
		return false;
	}

	
	
	private void sendPush(final String pushText, final DeviceToken dToken,final Long ettId) {
    	if(dToken!=null && !"".equals(dToken.getDeviceToken())){
	    	try{
	 			jmsTemplate.send(new MessageCreator() {
	 	            @Override
	 	            public Message createMessage(Session session) throws JMSException {
	 	            	
	 	                MapMessage mapMessage = session.createMapMessage();
	 	                mapMessage.setString("ID", dToken.getDeviceToken());
	 	                mapMessage.setString("DISPLAY_STRING", pushText);
	 	                mapMessage.setInt("BADGE_COUNT", 1);
	 	               mapMessage.setLong("ettId", ettId);
	 	                mapMessage.setString("DEVICE_TYPE", "ANDROID");
	 	                return mapMessage;
	 	            }
	 	        });
	 		}catch(Exception e){
	 			LOGGER.error("error in edr push "+e);
	 			e.printStackTrace();
	 		}
    	}
 	}

	private void sendDataUsagePush(final String pushText, final DeviceToken dToken,final Long ettId) {
    	if(dToken!=null && !"".equals(dToken.getDeviceToken())){
	    	try{
	 			jmsTemplate.send(new MessageCreator() {
	 	            @Override
	 	            public Message createMessage(Session session) throws JMSException {
	 	            	
	 	                MapMessage mapMessage = session.createMapMessage();
	 	                mapMessage.setString("ID", dToken.getDeviceToken());
	 	                mapMessage.setString("DISPLAY_STRING", pushText);
	 	                mapMessage.setInt("BADGE_COUNT", 1);
	 	                mapMessage.setLong("ettId", ettId);
	 	                mapMessage.setString("type","silent");
	 	                mapMessage.setString("DEVICE_TYPE", "ANDROID");
	 	                return mapMessage;
	 	            }
	 	        });
	 		}catch(Exception e){
	 			LOGGER.error("error in edr push "+e);
	 			e.printStackTrace();
	 		}
    	}
 	}
	
	@Override
	public void handleInstallEdrOnHold(User user, OfferDetails offerDetails) {
		InstallEdrOnHold installEdrOnHold = new InstallEdrOnHold();
		installEdrOnHold.setEttId(user.getEttId());
		installEdrOnHold.setOfferId(offerDetails.getOfferId());
		installEdrOnHold.setAmount(10);
		installEdrOnHold.setCreatedTime(new Date());
		installEdrOnHold.setPendingCreditAmount(0);
		installEdrOnHold.setPendingCreditDay(0);
		installEdrOnHoldRepository.save(installEdrOnHold);
		
		if(!rechargeService.getLocaleTextTemplate().get("AMAZON_SERIES_SUS_INSTALL_PUSH_"+user.getLocale()).equals("")){
			DeviceToken deviceToken = deviceTokenRepository.findByEttId(user.getEttId());
			if(deviceToken!=null && !deviceToken.getDeviceToken().equals("")){
				sendPush(rechargeService.getLocaleTextTemplate().get("AMAZON_SERIES_SUS_INSTALL_PUSH_"+user.getLocale()), deviceToken, user.getEttId());
			}
		}
	}


	@Override
	public void insertInDataUsagePendingCredits(OfferDetails offerDetails,Long ettId) {
		DataUsagePendingCredits dataUsagePendingCredits = new DataUsagePendingCredits();
		try{
			dataUsagePendingCredits.setEttId(ettId);
			dataUsagePendingCredits.setOfferId(offerDetails.getOfferId());
			dataUsagePendingCredits.setAppKey(offerDetails.getAppKey());
			dataUsagePendingCredits.setMaxCreditLimit(offerDetails.getMaxCreditLimit());
			dataUsagePendingCredits.setMaxCreditPerDayLimit(offerDetails.getMaxCreditPerDayLimit());
			dataUsagePendingCredits.setAmountPerDataThreshold(offerDetails.getAmountPerDataThreshold());
			dataUsagePendingCredits.setDataThreshold(offerDetails.getDataThreshold());
			DateTime endDate = new DateTime().plusDays(offerDetails.getDataUsageEligibleDays());
			dataUsagePendingCredits.setPayoutEndDate(endDate.toDate());
			dataUsagePendingCredits.setEligibleStatus(0);
			dataUsagePendingCredits.setTotalUsedData(0l);
			dataUsagePendingCredits.setImageUrl(offerDetails.getImageUrl());
			dataUsagePendingCredits.setPackageName(offerDetails.getPackageName());
			dataUsagePendingCredits.setOfferName(offerDetails.getOfferName());
			dataUsagePendingCredits = dataUsagePendingCreditsRepository.save(dataUsagePendingCredits);
			LOGGER.info("ettId={}, inserted into DataUsagePensingCredits",ettId);
			
			DataUsagePushDto dataUsagePushDto = new DataUsagePushDto();
			dataUsagePushDto.setAppKey(offerDetails.getAppKey());
			dataUsagePushDto.setDataThreshold(offerDetails.getDataThreshold());
			dataUsagePushDto.setEligibleDays(offerDetails.getDataUsageEligibleDays());
			dataUsagePushDto.setId(dataUsagePendingCredits.getId());
			dataUsagePushDto.setPackageName(offerDetails.getPackageName());
			dataUsagePushDto.setTimeInterval(offerDetails.getTimeIntervalOfRecheck());
			
			DeviceToken deviceToken = deviceTokenRepository.findByEttId(dataUsagePendingCredits.getEttId());
			if(deviceToken!=null && !deviceToken.getDeviceToken().equals("")){
				Gson gson = new Gson();
		    	String json = gson.toJson(dataUsagePushDto);
				sendDataUsagePush(json, deviceToken,dataUsagePendingCredits.getEttId());
 		   }
			
		}catch (Exception e){
			LOGGER.error("error occured while inserting in DataUsagePendingCredits"+e);
		}
		
	}


	@Override
	public OffersStarted setOffersStartedDetails(OffersStarted offersStarted,OfferDetails offerDetails,Long ettId,Long offerId,String payoutType,String appVersion,User user) {
		try {
				if(offersStarted == null && !rechargeService.getOfferIdBlockedInOffersStarted().contains(offerId)) {
					offersStarted = new OffersStarted();
					offersStarted.setId(ettId + "_" + offerId);
					offersStarted.setEttId(ettId);
					offersStarted.setOfferId(offerId);
					offersStarted.setOfferCategory(offerDetails.getOfferCategory());
					offersStarted.setOfferName(offerDetails.getOfferName());
					offersStarted.setInstalledTime(new Date());
					offersStarted.setPackageName(offerDetails.getPackageName());
					offersStarted.setPayOutType(payoutType);
					offersStarted.setPayoutOn(offerDetails.getPayoutOn().toString());
					offersStarted.setImageUrl(offerDetails.getImageUrl());
					if(offersStarted.getPayoutOn().equalsIgnoreCase("register"))
					{
						offersStarted.setOfferLifeCycle("Click,Open,Register");
						offersStarted.setEarnInfo("register and earn #offerAmount#".replace("#offerAmount#", offerDetails.getOfferAmount()+"").replaceAll(".0 ",""));
					}
					else if(offersStarted.getPayoutOn().equalsIgnoreCase("Use")){
						offersStarted.setOfferLifeCycle("Click,Install,Use");
						offersStarted.setEarnInfo("use and earn #offerAmount#".replace("#offerAmount#", offerDetails.getOfferAmount()+"").replaceAll(".0 ",""));
					}
					else if(offersStarted.getPayoutOn().equalsIgnoreCase("Order")){
						offersStarted.setOfferLifeCycle("Download,Open,Order");
						offersStarted.setEarnInfo("order and earn #offerAmount#".replace("#offerAmount#", offerDetails.getOfferAmount()+"").replaceAll(".0 ",""));
					}
					
					else if(offersStarted.getPayoutOn().equalsIgnoreCase("healthQuery")){
						offersStarted.setOfferLifeCycle("Click,Install,Ask health query");
						offersStarted.setEarnInfo("Ask a question and earn #offerAmount#".replace("#offerAmount#", offerDetails.getOfferAmount()+"").replaceAll(".0 ",""));
					}
					else if(offersStarted.getPayoutOn().equalsIgnoreCase("searchFlight")){
						offersStarted.setOfferLifeCycle("Click,Install,Search for Flight");
						offersStarted.setEarnInfo("Search for Flight and earn #offerAmount#".replace("#offerAmount#", offerDetails.getOfferAmount()+"").replaceAll(".0 ",""));
					}
					else {
						offersStarted.setOfferLifeCycle("Click,Install,Open");
						offersStarted.setEarnInfo("open and earn #offerAmount#".replace("#offerAmount#", offerDetails.getOfferAmount()+"").replaceAll(".0 ",""));
					}
					offersStarted.setApproveInfoText("approval pending from #offerName# team".replace("#offerName#", offerDetails.getOfferName()));
					offersStarted.setCriticalInfo("have you installed #offerName# before".replace("#offerName#", offerDetails.getOfferName()));
					if(offerDetails.getInstructions()!=null){
						offersStarted.setInstructions(offerDetails.getInstructions().replace("#INSTALLEDAMOUNT#", ((offerDetails.getOfferAmount()-offerDetails.getPendingAmountCredit())+"")).replace("#AMOUNT#", offerDetails.getOfferAmount()+"").replace("#OFFER_CREDIT_DAYS#", offerDetails.getBalanceCreditInDays()+"").replace("#PENDINGAMOUNT#", offerDetails.getPendingAmountCredit()+"").replace("#PENDINGRECCOUNT#", offerDetails.getPendingRecCount()+"").replace("#PENDINGRECDAY#", offerDetails.getPendingRecDay()+"").replaceAll("\\.0",""));
					try {
						if((offerDetails.getPayoutType() == PayoutType.DATAUSAGE || offerDetails.getMaxCreditLimit()>0.0f) && Float.parseFloat(appVersion)>=2.0f) {
							offersStarted.setInstructions(offersStarted.getInstructions()+";"+rechargeService.getLocaleTextTemplate().get("DATA_USAGE_INSTRUCTION_"+user.getLocale()).replace("#amountPerDataThreshold#", ((int)offerDetails.getAmountPerDataThreshold())+"").replace("#dataThreshold#", (offerDetails.getDataThreshold()/1024)+"").replace("#maxDataUsageLimit#", ((offerDetails.getDataThreshold()/1024)*((int)offerDetails.getMaxCreditLimit()))+"").replace("#maxCreditPerDayLimit#", ((int)offerDetails.getMaxCreditPerDayLimit())+"").replace("#maxCreditLimit#", ((int)offerDetails.getMaxCreditLimit())+"")+"$"+offerDetails.getMaxCreditLimit()+"");
						}
					}catch(Exception ex3) { LOGGER.info("erorr in adding data offer in offerStarted ettId={}",ettId);}
					}
					if(offerDetails.getUserDeferedInfo()!=null) {
						try {
							DateTime dt1 = new DateTime().plusDays(offerDetails.getBalanceCreditInDays());
							Date d1 = dt1.toDate();
							String newstring = new SimpleDateFormat("dd-MMM-yyyy").format(d1);
							String userDefInfo = offerDetails.getUserDeferedInfo().replace("#AMOUNT#", ((offerDetails.getOfferAmount() - offerDetails.getPendingAmountCredit()))+"").replace("#OFFER_CREDIT_DAYS#", offerDetails.getBalanceCreditInDays()+"").replace("#PENDINGAMOUNT#", offerDetails.getPendingAmountCredit()+"").replace("#PenditCreditDate#", newstring).replaceAll("\\.0","");
							offersStarted.setUserDeferedInfo(userDefInfo.split("\n")[0]);
							try {
								offersStarted.setDeferedPaymentFinalInfor(userDefInfo.split("\n")[1]);
							}catch(Exception ex2) {}
						}catch(Exception ex){}
					}
					offersStarted.setStatus(true);
					if(offersStarted.getDeferedPaymentFinalInfor()==null) {
						offersStarted.setDeferedPaymentFinalInfor("");
					}
					offersStarted = offersStartedRepository.save(offersStarted);
					return offersStarted;
				}
		}catch(Exception ex){
			LOGGER.error("error in setOffersStartedDetails="+ex);
			ex.printStackTrace();
		}
		return offersStarted;
			
	}


}
