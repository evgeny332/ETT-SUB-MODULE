package com.rh.persistence.entity;

import java.util.Date;

public class PayoutHoldData {

	private long id;
	private long offerDetailsOnClickId;
	private long eventDetailsOnClickId;
	private Date createdTime;
	private Date payoutTime;
	private int status;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOfferDetailsOnClickId() {
		return offerDetailsOnClickId;
	}
	public void setOfferDetailsOnClickId(long offerDetailsOnClickId) {
		this.offerDetailsOnClickId = offerDetailsOnClickId;
	}
	public long getEventDetailsOnClickId() {
		return eventDetailsOnClickId;
	}
	public void setEventDetailsOnClickId(long eventDetailsOnClickId) {
		this.eventDetailsOnClickId = eventDetailsOnClickId;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getPayoutTime() {
		return payoutTime;
	}
	public void setPayoutTime(Date payoutTime) {
		this.payoutTime = payoutTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "PayoutHoldData [id=" + id + ", offerDetailsOnClickId=" + offerDetailsOnClickId + ", eventDetailsOnClickId=" + eventDetailsOnClickId + ", createdTime=" + createdTime + ", payoutTime=" + payoutTime + ", status=" + status + "]";
	}
	
	
}
