package com.rh.persistence.domain;

import java.util.Date;

public class PendingCreditsReport {
	private String appKey;
	private Date creditDate;
	private Long totalApp;
	private Long eligibleUser;
	private Long notEligibleUser;
	private Double totalAmountCredited;
	
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public Date getCreditDate() {
		return creditDate;
	}
	public void setCreditDate(Date creditDate) {
		this.creditDate = creditDate;
	}
	public Long getTotalApp() {
		return totalApp;
	}
	public void setTotalApp(Long totalApp) {
		this.totalApp = totalApp;
	}
	public Long getEligibleUser() {
		return eligibleUser;
	}
	public void setEligibleUser(Long eligibleUser) {
		this.eligibleUser = eligibleUser;
	}
	public Long getNotEligibleUser() {
		return notEligibleUser;
	}
	public void setNotEligibleUser(Long notEligibleUser) {
		this.notEligibleUser = notEligibleUser;
	}
	public Double getTotalAmountCredited() {
		return totalAmountCredited;
	}
	public void setTotalAmountCredited(Double double1) {
		this.totalAmountCredited = double1;
	}
	
	
}
