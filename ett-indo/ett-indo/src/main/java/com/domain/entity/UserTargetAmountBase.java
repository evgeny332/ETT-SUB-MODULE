package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserTargetAmountBase implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ettId;
	
	private Date createdTime;
	private int targetAmount;
	private int totalDayEarning;
	private int rewardAmount;
	private String notification;
	private boolean status;
	private int popUpId;
	private int BreakingAlertId;
	public Long getEttId() {
		return ettId;
	}
	public void setEttId(Long ettId) {
		this.ettId = ettId;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public int getTargetAmount() {
		return targetAmount;
	}
	public void setTargetAmount(int targetAmount) {
		this.targetAmount = targetAmount;
	}
	public int getTotalDayEarning() {
		return totalDayEarning;
	}
	public void setTotalDayEarning(int totalDayEarning) {
		this.totalDayEarning = totalDayEarning;
	}
	public int getRewardAmount() {
		return rewardAmount;
	}
	public void setRewardAmount(int rewardAmount) {
		this.rewardAmount = rewardAmount;
	}
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getPopUpId() {
		return popUpId;
	}
	public void setPopUpId(int popUpId) {
		this.popUpId = popUpId;
	}
	public int getBreakingAlertId() {
		return BreakingAlertId;
	}
	public void setBreakingAlertId(int breakingAlertId) {
		BreakingAlertId = breakingAlertId;
	}
	
	
	
	
	
}
