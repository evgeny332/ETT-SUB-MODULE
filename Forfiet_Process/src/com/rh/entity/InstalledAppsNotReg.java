package com.rh.entity;

import java.io.Serializable;
import java.util.Date;

public class InstalledAppsNotReg implements Serializable{

	private String id;
	
	private Long ettId;
	
	private String appKey;
	
	private Date createdTs;
	
	protected void insertDates() {
		createdTs = new Date();
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


	public Date getCreatedTs() {
		return createdTs;
	}


	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}


}
