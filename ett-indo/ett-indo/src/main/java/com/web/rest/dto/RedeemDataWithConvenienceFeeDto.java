package com.web.rest.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RedeemDataWithConvenienceFeeDto {

	List<RedeemDataDto> redeemDataDto;
	String convenienceFee;
	String convenienceFeeNew;
	//private float threshold;
	//private float thresholdForIstRedeem;
	
	List <String> prepaidOperator;
	List <String> postpaidOperator;
	List <String> dthOperator;

	public List<RedeemDataDto> getRedeemDataDto() {
		return redeemDataDto;
	}
	public void setRedeemDataDto(List<RedeemDataDto> redeemDataDto) {
		this.redeemDataDto = redeemDataDto;
	}
	public String getConvenienceFee() {
		return convenienceFee;
	}
	public void setConvenienceFee(String convenienceFee) {
		this.convenienceFee = convenienceFee;
	}
	public String getConvenienceFeeNew() {
		return convenienceFeeNew;
	}
	public void setConvenienceFeeNew(String convenienceFeeNew) {
		this.convenienceFeeNew = convenienceFeeNew;
	}
	/*public float getThreshold() {
		return threshold;
	}
	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}
	public float getThresholdForIstRedeem() {
		return thresholdForIstRedeem;
	}
	public void setThresholdForIstRedeem(float thresholdForIstRedeem) {
		this.thresholdForIstRedeem = thresholdForIstRedeem;
	}*/
	public List<String> getPrepaidOperator() {
		return prepaidOperator;
	}
	public void setPrepaidOperator(List<String> prepaidOperator) {
		this.prepaidOperator = prepaidOperator;
	}
	public List<String> getPostpaidOperator() {
		return postpaidOperator;
	}
	public void setPostpaidOperator(List<String> postpaidOperator) {
		this.postpaidOperator = postpaidOperator;
	}
	public List<String> getDthOperator() {
		return dthOperator;
	}
	public void setDthOperator(List<String> dthOperator) {
		this.dthOperator = dthOperator;
	}

	
}
