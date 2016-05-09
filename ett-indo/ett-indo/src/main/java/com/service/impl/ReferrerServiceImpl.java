package com.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
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
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.domain.entity.DeviceToken;
import com.domain.entity.InviteeMsisdn;
import com.domain.entity.PROMOTION_CLICK_EVENT;
import com.domain.entity.SchedulePush;
import com.domain.entity.User;
import com.domain.entity.UserAccount;
import com.domain.entity.UserAccountSummary;
import com.domain.entity.UserBonusCreditSummary;
import com.domain.entity.UserDeviceIdChange;
import com.domain.entity.UserGameCreditSummary;
import com.domain.entity.UserSource;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.InviteeMsisdnRepository;
import com.repository.jpa.PROMOTION_CLICK_EVENTRepository;
import com.repository.jpa.SchedulePushRepository;
import com.repository.jpa.UserAccountRepository;
import com.repository.jpa.UserAccountSummaryRepository;
import com.repository.jpa.UserBonusCreditSummaryRepository;
import com.repository.jpa.UserDeviceIdChangeRepository;
import com.repository.jpa.UserGameCreditSummaryRepository;
import com.repository.jpa.UserRepository;
import com.repository.jpa.UserSourceRepository;
import com.service.EttApis;
import com.service.RechargeService;
import com.service.ReferrerService;

@Service
public class ReferrerServiceImpl implements ReferrerService {

	private static Logger LOGGER = LoggerFactory.getLogger(ReferrerServiceImpl.class);

	@Resource
	private RechargeService rechargeService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserSourceRepository userSourceRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private UserAccountSummaryRepository userAccountSummaryRepository;

	@Autowired
	private UserGameCreditSummaryRepository userGameCreditSummaryRepository;
	
	@Resource
	private UserDeviceIdChangeRepository userDeviceIdChangeRepository;

	@Autowired
	private UserBonusCreditSummaryRepository userBonusCreditSummaryRepository;

	@Autowired
	private DeviceTokenRepository deviceTokenRepository;

	@Resource
	private JmsTemplate jmsTemplate;

	@Resource
	EttApis ettApis;
	
	@Resource
	private PROMOTION_CLICK_EVENTRepository pROMOTION_CLICK_EVENTRepository;

	@Resource
	SchedulePushRepository schedulePushRepository;
	
	@Resource
	private InviteeMsisdnRepository inviteeMsisdnRepository;

