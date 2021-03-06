package com.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.domain.entity.DayAppDownloadCounter;
import com.domain.entity.DeviceToken;
import com.domain.entity.DownloadBoosterEligibleUser;
import com.domain.entity.RedeemThreshold;
import com.domain.entity.TempOtpRegId;
import com.domain.entity.User;
import com.domain.entity.UserAccount;
import com.domain.entity.UserAccountSummary;
import com.domain.entity.UserBlackList;
import com.repository.jpa.DayAppDownloadCounterRepository;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.RedeemThresholdRepository;
import com.repository.jpa.TempOtpRegIdRepository;
import com.repository.jpa.UserAccountRepository;
import com.repository.jpa.UserAccountSummaryRepository;
import com.repository.jpa.UserBlackListRepository;
import com.service.EttApis;
import com.service.RechargeService;



@Service
public class EttApisImpl implements EttApis {

	private static Logger LOGGER = LoggerFactory.getLogger(EttApisImpl.class);
	@Resource
	private JmsTemplate jmsTemplate;

	@Resource
	UserBlackListRepository userBlackListRepository;
	
	@Resource
	private UserAccountRepository userAccountRepository;
	
	@Resource
	private UserAccountSummaryRepository userAccountSummaryRepository;
	
	@Resource
	private DayAppDownloadCounterRepository dayAppDownloadCounterRepository;
	
	@Resource
	private DeviceTokenRepository deviceTokenRepository;

	@Resource
	RechargeService rechargeService;

	@Resource
	private TempOtpRegIdRepository tempOtpRegIdRepository;
	
	@Autowired 
	RedeemThresholdRepository redeemThresholdRepository;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 
	@Override
	public Date getTodayDate(User user) {
		DateTime today = new DateTime();
		int timeZone =  (user.getTimeZoneInSeconds() <= 0) ? 19800 : user.getTimeZoneInSeconds();
		Date dateToday = today.plusSeconds(timeZone).toDateMidnight().toDate();
		return dateToday;
	}
	
	@Override
	public boolean parameterValidate(String deviceId,String email,String msisdn) {
		if(deviceId==null || deviceId.length()<14 || deviceId.length()>17) {
			LOGGER.info("DeviceId Check fail not creating A/C checkFail=deviceId,msisdn={},deviceId={},email={}",msisdn,deviceId,email);
			return false;
		}
		try { Long.parseLong(deviceId); 
		} catch(Exception Ex) {
			LOGGER.info("DeviceId Check fail msisdn={},deviceId={},email={}",msisdn,deviceId,email);
			return false;
		}
		/*if(email != null && email.length()>0){
			List<DeviceToken> deviceToken = deviceTokenRepository.findByEmailLimit15(email);
			if(deviceToken!=null && deviceToken.size()>=15) {
				LOGGER.info("email Check fail not creating A/C checkFail=email,msisdn={},deviceId={},email={},no_of_email_reg={}",msisdn,deviceId,email,deviceToken.size());
				return false;
			}
		}*/
		return true;
	}
	
