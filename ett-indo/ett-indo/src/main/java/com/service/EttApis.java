package com.service;

import java.util.Date;

import com.domain.entity.DownloadBoosterEligibleUser;
import com.domain.entity.User;

public interface EttApis {

		int getUserAge(String dateOfBirth);
		void sendUNAUTHORIZEDEdr(Long ettId,int type);
		boolean getBlackListStatus(Long ettId);
		void sendPush(final String pushText,final String dToken,final Long ettId);
		boolean checkFirstTimeDay(User user);
		java.util.Date convertDBDate();
		
		void giveDownloadBoosterAmount(User user,DownloadBoosterEligibleUser eligibleUser);
		
		float getThresholdAmont(User user);
		String getFee(String amount, String fee, int currentBalance);
		boolean parameterValidate(String deviceId, String email,String msisdn);
		boolean isFraudPreventionCheck(User user);
		Date getTodayDate(User user);
		Date convertDBDateActive();
		String getFee(String amount, String fee, int currentBalance, User user);
		void giveAppDownloadCounterPay(long ettId, int amount, String msg);
		void giveAppCounterBonus(User user1);
		boolean checkSameDay(Date userCreatedTime, int userTimeZone,
				int serverTimeZone);
		
}
