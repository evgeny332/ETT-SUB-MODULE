package com.rh.persistence.domain;


public class UserAccount {
	private Long ettId;
	private float currentBalance;

	@Override
	public String toString() {
		return "UserAccount [ettId=" + ettId + ", currentBalance="
				+ currentBalance + "]";
	}

	public Long getEttId() {
		return ettId;
	}

	public void setEttId(Long ettId) {
		this.ettId = ettId;
	}

	public float getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(float currentBalance) {
		this.currentBalance = currentBalance;
	}
	 
}

