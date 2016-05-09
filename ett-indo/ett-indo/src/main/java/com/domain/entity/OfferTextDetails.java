package com.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Index;

import com.domain.entity.LocaleTextTemplate.Locale;

@Entity
public class OfferTextDetails implements Serializable {

	@Id
	private String id;
	
	@Index(name = "offerText_offerId")
	private long offerId;
	
	private Locale locale;
	
	private String description;
	
	private String alertText;
	
	private String callBackNotification;
	
	private String installNotification;
	
	private String instruction;
	
	private String appDescription;
	
	private String detailedInstructions;
	
	private String userDeferedInfo;

	private String offerCategory;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getOfferId() {
		return offerId;
	}

	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAlertText() {
		return alertText;
	}

	public void setAlertText(String alertText) {
		this.alertText = alertText;
	}

	public String getCallBackNotification() {
		return callBackNotification;
	}

	public void setCallBackNotification(String callBackNotification) {
		this.callBackNotification = callBackNotification;
	}

	public String getInstallNotification() {
		return installNotification;
	}

	public void setInstallNotification(String installNotification) {
		this.installNotification = installNotification;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
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

	public String getUserDeferedInfo() {
		return userDeferedInfo;
	}

	public void setUserDeferedInfo(String userDeferedInfo) {
		this.userDeferedInfo = userDeferedInfo;
	}

	public String getOfferCategory() {
		return offerCategory;
	}

	public void setOfferCategory(String offerCategory) {
		this.offerCategory = offerCategory;
	}

	
}
