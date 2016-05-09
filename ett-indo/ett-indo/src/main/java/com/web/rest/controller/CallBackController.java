package com.web.rest.controller;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.entity.BonusRetargetting;
import com.domain.entity.CallBackConfirmation;
import com.domain.entity.DownloadBoosterEligibleUser;
import com.domain.entity.Msisdn_30;
import com.domain.entity.OfferDetails;
import com.domain.entity.OfferDetails.BalanceType;
import com.domain.entity.OfferDetails.PayoutType;
import com.domain.entity.OffersStarted;
import com.domain.entity.TransactionTracker;
import com.domain.entity.User;
import com.domain.entity.UserAccount;
import com.domain.entity.UserAccountSummary;
import com.domain.entity.UserClickCallBackTracker;
import com.domain.entity.UserDublicateCallBackPrevent;
import com.domain.entity.UserSource;
import com.repository.jpa.BonusRetargettingRepository;
import com.repository.jpa.CallBackConfirmationRepository;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.DownloadBoosterEligibleUserRepository;
import com.repository.jpa.Msisdn_30Repository;
import com.repository.jpa.OfferDetailsRepository;
import com.repository.jpa.OffersStartedRepository;
import com.repository.jpa.PendingCreditsRepository;
import com.repository.jpa.TransactionTrackerRepository;
import com.repository.jpa.UserAccountRepository;
import com.repository.jpa.UserAccountSummaryRepository;
import com.repository.jpa.UserClickCallBackTrackerRepository;
import com.repository.jpa.UserDublicateCallBackPreventRepository;
import com.repository.jpa.UserGameCreditSummaryRepository;
import com.repository.jpa.UserRepository;
import com.repository.jpa.UserSourceRepository;
import com.service.EDRService;
import com.service.EttApis;
import com.service.Msisdn_30Service;
import com.service.RechargeService;
import com.service.ReferrerService;

/**
 * @author ankur
 */
@Controller
@RequestMapping(value = "/v1")
public class CallBackController {

	private static Logger LOGGER = LoggerFactory
			.getLogger(CallBackController.class);

	@Resource
	CallBackConfirmationRepository callBackConfirmationRepository;

	@Resource
	UserAccountRepository userAccountRepository;

	@Resource
	TransactionTrackerRepository transactionTrackerRepository;

	@Resource
	OfferDetailsRepository OfferDetailsRepository;

	@Resource
	UserAccountSummaryRepository userAccountSummaryRepository;
	
	@Resource
	Msisdn_30Repository msisdn_30Repository;

	@Autowired
	UserGameCreditSummaryRepository userGameCreditSummaryRepository;

	@Resource
	private JmsTemplate jmsTemplate;

	@Autowired
	private DeviceTokenRepository deviceTokenRepository;

	@Resource
	private RechargeService rechargeService;

	@Resource
	private OfferDetailsRepository offerDetailsRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Resource
	private DownloadBoosterEligibleUserRepository downloadBoosterEligibleUserRepository;

	@Resource
	UserDublicateCallBackPreventRepository userDublicateCallBackPreventRepository;
	@Resource
	private EttApis ettApis;

	@Autowired
	private UserSourceRepository userSourceRepository;

	@Autowired
	private ReferrerService referrerService;
	
	@Resource
	private BonusRetargettingRepository bonusRetargettingRepository;

	@Resource
	private EDRService edrService;
	@Autowired
	private PendingCreditsRepository pendingCreditsRepository;
	
	@Resource
	private OffersStartedRepository offersStartedRepository;
	@Autowired
	private Msisdn_30Service msisdn_30Service;
	@Autowired
	private UserClickCallBackTrackerRepository userClickCallBackTrackerRepository;

	
	
