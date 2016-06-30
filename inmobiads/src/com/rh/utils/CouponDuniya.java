package com.rh.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

public class CouponDuniya {

	/*public static void main(String[] args) {

		String Url = null;
		String pi = null;
		String api_key = null;
		String response = "";
		CouponDuniya cd = new CouponDuniya();

		try {
			Properties prop = new Properties();
			prop.load(CouponDuniya.class.getClassLoader().getResourceAsStream("coupon.properties"));

			Url = prop.getProperty("Url");
			pi = prop.getProperty("clientid");
			api_key = prop.getProperty("api_key");

		} catch (IOException e) {
			e.printStackTrace();
		}

		long ss = cd.getApiTimestamp();
		// System.out.println("Windows Timestamp: " +
		// System.currentTimeMillis());
		long ts = System.currentTimeMillis() / 1000L;
		ts = ts + (ts - ss);
		System.out.println("MyTimestamp: "+ts);

		try {

			String queryString = "pi=" + pi + "&ts=" +ts;
			String cs = "&cs=" + URLEncoder.encode(cd.getMD5(api_key+queryString), "UTF-8");
			queryString = "pi=" + URLEncoder.encode(pi, "UTF-8") + "&ts=" + URLEncoder.encode(Long.toString(ts), "UTF-8");
			String PostBody = queryString+cs;
			
			System.out.println("url | " + Url + PostBody);

			URL url = new URL(Url + "?" + PostBody);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			// con.setDoOutput(true);
			con.setDoInput(true);
			// con.setRequestProperty("Content-Type",
			// "application/x-www-form-urlencoded");
			con.setRequestMethod("GET");

			
			 * OutputStream os = con.getOutputStream(); BufferedWriter writer =
			 * new BufferedWriter(new OutputStreamWriter(os));
			 * writer.write(PostBody); writer.flush(); writer.close();
			 * os.close();
			 

			int responseCode = con.getResponseCode();
			System.out.println(responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) {
				String line;
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				while ((line = br.readLine()) != null) {
					response = line;
				}
			} else {
				String line;

				BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				while ((line = br.readLine()) != null) {
					response += line;
				}
			}
			System.out.println(response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	public String getMD5(String raw) {

		String checksum = null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(raw.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			
			checksum = sb.toString();
			System.out.println("CheckSum: " +checksum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checksum;
	}

	public long getApiTimestamp() {

		long apits = 0;
		try {

			String response = null;

			URL url = new URL("http://couponduniya.in/api/date");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);

			int responseCode = con.getResponseCode();
			System.out.println(responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) {
				String line;
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				while ((line = br.readLine()) != null) {
					response = line;
				}
			}

			JSONObject obj = new JSONObject(response);
			JSONObject obj1 = obj.getJSONObject("data");

			// System.out.println("Response: " + response);
			apits = obj1.getLong("timestamp");
			System.out.println("Timestamp: " + apits);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return apits;
	}
	
	public static void main(String[] args) {
		
		QueryParameters("&income=upto+260%2C000&marital=married&ip=&gender=male&advertisingId=89041f63-ce78-43cb-a416-551856e2acc4&yob=1978&otp=392484&ettId=19654389&tempParam=test1");
		
	}
	
	public static void QueryParameters(String data) {

		Map<String, String> map = null;
		try {
			map = new HashMap<String, String>();
			for (String pair : data.split("&")) {
				if(pair != null && !pair.equals("")){
					String[] kv = pair.split("=");
					if(kv[0].equalsIgnoreCase("income")){
						System.out.println("Heya");
						System.out.println(kv[0]);
						
						String tempIncome = URLDecoder.decode(kv[1],"utf-8");
						System.out.println(tempIncome);
						map.put(kv[0], tempIncome);
					}else if(kv[0].equalsIgnoreCase("ip")){
						
					}else{
						System.out.println("Heya");
						System.out.println(kv[0]);
						System.out.println(kv[1]);
						map.put(kv[0], kv[1]);
						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
//			log.error("data function Error in map|" +"data ["+data, e);
		}
	}
}
