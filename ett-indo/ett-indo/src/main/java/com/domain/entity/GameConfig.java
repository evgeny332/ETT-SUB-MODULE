package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GameConfig implements Serializable {

	@Id
	private Long id;
	
	private float dailyBudget;
	
	private float winAmount;
	
	private Integer winningFrequency;
	
	
	private Date createdTime;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public float getDailyBudget() {
		return dailyBudget;
	}


	public void setDailyBudget(float dailyBudget) {
		this.dailyBudget = dailyBudget;
	}


	public float getWinAmount() {
		return winAmount;
	}


	public void setWinAmount(float winAmount) {
		this.winAmount = winAmount;
	}


	public Integer getWinningFrequency() {
		return winningFrequency;
	}


	public void setWinningFrequency(Integer winningFrequency) {
		this.winningFrequency = winningFrequency;
	}


	public Date getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}


	
	
}