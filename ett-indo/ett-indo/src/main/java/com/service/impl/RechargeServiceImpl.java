package com.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.domain.entity.AppConfig;
import com.domain.entity.DeviceToken;
import com.domain.entity.LocaleTextTemplate;
import com.domain.entity.OfferDetails;
import com.domain.entity.OfferDetailsNew;
import com.domain.entity.OfferTextDetails;
import com.domain.entity.PendingCredits;
import com.domain.entity.User;
import com.domain.entity.UserAccount;
import com.domain.entity.UserAccountSummary;
import com.domain.entity.UserGameCreditSummary;
import com.domain.entity.UserSource;
import com.domain.entity.UserTargetAmountBase;
import com.repository.jpa.AppConfigRepository;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.EdrRepository;
import com.repository.jpa.LocaleTextTemplateRepository;
import com.repository.jpa.OfferTextDetailsRepository;
import com.repository.jpa.PendingCreditsRepository;
import com.repository.jpa.UserAccountRepository;
import com.repository.jpa.UserAccountSummaryRepository;
import com.repository.jpa.UserGameCreditSummaryRepository;
import com.repository.jpa.UserRepository;
import com.repository.jpa.UserSourceRepository;
import com.repository.jpa.UserTargetAmountBaseRepository;
import com.service.RechargeService;


public class RechargeServiceImpl implements RechargeService {

	private static Logger LOGGER = LoggerFactory.getLogger(RechargeServiceImpl.class);
	
	@Autowired
	private PendingCreditsRepository pendingCreditsRepository;
	
	@Resource
	UserAccountSummaryRepository userAccountSummaryRepository;

	@Resource
	UserAccountRepository userAccountRepository;

	@Resource
	private JmsTemplate jmsTemplate;

	@Autowired
	UserGameCreditSummaryRepository userGameCreditSummaryRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	EdrRepository edrRepository;

	@Autowired
	private DeviceTokenRepository deviceTokenRepository;
	
	@Autowired
	private AppConfigRepository appConfigRepository;

	@Autowired
	private UserSourceRepository userSourceRepository;
	
	@Autowired
	private LocaleTextTemplateRepository localeTextTemplateRepository;

	@Autowired
	private OfferTextDetailsRepository offerTextDetailsRepository;
	
	@Autowired
	private UserTargetAmountBaseRepository userTargetAmountBaseRepository;
	private ConcurrentHashMap<String, String> appConfig = new ConcurrentHashMap<>();
	
	private ConcurrentHashMap<String, String> localeTextTemplate = new ConcurrentHashMap<>();

	private Map<String, String> operatorKeyMap;
	private Map<String, Integer> operatorIdMap;
	private Map<String, Integer> circleIdMap;
	private List<String> competitorAppList;
	private int COMPETITOR_USER_STATUS;
	
	private int BlackListBlockCounter;
	private List<Long> surveyOfferIdList;

	private String[] offersMapArr;
	private Map<String, String> offersColorCodeMap;

	private List<String> redeemOperatorsList;
	private List<String> redeemAmountsList;
	private List<String> depricatedList;
	private List<Long> offerIdIgnoreList;
	private List<Long> offerIdBlockedInOffersStarted;
	private List<Long> installEdrOnHoldList;
	private List<String> newUserInstallAKBL;
	private List<String> amazonSeriesSus;
	private Map<String,String> appVersionUpdate;
	private Map<Long,Integer> extraAmountInCallBackOfferIDAmount;
	private Map<Long,Integer> extraAmountInCallBackOfferIDCount;
	private Map<Integer,String> isDeviceSupport;
	private List<String> OFFERID_BARRED;
	private List<String> REAL_OFFER_PAYOUTTYPE;
	private int appCounter;
	private int appCounterAppPayout;
	private String appCounterAppPayoutMsg;
	private float shareTabAppVersion;
	
