package com.rh.persistence.domain;

import java.util.Date;

public class UserRedeem {

	private Long id;
	
	private Long ettId;
	
	private Long msisdn;
	
	private String circle;
	
	private String operator;
	
	private float amount;
	
	private String type;
	
	private String status;
	private String vender;
	
	private Date createdTime;
	
	private Date updatedTime;

	@Override
	public String toString() {
		return "UserRedeem [id=" + id + ", ettId=" + ettId + ", msisdn="
				+ msisdn + ", circle=" + circle + ", operator=" + operator
				+ ", amount=" + amount + ", type=" + type + ", status="
				+ status + ", vender=" + vender + ", createdTime="
				+ createdTime + ", updatedTime=" + updatedTime + "]";
	}

	public String getVender() {
		return vender;
	}

	public void setVender(String vender) {
		this.vender = vender;
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

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
}
