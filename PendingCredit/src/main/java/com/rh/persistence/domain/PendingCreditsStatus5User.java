package com.rh.persistence.domain;

import java.util.Date;

public class PendingCreditsStatus5User {

	private String id;
	private Long ettId;
	private Long offerId;
	private float offeredAmount;
	private float givenAmount;
	private Date payoutDate;
	private String appKey;
	
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
		PendingCreditsStatus5User other = (PendingCreditsStatus5User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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
	public Long getOfferId() {
		return offerId;
	}
	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}
	public float getOfferedAmount() {
		return offeredAmount;
	}
	public void setOfferedAmount(float offeredAmount) {
		this.offeredAmount = offeredAmount;
	}
	public float getGivenAmount() {
		return givenAmount;
	}
	public void setGivenAmount(float givenAmount) {
		this.givenAmount = givenAmount;
	}
	public Date getPayoutDate() {
		return payoutDate;
	}
	public void setPayoutDate(Date payoutDate) {
		this.payoutDate = payoutDate;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
	
}
