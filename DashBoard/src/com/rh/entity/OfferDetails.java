package com.rh.entity;

public class OfferDetails {

	int offerId;
	String offerName;
	String appKey;
	double offerAmount;
	int priority;
	double DeferredPayout;
	double UpfrontPayout;
	
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public double getDeferredPayout() {
		return DeferredPayout;
	}
	public void setDeferredPayout(double deferredPayout) {
		DeferredPayout = deferredPayout;
	}
	public double getUpfrontPayout() {
		return UpfrontPayout;
	}
	public void setUpfrontPayout(double upfrontPayout) {
		UpfrontPayout = upfrontPayout;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}	
	public int getOfferId() {
		return offerId;
	}
	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	public double getOfferAmount() {
		return offerAmount;
	}
	public void setOfferAmount(double offerAmount) {
		this.offerAmount = offerAmount;
	}
}
