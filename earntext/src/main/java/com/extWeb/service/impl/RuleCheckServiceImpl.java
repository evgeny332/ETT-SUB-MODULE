package com.extWeb.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.RestTemplate;

import com.etxWeb.Controller.InitialCheckController;
import com.etxWeb.service.RuleCheckService;

@Component
public class RuleCheckServiceImpl{

	private static Logger LOGGER = LoggerFactory.getLogger(RuleCheckServiceImpl.class);
	public ResponseEntity<String> urlCall(String url, int readTimeOut, int connectTimeOut) {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
			return response;
	}
	
	public ResponseEntity<String> urlCallPost(String url,Map<String, Object> params) {
			RestTemplate restTemplate = new RestTemplate();
			//ResponseEntity<String> response = restTemplate.getForEntity(url,String.class,params);
			final HttpHeaders headers = new HttpHeaders();
		      //headers.setContentType(MediaType.APPLICATION_JSON);
			ResponseEntity<String> response = 	restTemplate.postForEntity(url, headers, String.class,params);
			return response;
	}
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
                LOGGER.info("URL|"+URL+" |resp|"+temp.toString()+" |time|"+(time2-time1));
                return temp.toString();
        } catch (Exception e) {
                e.printStackTrace();
               LOGGER.error("Error in  curl"+URL +"  "+e);
                return null;
        }
	}	
	
	public  int sendPOST(String URL,String postParam,int readTimeOut,int connectTimeOut){
		int responseCode=0;
		try {
			URL obj = new URL(URL);
			System.out.println(URL+postParam);
	        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	        con.setRequestMethod("POST");
	        con.setDoOutput(true);
	        OutputStream os = con.getOutputStream();
	        os.write(postParam.getBytes());
	        os.flush();
	        os.close();
	        // For POST only - END
	        
	        responseCode = con.getResponseCode();
	        //System.out.println("POST Response Code :: " + responseCode);
	 
	       /* if (responseCode == HttpURLConnection.HTTP_OK) { //success
	            BufferedReader in = new BufferedReader(new InputStreamReader(
	                    con.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	 
	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();
	 
	            // print result
	            System.out.println(response.toString());
	        } else {
	            System.out.println("POST request not worked");
	        }*/
		}catch(Exception ex) {
			LOGGER.error("Error in calling post url url :"+URL+"?"+postParam);
			ex.printStackTrace();
		}
		return responseCode;
	}
	
	
	public boolean chekDNDHour(int initialHour,int overHour){
		int currentHour = getCurrentHour();
		//System.out.println("currentHour:"+currentHour);
		boolean dndHour = false;
		if(currentHour<initialHour) {
			dndHour=true;
		}
		else if(currentHour>=overHour) {
			dndHour = true;
		}
		return dndHour;
	}
	
	public int getCurrentHour(){
		Date date = new Date();
		date.setTime(date.getTime()+19800000);
		return date.getHours();
	}

	public boolean checkValidateDate(long lastValidationDate, int expiryLimit) {
		// TODO Auto-generated method stub
		if(lastValidationDate==0l) {
			return false;
		}
		if(dayDiffIST(new Date(lastValidationDate), new Date())>expiryLimit) {
			return false;
		}
		return true;
	}
	public int dayDiffIST(Date date1,Date date2) {
		int  timezone = 19800;
		date1.setTime(date1.getTime()+timezone*1000);
        date2.setTime(date2.getTime()+timezone*1000);
        date1.setHours(0);
        date1.setMinutes(0);
        date1.setSeconds(0);
        date2.setHours(0);
        date2.setMinutes(0);
        date2.setSeconds(0);
        long sameDay1 = date1.getTime()-date2.getTime();
        //LOGGER.info("nowTime={},userCT={}",date1,date2);
        int sameDay = (int)(sameDay1/(1000*60*60*24));
        System.out.println("day diff:-"+sameDay);
        return sameDay;
	}
	public boolean checkRedeemCount(int redeemCount, int limit) {
		// TODO Auto-generated method stub
		if(redeemCount>=limit) {
			return true;
		}
		return false;
	}
	
	public  byte[] compress(String str) throws Exception {
        if (str == null || str.length() == 0) {
            return str.getBytes(Charset.forName("UTF-8"));
        }
        //System.out.println("String length : " + str.length());
        ByteArrayOutputStream obj=new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(obj);
        //byte[] b = obj.getBytes(Charset.forName("UTF-8"));
        gzip.write(str.getBytes("UTF-8"));
        gzip.close();
        //String outStr = obj.toString("UTF-8");
        //System.out.println("Output String length : " + gzip.length());
        return obj.toByteArray();
     }
}
