package com.rh.activityTracking.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	ClassPathXmlApplicationContext r = new ClassPathXmlApplicationContext("applicationContext.xml");
    	System.out.println( "acitvityPayoutStart..." );
    }
}
