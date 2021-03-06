package com.web.rest.dto;




import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Ankur
 */
//TODO:: status must have with tags or message
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RedeemDataDto {
   String Amount;
   String fee;
   String type;
   String threshold;
   String thresholdMain;
   List<DTHDetails> dthDetails;
   
   String minThreashHoldGift;
   String maxThreashHoldGift;
   String maxThreashHoldMsgGift;
   List <String> operatorList;
   
   
public String getAmount() {
	return Amount;
}

public void setAmount(String amount) {
	Amount = amount;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public List<DTHDetails> getDthDetails() {
	return dthDetails;
}

public void setDthDetails(List<DTHDetails> dthDetails) {
	this.dthDetails = dthDetails;
}

public String getFee() {
	return fee;
}

public void setFee(String fee) {
	this.fee = fee;
}

public String getMinThreashHoldGift() {
	return minThreashHoldGift;
}

public void setMinThreashHoldGift(String minThreashHoldGift) {
	this.minThreashHoldGift = minThreashHoldGift;
}

public String getMaxThreashHoldGift() {
	return maxThreashHoldGift;
}

public void setMaxThreashHoldGift(String maxThreashHoldGift) {
	this.maxThreashHoldGift = maxThreashHoldGift;
}

public String getMaxThreashHoldMsgGift() {
	return maxThreashHoldMsgGift;
}

public void setMaxThreashHoldMsgGift(String maxThreashHoldMsgGift) {
	this.maxThreashHoldMsgGift = maxThreashHoldMsgGift;
}

public String getThreshold() {
	return threshold;
}

public void setThreshold(String threshold) {
	this.threshold = threshold;
}

public String getThresholdMain() {
	return thresholdMain;
}

public void setThresholdMain(String thresholdMain) {
	this.thresholdMain = thresholdMain;
}

public List<String> getOperatorList() {
	return operatorList;
}

public void setOperatorList(List<String> operatorList) {
	this.operatorList = operatorList;
}



   
}