	@PostConstruct
	@Scheduled(cron = "0 0/5 * * * ?")
	public void relodadRechargeServiceImpl() {
	
		List<AppConfig> appConfigList = appConfigRepository.findAll();
		
		List<LocaleTextTemplate> textTemplateList = localeTextTemplateRepository.findAll();
		appConfig.clear();
		localeTextTemplate.clear();
		for(AppConfig appConfig : appConfigList){
			this.appConfig.put(appConfig.getKey(), appConfig.getValue());
		}
		System.out.println("appConfig Loaded, size="+appConfig.size());
		String value ="";
		for(LocaleTextTemplate textTemp : textTemplateList){
			if(textTemp.getValue().contains("20B9")){
				value = textTemp.getValue().replace("20B9",(char) Integer.parseInt( "20B9", 16 )+"");
				this.localeTextTemplate.put(textTemp.getKey() +"_"+ textTemp.getLocale(), value);
				continue;
			}
			this.localeTextTemplate.put(textTemp.getKey() +"_"+ textTemp.getLocale(), textTemp.getValue());
		}
		System.out.println("localeTextTemplate loaded, size="+localeTextTemplate.size());
		
		String[] operators = appConfig.get("OPERATOR").split(",");
		String[] operatorKeys = appConfig.get("OPERATOR_KEY").split(",");
		String[] operatorIds = appConfig.get("OPERATOR_ID").split(",");
		String[] circles = appConfig.get("OPERATOR_CIRCLE").split(",");
		String[] circleIds = appConfig.get("CIRCLE_ID").split(",");
		operatorKeyMap = new HashMap<String, String>();
		operatorIdMap = new HashMap<>();
		circleIdMap = new HashMap<>();
		for (int i = 0; i < operators.length; i++) {
			operatorKeyMap.put(operators[i], operatorKeys[i]);
			operatorIdMap.put(operators[i], Integer.valueOf(operatorIds[i]));
			circleIdMap.put(circles[i], Integer.valueOf(circleIds[i]));
		}
		
		System.out.println("operatorKeyMap:" + operatorKeyMap);
		System.out.println("operatorIdMap:" + operatorIdMap);
		System.out.println("circleIdMap:" + circleIdMap);
		try {
			isDeviceSupport = new HashMap<Integer,String>();
			String isDS[] = appConfig.get("IsDeviceSupport").split(",");
			//int i=0;
			for(String isDS1 : isDS) {
				isDeviceSupport.put(Integer.parseInt(isDS1.split("-")[0]),isDS1.split("-")[1]);
			}
		}catch(Exception ex){
			System.out.println("Error in IsDeviceSupport parameter configuration");
			ex.printStackTrace();
		}
		String[] competitorApps = appConfig.get("COMPETITOR_APPS_LIST").split(",");
		competitorAppList = new ArrayList<>(10);
		for (int i = 0; i < competitorApps.length; i++) {
			competitorAppList.add(competitorApps[i]);
		}
		System.out.println("competitorAppList" + competitorAppList);
		COMPETITOR_USER_STATUS = Integer.valueOf(appConfig.get("COMPETITOR_USER_STATUS"));
		BlackListBlockCounter = Integer.valueOf(appConfig.get("BlackListBlockCounter"));
		String[] surveyOfferIds = appConfig.get("SURVEY_OFFER_IDS").split(",");
		surveyOfferIdList = new ArrayList<>(10);
		for (int i = 0; i < surveyOfferIds.length; i++) {
			surveyOfferIdList.add(Long.valueOf(surveyOfferIds[i]));
		}
		System.out.println("surveyOfferIdList" + surveyOfferIdList);

		offersMapArr = appConfig.get("GET_OFFERS_OPTIONS").split(",");
		String[] offersColorCodeArr = appConfig.get("GET_OFFERS_OPTIONS_COLOR_CODE").split(",");
		offersColorCodeMap = new HashMap<>();
		for (int i = 0; i < offersColorCodeArr.length; i++) {
			offersColorCodeMap.put(offersMapArr[i], offersColorCodeArr[i]);
		}

		String[] amountsArray = "20,50,100".split(",");
		redeemAmountsList = Arrays.asList(amountsArray);
		
		String[] depList = appConfig.get("DEPRECATED_VERSION").split(",");
		depricatedList = Arrays.asList(depList);
		String[] operatorArray = "Airtel,Vodafone,Reliance,Idea,Aircel,BSNL,MTNL,TataDocomo,Uninor,MTS,Videocon,LoopMobile".split(",");
		redeemOperatorsList = Arrays.asList(operatorArray);
		
		String[] offerIdIgnore = appConfig.get("OFFER_ID_IGNORE").split(",");
		offerIdIgnoreList = new ArrayList<>(5);
		for (int i=0; i<offerIdIgnore.length; i++) {
			offerIdIgnoreList.add(Long.parseLong(offerIdIgnore[i]));
		}
		System.out.println("offerIdIgnoreList : "+offerIdIgnoreList);
		
		String[] installEdrOnHoldOffer = appConfig.get("INSTALL_EDR_ON_HOLD_OFFERID").split(",");
		installEdrOnHoldList = new ArrayList<>(installEdrOnHoldOffer.length);
		for(int i=0; i<installEdrOnHoldOffer.length; i++){
			installEdrOnHoldList.add(Long.parseLong(installEdrOnHoldOffer[i]));
		}
		
		String[] offerIdBlockedInStartedOffer = appConfig.get("OFFERID_BLOCKED_FROM_OFFERS_STARTED").split(",");
		offerIdBlockedInOffersStarted = new ArrayList<>(offerIdBlockedInStartedOffer.length);
		for(int i=0; i<offerIdBlockedInStartedOffer.length; i++){
			offerIdBlockedInOffersStarted.add(Long.parseLong(offerIdBlockedInStartedOffer[i]));
		}
		System.out.println("offerIdBlockedInOffersStarted : "+offerIdBlockedInOffersStarted);
		
		String[] newUserAmountBL =  appConfig.get("NEW_USER_INSTALL_APPKEY_BLACKLIST").split(",");
		newUserInstallAKBL = Arrays.asList(newUserAmountBL);
		
		String amazonSeriesSusAR [] =  appConfig.get("AMAZON_SERIES_SUS").split(",");
		amazonSeriesSus = Arrays.asList(amazonSeriesSusAR);
		
		
		String OFFERID_BARREDAR [] =  appConfig.get("OFFERID_BARRED").split(",");
		OFFERID_BARRED = Arrays.asList(OFFERID_BARREDAR);
		
		
		String REAL_OFFER_PAYOUTTYPE1 [] =  appConfig.get("REAL_OFFER_PAYOUTTYPE").split(",");
		REAL_OFFER_PAYOUTTYPE = Arrays.asList(REAL_OFFER_PAYOUTTYPE1);
		appVersionUpdate = new HashMap<String,String>();
		String appVU[] = appConfig.get("AppVersionUpdate").split(",");
		for(String appVU1:appVU) {
			appVersionUpdate.put(appVU1.split("-")[0],appVU1.split("-")[1]);
		}
		
		extraAmountInCallBackOfferIDAmount = new HashMap<Long,Integer>();
		extraAmountInCallBackOfferIDCount = new HashMap<Long,Integer>();
		String [] ExtraAmountInCallBackOfferID1 = appConfig.get("EXTRAAMOUNT_IN_CALLBACK_OFFERID").split(",");
		for(String eaicbo1:ExtraAmountInCallBackOfferID1) {
			extraAmountInCallBackOfferIDAmount.put(Long.parseLong(eaicbo1.split("-")[0]),Integer.parseInt(eaicbo1.split("-")[1]));
			extraAmountInCallBackOfferIDCount.put(Long.parseLong(eaicbo1.split("-")[0]),Integer.parseInt(eaicbo1.split("-")[2]));
		}
		
		String appCounterPromoDetails [] = appConfig.get("AppCounterPromoDetails").split("#");
		setAppCounter(Integer.parseInt(appCounterPromoDetails[0]));
		setAppCounterAppPayout(Integer.parseInt(appCounterPromoDetails[1]));
		setAppCounterAppPayoutMsg(appCounterPromoDetails[2]);
		
	}
	
	
	@Override
	public ConcurrentHashMap<String, String> getAppConfig() {
		return appConfig;
	}

	@Override
	public ConcurrentHashMap<String, String> getLocaleTextTemplate() {
		return localeTextTemplate;
	}

	public List<Long> getInstallEdrOnHoldList() {
		return installEdrOnHoldList;
	}

	@Override
	public Map<String, String> getOperatorKeyMap() {
		return operatorKeyMap;
	}

	@Override
	public String getOperatorKey(String operator) {
		return operatorKeyMap.get(operator);
	}

	@Override
	public Map<String, Integer> getOperatorIdMap() {
		return operatorIdMap;
	}

	@Override
	public Integer getOperatorId(String operator) {
		return operatorIdMap.get(operator);
	}

	@Override
	public Map<String, Integer> getCircleIdMap() {
		return circleIdMap;
	}

	@Override
	public Integer getCircleId(String circle) {
		return circleIdMap.get(circle);
	}

	@Override
	public List<String> getCompetitorAppList() {
		return competitorAppList;
	}

	@Override
	public int getCOMPETITOR_USER_STATUS() {
		return COMPETITOR_USER_STATUS;
	}

	@Override
	public List<Long> getSurveyOfferIdList() {
		return surveyOfferIdList;
	}

