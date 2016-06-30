package com.rh.app;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.rh.Dao.DBservice;
import com.rh.entity.DashInstallBack;
import com.rh.entity.OfferDetailsCheck;
import com.rh.utility.GetUtils;

public class NewMain {

	final static Logger logger = Logger.getLogger(NewMain.class);
	static String date;
	static List<DashInstallBack> list = new ArrayList<DashInstallBack>();
	static Map<Integer, Integer> map = new HashMap<Integer, Integer>();

	public static void main(String args[]) {

		logger.debug("Into the main");

		while (true) {

			date = GetUtils.GetDate();

			recoOfferId();
			generalOffers();
			recommendOffers();
			offerdetailscheck();

			if (list.size() > 0) {
				Collections.sort(list, new Comparator<DashInstallBack>() {
					@Override
					public int compare(final DashInstallBack object1, final DashInstallBack object2) {
						return Integer.compare(object1.getOfferId(), (object2.getOfferId()));
					}
				});
			}

			GetUtils.sendMessage(list);

			list.clear();
			map.clear();
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error("Error :", e);
				System.exit(0);
			}
		}
	}

	public static void generalOffers() {

		List<DashInstallBack> list1 = new ArrayList<DashInstallBack>();
		List<DashInstallBack> list2 = new ArrayList<DashInstallBack>();

		ResultSet rs = null;
		ResultSet sa = null;
		try {
			long stime = System.currentTimeMillis();
			int temp = 0;
			DashInstallBack dbc = new DashInstallBack();
			String query = "select count(1) count,offerId,vendor,offerInstalled from Edr where clickedTime>='"+date +" 18:30:00' group by offerId,vendor,offerInstalled";
			rs = DBservice.selectdata(query);

			while (rs.next()) {
				DashInstallBack db = new DashInstallBack();

				int check = 0;
				for (Entry<Integer, Integer> entry : map.entrySet()) {
					if (rs.getInt("offerId") == entry.getValue()) {
						check = 1;
					}
				}

				if (check == 0) {
					if (rs.getInt("offerInstalled") == 0) {

						if (temp == 0) {

							db.setOfferId(dbc.getOfferId());
							db.setVendor(dbc.getVendor());
							db.setClickCount(dbc.getClickCount());
							db.setIsOn(0);

							list1.add(db);
						}

						dbc.setOfferId(rs.getInt("offerId"));
						if (rs.getString("vendor") == null || rs.getString("vendor").equals("null") || rs.getString("vendor").equals("NULL")) {
							dbc.setVendor("");
						} else {
							dbc.setVendor(rs.getString("vendor"));
						}
						dbc.setClickCount(rs.getInt("count"));
						temp = 0;
					} else if (rs.getInt("offerInstalled") == 1) {

						/*
						 * if(temp==0 && rs.getInt("offerId") != dbc.getOfferId() && !rs.getString("vendor").equals(dbc.getVendor())){ list1.add(dbc); }
						 */
						temp = 1;

						logger.info("OfferId:" + dbc.getOfferId() + "| vendor:" + dbc.getVendor());

						if (rs.getInt("offerId") == dbc.getOfferId() && rs.getString("vendor").equals(dbc.getVendor())) {

							db.setOfferId(rs.getInt("offerId"));
							if (rs.getString("vendor") == null || rs.getString("vendor").equals("null")) {
								db.setVendor("");
							} else {
								db.setVendor(rs.getString("vendor"));
							}
							db.setInstalledCount(rs.getInt("count"));
							db.setClickCount(dbc.getClickCount() + rs.getInt("count"));
							db.setIsOn(0);
							list1.add(db);

						} else {

							logger.debug("in else");

							db.setOfferId(rs.getInt("offerId"));
							if (rs.getString("vendor") == null || rs.getString("vendor").equals("null")) {
								db.setVendor("");
							} else {
								db.setVendor(rs.getString("vendor"));
							}
							db.setInstalledCount(rs.getInt("count"));
							db.setClickCount(rs.getInt("count"));
							db.setIsOn(0);

							list1.add(db);
						}

					}
				}
			}
			if (temp == 0) {
				list1.add(dbc);
			}
			dbc = null;

			long etime = System.currentTimeMillis();
			logger.debug(query + ", Time: " + (double) (etime - stime) / 1000);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error :", e);
			System.exit(0);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.error("Error", e2);
				}
			}
		}

		try {

			long stime = System.currentTimeMillis();
			String que = "select count(1) count,offerId,vendor from CallBackConfirmation where createdTime>='"+date+" 18:30:00' group by offerId,vendor";
			sa = DBservice.selectdata(que);

			while (sa.next()) {

				int check = 0;
				for (Entry<Integer, Integer> entry : map.entrySet()) {
					if (sa.getInt("offerId") == entry.getValue()) {
						check = 1;
					}
				}

				if (check == 0) {

					DashInstallBack dib = new DashInstallBack();
					dib.setOfferId(sa.getInt("offerId"));
					if (sa.getString("vendor") == null || sa.getString("vendor").equals("null")) {
						dib.setVendor("");
					} else {
						dib.setVendor(sa.getString("vendor"));
					}
					dib.setCallbackCount(sa.getInt("count"));
					dib.setIsOn(0);

					list2.add(dib);
				}
			}

			long etime = System.currentTimeMillis();
			logger.debug(que + ", Time: " + (double) (etime - stime) / 1000);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error :", e);
			System.exit(0);
		} finally {
			if (sa != null) {
				try {
					sa.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.error("Error", e2);
				}
			}
		}

		list.addAll(match(list1, list2));
	}

	public static void recommendOffers() {

		List<DashInstallBack> list1 = new ArrayList<DashInstallBack>();
		List<DashInstallBack> list2 = new ArrayList<DashInstallBack>();
		ResultSet rs = null;
		ResultSet sa = null;

		try {

			long stime = System.currentTimeMillis();
			String query = "select count(1) count,offerId,vendor from EDR_CLICK_EVENT where offerClicked>='"+date +" 18:30:00' group by offerId,vendor";
			rs = DBservice.selectdata(query);

			while (rs.next()) {

				if (rs.getInt("offerId") != 108) {

					for (Entry<Integer, Integer> entry : map.entrySet()) {
						if (rs.getInt("offerId") == entry.getKey()) {

							DashInstallBack db = new DashInstallBack();
							db.setOfferId(rs.getInt("offerId"));
							if (rs.getString("vendor") == null || rs.getString("vendor").equals("null")) {
								db.setVendor("");
							} else {
								db.setVendor(rs.getString("vendor"));
							}
							db.setClickCount(rs.getInt("count"));
							db.setIsOn(0);

							list1.add(db);
						}
					}
				}
			}

			long etime = System.currentTimeMillis();
			logger.debug(query + ", Time: " + (double) (etime - stime) / 1000);

			stime = System.currentTimeMillis();
			String que = "select count(1) count,offerId,vendor from RecoCallBackConfirmation where createdTime>='"+date +" 18:30:00' group by offerId,vendor";
			sa= DBservice.selectdata(que);

			while (sa.next()) {

				if (sa.getInt("offerId") != 108) {

					for (Entry<Integer, Integer> entry : map.entrySet()) {
						if (sa.getInt("offerId") == entry.getKey()) {
							DashInstallBack dib = new DashInstallBack();
							dib.setOfferId(sa.getInt("offerId"));
							if (sa.getString("vendor") == null || sa.getString("vendor").equals("null")) {
								dib.setVendor("");
							} else {
								dib.setVendor(sa.getString("vendor"));
							}
							dib.setCallbackCount(sa.getInt("count"));
							dib.setIsOn(0);

							list2.add(dib);
						}
					}
				}
			}

			etime = System.currentTimeMillis();
			logger.debug(que + ", Time: " + (double) (etime - stime) / 1000);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error :", e);
			System.exit(0);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.error("Error", e2);
				}
			}
			if (sa != null) {
				try {
					sa.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.error("Error", e2);
				}
			}
		}

		list.addAll(rOfferId(match(list1, list2)));
	}

	public static List<DashInstallBack> match(List<DashInstallBack> list1, List<DashInstallBack> list2) {

		int l1size = list2.size();
		int l2size = list1.size();

		for (int i = 0; i < l1size; i++) {

			boolean isThere = false;
			DashInstallBack db = list2.get(i);

			for (int j = 0; j < l2size; j++) {

				DashInstallBack dib = list1.get(j);

				if (db.getOfferId() == (dib.getOfferId()) && (db.getVendor().toUpperCase()).equals(dib.getVendor().toUpperCase())) {

					dib.setCallbackCount(db.getCallbackCount());
					isThere = true;
				}
			}

			if (isThere != true) {
				DashInstallBack db2 = new DashInstallBack();

				db2.setOfferId(db.getOfferId());
				db2.setVendor(db.getVendor());
				db2.setCallbackCount(db.getCallbackCount());

				list1.add(db2);
			}
		}

		return list1;
	}

	private static void offerdetailscheck() {

		List<OfferDetailsCheck> li = new ArrayList<OfferDetailsCheck>();
		ResultSet ls = null;

		try {
			long stime = System.currentTimeMillis();
			String query = "select offerId,offerName,status,vendor from OfferDetails";
			ls = DBservice.selectdata(query);

			while (ls.next()) {
				OfferDetailsCheck dc = new OfferDetailsCheck();

				dc.setOfferId(ls.getInt("offerId"));
				dc.setOfferName(ls.getString("offerName"));
				dc.setIsOn(ls.getInt("status"));

				if (ls.getString("vendor") == null || ls.getString("vendor").equals("null")) {
					dc.setVendor("");
				} else {
					dc.setVendor(ls.getString("vendor"));
				}
				li.add(dc);
			}

			long etime = System.currentTimeMillis();
			logger.debug(query + ", Time: " + (double) (etime - stime) / 1000);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error :", e);
			System.exit(0);
		} finally {
			if (ls != null) {
				try {
					ls.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.error("Error", e2);
				}
			}
		}

		try {
			int sizes = list.size();
			for (int k = 0; k < sizes; k++) {

				DashInstallBack dbi = list.get(k);

				for (int l = 0; l < li.size(); l++) {

					OfferDetailsCheck od = li.get(l);

					if (dbi.getOfferId() == od.getOfferId()) {

						dbi.setOfferName(od.getOfferName());

						if ((dbi.getVendor().toUpperCase()).equals(od.getVendor().toUpperCase())) {
							dbi.setIsOn(od.getIsOn());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error :", e);
			System.exit(0);
		}
	}

	// public static void printList(List<DashInstallBack> lis) {
	//
	// for (int k = 0; k < lis.size(); k++) {
	// System.out.print(lis.get(k).getOfferId());
	// System.out.print(" ," + lis.get(k).getOfferName());
	// System.out.print(" ," + lis.get(k).getIsOn());
	// System.out.print(" ," + lis.get(k).getVendor());
	// System.out.print(" ," + lis.get(k).getClickCount());
	// System.out.print(" ," + lis.get(k).getInstalledCount());
	// System.out.println(" ," + lis.get(k).getCallbackCount());
	//
	// }
	// }

	public static List<DashInstallBack> rOfferId(List<DashInstallBack> lis) {

		try {
			for (DashInstallBack d : lis) {
				for (Entry<Integer, Integer> entry : map.entrySet()) {
					if (d.getOfferId() == entry.getKey()) {
						d.setOfferId(entry.getValue());
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lis;
	}

	public static void recoOfferId() {

		ResultSet rs = null;
		try {

			long stime = System.currentTimeMillis();
			String query = "select offer_id,recoOfferOfferId from recommend_offer";
			rs = DBservice.selectdata(query);

			while (rs.next()) {
				map.put(rs.getInt("offer_id"), rs.getInt("recoOfferOfferId"));
			}

			long etime = System.currentTimeMillis();
			logger.debug(query + ", Time: " + (double) (etime - stime) / 1000);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in recoOfferId", e);
			
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.error("Error", e2);
				}
			}
		}
	}
}
