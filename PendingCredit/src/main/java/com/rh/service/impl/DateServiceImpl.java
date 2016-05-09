package com.rh.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.LocalTime;

import com.rh.service.DateService;

public class DateServiceImpl implements DateService{

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final long HOUR = 3600*1000;
	
	@Override
	public String getDBDate() {
		Calendar cal = Calendar.getInstance();
		try {
			/*java.util.Date dateNow= new java.util.Date();
			Time timeNow = new Time(dateNow.getTime());
			Calendar calTocamp = Calendar.getInstance();
			calTocamp.set(Calendar.HOUR, 0);
			calTocamp.set(Calendar.MINUTE, 0);
			calTocamp.set(Calendar.SECOND, 66600);
			Time timeForCamp = new Time(calTocamp.getTime().getTime());*/
			LocalTime timeNow = new LocalTime();
			LocalTime timeToCompare = new LocalTime(18, 30, 00);
			if(timeNow.isAfter(timeToCompare)){
				String date = sdf.format(cal.getTime())+" 18:30:00";
				return date;
			} else {
				cal.add(Calendar.DAY_OF_MONTH,-1);
				String date = sdf.format(cal.getTime())+" 18:30:00";
				return date;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public String getDateAfter24Hour(String todayDate) {
		try {
			java.util.Date dateCrr = sdf2.parse(todayDate);
			Date after24Hour = new Date(dateCrr.getTime() + 24 * HOUR);
			String date24 = sdf2.format(after24Hour);
			return date24;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
