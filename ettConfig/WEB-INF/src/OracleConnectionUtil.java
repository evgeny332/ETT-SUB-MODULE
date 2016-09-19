import java.util.ResourceBundle;
import java.sql.*;
public class OracleConnectionUtil {
	//public static String strDBConnectString  = "jdbc:mysql://localhost/RND?useUnicode=true";//myResources.getString("dbconnect_string");
	public static String strDBConnectString  = "jdbc:mysql://earntalktime.cecz6xcsdkcj.us-east-1.rds.amazonaws.com:3306/ett?useUnicode=true&characterEncoding=UTF-8";//myResources.getString("dbconnect_string");
	public static String username            = "ettsuser";//myResources.getString("username");
	public static String password            = "EttP$55w04d";//myResources.getString("password");
	public static String db_type             = "MYSQL";//myResources.getString("db_type");
	public static synchronized Connection getConnection()throws SQLException
	{
		System.out.println("In OracleConnectionUtil.java: ["+strDBConnectString+"]  User Name is ["+username+"], Password is ["+password+"]");
		Connection dbConnection = null;
		try
		{
			if((db_type.toUpperCase()).equals("ORACLE"))
			{
                                System.out.println("connectiong with oracle");
				Class.forName("oracle.jdbc.driver.OracleDriver");
				System.out.println("In OracleConnectionUtil.java:  strDBConnectString ["+strDBConnectString+"]");
				dbConnection = DriverManager.getConnection(strDBConnectString,username,password);
				dbConnection.setAutoCommit(true);
				System.out.println("OracleOracleConnectionUtil :: DB[Oracle] : Entered");
			}
			else if((db_type.toUpperCase()).equals("MYSQL"))          
			{
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				System.out.println("In OracleConnectionUtil.java: MYSQL Drivers LOADED ");
				dbConnection =DriverManager.getConnection(strDBConnectString,username,password);
				dbConnection.setAutoCommit(true);
				System.out.println("OracleConnectionUtil :: DB[ MYSQL ] : Entered");
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
