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
public class OfferDetailsNew implements Serializable {
	

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
		OfferDetailsNew other = (OfferDetailsNew) obj;
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
	
	private String shareHeaderText;
	
	private String redirectText;
	
	private String currency;
	
	private String vendor;
	
	private String gender;
	private String operatorCircle;
	private String offerCat;
	private String ageLimit;
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

	public String getRedirectText() {
		return redirectText;
	}

	public void setRedirectText(String redirectText) {
		this.redirectText = redirectText;
	}

	public String getShareHeaderText() {
		return shareHeaderText;
	}

	public void setShareHeaderText(String shareHeaderText) {
		this.shareHeaderText = shareHeaderText;
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

	@Override
	public String toString() {
		return "OfferDetailsNew [offerId=" + offerId + ", offerName="
				+ offerName + ", appKey=" + appKey + "]";
	}

	
}