	@RequestMapping(value = "/callback/{vendorName}", method = {
			RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<?> saveUserProfile(
			HttpServletRequest httpServletRequest,
			@PathVariable("vendorName") String vendorName) {
		Long ettId = null;
		String msisdn = "";
		String tId = "";
		Long offerId = null;
		float amount = 0;
		String offerPaymentType = null;
		Integer currency = null;
		String offerCat = null;
		String clickId = httpServletRequest.getParameter("click_id");
		if(clickId==null || clickId.equals("null")) {
			clickId = httpServletRequest.getParameter("clickId");
		}
		String sale_amt = httpServletRequest.getParameter("sale_amt");
		if(sale_amt==null || sale_amt.equals("null")) {
			sale_amt=null;
		}
		String part_id = httpServletRequest.getParameter("part_id");
		if(part_id==null || part_id.equals("null")) {
			part_id = null;
		}
		String event = httpServletRequest.getParameter("event");
		if(event==null || event.equals("null")) {
			event = null;
		}
		String action = httpServletRequest.getParameter("action");
		if(action==null || action.equals("null")) {
			action = null;
		}
		
		/*################ event parameter handing ###############################################*/
		boolean diffPayMentType = false;
		if(event != null && event.length()>0){
			if(event.trim().equals("0")){
				//LOGGER.info("API /callback/ [Not Accepted]" + vendorName + "/ info " + clickId+ " sale_amt " + sale_amt + " part_id "+part_id);
				//return new ResponseEntity<>(HttpStatus.OK);
				diffPayMentType = true;
			}
		}
		
		
		/*################ event parameter handing ###############################################*/
		if(part_id != null && part_id.length()>0){
			vendorName=part_id;
			LOGGER.info("vendorName is raplaced by part_id :" +part_id+ "/ info " + clickId);
		}
		LOGGER.info("API /callback/" + vendorName + "/ info " + clickId+ " sale_amt " + sale_amt);
		if(vendorName!=null && vendorName.equalsIgnoreCase("unlockar")) {
			clickId = clickId.replace("A", "|");
			clickId = clickId.replace("B", ",");
		}
		String params[] = null;
		User user = null;
		UserClickCallBackTracker userClickCallBackTracker = null;
			try{
					if(clickId.indexOf(",")<0) {
						userClickCallBackTracker = userClickCallBackTrackerRepository.findOne(clickId);
						if(userClickCallBackTracker == null) {
							LOGGER.info("Wrong callBackRequest Found click_id="+clickId+"&sale_amt="+sale_amt+"&part_id="+part_id);
							return new ResponseEntity<>(HttpStatus.OK);
						}
						else {
							ettId=userClickCallBackTracker.getEttId();
						}
					}
					else {
						// clickId,msisdn,ettId,tId,offerId
						params = clickId.split(",");
						ettId = Long.valueOf(params[2]);
						if((params != null && params.length > 5)) {
						offerId = Long.valueOf(params[5]);
						}else {
							offerId = Long.valueOf(params[4]);
							
						}
					}
					user = userRepository.findByEttId(ettId);
					OfferDetails offerDetails = offerDetailsRepository.findByOfferId(offerId);
					if(offerDetails.getOfferPaymentType().equals("CPR") && (event==null || !event.equals("1"))){
						diffPayMentType = true;
					}
					try {
						if(rechargeService.getAppConfig().get("ISWINBACK").equals("true")){
							Msisdn_30 msisdn_30 = msisdn_30Repository.findByettIdStatusType(user.getEttId(), 1);
							if(msisdn_30!=null && msisdn_30.getType()==2) {
								msisdn_30Repository.updateStatus(2,user.getEttId());
								msisdn_30Service.updateAmount(user, msisdn_30,msisdn_30.getAmount2(),8876l,"WINBACK_CALLBACK");
								//sendPush(msisdn_30.getMsg().replaceFirst("#AMOUNT#", msisdn_30.getAmount2()+""), deviceToken);
								}
							}
						}catch(Exception exx) {
							LOGGER.info("error while working for winback ettId{}"+user.getEttId());
							exx.printStackTrace();
					}
					
					
					if ((params != null && params.length > 5) || diffPayMentType || (action!=null && action.equalsIgnoreCase("CW"))) {
						LOGGER.info("click_id length is greater than 5 sending it to udp..."+ clickId);
						DatagramSocket datagramSocket = new DatagramSocket();
						InetAddress localInetAddress = InetAddress.getByName(rechargeService.getAppConfig().get("CALLBACK_UDP_IP"));
						byte[] arrayOfByte = new byte[1024];
						
						String dataSent = "source=" + vendorName + "&click_id="+ clickId + "&sale_amt=" + sale_amt;
						if(diffPayMentType){
							dataSent = dataSent + "&event=0";
						}
						if(action!=null && action.equalsIgnoreCase("CW")) {
							dataSent = dataSent + "&action="+action;
						}
						arrayOfByte = dataSent.getBytes();
						DatagramPacket localDatagramPacket1 = new DatagramPacket(arrayOfByte, arrayOfByte.length, localInetAddress,Integer.parseInt(rechargeService.getAppConfig().get("CALLBACK_UDP_PORT")));
						datagramSocket.send(localDatagramPacket1);
						datagramSocket.close();
						LOGGER.info("Data Sent To UDP SERVER::  " + dataSent);
						return new ResponseEntity<>(HttpStatus.OK);
					}
					
					if(userClickCallBackTracker != null){
						currency = userClickCallBackTracker.getCurrency();
						msisdn = userClickCallBackTracker.getMsisdn();
						ettId= userClickCallBackTracker.getEttId();
						tId = userClickCallBackTracker.getTid();
						amount = userClickCallBackTracker.getIncomeAmount();
						offerCat = userClickCallBackTracker.getOfferCat();
						offerId = userClickCallBackTracker.getOfferId();
						offerPaymentType = userClickCallBackTracker.getOfferPaymentType();
						offerDetails.setOfferAmount(userClickCallBackTracker.getOfferAmount());
						offerDetails.setPendingAmountCredit(userClickCallBackTracker.getPendingAmountCredit());
						offerDetails.setPendingRecCount(userClickCallBackTracker.getPendingRecCount());
						offerDetails.setPendingRecDay(userClickCallBackTracker.getPendingRecDay());
						//offerDetails.setCallbackNotification(userClickCallBackTracker.getCa);
					}
					else{
							if (params!=null && params[0] != null & !params[0].equals("")) {
								String amountCurrency[] = params[0].split("\\|");
								try {
										if (amountCurrency.length <= 1) {
											amountCurrency = params[0].split("#");
										}
									} catch (Exception ex) {
									
									}
							amount = Float.valueOf(amountCurrency[0]);
							if (amountCurrency.length == 2) {
								currency = Integer.valueOf(amountCurrency[1]);
							}
						}
						msisdn = params[1];
						ettId = Long.valueOf(params[2]);
						tId = params[3];
						offerId = Long.valueOf(params[4]);
					}
					
				
			} catch (Exception e) {
				LOGGER.error("error in parsing callback" + e +"[clickedId]["+clickId+"] [error]["+e+"]");
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}
		LOGGER.info("/callback/" + vendorName+ "/ ettId {} | offerId {} | tId {} | msisdn {}", ettId,offerId, tId, msisdn);
		List<CallBackConfirmation> backConfirmations = callBackConfirmationRepository.findByIdDeviceIdFlage(ettId+"_"+offerId);
		if(backConfirmations != null && backConfirmations.size()>=Integer.parseInt(rechargeService.getAppConfig().get("DEVICE_CHANGE_CALLBACKOFFER_THREASHHOLD"))) {
			LOGGER.info("Month Threashhold crossed for ettId {} | offerId {} | tId {} | msisdn {}", ettId,offerId, tId, msisdn);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		int trueCallBackConfirmations=0;
		for(CallBackConfirmation backConfirmation : backConfirmations) {
			if(backConfirmation.isDeviceIdFlage()==true) {
				trueCallBackConfirmations++;
			}
		}
		//if(backConfirmations != null && backConfirmations.size()>0) {
		if(trueCallBackConfirmations>0) {
			LOGGER.info("dublicate callback found ettId {} | offerId {} | tId {} | msisdn {}", ettId,offerId, tId, msisdn);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			
			/************** we are getting the multiple callback in same time so preventing this**********************/
			try {
				UserDublicateCallBackPrevent backPrevent = new UserDublicateCallBackPrevent();
				backPrevent.setEttId(ettId);
				backPrevent.setId(ettId+"_"+offerId);
				backPrevent.setCreatedTime(new java.util.Date());
				userDublicateCallBackPreventRepository.save(backPrevent);
			}
			catch(Exception exception) {
				LOGGER.info("inserting in UserDublicateCallBackPreventEttId dublicate callback found ettId {} | offerId {} | tId {} | msisdn {}", ettId,offerId, tId, msisdn);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		/************** we are getting the multiple callback in same time so preventing this**********************/
		CallBackConfirmation callBackConfirmation = new CallBackConfirmation();
		callBackConfirmation.setEttId(ettId);
		callBackConfirmation.setMsisdn(msisdn);
		callBackConfirmation.setOfferId(offerId);
		callBackConfirmation.settId(tId);
		callBackConfirmation.setVendor(vendorName);
		callBackConfirmation.setAmount(amount);
		callBackConfirmation.setCurrency(currency);
		callBackConfirmation.setOfferCat(offerCat);
		try {
			callBackConfirmation = callBackConfirmationRepository.save(callBackConfirmation);
			} catch (org.springframework.dao.DataIntegrityViolationException e) {
				LOGGER.error("repeating request " + e);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}
		if(rechargeService.getAppConfig().get("NEW_USER_BONUS_RETARGETTING").equals("true") && user.getTotlNoOfDLoadApp()<= Integer.parseInt(rechargeService.getAppConfig().get("NEW_USER_BONUS_RETARGETTING_OFFER_COUNT")) && user.getCreatedTime().getTime() >Long.parseLong(rechargeService.getAppConfig().get("NEW_USER_BONUS_ELIGIBILITY_TIME_FROM"))){
			BonusRetargetting bonusRetargetting = bonusRetargettingRepository.findByEttId(ettId);
			if(bonusRetargetting != null && !bonusRetargetting.equals("")){
				OfferDetails offerDetails = OfferDetailsRepository.findByOfferId(offerId);
				if(offerDetails != null && !offerDetails.equals("")){
					float offerGiveAmount = (float)Math.ceil((((offerDetails.getOfferAmount()-offerDetails.getPendingAmountCredit())*Integer.parseInt(rechargeService.getAppConfig().get("NEW_USER_BONUS_RETARGETTING_PERCENTAGE")))/100));
					List<UserAccountSummary> userAccountSummaryList = userAccountSummaryRepository.findByEttIdAndOfferId(ettId,offerDetails.getOfferId());
					if ((userAccountSummaryList == null || userAccountSummaryList.size() <= 0) && offerGiveAmount>0) {
						rechargeService.giveBonusRetargettingAmountToUser(user, tId, offerDetails, offerGiveAmount);
					}
				}
			}
			
		}
		if (!user.isDownloadedFirstApp()) {
			if (user.isVerified()) {
				if(rechargeService.getAppConfig().get("INVITE_OFFER_ETT_DOWNLOAD_CALLBACK_AVAILABLE").equals("true")){
					referrerService.inviteSomeMoneyOnCallBack(user);
				}
				/*else if (user.getAppVersion().equals("1.2")) {
						referrerService.rewardReffrerOnFirstAppInstallCoin(user);
				} else{
					referrerService.rewardReffrerOnFirstAppInstallCoin(user);
				}*/
				user.setDownloadedFirstApp(true);
				userRepository.save(user);
				referrerService.setPromotionClickEventFirstAppDownload(user);
				} else {
					LOGGER.error("user is not verified so not giving the referal amount to aparty ettId={},clickId{},"+ ettId, clickId);
				}
			}

		// maintain transaction tracker
		// Check of clickMachine offerId
		OffersStarted offersStarted = offersStartedRepository.findOne(ettId + "_" + offerId);
		if (!(rechargeService.getOfferIdIgnoreList().contains(offerId))) {

			TransactionTracker tracker = new TransactionTracker();

			tracker.setEttId(ettId);
			tracker.setOfferId(offerId);
			tracker.setCallBackTime(new Date());
			tracker.setRemark("CALLBACK_" + vendorName);
			
			OfferDetails offerDetails = OfferDetailsRepository.findByOfferId(offerId);
			 
			if (offerDetails == null) {
				LOGGER.error("offer id not found in db " + offerId + "&ettId="+ ettId);
				return new ResponseEntity<>(HttpStatus.MOVED_TEMPORARILY);
			}
			try {
				tracker = transactionTrackerRepository.save(tracker);
				LOGGER.info("save callback in TransactionTracker table : "+ ettId);
			
				/*--------------------- Offers Started ------------------------------------------------*/
				
				if(offersStarted == null) {
					/*edrService.setOffersStartedDetails(offersStarted, offerDetails, ettId, offerId, PayOutType.INSTALL);*/
					offersStarted = edrService.setOffersStartedDetails(offersStarted, offerDetails, ettId, offerId, "INSTALL",user.getAppVersion(),user);
				}
				  /*--------------------- Offers Started ------------------------------------------------*/
				
				// if balance type in offerDeatails table is install/ANY
				// increase act. amount
				if ((offerDetails.getBalanceType().equals(BalanceType.ANY) || offerDetails.getBalanceType().equals(BalanceType.CALLBACK))) {
					rechargeService.giveBalance(ettId, tId, offerDetails,vendorName,user);
					if(rechargeService.getAppConfig().get("DOWNLOAD_BOOSTER_ENABLE").equals("true")){
						DownloadBoosterEligibleUser downloadBoosterEligibleUser = downloadBoosterEligibleUserRepository.findByEttId(user.getEttId());
						if(downloadBoosterEligibleUser != null){
							ettApis.giveDownloadBoosterAmount(user, downloadBoosterEligibleUser);
							LOGGER.info("ettId={} BoosterAmount={} credited in a/c",user.getEttId(),downloadBoosterEligibleUser.getBoosterAmount());
						}
					}

					if(rechargeService.getAppConfig().get("AppCounterPromoFlage").equals("true")) {
		    			ettApis.giveAppCounterBonus(user);
		    		}
					if (offerDetails.getBalanceCreditInDays() > 0) {
						rechargeService.pendingCreditsInsert(offerDetails,ettId);
					}
					//Inserting the entry in dataUsagePendingCredits table
					if((offerDetails.getPayoutType() == PayoutType.DATAUSAGE || offerDetails.getMaxCreditLimit()>0.0f) && Float.parseFloat(user.getAppVersion())>=2.0f){
						edrService.insertInDataUsagePendingCredits(offerDetails, ettId);
					}
					
					if(offersStarted != null){
						offersStarted.setStatus(true);
						offersStarted.setCallbackTime(new Date());
						if (offerDetails.getBalanceCreditInDays() > 0) {
								//offersStarted.setPayoutType(PayOutType.DEFFERED);
							offersStarted.setPayOutType("DEFFERED");
							offersStarted.setPayoutOn(offerDetails.getPayoutOn().toString());
							offersStarted.setStatus(true);
						}
						else if((offerDetails.getPayoutType() == PayoutType.DATAUSAGE || offerDetails.getMaxCreditLimit()>0.0f) && Float.parseFloat(user.getAppVersion())>=2.0f) {
							offersStarted.setPayOutType("DEFFERED");
							offersStarted.setPayoutOn(offerDetails.getPayoutOn().toString());
							offersStarted.setStatus(true);
						}
						else {
							offersStarted.setStatus(false);
						}
						offersStartedRepository.save(offersStarted);
					}
				}
			} catch (org.springframework.dao.DataIntegrityViolationException e) {
				LOGGER.warn("Already done by installed process " + ettId + "&"+ e);
				tracker = transactionTrackerRepository.findOne(ettId + "_" + offerId);
				if (tracker.getCallBackTime() == null && offerDetails.getBalanceType().equals(BalanceType.CALLBACK)) {
					rechargeService.giveBalance(ettId, tId, offerDetails,vendorName,user);
					if(rechargeService.getAppConfig().get("DOWNLOAD_BOOSTER_ENABLE").equals("true")){
						DownloadBoosterEligibleUser downloadBoosterEligibleUser = downloadBoosterEligibleUserRepository.findByEttId(user.getEttId());
						if(downloadBoosterEligibleUser != null){
							ettApis.giveDownloadBoosterAmount(user, downloadBoosterEligibleUser);
							LOGGER.info("ettId={} BoosterAmount={} credited in a/c",user.getEttId(),downloadBoosterEligibleUser.getBoosterAmount());
						}
					}
					if(rechargeService.getAppConfig().get("AppCounterPromoFlage").equals("true")) {
		    			ettApis.giveAppCounterBonus(user);
		    		}
					if (offerDetails.getBalanceCreditInDays() > 0) {
						rechargeService.pendingCreditsInsert(offerDetails,ettId);
					}
					if(offersStarted != null){
						offersStarted.setStatus(true);
						offersStarted.setCallbackTime(new Date());
						if (offerDetails.getBalanceCreditInDays() > 0) {
							offersStarted.setPayOutType("DEFFERED");
							offersStarted.setPayoutOn(offerDetails.getPayoutOn().toString());
							offersStarted.setStatus(false);
						}
						offersStartedRepository.save(offersStarted);
					}
				}
				tracker.setCallBackTime(new Date());
				transactionTrackerRepository.save(tracker);
			} catch (Exception ee) {
				LOGGER.error("callback ettId=" + ettId + "&  " + ee);
				ee.printStackTrace();
			}

			// give balance to user who have invited
			if (rechargeService.getAppConfig().get("IS_INVITER_OFFER_AVAILABLE").equals("true")) {
					giveBalanceToInviter(ettId, tId, msisdn,offerDetails.getOfferName(),vendorName,offerCat);
				}
			}else{
			LOGGER.debug("offer id not inserted in TransectionTracker : " + offerId + "&ettId="+ ettId);
			}
		
			if(userClickCallBackTracker != null){
				userClickCallBackTrackerRepository.delete(clickId);
			}
			OfferDetails offerDetails = offerDetailsRepository.findByOfferId(offerId);
			if(offerDetails != null) {
				
				List<String> appKeyList = new ArrayList<String>();
				appKeyList.add(offerDetails.getAppKey());
				enqueueInstalled(user.getEttId(),appKeyList,"EDR","UNINSTALL_APP_MOVE");
			}
			return new ResponseEntity<>(HttpStatus.OK);
		}

	private void giveBalanceToInviter(Long ettId, String tId, String msisdn,String offerName,String vendor,String offerCat) {
		UserSource userSource = userSourceRepository.findOne(ettId);
		if (userSource == null || userSource.getUtmMedium() == null|| !userSource.getUtmMedium().equals("INVITE")) {
			LOGGER.info("didn't downloaded by invite ettId {}", ettId);
			return;
		}

		Long inviterId = 0l;
		try {
			inviterId = Long.valueOf(userSource.getUtmSource());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return;
		}

		UserAccount userAccount = userAccountRepository.findByEttId(inviterId);
		if (userAccount != null) {
			float oldBalance = userAccount.getCurrentBalance();
			userAccount.setCurrentBalance(oldBalance + 1);
			userAccount = userAccountRepository.save(userAccount);
			LOGGER.info("credited amount in Inviter's UserAccount table : ettId="+ ettId+ "&inviterId="+ inviterId+ "&oldBalance="+ oldBalance+ "&currentBalance="+ userAccount.getCurrentBalance());
			UserAccountSummary userAccountSummary = new UserAccountSummary();
			userAccountSummary.setEttId(inviterId);
			userAccountSummary.setOfferId(8282l);
			userAccountSummary.setOfferName(msisdn + "_USING_" + offerName);
			userAccountSummary.setAmount(1);
			userAccountSummary.setRemarks(tId);
			userAccountSummary.setVendor(vendor);
			userAccountSummary.setOfferCat(offerCat);
			userAccountSummaryRepository.save(userAccountSummary);
			
			LOGGER.info("save callback in UserAccountSummary table inviterId: "
					+ inviterId);
			/*
			 * if(offerDetails.getOfferAmount()>0){ String pushText = "";
			 * if(rechargeService
			 * .getSurveyOfferIdList().contains(offerDetails.getOfferId())){
			 * pushText =
			 * rechargeService.getProperties().getProperty("SURVEY_NOTIFICATION"
			 * ).replaceFirst("#AMOUNT#", offerDetails.getOfferAmount()+"");
			 * }else{ pushText = rechargeService.getProperties().getProperty(
			 * "MONEY_CREDITED_FOR_DOWNLOAD").replaceFirst("#AMOUNT#",
			 * offerDetails.getOfferAmount()+""); } pushText =
			 * pushText.replaceFirst("#APP_NAME#", offerDetails.getOfferName());
			 * User user = userRepository.findByEttId(ettId); DeviceToken dToken
			 * = deviceTokenRepository.findByDeviceId(user.getDeviceId());
			 * sendPush(pushText, dToken); }
			 */
		}

	}
	private void enqueueInstalled(final long ettId, final List<String> appKey,final String queueName,final String type) {
        try {
                jmsTemplate.send(queueName, new MessageCreator() {
                @Override
                public Message createMessage(Session session)
                                throws JMSException {
                        MapMessage mapMessage = session.createMapMessage();

                                        mapMessage.setString("type", type);
                                        mapMessage.setLong("ettId", ettId);
                                        mapMessage.setObject("appKey", appKey);

                        return mapMessage;
                	}
                });

        	} catch (Exception e) {
        		LOGGER.error("error in enqueueClick" + e);
                e.printStackTrace();
        	}
	}
}
