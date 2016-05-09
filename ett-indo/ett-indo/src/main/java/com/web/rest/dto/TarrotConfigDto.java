package com.web.rest.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class TarrotConfigDto {

	private List<TarrotConfigData> tarrotConfigData;
	private String text;
	public List<TarrotConfigData> getTarrotConfigData() {
		return tarrotConfigData;
	}
	public void setTarrotConfigData(List<TarrotConfigData> tarrotConfigData) {
		this.tarrotConfigData = tarrotConfigData;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

		
}
