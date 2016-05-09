package com.web.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class AstroData {

	private String sunshine;
	
	private String description;

	private String shareImageUrl;
	public String getSunshine() {
		return sunshine;
	}

	public void setSunshine(String sunshine) {
		this.sunshine = sunshine;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShareImageUrl() {
		return shareImageUrl;
	}

	public void setShareImageUrl(String shareImageUrl) {
		this.shareImageUrl = shareImageUrl;
	}
	
	
	
}
