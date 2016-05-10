package com.rh.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.rh.bean.ApiOfferDataBean;
import com.rh.clicksmob.beans.ClicksMobOfferBean;
import com.rh.clicksmob.beans.CountryPlatformSet;
import com.rh.clicksmob.beans.OfferPayout;
import com.rh.utility.ConfigHolder;
import com.rh.utility.ReadUrl;

;
public class ParseUrlClicksMob {
	ArrayList<String> listdata = new ArrayList<String>();
	static ApiOfferDataBean bean1 = new ApiOfferDataBean();
	static String result;
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
	static String vandorName = "ClicksMob";
	static String playstoreurl = "NA";
	static String countries = "NA";
	static String countri = "NA";
	static String conversionMode = "NA";
	static String conversionUserFlow = "NA";
	static String restrictions = "NA";
	static String adultTraffic = "NA";
	static String mediabuyerAllowed = "NA";
	static String keyworderAllowed = "NA";
	static String pushAllowed = "NA";
	static String applicationTrafficAllowed = "NA";
	static String publisherValidationAutomatic = "NA";
	static String allowedUseOwnCreative = "NA";
	static String allowedCustomizeBannerTarget = "NA";
	static String eachCreativeMustHaveDifferentLink = "NA";
	static String approvalRequired = "NA";
	static String needAcceptTerms = "NA";
	static String allowedWiFi = "NA";
	static String allowed3G = "NA";
	static String minimalAge = "NA";
	static String rebrokerAllowed = "NA";
	static String discoveryAllowed = "NA";
	static String emailingAllowed = "NA";
	static String android = "NA";
	static String smsallowed = "NA";
	static int isIncent = 3;
	static HashMap<String, String> hm = new HashMap<String, String>();
	static String extradata = "NA";
	static String platform = "NA";
	static Logger error = Logger.getLogger("error");
	static Logger logger = Logger.getLogger("DATA");

	public static void clicksMob(ReadUrl readUrl1) {
		try {
			String url = ConfigHolder.clicksMobApi;
			result = readUrl1.readURL(url);
			logger.info("ClicksMob:"+url);
			parseJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			error.info("Error Message ClicksMob URL"+e);
		}
	}

