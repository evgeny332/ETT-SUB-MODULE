package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Index;

@Entity
public class DataUsagePendingCredits implements Serializable{
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((offerId == null) ? 0 : offerId.hashCode());
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
		DataUsagePendingCredits other = (DataUsagePendingCredits) obj;
		if (offerId == null) {
			if (other.offerId != null)
				return false;
		} else if (!offerId.equals(other.offerId))
			return false;
		return true;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Index(name = "dataUsage_ettId")
	private Long ettId;
	
	private Long offerId;
	
	private String appKey;
	
	private Date StartedTime;
	
	@PrePersist
	protected void insertValue(){
		StartedTime = new Date();
	}
	
	private Float maxCreditLimit;
	
	private Float maxCreditPerDayLimit;
	
	private Float amountPerDataThreshold;
	
	private int dataThreshold;
	
	private Date payoutEndDate;
	
	private int eligibleStatus;
	
	private Long totalUsedData;

	private String imageUrl;
	
	private String packageName;
	
	private String offerName;
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

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public Date getStartedTime() {
		return StartedTime;
	}

	public void setStartedTime(Date startedTime) {
		StartedTime = startedTime;
	}

	public Float getMaxCreditLimit() {
		return maxCreditLimit;
	}

	public void setMaxCreditLimit(Float maxCreditLimit) {
		this.maxCreditLimit = maxCreditLimit;
	}

	public Float getMaxCreditPerDayLimit() {
		return maxCreditPerDayLimit;
	}

	public void setMaxCreditPerDayLimit(Float maxCreditPerDayLimit) {
		this.maxCreditPerDayLimit = maxCreditPerDayLimit;
	}

	public Float getAmountPerDataThreshold() {
		return amountPerDataThreshold;
	}

	public void setAmountPerDataThreshold(Float amountPerDataThreshold) {
		this.amountPerDataThreshold = amountPerDataThreshold;
	}

	public int getDataThreshold() {
		return dataThreshold;
	}

	public void setDataThreshold(int dataThreshold) {
		this.dataThreshold = dataThreshold;
	}

	public Date getPayoutEndDate() {
		return payoutEndDate;
	}

	public void setPayoutEndDate(Date payoutEndDate) {
		this.payoutEndDate = payoutEndDate;
	}

	public int getEligibleStatus() {
		return eligibleStatus;
	}

	public void setEligibleStatus(Integer eligibleStatus) {
		this.eligibleStatus = eligibleStatus;
	}

	public Long getTotalUsedData() {
		return totalUsedData;
	}

	public void setTotalUsedData(Long totalUsedData) {
		this.totalUsedData = totalUsedData;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setEligibleStatus(int eligibleStatus) {
		this.eligibleStatus = eligibleStatus;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	
	
}
