package com.rh.persistence.domain;

import java.io.Serializable;
import java.util.Date;

public class UserActivityBooster implements Serializable{
	
	private long id;
	private long ettId;
	private int currentBalance;
	private int targetBalance;
	private int rechargeAmount;
	private int validityStatus;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getEttId() {
		return ettId;
	}
	public void setEttId(long ettId) {
		this.ettId = ettId;
	}
	public int getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(int currentBalance) {
		this.currentBalance = currentBalance;
	}
	public int getTargetBalance() {
		return targetBalance;
	}
	public void setTargetBalance(int targetBalance) {
		this.targetBalance = targetBalance;
	}
	public int getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(int rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public int getValidityStatus() {
		return validityStatus;
	}
	public void setValidityStatus(int validityStatus) {
		this.validityStatus = validityStatus;
	}
	
	
}