	@Override
	public String[] getOffersMapArr() {
		return offersMapArr;
	}

	public void setOffersMapArr(String[] offersMapArr) {
		this.offersMapArr = offersMapArr;
	}

	public void setOperatorKeyMap(Map<String, String> operatorKeyMap) {
		this.operatorKeyMap = operatorKeyMap;
	}

	public void setOperatorIdMap(Map<String, Integer> operatorIdMap) {
		this.operatorIdMap = operatorIdMap;
	}

	public void setCircleIdMap(Map<String, Integer> circleIdMap) {
		this.circleIdMap = circleIdMap;
	}

	public void setCompetitorAppList(List<String> competitorAppList) {
		this.competitorAppList = competitorAppList;
	}

	public void setCOMPETITOR_USER_STATUS(int cOMPETITOR_USER_STATUS) {
		COMPETITOR_USER_STATUS = cOMPETITOR_USER_STATUS;
	}

	public void setSurveyOfferIdList(List<Long> surveyOfferIdList) {
		this.surveyOfferIdList = surveyOfferIdList;
	}

	@Override
	public Map<String, String> getOffersColorCodeMap() {
		return offersColorCodeMap;
	}

	@Override
	public List<String> getRedeemOperatorsList() {
		return redeemOperatorsList;
	}

	@Override
	public List<String> getRedeemAmountsList() {
		return redeemAmountsList;
	}

	@Override
	public void pendingCreditsInsert(OfferDetails offerDetails, Long ettId) {
		if (offerDetails.getPendingRecCount() <= 0|| offerDetails.getPendingAmountCredit() <= 0) {
			return;
		}
		float pendingCAmount = offerDetails.getPendingAmountCredit()
				/ offerDetails.getPendingRecCount();
		PendingCredits pendingCredits = new PendingCredits();
		pendingCredits.setId(ettId + "_" + offerDetails.getOfferId());
		pendingCredits.setEttId(ettId);
		pendingCredits.setOfferId(offerDetails.getOfferId());
		pendingCredits.setAppKey(offerDetails.getAppKey());
		pendingCredits.setAmount(pendingCAmount);
		DateTime dt1 = new DateTime().plusDays(offerDetails.getBalanceCreditInDays());
		Date d1 = dt1.toDate();
		pendingCredits.setCreditDate(d1);
		pendingCredits.setVendor(offerDetails.getVendor());
		pendingCredits.setOfferCat(offerDetails.getOfferCat());
		pendingCreditsRepository.save(pendingCredits);
		LOGGER.info("added to pendingCredits table ettId {}", ettId);

		// Recuring
		for (int i = 1; i < offerDetails.getPendingRecCount(); i++) {
			pendingCredits = new PendingCredits();
			pendingCredits.setId(ettId + "_" + offerDetails.getOfferId() + "_"
					+ i);
			pendingCredits.setEttId(ettId);
			pendingCredits.setOfferId(offerDetails.getOfferId());
			pendingCredits.setAppKey(offerDetails.getAppKey());
			pendingCredits.setAmount(pendingCAmount);
			dt1 = dt1.plusDays(offerDetails.getPendingRecDay());
			Date dt2 = dt1.toDate();
			pendingCredits.setCreditDate(dt2);
			pendingCredits.setVendor(offerDetails.getVendor());
			pendingCredits.setOfferCat(offerDetails.getOfferCat());
			pendingCreditsRepository.save(pendingCredits);
		}

		if ((offerDetails.getCallbackNotification() == null || offerDetails.getCallbackNotification().trim().equals(""))&& (offerDetails.getInstalledNotification() == null || offerDetails.getInstalledNotification().trim().equals(""))) {
			User user = userRepository.findByEttId(ettId);
			String pushText = localeTextTemplate.get("DFRAGMENT_TEXT"+"_"+user.getLocale())
					.replaceFirst("#OFFER_NAME#", offerDetails.getOfferName())
					.replaceFirst("#OFFERAMOUNT#",
							offerDetails.getPendingAmountCredit() + "")
					.replaceFirst("#OFFER_CREDIT_DAYS#",
							offerDetails.getBalanceCreditInDays() + "");
			//DeviceToken dToken = deviceTokenRepository.findByDeviceId(user.getDeviceId());
			DeviceToken dToken = deviceTokenRepository.findByEttId(user.getEttId());
			sendPush(pushText, dToken,user.getEttId());
		}

	}

