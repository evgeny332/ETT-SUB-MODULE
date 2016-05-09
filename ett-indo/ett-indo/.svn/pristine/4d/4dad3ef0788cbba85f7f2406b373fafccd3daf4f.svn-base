package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Index;

@Entity
public class UserCompetitorApps implements Serializable {	
	@Id
	private String id;
	
	@Index(name="ettId")
	private Long ettId;
	
	private Date installedTime;
		
	private String appKey;

	@PrePersist
	protected void insertDates() {
		installedTime = new Date();
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

	
	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	
	public Date getInstalledTime() {
		return installedTime;
	}

	public void setInstalledTime(Date installedTime) {
		this.installedTime = installedTime;
	}
	
}