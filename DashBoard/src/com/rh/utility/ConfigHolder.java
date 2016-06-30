package com.rh.utility;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigHolder {

	private Map<Integer, String> mymap;
	public String UsaQuery;
	public String OfferDetailsQuery;
	public String CallBackQuery;
	public String RedeemQuery;
	public String RecoCallBackQuery;
	public String AstroQuery;
	public String InstallEdrQuery;
	public String TotalUserQuery;
	public String TotalNotVerifUserQuery;
	public String DayUserQuery;
	public String DayNotVerifUserQuery;
	public String BollywoodQuery;
	
	final static Logger logger = Logger.getLogger(ConfigHolder.class);

//	public static void main(String args[]){
//		
//		boolean is = false;
//		
//		if(!is){
//			System.out.print("Got it");
//		}else{
//			System.out.print("Negative");
//		}
//	}
	
	public ConfigHolder() {
		System.out.println("Inside Offers Config");
		Properties properties = new Properties();
		Properties prop = new Properties();
		try {
			prop.load(ConfigHolder.class.getClassLoader().getResourceAsStream("queries.properties"));
			properties.load(ConfigHolder.class.getClassLoader().getResourceAsStream("offer.properties"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in reading Properties File: ",e);
		}

		BollywoodQuery=prop.getProperty("BollywoodQuery");
		UsaQuery=prop.getProperty("UsaQuery");
		OfferDetailsQuery=prop.getProperty("OfferDetailsQuery");
		CallBackQuery=prop.getProperty("CallBackQuery");
		RedeemQuery=prop.getProperty("RedeemQuery");
		RecoCallBackQuery=prop.getProperty("RecoCallBackQuery");
		AstroQuery=prop.getProperty("AstroQuery");
		InstallEdrQuery=prop.getProperty("InstallEdrQuery");
		TotalUserQuery=prop.getProperty("TotalUserQuery");
		TotalNotVerifUserQuery=prop.getProperty("TotalNotVerifUserQuery");
		DayUserQuery=prop.getProperty("DayUserQuery");
		DayNotVerifUserQuery=prop.getProperty("DayNotVerifUserQuery");
				
		try {
			mymap = new HashMap<Integer, String>();
			for (String key : properties.stringPropertyNames()) {
				String value = properties.getProperty(key);
				mymap.put(Integer.parseInt(key), value);
				System.out.print("Key:" + key + " ");
				System.out.println("Value:" + mymap.get(Integer.parseInt(key)));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in HashMap Properties File: ",e);
		}
	}

	public Map<Integer, String> getMymap() {
		return mymap;
	}

	public void setMymap(Map<Integer, String> mymap) {
		this.mymap = mymap;
	}

	public boolean checkConfigOffers(int i) {

		try {
			if (mymap.containsKey(i)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in checkConfigOffers: ",e);
		}
		return false;
	}
}
