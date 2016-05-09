package com.web.rest.dto;




import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Ankur
 */
//TODO:: status must have with tags or message
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GiftMsgResp {
   String msg;
   boolean status = true;

public String getMsg() {
	return msg;
}

public void setMsg(String msg) {
	this.msg = msg;
}

public boolean isStatus() {
	return status;
}

public void setStatus(boolean status) {
	this.status = status;
}
   
   
   
}