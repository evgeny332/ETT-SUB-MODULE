package com.etxWeb.service;

import java.util.Date;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface RuleCheckService {
	
	ResponseEntity<String> urlCall(String url, int readTimeOut, int connectTimeOut);

	boolean chekDNDHour(int initialHour,int overHour);

	ResponseEntity<String> urlCallPost(String url, Map<String, Object> params);

	String sendRequest(String URL, int readTimeOut, int connectTimeOut);
	
	int sendPOST(String URL,String postParam,int readTimeOut,int connectTimeOut);
	boolean checkValidateDate(long lastValidationDate,int expiryLimit);
	boolean checkRedeemCount(int redeemCount,int limit);

	byte[] compress(String str) throws Exception;
}
