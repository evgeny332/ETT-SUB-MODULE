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

public class ParseCrunchieMediaUrl {
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
	static String vandorName = "crunchiemedia";
	static String playstoreurl = "NA";
	static int isIncent = 3;
	static String countries = "NA";
	static String IncentAllowed = "Na";
	static String UserRating = "NA";
	static String MarketAppID = "NA";
	static String id = "NA";
	static HashMap<String, String> hm = new HashMap<String, String>();
	static String extradata = "NA";
	static String platform = "NA";
	// static Logger logger = Logger.getLogger("logger");
	static Logger logger = Logger.getLogger(ParseCrunchieMediaUrl.class);

	public static void crunchieMediaUrl(ReadUrl readUrl) {
		logger.info("Start");
		try {
			String url = ConfigHolder.crunchiemediaApi;
			result = readUrl.readURL(url);
			logger.info("CruchieMedia:" + url);
			parseJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("logger Message CrunchieMedia URL" + e);
		}
	}

	public static void parseJson(String result) {
		try {

			JSONObject jsonobj = new JSONObject(result);
			JSONArray jsonArray = jsonobj.getJSONArray("Offer");
			int count = jsonArray.length();
			for (int i = 0; i < count; i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONArray platforms = jsonObject
						.getJSONArray("SupportedPlatforms");
				String c1 = platforms.toString();

				if ((ConfigHolder.Platform).equalsIgnoreCase("false")) {
					if (c1.contains("android")) {
						platform = c1.replaceAll("[\\[\"\\]]", "");
						parseData(jsonObject, c1);

					}
				} else {
					if (c1.contains("android") || c1.contains("iphone")) {
						platform = c1.replaceAll("[\\[\"\\]]", "");

						parseData(jsonObject, c1);
					}
				}
			}
			logger.info("DONE");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("logger Message CrunchieMedia Parsing Json:" + e);
		}

	}

	public static void parseData(JSONObject jsonObject, String c1) {
		try {
			JSONArray country = jsonObject.getJSONArray("SupportedCountries");
			String countries1 = country.toString();
			String countries2 = countries1.replaceAll("[\\[\\]]", "");
			countries = countries2.replace("\"", "");

			if (countries.contains("IN") || countries.contains("ID")) {

				if (jsonObject.getString("OfferName") != null) {
					offerName = jsonObject.getString("OfferName");
				} else {
					offerName = jsonObject.getString("AppName");
				}

				String Description1 = jsonObject.getString("AppName");
				Description = Description1.replaceAll("\\<.*?>", "");
				payout = jsonObject.getDouble("Payout");
				packagename = jsonObject.getString("MarketAppID");
				actionurl = jsonObject.getString("TrackingLink");
				imageurl = jsonObject.getString("AppIconURL");
				payoutmode = jsonObject.getString("PayoutType");
				playstoreurl = jsonObject.getString("PreviewLink");
				totalcap = jsonObject.getString("AvailableLeads");
				// IncentAllowed = jsonObject.getString("IncentAllowed");
				UserRating = jsonObject.getString("UserRating");
				UserRating = jsonObject.getString("UserRating");
				MarketAppID = jsonObject.getString("MarketAppID");
				id = jsonObject.getString("ID");
				if (jsonObject.getString("IncentAllowed").equalsIgnoreCase(
						"true")) {
					isIncent = 1;
				} else if (jsonObject.getString("IncentAllowed")
						.equalsIgnoreCase("false")) {
					isIncent = 0;
				}
				hm.put("id", id);
				hm.put("UserRating", UserRating);
				hm.put("MarketAppId", MarketAppID);
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
				// logger.info(" offerName=" + offerName + " Description=" +
				// Description + " Payout=" + payout + " packageName=" +
				// packagename + " imageurl=" + imageurl + " dailycap=" +
				// dailycap + " totalcap=" + totalcap + " country=" + countries
				// + " vandorName=" + vandorName + " Currency=" + payoutCurrency
				// + " payoutMode=" + payoutmode + " actionUrl=" + actionurl +
				// " playstoreUrl=" + playstoreurl + " status=" + status +
				// " isIncent=" + isIncent + " extraData=" + extradata +
				// " platform=" + platform);
				ApiOfferDataBean.insertIntoDB();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("logger Message CrunchieMedia Parsing Data:" + e);
		}
	}

}