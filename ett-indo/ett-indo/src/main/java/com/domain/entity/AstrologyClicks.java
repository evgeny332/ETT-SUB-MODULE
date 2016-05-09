package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

@Entity
public class AstrologyClicks implements Serializable{
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long ettId;
	
	private String sunshine;
	
	private Date createdTime;
	
	private int type;
	
	private int clickType;
	@PrePersist
	protected void insertDate(){
		createdTime = new Date();
	}

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

	public String getSunshine() {
		return sunshine;
	}

	public void setSunshine(String sunshine) {
		this.sunshine = sunshine;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getClickType() {
		return clickType;
	}

	public void setClickType(int clickType) {
		this.clickType = clickType;
	}
	
	
}
