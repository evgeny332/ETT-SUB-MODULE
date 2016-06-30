package com.rh.app;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.rh.Dao.DBservice;
import com.rh.entity.Astrology;
import com.rh.entity.CallBackConfirmation;
import com.rh.entity.OfferDetails;
import com.rh.entity.RedeemStatus;
import com.rh.entity.UserRedeem;
import com.rh.utility.BuildString;
import com.rh.utility.ConfigHolder;
import com.rh.utility.GetStatics;
import com.rh.utility.GetUserAccountSummary;
import com.rh.utility.SendMessage;

public class DashBMin implements Runnable {

	final static Logger logger = Logger.getLogger(DashBMin.class);

	public static List<OfferDetails> Offers;
	public static List<Integer> onOffer;
	public static List<Integer> offOffer;
	public static List<Integer> dataOffer;
	public static StringBuilder onusers;
	public static StringBuilder offusers;
	public static StringBuilder callbacks;
	public static StringBuilder recocallbacks;
	public static StringBuilder redeems;
	public static StringBuilder rstatus;
	public static StringBuilder installcallbs;
	public static StringBuilder alloffers;
	public static StringBuilder deferred;
	public static StringBuilder astrology;
	private static ConfigHolder holder = new ConfigHolder();

	public void run() {
		logger.debug(Thread.currentThread().getName() + " Start." + System.currentTimeMillis());
		processCommand();
		logger.debug(Thread.currentThread().getName() + " End." + System.currentTimeMillis());
	}

