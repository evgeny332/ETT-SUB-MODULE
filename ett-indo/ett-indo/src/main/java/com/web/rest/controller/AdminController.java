package com.web.rest.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

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

import com.domain.entity.BreakingAlerDailyCheck;
import com.domain.entity.CallBackConfirmation;
import com.domain.entity.CallBackConfirmationDeviceChange;
import com.domain.entity.CountryDetails;
import com.domain.entity.DeviceIdInstalledApps;
import com.domain.entity.DeviceToken;
import com.domain.entity.Edr;
import com.domain.entity.InstalledApps;
import com.domain.entity.PopUpAlert;
import com.domain.entity.User;
import com.domain.entity.UserAccount;
import com.domain.entity.UserAccountSummary;
import com.domain.entity.UserBlackList;
import com.domain.entity.UserDeviceIdChange;
import com.domain.entity.UserSource;
import com.repository.jpa.BreakingAlerDailyCheckRepository;
import com.repository.jpa.CallBackConfirmationDeviceChangeRepository;
import com.repository.jpa.CallBackConfirmationRepository;
import com.repository.jpa.CountryDetailsRepository;
import com.repository.jpa.DeviceIdInstalledAppsRepository;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.EdrRepository;
import com.repository.jpa.InstalledAppsFirstTimeRepository;
import com.repository.jpa.InstalledAppsRepository;
import com.repository.jpa.PopUpAlertRepository;
import com.repository.jpa.TodaysOffersRepository;
import com.repository.jpa.TransactionTrackerRepository;
import com.repository.jpa.UserAccountRepository;
import com.repository.jpa.UserAccountSummaryRepository;
import com.repository.jpa.UserBlackListRepository;
import com.repository.jpa.UserDeviceIdChangeRepository;
import com.repository.jpa.UserDublicateCallBackPreventRepository;
import com.repository.jpa.UserRepository;
import com.service.CustomDatabaseService;
import com.service.EttApis;
import com.service.FirstHitService;
import com.service.RechargeService;
import com.service.ReferrerService;
import com.web.rest.dto.CountryDetailsDto;
import com.web.rest.dto.PopUpAlertDto;
import com.web.rest.dto.SplaseScreenData;

/**
 * @author ankur
 */
@Controller
@RequestMapping(value = "/v1")
public class AdminController {

	private static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

	@Resource
	UserAccountRepository userAccountRepository;

	@Resource
	DeviceTokenRepository deviceTokenRepository;
	
	@Resource
	EdrRepository edrRepository;
	
	@Resource
	UserRepository userRepository;

	@Resource
	UserDublicateCallBackPreventRepository userDublicateCallBackPreventRepository;

	@Resource
	CustomDatabaseService customDatabaseService;
	
	@Resource
	UserAccountSummaryRepository userAccountSummaryRepository;

	@Resource
	CountryDetailsRepository countryDetailsRepository;

	@Resource
	UserBlackListRepository userBlackListRepository;

	@Resource
	DeviceIdInstalledAppsRepository deviceIdInstalledAppsRepository;
	
	
	@Resource
	UserDeviceIdChangeRepository userDeviceIdChangeRepository;

	@Resource
	RechargeService rechargeService;

	@Resource
    private BreakingAlerDailyCheckRepository breakingAlerDailyCheckRepository;

	@Resource
	InstalledAppsRepository installedAppsRepository;

	@Resource
	TransactionTrackerRepository transactionTrackerRepository;

	@Resource
	TodaysOffersRepository todaysOffersRepository;
	@Autowired
	private ReferrerService referrerService;

	@Resource
	InstalledAppsFirstTimeRepository installedAppsFirstTimeRepository;

	@Resource
	UserAccountSummaryRepository accountSummaryRepository;
	@Resource
    private FirstHitService firstHitDayService;
	
	@Autowired
	private CallBackConfirmationRepository callBackConfirmationRepository;
	
	@Resource
	private CallBackConfirmationDeviceChangeRepository callbackConfirmationDeviceChangeRepository;

	@Resource
	private PopUpAlertRepository popUpAlertRepository;

	@Resource
	EttApis ettApis;
	
