package com.etxWeb.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class SmsCliDto {

	private int nextStatus;
	private String cliList;
	public int getNextStatus() {
		return nextStatus;
	}
	public void setNextStatus(int nextStatus) {
		this.nextStatus = nextStatus;
	}
	public String getCliList() {
		return cliList;
	}
	public void setCliList(String cliList) {
		this.cliList = cliList;
	}
	@Override
	public String toString() {
		return "SmsCliDto [nextStatus=" + nextStatus + ", cliList=" + cliList
				+ "]";
	}
	
	
}
