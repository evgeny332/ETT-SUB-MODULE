package com.web.rest.dto;




import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Ankur
 */
//TODO:: status must have with tags or message
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class OtpDto {
    private Long ettId;
    private boolean isVerified;
    private Integer otpAllocated;
    private String currency;
    private String operator;
    private String countryCode;
    private List<String> operatorsList;
    private List<String> redeemAmountsList;
    
    private boolean isTimerOn;
    private int timer;
    private int tempOtp;
    private boolean isManualOtp;
    
    

    
	public boolean isTimerOn() {
		return isTimerOn;
	}
	public void setTimerOn(boolean isTimerOn) {
		this.isTimerOn = isTimerOn;
	}
	public int getTimer() {
		return timer;
	}
	public void setTimer(int timer) {
		this.timer = timer;
	}
	public int getTempOtp() {
		return tempOtp;
	}
	public void setTempOtp(int tempOtp) {
		this.tempOtp = tempOtp;
	}
	public List<String> getOperatorsList() {
		return operatorsList;
	}
	public void setOperatorsList(List<String> operatorsList) {
		this.operatorsList = operatorsList;
	}
	public List<String> getRedeemAmountsList() {
		return redeemAmountsList;
	}
	public void setRedeemAmountsList(List<String> redeemAmountsList) {
		this.redeemAmountsList = redeemAmountsList;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Long getEttId() {
		return ettId;
	}
	public void setEttId(Long ettId) {
		this.ettId = ettId;
	}
	public boolean isVerified() {
		return isVerified;
	}
	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
	public Integer getOtpAllocated() {
		return otpAllocated;
	}
	public void setOtpAllocated(Integer otpAllocated) {
		this.otpAllocated = otpAllocated;
	}
	public boolean isManualOtp() {
		return isManualOtp;
	}
	public void setManualOtp(boolean isManualOtp) {
		this.isManualOtp = isManualOtp;
	}
    
    
}