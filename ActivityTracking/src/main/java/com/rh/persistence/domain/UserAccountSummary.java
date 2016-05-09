package com.rh.persistence.domain;

import java.io.Serializable;
import java.util.Date;

public class UserAccountSummary implements Serializable{
	
	private long id;
	private float amount;
	private Date createdTime;
	private long ettId;
	private long offerId;
	private String remarks;
	private String offerName;
	private int coin;
	private String vendor;
	private String offerCat;
	private boolean deviceIdFlage;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public long getEttId() {
		return ettId;
	}
	public void setEttId(long ettId) {
		this.ettId = ettId;
	}
	public long getOfferId() {
		return offerId;
	}
	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	public int getCoin() {
		return coin;
	}
	public void setCoin(int coin) {
		this.coin = coin;
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
	public boolean isDeviceIdFlage() {
		return deviceIdFlage;
	}
	public void setDeviceIdFlage(boolean deviceIdFlage) {
		this.deviceIdFlage = deviceIdFlage;
	}
	
	
	

}
