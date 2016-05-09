package com.rh.remote;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SendGetRequest {
	private static Log oLog = LogFactory.getLog(SendGetRequest.class);
	public String sendRequest(String URL,int readTimeOut,int connectTimeOut){
		try {
			URL url = new URL(URL);
			System.out.println(new Date()+":send request"+URL);
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
			System.out.println(new Date()+"|reqst="+URL+"resp="+temp.toString());
			return temp.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			oLog.error("Error in  curl"+URL +"  "+e);
			return null;
		}
			
	}
}