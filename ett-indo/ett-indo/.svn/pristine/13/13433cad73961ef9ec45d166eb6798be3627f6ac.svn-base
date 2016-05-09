package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

@Entity
public class TransactionTracker implements Serializable {	
	

	@Id
	private String id;
	
	private Long ettId;
	
	private Long offerId;
	
	private String remark;
	
	private Date callBackTime;
	
	private Date installedTime;

	@PrePersist
	protected void insertDates() {
		id = ettId + "_" + offerId;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCallBackTime() {
		return callBackTime;
	}

	public void setCallBackTime(Date callBackTime) {
		this.callBackTime = callBackTime;
	}

	public Date getInstalledTime() {
		return installedTime;
	}

	public void setInstalledTime(Date installedTime) {
		this.installedTime = installedTime;
	}


	public Long getEttId() {
		return ettId;
	}


	public void setEttId(Long ettId) {
		this.ettId = ettId;
	}


	public Long getOfferId() {
		return offerId;
	}


	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}
	
	
}