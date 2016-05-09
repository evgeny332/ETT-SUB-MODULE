package com.web.rest.dto;




import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Ankur
 */
//TODO:: status must have with tags or message
@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class UserProfileDto {
private Long ettId;
	
	private String dob;
	
	private String email;
	
	private String gender;
	
	private String occupation;
	
	private String maritalStatus;
	
	private String income;

	public Long getEttId() {
		return ettId;
	}

	public void setEttId(Long ettId) {
		this.ettId = ettId;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}
	

	

	
    
	
  
}
