package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;


@Entity
public class Feedback implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long ettId;

    private String feedbackType;

    private String feedback;
    
    private String rate;
    
    private boolean status;

    private Date createdTime;
    
    private String name;
    
    private String email;
    
    private String msisdn;

    @PrePersist
    protected void updateDates() {
        if (createdTime == null) {
            createdTime = new Date();
        }
    }

    
    
    
    
    
    
	public boolean isStatus() {
		return status;
	}







	public void setStatus(boolean status) {
		this.status = status;
	}







	public String getRate() {
		return rate;
	}




	public void setRate(String rate) {
		this.rate = rate;
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

	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}







	public String getName() {
		return name;
	}







	public void setName(String name) {
		this.name = name;
	}







	public String getEmail() {
		return email;
	}







	public void setEmail(String email) {
		this.email = email;
	}







	public String getMsisdn() {
		return msisdn;
	}







	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	
	
	
}
