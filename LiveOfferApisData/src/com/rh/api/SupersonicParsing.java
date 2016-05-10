package com.rh.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.google.common.base.Joiner;
import com.rh.bean.ApiOfferDataBean;
import com.rh.utility.ConfigHolder;
import com.rh.utility.ReadUrl;

public class SupersonicParsing {
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
	static String vandorName = "Supersonic";
	static String playstoreurl = "NA";
	static String platform = "NA";
	static String countries = "IN";
	static String id = "NA";
	static String tags = "NA";

	static HashMap<String, String> hm = new HashMap<String, String>();

	private static String extradata = "NA";
	static int isIncent = 3;
	private static String applicationSize = "NA";
	private static String minOsVersion = "NA";
	static Logger error = Logger.getLogger("error");
	static Logger logger = Logger.getLogger("DATA");

	public static void superSonic(ReadUrl readUrl) {
		try {
			String url = ConfigHolder.Supersonic;
			result = readUrl.readURL(url);
			logger.info("Supersonic:"+url);
			parseJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			error.info("Error Message IODisplay URL"+e);
		}
	}

	public static void parseJson(String result) {
		try {

			JSONObject jsonobj = new JSONObject(result);
			JSONObject jsonobj1 = jsonobj.getJSONObject("offers");
			Iterator<?> itr = jsonobj1.keys();

			while (itr.hasNext()) {
				String key = (String) itr.next();
				JSONObject jsonObject = jsonobj1.getJSONObject(key);
				countries = jsonObject.getJSONArray("countries").toString();
				String platforms = jsonObject.getJSONArray("supportedPlatforms").toString();
				if ((ConfigHolder.Platform).equalsIgnoreCase("false")) {
					if (platforms.contains("android")) {
						platform = platforms.replaceAll("[\\[\"\\]]", "");
						parseData(jsonObject);
					}
				} else {
					if (platforms.contains("android") || platforms.contains("iphone")) {
						platform = platforms.replaceAll("[\\[\"\\]]", "");
						parseData(jsonObject);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			error.info("Error Message Supersonic Parsing JSON:"+e);
			
		}

	}

	public static void parseData(JSONObject jsonObject) {
		try{
		if (countries.contains("IN") || countries.contains("ID")) {
			offerName = jsonObject.getString("title");
			Description = jsonObject.getString("disclaimer");
			payout = jsonObject.getDouble("payout");
//			isIncent=jsonObject.getString("incentAllowed");
			if(jsonObject.getString("incentAllowed").equalsIgnoreCase("true")){
				isIncent = 1;
			}else if (jsonObject.getString("incentAllowed").equalsIgnoreCase("false")){
				isIncent = 0;
			}
			payoutmode = jsonObject.getString("conciseType");
			String country = jsonObject.getJSONArray("countries").toString();
			String countrie = country.replaceAll("[\\[\\]]", "");
			countries = countrie.replace("\"", "");
			imageurl = jsonObject.getJSONArray("images").getJSONObject(0).getString("url");
			playstoreurl = jsonObject.getString("reviewOnlyUrl");
			packagename = jsonObject.getString("applicationBundleId");
			actionurl = jsonObject.getString("url");
			id = jsonObject.getString("offerId");
			String rewards = jsonObject.getString("rewards");
			if (jsonObject.has("minOsVersion")) {
				minOsVersion = jsonObject.getString("minOsVersion");
			}
			String applicationCategories = jsonObject.getString("applicationCategories");
			if (jsonObject.has("applicationSize")) {
				applicationSize = jsonObject.getString("applicationSize");
			}
			String applicationDeveloper = jsonObject.getString("applicationDeveloper");
			String game = jsonObject.getString("game");
			String purchase = jsonObject.getString("purchase");

			hm.put("id", id);
			hm.put("rewards", rewards);
			hm.put("minOsVersion", minOsVersion);
			hm.put("applicationCategories", applicationCategories);
			hm.put("applicationSize", applicationSize);
			hm.put("applicationDeveloper", applicationDeveloper);
			hm.put("game", game);
			hm.put("purchase", purchase);

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
//			logger.info(" offerName=" + offerName + " Description=" + Description + " Payout=" + payout + " packageName=" + packagename + " imageurl=" + imageurl + " dailycap=" + dailycap + " totalcap=" + totalcap + " country=" + countries + " vandorName=" + vandorName + " Currency=" + payoutCurrency + " payoutMode=" + payoutmode + " actionUrl=" + actionurl + " playstoreUrl=" + playstoreurl + " status=" + status + " isIncent=" + isIncent + " extraData=" + extradata + " platform=" + platform);

			ApiOfferDataBean.insertIntoDB();
		}
		}catch(Exception e){
			e.printStackTrace();
			error.info("Error Message Supersonic Parsing Data:"+e);
		}
	}

}
