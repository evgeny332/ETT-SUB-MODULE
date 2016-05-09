package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Index;

@Entity
public class UserGameSummary implements Serializable {

	public enum GameStatus {
		WON,
		LOST,
		BUDGET_EXPIRED
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Index(name="ettId")
	private Long ettId;

	private int gameCounter;
	
	private int winCounter;
	
	@Index(name="gameStatus")
	private GameStatus gameStatus;
	
	private long tId;
	
	@Index(name="createdTime")
	private Date createdTime;

	@PrePersist
	protected void insertDates() {
		createdTime = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public long gettId() {
		return tId;
	}

	public void settId(long tId) {
		this.tId = tId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	
}