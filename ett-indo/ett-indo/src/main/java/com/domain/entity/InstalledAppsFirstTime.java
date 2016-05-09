package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Index;

@Entity
public class InstalledAppsFirstTime implements Serializable {

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		InstalledAppsFirstTime other = (InstalledAppsFirstTime) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Id
	private String id;
	
	@Index(name= "ettId")
	private Long ettId;
	
	private String appKey;
	
	private Date createdTs;

	private Date createdTime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getEttId() {
		return ettId;
	}

	public void setEttId(Long ettId) {
		this.ettId = ettId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Override
	public String toString() {
		return "InstalledAppsFirstTime [id=" + id + ", ettId=" + ettId
				+ ", appKey=" + appKey + ", createdTs=" + createdTs
				+ ", createdTime=" + createdTime + "]";
	}

	
}
