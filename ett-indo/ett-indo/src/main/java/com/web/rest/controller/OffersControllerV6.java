package com.web.rest.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.hibernate.validator.internal.xml.PayloadType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.entity.Astrology;
import com.domain.entity.BreakingAlert;
import com.domain.entity.DataUsagePendingCredits;
import com.domain.entity.DeviceToken;
import com.domain.entity.DynamicTabDetails;
import com.domain.entity.EttGiftPending;
import com.domain.entity.InviteBonusMsisdn;
import com.domain.entity.LocaleTextTemplate.Locale;
import com.domain.entity.OfferDetails;
import com.domain.entity.OfferDetails.BalanceType;
import com.domain.entity.OfferDetails.PayoutType;
import com.domain.entity.OffersStarted;
import com.domain.entity.PopUpAlert;
import com.domain.entity.PopUpSheduled;
import com.domain.entity.SchedulePush;
import com.domain.entity.ShoppingDetails;
import com.domain.entity.User;
import com.domain.entity.UserAccount;
import com.domain.entity.UserAccountSummary;
import com.domain.entity.UserClickCallBackTracker;
import com.domain.entity.UserNoOffersAvailable;
import com.domain.entity.UserSource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.repository.jpa.AstrologyRepository;
import com.repository.jpa.BreakingAlerDailyCheckRepository;
import com.repository.jpa.BreakingAlertRepository;
import com.repository.jpa.DataUsagePendingCreditsRepository;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.DynamicTabDetailsRepository;
import com.repository.jpa.EttGiftPendingRepository;
import com.repository.jpa.FirstHitDayRepository;
import com.repository.jpa.InviteBonusMsisdnRepository;
import com.repository.jpa.OfferDetailsRepository;
import com.repository.jpa.OffersStartedRepository;
import com.repository.jpa.PopUpAlertRepository;
import com.repository.jpa.PopUpSheduledRepository;
import com.repository.jpa.RedeemThresholdRepository;
import com.repository.jpa.SchedulePushRepository;
import com.repository.jpa.ShoppingDetailsRepository;
import com.repository.jpa.UserAccountRepository;
import com.repository.jpa.UserAccountSummaryRepository;
import com.repository.jpa.UserBlackListRepository;
import com.repository.jpa.UserClickCallBackTrackerRepository;
import com.repository.jpa.UserNoOffersAvailableRepository;
import com.repository.jpa.UserRepository;
import com.repository.jpa.UserSourceRepository;
import com.service.BreakingAlertService;
import com.service.EttApis;
import com.service.FirstHitService;
import com.service.Msisdn_30Service;
import com.service.OffersService;
import com.service.PopUpAlertService;
import com.service.RechargeService;
import com.service.ReferrerService;
import com.web.rest.dto.BreakingAlertDto;
import com.web.rest.dto.DynamicTabDetailsDto;
import com.web.rest.dto.OfferInstructionDto;
import com.web.rest.dto.OffersDtoV2;
import com.web.rest.dto.OffersDtoV4;
import com.web.rest.dto.OffersDtoV4Compress;
import com.web.rest.dto.OffersStartedDto;
import com.web.rest.dto.PopUpAlertDto;
import com.web.rest.dto.ShoppingDetailsDto;


/**
 * @author ankur
 */
@Controller
@RequestMapping(value = "/v6")
public class OffersControllerV6 {

    private static Logger LOGGER = LoggerFactory.getLogger(OffersControllerV6.class);

    @Inject 
    OffersService offersService;

    @Resource
    UserRepository userRepository;

    @Resource
    UserBlackListRepository userBlackListRepository;

    @Resource
    InviteBonusMsisdnRepository inviteBonusMsisdnRepository;
    
    @Resource
    EttApis ettApis;
    
    @Resource
    ShoppingDetailsRepository shoppingDetailsRepository;
    
    @Resource
    RechargeService rechargeService;

    @Resource
    OfferDetailsRepository offerDetailsRepository;
    
    @Resource
    UserClickCallBackTrackerRepository userClickCallBackTrackerRepository;
    
    @Resource
    DeviceTokenRepository deviceTokenRepository;
    
    @Resource
	SchedulePushRepository schedulePushRepository;

    @Resource
    EttGiftPendingRepository ettGiftPendingRepository;
    
    @Resource
    AstrologyRepository astrologyRepository;
    
    @Resource
    private ReferrerService referrerService;

    @Resource
    private PopUpAlertService popUpAlertService;
    
    @Resource
    BreakingAlertService breakingAlertService;
    @Autowired 
    RedeemThresholdRepository redeemThresholdRepository;
    
    @Resource
    private UserAccountRepository userAccountRepository;
    
    @Resource
    private UserAccountSummaryRepository userAccountSummaryRepository;
    @Resource
    private BreakingAlertRepository breakingAlertRepository;

    @Resource
    private PopUpSheduledRepository popUpSheduledRepository;
    
    @Resource
    private PopUpAlertRepository popUpAlertRepository;
    
    @Resource
	private FirstHitDayRepository firstHitDayRepository;
    
    @Resource
    private BreakingAlerDailyCheckRepository breakingAlerDailyCheckRepository;
    
    @Resource
    DataUsagePendingCreditsRepository dataUsagePendingCreditsRepository;
    
    @Resource
    DynamicTabDetailsRepository dynamicTabDetailsRepository;
    
    @Resource
    private UserSourceRepository userSourceRepository;
    @Resource
    private FirstHitService firstHitDayService;
 
    @Resource
    OffersStartedRepository offersStartedRepository;

    @Resource
	private Msisdn_30Service msisdn_30Service;

    @Resource
    UserNoOffersAvailableRepository userNoOffersAvailableRepository;
    
