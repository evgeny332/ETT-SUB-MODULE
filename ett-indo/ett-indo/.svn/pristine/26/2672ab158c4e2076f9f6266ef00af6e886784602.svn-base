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
public class PendingCredits implements Serializable {
	
	 public enum CreditStatus{
	        PENDING,
	        SUCCESS,
	        FAILED,
	 }

 	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long autoId;
 
	private String id;

	@Index(name="ettId")
	private Long ettId;
	
	private Long offerId;	
	
	private String appKey;
	
	private float amount;
	
	private Date createdTime;
	
	private Date creditDate;
	
	private String vendor;
	private String offerCat;
	@PrePersist
	protected void insertDates() {
		createdTime = new Date();
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


	public String getAppKey() {
		return appKey;
	}


	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}


	public float getAmount() {
		return amount;
	}


	public void setAmount(float amount) {
		this.amount = amount;
	}


	public Date getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}


	public Date getCreditDate() {
		return creditDate;
	}


	public void setCreditDate(Date creditDate) {
		this.creditDate = creditDate;
	}

	public String getVendor() {
		return vendor;
	}


	public void setVendor(String vendor) {
		this.vendor = vendor;
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

	
}
