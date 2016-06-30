package com.rh.entity;

public class RequestData {

	long ettId = 0;
	String gpid = null;
	int otp = 0;
	String ua;
	String ip;
	String locale;
	int yob;
	String gender;
	String income;
	String marital;
	
	
	public long getEttId() {
		return ettId;
	}
	public void setEttId(long ettId) {
		this.ettId = ettId;
	}
	public String getGpid() {
		return gpid;
	}
	public void setGpid(String gpid) {
		this.gpid = gpid;
	}
	public int getOtp() {
		return otp;
	}
	public void setOtp(int otp) {
		this.otp = otp;
	}
	public String getUa() {
		return ua;
	}
	public void setUa(String ua) {
		this.ua = ua;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public int getYob() {
		return yob;
	}
	public void setYob(int yob) {
		this.yob = yob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getMarital() {
		return marital;
	}
	public void setMarital(String marital) {
		this.marital = marital;
	}
}
