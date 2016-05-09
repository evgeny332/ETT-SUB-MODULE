package com.web.rest.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * @author Ankur
 */
// TODO:: status must have with tags or message
@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class OffersDtoV4 {
	
	List <OffersDtoV2> offersDto;// cc = new ArrayList<OffersDto>();
	BreakingAlertDto breakingAlertDto;
	PopUpAlertDto popUpAlertDto;
	private float balance;
	private int balanceCoins;
	private float threshold;

	private String inviteFriendUrlText;

	private String inviteFriendText;

	private String bestOffersText;

	private String balanceText;

	private boolean isRedeem;

	private String  appVersion;
	
	private boolean isAddOn;
	
	private String updateText;
	
	private String inviteTabText;
	
	private String popUpMsg;

	private String popUpMsgUrl;
	
	private String appVersionUpdate;
	
	private boolean isNotRegister;
	
	private boolean isDeviceNotSupport ;
	
	private String invitingHeadingText;
	
	private String invitingBodyText;
	
	private boolean isRateUs;
	
	private int offerRefeshTimer;
	
	private boolean defaultAction;
	
	private boolean featureAction;
	
	private int offerCount;
	
	private int remindCounter;
	
	private float inviteAmount;
	
	private String ShoppingTab;
	
	private String RecommendedTab;

	private String RenewBuyTab;
	
	private String ASTROTAB;
	private String FollowIn;
	private String ShareTab;
	private String TARROTTAB ="TARROTTAB";
	private int wifiAutoNotificationCounter;
	private String wifiAutoNotificaitonTextWiFi [];
	private String ongoingTab;
	private String inMobiBannerAds;
	
	private List<DynamicTabDetailsDto> dynamicTabDetailsDtos;
	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public int getBalanceCoins() {
		return balanceCoins;
	}

	public void setBalanceCoins(int balanceCoins) {
		this.balanceCoins = balanceCoins;
	}

	public float getThreshold() {
		return threshold;
	}

	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}

	public String getInviteFriendUrlText() {
		return inviteFriendUrlText;
	}

	public void setInviteFriendUrlText(String inviteFriendUrlText) {
		this.inviteFriendUrlText = inviteFriendUrlText;
	}

	public String getInviteFriendText() {
		return inviteFriendText;
	}

	public void setInviteFriendText(String inviteFriendText) {
		this.inviteFriendText = inviteFriendText;
	}

	public String getBestOffersText() {
		return bestOffersText;
	}

	public void setBestOffersText(String bestOffersText) {
		this.bestOffersText = bestOffersText;
	}

	public String getBalanceText() {
		return balanceText;
	}

	public void setBalanceText(String balanceText) {
		this.balanceText = balanceText;
	}

	public boolean isRedeem() {
		return isRedeem;
	}

	public void setRedeem(boolean isRedeem) {
		this.isRedeem = isRedeem;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public boolean isAddOn() {
		return isAddOn;
	}

	public void setAddOn(boolean isAddOn) {
		this.isAddOn = isAddOn;
	}

	public String getUpdateText() {
		return updateText;
	}

	public void setUpdateText(String updateText) {
		this.updateText = updateText;
	}

	public String getInviteTabText() {
		return inviteTabText;
	}

	public void setInviteTabText(String inviteTabText) {
		this.inviteTabText = inviteTabText;
	}

	public String getPopUpMsg() {
		return popUpMsg;
	}

	public void setPopUpMsg(String popUpMsg) {
		this.popUpMsg = popUpMsg;
	}

	public String getPopUpMsgUrl() {
		return popUpMsgUrl;
	}

	public void setPopUpMsgUrl(String popUpMsgUrl) {
		this.popUpMsgUrl = popUpMsgUrl;
	}

	public String getAppVersionUpdate() {
		return appVersionUpdate;
	}

	public void setAppVersionUpdate(String appVersionUpdate) {
		this.appVersionUpdate = appVersionUpdate;
	}

	public boolean isNotRegister() {
		return isNotRegister;
	}

	public void setNotRegister(boolean isNotRegister) {
		this.isNotRegister = isNotRegister;
	}

	public List<OffersDtoV2> getOffersDto() {
		return offersDto;
	}

	public void setOffersDto(List<OffersDtoV2> offersDto) {
		this.offersDto = offersDto;
	}

	public BreakingAlertDto getBreakingAlertDto() {
		return breakingAlertDto;
	}

	public void setBreakingAlertDto(BreakingAlertDto breakingAlertDto) {
		this.breakingAlertDto = breakingAlertDto;
	}

	public PopUpAlertDto getPopUpAlertDto() {
		return popUpAlertDto;
	}

	public void setPopUpAlertDto(PopUpAlertDto popUpAlertDto) {
		this.popUpAlertDto = popUpAlertDto;
	}

	public String getInvitingHeadingText() {
		return invitingHeadingText;
	}

	public void setInvitingHeadingText(String invitingHeadingText) {
		this.invitingHeadingText = invitingHeadingText;
	}

	public String getInvitingBodyText() {
		return invitingBodyText;
	}

	public void setInvitingBodyText(String invitingBodyText) {
		this.invitingBodyText = invitingBodyText;
	}

	public boolean isDeviceNotSupport() {
		return isDeviceNotSupport;
	}

	public void setDeviceNotSupport(boolean isDeviceNotSupport) {
		this.isDeviceNotSupport = isDeviceNotSupport;
	}

	public int getOfferRefeshTimer() {
		return offerRefeshTimer;
	}

	public void setOfferRefeshTimer(int offerRefeshTimer) {
		this.offerRefeshTimer = offerRefeshTimer;
	}

	public boolean isRateUs() {
		return isRateUs;
	}

	public void setRateUs(boolean isRateUs) {
		this.isRateUs = isRateUs;
	}

	public boolean isDefaultAction() {
		return defaultAction;
	}

	public void setDefaultAction(boolean defaultAction) {
		this.defaultAction = defaultAction;
	}

	public boolean isFeatureAction() {
		return featureAction;
	}

	public void setFeatureAction(boolean featureAction) {
		this.featureAction = featureAction;
	}

	public int getOfferCount() {
		return offerCount;
	}

	public void setOfferCount(int offerCount) {
		this.offerCount = offerCount;
	}

	public int getRemindCounter() {
		return remindCounter;
	}

	public void setRemindCounter(int remindCounter) {
		this.remindCounter = remindCounter;
	}

	public float getInviteAmount() {
		return inviteAmount;
	}

	public void setInviteAmount(float inviteAmount) {
		this.inviteAmount = inviteAmount;
	}

	public String getShoppingTab() {
		return ShoppingTab;
	}

	public void setShoppingTab(String shoppingTab) {
		ShoppingTab = shoppingTab;
	}

	public String getRecommendedTab() {
		return RecommendedTab;
	}

	public void setRecommendedTab(String recommendedTab) {
		RecommendedTab = recommendedTab;
	}

	public String getRenewBuyTab() {
		return RenewBuyTab;
	}

	public void setRenewBuyTab(String renewBuyTab) {
		RenewBuyTab = renewBuyTab;
	}

	public String getFollowIn() {
		return FollowIn;
	}

	public void setFollowIn(String followIn) {
		FollowIn = followIn;
	}

	public String getASTROTAB() {
		return ASTROTAB;
	}

	public void setASTROTAB(String aSTROTAB) {
		ASTROTAB = aSTROTAB;
	}

	public String getShareTab() {
		return ShareTab;
	}

	public void setShareTab(String shareTab) {
		ShareTab = shareTab;
	}

	public int getWifiAutoNotificationCounter() {
		return wifiAutoNotificationCounter;
	}

	public void setWifiAutoNotificationCounter(int wifiAutoNotificationCounter) {
		this.wifiAutoNotificationCounter = wifiAutoNotificationCounter;
	}

	public String[] getWifiAutoNotificaitonTextWiFi() {
		return wifiAutoNotificaitonTextWiFi;
	}

	public void setWifiAutoNotificaitonTextWiFi(
			String[] wifiAutoNotificaitonTextWiFi) {
		this.wifiAutoNotificaitonTextWiFi = wifiAutoNotificaitonTextWiFi;
	}

	public String getTARROTTAB() {
		return TARROTTAB;
	}

	public void setTARROTTAB(String tARROTTAB) {
		TARROTTAB = tARROTTAB;
	}

	public String getOngoingTab() {
		return ongoingTab;
	}

	public void setOngoingTab(String ongoingTab) {
		this.ongoingTab = ongoingTab;
	}

	public String getInMobiBannerAds() {
		return inMobiBannerAds;
	}

	public void setInMobiBannerAds(String inMobiBannerAds) {
		this.inMobiBannerAds = inMobiBannerAds;
	}

	public List<DynamicTabDetailsDto> getDynamicTabDetailsDtos() {
		return dynamicTabDetailsDtos;
	}

	public void setDynamicTabDetailsDtos(
			List<DynamicTabDetailsDto> dynamicTabDetailsDtos) {
		this.dynamicTabDetailsDtos = dynamicTabDetailsDtos;
	}
	
	
}