	public static void processCommand() {

		while (true) {

			offers();
			CallBackC();
			RecoCallBackC();
			UserRedeem();
			Astrology();
			GetUserAccountSummary.UserAccountS();
			SendMessage.sendMessage();

			try {
				onusers.setLength(0);
				offusers.setLength(0);
				callbacks.setLength(0);
//				recocallbacks.setLength(0);
				redeems.setLength(0);
				rstatus.setLength(0);
				installcallbs.setLength(0);
				alloffers.setLength(0);
				deferred.setLength(0);
				astrology.setLength(0);
				
				Thread.sleep(120000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error("Error :", e);
				System.exit(0);
			}
		}
	}

	public static void offers() {

		ResultSet rs = null;
		try {
			long stime = System.currentTimeMillis();
			List<OfferDetails> Deferred = new ArrayList<OfferDetails>();
			Offers = new ArrayList<OfferDetails>();
			onOffer = new ArrayList<Integer>();
			offOffer = new ArrayList<Integer>();
			dataOffer = new ArrayList<Integer>();
			String query = holder.OfferDetailsQuery;
			rs = DBservice.selectdata(query);

			while (rs.next()) {
				OfferDetails od = new OfferDetails();
				if (rs.getString("status").equals("0")) {

					offOffer.add(rs.getInt("offerId"));
					od.setOfferId(rs.getInt("offerId"));
					od.setAppKey(rs.getString("appKey"));
					od.setOfferAmount(rs.getDouble("offerAmount"));
					od.setOfferName(rs.getString("offerName"));
					if(rs.getInt("dataOfferType") == 2){
						dataOffer.add(rs.getInt("offerId"));
					}

				} else if (rs.getString("status").equals("1")) {

					onOffer.add(rs.getInt("offerId"));
					od.setOfferId(rs.getInt("offerId"));
					od.setAppKey(rs.getString("appKey"));
					od.setOfferAmount(rs.getDouble("offerAmount"));
					od.setOfferName(rs.getString("offerName"));
					if(rs.getInt("dataOfferType") == 2){
						dataOffer.add(rs.getInt("offerId"));
					}
					OfferDetails od1 = new OfferDetails();
					od1.setOfferId(rs.getInt("offerId"));
					od1.setPriority(rs.getInt("priority"));
					od1.setDeferredPayout(rs.getDouble("DeferredPayout"));
					od1.setUpfrontPayout(rs.getDouble("UpfrontPayout"));
					od1.setOfferAmount(rs.getDouble("offerAmount"));
					od1.setOfferName(rs.getString("offerName"));

					Deferred.add(od1);
				}

				Offers.add(od);
			}

			if (Deferred.size() > 0) {
				Collections.sort(Deferred, new Comparator<OfferDetails>() {
					@Override
					public int compare(final OfferDetails object1, final OfferDetails object2) {
						int c = Integer.compare(object1.getPriority(), (object2.getPriority()));
						if (c == 0) {
							c = Double.compare(object2.getOfferAmount(), (object1.getOfferAmount()));
						}
						return c;
					}
				});
			}

			deferred = BuildString.buildDeferred(Deferred);
			alloffers = BuildString.buildOffers(Offers);

			long etime = System.currentTimeMillis();
			logger.debug(query + ", Time: " + (double) (etime - stime) / 1000);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in offers: ", e);
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

	public static void CallBackC() {

		ResultSet rs = null;

		try {

			long stime = System.currentTimeMillis();
			List<CallBackConfirmation> CallBacks = new ArrayList<CallBackConfirmation>();
			String query = holder.CallBackQuery.replace("?", "'" + GetStatics.date + " 18:30'");
			rs = DBservice.selectdata(query);

			while (rs.next()) {
				CallBackConfirmation cbc = new CallBackConfirmation();

				cbc.setOfferId(rs.getInt("offerId"));
				cbc.setCount(rs.getInt("count"));
				cbc.setVendor(rs.getString("vendor"));

				CallBacks.add(cbc);
			}

			long etime = System.currentTimeMillis();
			logger.debug(query + ", Time: " + (double) (etime - stime) / 1000);

			callbacks = BuildString.buildCallBack(CallBacks);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in CallBackC: ", e);
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

	public static void RecoCallBackC() {

		ResultSet rs = null;
		try {

			long stime = System.currentTimeMillis();
			List<CallBackConfirmation> RecoCallBacks = new ArrayList<CallBackConfirmation>();
			String query = holder.RecoCallBackQuery.replace("?", "'" + GetStatics.date + " 18:30'");
			rs = DBservice.selectdata(query);

			while (rs.next()) {
				CallBackConfirmation rbc = new CallBackConfirmation();

				rbc.setOfferId(rs.getInt("offerId"));
				rbc.setCount(rs.getInt("cnt"));
				rbc.setVendor(rs.getString("vendor"));

				RecoCallBacks.add(rbc);
			}

			long etime = System.currentTimeMillis();
			logger.debug(query + ", Time: " + (double) (etime - stime) / 1000);

			if (RecoCallBacks.size() > 0) {
				recocallbacks = BuildString.buildCallBack(RecoCallBacks);
			} else {
				recocallbacks = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in RecoCallBackC: ", e);
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

	public static void UserRedeem() {

		ResultSet rs = null;
		try {

			long stime = System.currentTimeMillis();
			List<RedeemStatus> Rstatus = new ArrayList<RedeemStatus>();
			List<UserRedeem> Redeem = new ArrayList<UserRedeem>();
			String query = holder.RedeemQuery.replace("?", "" + GetStatics.RedeemId);
			rs = DBservice.selectdata(query);

			int ok = 0;
			int count = 0;
			String status = null;
			while (rs.next()) {
				UserRedeem ur = new UserRedeem();

				ur.setOperator(rs.getString("operator1"));
				ur.setStatus(rs.getString("status"));
				ur.setCount(rs.getInt("count"));

				Redeem.add(ur);

				RedeemStatus rms = new RedeemStatus();

				if (ok == 0) {

					status = rs.getString("status");
					count = rs.getInt("count");

					ok++;
				} else {
					if (status.equals(rs.getString("status"))) {
						count = count + rs.getInt("count");
					} else {
						rms.setStatus(status);
						rms.setCount(count);

						Rstatus.add(rms);

						status = rs.getString("status");
						count = rs.getInt("count");

					}
				}

			}
			RedeemStatus rms1 = new RedeemStatus();

			rms1.setStatus(status);
			rms1.setCount(count);
			Rstatus.add(rms1);

			if (Redeem.size() > 0) {
				Collections.sort(Redeem, new Comparator<UserRedeem>() {
					@Override
					public int compare(final UserRedeem object1, final UserRedeem object2) {
						int c = (object2.getOperator().compareTo(object1.getOperator()));
						if (c == 0) {
							c = (object2.getStatus().compareTo(object1.getStatus()));
						}
						return c;
					}
				});
			}

			long etime = System.currentTimeMillis();
			logger.debug(query + ", Time: " + (double) (etime - stime) / 1000);

			rstatus = BuildString.buildRedeemStatus(Rstatus);
			redeems = BuildString.buildRedeem(Redeem);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in UserRedeem: ", e);
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

	public static void Astrology() {

		ResultSet rs = null;
		try {
			long stime = System.currentTimeMillis();
			List<Astrology> astro = new ArrayList<Astrology>();
//			List<Astrology> taro = new ArrayList<Astrology>();
			String query = holder.AstroQuery.replace("#DATE#", GetStatics.date);
			rs = DBservice.selectdata(query);

			while (rs.next()) {
				Astrology astrology = new Astrology();

				if (rs.getInt("type") == 1) {
					astrology.setSunshine(rs.getString("sunshine"));
					astrology.setCount(rs.getInt("cnt"));

					astro.add(astrology);
				} 
			}

			long etime = System.currentTimeMillis();
			logger.debug(query + ", Time: " + (double) (etime - stime) / 1000);

			astrology = BuildString.buildAstrology(astro);
//			tarot = BuildString.buildAstrology(taro);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in Astrology: ", e);
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

	/*public static void Bollywood() {

		ResultSet rs = null;
		try {
			long stime = System.currentTimeMillis();
			List<Astrology> bolly = new ArrayList<Astrology>();
			String query = holder.BollywoodQuery.replace("#DATE#", GetStatics.date);
			rs = DBservice.selectdata(query);

			while (rs.next()) {
				String text = null;
				String text2 = null;
				Astrology astrology = new Astrology();

				if (rs.getString("Count").equals("Count")) {
					text = "Distinct Count";
					text2 = "Total Count";
				} else if (rs.getString("Count").equals("Read")) {
					text = "Distinct Read Count";
					text2 = "Total Read Count";
				}
				astrology.setSunshine(text2);
				astrology.setCount(rs.getInt("cnt2"));

				bolly.add(astrology);

				Astrology astrology2 = new Astrology();

				astrology2.setSunshine(text);
				astrology2.setCount(rs.getInt("cnt"));

				bolly.add(astrology2);
			}

			long etime = System.currentTimeMillis();
			logger.debug(query + ", Time: " + (double) (etime - stime) / 1000);

			bollywood = BuildString.buildAstrology(bolly);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in Astrology: ", e);
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
	}*/
}
