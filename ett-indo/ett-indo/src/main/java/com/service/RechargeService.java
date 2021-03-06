package com.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.domain.entity.OfferDetails;
import com.domain.entity.OfferDetailsNew;
import com.domain.entity.User;

public interface RechargeService {

	Map<String,String> getOperatorKeyMap();
	
	String getOperatorKey(String operator);
	
	Map<String,Integer> getOperatorIdMap();
	
	Integer getOperatorId(String operator);
	
	Map<String,Integer> getCircleIdMap();
	
	Integer getCircleId(String circle);

	List<String> getCompetitorAppList();

	int getCOMPETITOR_USER_STATUS();

	List<Long> getSurveyOfferIdList();

	String[] getOffersMapArr();

	Map<String, String> getOffersColorCodeMap();

	List<String> getRedeemOperatorsList();

	List<String> getRedeemAmountsList();
	
	List<Long> getOfferIdIgnoreList();
	
	List<Long> getOfferIdBlockedInOffersStarted();
	
	List <String> getNewUserInstallAKBL();
	
	void pendingCreditsInsert(OfferDetails offerDetails,Long ettId);
	void pendingCreditsInsertNew(OfferDetailsNew offerDetails,Long ettId);
	
	void giveBalance(Long ettId, String tId, OfferDetails offerDetails,String vendor);
	void giveBalance(Long ettId, String tId, OfferDetails offerDetails,String vendor,User user);
	void giveBalanceNew(Long ettId, String tId, OfferDetailsNew offerDetailsNew,String vendor);
	void giveBalanceNew(Long ettId, String tId, OfferDetailsNew offerDetailsNew,String vendor,User user);
	
	void giveBonusRetargettingAmountToUser(User user, String tId, OfferDetails offerDetails,float amount);
	
	void giveBonusAmountToNewUser(User user, String tId, OfferDetails offerDetails,float amount,int sameDay);
	void giveBonusAmountToNewUserNew(User user, String string, OfferDetailsNew offerDetails, float offerGiveAmount,int sameDay);
	
	void giveSomeAmountOnIntall(User user, String tId, OfferDetails offerDetails,float amount);
	void giveSomeAmountOnIntallNew(User user, String tId, OfferDetailsNew offerDetails,float amount);

	Map<String, String> getAppVersionUpdate();
	int getBlackListBlockCounter();

	Map<Integer, String> getIsDeviceSupport();
	
	List<Long> getInstallEdrOnHoldList();

	List<String> getAmazonSeriesSus();

	List<String> getOFFERID_BARRED();

	List<String> getDepricatedList();

	ConcurrentHashMap<String, String> getAppConfig();
	
	ConcurrentHashMap<String, String> getLocaleTextTemplate();

	List<String> getREAL_OFFER_PAYOUTTYPE();

	int getAppCounter();

	int getAppCounterAppPayout();

	String getAppCounterAppPayoutMsg();

	int checkTheMonthCountOffer(User user, OfferDetails offerDetails);

	void giveBonusAmountToReferal(User user);

	void getBalanceToUserTarAmount(long ettId, float amount);

	float getShareTabAppVersion();
	
}