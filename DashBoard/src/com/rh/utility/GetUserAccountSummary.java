package com.rh.utility;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rh.Dao.DBservice;
import com.rh.app.DashBMin;
import com.rh.entity.OfferDetails;
import com.rh.entity.UserAccount;
import com.rh.entity.UserAccountSummary;

public class GetUserAccountSummary {

	final static Logger logger = Logger.getLogger(GetUserAccountSummary.class);
	public static ConfigHolder holder = new ConfigHolder();

	public static void UserAccountS() {

		ResultSet rs = null;
		try {

			long stime = System.currentTimeMillis();
			List<UserAccount> onUsers = new ArrayList<UserAccount>();
			List<UserAccount> offUsers = new ArrayList<UserAccount>();
			List<UserAccountSummary> callbacks = new ArrayList<UserAccountSummary>();
			List<UserAccountSummary> installs = new ArrayList<UserAccountSummary>();
			List<UserAccountSummary> InstallCallBackData = new ArrayList<UserAccountSummary>();

			String query = holder.UsaQuery.replace("?", "" + GetStatics.USAId);
			;
			rs = DBservice.selectdata(query);

			int rl = 0;
			int rk = 0;
			int offerId = 0;
			int kofferId = 0;
			int count = 0;
			int kcount = 0;
			int sum = 0;
			int ksum = 0;
			String offerName = null;

			logger.debug("outside resltset");
			UserAccountSummary uas = new UserAccountSummary();

			while (rs.next()) {

				if (!rs.getString("remarks").equals("PENDING_CREDIT") && !rs.getString("remarks").equals("INSTALL") && !rs.getString("remarks").equals("DATA_USAGE")) {
					/*if(rs.getString("remarks").equals("Suspect Offer")){
						
						UserAccountSummary uas1 = new UserAccountSummary();

						uas1.setOfferId(rs.getInt("offerId"));
						uas1.setAppKey(rs.getString("remarks"));
						uas1.setCallbackAmount(rs.getInt("sum"));
						uas1.setCallbackCount(rs.getInt("count"));
						uas1.setInstallAmount(0);
						uas1.setInstallCount(0);
						
						callbacks.add(uas1);
					}
					else*/ if (rs.getString("remarks").equals("convenience charge") || rs.getString("remarks").equals("convenience charge on browseplans")) {

						if (rs.getInt("offerId") == 8884) {
							UserAccount ua1 = new UserAccount();
							ua1.setOfferId(rs.getInt("offerId"));
							ua1.setCount(rs.getInt("count"));
							ua1.setOfferName("conv gift");
							ua1.setSum(rs.getInt("sum"));

							onUsers.add(ua1);
						}
						else{
							UserAccount ua1 = new UserAccount();
							ua1.setOfferId(rs.getInt("offerId"));
							ua1.setCount(rs.getInt("count"));
							ua1.setOfferName("conv redemption");
							ua1.setSum(rs.getInt("sum"));

							onUsers.add(ua1);
						}
						

					} else if (rs.getString("remarks").equals("convenience charge on FGV")) {

						UserAccount ua1 = new UserAccount();
						ua1.setOfferId(rs.getInt("offerId"));
						ua1.setCount(rs.getInt("count"));
						ua1.setOfferName("conv Voucher");
						ua1.setSum(rs.getInt("sum"));

						onUsers.add(ua1);
					} else if (rs.getString("remarks").equals("FLIPKART")) {

						UserAccount ua1 = new UserAccount();
						ua1.setOfferId(rs.getInt("offerId"));
						ua1.setCount(rs.getInt("count"));
						ua1.setOfferName("GiftVoucher Flipkart");
						ua1.setSum(rs.getInt("sum"));

						onUsers.add(ua1);
					} else {
						if (rl == 0) {

							offerId = rs.getInt("offerId");
							count = rs.getInt("count");
							offerName = rs.getString("offerName");
							sum = rs.getInt("sum");

							rl++;

						} else {
							if (offerId == rs.getInt("offerId")) {

								count = count + rs.getInt("count");
								sum = sum + rs.getInt("sum");

							} else {
								if (DashBMin.onOffer.contains(offerId) || offerId == 0 || offerId == 121 || offerId == 921 || offerId == 920) {

									UserAccount ua = new UserAccount();

									ua.setOfferId(offerId);
									ua.setCount(count);
									ua.setOfferName(offerName);
									ua.setSum(sum);

									offerId = rs.getInt("offerId");
									count = rs.getInt("count");
									offerName = rs.getString("offerName");
									sum = rs.getInt("sum");

									onUsers.add(ua);

								} else if (DashBMin.offOffer.contains(offerId)) {

									UserAccount ua1 = new UserAccount();

									ua1.setOfferId(offerId);
									ua1.setCount(count);
									ua1.setOfferName(offerName);
									ua1.setSum(sum);

									offerId = rs.getInt("offerId");
									count = rs.getInt("count");
									offerName = rs.getString("offerName");
									sum = rs.getInt("sum");

									offUsers.add(ua1);
								}
							}
						}
					}
				}
				if(rs.getString("remarks").equals("INSTALL")){
					
					UserAccountSummary uas2 = new UserAccountSummary();
					uas2.setOfferId(rs.getInt("offerId"));
					uas2.setInstallAmount(rs.getInt("sum"));
					uas2.setInstallCount(rs.getInt("count"));
					
					installs.add(uas2);
					
				} else if (holder.checkConfigOffers(rs.getInt("offerId"))) {
					if (!rs.getString("remarks").equals("convenience charge")) {
						UserAccountSummary uas3 = new UserAccountSummary();

						if (rk == 0) {

							kofferId = rs.getInt("offerId");
							kcount = rs.getInt("count");
							ksum = rs.getInt("sum");

							rk++;
						} else {
							if (kofferId == rs.getInt("offerId")) {

								kofferId = rs.getInt("offerId");
								kcount = kcount + rs.getInt("count");
								ksum = ksum + rs.getInt("sum");

							} else {

								uas3.setOfferId(kofferId);
								uas3.setInstallAmount(ksum);
								uas3.setInstallCount(kcount);

								kofferId = rs.getInt("offerId");
								kcount = rs.getInt("count");
								ksum = rs.getInt("sum");

								installs.add(uas3);
							}
						}
					}
				} else if (rs.getString("remarks").equals("CALLBACK")) {

					UserAccountSummary uas1 = new UserAccountSummary();

					uas1.setOfferId(rs.getInt("offerId"));
					uas1.setCallbackAmount(rs.getInt("sum"));
					uas1.setCallbackCount(rs.getInt("count"));

					callbacks.add(uas1);
				}
			}

			uas.setOfferId(kofferId);
			uas.setInstallAmount(ksum);
			uas.setInstallCount(kcount);
//			installs.add(uas);

			long etime = System.currentTimeMillis();
			logger.debug(query + ", Time: " + (double) (etime - stime) / 1000);

//			DashBMin.earn_bytes = BuildString.buildUserAccount(dataUsage);
			Iterator<UserAccount> it = onUsers.iterator();
			while (it.hasNext()) {
	            if (DashBMin.dataOffer.contains(it.next().getOfferId())) {
	                it.remove();
	            }
	        }
			Iterator<UserAccount> its = offUsers.iterator();
			while (its.hasNext()) {
	            if (DashBMin.dataOffer.contains(its.next().getOfferId())) {
	                its.remove();
	            }
	        }
			
			DashBMin.onusers = BuildString.buildUserAccount(onUsers);
			DashBMin.offusers = BuildString.buildUserAccount(offUsers);

			List<UserAccountSummary> newList = new ArrayList<UserAccountSummary>(installs);
			newList.addAll(InstallEdrHold());

			InstallCallBackData = GetAppKey(match(newList, callbacks), holder);
			DashBMin.installcallbs = BuildString.buildInstallCB(InstallCallBackData);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in UaS: ", e);
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
	}

	public static List<UserAccountSummary> InstallEdrHold() {

		ResultSet rs = null;
		List<UserAccountSummary> installonHold = new ArrayList<UserAccountSummary>();
		try {
			long stime = System.currentTimeMillis();
			String query = holder.InstallEdrQuery.replace("?", "'" + GetStatics.date + " 18:30'");
			rs = DBservice.selectdata(query);

			while (rs.next()) {
				UserAccountSummary inus = new UserAccountSummary();
				inus.setOfferId(rs.getInt("offerId"));
				inus.setInstallAmount(rs.getInt("sum"));
				inus.setInstallCount(rs.getInt("count"));
				inus.setAppKey(rs.getString("appKey"));

				installonHold.add(inus);
			}

			long etime = System.currentTimeMillis();
			logger.debug(query + ", Time: " + (double) (etime - stime) / 1000);

			return installonHold;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in InstallEdrHold: ", e);
			System.exit(0);
			return installonHold;
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
	
	public static List<UserAccountSummary> remove(){
		
		
		return null;
	}

	public static List<UserAccountSummary> match(List<UserAccountSummary> list1, List<UserAccountSummary> list2) {

		int l2size = list2.size();
		int l1size = list1.size();
		// logger.debug(l1size);

		for (int i = 0; i < l2size; i++) {

			boolean isThere = false;
			UserAccountSummary dib = list2.get(i);

			for (int j = 0; j < l1size; j++) {

				UserAccountSummary db = list1.get(j);
				if (db.getOfferId() == dib.getOfferId()) {

					db.setCallbackCount(dib.getCallbackCount());
					db.setCallbackAmount(dib.getCallbackAmount());
					isThere = true;
					break;
				}
			}

			if (isThere != true) {
				UserAccountSummary db2 = new UserAccountSummary();

				db2.setOfferId(dib.getOfferId());
				db2.setInstallAmount(0);
				db2.setInstallCount(0);
				db2.setCallbackCount(dib.getCallbackCount());
				db2.setCallbackAmount(dib.getCallbackAmount());

				list1.add(db2);
			}
		}
		return list1;
	}

	public static List<UserAccountSummary> GetAppKey(List<UserAccountSummary> list, ConfigHolder configHolder) {

		int of = DashBMin.Offers.size();
		int li = list.size();
		Map<Integer, String> myMap = configHolder.getMymap();
		// boolean hasOccurred = false;
		for (int i = 0; i < of; i++) {
			OfferDetails odt = DashBMin.Offers.get(i);

			for (int j = 0; j < li; j++) {

				UserAccountSummary usa = list.get(j);

				if (usa.getOfferId() == 108) {
					if (usa.getAppKey() == null){
						usa.setAppKey("Amazon Shopping");
					}
					else {
						usa.setAppKey("Amazon Suspects");
						usa.setAppKey(myMap.get(usa.getOfferId()).toString());
					}
//					usa.setAppKey("Amazon Suspects");
				} else {
					if (usa.getOfferId() == odt.getOfferId()) {
						usa.setAppKey((odt.getAppKey()).replace("#", ""));
					} else if (myMap.containsKey(usa.getOfferId())) {
						usa.setAppKey(myMap.get(usa.getOfferId()).toString());
					}
				}
			}
		}
		return list;
	}
}