	@Override
	public void pendingCreditsInsertNew(OfferDetailsNew offerDetails, Long ettId) {
		if (offerDetails.getPendingRecCount() <= 0
				|| offerDetails.getPendingAmountCredit() <= 0) {
			return;
		}
		float pendingCAmount = offerDetails.getPendingAmountCredit()
				/ offerDetails.getPendingRecCount();
		PendingCredits pendingCredits = new PendingCredits();
		pendingCredits.setId(ettId + "_" + offerDetails.getOfferId());
		pendingCredits.setEttId(ettId);
		pendingCredits.setOfferId(offerDetails.getOfferId());
		pendingCredits.setAppKey(offerDetails.getAppKey());
		pendingCredits.setAmount(pendingCAmount);
		DateTime dt1 = new DateTime().plusDays(offerDetails
				.getBalanceCreditInDays());
		Date d1 = dt1.toDate();
		pendingCredits.setCreditDate(d1);
		pendingCredits.setVendor(offerDetails.getVendor());
		pendingCredits.setOfferCat(offerDetails.getOfferCat());
		pendingCreditsRepository.save(pendingCredits);
		LOGGER.info("added to pendingCredits table ettId {}", ettId);

		// Recuring
		for (int i = 1; i < offerDetails.getPendingRecCount(); i++) {
			pendingCredits = new PendingCredits();
			pendingCredits.setId(ettId + "_" + offerDetails.getOfferId() + "_"
					+ i);
			pendingCredits.setEttId(ettId);
			pendingCredits.setOfferId(offerDetails.getOfferId());
			pendingCredits.setAppKey(offerDetails.getAppKey());
			pendingCredits.setAmount(pendingCAmount);
			dt1 = dt1.plusDays(offerDetails.getPendingRecDay());
			Date dt2 = dt1.toDate();
		
			pendingCredits.setCreditDate(dt2);
			pendingCredits.setVendor(offerDetails.getVendor());
			pendingCredits.setOfferCat(offerDetails.getOfferCat());
			pendingCreditsRepository.save(pendingCredits);
		}

		if ((offerDetails.getCallbackNotification() == null || offerDetails
				.getCallbackNotification().trim().equals(""))
				&& (offerDetails.getInstalledNotification() == null || offerDetails
						.getInstalledNotification().trim().equals(""))) {
			User user = userRepository.findByEttId(ettId);
			String pushText = localeTextTemplate.get("DFRAGMENT_TEXT"+"_"+user.getLocale())
					.replaceFirst("#OFFER_NAME#", offerDetails.getOfferName())
					.replaceFirst("#OFFERAMOUNT#",
							offerDetails.getPendingAmountCredit() + "")
					.replaceFirst("#OFFER_CREDIT_DAYS#",
							offerDetails.getBalanceCreditInDays() + "");
			//DeviceToken dToken = deviceTokenRepository.findByDeviceId(user.getDeviceId());
			DeviceToken dToken = deviceTokenRepository.findByEttId(user.getEttId());
			sendPush(pushText, dToken,user.getEttId());
		}

	}

	
	public void giveBalance(Long ettId, String tId, OfferDetails offerDetails,String vendor,User user) {
		float amount = offerDetails.getOfferAmount();
		boolean amountGiven = false;
		if(getAppConfig().get("EXTRAAMOUNT_IN_CALLBACK").equals("true") && extraAmountInCallBackOfferIDAmount.containsKey(offerDetails.getOfferId())) {
			List<UserAccountSummary> accountSummaries = userAccountSummaryRepository.findByOfferId(offerDetails.getOfferId());
			LOGGER.info("Extra amount check pass given for ettId{},offerId{},size{}",ettId,offerDetails.getOfferId(),accountSummaries.size());
			if(accountSummaries!=null && (accountSummaries.size()%extraAmountInCallBackOfferIDCount.get(offerDetails.getOfferId())==0)) {
				LOGGER.info("Extra amount given for ettId{},offerId{},amount{}",ettId,offerDetails.getOfferId(),extraAmountInCallBackOfferIDAmount.get(offerDetails.getOfferId()));
				amount = amount+extraAmountInCallBackOfferIDAmount.get(offerDetails.getOfferId());
			}
		}
		LOGGER.info("amount="+amount+"[ettId]"+ettId);
		if (offerDetails.getBalanceCreditInDays() > 0) {
			amount = amount - offerDetails.getPendingAmountCredit();
		}
		UserAccount userAccount = userAccountRepository.findByEttId(ettId);
		
		if(userAccount == null) {
			userAccount = new UserAccount();
			userAccount.setCurrentBalance(0);
			userAccount.setEttId(ettId);
			userAccountRepository.save(userAccount);
			
		}
		{
			LOGGER.info("inside userAccountSummary null"+ettId);
			List<UserAccountSummary> userAccountSummaryList = userAccountSummaryRepository.findByEttIdAndOfferId(ettId, offerDetails.getOfferId());
			if (userAccountSummaryList != null || userAccountSummaryList.size()>0) {
				List<UserAccountSummary> userAccountSummaryListf = new ArrayList<UserAccountSummary>();
				
				for(UserAccountSummary accountSummary : userAccountSummaryList) {
					if(accountSummary.isDeviceIdFlage()==false) {
						userAccountSummaryListf.add(accountSummary);
					}
				}
				if(userAccountSummaryListf.size()>0) {
					userAccountSummaryList.removeAll(userAccountSummaryListf);
				}
				
			}
			if (userAccountSummaryList == null || userAccountSummaryList.size()<=0) {
				UserAccountSummary userAccountSummary = new UserAccountSummary();
				userAccountSummary.setEttId(ettId);
				userAccountSummary.setOfferId(offerDetails.getOfferId());
				userAccountSummary.setOfferName(offerDetails.getOfferName());
				userAccountSummary.setAmount(amount);
				userAccountSummary.setRemarks(tId);
				userAccountSummary.setVendor(vendor);
				userAccountSummary.setOfferCat(offerDetails.getOfferCat());
				userAccountSummaryRepository.save(userAccountSummary);
				LOGGER.info("save callback without install in UserAccountSummary table : "+ ettId);

			} else {
				UserAccountSummary userAccountSummary = userAccountSummaryList.get(0);
				amount = amount - userAccountSummary.getAmount();
				userAccountSummary.setEttId(ettId);
				userAccountSummary.setOfferId(offerDetails.getOfferId());
				userAccountSummary.setOfferName(offerDetails.getOfferName());
				userAccountSummary.setAmount(amount+userAccountSummary.getAmount());
				userAccountSummary.setRemarks("CALLBACK");
				userAccountSummary.setVendor(offerDetails.getVendor());
				userAccountSummaryRepository.save(userAccountSummary);
				LOGGER.info("save callback in UserAccountSummary table : "
						+ ettId);
			}

			if(amount<0) {
				return;
			}
			float oldBalance = userAccount.getCurrentBalance();
			userAccount.setCurrentBalance((oldBalance + amount));
			int oldcoin = userAccount.getBalanceCoins();
			userAccount.setBalanceCoins(oldcoin + offerDetails.getCoins());
			userAccount = userAccountRepository.save(userAccount);
			LOGGER.info("credited amount in UserAccount table : ettId=" + ettId+ "&oldBalance=" + oldBalance + "&currentBalance="+ userAccount.getCurrentBalance() + "&oldcoin=" + oldcoin+ "& currentCoin=" + userAccount.getBalanceCoins());
			amountGiven = true;
			//String pushText = offerDetails.getCallbackNotification();
			String pushText = null;
			OfferTextDetails offerTextDetail = offerTextDetailsRepository.findById(offerDetails.getOfferId()+"_"+user.getLocale());
			if(offerTextDetail==null || offerTextDetail.getInstallNotification() ==null) {
				pushText = offerDetails.getInstalledNotification();
			}
			else {
					pushText = offerTextDetail.getInstallNotification();
			}	                       

			if (amount > 0) {
				// String pushText = "";
				if (pushText == null || pushText.trim().equals("") || pushText.trim().equals("null")) {
					if (getSurveyOfferIdList().contains(
							offerDetails.getOfferId())) {
						pushText = getAppConfig().get("SURVEY_NOTIFICATION");
					} else {
						pushText = getAppConfig().get("MONEY_CREDITED_FOR_DOWNLOAD");
					}
				}
				pushText = pushText.replaceFirst("#AMOUNT#", amount + "").replaceFirst("#APP_NAME#", offerDetails.getOfferName()).replaceFirst("#OFFER_NAME#", offerDetails.getOfferName()).replaceFirst("#OFFERAMOUNT#",offerDetails.getPendingAmountCredit() + "").replaceFirst("#OFFER_CREDIT_DAYS#",offerDetails.getBalanceCreditInDays() + "");
				//User user = userRepository.findByEttId(ettId);
				//DeviceToken dToken = deviceTokenRepository.findByDeviceId(user.getDeviceId());
				DeviceToken dToken = deviceTokenRepository.findByEttId(user.getEttId());
				sendPush(pushText, dToken,user.getEttId());
			}
		}
		
		
		if(getAppConfig().get("UserTargetAmountBaseCheck").equals("true") && amountGiven && amount>0) {
			getBalanceToUserTarAmount(ettId,amount);
		}

	}



