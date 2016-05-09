package com.rh.entity;

import java.io.Serializable;
import java.util.Date;


public class DeviceToken implements Serializable {

    public enum DeviceType{
        IPHONE,
        ANDROID,
        WAP,
        WEB,
        UNKNOWN
    }

   
    private String deviceId;

    private Long ettId;
    
    private String deviceToken;

    private boolean active;

    private DeviceType deviceType;   
    
    private Date createdTime;

    private Date updatedTime;

    private String androidId;
    private String email;
    private String advertisingId;
    private String macAddress;
    
    protected void insertDates() {
        if (createdTime == null) {
            createdTime = new Date();
        }
    }

    protected void updateDates() {
           updatedTime = new Date();
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


	public String getDeviceToken() {
		return deviceToken;
	}


	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public DeviceType getDeviceType() {
		return deviceType;
	}


	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}


	public Date getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}


	public Date getUpdatedTime() {
		return updatedTime;
	}


	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}


	public String getAndroidId() {
		return androidId;
	}


	public void setAndroidId(String androidId) {
		this.androidId = androidId;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAdvertisingId() {
		return advertisingId;
	}


	public void setAdvertisingId(String advertisingId) {
		this.advertisingId = advertisingId;
	}


	public String getMacAddress() {
		return macAddress;
	}


	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	
	
}