    @RequestMapping(value = "user/offerStarted", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getOfferStarted(@RequestParam("ettId") long ettId,
    										 @RequestParam("otp") long otp,
    										 @RequestParam(value="limit", required=false, defaultValue="30") int limit,
    										 @RequestParam(value="update_id", required=false) List<String> update_id,
    										 @RequestParam(value="isCompressed", required=false, defaultValue="true") boolean isCompressed
	) {
    	LOGGER.info("user/offerStarted/ ettId={},otp={},limit={}",ettId,otp,limit);
    	if(ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
    	User user = userRepository.findByEttId(ettId);
    	if(user == null)
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    		if(user.getOtp()!=0 && user.getOtp()!=otp) {
    			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
    			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    		}
    		
    		List<OffersStarted> offerStarted = offersStartedRepository.findByEttId(ettId);
    		List<DataUsagePendingCredits> dataUsagePendingCredits = dataUsagePendingCreditsRepository.findByEttId(ettId);
    		if((offerStarted==null || offerStarted.size()==0) && (dataUsagePendingCredits==null || dataUsagePendingCredits.size()==0)){
    			return new ResponseEntity<>(HttpStatus.OK);
    		}
    		
    		List <OffersStartedDto> offerStartedDto = new ArrayList<OffersStartedDto>(offerStarted.size());
    		
    		
    		for(OffersStarted offersStart : offerStarted){
    			//done by sumeet sir on 25-08-2015
    			if(offersStart.getPayOutType().equals("INSTALL") && (offersStart.getOfferLifeCycle().indexOf("Use")>=0 || offersStart.getOfferLifeCycle().indexOf("Open")>=0)) {
    				LOGGER.info("Offer payout is INSTALL and the offerLifeCycle contains Use or Open for not showing in offersStarted ettId={},offerId={}",user.getEttId(),offersStart.getOfferId());
    				continue;
    			}
    			if(offersStart.getPayOutType().equals("INSTALL") && ((new Date().getTime())-offersStart.getInstalledTime().getTime())>10800000l) {
    				LOGGER.info("Offer PayoutType is INSTALL and the time is more the 3 hour so not showing in offerstarted ettId={},offerId={}",user.getEttId(),offersStart.getOfferId());
    				continue;
    			}
    			OffersStartedDto offersStartDto = new OffersStartedDto();
    			offersStartDto.setId(offersStart.getId());
    			offersStartDto.setOfferCategory(offersStart.getOfferCategory());
    			offersStartDto.setImageUrl(offersStart.getImageUrl());
    			offersStartDto.setOfferName(offersStart.getOfferName());
    			offersStartDto.setPayoutType(offersStart.getPayOutType());
    			if(Float.parseFloat(user.getAppVersion())>1.5f){
    				offersStartDto.setPackageName(offersStart.getPackageName());
    			}
    			if(offersStartDto.getPayoutType().equals("DEFFERED") || offersStartDto.getPayoutType().equals("DATAUSAGE")) {
    				java.util.Date dateNow = new java.util.Date();
					
    				try {
    					DataUsagePendingCredits dataCredits = new DataUsagePendingCredits();
						dataCredits.setOfferId(offersStart.getOfferId());
						int dataIndex = dataUsagePendingCredits.indexOf(dataCredits);
						float remainnigAmount = 0.0f;
						String dataDeferedInfo ="";
						if(dataIndex>=0) {
							//Instructions of data usage defered info
							float earnedAmount = 0.0f;
							int consumedData = 0;
							dataCredits = dataUsagePendingCredits.get(dataIndex);
							List<UserAccountSummary> accSummary = userAccountSummaryRepository.findByEttIdAndOfferId(user.getEttId(), offersStart.getOfferId());
							for(UserAccountSummary accountSumm : accSummary){
								if(accountSumm.getRemarks().equalsIgnoreCase("DATA_USAGE")){
									earnedAmount = earnedAmount + accountSumm.getAmount();
									consumedData = consumedData + dataCredits.getDataThreshold();
								}
							}
							remainnigAmount = dataCredits.getMaxCreditLimit() - earnedAmount;
							if(earnedAmount>0 && earnedAmount<dataCredits.getMaxCreditLimit()){
								dataDeferedInfo = rechargeService.getLocaleTextTemplate().get("DATA_USAGE_DEFERED_INFO_"+user.getLocale()).replaceFirst("#AMOUNT#", String.valueOf(earnedAmount))
										.replaceFirst("#DATA#", String.valueOf(consumedData / 1024)).replaceFirst("#MORE#", String.valueOf(remainnigAmount));
							}
						}
						if(remainnigAmount > 0 && !dataDeferedInfo.equals("")){
							offersStartDto.setUserDeferedInfo(dataDeferedInfo);
						} else {
							offersStartDto.setUserDeferedInfo(offersStart.getUserDeferedInfo().replace("20B9",(char) Integer.parseInt( "20B9", 16 )+""));
						}
						
    					offersStartDto.setDeferedPaymentFinalInfor(offersStart.getDeferedPaymentFinalInfor().replace("20B9",(char) Integer.parseInt( "20B9", 16 )+""));
    				}catch(Exception ex){}
    				offersStart.setInstructions(offersStart.getInstructions().replace("20B9",(char) Integer.parseInt( "20B9", 16 )+""));
    				if(offersStart.getInstructions() !=null) {
    					try {
    						String instruction[] = offersStart.getInstructions().split(";");
    						String instructionText [] = new String[instruction.length];
    						String instStringRate [] = new String[instruction.length];
    						int ii =0;
    						List<OfferInstructionDto> OfferInstructionDtolist = new ArrayList<OfferInstructionDto>(instruction.length);
    						for(String inst:instruction){
    							OfferInstructionDto offerInstructionDto = new OfferInstructionDto();
    							offerInstructionDto.setInstructionsText(inst.split("\\$")[0]);
    							offerInstructionDto.setInstStringAmount(inst.split("\\$")[1]);
    							
    							if(ii==00){
    								offerInstructionDto.setStatus(1);
    							}
    							else {
    								offerInstructionDto.setStatus(0);
    							}
    							ii++;
    							OfferInstructionDtolist.add(offerInstructionDto);
    						}
    						//check the expiry 	time here also
    						DataUsagePendingCredits credits = new DataUsagePendingCredits();
    						credits.setOfferId(offersStart.getOfferId());
    						int index = dataUsagePendingCredits.indexOf(credits);
    						if(index>=0) {
    							credits=dataUsagePendingCredits.get(index);
    							dateNow = new java.util.Date();
    							
    							if(credits.getPayoutEndDate().after(dateNow)) {
		    							OfferInstructionDto offerInstructionDto = new OfferInstructionDto();
		    							offerInstructionDto.setInstructionsText(rechargeService.getLocaleTextTemplate().get("DATA_USAGE_INSTRUCTION_"+user.getLocale()).replace("#amountPerDataThreshold#", ((int)(float)credits.getAmountPerDataThreshold())+"").replace("#maxDataUsageLimit#", ((int)((credits.getDataThreshold()/1024)*(credits.getMaxCreditLimit())))+"").replace("#dataThreshold#", (credits.getDataThreshold()/1024)+"").replace("#maxCreditPerDayLimit#", credits.getMaxCreditPerDayLimit()+"").replace("#maxCreditLimit#", credits.getMaxCreditLimit()+""));
		    							offerInstructionDto.setInstStringAmount(credits.getMaxCreditLimit()+"");
		    							offerInstructionDto.setStatus(0);
		    							OfferInstructionDtolist.add(offerInstructionDto);
		    							dataUsagePendingCredits.remove(credits);
		    							
    							}
    						}
    						offersStartDto.setOfferInstructionDto(OfferInstructionDtolist);
    					}
    					catch(Exception exx1){
    						LOGGER.error("[error in instruction configuraion ettId]["+offersStart.getEttId()+"]");
    					}
    				}
    			}
    			else if(offersStartDto.equals("INSTALL")) {
    				offersStartDto.setOfferLifeCycle(offersStart.getOfferLifeCycle());
    				offersStartDto.setApproveInfoText(offersStart.getApproveInfoText());
    				offersStartDto.setCriticalInfo(offersStart.getCriticalInfo());
    				offersStartDto.setEarnInfo(offersStart.getEarnInfo());
    			}
    			else{
    				offersStartDto.setDeferedPaymentFinalInfor(offersStart.getDeferedPaymentFinalInfor());
    				offersStartDto.setOfferLifeCycle(offersStart.getOfferLifeCycle());
    				offersStartDto.setApproveInfoText(offersStart.getApproveInfoText());
    				offersStartDto.setCriticalInfo(offersStart.getCriticalInfo());
    				offersStartDto.setEarnInfo(offersStart.getEarnInfo());
    				
    			}
    			offersStartDto.setUpdate_id(offersStart.getId()+"_"+offersStart.getUpdate_triger_id());
    			offerStartedDto.add(offersStartDto);
    		}
    		
    		
    		//show only data offers which are not in offersStarted
    		if(rechargeService.getLocaleTextTemplate().get("ONGOING_DATA_SAPARATE_OFFER_"+user.getLocale()).equals("true") && dataUsagePendingCredits!=null && dataUsagePendingCredits.size()>0) {
    			for(DataUsagePendingCredits credits:dataUsagePendingCredits) {
    				java.util.Date dateNow = new java.util.Date();
					if(credits.getPayoutEndDate().after(dateNow)) {
	    					OffersStartedDto offersStartDto = new OffersStartedDto();
			    			offersStartDto.setId(credits.getId()+"");
			    			offersStartDto.setOfferId(credits.getOfferId());
			    			offersStartDto.setImageUrl(credits.getImageUrl());
			    			offersStartDto.setOfferName(credits.getOfferName());
			    			offersStartDto.setPackageName(credits.getPackageName());
			    			//offersStartDto.setPayoutType("DATAUSAGE");
			    			offersStartDto.setPayoutType("DEFFERED");
			    			offersStartDto.setDeferedPaymentFinalInfor("");
			    			offersStartDto.setOfferCategory("use & earn");
			    			offersStartDto.setUserDeferedInfo("");
			    			
			    			offerStartedDto.add(offersStartDto);
			    			OfferInstructionDto offerInstructionDto = new OfferInstructionDto();
							offerInstructionDto.setInstructionsText(rechargeService.getLocaleTextTemplate().get("DATA_USAGE_INSTRUCTION_"+user.getLocale()).replace("#amountPerDataThreshold#", ((int)(float)credits.getAmountPerDataThreshold())+"").replace("#maxDataUsageLimit#", ((int)((credits.getDataThreshold()/1024)*(credits.getMaxCreditLimit())))+"").replace("#dataThreshold#", (credits.getDataThreshold()/1024)+"").replace("#maxCreditPerDayLimit#", credits.getMaxCreditPerDayLimit()+"").replace("#maxCreditLimit#", credits.getMaxCreditLimit()+""));
							offerInstructionDto.setInstStringAmount(credits.getMaxCreditLimit()+"");
							offerInstructionDto.setStatus(0);
							List<OfferInstructionDto> OfferInstructionDtolist = new ArrayList<OfferInstructionDto>(1);
							offersStartDto.setOfferInstructionDto(OfferInstructionDtolist);
							OfferInstructionDtolist.add(offerInstructionDto);		
					}
    			}
    		}
    		
    		
    		//checking here that data is availabe at client end or not
    		offersService.offerStartedDtoUpdateIdCheck(offerStartedDto, update_id);
    		
    		try {
    	    	if(rechargeService.getAppConfig().get("IsOfferDataCompress").equals("true") && isCompressed == true) {
    		    	Gson gson = new Gson();
    		    	String json = gson.toJson(offerStartedDto);
    		    	byte [] compressed = offersService.compress(json);
    		    	OffersDtoV4Compress offersDtoV4Compress = new OffersDtoV4Compress();
    		    	offersDtoV4Compress.setIsCompress(true);
    		    	offersDtoV4Compress.setOfferDetails(compressed);
    		    	return new ResponseEntity<>(offersDtoV4Compress, HttpStatus.OK);
    	    	}
    	    	else{
    	    		return new ResponseEntity<>(offerStartedDto,HttpStatus.OK);
    	    	}
        	}catch(Exception ex){
        		return new ResponseEntity<>(offerStartedDto,HttpStatus.OK);
        	}
    	}


    @RequestMapping(value = "user/singleOffer/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getOfferDetails(@RequestParam("ettId") long ettId,
    											@RequestParam("otp") int otp,
    											@RequestParam("offerId") long offerId,
    											@RequestParam(value="update_id", required=false) List<String> update_id,
    											@RequestParam(value = "networkType", required=false, defaultValue="UNKNOWN") String networkType) {
    		LOGGER.info("user/offersDetails/ ettId={},otp={},offerId={},networkType{}", ettId, otp, offerId,networkType);
    		if(ettApis.getBlackListStatus(ettId)) {
    			LOGGER.info("ettId BlackListed {}",ettId);
    			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    		}

        	User user = userRepository.findByEttId(ettId);
        	if(user == null)
        		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        		
    		if(user.getOtp()!=0 && user.getOtp()!=otp) {
    			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
    			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    		}

    		UserAccount userAccount = userAccountRepository.findByEttId(ettId);
        	if(userAccount == null){
        		LOGGER.info("insert in userAccount with 0 ettId = "+ettId);
    			userAccount = new UserAccount();
    			userAccount.setEttId(user.getEttId());
    			userAccount.setCurrentBalance(0);
    			userAccount = userAccountRepository.save(userAccount);
        	}
    		List<Long> offerIdList = new ArrayList<Long>();
        	offerIdList.add(offerId);
        	List<OfferDetails> singleOffer = offerDetailsRepository.findByOfferIdInStatus(offerIdList);
        	if(singleOffer == null) {
        		return new ResponseEntity<>("", HttpStatus.OK);
        	}
        	offersService.freshUserBonusOffer(singleOffer,user);
        	return new ResponseEntity<>(getOfferDtoList(user, singleOffer,false,false,update_id), HttpStatus.OK);
    	}
    
    @RequestMapping(value = "user/offers/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getBalance(@RequestParam("ettId") long ettId,
    									@RequestParam(value = "networkType", required=false, defaultValue="UNKNOWN") String networkType,
    									@RequestParam(value="isLogin", required=false, defaultValue="false") boolean isLogin,
    									@RequestParam("otp") int otp,
    									@RequestParam(value="tempOtp", required=false, defaultValue="0") int tempOtp,
    									@RequestParam(value="deviceId", required=false) String deviceId,
    									@RequestParam(value="version", required=false, defaultValue="0.0") float version,
    									@RequestParam(value="update_id", required=false) List<String> update_id,
    									@RequestParam(value="updateIdBreakingAlert", required=false) String updateIdBreakingAlert,
    									@RequestParam(value="updateIdPopupAlert", required=false) String updateIdPopupAlert,
    									@RequestParam(value="isCompressed", required=false, defaultValue="true") boolean isCompressed,
    									@RequestParam(value = "locale", required = false, defaultValue = "EN") String locale
    ) {
    	LOGGER.info("user/offers/ ettId={},networkType={},isLogin={},otp={},tempOtp={},deviceId={},version={},locale={}", ettId, networkType,isLogin,otp,tempOtp,deviceId,version,locale);
    	if(ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
    	User user = userRepository.findByEttId(ettId);
    	if(user == null)
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    		
		if(user.getOtp()!=0 && user.getOtp()!=otp) {
			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		if(user.getLocale() != Locale.valueOf(locale)) {
			user.setLocale(Locale.valueOf(locale));
			userRepository.save(user);
		}
		UserAccount userAccount = userAccountRepository.findByEttId(ettId);
    
		if(userAccount == null){
    		LOGGER.info("insert in userAccount with 0 ettId = "+ettId);
			userAccount = new UserAccount();
			userAccount.setEttId(user.getEttId());
			userAccount.setCurrentBalance(0);
			userAccount = userAccountRepository.save(userAccount);
    	}
    	if(!user.isVerified() && otp!=0){
		LOGGER.info("verifying ettId="+ettId);
		user.setRegDate(new Date());
		user.setVerified(true);
		user.setUpdatedTime(new Date());
		user.setFirstLogin(true);
		DeviceToken deviceTokenobj = deviceTokenRepository.findByEttId(ettId);
		if(deviceTokenobj == null) {
			deviceTokenobj = new DeviceToken();
			deviceTokenobj.setDeviceId(user.getDeviceId());
			deviceTokenobj.setCreatedTime(new Date());
			deviceTokenobj.setUpdatedTime(new Date());
			deviceTokenobj.setEttId(user.getEttId());
			deviceTokenobj.setActive(false);
			deviceTokenRepository.save(deviceTokenobj);
		}
		else{
			if(deviceTokenobj.isActive()==false) {
				deviceTokenobj.setActive(true);
				deviceTokenRepository.save(deviceTokenobj);
			}
		}
		if(rechargeService.getAppConfig().get("NEW_USER_PUSH_CHECK").equals("true"))
			ettApis.sendPush(rechargeService.getLocaleTextTemplate().get("NEW_USER_PUSH_"+user.getLocale()), deviceTokenobj.getDeviceToken(),user.getEttId());
			if(rechargeService.getAppConfig().get("NEW_USER_BONUS_ON_FIRST_APP").equals("true")){
				SchedulePush schedulePush = new SchedulePush();
				schedulePush.setEttId(user.getEttId());
				UserSource userSource = userSourceRepository.findByEttId(user.getEttId());
				if(rechargeService.getAppConfig().get("DAT_1_2_3_NOT_MEDIA").equals("true") && userSource!=null && userSource.getUtmCampaign()!=null && (!userSource.getUtmCampaign().equals("")) && (!userSource.getUtmCampaign().equals("INVITE"))) {
					schedulePush.setMessage(rechargeService.getLocaleTextTemplate().get("DAT_1_2_3_NOT_MEDIA_FIRST_PUSH_"+user.getLocale()));
				}
				else  {
					schedulePush.setMessage(rechargeService.getLocaleTextTemplate().get("NEW_USER_SECOND_PUSH_"+user.getLocale()));
				}
				java.util.Date datetime = new java.util.Date();
				datetime.setTime(datetime.getTime()+360000);
				schedulePush.setPushTime(datetime);
				schedulePushRepository.save(schedulePush);
				}
			float aver = 0.0f;
			if(version>0.0) {
	    		try {
	    				aver = Float.parseFloat(user.getAppVersion());
	    		}catch(Exception ex1){}
	    		if(aver<version) {
	    			user.setAppVersion(version+"");
	    		}
			}
		   		referrerService.giveInviteCredit(user);
				List<EttGiftPending> ettGiftPending = ettGiftPendingRepository.findByMsisdnInStatus(user.getMsisdn(),false);
				if(ettGiftPending !=null && ettGiftPending.size()>0) {
					for(EttGiftPending ettGiftPending1: ettGiftPending) {
			   			UserAccount userAccount2 = userAccountRepository.findByEttId(ettId);
			   			if(userAccount2==null) {
			   				userAccount2 = new UserAccount();
			   			}
		   			userAccount2.setCurrentBalance(userAccount2.getCurrentBalance()+ettGiftPending1.getAmount());
		   			userAccountRepository.save(userAccount2);
		   			UserAccountSummary userAccountSummary = new UserAccountSummary();
		   			userAccountSummary.setEttId(ettId);
		   			userAccountSummary.setAmount(ettGiftPending1.getAmount());
		   			userAccountSummary.setCreatedTime(new java.util.Date());
		   			userAccountSummary.setOfferId(8883l);
		   			userAccountSummary.setRemarks("GIFT_"+ettGiftPending1.getEttId());
		   			User userSend = userRepository.findByEttId(ettGiftPending1.getEttId());
		   			userAccountSummary.setOfferName("gift from "+userSend.getMsisdn());
		   			userAccountSummaryRepository.save(userAccountSummary);
		   			
		   			SchedulePush schedulePush = new SchedulePush();
					schedulePush.setEttId(user.getEttId());
					schedulePush.setMessage(ettGiftPending1.getMsg());
					java.util.Date datetime = new java.util.Date();
					schedulePush.setPushTime(datetime);
					schedulePushRepository.save(schedulePush);
		   		}
				ettGiftPendingRepository.updateStatus(user.getMsisdn());
		   	}
			/****************** INVITE BONUS ***************************************/
		   	if(rechargeService.getAppConfig().get("INVITE_BONUS").equals("true")){
		   		InviteBonusMsisdn inviteBonusMsisdn = inviteBonusMsisdnRepository.findByMsisdn(user.getMsisdn());
		   		if(inviteBonusMsisdn!=null && inviteBonusMsisdn.getStatus()==0) {
		   			msisdn_30Service.giveInviteMoney(user, inviteBonusMsisdn);
		   		}
		   		
		   	}
		   	/****************** INVITE BONUS ***************************************/
		}
		/*********************************************************************************************************************/
		try {
	    	if(!user.getDeviceId().equals(deviceId)) {
	    		LOGGER.info("Device ID change for ettId in offerController ettId={},currentDeviceId={},DeviceId in DB={}",user.getEttId(),deviceId,user.getDeviceId());
	    	}
    	}catch(Exception ex2) {}
		boolean ispopUpEnable = false;
		List<PopUpSheduled> popUpSheduleds = popUpSheduledRepository.findByEttIdStatus(user.getEttId(),true);
		if(popUpSheduleds!=null && popUpSheduleds.size()>0){
			ispopUpEnable=true;
		}
		boolean isBreakingAlertToday = firstHitDayService.checkBreakingAlertDailyCheck(user);
		if(ispopUpEnable) {
			PopUpSheduled popUpSheduled = popUpSheduleds.get(0);
			popUpSheduled.setPopUpTime(new Date());
			popUpSheduled.setStatus(false);
			popUpSheduledRepository.save(popUpSheduled);
		}
		user.setUpdatedTime(new Date());
   		user = userRepository.save(user);
   		float threshold = ettApis.getThresholdAmont(user);
    	List<OfferDetails> offersList = offersService.getOffers(user, false,networkType);
    	OffersDtoV4 offerDto = getOffersDto(user, offersList,threshold,userAccount,ispopUpEnable,isBreakingAlertToday,update_id,updateIdBreakingAlert,updateIdPopupAlert);
    	offersService.userTargetAmountBase(offerDto,user);
    	try {
	    	if(rechargeService.getAppConfig().get("IsOfferDataCompress").equals("true") && isCompressed == true) {
		    	//Gson gson = new GsonBuilder().serializeNulls().create();
	    		Gson gson = new Gson();
		    	String json = gson.toJson(offerDto);
		    	byte [] compressed = offersService.compress(json);
		    	OffersDtoV4Compress offersDtoV4Compress = new OffersDtoV4Compress();
		    	offersDtoV4Compress.setIsCompress(true);
		    	offersDtoV4Compress.setOfferDetails(compressed);
		    	return new ResponseEntity<>(offersDtoV4Compress, HttpStatus.OK);
	    	}
	    	else{
	    		ResponseEntity entity = new ResponseEntity<>(offerDto, HttpStatus.OK);
	    		return new ResponseEntity<>(offerDto, HttpStatus.OK);
	    	}
    	}catch(Exception ex){
    		return new ResponseEntity<>(offerDto, HttpStatus.OK);
    	}
    }

    private OffersDtoV4 getOffersDto(User user, List<OfferDetails> offersList, float threshold,UserAccount userAccount,boolean isTodayFirst,boolean isBreakingAlertToday,List<String> update_id,String updateIdBreakingAlert,String updateIdPopupAlert) {
		OffersDtoV4 offersDtoV4 = new OffersDtoV4();
		if(userAccount.getCurrentBalance()>=threshold)
			offersDtoV4.setRedeem(true);
		
		offersDtoV4.setBalance(userAccount.getCurrentBalance());
		offersDtoV4.setThreshold(threshold);
		String btext = rechargeService.getLocaleTextTemplate().get("BALANCE_TEXT_"+user.getLocale()).replaceFirst("#BALANCE#", userAccount.getCurrentBalance()+"");
		offersDtoV4.setBalanceText(btext);
		offersDtoV4.setBestOffersText(rechargeService.getLocaleTextTemplate().get("BEST_OFFERS_TEXT_"+user.getLocale()));
		offersDtoV4.setInviteFriendText(rechargeService.getLocaleTextTemplate().get("INVITE_TEXT_"+user.getLocale()));
		String con = rechargeService.getLocaleTextTemplate().get("INVITE_MESSAGE_"+user.getLocale()).replaceFirst("#ETT_ID#", userAccount.getEttId()+"");
		con = con.replaceFirst("#DATE#", System.currentTimeMillis()+"");
		offersDtoV4.setInviteFriendUrlText(con);
		offersDtoV4.setAppVersion(rechargeService.getAppConfig().get("CURRENT_ANDROID_VERSION"));
		offersDtoV4.setAppVersionUpdate(rechargeService.getAppVersionUpdate().get(user.getAppVersion()));
		offersDtoV4.setUpdateText(rechargeService.getLocaleTextTemplate().get("UPDATE_TEXT_"+user.getLocale()));
		if(rechargeService.getAppConfig().get("ADD_ON").equals(true))
		{
			offersDtoV4.setAddOn(true);
		}
		else{
			offersDtoV4.setAddOn(false);
		}
		
		if(user.isFlage()){
			offersDtoV4.setNotRegister(true);
		}
		else {
			offersDtoV4.setNotRegister(false);
		}
		if(rechargeService.getIsDeviceSupport().get(user.getIsDeviceSupport()).equals("T")){
			offersDtoV4.setDeviceNotSupport(false);
		}
		else {
			LOGGER.info("USER BLOCK as the Device Support Parameter is false ettId={}",user.getEttId());
			offersDtoV4.setDeviceNotSupport(true);
		}
		try{
			offersDtoV4.setOfferRefeshTimer(Integer.parseInt(rechargeService.getAppConfig().get("OFFER_REFERESH_TIMER")));
		}catch(Exception ex){}
		offersDtoV4.setInviteTabText(rechargeService.getLocaleTextTemplate().get("INVITE_TAB_TEXT_"+user.getLocale()));
		List<OffersDtoV2> offersDtoList = new ArrayList<>(offersList.size());
		offersDtoList = getOfferDtoList(user,offersList,true,false,update_id);
		offersDtoV4.setOffersDto(offersDtoList);
		boolean isBreakingAlert = false;
		if(rechargeService.getAppConfig().get("IFBREAKINGALERTONCE_DAY").equals("true"))
		{
			if(isBreakingAlertToday) {
				isBreakingAlert = true;
			}
		}
		else {
			isBreakingAlert = false;
		}
		if(isBreakingAlert)
		{
			BreakingAlertDto breakingAlertDto = null;
			int breakingAlertIndex=-1;
			List<BreakingAlert> breakingAlert1;
			OffersDtoV2 D123 = new OffersDtoV2();
			D123.setOfferId(374l);
			int checkD123 = offersDtoList.indexOf(D123);
			if(checkD123>=0)
			{
				D123= offersDtoList.get(checkD123);
				breakingAlert1 = breakingAlertRepository.findById(true,3l);
				breakingAlert1.get(0).setText(D123.getDescription());
				breakingAlert1.get(0).setActionUrl(D123.getActionUrl());
				offersDtoList.remove(D123);
			}
			else{
				if(rechargeService.getAppConfig().get("AppCounterPromoFlage").equals("true")) {
					if(ettApis.checkSameDay(user.getRegDate(),19800,19800)){
						breakingAlert1 = breakingAlertRepository.findById(false,11l);
					}
					else {
						breakingAlert1 = breakingAlertRepository.findById(true,1l);
					}
	    		}
				else {
					breakingAlert1 = breakingAlertRepository.findById(true,1l);
				}
				if(breakingAlert1 !=null && breakingAlert1.size()>0) {
						int ind=0;
						for(BreakingAlert alert:breakingAlert1) {
							if(alert.getVersion() !=null && alert.getVersion().equalsIgnoreCase("All")) {
								breakingAlertIndex=ind;
							}
							else if(user.getAppVersion().indexOf(alert.getVersion())>-1) {
								breakingAlertIndex=ind;
							}
							ind++;
						}
					}
				}
			if(breakingAlertIndex>=0) {
						breakingAlertDto = new BreakingAlertDto();
				BreakingAlert breakingAlert = breakingAlert1.get(breakingAlertIndex);
				breakingAlertDto = breakingAlertService.getBreakingAlertDto(breakingAlert,user);
			}
			breakingAlertDto = offersService.breakingAlertUpdateIdCheck(breakingAlertDto,updateIdBreakingAlert);
			offersDtoV4.setBreakingAlertDto(breakingAlertDto);
		}
		if(isTodayFirst){
			PopUpAlertDto popUpAlertDto = new PopUpAlertDto();
			PopUpAlert popUpAlert = popUpAlertRepository.findById(1l,true);
			if(popUpAlert != null) {
				popUpAlertDto = popUpAlertService.getPopUpAlertDto(popUpAlert, user);
			}
			popUpAlertDto = offersService.popUpAlertUpdateIdCheck(popUpAlertDto, updateIdPopupAlert);
			offersDtoV4.setPopUpAlertDto(popUpAlertDto);
		}
		offersDtoV4.setInvitingHeadingText(rechargeService.getLocaleTextTemplate().get("INVITE_HEADING_TEXT1.4_"+user.getLocale()));
		offersDtoV4.setInvitingBodyText(rechargeService.getLocaleTextTemplate().get("INVITE_BODY_TEXT1.4_"+user.getLocale()));
		if(rechargeService.getAppConfig().get("isRateUs").equalsIgnoreCase("true")) {
			offersDtoV4.setRateUs(true);
		}
		else {
			offersDtoV4.setRateUs(false);
		}
		List<String> msisdnList = referrerService.getMsisdnList(user.getEttId(), 121l, 0, 100);
		offersDtoV4.setRemindCounter(msisdnList.size());
		OfferDetails offerDetailsInvite = offerDetailsRepository.findByOfferId(13l);
		if(offerDetailsInvite!=null) {
			offersDtoV4.setInviteAmount(offerDetailsInvite.getOfferAmount());
		}
		offersDtoV4.setWifiAutoNotificationCounter(Integer.parseInt(rechargeService.getAppConfig().get("wifiAutoNotificationCounter")));
		offersDtoV4.setWifiAutoNotificaitonTextWiFi(rechargeService.getAppConfig().get("wifiAutoNotificaitonTextWiFi").split("#"));
		offersDtoV4.setInMobiBannerAds(rechargeService.getAppConfig().get("inMobiBannerAds"));
		{
		try  {
					List<DynamicTabDetails> dynamicTabDetails = dynamicTabDetailsRepository.findByTabStatusOrderByPriority(false);
					List<DynamicTabDetailsDto> detailsDtos = new ArrayList<DynamicTabDetailsDto>(dynamicTabDetails.size());
					for(DynamicTabDetails tabDetails: dynamicTabDetails) {
							if(tabDetails.getCategory()==null ||tabDetails.getCategory().equals("") || tabDetails.getCategory()!=null && !tabDetails.equals("null") && !tabDetails.getCategory().equals("") && user.getCategory()!=null && tabDetails.getCategory().equals(user.getCategory())) {
								//if(tabDetails.getVersion()!=null && tabDetails.getVersion().equalsIgnoreCase("All")) {
									DynamicTabDetailsDto dynamicTabDetailsDto = new DynamicTabDetailsDto();
									dynamicTabDetailsDto.setTabName(tabDetails.getTabName());
									if(tabDetails.getTabActionUrl()!=null && tabDetails.getTabActionUrl().length()>0) {
										dynamicTabDetailsDto.setTabActionUrl(tabDetails.getTabActionUrl().replace("#ETTID#", user.getEttId()+"")+URLEncoder.encode(",123,"+user.getEttId()+","+System.currentTimeMillis()+","+tabDetails.getOfferId()));
									}
									else {
										dynamicTabDetailsDto.setTabActionUrl(tabDetails.getTabActionUrl());
									}
									if(tabDetails.getTabPopupText() !=null) {
										dynamicTabDetailsDto.setTabPopupText(tabDetails.getTabPopupText().replace("20B9",(char) Integer.parseInt( "20B9", 16 )+""));
									}
									dynamicTabDetailsDto.setTabId(tabDetails.getTabId());
									dynamicTabDetailsDto.setRedirection(tabDetails.isRedirection());
									dynamicTabDetailsDto.setShowAdds(tabDetails.isShowAdds());
									dynamicTabDetailsDto.setTabType(tabDetails.getTabType());
									detailsDtos.add(dynamicTabDetailsDto);
								//}
							}
				}
				offersDtoV4.setDynamicTabDetailsDtos(detailsDtos);
				//removing the renewBuytab static now it is dynamic
				offersDtoV4.setRenewBuyTab("");
			}catch(Exception ex) {
				LOGGER.info("Error in Adding Dynamic tab ettId={}",user.getEttId());
				ex.printStackTrace();
			}
			
		}
		
		return offersDtoV4;
	}
    public List<OffersDtoV2> getOfferDtoList(User user,List<OfferDetails> offersList,boolean isBulkOffer,boolean shareOffer,List<String> update_id) {
		List<OffersDtoV2> offersDtoList = new ArrayList<>(offersList.size());
		int offerCounter = 0;
		for(OfferDetails offerDetails : offersList){
			if(isBulkOffer && (rechargeService.getOFFERID_BARRED().contains(offerDetails.getOfferId()+""))) {
				continue;
			}
			if(shareOffer==false && offerDetails.getPayoutType().toString().equalsIgnoreCase("SHARE") && Float.parseFloat(user.getAppVersion())>=rechargeService.getShareTabAppVersion()){
				continue;
			}
			OffersDtoV2 offersDto = new OffersDtoV2();
			offersDto.setOfferId(offerDetails.getOfferId());
			offersDto.setText(offerDetails.getOfferName());
			offersDto.setDescription(offerDetails.getDescription().replace("#INSTALLEDAMOUNT#", (offerDetails.getOfferAmount()-offerDetails.getPendingAmountCredit()+"")).replace("#AMOUNT#", offerDetails.getOfferAmount()+"").replace("#OFFER_CREDIT_DAYS#", offerDetails.getBalanceCreditInDays()+"").replace("#PENDINGAMOUNT#", offerDetails.getPendingAmountCredit()+"").replace("#PENDINGRECCOUNT#", offerDetails.getPendingRecCount()+"").replace("#PENDINGRECDAY#", offerDetails.getPendingRecDay()+"").replace("Rs.",(char) Integer.parseInt( "20B9", 16 )+""));
			offersDto.setAppKey(offerDetails.getAppKey());
			offersDto.setOfferName(offerDetails.getOfferName());
			offersDto.setImageUrl(offerDetails.getImageUrl());
			String actionUrl = null;
			String param = offerDetails.getActionUrl().substring(offerDetails.getActionUrl().lastIndexOf("=")+1);
			if(offerDetails.isOfferUrlParamFromDB()) {
				String callBackTid = System.currentTimeMillis()+"";
				String curamount[] = param.split("\\|");
				UserClickCallBackTracker userClickCallBackTracker = new UserClickCallBackTracker();
				userClickCallBackTracker.setBalanceCreditInDays(offerDetails.getBalanceCreditInDays());
				userClickCallBackTracker.setCurrency(Integer.parseInt(curamount[1]));
				userClickCallBackTracker.setEttId(user.getEttId());
				userClickCallBackTracker.setId(callBackTid);
				userClickCallBackTracker.setIncomeAmount(Float.parseFloat(curamount[0]));
				userClickCallBackTracker.setInstalledAmount(offerDetails.getInstalledAmount());
				userClickCallBackTracker.setMsisdn(user.getMsisdn());
				userClickCallBackTracker.setOfferAmount(offerDetails.getOfferAmount());
				userClickCallBackTracker.setOfferCat(offerDetails.getOfferCat());
				userClickCallBackTracker.setOfferId(offerDetails.getOfferId());
				userClickCallBackTracker.setPendingAmountCredit(offerDetails.getPendingAmountCredit());
				userClickCallBackTracker.setPendingRecCount(offerDetails.getPendingRecCount());
				userClickCallBackTracker.setPendingRecDay(offerDetails.getPendingRecDay());
				userClickCallBackTracker.setTid(callBackTid);
				userClickCallBackTracker.setOfferPaymentType(offerDetails.getOfferPaymentType());
				userClickCallBackTrackerRepository.save(userClickCallBackTracker);
				actionUrl = offerDetails.getActionUrl().substring(0,offerDetails.getActionUrl().indexOf("="))+callBackTid;
			}
			else {
					if(offerDetails.getVendor() != null && offerDetails.getVendor().equals("mobusi")){
					String urlMobusi = param.substring(0, param.indexOf("|"));
					String urlAppend = URLEncoder.encode(",321,"+user.getEttId()+","+System.currentTimeMillis()+","+offerDetails.getOfferId()+","+urlMobusi);
					actionUrl = offerDetails.getActionUrl() + urlAppend ;
				}else if(offerDetails.getVendor()!=null && offerDetails.getVendor().equalsIgnoreCase("unlockar")) {
					String encodeData = (",321,"+user.getEttId()+","+System.currentTimeMillis()+","+offerDetails.getOfferId()).replaceAll(",", "B");
					String urlAppend = URLEncoder.encode(encodeData);
					actionUrl = offerDetails.getActionUrl().replaceAll("\\|", "A") + urlAppend ;
				}else {
					String urlAppend = URLEncoder.encode(",321,"+user.getEttId()+","+System.currentTimeMillis()+","+offerDetails.getOfferId());
					actionUrl = offerDetails.getActionUrl() + urlAppend ;
				}
			}
			actionUrl = actionUrl.replaceFirst("\\{android_id\\}", user.getDeviceId());
			if(actionUrl.indexOf("{idfa}")>-1 || actionUrl.indexOf("{aifa}")>-1) {
				DeviceToken deviceToken = deviceTokenRepository.findByEttId(user.getEttId());
				if(deviceToken!=null && deviceToken.getAdvertisingId()!=null) {
					actionUrl = actionUrl.replaceFirst("\\{idfa\\}",deviceToken.getAdvertisingId());
				}
				else 
				{
					actionUrl = actionUrl.replaceFirst("\\{idfa\\}","");
				}
				if(deviceToken!=null && deviceToken.getAndroidId()!=null) {
					actionUrl = actionUrl.replaceFirst("\\{aifa\\}", deviceToken.getAndroidId());
				}
				else {
					actionUrl = actionUrl.replaceFirst("\\{aifa\\}", "");
				}
			}
			offersDto.setActionUrl(actionUrl);
			offersDto.setAmount((offerDetails.getOfferAmount()+offerDetails.getMaxCreditLimit())+"");
			offersDto.setSize(offerDetails.getSize());
			offersDto.setType(offerDetails.getOfferType());
			try {
				offersDto.setPlayStoreDetails(offerDetails.getPlayStoreDetails().replace("#appSize#", offerDetails.getSize()).replace("()", ""));
			}catch(Exception ex){}
			offersDto.setRating(offerDetails.getRating());
			
			
			if(offerDetails.getInstructions() !=null) {
				try {
					String instruction[] = offerDetails.getInstructions().split(";");
					int ii =0;
					List<OfferInstructionDto> OfferInstructionDtolist = new ArrayList<OfferInstructionDto>(instruction.length);
					for(String inst:instruction){
						OfferInstructionDto offerInstructionDto = new OfferInstructionDto();
						offerInstructionDto.setInstructionsText(inst.split("\\$")[0].replace("#INSTALLEDAMOUNT#", (offerDetails.getOfferAmount()-offerDetails.getPendingAmountCredit()+"")).replace("#AMOUNT#", offerDetails.getOfferAmount()+"").replace("#OFFER_CREDIT_DAYS#", offerDetails.getBalanceCreditInDays()+"").replace("#PENDINGAMOUNT#", offerDetails.getPendingAmountCredit()+"").replace("#PENDINGRECCOUNT#", offerDetails.getPendingRecCount()+"").replace("#PENDINGRECDAY#", offerDetails.getPendingRecDay()+""));
						offerInstructionDto.setInstStringAmount(inst.split("\\$")[1].replace("#INSTALLEDAMOUNT#", (offerDetails.getOfferAmount()-offerDetails.getPendingAmountCredit()+"")).replace("#AMOUNT#", offerDetails.getOfferAmount()+"").replace("#OFFER_CREDIT_DAYS#", offerDetails.getBalanceCreditInDays()+"").replace("#PENDINGAMOUNT#", offerDetails.getPendingAmountCredit()+"").replace("#PENDINGRECCOUNT#", offerDetails.getPendingRecCount()+"").replace("#PENDINGRECDAY#", offerDetails.getPendingRecDay()+""));
						if(ii==00){
							offerInstructionDto.setStatus(1);
						}
						else {
							offerInstructionDto.setStatus(0);
						}
						ii++;
						OfferInstructionDtolist.add(offerInstructionDto);
					}
					
					if((offerDetails.getPayoutType().equals("DATAUSAGE") || offerDetails.getMaxCreditLimit()>=1.0f) && Float.parseFloat(user.getAppVersion())>=2.0f) {
					//	DATA_USAGE_INSTRUCTION
						OfferInstructionDto offerInstructionDto = new OfferInstructionDto();
						offerInstructionDto.setInstructionsText(rechargeService.getLocaleTextTemplate().get("DATA_USAGE_INSTRUCTION_"+user.getLocale()).replace("#amountPerDataThreshold#", ((int)offerDetails.getAmountPerDataThreshold())+"").replace("#dataThreshold#", (offerDetails.getDataThreshold()/1024)+"").replace("#maxDataUsageLimit#", ((offerDetails.getDataThreshold()/1024)*((int)offerDetails.getMaxCreditLimit()))+"").replace("#maxCreditPerDayLimit#", ((int)offerDetails.getMaxCreditPerDayLimit())+"").replace("#maxCreditLimit#", ((int)offerDetails.getMaxCreditLimit())+""));
						offerInstructionDto.setInstStringAmount(offerDetails.getMaxCreditLimit()+"");
						OfferInstructionDtolist.add(offerInstructionDto);
					}
					offersDto.setOfferInstructionDto(OfferInstructionDtolist);
				}
				catch(Exception exx1){
					LOGGER.error("[error in instruction configuraion offerId]["+offerDetails.getOfferId()+"]["+offerDetails.getAppKey()+"]");
				}
			}
			if(offerDetails.getAppDescription() == null || offerDetails.getAppDescription().equalsIgnoreCase("null") || offerDetails.getAppDescription().length()==0 ){
				offersDto.setAppDescription("<html><body><font color=\"#FF8000\">detailed instructions</font><br/><br/>"+offerDetails.getAlertText().replaceAll("\n", "<br/>").replaceAll("Rs.",(char) Integer.parseInt( "20B9", 16 )+"").replaceFirst("\\*\\*", "<B>").replace("\\*\\*", "</B>")+"</body></html>");
			}
			else if(offerDetails.getAppDescription().indexOf("<html>")>-1 && offerDetails.getAppDescription().indexOf("</html>")>-1){
				offersDto.setAppDescription(offerDetails.getAppDescription().replaceAll("Rs.",(char) Integer.parseInt( "20B9", 16 )+""));
			}
			else {
				offersDto.setAppDescription("<html><body><font color=\"#FF8000\">description</font><br/><br/>"+offerDetails.getAppDescription().replaceAll("Rs.",(char) Integer.parseInt( "20B9", 16 )+"").replaceAll("\n", "<br/>")+"<br/><br/><font color=\"#FF8000\">detailed instructions</font><br/><br/>"+offerDetails.getAlertText().replaceAll("Rs.",(char) Integer.parseInt( "20B9", 16 )+"").replace("\n", "<br/>").replaceFirst("\\*\\*", "<B>").replace("\\*\\*", "</B>")+"</body></html>");
			}
			offersDto.setDetailedInstructions(offerDetails.getDetailedInstructions());
			if(offerDetails.getPayoutOn().toString().equals("healthQuery")) {
				offersDto.setPayoutOn("Ask health query");
			}
			else if(offerDetails.getPayoutOn().toString().equals("searchFlight")) {
				offersDto.setPayoutOn("Search for Flight");
			}
			else {
				offersDto.setPayoutOn(offerDetails.getPayoutOn().toString());
			}
			offersDto.setPayoutType(offerDetails.getPayoutType().toString());
			offersDto.setOfferCategory(offerDetails.getOfferCategory());
			offersDto.setTitle(offerDetails.getTitle());
			if(offerDetails.getPendingAmountCredit()>=1){
				
			}
			if(offerDetails.getOfferAmount()<offerDetails.getPendingAmountCredit()){
				LOGGER.error("offer wrong configured offerAmount is lesser then pendingAmountCredit offerId{},offerAmount{},PendingAmountCredit{}",offerDetails.getOfferId(),offerDetails.getOfferAmount(),offerDetails.getPendingAmountCredit());
				continue;
			}
			if(offerDetails.getPayoutType().toString().equalsIgnoreCase("SHARE") ){
				offersDto.setAlertText(offerDetails.getAppDescription());
				offersDto.setActionUrl(rechargeService.getLocaleTextTemplate().get("SHAREURL_"+user.getLocale()).replace("#ETTID#", user.getEttId()+"").replace("#OFFERID#", offerDetails.getOfferId()+""));
			}
			else {
				offersDto.setAlertText(offerDetails.getAlertText().replace("#INSTALLEDAMOUNT#", (offerDetails.getOfferAmount()-offerDetails.getPendingAmountCredit()+"")).replace("#AMOUNT#", offerDetails.getOfferAmount()+"").replace("#OFFER_CREDIT_DAYS#", offerDetails.getBalanceCreditInDays()+"").replace("#PENDINGAMOUNT#", offerDetails.getPendingAmountCredit()+"").replace("#PENDINGRECCOUNT#", offerDetails.getPendingRecCount()+"").replace("#PENDINGRECDAY#", offerDetails.getPendingRecDay()+"").replace("Rs.",(char) Integer.parseInt( "20B9", 16 )+""));
			}
			offersDto.setPackageName(offerDetails.getPackageName());
			if(rechargeService.getREAL_OFFER_PAYOUTTYPE().contains(offersDto.getPayoutType())) {
				offerCounter++;
			}
			if(offerDetails.getPayoutType()==PayoutType.INVITE) {
				offersDto.setAppDescription(rechargeService.getLocaleTextTemplate().get("BEST_OFFERS_TEXT_"+user.getLocale()));
				offersDto.setDetailedInstructions(rechargeService.getLocaleTextTemplate().get("INVITE_TEXT_"+user.getLocale()));
				String con = rechargeService.getLocaleTextTemplate().get("INVITE_MESSAGE_"+user.getLocale()).replaceFirst("#ETT_ID#", user.getEttId()+"");
				con = con.replaceFirst("#DATE#", System.currentTimeMillis()+"");
				offersDto.setPackageName(con);
				offersDto.setAlertText(rechargeService.getLocaleTextTemplate().get("INVITE_HEADING_TEXT1.4_"+user.getLocale()));
			}
			String update_id_client = offerDetails.getOfferId()+"_"+offerDetails.getUpdate_triger_id();
			offersDto.setUpdate_id(update_id_client);
			offersDto = offersService.offersUpdateIdCheck(offersDto, update_id);
			offersDtoList.add(offersDto);
			
		}
		if(offerCounter == 0) {
			LOGGER.info("no offer available for user ettId={}",user.getEttId());
			UserNoOffersAvailable userNoOffersAvailable = new UserNoOffersAvailable();
			userNoOffersAvailable.setEttId(user.getEttId());
			userNoOffersAvailableRepository.save(userNoOffersAvailable);
		}
		return offersDtoList;
	}


	
    @RequestMapping(value = "user/ShareOffers", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getOfferShare(@RequestParam("ettId") long ettId,
    										 @RequestParam("otp") long otp,
    										 @RequestParam(value="limit", required=false, defaultValue="30") int limit,
    										 @RequestParam(value="update_id", required=false) List<String> update_id,
    										 @RequestParam(value = "networkType", required=false, defaultValue="UNKNOWN") String networkType,
    										 @RequestParam(value="isCompressed", required=false, defaultValue="true") boolean isCompressed
	) {
		LOGGER.info("user/ShareOffers/ ettId={},otp={},limit={}",ettId,otp,limit);
    	if(ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
    	User user = userRepository.findByEttId(ettId);
    	if(user == null)
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    		if(user.getOtp()!=0 && user.getOtp()!=otp) {
    			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
    			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    		}
    		List<OfferDetails> offersList = offersService.getShareOffers(user, false, networkType);
    		if(offersList==null || offersList.size()==0){
    			return new ResponseEntity<>(HttpStatus.OK);
    		}
    		List<OffersDtoV2> offersDtoList = new ArrayList<>(offersList.size());
    		offersDtoList = getOfferDtoList(user, offersList,false,true,update_id);
    		
		try {
				if(rechargeService.getAppConfig().get("IsOfferDataCompress").equals("true") && isCompressed == true) {
					Gson gson = new Gson();
					String json = gson.toJson(offersDtoList);
					byte [] compressed = offersService.compress(json);
					OffersDtoV4Compress offersDtoV4Compress = new OffersDtoV4Compress();
					offersDtoV4Compress.setIsCompress(true);
					offersDtoV4Compress.setOfferDetails(compressed);
					return new ResponseEntity<>(offersDtoV4Compress, HttpStatus.OK);
			}
			else{
				return new ResponseEntity<>(offersDtoList,HttpStatus.OK);
			}
		}catch(Exception ex){
			return new ResponseEntity<>(offersDtoList,HttpStatus.OK);
		}
	}
    
    @RequestMapping(value = "user/ShoppingOffers", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getOfferShopping(@RequestParam("ettId") long ettId,
    										 @RequestParam("otp") long otp,
    										 @RequestParam(value="update_id", required=false) List<String> update_id,
    										 @RequestParam(value="limit", required=false, defaultValue="30") int limit,
    										 @RequestParam(value="isCompressed", required=false, defaultValue="true") boolean isCompressed
	) {
    	LOGGER.info("user/ShoppingOffers/ ettId={},otp={},limit={}",ettId,otp,limit);
    	if(ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
    	User user = userRepository.findByEttId(ettId);
    	if(user == null)
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    		if(user.getOtp()!=0 && user.getOtp()!=otp) {
    			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
    			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    		}
    		
    		
    		List<ShoppingDetails> shoppingDetails = shoppingDetailsRepository.findByStatus(1);
    		//List<OffersStarted> offerStarted = offersStartedRepository.findByEttId(ettId);
    		if(shoppingDetails==null || shoppingDetails.size()==0){
    			return new ResponseEntity<>(HttpStatus.OK);
    		}
    		
    		List <ShoppingDetailsDto> shoDetailsDtos = new ArrayList<ShoppingDetailsDto>(shoppingDetails.size());
    		for(ShoppingDetails shoppingStart : shoppingDetails){
    			
    			ShoppingDetailsDto shoppingDetailsDto = new ShoppingDetailsDto();
    			shoppingDetailsDto.setOfferId(shoppingStart.getOfferId());
    			shoppingDetailsDto.setActionUrl((shoppingStart.getActionUrl()+URLEncoder.encode(",321,"+user.getEttId()+","+System.currentTimeMillis()+","+shoppingStart.getOfferId())).replace("#ETTID#", user.getEttId()+""));
    			shoppingDetailsDto.setAmount(shoppingStart.getAmount());
    			shoppingDetailsDto.setButtonText(shoppingStart.getButtonText());
    			shoppingDetailsDto.setCategory(shoppingStart.getCategory());
    			shoppingDetailsDto.setDescription(shoppingStart.getDescription());
    			shoppingDetailsDto.setImageUrl(shoppingStart.getImageUrl());
    			shoppingDetailsDto.setOfferName(shoppingStart.getOfferName());
    			shoppingDetailsDto.setOfferType(shoppingStart.getOfferType());
    			shoppingDetailsDto.setTitle(shoppingStart.getTitle());
    			shoppingDetailsDto.setPriority(shoppingStart.getPriority());
    			shoppingDetailsDto.setUpdate_id(shoppingStart.getOfferId()+"_"+shoppingStart.getUpdate_triger_id());
    			shoDetailsDtos.add(shoppingDetailsDto);
    			}
    			offersService.offerShoppingDtoUpdateIdCheck(shoDetailsDtos, update_id);
    			try {
    				if(rechargeService.getAppConfig().get("IsOfferDataCompress").equals("true") && isCompressed == true) {
    					Gson gson = new Gson();
    					String json = gson.toJson(shoDetailsDtos);
    					byte [] compressed = offersService.compress(json);
    					OffersDtoV4Compress offersDtoV4Compress = new OffersDtoV4Compress();
    					offersDtoV4Compress.setIsCompress(true);
    					offersDtoV4Compress.setOfferDetails(compressed);
    					return new ResponseEntity<>(offersDtoV4Compress, HttpStatus.OK);
    	    	}
    	    	else{
    	    		return new ResponseEntity<>(shoDetailsDtos,HttpStatus.OK);
    	    	}
        	}catch(Exception ex){
        		return new ResponseEntity<>(shoDetailsDtos,HttpStatus.OK);
        	}
	}

		
}