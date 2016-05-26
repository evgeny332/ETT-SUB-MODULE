/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rh.dbo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.rh.utility.ConfigHolder;

public class DBservice {

	private static Connection con = null;
	static Logger error = Logger.getLogger("error");
//	static Logger logger = Logger.getLogger("Info");
	// static Logger logger = Logger.getLogger(DBservice.class);
	static {
		
		try {
			if (con == null) {
				String host = ConfigHolder.host;
				String User = ConfigHolder.user;
				String password = ConfigHolder.password;
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(host, User, password);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		 error.info("error in DBSERVICE" + e.getMessage());
		}
	}

	public static void closeConnection() {
		if (con != null)
			;
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
			error.info("update error" + e.getMessage());
			return -1;
		}
	}

	public static ResultSet selectdata(String Projquery) {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(Projquery);
			return rs;
		} catch (Exception e) {
			 error.info(" select error" + e.getMessage());
			return null;
		}
	}

	public static PreparedStatement getPS(String query) {
		try {
			PreparedStatement pst = con.prepareStatement(query);
			return pst;
		} catch (Exception e) {
			e.printStackTrace();
			error.info("ps error" + e.getMessage());
			return null;
		}

	}

}
