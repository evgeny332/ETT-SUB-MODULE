package com.rh.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class ConfigHolder {
	private Map<String, String> operatorKeyMap;
	private Map<String, String> operatorKeyMapGETAPI;
	private Map<String, Integer> operatorIdMap;
	private Map<String, Integer> circleIdMap;
	private Map<String, Integer> postPaidOperatorIdMap;
	private Map<String, Integer> dthOperatorIdMap;

	private Map<String, String> operatorKeyMapOxygenPrepaid;
	private Map<String, String> operatorKeyMapOxygenPostpaid;
	private Map<String, String> operatorKeyMapOxygenDth;
	private Map<Integer, Integer> validityPriceMapNexgenTV;
	
	private List<Long> barredEttId;

	private Properties props;
	private boolean IS_GETAPI_ENABLE;

	public ConfigHolder() throws IOException {
		try {
			System.out.println("loading configuration.....");
			org.springframework.core.io.Resource resource = new ClassPathResource("config.properties");
			this.props = PropertiesLoaderUtils.loadProperties(resource);
			String[] operators = props.getProperty("OPERATOR").split(",");
			String[] operatorKeys = props.getProperty("OPERATOR_KEY").split(",");
			String[] operatorKeysGETAPI = props.getProperty("OPERATOR_KEY_GETAPI").split(",");
			String[] operatorIds = props.getProperty("OPERATOR_ID").split(",");
			String[] circles = props.getProperty("OPERATOR_CIRCLE").split(",");
			String[] circleIds = props.getProperty("CIRCLE_ID").split(",");
			String[] operatorPostPaid = props.getProperty("OPERATOR_POSTPAID").split(",");
			String[] operatorIdPostPaid = props.getProperty("OPERATOR_ID_POSTPAID").split(",");
			String[] operatorDth = props.getProperty("OPERATOR_DTH").split(",");
			String[] operatorIdDth = props.getProperty("OPERATOR_ID_DTH").split(",");
			String[] operatorKeyOxygenPrepaid = props.getProperty("OPERATOR_KEY_OXYGEN_PREPAID").split(",");
			String[] operatorKeyOxygenPostpaid = props.getProperty("OPERATOR_KEY_OXYGEN_POSTPAID").split(",");
			String[] operatorKeyOxygenDth = props.getProperty("OPERATOR_KEY_OXYGEN_DTH").split(",");
			String[] nexGenValidity = props.getProperty("NEXGENTV_VALIDITY").split(",");
			String[] nexGenPrice = props.getProperty("NEXGENTV_PRICE").split(",");

			operatorKeyMap = new HashMap<String, String>();
			operatorKeyMapGETAPI = new HashMap<String, String>();
			operatorIdMap = new HashMap<>();
			circleIdMap = new HashMap<>();
			

			postPaidOperatorIdMap = new HashMap<>();
			dthOperatorIdMap = new HashMap<>();
			operatorKeyMapOxygenPrepaid = new HashMap<String, String>();
			operatorKeyMapOxygenPostpaid = new HashMap<String, String>();
			operatorKeyMapOxygenDth = new HashMap<String, String>();
			validityPriceMapNexgenTV = new HashMap<Integer, Integer>();

			for (int i = 0; i < operators.length; i++) {
				operatorKeyMap.put(operators[i].toUpperCase(), operatorKeys[i]);
				operatorKeyMapGETAPI.put(operators[i].toUpperCase(), operatorKeysGETAPI[i]);
				operatorIdMap.put(operators[i].toUpperCase(), Integer.valueOf(operatorIds[i]));
				operatorKeyMapOxygenPrepaid.put(operators[i].toUpperCase(), operatorKeyOxygenPrepaid[i]);

			}
			for (int i = 0; i < circles.length; i++) {
				circleIdMap.put(circles[i].toUpperCase(), Integer.valueOf(circleIds[i]));
			}
			for (int i = 0; i < operatorPostPaid.length; i++) {
				postPaidOperatorIdMap.put(operatorPostPaid[i].toUpperCase(), Integer.valueOf(operatorIdPostPaid[i]));
				operatorKeyMapOxygenPostpaid.put(operatorPostPaid[i].toUpperCase(), operatorKeyOxygenPostpaid[i]);
			}
			for (int i = 0; i < operatorDth.length; i++) {
				dthOperatorIdMap.put(operatorDth[i], Integer.valueOf(operatorIdDth[i]));
				operatorKeyMapOxygenDth.put(operatorDth[i], operatorKeyOxygenDth[i]);
			}
			for (int i=0; i < nexGenPrice.length; i++) {
				validityPriceMapNexgenTV.put(Integer.valueOf(nexGenPrice[i]), Integer.valueOf(nexGenValidity[i]));
			}
			
			String I_GA_E = props.getProperty("IS_GETAPI_ENABLE").trim();
			if (I_GA_E.equalsIgnoreCase("TRUE") || I_GA_E.equalsIgnoreCase("YES")) {
				IS_GETAPI_ENABLE = true;
			}

			if (props.getProperty("BARRED_USER_CHECK") != null && props.getProperty("BARRED_USER_ETTID") != null) {
				if (props.getProperty("BARRED_USER_CHECK").equals("true") && !props.getProperty("BARRED_USER_ETTID").equals("")) {
					String[] barredIds = props.getProperty("BARRED_USER_ETTID").split(",");
					barredEttId = new ArrayList<>(barredIds.length);
					for (int i = 0; i < barredIds.length; i++) {
						barredEttId.add(Long.valueOf(barredIds[i]));
					}
					System.out.println("Barred ettIds : " + barredEttId.toString());
				}
			}

			// System.out.println("operatorKeyMap:"+operatorKeyMap);
			// System.out.println("operatorKeyMapGETAPI:"+operatorKeyMapGETAPI);
			// System.out.println("operatorIdMap:"+operatorIdMap);
			// System.out.println("circleIdMap:"+circleIdMap);
			// System.out.println("postPaidOperatorIdMap:"+postPaidOperatorIdMap);
			// System.out.println("dthOperatorIdMap:"+dthOperatorIdMap);
			// System.out.println("operatorKeyMapOxygenPrepaid map:"+operatorKeyMapOxygenPrepaid);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public Map<String, String> getOperatorKeyMap() {
		return operatorKeyMap;
	}

	public String getOperatorKey(String operator) {
		return operatorKeyMap.get(operator);
	}

	public Map<String, String> getOperatorKeyMapGETAPI() {
		return operatorKeyMapGETAPI;
	}

	public String getOperatorKeyGETAPI(String operator) {
		return operatorKeyMapGETAPI.get(operator);
	}

	public Map<String, Integer> getOperatorIdMapI() {
		return operatorIdMap;
	}
	
	public Integer getValidityPriceMapNexgenTV(int amount){
		return validityPriceMapNexgenTV.get(amount);
	}

	public Integer getOperatorId(String operator) {
		return operatorIdMap.get(operator);
	}

	public Integer getPostPaidOperatorId(String operator) {
		return postPaidOperatorIdMap.get(operator);
	}

	public Integer getDthOperatorId(String operator) {
		return dthOperatorIdMap.get(operator);
	}

	public String getOperatorKeyMapOxygenPrepaid(String operator) {
		// System.out.println("[operator=]["+operator+" ["+operatorKeyMapOxygenPrepaid.get(operator)+"]");
		return operatorKeyMapOxygenPrepaid.get(operator.toUpperCase());
	}

	public String getOperatorKeyMapOxygenPostpaid(String operator) {
		return operatorKeyMapOxygenPostpaid.get(operator.toUpperCase());
	}

	public String getOperatorKeyMapOxygenDth(String operator) {
		return operatorKeyMapOxygenDth.get(operator.toUpperCase());
	}

	public Map<String, Integer> getCircleIdMap() {
		return circleIdMap;
	}

	public Integer getCircleId(String circle) {
		return circleIdMap.get(circle);
	}

	public Properties getProperties() {
		return props;
	}

	public boolean getIsGetApi() {
		return IS_GETAPI_ENABLE;
	}

	public boolean getBarredStatus(Long id) {
		if (barredEttId.contains(id)) {
			return true;
		}
		return false;
	}
}