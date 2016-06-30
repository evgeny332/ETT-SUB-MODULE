package com.rh.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.rh.entity.JsonFormat;

public class CreateJSONPayload {

	private static final Logger log = Logger.getLogger(CreateJSONPayload.class);

	public String respJSONFormat(JSONArray rJSON) {

		String formattedjson = null;
		List<JsonFormat> list = new ArrayList<JsonFormat>();
		try {
			for (int i = 0; i < rJSON.length(); i++) {
				JsonFormat jf = new JsonFormat();
				
				JSONObject array1 = rJSON.getJSONObject(i);
								
				JSONObject job = array1.getJSONObject("pubContent");
				JSONObject ob1 = job.getJSONObject("AdIcon");

				try {
					jf.setOfferName(job.getString("AdName"));
					jf.setActionUrl(job.getString("Url"));
					jf.setDescription(job.getString("Description"));
					jf.setOfferButtonText(job.getString("cta").toLowerCase());
					jf.setImageUrl(ob1.getString("url"));
					jf.setNamespace(array1.getString("namespace"));
					jf.setScript(array1.getString("script"));
					if(!job.isNull("rating")){
						jf.setRating(Integer.toString(job.getInt("rating")));
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error("Error in Parsing json: ", e);
				}

				list.add(jf);
			}

			Gson json = new Gson();
			formattedjson = json.toJson(list);

//			log.debug("No. of ads: " + rJSON.length());

		} catch (JSONException e) {
			e.printStackTrace();
			log.error("NativeAds | CreateJsonPayLoad| respJSONformat | Error in Formatting Json");
		}
		return formattedjson;
	}

	public String respBannerJSON(String str) {
		str = str.replace("<!--// <![CDATA[", "").replace("// ]]> -->", "");
		Document doc = Jsoup.parse(str);

		Elements cat = doc.getElementsByTag("body");
		String str2 = cat.text().replace("null]", "").replace("[", "");
		Document doc2 = Jsoup.parse(str2);

		Elements cat1 = doc2.select("a");
		String actionUrl = cat1.attr("href");
		String imageUrl = cat1.select("img").attr("src");

		JSONObject jsonObject = new JSONObject();
		
		try {
			jsonObject.put("imageUrl", imageUrl);
			jsonObject.put("landingurl", actionUrl);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("BannerAds | CreateJsonPayLoad| respBannerJSON| Error in formatting json");
		}

		return jsonObject.toString();
	}
	
	public String respBannerJSON1(String str){
		JSONObject jsonObject = new JSONObject();
		
		try {
			jsonObject.put("html", str);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("BannerAds | CreateJsonPayLoad| respBannerJSON| Error in formatting json");
		}
		return jsonObject.toString();
	}
}
