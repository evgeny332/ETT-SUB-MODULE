package com.etxWeb.entity.passive;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="EtxMsgSubmitDetails")
public class EtxMsgSubmitDetails implements Serializable{
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name="ettId")
	private Long ettId;
	
	@Column(name="createdTime")
	private String createdTime;
	
	@Column(name="createdTimeIST")
	private String createdTimeIST;
	
	@Column(name="vendor")
	private String vendor;
	
	@Column(name="msg")
	private String msg;
	
	@Column(name="respCode")
	private String respCode;
	
	@Column(name="status")
	private int status;
	
	@Column(name="msisdn")
	private String msisdn;

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

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedTimeIST() {
		return createdTimeIST;
	}

	public void setCreatedTimeIST(String createdTimeIST) {
		this.createdTimeIST = createdTimeIST;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	@Override
	public String toString() {
		return "EtxMsgSubmitDetails [id=" + id + ", ettId=" + ettId
				+ ", createdTime=" + createdTime + ", createdTimeIST="
				+ createdTimeIST + ", vendor=" + vendor + ", msg=" + msg
				+ ", respCode=" + respCode + ", status=" + status + "]";
	}
	
	

}
