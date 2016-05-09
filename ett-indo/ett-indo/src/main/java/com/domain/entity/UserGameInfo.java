package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

@Entity
public class UserGameInfo implements Serializable {

	@Id
	private Long ettId;

	private int gameCounter;

	private int winCounter;	

	private Date updatedTime;

	@PrePersist
	protected void insertDates() {
		updatedTime = new Date();
	}

	public Long getEttId() {
		return ettId;
	}

	public void setEttId(Long ettId) {
		this.ettId = ettId;
	}

	public int getGameCounter() {
		return gameCounter;
	}

	public void setGameCounter(int gameCounter) {
		this.gameCounter = gameCounter;
	}

	public int getWinCounter() {
		return winCounter;
	}

	public void setWinCounter(int winCounter) {
		this.winCounter = winCounter;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}



}