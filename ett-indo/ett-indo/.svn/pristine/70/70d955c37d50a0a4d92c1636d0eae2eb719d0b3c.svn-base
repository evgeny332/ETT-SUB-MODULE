package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EttTabDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String tabName;
	private int tabPos;
	private boolean tabStatus;
	private String tabActionUrl;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public int getTabPos() {
		return tabPos;
	}
	public void setTabPos(int tabPos) {
		this.tabPos = tabPos;
	}
	public boolean isTabStatus() {
		return tabStatus;
	}
	public void setTabStatus(boolean tabStatus) {
		this.tabStatus = tabStatus;
	}
	public String getTabActionUrl() {
		return tabActionUrl;
	}
	public void setTabActionUrl(String tabActionUrl) {
		this.tabActionUrl = tabActionUrl;
	}
	
	
}
