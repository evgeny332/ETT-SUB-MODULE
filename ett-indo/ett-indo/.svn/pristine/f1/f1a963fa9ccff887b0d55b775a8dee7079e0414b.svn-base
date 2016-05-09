package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Index;

@Entity
public class CallBackConfirmation implements Serializable {	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long autoId;
	
	private String id;
	@Index(name="ettId")
	private Long ettId;
	
	private Long offerId;
	
	private String tId;
	
	private String msisdn;
	
	private Date createdTime;
		
	private String vendor;
	
	private float amount;
	
	private Integer currency;

	private String offerCat;
	
	private boolean deviceIdFlage=true;
	@PrePersist
	protected void insertDates() {
		createdTime = new Date();
		id = ettId + "_" + offerId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getEttId() {
		return ettId;
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

	public String gettId() {
		return tId;
	}

	public void settId(String tId) {
		this.tId = tId;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Integer getCurrency() {
		return currency;
	}

	public void setCurrency(Integer currency) {
		this.currency = currency;
	}

	public String getOfferCat() {
		return offerCat;
	}

	public void setOfferCat(String offerCat) {
		this.offerCat = offerCat;
	}

	public Long getAutoId() {
		return autoId;
	}

	public void setAutoId(Long autoId) {
		this.autoId = autoId;
	}

	public boolean isDeviceIdFlage() {
		return deviceIdFlage;
	}

	public void setDeviceIdFlage(boolean deviceIdFlage) {
		this.deviceIdFlage = deviceIdFlage;
	}
	
	
	
}