package com.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Index;

@Entity
public class AppKeyMap implements Serializable {	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appKey == null) ? 0 : appKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppKeyMap other = (AppKeyMap) obj;
		if (appKey == null) {
			if (other.appKey != null)
				return false;
		} else if (!appKey.equals(other.appKey))
			return false;
		return true;
	}

	@Id
	private String appKey;
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Index(name="appId")
	private Long appId;

	
	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}
	
	
	
}