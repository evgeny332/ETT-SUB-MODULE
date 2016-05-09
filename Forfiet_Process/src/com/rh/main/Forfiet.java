package com.rh.main;

import java.io.*;
import java.lang.*;

import com.rh.Util.ConnectionUtil;

import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

class Forfiet {
	public static ArrayList ETTID = new ArrayList();
	public static HashMap OFFER = new HashMap();
	static Properties configFile=new Properties();
	public static String days="";
	public static String msg = null;
	private static boolean manageNonActive = false;
	public static final String USER_AGENT = "Mozilla/5.0";
	
	private final static Logger logger=Logger.getLogger(Forfiet.class.getName()); 

	public static void main(String args[]) {
		try {
			configFile.load(Forfiet.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			logger.error("Exception while loading properties file "+e);
			System.exit(-1);
		}
		days=configFile.getProperty("FORFIET_DAY_BEFORE").trim();
		logger.info("Forfiet day is :"+days);
		manageNonActive = Boolean.parseBoolean(configFile.getProperty("MANAGE_NON_ACTIVE_USER").trim());
		getETT_ID(ETTID);
		ForfeightAmt();
		logger.info("Forfiet Executed !!");
		ETTID.clear();
		if(manageNonActive){
			manageNonActiveUsers();
		}
		ConnectionUtil.closeConnection();
		logger.info("Process Done !! Connection closed..");
	}

	private static void manageNonActiveUsers() {
		List<Long> ettIds = new ArrayList<>();
		String SelectQuery = "";
		Statement selectStmt = null;
		Connection activeConnection = null;
		ResultSet DetailsResultSet = null;
		SelectQuery = "select ettId from User where date(updatedTime) = date(DATE_ADD(DATE_ADD(now(),INTERVAL -"+days+" DAY),INTERVAL -1 DAY)) and flage=1";
		
		try {
			activeConnection = ConnectionUtil.getConnection();
		} catch (Exception od) {
			logger.error("Forfiet.java:   Not able to connect with MYSQL"+od);
		}
			logger.info("Forfiet.java:   MYSQL Connection Created!");
		try {
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			while (DetailsResultSet.next()) {
				ettIds.add(DetailsResultSet.getLong("ettId"));
				logger.info("churn NonActive ettId="+ DetailsResultSet.getLong("ettId"));
			}
			DetailsResultSet.close();
			selectStmt.close();
			//activeConnection.close();
			logger.info("total nonActive Users to churn are : "+ettIds.size());
			
			if(ettIds.size()>0){
				churnNonActiveUsers(ettIds);
			}
		} catch (SQLException ESQL) {
			logger.error("SQL exception :"+ESQL.getMessage());
		} finally {
			try {
				selectStmt.close();
				//activeConnection.close();
			} catch (Exception ee) {
				ee.printStackTrace();
				logger.error("[ETT] []In Forfeight.java:    Exception: "+ ee.getMessage());
			}
		}
	}

	private static void churnNonActiveUsers(List<Long> ettIds) {
		for(Long id : ettIds){
			String deleteFromNotReg = "delete from InstalledAppsNotReg where ettId="+id;
			String moveInstallQuery = "insert into InstalledAppsNotReg (id,appKey,createdTs,ettId) select id,appKey,createdTs,ettId from InstalledApps where ettId="+id;
			String deleteInstallQuery = "delete from InstalledApps where ettId="+id;
			
			String moveProfileQuery = "insert into UserProfileActive0 select * from UserProfile where ettId="+id;
			String deleteProfileQuery = "delete from UserProfile where ettId="+id;

			String moveDTokenQuery = "insert into DeviceTokenActive0 select * from DeviceToken where ettId="+id;
			String deleteDTokenQuery = "delete from DeviceToken where ettId="+id;
			
			String updateFlag = "update User set flage=1 where ettId="+id;
			
			ExecuteQuery(deleteFromNotReg);
			logger.info("delete from InstallNotReg | "+deleteFromNotReg);
			ExecuteQuery(moveInstallQuery);
			logger.info("move InstalledApps to not Reg | "+moveInstallQuery);
			ExecuteQuery(deleteInstallQuery);
			logger.info("delete from Install | "+deleteInstallQuery);
			ExecuteQuery(moveProfileQuery);
			logger.info("move from UserProfile | "+moveProfileQuery);
			ExecuteQuery(deleteProfileQuery);
			logger.info("delete from UserProfile | "+deleteProfileQuery);
			ExecuteQuery(moveDTokenQuery);
			logger.info("move from DToken | "+moveDTokenQuery);
			ExecuteQuery(deleteDTokenQuery);
			logger.info("deleted from Dtoken | "+deleteDTokenQuery);
			ExecuteQuery(updateFlag);
			logger.info("update flag in User table | "+updateFlag);
		}
	}

	public static void getETT_ID(ArrayList ETTID) {
		String SelectQuery = "";
		Statement selectStmt = null;
		Connection activeConnection = null;
		ResultSet DetailsResultSet = null;
		SelectQuery = "select A.ettId,B.currentBalance from User A, UserAccount B where date(A.updatedTime)< date(DATE_ADD(now(),INTERVAL -"+days+" DAY)) and A.ettId=B.ettId and ROUND(B.currentBalance,2)>0.00 ";
		logger.info("Query : "+SelectQuery);
		try {
			activeConnection = ConnectionUtil.getConnection();
		} catch (Exception od) {
			logger.error("Forfiet.java:   Not able to connect with MYSQL"+od);
		}
			logger.info("Forfiet.java:   MYSQL Connection Created!");
		try {
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			while (DetailsResultSet.next()) {

				ETTID.add(DetailsResultSet.getString(1) + "-"
						+ DetailsResultSet.getString(2));
				logger.info("ettId="+ DetailsResultSet.getString(1)+" currentBalance="+DetailsResultSet.getString(2));
			}

			DetailsResultSet.close();
			selectStmt.close();
			//activeConnection.close();
			logger.info("query has been executed...!!!");
		} catch (SQLException ESQL) {
			logger.error("SQL exception :"+ESQL.getMessage());
		} finally {
			try {
				selectStmt.close();
				//activeConnection.close();
			} catch (Exception ee) {
				ee.printStackTrace();
				logger.error("[ETT] []In Forfeight.java:    Exception: "+ ee.getMessage());
			}
		}

	}

	public static void ExecuteQuery(String Query) {
		Statement selectStmt = null;
		Connection activeConnection = null;
		int Result = 0;
		try {
			activeConnection = ConnectionUtil.getConnection();
		} catch (Exception od) {
			logger.error("Forfiet.java: Not able to connect with MYSQL"+od);
		}
		try {
			selectStmt = activeConnection.createStatement();
			Result = selectStmt.executeUpdate(Query);
			selectStmt.close();

			//activeConnection.close();
			logger.info("query[" + Query + "] has been executed...!!!");
		} catch (SQLException ESQL) {
			logger.error("Forfeight.java/ExecuteQuery :  SQL Error"+ ESQL.getMessage());
		} 
	}
	
	private static java.sql.Timestamp  getCurrentDate() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp (today.getTime());
}

