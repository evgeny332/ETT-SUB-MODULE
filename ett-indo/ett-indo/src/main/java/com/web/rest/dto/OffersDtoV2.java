package com.web.rest.dto;

import java.util.List;

import com.domain.entity.OfferDetails;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Ankur
 */
// TODO:: status must have with tags or message
@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class OffersDtoV2 {
	private Long offerId;
	
	private String imageUrl;
	
	private String appKey;
	
	private String offerName;
	
	private String text;
	
	private String description;
	
	private String alertText;
	
	private String amount;
	
	private String actionUrl;
	
	private String type;
	
	private String size;
	
	private String shareUrl;
	
	private String currency;
	
	private String redirectText;
	
	private String shareHeaderText;

	private String playStoreDetails;
	
	private float rating;
	
	private String appRating;
	
	private String appDescription;
	
	private String detailedInstructions;
	
	private String payoutOn;
	
	private String payoutType;
	
	private String offerCategory;
	
	private String title;
	private String packageName;
	
	private String update_id;
	public List<OfferInstructionDto> offerInstructionDto;
	
	
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
		OffersDtoV2 other = (OffersDtoV2) obj;
		if (offerId == null) {
			if (other.offerId != null)
				return false;
		} else if (!offerId.equals(other.offerId))
			return false;
		return true;
	}
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	
	public List<OfferInstructionDto> getOfferInstructionDto() {
		return offerInstructionDto;
	}

	public void setOfferInstructionDto(List<OfferInstructionDto> offerInstructionDto) {
		this.offerInstructionDto = offerInstructionDto;
	}

	public String getOfferCategory() {
		return offerCategory;
	}

	public void setOfferCategory(String offerCategory) {
		this.offerCategory = offerCategory;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
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

	public String getUpdate_id() {
		return update_id;
	}

	public void setUpdate_id(String update_id) {
		this.update_id = update_id;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public String getAppRating() {
		return appRating;
	}

	public void setAppRating(String appRating) {
		this.appRating = appRating;
	}

	
}
