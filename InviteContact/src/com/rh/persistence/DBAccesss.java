package com.rh.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DBAccesss {
	
	private static Log log = LogFactory.getLog(DBAccesss.class);
	static PreparedStatement   preparedStatement = null;
	static Connection con = null;
	static String query ="insert into InviteContactMsisdn (ettIdAparty,msisdn,status) values(?,?,?)";
	
	public static PreparedStatement getPreparedStatement(){
		try {
			if(con==null) {
				con = getConnection();
				preparedStatement = con.prepareStatement(query);
			}
			return preparedStatement;
		}catch(Exception ex) {
			log.info("error in getPreparedStatement:"+ex);
			ex.printStackTrace();
			System.exit(0);
		}
		return null;
		
	}
	
	public static  Connection getConnection() {
             try {
                     Class.forName("com.mysql.jdbc.Driver");
                     Connection con = DriverManager.getConnection("jdbc:mysql://earntalktime.cecz6xcsdkcj.us-east-1.rds.amazonaws.com:3306/ett?autoReconnect=true&useUnicode=true&connectionCollation=utf8_general_ci&characterSetResults=utf8","ettsuser","EttP$55w04d");
                     con.setAutoCommit(false);
                     return con;
             }
             catch(Exception ex) {
                     ex.printStackTrace();
                     System.out.println("[Error in getConnection["+ex+"]");
                     System.exit(0);
             }
             return null;
     }
}
