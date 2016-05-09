package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class UserDeviceInfo implements Serializable {

	@Id
	private String id;
	
	private Long ettId;
	
	private String alreadyInstalledApps;
	
	private String androidVersion;
	
	private String deviceName;
	
	private Date createdTime;

	private Date updatedTime;

	
	@PrePersist
	protected void insertDates() {
		createdTime = new Date();
		updatedTime = new Date();
		id = ettId+"_"+alreadyInstalledApps;
	}

	@PreUpdate
	protected void updateDates() {
		updatedTime = new Date();
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

	public String getAlreadyInstalledApps() {
		return alreadyInstalledApps;
	}

	public void setAlreadyInstalledApps(String alreadyInstalledApps) {
		this.alreadyInstalledApps = alreadyInstalledApps;
	}

	public String getAndroidVersion() {
		return androidVersion;
	}

	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
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
	
}