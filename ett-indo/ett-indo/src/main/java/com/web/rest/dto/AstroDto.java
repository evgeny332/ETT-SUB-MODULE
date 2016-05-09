package com.web.rest.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class AstroDto {

	private List<AstroData> astroData;
	private String text;

	public List<AstroData> getAstroData() {
		return astroData;
	}

	public void setAstroData(List<AstroData> astroData) {
		this.astroData = astroData;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
