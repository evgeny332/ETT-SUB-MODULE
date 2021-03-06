package com.web.rest.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Ankur
 */
// TODO:: status must have with tags or message
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class OffersStartedDto {
	
	private String id;
	private long offerId;
	
	private String imageUrl;
	
	private String offerName;
	
	private String payoutType;
	
	private String offerLifeCycle;
	
	private String approveInfoText;
	
	private String criticalInfo;
	
	private String earnInfo;
	
	private String deferedPaymentFinalInfor;
	
	private String userDeferedInfo;
	
	private String offerCategory;
	
	private String packageName;

	private String update_id;
	public List<OfferInstructionDto> offerInstructionDto;
	
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

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getPayoutType() {
		return payoutType;
	}

	public void setPayoutType(String payoutType) {
		this.payoutType = payoutType;
	}

	public String getOfferLifeCycle() {
		return offerLifeCycle;
	}

	public void setOfferLifeCycle(String offerLifeCycle) {
		this.offerLifeCycle = offerLifeCycle;
	}

	public String getApproveInfoText() {
		return approveInfoText;
	}

	public void setApproveInfoText(String approveInfoText) {
		this.approveInfoText = approveInfoText;
	}

	public String getCriticalInfo() {
		return criticalInfo;
	}

	public void setCriticalInfo(String criticalInfo) {
		this.criticalInfo = criticalInfo;
	}

	public String getEarnInfo() {
		return earnInfo;
	}

	public void setEarnInfo(String earnInfo) {
		this.earnInfo = earnInfo;
	}

	public String getDeferedPaymentFinalInfor() {
		return deferedPaymentFinalInfor;
	}

	public void setDeferedPaymentFinalInfor(String deferedPaymentFinalInfor) {
		this.deferedPaymentFinalInfor = deferedPaymentFinalInfor;
	}

	public String getUserDeferedInfo() {
		return userDeferedInfo;
	}

	public void setUserDeferedInfo(String userDeferedInfo) {
		this.userDeferedInfo = userDeferedInfo;
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

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUpdate_id() {
		return update_id;
	}

	public void setUpdate_id(String update_id) {
		this.update_id = update_id;
	}

	
	
	
	
}
