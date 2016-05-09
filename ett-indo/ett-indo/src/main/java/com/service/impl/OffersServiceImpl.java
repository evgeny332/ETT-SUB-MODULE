package com.service.impl;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import scala.actors.threadpool.Arrays;

import com.domain.entity.BreakingAlert;
import com.domain.entity.InstalledApps;
import com.domain.entity.LocaleTextTemplate.Locale;
import com.domain.entity.OfferDetails;
import com.domain.entity.OfferTextDetails;
import com.domain.entity.PopUpAlert;
import com.domain.entity.TodaysOffers;
import com.domain.entity.TransactionTracker;
import com.domain.entity.User;
import com.domain.entity.UserAccountSummary;
import com.domain.entity.UserCompetitorApps;
import com.domain.entity.UserProfile;
import com.domain.entity.UserTargetAmountBase;
import com.repository.jpa.BreakingAlertRepository;
import com.repository.jpa.InstalledAppsRepository;
import com.repository.jpa.OfferDetailsRepository;
import com.repository.jpa.OfferTextDetailsRepository;
import com.repository.jpa.PopUpAlertRepository;
import com.repository.jpa.TodaysOffersRepository;
import com.repository.jpa.TransactionTrackerRepository;
import com.repository.jpa.UserAccountSummaryRepository;
import com.repository.jpa.UserCompetitorAppsRepository;
import com.repository.jpa.UserProfileRepository;
import com.repository.jpa.UserTargetAmountBaseRepository;
import com.service.BreakingAlertService;
import com.service.EttApis;
import com.service.OffersService;
import com.service.PopUpAlertService;
import com.service.RechargeService;
import com.web.rest.dto.BreakingAlertDto;
import com.web.rest.dto.OffersDtoV2;
import com.web.rest.dto.OffersDtoV4;
import com.web.rest.dto.OffersStartedDto;
import com.web.rest.dto.PopUpAlertDto;
import com.web.rest.dto.ShoppingDetailsDto;


