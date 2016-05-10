package com.rh.api;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.base.Joiner;
import com.rh.bean.ApiOfferDataBean;
import com.rh.utility.ConfigHolder;
import com.rh.utility.ReadUrl;

public class ClickyApiParsing {
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
	static String vandorName = "Clicky";
	static String playstoreurl = "NA";
	static String platform = "NA";
	static String countries = "IN";
	static String id = "NA";
	static String tags = "NA";

	static HashMap<String, String> hm = new HashMap<String, String>();

	private static String extradata = "NA";
	static int isIncent = 3;
	static Logger logger = Logger.getLogger(ClickyApiParsing.class);
	private static String free;
	private static String type;
	private static String leadtype;
	private static String category;
	private static String capsdailyremaining;
	private static String capstotalremaining;
	private static String avgcr;
	private static String rpc;
	private static String votes;
	private static String average;

	public static void clickyUrlParsing(ReadUrl readUrl) {
		logger.info("START: ");
		try {
			String url = ConfigHolder.Clicky;
			result = readUrl.readURL(url);
			logger.info("Clicky:" + url);
			parseJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("logger Message Clicky URL" + e);
		}
	}

	public static void parseJson(String result) {
		try {

			JSONObject jsonobj = new JSONObject(result);
			JSONArray jsonArray = jsonobj.getJSONArray("offers");
			int count = jsonArray.length();
			for (int i = 0; i < count; i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject platformobj = jsonObject.getJSONObject("targeting");
				JSONObject platformObj = platformobj.getJSONObject("allowed");
				String platform1 = platformObj.getJSONArray("os").toString();
				if (ConfigHolder.Platform.equalsIgnoreCase("false")) {
					if (platform1.equalsIgnoreCase("android")) {
						String platform0 = platform1.replace("[\"", "");
						platform = platform0.replace("\"]", "");
					}
				} else if (platform1.contains("Android")
						|| platform1.equalsIgnoreCase("ios")) {

					String platform0 = platform1.replace("[\"", "");
					platform = platform0.replace("\"]", "");

					parseData(jsonObject, platformObj);
				}
			}
			logger.info("done");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error Message Clicky Parsing JSON:" + e);
		}

	}

	public static void parseData(JSONObject jsonObject, JSONObject platformObj) {
		try {
			countries = platformObj.getJSONArray("countries").toString();
			if (countries.contains("IN") || countries.contains("ID")) {
				offerName = jsonObject.getString("name");
				String Description1 = jsonObject.getString("description");
				String Description2 = Description1.replace("\\n", "");
				String Description3 = Description2.replace("\"", "");
				Description = Description3.replace(" ", "");
				payout = jsonObject.getDouble("payout");
				payoutmode = jsonObject.getString("offer_model");
				
				  countries = countries.replaceAll("[\\[\\]]", "");
				countries = countries.replace("\"", "");
				imageurl = jsonObject.getString("icon");
				playstoreurl = jsonObject.getString("target_url");
				packagename = jsonObject.getString("app_id");
				actionurl = jsonObject.getString("link");
				dailycap = jsonObject.getString("caps_daily");
				totalcap = jsonObject.getString("caps_daily");
				if (jsonObject.getString("traffic_type").equalsIgnoreCase(
						"non incentive")) {
					isIncent = 0;
				} else if (jsonObject.getString("traffic_type")
						.equalsIgnoreCase("incentive")) {
					isIncent = 1;
				}

				id = jsonObject.getString("offer_id");
				free = jsonObject.getString("free");
				type = jsonObject.getString("type");
				leadtype = jsonObject.getString("lead_type");
				category = jsonObject.getJSONArray("category").toString();
				capsdailyremaining = jsonObject
						.getString("caps_daily_remaining");
				capstotalremaining = jsonObject
						.getString("caps_total_remaining");
				avgcr = jsonObject.getString("avg_cr");
				rpc = jsonObject.getString("rpc");
				JSONObject jobj = jsonObject.getJSONObject("rating");
				votes = jobj.getString("votes");
				average = jobj.getString("average");

				hm.put("id", id);
				hm.put("free", free);
				hm.put("type", type);
				hm.put("leadtype", leadtype);
				hm.put("category", category);
				hm.put("capsdailyremaining", capsdailyremaining);
				hm.put("capstotalremaining", capstotalremaining);
				hm.put("avgcr", avgcr);
				hm.put("rpc", rpc);
				hm.put("votes", votes);
				hm.put("average", average);
				extradata = Joiner.on("^|").withKeyValueSeparator("~>")
						.join(hm);

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
				bean.setExtraData(extradata);
				bean.setPlatform(platform);
				bean.setIsIncent(isIncent);
				ApiOfferDataBean.insertIntoDB();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("logger Message Clicky Parsing Data:" + e);
		}
	}

}
