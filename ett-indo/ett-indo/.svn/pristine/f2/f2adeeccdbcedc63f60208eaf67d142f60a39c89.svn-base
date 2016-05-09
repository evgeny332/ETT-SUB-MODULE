package com.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.entity.DataUsagePendingCredits;
import com.domain.entity.DeviceToken;
import com.domain.entity.User;
import com.domain.entity.UserAccount;
import com.domain.entity.UserAccountSummary;
import com.repository.jpa.DeviceTokenRepository;
import com.repository.jpa.UserAccountRepository;
import com.repository.jpa.UserAccountSummaryRepository;
import com.service.DataUsageService;

@Service
public class DataUsageServiceImpl implements DataUsageService{
	
	private static Logger LOGGER = LoggerFactory.getLogger(DataUsageServiceImpl.class);
	
	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	UserAccountSummaryRepository userAccountSummaryRepository;
	
	@Autowired
	private DeviceTokenRepository deviceTokenRepository;

	@Override
	public boolean checkDateEligiblity(DataUsagePendingCredits usagePendingCredits) {
		
		Date now = new Date();
		if(usagePendingCredits.getStartedTime().before(now) && usagePendingCredits.getPayoutEndDate().after(now))
			return true;
		
		return false;
	}

	@Override
	public int getThresholdChunk(DataUsagePendingCredits dataUsage, long usedData, float totalEarnedAmount, float todaysEarnedAmount) {
		int totalDataChunk = (int) ((usedData - dataUsage.getTotalUsedData()) / dataUsage.getDataThreshold());
		float totalRemainingAmount = dataUsage.getMaxCreditLimit() - totalEarnedAmount;
		float todaysRemainingAmount = dataUsage.getMaxCreditPerDayLimit() - todaysEarnedAmount;
		int maxAvlCredit = 0;
		if(totalRemainingAmount <= todaysEarnedAmount){
			maxAvlCredit = (int) (totalRemainingAmount / dataUsage.getAmountPerDataThreshold());
		}else{
			maxAvlCredit = (int) (todaysRemainingAmount / dataUsage.getAmountPerDataThreshold());
		}
		
		if(totalDataChunk < maxAvlCredit)
			return  totalDataChunk;
					
		return maxAvlCredit;
	}

	@Override
	public void creditUserForDataUsage(User user, DataUsagePendingCredits dataUsagePendingCredits, int dataChunks) {
		UserAccount userAccount = userAccountRepository.findByEttId(user.getEttId());
		if (userAccount != null) {
			float oldBalance = userAccount.getCurrentBalance();
			userAccount.setCurrentBalance((oldBalance + (dataUsagePendingCredits.getAmountPerDataThreshold() * dataChunks)));
			userAccount = userAccountRepository.save(userAccount);
			LOGGER.info("credited DataUsage amount in UserAccount table : ettId="+ user.getEttId()+ "&oldBalance="+ oldBalance+ "&currentBalance=" + userAccount.getCurrentBalance());
			for(int i=0; i<dataChunks; i++){
				UserAccountSummary userAccountSummary = new UserAccountSummary();
				userAccountSummary.setEttId(user.getEttId());
				userAccountSummary.setOfferId(dataUsagePendingCredits.getOfferId());
				userAccountSummary.setOfferName(dataUsagePendingCredits.getAppKey()+" DATA USAGE");
				userAccountSummary.setAmount(dataUsagePendingCredits.getAmountPerDataThreshold());
				userAccountSummary.setRemarks("DATA_USAGE");
				userAccountSummaryRepository.save(userAccountSummary);
				LOGGER.info("save Data Usage in UserAccountSummary table for ettId : "+ user.getEttId());
			}
			
			
			/*String pushText = props.getProperty("NEW_USER_BONUS_RETARGETTING_PUSH");
			if (pushText != null && (!pushText.trim().equals(""))){
				pushText = pushText.replaceFirst("#AMOUNT#", amount + "");
				DeviceToken dToken = deviceTokenRepository.findByEttId(user.getEttId());
				sendPush(pushText, dToken,user.getEttId());
			}*/
		}
	}

	
}
