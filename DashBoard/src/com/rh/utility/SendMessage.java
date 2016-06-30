package com.rh.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

import com.rh.app.DashBMin;
import com.rh.app.DashBminMin;

public class SendMessage {
	
	final static Logger logger = Logger.getLogger(SendMessage.class);
	private static URL url, url1, url2, url3, url4;

	public static void sendMessage() {

		if (DashBMin.rstatus != null && DashBminMin.preUsers != null && DashBminMin.users != null) {
			try {
				url = new URL("http://108.161.134.70/dashboard/UserAccountSummary?UserDetails=" + URLEncoder.encode(DashBminMin.users.toString(), "UTF-8") + "&PREUserDetails=" + URLEncoder.encode(DashBminMin.preUsers.toString(), "UTF-8") + "&Reddem=" + URLEncoder.encode(DashBMin.rstatus.toString(), "UTF-8") + "&flag=0");

				URLConnection conn1 = url.openConnection();

				// open the stream and put it into BufferedReader
				BufferedReader br = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
				StringBuilder bs = new StringBuilder();

				String inputLine;
				while ((inputLine = br.readLine()) != null) {
					bs.append(inputLine + "\n");
					logger.debug(bs);
					// logger.debug(bs);
				}
				br.close();
				
//				DashBminMin.users.setLength(0);

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error :", e);
				System.exit(0);
			}
		}

		try {
			String callbackurl;
			String earnBytes;
			if (DashBMin.recocallbacks == null) {
				callbackurl = "";
			} else {
				callbackurl = DashBMin.recocallbacks.toString();
			}
			/*if (DashBMin.earn_bytes == null) {
				earnBytes = "";
			} else {
				earnBytes = DashBMin.earn_bytes.toString();
			}*/

			url = new URL("http://108.161.134.70/dashboard/CallBackConfirmation?CallBack=" + URLEncoder.encode(DashBMin.callbacks.toString(), "UTF-8") + "&UserAccount=" + URLEncoder.encode(DashBMin.onusers.toString(), "UTF-8") + "&UserAccount0=" + URLEncoder.encode(DashBMin.offusers.toString(), "UTF-8") + "&CallBackReco=" + URLEncoder.encode(callbackurl, "UTF-8") + "&InstlCallBackData=" + URLEncoder.encode(DashBMin.installcallbs.toString(), "UTF-8") + "&flag=0");

			URLConnection conn = url.openConnection();

			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder bs = new StringBuilder();

			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				bs.append(inputLine + "\n");
				logger.debug(bs);
			}
			br.close();
			
			if (DashBMin.recocallbacks != null) {
    			DashBMin.recocallbacks.setLength(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error :", e);
			System.exit(0);
		}
		
		try {

			url1 = new URL("http://108.161.134.70/dashboard/UserRedeem?REDEEM=" + URLEncoder.encode(DashBMin.redeems.toString(), "UTF-8") + "&flag=0");

			URLConnection conn1 = url1.openConnection();

			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
			StringBuilder bs = new StringBuilder();

			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				bs.append(inputLine + "\n");
				logger.debug(bs);
			}
			br.close();
			DashBMin.redeems.setLength(0);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error :", e);
			System.exit(0);
		}

		try {

			String callbackurl;
			if (DashBMin.recocallbacks == null) {
				callbackurl = "";
			} else {
				callbackurl = DashBMin.recocallbacks.toString();
			}
			

			url2 = new URL("http://108.161.134.70/dashboardApp/CallBackConfirmation?CallBack=" + URLEncoder.encode(DashBMin.callbacks.toString(), "UTF-8") + "&UserAccount=" + URLEncoder.encode(DashBMin.onusers.toString(), "UTF-8") + "&UserAccount0=" + URLEncoder.encode(DashBMin.offusers.toString(), "UTF-8") + "&CallBackReco=" + URLEncoder.encode(callbackurl, "UTF-8") +"&flag=0");

			URLConnection conn2 = url2.openConnection();

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

		try {

			url3 = new URL("http://108.161.134.70/dashboardApp/UserRedeem?REDEEM=" + URLEncoder.encode(DashBMin.redeems.toString(), "UTF-8") + "&flag=0");

			URLConnection conn2 = url3.openConnection();

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

		try {

//			System.out.println(DashBMin.deferred.toString());

			url4 = new URL("http://108.161.134.70/dashboardApp/AllOfferDetails?Deferred=" + URLEncoder.encode(DashBMin.deferred.toString(), "UTF-8") + "&flag=2");

			URLConnection conn2 = url4.openConnection();

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
		
		try {

//			System.out.println(DashBMin.deferred.toString());

			url4 = new URL("http://108.161.134.70/dashboard/Mobiquiti?astrology=" + URLEncoder.encode(DashBMin.astrology.toString(), "UTF-8") + "&flag=2");

			URLConnection conn2 = url4.openConnection();

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
		
		/*try {

//			System.out.println(DashBMin.deferred.toString());
			url4 = new URL("http://108.161.134.70/dashboard/Mobiquiti?tarot=" + URLEncoder.encode(DashBMin.tarot.toString(), "UTF-8") + "&flag=4");

			URLConnection conn2 = url4.openConnection();

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
		}*/
		
//		try {
//
//			System.out.println(DashBMin.deferred.toString());
//
//			url4 = new URL("http://108.161.134.70/dashboard/Mobiquiti?bolly=" + URLEncoder.encode(DashBMin.bollywood.toString(), "UTF-8") + "&flag=6");
//
//			URLConnection conn2 = url4.openConnection();
//
//			// open the stream and put it into BufferedReader
//			BufferedReader br = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
//			StringBuilder bs = new StringBuilder();
//
//			String inputLine;
//			while ((inputLine = br.readLine()) != null) {
//				bs.append(inputLine + "\n");
//				logger.debug(bs);
//			}
//			br.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("Error :", e);
//			System.exit(0);
//		}
		
	}
}
