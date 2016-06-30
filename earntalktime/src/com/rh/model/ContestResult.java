package com.rh.model;

public class ContestResult {
	
	int id;
	long ettId;
	String msisdn;
	String imageUrl;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getEttId() {
		return ettId;
	}
	public void setEttId(long ettId) {
		this.ettId = ettId;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}