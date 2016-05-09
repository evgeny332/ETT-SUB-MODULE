package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Index;

@Entity
public class UserDeviceIdChange implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Index(name="userDeviceIdChange_ettId")
	private Long ettId;
	
	@Index(name="userDeviceIdChange_oldDeviceId")
	private String oldDeviceId;
	
	private String newDeviceId;
	
	private Date createdTime;
	
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
	public String getOldDeviceId() {
		return oldDeviceId;
	}
	public void setOldDeviceId(String oldDeviceId) {
		this.oldDeviceId = oldDeviceId;
	}
	public String getNewDeviceId() {
		return newDeviceId;
	}
	public void setNewDeviceId(String newDeviceId) {
		this.newDeviceId = newDeviceId;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	

}
