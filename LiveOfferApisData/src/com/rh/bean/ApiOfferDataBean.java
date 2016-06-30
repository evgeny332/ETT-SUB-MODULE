package com.rh.bean;

import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.rh.dbo.DBservice;

public class ApiOfferDataBean {
	// public static int count = 0;
	private String id;
	private static String offerName;
	private static String description;
	private static String packageName;
	private static String currency;
	private static double payout;
	private static String imageUrl;
	private static String actionUrl;
	private static String mode;
	private static String totalcap;
	private static String dailycap;
	private static String playstoreUrl;
	private static String countries;
	private static int status;
	private static int isIncent;
	private static String extraData;
	private static String Platform;
	// static Logger error = Logger.getLogger("error");
	static Logger logger = Logger.getLogger(ApiOfferDataBean.class);

	public static String getPlatform() {
		return Platform;
	}

	public void setPlatform(String platform) {
		Platform = platform;
	}

	// private static String countri;
	private static String vendorName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOfferName() {
		return offerName;
	}

	public String setOfferName(String offerName) {
		return ApiOfferDataBean.offerName = offerName;
	}

	public static String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		ApiOfferDataBean.description = description;
	}

	public static String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		ApiOfferDataBean.imageUrl = imageUrl;
	}

	public static String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		ApiOfferDataBean.mode = mode;
	}

	public static String getPlaystoreUrl() {
		return playstoreUrl;
	}

	public void setPlaystoreUrl(String playstoreUrl) {
		ApiOfferDataBean.playstoreUrl = playstoreUrl;
	}

	public int getIsIncent() {
		return isIncent;
	}

	public void setIsIncent(int isIncent) {
		ApiOfferDataBean.isIncent = isIncent;
	}

	public static String getPackageName() {
		return packageName;
	}

	public String setPackageName(String packageName) {
		return ApiOfferDataBean.packageName = packageName;
	}

	public static String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		ApiOfferDataBean.currency = currency;
	}

	public double getPayout() {
		return payout;
	}

	public void setPayout(double payout2) {
		ApiOfferDataBean.payout = payout2;
	}

	public static String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		ApiOfferDataBean.actionUrl = actionUrl;
	}

	public String getTotalcap() {
		return totalcap;
	}

	public void setTotalcap(String totalcap2) {
		ApiOfferDataBean.totalcap = totalcap2;
	}

	public String getDailycap() {
		return dailycap;
	}

	public void setDailycap(String dailycap2) {
		ApiOfferDataBean.dailycap = dailycap2;
	}

	public static String getPalystoreUrl() {
		return playstoreUrl;
	}

	public String setPalystoreUrl(String palystoreUrl) {
		return ApiOfferDataBean.playstoreUrl = palystoreUrl;
	}

	public String getCountries() {
		return countries;
	}

	public void setCountries(String countries) {
		ApiOfferDataBean.countries = countries;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		ApiOfferDataBean.status = status;
	}

	public static String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		ApiOfferDataBean.vendorName = vendorName;
	}

	public static String getExtraData() {
		return extraData;
	}

	public void setExtraData(String extraData) {
		ApiOfferDataBean.extraData = extraData;
	}

	public static void insertIntoDB() {
//		logger.info("Start");
		
		try {
			String query = "insert into ApiOfferData(offerName,description,packageName,currency ,payout,imageUrl ,mode ,totalcap ,dailycap ,palystoreUrl,countries ,status,vendorName,actionUrl,isIncent,extraData,platform) values(? ,?,?,? ,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pt1 = DBservice.getPS(query);
			// logger.info(query);

			pt1.setString(1, offerName);
			pt1.setString(2, description);
			pt1.setString(3, packageName);
			pt1.setString(4, currency);
			pt1.setDouble(5, payout);
			pt1.setString(6, imageUrl);
			pt1.setString(7, mode);
			pt1.setString(8, totalcap);
			pt1.setString(9, dailycap);
			pt1.setString(10, playstoreUrl);
			pt1.setString(11, countries);
			pt1.setInt(12, status);
			pt1.setString(13, vendorName);
			pt1.setString(14, actionUrl);
			pt1.setInt(15, isIncent);
			pt1.setString(16, extraData);
			pt1.setString(17, Platform);

			pt1.executeUpdate();
//			logger.info("Done");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("SqlError:" + e);

		}

	}

}
