package com.web.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Ankur
 */
// TODO:: status must have with tags or message
@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class OfferInstructionDto {
	private String instructionsText;
	
	private String instStringAmount;
	
	private int status;

public String getInstructionsText() {
		return instructionsText;
	}

	public void setInstructionsText(String instructionsText) {
		this.instructionsText = instructionsText;
	}

	public String getInstStringAmount() {
		return instStringAmount;
	}

	public void setInstStringAmount(String instStringAmount) {
		this.instStringAmount = instStringAmount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
}
