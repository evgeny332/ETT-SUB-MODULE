package com.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PopUpAlert implements Serializable {	
	
	@Id
	private Long id;
	
	private Boolean status;
	
	private String heading;
	
	private String text;
	
	private int noOfButton;
	
	private String buttonsText;
	
	private String actinoUrl;

	private int update_triger_id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getNoOfButton() {
		return noOfButton;
	}

	public void setNoOfButton(int noOfButton) {
		this.noOfButton = noOfButton;
	}

	public String getButtonsText() {
		return buttonsText;
	}

	public void setButtonsText(String buttonsText) {
		this.buttonsText = buttonsText;
	}

	public String getActinoUrl() {
		return actinoUrl;
	}

	public void setActinoUrl(String actinoUrl) {
		this.actinoUrl = actinoUrl;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public int getUpdate_triger_id() {
		return update_triger_id;
	}

	public void setUpdate_triger_id(int update_triger_id) {
		this.update_triger_id = update_triger_id;
	}
	
	
}