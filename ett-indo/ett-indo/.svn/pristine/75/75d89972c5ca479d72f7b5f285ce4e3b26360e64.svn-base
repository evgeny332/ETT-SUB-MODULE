package com.web.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Ankur
 */
// TODO:: status must have with tags or message
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProfileActionDto {
	private String text;
	
	private String actionUrl;

	private UserProfileDto userProfileDto;
	
	
	
	public UserProfileDto getUserProfileDto() {
		return userProfileDto;
	}

	public void setUserProfileDto(UserProfileDto userProfileDto) {
		this.userProfileDto = userProfileDto;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	
	
}
