	package com.web.rest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.entity.DeviceToken;
import com.domain.entity.RedeemThreshold;
import com.domain.entity.SchedulePush;
import com.domain.entity.User;
import com.domain.entity.UserAccount;
import com.domain.entity.UserAccountSummary;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.FirstHitDayRepository;
import com.repository.jpa.Msisdn_30Repository;
import com.repository.jpa.RedeemThresholdRepository;
import com.repository.jpa.SchedulePushRepository;
import com.repository.jpa.UserAccountRepository;
import com.repository.jpa.UserAccountSummaryRepository;
import com.repository.jpa.UserBlackListRepository;
import com.repository.jpa.UserRepository;
import com.repository.jpa.UserSourceRepository;
import com.service.EttApis;
import com.service.FirstHitService;
import com.service.Msisdn_30Service;
import com.service.OffersService;
import com.service.RechargeService;
import com.service.ReferrerService;
import com.web.rest.dto.AccountSummaryDto;
import com.web.rest.dto.AccountSummaryDtoCompress;
import com.web.rest.dto.BalanceCurrentDto;
import com.web.rest.dto.CompressDto;
import com.web.rest.dto.GetBalanceDto;
import com.web.rest.dto.InviteeDetailsDto;
import com.web.rest.dto.OffersDtoV4Compress;
import com.web.rest.dto.SummaryDto;
import com.web.rest.dto.SummaryDtoCompress;


/**
 * @author ankur
 */
@Controller
@RequestMapping(value = "/v2")
public class BalanceControllerV2 {

    private static Logger LOGGER = LoggerFactory.getLogger(BalanceControllerV2.class);

    @Inject 
    OffersService offersService;
    
    @Autowired
    UserRepository userRepository;
   
    @Autowired
    UserAccountRepository userAccountRepository;
    
    @Autowired 
    UserAccountSummaryRepository userAccountSummaryRepository;
    
    @Autowired 
    RedeemThresholdRepository redeemThresholdRepository;
    
    @Resource
    RechargeService rechargeService;
    
    @Resource
    private FirstHitService firstHitDayService;
    
    @Resource
    FirstHitService firstHitService;
    
    @Autowired
    private DeviceTokenRepository deviceTokenRepository;
    
    @Autowired
    UserSourceRepository userSourceRepository;
    
    @Autowired
    FirstHitDayRepository firstHitDayRepository;
    
    @Resource
	Msisdn_30Repository msisdn_30Repository;
    
    @Resource
    private JmsTemplate jmsTemplate;
    
    @Resource
    private ReferrerService referrerService;
    
    @Autowired
	private Msisdn_30Service msisdn_30Service;
    
    @Resource
    UserBlackListRepository userBlackListRepository;

    @Resource
    EttApis ettApis;

