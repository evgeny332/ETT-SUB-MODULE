package com.rh.Util;

import java.sql.*;


public class ConnectionUtil {
	
	private static Connection connection = null;
    private final static String ADRESS   = "jdbc:mysql://earntalktime.cecz6xcsdkcj.us-east-1.rds.amazonaws.com";
    private final static String DATABASE = "ett";
    private final static String USER     = "ettsuser";
    private final static String PASSWORD = "EttP$55w04d";
    private final static String PORT     = "3306";
    private final static String DRIVER   = "com.mysql.jdbc.Driver";
    
    private static void loadDriver() {
        try {
            Class.forName(DRIVER).newInstance();
        }
        catch (Exception e) {
            errorHandler("Failed to load the driver " + DRIVER, e);
        }
    }

    private static void loadConnection() {
        try {
            connection = DriverManager.getConnection(getFormatedUrl(), USER, PASSWORD);
        }
        catch (SQLException e) {
            errorHandler("Failed to connect to the database " + getFormatedUrl(), e);         
        }
    }
    
    private static void errorHandler(String message, Exception e) {
        System.out.println(message);  
        if (e != null) System.out.println(e.getMessage());   
    }

    private static String getFormatedUrl() {
        return ADRESS + ":" + PORT + "/" + DATABASE;
    }
    
    public static Connection getConnection() {
        if (connection == null) {
            loadDriver();
            loadConnection();
        }
        return connection;
    }
    
    public static void closeConnection() {
        if (connection == null) {
            errorHandler("No connection found", null);
        }
        else {
            try {
                connection.close();
                connection = null;
            }
            catch (SQLException e) {
                errorHandler("Failed to close the connection", e);
            }
        }
    }

	/*// public static String strDBConnectString =
	// "jdbc:mysql://localhost/RND?useUnicode=true";//myResources.getString("dbconnect_string");
	// public static String strDBConnectString =
	// "jdbc:mysql://localhost/Survey?useUnicode=true&characterEncoding=UTF-8";//myResources.getString("dbconnect_string");
	public static String strDBConnectString = "jdbc:mysql://earntalktime.cecz6xcsdkcj.us-east-1.rds.amazonaws.com:3306/ett?useUnicode=true&characterEncoding=UTF-8";
	public static String username = "ettsuser";// myResources.getString("username");
	public static String password = "EttP$55w04d";// myResources.getString("password");
	public static String db_type = "MYSQL";// myResources.getString("db_type");
	
	private final static Logger logger=Logger.getLogger(ConnectionUtil.class.getName());

	public static synchronized Connection getConnection() throws SQLException {
		
		System.out.println("In ConnectionUtil.java: [" + strDBConnectString
				+ "]  User Name is [" + username + "], Password is ["
				+ password + "]");
				
		Connection dbConnection = null;
		try {
			if ((db_type.toUpperCase()).equals("ORACLE")) {
				//System.out.println("connectiong with oracle");
				logger.info("connectiong with oracle");
				Class.forName("oracle.jdbc.driver.OracleDriver");
				//System.out.println("In ConnectionUtil.java:  strDBConnectString ["+ strDBConnectString + "]");
				dbConnection = DriverManager.getConnection(strDBConnectString,
						username, password);
				dbConnection.setAutoCommit(true);
				System.out.println("OracleConnectionUtil :: DB[Oracle] : Entered");
			} else if ((db_type.toUpperCase()).equals("MYSQL")) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				//System.out.println("In OracleConnectionUtil.java: MYSQL Drivers LOADED ");
				
				dbConnection = DriverManager.getConnection(strDBConnectString,
						username, password);
				dbConnection.setAutoCommit(true);
				//System.out.println("ConnectionUtil :: DB[ MYSQL ] : Entered");
				logger.info("ConnectionUtil :: DB[ MYSQL ] : Entered");
			} else
				System.out.println("Unkonown DataBase");
				logger.debug("Unkonown DataBase");
		} catch (Exception ex) {
			System.out.println("ConnectionUtil :: Error in Reading File " + ex);
			ex.printStackTrace();
			logger.error("ConnectionUtil :: Error in Reading File " + ex);
		}
		return dbConnection;
	}*/
}
