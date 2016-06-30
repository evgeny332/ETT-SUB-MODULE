package com.rh.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartHandler {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("timeoutContext.xml");
		
		TimeoutHandler handler = (TimeoutHandler) context.getBean("timeoutHandler");
		System.out.println("Starting timeout Handler");
		handler.handler();
	}
}
