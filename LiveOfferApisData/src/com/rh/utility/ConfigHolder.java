package com.rh.utility;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigHolder {
	public static String urlMyOffers = "";
	public static String urlAvalableOffers = "";
	public static String urlAvalableOffers2 = "";
	public static String clicksMobApi = "";
	public static String ioDisplayApi = "";
	public static String presonaListApi = "";
	public static String Platform = "";
	public static String Country = "";
	public static String incentivizedDescription = "";
	public static String crunchiemediaApi = "";
	public static String MappStreetApi = "";
	public static String Supersonic = "";
	public static String host = "";
	public static String user = "";
	public static String password = "";
	public static String Woobi = "";
	public static String Clicky = "";
	static Logger error = Logger.getLogger(ConfigHolder.class);
	static {
		try {

			Properties prop = new Properties();
			prop.load(ConfigHolder.class.getClassLoader().getResourceAsStream(
					"config.properties"));
			urlMyOffers = prop.getProperty("urlMyOffers");
			urlAvalableOffers = prop.getProperty("urlAvalableOffers");
			urlAvalableOffers2 = prop.getProperty("urlAvalableOffers2");
			clicksMobApi = prop.getProperty("clicksMobApi");
			ioDisplayApi = prop.getProperty("ioDisplayApi");
			presonaListApi = prop.getProperty("presonaListApi");
			Platform = prop.getProperty("Platform");
			Country = prop.getProperty("Country");
			incentivizedDescription = prop
					.getProperty("incentivizedDescription");
			crunchiemediaApi = prop.getProperty("crunchiemediaApi");
			MappStreetApi = prop.getProperty("MappStreetApi");
			Supersonic = prop.getProperty("Supersonic");
			Woobi = prop.getProperty("Woobi");
			Clicky=prop.getProperty("Clicky");
			host = prop.getProperty("host");
			user = prop.getProperty("user");
			password = prop.getProperty("password");

		} catch (IOException ex) {
			error.info("FileNotFoundException : " + ex.getMessage());
		}
	}
}
