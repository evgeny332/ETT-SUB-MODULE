package com.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import scala.actors.threadpool.Arrays;

import com.domain.entity.InstalledApps;
import com.domain.entity.OfferDetailsNew;
import com.domain.entity.TodaysOffersNew;
import com.domain.entity.TransactionTracker;
import com.domain.entity.User;
import com.domain.entity.UserProfile;
import com.repository.jpa.InstalledAppsRepository;
import com.repository.jpa.OfferDetailsNewRepository;
import com.repository.jpa.TodaysOffersNewRepository;
import com.repository.jpa.TransactionTrackerRepository;
import com.repository.jpa.UserProfileRepository;
import com.service.EttApis;
import com.service.OffersServiceNew;
import com.service.RechargeService;


@Service
public class OffersNewServiceImpl implements OffersServiceNew {
	
	private static Logger LOGGER = LoggerFactory.getLogger(OffersNewServiceImpl.class);

	@Resource
	private OfferDetailsNewRepository offerDetailsNewRepository;
	
	@Resource
	private TransactionTrackerRepository transactionTrackerRepository;
	
	@Resource
	private InstalledAppsRepository installedAppsRepository;
	
	@Resource 
	private TodaysOffersNewRepository todaysOffersNewRepository;
	
	@Resource
	private UserProfileRepository userProfileRepository;
	
	@Resource
	private RechargeService rechargeService;
	
	@Resource
	private EttApis ettApisService;
	
	
	private int TODAYS_OFFERS_MAX_SIZE;
	
	
	public OffersNewServiceImpl(){
		
	}
	
