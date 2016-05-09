package com.rh.persistence.domain;

import java.util.Date;

public class SchedulePush {

	private Long id;
	private Long ettId;
	private String message;
	private Date pushTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEttId() {
		return ettId;
	}
	public void setEttId(Long ettId) {
		this.ettId = ettId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String messageId) {
		this.message = messageId;
	}
	public Date getPushTime() {
		return pushTime;
	}
	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}
	
}