	public static void ExecuteProcedure(float amount,long ettId,long offerId,String remarks,String offerName) {
		//Statement selectStmt = null;
		Connection activeConnection = null;
		int Result = 0;
		try {
			activeConnection = ConnectionUtil.getConnection();
		} catch (Exception od) {
			logger.error("Forfiet.java: Not able to connect with MYSQL"+od);
		}
		try {
			
			CallableStatement cStmt = activeConnection.prepareCall("{call UserAccountUpdate(?,?,?,?,?,?,?,?,?,?)}");
			 cStmt.setFloat(1, amount);
			 cStmt.setTimestamp(2, getCurrentDate());
			 cStmt.setLong(3, ettId);
			 cStmt.setLong(4, offerId);
			 cStmt.setString(5, remarks);
			 cStmt.setString(6, offerName);
			 cStmt.setInt(7, 0);
			 cStmt.setString(8, "");
			 cStmt.setString(9, "");
			 cStmt.setInt(10, 1);
			// cStmt.execute();
			//selectStmt = activeConnection.createStatement();
			Result = cStmt.executeUpdate();
			cStmt.close();

			//activeConnection.close();
			logger.info("query[ ettId,amount] ["+ettId+","+amount+"]");
		} catch (SQLException ESQL) {
			logger.error("Forfeight.java/ExecuteQuery :  SQL Error"+ ESQL.getMessage());
		} 
	}

	public static void ForfeightAmt() {
		String offer = null;
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		try {
			for (int i = 0; i < ETTID.size(); i++) {
				String ETT = (String) ETTID.get(i);
				String sBuff[] = ETT.split("-");
				/*String InsertQuery = "insert into UserAccountSummary(amount,createdTime,ettId,offerId,remarks,offerName) 
				 * values(-"+ sBuff[1]+ ",'"+ currentTime+ "',"+ sBuff[0]+ ",9999,'Forfeited','Forfeited')";
				String UpdateQuery = "update UserAccount set currentBalance=currentBalance-"+ sBuff[1] + " where ettid=" + sBuff[0];
				
				ExecuteQuery(InsertQuery);
				ExecuteQuery(UpdateQuery);
*/
				ExecuteProcedure(Float.parseFloat("-"+sBuff[1]),Long.parseLong(sBuff[0]),9999,"Forfeited","Forfeited");
				
				}
		} catch (Exception ee) {
			logger.error("In Forfeight.java: Exception: when send msg "+ ee.getMessage());
		}

	}
}
