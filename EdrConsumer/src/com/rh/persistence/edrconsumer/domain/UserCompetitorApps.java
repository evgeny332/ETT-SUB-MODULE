package com.rh.persistence.edrconsumer.domain;

import java.util.Date;

public class UserCompetitorApps {
	
	private Long ettId;
	private Date installedTime;
	private String appKey;
	public Long getEttId() {
		return ettId;
	}
	public void setEttId(Long ettId) {
		this.ettId = ettId;
	}
	public Date getInstalledTime() {
		return installedTime;
	}
	public void setInstalledTime(Date installedTime) {
		this.installedTime = installedTime;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
}
