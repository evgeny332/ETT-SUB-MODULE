package com.rh.push.notifications.server.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: Sanjay
 * Date: 5/4/12
 * Time: 4:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class AppMain {
    public static void main (String a[])
    {
        ApplicationContext context =
                new AnnotationConfigApplicationContext("com.rh.push.notifications.server.spring.root");
    }
}