@Service
public class OffersServiceImpl implements OffersService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(OffersServiceImpl.class);

	@Resource
	private OfferDetailsRepository offerDetailsRepository;
	
	@Resource
	private TransactionTrackerRepository transactionTrackerRepository;
	
	@Resource
	private InstalledAppsRepository installedAppsRepository;
	
	@Resource 
	private TodaysOffersRepository todaysOffersRepository;
	
	@Resource
	private UserProfileRepository userProfileRepository;
	
	@Resource
	UserTargetAmountBaseRepository userTargetAmountBaseRepository;
	
	@Resource
	private UserAccountSummaryRepository userAccountSummaryRepository;
	
	@Resource
	BreakingAlertRepository breakingAlertRepository;
	
	@Resource
	PopUpAlertRepository popUpAlertRepository;

	@Resource
	private RechargeService rechargeService;
	
	@Resource
	private BreakingAlertService breakingAlertService;
	
	@Resource
	private PopUpAlertService popUpAlertService;
	
	@Resource
	private EttApis ettApisService;
	
	@Resource
	private OfferTextDetailsRepository offerTextDetailsRepository;

	@Resource
	private UserCompetitorAppsRepository userCompetitorAppsRepository;
	
	private int TODAYS_OFFERS_MAX_SIZE;
	
	
	public OffersServiceImpl(){
		
	}
	
	@PostConstruct
	public void init(){
		TODAYS_OFFERS_MAX_SIZE = Integer.valueOf(rechargeService.getAppConfig().get("TODAYS_OFFERS_MAX_SIZE"));
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public List<OfferDetails> getOffers(User user, boolean isCategorize,String networkType) {
		List<OfferDetails> offerDetails = new ArrayList<>(20);
		//get todays offers	
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int timezone = user.getTimeZoneInSeconds();
		if(timezone == 0)
			timezone = 19800;
		 Date date = DateTime.now(DateTimeZone.forOffsetMillis(timezone*1000)).toDateMidnight().toDate();
	     //String today = df.format(date);
		
		List<TodaysOffers> todaysOffers = todaysOffersRepository.findByEttIdAndCreatedTimeGreaterThanAndOfferDetails_Status(user.getEttId(), date, true);
		if(todaysOffers.size()>0){
			List<String> transactionTrackerIds = new ArrayList<>(offerDetails.size());
			for(TodaysOffers todaysOffer : todaysOffers){
				offerDetails.add(todaysOffer.getOfferDetails());
				transactionTrackerIds.add(user.getEttId()+"_"+todaysOffer.getOfferDetails().getOfferId());
			}
			LOGGER.info("todays offers for ettId={}, offerSize= {} before substraction",user.getEttId(), offerDetails.size());

			//find from transaction tracker
			List<TransactionTracker> transactionTrackers = transactionTrackerRepository.findByIdIn(transactionTrackerIds);

			List<OfferDetails> offersToDel = new ArrayList<>(offerDetails.size());
			for(OfferDetails offerDetail : offerDetails){
				for(TransactionTracker transactionTracker : transactionTrackers){
					if(offerDetail.getOfferId().equals(transactionTracker.getOfferId())){
						if(isCategorize){
							if(offerDetail.isDownload()){
								offerDetail.setDownload(false);
							}
							
							if(!(offerDetail.isClick() || offerDetail.isDownload() || offerDetail.isShare() || offerDetail.isShop())){
								offersToDel.add(offerDetail);	
							}
						}else{
							offersToDel.add(offerDetail);							
						}
						//System.out.println(offerDetail.getOfferId());
						continue;
					}
				}				
			}

			offerDetails.removeAll(offersToDel);
			freshUserBonusOffer(offerDetails,user);
		 	LOGGER.info("todays offers for ettId={}, offerSize= {} after substraction",user.getEttId(), offerDetails.size());
		 	
		 	offerDetails=getOfferFilter(offerDetails,networkType);
		 	if(rechargeService.getAppConfig().get("AMAZON_SERIES_SUS_FLAGE").equals("true") || (user.getIsDeviceSupport()>=6 && user.getIsDeviceSupport()<=11))
		 	{
		 		changeAmazonOfferDetails(user,offerDetails,false);
		 	}
		 	if(user.getLocale() != Locale.EN){
				   offerDetails = changeTextDetails(offerDetails,user);
			}
			return offerDetails;
		}

	    offerDetails = getAllOffers(user, isCategorize);
	    
	    if (offerDetails.size() == 0) {
			LOGGER.warn("no offers for today ettId="+user.getEttId());
			return Collections.emptyList();
		}
	     /*if(rechargeService.getProperties().getProperty("TODAYS_OFFERS_MAX_SIZE_WIFI").equals(true) && networkType.equalsIgnoreCase("WIFI")){
	    	//same as it is  
	      }
	      else{
		    if(offerDetails.size() > TODAYS_OFFERS_MAX_SIZE){
		    	List<OfferDetails> nonIncentiveOffers = new ArrayList<>(30);
		    	for(int i=TODAYS_OFFERS_MAX_SIZE; i< offerDetails.size(); i++){
		    		OfferDetails offerDetail = offerDetails.get(i);
		    		if(offerDetail.getOfferAmount() <= 1){
		    			nonIncentiveOffers.add(offerDetail);
		    		}
		    	}
		    	
		    	offerDetails = offerDetails.subList(0, TODAYS_OFFERS_MAX_SIZE);
		    	
		    	if(nonIncentiveOffers.size() > 0)
		    		offerDetails.addAll(nonIncentiveOffers);
		    }
	    
	      }*/
	    List<Long> offerIdList = new ArrayList<>();
		for(OfferDetails offerDetail : offerDetails){
			offerIdList.add(offerDetail.getOfferId());
		}		
	    saveTodaysOffers(user.getEttId(), offerIdList);  
	    
	    freshUserBonusOffer(offerDetails,user);
	    offerDetails=getOfferFilter(offerDetails,networkType);
	    
	   if(rechargeService.getAppConfig().get("AMAZON_SERIES_SUS_FLAGE").equals("true"))
	   {
 		 changeAmazonOfferDetails(user,offerDetails,true);
	   }
	   //locale text template implementation
	   System.out.println("total size before : "+offerDetails.size());
	   if(user.getLocale() != Locale.EN){
		   offerDetails = changeTextDetails(offerDetails,user);
	   }
		
		return offerDetails;
	}

	private List<OfferDetails> changeTextDetails(List<OfferDetails> offerDetails, User user) {
		List<String> offerIds = new ArrayList<>(offerDetails.size());
		for(OfferDetails details : offerDetails){
			offerIds.add(details.getOfferId() + "_" + user.getLocale());
			System.out.println("offerId : "+details.getOfferId());
		}
		List<OfferTextDetails> offerTextDetails = offerTextDetailsRepository.findByIdIn(offerIds);
		if(offerTextDetails == null || offerTextDetails.size() == 0){
			return Collections.EMPTY_LIST;
		}
		int index = 0;
		OfferDetails tempOffer = new OfferDetails();
		System.out.println("offerTextSize : "+offerTextDetails.size());
		for(OfferTextDetails textDetails : offerTextDetails){
			System.out.println("text OI : "+textDetails.getOfferId());
			tempOffer.setOfferId(textDetails.getOfferId());
			index = offerDetails.indexOf(tempOffer);
			if(index >= 0){
				OfferDetails offer = offerDetails.get(index);
				offer.setDescription(textDetails.getDescription());
				offer.setAlertText(textDetails.getAlertText());
				offer.setCallbackNotification(textDetails.getCallBackNotification());
				offer.setInstalledNotification(textDetails.getInstallNotification());
				offer.setInstructions(textDetails.getInstruction());
				offer.setAppDescription(textDetails.getAppDescription());
				offer.setDetailedInstructions(textDetails.getDetailedInstructions());
				offer.setUserDeferedInfo(textDetails.getUserDeferedInfo());
				offer.setOfferCategory(textDetails.getOfferCategory());
			}
		}
		return offerDetails;
	}

	public List<OfferDetails> getOfferFilter(List<OfferDetails> offerDetails,String networkType) {
		
		if (rechargeService.getAppConfig().get("TODAYS_OFFERS_MAX_SIZE_WIFI").equals("true") && networkType.equalsIgnoreCase("WIFI")) {
			// same as it is
		} else {
			if (offerDetails.size() > TODAYS_OFFERS_MAX_SIZE) {

				List<OfferDetails> nonIncentiveOffers = new ArrayList<>(30);
				for (int i = TODAYS_OFFERS_MAX_SIZE; i < offerDetails.size(); i++) {
					OfferDetails offerDetail = offerDetails.get(i);
					if (offerDetail.getOfferAmount() <= 1) {
						System.out.println("deleted : "+ offerDetail.getOfferName());
						nonIncentiveOffers.add(offerDetail);
						offerDetails.remove(i);
					}
				}

				offerDetails = offerDetails.subList(0, TODAYS_OFFERS_MAX_SIZE);
				if (nonIncentiveOffers.size() > 0)
					offerDetails.addAll(nonIncentiveOffers);
			}

		}
		return offerDetails;
		
	}
	private void saveTodaysOffers(Long ettId,  List<Long> offerIdList) {
		
		List<OfferDetails> offersToSave = offerDetailsRepository.findByOfferIdIn(offerIdList);
		
		 List<TodaysOffers> todaysOffersToSave = new ArrayList<>(20);
		    for(OfferDetails offerDetail : offersToSave){
		    	TodaysOffers todaysOffer = new TodaysOffers();
		    	todaysOffer.setEttId(ettId);
		    	todaysOffer.setOfferDetails(offerDetail);
		    	todaysOffer.setCreatedTime(new Date());
		    	todaysOffer.setUpdatedTime(new Date());
		    	todaysOffersToSave.add(todaysOffer);
		    }
		    LOGGER.info("saving todays offers for ettId={}, offerSize={}", ettId, offerIdList.size());
		    todaysOffersRepository.save(todaysOffersToSave);  
		
	}

	private List<OfferDetails> getAllOffers(User user, boolean isCategorize) {
		//get all offers
		List<OfferDetails> offerDetailsList = offerDetailsRepository.findByStatus(true);
		
		//get category offers
		
		if(user.getCategory()!=null && !user.getCategory().equals("")){
				List<String> cat = Arrays.asList(user.getCategory().split(","));
				List<OfferDetails> categoryOffers = offerDetailsRepository.findByStatusAndCategory(true, cat);
		    	if(categoryOffers.size()>0)
		    		offerDetailsList.addAll(0, categoryOffers);
		}
			
		
		List<String> appKeyIds = new ArrayList<>(offerDetailsList.size());
		List<String> transactionTrackerIds = new ArrayList<>(offerDetailsList.size());
		
		/***************************Check from UserProfile ****************************************************/
		UserProfile userProfile = userProfileRepository.findByEttId(user.getEttId());
		if(userProfile==null) {
			userProfile = new UserProfile();
		}	
			if(userProfile.getGender()==null || userProfile.getGender().length()==0){
				userProfile.setGender("UNKNOWN");
			}
			if(userProfile.getOperatorCircle()==null || userProfile.getOperatorCircle().length()==0){
				userProfile.setOperatorCircle("UNKNOWN");
			}
		
		List<String> operatorList = Arrays.asList(userProfile.getOperatorCircle().split(","));
		List<OfferDetails> offerDetailsFiler = new ArrayList<OfferDetails>(offerDetailsList.size());

		/***************************Check from UserProfile ****************************************************/
		
		for(OfferDetails offerDetail : offerDetailsList){				
				boolean checkOfferCat = false;
				if(rechargeService.getAppConfig().get("Profile_Filter_UserProfile").equals("true")){
					checkOfferCat = checkOfferProfiling(user.getEttId(),offerDetail,userProfile.getGender(),userProfile.getOperatorCircle(),userProfile.getOperator(),userProfile.getDob());
				}
				if(checkOfferCat){
					offerDetailsFiler.add(offerDetail);
					appKeyIds.add(user.getEttId()+"_"+offerDetail.getAppKey());
					transactionTrackerIds.add(user.getEttId()+"_"+offerDetail.getOfferId());
				}
		}
		
		offerDetailsList = offerDetailsFiler;
		//LOGGER.info("final list of offers={}",offerDetailsList);
		
		//find installed apps of user
		/*********** handing the 'Amazon' name to 'Amazon Shoping' ***************************/
		if(!rechargeService.getAppConfig().get("AMAZON_CHECK_INSTALL").equals("true")) {
		appKeyIds.add(user.getEttId()+"_"+"Amazon");
		}
		/*************************************************************************************/
		List<InstalledApps> installedApps = installedAppsRepository.findByIdIn(appKeyIds);
		
		/*********** handing the 'Amazon' name to 'Amazon Shoping' ***************************/
		if(!rechargeService.getAppConfig().get("AMAZON_CHECK_INSTALL").equals("true")) {
		InstalledApps intappAmz = new InstalledApps();
		intappAmz.setId(user.getEttId()+"_Amazon");
		intappAmz.setEttId(user.getEttId());
		intappAmz.setAppKey("Amazon Shopping");
		intappAmz.setCreatedTs(new Date());
		if(installedApps.contains(intappAmz)) {
				LOGGER.info("found Amazon old ettId={}",user.getEttId());
				intappAmz.setId(user.getEttId()+"_Amazon Shopping");
				installedApps.add(intappAmz);
				}
		}
		/********************************************************************************/

		//find from transaction tracker
		List<TransactionTracker> transactionTrackers = transactionTrackerRepository.findByIdIn(transactionTrackerIds);
		
		List<OfferDetails> offersToDel = new ArrayList<>(offerDetailsList.size());
		for(OfferDetails offerDetail : offerDetailsList){
			for(TransactionTracker transactionTracker : transactionTrackers){
				if(offerDetail.getOfferId().equals(transactionTracker.getOfferId())){
					if(isCategorize){
						if(offerDetail.isDownload()){
							offerDetail.setDownload(false);
						}
						
						if(!(offerDetail.isClick() || offerDetail.isDownload() || offerDetail.isShare() || offerDetail.isShop())){
							offersToDel.add(offerDetail);	
						}
						
					}else{
						offersToDel.add(offerDetail);							
					}
					continue;
				}
			}
			
			for(InstalledApps installedApp : installedApps){
				if(offerDetail.getAppKey().equals(installedApp.getAppKey())){
					
					//before 6 month app show
					if(rechargeService.getAppConfig().get("Before_6_Month_App_Show").equals("true") && installedApp.getCreatedTs()!=null) {
						long sameDay1 = (new Date().getTime())-installedApp.getCreatedTs().getTime();
                        int dayGap = (int)(sameDay1/(1000*60*60*24));
						if(dayGap>=180) {
							LOGGER.info("Installed App before 6 month Install so showing it ettId={},appKey={},createdTime={}",installedApp.getEttId(),installedApp.getAppKey(),installedApp.getCreatedTs());
							continue;
						}
						
					}
					
					if(isCategorize){
						if(offerDetail.isDownload()){
							offerDetail.setDownload(false);
						}
						
						if(!(offerDetail.isClick() || offerDetail.isDownload() || offerDetail.isShare() || offerDetail.isShop())){
							offersToDel.add(offerDetail);	
						}
						
					}else{
						offersToDel.add(offerDetail);							
					}
					continue;
				}
			}		
		}

		offerDetailsList.removeAll(offersToDel);

		return offerDetailsList;
	}
	
	public boolean checkOfferProfiling(Long ettId,OfferDetails offerDetail,String userGender,String userOperatorCircle,String userOperator,String userDOB){
		
		if(offerDetail.getGender()==null || offerDetail.getGender().length()==0) {
			offerDetail.setGender("All");
		}
		if(offerDetail.getOperatorCircle()==null || offerDetail.getOperatorCircle().length()==0){
			offerDetail.setOperatorCircle("All");
		}
		if(offerDetail.getOperator()==null || offerDetail.getOperator().length()==0){
			offerDetail.setOperator("All");
		}
		if(offerDetail.getAgeLimit()==null || offerDetail.getAgeLimit().length()==0){
			offerDetail.setAgeLimit("All");
		}
	
		List<String> offerDetailoperatorCircleList = Arrays.asList(offerDetail.getOperatorCircle().split(","));
		List<String> offerDetailoperatorList = Arrays.asList(offerDetail.getOperator().split(","));
		if(!offerDetail.getGender().equalsIgnoreCase("All")) {
			if(!offerDetail.getGender().equals(userGender)) {
				LOGGER.info("Offer not for this user ettId={},userGender={}offerGender={}",ettId,userGender,offerDetail.getGender());
				return false;
			}
		}
		

		if(!offerDetailoperatorCircleList.contains("All")){
			if(!offerDetailoperatorCircleList.contains(userOperatorCircle)){
				LOGGER.info("Offer not for this user ettId={},userGender={},userOperator={},offerGender={}",ettId,userGender,userOperatorCircle,offerDetail.getGender());
				return false;
			}
		}	
		
		if(!offerDetailoperatorList.contains("All")){
			if(!offerDetailoperatorList.contains(userOperator)){
				LOGGER.info("Offer not for this user ettId={},userGender={},userOperator={},offerGender={}",ettId,userGender,userOperator,offerDetail.getGender());
				return false;
			}
		}	

		if(!offerDetail.getAgeLimit().contains("All")){
			try {
				String age1[]=offerDetail.getAgeLimit().split("-");
				int offerMinAge=Integer.parseInt(age1[0]);
				int offerMaxAge=Integer.parseInt(age1[1]);
				int userAge = ettApisService.getUserAge(userDOB);
	
				if(userAge<offerMinAge || userAge>offerMaxAge){
					LOGGER.info("Offer not for this user ettId={},user DOB={},userOperator={},offerAgeLimit={}",ettId,userDOB,userOperatorCircle,offerDetail.getAgeLimit());
					return false;
					}
			}catch(Exception ex) {LOGGER.info("Error in filtring the offers ettId={}",offerDetail.getOfferId());return false;}
		}
		return true;
	
	}
	
	@Override
	public void freshUserBonusOffer(List<OfferDetails> offerDetails,User user) {
		/************ BONUS GAURANTEED OFFER 100 ***********/
		if(rechargeService.getAppConfig().get("BONUS_GAURANTEED_OFFER_100").equals("true") && user.getTotlNoOfDLoadApp()>0){
			List<UserAccountSummary> userAcSummary;
			userAcSummary = userAccountSummaryRepository.findByEttIdAndOfferId(user.getEttId(), 8886L);
			if(userAcSummary == null || userAcSummary.size()<=0){
				//OfferDetails bonusOffer = offerDetailsRepository.findByOfferId(387L);
				OfferDetails bonusOffer = new OfferDetails();
				bonusOffer.setOfferId(387l);
				int index = offerDetails.indexOf(bonusOffer);
				if(index>-1){
					bonusOffer = offerDetails.get(index);
					if(bonusOffer!=null && user.getCreatedTime().getTime()<1432040400000l){
						bonusOffer.setActionUrl(bonusOffer.getActionUrl().replace("bonus100", "bonus100O"));
						bonusOffer.setAlertText(bonusOffer.getAlertText().replace("(At least Rs.100 of earnings need to be from App downloads)", ""));
						bonusOffer.setAlertText(bonusOffer.getAlertText().replace("Earn Rs.400 in the first 100 days of joining Earn Talktime", "Earn Rs.400 before 26th August 2015"));
						//bonusOffer.setDescription(bonusOffer.getDescription().replace("", ""));
					}
					//offerDetails.add(0, bonusOffer);
					LOGGER.info("Bonus offer 100 added for ettId="+user.getEttId());
				}
			}
			else {
				//remove
				OfferDetails bonusOffer = new OfferDetails();
				bonusOffer.setOfferId(387l);
				int index = offerDetails.indexOf(bonusOffer);
				if(index>-1){
					offerDetails.remove(offerDetails.indexOf(bonusOffer));
					LOGGER.info("offerId 387 deleted for ettId="+user.getEttId());
				}
			}
		}
		else {
			//remove
			OfferDetails bonusOffer = new OfferDetails();
			bonusOffer.setOfferId(387l);
			int index = offerDetails.indexOf(bonusOffer);
			if(index>-1){
				offerDetails.remove(offerDetails.indexOf(bonusOffer));
				LOGGER.info("offerId 387 deleted for ettId="+user.getEttId());
			}
		}
		/************ BONUS GAURANTEED OFFER 100 ***********/
		int sameDay =5;
		try {
			int timezone = user.getTimeZoneInSeconds();
			if(timezone == 0)
				timezone = 19800;
			Date nowTime = new Date();
			nowTime.setTime(nowTime.getTime()+timezone*1000);
			
			Date userCT = new Date();
			long userCTT = user.getCreatedTime().getTime(); 
			userCT.setTime(userCTT+timezone*1000);
			/*DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			int nowTimeWithZeroTime = Integer.parseInt(formatter.format(nowTime));
			int userCTWithZeroTime = Integer.parseInt(formatter.format(userCT));
			sameDay = nowTimeWithZeroTime-userCTWithZeroTime;*/
			
			long sameDay1 = nowTime.getTime() - userCTT;
			sameDay = (int)(sameDay1/(1000*60*60*24));
		}catch(Exception ex1){
			ex1.printStackTrace();
			LOGGER.error("Error in freshUserBonusOffer"+ex1);
		}
		
		 if(sameDay<=2) {
			if(rechargeService.getAppConfig().get("NEW_USER_BONUS_ON_FIRST_APP").equals("true") ){
				if(user.getTotlNoOfDLoadApp()<=0) {
					OfferDetails bonusOffer =  new OfferDetails();
			 		bonusOffer.setOfferId(230l);
			 		offerDetails.remove(bonusOffer);
			 		bonusOffer.setOfferId(374l);
			 		int index = offerDetails.indexOf(bonusOffer);
		 			if(index>-1) {
		 				bonusOffer=offerDetails.get(index);
		 				if(sameDay==1){
			 				bonusOffer.setOfferAmount(700);
			 				bonusOffer.setActionUrl(bonusOffer.getActionUrl().replaceFirst("bonus-extra.jsp", "bonus-extraDay2.jsp"));
			 				bonusOffer.setAlertText(bonusOffer.getAlertText().replaceFirst("1,000", "700"));
			 				bonusOffer.setDescription(bonusOffer.getDescription().replaceFirst("1,000","700"));
		 				}
		 				else if(sameDay==2)
		 				{
		 					bonusOffer.setOfferAmount(500);
		 					bonusOffer.setActionUrl(bonusOffer.getActionUrl().replaceFirst("bonus-extra.jsp", "bonus-extraDay3.jsp"));
			 				bonusOffer.setAlertText(bonusOffer.getAlertText().replaceFirst("1,000", "500"));
			 				bonusOffer.setDescription(bonusOffer.getDescription().replaceFirst("1,000","500"));
		 				}
		 				else{}
		 			}
			 		if(rechargeService.getAppConfig().get("NEW_USER_BONUS_ON_FIRST_APP_REDIRECT_URL").equals("true")){
			 			OfferDetails of = new OfferDetails();
			 			of.setOfferId(374l);
			 			//LOGGER.info(offerDetails.toString());
			 			index = offerDetails.indexOf(of);
			 			if(index>-1) {
				 			OfferDetails of1 = offerDetails.get(index);
				 			OfferDetails offD = offerDetails.get(2);
				 			of1.setActionUrl(offD.getActionUrl());
				 			of1.setAppKey(offD.getAppKey());
				 			of1.setOfferId(offD.getOfferId());
				 			offerDetails.set(index, of1);
			 			}
			 			
			 		}
			 		//return offerDetails;
				}
				else{
					OfferDetails bonusOffer =  new OfferDetails();
			 		bonusOffer.setOfferId(374l);
			 		offerDetails.remove(bonusOffer);
			 		
			 		//return offerDetails;
				}
				
		 	}
		 	else{
		 		OfferDetails bonusOffer =  new OfferDetails();
		 		bonusOffer.setOfferId(374l);
		 		offerDetails.remove(bonusOffer);
		 		//return offerDetails;
		 	}
		}
		 else {
			OfferDetails bonusOffer =  new OfferDetails();
		 	bonusOffer.setOfferId(374l);
		 	offerDetails.remove(bonusOffer);
			 LOGGER.info("User not the same day so not eligible for the fresh bonus balance ettId{}",user.getEttId());
		 }
	}
	
	@Override
	public  byte[] compress(String str) throws Exception {
        if (str == null || str.length() == 0) {
            return str.getBytes(Charset.forName("UTF-8"));
        }
        //System.out.println("String length : " + str.length());
        ByteArrayOutputStream obj=new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(obj);
        //byte[] b = obj.getBytes(Charset.forName("UTF-8"));
        gzip.write(str.getBytes("UTF-8"));
        gzip.close();
        //String outStr = obj.toString("UTF-8");
        //System.out.println("Output String length : " + gzip.length());
        return obj.toByteArray();
     }
	
	@Override
	public void changeAmazonOfferDetails(User user,List<OfferDetails> offerDetails,boolean priority) {
		String series = user.getMsisdn().substring(0,5);
		if(rechargeService.getAmazonSeriesSus().contains(series)){
		    OfferDetails amazonOffer = new OfferDetails();
		    amazonOffer.setOfferId(108l);
			int index = offerDetails.indexOf(amazonOffer);
			if(index>-1){
				amazonOffer=offerDetails.get(index);
				amazonOffer.setOfferAmount(10.0f);
				amazonOffer.setPendingAmountCredit(0.0f);
				amazonOffer.setPendingRecDay(0);
				amazonOffer.setDescription("Download Amazon App  and you can earn Rs.10");
				amazonOffer.setAlertText("1. download Amazon app and earn Rs.10 within 7 days, post confirmation from Amazon\n\n2. keep the app for 7 days\n\n**Offer is for users that have not installed this app before");
				amazonOffer.setInstructions("");
				//offerDetails.remove(offerDetails.indexOf(amazonOffer));
				LOGGER.info("Amazon offer suspected series modified with Rs.10 ettId={}",user.getEttId());
				/*if(priority && offerDetails.size()>=7) {
					 amazonOffer = offerDetails.get(7);
					 OfferDetails offerDetails7 = offerDetails.get(7);
					 OfferDetails offerDetails1 = offerDetails.get(7);
					 offerDetails.get(7) = offerDetails.get(1);
					 amazonOffer.get(1) = amazonOffer;
				}*/
			}
		}
	}
	
	@Override
	public List<OfferDetails> getShareOffers(User user, boolean isCategorize,String networkType) {
		List<OfferDetails> offerDetails = offerDetailsRepository.findByStatusPayoutType(true,"SHARE");
		return offerDetails;
		
	}
	public boolean checkOfferProfiling1(Long ettId,OfferDetails offerDetail,String userGender,String userOperatorCircle,String userOperator,String userDOB){
		
		if(offerDetail.getGender()==null || offerDetail.getGender().length()==0) {
			offerDetail.setGender("All");
		}
		if(offerDetail.getOperatorCircle()==null || offerDetail.getOperatorCircle().length()==0){
			offerDetail.setOperatorCircle("All");
		}
		if(offerDetail.getOperator()==null || offerDetail.getOperator().length()==0){
			offerDetail.setOperator("All");
		}
		if(offerDetail.getAgeLimit()==null || offerDetail.getAgeLimit().length()==0){
			offerDetail.setAgeLimit("All");
		}
	
		List<String> offerDetailoperatorCircleList = Arrays.asList(offerDetail.getOperatorCircle().split(","));
		List<String> offerDetailoperatorList = Arrays.asList(offerDetail.getOperator().split(","));
		if(!offerDetail.getGender().equalsIgnoreCase("All")) {
			if(!offerDetail.getGender().equals(userGender)) {
				//LOGGER.info("Offer not for this user ettId={},userGender={}offerGender={}",ettId,userGender,offerDetail.getGender());
				return false;
			}
		}
		

		if(!offerDetailoperatorCircleList.contains("All")){
			if(!offerDetailoperatorCircleList.contains(userOperatorCircle)){
				//LOGGER.info("Offer not for this user ettId={},userGender={},userOperator={},offerGender={}",ettId,userGender,userOperatorCircle,offerDetail.getGender());
				return false;
			}
		}	
		
		if(!offerDetailoperatorList.contains("All")){
			if(!offerDetailoperatorList.contains(userOperator)){
				//LOGGER.info("Offer not for this user ettId={},userGender={},userOperator={},offerGender={}",ettId,userGender,userOperator,offerDetail.getGender());
				return false;
			}
		}	

		if(!offerDetail.getAgeLimit().contains("All")){
			try {
				String age1[]=offerDetail.getAgeLimit().split("-");
				int offerMinAge=Integer.parseInt(age1[0]);
				int offerMaxAge=Integer.parseInt(age1[1]);
				int userAge = ettApisService.getUserAge(userDOB);
	
				if(userAge<offerMinAge || userAge>offerMaxAge){
					//LOGGER.info("Offer not for this user ettId={},user DOB={},userOperator={},offerAgeLimit={}",ettId,userDOB,userOperatorCircle,offerDetail.getAgeLimit());
					return false;
					}
			}catch(Exception ex) {return false;}
		}
		return true;
	
	}
	
	@Override
	public boolean getAmazonSuspectFlage(User user,OfferDetails offerDetails) {
    	//if((rechargeService.getProperties().getProperty("AMAZON_SERIES_SUS_FLAGE").equals("true") && rechargeService.getProperties().getProperty("INSTALL_EDR_ON_HOLD_OFFERID").equals(offerDetails.getOfferId()+"")) && ((user.getIsDeviceSupport()>=6 && user.getIsDeviceSupport()<=11) || (userCompetitorAppsRepository.findByettId(user.getEttId()).size()>0))) {
		//if((rechargeService.getProperties().getProperty("AMAZON_SERIES_SUS_FLAGE").equals("true")) && ((user.getIsDeviceSupport()>=6 && user.getIsDeviceSupport()<=11) || (userCompetitorAppsRepository.findByettId(user.getEttId()).size()>0))) {
		if(rechargeService.getAppConfig().get("INSTALL_EDR_ON_HOLD_OFFERID").equals(offerDetails.getOfferId()+"")) {
			String series = user.getMsisdn().substring(0,5);
			if(((rechargeService.getAppConfig().get("AMAZON_SERIES_SUS_FLAGE").equals("true") && rechargeService.getAmazonSeriesSus().contains(series))) || ((user.getIsDeviceSupport()>=6 && user.getIsDeviceSupport()<=11))) {
				return true;
				
	    	}
			else if(rechargeService.getAppConfig().get("COMPATETOR_APP_AMAZON_OFFER_TYPE_B").equals("true")) {
				List<UserCompetitorApps> userCompetitorApps = userCompetitorAppsRepository.findByettId(user.getEttId());
				if(userCompetitorApps!=null && userCompetitorApps.size()>0) {
					return true;
				}
				else {
					return false;
				}
			}
	    	else {
	    		return false;
	    	}
		}
		else {
			return false;
		}
    }

	@Override
	public void userTargetAmountBase(OffersDtoV4 offerDto,User user) {
		if(rechargeService.getAppConfig().get("UserTargetAmountBaseCheck").equals("true")){
			UserTargetAmountBase userTargetAmountBase = userTargetAmountBaseRepository.findByEttId(user.getEttId());
			if(userTargetAmountBase!=null && userTargetAmountBase.isStatus()==false) {
				//List<BreakingAlert> breakingAlert = breakingAlertRepository.findById(true,userTargetAmountBase.getBreakingAlertId());
				List<BreakingAlert> breakingAlert = breakingAlertRepository.findById(false,userTargetAmountBase.getBreakingAlertId());
				if(breakingAlert!=null && breakingAlert.size()>0) {
						BreakingAlertDto breakingAlertDto = breakingAlertService.getBreakingAlertDto(breakingAlert.get(0), user);
						breakingAlertDto.setText(breakingAlertDto.getText().replace("#TODAYEARNING#", userTargetAmountBase.getTotalDayEarning()+""));
						offerDto.setBreakingAlertDto(breakingAlertDto);
				}
				
				PopUpAlert popUpAlerts = popUpAlertRepository.findById(userTargetAmountBase.getPopUpId(), true);
				if(popUpAlerts!=null ) {
					PopUpAlertDto popUpAlertDto = popUpAlertService.getPopUpAlertDto(popUpAlerts, user);
					offerDto.setPopUpAlertDto(popUpAlertDto);
				}
				
			}
		}
	}

	@Override
	public void offerStartedDtoUpdateIdCheck(List<OffersStartedDto> offerStartedDto, List<String> update_id) {
		if(offerStartedDto!=null && update_id !=null && offerStartedDto.size()>0 && update_id.size()>0) {
			List<OffersStartedDto> offersStartedDtoClient = new ArrayList<OffersStartedDto>(update_id.size());
			for(String update_id1 : update_id) {
				OffersStartedDto offersStartedDto2 = new OffersStartedDto();
				offersStartedDto2.setUpdate_id(update_id1);
				if(offerStartedDto.contains(offersStartedDto2)) {
					int index = offerStartedDto.indexOf(offersStartedDto2);
					//offerStartedDto.setOfferId(offerStartedDto.get(index).getOfferId());
					offerStartedDto.add(index,offersStartedDto2);
				}
				//offersStartedDtoClient.add(offersStartedDto2);
			}
			//offersStartedDtoClient.retainAll(offerStartedDto);
			//offerStartedDto.removeAll(offersStartedDtoClient);
			//offerStartedDto.addAll(offersStartedDtoClient);
		}
	}

	@Override
	public void offerShoppingDtoUpdateIdCheck(List<ShoppingDetailsDto> shoppingDetailsDtos, List<String> update_id) {
		if(shoppingDetailsDtos!=null && update_id !=null && shoppingDetailsDtos.size()>0 && update_id.size()>0) {
			//List<ShoppingDetailsDto> offersShoppingDtoClient = new ArrayList<ShoppingDetailsDto>(update_id.size());
			for(String update_id1 : update_id) {
				ShoppingDetailsDto shoppingDetailsDto = new ShoppingDetailsDto();
				shoppingDetailsDto.setUpdate_id(update_id1);
				if(shoppingDetailsDtos.contains(shoppingDetailsDto)) {
					int index = shoppingDetailsDtos.indexOf(shoppingDetailsDto);
					shoppingDetailsDto.setOfferId(shoppingDetailsDtos.get(index).getOfferId());
					shoppingDetailsDtos.add(index,shoppingDetailsDto);
					//LOGGER.info(shoppingDetailsDto.getOfferId()+"");
					//shoppingDetailsDtos.remove(shoppingDetailsDto);
					//shoppingDetailsDtos.add(shoppingDetailsDto);
				}
				//shoppingDetailsDto.setOfferId(offerId);
				//offersShoppingDtoClient.add(shoppingDetailsDto);
			}
			//offersShoppingDtoClient.retainAll(shoppingDetailsDtos);
			//shoppingDetailsDtos.removeAll(offersShoppingDtoClient);
			//shoppingDetailsDtos.addAll(offersShoppingDtoClient);
		}
	}


	@Override
	public OffersDtoV2 offersUpdateIdCheck(OffersDtoV2 offersDtoV2s,List<String> update_id) {
		//LOGGER.info("[offers] offersDtoV2s={},update_id={}",offersDtoV2s.getUpdate_id(),update_id);
		if(offersDtoV2s!=null && update_id!=null) {
			String update_id1 =  offersDtoV2s.getUpdate_id();
			Long offerId = offersDtoV2s.getOfferId();
			if(update_id.contains(offersDtoV2s.getUpdate_id())) {
				//OffersDtoV2 offersDtoV2 = new OffersDtoV2();
				offersDtoV2s = new OffersDtoV2();
				offersDtoV2s.setUpdate_id(update_id1);
				offersDtoV2s.setOfferId(offerId);
			}
		}
		return offersDtoV2s;

	}

	@Override
	public BreakingAlertDto breakingAlertUpdateIdCheck(BreakingAlertDto breakingAlertDto,String update_id) {
		if(breakingAlertDto != null && update_id != null) {
			String update_id1 =  breakingAlertDto.getUpdate_id();
			Long id = breakingAlertDto.getId();
			if(breakingAlertDto.getUpdate_id().equals(update_id)) {
				breakingAlertDto = new BreakingAlertDto();
				breakingAlertDto.setUpdate_id(update_id1);
				breakingAlertDto.setId(id);
			}
		}
		return breakingAlertDto;
	}

	@Override
	public PopUpAlertDto popUpAlertUpdateIdCheck(PopUpAlertDto popUpAlertDto,String update_id) {
		if(popUpAlertDto != null && update_id != null) {
			String update_id1 =  popUpAlertDto.getUpdate_id();
			Long id = popUpAlertDto.getId();
			if(popUpAlertDto.getUpdate_id().equals(update_id)) {
				popUpAlertDto = new PopUpAlertDto();
				popUpAlertDto.setUpdate_id(update_id1);
				popUpAlertDto.setId(id);
			}
		}
		return popUpAlertDto;
	}

}