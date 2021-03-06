package com.web.rest.controller;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

import com.domain.entity.DeviceToken;
import com.domain.entity.InstalledApps;
import com.domain.entity.InstalledAppsFirstTime;
import com.domain.entity.InstalledAppsNotReg;
import com.domain.entity.LocaleTextTemplate.Locale;
import com.domain.entity.EttTabDetails;
import com.domain.entity.Msisdn_30;
import com.domain.entity.User;
import com.domain.entity.UserBlackList;
import com.domain.entity.UserProfile;
import com.domain.entity.UserSource;
import com.domain.entity.UserUrl;
import com.http.client.HttpRequestProcessor;
import com.ning.http.client.AsyncCompletionHandlerBase;
import com.ning.http.client.Response;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.EttTabDetailsRepository;
import com.repository.jpa.InstalledAppsFirstTimeRepository;
import com.repository.jpa.InstalledAppsNotRegRepository;
import com.repository.jpa.InstalledAppsRepository;
import com.repository.jpa.Msisdn_30Repository;
import com.repository.jpa.SchedulePushRepository;
import com.repository.jpa.TodaysOffersRepository;
import com.repository.jpa.TransactionTrackerNotRegRepository;
import com.repository.jpa.TransactionTrackerRepository;
import com.repository.jpa.UserAccountRepository;
import com.repository.jpa.UserAccountSummaryRepository;
import com.repository.jpa.UserBlackListRepository;
import com.repository.jpa.UserProfileRepository;
import com.repository.jpa.UserRepository;
import com.repository.jpa.UserSourceRepository;
import com.repository.jpa.UserUrlRepository;
import com.service.CustomDatabaseService;
import com.service.EttApis;
import com.service.Msisdn_30Service;
import com.service.RechargeService;
import com.service.ReferrerService;
import com.web.rest.dto.BeforRegisterDto;
import com.web.rest.dto.EttTabDetailsDto;
import com.web.rest.dto.OtpDto;


/**
 * @author ankur
 */
@Controller
@RequestMapping(value = "/v1")
public class OtpController{

	private static Logger LOGGER = LoggerFactory.getLogger(OtpController.class);

	@Autowired
		private UserRepository userRepository;

	@Autowired
		private UserAccountRepository userAccountRepository;

	@Resource
		private HttpRequestProcessor httpRequestProcessor;

	@Autowired
		private DeviceTokenRepository deviceTokenRepository;

	@Resource
		private InstalledAppsRepository installedAppsRepository;
	
	@Resource
		private InstalledAppsFirstTimeRepository installedAppsFirstTimeRepository;
	
	@Resource
	TodaysOffersRepository todaysOffersRepository;
	@Resource
		private InstalledAppsNotRegRepository installedAppsNotRegRepository;
	
	@Resource
		private TransactionTrackerRepository transactionTrackerRepository;
	
	@Resource
		private TransactionTrackerNotRegRepository transactionTrackerNotRegRepository;

	@Resource
		private RechargeService rechargeService;

	@Resource
	private Msisdn_30Repository msisdn_30Repository;
	
	@Resource
	private Msisdn_30Service msisdn_30Service;
	@Resource
		private UserAccountSummaryRepository userAccountSummaryRepository;

	@Resource
		private JmsTemplate jmsTemplate;

	@Resource
		private UserSourceRepository userSourceRepository;

	@Resource
	EttTabDetailsRepository ettTabDetailsRepository;
	
	@Resource
    UserProfileRepository userProfileRepository;
 
	@Resource
	UserBlackListRepository userBlackListRepository;
	
	@Resource
	SchedulePushRepository schedulePushRepository;
	
	@Resource
	UserUrlRepository userUrlRepository;
	
	@Autowired
	private CustomDatabaseService customDatabaseService;

	@Resource
	EttApis ettApis;

	private ExecutorService executorService = Executors.newFixedThreadPool(10);

	private ExecutorService installExecutorService = Executors.newFixedThreadPool(4);
	
