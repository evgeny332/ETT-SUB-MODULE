package com.rh.api;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.base.Joiner;
import com.rh.bean.ApiOfferDataBean;
import com.rh.dbo.DBservice;
import com.rh.utility.ConfigHolder;
import com.rh.utility.ReadUrl;

public class ParseWoobi {
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
	static String vandorName = "Woobi";
	static String playstoreurl = "NA";
	static String platform = "Android";
	static String countries = "IN";
	static String id = "NA";
	static String tags = "NA";

	static HashMap<String, String> hm = new HashMap<String, String>();

	private static String extradata = "NA";
	static int isIncent = 3;
	private static String appType = "NA";
	private static String appPublisher = "NA";
	private static String appSize = "NA";
	private static String appRanking = "NA";
	private static String artworkSqr = "NA";
	private static String artworkLong = "NA";
	private static String isIdfaMandatory = "NA";
	private static String appDomain = "NA";
	private static String language = "NA";
	private static String cpe = "NA";
	private static String cpnId = "NA";
	private static String isForAccumulation = "NA";
	private static String conversionType = "NA";
	private static String credits = "NA";
	private static String priceTerm = "NA";
	private static String offerCompEstTme = "NA";
	private static String isPaid = "NA";
	private static String addId = "NA";
//	static Logger logger = Logger.getLogger("logger");
	static Logger logger = Logger.getLogger(ParseWoobi.class);
private static int counter=1;

	public static void woobi(ReadUrl readUrl) {
		logger.info("Start");
		try {
			String url = ConfigHolder.Woobi;
			logger.info("Woobi:" + url);
			result = readUrl.readURL(url);
			
			parseJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("logger Message Woobi URL" + e);
		}
	}

	public static void parseJson(String result) {
		try {

			JSONObject jsonobj = new JSONObject(result);
			System.out.print(result);
			JSONArray jsonArray = jsonobj.getJSONArray("offers");
			int count = jsonArray.length();
			for (int i = 0; i < count; i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				// String platforms = jsonObject.getString("OS");

				// if ((ParserMainClass.Platform).equalsIgnoreCase("false")) {
				// if (platforms.contains("Android")) {
				// platform=platforms.replaceAll("[\\[\"\\]]","");
				parseData(jsonObject);
				// }
				// } else {
				// if (platforms.contains("Android") ||
				// platforms.contains("iOS")) {
				// platform=platforms.replaceAll("[\\[\"\\]]","");
				// parseData(jsonObject);
				// }
				// }
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("logger Message Woobi Parsing JSON:" + e);
		}
		logger.info("DONE");

	}

	public static void parseData(JSONObject jsonObject) {
		try {
			if (countries.contains("IN") || countries.contains("ID")) {
				offerName = jsonObject.getString("title");
				Description = jsonObject.getString("subtitle");
				payout = jsonObject.getDouble("payout");
				if (jsonObject.has("priceCurrency")) {
					payoutCurrency = jsonObject.getString("priceCurrency");
				}
				actionurl = jsonObject.getString("clickURL");
				imageurl = jsonObject.getString("thumbnail");
				offerCompEstTme = jsonObject.getString("offer_complete_est_time");
				isPaid = jsonObject.getString("isPaid");
				id = jsonObject.getString("offerId");
				addId = jsonObject.getString("adId");
				priceTerm = jsonObject.getString("priceTerm");
				credits = jsonObject.getString("credits");

				if (jsonObject.has("appType")) {
					appType = jsonObject.getString("appType");
				}
				if (jsonObject.has("appPublisher")) {
					appPublisher = jsonObject.getString("appPublisher");
				}
				if (jsonObject.has("appSize")) {
					appSize = jsonObject.getString("appSize");
				}
				if (jsonObject.has("appRanking")) {
					appRanking = jsonObject.getString("appRanking");
				}
				if (jsonObject.has("artworkSqr")) {
					artworkSqr = jsonObject.getString("artworkSqr");
				}
				if (jsonObject.has("artworkLong")) {
					artworkLong = jsonObject.getString("artworkLong");
				}

				isIdfaMandatory = jsonObject.getString("isIdfaMandatory");
				appDomain = jsonObject.getString("appDomain");
				language = jsonObject.getString("language");
				cpe = jsonObject.getString("cpe");
				cpnId = jsonObject.getString("cpnId");
				isForAccumulation = jsonObject.getString("isForAccumulation");
				conversionType = jsonObject.getString("conversionType");

				hm.put("id", id);
				hm.put("isIdfaMandatory", isIdfaMandatory);
				hm.put("artworkLong", artworkLong);
				hm.put("artworkSqr", artworkSqr);
				hm.put("appRanking", appRanking);
				hm.put("appRanking", appRanking);
				hm.put("appDomain", appDomain);
				hm.put("credits", credits);
				hm.put("priceTerm", priceTerm);
				hm.put("appPublisher", appPublisher);
				hm.put("appSize", appSize);
				hm.put("appType", appType);
				hm.put("offerCompEstTme", offerCompEstTme);
				hm.put("isPaid", isPaid);
				hm.put("addId", addId);
				hm.put("language", language);
				hm.put("cpe", cpe);
				hm.put("isForAccumulation", isForAccumulation);
				hm.put("cpnId", cpnId);
				hm.put("conversionType", conversionType);

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
//				logger.info(" offerName=" + offerName + " Description=" + Description + " Payout=" + payout + " packageName=" + packagename + " imageurl=" + imageurl + " dailycap=" + dailycap + " totalcap=" + totalcap + " country=" + countries + " vandorName=" + vandorName + " Currency=" + payoutCurrency + " payoutMode=" + payoutmode + " actionUrl=" + actionurl + " playstoreUrl=" + playstoreurl + " status=" + status + " isIncent=" + isIncent + " extraData=" + extradata + " platform=" + platform);
				if(counter==1){
					String query1 = "Delete from ApiOfferData where vendorName='"+ vandorName + "'";
					DBservice.UpdateData(query1);
					counter=2;
					}
				ApiOfferDataBean.insertIntoDB();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("logger Message Woobi Parsing Data:"+e);
		}
	}

}
