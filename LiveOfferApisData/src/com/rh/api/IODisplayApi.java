package com.rh.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.base.Joiner;
import com.rh.bean.ApiOfferDataBean;
import com.rh.utility.ConfigHolder;
import com.rh.utility.ReadUrl;

public class IODisplayApi {
	static int count = 0;
	ArrayList<String> listdata = new ArrayList<String>();
	static String result = "NA";
	static String offerName = "NA";
	static String Description = "NA";
	static String payoutmode = "NA";
	static double payout = 0.0;
	static String totalcap = "NA";
	static String dailycap = "NA";
	static String payoutCurrency = "USD";
	static String imageurl = "NA";
	static String actionurl = "NA";
	static String packagename = "NA";
	static int status = 0;
	static String vandorName = "IosDisplay";
	static String playstoreurl = "NA";
	static String targetPlatform = "NA";
	static String categories = "NA";
	static String id = "NA";
	static String platform = "NA";

	static List<String> countries = new ArrayList<String>();
	static ApiOfferDataBean bean = new ApiOfferDataBean();
	static HashMap<String, String> hm = new HashMap<String, String>();
	static Logger error = Logger.getLogger("error");
	static Logger logger = Logger.getLogger("DATA");
	static String countri;
	static String osversion = "NA";
	private static String extradata = "NA";
	static int isIncent = 3;

	public static void ioDisplayApi(ReadUrl readUrl) {
		logger.info("Start");
		try {
			String url = ConfigHolder.ioDisplayApi;
			result = readUrl.readURL(url);
			logger.info("IODisplay:"+url);
			parseJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			error.info("Error Message IODisplay URL"+e);
		}
	}

	public static void parseJson(String result) {
		try {

			JSONObject jsonobj = new JSONObject(result);
			JSONArray jsonArray = jsonobj.getJSONArray("data");
			int count = jsonArray.length();
			for (int i = 0; i < count; i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONArray countries = jsonObject.getJSONArray("countryCodes");
				String country = countries.toString();
				if (ConfigHolder.Platform.equalsIgnoreCase("false")) {
					if (jsonObject.getString("os").equals("android")) {
						platform = jsonObject.getString("os");
						parseData(jsonObject, country);
					}
				} else {
					if (jsonObject.getString("os").equalsIgnoreCase("android") || jsonObject.getString("os").equalsIgnoreCase("ios"))
						platform = jsonObject.getString("os");
					parseData(jsonObject, country);
				}
			}
			System.out.print("done");
		} catch (Exception e) {
			e.printStackTrace();
			error.info("Error Message IODisplay Parsing JSON:"+e);
		}

	}

	public static void parseData(JSONObject jsonObject, String country) throws SQLException {
		try {
			if (country.contains("IN") || country.contains("ID")) {

				playstoreurl = jsonObject.getString("previewUrl");
				String offerName1 = jsonObject.getString("name");
				String offerName2[] = offerName1.split("\\W");
				offerName = offerName2[0];

				packagename = jsonObject.getString("bundleId");
				String country1 = country.replaceAll("[\\[\\]]", "");
				countri = country1.replace("\"", "");
				System.out.println(countri);

				imageurl = jsonObject.getString("thumbnail");
				payout = jsonObject.getDouble("payout");
				actionurl = jsonObject.getString("clickurl");
				String Description1 = jsonObject.getString("storeTitle");
				Description = Description1.replaceAll("\\<.*?>", "");
				targetPlatform = jsonObject.getString("os");
				JSONArray category = jsonObject.getJSONArray("categories");

				String categor1 = category.toString();
				String categot2 = categor1.replaceAll("[\\[\\]]", "");
				categories = categot2.replace("\"", "");

				if (categories.contains("Non-Incent")) {
					isIncent = 0;
				} else if (categories.contains("Incent")) {
					isIncent = 1;
				}

				id = jsonObject.getString("id");
				osversion = jsonObject.getString("minOsVer");

				hm.put("id", id);
				hm.put("categories", categories);
				hm.put("osversion", osversion);
				// String extradata1 = hm.toString();
				// String extradata2=extradata1.replace("{", "");
				// extradata=extradata2.replace("}", "");
				extradata = Joiner.on("^|").withKeyValueSeparator("~>").join(hm);
				// String a = Joiner.on('+').join(hm);

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

//				logger.info(" offerName=" + offerName + " Description=" + Description + " Payout=" + payout + " packageName=" + packagename + " imageurl=" + imageurl + " dailycap=" + dailycap + " totalcap=" + totalcap + " country=" + countri + " vandorName=" + vandorName + " Currency=" + payoutCurrency + " payoutMode=" + payoutmode + " actionUrl=" + actionurl + " playstoreUrl=" + playstoreurl + " status=" + status + " isIncent=" + isIncent + " extraData=" + extradata + " platform=" + platform);

				ApiOfferDataBean.insertIntoDB();

			}
		} catch (JSONException e) {
			e.printStackTrace();
			error.info("Error Message IODisplay Parsing Data:"+e);
		}
	}
}