	public void giveBalanceNew(Long ettId, String tId, OfferDetailsNew offerDetails,String vendor,User user) {
		float amount = offerDetails.getOfferAmount();
		LOGGER.info("amount="+amount+"[ettId]"+ettId);
		if (offerDetails.getBalanceCreditInDays() > 0) {
			amount = amount - offerDetails.getPendingAmountCredit();
		}
		UserAccount userAccount = userAccountRepository.findByEttId(ettId);
		if (userAccount != null) {
			LOGGER.info("inside userAccountSummary null"+ettId);
			/*UserAccountSummary userAccountSummary = userAccountSummaryRepository
					.findByEttIdAndOfferId(ettId, offerDetails.getOfferId());*/
			List<UserAccountSummary> userAccountSummaryList = userAccountSummaryRepository
					.findByEttIdAndOfferId(ettId, offerDetails.getOfferId());
			if (userAccountSummaryList == null || userAccountSummaryList.size()<=0) {
				UserAccountSummary userAccountSummary = new UserAccountSummary();
				userAccountSummary.setEttId(ettId);
				userAccountSummary.setOfferId(offerDetails.getOfferId());
				userAccountSummary.setOfferName(offerDetails.getOfferName());
				userAccountSummary.setAmount(amount);
				// userAccountSummary.setCoin(offerDetails.getCoins());
				userAccountSummary.setRemarks(tId);
				userAccountSummary.setOfferCat(offerDetails.getOfferCat());
				userAccountSummary.setVendor(vendor);
				userAccountSummaryRepository.save(userAccountSummary);
				LOGGER.info("save callback without install in UserAccountSummary table : "
						+ ettId);

			} else {
				UserAccountSummary userAccountSummary = userAccountSummaryList.get(0);
				amount = amount - userAccountSummary.getAmount();
				userAccountSummary.setEttId(ettId);
				userAccountSummary.setOfferId(offerDetails.getOfferId());
				userAccountSummary.setOfferName(offerDetails.getOfferName());
				userAccountSummary.setAmount(amount+userAccountSummary.getAmount());
				// userAccountSummary.setCoin(offerDetails.getCoins());
				userAccountSummary.setRemarks("CALLBACK");
				userAccountSummary.setVendor(offerDetails.getVendor());
				userAccountSummary.setOfferCat(offerDetails.getOfferCat());
				userAccountSummary.setOfferCat(offerDetails.getOfferCat());
				userAccountSummaryRepository.save(userAccountSummary);
				LOGGER.info("save callback in UserAccountSummary table : "
						+ ettId);
			}

			if(amount<0) {
				return;
			}
			float oldBalance = userAccount.getCurrentBalance();
			userAccount.setCurrentBalance((oldBalance + amount));
			int oldcoin = userAccount.getBalanceCoins();
			userAccount.setBalanceCoins(oldcoin + offerDetails.getCoins());
			userAccount = userAccountRepository.save(userAccount);
			LOGGER.info("credited amount in UserAccount table : ettId=" + ettId
					+ "&oldBalance=" + oldBalance + "&currentBalance="
					+ userAccount.getCurrentBalance() + "&oldcoin=" + oldcoin
					+ "& currentCoin=" + userAccount.getBalanceCoins());

			UserGameCreditSummary userGameCreditSummary = new UserGameCreditSummary();
			userGameCreditSummary.setEttId(ettId);
			userGameCreditSummary.setOfferId(offerDetails.getOfferId());
			userGameCreditSummary.setOfferName(offerDetails.getOfferName());
			// userGameCreditSummary.setAmount(amount);
			userGameCreditSummary.setCoin(offerDetails.getCoins());
			userGameCreditSummary.setRemarks(tId);
			userGameCreditSummaryRepository.save(userGameCreditSummary);
			LOGGER.info("save callback in UserAccountSummary table : " + ettId);

			//String pushText = offerDetails.getCallbackNotification();
			String pushText = null;
			OfferTextDetails offerTextDetail = offerTextDetailsRepository.findById(offerDetails.getOfferId()+"_"+user.getLocale());
			if(offerTextDetail==null || offerTextDetail.getInstallNotification() ==null) {
				pushText = offerDetails.getInstalledNotification();
			}
			else {
					pushText = offerTextDetail.getInstallNotification();
			}	                       

			if (amount > 0) {
				// String pushText = "";
				//User user = userRepository.findByEttId(ettId);
				if (pushText == null || pushText.trim().equals("") || pushText.trim().equals("null")) {
					if (getSurveyOfferIdList().contains(
							offerDetails.getOfferId())) {
						pushText = localeTextTemplate.get("SURVEY_NOTIFICATION_"+user.getLocale());
					} else {
						pushText = localeTextTemplate.get("MONEY_CREDITED_FOR_DOWNLOAD_"+user.getLocale());
					}
				}
				pushText = pushText
						.replaceFirst("#AMOUNT#", amount + "").replaceFirst("#APP_NAME#", offerDetails.getOfferName()).replaceFirst("#OFFER_NAME#", offerDetails.getOfferName()).replaceFirst("#OFFERAMOUNT#",offerDetails.getPendingAmountCredit() + "").replaceFirst("#OFFER_CREDIT_DAYS#",offerDetails.getBalanceCreditInDays() + "");
				//DeviceToken dToken = deviceTokenRepository.findByDeviceId(user.getDeviceId());
				DeviceToken dToken = deviceTokenRepository.findByEttId(user.getEttId());
				sendPush(pushText, dToken,user.getEttId());
			}
		}

	}
	