    @Resource
	SchedulePushRepository schedulePushRepository;

    
    @RequestMapping(value = "user/getBalance/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getBalance(@RequestParam("ettId") long ettId,
    									@RequestParam(value="networkType", required=false, defaultValue="UNKNOWN") String networkType,
    									@RequestParam(value="isLogin", required=false, defaultValue="false") boolean isLogin,
    									@RequestParam("otp") int otp,
    									@RequestParam(value="tempOtp", required=false, defaultValue="0") int tempOtp,
    									@RequestParam(value="deviceId", required=false) String deviceId,
    									@RequestParam(value="deviceToken", required=false) String deviceToken,
    									@RequestParam(value="version", required=false, defaultValue="0.0") float version
    									
    ) throws IOException, ExecutionException, InterruptedException {
    	LOGGER.info("API user/getBalance/ ettId={}, networkType={}, isLogin={}, otp={}, tempOtp={}, deviceId={}, deviceToken={},version={}", ettId, networkType, isLogin, otp, tempOtp,deviceId,deviceToken,version);
    	
    	if(ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

    	User user = userRepository.findByEttId(ettId);
    	if(user == null)
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    		
    	
    	UserAccount userAccount = userAccountRepository.findByEttId(ettId);
    	if(userAccount == null){
    		LOGGER.info("insert in userAccount with 0 ettId = "+ettId);
			userAccount = new UserAccount();
			userAccount.setEttId(user.getEttId());
			userAccount.setCurrentBalance(0);
			//userAccount.setBalanceCoins(Integer.parseInt(rechargeService.getProperties().getProperty("JOINING_COIN"))); //5 coin for opening the A/C
			userAccount = userAccountRepository.save(userAccount);
    		
			
    		
    	}
    	
    	//if(!user.isVerified() && otp!=0){
    		if(user.getOtp()!=0 && user.getOtp()!=otp) {
    			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
    			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    		}
    		
    			
    		//check for deviceId update
    		if(deviceId!=null && !deviceId.equals("") && !user.getDeviceId().equals(deviceId)){
    			user.setDeviceId(user.getDeviceId());
    		}
    		if(!user.isVerified() && otp!=0){
    		LOGGER.info("verifying ettId="+ettId);
    		user.setRegDate(new Date());
    		user.setVerified(true);
    		user.setFlage(false);
    		user.setUpdatedTime(new Date());
    		user.setFirstLogin(true);
    		DeviceToken deviceTokenobj = deviceTokenRepository.findByEttId(ettId);
    		if(deviceTokenobj == null) {
    			deviceTokenobj = new DeviceToken();
    			deviceTokenobj.setEttId(user.getEttId());
				deviceTokenobj.setDeviceId(user.getDeviceId());
    			deviceTokenobj.setDeviceToken(deviceToken);
    			
    		}
    		deviceTokenobj.setActive(true);
    		deviceTokenRepository.save(deviceTokenobj);
    		referrerService.setPromotionClickEvent(user);
    		if(rechargeService.getAppConfig().get("NEW_USER_PUSH_CHECK").equals("true"))
				sendPush(rechargeService.getLocaleTextTemplate().get("NEW_USER_PUSH_"+user.getLocale()), deviceTokenobj.getDeviceToken(),user.getEttId());
				
				if(rechargeService.getAppConfig().get("NEW_USER_BONUS_ON_FIRST_APP").equals("true")){
					//sendPush(properties.getProperty("NEW_USER_SECOND_PUSH"), updatedDeviceToken.getDeviceToken());
					SchedulePush schedulePush = new SchedulePush();
					schedulePush.setEttId(user.getEttId());
					schedulePush.setMessage(rechargeService.getLocaleTextTemplate().get("NEW_USER_SECOND_PUSH_"+user.getLocale()));
					java.util.Date datetime = new java.util.Date();
					datetime.setTime(datetime.getTime()+60000);
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
    		   		user = userRepository.save(user);
    		
    		
    		   		if(rechargeService.getAppConfig().get("INVITE_OFFER_ETT_DOWNLOAD_CALLBACK_AVAILABLE").equals("true") ){
    		   			if(!user.isDownloadedFirstApp()) {
    		   			referrerService.rewardReffrerOnDownloadEtt(user);  
    					userRepository.save(user);
    		   			}
    		   			else {}
    		   		}
    		   		else if(rechargeService.getAppConfig().get("INVITE_OFFER_ETT_DOWNLOAD_AVAILABLE").equals("true") && !user.isDownloadedFirstApp()){
    		referrerService.rewardReffrerOnDownloadEtt(user);  
    		user.setDownloadedFirstApp(true);
			userRepository.save(user);
			//referrerService.setPromotionClickEventFirstAppDownload(user);
    		}
    		else {
    			referrerService.rewardReffrerOnDownloadEttZeroSummary(user);
    		}
    	}
    	/*if(!user.isVerified() && otp==0){
    		if(user.getTempOtp() != tempOtp){
    			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    		}
    		
    	}else*/ if(isLogin){
    		LOGGER.info("login ettId="+ettId);
    		user.setUpdatedTime(new Date());
    		user = userRepository.save(user);
    	}

    	RedeemThreshold redeemThreshold = redeemThresholdRepository.findById(1);
    	float threshold = redeemThreshold.getThresholdAmount();
    	
    	
        return new ResponseEntity<>(getBalancetDto(user, userAccount,threshold),HttpStatus.OK);
    }

    @RequestMapping(value = "user/getCurrentBalance/", method = RequestMethod.GET)
	 @ResponseBody
	 public ResponseEntity<?> getBalance(@RequestParam("ettId") long ettId,
			@RequestParam("otp") int otp,
			@RequestParam(value="tempOtp", required=false, defaultValue="0") int tempOtp
			) throws IOException, ExecutionException, InterruptedException {
    	
    	
    	
    			
    	LOGGER.info("API user/getCurrentBalance/ ettId={}, otp={}, tempOtp={}", ettId, otp, tempOtp);
    	
    	if(ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

    	User user = userRepository.findByEttId(ettId);
    	if(user == null)
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    		
    	if(user.getOtp()!=otp) {
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
    	UserAccount userAccount = userAccountRepository.findByEttId(ettId);
    	if(userAccount == null){
    			//return new ResponseEntity<>(getBalancetDto(user, userAccount,threshold),HttpStatus.OK);
    			userAccount = new UserAccount();
    			userAccount.setEttId(ettId);
    			userAccount.setCurrentBalance(0);
    			userAccountRepository.save(userAccount);
			}
    	
    	float threshold = ettApis.getThresholdAmont(user);
    	
    	BalanceCurrentDto balanceCurrentDto = new BalanceCurrentDto();
    	balanceCurrentDto.setBalance(userAccount.getCurrentBalance());
    	balanceCurrentDto.setThreshold(threshold);
        return new ResponseEntity<>(balanceCurrentDto,HttpStatus.OK);
    	//return new ResponseEntity<>(userAccount.getCurrentBalance(),HttpStatus.OK);
    }
 
    @RequestMapping(value = "user/getInvitee", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> invitee(@RequestParam("ettId") long ettId,
    								 @RequestParam(value="offset", required=false, defaultValue="0") int offset,
    								 @RequestParam(value="limit", required=false, defaultValue="100") int limit,
    								 @RequestParam("otp") int otp,
    								 @RequestParam(value="isCompressed", required=false, defaultValue="true") boolean isCompressed
    ){
    	LOGGER.info("API user/getInvitee/ ettId={}, otp={}", ettId, otp);
    	User user = userRepository.findByEttId(ettId);
    	if(ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
    	if(user == null)
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    		
    	if(user.getOtp()!=0 && user.getOtp()!=otp) {
    		ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
    	//List<UserAccountSummary> userAccountSummary = userAccountSummaryRepository.findByEttIdAndOfferIdOrderByIdDesc(ettId, 121l, new PageRequest(offset, limit));
    	List<String> msisdnList = referrerService.getMsisdnList(ettId, 121l, offset, limit);
    	/*if(userAccountSummary != null && userAccountSummary.size()>0){
    		msisdnList = new ArrayList<>(userAccountSummary.size());
    		DateTime date = DateTime.now();
    		for(UserAccountSummary accountSummary : userAccountSummary){
    			if(accountSummary.getOfferName().contains("INVITE")){
    				int hour = (int) ((date.getMillis() - accountSummary.getCreatedTime().getTime()) / (1000 * 60 * 60));
    				if(hour>48)	continue;
    				int index = accountSummary.getOfferName().lastIndexOf("_");
    				if(index >= 0){
    					msisdnList.add("0"+accountSummary.getOfferName().substring(index+1));
    				}
    			}
    	}*/
		if(msisdnList.size()>0){	
			try{
					if(rechargeService.getAppConfig().get("IsOfferDataCompress").equals("true") && isCompressed == true) {
					Gson gson = new GsonBuilder().serializeNulls().create();
					String json = gson.toJson(getInviteeDetailsDto(msisdnList,user));
					byte [] compressed = offersService.compress(json);
					
					CompressDto compressDto = new CompressDto();
			    	compressDto.setIsCompress(true);
			    	compressDto.setCompressedData(compressed);
			    	return new ResponseEntity<>(compressDto, HttpStatus.OK);
	
				}
				else{
					return new ResponseEntity<>(getInviteeDetailsDto(msisdnList,user), HttpStatus.OK);
				}
			}catch(Exception ex ){
				return new ResponseEntity<>(getInviteeDetailsDto(msisdnList,user), HttpStatus.OK);
			}
		}
    	//}
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	
    }

	private Object getInviteeDetailsDto(List<String> msisdnList, User user) {
		InviteeDetailsDto inviteeDetailsDto = new InviteeDetailsDto();
		int amount = Integer.valueOf(rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_MONEY"));
		String offerAmount = String.valueOf(amount);
		String totalAmount = String.valueOf(amount * msisdnList.size());
		inviteeDetailsDto.setHeading(rechargeService.getLocaleTextTemplate().get("INVITEE_API_HEADING_"+user.getLocale()).replaceFirst("#AMOUNT#", offerAmount));
		inviteeDetailsDto.setHeaderHeading(rechargeService.getLocaleTextTemplate().get("INVITEE_API_HEADER_HEADING_"+user.getLocale()));
		inviteeDetailsDto.setHeaderText(rechargeService.getLocaleTextTemplate().get("INVITEE_API_HEADER_TEXT_"+user.getLocale()).replaceFirst("#AMOUNT#", totalAmount));
		inviteeDetailsDto.setSmsShareText(rechargeService.getLocaleTextTemplate().get("INVITEE_API_SMS_SHARE_TEXT_"+user.getLocale()));
		inviteeDetailsDto.setWhatsAppShareText(rechargeService.getLocaleTextTemplate().get("INVITEE_API_WHATSAPP_SHARE_TEXT_"+user.getLocale()));
		inviteeDetailsDto.setInviteDto(msisdnList);
		return inviteeDetailsDto;
	}

	@RequestMapping(value = "user/accountSummary/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> accountSummary(@RequestParam("ettId") long ettId,
    										@RequestParam(value="offset", required=false, defaultValue="0") int offset,
    										@RequestParam(value="limit", required=false, defaultValue="30") int limit,
    										@RequestParam("otp") int otp,
    										@RequestParam(value="tempOtp", required=false, defaultValue="0") int tempOtp,
    										@RequestParam(value="isCompressed", required=false, defaultValue="true") boolean isCompressed
    ) {
    	LOGGER.info("API user/accountSummary/ ettId={}, offset={}, limit={}, otp={}, tempOtp={}, isCompressed{}", ettId, offset, limit,otp,tempOtp,isCompressed);
    	User user = userRepository.findByEttId(ettId);
    	if(ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
    	if(user == null)
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	
    		if(user.getOtp()!=0 && user.getOtp()!=otp) {
    			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
    			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    		}
    	List<UserAccountSummary> userAccountSummaryList = userAccountSummaryRepository.findByEttIdOrderByIdDesc(ettId, new PageRequest(offset, limit));
    	UserAccount userAccount = userAccountRepository.findByEttId(ettId);
    	if(userAccount==null) {
    		userAccount = new UserAccount();
    		userAccount.setCurrentBalance(0.0f);
    		userAccount = userAccountRepository.save(userAccount);
    	}
    	float currentBalance = userAccount.getCurrentBalance();
        
    	try {
	    	if(rechargeService.getAppConfig().get("IsOfferDataCompress").equals("true") && isCompressed == true) {
	    		Object offerDto = getActSummaryDtoCompress(userAccountSummaryList,currentBalance);
		    	//Gson gson = new Gson();
		    	
		    	
	    		Gson gson = new GsonBuilder().serializeNulls().create();
		    	
		    	String json = gson.toJson(offerDto);
		    	//System.out.println("[json]["+json+"]");
		    	byte [] compressed = offersService.compress(json);
		    	OffersDtoV4Compress offersDtoV4Compress = new OffersDtoV4Compress();
		    	offersDtoV4Compress.setIsCompress(true);
		    	offersDtoV4Compress.setOfferDetails(compressed);
		    	return new ResponseEntity<>(offersDtoV4Compress, HttpStatus.OK);
	    	}
	    	else{
	    		return new ResponseEntity<>(getActSummaryDto(userAccountSummaryList,currentBalance),HttpStatus.OK);
	    	}
    	}catch(Exception ex){
    		return new ResponseEntity<>(getActSummaryDto(userAccountSummaryList,currentBalance),HttpStatus.OK);
    	}
    	
    	//return new ResponseEntity<>(getActSummaryDto(userAccountSummaryList,currentBalance),HttpStatus.OK);
    }

    
    
	private AccountSummaryDtoCompress getActSummaryDtoCompress(List<UserAccountSummary> userAccountSummaryList,float currentBalance) {
		List<SummaryDtoCompress> summaryDtos = new ArrayList<>(userAccountSummaryList.size());
		for(UserAccountSummary userAccountSummary : userAccountSummaryList){
			SummaryDtoCompress summaryDto = new SummaryDtoCompress();
			summaryDto.setId(userAccountSummary.getId());
			summaryDto.setOfferId(userAccountSummary.getOfferId());
			//summaryDto.setOfferName(userAccountSummary.getOfferName().replace("REDEEM", "redemption"));
			summaryDto.setOfferName(userAccountSummary.getOfferName().replace("REDEEM", "redemption").replace("convenience charge", "convenience fee"));
			summaryDto.setAmount(userAccountSummary.getAmount());
			summaryDto.setText(userAccountSummary.getRemarks());
			summaryDto.setDate(userAccountSummary.getCreatedTime().getTime());
			summaryDtos.add(summaryDto);
		}
		
		AccountSummaryDtoCompress accountSummaryDto = new AccountSummaryDtoCompress();
		accountSummaryDto.setTotal(currentBalance);
		accountSummaryDto.setSummary(summaryDtos);
		return accountSummaryDto;
	}
	
	private AccountSummaryDto getActSummaryDto(List<UserAccountSummary> userAccountSummaryList,float currentBalance) {
		List<SummaryDto> summaryDtos = new ArrayList<>(userAccountSummaryList.size());
		for(UserAccountSummary userAccountSummary : userAccountSummaryList){
			SummaryDto summaryDto = new SummaryDto();
			summaryDto.setId(userAccountSummary.getId());
			summaryDto.setOfferId(userAccountSummary.getOfferId());
			summaryDto.setOfferName(userAccountSummary.getOfferName());
			summaryDto.setAmount(userAccountSummary.getAmount());
			summaryDto.setText(userAccountSummary.getRemarks());
			summaryDto.setDate(userAccountSummary.getCreatedTime());
			summaryDtos.add(summaryDto);
		}
		
		AccountSummaryDto accountSummaryDto = new AccountSummaryDto();
		accountSummaryDto.setTotal(currentBalance);
		accountSummaryDto.setSummary(summaryDtos);
		return accountSummaryDto;
	}


	private GetBalanceDto getBalancetDto(User user, UserAccount userAccount, float threshold) {
		GetBalanceDto getBalanceDto = new GetBalanceDto();
		if(userAccount.getCurrentBalance()>=threshold)
			getBalanceDto.setRedeem(true);
		getBalanceDto.setBalance(userAccount.getCurrentBalance());
		//getBalanceDto.setBalanceCoins(userAccount.getBalanceCoins());
		getBalanceDto.setThreshold(threshold);
		String btext = rechargeService.getLocaleTextTemplate().get("BALANCE_TEXT_"+user.getLocale()).replaceFirst("#BALANCE#", userAccount.getCurrentBalance()+"");
		getBalanceDto.setBalanceText(btext);
		getBalanceDto.setBestOffersText(rechargeService.getLocaleTextTemplate().get("BEST_OFFERS_TEXT_"+user.getLocale()));
		getBalanceDto.setInviteFriendText(rechargeService.getLocaleTextTemplate().get("INVITE_TEXT_"+user.getLocale()));
		String con = rechargeService.getLocaleTextTemplate().get("INVITE_MESSAGE_"+user.getLocale()).replaceFirst("#ETT_ID#", userAccount.getEttId()+"");
		con = con.replaceFirst("#DATE#", System.currentTimeMillis()+"");
		getBalanceDto.setInviteFriendUrlText(con);
		getBalanceDto.setAppVersion(rechargeService.getAppConfig().get("CURRENT_ANDROID_VERSION"));
		getBalanceDto.setAppVersionUpdate(rechargeService.getAppVersionUpdate().get(user.getAppVersion()));
		
		getBalanceDto.setUpdateText(rechargeService.getLocaleTextTemplate().get("UPDATE_TEXT_"+user.getLocale()));
		//getBalanceDto.setAddOn(true);
		if(rechargeService.getAppConfig().get("ADD_ON").equals(true))
		{
			getBalanceDto.setAddOn(true);
		}
		else{
			getBalanceDto.setAddOn(false);
		}
		getBalanceDto.setNotRegister(user.isFlage());
		
		getBalanceDto.setInviteTabText(rechargeService.getLocaleTextTemplate().get("INVITE_TAB_TEXT_"+user.getLocale()));
		return getBalanceDto;
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
	 	                mapMessage.setLong("ettId", ettId);
	 	                mapMessage.setInt("BADGE_COUNT", 1);
	 	                mapMessage.setString("DEVICE_TYPE", "ANDROID");
	 	               mapMessage.setString("type", "BALANCE");
	 	                
	 	                return mapMessage;
	 	            }
	 	        });
	 		}catch(Exception e){
	 			LOGGER.error("error in callback controller push "+e);
	 			e.printStackTrace();
	 		}
		 }
	 	}
	private void sendPush(final String pushText, final String dToken,final Long ettId) {
		try{
			jmsTemplate.send(new MessageCreator() {
					@Override
					public Message createMessage(Session session) throws JMSException {

					MapMessage mapMessage = session.createMapMessage();
					mapMessage.setString("ID", dToken);
					mapMessage.setString("DISPLAY_STRING", pushText);
					mapMessage.setInt("BADGE_COUNT", 1);
					mapMessage.setLong("ettId", ettId);
					mapMessage.setString("DEVICE_TYPE", "ANDROID");
					mapMessage.setString("type", "BALANCE");
					return mapMessage;
					}
					});
		}catch(Exception e){
			LOGGER.error("error in otp controller push "+e);
			e.printStackTrace();
		}

	}
}