	@PostConstruct
	public void init(){
		TODAYS_OFFERS_MAX_SIZE = Integer.valueOf(rechargeService.getAppConfig().get("TODAYS_OFFERS_MAX_SIZE"));
	}
	
	
	@Override
	public List<OfferDetailsNew> getOffers(User user, boolean isCategorize,String networkType) {
		List<OfferDetailsNew> offerDetailsNew = new ArrayList<>(20);
		//get todays offers	
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int timezone = user.getTimeZoneInSeconds();
		if(timezone == 0)
			timezone = 19800;
		 Date date = DateTime.now(DateTimeZone.forOffsetMillis(timezone*1000)).toDateMidnight().toDate();
	     //String today = df.format(date);
		
		List<TodaysOffersNew> todaysOffersNew = todaysOffersNewRepository.findByEttIdAndCreatedTimeGreaterThanAndOfferDetailsNew_Status(user.getEttId(), date, true);
		if(todaysOffersNew.size()>0){
			List<String> transactionTrackerIds = new ArrayList<>(offerDetailsNew.size());
			for(TodaysOffersNew todaysOffer : todaysOffersNew){
				offerDetailsNew.add(todaysOffer.getOfferDetails());
				transactionTrackerIds.add(user.getEttId()+"_"+todaysOffer.getOfferDetails().getOfferId());
			}
			LOGGER.info("todays offers for ettId={}, offerSize= {} before substraction",user.getEttId(), offerDetailsNew.size());

			//find from transaction tracker
			List<TransactionTracker> transactionTrackers = transactionTrackerRepository.findByIdIn(transactionTrackerIds);

			List<OfferDetailsNew> offersToDel = new ArrayList<>(offerDetailsNew.size());
			for(OfferDetailsNew offerDetail : offerDetailsNew){
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

			offerDetailsNew.removeAll(offersToDel);
			freshUserBonusOffer(offerDetailsNew,user);
			LOGGER.info("todays offers for ettId={}, offerSize= {} after substraction",user.getEttId(), offerDetailsNew.size());	
			getOfferFilter(offerDetailsNew,networkType);
		 	return offerDetailsNew;
		}

	    offerDetailsNew = getAllOffers(user, isCategorize);
	    
	    if (offerDetailsNew.size() == 0) {
			LOGGER.warn("no offers for today ettId="+user.getEttId());
			return Collections.emptyList();
		}
	    
	      
	    if(offerDetailsNew.size() > TODAYS_OFFERS_MAX_SIZE){
	    	List<OfferDetailsNew> nonIncentiveOffers = new ArrayList<>(30);
	    	for(int i=TODAYS_OFFERS_MAX_SIZE; i< offerDetailsNew.size(); i++){
	    		OfferDetailsNew offerDetail = offerDetailsNew.get(i);
	    		if(offerDetail.getOfferAmount() <= 1){
	    			nonIncentiveOffers.add(offerDetail);
	    		}
	    	}
	    	
	    	offerDetailsNew = offerDetailsNew.subList(0, TODAYS_OFFERS_MAX_SIZE);
	    	
	    	if(nonIncentiveOffers.size() > 0)
	    		offerDetailsNew.addAll(nonIncentiveOffers);
	    }
	    
	    
	    List<Long> offerIdList = new ArrayList<>();
		for(OfferDetailsNew offerDetail : offerDetailsNew){
			offerIdList.add(offerDetail.getOfferId());
		}		
	    saveTodaysOffers(user.getEttId(), offerIdList);  
	    freshUserBonusOffer(offerDetailsNew,user);
	    offerDetailsNew = getOfferFilter(offerDetailsNew,networkType);
	    return offerDetailsNew;
	}

public List<OfferDetailsNew> getOfferFilter(List<OfferDetailsNew> offerDetails,String networkType) {
		
		if(rechargeService.getAppConfig().get("TODAYS_OFFERS_MAX_SIZE_WIFI").equals("true") && networkType.equalsIgnoreCase("WIFI")){
	    	//same as it is  
	      }
	      else{
		    if(offerDetails.size() > TODAYS_OFFERS_MAX_SIZE){
		    	List<OfferDetailsNew> nonIncentiveOffers = new ArrayList<>(30);
		    	for(int i=TODAYS_OFFERS_MAX_SIZE; i< offerDetails.size(); i++){
		    		OfferDetailsNew offerDetail = offerDetails.get(i);
		    		if(offerDetail.getOfferAmount() <= 1){
		    			nonIncentiveOffers.add(offerDetail);
		    			offerDetails.remove(i);
		    		}
		    	}
		    	
		    	offerDetails = offerDetails.subList(0, TODAYS_OFFERS_MAX_SIZE);
		    	
		    	if(nonIncentiveOffers.size() > 0)
		    		offerDetails.addAll(nonIncentiveOffers);
		    }
	    
	      }
		return offerDetails;
	}
	private void saveTodaysOffers(Long ettId,  List<Long> offerIdList) {
		
		List<OfferDetailsNew> offersToSave = offerDetailsNewRepository.findByOfferIdIn(offerIdList);
		
		 List<TodaysOffersNew> todaysOffersToSave = new ArrayList<>(20);
		    for(OfferDetailsNew offerDetail : offersToSave){
		    	TodaysOffersNew todaysOffer = new TodaysOffersNew();
		    	todaysOffer.setEttId(ettId);
		    	todaysOffer.setOfferDetails(offerDetail);
		    	todaysOffer.setCreatedTime(new Date());
		    	todaysOffer.setUpdatedTime(new Date());
		    	todaysOffersToSave.add(todaysOffer);
		    }
		    LOGGER.info("saving todays offers for ettId={}, offerSize={}", ettId, offerIdList.size());
		    todaysOffersNewRepository.save(todaysOffersToSave);  
		
	}

	private List<OfferDetailsNew> getAllOffers(User user, boolean isCategorize) {
		//get all offers
		List<OfferDetailsNew> offerDetailsNewList = offerDetailsNewRepository.findByStatus(true);
		
		//get category offers
		
		if(user.getCategory()!=null && !user.getCategory().equals("")){
			List<String> cat = Arrays.asList(user.getCategory().split(","));
	    	List<OfferDetailsNew> categoryOffers = offerDetailsNewRepository.findByStatusAndCategory(true, cat);
	    	
	    	if(categoryOffers.size()>0)
	    		offerDetailsNewList.addAll(0, categoryOffers);
		}
		
		List<String> appKeyIds = new ArrayList<>(offerDetailsNewList.size());
		List<String> transactionTrackerIds = new ArrayList<>(offerDetailsNewList.size());
		
		/***************************Check from UserProfile ****************************************************/
		UserProfile userProfile = userProfileRepository.findByEttId(user.getEttId());
		if(userProfile == null) {
			userProfile = new UserProfile();
		}
			if(userProfile.getGender()==null || userProfile.getGender().length()==0){
				userProfile.setGender("UNKNOWN");
			}
			if(userProfile.getOperator()==null || userProfile.getOperator().length()==0){
				userProfile.setOperator("UNKNOWN");
			}
		
		/***************************Check from UserProfile ****************************************************/
		List<String> operatorList = Arrays.asList(userProfile.getOperatorCircle().split(","));
		List<OfferDetailsNew> offerDetailsFiler = new ArrayList<OfferDetailsNew>(offerDetailsNewList.size());
		for(OfferDetailsNew offerDetail : offerDetailsNewList){	
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
		offerDetailsNewList = offerDetailsFiler;
		LOGGER.info("final list of offers={}",offerDetailsNewList);
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
		
		List<OfferDetailsNew> offersToDel = new ArrayList<>(offerDetailsNewList.size());
		for(OfferDetailsNew offerDetail : offerDetailsNewList){
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

		offerDetailsNewList.removeAll(offersToDel);

		return offerDetailsNewList;
	}
	
	public boolean checkOfferProfiling(Long ettId,OfferDetailsNew offerDetail,String userGender,String userOperatorCircle,String userOperator,String userDOB){
		
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
		if(!offerDetail.getGender().equals("All")) {
			if(!offerDetail.getGender().equals(userGender)) {
				LOGGER.info("Offer not for this user ettId={},userGender={}offerGender={}",ettId,userGender,offerDetail.getGender());
				return false;
			}
		}
		
		if(!offerDetailoperatorCircleList.contains("All")){
			if(!offerDetailoperatorCircleList.contains(userOperatorCircle)){
				LOGGER.info("Offer not for this user ettId={},userGender={},userOperatorCircle={},offerGender={}",ettId,userGender,userOperatorCircle,offerDetail.getGender());
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
			}catch(Exception ex) {return false;}
		}
		return true;
	
	}

	public void freshUserBonusOffer(List<OfferDetailsNew> offerDetails,User user){
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
			DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			int nowTimeWithZeroTime = Integer.parseInt(formatter.format(nowTime));
			int userCTWithZeroTime = Integer.parseInt(formatter.format(userCT));
			sameDay = nowTimeWithZeroTime-userCTWithZeroTime;
		}catch(Exception ex1){
			ex1.printStackTrace();
			LOGGER.error("Error in freshUserBonusOffer"+ex1);
		}
		
		if(rechargeService.getAppConfig().get("NEW_USER_BONUS_ON_FIRST_APP").equals("true")){
			if(user.getTotlNoOfDLoadApp()<=0) {
				OfferDetailsNew bonusOffer =  new OfferDetailsNew();
		 		bonusOffer.setOfferId(320l);
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
	 				else {}
	 			}
		 		if(rechargeService.getAppConfig().get("NEW_USER_BONUS_ON_FIRST_APP_REDIRECT_URL").equals("true")){
		 			OfferDetailsNew of = new OfferDetailsNew();
		 			of.setOfferId(374l);
		 			index = offerDetails.indexOf(of);
		 			OfferDetailsNew of1 = offerDetails.get(index);

		 			OfferDetailsNew offD = offerDetails.get(2);
		 			of1.setActionUrl(offD.getActionUrl());
		 			of1.setAppKey(offD.getAppKey());
		 			of1.setOfferId(offD.getOfferId());
		 			offerDetails.set(index, of1);
		 			
		 			
		 		}
		 		//return offerDetails;
			}
			else{
				OfferDetailsNew bonusOffer =  new OfferDetailsNew();
		 		bonusOffer.setOfferId(374l);
		 		offerDetails.remove(bonusOffer);
		 		
		 		//return offerDetails;
			}
			
	 	}
	 	else{
	 		OfferDetailsNew bonusOffer =  new OfferDetailsNew();
	 		bonusOffer.setOfferId(374l);
	 		offerDetails.remove(bonusOffer);
	 		//return offerDetails;
	 	}
		//return offerDetails;
		
	}
}