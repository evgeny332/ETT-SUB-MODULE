package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.Index;

@Entity
public class OfferDetails implements Serializable {
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((offerId == null) ? 0 : offerId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OfferDetails other = (OfferDetails) obj;
		if (offerId == null) {
			if (other.offerId != null)
				return false;
		} else if (!offerId.equals(other.offerId))
			return false;
		return true;
	}
	
	 public enum BalanceType{
	        CALLBACK,
	        INSTALL,
	        ANY,
	    }
	 
	 public enum PayoutOn {
		 open,
		 register,
		 use,
		 order,
		 healthQuery,
		 searchFlight,
		 //user,
	 }
	 public enum PayoutType {
		 INSTALL,
		 DEFFERED,
		 SHARE,
		 INVITE,
		 SURVEY,
		 SHAREDIRECT,
		 CLICK,
		 SPECIALOFFER,
		 LOANSERVICE,
		 DATAUSAGE
	}
	 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long offerId;

	private String offerName;
	
	private String offerType;
	
	@Index(name="appKey")
	private String appKey;
	
	private String imageUrl;
	
	private String actionUrl;
	
	private float offerAmount;
	
	private String description;
	
	private String alertText;
	
	private boolean status;
	//2G/3G/WIFI/ALL
	private String networkType;
	
	private BalanceType balanceType;
	
	private String operator;
	
	private String size;
	
	private int priority;
	
	private Date createdTime;

	private Date updatedTime;

	private String category;
	
	private int balanceCreditInDays;

	private boolean isDownload;
	
	private boolean isShare;
	
	private boolean isClick;
	
	private boolean isShop;
	
	private String root;
	
	private int coins;
	
	private float pendingAmountCredit;
	
	private int pendingRecDay;
	
	private int pendingRecCount;
	
	private int installedAmount;
	
	private String installedNotification;
	private String callbackNotification;
	
	private String currency;
	
	private String vendor;
	private String gender;
	private String operatorCircle;
	private String offerCat;
	private String ageLimit;
	
	private String playStoreDetails;
	private float rating;
	private String instructions;
	private String appDescription;
	private String detailedInstructions;
	
	private PayoutOn payoutOn;
	private PayoutType payoutType;

	private String offerCategory;

	private String userDeferedInfo;
	
	private String offerPaymentType;
	
	private String title;
	
	private String packageName;
	
	private boolean offerUrlParamFromDB;
	
	private float maxCreditLimit;
	
	private float maxCreditPerDayLimit;
	
	private float amountPerDataThreshold;
	
	private int dataThreshold;
	
	private int dataUsageEligibleDays;
	
	private int timeIntervalOfRecheck;
	
	private int update_triger_id;
	
	public float getMaxCreditLimit() {
		return maxCreditLimit;
	}

	public void setMaxCreditLimit(float maxCreditLimit) {
		this.maxCreditLimit = maxCreditLimit;
	}

	public float getMaxCreditPerDayLimit() {
		return maxCreditPerDayLimit;
	}

	public void setMaxCreditPerDayLimit(float maxCreditPerDayLimit) {
		this.maxCreditPerDayLimit = maxCreditPerDayLimit;
	}

	public float getAmountPerDataThreshold() {
		return amountPerDataThreshold;
	}

	public void setAmountPerDataThreshold(float amountPerDataThreshold) {
		this.amountPerDataThreshold = amountPerDataThreshold;
	}

	public Integer getDataThreshold() {
		return dataThreshold;
	}

	public void setDataThreshold(Integer dataThreshold) {
		this.dataThreshold = dataThreshold;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	@PrePersist
	protected void insertDates() {
		createdTime = new Date();
		updatedTime = new Date();
	}

	@PreUpdate
	protected void updateDates() {
		updatedTime = new Date();
	}
	
	public boolean isDownload() {
		return isDownload;
	}

	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
	}

	public boolean isShare() {
		return isShare;
	}

	public void setShare(boolean isShare) {
		this.isShare = isShare;
	}

	public boolean isClick() {
		return isClick;
	}

	public void setClick(boolean isClick) {
		this.isClick = isClick;
	}

	public boolean isShop() {
		return isShop;
	}

