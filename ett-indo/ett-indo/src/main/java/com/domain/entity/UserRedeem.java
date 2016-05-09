package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.Index;

@Entity
public class UserRedeem implements Serializable {
	 public enum RedeemType{
	        RECHARGE,
	        LOAN
	 }


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Index(name="ettId")
	private Long ettId;
	
	private Long msisdn;
	
	private String circle;
	
	private String operator;
	
	private float amount;
	
	private String type;
	
	private RedeemType redeemType;
	
	private float loanAmount;
	
	private String status;
	
	private Date createdTime;
	
	private Date updatedTime;
	
	private float postBalance;
	
	private float fee;
	
	@PrePersist
	protected void insertDates() {
		if (createdTime == null) {
			createdTime = new Date();
		}
	}
	
	@PreUpdate
	protected void updateDates() {
		if (updatedTime == null) {
			updatedTime = new Date();
		}
	}
	
	
	
	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
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

	public Long getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(Long msisdn) {
		this.msisdn = msisdn;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public RedeemType getRedeemType() {
		return redeemType;
	}

	public void setRedeemType(RedeemType redeemType) {
		this.redeemType = redeemType;
	}

	public float getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(float loanAmount) {
		this.loanAmount = loanAmount;
	}

	public float getPostBalance() {
		return postBalance;
	}

	public void setPostBalance(float postBalance) {
		this.postBalance = postBalance;
	}

	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}
	
	
	
	

}