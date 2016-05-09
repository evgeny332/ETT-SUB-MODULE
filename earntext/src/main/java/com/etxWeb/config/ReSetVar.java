package com.etxWeb.config;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReSetVar implements Runnable {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ReSetVar.class);
	private AtomicInteger errorCheckCounter = new AtomicInteger();
	public ReSetVar(){
		LOGGER.info("Inside the ResetVar Constructor....");
		
		new Thread(this).start();
	}
	
	public void run() {
		try {
			while(true) {
				Thread.sleep(120000);
				if(errorCheckCounter.get()>0){
					LOGGER.info("Resetting the Error Counter:{}",errorCheckCounter.get());
					errorCheckCounter.set(0);
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public AtomicInteger getErrorCheckCounter() {
		return errorCheckCounter;
	}

	public void setErrorCheckCounter(AtomicInteger errorCheckCounter) {
		this.errorCheckCounter = errorCheckCounter;
	}
	
}
