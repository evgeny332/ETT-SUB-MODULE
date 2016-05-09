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
public class Edr implements Serializable {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long autoId;
	
	
	private String id;
	
	@Index(name="ettId")
	private Long ettId;
	
	private Long offerId;
	
	private String appKey;
	
	private boolean offerClicked;
	
	private boolean offerInstalled;
	
	private Date clickedTime;
		
	private Date installedTime;
	
	private String vendor;

	private String offerCat;
	
	private boolean deviceIdFlage=true;
	@PrePersist
	protected void insertDates() {
		clickedTime = new Date();
		offerClicked = true;
		id = ettId + "_" + appKey;
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

	public boolean isOfferClicked() {
		return offerClicked;
	}

	public void setOfferClicked(boolean offerClicked) {
		this.offerClicked = offerClicked;
	}

	public boolean isOfferInstalled() {
		return offerInstalled;
	}

	public void setOfferInstalled(boolean offerInstalled) {
		this.offerInstalled = offerInstalled;
	}

	public Date getClickedTime() {
		return clickedTime;
	}

	public void setClickedTime(Date clickedTime) {
		this.clickedTime = clickedTime;
	}

	public Date getInstalledTime() {
		return installedTime;
	}

	public void setInstalledTime(Date installedTime) {
		this.installedTime = installedTime;
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

	public Long getAutoId() {
		return autoId;
	}

	public void setAutoId(Long autoId) {
		this.autoId = autoId;
	}

	
	
	
}