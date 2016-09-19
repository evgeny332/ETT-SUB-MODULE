package com.earntt;
import java.io.*;
public class OfferDetails
{

	private String offerId;
	private String offerName;
	
	private String offerType;
	
	private String appKey;
	
	private String imageUrl;
	
	private String actionUrl;
	
	private String offerAmount;
	
	private String description;
	
	private String status;
	
	private String operator;
	
	private String priority;
	
	private String createdTime;

	private String updatedTime;
	private String size;
	private String networkType;
	private String alertText;
	private String balanceType;
	private String category;
	private String balanceCreditInDays;
	private String isClick;
	private String isDownload;
	private String isShare;
	private String isShop;
	private String root;
	private String redirectText;
	private String pendingAmountCredit;
	private String pendingRecCount;
	private String pendingRecDay;
	private String callbackNotification;
	private String installedAmount;
	private String installedNotification;
	private String vendor;
	private String gender;               
	private String operatorCircle;       
	private String offerCat ;            
	private String ageLimit  ;           
	private String playStoreDetails;     
	private String rating           ;    
	private String instructions      ;   
	private String appDescription     ;  
	private String detailedInstructions; 
	private String payOutOn             ;
	private String payoutType ;
	private String offerCategory;
	private String userDeferedInfo;
	private String title;
	private String offerUrlParamFromDB;
	private String maxCreditLimit;
	private String maxCreditPerDayLimit;
	private String amountPerDataThreshold;
	private String dataThreshold;
	private String dataUsageEligibleDays;
	private String timeIntervalOfRecheck;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOfferUrlParamFromDB() {
		return offerUrlParamFromDB;
	}
	public void setOfferUrlParamFromDB(String offerUrlParamFromDB) {
		this.offerUrlParamFromDB = offerUrlParamFromDB;
	}
	public String getMaxCreditLimit() {
		return maxCreditLimit;
	}
	public void setMaxCreditLimit(String maxCreditLimit) {
		this.maxCreditLimit = maxCreditLimit;
	}
	public String getMaxCreditPerDayLimit() {
		return maxCreditPerDayLimit;
	}
	public void setMaxCreditPerDayLimit(String maxCreditPerDayLimit) {
		this.maxCreditPerDayLimit = maxCreditPerDayLimit;
	}
	public String getAmountPerDataThreshold() {
		return amountPerDataThreshold;
	}
	public void setAmountPerDataThreshold(String amountPerDataThreshold) {
		this.amountPerDataThreshold = amountPerDataThreshold;
	}
	public String getDataThreshold() {
		return dataThreshold;
	}
	public void setDataThreshold(String dataThreshold) {
		this.dataThreshold = dataThreshold;
	}
	public String getDataUsageEligibleDays() {
		return dataUsageEligibleDays;
	}
	public void setDataUsageEligibleDays(String dataUsageEligibleDays) {
		this.dataUsageEligibleDays = dataUsageEligibleDays;
	}
	public String getTimeIntervalOfRecheck() {
		return timeIntervalOfRecheck;
	}
	public void setTimeIntervalOfRecheck(String timeIntervalOfRecheck) {
		this.timeIntervalOfRecheck = timeIntervalOfRecheck;
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
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
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
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
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
	public String getPayOutOn() {
		return payOutOn;
	}
	public void setPayOutOn(String payOutOn) {
		this.payOutOn = payOutOn;
	}
	public String getPayoutType() {
		return payoutType;
	}
	public void setPayoutType(String payoutType) {
		this.payoutType = payoutType;
	}
	public String getPendingAmountCredit() {
		return pendingAmountCredit;
	}
	public void setPendingAmountCredit(String pendingAmountCredit) {
		this.pendingAmountCredit = pendingAmountCredit;
	}
	public String getPendingRecCount() {
		return pendingRecCount;
	}
	public void setPendingRecCount(String pendingRecCount) {
		this.pendingRecCount = pendingRecCount;
	}
	public String getPendingRecDay() {
		return pendingRecDay;
	}
	public void setPendingRecDay(String pendingRecDay) {
		this.pendingRecDay = pendingRecDay;
	}
	public String getCallbackNotification() {
		return callbackNotification;
	}
	public void setCallbackNotification(String callbackNotification) {
		this.callbackNotification = callbackNotification;
	}
	public String getInstalledAmount() {
		return installedAmount;
	}
	public void setInstalledAmount(String installedAmount) {
		this.installedAmount = installedAmount;
	}
	public String getInstalledNotification() {
		return installedNotification;
	}
	public void setInstalledNotification(String installedNotification) {
		this.installedNotification = installedNotification;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBalanceCreditInDays() {
		return balanceCreditInDays;
	}
	public void setBalanceCreditInDays(String balanceCreditInDays) {
		this.balanceCreditInDays = balanceCreditInDays;
	}
	public String getIsClick() {
		return isClick;
	}
	public void setIsClick(String isClick) {
		this.isClick = isClick;
	}
	public String getIsDownload() {
		return isDownload;
	}
	public void setIsDownload(String isDownload) {
		this.isDownload = isDownload;
	}
	public String getIsShare() {
		return isShare;
	}
	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}
	public String getIsShop() {
		return isShop;
	}
	public void setIsShop(String isShop) {
		this.isShop = isShop;
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public String getBalanceType()
	{
		return balanceType;
	}
	public void setBalanceType(String balanceType)
	{
		this.balanceType=balanceType;
	}
	public String getNetworkType() {
                return networkType;
        }

        public void setNetworkType(String networkType) {
                this.networkType =networkType;
        }
	public String getAlertText() {
                return alertText;
        }

        public void setAlertText(String alertText) {
                this.alertText =alertText;
        }
	
	public String getSize() {
                return size;
        }

        public void setSize(String size) {
                this.size =size;
        }



	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
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

	public String getOfferAmount() {
		return offerAmount;
	}

	public void setOfferAmount(String offerAmount) {
		this.offerAmount = offerAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}
	public String getRedirectText() {
                return redirectText;
        }

        public void setRedirectText(String redirectText) {
                this.redirectText = redirectText;
        }
	 public String getVendor() {
                return vendor;
        }

        public void setVendor(String vendor) {
                this.vendor = vendor;
        }



	
}

