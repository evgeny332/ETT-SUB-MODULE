package com.mcarbon;

import java.io.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;


public class DateUtil{

        public static void main(String [] argv){
                System.out.println( new DateUtil().incrementDecHourByGivenValue( argv[ 0 ], argv[ 1 ], argv[ 2 ].charAt( 0 ) ) );
        }

        public String incrementDecHourByGivenValue (String timeToIncStr, String dateToCheck, char actionOnTime ){
                String newDate = "";
                int timeToInc = 0000;
                String timeToIncStrRec = "";
                try{
                        timeToInc = Integer.parseInt (timeToIncStr);
                }catch (Exception e){
                        timeToInc = 0;
                }
                System.out.println ("original time is [" + timeToInc + "]");
                if (timeToInc != 0){
                        if (timeToInc == 2300){
                                if ( actionOnTime == 'I' ){
                                        timeToInc = 0;
                                        newDate = this.incrementDate (dateToCheck,1);
                                }else{
                                        timeToInc = timeToInc - 100;
                                        newDate = dateToCheck;
                                }
                        }else{
                                if ( actionOnTime == 'I' ){
                                        timeToInc = timeToInc + 100;
                                }else{
                                        timeToInc = timeToInc - 100;
                                }
                                newDate = dateToCheck;
                        }
                }else{
                        if ( actionOnTime == 'I' ){
                                timeToInc = timeToInc + 100;
                                newDate   = dateToCheck;
                        }else{
                                timeToInc = 2300;
                                newDate   = this.decrementDate( dateToCheck,1 );
                        }
                }
                if ( timeToInc < 1000 ){
                        timeToIncStrRec = "" + timeToInc;
                        for (int i = 0; i < ( 4 - (timeToInc + "").length() ); i++){
                                timeToIncStrRec = "0" + timeToIncStrRec;
                        }
                }else{
                        timeToIncStrRec = "" + timeToInc;
		 }
                System.out.println ("timeToInc is after inc/dec ["+ actionOnTime +"] [" + timeToIncStrRec + "]");
                return "PD:" + newDate + ",CH:" + timeToIncStrRec;
        }

        public String decrementDate( String date ,int numOfDays){
                String prev_date = null;
                try{
                        SimpleDateFormat sdf = new SimpleDateFormat ("YYYY-MM-DD");
                        Calendar c = Calendar.getInstance ();
                        c.setTime (sdf.parse (date));
                        c.add (Calendar.DATE, 0-numOfDays);      // number of days to subtract
                        prev_date = sdf.format (c.getTime ());
                        System.out.println ("decremented date is" + prev_date);
                }catch (Exception e){
                        System.out.println (e);
                }

                return prev_date;

        }

        public String incrementDate (String date, int numOfDays)
        {
                String next_date = null;
                try
                {
                        SimpleDateFormat sdf = new SimpleDateFormat ("YYYY-MM-DD");
                        Calendar c = Calendar.getInstance ();
                        c.setTime (sdf.parse (date));
                        c.add (Calendar.DATE, numOfDays);       // number of days to add
                        next_date = sdf.format (c.getTime ());
                        System.out.println ("incremented date is" + next_date);
                }
                catch (Exception e)
                {
                        System.out.println (e);
                }
                return next_date;
        }
	
	public String getCurrentDateTime()
        {
                String returnString=null;
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
                //System.out.println(sdf.format(cal.getTime()));
                //String prev_date     =decrementDate(cal_date);
                //String next_date     =incrementDate(cal_date);
                //System.out.println("next_date#####"+next_date);
                //System.out.println("cal_date-->"+cal_date);
                //SimpleDateFormat  sdf1 = new SimpleDateFormat("HH");
                //System.out.println(sdf1.format(cal.getTime()));
                //String cal_hour=sdf1.format(cal.getTime()).toString()+"00";
                //System.out.println("cal_hour-->"+cal_hour);
                //returnString = ""+cal_hour+"^"+cal_date+"^"+prev_date+"^"+next_date;
		returnString	= sdf.format(cal.getTime()).toString();
                return returnString;
        }

}
