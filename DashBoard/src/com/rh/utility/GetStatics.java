package com.rh.utility;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.rh.Dao.DBservice;

public class GetStatics implements Runnable{

	public static String date;
	public static long PreDayUserId;
	public static long TodayUserId;
	public static long USAId;
	public static long RedeemId;
	static String pdate;
	
	final static Logger logger = Logger.getLogger(GetStatics.class);
	
	@Override
	public void run() {
		logger.debug(Thread.currentThread().getName() + " Start." + System.currentTimeMillis());
		GetDate();
		GetPreDayIds();
		GetCurDayIds();
		logger.debug(Thread.currentThread().getName() + " End." + System.currentTimeMillis());
		
	}
	
	private void GetPreDayIds() {
		
		long stime = System.currentTimeMillis();
		try {
			String query = "select ettId, min(createdTime) from User where createdTime>'"+pdate+" 18:30'";
			ResultSet rs = DBservice.selectdata(query);
			
			while(rs.next()){
				PreDayUserId = rs.getLong("ettId");
			}
			
			long etime = System.currentTimeMillis();
			logger.debug(query+ ", Time: " + (double) (etime - stime) / 1000);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("GetPreDayIds :", e);
			System.exit(0);
		}
	}
	
	private void GetCurDayIds() {
		try {
			long stime = System.currentTimeMillis();
			String query = "select ettId, min(createdTime) from User where createdTime>'"+date+" 18:30'";
			ResultSet rs = DBservice.selectdata(query);
			
			while(rs.next()){
				TodayUserId = rs.getLong("ettId");
			}
			
			long etime = System.currentTimeMillis();
			logger.debug(query+ ", Time: " + (double) (etime - stime) / 1000);
			
			stime = System.currentTimeMillis();
			String que = "select id, min(createdTime) from UserAccountSummary where createdTime>'"+date+" 18:30'";
			ResultSet rs1 = DBservice.selectdata(que);
			
			while(rs1.next()){
				USAId = rs1.getLong("id");
			}
			
			etime = System.currentTimeMillis();
			logger.debug(que+ ", Time: " + (double) (etime - stime) / 1000);
			
			stime = System.currentTimeMillis();
			String quer = "select id, min(createdTime) from UserRedeem where createdTime>'"+date+" 18:30'";
			ResultSet rs2 = DBservice.selectdata(quer);
			
			while(rs2.next()){
				RedeemId = rs2.getLong("id");
			}
			
			etime = System.currentTimeMillis();
			logger.debug(quer+ ", Time: " + (double) (etime - stime) / 1000);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("GetCurDayIds :", e);
			System.exit(0);
		}
	}
	


	private void GetDate(){
		SimpleDateFormat fdf = new SimpleDateFormat("yyyy-MM-dd");

		try {

			date = new String();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			Date date3 = cal.getTime();
			date = fdf.format(date3);
			
			cal.add(Calendar.DATE, -1);
			Date date2 = cal.getTime();
			pdate = fdf.format(date2);
			logger.debug("Date :" + date);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ParseException error :", e);
			System.exit(0);
		}
	}

}
