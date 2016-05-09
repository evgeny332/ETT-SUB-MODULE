package com.sms.process;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;

public class SmsManager extends AsyncCompletionHandler {

	private static final Logger log = LoggerFactory.getLogger(SmsManager.class);
	
	Map<String, String> requestMap;
	public SmsManager(Map<String, String> requestMap) {
		this.requestMap = requestMap;
	}

	@Override
	public Object onCompleted(Response response) throws Exception {
		// decrement tps counter
		log.info("response from server="+response.getResponseBody()+" "+requestMap);
		
		return response;
	}

	@Override
	public void onThrowable(Throwable t) {
		log.error("RequestId {} Error in processing request "+requestMap + t.getMessage());
	}
}