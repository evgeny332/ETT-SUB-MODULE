package com.rh.persistence.domain;

import java.util.Date;


public class DeviceToken {
	

   
    private String deviceId;

    private String deviceToken;

    private boolean active;

    
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

	
    
	 
}

