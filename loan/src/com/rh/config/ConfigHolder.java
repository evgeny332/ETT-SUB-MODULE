package com.rh.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class ConfigHolder {
	private Map<String,String> operatorKeyMap;
	private Map<String,Integer> operatorIdMap;
	private Map<String,Integer> circleIdMap;
	
	private  Properties props;
    private float balanceElligibilityForLoan;
    
	public ConfigHolder() throws IOException{
		System.out.println("loading config.....");
		org.springframework.core.io.Resource resource = new ClassPathResource("config.properties");
		this.props = PropertiesLoaderUtils.loadProperties(resource);
		String[] operators = props.getProperty("OPERATOR").split(",");
		String[] operatorKeys = props.getProperty("OPERATOR_KEY").split(",");
		String[] operatorIds = props.getProperty("OPERATOR_ID").split(",");
		String[] circles = props.getProperty("OPERATOR_CIRCLE").split(",");
		String[] circleIds =  props.getProperty("CIRCLE_ID").split(",");
		operatorKeyMap = new HashMap<String, String>();
		operatorIdMap = new HashMap<>();
		circleIdMap = new HashMap<>();
		for(int i=0; i<operators.length; i++){
			operatorKeyMap.put(operators[i], operatorKeys[i]);
			operatorIdMap.put(operators[i], Integer.valueOf(operatorIds[i]));
			circleIdMap.put(circles[i], Integer.valueOf(circleIds[i]));
		}
		System.out.println("operatorKeyMap:"+operatorKeyMap);
		System.out.println("operatorIdMap:"+operatorIdMap);
		System.out.println("circleIdMap:"+circleIdMap);
		balanceElligibilityForLoan = Float.valueOf(props.getProperty("BalanceElligibilityForLoan"));
		System.out.println("balanceElligibilityForLoan="+balanceElligibilityForLoan);
	}
	
	

	public float getBalanceElligibilityForLoan() {
		return balanceElligibilityForLoan;
	}



	public void setBalanceElligibilityForLoan(float balanceElligibilityForLoan) {
		this.balanceElligibilityForLoan = balanceElligibilityForLoan;
	}



	public Map<String,String> getOperatorKeyMap(){
		return operatorKeyMap;
	}
	

	public String getOperatorKey(String operator){
		return operatorKeyMap.get(operator);
	}
	

	public Map<String,Integer> getOperatorIdMap(){
		return operatorIdMap;
	}
	

	public Integer getOperatorId(String operator){
		return operatorIdMap.get(operator);
	}

	public Map<String,Integer> getCircleIdMap(){
		return circleIdMap;
	}
	

	public Integer getCircleId(String circle){
		return circleIdMap.get(circle);
	}

	public Properties getProperties(){
		return props;
	}
	
}