package com.web.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Ankur
 */
// TODO:: status must have with tags or message
@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class OffersDto {
	private long offerId;
	
	private String imageUrl;
	
	private String appKey;
	
	private String text;
	
	private String description;
	
	private String alertText;
	
	private String amount;
	
	private String actionUrl;
	
	private String type;
	
	private String size;
	
	private String shareUrl;
	
	private String currency;
	
	private boolean isShare;
	
	private boolean isDownload;
	
	private boolean isClick;
	
	private boolean isShop;
	
	private String redirectText;
	
	private String shareHeaderText;

	private String playStoreDetails;
	
	private float rating;
	
	private String instructions;
	
	private String appDescription;
	
	private String detailedInstructions;
	
	private String payoutOn;
	
	private String payoutType;
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean isShare() {
		return isShare;
	}

	public void setShare(boolean isShare) {
		this.isShare = isShare;
	}

	public boolean isDownload() {
		return isDownload;
	}

	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
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

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAlertText() {
		return alertText;
	}

	public void setAlertText(String alertText) {
		this.alertText = alertText;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public long getOfferId() {
		return offerId;
	}

	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
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

	public String getPayoutOn() {
		return payoutOn;
	}

	public void setPayoutOn(String payoutOn) {
		this.payoutOn = payoutOn;
	}

	public String getPayoutType() {
		return payoutType;
	}

	public void setPayoutType(String payoutType) {
		this.payoutType = payoutType;
	}

	
	

}
