package com.rh.persistence.domain;

import java.util.Date;

public class User {

	private Long ettId;
	private String deviceId;
	private String msisdn;
	private String appVersion;
	private Date regDate;
	private int redeemCount;
	private int otp;

	public int getOtp() {
		return otp;
	}
	public void setOtp(int otp) {
		this.otp = otp;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

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
