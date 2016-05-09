package com.web.rest.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domain.entity.DayAppDownloadCounter;
import com.domain.entity.DeviceToken;
import com.domain.entity.DownloadBoosterEligibleUser;
import com.domain.entity.Edr;
import com.domain.entity.InstalledApps;
import com.domain.entity.OfferDetails;
import com.domain.entity.OfferDetails.BalanceType;
import com.domain.entity.OfferDetails.PayoutType;
import com.domain.entity.OfferTextDetails;
import com.domain.entity.OffersStarted;
import com.domain.entity.TransactionTracker;
import com.domain.entity.User;
import com.domain.entity.UserAccount;
import com.domain.entity.UserAccountSummary;
import com.domain.entity.UserCompetitorApps;
import com.domain.entity.UserSource;
import com.repository.jpa.CallBackConfirmationRepository;
import com.repository.jpa.DayAppDownloadCounterRepository;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.DownloadBoosterEligibleUserRepository;
import com.repository.jpa.EdrRepository;
import com.repository.jpa.InstalledAppsRepository;
import com.repository.jpa.OfferDetailsRepository;
import com.repository.jpa.OfferTextDetailsRepository;
import com.repository.jpa.OffersStartedRepository;
import com.repository.jpa.PendingCreditsRepository;
import com.repository.jpa.TodaysOffersRepository;
import com.repository.jpa.TransactionTrackerRepository;
import com.repository.jpa.UnInstalledAppsRepository;
import com.repository.jpa.UserAccountRepository;
import com.repository.jpa.UserAccountSummaryRepository;
import com.repository.jpa.UserBlackListRepository;
import com.repository.jpa.UserCompetitorAppsRepository;
import com.repository.jpa.UserRepository;
import com.repository.jpa.UserSourceRepository;
import com.service.EDRService;
import com.service.EttApis;
import com.service.OffersService;
import com.service.RechargeService;
import com.service.ReferrerService;

/**
 * @author ankur
 */
@Controller
@RequestMapping(value = "/v2")
public class EdrControllerV2 {

        private static Logger LOGGER = LoggerFactory
                        .getLogger(EdrControllerV2.class);

        @Autowired
        EdrRepository edrRepository;

        @Resource
        private JmsTemplate jmsTemplate;

        @Autowired
        OfferDetailsRepository offerDetailsRepository;

        @Autowired
        TransactionTrackerRepository trackerRepository;

        @Autowired
        UserAccountRepository userAccountRepository;

        @Autowired
        UserAccountSummaryRepository userAccountSummaryRepository;

        @Autowired
        CallBackConfirmationRepository callBackConfirmationRepository;

        @Autowired
        InstalledAppsRepository installedAppsRepository;

        @Autowired
        UnInstalledAppsRepository unInstalledAppsRepository;

        @Autowired
        private DeviceTokenRepository deviceTokenRepository;

        @Resource
        private RechargeService rechargeService;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private UserSourceRepository userSourceRepository;

        @Autowired
        private UserCompetitorAppsRepository userCompetitorAppsRepository;

        @Autowired
        private DayAppDownloadCounterRepository dayAppDownloadCounterRepository;

        @Autowired
        private OfferTextDetailsRepository offerTextDetailsRepository;
        @Autowired
        private ReferrerService referrerService;

        @Autowired
        private EDRService edrService;

        @Autowired
        private PendingCreditsRepository pendingCreditsRepository;

        @Resource
        private UserBlackListRepository userBlackListRepository;

        @Resource
        private DownloadBoosterEligibleUserRepository downloadBoosterEligibleUserRepository;
        
        @Resource
        private OffersService offersService;

        @Resource
        private EttApis ettApis;

        @Resource
        private OffersStartedRepository offersStartedRepository;

        @Resource
        private TodaysOffersRepository todaysOffersRepository;

