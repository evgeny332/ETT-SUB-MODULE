package com.rh.persistence.edrconsumer.domain;

import java.util.Date;

public class UserBlackList {
	
	private Long ettId;           
	private Integer blackListCounter;
	private Integer type;            
	private Date updatedTime;
	public Long getEttId() {
		return ettId;
	}
	public void setEttId(Long ettId) {
		this.ettId = ettId;
	}
	public Integer getBlackListCounter() {
		return blackListCounter;
	}
	public void setBlackListCounter(Integer blackListCounter) {
		this.blackListCounter = blackListCounter;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}    

}