	@Override
	public void giveBonusRetargettingAmountToUser(User user, String tId, OfferDetails offerDetails, float amount) {
		UserAccount userAccount = userAccountRepository.findByEttId(user.getEttId());
		//float amount = offerDetails.getInstalledAmount();
		if (userAccount != null) {
			float oldBalance = userAccount.getCurrentBalance();
			userAccount.setCurrentBalance((oldBalance + amount));
			userAccount = userAccountRepository.save(userAccount);
			LOGGER.info("credited Retargetting Bonus amount in UserAccount table : ettId="+ user.getEttId()+ "&oldBalance="+ oldBalance+ "&currentBalance=" + userAccount.getCurrentBalance());
			UserAccountSummary userAccountSummary = new UserAccountSummary();
			userAccountSummary.setEttId(user.getEttId());
			userAccountSummary.setOfferId((long) 928);
			userAccountSummary.setOfferName("BONUS");
			userAccountSummary.setAmount(amount);
			// userAccountSummary.setCoin(offerDetails.getCoins());
			// userAccountSummary.setRemarks(tId);
			userAccountSummary.setRemarks("BONUS_RETARGETTING_"+offerDetails.getOfferName());
			userAccountSummary.setVendor(offerDetails.getVendor());
			userAccountSummary.setOfferCat(offerDetails.getOfferCat());
			userAccountSummaryRepository.save(userAccountSummary);
			LOGGER.info("save Bonus Retargetting in UserAccountSummary table : "+ user.getEttId());
			
			String pushText = localeTextTemplate.get("NEW_USER_BONUS_RETARGETTING_PUSH_"+user.getLocale());
			if (pushText != null && (!pushText.trim().equals(""))){
				pushText = pushText.replaceFirst("#AMOUNT#", amount + "");
				DeviceToken dToken = deviceTokenRepository.findByEttId(user.getEttId());
				sendPush(pushText, dToken,user.getEttId());
			}
		}
	}

