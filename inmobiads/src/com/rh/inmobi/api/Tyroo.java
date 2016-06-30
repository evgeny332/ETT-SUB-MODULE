package com.rh.inmobi.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Path("tyroo/")
public class Tyroo {
	
	private String hashCode;
	private String deviceX;
	private String deviceY;
	private String gaid;
	private String size;
	private String ettId;
//	private String gender;
	private boolean isValid;
	
	private final static Logger log = Logger.getLogger(Tyroo.class);

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAds(@Context UriInfo info, @Context HttpServletRequest httpServletRequest, String data) {

		System.out.println("Request: " + data);
		System.out.println("HttpRequest: " + httpServletRequest);
		QueryParams(data, httpServletRequest);
		
		String resp = null;
		if(isValid){
			resp = getTyrooAds();
		}
		
		if (resp != null) {
			if(resp.equalsIgnoreCase("{\"dto\":[0]}")){
				
				return Response.status(204).entity("No Content").build();
				
			}
			return Response.ok(resp).build();
		}
		return Response.status(204).entity("No Content").build();
	}
	
	private void QueryParams(String data, HttpServletRequest request){
		
		Map<String, String> map = null;
		
		isValid = false;
		try {
			map = new HashMap<String, String>();
			for (String pair : data.split("&")) {
				if(pair != null && !pair.equals("")){
					String[] kv = pair.split("=");
					map.put(kv[0], kv[1]);
				}
			}
		
			Properties prop = new Properties();
			
			try {
				
				prop.load(Tyroo.class.getClassLoader().getResourceAsStream("config.properties"));
				
				hashCode = prop.getProperty("TYROO_HASHCODE");
				size = prop.getProperty("TYROO_AD_SIZE");
				
				if (map.containsKey("deviceX")) {
					deviceX = map.get("deviceX");
				}
				if (map.containsKey("deviceY")) {
					deviceY = map.get("deviceY");
				}
				if (map.containsKey("gaid")) {
					gaid = map.get("gaid");
				}
				/*if (map.containsKey("gender")) {
					
				}*/
				if (map.containsKey("ettId")) {
					ettId = map.get("ettId");
					isValid = true;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				isValid = false;
			}
		
			log.info("Tyroo Parameters [ ettId="+ ettId + " | hashCode="+ hashCode+" | size="+size+" | deviceX="+deviceY+" | deviceY="+deviceY+" | gaid="+gaid+" ]");
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("data function Error in map|" +"data "+data, e);
		}
		
		
	}

	private String getTyrooAds() {

		try {

			String url = "http://api.tyroocentral.com/www/api/v3/API.php?requestParams=" + URLEncoder.encode(createJson(), "utf-8");
			String resp = ParseJson(sendRequest(url));
			
			return resp;

		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private String createJson() {

		JsonObject json = new JsonObject();
		JsonArray adsarray = new JsonArray();
		JsonArray excludedCreatives = new JsonArray();

		JsonObject object = new JsonObject();
		JsonObject object2 = new JsonObject();
		object2.addProperty("active", true);
		object2.addProperty("country", "IN");
		object.add("geo", object2);
		
		JsonObject ads = new JsonObject();
		ads.add("excludedCreatives", excludedCreatives);
		ads.addProperty("adViewId", "smartAdwall");
		ads.addProperty("isAdWall", "true");
		ads.addProperty("sendRepeat", true);
		ads.addProperty("size", size);
		ads.addProperty("startIndex", "0");
		
		adsarray.add(ads);
		json.add("ads", adsarray);
		json.addProperty("apiVersion", "3");
		json.addProperty("deviceLanguage", "en");
		json.addProperty("deviceX", deviceX);
		json.addProperty("deviceY", deviceY);
//		json.addProperty("directImageUrl", "1");
		json.addProperty("gaid", gaid);
		json.addProperty("hashCode", hashCode);
		json.addProperty("isMobile", "true");
		json.add("specificTargetting", object);
		json.addProperty("requestSource", "PUBLIC");

		System.out.println("jsonCreated: "+json.toString());
		return json.toString();
	}

	private String ParseJson(String json) throws JSONException {

		System.out.println("Json=" + json);
		
		JsonObject resp = new JsonObject();
		JSONObject object = new JSONObject(json);
		
		boolean success = object.getBoolean("success");
		
		if(success){
			JSONArray array = object.getJSONArray("updatelist");
			JsonArray respArray = new JsonArray();
			for(int i=0; i< array.length(); i++ ){
				
				JsonObject respObject = new JsonObject();
				
				JSONObject object2 = array.getJSONObject(i);
				JSONObject imgObject = object2.getJSONObject("imageattributes");
				JSONObject addwallObject = object2.getJSONObject("adwallproductattributes");
				String name = addwallObject.getString("name");
//				String title = addwallObject.getString("title");
				String description = addwallObject.getString("description");
				String imageurl = imgObject.getString("imageurl");
				String targeturl = object2.getString("targeturl");
				String offertype = object2.getString("offertype");
				String actualPrice = addwallObject.getString("actualprice");
				int discount = 0;
				if(!addwallObject.getString("discountpercent").equals("")){
					discount = Integer.parseInt(addwallObject.getString("discountpercent"));
				}
				
				if(name.length()>40){
					name = name.substring(0,40) + "...";
				}
//				respObject.addProperty("rating", name);
				respObject.addProperty("offerName", name);
				respObject.addProperty("description", description);
				respObject.addProperty("imageUrl", imageurl);
				respObject.addProperty("actionUrl", targeturl);
				if(discount != 0){
					respObject.addProperty("offerType", discount + "% off");
				}else if (!actualPrice.equals("0") && !actualPrice.equals("")){
					respObject.addProperty("offerType", "Rs. "+actualPrice);
				}else {
					respObject.addProperty("offerType", offertype);
				}
				
				respObject.addProperty("offerButtonText", "buy now");
				
				respArray.add(respObject);
			}
			
			resp.add("dto", respArray);
			log.info("Tyroo Ads served [ count="+array.length()+" | ettId="+ettId+" ]");
		}
		
		System.out.println(success);
		
		return resp.toString();
	}

	public String sendRequest(String URL) {
		try {
			URL url = new URL(URL);
			System.out.println(new Date() + ":send request" + URL);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.setReadTimeout(50000);
			urlConnection.setConnectTimeout(50000);
			urlConnection.setRequestMethod("POST");

			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line = "";
			StringBuilder temp = new StringBuilder();
			while ((line = in.readLine()) != null) {
				temp.append(line);
			}
			in.close();

			log.info("|request=" + URL + "| resp=" + temp.toString());
			System.out.println(new Date() + "|request=" + URL + " | resp=" + temp.toString());

			return temp.toString();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("[Error in  curl][" + URL + "]" + e);
			return null;
		}
	}
}