        @RequestMapping(value = "user/offerClickedEdr/", method = RequestMethod.POST)
        @ResponseBody
        public ResponseEntity<?> offerClickedEdr(@RequestParam("ettId") long ettId,
                        @RequestParam("offerId") long offerId,
                        @RequestParam(value = "appKey") String appKey,
                        @RequestParam(value="type", required=false, defaultValue="0") String type

        ) throws IOException, ExecutionException, InterruptedException {
                LOGGER.info("API user/offerClickedEdr/ request ettId={},offerId={},appKey={},type={}",ettId,offerId,appKey,type);
         
                try{
                	if(ettApis.getBlackListStatus(ettId)) {
	                        LOGGER.info("ettId BlackListed {}",ettId);
	                        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	                }
	                enqueueClick(ettId,offerId,appKey,"EDR" );
	                OfferDetails offerDetails = offerDetailsRepository.findByOfferId(offerId);
	                List<Edr> edrList = edrRepository.findOneDeviceIdFlage(ettId + "_" + appKey);
	                Edr edr = null;
	                if(edrList!=null && edrList.size()>0) {
	                	edr = edrList.get(0);
	                }
	                if (edr == null) {
	                        edr = new Edr();
	                        edr.setEttId(ettId);
	                        edr.setOfferId(offerId);
	                        edr.setAppKey(appKey);
	                        edr.setVendor(offerDetails.getVendor());
	                        edr.setOfferCat(offerDetails.getOfferCat());
	                } else {
	                        if(edr.isOfferInstalled()){
	                                LOGGER.warn("already offer installed user/offerClickedEdr/ request ettId="
	                                        + ettId + "&offerId=" + offerId);
	                                return new ResponseEntity<>(HttpStatus.OK);
	                        }
	
	                        edr.setVendor(offerDetails.getVendor());
	                        edr.setOfferCat(offerDetails.getOfferCat());
	
	                        edr.setClickedTime(new Date());
	                        LOGGER.warn("repeating user/offerClickedEdr/ request ettId="+ ettId + "&offerId=" + offerId);
	                }
	                edr = edrRepository.save(edr);
	                if(offerDetails.isStatus()){
	                        return new ResponseEntity<>(HttpStatus.OK);
	                }
	                else {
	                        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
	                }
                }catch(Exception ex) {
                	LOGGER.info("Error in OfferClickEdr {}"+ex);
                	ex.printStackTrace();
                	throw ex;
                }
        }

        private void enqueueClick(final long ettId, final long offerId, final String appKey,
                        String queueName) {
                try {
                        jmsTemplate.send(queueName, new MessageCreator() {
                        @Override
                        public Message createMessage(Session session)
                                        throws JMSException {
                                MapMessage mapMessage = session.createMapMessage();

                                                mapMessage.setString("type", "CLICK");
                                                mapMessage.setLong("ettId", ettId);
                                                mapMessage.setLong("offerId", offerId);
                                                mapMessage.setString("appKey", appKey);

                                return mapMessage;
                        }
                });

                } catch (Exception e) {
                        LOGGER.error("error in enqueueClick" + e);
                        e.printStackTrace();
                }
        }

