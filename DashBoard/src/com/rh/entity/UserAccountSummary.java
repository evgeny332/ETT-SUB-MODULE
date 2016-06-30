package com.rh.entity;

public class UserAccountSummary {
	
	int offerId;
	String appKey;
	int installCount;
	int installAmount;
	int callbackCount;
	int callbackAmount;
	
	public int getOfferId() {
		return offerId;
	}
	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public int getInstallCount() {
		return installCount;
	}
	public void setInstallCount(int installCount) {
		this.installCount = installCount;
	}
	public int getInstallAmount() {
		return installAmount;
	}
	public void setInstallAmount(int installAmount) {
		this.installAmount = installAmount;
	}
	public int getCallbackCount() {
		return callbackCount;
	}
	public void setCallbackCount(int callbackCount) {
		this.callbackCount = callbackCount;
	}
	public int getCallbackAmount() {
		return callbackAmount;
	}
	public void setCallbackAmount(int callbackAmount) {
		this.callbackAmount = callbackAmount;
	}
	
}
