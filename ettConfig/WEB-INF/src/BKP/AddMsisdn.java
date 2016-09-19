package com.mcarbon;
import java.io.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;
public class AddMsisdn 
{	

	public String CheckMsisdn( String msisdn )
        {	

		Connection conn              = null;
                Statement stmt               = null;
                ResultSet rs                 = null;
                String query		     = "";
		int resultCount		     = 0;
		String bool		     = "false";
		String Tablename             = null;
                System.out.println("Inside CheckMsisdn ");
                try
                {
                        conn = OracleConnectionUtilS.getConnection();
                        stmt = conn.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
			String series = msisdn.substring(9,10);
			Tablename = "DND_SERIES" + series;
                        //query = "select T1.FILEACTION from DND_DATA T2,FILEDETAILS T1 where T1.FID = T2.FID and T2.MSISDN ="+msisdn+" and T1.FILEACTION ='INSERT'";
			query = "select * from "+Tablename+" where MSISDN = "+msisdn+"";
			System.out.println("query is "+query);
                        rs = stmt.executeQuery(query);
			while( rs.next() )
			{
				resultCount++;
                        }
			System.out.println("Value of resultCount::"+resultCount);
			if( resultCount == 0 )
			{
				System.out.println("Calling Add Function");
				bool = "true";
				Add(msisdn);
			}
			stmt.close();
                       	rs.close();
			conn.close();
                }
		catch(Exception e)
                {
                        e.printStackTrace();
                        System.out.println("Error :: "+e);

                }
                finally
                {
                        if ( conn != null ){
                                try{
                                        if ( rs != null && stmt != null ){
                                                stmt.close();
                                                rs.close();
                                        }
                                        conn.close();
                                }catch(Exception eee){}
                        }
                }
		System.out.println(" Value of the bool ::"+bool);
		return bool;
	}
	public void Add( String msisdn )
        {		
		FileOutputStream out; 
		PrintStream p;

		try
		{
			out = new FileOutputStream("//home//mcarbon//dnconlinefile//"+GetDate()+"_ADD.txt",true);

			p = new PrintStream( out );

			p.println (msisdn);

			System.out.println("file created at /home/mcarbon/dnconlinefile/");

			p.close();
		}
		catch (Exception e)
		{
			System.err.println ("Error writing to file");
		}
	}

	public String GetDateDef( String DATE_FORMAT_NOW)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
	public String GetDate()
	{
		Calendar cal = Calendar.getInstance();
		String DATE_FORMAT_NOW = "yyyyMMddHHmm";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

}

