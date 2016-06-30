package com.rh.persistence.domain;

import java.util.Date;

public class GiftVoucher {

	private Long ettId;
	private int amount;
	private String transactionId;
	private String code;
	private String pin;
	private String medium;
	private String status;
	private String expiryDate;
	private Date createdTime;
	private String name;
	private String dispatchStatus;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDispatchStatus() {
		return dispatchStatus;
	}

	public void setDispatchStatus(String dispatchStatus) {
		this.dispatchStatus = dispatchStatus;
	}

	public Long getEttId() {
		return ettId;
	}

	public void setEttId(Long ettId) {
		this.ettId = ettId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate2) {
		this.expiryDate = expiryDate2;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