	public static void parseJson(String result) {
		try {

			JSONObject jsonobj = new JSONObject(result);
			JSONArray jsonArray = jsonobj.getJSONArray("offer");
			int count1 = jsonArray.length();
			for (int i = 0; i < count1; i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				ClicksMobOfferBean bean = new Gson().fromJson(jsonObject.toString(), ClicksMobOfferBean.class);
				List<String> c1 = new ArrayList<String>();
				if (bean != null && bean.getOfferPayouts() != null && bean.getOfferPayouts().getOfferPayout() != null) {
					List<OfferPayout> payoutList = bean.getOfferPayouts().getOfferPayout();
					for (OfferPayout payoutBean : payoutList) {
						c1.addAll(payoutBean.getCountries().getCountry());

					}
				}
				if (bean != null && bean.getOfferCaps() != null && bean.getOfferCaps().getCountryPlatformSet() != null) {
					List<CountryPlatformSet> payoutList = bean.getOfferCaps().getCountryPlatformSet();
					for (CountryPlatformSet payoutBean : payoutList) {
						if (payoutBean.getPlatforms().getPlatform().contains("ANDROID") || payoutBean.getPlatforms().getPlatform().contains("IPHONE")) {
							c1.addAll(payoutBean.getCountries().getCountry());
							if (payoutBean.getPlatforms().getPlatform().contains("ANDROID")) {
								platform = "ANDROID";
							}
							if (payoutBean.getPlatforms().getPlatform().contains("IPHONE")) {
								platform = "IPHONE";
							}
						}
					}
				}

				if (c1.contains("IN") || c1.contains("ID")) {
					if (bean.getOfferPayouts() != null && bean.getOfferPayouts().getOfferPayout() != null) {
						List<OfferPayout> payout1 = bean.getOfferPayouts().getOfferPayout();
						for (int j = 0; j < payout1.size(); j++) {
							OfferPayout payout2 = payout1.get(j);
							if (payout2.getCountries().getCountry().contains("IN") || payout2.getCountries().getCountry().contains("ID")) {
								payout = payout2.getPayout();
							}
						}
					}

					dailycap = bean.getOfferCaps().getNoDailyCap();
					countries = c1.toString();
					String countri1 = countries.replace("[", "");
					countri = countri1.replace("]", "");
					System.out.println(countri);
					String offerName1 = bean.getOfferName();
					offerName = offerName1.replace("\'", "");
					String Description1 = jsonObject.getString("description");
					String Description2 = Description1.replace("\'", "");
					Description = Description2.replaceAll("\\<.*?>", "");
					actionurl = bean.getTargetURL();
					imageurl = bean.getThumbnailURL();
					packagename = bean.getAndroidPackageName();
					payoutmode = bean.getConversionMode();
					conversionMode = jsonObject.getString("conversionMode");
					conversionUserFlow = jsonObject.getString("conversionUserFlow");
					adultTraffic = jsonObject.getString("adultTraffic");
					mediabuyerAllowed = jsonObject.getString("mediabuyerAllowed");
					keyworderAllowed = jsonObject.getString("keyworderAllowed");
					if(jsonObject.getString("incentiveAllowed").equalsIgnoreCase("1")){
						isIncent =2;
					}
					else if(jsonObject.getString("incentiveAllowed").equalsIgnoreCase("0")){
						isIncent =1;
					}
					
					if (jsonObject.has("iosbundleID") && jsonObject.has("androidPackageName")) {

					} else if (jsonObject.has("") && jsonObject.has("")) {

					}

					pushAllowed = jsonObject.getString("pushAllowed");
					applicationTrafficAllowed = jsonObject.getString("applicationTrafficAllowed");
					publisherValidationAutomatic = jsonObject.getString("publisherValidationAutomatic");
					allowedUseOwnCreative = jsonObject.getString("allowedCustomizeBannerTarget");
					allowedCustomizeBannerTarget = jsonObject.getString("allowedCustomizeBannerTarget");
					eachCreativeMustHaveDifferentLink = jsonObject.getString("eachCreativeMustHaveDifferentLink");
					approvalRequired = jsonObject.getString("approvalRequired");
					needAcceptTerms = jsonObject.getString("needAcceptTerms");
					allowedWiFi = jsonObject.getString("allowedWiFi");
					allowed3G = jsonObject.getString("allowed3G");
					minimalAge = jsonObject.getString("minimalAge");
					rebrokerAllowed = jsonObject.getString("rebrokerAllowed");
					discoveryAllowed = jsonObject.getString("discoveryAllowed");
					emailingAllowed = jsonObject.getString("emailingAllowed");
					// JSONArray jb = jsonObject.getJSONArray("android");
					// android = jb.toString();
					smsallowed = jsonObject.getString("smsallowed");
					hm.put("adultTraffic", adultTraffic);
					hm.put("mediabuyerAllowed", mediabuyerAllowed);
					hm.put("keyworderAllowed", keyworderAllowed);
					hm.put("pushAllowed", pushAllowed);
					hm.put("applicationTrafficAllowed", applicationTrafficAllowed);
					hm.put("publisherValidationAutomatic", publisherValidationAutomatic);
					hm.put("allowedUseOwnCreative", allowedUseOwnCreative);
					hm.put("allowedCustomizeBannerTarget", allowedCustomizeBannerTarget);
					hm.put("eachCreativeMustHaveDifferentLink", eachCreativeMustHaveDifferentLink);
					hm.put("approvalRequired", approvalRequired);
					hm.put("needAcceptTerms", needAcceptTerms);
					hm.put("allowedWiFi", allowedWiFi);
					hm.put("allowed3G", allowed3G);
					hm.put("minimalAge", minimalAge);
					hm.put("rebrokerAllowed", rebrokerAllowed);
					hm.put("discoveryAllowed", discoveryAllowed);
					hm.put("emailingAllowed", emailingAllowed);
					hm.put("smsallowed", smsallowed);

					// String extradata1 = hm.toString();
					// String extradata2=extradata1.replace("{", "");
					extradata = Joiner.on("^|").withKeyValueSeparator("~>").join(hm);

					bean1.setOfferName(offerName);
					bean1.setDescription(Description);
					bean1.setPayout(payout);
					bean1.setPackageName(packagename);
					bean1.setImageUrl(imageurl);
					bean1.setDailycap(dailycap);
					bean1.setTotalcap(totalcap);
					bean1.setCountries(countri);
					bean1.setVendorName(vandorName);
					bean1.setCurrency(payoutCurrency);
					bean1.setMode(payoutmode);
					bean1.setActionUrl(actionurl);
					bean1.setPalystoreUrl(playstoreurl);
					bean1.setStatus(status);
					bean1.setIsIncent(isIncent);
					bean1.setExtraData(extradata);
					bean1.setPlatform(platform);
					// ApiOfferDataBean.count = 0;
//					logger.info(" offerName=" + offerName + " Description=" + Description + " Payout=" + payout + " packageName=" + packagename + " imageurl=" + imageurl + " dailycap=" + dailycap + " totalcap=" + totalcap + " country=" + countri + " vandorName=" + vandorName + " Currency=" + payoutCurrency + " payoutMode=" + payoutmode + " actionUrl=" + actionurl + " playstoreUrl=" + playstoreurl + " status=" + status + " isIncent=" + isIncent + " extraData=" + extradata + " platform=" + platform);

					ApiOfferDataBean.insertIntoDB();

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			error.info("Error Message ClicksMob Parsing Data:"+e);
		}

	}
}