	public String toddMMyy(Date day){
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); String date = formatter.format(day); return date; 
		}

	@Override
	public void giveBonusAmountToNewUser(User user, String tId, OfferDetails offerDetails,float amount,int sameDay){
		UserAccount userAccount = userAccountRepository.findByEttId(user.getEttId());
		//float amount = offerDetails.getInstalledAmount();
		long offerId = 925l;
		String pushText = localeTextTemplate.get("NEW_USER_BONUS_PUSH_"+user.getLocale());
		if(sameDay==0) {
			offerId=925l;
			 Calendar cal = Calendar.getInstance();
			 cal.add(Calendar.DAY_OF_MONTH, 14);
			 Date d14 = cal.getTime();
			 pushText = localeTextTemplate.get("NEW_USER_D1_BONUS_FIRST_APP_"+user.getLocale()).replaceFirst("#DAY_14#", toddMMyy(d14));
		}
		else if(sameDay ==1)  {
			offerId=926l;Calendar cal = Calendar.getInstance();
			 cal.add(Calendar.DAY_OF_MONTH, 14);
			 Date d14 = cal.getTime();
			 pushText = localeTextTemplate.get("NEW_USER_D2_BONUS_FIRST_APP_"+user.getLocale()).replaceFirst("#DAY_14#", toddMMyy(d14));
		}
		else {
			offerId=927l;
			Calendar cal = Calendar.getInstance();
			 cal.add(Calendar.DAY_OF_MONTH, 14);
			 Date d14 = cal.getTime();
			 pushText = localeTextTemplate.get("NEW_USER_D3_BONUS_FIRST_APP_"+user.getLocale()).replaceFirst("#DAY_14#", toddMMyy(d14));
		}
		LOGGER.info("NEW USER BONUS ettId={},amount={},sameDay={},offerId={},pushText={}",user.getEttId(),amount,sameDay,offerId,pushText);
		/**############ not giving the money for D1,D2,D3 User #######################*/
		/*if (userAccount != null) {
			float oldBalance = userAccount.getCurrentBalance();
			userAccount.setCurrentBalance((oldBalance + amount));
			userAccount = userAccountRepository.save(userAccount);
			LOGGER.info("credited Bonus amount in UserAccount table : ettId="+ user.getEttId()+ "&oldBalance="+ oldBalance+ "&currentBalance=" + userAccount.getCurrentBalance());
			UserAccountSummary userAccountSummary = new UserAccountSummary();
			userAccountSummary.setEttId(user.getEttId());
			userAccountSummary.setOfferId(offerId);
			userAccountSummary.setOfferName("BONUS");
			userAccountSummary.setAmount(amount);
			userAccountSummary.setRemarks("BONUS_"+offerDetails.getOfferName());
			userAccountSummary.setVendor(offerDetails.getVendor());
			userAccountSummary.setOfferCat(offerDetails.getOfferCat());
			userAccountSummaryRepository.save(userAccountSummary);
			LOGGER.info("save Bonus in UserAccountSummary table : "+ user.getEttId());*/
			/**############ not giving the money for D1,D2,D3 User #######################*/
			
			
			if (pushText != null && (!pushText.trim().equals(""))){
				pushText = pushText.replaceFirst("#AMOUNT#", amount + "");
				DeviceToken dToken = deviceTokenRepository.findByEttId(user.getEttId());
				sendPush(pushText, dToken,user.getEttId());
			}
		}
	//}
	
	@Override
	public void giveBonusAmountToNewUserNew(User user, String string, OfferDetailsNew offerDetails, float amount,int sameDay) {
		UserAccount userAccount = userAccountRepository.findByEttId(user.getEttId());
		//float amount = offerDetails.getInstalledAmount();
		long offerId = 925l;
		if(sameDay==0) {
			offerId=925l;
		}
		else if(sameDay ==1)  {
			offerId=926l;
		}
		else {
			offerId=927l;
		}
		if (userAccount != null) {
			float oldBalance = userAccount.getCurrentBalance();
			userAccount.setCurrentBalance((oldBalance + amount));
			userAccount = userAccountRepository.save(userAccount);
			LOGGER.info("credited Bonus amount in UserAccount table : ettId="+ user.getEttId()+ "&oldBalance="+ oldBalance+ "&currentBalance=" + userAccount.getCurrentBalance());
			UserAccountSummary userAccountSummary = new UserAccountSummary();
			userAccountSummary.setEttId(user.getEttId());
			userAccountSummary.setOfferId(offerId);
			userAccountSummary.setOfferName("BONUS");
			userAccountSummary.setAmount(amount);
			// userAccountSummary.setCoin(offerDetails.getCoins());
			// userAccountSummary.setRemarks(tId);
			userAccountSummary.setRemarks("BONUS_"+offerDetails.getOfferName());
			userAccountSummary.setVendor(offerDetails.getVendor());
			userAccountSummary.setOfferCat(offerDetails.getOfferCat());
			userAccountSummaryRepository.save(userAccountSummary);
			LOGGER.info("save Bonus in UserAccountSummary table : "+ user.getEttId());
			
			String pushText = localeTextTemplate.get("NEW_USER_BONUS_PUSH_"+user.getLocale());
			if (pushText != null && (!pushText.trim().equals(""))){
				pushText = pushText.replaceFirst("#AMOUNT#", amount + "");
				DeviceToken dToken = deviceTokenRepository.findByEttId(user.getEttId());
				sendPush(pushText, dToken,user.getEttId());
			}
		}
		
	}

	@Override
	public void giveSomeAmountOnIntall(User user, String tId,OfferDetails offerDetails,float amount) {
		UserAccount userAccount = userAccountRepository.findByEttId(user.getEttId());
		//float amount = offerDetails.getInstalledAmount();
		if (userAccount != null) {
			float oldBalance = userAccount.getCurrentBalance();
			userAccount.setCurrentBalance((oldBalance + amount));

			userAccount = userAccountRepository.save(userAccount);
			LOGGER.info("credited installed amount in UserAccount table : ettId="+ user.getEttId()+ "&oldBalance="+ oldBalance+ "&currentBalance=" + userAccount.getCurrentBalance());
			UserAccountSummary userAccountSummary = new UserAccountSummary();
			userAccountSummary.setEttId(user.getEttId());
			userAccountSummary.setOfferId(offerDetails.getOfferId());
			userAccountSummary.setOfferName(offerDetails.getOfferName());
			userAccountSummary.setAmount(amount);
			// userAccountSummary.setCoin(offerDetails.getCoins());
			// userAccountSummary.setRemarks(tId);
			userAccountSummary.setRemarks("INSTALL");
			userAccountSummary.setVendor(offerDetails.getVendor());
			userAccountSummary.setOfferCat(offerDetails.getOfferCat());
			userAccountSummaryRepository.save(userAccountSummary);
			LOGGER.info("save intalled in UserAccountSummary table : "+ user.getEttId());

			//String pushText = offerDetails.getInstalledNotification();
			String pushText = null;
			OfferTextDetails offerTextDetail = offerTextDetailsRepository.findById(offerDetails.getOfferId()+"_"+user.getLocale());
			if(offerTextDetail==null || offerTextDetail.getInstallNotification() ==null) {
				pushText = offerDetails.getInstalledNotification();
			}
			else {
					pushText = offerTextDetail.getInstallNotification();
			}	                       
			if (pushText != null && (!pushText.trim().equals("")) && !pushText.trim().equals("null")) {
				pushText = pushText.replaceFirst("#AMOUNT#", amount + "");
				pushText = pushText.replaceFirst("#APP_NAME#",offerDetails.getOfferName());
				pushText = pushText.replaceFirst("#REMAMOUNT#",(offerDetails.getOfferAmount()-amount)+"");
				pushText = pushText.replaceFirst("#REMAMOUNT#", offerDetails.getPendingAmountCredit()+"").replaceFirst("#OFFER_CREDIT_DAYS#", offerDetails.getBalanceCreditInDays()+"").replaceFirst("#AMOUNT#", offerDetails.getOfferAmount()+"").replaceFirst("#APP_NAME#", offerDetails.getOfferName()).replaceFirst("#CALLBACKAMOUNT#",(offerDetails.getOfferAmount()-offerDetails.getInstalledAmount()+""));
				//DeviceToken dToken = deviceTokenRepository.findByDeviceId(user.getDeviceId());
				DeviceToken dToken = deviceTokenRepository.findByEttId(user.getEttId());
				sendPush(pushText, dToken,user.getEttId());
			}

		}

	}

	public void giveSomeAmountOnIntallNew(User user, String tId,OfferDetailsNew offerDetails,float amount) {
		UserAccount userAccount = userAccountRepository.findByEttId(user
				.getEttId());
		//float amount = offerDetails.getInstalledAmount();
		if (userAccount != null) {
			float oldBalance = userAccount.getCurrentBalance();
			userAccount.setCurrentBalance((oldBalance + amount));

			userAccount = userAccountRepository.save(userAccount);
			LOGGER.info("credited installed amount in UserAccount table : ettId="
					+ user.getEttId()
					+ "&oldBalance="
					+ oldBalance
					+ "&currentBalance=" + userAccount.getCurrentBalance());
			UserAccountSummary userAccountSummary = new UserAccountSummary();
			userAccountSummary.setEttId(user.getEttId());
			userAccountSummary.setOfferId(offerDetails.getOfferId());
			userAccountSummary.setOfferName(offerDetails.getOfferName());
			userAccountSummary.setAmount(amount);
			// userAccountSummary.setCoin(offerDetails.getCoins());
			// userAccountSummary.setRemarks(tId);
			userAccountSummary.setRemarks("INSTALL");
			userAccountSummary.setVendor(offerDetails.getVendor());
			userAccountSummary.setOfferCat(offerDetails.getOfferCat());
			userAccountSummaryRepository.save(userAccountSummary);
			LOGGER.info("save intalled in UserAccountSummary table : "
					+ user.getEttId());

			String pushText = offerDetails.getInstalledNotification();
			if (pushText != null && (!pushText.trim().equals("")) && !pushText.trim().equals("null")) {
				pushText = pushText.replaceFirst("#AMOUNT#", amount + "");
				pushText = pushText.replaceFirst("#APP_NAME#",offerDetails.getOfferName());
				pushText = pushText.replaceFirst("#REMAMOUNT#",(offerDetails.getOfferAmount()-amount)+"");
				pushText = pushText.replaceFirst("#REMAMOUNT#", offerDetails.getPendingAmountCredit()+"").replaceFirst("#OFFER_CREDIT_DAYS#", offerDetails.getBalanceCreditInDays()+"").replaceFirst("#AMOUNT#", offerDetails.getOfferAmount()+"").replaceFirst("#APP_NAME#", offerDetails.getOfferName()).replaceFirst("#CALLBACKAMOUNT#",(offerDetails.getOfferAmount()-offerDetails.getInstalledAmount()+""));
				//DeviceToken dToken = deviceTokenRepository.findByDeviceId(user.getDeviceId());
				DeviceToken dToken = deviceTokenRepository.findByEttId(user.getEttId());
				sendPush(pushText, dToken,user.getEttId());
			}

		}

	}

	private void sendPush(final String pushText, final DeviceToken dToken,final Long ettId) {
		if (pushText == null || pushText.trim().equals("") || pushText.trim().equals("null")) {
			return;
		}
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
				LOGGER.error("error in callback controller push " + e);
				e.printStackTrace();
			}
		}
	}

	public List<Long> getOfferIdIgnoreList() {
		return offerIdIgnoreList;
	}
	
	public List<Long> getOfferIdBlockedInOffersStarted(){
		return offerIdBlockedInOffersStarted;
	}
	
	public List <String> getNewUserInstallAKBL() {
		return newUserInstallAKBL;
	}

	@Override
	public List <String> getAmazonSeriesSus() {
		return amazonSeriesSus;
	}
	@Override
	public Map<String,String> getAppVersionUpdate() {
		return appVersionUpdate;
	}
	

	@Override
	public int getBlackListBlockCounter(){
		return BlackListBlockCounter;
	}

	@Override
	public Map<Integer,String> getIsDeviceSupport(){
		return isDeviceSupport;
	}

	@Override
	public List<String> getOFFERID_BARRED() {
		return OFFERID_BARRED;
	}

	@Override
	public List<String> getDepricatedList() {
		return depricatedList;
	}

	@Override
	public List<String> getREAL_OFFER_PAYOUTTYPE() {
		return REAL_OFFER_PAYOUTTYPE;
	}

	public void setREAL_OFFER_PAYOUTTYPE(List<String> rEAL_OFFER_PAYOUTTYPE) {
		REAL_OFFER_PAYOUTTYPE = rEAL_OFFER_PAYOUTTYPE;
	}

	@Override
	public int getAppCounter() {
		return appCounter;
	}

	public void setAppCounter(int appCounter) {
		this.appCounter = appCounter;
	}

	@Override
	public int getAppCounterAppPayout() {
		return appCounterAppPayout;
	}

	public void setAppCounterAppPayout(int appCounterAppPayout) {
		this.appCounterAppPayout = appCounterAppPayout;
	}

	@Override
	public String getAppCounterAppPayoutMsg() {
		return appCounterAppPayoutMsg;
	}

	public void setAppCounterAppPayoutMsg(String appCounterAppPayoutMsg) {
		this.appCounterAppPayoutMsg = appCounterAppPayoutMsg;
	}
	
	@Override
	public int checkTheMonthCountOffer(User user,OfferDetails offerDetails) {
		int counter = 0;
		int month = new Date().getMonth()+1;
		
		List<UserAccountSummary> accountSummaries = userAccountSummaryRepository.findByEttIdAndOfferId(user.getEttId(),offerDetails.getOfferId());
		if(accountSummaries!=null && accountSummaries.size()>0) {
			for(UserAccountSummary accountSummary: accountSummaries) {
				LOGGER.info("Time Check ettId={},month={},createdTime={}",user.getEttId(),month,accountSummary.getCreatedTime());
				if(accountSummary.getRemarks().equals("PENDING_CREDIT") || accountSummary.getRemarks().equals("DATA_USAGE")){
				}
				else if(accountSummary.getCreatedTime().getMonth()+1==month) {
					counter++;
					return counter;
				}
				else{
					//counter ++;
				}
			}
		}
		return counter;
	}

	@Override
	public void giveBonusAmountToReferal(User user) {
		UserSource userSource = userSourceRepository.findByEttId(user.getEttId());
		if(userSource.getUtmCampaign()!=null && (!userSource.getUtmCampaign().equals("")) && (!userSource.getUtmCampaign().equals("INVITE"))) {
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
            int sameDay = (int)(sameDay1/(1000*60*60*24));
            if(sameDay>2) {
            	LOGGER.info("not giving the bonus amount to invite the sameDay is greater then 2 sameDay={},ettId={}",sameDay,user.getEttId());
            	return;
            }
			UserAccount userAccount = userAccountRepository.findByEttId(user.getEttId());
			if(userAccount==null) {
				userAccount = new UserAccount();
			}
			userAccount.setCurrentBalance(userAccount.getCurrentBalance()+5.0f);
			userAccountRepository.save(userAccount);
			
			UserAccountSummary userAccountSummary = new UserAccountSummary();
			userAccountSummary.setEttId(user.getEttId());
			userAccountSummary.setOfferId(8881l);
			userAccountSummary.setOfferName("BONUS");
			userAccountSummary.setAmount(5.0f);
			userAccountSummary.setRemarks("BONUS");
			//userAccountSummary.setVendor(offerDetails.getVendor());
			//userAccountSummary.setOfferCat(offerDetails.getOfferCat());
			userAccountSummaryRepository.save(userAccountSummary);
			String pushText = getAppConfig().get("DAT_1_2_3_NOT_MEDIA_FIRST_PUSH_CREDIT");
			//DeviceToken dToken = deviceTokenRepository.findByDeviceId(user.getDeviceId());
			DeviceToken dToken = deviceTokenRepository.findByEttId(user.getEttId());
			sendPush(pushText, dToken,user.getEttId());
		}		
	}

	@Override
	public void getBalanceToUserTarAmount(long ettId, float amount) {
		UserTargetAmountBase targetAmountBase = userTargetAmountBaseRepository.findByEttId(ettId);
		if (targetAmountBase != null && targetAmountBase.isStatus() == false) {
			targetAmountBase.setTotalDayEarning(targetAmountBase.getTotalDayEarning() + (int) amount);
			if (targetAmountBase.getTotalDayEarning() >= targetAmountBase.getTargetAmount()) {
				targetAmountBase.setStatus(true);
				userTargetAmountBaseRepository.save(targetAmountBase);
				UserAccount userAccount = userAccountRepository.findByEttId(ettId);
				if (userAccount != null) {
					float oldBalance = userAccount.getCurrentBalance();
					userAccount.setCurrentBalance((oldBalance + targetAmountBase.getRewardAmount()));
					userAccount = userAccountRepository.save(userAccount);
				}

				UserAccountSummary userAccountSummary = new UserAccountSummary();
				userAccountSummary.setEttId(ettId);
				userAccountSummary.setOfferId(7773l);
				userAccountSummary.setOfferName("APP_DOWNLOAD_BONUS");
				userAccountSummary.setAmount(targetAmountBase.getRewardAmount());
				// userAccountSummary.setCoin(offerDetails.getCoins());
				// userAccountSummary.setRemarks(tId);
				userAccountSummary.setRemarks("APP_DOWNLOAD_BONUS");
				userAccountSummary.setVendor("RH");
				userAccountSummary.setOfferCat("");
				userAccountSummaryRepository.save(userAccountSummary);

				String pushText = targetAmountBase.getNotification();
				if (pushText != null && (!pushText.trim().equals(""))&& !pushText.trim().equals("null")) {
					DeviceToken dToken = deviceTokenRepository.findByEttId(ettId);
					sendPush(pushText, dToken, ettId);
				}
			} 
			else {
				userTargetAmountBaseRepository.save(targetAmountBase);
			}
		}
	}
	@Override
	public float getShareTabAppVersion() {
		return shareTabAppVersion;
	}

	public void setShareTabAppVersion(float shareTabAppVersion) {
		this.shareTabAppVersion = shareTabAppVersion;
	}


	@Override
	public void giveBalance(Long ettId, String tId, OfferDetails offerDetails,
			String vendor) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void giveBalanceNew(Long ettId, String tId,
			OfferDetailsNew offerDetailsNew, String vendor) {
		// TODO Auto-generated method stub
		
	}

}
