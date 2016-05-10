package com.rh.api;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class ParseFiber
{
    DateFormat dfm = new SimpleDateFormat("yyyyMMddHHmm");  

    long unixtime;
    public long timeConversion(String time)
    {
        dfm.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));//Specify your timezone 
    try
    {
        unixtime = dfm.parse(time).getTime();  
        unixtime=unixtime/1000;
    } 
    catch (ParseException e) 
    {
        e.printStackTrace();
    }
    return unixtime;
    }

}