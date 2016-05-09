package com.rh.edrconsumer.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartApp {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext r = new ClassPathXmlApplicationContext("applicationContext.xml");
		System.out.println("inside main");
	}
}