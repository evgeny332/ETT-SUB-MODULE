package com.rh.persistence.domain;



public class User {
	
	
	private Long ettId;
	
	private String deviceId;
	
	private String msisdn;


	private String appVersion;

	private int redeemCount;
	public Long getEttId() {
		return ettId;
	}

	public void setEttId(Long ettId) {
		this.ettId = ettId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public int getRedeemCount() {
		return redeemCount;
	}

	public void setRedeemCount(int redeemCount) {
		this.redeemCount = redeemCount;
	}

	
}