	@RequestMapping(value = "user/ettTabDetails/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getTabDetails(@RequestParam("ettId") Long ettId,
			@RequestParam("otp") String otp) {
			
			LOGGER.info("API ettTabDetails ettId={} , otp={}", ettId,otp);
			
			List<EttTabDetails> ettTabDetails = ettTabDetailsRepository.findByStatus();
			List<EttTabDetailsDto> ettTabDetailsDtoList = new ArrayList<EttTabDetailsDto>();
			if(ettTabDetails!=null && ettTabDetails.size()>0) {
				for(EttTabDetails ettTabDetails2 : ettTabDetails) {
					EttTabDetailsDto ettTabDetailsDto1 = new EttTabDetailsDto();
					ettTabDetailsDto1.setId(ettTabDetails2.getId());
					ettTabDetailsDto1.setTabActionUrl(ettTabDetails2.getTabActionUrl());
					ettTabDetailsDto1.setTabName(ettTabDetails2.getTabName());
					ettTabDetailsDto1.setTabPos(ettTabDetails2.getTabPos());
					ettTabDetailsDtoList.add(ettTabDetailsDto1);
				}
			}
			return new ResponseEntity<>(ettTabDetailsDtoList,HttpStatus.OK);
	}


	
	
	@RequestMapping(value = "user/reSendOtp/", method = RequestMethod.GET)
	@ResponseBody
		public ResponseEntity<?> reSendOtp(@RequestParam("msisdn") String msisdn,
					@RequestParam("deviceId") String deviceId){
		LOGGER.info("API Resend Otp request deviceId={} , msisdn={}", deviceId , msisdn);
		List<User> userList = userRepository.findByMsisdn(msisdn);
		User user = null;
		if(userList == null || userList.size()<=0) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		else {
			user = userList.get(0);
		}

		UserBlackList userBlackList = userBlackListRepository.findByEttId(user.getEttId());
		if(userBlackList != null && userBlackList.getBlackListCounter()>=rechargeService.getBlackListBlockCounter()) {
			LOGGER.info("ettId BlackListed {}",user.getEttId());
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		if(deviceId == null || deviceId.equals("")){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
				if(user.getDeviceId().equals(deviceId)){
			String otpText = rechargeService.getLocaleTextTemplate().get("OTP_TEXT_"+user.getLocale()).replaceFirst("#OTP_KEY#", "otp="+user.getOtp());
			LOGGER.info("otp sent"+SendUDP(msisdn+"#"+otpText+"#RESEND",rechargeService.getAppConfig().get("OTP_MSG_UDP_IP"),rechargeService.getAppConfig().get("OTP_MSG_UDP_PORT")));
			try {
				float appV = Float.parseFloat(user.getAppVersion());
			if(appV>1.2) {
				DeviceToken deviceToken = deviceTokenRepository.findByEttId(user.getEttId());
				
				sendPush("otp="+user.getTempOtp	(), deviceToken.getDeviceToken(),"SILENT",user.getEttId());
			}
		}catch(Exception ex1) {
			ex1.printStackTrace();
		}
	
			
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "user/beforeRegister/", method = RequestMethod.GET)
		@ResponseBody
		public ResponseEntity	<?> beforeRegister(@RequestParam(value = "deviceId" , required = false, defaultValue = "") String deviceId,
		@RequestParam(value= "deviceType", required = false, defaultValue = "") String deviceType,
				@RequestParam(value = "deviceToken", required = false, defaultValue = "") String deviceToken,
				@RequestParam(value = "appVersion", required = false, defaultValue = "") String appVersion,
				@RequestParam(value = "timeZoneInSeconds", required = false, defaultValue = "") int timeZoneInSeconds,
				@RequestParam(value = "androidVersion", required = false, defaultValue = "") String androidVersion,
				@RequestParam(value = "deviceName", required = false, defaultValue = "") String deviceName,
				@RequestParam(value = "networkType", required = false, defaultValue = "") String networkType,
				@RequestParam(value = "source", required = false, defaultValue = "") final String source,
				@RequestParam(value = "utm_source", required = false, defaultValue = "") final String utmSource,
				@RequestParam(value = "utm_medium", required = false, defaultValue = "") final String utmMedium,
				@RequestParam(value = "utm_term", required = false, defaultValue = "") final String utmTerm,
				@RequestParam(value = "advertisingId", required = false, defaultValue = "") final String advertisingId,
				@RequestParam(value = "androidId", required = false, defaultValue = "") final String androidId,
				@RequestParam(value = "email", required = false, defaultValue = "") final String email
				) {
					LOGGER.info("API Before Register deviceId={},deviceType={},deviceToken={},appVersion={},timeZoneInSeconds={},androindVersion={},deviceName={},networkType={},source={},utm_source={},utm_medium={},utm_term={},advertisingId={},androidId={},email={}",deviceId,deviceType,deviceToken,appVersion,timeZoneInSeconds,androidVersion,deviceName,networkType,source,utmSource,utmMedium,utmTerm,advertisingId,androidId,email);
					if(email.length()>0) {
					List<UserProfile> emailProfile = userProfileRepository.findByEmailOrderByUpdateDateDesc(email);
					String msisdn = "";
					UserUrl userUrl = null;
					if(emailProfile != null && emailProfile.size()>0){
						User user = userRepository.findByEttId(emailProfile.get(0).getEttId());
						if(user.isVerified())
							msisdn = user.getMsisdn();					
					}
					if(msisdn!="") {
						userUrl = userUrlRepository.findByettIdStatus(Long.parseLong(msisdn));
					}
						
						BeforRegisterDto beforRegisterDto = new BeforRegisterDto();
						beforRegisterDto.setMsisdn(msisdn);
						if(userUrl !=null) {
							beforRegisterDto.setUserUrl(userUrl.getUrl());
						}
						return new ResponseEntity<>(beforRegisterDto,HttpStatus.OK);
					}
					return new ResponseEntity<>("",HttpStatus.OK);
				}
	@RequestMapping(value = "user/getOtp/", method = RequestMethod.GET)
		@ResponseBody
		public ResponseEntity<?> login(@RequestParam("msisdn") String msisdn,
				@RequestParam("deviceId") String deviceId,
				@RequestParam("deviceType") String deviceType,
				@RequestParam("deviceToken") String deviceToken,
				@RequestParam(value = "appVersion") String appVersion,
				@RequestParam(value = "timeZoneInSeconds") int timeZoneInSeconds,
				@RequestParam(value = "androidVersion") String androidVersion,
				@RequestParam(value = "deviceName") String deviceName,
				@RequestParam(value = "networkType", required = false, defaultValue = "UNKNOWN") String networkType,
				@RequestParam(value = "alreadyInstalledApps", required = false, defaultValue = "UNKNOWN") List<String> alreadyInstalledApps,
				@RequestParam(value = "operator", required = false, defaultValue = "UNKNOWN") String operator,
				@RequestParam(value = "source", required = false, defaultValue = "UNKNOWN") final String source,
				@RequestParam(value = "utm_source", required = false, defaultValue = "UNKNOWN") final String utmSource,
				@RequestParam(value = "utm_medium", required = false, defaultValue = "UNKNOWN") final String utmMedium,
				@RequestParam(value = "utm_term", required = false, defaultValue = "UNKNOWN") final String utmTerm,
				@RequestParam(value = "utm_content", required = false, defaultValue = "UNKNOWN") final String utmContent,
				@RequestParam(value = "utm_campaign", required = false, defaultValue = "UNKNOWN") final String utmCampaign,
				@RequestParam(value = "advertisingId", required = false, defaultValue = "") final String advertisingId,
				@RequestParam(value = "androidId", required = false, defaultValue = "") final String androidId,
				@RequestParam(value = "email", required = false, defaultValue = "") final String email,
				@RequestParam(value = "locale", required = false, defaultValue = "EN") String locale
				
				)  {
			LOGGER.info("API login deviceId={},deviceType={},deviceToken={},appVersion={},timeZoneInSeconds={},androindVersion={},deviceName={},networkType={},source={},utm_source={},utm_medium={},utm_term={},advertisingId={},androidId={},email={},locale={}",deviceId,deviceType,deviceToken,appVersion,timeZoneInSeconds,androidVersion,deviceName,networkType,source,utmSource,utmMedium,utmTerm,advertisingId,androidId,email,locale);
			Future<Response> circleResp  = null;
			
			try {
				msisdn = Long.parseLong(msisdn)+"";
			}catch(Exception ex) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			if(msisdn.length()<9 || msisdn.length()>11) {
				LOGGER.error("Msisdn length Error {}",msisdn);
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			int status=0;
			if(!ettApis.parameterValidate(deviceId,email,msisdn)) {
				status=3;
				//return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			
			//get 1operator circle url
			Map<String,String> queryParam = new HashMap<>();
			queryParam.put("msisdn", msisdn);
			try {
				circleResp = httpRequestProcessor.submitGetRequest(rechargeService.getAppConfig().get("FETCH_OPERATOR_CIRCLE_URL"), null, queryParam, new AsyncCompletionHandlerBase());
			} catch (Exception e) {
				LOGGER.error("Error in curl operator circle msisdn="+msisdn);
				e.printStackTrace();
			}

			//save deviceToken
			//DeviceToken updatedDeviceToken = saveDeviceTokenInMysql(ettId,deviceToken, deviceType, deviceId, advertisingId, androidId, email);

			List<User> userList = userRepository.findByMsisdn(msisdn);
			User user = null;
			if(userList == null || userList.size()<=0) {
				
			}
			else {
				user = userList.get(0);
			}
			String operatorCircle = getOperatorCircle(circleResp);
			if(user == null){
				//save user
				int otp = getRandomNumber();
				int tempOtp = getRandomNumber();
				user  = new User.Builder().deviceId(deviceId).msisdn(msisdn).appVersion(appVersion).timeZoneInSeconds(timeZoneInSeconds).deviceType(DeviceToken.DeviceType.valueOf(deviceType)).otp(otp)
					.tempOtp(tempOtp).networkType(networkType).operator(operator).operatorCircle(operatorCircle).status(status).locale(Locale.valueOf(locale)).build();
				user.setFlage(false);
				if(Float.parseFloat(user.getAppVersion())<1.6f) {
					user.setRedeemCount(1);
				}
				user = userRepository.save(user);
				LOGGER.info("new user saved");
				//save deviceToken
				DeviceToken updatedDeviceToken = saveDeviceTokenInMysql(user.getEttId(),deviceToken, deviceType, deviceId, advertisingId, androidId, email);
				final Long ettId = user.getEttId();
				executorService.submit(new Runnable() {
						@Override
						public void run() {
						saveUserSource(ettId,source,utmSource,utmMedium,utmContent,utmCampaign,utmTerm);				
						}
						});

			}else if(!appVersion.equals(user.getAppVersion())){
				DeviceToken updatedDeviceToken = saveDeviceTokenInMysql(user.getEttId(),deviceToken, deviceType, deviceId, advertisingId, androidId, email);

				user.setAppVersion(appVersion);
				user.setTimeZoneInSeconds(timeZoneInSeconds);
				user.setNetworkType(networkType);
				user.setOperatorCircle(operatorCircle);
				user.setFlage(true);
				user.setIsDeviceSupport(0);
				user.setLocale(Locale.valueOf(locale));
				user = userRepository.save(user);
				LOGGER.info("msisdn updated user saved");
			}
			else if(user.getIsDeviceSupport()!=0) {
				user.setIsDeviceSupport(0);
				userRepository.save(user);
			}
			//dump installed apps
			final User finalUser = user;
			final List<String> finalAlreadyInstalledApps = alreadyInstalledApps;
			final String finalDeviceToken = deviceToken;
			final float appVer = Float.valueOf(appVersion);

			installExecutorService.submit(new Runnable() {
					@Override
					public void run() {
					dumpInstalledApps(finalUser, finalAlreadyInstalledApps, finalDeviceToken, appVer);			      		
					}
					});

			//send otp sms
			
			String otpText = rechargeService.getLocaleTextTemplate().get("OTP_TEXT_"+user.getLocale()).replaceFirst("#OTP_KEY#", "otp="+user.getOtp());
			//Map<String,String> queryParamMap = new HashMap<>();
			/* queryParamMap.put("username", "RationalHeads");
			   queryParamMap.put("password", "heads12");
			   queryParamMap.put("destination", "91"+msisdn);
			   queryParamMap.put("source", "RHTOTP");
			   queryParamMap.put("message", otpText);
			   try {
			   httpRequestProcessor.submitGetRequest("http://sms6.routesms.com:8080/bulksms/bulksms?type=0&dlr=1&", null, queryParamMap, new SmsManager(queryParamMap));
			   } catch (IOException e) {
			   e.printStackTrace();
			   }*/
			/*queryParamMap.put("user", "rationalh");
			queryParamMap.put("password", "rationalh123");
			queryParamMap.put("PhoneNumber", "91"+msisdn);
			queryParamMap.put("sender", "RHTETT");
			queryParamMap.put("text", otpText);
			try {
				httpRequestProcessor.submitGetRequest("http://203.92.40.186:8443/Sun3/Send_SMS2x?", null, queryParamMap, new SmsManager(queryParamMap));
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			
			//LOGGER.info("otp sent"+SendUDP(msisdn+"#"+otpText,"127.0.0.1","7777"));
			LOGGER.info("otp sent"+SendUDP(msisdn+"#"+otpText,rechargeService.getAppConfig().get("OTP_MSG_UDP_IP"),rechargeService.getAppConfig().get("OTP_MSG_UDP_PORT")));
			try {
						float appV = Float.parseFloat(appVersion);
					if(appV>1.2f) {
						sendPush("otp="+user.getTempOtp	(), deviceToken,"SILENT",user.getEttId());
						LOGGER.info("Silent temp otp sent ettId={},tempOtp={}",user.getEttId(),user.getTempOtp());
					}
				}catch(Exception ex1) {
					ex1.printStackTrace();
				}
			
			enqueueMap(user.getEttId(),finalAlreadyInstalledApps,"EDR");
			/**********************User Profile *********************/
			UserProfile userProfile = userProfileRepository.findByEttId(user.getEttId());
			if (userProfile == null) {
				userProfile = new UserProfile();
				userProfile.setEttId(user.getEttId());
			}
				/*userProfile.setDob(dob);
				userProfile.setEmail(email);
				userProfile.setGender(gender);
				userProfile.setOccupation(occupation);
				userProfile.setMaritalStatus(maritalstatus);
				userProfile.setIncome(income);
				if(latitude!=null)
				userProfile.setLatitude(latitude);
				if(longitude!=null)
				userProfile.setLongitude(longitude);*/
				//userProfile.setEmail(email);
				userProfile.setLatitude(0);
				userProfile.setLongitude(0);
				
				userProfile.setOSVersion(androidVersion);
				if(deviceName.indexOf(" ")>-1) {
					userProfile.setDeviceBrand(deviceName.substring(0,deviceName.indexOf(" ")));
					userProfile.setDeviceModel(deviceName.substring(deviceName.indexOf(" ")+1));
				} else{
				userProfile.setDeviceBrand(deviceName);
				}
				userProfile.setOperator(operator);
				userProfile.setOperatorCircle(operatorCircle);
				userProfile = userProfileRepository.save(userProfile);

			/**********************User Profile *********************/
				try {
					if(rechargeService.getAppConfig().get("ISWINBACK").equals("true")){
					Msisdn_30 msisdn_30 = msisdn_30Repository.findByettIdStatusType(user.getEttId(), 0);
						if(msisdn_30!=null ) {
							msisdn_30Repository.updateStatus(1,user.getEttId());
							if( msisdn_30.getType()==1) {
								msisdn_30Service.updateAmount(user, msisdn_30,msisdn_30.getAmount(),923l,"WINBACK_REG");
							}
							else if(msisdn_30.getType()==2){
								msisdn_30Service.updateAmount(user, msisdn_30,msisdn_30.getAmount(),924l,"WINBACK_REG");
							}
							else{}
							//sendPush(msisdn_30.getMsg().replaceFirst("#AMOUNT", msisdn_30.getAmount()+""), deviceToken);
						}
					}
				}catch(Exception exx) {
					LOGGER.info("error while working for winback ettId{}"+user.getEttId());
					exx.printStackTrace();
				}

				
			return new ResponseEntity<>(getOtpDto(user),HttpStatus.OK);
		}

	
	@RequestMapping(value = "/user/updateOtp/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> push(@RequestParam("ettId") long ettId,
								@RequestParam("otp") int otp,
								@RequestParam("updateOtp") int updateOtp,
								@RequestParam(value="tempOtp", required=false, defaultValue="0") int tempOtp
								)
								
	{

		LOGGER.info("API /account/update/ ettId {}, otp={},updateOtp={},tempOtp={}",ettId, otp, updateOtp, tempOtp);
		
		User user = userRepository.findByEttId(ettId);
		if(user.getOtp() != otp) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		else {
			user.setOtp(updateOtp);
			userRepository.save(user);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
	}
	public String SendUDP(String strFinal,String IP,String port)
        {
                System.out.println("[ETT][SendUDP] [SendUDP] [OnlineDA Message] FINAL String "+strFinal+":::"+IP+"::"+port);
                String resp="";
                try
                {
                        DatagramSocket  clientSocket = new DatagramSocket();
                        int localport=clientSocket.getLocalPort();
                        String portn=localport+"";
                        strFinal=strFinal.replace("LPORT",portn);
                        InetAddress IPAddress=InetAddress.getByName(IP);
                        DatagramPacket sendPacket = new DatagramPacket(strFinal.getBytes(),strFinal.getBytes().length,IPAddress,Integer.parseInt(port));
                        clientSocket.send(sendPacket);
                        System.out.println("[ETT] [SendUDP] [OnlineDA Message] SendUDP["+strFinal+"] IP:"+IP+" Port:"+port);
                        clientSocket.close();
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                        System.out.println("[ETT][SendUDP] [OnlineDA Message] Exception When send Packet!!!!!!!!!! "+e);
                }
		return resp;
        }

	private void dumpInstalledApps(User user, List<String> alreadyInstalledApps, String deviceToken, float appVer) {
	try {
		if (rechargeService.getAppConfig().get("INSTALLED_APP_DEVICEID_CHECK").equals("true")&& appVer >= 1.2f) {
			List<InstalledAppsFirstTime> installedAppsFirstTimeList = new ArrayList<>(alreadyInstalledApps.size());
			// List<InstalledAppsFirstTime> installedAppsFirstTimeIndb =
			// installedAppsFirstTimeRepository.findByEttId(user.getEttId());
			installedAppsFirstTimeRepository.deleteIAFTRByEttId(user.getEttId());
			// for(InstalledApps installedApp : alreadyInstalledApps){
			for (String installedApp : alreadyInstalledApps) {
				InstalledAppsFirstTime appsFirstTime = new InstalledAppsFirstTime();
				appsFirstTime.setId(user.getEttId() + "_" + installedApp);
				appsFirstTime.setEttId(user.getEttId());
				appsFirstTime.setAppKey(installedApp);
				appsFirstTime.setCreatedTs(new java.util.Date());
				appsFirstTime.setCreatedTime(new java.util.Date());
				installedAppsFirstTimeList.add(appsFirstTime);
			}
			// installedAppsFirstTimeList.removeAll(installedAppsFirstTimeIndb);
			// installedAppsFirstTimeList.addAll(installedAppsFirstTimeIndb);
			// LOGGER.info("final InstalledApps to save {}",installedAppsFirstTimeList);
			customDatabaseService.saveInstalledAppsFirstTime(installedAppsFirstTimeList);
			/*
			 * for(InstalledAppsFirstTime installedAppsFirstTime :
			 * installedAppsFirstTimeList){
			 * System.out.println("installedApps id : "
			 * +installedAppsFirstTime.getId());
			 * installedAppsFirstTimeRepository
			 * .save(installedAppsFirstTime); }
			 */

			// LOGGER.info("appkeys : "+installedAppsFirstTimeList+" saved in InstalledAppsFirstTime");
		} else {

			List<InstalledApps> installedApps = new ArrayList<>(alreadyInstalledApps.size());
			List<String> appIds = new ArrayList<>();
			for (String alreadyInstalledApp : alreadyInstalledApps) {
				InstalledApps installedApp = new InstalledApps();
				installedApp.setId(user.getEttId() + "_"+ alreadyInstalledApp);
				installedApp.setEttId(user.getEttId());

				installedApp.setCreatedTs(new java.util.Date());
				installedApp.setAppKey(alreadyInstalledApp);

				appIds.add(user.getEttId() + "_" + alreadyInstalledApp);
				installedApps.add(installedApp);
			}
			List<InstalledApps> installedApssInDb = installedAppsRepository.findByEttId(user.getEttId());
			List<InstalledAppsNotReg> installedApssNotRegInDb = installedAppsNotRegRepository.findByEttId(user.getEttId());

			List<InstalledApps> installedApssNotRegInDb1 = new ArrayList<InstalledApps>(installedApssNotRegInDb.size());
			for (int i = 0; i < installedApssNotRegInDb.size(); i++) {
				InstalledApps installapp = new InstalledApps();
				InstalledAppsNotReg installNotReg = installedApssNotRegInDb.get(i);
				installapp.setId(installNotReg.getId());
				installapp.setAppKey(installNotReg.getAppKey());
				installapp.setCreatedTs(installNotReg.getCreatedTs());
				installapp.setEttId(installNotReg.getEttId());
				installedApssNotRegInDb1.add(installapp);
				// LOGGER.info("InstalledApps Add{}",installapp.getId());
			}
			// LOGGER.info("installedApps{}\ninstalledApssNotRegInDb1{}",installedApps,installedApssNotRegInDb1);

			installedApps.removeAll(installedApssNotRegInDb1);
			installedApps.addAll(installedApssNotRegInDb1);
			// LOGGER.info("installedApps{}\ninstalledApssNotRegInDb1{}",installedApps,installedApssNotRegInDb1);
			installedApps.removeAll(installedApssInDb);
			installedApps.addAll(installedApssInDb);
			// LOGGER.info("installedApps{}",installedApps);
			customDatabaseService.saveInstalledApps(installedApps);
			// installedAppsRepository.save(installedApps);
			// LOGGER.info("appkeys : "+installedApps+" saved in InstalledApps");
		}

		// transaction tracker handling
		/*
		 * List<TransactionTracker> transctionTrackerNotReg =
		 * transactionTrackerNotRegRepository.findByIdIn(appIds);
		 * List<TransactionTracker> transctionTracker =
		 * transactionTrackerRepository.findByIdIn(appIds);
		 * transctionTracker.remove(transctionTrackerNotReg);
		 * transctionTracker.addAll(transctionTrackerNotReg);
		 * transactionTrackerRepository.save(transctionTracker);
		 */
		todaysOffersRepository.deleteEttIdOfferId(user.getEttId());
	} catch (org.springframework.dao.DataIntegrityViolationException e) {
		LOGGER.error("error in installedApps " + e);
		e.printStackTrace();
	}	
	
	}

	private void sendPush(final String pushText, final String dToken,final String type,final Long ettId) {
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
					mapMessage.setString("type", type);
					return mapMessage;
					}
					});
		}catch(Exception e){
			LOGGER.error("error in otp controller push "+e);
			e.printStackTrace();
		}

	}

	private String getOperatorCircle(Future<Response> circleRespFuture) {
		try {
			String circle = circleRespFuture.get().getResponseBody();
			return circle.trim();
		} catch (Exception e) {
			LOGGER.error("error in fetching circle "+e);
			e.printStackTrace();
			return "UNKNOWN";
		}
	}

	private OtpDto getOtpDto(User user) {
		OtpDto otpDto = new OtpDto();
		otpDto.setEttId(user.getEttId());
		otpDto.setVerified(user.isVerified());
		otpDto.setCountryCode("+91");
		otpDto.setCurrency("\u20B9");
		otpDto.setOperator(user.getOperator());
		otpDto.setOperatorsList(rechargeService.getRedeemOperatorsList());
		otpDto.setRedeemAmountsList(rechargeService.getRedeemAmountsList());
		otpDto.setTimerOn(true);
		otpDto.setTempOtp(user.getTempOtp());
		otpDto.setTimer(40);
		if(rechargeService.getAppConfig().get("IS_MANUAL_OTP").equals("true"))
		{
			otpDto.setManualOtp(true);
		}
		else {
			otpDto.setManualOtp(false);
		}
		return otpDto;
	}

	private DeviceToken saveDeviceTokenInMysql(Long ettId,String token,String deviceType, String deviceId, String advertisingId, String androidId, String email){
		//DeviceToken deviceToken = deviceTokenRepository.findByDeviceId(deviceId);
		DeviceToken deviceToken = deviceTokenRepository.findByEttId(ettId);
		if(deviceToken==null){
			deviceToken = new DeviceToken();
			deviceToken.setDeviceId(deviceId);
			deviceToken.setEttId(ettId);
		}else if(deviceToken.getDeviceToken()!=null && deviceToken.getDeviceToken().equals(deviceToken) && deviceToken.isActive()){
			return deviceToken;
		}        
		deviceToken.setActive(false);
		deviceToken.setDeviceType(DeviceToken.DeviceType.valueOf(deviceType));
		deviceToken.setDeviceToken(token);
		deviceToken.setAdvertisingId(advertisingId);
		deviceToken.setAndroidId(androidId);
		deviceToken.setEmail(email);
		
		deviceToken = deviceTokenRepository.save(deviceToken);
		return deviceToken;
	}

	private void saveUserSource(Long ettId, String source, String utmSource, String utmMedium, String utmContent, String utmCampaign, String utmTerm) {
		UserSource userSource = new UserSource();
		userSource.setEttId(ettId);
		userSource.setSource(source);
		userSource.setUtmSource(utmSource);
		userSource.setUtmContent(utmContent);
		userSource.setUtmMedium(utmMedium);
		if(utmMedium.equalsIgnoreCase("INVITE")){
			utmCampaign=utmMedium;	
		}
		userSource.setUtmCampaign(utmCampaign);
		userSource.setUtmTerm(utmTerm);
		userSourceRepository.save(userSource);
		LOGGER.info("Source save for ettId="+ettId);
	}

	private static int getRandomNumber() {
		return (new Random().nextInt(999999-100000)+100000);
	}
	
	private void enqueueMap(final long ettId, final List msg,final String queueName) {
		try {
			jmsTemplate.send(queueName, new MessageCreator() {
				@Override
				public Message createMessage(Session session)
						throws JMSException {
					MapMessage mapMessage = session.createMapMessage();
					// textMessage.setText(id + "");

					mapMessage.setString("type", "REGISTERAPPS");
					mapMessage.setLong("ettId", ettId);
					mapMessage.setObject("installedApps", msg);
					// }
					return mapMessage;
				}
			});

		} catch (Exception e) {
			LOGGER.error("error in enqueue" + e);
			e.printStackTrace();
		}

	}

}
