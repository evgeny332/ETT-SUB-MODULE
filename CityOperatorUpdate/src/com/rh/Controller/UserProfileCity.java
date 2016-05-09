package com.rh.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.rh.Dao.DBservice;

public class UserProfileCity {

	final static Logger logger = Logger.getLogger(UserProfileCity.class);

	private static Map<String, String> myMap;

	public static void main(String[] args) {

		URL url;
		logger.debug("Program Start");
		int count = 0;
		int UpdateCount = 0;

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(new File("src/hashfile")));
			String li = null;
			myMap = new HashMap<String, String>();
			while ((li = reader.readLine()) != null) {
				if (li.contains("=")) {
					String[] strings = li.split("=");
					myMap.put(strings[0], strings[1]);
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			logger.error("FileNotFoundException", e1);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("IOException", e);
		}

		try {
			logger.debug("Into try block");
			String q = "SELECT * from UserProfile where updateDate between date(date_add(date_add(now(),INTERVAL 19800 SECOND),INTERVAL -1 DAY)) and date(date_add(now(),INTERVAL 19800 SECOND)) order by updateDate";
			PreparedStatement cs = DBservice.getPS(q);
			ResultSet rs = cs.executeQuery();

			while (rs.next()) {
				System.out.println("5");
				try {

					logger.debug("Into Resultset");
					long ettId = rs.getLong("ettId");
					double latitude = rs.getDouble("latitude");
					double longitude = rs.getDouble("longitude");
					String operator = rs.getString("operator");

					String op = getoperator(operator);

					if (latitude != 0) {

						String a = "http://nominatim.openstreetmap.org/reverse?format=json&lat=" + latitude + "&lon=" + longitude + "&zoom=18&addressdetails=1";
						url = new URL(a);
						URLConnection conn = url.openConnection();

						// open the stream and put it into BufferedReader
						BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						StringBuilder sb = new StringBuilder();

						String inputLine;
						while ((inputLine = br.readLine()) != null) {
							sb.append(inputLine + "\n");
							logger.debug(count);
							// System.out.println(count);
						}
						br.close();
						String line = sb.toString();

						String lis = ParseJson(line);
						// System.out.println(lis);
						logger.debug(lis);

						if (lis != null) {

							lis = lis.replace("�?", "a").replace("ī", "i").replace("ū", "u");
							String query = "update UserProfile set city = '" + lis + "', operator = '" + op + "' where ettId= " + ettId + "";
							PreparedStatement ps = DBservice.getPS(query);
							ps.executeUpdate();
							logger.debug("city, operator updated");
							UpdateCount++;
							logger.debug(UpdateCount);

						} else {

							String que = "update UserProfile set operator = '" + op + "'";
							PreparedStatement ts = DBservice.getPS(que);
							ts.executeUpdate();
							logger.debug("operator updated");
						}

					} else {

						String ry = "update UserProfile set operator = '" + op + "'";
						PreparedStatement vs = DBservice.getPS(ry);
						vs.executeUpdate();
						logger.debug("operator updated");
					}
					// Thread.sleep();

				} catch (MalformedURLException e) {
					e.printStackTrace();
					logger.error("MalformedUrlException", e);
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("IOException", e);
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error("SQLException", e);
				}

				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQLException", e);
		} catch (Exception es){
			es.printStackTrace();
			logger.error("Exception", es);
		}
	}

	private static String getoperator(String operator) {
		if (operator != null) {
			String[] opera = operator.split(",");

			for (Map.Entry<String, String> entry : myMap.entrySet()) {
				if (opera[0].toUpperCase().contains(entry.getKey())) {
					operator = entry.getValue();
					break;
				}
			}
			return operator;
		} else {
			logger.debug("operator is null");
			return operator;
		}
	}

	public static String ParseJson(String k) {

		String city = null;
		try {
			JSONObject obj = new JSONObject(k);
			JSONObject obj2 = obj.getJSONObject("address");
			if (obj2.has("city")) {
				city = obj2.getString("city");
			} else if (obj2.has("county")) {
				city = obj2.getString("county");

			} else if (obj2.has("town")) {
				city = obj2.getString("town");

			} else if (obj2.has("state_district")) {
				city = obj2.getString("state_district");
			}

		} catch (JSONException e) {
			e.printStackTrace();
			logger.error("JSONException", e);
		}
		return city;
	}
}