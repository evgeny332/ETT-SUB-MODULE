package com.rh.utility;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.rh.entity.DashInstallBack;

public class GetUtils {

	final static Logger logger = Logger.getLogger(GetUtils.class);
	private static URL url;

	public static String GetDate() {

		String date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fdf = new SimpleDateFormat("yyyy-MM-dd");

		Date now = new Date();
		String Date1 = sdf.format(now);
		logger.debug("Current Date :" + Date1);

		try {

			date = new String();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			Date date3 = cal.getTime();
			date = fdf.format(date3);
			// System.out.println(date);
			logger.debug("Date :" + date);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ParseException error :", e);
			System.exit(0);
		}

		return date;
	}

	public static void sendMessage(List<DashInstallBack> lis) {

		StringBuilder sb = new StringBuilder();

		int total = 0;
		int totalClickCount = 0;
		int totalInstalledCount = 0;
		int totalCallbackCount = 0;
		StringBuilder bls = new StringBuilder();
		for (int k = 0; k < lis.size(); k++) {
			if (lis.get(k).getOfferId() != 0) {
				total++;
				totalClickCount = totalClickCount + lis.get(k).getClickCount();
				totalInstalledCount = totalInstalledCount + lis.get(k).getInstalledCount();
				totalCallbackCount = totalCallbackCount + lis.get(k).getCallbackCount();
				
				if(lis.get(k).getClickCount()>2){
					bls.append(lis.get(k).getOfferId() + "," + lis.get(k).getOfferName().replace("&", "") + "," + lis.get(k).getVendor() + "," + lis.get(k).getIsOn() + "," + lis.get(k).getClickCount() + "," + lis.get(k).getInstalledCount()
							+ "," + lis.get(k).getCallbackCount() + "#");
				}
				
			}
		}
		bls.setLength(bls.length() - 1);
		sb.append("--,Total Offers = " + total + ",Total Counts,=," + totalClickCount + "," + totalInstalledCount + "," + totalCallbackCount + "#");
		sb.append(bls);

		logger.info(sb.toString());

		try {

			url = new URL("http://108.161.134.70/dashboard/ElectionResult");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			// conn.setConnectTimeout(50000);
			// conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
			// conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
			// System.out.println(conn.getResponseCode());

			String urlParameters = "var=SET_DAS&value=" + sb.toString() + "";

			conn.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			// InputStream error = ((HttpURLConnection) conn).getErrorStream();
			// System.out.println(error);
			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder bs = new StringBuilder();

			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				bs.append(inputLine + "\n");
			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error :", e);
			System.exit(0);
		}

	}
}
