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
public class UserAccountSummary implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Index(name="ettId")
	private Long ettId;
	
	@Index(name="offerId")
	private Long offerId;
	
	private String offerName;
	
	private float amount;
	
	private String remarks;
	
	private Date createdTime;
	
	private int coin;
	
	private String vendor;
	
	private String offerCat;
	private boolean deviceIdFlage=true;
	
	@PrePersist
	protected void updateDates() {
		if (createdTime == null) {
			createdTime = new Date();
		}
	}
	
	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

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

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getOfferCat() {
		return offerCat;
	}

	public void setOfferCat(String offerCat) {
		this.offerCat = offerCat;
	}

	public boolean isDeviceIdFlage() {
		return deviceIdFlage;
	}

	public void setDeviceIdFlage(boolean deviceIdFlage) {
		this.deviceIdFlage = deviceIdFlage;
	}
	
	
	
}