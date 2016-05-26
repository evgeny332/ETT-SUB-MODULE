package com.rh.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        APIImpl apiImpl = (APIImpl) context.getBean("restAPI");
        apiImpl.holdPayOut();
    }
}
