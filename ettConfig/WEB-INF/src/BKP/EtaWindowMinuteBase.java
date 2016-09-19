import java.util.* ;
import java.util.Date ;
import java.sql.* ;
import java.text.* ;
import java.text.SimpleDateFormat ;
public class EtaWindowMinuteBase
{
	public class CampaignInfo
	{
		String campaign = null ; 
		String datetime = null ; 
		String eta 	= null ; 
		int messagecount= 0 ;
		int total 	= 0 ;
		int msg_sent 	= 0 ;
		int pending 	= 0 ;
		boolean processed = false ;
	}
	public static void main (String args[])
	{
		EtaWindowMinuteBase ce  = new EtaWindowMinuteBase();
		ce.refreshWindowSize("parveen" , "2010-12-13" , 40 );
	}
	public String getEtaTime (String user , String campaign_name , int total , String datetime , int total_tps ) 
	{
		String eta = null ;
		int activeCamp  = getActiveCampaign( user , datetime );
		if(activeCamp == 0 )
			activeCamp = 1 ; 
		int activetps = total_tps / activeCamp ; 
		System.out.println("Active Campaign ["+activeCamp+"] TotalTPs ["+total_tps+"] Active Tps ["+activetps+"]");
		if(activetps == 0)
			activetps =1 ; 
		int secReq = total / activetps ;
		eta = getExpectedTime(secReq , datetime ) ;
		System.out.println("USER ["+user+"] CAMP ["+campaign_name+"]TOTAL ["+total+"] DATETIME ["+datetime+"] SecReq ["+secReq+"]");
		return eta ; 
	}
	public void refreshWindowSize( String user , String datetime , int total_tps )
	{
		try
		{
			System.out.println("USER ["+user+"] TOTAL_TPS ["+total_tps+"] DATETIME ["+datetime+"]");
			int maxCapacity = total_tps * 60 * 60 ;
			Vector existCamp  = getTodayExistCamp(user , datetime);
			int MinWindow [][] = new int[25][60];
			int hour_window [] = new int[25];	
			HashMap HourUsed = new HashMap() ;
			HashMap hour_camp[] = new HashMap[25];
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String hour_hh = "" ;
			String min_mm = "" ;
			for( int hr_index = 7 ; hr_index < 21 ; hr_index++ )
			{
				if(hr_index < 10)
					hour_hh = "0"+hr_index ; 
				else
					hour_hh = ""+hr_index  ;
				hour_camp[hr_index] = new HashMap(); 
				for( int min_index =  0 ; min_index < 60 ; min_index  ++ )
				{
					if(min_index < 10 )
						min_mm = "0" + min_index  ;
					else
						min_mm = ""+min_index ;  
					Date currenttime  = df.parse(datetime + " " + hour_hh + ":" + min_index + ":00" );
					int totalsent = getSentInMin(currenttime , existCamp , total_tps , hour_camp[hr_index] );	
					MinWindow[hr_index][min_index] = totalsent ;
					System.out.println("TIME ["+hour_hh+":"+min_mm+"] Sent["+totalsent+"]");
				}
			}
			//update hour values in DB
			removeInfoFromEndOfWindow(user , datetime );
			String Query = "UPDATE CM_HOUR_DATE_DETAILS SET MAX_CAPACITY = "+maxCapacity + " ";
			for( int hr_index = 7 ; hr_index < 21 ; hr_index++ )
                        {
				if(hr_index < 10)
					hour_hh = "0"+hr_index ; 
				else
					hour_hh = ""+hr_index ; 
				int hr_total = 0 ;
				String endofwindow = ""; 
				for( int min_index =  0 ; min_index < 60 ; min_index++ )
				{
					if(min_index < 10 )
						min_mm = "0" + min_index  ;
					else
						min_mm = ""+min_index ; 
					hr_total = hr_total + MinWindow[hr_index][min_index] ;
					if(MinWindow[hr_index][min_index] > 0 ) 
 						endofwindow = datetime+" "+hour_hh+":"+min_index+":00";
				}
				String allCamp = ""; 
				for(Iterator it = hour_camp[hr_index].keySet().iterator(); it.hasNext();)
				{
					String camp = (String)it.next();
					allCamp = allCamp + camp + "," ; 
				}
				hour_window[hr_index] = hr_total ; 
				System.out.println("HOUR ["+hr_index+"] TOTAL ["+hr_total+"] ["+allCamp+"]");
				int updateValue  =  maxCapacity - hr_total ; 
				if(updateValue < 0 )
					updateValue = 0; 
			
				if(hr_total > 0)
					UpdateHourEndOfWindow(user , datetime+"-"+hour_hh , allCamp , endofwindow );

				Query = Query + " , HR_" +hour_hh+ " = " + (updateValue ); 
			}
			Query = Query + " WHERE USERNAME = '"+user+"' AND CAPACITY_DATE ='"+datetime+"'" ;
			System.out.println(Query);
			checkAndUpdateInDB(user , datetime , Query);	
		}	
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}
	public Vector getAllToProcess(Date currenttime , Vector existCamp )
	{
		Vector rangeCamp = new Vector();
		try
		{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(int index = 0 ; index < existCamp.size() ; index ++ )
			{
				CampaignInfo campinfo = (CampaignInfo)existCamp.get(index);	
				Date campstarttime = df.parse(campinfo.datetime);
				if( ( currenttime.after(campstarttime) || currenttime.equals(campstarttime) ) && campinfo.pending > 0 )
				{	
					//Oh my god i found this use it
					rangeCamp.add(campinfo);
				}
			}
		}
		catch(Exception exp)		
		{
			exp.printStackTrace(); 	
		}
		return rangeCamp ; 
	}	
	public int getSentInMin ( Date currenttime , Vector existCamp , int  total_tps , HashMap hour_camp ) 
	{
		int total = 0 ; 
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
		
			Vector rangeCamp = getAllToProcess ( currenttime , existCamp ); 
			int allActiveInWindow = rangeCamp.size();
			if(allActiveInWindow > 0 )
			{
				for(int index = 0 ; index < rangeCamp.size() ; index ++ )
				{			
					CampaignInfo campinfo = (CampaignInfo)rangeCamp.get(index);
					campinfo.pending = campinfo.pending - ( 60 * ( total_tps / allActiveInWindow ) ) ;
					hour_camp.put( campinfo.campaign , 1 );
					total = total + ( 60 * ( total_tps / allActiveInWindow ) ) ; 
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return total ;  
	}
	public void UpdateHourEndOfWindow(String user ,String date_hour ,String campaignlist ,String endofwindow ) 
	{
		Connection activeConnection = null;
		Statement st1 = null;
		int count = 0 ;
		try
		{
			activeConnection = OracleConnectionUtil.getConnection();
			st1=activeConnection.createStatement();
			String Query = "UPDATE CM_CAMP_HOURLY_DETAILS SET CAMPAIGNS ='"+campaignlist+"' , END_OF_TIME_WINDOW ='"+endofwindow+"' WHERE USERNAME ='"+user+"'  AND DATE_HR ='"+date_hour+"'" ;
			System.out.println(Query); 
			int updatecount = st1.executeUpdate(Query);
			if(updatecount <= 0 )
			{
				String inserQuery = "INSERT INTO CM_CAMP_HOURLY_DETAILS ( USERNAME , DATE_HR ) VALUES ('"+user+"' , '"+date_hour+"' )";
				System.out.println("INSERT QUERY ["+inserQuery+"] ["+st1.executeUpdate(inserQuery)+"]");
				updatecount = st1.executeUpdate(Query);
			}
			st1.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(activeConnection != null )
				{
					activeConnection.close();
				}
			}
			catch(Exception dbclose){}
		}
	}
	public void checkAndUpdateInDB(String user , String date , String Query)
	{
		Connection activeConnection = null;
		Statement st1 = null;
		int count = 0 ;
		try
		{
			activeConnection = OracleConnectionUtil.getConnection();
			st1=activeConnection.createStatement();
			int updatecount = st1.executeUpdate(Query);
			if(updatecount <= 0 )
			{
				String inserQuery = "INSERT INTO CM_HOUR_DATE_DETAILS ( CAPACITY_DATE , USERNAME ) VALUES ('"+date+"' , '"+user+"' )";
				System.out.println("INSERT QUERY ["+inserQuery+"] ["+st1.executeUpdate(inserQuery)+"]");
				updatecount = st1.executeUpdate(Query);
			}
			st1.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(activeConnection != null )
				{
					activeConnection.close();
				}
			}
			catch(Exception dbclose){}
		}
	}	
	public Vector getTodayExistCamp(String user , String dateTime)
	{
		Vector existCamp = new Vector();
		Connection activeConnection = null ;
		Statement st1 = null ;
		int count = 0 ;
		try
		{
			activeConnection = OracleConnectionUtil.getConnection();
			st1=activeConnection.createStatement();
			String  qur_insert1="SELECT CAMPAIGN_NAME , DATETIME , MESSAGECOUNT , ETA ,TOTAL ,MSG_SENT FROM PROMOTIONAL_MESSAGE_INFO WHERE USERNAME = '"+user+"' AND DATETIME > '"+dateTime+" 07' AND DATETIME < '"+dateTime+" 20' AND CAMPAIGN_STATUS != 'Canceled' AND ETA != 'pending' ORDER BY DATETIME ";
			System.out.println(qur_insert1);
			ResultSet  rs  = st1.executeQuery(qur_insert1);
			while(rs.next())
			{
				CampaignInfo campinfo = new CampaignInfo();
				campinfo.campaign = rs.getString("CAMPAIGN_NAME");
				campinfo.datetime = rs.getString("DATETIME");
				campinfo.eta	 = rs.getString("ETA");
				campinfo.messagecount = rs.getInt("MESSAGECOUNT");
				campinfo.total = rs.getInt("TOTAL");
				campinfo.msg_sent = rs.getInt("MSG_SENT");
				campinfo.pending = campinfo.total ; 
				existCamp.add(campinfo);
			}
			System.out.println("["+GetDate()+"]"+"count ["+count+"]");
			rs.close();
			st1.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(activeConnection != null )
				{
					activeConnection.close();
				}
			}
			catch(Exception dbclose){}
		}
		return existCamp ;
	}
	public int getActiveCampaign(String user , String datetime)
	{
		Connection activeConnection = null;
		Statement st1 = null;
		int count = 0 ;
		try
		{
			activeConnection = OracleConnectionUtil.getConnection();
			st1=activeConnection.createStatement();
			String  qur_insert1="SELECT COUNT(*) FROM PROMOTIONAL_MESSAGE_INFO WHERE USERNAME = '"+user+"' AND DATETIME <= '"+datetime+"' AND ETA > '"+datetime+"' AND ETA !='Canceled' AND ETA !='Pause'";
			System.out.println(qur_insert1);
			ResultSet  rs  = st1.executeQuery(qur_insert1);
			if(rs.next())
				count = rs.getInt(1);
			else
				count = 0 ;
			System.out.println("count ["+count+"]");
			rs.close();
			st1.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(activeConnection != null )
				{
					activeConnection.close();
				}
			}
			catch(Exception dbclose){}
		}
		return count ;
	}
	public int getTotalTps(String user)
	{
		Connection activeConnection = null;
		Statement st1 = null;
		int count = 0 ;
		try
		{
			activeConnection = OracleConnectionUtil.getConnection();
			st1=activeConnection.createStatement();
			String  qur_insert1="SELECT TOTAL_TPS FROM CM_USER_LOGIN_DETAILS WHERE USERNAME = '"+user+"'";
			ResultSet  rs  = st1.executeQuery(qur_insert1);
			if(rs.next())
				count = rs.getInt(1);
			else
				count = 0 ;
			System.out.println("["+GetDate()+"]"+"count ["+count+"]");
			rs.close();
			st1.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(activeConnection != null )
				{
					activeConnection.close();
				}
			}
			catch(Exception dbclose){}
		}
		return count ;
	}
	public void removeInfoFromEndOfWindow(String user , String date)
	{
		Connection activeConnection = null;
		Statement st1 = null;
		int count = 0 ;
		try
		{
			activeConnection = OracleConnectionUtil.getConnection();
			st1=activeConnection.createStatement();
			String  qur_insert1="DELETE FROM CM_HOUR_DATE_DETAILS WHERE USERNAME ='"+user+"' AND CAPACITY_DATE LIKE '"+date+"%'";
			count  = st1.executeUpdate(qur_insert1);
			System.out.println("["+GetDate()+"]"+"count ["+count+"]");
			st1.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(activeConnection != null )
				{
					activeConnection.close();
				}
			}
			catch(Exception dbclose){}
		}
	}
	public String GetDate()
	{
		Calendar cal = Calendar.getInstance();
		String DATE_FORMAT_NOW = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
	public String getEOW(String datetime , int sec_req)
	{
		String datevalue = null ;  
		try
		{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date starttime = df.parse(datetime+":00:00");
			String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			Calendar c1 = Calendar.getInstance();
			c1.setTime(starttime);
			c1.add(Calendar.SECOND, sec_req);
			datevalue =  sdf.format(c1.getTime());		
		}
		catch(Exception exp)
		{
			exp.printStackTrace(); 
		}
		return datevalue ; 
			
	}
	public String getExpectedTime(int sec_req , String settime )
	{
		String datevalue = null ;
		try
		{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date starttime = df.parse(settime);
			Calendar c1 = Calendar.getInstance();
			c1.setTime(starttime);
			c1.add(Calendar.SECOND, sec_req);
			datevalue =  df.format(c1.getTime());	
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		System.out.println("STARTTIME ["+settime+"]ETA ["+datevalue+"]");
		return datevalue ; 
	}
}
