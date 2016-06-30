package com.rh.model;

import java.util.Date;

public class T20 {

	int id;
	Date date;
	String stage;
	String teamA;
	String teamB;
	String winner;
	String winMargin;
	String venue;
	String resultUrl;
	int isPayout;
	String selected;
	boolean status;
	
	public int getIsPayout() {
		return isPayout;
	}
	public void setIsPayout(int isPayout) {
		this.isPayout = isPayout;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getTeamA() {
		return teamA;
	}
	public void setTeamA(String teamA) {
		this.teamA = teamA;
	}
	public String getTeamB() {
		return teamB;
	}
	public void setTeamB(String teamB) {
		this.teamB = teamB;
	}
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}
	public String getWinMargin() {
		return winMargin;
	}
	public void setWinMargin(String winMargin) {
		this.winMargin = winMargin;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getResultUrl() {
		return resultUrl;
	}
	public void setResultUrl(String resultUrl) {
		this.resultUrl = resultUrl;
	}
}