	@Override
	public boolean isFraudPreventionCheck(User user) {
		
		//if(Float.parseFloat(user.getAppVersion())<Float.parseFloat(rechargeService.getAppConfig().get("CURRENT_ANDROID_VERSION_"+user.getLocale()))) {
		if(Float.parseFloat(user.getAppVersion())<Float.parseFloat(rechargeService.getAppConfig().get("CURRENT_ANDROID_VERSION"))) {
			LOGGER.info("Version Check fail not giving invite money checkFail=version,ettId={},version={}",user.getEttId(),user.getAppVersion());
			return true;
		}
		DeviceToken deviceToken = deviceTokenRepository.findByEttId(user.getEttId());
		if(deviceToken == null) {
			LOGGER.info("Some Problem not found any entry in DeviceToken table ettId={}",user.getEttId());
			return true;
			
		}
		if(deviceToken.getEmail()==null) {
			LOGGER.info("email Check fail not giving invite money checkFail=email,ettId={},email={}",user.getEttId(),deviceToken.getEmail());
			return true;
		}
		else {
			String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			Boolean b = deviceToken.getEmail().matches(EMAIL_REGEX);
			if(b==false) {
				LOGGER.info("email Check fail not giving invite money checkFail=email,ettId={},email={}",user.getEttId(),deviceToken.getEmail());
				return true;
			}
		}
		
		/*if(deviceToken.getMacAddress() == null || deviceToken.getMacAddress().trim().length()==0) {
			LOGGER.info("macAddress Check fail not giving invite money checkFail=macAddress,ettId={},macAddress={}",user.getEttId(),deviceToken.getMacAddress());
			return true;
		}*/
		//null check remove by Gaurav Sir 26-09-2015
		/*if(deviceToken.getAdvertisingId() != null && deviceToken.getAdvertisingId().trim().length()==0) {
			LOGGER.info("AdvertisingId Check fail not giving invite money checkFail=AdvertisingId,ettId={},AdvertisingId={}",user.getEttId(),deviceToken.getAdvertisingId());
			return true;
		}*/
		/*if(deviceToken.getAndroidId() == null || deviceToken.getAndroidId().trim().length()==0) {
			LOGGER.info("AndroidId Check fail not giving invite money checkFail=AndroidId,ettId={},AndroidId={}",user.getEttId(),deviceToken.getAndroidId());
			return true;
		}*/
		
		
		List <DeviceToken> deviceTokenpar = deviceTokenRepository.findByEmail(deviceToken.getEmail(),new PageRequest(0, 2));
		if(deviceTokenpar!=null && deviceTokenpar.size()>1) {
			LOGGER.info("email Check fail not giving invite money allready exist checkFail=email,ettId={},email={}",user.getEttId(),deviceToken.getEmail());
			return true;
		}
		//temp Closed
		/*if(deviceToken.getAdvertisingId() != null && deviceToken.getAdvertisingId().trim().length()>0 ){
			deviceTokenpar = deviceTokenRepository.findByAdvertisingId(deviceToken.getAdvertisingId());
			if(deviceTokenpar!=null && deviceTokenpar.size()>1) {
				LOGGER.info("AdvertisingId Check fail not giving invite money allready exist checkFail=AdvertisingId,ettId={},AdvertisingId={}",user.getEttId(),deviceToken.getAdvertisingId());
				return true;
			}
		}*/
		/*deviceTokenpar = deviceTokenRepository.findByAndroidId(deviceToken.getAndroidId());
		if(deviceTokenpar!=null && deviceTokenpar.size()>1) {
			LOGGER.info("AndroidId Check fail not giving invite money allready exist checkFail=AndroidId,ettId={},AndroidId={}",user.getEttId(),deviceToken.getAndroidId());
			return true;
		}*/
		if((deviceToken.getDeviceToken()==null) || (deviceToken.getDeviceToken()!=null && deviceToken.getDeviceToken().trim().length()<5)) {
			LOGGER.info("DeviceToken Check fail not giving invite money wrong checkFail=DeviceToken,ettId={},DeviceToken={}",user.getEttId(),deviceToken.getDeviceToken());
			return true;
		}
		deviceTokenpar = deviceTokenRepository.findByDeviceToken(deviceToken.getDeviceToken(),new PageRequest(0, 2));
		if(deviceTokenpar!=null && deviceTokenpar.size()>1) {
			LOGGER.info("DeviceToken Check fail not giving invite money allready exist checkFail=DeviceToken,ettId={},DeviceToken={}",user.getEttId(),deviceToken.getDeviceToken());
			return true;
		}
		//DeviceToken from GCM
		List <TempOtpRegId> tempOtpRegIds = tempOtpRegIdRepository.findByettId_status(user.getEttId());
		if(tempOtpRegIds!=null && tempOtpRegIds.size()>=1) {
			LOGGER.info("GCM fail for this ettId so not giving invite money checkFail=DeviceToken,ettId={},DeviceToken={}",user.getEttId(),deviceToken.getDeviceToken());
			return true;
		}
		/*deviceTokenpar = deviceTokenRepository.findByMacAddress(deviceToken.getMacAddress());
		if(deviceTokenpar.size()>1) {
			LOGGER.info("MacAddress Check fail not giving invite money allready exist checkFail=MacAddress,ettId={},MacAddress={}",user.getEttId(),deviceToken.getMacAddress());
			return true;
		}*/
		return false;
	}
	
