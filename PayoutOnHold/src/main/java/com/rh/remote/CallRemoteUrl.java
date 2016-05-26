package com.rh.remote;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class CallRemoteUrl {
	private static Logger LOGGER = LoggerFactory.getLogger(CallRemoteUrl.class);
	//private static Log oLog = LogFactory.getLog(SendGetRequest.class);
	public String sendRequest(String URL,int readTimeOut,int connectTimeOut){
		try {
			long time1 = System.currentTimeMillis();
			URL url = new URL(URL);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setReadTimeout(readTimeOut);
			urlConnection.setConnectTimeout(connectTimeOut);
			BufferedReader in = new BufferedReader(
			new InputStreamReader(urlConnection.getInputStream()));
			String line = "";
			String temp = "";
			while ((line = in.readLine()) != null)
			{
				temp = temp+line;				
			}
			in.close();
			long time2 = System.currentTimeMillis();
			LOGGER.info("URL|"+URL+" |time|"+(time2-time1));
			return temp.toString();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error in  curl"+URL +"  "+e);
			return null;
		}
			
	}
}