package com.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RedeemThreshold implements Serializable {

	@Id
	private int id;
	
	private float thresholdAmount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getThresholdAmount() {
		return thresholdAmount;
	}

	public void setThresholdAmount(float thresholdAmount) {
		this.thresholdAmount = thresholdAmount;
	}
}