	@RequestMapping(value = "/account/update/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> push(@RequestParam("ettId") long ettId,
								@RequestParam("amount") float amount,
								@RequestParam("offerId") Long offerId,
								@RequestParam("offerName") String offerName,
								@RequestParam("password") String password
								)
	{

		LOGGER.info("API /account/update/ ettId={}, amount={}, offerId={}, offerName={}",ettId, amount, offerId, offerName);

			if(ettApis.getBlackListStatus(ettId)) {
				LOGGER.info("ettId BlackListed {}",ettId);
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}


		if(!password.equals("rh@321rh@")){
			ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		giveBalance(ettId, System.currentTimeMillis()+"", amount, offerId, offerName);
		return new ResponseEntity<>(HttpStatus.OK);
	}


	@RequestMapping(value = "/infoData/data/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> push(@RequestParam("ettId") long ettId,
								@RequestParam(value="msg", required=false, defaultValue="") String msg
								)
							{

		LOGGER.info("API /info data/data/ ettId={}, msg={},",ettId, msg);
		return new ResponseEntity<>(HttpStatus.OK);

	}
	@RequestMapping(value = "/deviceToken/update/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> push(@RequestParam("ettId") long ettId,
								@RequestParam(value="deviceId", required=false, defaultValue="") String deviceId,
								@RequestParam(value="deviceToken", required=false, defaultValue="") String deviceToken,
								@RequestParam("otp") int otp,
								@RequestParam(value="tempOtp", required=false, defaultValue="0") int tempOtp,
								@RequestParam(value="androidId", required=false, defaultValue="") String androidId,
								@RequestParam(value="email", required=false, defaultValue="") String email,
								@RequestParam(value="advertisingId", required=false, defaultValue="") String advertisingId,
								@RequestParam(value="appVersion", required=false, defaultValue="") String appVersion,
								@RequestParam(value="macAddress", required=false, defaultValue="") String macAddress
								)
							{

		LOGGER.info("API /deviceToken/update/ ettId={}, deviceId={}, deviceToken={}, otp={}, tempOtp={},androidId={},email={},advertisingId={},appVersion={},macAddress={}",ettId, deviceId, deviceToken, otp,tempOtp,androidId,email,advertisingId,appVersion,macAddress);

		if(ettApis.getBlackListStatus(ettId)) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = userRepository.findByEttId(ettId);
		if(user.getOtp()!=otp){
    		LOGGER.warn("ettId"+ettId+"is not verified but still trying to send install edr");
    		ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
		if(deviceId == null || deviceId.length()==0){
			deviceId=androidId;
		}
				/****************Installed Apps Handling********************/
		if(rechargeService.getAppConfig().get("INSTALLED_APP_DEVICEID_CHECK").equals("true")){
			List<String> installedAppsFirstTime = installedAppsFirstTimeRepository.findAppkeyByEttId(ettId);
			LOGGER.info("ettId="+user.getEttId()+" | installedAppsFirstTime size : "+installedAppsFirstTime.size());
			if(!deviceId.equals(user.getDeviceId())){
				LOGGER.info("deviceId="+user.getDeviceId()+" changed to "+deviceId+" for ettId {}",user.getEttId());
				installedAppsToDeviceInstalledApps(user);
				List<String> devicdIdAppsList = deviceIdInstalledAppsRepository.findAppkeyByDeviceId(deviceId);
				if(devicdIdAppsList == null || devicdIdAppsList.size()<=0){
					LOGGER.info("deviceIdApp Not Found! for ettId="+user.getEttId());
					List<Long> ettIds = userRepository.findByDeviceId_2(deviceId);
					if(ettIds != null && ettIds.size()>0){
						LOGGER.info("ettIds on the same device are : "+ettIds);
						List<String> uniqueInstalls = new ArrayList<>();
						for(int i=0; i<ettIds.size(); i++){
							uniqueInstalls = getUniqueInstalledApps(ettIds.get(i),uniqueInstalls);
						}
						uniqueInstalls.removeAll(installedAppsFirstTime);
						installedAppsFirstTime.addAll(uniqueInstalls);
						LOGGER.info("final InstalledApps : "+installedAppsFirstTime+" | ettId="+user.getEttId());
					}
				}
				else {
					LOGGER.info("deviceId Apps are : "+devicdIdAppsList+" for ettId="+user.getEttId());
					devicdIdAppsList.removeAll(installedAppsFirstTime);
					installedAppsFirstTime.addAll(devicdIdAppsList);
					//deviceIdInstalledAppsRepository.deleteDeviceId(deviceId);
					LOGGER.info("final InstalledApps : "+installedAppsFirstTime+" for ettId="+user.getEttId());
				}
				if(installedAppsFirstTime.size()>0){
					LOGGER.info("Installed Apps final list to save : "+installedAppsFirstTime.size()+" | ettId="+user.getEttId());
					updateUserAppkey(installedAppsFirstTime,user);
//					user.setDeviceId(deviceId);
//					userRepository.save(user);
//					LOGGER.info("User deviceId changed to "+deviceId);
				}
			}
			else {
				LOGGER.info("deviceId same for ettId="+user.getEttId());
				List<Long> ettIds = userRepository.findByDeviceId_2(deviceId);
				if(ettIds != null && ettIds.size()>1){
					LOGGER.info("User has "+ettIds+" ettIds for the same device");
					List<String> uniqueInstalls = new ArrayList<>();
					for(int i=0; i<ettIds.size(); i++){
						uniqueInstalls = getUniqueInstalledApps(ettIds.get(i),uniqueInstalls);
					}
					LOGGER.info("Unique installs of InstalledApps  : "+uniqueInstalls+" | ettId="+user.getEttId());
					uniqueInstalls.removeAll(installedAppsFirstTime);
					installedAppsFirstTime.addAll(uniqueInstalls);
					LOGGER.info("final InstalledApps : "+installedAppsFirstTime+" | ettId="+user.getEttId());
				}
				if(installedAppsFirstTime.size()>0){
					List<InstalledApps> installedAppsFinal = new ArrayList<>(installedAppsFirstTime.size());
					for(String installedApp : installedAppsFirstTime){
						InstalledApps installedApps2 = new InstalledApps();
						installedApps2.setId(user.getEttId()+"_"+installedApp);
						installedApps2.setEttId(user.getEttId());
						installedApps2.setAppKey(installedApp);
						installedApps2.setCreatedTs(new Date());
						installedAppsFinal.add(installedApps2);
					}
					//installedAppsRepository.save(installedAppsFinal);
					customDatabaseService.saveInstalledApps(installedAppsFinal);
					//LOGGER.info("final Installed Apss to save : "+installedAppsFinal+" | ettId="+user.getEttId());
				}
			}
		}
		todaysOffersRepository.deleteEttIdOfferId(user.getEttId());
		
		/***********************************************************/

		DeviceToken deviceT = deviceTokenRepository.findByEttId(user.getEttId());
		if(deviceT==null) {
			deviceT = new DeviceToken();
		}

		{
			if(!deviceId.equals(user.getDeviceId())){
				UserDeviceIdChange userDeviceIdChange = new UserDeviceIdChange();
				userDeviceIdChange.setEttId(user.getEttId());
				userDeviceIdChange.setOldDeviceId(user.getDeviceId());
				userDeviceIdChange.setNewDeviceId(deviceId);
				userDeviceIdChange.setCreatedTime(new Date());
				userDeviceIdChangeRepository.save(userDeviceIdChange);
				LOGGER.info("[UserDeviceIdChange] ettId={}, changed the device oldDeviceId={}, newDeviceId={}",ettId,user.getDeviceId(),deviceId);
			}
			deviceT.setDeviceToken(deviceToken);
			deviceT.setAdvertisingId(advertisingId);
			deviceT.setEmail(email);
			deviceT.setAndroidId(androidId);
			deviceT.setDeviceId(deviceId);
			deviceT.setEttId(ettId);
			deviceT.setMacAddress(macAddress);
			deviceT.setActive(true);
			deviceTokenRepository.save(deviceT);
			//User user = userRepository.findByEttId(ettId);
			user.setAppVersion(appVersion);
			user.setDeviceId(deviceId);
			user.setFlage(false);
			userRepository.save(user);
		}
		
		/*if( (((new java.util.Date()).getTime())-user.getCreatedTime().getTime()) <= 600000) {
			referrerService.giveInviteCredit(user);
			referrerService.setPromotionClickEvent(user);
		}
		else
		{
			LOGGER.info("Time is greater then 10 min so not going for invite amount ettId={}",ettId);
		}*/
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void updateUserAppkey(List<String> installedAppsFirstTime, User user) {
		//this data need to save in another table for reporting purpose.
		installedAppsRepository.deleteInstalledAppsByEttId(user.getEttId());
		transactionTrackerRepository.deleteTransactionTrackerByEttId(user.getEttId());
		userDublicateCallBackPreventRepository.deleteUserDublicateCallBackByEttId(user.getEttId());
		LOGGER.info("InstalledApps and TransactionTracker has been deleted for ettId=",user.getEttId());
		List<CallBackConfirmation> callBackConf = callBackConfirmationRepository.findByEttId(user.getEttId());
		for(CallBackConfirmation backConfirmation:callBackConf) {
			backConfirmation.setDeviceIdFlage(false);
		}
		callBackConfirmationRepository.save(callBackConf);
		LOGGER.info("set the DeviceId flage to false in CallBackConfirmation ettId={}",user.getEttId());
		List<Edr> edrs = edrRepository.findByEttId(user.getEttId()); 
		for(Edr edr: edrs) {
			edr.setDeviceIdFlage(false);
		}
		edrRepository.save(edrs);
		LOGGER.info("set the DeviceId flage to false in Edr ettId={}",user.getEttId());
		
		List<UserAccountSummary> accountSummaries = userAccountSummaryRepository.findByEttId(user.getEttId()); 
		for(UserAccountSummary accountS: accountSummaries) {
			accountS.setDeviceIdFlage(false);
		}
		userAccountSummaryRepository.save(accountSummaries);
		LOGGER.info("set the DeviceId flage to false in UserAccountSummary ettId={}",user.getEttId());
		try {
				List<InstalledApps> installedAppsFinal = new ArrayList<>(installedAppsFirstTime.size());
				for(String installedApp : installedAppsFirstTime){
					InstalledApps installedApps2 = new InstalledApps();
					installedApps2.setId(user.getEttId()+"_"+installedApp);
					installedApps2.setEttId(user.getEttId());
					installedApps2.setAppKey(installedApp);
					installedApps2.setCreatedTs(new Date());
					installedAppsFinal.add(installedApps2);
				}
				//installedAppsRepository.save(installedAppsFinal);
				customDatabaseService.saveInstalledApps(installedAppsFinal);
				LOGGER.info("ettId="+user.getEttId()+" | final appKey to save in InstalledApps="+installedAppsFinal.toString());
		}catch(Exception ex) {
			LOGGER.info("Error in saving in Installed Apps:{}",ex);
			ex.printStackTrace();
		}
	}


	private List<String> getUniqueInstalledApps(Long ettId, List<String> uniqueInstalls) {
		List<String> appKeys = installedAppsRepository.findAppkeyByEttId(ettId);
		if(appKeys.size()>0){
			appKeys.removeAll(uniqueInstalls);
			uniqueInstalls.addAll(appKeys);
			return uniqueInstalls;
		}
		return uniqueInstalls;
	}


	@RequestMapping(value = "/country/getCountryData/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getConfig(@RequestParam("ISO_CC") String ISO_CC
								)

	{
		LOGGER.info("/country/getCountryData/ ISO_CC {}",ISO_CC);
		CountryDetails countryDetails = countryDetailsRepository.findByIso_cc1(ISO_CC);



		return new ResponseEntity<>(getCountryDetailsDto(countryDetails),HttpStatus.OK);
	}

	@RequestMapping(value = "/user/setDeviceVerified/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> setDeviceVerified(@RequestParam("ettId") long ettId,
				@RequestParam("deviceVerified") int deviceVerified,
				@RequestParam("otp") int otp
				)
				{
				LOGGER.info("/user/setDeviceVerified/ ettId={},deviceVerified={},otp={}",ettId,deviceVerified,otp);
					if(ettApis.getBlackListStatus(ettId)) {
						LOGGER.info("ettId BlackListed {}",ettId);
						return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
					}

					User user = userRepository.findByEttId(ettId);
					if(user.getOtp()!=otp){
			    		LOGGER.warn("ettId"+ettId+"is not verified");
			    		ettApis.sendUNAUTHORIZEDEdr(ettId, 0);
			    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			    	}
					//if(rechargeService.getIsDeviceSupport().get(deviceVerified).equals("T")){
					if(rechargeService.getIsDeviceSupport().get(user.getIsDeviceSupport()).equals("T")){
							LOGGER.warn("ettId={} updating isDeviceSupport from {} to {}",user.getEttId(),user.getIsDeviceSupport(),deviceVerified);
							user.setIsDeviceSupport(deviceVerified);
							userRepository.save(user);
							
						}
						else {
							LOGGER.warn("ettId{}, isDeviceSupport{} but this parameter false so ignoring this",user.getEttId(),deviceVerified);
						}

					
					return new ResponseEntity<>(HttpStatus.OK);
	}


	@RequestMapping(value = "/user/splaseScreenData/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> splaseScreenData(@RequestParam(value="ettId", required=false, defaultValue="") long ettId,
											  @RequestParam(value="currentName", required=false, defaultValue="") String currentName
				)
				{
					LOGGER.info("/user/splaseScreenData/ ettId={},currentName{}",ettId,currentName);
					SplaseScreenData splaseScreenData =  new SplaseScreenData();
					splaseScreenData.setImageUrl(rechargeService.getAppConfig().get("SPLASE_SCREEN_IMAGE_URL"));
					splaseScreenData.setTimeout(Integer.parseInt(rechargeService.getAppConfig().get("SPLASE_SCREEN_TIMEOUT")));
					splaseScreenData.setCurrentName(rechargeService.getAppConfig().get("SPLASE_SCREEN_NAME"));
					return new ResponseEntity<>(splaseScreenData,HttpStatus.OK);
	}

	@RequestMapping(value = "/user/clickBreakingAlert/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> clickBreakingAlert(@RequestParam("ettId") long ettId,
				@RequestParam("type") String type
				)
				{
					LOGGER.info("/user/clickBreakingAlert/ ettId={},type={}",ettId,type);
					User user = userRepository.findByEttId(ettId);
					boolean isBreakingAlertToday = firstHitDayService.checkBreakingAlertDailyCheck(user);
					if(type.equals("CHECKIN") && isBreakingAlertToday == true) {
						PopUpAlert popUpAlert = popUpAlertRepository.findById(2l,true);

						BreakingAlerDailyCheck breakingAlerDailyCheck = new BreakingAlerDailyCheck();
						breakingAlerDailyCheck.setCreatedTime(new Date());
						breakingAlerDailyCheck.setEttId(user.getEttId());
						breakingAlerDailyCheckRepository.save(breakingAlerDailyCheck);
						if(popUpAlert !=null) {
							PopUpAlertDto popUpAlertDto = new PopUpAlertDto();
							popUpAlertDto.setActinoUrl(popUpAlert.getActinoUrl().split(";"));
							popUpAlertDto.setButtonsText(popUpAlert.getButtonsText().split(";"));
							popUpAlertDto.setHeading(popUpAlert.getHeading());
							popUpAlertDto.setNoOfButton(popUpAlert.getNoOfButton());
							popUpAlertDto.setText(popUpAlert.getText());
							return new ResponseEntity<>(popUpAlertDto,HttpStatus.OK);
						}
					}
					return new ResponseEntity<>(HttpStatus.OK);
	}


	private CountryDetailsDto getCountryDetailsDto(CountryDetails countryDetails){
		CountryDetailsDto countryDetailsDto = new CountryDetailsDto();
		countryDetailsDto.setISO_CC1(countryDetails.getIso_cc1());
		countryDetailsDto.setISO_CC2(countryDetails.getIso_cc2());
		countryDetailsDto.setCC(countryDetails.getCc());
		countryDetailsDto.setDomainName(countryDetails.getDomainName());
		countryDetailsDto.setName(countryDetails.getName());
		countryDetailsDto.setCurrency(countryDetails.getCurrency());
		return countryDetailsDto;
	}

	private void giveBalance(Long ettId, String tId, float amount, long offerId, String offerName) {
		UserAccount userAccount = userAccountRepository.findByEttId(ettId);
		if(userAccount != null){
			float oldBalance = userAccount.getCurrentBalance();
			userAccount.setCurrentBalance((oldBalance+amount));
			userAccount.setBalanceCoins(userAccount.getBalanceCoins()+0);
			userAccount = userAccountRepository.save(userAccount);
			LOGGER.info("/account/update/ : ettId="+ettId+"&oldBalance="+oldBalance + "&currentBalance="+userAccount.getCurrentBalance());
			UserAccountSummary userAccountSummary = new UserAccountSummary();
			userAccountSummary.setEttId(ettId);
			userAccountSummary.setOfferId(offerId);
			userAccountSummary.setOfferName(offerName);
			userAccountSummary.setAmount(amount);
			userAccountSummary.setRemarks(tId);
			userAccountSummaryRepository.save(userAccountSummary);
		}else{
			LOGGER.error("/account/update/ : ettId {} not found", ettId);
		}
	}

	public void deviceInstalledAppToInstalledApps(List<DeviceIdInstalledApps> devicdIdAppsList,String deviceId,Long ettId,User user){
		if(devicdIdAppsList != null && devicdIdAppsList.size() > 0){
			List<InstalledApps> installedApps = installedAppsRepository.findByEttId(user.getEttId());
			List<InstalledApps> installedApps3 = new ArrayList<InstalledApps>();
			for(DeviceIdInstalledApps insApp : devicdIdAppsList){
				InstalledApps installedApps2 = new InstalledApps();
				installedApps2.setId(ettId+"_"+insApp.getAppKey());
				installedApps2.setEttId(ettId);
				installedApps2.setAppKey(insApp.getAppKey());
				installedApps2.setCreatedTs(new Date());
				if(installedApps.contains(installedApps2)){
				}
				else {
					installedApps3.add(installedApps2);
				}
			}
			if(installedApps3.size()>0){
				installedAppsRepository.save(installedApps3);
				LOGGER.info("app added in installed apps table from old ettId of same device ettId={},installedApps={}",ettId,installedApps3);
			}
			if(devicdIdAppsList!=null && devicdIdAppsList.size()>0){
				deviceIdInstalledAppsRepository.deleteDeviceId(deviceId);
			}

		}
	}

	public void installedAppsToDeviceInstalledApps(User user){
		List<User> userList = userRepository.findByDeviceId_1(user.getDeviceId());
		List<DeviceIdInstalledApps> deviceIdInstalledApps = deviceIdInstalledAppsRepository.findByDeviceId(user.getDeviceId());
		if(deviceIdInstalledApps == null)
			deviceIdInstalledApps = new ArrayList<DeviceIdInstalledApps>();

		if(userList != null && userList.size()>0){
			for(User usr : userList) {
				List<InstalledApps> installedApps = installedAppsRepository.findByEttId(usr.getEttId());
				for(InstalledApps installedApps2:installedApps) {
					//if(!deviceIdInstalledApps.contains(usr.getDeviceId()+"_"+installedApps2.getAppKey())){
						DeviceIdInstalledApps deviceIdInstalledApps2 = new DeviceIdInstalledApps();
						deviceIdInstalledApps2.setId(usr.getDeviceId()+"_"+installedApps2.getAppKey());
						deviceIdInstalledApps2.setAppKey(installedApps2.getAppKey());
						deviceIdInstalledApps2.setDeviceId(usr.getDeviceId());
						deviceIdInstalledApps2.setCreatedTs(new Date());
						if(!deviceIdInstalledApps.contains(deviceIdInstalledApps2)){
							deviceIdInstalledApps.add(deviceIdInstalledApps2);
						}
					//}
				}
			}
			//deviceIdInstalledAppsRepository.save(deviceIdInstalledApps);
			//LOGGER.info("app key save to DeviceInstalledApps ettId={},table={}",user.getEttId(),deviceIdInstalledApps);
			customDatabaseService.saveDeviceIdInstalledApps(deviceIdInstalledApps);
		}
	}
}