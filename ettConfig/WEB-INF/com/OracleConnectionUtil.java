package com;
import java.sql.*;
import java.util.*;
public class OracleConnectionUtil
{
	public static ResourceBundle myResources = ResourceBundle.getBundle("config");
	public static String strDBConnectString =myResources.getString("dbconnect_string");
	public static String username =myResources.getString("username");
	public static String password =myResources.getString("password");
	public static String db_type =myResources.getString("db_type");
	public OracleConnectionUtil(String dbConnectionString,String dbUser,String dbPass)
	{
                strDBConnectString      =       dbConnectionString;
                password                =       dbPass;
                username                =       dbUser;
	}
	public static synchronized Connection getConnection()throws SQLException
	{
		System.out.println("In OracleConnectionUtil.java:  User Name is ["+username+"], Password is ["+password+"]");
		Connection dbConnection = null;
		try
		{
			if((db_type.toUpperCase()).equals("ORACLE"))
			{
                                System.out.println("connectiong with oracle");
				Class.forName("oracle.jdbc.driver.OracleDriver");
				dbConnection = DriverManager.getConnection(strDBConnectString,username,password);
				dbConnection.setAutoCommit(true);
				System.out.println("OracleConnectionUtil :: DB[Oracle] : Entered");
			}
			else if((db_type.toUpperCase()).equals("POLYHEDRA"))          
			{
				Class.forName("com.polyhedra.jdbc.JdbcDriver").newInstance();
				System.out.println("In OracleConnectionUtil.java: Polyhedra Drivers LOADED ");
				dbConnection =DriverManager.getConnection(strDBConnectString,username,password);
				dbConnection.setAutoCommit(true);
				System.out.println("OracleConnectionUtil :: DB[Polyhedra] : Entered");
			}
			else
				System.out.println("Unkonown DataBase");
		}
		catch(Exception ex)
		{
			System.out.println("OracleConnectionUtil :: Error in Reading File " + ex);
			ex.printStackTrace();
		}
		return dbConnection;
	}
}
