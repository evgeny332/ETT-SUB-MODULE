package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Index;

@Entity
public class TodaysOffers implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Index(name="ettId")
	private Long ettId;
	
	@ManyToOne(cascade = CascadeType.DETACH,  fetch= FetchType.EAGER)
    @JoinColumn(name = "offerId", referencedColumnName = "offerId")
	private OfferDetails offerDetails;

	private Date createdTime;

	private Date updatedTime;

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

	public OfferDetails getOfferDetails() {
		return offerDetails;
	}

	public void setOfferDetails(OfferDetails offerDetails) {
		this.offerDetails = offerDetails;
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
