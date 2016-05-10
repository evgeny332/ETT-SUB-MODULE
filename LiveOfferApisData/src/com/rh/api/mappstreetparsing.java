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

public class mappstreetparsing {

	ArrayList<String> listdata = new ArrayList<String>();
	static ApiOfferDataBean bean = new ApiOfferDataBean();
	static String result = "NA";
	static String offerName = "NA";
	static String Description = "NA";
	static String payoutmode = "NA";
	static Double payout = 0.0;
	static String totalcap = "NA";
	static String dailycap = "NA";
	static String payoutCurrency = "NA";
	static String imageurl = "NA";
	static String actionurl = "NA";
	static String packagename = "NA";
	static int status = 0;
	static String vandorName = "MappStreet";
	static String playstoreurl = "NA";

	static String countries = "IN";
	static String id = "NA";
	static String tags = "NA";
	static HashMap<String, String> hm = new HashMap<String, String>();
	static Logger error = Logger.getLogger("error");
	static Logger logger = Logger.getLogger("DATA");
	private static String extradata = "NA";
	static int isIncent = 3;
	static String platform = "NA";

	public static void MappStreet(ReadUrl readUrl) {

		try {

			String url = ConfigHolder.MappStreetApi;
			result = readUrl.readURL(url);
			logger.info(" MappStreet Url" + url);
			System.out.println(" MappStreet Url" + url);
			parseJson(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void parseJson(String result) {
		try {

			JSONObject jsonobj = new JSONObject(result);
			JSONObject jsonobject1 = jsonobj.getJSONObject("response");
			System.out.print(result);
			JSONArray jsonArray = jsonobject1.getJSONArray("data");
			int count = jsonArray.length();
			for (int i = 0; i < count; i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				System.out.println(jsonObject);
				String platforms = jsonObject.getString("OS");

				if ((ConfigHolder.Platform).equalsIgnoreCase("false")) {
					if (platforms.contains("Android")) {
						platform = platforms.replaceAll("[\\[\"\\]]", "");
						parseData(jsonObject);
					}
				} else {
					if (platforms.contains("Android") || platforms.contains("iOS")) {
						platform = platforms.replaceAll("[\\[\"\\]]", "");
						parseData(jsonObject);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			error.info("Error Message MappStreet URL" + e);
		}

	}

	public static void parseData(JSONObject jsonObject) {
		try {
			countries = jsonObject.getString("country_iso");
			if (countries.equalsIgnoreCase("IN") || countries.equalsIgnoreCase("ID")) {
				countries = jsonObject.getString("country_iso");
				String offerName1 = jsonObject.getString("brand_title");
				String offerName2[] = offerName1.split("-");
				offerName = offerName2[0];
				Description = jsonObject.getString("description");
				playstoreurl = jsonObject.getString("preview_url");
				imageurl = jsonObject.getString("app_icon");
				JSONObject jb = jsonObject.getJSONObject("payout");
				if (jb.has("cpl") || jb.has("cpi")) {
					if (jb.has("cpl")) {
						JSONObject jb1 = jb.getJSONObject("cpl");
						payout = jb1.getDouble("actual_value");
						payoutCurrency = jb1.getString("currency");

					} else if (jb.has("cpi")) {
						JSONObject jb1 = jb.getJSONObject("cpi");
						payout = jb1.getDouble("actual_value");
						payoutCurrency = jb1.getString("currency");
					}
				}
				Object ob = jsonObject.get("app_ids");
				if (ob instanceof JSONObject) {
					JSONObject jb2 = jsonObject.getJSONObject("app_ids");
					if (jb2.has("google_android")) {
						packagename = jb2.getString("google_android");
					}
				} else if (ob instanceof JSONArray) {
					String ja = jsonObject.getJSONArray("app_ids").toString();
					if (ja.contains("google_android")) {

					}
				}
				id = jsonObject.getString("campaign_id");
				status = jsonObject.getInt("status");
				actionurl = jsonObject.getString("url");

				Object obj = jsonObject.get("capping");
				if (obj instanceof JSONObject) {
					JSONObject jb3 = jsonObject.getJSONObject("capping");
					if (jb3.has("product_capping")) {
						dailycap = jb3.getJSONArray("product_capping").getJSONObject(0).getString("capping_limit");

					} else if (obj instanceof JSONArray) {
						dailycap = "NA";
					}

				}

				String contentlocking = jsonObject.getString("content_locking");

				if (jsonObject.getString("incentivized").equalsIgnoreCase("Yes")) {
					isIncent = 1;
				} else if (jsonObject.getString("incentivized").equalsIgnoreCase("No")) {
					isIncent = 0;
				}
				String startdate = jsonObject.getString("start_date");
				String campaignrank = jsonObject.getString("campaign_rank");
				String upon_approval = jsonObject.getString("upon_approval");
				String not_allowed = jsonObject.getString("not_allowed");

				hm.put("id", id);
				hm.put("campaignrank", campaignrank);
				hm.put("startdate", startdate);
				hm.put("contentlocking", contentlocking);
				hm.put("not_allowed", not_allowed);
				hm.put("upon_approval", upon_approval);
			}

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
			bean.setExtraData(extradata);
			bean.setPlatform(platform);
			ApiOfferDataBean.insertIntoDB();

			// logger.info(" offerName=" + offerName + " Description=" +
			// Description + " Payout=" + payout + " packageName=" + packagename
			// + " imageurl=" + imageurl + " dailycap=" + dailycap +
			// " totalcap=" + totalcap + " country=" + countries +
			// " vandorName=" + vandorName + " Currency=" + payoutCurrency +
			// " payoutMode=" + payoutmode + " actionUrl=" + actionurl +
			// " playstoreUrl=" + playstoreurl + " status=" + status +
			// " isIncent=" + isIncent + " extraData=" + extradata +
			// " platform=" + platform);

		} catch (Exception e) {
			e.printStackTrace();
			error.info("Error Message Mappstreet Parsing Data:" + e);
		}
	}

}
