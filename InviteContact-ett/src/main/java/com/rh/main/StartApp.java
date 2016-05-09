package com.rh.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class StartApp 
{
    public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
    	ClassPathXmlApplicationContext r = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
}
