package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Index;

@Entity
public class InstallEdrOnHold implements Serializable{

	@Id
	private String id;
	
	private Long offerId;
	
	@Index(name="ettId")
	private Long ettId;
	
	private float amount;
	
	private Date createdTime;
	
	private float pendingCreditAmount;
	
	private Integer pendingCreditDay;
	
	@PrePersist
	protected void inserDate(){
		createdTime = new Date();
		id = ettId + "_" + offerId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public Long getEttId() {
		return ettId;
	}

	public void setEttId(Long ettId) {
		this.ettId = ettId;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public float getPendingCreditAmount() {
		return pendingCreditAmount;
	}

	public void setPendingCreditAmount(float pendingCreditAmount) {
		this.pendingCreditAmount = pendingCreditAmount;
	}

	public Integer getPendingCreditDay() {
		return pendingCreditDay;
	}

	public void setPendingCreditDay(Integer pendingCreditDay) {
		this.pendingCreditDay = pendingCreditDay;
	}
	
}
