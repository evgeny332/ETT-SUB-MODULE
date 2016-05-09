package com.rh.persistence.domain;

import java.util.Date;

public class UserAccountSummary {

	private Long id;
	private Long ettId;
	private Long offerId;
	private String offerName;
	private float amount;
	private String remarks;
	private Date createdTime;

	public Long getId() {
		return this.id;
	}

	public void setId(Long paramLong) {
		this.id = paramLong;
	}

	public Long getEttId() {
		return this.ettId;
	}

	public String getOfferName() {
		return this.offerName;
	}

	public void setOfferName(String paramString) {
		this.offerName = paramString;
	}

	public void setEttId(Long paramLong) {
		this.ettId = paramLong;
	}

	public Long getOfferId() {
		return this.offerId;
	}

	public void setOfferId(Long paramLong) {
		this.offerId = paramLong;
	}

	public float getAmount() {
		return this.amount;
	}

	public void setAmount(float paramFloat) {
		this.amount = paramFloat;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String paramString) {
		this.remarks = paramString;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date paramDate) {
		this.createdTime = paramDate;
	}
}