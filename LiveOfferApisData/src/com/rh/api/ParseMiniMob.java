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

public class ParseMiniMob {

	static ArrayList<String> listdata = new ArrayList<String>();
	private static String extradata;
	static String acquisitionModel = "NA";
	static String acquisitionModelDescription = "NA";
	static String result = "NA";
	static String offerName = "NA";
	static String Description = "NA";
	static String payoutmode = "NA";
	static double payout = 0.0;
	static String totalcap = "NA";
	static String dailycap = "NA";
	static String payoutCurrency = "NA";
	static String imageurl = "NA";
	static String actionurl = "NA";
	static String packagename = "NA";
	static int status = 0;
	static String vandorName = "MiniMobs";
	static String playstoreurl = "NA";
	static int isIncent = 3;
	static String targetplatform = "NA";
	static String weekelyconversioncap = "NA";
	static String monthlycomversioncap = "NA";
	static String appDescription = "NA";
	static String id = "NA";
	static String appdescription = "NA";
	static String incentivized = "NA";
	static String storeId = "NA";
	static String platform = "NA";

	static ArrayList<String> countries = new ArrayList<String>();
	static ApiOfferDataBean bean = new ApiOfferDataBean();
	static HashMap<String, String> hm = new HashMap<String, String>();
//	static Logger logger = Logger.getLogger("logger");
	static Logger logger = Logger.getLogger(ParseMiniMob.class);
	static String countri;
	static ReadUrl readUrl;

	public static void MiniMob( ReadUrl readUrl1) {
		logger.info("Start");
		readUrl = readUrl1; 
		try {

			String url = ConfigHolder.urlAvalableOffers;
			result = readUrl.readURL(url);
			logger.info("ParseMiniMobs:"+url);
			parseJson(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void parseJson(String result) {
		try {

			JSONArray jsonArray = new JSONArray(result);

			int count = jsonArray.length(); // get totalCount of all jsonObjects
			for (int i = 0; i < count; i++) { // iterate through jsonArray

				JSONObject jsonObject = jsonArray.getJSONObject(i);

				JSONArray country = jsonObject.getJSONArray("targetedCountries");
				String Countries = country.toString();
				String incentivizedDescription = jsonObject.getString("incentivizedDescription");
				String platforms = jsonObject.getString("targetPlatform");
				if ((ConfigHolder.Platform).equalsIgnoreCase("false")) {
					if (platforms.equalsIgnoreCase("Android")) {
						platform = platforms;
						parseData(Countries, jsonObject, incentivizedDescription);

					}
				} else {
					if (platforms.equalsIgnoreCase("Android") || platforms.equalsIgnoreCase("IOS")) {
						platform = platforms;
						parseData(Countries, jsonObject, incentivizedDescription);
					}
				}
			}
			logger.info("Done");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("logger Message MiniMob Parsing JSON:"+e);
		}

	}

	public static void parseData(String Countries, JSONObject jsonObject, String incentivizedDescription) {
		try{
		if ((Countries.contains("IN") || listdata.contains("ID")) && (incentivizedDescription.equalsIgnoreCase("Incentivized") || incentivizedDescription.equalsIgnoreCase("BothIncentivizedAndNot"))) {
			targetplatform = platform;
			if (incentivizedDescription.equalsIgnoreCase("Incentivized")) {
				isIncent = 1;
			} else if (incentivizedDescription.equalsIgnoreCase("BothIncentivizedAndNot")) {
				isIncent = 2;
			}
			System.out.println("jsonObject : " + jsonObject);
			String country1 = Countries.replace("[", "");
			String country2 = country1.replace("]", "");
			countri = country2.replace("\"", "");
			System.out.print(countri);

			status = jsonObject.getInt("status");
			storeId = jsonObject.getString("storeId");
			incentivized = jsonObject.getString("incentivized");
			weekelyconversioncap = jsonObject.getString("weeklyConversionCap");
			monthlycomversioncap = jsonObject.getString("monthlyConversionCap");
			totalcap = jsonObject.getString("overallConversionCap");
			dailycap = jsonObject.getString("dailyConversionCap");
			String id = jsonObject.getString("id");
			parseUrlWithID(id);
		}
		}catch(Exception e){
			e.printStackTrace();
			logger.info("MiniMob"+e);
		}
		
	}

	public static void parseUrlWithID(String id) {
		String url = ConfigHolder.urlAvalableOffers2 + id;
		result = readUrl.readURL(url);
//		System.out.println(url);
		try {
			JSONObject jsonObject = new JSONObject(result);
			offerName = jsonObject.getString("name");
			String Description1 = jsonObject.getString("description");
			Description = Description1.replaceAll("\\<.*?>", "");
			payoutmode = jsonObject.getString("payoutModel");
			payout = jsonObject.getDouble("payout");

			payoutCurrency = jsonObject.getString("payoutCurrency");
			// String imageUrl = jsonObject.getString("creatives");
			packagename = jsonObject.getString("appId");
			playstoreurl = jsonObject.getString("appPreviewLink");
			JSONArray jsonArray = jsonObject.getJSONArray("creatives");
			for (int i = 0; i < jsonArray.length(); i++) {
				imageurl = jsonArray.getJSONObject(i).getString("previewUrl");
			}
			appdescription = jsonObject.getString("appDescription");
			acquisitionModel = jsonObject.getString("acquisitionModel");
			acquisitionModelDescription = jsonObject.getString("acquisitionModelDescription");
			if (jsonObject.has("objectiveUrl")) {
				actionurl = jsonObject.getString("objectiveUrl");
			}

			hm.put("Id", id);
			hm.put("Platform", targetplatform);
			hm.put("appdescription", appdescription);
			hm.put("weekelyconversioncap", weekelyconversioncap);
			hm.put("monthlyconversioncap", monthlycomversioncap);
			hm.put("incentivized", incentivized);
			hm.put("storeId", storeId);
			hm.put("acquisitionModelDescription", acquisitionModelDescription);
			hm.put("acquisitionModel", acquisitionModel);

			// String extradata1 = hm.toString();
			// String extradata2 = extradata1.replace("{", "");
			extradata = Joiner.on("^|").withKeyValueSeparator("~>").join(hm);

//			System.out.println("jsonObject2 : " + jsonObject);

			bean.setOfferName(offerName);
			bean.setDescription(Description);
			bean.setPayout(payout);
			bean.setPackageName(packagename);
			bean.setImageUrl(imageurl);
			bean.setDailycap(dailycap);
			bean.setTotalcap(totalcap);
			bean.setCountries(countri);
			bean.setVendorName(vandorName);
			bean.setCurrency(payoutCurrency);
			bean.setMode(payoutmode);
			bean.setActionUrl(actionurl);
			bean.setPalystoreUrl(playstoreurl);
			bean.setStatus(status);
			bean.setIsIncent(isIncent);
			bean.setExtraData(extradata);
			bean.setPlatform(platform);
//			logger.info(" offerName=" + offerName + " Description=" + Description + " Payout=" + payout + " packageName=" + packagename + " imageurl=" + imageurl + " dailycap=" + dailycap + " totalcap=" + totalcap + " country=" + countri + " vandorName=" + vandorName + " Currency=" + payoutCurrency + " payoutMode=" + payoutmode + " actionUrl=" + actionurl + " playstoreUrl=" + playstoreurl + " status=" + status + " isIncent=" + isIncent + " extraData=" + extradata + " platform=" + platform);

			ApiOfferDataBean.insertIntoDB();

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("MiniMob:"+e);
		}
	}
}
