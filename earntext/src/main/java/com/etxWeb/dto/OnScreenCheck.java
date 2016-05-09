package com.etxWeb.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class OnScreenCheck {
	private int redeemCount;
	private long lastValidationDate;
	public int getRedeemCount() {
		return redeemCount;
	}
	public void setRedeemCount(int redeemCount) {
		this.redeemCount = redeemCount;
	}
	public long getLastValidationDate() {
		return lastValidationDate;
	}
	public void setLastValidationDate(long lastValidationDate) {
		this.lastValidationDate = lastValidationDate;
	}
	
	
}
