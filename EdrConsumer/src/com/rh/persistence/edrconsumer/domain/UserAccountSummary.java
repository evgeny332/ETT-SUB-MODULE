package com.rh.persistence.edrconsumer.domain;

import java.util.Date;


public class UserAccountSummary{
	
	private Long id;	
	
	private Long ettId;
	
	
	private Long offerId;
	
	private String offerName;
	
	private float amount;
	
	private String remarks;
	
	private Date createdTime;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEttId() {
		return ettId;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public void setEttId(Long ettId) {
		this.ettId = ettId;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}