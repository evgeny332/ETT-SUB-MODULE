package com.rh.remote;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SendGetRequest {
	private static Log oLog = LogFactory.getLog(SendGetRequest.class);
	public String sendRequest(String URL){
		try {
			URL url = new URL(URL);
			System.out.println(new Date()+":send request"+URL);
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.addRequestProperty("User-Agent", "curl/7.19.7 (x86_64-redhat-linux-gnu) libcurl/7.19.7 NSS/3.14.3.0 zlib/1.2.3 libidn/1.18 libssh2/1.4.2");
			//urlConnection.addRequestProperty(key, value);
			urlConnection.setReadTimeout(60000);
			urlConnection.setConnectTimeout(60000);
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
	public String sendRequest(String URL,String postData){
		try {
			URL url = new URL(URL);
			System.out.println(new Date()+":send request"+URL);
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.addRequestProperty("User-Agent", "curl/7.19.7 (x86_64-redhat-linux-gnu) libcurl/7.19.7 NSS/3.14.3.0 zlib/1.2.3 libidn/1.18 libssh2/1.4.2");
			//urlConnection.addRequestProperty(key, value);
			urlConnection.setReadTimeout(50000);
			urlConnection.setConnectTimeout(50000);
			urlConnection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(postData);
            wr.flush();

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
			oLog.error("[Error in  curl]["+URL +"] [postData]["+postData+"]"+e);
			return null;
		}
			
	}

}