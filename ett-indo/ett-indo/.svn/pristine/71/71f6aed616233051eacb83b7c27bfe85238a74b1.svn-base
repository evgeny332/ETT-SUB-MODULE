package com.web.rest.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Ankur
 */
// TODO:: status must have with tags or message
@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class AccountSummaryDto {
	private float total;
	
	private List<SummaryDto> summary;

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public List<SummaryDto> getSummary() {
		return summary;
	}

	public void setSummary(List<SummaryDto> summary) {
		this.summary = summary;
	}
	
	
}
