package com.web.rest.dto;




import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Ankur
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BeforRegisterDto {

	private String msisdn;
	private String userUrl;
	
	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getUserUrl() {
		return userUrl;
	}

	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}
	
	
}
