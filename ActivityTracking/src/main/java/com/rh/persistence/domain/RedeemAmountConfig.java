package com.rh.persistence.domain;

import java.io.Serializable;
import java.util.Date;

public class RedeemAmountConfig implements Serializable{
	
	private String amount;
	private String fee;
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
}