	public void setShop(boolean isShop) {
		this.isShop = isShop;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BalanceType getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(BalanceType balanceType) {
		this.balanceType = balanceType;
	}

	public String getAlertText() {
		return alertText;
	}

	public void setAlertText(String alertText) {
		this.alertText = alertText;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getOfferType() {
		return offerType;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public float getOfferAmount() {
		return offerAmount;
	}

	public void setOfferAmount(float offerAmount) {
		this.offerAmount = offerAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public int getBalanceCreditInDays() {
		return balanceCreditInDays;
	}

	public void setBalanceCreditInDays(int balanceCreditInDays) {
		this.balanceCreditInDays = balanceCreditInDays;
	}

	public float getPendingAmountCredit() {
		return pendingAmountCredit;
	}

	public void setPendingAmountCredit(float pendingAmountCredit) {
		this.pendingAmountCredit = pendingAmountCredit;
	}

	public int getPendingRecDay() {
		return pendingRecDay;
	}

	public void setPendingRecDay(int pendingRecDay) {
		this.pendingRecDay = pendingRecDay;
	}

	public int getPendingRecCount() {
		return pendingRecCount;
	}

	public void setPendingRecCount(int pendingRecCount) {
		this.pendingRecCount = pendingRecCount;
	}

	public int getInstalledAmount() {
		return installedAmount;
	}

	public void setInstalledAmount(int installedAmount) {
		this.installedAmount = installedAmount;
	}

	public String getInstalledNotification() {
		return installedNotification;
	}

	public void setInstalledNotification(String installedNotification) {
		this.installedNotification = installedNotification;
	}

	public String getCallbackNotification() {
		return callbackNotification;
	}

	public void setCallbackNotification(String callbackNotification) {
		this.callbackNotification = callbackNotification;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "OfferDetails [offerId=" + offerId + ", offerName=" + offerName
				+ ", appKey=" + appKey + "]";
	}

	public String getOperatorCircle() {
		return operatorCircle;
	}

	public void setOperatorCircle(String operatorCircle) {
		this.operatorCircle = operatorCircle;
	}

	public String getOfferCat() {
		return offerCat;
	}

	public void setOfferCat(String offerCat) {
		this.offerCat = offerCat;
	}

	public String getAgeLimit() {
		return ageLimit;
	}

	public void setAgeLimit(String ageLimit) {
		this.ageLimit = ageLimit;
	}

	public String getPlayStoreDetails() {
		return playStoreDetails;
	}

	public void setPlayStoreDetails(String playStoreDetails) {
		this.playStoreDetails = playStoreDetails;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getAppDescription() {
		return appDescription;
	}

	public void setAppDescription(String appDescription) {
		this.appDescription = appDescription;
	}

	public String getDetailedInstructions() {
		return detailedInstructions;
	}

	public void setDetailedInstructions(String detailedInstructions) {
		this.detailedInstructions = detailedInstructions;
	}

	public PayoutOn getPayoutOn() {
		return payoutOn;
	}

	public void setPayoutOn(PayoutOn payoutOn) {
		this.payoutOn = payoutOn;
	}

	public PayoutType getPayoutType() {
		return payoutType;
	}

	public void setPayoutType(PayoutType payoutType) {
		this.payoutType = payoutType;
	}

	public String getUserDeferedInfo() {
		return userDeferedInfo;
	}

	public void setUserDeferedInfo(String userDeferedInfo) {
		this.userDeferedInfo = userDeferedInfo;
	}

	public String getOfferCategory() {
		return offerCategory;
	}

	public void setOfferCategory(String offerCategory) {
		this.offerCategory = offerCategory;
	}

	public String getOfferPaymentType() {
		return offerPaymentType;
	}

	public void setOfferPaymentType(String offerPaymentType) {
		this.offerPaymentType = offerPaymentType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public boolean isOfferUrlParamFromDB() {
		return offerUrlParamFromDB;
	}

	public void setOfferUrlParamFromDB(boolean offerUrlParamFromDB) {
		this.offerUrlParamFromDB = offerUrlParamFromDB;
	}

	public int getDataUsageEligibleDays() {
		return dataUsageEligibleDays;
	}

	public void setDataUsageEligibleDays(int dataUsageEligibleDays) {
		this.dataUsageEligibleDays = dataUsageEligibleDays;
	}

	public int getTimeIntervalOfRecheck() {
		return timeIntervalOfRecheck;
	}

	public void setTimeIntervalOfRecheck(int timeIntervalOfRecheck) {
		this.timeIntervalOfRecheck = timeIntervalOfRecheck;
	}

	public int getUpdate_triger_id() {
		return update_triger_id;
	}

	public void setUpdate_triger_id(int update_triger_id) {
		this.update_triger_id = update_triger_id;
	}

	public void setDataThreshold(int dataThreshold) {
		this.dataThreshold = dataThreshold;
	}

	
}
