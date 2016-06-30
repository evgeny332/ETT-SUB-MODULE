/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rh.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 *
 * @author Hehe
 */
public class DBservice1 {

	final static Logger logger = Logger.getLogger(DBservice1.class);

	public static PreparedStatement ps1;
	public static PreparedStatement ps2;
	public static PreparedStatement ps3;
	public static PreparedStatement ps4;
	public static PreparedStatement ps5;
	public static PreparedStatement ps6;
	public static PreparedStatement ps7;
	public static PreparedStatement ps8;
	public static PreparedStatement ps9;
	public static PreparedStatement ps10;
	public static PreparedStatement ps11;

	private static Connection con0 = null;
	private static Connection con1 = null;
	private static Connection con2 = null;
	private static Connection con3 = null;
	private static Connection con4 = null;
	private static Connection con5 = null;
	private static Connection con6 = null;
	private static Connection con7 = null;
	private static Connection con8 = null;
	private static Connection con9 = null;
	private static Connection con10 = null;
	private static Connection con11 = null;


	static String host;
	static String driver;
	static String database;
	static String user;
	static String pass;
	static String port;

	static {
		try {
			Properties props = new Properties();	
			props.load(DBservice1.class.getClassLoader().getResourceAsStream("db.properties"));

			host = props.getProperty("host");
			driver = props.getProperty("driver");
			database = props.getProperty("database");
			user = props.getProperty("user");
			pass = props.getProperty("pass");
			port = props.getProperty("port");

			Class.forName("" + driver + "");

			if (con0 == null) {
				con0 = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "", "" + user + "", "" + pass + "");
			}
			
			if (con1 == null) {
				con1 = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "", "" + user + "", "" + pass + "");
			}
			if (con2 == null) {
				con2 = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "", "" + user + "", "" + pass + "");
			}
			if (con3 == null) {
				con3 = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "", "" + user + "", "" + pass + "");
			}
			if (con4 == null) {
				con4 = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "", "" + user + "", "" + pass + "");
			}
			if (con5 == null) {
				con5 = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "", "" + user + "", "" + pass + "");
			}
			if (con6 == null) {
				con6 = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "", "" + user + "", "" + pass + "");
			}
			if (con7 == null) {
				con7 = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "", "" + user + "", "" + pass + "");
			}
			if (con8 == null) {
				con8 = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "", "" + user + "", "" + pass + "");
			}
			if (con9 == null) {
				con9 = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "", "" + user + "", "" + pass + "");
			}
			if (con10 == null) {
				con10 = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "", "" + user + "", "" + pass + "");
			}
			if (con11 == null) {
				con11 = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "", "" + user + "", "" + pass + "");
			}
			
		} catch (Exception e) {
			System.out.println("error" + e.getMessage());
			logger.error("Exception", e);
		}
	}

	public static PreparedStatement getPS(String query) {
		try {
			PreparedStatement ps = con1.prepareStatement(query);
			return ps;
		} catch (Exception e) {
			logger.error("Exception", e);
			System.out.println("ps error" + e.getMessage());
			return null;
		}
	}
	
	public static ResultSet selectdata(String Projquery) {
		try {
			Statement st = con1.createStatement();
			ResultSet rs = st.executeQuery(Projquery);
			return rs;
		} catch (Exception e) {
			System.out.println(" select error" + e.getMessage());
			return null;
		}
	}
	
	public static ResultSet selectdata0(String Projquery) {
		try {
			Statement st = con0.createStatement();
			ResultSet rs = st.executeQuery(Projquery);
			return rs;
		} catch (Exception e) {
			System.out.println(" select error" + e.getMessage());
			return null;
		}
	}
	
	public static PreparedStatement getPS1(String query) {
		try {
			if (ps1 == null) {
				ps1 = con1.prepareStatement(query);
				return ps1;
			}else{
				return ps1;
			}
		} catch (Exception e) {
			logger.error("Exception", e);
			System.out.println("ps error" + e.getMessage());
			return null;
		}

	}
	
	public static PreparedStatement getPS2(String query) {
		try {
			if (ps2 == null) {
				ps2 = con2.prepareStatement(query);
				return ps2;
			}else{
				return ps2;
			}
		} catch (Exception e) {
			logger.error("Exception", e);
			System.out.println("ps error" + e.getMessage());
			return null;
		}

	}
	
	public static PreparedStatement getPS3(String query) {
		try {
			if (ps3 == null) {
				ps3 = con3.prepareStatement(query);
				return ps3;
			}else{
				return ps3;
			}
		} catch (Exception e) {
			logger.error("Exception", e);
			System.out.println("ps error" + e.getMessage());
			return null;
		}

	}
	
	public static PreparedStatement getPS4(String query) {
		try {
			if (ps4 == null) {
				ps4 = con4.prepareStatement(query);
				return ps4;
			}else{
				return ps4;
			}
		} catch (Exception e) {
			logger.error("Exception", e);
			System.out.println("ps error" + e.getMessage());
			return null;
		}

	}
	
	public static PreparedStatement getPS5(String query) {
		try {
			if (ps5 == null) {
				ps5 = con5.prepareStatement(query);
				return ps5;
			}else{
				return ps5;
			}
		} catch (Exception e) {
			logger.error("Exception", e);
			System.out.println("ps error" + e.getMessage());
			return null;
		}

	}
	
	public static PreparedStatement getPS6(String query) {
		try {
			if (ps6 == null) {
				ps6 = con6.prepareStatement(query);
				return ps6;
			}else{
				return ps6;
			}
		} catch (Exception e) {
			logger.error("Exception", e);
			System.out.println("ps error" + e.getMessage());
			return null;
		}

	}
	
	public static PreparedStatement getPS7(String query) {
		try {
			if (ps7 == null) {
				ps7 = con7.prepareStatement(query);
				return ps7;
			}else{
				return ps7;
			}
		} catch (Exception e) {
			logger.error("Exception", e);
			System.out.println("ps error" + e.getMessage());
			return null;
		}
	}
	
	public static PreparedStatement getPS8(String query) {
		try {
			if (ps8 == null) {
				ps8 = con8.prepareStatement(query);
				return ps8;
			}else{
				return ps8;
			}
		} catch (Exception e) {
			logger.error("Exception", e);
			System.out.println("ps error" + e.getMessage());
			return null;
		}
	}
	
	public static PreparedStatement getPS9(String query) {
		try {
			if (ps9 == null) {
				ps9 = con9.prepareStatement(query);
				return ps9;
			}else{
				return ps9;
			}
		} catch (Exception e) {
			logger.error("Exception", e);
			System.out.println("ps error" + e.getMessage());
			return null;
		}
	}
	
	public static PreparedStatement getPS10(String query) {
		try {
			if (ps10 == null) {
				ps10 = con10.prepareStatement(query);
				return ps10;
			}else{
				return ps10;
			}
		} catch (Exception e) {
			logger.error("Exception", e);
			System.out.println("ps error" + e.getMessage());
			return null;
		}
	}
	
	public static PreparedStatement getPS11(String query) {
		try {
			if (ps11 == null) {
				ps11 = con11.prepareStatement(query);
				return ps11;
			}else{
				return ps11;
			}
		} catch (Exception e) {
			logger.error("Exception", e);
			System.out.println("ps error" + e.getMessage());
			return null;
		}
	}

}
