package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Astrology implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String sunshine;
	
	private String description;
	
	private Date createdTime;
	
	private Date viewDateIST;
	
	private String shareImageUrl;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSunshine() {
		return sunshine;
	}

	public void setSunshine(String sunshine) {
		this.sunshine = sunshine;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getViewDateIST() {
		return viewDateIST;
	}

	public void setViewDateIST(Date viewDateIST) {
		this.viewDateIST = viewDateIST;
	}

	public String getShareImageUrl() {
		return shareImageUrl;
	}

	public void setShareImageUrl(String shareImageUrl) {
		this.shareImageUrl = shareImageUrl;
	}

	
}
