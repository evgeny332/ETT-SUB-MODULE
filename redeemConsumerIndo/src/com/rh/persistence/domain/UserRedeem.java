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
	private RedeemType redeemType;
	private Date createdTime;
	private Date updatedTime;
	private String trans_id;
	private String trans_id_ett;
	private float fee;
	private float postBalance;

	public String toString() {
		return "UserRedeem [id=" + this.id + ", ettId=" + this.ettId + ", msisdn=" + this.msisdn + ", circle="
				+ this.circle + ", operator=" + this.operator + ", amount=" + this.amount + ", type=" + this.type
				+ ", status=" + this.status + ", vender=" + this.vender + ", redeemType=" + this.redeemType
				+ ", createdTime=" + this.createdTime + ", updatedTime=" + this.updatedTime + ", trans_id="
				+ this.trans_id + ", trans_id_ett=" + this.trans_id_ett + ", fee=" + this.fee + "]";
	}

	public String getVender() {
		return this.vender;
	}

	public void setVender(String paramString) {
		this.vender = paramString;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long paramLong) {
		this.id = paramLong;
	}

	public Long getEttId() {
		return this.ettId;
	}

	public void setEttId(Long paramLong) {
		this.ettId = paramLong;
	}

	public Long getMsisdn() {
		return this.msisdn;
	}

	public void setMsisdn(Long paramLong) {
		this.msisdn = paramLong;
	}

	public String getCircle() {
		return this.circle;
	}

	public void setCircle(String paramString) {
		this.circle = paramString;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String paramString) {
		this.operator = paramString;
	}

	public float getAmount() {
		return this.amount;
	}

	public void setAmount(float paramFloat) {
		this.amount = paramFloat;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String paramString) {
		this.type = paramString;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String paramString) {
		this.status = paramString;
	}

	public Date getCreatedTime() {
		return this.createdTime;
	}

	public RedeemType getRedeemType() {
		return this.redeemType;
	}

	public void setRedeemType(RedeemType paramRedeemType) {
		this.redeemType = paramRedeemType;
	}

	public void setCreatedTime(Date paramDate) {
		this.createdTime = paramDate;
	}

	public Date getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Date paramDate) {
		this.updatedTime = paramDate;
	}

	public String getTrans_id() {
		return this.trans_id;
	}

	public void setTrans_id(String paramString) {
		this.trans_id = paramString;
	}

	public String getTrans_id_ett() {
		return this.trans_id_ett;
	}

	public void setTrans_id_ett(String paramString) {
		this.trans_id_ett = paramString;
	}

	public float getFee() {
		return this.fee;
	}

	public void setFee(float paramFloat) {
		this.fee = paramFloat;
	}

	public float getPostBalance() {
		return this.postBalance;
	}

	public void setPostBalance(float paramFloat) {
		this.postBalance = paramFloat;
	}

	public static enum RedeemType {
		RECHARGE, LOAN, EGV, BROWSEPLAN;
	}
}