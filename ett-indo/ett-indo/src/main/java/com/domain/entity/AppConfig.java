package com.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AppConfig implements Serializable{

	@Id
	private String configKey;
	
	@Column(length = 1000)
	private String value;
	
	public String getKey() {
		return configKey;
	}
	public void setKey(String configKey) {
		this.configKey = configKey;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "AppConfig [configKey=" + configKey + ", value=" + value + "]";
	}
	
	
}
