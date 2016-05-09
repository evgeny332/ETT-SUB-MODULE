package com.domain.entity;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DayAppDownloadCounter implements Serializable {	
	
	
	@Id
	private Long ettId;
	
	private boolean status;
	
	private int appCounter;

	public Long getEttId() {
		return ettId;
	}

	public void setEttId(Long ettId) {
		this.ettId = ettId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getAppCounter() {
		return appCounter;
	}

	public void setAppCounter(int appCounter) {
		this.appCounter = appCounter;
	}
	
		
}