	@Override
	public List <String> getMsisdnList(long ettId,long offerId,int offset,int limit) {
		// fetch the data from Invite list table.
		List<String> msisdnList;
		String msisdn = inviteeMsisdnRepository.findMsisdnByEttId(ettId);
		if(msisdn != null){
			if(msisdn.equals("")){
				return Collections.emptyList();
			}
			String[] msisdnArr = msisdn.split(",");
			msisdnList = new ArrayList<>(msisdnArr.length);
			for(int i=0; i<msisdnArr.length; i++){
				if(msisdnArr[i].equals("")) continue;
				msisdnList.add(msisdnArr[i]);
			}
			return msisdnList;
		}
		List<UserAccountSummary> userAccountSummary = userAccountSummaryRepository.findByEttIdAndOfferIdOrderByIdDesc(ettId, 121l, new PageRequest(offset, limit));
    	msisdnList = new ArrayList<>(userAccountSummary.size());
    	StringBuffer msisdnStr = new StringBuffer();
    	InviteeMsisdn inviteeMsisdn = new InviteeMsisdn();
    	inviteeMsisdn.setEttId(ettId);
    	inviteeMsisdn.setMsisdn("");
    	if(userAccountSummary != null && userAccountSummary.size()>0){
    		//msisdnList = new ArrayList<>(userAccountSummary.size());
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
    		}
    		for(String newMsisdn : msisdnList){
    			msisdnStr.append(newMsisdn);
    			msisdnStr.append(",");
    		}
    		if(msisdnStr.length()>0){
    			inviteeMsisdn.setMsisdn(msisdnStr.deleteCharAt(msisdnStr.length()-1).toString());
    		}
    	}
    	inviteeMsisdnRepository.save(inviteeMsisdn);
    	return msisdnList;
	}
	
	@Override
	public void addInInviteeMsisdn(long ettId, String msisdn) {
		InviteeMsisdn inviteeMsisdn = inviteeMsisdnRepository.findByEttId(ettId);
		if(inviteeMsisdn != null){
			if(inviteeMsisdn.getMsisdn()!=null && inviteeMsisdn.getMsisdn().indexOf(msisdn)>=0) {
				LOGGER.info("allready added ettId={},msisdn={}",ettId,msisdn);
				return;
			}
			StringBuffer msisdnStr = new StringBuffer(inviteeMsisdn.getMsisdn());
			msisdnStr = msisdnStr.append(","+msisdn);
			inviteeMsisdn.setMsisdn(msisdnStr.toString());
			inviteeMsisdnRepository.save(inviteeMsisdn);
			LOGGER.info("new entry added in InviteeMsisdn");
			return;
		}
		inviteeMsisdn = new InviteeMsisdn();
		inviteeMsisdn.setEttId(ettId);
		inviteeMsisdn.setMsisdn(msisdn);
		inviteeMsisdnRepository.save(inviteeMsisdn);
		LOGGER.info("new msisdn={} inserted in InviteeMsisdn of ettId={}",msisdn,ettId);
	}
	
	@Override 
	public void giveInviteCredit(User user) {
		if(rechargeService.getAppConfig().get("INVITE_OFFER_ETT_DOWNLOAD_CALLBACK_AVAILABLE").equals("true") ){
				if(!user.isDownloadedFirstApp()) {
	   			rewardReffrerOnDownloadEtt(user);  
				//userRepository.save(user);
				}
				else {}
			}
			else if(rechargeService.getAppConfig().get("INVITE_OFFER_ETT_DOWNLOAD_AVAILABLE").equals("true") && !user.isDownloadedFirstApp()){
					//referrerService.rewardReffrerOnDownloadEtt(user);  
					//user.setDownloadedFirstApp(true);
					//referrerService.setPromotionClickEventFirstAppDownload(user);
					//userRepository.save(user);
			}
			else {
				rewardReffrerOnDownloadEttZeroSummary(user);
			}
	}
	
	@Override
	public void rewardReffrerOnDownloadEttZeroSummary(User user) {
		UserSource userSource = userSourceRepository.findOne(user.getEttId());
		if (userSource == null) {
			LOGGER.error("user source not found " + user.getEttId());
			return;
		}
		//Properties properties = rechargeService.getProperties();
		if (userSource.getUtmMedium().equals("INVITE")) {
			Long refererId = 0l;
			try {
				refererId = Long.valueOf(userSource.getUtmSource());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			// ################device same device ID Check
			// ############################
			User userRef = userRepository.findByEttId(refererId);
			if (userRef == null) {
				return;
			}
			if (user.getDeviceId().equals(userRef.getDeviceId())) {
				LOGGER.info(
						"[rewardReffrerOnDownloadEttZeroSummary] deviceID same ettId={}, ettId referer {}",
						user.getEttId(), userRef.getEttId());
				return;
			}
			// ################device same device ID Check

			// ############################
			UserAccountSummary userAccountSummary = new UserAccountSummary();
			userAccountSummary.setEttId(refererId);
			userAccountSummary.setOfferId(920l);
			userAccountSummary.setOfferName("INVITE_" + user.getMsisdn());
			userAccountSummary.setAmount(0);
			userAccountSummary.setRemarks(userSource.getUtmTerm());
			//userAccountSummary.setRemarks(user.getEttId()+"");
			//userAccountSummary.setRemarks(user.getEttId()+"");
			//userAccountSummary.setVendor(offer);
			userAccountSummaryRepository.save(userAccountSummary);
			
			
		}
	}

	@Override
	public void setPromotionClickEvent(User user){
		UserSource userSource = userSourceRepository.findOne(user.getEttId());
		String status="2";
		if (userSource == null) {
			LOGGER.error("user source not found " + user.getEttId());
			return;
		}
		
		List<String> utmMedium = new ArrayList<String>();
		if(userSource.getUtmSource() != null && userSource.getUtmSource().trim().length()>0) {
			utmMedium.add(userSource.getUtmMedium());
		}
		else {
			return;
		}
		List<PROMOTION_CLICK_EVENT> PromotionClickEvent = pROMOTION_CLICK_EVENTRepository.findByClick_idPromotion(utmMedium);
		if(PromotionClickEvent == null || PromotionClickEvent.size()==0){
			return;
		}	
		/*if(user.getStatus()>=2) {
			LOGGER.info("user in fraud list so not giving the promotion callback ettId={}",user.getEttId());
			status="5";
		}*/
		//if(user.getStatus()==2) {
		if(user.getStatus()==3) {
			LOGGER.info("user problem with the deviceId so not giving the promotion callback ettId={}",user.getEttId());
			status="5";
			//return;
		}
		List<Long> userSameDevice = userRepository.findByDeviceId_3(user.getDeviceId());
		//1 device is for the actual user.
		if(userSameDevice.size()>1) {
			LOGGER.info("same deviceId is found for the deviceID before that so not giving the promotion callback refEttId={},user deviceId={}, user ettId={}",userSource.getEttId(), user.getDeviceId(),user.getEttId());
			status="5";
			//return;
		}
		List<UserDeviceIdChange> userDeviceIdChange = userDeviceIdChangeRepository.findByOldDeviceId(user.getDeviceId());
		if(userDeviceIdChange != null && userDeviceIdChange.size()>0){
			//LOGGER.info("[Reward Barred/UserDeviceIdChange] deviceId={}, user ettId={},refEttId={}  has previously installed",user.getDeviceId(),user.getEttId(),userRef.getEttId());
			LOGGER.info("UserDeviceIdChange Check fail not giving the promotion callback checkFail=UserDeviceIdChange,ettId={},UserDeviceIdChange={}",user.getEttId(),user.getDeviceId());
			status="5";
			//return;
		}
		
		if(!rechargeService.getIsDeviceSupport().get(user.getIsDeviceSupport()).equals("T")){
			//LOGGER.info("User IsDeviceSupport parameter is False, not giving the referal amount ettId={},refEttId={},user deviceId={},isDeviceSupport={}",user.getEttId(),userRef.getEttId(), user.getDeviceId(),user.getIsDeviceSupport());
			LOGGER.info("IsDeviceSupport Check fail not giving the promotion callback checkFail=IsDeviceSupport,ettId={},IsDeviceSupport={}",user.getEttId(),user.getIsDeviceSupport());
			status="5";
			//return;
		}

		if(ettApis.isFraudPreventionCheck(user)) {
			//this is because the user all mark as fake registration
			if(user.getStatus()<2) {
				LOGGER.info("User in fraud list so not giving the promotion callback ettId={}",user.getEttId());
			user.setStatus(2);
			userRepository.save(user);
			}
			status="5";
			//return;
		}
		/*List<String> utmMedium = new ArrayList<String>();
		if(userSource.getUtmSource() != null) {
			utmMedium.add(userSource.getUtmMedium());
		}
		List<PROMOTION_CLICK_EVENT> PromotionClickEvent = pROMOTION_CLICK_EVENTRepository.findByClick_idPromotion(utmMedium);*/
		if(PromotionClickEvent != null && PromotionClickEvent.size()>0){
			PROMOTION_CLICK_EVENT pcEvent = PromotionClickEvent.get(0);
			LOGGER.info("PROMOTION_CLICK_EVENT status parameter set status={},ettId={}",status,user.getEttId());
			pcEvent.setStatus(status);
			pROMOTION_CLICK_EVENTRepository.save(pcEvent);
			}
		
	}
	
	@Override
	public void setPromotionClickEventFirstAppDownload(User user) {
		UserSource userSource = userSourceRepository.findOne(user.getEttId());
		List<String> utmMedium = new ArrayList<String>();
		if(userSource.getUtmSource() != null) {
			utmMedium.add(userSource.getUtmMedium());
		}
		List<Long> userSameDevice = userRepository.findByDeviceId_3(user.getDeviceId());
		//1 device is for the actual user.
		if(userSameDevice.size()>1) {
			LOGGER.info("same deviceId is found for the deviceID before that so dump request in referal refEttId={},user deviceId={}, user ettId={}",userSource.getEttId(), user.getDeviceId(),user.getEttId());
			return;
		}
		List<PROMOTION_CLICK_EVENT> PromotionClickEvent = pROMOTION_CLICK_EVENTRepository.findByClick_idPromotion(utmMedium);
		if(PromotionClickEvent != null && PromotionClickEvent.size()>0){
			PROMOTION_CLICK_EVENT pcEvent = PromotionClickEvent.get(0);
			pcEvent.setStatus_cpa(2);
			pROMOTION_CLICK_EVENTRepository.save(pcEvent);
			LOGGER.info("CPR campain status set 2 for ettId={},utmMedium={}",user.getEttId(),userSource.getUtmMedium());
		}
	}
	@Override
	public void rewardReffrerOnDownloadEtt(User user) {
		UserSource userSource = userSourceRepository.findOne(user.getEttId());
		if (userSource == null) {
			LOGGER.error("user source not found " + user.getEttId());
			return;
		}
		if (userSource.getUtmMedium().equals("INVITE")) {
			if (rechargeService.getAppConfig().get("INVITE_OFFER_AVAILABLE").equals("true")) {
				// increase act. amount
				Long refererId = 0l;
				try {
					refererId = Long.valueOf(userSource.getUtmSource());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

				// ################device same device ID Check
				// ############################
				User userRef = userRepository.findByEttId(refererId);
				if(userRef==null) {
					LOGGER.info("[rewardReffrerOnDownloadEtt] user Referal is null deviceID same ettId={}",user.getEttId());
					
					return;
				}
				
				giveBpartyMoneyOnReg(user,userRef);
				//add in Invitee Msisdn List
				addInInviteeMsisdn(refererId, "0"+user.getMsisdn());
				//if(user.getStatus()>=2 || userRef.getStatus()>=2) {
				if(user.getStatus()==3) {
					LOGGER.info("A party or B party in fraud list so not giving the invite amount Aparty={},Bparty={},Apartystatus={},Bpartystatus={}",userRef.getEttId(),user.getEttId(),userRef.getStatus(),user.getStatus());
					return;
				}
				if (user.getDeviceId().equals(userRef.getDeviceId())) {
					LOGGER.info("[rewardReffrerOnDownloadEtt] deviceID same ettId={}, ettId referer {}",user.getEttId(), userRef.getEttId());
					LOGGER.info("DeviceId Check fail not giving invite money checkFail=DeviceId,ettId={},DeviceId={}",user.getEttId(),user.getDeviceId());
					return;
				}
				// ################device same device ID Check

				// ############## some count strict ########
				if (isInviteMoneyLeave(userRef)) {
					LOGGER.info("[IS_INVITE_MONEY_LEAVE_AVAILABLE] parameter fails the referal amount ettId={},aparty={}",user.getEttId(), userRef.getEttId());
							userAccountSummaryRepository.deleteEttId_ref(userRef.getEttId(), 920l,"INVITE_" + user.getMsisdn());
							return;
				}
				
				List<Long> userSameDevice = userRepository.findByDeviceId_3(user.getDeviceId());
				//1 device is for the actual user.
				if(userSameDevice.size()>1) {
					LOGGER.info("same deviceId is found for the deviceID before that so dump request in referal refEttId={},user deviceId={}, user ettId={}",userRef.getEttId(), user.getDeviceId(),user.getEttId());
					LOGGER.info("DeviceId Check fail not giving invite money checkFail=DeviceId,ettId={},DeviceId={}",user.getEttId(),user.getDeviceId());
					return;
				}
				
				List<UserDeviceIdChange> userDeviceIdChange = userDeviceIdChangeRepository.findByOldDeviceId(user.getDeviceId());
				if(userDeviceIdChange != null && userDeviceIdChange.size()>0){
					LOGGER.info("[Reward Barred/UserDeviceIdChange] deviceId={}, user ettId={},refEttId={}  has previously installed",user.getDeviceId(),user.getEttId(),userRef.getEttId());
					LOGGER.info("UserDeviceIdChange Check fail not giving invite money checkFail=UserDeviceIdChange,ettId={},UserDeviceIdChange={}",user.getEttId(),user.getDeviceId());
					return;
				}
				
				if(!rechargeService.getIsDeviceSupport().get(user.getIsDeviceSupport()).equals("T")){
					LOGGER.info("User IsDeviceSupport parameter is False, not giving the referal amount ettId={},refEttId={},user deviceId={},isDeviceSupport={}",user.getEttId(),userRef.getEttId(), user.getDeviceId(),user.getIsDeviceSupport());
					LOGGER.info("IsDeviceSupport Check fail not giving invite money checkFail=IsDeviceSupport,ettId={},IsDeviceSupport={}",user.getEttId(),user.getIsDeviceSupport());
					return;
				}
				
				//new fruad prevention checks on 22-08-2015
				if(ettApis.isFraudPreventionCheck(user)) {
					//this is because the user all mark as fake registration
					if(user.getStatus()<2) {
					user.setStatus(2);
					userRepository.save(user);
					}
					return;
				}
				
				
				UserAccountSummary userAccountSummaryCheck = userAccountSummaryRepository.findByEttIdAndOfferIdAndOfferName(refererId,121l,"INVITE_"+user.getMsisdn());
				if(userAccountSummaryCheck==null) {
				UserAccount userAccount = userAccountRepository.findByEttId(refererId);
				if (userAccount != null) {
						userAccount.setCurrentBalance((userAccount.getCurrentBalance() + Integer.valueOf(rechargeService.getAppConfig().get("INVITE_MONEY"))));
						userAccountRepository.save(userAccount);
						UserAccountSummary userAccountSummary = new UserAccountSummary();
						userAccountSummary.setEttId(refererId);
						userAccountSummary.setOfferId(121l);
						userAccountSummary.setOfferName("INVITE_"+ user.getMsisdn());
						userAccountSummary.setAmount(Integer.valueOf(rechargeService.getAppConfig().get("INVITE_MONEY")));
						userAccountSummary.setRemarks(userSource.getUtmTerm());
						//userAccountSummary.setRemarks(user.getEttId()+"");
						userAccountSummaryRepository.save(userAccountSummary);
						User refererUser = userRepository.findByEttId(refererId);
						LOGGER.info("ett verified giving reward to referrer| referrer ettId {}, user ettId",refererId, user.getEttId());
						// send friend join push
						//final DeviceToken dToken = deviceTokenRepository.findByDeviceId(refererUser.getDeviceId());
						final DeviceToken dToken = deviceTokenRepository.findByEttId(refererUser.getEttId());
						StringBuffer sib = new StringBuffer(user.getMsisdn());
						sib.replace(2, 6, "xxxx");
						String pushText = rechargeService.getAppConfig().get("INVITE_PUSH_TEXT").replaceFirst("#MSISDN#", sib.toString());
						pushText = pushText.replaceFirst("#AMOUNT#",Integer.valueOf(rechargeService.getAppConfig().get("INVITE_MONEY")) + "");
						sendPush(pushText, dToken,refererUser.getEttId());
						
						//final DeviceToken dToken1 = deviceTokenRepository.findByEttId(user.getEttId());
						StringBuffer sb = new StringBuffer(refererUser.getMsisdn());
						sb.replace(2,6, "xxxx");
						pushText = rechargeService.getAppConfig().get("INVITE_MONEY_EARN_PUSH_BPARTY").replaceFirst("#MSISDN#", sb.toString()).replaceFirst("#JOINAMOUNT#", rechargeService.getAppConfig().get("INVITE_MONEY")).replaceFirst("#FIRST_APP_DOWN_MONEY#", rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_MONEY"));
						//sendPush(pushText, dToken1, user.getEttId());
						SchedulePush schedulePush = new SchedulePush();
						schedulePush.setEttId(user.getEttId());
						schedulePush.setMessage(pushText);
						java.util.Date datetime = new java.util.Date();
						datetime.setTime(datetime.getTime()+60000);
						schedulePush.setPushTime(datetime);
						schedulePushRepository.save(schedulePush);
					}
				}
				else {
						LOGGER.info("[Allready given the referal amount to this user ettId={},refEttId{},refMsisdn{}",user.getEttId(),refererId,userRef.getMsisdn());
				}
			}
		}
	}

	public void giveBpartyMoneyOnReg(User user,User userRef) {
		if(rechargeService.getAppConfig().get("BPARTY_REG_MONEY_FLAGE").equals("true")) {
			
			List<UserAccountSummary> userAccountSummaryCheck = userAccountSummaryRepository.findByEttIdAndOfferId(user.getEttId(),928l);
			if(userAccountSummaryCheck!=null && userAccountSummaryCheck.size()>0) {
				LOGGER.info("allready given the joning bonus to this ettId={}",user.getEttId());
				return;
			}
			
			UserAccount userAccount = userAccountRepository.findByEttId(user.getEttId());
			if(userAccount==null) {
				userAccount = new UserAccount();
			}
			userAccount.setCurrentBalance(userAccount.getCurrentBalance()+Integer.parseInt(rechargeService.getAppConfig().get("BPARTY_INVITE_REG_MONEY")));
			userAccountRepository.save(userAccount);
			
			UserAccountSummary userAccountSummary = new UserAccountSummary();
			userAccountSummary.setEttId(user.getEttId());
			userAccountSummary.setOfferId(928l);
			userAccountSummary.setOfferName("joining bonus");
			userAccountSummary.setAmount(Integer.parseInt(rechargeService.getAppConfig().get("BPARTY_INVITE_REG_MONEY")));
			userAccountSummary.setRemarks("REG_MONEY");
			//userAccountSummary.setRemarks(user.getEttId()+"");
			userAccountSummaryRepository.save(userAccountSummary);
			
			String pushText = rechargeService.getLocaleTextTemplate().get("BPARTY_INVITE_REG_MONEY_PUSH_"+user.getLocale()).replaceFirst("#BPARTY_INVITE_REG_MONEY#", rechargeService.getAppConfig().get("BPARTY_INVITE_REG_MONEY"));
			//sendPush(pushText, dToken1, user.getEttId());
			SchedulePush schedulePush = new SchedulePush();
			schedulePush.setEttId(user.getEttId());
			schedulePush.setMessage(pushText);
			java.util.Date datetime = new java.util.Date();
			datetime.setTime(datetime.getTime()+5000);
			schedulePush.setPushTime(datetime);
			schedulePushRepository.save(schedulePush);
		}
	}
	
	@Override
	public void rewardReffrerOnDownloadEttCoin(User user) {
		UserSource userSource = userSourceRepository.findOne(user.getEttId());
		if (userSource == null) {
			LOGGER.error("user source not found " + user.getEttId());
			return;
		}
		if (userSource.getUtmMedium().equals("INVITE")) {
			if (rechargeService.getAppConfig().get("INVITE_OFFER_AVAILABLE").equals("true")) {
				// increase act. amount
				Long refererId = 0l;
				try {
					refererId = Long.valueOf(userSource.getUtmSource());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

				// ################device same device ID Check
				// ############################
				User userRef = userRepository.findByEttId(refererId);
				if (user.getDeviceId().equals(userRef.getDeviceId())) {
					LOGGER.info(
							"[rewardReffrerOnDownloadEttCoin] deviceID same ettId={}, ettId referer {}",
							user.getEttId(), userRef.getEttId());
					return;
				}
				// ################device same device ID Check
				// ############################
				// ############## some count strict ########
				if (isInviteMoneyLeave(userRef)) {
					LOGGER.info(
							"[IS_INVITE_MONEY_LEAVE_AVAILABLE] parameter fails the referal amount ettId={},aparty={}",
							user.getEttId(), userRef.getEttId());
					userAccountSummaryRepository.deleteEttId_ref(
							userRef.getEttId(), 920l,
							"INVITE_" + user.getMsisdn());
					return;
				}

				// ##### check that same deviceID got the reward or not
				List<User> userSameDevice = userRepository
						.findByDeviceIdIsDownLoad_1(user.getDeviceId());
				if (userSameDevice.size() > 0) {
					LOGGER.info(
							"Same device ID allready got the reward for Invite to dump this referal ettId{},user deviceId{}, user ettId{}",
							userRef.getEttId(), user.getDeviceId(),
							user.getEttId());
					return;
				}
				// ##### check that same deviceID got the reward or not
				List<UserDeviceIdChange> userDeviceIdChange = userDeviceIdChangeRepository.findByOldDeviceId(user.getDeviceId());
				if(userDeviceIdChange != null && userDeviceIdChange.size()>0){
					LOGGER.info("[Reward Barred/UserDeviceIdChange] deviceId={}, user ettId={},refEttId={}  has previously installed",user.getDeviceId(),user.getEttId(),userRef.getEttId());
					return;
				}
				if(!rechargeService.getIsDeviceSupport().get(user.getIsDeviceSupport()).equals("T")){
					LOGGER.info("User IsDeviceSupport parameter is False, not giving the referal amount ettId={},refEttId={},user deviceId={},isDeviceSupport={}",user.getEttId(),userRef.getEttId(), user.getDeviceId(),user.getIsDeviceSupport());
					return;
				}
				UserAccount userAccount = userAccountRepository
						.findByEttId(refererId);

				if (userAccount != null) {
					userAccount.setCurrentBalance((userAccount
							.getCurrentBalance() + Integer.valueOf(rechargeService.getAppConfig().get("INVITE_MONEY"))));
					userAccount
							.setBalanceCoins((userAccount.getBalanceCoins() + Integer
									.valueOf(rechargeService.getAppConfig().get("INVITE_COIN"))));
					userAccountRepository.save(userAccount);
					UserAccountSummary userAccountSummary = new UserAccountSummary();
					userAccountSummary.setEttId(refererId);
					userAccountSummary.setOfferId(121l);
					userAccountSummary.setOfferName("INVITE_"
							+ user.getMsisdn());
					userAccountSummary.setAmount(Integer.valueOf(rechargeService.getAppConfig().get("INVITE_MONEY")));
					// userAccountSummary.setCoin(Integer.valueOf(properties.getProperty("INVITE_COIN")));
					userAccountSummary.setRemarks(userSource.getUtmTerm());
					//userAccountSummary.setRemarks(user.getEttId()+"");
					userAccountSummaryRepository.save(userAccountSummary);
					User refererUser = userRepository.findByEttId(refererId);
					LOGGER.info(
							"ett verified giving reward to referrer| referrer ettId {}, user ettId",
							refererId, user.getEttId());

					UserGameCreditSummary userGameCreditSummary = new UserGameCreditSummary();
					userGameCreditSummary.setEttId(refererId);
					userGameCreditSummary.setOfferId(121l);
					userGameCreditSummary.setOfferName("INVITE_"
							+ user.getMsisdn());
					// userGameCreditSummary.setAmount(Integer.valueOf(properties.getProperty("INVITE_MONEY")));
					userAccountSummary.setCoin(Integer.valueOf(rechargeService.getAppConfig().get("INVITE_COIN")));
					userGameCreditSummary.setRemarks(userSource.getUtmTerm());
					//userAccountSummary.setRemarks(user.getEttId()+"");
					userGameCreditSummaryRepository.save(userGameCreditSummary);
					LOGGER.info(
							"ett verified giving coin to referrer| referrer ettId {}, user ettId",
							refererId, user.getEttId());

					// send friend join push
					//final DeviceToken dToken = deviceTokenRepository.findByDeviceId(refererUser.getDeviceId());
					final DeviceToken dToken = deviceTokenRepository.findByEttId(refererUser.getEttId());
					String pushText = rechargeService.getLocaleTextTemplate().get("INVITE_PUSH_TEXT_COIN_"+user.getLocale()).replaceFirst("#MSISDN#",
							user.getMsisdn());
					pushText = pushText.replaceFirst(
							"#AMOUNT#",
							Integer.valueOf(rechargeService.getLocaleTextTemplate().get("INVITE_MONEY_"+user.getLocale())) + "");
					sendPush(pushText, dToken,refererUser.getEttId());
				}
			}
		}
	}

	@Override
	public void rewardReffrerOnFirstAppInstall(User user) {
		UserSource userSource = userSourceRepository.findOne(user.getEttId());
		if (userSource == null) {
			LOGGER.error("user source not found " + user.getEttId());
			return;
		}
		// LOGGER.error("inside rewardReffrerOnFirstAppInstall");
		// LOGGER.info("befor INVITE]");
		if (userSource.getUtmMedium().equals("INVITE")) {
			// LOGGER.info("[AFTER INVITE]");
			if (rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_AVAILABLE").equals("true")) {
				// LOGGER.info("[INVITE_OFFER_FIRST_DOWNLOAD_AVAILABLE]");
				/*
				 * Period p = new Period( new
				 * DateTime(userSource.getCreatedTime()), new DateTime(new
				 * Date())); int days = p.getDays(); // increase act. amount
				 * LOGGER.info("[days][" + days + "]");
				 */
				Date date = DateTime
						.now(DateTimeZone.forOffsetMillis(19800 * 1000))
						.toDateMidnight().toDate();
				//int days = (int) ((date.getTime() - userSource.getCreatedTime().getTime()) / (1000 * 60 * 60 * 24));
				int hour = (int) ((date.getTime() - userSource.getCreatedTime().getTime()) / (1000 * 60 * 60));

				LOGGER.info("ettId={} days={}", userSource.getEttId(), hour);
				if (hour <= Integer.parseInt(rechargeService.getAppConfig().get("DAY_BETWEEN_INSTALL_DOWNLOAD_CHECK"))) {
					LOGGER.info("source ettId{},ettId {}, Day Difference{}," + userSource.getEttId() + "," + user.getEttId() + "," + hour + "]");
					Long refererId = 0l;
					try {
						refererId = Long.valueOf(userSource.getUtmSource());
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}

					// ################device same device ID Check
					// ############################
					User userRef = userRepository.findByEttId(refererId);
					if (user.getDeviceId().equals(userRef.getDeviceId())) {
						LOGGER.info(
								"[rewardReffrerOnFirstAppInstall] deviceID same ettId={}, ettId referer {}",
								user.getEttId(), userRef.getEttId());
						return;
					}
					// ################device same device ID Check
					// ############## some count strict ########
					if (isInviteMoneyLeave(userRef)) {
						LOGGER.info(
								"[IS_INVITE_MONEY_LEAVE_AVAILABLE] parameter fails the referal amount ettId={},aparty={}",user.getEttId(), userRef.getEttId());
						userAccountSummaryRepository.deleteEttId_ref(userRef.getEttId(), 920l,"INVITE_" + user.getMsisdn());
						return;
					}
					// ############################
					// ##### check that same deviceID got the reward or not
					List<User> userSameDevice = userRepository.findByDeviceIdIsDownLoad_1(user.getDeviceId());
					if (userSameDevice.size() > 0) {
						LOGGER.info("Same device ID allready got the reward for Invite to dump this referal ettId{},user deviceId{}, user ettId{}",userRef.getEttId(), user.getDeviceId(),user.getEttId());
						return;
					}
					// ##### check that same deviceID got the reward or not
					if(!rechargeService.getIsDeviceSupport().get(user.getIsDeviceSupport()).equals("T")){
						LOGGER.info("User IsDeviceSupport parameter is False, not giving the referal amount ettId={},refEttId={},user deviceId={},isDeviceSupport={}",user.getEttId(),userRef.getEttId(), user.getDeviceId(),user.getIsDeviceSupport());
						return;
					}
					List<UserDeviceIdChange> userDeviceIdChange = userDeviceIdChangeRepository.findByOldDeviceId(user.getDeviceId());
					if(userDeviceIdChange != null && userDeviceIdChange.size()>0){
						LOGGER.info("[Reward Barred/UserDeviceIdChange] deviceId={}, user ettId={},refEttId={}  has previously installed",user.getDeviceId(),user.getEttId(),userRef.getEttId());
						return;
					}
					UserAccount userAccount = userAccountRepository.findByEttId(refererId);

					if (userAccount != null) {
						userAccount.setCurrentBalance((userAccount.getCurrentBalance() + Integer.valueOf(rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_MONEY"))));
						if (rechargeService.getAppConfig().get("BONUS_FLAG").equals("true")) {
							userAccount
									.setBalanceBonus(userAccount
											.getBalanceBonus()
											+ Integer.valueOf(rechargeService.getAppConfig().get("BONUS_AMOUNT_REFERAL_DOWNLOAD")));
						}
						userAccountRepository.save(userAccount);
						UserAccountSummary userAccountSummary = new UserAccountSummary();
						userAccountSummary.setEttId(refererId);
						userAccountSummary.setOfferId(921l);
						userAccountSummary.setOfferName("REFER_"
								+ user.getMsisdn());
						userAccountSummary.setAmount(Integer.valueOf(rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_MONEY")));
						userAccountSummary.setRemarks(userSource.getUtmTerm());
						//userAccountSummary.setRemarks(user.getEttId()+"");

						User refererUser = userRepository
								.findByEttId(refererId);
						LOGGER.info(
								"firstApp downloaded giving reward to referrer| referrer ettId {}, user ettId",
								refererId, user.getEttId());

						String pushText = rechargeService.getLocaleTextTemplate().get(
								"INVITE_OFFER_FIRST_DOWNLOAD_PUSH_TEXT_"+user.getLocation())
								.replaceFirst("#MSISDN#", user.getMsisdn());
						pushText = pushText
								.replaceFirst(
										"#AMOUNT#",
										Integer.valueOf(rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_MONEY"))
												+ "");

						if (rechargeService.getAppConfig().get("BONUS_FLAG").equals("true")) {
							// userAccount.setBalanceBonus(userAccount.getBalanceBonus()+Integer.valueOf(properties.getProperty("BONUS_AMOUNT_REFERAL_DOWNLOAD")));
							UserBonusCreditSummary userBonusCreditSummary = new UserBonusCreditSummary();
							userBonusCreditSummary.setEttId(refererId);
							userBonusCreditSummary.setOfferId(921l);
							userBonusCreditSummary.setOfferName("APP_DOWN_"
									+ user.getMsisdn());
							userBonusCreditSummary.setAmount(Integer.valueOf(rechargeService.getAppConfig().get("BONUS_AMOUNT_REFERAL_DOWNLOAD")));
							userBonusCreditSummary.setRemarks(userSource.getUtmTerm());
							//userAccountSummary.setRemarks(user.getEttId()+"");
							userBonusCreditSummaryRepository
									.save(userBonusCreditSummary);
							LOGGER.info(
									"firstApp downloaded giving bonus to referrer| referrer ettId {}, user ettId",
									refererId, user.getEttId());
							pushText = rechargeService.getLocaleTextTemplate().get(
											"INVITE_OFFER_FIRST_DOWNLOAD_PUSH_TEXT_BONUS_"+user.getLocale())
									.replaceFirst("#MSISDN#", user.getMsisdn());
							pushText = pushText
									.replaceFirst(
											"#BONUS_AMOUNT_REFERAL_DOWNLOAD#",
											rechargeService.getAppConfig().get("BONUS_AMOUNT_REFERAL_DOWNLOAD"));
							pushText = pushText.replaceFirst("#TOTALBONUS#",
									userAccount.getBalanceBonus() + "");
							pushText = pushText
									.replaceFirst(
											"#AMOUNT#",
											Integer.valueOf(rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_MONEY"))
													+ "");

						}

						userAccountSummaryRepository.save(userAccountSummary);
						// send friend join push
						//final DeviceToken dToken = deviceTokenRepository.findByDeviceId(refererUser.getDeviceId());
						final DeviceToken dToken = deviceTokenRepository.findByEttId(refererUser.getEttId());

						sendPush(pushText, dToken,refererUser.getEttId());
					}
				}
			}
		}
	}
	@Override
	public void inviteSomeMoneyOnCallBack(User user) {
		UserSource userSource = userSourceRepository.findOne(user.getEttId());
		if (userSource == null) {
			LOGGER.error("user source not found " + user.getEttId());
			return;
		}
		if (userSource.getUtmMedium().equals("INVITE")) {
			Long refererId = 0l;
			
			Date date = DateTime.now(DateTimeZone.forOffsetMillis(19800 * 1000)).toDateMidnight().toDate();
			int hour = (int) ((date.getTime() - userSource.getCreatedTime().getTime()) / (1000 * 60 * 60));

			LOGGER.info("ettId {} days{}", userSource.getEttId(), hour);
			if (hour > Integer.parseInt(rechargeService.getAppConfig().get("DAY_BETWEEN_INSTALL_DOWNLOAD_CHECK"))) {
						LOGGER.info("Invite money not giving as the hour count cross Day Difference={} User ettId{}, Referal User ettId{}",hour,user.getEttId(),refererId);
						return;
				}
			try {
					refererId = Long.valueOf(userSource.getUtmSource());
				} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			User userRef = userRepository.findByEttId(refererId);
			if (user.getDeviceId().equals(userRef.getDeviceId())) {
				LOGGER.info("[rewardReffrerOnFirstAppInstallCoin] deviceID same ettId={}, ettId referer {}",user.getEttId(), userRef.getEttId());
				return;
			}
			/** Removing from Invitee Msisdn table **/
			removeFromInviteeMsisdn(refererId,"0"+user.getMsisdn());
			
			List<User> userSameDevice = userRepository.findByDeviceIdIsDownLoad_1(user.getDeviceId());
			if (userSameDevice.size() > 0) {
				LOGGER.info("Same device ID allready got the reward for Invite to dump this referal ettId{},user deviceId{}, user ettId{}",userRef.getEttId(),user.getDeviceId(),user.getEttId() + "size"+ userSameDevice.size());
				return;
			}
			List<UserDeviceIdChange> userDeviceIdChange = userDeviceIdChangeRepository.findByOldDeviceId(user.getDeviceId());
			if(userDeviceIdChange != null && userDeviceIdChange.size()>0){
				LOGGER.info("[Reward Barred/UserDeviceIdChange] deviceId={}, user ettId={},refEttId={}  has previously installed",user.getDeviceId(),user.getEttId(),userRef.getEttId());
				return;
			}
				UserAccountSummary userAccountSummary = userAccountSummaryRepository.findByEttIdAndOfferIdAndOfferName(refererId, 121l,"INVITE_"+user.getMsisdn());
				if(userAccountSummary != null){
				//if(userAccountSummary.getRemarks().equals("facebook") && !inviteAppDownLLeaveOnFB(userRef.getEttId(),user.getEttId())) 
				{
			
				//UserAccountSummary userAccountSummary = userAccountSummaryRepository.findByEttIdAndOfferIdAndOfferName(refererId, 121l,"INVITE_"+user.getMsisdn());
					if(!rechargeService.getIsDeviceSupport().get(user.getIsDeviceSupport()).equals("T")){
						LOGGER.info("User IsDeviceSupport parameter is False, not giving the referal amount ettId={},refEttId={},user deviceId={},isDeviceSupport={}",user.getEttId(),userRef.getEttId(), user.getDeviceId(),user.getIsDeviceSupport());
						return;
					}
					UserAccount userAccount = userAccountRepository.findByEttId(refererId);
					userAccount.setCurrentBalance(userAccount.getCurrentBalance()+Integer.valueOf(rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_MONEY")));
					userAccountRepository.save(userAccount);
					
					userAccountSummary.setAmount(userAccountSummary.getAmount()+Integer.valueOf(rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_MONEY")));
					userAccountSummary.setOfferName("REFER_" + user.getMsisdn());
					//userAccountSummary.setRemarks(getCurrentTimeStamp());
					userAccountSummary.setVendor(getCurrentTimeStamp());
					userAccountSummaryRepository.save(userAccountSummary);
					final DeviceToken dToken = deviceTokenRepository.findByEttId(refererId);
					String pushText = rechargeService.getLocaleTextTemplate().get("INVITE_OFFER_FIRST_DOWNLOAD_PUSH_TEXT_"+user.getLocale()).replaceFirst("#MSISDN#", user.getMsisdn());
					pushText = pushText.replaceFirst("#AMOUNT#",Integer.valueOf(rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_MONEY")) + "");
					sendPush(pushText, dToken,refererId);
					
					final DeviceToken dToken2 = deviceTokenRepository.findByEttId(user.getEttId());
					StringBuffer sb = new StringBuffer(userRef.getMsisdn());
					sb.replace(2,6, "xxxx");
					pushText = rechargeService.getLocaleTextTemplate().get("INVITE_OFFER_FIRST_DOWNLOAD_PUSH_TEXT_BPARTY_"+user.getLocale()).replaceFirst("#MSISDN#", sb.toString()).replaceFirst("#FIRST_APP_DOWN_MONEY#", rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_MONEY"));
					sendPush(pushText, dToken2,user.getEttId());
					
				}
			}
				
		}
	}

	private void removeFromInviteeMsisdn(Long refererId, String msisdnToRemove) {
		InviteeMsisdn inviteeMsisdn = inviteeMsisdnRepository.findByEttId(refererId);
		if(inviteeMsisdn != null){
			String msisdn = inviteeMsisdn.getMsisdn();
			int index = msisdn.indexOf(msisdnToRemove);
			if(index >= 0){
				msisdn = msisdn.replace(msisdnToRemove, "");
				inviteeMsisdn.setMsisdn(msisdn);
				inviteeMsisdnRepository.save(inviteeMsisdn);
				LOGGER.info("msisdn={}, removed from Invitte List of ettId={}",msisdnToRemove,refererId);
			}
		}
	}
	public String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
	public boolean inviteAppDownLLeaveOnFB(Long refEttId,Long ettId) {
		if(rechargeService.getAppConfig().get("IS_INVITE_DOWNL_FB_MONEY_LEAVE_AVAILABLE").equals("true")){
			int count = 0;
			List<UserAccountSummary> facebookList = userAccountSummaryRepository.findByEttIdAndOfferIdAndRemarks(refEttId,121l,"facebook");
			if(facebookList!=null) {
				count=facebookList.size();
			}
			int limit = Integer.parseInt(rechargeService.getAppConfig().get("IS_INVITE_DOWNL_FB_MONEY_LEAVE_NUMBER"));
			if ((count + 1) % limit == 0) {
				LOGGER.info("not giving the amount for refEttId{},ettId{} because of facebook",refEttId,ettId);
				return true;
			}
		}
		return false;
	}
	@Override
	public void rewardReffrerOnFirstAppInstallCoin(User user) {
		UserSource userSource = userSourceRepository.findOne(user.getEttId());
		if (userSource == null) {
			LOGGER.error("user source not found " + user.getEttId());
			return;
		}
		// LOGGER.error("inside rewardReffrerOnFirstAppInstall");
		// LOGGER.info("befor INVITE]");
		if (userSource.getUtmMedium().equals("INVITE")) {
			// LOGGER.info("[AFTER INVITE]");
			if (rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_AVAILABLE").equals("true")) {
				// LOGGER.info("[INVITE_OFFER_FIRST_DOWNLOAD_AVAILABLE]");
				/*
				 * Period p = new Period( new
				 * DateTime(userSource.getCreatedTime()), new DateTime(new
				 * Date())); int days = p.getDays();
				 */
				// increase act. amount
				Date date = DateTime.now(DateTimeZone.forOffsetMillis(19800 * 1000)).toDateMidnight().toDate();
				int hour = (int) ((date.getTime() - userSource.getCreatedTime().getTime()) / (1000 * 60 * 60));

				LOGGER.info("ettId={} hour={}", userSource.getEttId(), hour);
				if (hour <= Integer.parseInt(rechargeService.getAppConfig().get("DAY_BETWEEN_INSTALL_DOWNLOAD_CHECK"))) {
					LOGGER.info("Day Difference{}," + hour + "]");
					Long refererId = 0l;
					try {
						refererId = Long.valueOf(userSource.getUtmSource());
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}

					// ################device same device ID Check
					// ############################
					User userRef = userRepository.findByEttId(refererId);
					if (user.getDeviceId().equals(userRef.getDeviceId())) {
						LOGGER.info("[rewardReffrerOnFirstAppInstallCoin] deviceID same ettId={}, ettId referer {}",user.getEttId(), userRef.getEttId());
						return;
					}// ################device same device ID Check
						// ############## some count strict ########
					if (isInviteMoneyLeave(userRef)) {
						LOGGER.info("[IS_INVITE_MONEY_LEAVE_AVAILABLE] parameter fails the referal amount ettId={},aparty={}",user.getEttId(), userRef.getEttId());
						userAccountSummaryRepository.deleteEttId_ref(userRef.getEttId(), 920l,"INVITE_" + user.getMsisdn());
						return;
					}
					// ############################
					// ##### check that same deviceID got the reward or not
					List<User> userSameDevice = userRepository.findByDeviceIdIsDownLoad_1(user.getDeviceId());
					if (userSameDevice.size() > 0) {
						LOGGER.info("Same device ID allready got the reward for Invite to dump this referal ettId{},user deviceId{}, user ettId{}",userRef.getEttId(),user.getDeviceId(),user.getEttId() + "size"+ userSameDevice.size());
						return;
					}
					// ##### check that same deviceID got the reward or not
					if(!rechargeService.getIsDeviceSupport().get(user.getIsDeviceSupport()).equals("T")){
						LOGGER.info("User IsDeviceSupport parameter is False, not giving the referal amount ettId={},refEttId={},user deviceId={},isDeviceSupport={}",user.getEttId(),userRef.getEttId(), user.getDeviceId(),user.getIsDeviceSupport());
						return;
					}
					List<UserDeviceIdChange> userDeviceIdChange = userDeviceIdChangeRepository.findByOldDeviceId(user.getDeviceId());
					if(userDeviceIdChange != null && userDeviceIdChange.size()>0){
						LOGGER.info("[Reward Barred/UserDeviceIdChange] deviceId={}, user ettId={},refEttId={}  has previously installed",user.getDeviceId(),user.getEttId(),userRef.getEttId());
						return;
					}
					UserAccount userAccount = userAccountRepository
							.findByEttId(refererId);

					if (userAccount != null) {
						userAccount.setCurrentBalance((userAccount.getCurrentBalance() + Integer.valueOf(rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_MONEY"))));
						userAccount.setBalanceCoins((userAccount.getBalanceCoins() + Integer.valueOf(rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_COIN"))));
						if (rechargeService.getAppConfig().get("BONUS_FLAG").equals("true")) {
							userAccount.setBalanceBonus(userAccount.getBalanceBonus()+ Integer.valueOf(rechargeService.getAppConfig().get("BONUS_AMOUNT_REFERAL_DOWNLOAD")));
						}
						userAccountRepository.save(userAccount);
						UserAccountSummary userAccountSummary = new UserAccountSummary();
						userAccountSummary.setEttId(refererId);
						userAccountSummary.setOfferId(921l);
						userAccountSummary.setOfferName("REFER_"+ user.getMsisdn());
						userAccountSummary.setAmount(Integer.valueOf(rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_MONEY")));
						// userAccountSummary.setCoin(Integer.valueOf(properties.getProperty("INVITE_OFFER_FIRST_DOWNLOAD_COIN")));
						userAccountSummary.setRemarks(userSource.getUtmTerm());
						//userAccountSummary.setRemarks(user.getEttId()+"");
						userAccountSummaryRepository.save(userAccountSummary);
						User refererUser = userRepository.findByEttId(refererId);
						LOGGER.info("firstApp downloaded giving reward to referrer| referrer ettId {}, user ettId",refererId, user.getEttId());

						UserGameCreditSummary userGameCreditSummary = new UserGameCreditSummary();
						userGameCreditSummary.setEttId(refererId);
						userGameCreditSummary.setOfferId(921l);
						userGameCreditSummary.setOfferName("APP_DOWN_"
								+ user.getMsisdn());
						// userGameCreditSummary.setAmount(Integer.valueOf(properties.getProperty("INVITE_OFFER_FIRST_DOWNLOAD_MONEY")));
						userGameCreditSummary.setCoin(Integer.valueOf(rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_COIN")));
						userGameCreditSummary.setRemarks(userSource.getUtmTerm());
						//userAccountSummary.setRemarks(user.getEttId()+"");
						userGameCreditSummaryRepository
								.save(userGameCreditSummary);
						// User refererUser =
						// userRepository.findByEttId(refererId);
						LOGGER.info(
								"firstApp downloaded giving coin to referrer| referrer ettId {}, user ettId",
								refererId, user.getEttId());
						String pushText = rechargeService.getLocaleTextTemplate().get(
								"INVITE_OFFER_FIRST_DOWNLOAD_PUSH_TEXT_COIN_"+user.getLocale())
								.replaceFirst("#MSISDN#", user.getMsisdn());
						pushText = pushText
								.replaceFirst(
										"#AMOUNT#",
										Integer.valueOf(rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_MONEY"))
												+ "");
						pushText = pushText
								.replaceFirst(
										"#COIN#",
										Integer.valueOf(rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_COIN"))
												+ "");
						if (rechargeService.getAppConfig().get("BONUS_FLAG").equals("true")) {
							UserBonusCreditSummary userBonusCreditSummary = new UserBonusCreditSummary();
							userBonusCreditSummary.setEttId(refererId);
							userBonusCreditSummary.setOfferId(921l);
							userBonusCreditSummary.setOfferName("APP_DOWN_"+ user.getMsisdn());
							userBonusCreditSummary.setAmount(Integer.valueOf(rechargeService.getAppConfig().get("BONUS_AMOUNT_REFERAL_DOWNLOAD")));
							userBonusCreditSummary.setRemarks(userSource.getUtmTerm());
							//userAccountSummary.setRemarks(user.getEttId()+"");
							userBonusCreditSummaryRepository.save(userBonusCreditSummary);
							LOGGER.info("firstApp downloaded giving bonus to referrer| referrer ettId {}, user ettId",refererId, user.getEttId());
							pushText = rechargeService.getLocaleTextTemplate().get("INVITE_OFFER_FIRST_DOWNLOAD_PUSH_TEXT_BONUS_"+user.getLocale()).replaceFirst("#MSISDN#", user.getMsisdn());
							pushText = pushText.replaceFirst("#BONUS_AMOUNT_REFERAL_DOWNLOAD#",rechargeService.getAppConfig().get("BONUS_AMOUNT_REFERAL_DOWNLOAD"));
							pushText = pushText.replaceFirst("#TOTALBONUS#",userAccount.getBalanceBonus() + "");
							pushText = pushText.replaceFirst("#AMOUNT#",Integer.valueOf(rechargeService.getAppConfig().get("INVITE_OFFER_FIRST_DOWNLOAD_MONEY"))+ "");

						}

						// send friend join push
						//final DeviceToken dToken = deviceTokenRepository.findByDeviceId(refererUser.getDeviceId());
						final DeviceToken dToken = deviceTokenRepository.findByEttId(refererUser.getEttId());
						sendPush(pushText, dToken,refererUser.getEttId());
					}
				}
			}
		}
	}

	private boolean isInviteMoneyLeave(User user) {
		if (rechargeService.getAppConfig().get("IS_INVITE_MONEY_LEAVE_AVAILABLE").equals("true")) {
			List<UserAccountSummary> userAccountSummaryList = userAccountSummaryRepository.findByEttIdAndOfferId(user.getEttId(), 921L);
			if (userAccountSummaryList != null) {
				int count = 0;
				Iterator<UserAccountSummary> itr = userAccountSummaryList.iterator();
				while (itr.hasNext()) {
					UserAccountSummary userAccountSummary = itr.next();
					if (userAccountSummary.getCreatedTime().getTime() >= 1421087400000l) // this milliseconds for release date 05-Jan-2015
						count++;
				}
				int limit = Integer.parseInt(rechargeService.getAppConfig().get("IS_INVITE_MONEY_LEAVE_NUMBER"));
				if ((count + 1) % limit == 0) {
					return true;
				}
			}
		}
		return false;
	}

	private void sendPush(final String pushText, final DeviceToken dToken,final Long ettId) {
		if (dToken != null && !"".equals(dToken.getDeviceToken())) {
			try {
				jmsTemplate.send(new MessageCreator() {
					@Override
					public Message createMessage(Session session)
							throws JMSException {

						MapMessage mapMessage = session.createMapMessage();
						mapMessage.setString("ID", dToken.getDeviceToken());
						mapMessage.setString("DISPLAY_STRING", pushText);
						mapMessage.setInt("BADGE_COUNT", 1);
						mapMessage.setLong("ettId", ettId);
						mapMessage.setString("DEVICE_TYPE", "ANDROID");
						mapMessage.setString("type", "BALANCE");
						return mapMessage;
					}
				});
			} catch (Exception e) {
				LOGGER.error("error in edr push " + e);
				e.printStackTrace();
			}
		}
	}


}