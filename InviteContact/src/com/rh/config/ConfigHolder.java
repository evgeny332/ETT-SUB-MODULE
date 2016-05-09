package com.rh.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class ConfigHolder {
	 private  Properties props;
   public ConfigHolder() throws IOException{
		try{
		System.out.println("loading configuration.....");
		org.springframework.core.io.Resource resource = new ClassPathResource("config.properties");
		this.props = PropertiesLoaderUtils.loadProperties(resource);
		
		
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
public Properties getProps() {
	return props;
}
public void setProps(Properties props) {
	this.props = props;
}
   
   
	
	

	
}