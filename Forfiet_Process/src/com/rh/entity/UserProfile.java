package com.rh.entity;

import java.io.Serializable;
import java.util.Date;


public class UserProfile implements Serializable {

	private Long ettId;
	
	private String dob;
	
	private String email;
	
	private String gender;
	
	private String occupation;
	
	private String maritalStatus;
	
	private String income;
	
	private double latitude;
	
	private double longitude;
	
	private Date updateDate;
	
	private String OSVersion;
	
	private String DeviceBrand;
	
	private String DeviceModel;

	private String operator;
	
	private String city;
	private String operatorCircle;


	public Long getEttId() {
		return ettId;
	}


	public void setEttId(Long ettId) {
		this.ettId = ettId;
	}


	public String getDob() {
		return dob;
	}


	public void setDob(String dob) {
		this.dob = dob;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getOccupation() {
		return occupation;
	}


	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}


	public String getMaritalStatus() {
		return maritalStatus;
	}


	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}


	public String getIncome() {
		return income;
	}


	public void setIncome(String income) {
		this.income = income;
	}


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public Date getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public String getOSVersion() {
		return OSVersion;
	}


	public void setOSVersion(String oSVersion) {
		OSVersion = oSVersion;
	}


	public String getDeviceBrand() {
		return DeviceBrand;
	}


	public void setDeviceBrand(String deviceBrand) {
		DeviceBrand = deviceBrand;
	}


	public String getDeviceModel() {
		return DeviceModel;
	}


	public void setDeviceModel(String deviceModel) {
		DeviceModel = deviceModel;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}

	
	public String getOperatorCircle() {
		return operatorCircle;
	}


	public void setOperatorCircle(String operatorCircle) {
		this.operatorCircle = operatorCircle;
	}


	@Override
	public String toString() {
		return "UserProfile [ettId=" + ettId + ", dob=" + dob + ", email="
				+ email + ", gender=" + gender + ", occupation=" + occupation
				+ ", maritalStatus=" + maritalStatus + ", income=" + income
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", updateDate=" + updateDate + ", OSVersion=" + OSVersion
				+ ", DeviceBrand=" + DeviceBrand + ", DeviceModel="
				+ DeviceModel + ", operator=" + operator + ", city=" + city
				+ ", operatorCircle=" + operatorCircle + "]";
	}


		

}