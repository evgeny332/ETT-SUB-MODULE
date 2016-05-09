package com.etxWeb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.etxWeb.service.RuleCheckService;

/*@ComponentScan("com.etxWeb.service")
@Configuration
@EnableAutoConfiguration
*/
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class EarntextApplication {
	
	  
    
	public static void main(String[] args) {
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		SpringApplication.run(EarntextApplication.class, args);
	}
}
