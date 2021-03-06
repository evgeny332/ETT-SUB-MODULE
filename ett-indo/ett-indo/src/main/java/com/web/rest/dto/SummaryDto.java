package com.web.rest.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Ankur
 */
// TODO:: status must have with tags or message
@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class SummaryDto {
	private long id;
	
	private long offerId;
	
	private String offerName;
	
	private String text;
	
	private float amount;
	
	private Date date;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOfferId() {
		return offerId;
	}

	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	


	
}
