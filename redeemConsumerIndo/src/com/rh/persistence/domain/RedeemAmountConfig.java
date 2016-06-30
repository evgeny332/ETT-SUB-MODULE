package com.rh.persistence.domain;

public class RedeemAmountConfig {
	
	private long id;
	
	private String amount;
	
	private boolean status;
	
	private String operator;
	
	private String type;
	
	private String fee;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	@Override
	public String toString() {
		return "RedeemAmountConfig [id=" + id + ", amount=" + amount
				+ ", status=" + status + ", operator=" + operator + ", type="
				+ type + ", fee=" + fee + "]";
	}
	
	
	
}
