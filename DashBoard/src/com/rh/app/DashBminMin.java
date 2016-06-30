package com.rh.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.rh.Dao.DBservice;
import com.rh.utility.BuildString;
import com.rh.utility.ConfigHolder;
import com.rh.utility.GetStatics;

public class DashBminMin implements Runnable {

	final static Logger logger = Logger.getLogger(DashBminMin.class);
	private static URL url1;
	public static StringBuilder users;
	public static StringBuilder preUsers;
	private static ConfigHolder holder = new ConfigHolder();

	@Override
	public void run() {
		logger.debug(Thread.currentThread().getName() + " Start." + System.currentTimeMillis());
		processCommand();
		logger.debug(Thread.currentThread().getName() + " End." + System.currentTimeMillis());
	}

	public static void processCommand() {

		while (true) {

			Users();
			sendMessage(DashBMin.rstatus, users, preUsers);

			try {
				Thread.sleep(3600000);
			} catch (InterruptedException e) {
				logger.error("error in main ", e);
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	public static void Users() {

		ResultSet ds = null;
		ResultSet ds1 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		ResultSet rs6 = null;
		ResultSet rs7 = null;
		PreparedStatement ps = null;
		PreparedStatement fd = null;
		long sti = 0;
		long eti = 0;
		int[] array = new int[6];
		int[] yarray = new int[3];

		try {

			sti = System.currentTimeMillis();
			String que = holder.TotalUserQuery;
			ds = DBservice.selectdata(que);

			// Overall Registrations
			while (ds.next()) {
				array[0] = ds.getInt("count");
				logger.debug(array[0]);
			}
			eti = System.currentTimeMillis();
			logger.debug(que + ", Time:" + (double) (eti - sti) / 1000);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error", e);
		} finally {
			if (ds != null) {
				try {
					ds.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.error("Error", e2);
				}
			}
		}
		
		try {
			sti = System.currentTimeMillis();
			String quer = holder.TotalNotVerifUserQuery;
			ds1 = DBservice.selectdata(quer);
			while (ds1.next()) {
				array[2] = ds1.getInt("count");
				logger.debug(array[2]);
			}
			eti = System.currentTimeMillis();
			logger.debug(quer + ", Time:" + (double) (eti - sti) / 1000);

			array[1] = array[0] - array[2];
			logger.debug(array[1]);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error", e);
		} finally {
			if (ds1 != null) {
				try {
					ds1.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.error("Error", e2);
				}
			}
		}

		try {
			sti = System.currentTimeMillis();
			String query = holder.DayUserQuery;
			ps = DBservice.getPS(query);

			// Today Registrations
			ps.setLong(1, GetStatics.TodayUserId);
			ps.setLong(2, 9223372036854775807L);
			rs3 = ps.executeQuery();
			while (rs3.next()) {
				array[3] = rs3.getInt("count");
				logger.debug(array[3]);
			}
			eti = System.currentTimeMillis();
			logger.debug(query.replace("?", "" + GetStatics.TodayUserId + "") + ", Time: " + (double) (eti - sti) / 1000);
			
			sti = System.currentTimeMillis();
			ps.setLong(1, GetStatics.PreDayUserId);
			ps.setLong(2, GetStatics.TodayUserId);
			rs6 = ps.executeQuery();
			while (rs6.next()) {
				yarray[0] = rs6.getInt("count");
				logger.debug(yarray[0]);
			}
			eti = System.currentTimeMillis();
			logger.debug(query.replace("?", "" + GetStatics.TodayUserId + "") + ", Time: " + (double) (eti - sti) / 1000);
			

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error", e);
		} finally {
			if (rs3 != null) {
				try {
					rs3.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.error("Error", e2);
				}
			}
			if (rs6 != null) {
				try {
					rs6.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.error("Error", e2);
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.error("Error", e2);
				}
			}
		}

		try{
			sti = System.currentTimeMillis();
			String query1 = holder.DayNotVerifUserQuery;
			fd = DBservice.getPS(query1);

			fd.setLong(1, GetStatics.TodayUserId);
			fd.setLong(2, 9223372036854775807L);
			rs4 = fd.executeQuery();
			while (rs4.next()) {
				array[5] = rs4.getInt("count");
				logger.debug(array[5]);
			}
			eti = System.currentTimeMillis();
			logger.debug(query1.replace("?", "" + GetStatics.TodayUserId + "") + ", Time: " + (double) (eti - sti) / 1000);

			// PRE_User_Details
			sti = System.currentTimeMillis();
			fd.setLong(1, GetStatics.PreDayUserId);
			fd.setLong(2, GetStatics.TodayUserId);

			rs7 = fd.executeQuery();
			while (rs7.next()) {
				yarray[2] = rs7.getInt("count");
				logger.debug(yarray[2]);
			}
			eti = System.currentTimeMillis();
			logger.debug(query1.replace("?", "" + GetStatics.PreDayUserId + "") + ", Time: " + (double) (eti - sti) / 1000);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error", e);
			System.exit(0);
		} finally {
			if (rs4 != null) {
				try {
					rs4.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.error("Error", e2);
				}
			}
			if (rs7 != null) {
				try {
					rs7.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.error("Error", e2);
				}
			}
			if (fd != null) {
				try {
					fd.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.error("Error", e2);
				}
			}
		}	

		array[4] = array[3] - array[5];
		logger.debug(array[4]);

		yarray[1] = yarray[0] - yarray[2];
		logger.debug(yarray[1]);

		users = BuildString.buildUserDetails(array);
		preUsers = BuildString.buildUserDetails(yarray);

	}

	public static void sendMessage(StringBuilder sbredm, StringBuilder sbusers, StringBuilder sbpreUsers) {

		try {

			url1 = new URL("http://108.161.134.70/dashboardApp/UserAccountSummary?UserDetails=" + URLEncoder.encode(sbusers.toString(), "UTF-8") + "&PREUserDetails=" + URLEncoder.encode(sbpreUsers.toString(), "UTF-8") + "&Reddem=" + URLEncoder.encode(sbredm.toString(), "UTF-8") + "&flag=0");

			URLConnection conn2 = url1.openConnection();

			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
			StringBuilder bs = new StringBuilder();

			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				bs.append(inputLine + "\n");
				logger.debug(bs);
			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error :", e);
			System.exit(0);
		}

	}
}
