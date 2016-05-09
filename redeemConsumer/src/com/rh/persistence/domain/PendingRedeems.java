package com.rh.persistence.domain;

import java.util.Date;

public class PendingRedeems {
	
	private UserRedeem userRedeem;
	private Long id;
	private Long ettId;
	private Long msisdn;
	private String operator;
	private float amount;
	private String circle;
	private String type;
	private String status;
	private String vender;
	private String redeemType;
	private Date createdTime;
	private Date updatedTime;
	private String trans_id;
	private String trans_id_ett;
	private float fee;
	private float postBalance;
	private int count;

	@Override
	public String toString(){
		return "[id=" + this.id + ", ettId=" + this.ettId + ", msisdn=" + this.msisdn + ", circle="
				+ this.circle + ", operator=" + this.operator + ", amount=" + this.amount + ", type=" + this.type
				+ ", status=" + this.status + ", vender=" + this.vender + ", redeemType=" + this.redeemType
				+ ", createdTime=" + this.createdTime + ", updatedTime=" + this.updatedTime + ", trans_id="
				+ this.trans_id + ", trans_id_ett=" + this.trans_id_ett + ", fee=" + this.fee + "]";
	}
	public UserRedeem getUserRedeem() {
		return userRedeem;
	}

	public void setUserRedeem(UserRedeem userRedeem) {
		this.userRedeem = userRedeem;
		setAmount(userRedeem.getAmount());
		setCreatedTime(userRedeem.getCreatedTime());
		setEttId(userRedeem.getEttId());
		setCircle(userRedeem.getCircle());
		setId(userRedeem.getId());
		setFee(userRedeem.getFee());
		setMsisdn(userRedeem.getMsisdn());
		setOperator(userRedeem.getOperator());
		setRedeemType(userRedeem.getRedeemType().toString());
		setStatus(userRedeem.getStatus());
		setTrans_id(userRedeem.getTrans_id());
		setTrans_id_ett(userRedeem.getTrans_id_ett());
		setType(userRedeem.getType());
		setVender(userRedeem.getVender());
		setPostBalance(userRedeem.getPostBalance());
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
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

	public String getVender() {
		return vender;
	}

	public void setVender(String vender) {
		this.vender = vender;
	}

	public String getRedeemType() {
		return redeemType;
	}

	public void setRedeemType(String redeemType2) {
		this.redeemType = redeemType2;
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

	public String getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}

	public String getTrans_id_ett() {
		return trans_id_ett;
	}

	public void setTrans_id_ett(String trans_id_ett) {
		this.trans_id_ett = trans_id_ett;
	}

	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	public float getPostBalance() {
		return postBalance;
	}

	public void setPostBalance(float postBalance) {
		this.postBalance = postBalance;
	}

	
}