        @RequestMapping(value = "user/offerInstalledEdr/", method = RequestMethod.POST)
        @ResponseBody
        public ResponseEntity<?> offerInstalledEdr(
                        @RequestParam("ettId") long ettId,
                        @RequestParam("appKey") List<String> appKey,
                        @RequestParam("otp") int otp,
                        @RequestParam(value = "tempOtp", required = false, defaultValue = "0") int tempOtp)
                        throws IOException, ExecutionException, InterruptedException {
                //LOGGER.info("API user/offerInstalledEdr/ request ettId={},appKey={},otp={},tempOtp={}",ettId,appKey,otp,tempOtp);
                long startTime = System.currentTimeMillis();
                
        		String logdata = "API user/offerInstalledEdr/ request ettId="+ettId+",appKey="+appKey+",otp="+otp+",tempOtp="+tempOtp;
        		try {
                if(ettApis.getBlackListStatus(ettId)) {
                        LOGGER.info("ettId BlackListed {}",ettId);
                        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }

                InstalledApps installedApps = null;
                User user = userRepository.findByEttId(ettId);
                if (!user.isVerified() && otp == 0 && user.getTempOtp() != tempOtp) {
                    LOGGER.warn("ettId="+ ettId+ "is not verified but still trying to send install edr");
                    ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
                    LOGGER.info(logdata +"["+(System.currentTimeMillis()-startTime)+"] ["+HttpStatus.UNAUTHORIZED+"]");
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                } else if (!user.isVerified() || user.getOtp() != otp) {
                    LOGGER.warn("ettId="+ ettId+ "is not verified but still trying to send install edr");
                    ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
                    LOGGER.info(logdata +"["+(System.currentTimeMillis()-startTime)+"] ["+HttpStatus.UNAUTHORIZED+"]");
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
                if(appKey.size()>2){
                	enqueueInstalled(ettId, appKey, "EDR","INSTALLAPPS");
                	LOGGER.info("installEdr enqued of ettId={}",ettId);
                	return new ResponseEntity<>(HttpStatus.OK);
                }
                String appKeyCrr = appKey.get(0);
                List<Edr> edrList = edrRepository.findOneDeviceIdFlage(ettId + "_" + appKeyCrr);
                Edr edr = null;
                if(edrList!=null && edrList.size()>0) {
                	edr = edrList.get(0);
                }
                if (edr != null && !edr.isOfferInstalled()) {
                	
                                edr.setOfferInstalled(true);
                                edr.setInstalledTime(new Date());
                                edrRepository.save(edr);
                                LOGGER.info("save edr in Edr table : " + ettId);
                                OfferDetails offerDetails = offerDetailsRepository.findByOfferId(edr.getOfferId());
                                OffersStarted offersStarted = offersStartedRepository.findOne(ettId + "_" + offerDetails.getOfferId());
                                offersStarted = edrService.setOffersStartedDetails(offersStarted, offerDetails, ettId, offerDetails.getOfferId(),"INSTALL",user.getAppVersion(),user);
                                if(offersStarted!=null && offersStarted.getPayoutOn().equalsIgnoreCase("order")) {
                                        offersStarted.setPayOutType("DEFFERED");
                                }
                                if (offerDetails.getBalanceType().equals(BalanceType.INSTALL)) {
                                        boolean installedCheck = false;
                                        if (rechargeService.getAppConfig().get("USER_COMPETITOR_CHECK").equals("true") && userCompetitorAppsRepository.findByettId(user.getEttId()).size()>0) {
                                                installedCheck = true;
                                        }
                                        if (installedCheck == true) {
                                                LOGGER.info("user has competetor app offer is={}, ettId={}",offerDetails.getOfferName(), ettId);
                                                /* if(appKey.equals("Amazon Shopping")) */{
                                                if (rechargeService.getAppConfig().get("AMAZON_CHECK_INSTALL").equals("true")) {
                                                        if (edrService.checkAmazonInstallStaus(edr, user,offerDetails.getOfferId())) {
                                                                // Fail here for amazon
                                                        	LOGGER.info(logdata +"["+(System.currentTimeMillis()-startTime)+"] ["+HttpStatus.OK+"]");
                                                        		return new ResponseEntity<>(HttpStatus.OK);
                                                        }
                                                }
                                        }
                                }
                                if (!user.isVerified()) {
                                        LOGGER.error("Wrong EDR of ettId without isVerified ettId={},"+ ettId);
                                        //return new ResponseEntity<>(HttpStatus.OK);
                                        ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
                                        LOGGER.info(logdata +"["+(System.currentTimeMillis()-startTime)+"] ["+HttpStatus.UNAUTHORIZED+"]");
                                        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                                } else {
                                        // check for first app download invite money
                                        if (!user.isDownloadedFirstApp()) {
                                                if (user.isVerified()) {
                                                	//if(rechargeService.getProperties().getProperty("INVITE_OFFER_ETT_DOWNLOAD_CALLBACK_AVAILABLE").equals("true")){
                        							if(rechargeService.getAppConfig().get("INVITE_OFFER_ETT_DOWNLOAD_AVAILABLE").equals("true")) {
                        									referrerService.inviteSomeMoneyOnCallBack(user);
                        									user.setDownloadedFirstApp(true);
                        									userRepository.save(user);
                        									referrerService.setPromotionClickEventFirstAppDownload(user);
                        							}
                                                } else{
                                                	LOGGER.error("user is not verified so not giving the referal amount to aparty ettId={}"+ ettId);
                                                	}
                                         }
                                }
                                        // maintain transaction tracker
                                        TransactionTracker tracker = new TransactionTracker();
                                        tracker.setEttId(ettId);
                                        tracker.setOfferId(edr.getOfferId());
                                        tracker.setInstalledTime(new Date());
                                        tracker.setRemark("EDR_INSTALLED");
                                        try {
                                                tracker = trackerRepository.save(tracker);
                                                LOGGER.info("save edr in TransactionTracker table : "+ ettId);
                                                if (offerDetails.getBalanceType().equals(BalanceType.ANY) || offerDetails.getBalanceType().equals(BalanceType.INSTALL)) {
                                                          if(offersService.getAmazonSuspectFlage(user,offerDetails)) {
                                                        		edrService.handleInstallEdrOnHold(user, offerDetails);
                                                                LOGGER.info("ettId={} and offerId={} inserted into InstallEdrOnHold",user.getEttId(),offerDetails.getOfferId());
                                                        }
                                                        else if(rechargeService.checkTheMonthCountOffer(user,offerDetails)>=1) {
                                                        		LOGGER.info("Allready given the money for this offer in calendar month user might be changed device ettId={},offerId={}",user.getEttId(),offerDetails.getOfferId());
                                                        	}
                                                        	else {
                                                        			giveInstallBalance(user,offerDetails);
                                                        	}
                                                        if(offersStarted != null){
                                                                offersStarted.setStatus(false);
                                                                if (offerDetails.getBalanceCreditInDays() > 0) {
                                                                        offersStarted.setPayOutType("DEFFERED");
                                                                        offersStarted.setPayoutOn(offerDetails.getPayoutOn().toString());
                                                                        offersStarted.setStatus(true);
                                                                }
                                                                offersStartedRepository.save(offersStarted);
                                                        }
                                                        if(rechargeService.getAppConfig().get("NEW_USER_BONUS_ON_FIRST_APP").equals("true") && user.getTotlNoOfDLoadApp()<= 0 && user.getCreatedTime().getTime() >Long.parseLong(rechargeService.getAppConfig().get("NEW_USER_BONUS_ELIGIBILITY_TIME_FROM"))){
                                                                int sameDay = 5;
                                                                try {
                                                                        int timezone = user.getTimeZoneInSeconds();
                                                                        if(timezone == 0)
                                                                                timezone = 19800;
                                                                        Date nowTime = new Date();
                                                                        nowTime.setTime(nowTime.getTime()+timezone*1000);
                                                                        Date userCT = new Date();
                                                                        long userCTT = user.getCreatedTime().getTime();
                                                                        userCT.setTime(userCTT+timezone*1000);
                                                                        //DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                                                                        //int nowTimeWithZeroTime = Integer.parseInt(formatter.format(nowTime));
                                                                        //int userCTWithZeroTime = Integer.parseInt(formatter.format(userCT));
                                                                        long sameDay1 = nowTime.getTime()-userCTT;
                                                                        sameDay = (int)(sameDay1/(1000*60*60*24));
                                                                }catch(Exception ex1){
                                                                        ex1.printStackTrace();
                                                                        LOGGER.error("Error in freshUserBonusOffer"+ex1);
                                                                }
                                                                if(sameDay<=2) {
                                                                        float offerGiveAmount = (float)Math.ceil((((offerDetails.getOfferAmount()-offerDetails.getPendingAmountCredit())*Integer.parseInt(rechargeService.getAppConfig().get("NEW_USER_BONUS_AMOUNT_PERCENTAGE")))/100));

                                                                        rechargeService.giveBonusAmountToNewUser(user,System.currentTimeMillis() + "", offerDetails,offerGiveAmount,sameDay);
                                                                        LOGGER.info("Bonus amount credited to the new user for the first app |  amount={}, ettId={}",offerGiveAmount,ettId);

                                                                }
                                                                else
                                                                {
                                                                        LOGGER.info("User not the same day so not eligible for the fresh bonus balance ettId={}",user.getEttId());
                                                                }
                                                        }
                                                }

                                        } catch (org.springframework.dao.DataIntegrityViolationException e) {
                                                LOGGER.warn("Already done by callback process " + ettId+ "&" + e);
                                                e.printStackTrace();
                                                tracker = trackerRepository.findOne(ettId + "_"+ edr.getOfferId());
                                                if (tracker.getInstalledTime() == null && offerDetails.getBalanceType().equals(BalanceType.INSTALL)) {
                                                        //UserSource userSource = userSourceRepository.findByEttId(user.getEttId());
                                                        if(offersService.getAmazonSuspectFlage(user,offerDetails)) {
                                                        	//if(rechargeService.getProperties().getProperty("INSTALL_EDR_ON_HOLD_OFFERID").equals(offerDetails.getOfferId()+"")) {
                                                        		edrService.handleInstallEdrOnHold(user, offerDetails);
                                                                LOGGER.info("ettId={} and offerId={} inserted into InstallEdrOnHold",user.getEttId(),offerDetails.getOfferId());
                                                        	/*}
                                                        	else if(rechargeService.checkTheMonthCountOffer(user,offerDetails)>=1) {
                                                        		LOGGER.info("Allready given the money for this offer in calendar month user might be changed device ettId={},offerId={}",user.getEttId(),offerDetails.getOfferId());
                                                        	}
                                                        	else {
                                                        			giveInstallBalance(user,offerDetails);
                                                        		}*/
                                                        }
                                                        else if(rechargeService.checkTheMonthCountOffer(user,offerDetails)>=1) {
                                                        		LOGGER.info("Allready given the money for this offer in calendar month user might be changed device ettId={},offerId={}",user.getEttId(),offerDetails.getOfferId());
                                                        	}
                                                        	/*else if(rechargeService.getProperties().getProperty("COMPATETOR_APP_AMAZON_OFFER_TYPE_B").equals("true") && offerDetails.getOfferId()==108) {
                                                        		List<UserCompetitorApps> userCompetitorApps = userCompetitorAppsRepository.findByettId(user.getEttId());
                                                        		if(userCompetitorApps!=null && userCompetitorApps.size()>0) {
                                                        			LOGGER.info("user has competetor app so amazon offer converted to type B ettId={} and offerId={} inserted into InstallEdrOnHold",user.getEttId(),offerDetails.getOfferId());
                                                        			edrService.handleInstallEdrOnHold(user, offerDetails);
                                                        		}
                                                        		else {
                                                        			giveInstallBalance(user,offerDetails);
                                                        		}
                                                        		
                                                        	}*/
                                                        	else {
                                                        			giveInstallBalance(user,offerDetails);
                                                        	}
                                                        //}
                                                        

                                                }
                                                tracker.setInstalledTime(new Date());
                                                trackerRepository.save(tracker);
                                        } catch (Exception ee) {
                                                LOGGER.error("Edr installed ettId=" + ettId + "& " + ee);
                                                ee.printStackTrace();
                                        }

                                //}

                        }
                        else {
                                // give some money on installed
                                if (offerDetails.getInstalledAmount() > 0) {
                                        List<Long> listUser = userRepository.findByDeviceId_2(user.getDeviceId());
                                        List<UserAccountSummary> userAccountSummaryList = userAccountSummaryRepository.findByEttIdAndOfferId(listUser,offerDetails.getOfferId());
                                        if (userAccountSummaryList == null|| userAccountSummaryList.size() <= 0) {

                                                rechargeService.giveSomeAmountOnIntall(user,System.currentTimeMillis() + "", offerDetails,offerDetails.getInstalledAmount());
                                                LOGGER.warn(
                                                                "intalled offer is {}, and balance type is not install, no credit ettId={}",
                                                                offerDetails.getOfferName(), ettId);

                                        } else {
                                                LOGGER.warn("allready installed intalled offer is {}, no credit ettId={}",
                                                                offerDetails.getOfferName(), ettId);
                                                LOGGER.info(logdata +"["+(System.currentTimeMillis()-startTime)+"] ["+HttpStatus.OK+"]");
                                                        return new ResponseEntity<>(HttpStatus.OK);
                                        }

                                }
                                else
                                {
                                        if(rechargeService.getAppConfig().get("NEW_USER_BONUS_ON_FIRST_APP").equals("true") && user.getTotlNoOfDLoadApp()<= 0 ){


                                                int sameDay = 5;
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
                                                        //int nowTimeWithZeroTime = Integer.parseInt(formatter.format(nowTime));
                                                        //int userCTWithZeroTime = Integer.parseInt(formatter.format(userCT));
                                                        //sameDay = nowTimeWithZeroTime-userCTWithZeroTime;
                                                        long sameDay1 = nowTime.getTime()-userCTT;
                                                        sameDay = (int)(sameDay1/(1000*60*60*24));

                                                }catch(Exception ex1){
                                                        ex1.printStackTrace();
                                                        LOGGER.error("Error in freshUserBonusOffer"+ex1);
                                                }
                                                if(sameDay<=2) {
                                                        float offerGiveAmount = (float)Math.ceil((((offerDetails.getOfferAmount()-offerDetails.getPendingAmountCredit())*Integer.parseInt(rechargeService.getAppConfig().get("NEW_USER_BONUS_AMOUNT_PERCENTAGE")))/100));
                                                        List<UserAccountSummary> userAccountSummaryList = userAccountSummaryRepository.findByEttIdAndOfferId(ettId,offerDetails.getOfferId());
                                                        if ((userAccountSummaryList == null || userAccountSummaryList.size() <= 0) && offerGiveAmount>0) {
                                                                rechargeService.giveBonusAmountToNewUser(user,System.currentTimeMillis() + "", offerDetails,offerGiveAmount,sameDay);
                                                                LOGGER.info("Bonus amount credited to the new user for the first app |  amount{}, ettId{}",offerGiveAmount,ettId);
                                                        }
                                                }
                                                else {
                                                        LOGGER.info("User not the same day so not eligible for the fresh bonus balance ettId{}",user.getEttId());
                                                }
                                        }
                                        else if(rechargeService.getAppConfig().get("NEW_USER_INSTALL_AMOUNT_FLAG").equals("true") && (!rechargeService.getNewUserInstallAKBL().contains(appKeyCrr)) && user.getTotlNoOfDLoadApp()<= Integer.parseInt(rechargeService.getAppConfig().get("NEW_USER_INSTALL_COUNT")) && user.getCreatedTime().getTime() >Long.parseLong(rechargeService.getAppConfig().get("NEW_USER_INSTALL_TIME_AFTER"))){  // and > today
                                                UserAccount userAccount = userAccountRepository.findByEttId(ettId);
                                                if(userAccount.getCurrentBalance()<=Float.parseFloat(rechargeService.getAppConfig().get("NEW_USER_INSTALL_AMOUNT_CHECK"))) {
                                                        float offerGiveAmount = (float)Math.ceil((((offerDetails.getOfferAmount()-offerDetails.getPendingAmountCredit())*Integer.parseInt(rechargeService.getAppConfig().get("NEW_USER_INSTALL_AMOUNT_PERCENTAGE")))/100));
                                                        List<UserAccountSummary> userAccountSummaryList = userAccountSummaryRepository.findByEttIdAndOfferId(ettId,offerDetails.getOfferId());
                                                        if ((userAccountSummaryList == null|| userAccountSummaryList.size() <= 0) && offerGiveAmount>0) {
                                                                rechargeService.giveSomeAmountOnIntall(user,System.currentTimeMillis() + "", offerDetails,offerGiveAmount);
                                                                LOGGER.info("give some amount the new user in the time of install amount{},ettId{}",offerGiveAmount,ettId);
                                                        }
                                                }
                                        }
                                }
                        }
                        user.setTotlNoOfDLoadApp(user.getTotlNoOfDLoadApp()+1);
                        userRepository.save(user);
                        todaysOffersRepository.deleteEttIdOfferId(ettId);
                        if(user.getTotlNoOfDLoadApp()==3 && rechargeService.getAppConfig().get("DAT_1_2_3_NOT_MEDIA").equals("true")) {
                        	
                        	rechargeService.giveBonusAmountToReferal(user);
                        }
                }

                // save in installed apps table
                // if(installedApps == null)
                if(rechargeService.getAppConfig().get("INSTALLAPPS_INSERT_HERE").equals("true")){
                        installedApps = installedAppsRepository.findOne(ettId + "_" + appKeyCrr);
                        if (installedApps == null) {
                                installedApps = new InstalledApps();
                                installedApps.setId(ettId + "_" + appKeyCrr);
                                installedApps.setEttId(ettId);
                                installedApps.setAppKey(appKeyCrr);
                                installedApps.setCreatedTs(new Date());
                                installedAppsRepository.save(installedApps);
                                LOGGER.info("save offer in InstalledApps table : " + ettId);
                                enqueueInstalled(user.getEttId(),appKey,"EDR","UNINSTALL_APP_MOVE");

                                /*if (user.getStatus() != rechargeService.getCOMPETITOR_USER_STATUS()&& rechargeService.getCompetitorAppList().contains(appKeyCrr)) {
                                        user.setStatus(rechargeService.getCOMPETITOR_USER_STATUS());
                                        user = userRepository.save(user);
                                        LOGGER.warn("User has competitor app {}, updated user status to {}, ettId={}",appKeyCrr, user.getStatus(), user.getEttId());
                                        //DeviceToken deviceToken = deviceTokenRepository.findByDeviceId(user.getDeviceId());
                                        DeviceToken deviceToken = deviceTokenRepository.findByEttId(user.getEttId());
                                        if (deviceToken != null&& !deviceToken.getDeviceToken().equals("")&& !rechargeService.getProperties().getProperty("COMPETITOR_USER_STATUS_NOTIFICATION").equals("false")) {
                                                sendPush(rechargeService.getProperties().getProperty("COMPETITOR_USER_STATUS_NOTIFICATION"),deviceToken,user.getEttId());
                                        }
                                }*/
                        }
                }
                else {
                        enqueueInstalled(user.getEttId(),appKey,"EDR","INSTALLAPPS");
                        enqueueInstalled(user.getEttId(),appKey,"EDR","UNINSTALL_APP_MOVE");
                }
                LOGGER.info(logdata +"["+(System.currentTimeMillis()-startTime)+"] ["+HttpStatus.OK+"]");
                return new ResponseEntity<>(HttpStatus.OK);
                        }catch(Exception ex) {
                        	//LOGGER.info("Error in OfferClickEdr {}"+ex);
                        	LOGGER.info(logdata +"["+(System.currentTimeMillis()-startTime)+"] [500] ["+ex+"]");
                        	ex.printStackTrace();
                        	throw ex;
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

        private void giveBalance(User user, String tId, OfferDetails offerDetails) {
               	
        		//List<UserAccountSummary> accountSummaries = userAccountSummaryRepository.findByEttIdAndOfferId(user.getEttId(),offerDetails.getOfferId());
        		//for()
        		boolean amountGiven = false;
        		float amount = offerDetails.getOfferAmount();
                if (offerDetails.getBalanceCreditInDays() > 0) {
                        amount = amount - offerDetails.getPendingAmountCredit();
                }
                UserAccount userAccount = userAccountRepository.findByEttId(user.getEttId());
                if (userAccount != null && offerDetails.isStatus()) {
                        UserAccountSummary userAccountSummary = new UserAccountSummary();
                        userAccountSummary.setEttId(user.getEttId());
                        userAccountSummary.setOfferId(offerDetails.getOfferId());
                        userAccountSummary.setOfferName(offerDetails.getOfferName());
                        userAccountSummary.setAmount(amount);
                        // userAccountSummary.setCoin(offerDetails.getCoins());
                        userAccountSummary.setRemarks(System.currentTimeMillis() + "");
                        userAccountSummary.setVendor(offerDetails.getVendor());
                        userAccountSummary.setOfferCat(offerDetails.getOfferCat());
                        userAccountSummaryRepository.save(userAccountSummary);
                        LOGGER.info("save edr in UserAccountSummary table : "+ user.getEttId());
                        if (amount <= 0) {
                                return;
                        }
                        float oldBalance = userAccount.getCurrentBalance();
                        // int oldcoin = userAccount.getBalanceCoins();
                        // userAccount.setBalanceCoins(oldcoin+offerDetails.getCoins());
                        userAccount.setCurrentBalance((oldBalance + amount));
                        userAccount = userAccountRepository.save(userAccount);
                        amountGiven=true;
                        LOGGER.info("credited amount in UserAccount table : ettId="+ user.getEttId() + "&oldBalance=" + oldBalance      + "&currentBalance=" + userAccount.getCurrentBalance());
                        if (offerDetails.getBalanceCreditInDays() < 100) {
                        		String pushText = null;
                        		OfferTextDetails offerTextDetail = offerTextDetailsRepository.findById(offerDetails.getOfferId()+"_"+user.getLocale());
                        		if(offerTextDetail==null || offerTextDetail.getInstallNotification() ==null) {
                        			pushText = offerDetails.getInstalledNotification();
                        		}
                        		else {
	                        			pushText = offerTextDetail.getInstallNotification();
	                        	}
                                if (pushText == null || pushText.trim().equals("")|| pushText.trim().equals("null")) {
                                        pushText = rechargeService.getAppConfig().get("MONEY_CREDITED_FOR_DOWNLOAD");

                                }
                                pushText = pushText.replaceFirst("#OFFER_NAME#",offerDetails.getOfferName()).replaceFirst("#OFFERAMOUNT#",offerDetails.getPendingAmountCredit() + "").replaceFirst("#OFFER_CREDIT_DAYS#",offerDetails.getBalanceCreditInDays() + "").replaceFirst("#AMOUNT#",offerDetails.getOfferAmount() + "").replaceFirst("#APP_NAME#", offerDetails.getOfferName()).replaceFirst("#REMAMOUNT#",(offerDetails.getOfferAmount()
                                                                                - offerDetails.getInstalledAmount() + ""))
                                                                                .replaceFirst(
                                                                "#INSTALLEDAMOUNT#",
                                                                (offerDetails.getInstalledAmount() + ""))
                                                .replaceFirst("#APP_NAME#", offerDetails.getOfferName())
                                                .replaceFirst("#OFFER_NAME#",
                                                                offerDetails.getOfferName())
                                                .replaceFirst("#OFFERAMOUNT#",
                                                                offerDetails.getPendingAmountCredit() + "")
                                                .replaceFirst("#OFFER_CREDIT_DAYS#",
                                                                offerDetails.getBalanceCreditInDays() + "");
                                if (pushText != null && (!pushText.trim().equals(""))) {
                                        //DeviceToken dToken = deviceTokenRepository.findByDeviceId(user.getDeviceId());
                                        DeviceToken dToken = deviceTokenRepository.findByEttId(user.getEttId());
                                        sendPush(pushText, dToken,user.getEttId());
                                }
                        }
                }
                if(rechargeService.getAppConfig().get("UserTargetAmountBaseCheck").equals("true") && amountGiven && amount>0) {
        			rechargeService.getBalanceToUserTarAmount(user.getEttId(),amount);
        		}
        }

        @RequestMapping(value = "user/UnInstalledEdr/", method = RequestMethod.POST)
        @ResponseBody
        public ResponseEntity<?> offerUnInstalledEdr(
                        @RequestParam("ettId") long ettId,
                        @RequestParam("installedApps") List<String> currentInstalledApps

        ) throws IOException, ExecutionException, InterruptedException {
                LOGGER.info("user/UnInstalledEdr/ request ettId={},installedApps={}", ettId,currentInstalledApps);
                enqueueMap(ettId, currentInstalledApps, "EDR");
                return new ResponseEntity<>(HttpStatus.OK);
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

        private void enqueueMap(final long ettId, final List msg,
                        final String queueName) {
                try {
                        jmsTemplate.send(queueName, new MessageCreator() {
                                @Override
                                public Message createMessage(Session session)
                                                throws JMSException {
                                        MapMessage mapMessage = session.createMapMessage();
                                        mapMessage.setString("type", "UNINSTALLAPPS");
                                        mapMessage.setLong("ettId", ettId);
                                        mapMessage.setObject("installedApps", msg);
                                        return mapMessage;
                                }
                        });

                } catch (Exception e) {
                        LOGGER.error("error in enqueue" + e);
                        e.printStackTrace();
                }

        }
  
        private void giveInstallBalance(User user1,OfferDetails offerDetails1) {
        	giveBalance(user1, System.currentTimeMillis() + "",offerDetails1);
            if (offerDetails1.getBalanceCreditInDays() > 0) {
                    rechargeService.pendingCreditsInsert(offerDetails1,user1.getEttId());
            }
            if((offerDetails1.getPayoutType() == PayoutType.DATAUSAGE || offerDetails1.getMaxCreditLimit()>0.0f) && Float.parseFloat(user1.getAppVersion())>=2.0f){
                edrService.insertInDataUsagePendingCredits(offerDetails1, user1.getEttId());
            }
            if(rechargeService.getAppConfig().get("DOWNLOAD_BOOSTER_ENABLE").equals("true")){
                    DownloadBoosterEligibleUser downloadBoosterEligibleUser = downloadBoosterEligibleUserRepository.findByEttId(user1.getEttId());
                    if(downloadBoosterEligibleUser != null){
                            ettApis.giveDownloadBoosterAmount(user1, downloadBoosterEligibleUser);
                            LOGGER.info("ettId={} BoosterAmount={} credited in a/c",user1.getEttId(),downloadBoosterEligibleUser.getBoosterAmount());
                    	}
            	}
    		if(rechargeService.getAppConfig().get("AppCounterPromoFlage").equals("true")) {
    			ettApis.giveAppCounterBonus(user1);
    		}
        }
}
