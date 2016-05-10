package com.rh.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.base.Joiner;
import com.rh.bean.ApiOfferDataBean;
import com.rh.utility.ConfigHolder;
import com.rh.utility.ReadUrl;

public class PersonListApiParser {
	ArrayList<String> listdata = new ArrayList<String>();
	static ApiOfferDataBean bean = new ApiOfferDataBean();
	static String result = "NA";
	static String offerName = "NA";
	static String Description = "NA";
	static String payoutmode = "NA";
	static Double payout = 0.0;
	static String totalcap = "NA";
	static String dailycap = "NA";
	static String payoutCurrency = "USD";
	static String imageurl = "NA";
	static String actionurl = "NA";
	static String packagename = "NA";
	static int status = 0;
	static String vandorName = "Persona";
	static String playstoreurl = "NA";

	static String countries = "IN";
	static String id = "NA";
	static String tags = "NA";
	static HashMap<String, String> hm = new HashMap<String, String>();

	private static String extradata = "NA";
	static int isIncent = 1;
	static String platform = "NA";
	static Logger error = Logger.getLogger("error");
	static Logger logger = Logger.getLogger("DATA");

	public static void personaList(ReadUrl readUrl) {
		try {
			String url = ConfigHolder.presonaListApi;
			result = readUrl.readURL(url);
			logger.info("PersonList:"+url);
			parseJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			error.info("Error Message PersonList URL"+e);
		}
	}

	public static void parseJson(String result) {
		try {

			JSONObject jsonobj = new JSONObject(result);
			JSONArray jsonArray = jsonobj.getJSONArray("offers");
			int count = jsonArray.length();
			for (int i = 0; i < count; i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				List<String> c1 = new ArrayList<String>();
				JSONArray platforms = jsonObject.getJSONArray("platforms");
				for (int j = 0; j < platforms.length(); j++) {
					JSONObject jb = platforms.getJSONObject(j);
					c1.add(jb.getString("name"));
				}
				String platform1 = c1.toString();
				if ((ConfigHolder.Platform).equalsIgnoreCase("false")) {
					if (c1.contains("Android")) {
						platform = platform1.replaceAll("[\\[\"\\]]", "");
						parseData(jsonObject);
					}
				} else {
					if (c1.contains("Android") || c1.contains("iPhone")) {
						platform = platform1.replaceAll("[\\[\"\\]]", "");
						parseData(jsonObject);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			error.info("Error Message PersonList Parsing JSON:"+e);
		}
		

	}

	public static void parseData(JSONObject jsonObject) {
		try{
		offerName = jsonObject.getString("name");
		String Description1 = jsonObject.getString("description");
		Description = Description1.replaceAll("\\<.*?>", "");
		payout = jsonObject.getDouble("payment");
		
		if(jsonObject.has("model")){
			payoutmode = jsonObject.getString("model");
		}
		if (jsonObject.has("store_app_ids")) {

			JSONArray packagename1 = jsonObject.getJSONArray("store_app_ids");
			for (int j = 0; j < packagename1.length(); j++) {
				packagename = packagename1.getJSONObject(j).getString("app_id");

			}

		}

		actionurl = jsonObject.getString("url");
		JSONArray imageurl1 = jsonObject.getJSONArray("banners");
		for (int k = 0; k < imageurl1.length(); k++) {
			imageurl = imageurl1.getJSONObject(k).getString("url");

		}
//		payoutCurrency = jsonObject.getString("currency_short_name");
		id = jsonObject.getString("id");

		hm.put("id", id);
		hm.put("guidelines", jsonObject.getString("guidelines"));
		// String extradata1 = hm.toString();
		// String extradata2=extradata1.replace("{", "");
		extradata = Joiner.on("^|").withKeyValueSeparator("~>").join(hm);

		bean.setOfferName(offerName);
		bean.setDescription(Description);
		bean.setPayout(payout);
		bean.setPackageName(packagename);
		bean.setImageUrl(imageurl);
		bean.setDailycap(dailycap);
		bean.setTotalcap(totalcap);
		bean.setCountries(countries);
		bean.setVendorName(vandorName);
		bean.setCurrency(payoutCurrency);
		bean.setMode(payoutmode);
		bean.setActionUrl(actionurl);
		bean.setPalystoreUrl(playstoreurl);
		bean.setStatus(status);
		bean.setIsIncent(isIncent);
		bean.setPlatform(platform);
		bean.setExtraData(extradata);
//		logger.info(" offerName=" + offerName + " Description=" + Description + " Payout=" + payout + " packageName=" + packagename + " imageurl=" + imageurl + " dailycap=" + dailycap + " totalcap=" + totalcap + " country=" + countries + " vandorName=" + vandorName + " Currency=" + payoutCurrency + " payoutMode=" + payoutmode + " actionUrl=" + actionurl + " playstoreUrl=" + playstoreurl + " status=" + status + " isIncent=" + isIncent + " extraData=" + extradata + " platform=" + platform);

		ApiOfferDataBean.insertIntoDB();
	}catch(Exception e){
		e.printStackTrace();
		error.info("Error Message PersonList Parsing Data:"+e);
	}
	}
}