	@Override
	public java.util.Date convertDBDateActive() {
		Calendar cal = Calendar.getInstance();
		String date;
		java.util.Date date1 = null;
		  try {
		   
		   LocalTime timeNow = new LocalTime();
		   LocalTime timeToCompare = new LocalTime(18, 30, 00);
		   if(timeNow.isAfter(timeToCompare)){
		    date = sdf.format(cal.getTime())+" 18:30:00";
		    date1 = sdf2.parse(date);
		   } else {
		    cal.add(Calendar.DAY_OF_MONTH,-1);
		    date = sdf.format(cal.getTime())+" 18:30:00";
		    date1 = sdf2.parse(date);
		   }
		  }catch(Exception ex) {
		   ex.printStackTrace();
		   return null;
		  }
		  return date1;

	}
	@Override
	public java.util.Date convertDBDate(){
		try {
			Calendar cal = Calendar.getInstance();
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int minute = cal.get(Calendar.MINUTE);
			if(hour>=18 && minute>30)
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = sdf.format(cal.getTime())+" 18:30:00";
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				java.util.Date date1 = sdf.parse(date);
				//System.out.println(date);
				return date1;
			}
			else {
				//System.out.println("before Day");
				cal.add(Calendar.DAY_OF_MONTH,-1);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = sdf.format(cal.getTime())+" 18:30:00";
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				java.util.Date date1 = sdf.parse(date);
				//System.out.println(date);
				return date1;
			}
		}catch(Exception ex) {
			LOGGER.error("error in convertDBDate -"+ex);
			ex.printStackTrace();
			return null;
		}
	}
	@Override
	public int getUserAge(String dateOfBirth){
		try {
				Calendar dob = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
				Date date = formatter.parse(dateOfBirth);
				dob.setTime(date);
				Calendar today = Calendar.getInstance();
				int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
				if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
				  age--;
				} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
				    && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
				  age--;
				}
				return age;
		}catch(Exception ex){return 0;}
	}
	
	@Override
	public void sendUNAUTHORIZEDEdr(final Long ettId,final int blackListType){
		try {
			jmsTemplate.send("EDR", new MessageCreator() {
			@Override
			public Message createMessage(Session session)
					throws JMSException {
				MapMessage mapMessage = session.createMapMessage();
				
						mapMessage.setString("type", "UNAUTHORIZED");
						mapMessage.setLong("ettId", ettId);
						mapMessage.setInt("blackListType", blackListType);

				return mapMessage;
			}
		});
		
		} catch (Exception e) {
			LOGGER.error("error in enqueueClick" + e);
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean getBlackListStatus(Long ettId) {
	
		UserBlackList userBlackList = userBlackListRepository.findByEttId(ettId);
		if(userBlackList != null && userBlackList.getBlackListCounter()>=rechargeService.getBlackListBlockCounter()) {
			LOGGER.info("ettId BlackListed {}",ettId);
			return true;
		}
		return false;
		
	}
	
	@Override
	public void sendPush(final String pushText, final String dToken,final Long ettId) {
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
					return mapMessage;
					}
					});
		}catch(Exception e){
			LOGGER.error("error in otp controller push "+e);
			e.printStackTrace();
		}

	}
	
	@Override
	public boolean checkFirstTimeDay(User user) {
		try {
				int timezone = user.getTimeZoneInSeconds();
				if(timezone == 0)
					timezone = 19800;
				long dateMidNight = DateTime.now(DateTimeZone.forOffsetMillis(timezone*1000)).toDateMidnight().toDate().getTime();
				long updateTime=user.getUpdatedTime().getTime()+timezone*1000;
				LOGGER.info("user ettId{},currentTime{},lastUpdatedtime{}",user.getEttId(),dateMidNight,updateTime);
				if(updateTime<dateMidNight) {
					return true;
				}
			return true;
		}catch(Exception ex){
			LOGGER.error("error in checkFirstTimeDay{}",ex);
			ex.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void giveDownloadBoosterAmount(User user, DownloadBoosterEligibleUser eligibleUser) {
		
		if(eligibleUser.getBoosterAmount()<=0){
			LOGGER.error("Invalid Booster Amount for ettId={} and boosterAmount={}",user.getEttId(),eligibleUser.getBoosterAmount());
			return;
		}
		
		UserAccount userAccount = userAccountRepository.findByEttId(user.getEttId());
		if(userAccount != null){
			UserAccountSummary userAccountSummary = new UserAccountSummary();
			userAccountSummary.setEttId(user.getEttId());
			userAccountSummary.setOfferId(eligibleUser.getOfferId());
			userAccountSummary.setOfferName("download bonus");
			userAccountSummary.setAmount(eligibleUser.getBoosterAmount());
			userAccountSummary.setRemarks(System.currentTimeMillis() + "");
			userAccountSummaryRepository.save(userAccountSummary);
			LOGGER.info("save download booster in UserAccountSummary table : "+ user.getEttId());
			
			float oldBalance = userAccount.getCurrentBalance();
			userAccount.setCurrentBalance((oldBalance + eligibleUser.getBoosterAmount()));
			userAccount = userAccountRepository.save(userAccount);
			LOGGER.info("credited amount in UserAccount table : ettId="+ user.getEttId() + "&oldBalance=" + oldBalance	+ "&currentBalance=" + userAccount.getCurrentBalance());
			DeviceToken dToken = deviceTokenRepository.findByEttId(user.getEttId());
			if(dToken != null){
				sendPush(eligibleUser.getPushTxt(), dToken.getDeviceToken(),user.getEttId());
			}
		}
	}
	@Override
	public float getThresholdAmont(User user) {
		RedeemThreshold redeemThreshold = null;
   		if(user.getRedeemCount()<=0){
   			redeemThreshold = redeemThresholdRepository.findById(2);
   		}
   		else{
   			redeemThreshold = redeemThresholdRepository.findById(1);
   		}
		return redeemThreshold.getThresholdAmount();
	}
	
	@Override
	public String getFee(String amount,String fee,int currentBalance,User user)
	{
		//System.out.println("[amount]["+amount+"] [fee]["+fee+"] [currentBalance]["+currentBalance+"]");
		if(rechargeService.getAppConfig().get("IS_CONVIENCE_FEE_NO_IN_SAME_BALANCE").equals("false")) {
			return fee;
		}
		try {
			String amountArr []=amount.split(",");
			String feeArr []=fee.split(",");
			String finalFee ="";
			for(int i=0;i<amountArr.length;i++) {
				float currentBalance1 = currentBalance-Float.parseFloat(amountArr[i]);
				if(currentBalance1<0) {
					finalFee=finalFee+","+feeArr[0];
				}
				else if(currentBalance1==0) {
					finalFee=finalFee+",0";
				}
				else if(currentBalance1<Integer.parseInt(feeArr[i])){
					finalFee=finalFee+","+(int)currentBalance1;
				}
				else {
					finalFee=finalFee+","+feeArr[i];
				}

			}return finalFee.substring(1);
	}catch(Exception ex){
		LOGGER.info("error in getting the process Fee amount{},fee{},currentBalance{}",amount,fee,currentBalance);
		ex.printStackTrace();
		return "0,0,0";
	}
}
	
	@Override
	public void giveAppDownloadCounterPay(long ettId,int amount,String msg) {

		if(amount<=0){
			//LOGGER.error("Invalid Booster Amount for ettId={} and boosterAmount={}",user.getEttId(),eligibleUser.getBoosterAmount());
			return;
		}
		
		UserAccount userAccount = userAccountRepository.findByEttId(ettId);
		if(userAccount != null){
			UserAccountSummary userAccountSummary = new UserAccountSummary();
			userAccountSummary.setEttId(ettId);
			userAccountSummary.setOfferId(8874l);
			userAccountSummary.setOfferName("app earning");
			userAccountSummary.setAmount(amount);
			userAccountSummary.setRemarks(System.currentTimeMillis() + "");
			userAccountSummaryRepository.save(userAccountSummary);
			LOGGER.info("save app download counter in UserAccountSummary table : "+ ettId);
			
			float oldBalance = userAccount.getCurrentBalance();
			userAccount.setCurrentBalance((oldBalance + amount));
			userAccount = userAccountRepository.save(userAccount);
			LOGGER.info("credited amount in UserAccount table : ettId="+ ettId + "&oldBalance=" + oldBalance	+ "&currentBalance=" + userAccount.getCurrentBalance());
			DeviceToken dToken = deviceTokenRepository.findByEttId(ettId);
			if(dToken != null){
				sendPush(msg, dToken.getDeviceToken(),ettId);
			}
		}
	}
	
	@Override
	public void giveAppCounterBonus(User user1){
		//if(ifDay1User(user1)) {
		if(checkSameDay(user1.getRegDate(),19800,19800)){
			LOGGER.info("User is same day not eligible for this offer ettId={}",user1.getEttId());
			return;
		}
		DayAppDownloadCounter dayAppDownloadCounter = dayAppDownloadCounterRepository.findByEttId(user1.getEttId());
		if(dayAppDownloadCounter==null) {
			dayAppDownloadCounter =  new DayAppDownloadCounter();
			dayAppDownloadCounter.setEttId(user1.getEttId());
			dayAppDownloadCounter.setStatus(false);
			dayAppDownloadCounter.setAppCounter(0);
			dayAppDownloadCounterRepository.save(dayAppDownloadCounter);
			}

			if(dayAppDownloadCounter.isStatus()==true) {
				LOGGER.info("Allready given the day app counter bonus ettId={},counter={}",dayAppDownloadCounter.getEttId(),dayAppDownloadCounter.getAppCounter());
				return;
			}
			else {
				dayAppDownloadCounter.setAppCounter(dayAppDownloadCounter.getAppCounter()+1);
			}
			if(dayAppDownloadCounter.getAppCounter()%rechargeService.getAppCounter()==0) {

				int amount = rechargeService.getAppCounterAppPayout();
				String msg = rechargeService.getAppCounterAppPayoutMsg();
				giveAppDownloadCounterPay(user1.getEttId(),amount,msg);
				LOGGER.info("Money give bonus to user ettId={},amount={},offerId={}",user1.getEttId());
				dayAppDownloadCounter.setStatus(false);
			}
			dayAppDownloadCounterRepository.save(dayAppDownloadCounter);

	}
	
	public boolean ifDay1User(User user) {
		//Date userDate = new Date(user.getCreatedTime().getTime()+19800l);
		return false;
		
		
	}
	
	@Override
	public boolean checkSameDay(Date userCreatedTime, int userTimeZone, int serverTimeZone) {
		  
		  DateTime crrDate = new DateTime(userCreatedTime);
		  crrDate.plusSeconds(userTimeZone);
		  LocalDate dateToCheck = crrDate.toLocalDate();
		  
		  DateTime serverDate = new DateTime();
		  serverDate.plusSeconds(serverTimeZone);
		  LocalDate dateNow = serverDate.toLocalDate();
		  
		  if(dateNow.isEqual(dateToCheck))
		   return true;
		  
		  return false;
		 }

	@Override
	public String getFee(String amount,String fee,int currentBalance)
	{
		//System.out.println("[amount]["+amount+"] [fee]["+fee+"] [currentBalance]["+currentBalance+"]");
		if(rechargeService.getAppConfig().get("IS_CONVIENCE_FEE_NO_IN_SAME_BALANCE").equals("false")) {
			return fee;
		}
		try {
			String amountArr []=amount.split(",");
			String feeArr []=fee.split(",");
			String finalFee ="";
			for(int i=0;i<amountArr.length;i++) {
				float currentBalance1 = currentBalance-Float.parseFloat(amountArr[i]);
				if(currentBalance1<0) {
					finalFee=finalFee+","+feeArr[0];
				}
				else if(currentBalance1==0) {
					finalFee=finalFee+",0";
				}
				else if(currentBalance1<Integer.parseInt(feeArr[i])){
					finalFee=finalFee+","+(int)currentBalance1;
				}
				else {
					finalFee=finalFee+","+feeArr[i];
				}

			}return finalFee.substring(1);
	}catch(Exception ex){
		LOGGER.info("error in getting the process Fee amount{},fee{},currentBalance{}",amount,fee,currentBalance);
		ex.printStackTrace();
		return "0,0,0";
	}
}
	

}
