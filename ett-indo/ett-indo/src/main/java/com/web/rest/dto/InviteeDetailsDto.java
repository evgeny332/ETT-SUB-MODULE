package com.web.rest.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class InviteeDetailsDto {
	
	private String heading;
	private String headerText;
	private String headerHeading;
	private String whatsAppShareText;
	private String smsShareText;
	private List<String> inviteDto;
	
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getHeaderText() {
		return headerText;
	}
	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}
	public String getHeaderHeading() {
		return headerHeading;
	}
	public void setHeaderHeading(String headerHeading) {
		this.headerHeading = headerHeading;
	}
	public String getWhatsAppShareText() {
		return whatsAppShareText;
	}
	public void setWhatsAppShareText(String whatsAppShareText) {
		this.whatsAppShareText = whatsAppShareText;
	}
	public String getSmsShareText() {
		return smsShareText;
	}
	public void setSmsShareText(String smsShareText) {
		this.smsShareText = smsShareText;
	}
	public List<String> getInviteDto() {
		return inviteDto;
	}
	public void setInviteDto(List<String> inviteDto) {
		this.inviteDto = inviteDto;
	}
	
}