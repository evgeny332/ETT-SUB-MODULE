package com.etxWeb.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class DNDRespDncIndia {

	 private String status;
     private String number;
     private String call;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCall() {
		return call;
	}
	public void setCall(String call) {
		this.call = call;
	}

     
}
