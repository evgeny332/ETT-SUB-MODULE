package com.rh.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DBservice1 {

	final static Logger logger = Logger.getLogger(DBservice1.class);

	private static Connection con = null;

	static String host;
	static String driver;
	static String database;
	static String user;
	static String pass;
	static String port;

	static {

		try {

			Properties props = new Properties();
			props.load(DBservice1.class.getClassLoader().getResourceAsStream("db1.properties"));

			host = props.getProperty("host");
			driver = props.getProperty("driver");
			database = props.getProperty("database");
			user = props.getProperty("user");
			pass = props.getProperty("pass");
			port = props.getProperty("port");

		} catch (FileNotFoundException ex) {
			logger.error("FileNotFoundException", ex);
		} catch (IOException e) {
			logger.error("IOException", e);
		}

		try {
			if (con == null) {

				Class.forName("" + driver + "");
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "", "" + user + "", "" + pass + "");
				logger.info(con);
			}
		} catch (Exception e) {
			System.out.println("error" + e.getMessage());
			logger.error("Exception", e);
		}
	}

	public static void closeConnection() {
		if (con != null)
		{
			try {
				con.close();
			} catch (Exception ex) {
			}
		}
		con = null;
	}

	public static Connection connect() {
		return con;
	}

	public static int UpdateData(String DMquery) {
		try {
			Statement st = con.createStatement();
			int ur = st.executeUpdate(DMquery);
			return ur;
		} catch (Exception e) {
			System.out.println("update error" + e.getMessage());
			return -1;
		}
	}

	public static ResultSet selectdata(String Projquery) {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(Projquery);
			return rs;
		} catch (Exception e) {
			System.out.println(" select error" + e.getMessage());
			return null;
		}
	}

	public static PreparedStatement getPS(String query) {
		try {
			PreparedStatement pst = con.prepareStatement(query);
			return pst;
		} catch (Exception e) {
			logger.error("Exception", e);
			System.out.println("ps error" + e.getMessage());
			return null;
		}

	}

}
