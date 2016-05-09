package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Index;

@Entity
public class DownloadBoosterEligibleUser implements Serializable{

	@Id
	private String id;
	
	@Index(name="ettId")
	private Long ettId;
	
	private float boosterAmount;
	
	private Long offerId;
	
	private String pushTxt;
	
	private Date createdTime;
	
	@PrePersist
	protected void insertValues(){
		if(createdTime == null)
			createdTime = new Date();
		
		id = ettId +"_"+ offerId;
	}

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

	public float getBoosterAmount() {
		return boosterAmount;
	}

	public void setBoosterAmount(float boosterAmount) {
		this.boosterAmount = boosterAmount;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public String getPushTxt() {
		return pushTxt;
	}

	public void setPushTxt(String pushTxt) {
		this.pushTxt = pushTxt;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Override
	public String toString() {
		return "DownloadBoosterEligibleUser [id=" + id + ", ettId=" + ettId
				+ ", boosterAmount=" + boosterAmount + ", offerId=" + offerId
				+ ", pushTxt=" + pushTxt + ", createdTime=" + createdTime + "]";
	}
	
}
