package com.mcarbon;
import java.util.*;
import java.net.*;
import java.io.*;
import java.text.*;
import java.util.ResourceBundle;
import java.sql.*;
import oracle.jdbc.driver.*;
import jxl.write.*;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.*;
public class InsertIntoDb
{

	public InsertIntoDb()
	{
	}
	public HashMap getUrnPair( )
	{
		Connection conn              = null;
		Statement stmt               = null;
		ResultSet rs                 = null;
		int resultCount              = 0;
		int i                        = 0;
		HashMap hm 		     = null; 
		String queryToGetRefNumber   = "";

		System.out.println("Inside User Details ");
		try
		{
			hm = new HashMap(); 
			conn = OracleConnectionUtilS.getConnection();
			stmt = conn.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
			queryToGetRefNumber = "SELECT distinct TM_URN , TM_NAME from TELEMARKETER_LIST " ;
			rs = stmt.executeQuery(queryToGetRefNumber );
			try
			{


				while( rs.next() )
				{

					hm.put(rs.getString(1),rs.getString(2));
					System.out.println("Value in hash map are"+hm.size());
				}
			}
			catch(Exception ee){System.out.println("Exception  :: ["+ee+"]");}
			return hm;

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Error :: "+e);
			return hm;

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
	}

	public String []getUrnName( ){
		Connection conn              = null;
		Statement stmt               = null;
		ResultSet rs                 = null;
		int resultCount              = 0;
		int i                        = 0;
		String [] conResult          = null;
		String queryToGetRefNumber   = "";
		//String conResul              = "A";

		System.out.println("Inside TM_URN TM_NAME Details ");
		try{
			conn = OracleConnectionUtilS.getConnection();
			stmt = conn.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
			queryToGetRefNumber = "SELECT distinct TM_NAME from TELEMARKETER_LIST " ;
			rs = stmt.executeQuery(queryToGetRefNumber );
			rs.last();
			resultCount = rs.getRow();
			System.out.println("Number of Distributor IDs"+resultCount);
			rs.beforeFirst();
			conResult = new String[resultCount];
			System.out.println("getDistributorID length "+conResult.length);
			System.out.println("getDistributorID Query:"+queryToGetRefNumber);
			try{


				while( rs.next() ){

					conResult[i] = rs.getString(1);
					System.out.println("queryToGetRefNumber Array"+conResult[i]);
					i++;
				}
			}catch(Exception ee){System.out.println("Exception  :: ["+ee+"]");}
			return conResult;

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error :: "+e);
			return conResult;
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
	}


	public String [] getName(String TM_URN)
	{
		Connection conn              = null;
		Statement stmt               = null;
		ResultSet rs                 = null;
		int resultCount              = 0;
		int i                        = 0;
		String [] conResult          = null;
		String queryToGetRefNumber   = "";
		String conResul              = "A";

		System.out.println("Inside Name Details ");
		try{
			conn = OracleConnectionUtilS.getConnection();
			stmt = conn.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
			queryToGetRefNumber = "SELECT distinct TM_NAME from TELEMARKETER_LIST where TM_URN='" + TM_URN +"'";
			System.out.println("Query for queryToGetRefNumber ["+queryToGetRefNumber+"]") ;
			rs = stmt.executeQuery(queryToGetRefNumber );
			if(rs.next())
			{
				rs.last();
				resultCount = rs.getRow();
				System.out.println("getREF_NUMBER length"+resultCount);
				rs.beforeFirst();
				conResult = new String[resultCount];
			}
			else
			{
				conResult = new String[1];
				conResult[0] = "";
			}
			System.out.println("getItemId length "+conResult.length);
			System.out.println("getItemId Query:"+queryToGetRefNumber);
			try{


				while( rs.next() ){

					conResult[i] = rs.getString(1);
					System.out.println("queryToGetRefNumber Array"+conResult[i]);
					i++;
				}
			}catch(Exception ee){System.out.println("Exception  :: ["+ee+"]");}
			return conResult;

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error :: "+e);
			return conResult;

		}finally{
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


	}
	//----------------------------
	public String [] getNumber1(String TM_URN)
	{
		Connection conn              = null;
		Statement stmt               = null;
		ResultSet rs                 = null;
		int resultCount              = 0;
		int i                        = 0;
		String [] conResult          = null;
		String queryToGetRefNumber   = "";
		String conResul              = "A";

		System.out.println("Inside Number Details ");
		try{
			conn = OracleConnectionUtilS.getConnection();
			stmt = conn.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
			queryToGetRefNumber = "SELECT distinct TM_NUMBER from TELEMARKETER_LIST where TM_URN ='" + TM_URN +"' and TM_NUMBER is not null" ;
			System.out.println("Query for queryToGetRefNumber ["+queryToGetRefNumber+"]") ;
			rs = stmt.executeQuery(queryToGetRefNumber );
			if(rs.next())
			{
				rs.last();
				resultCount = rs.getRow();
				System.out.println("getREF_NUMBER length"+resultCount);
				rs.beforeFirst();
				conResult = new String[resultCount];
			}
			else
			{
				conResult = new String[1];
				conResult[0] = "";
			}
			System.out.println("getItemId length "+conResult.length);
			System.out.println("getItemId Query:"+queryToGetRefNumber);
			try{


				while( rs.next() ){

					conResult[i] = rs.getString(1);
					System.out.println("queryToGetRefNumber Array"+conResult[i]);
					i++;
				}
			}catch(Exception ee){System.out.println("Exception  :: ["+ee+"]");}
			return conResult;

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error :: "+e);
			return conResult;

		}finally{
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


	}
	//----------------------------------------
	public String [] getNumber(String TM_NAME)
	{
		Connection conn              = null;
		Statement stmt               = null;
		ResultSet rs                 = null;
		int resultCount              = 0;
		int i                        = 0;
		String [] conResult          = null;
		String queryToGetRefNumber   = "";
		String conResul              = "A";

		System.out.println("Inside Number Details ");
		try{
			conn = OracleConnectionUtilS.getConnection();
			stmt = conn.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
			queryToGetRefNumber = "SELECT distinct TM_NUMBER from TELEMARKETER_LIST where TM_NAME='" + TM_NAME +"' and TM_NUMBER is not null" ;
			System.out.println("Query for queryToGetRefNumber ["+queryToGetRefNumber+"]") ;
			rs = stmt.executeQuery(queryToGetRefNumber );
			if(rs.next())
			{
				rs.last();
				resultCount = rs.getRow();
				System.out.println("getREF_NUMBER length"+resultCount);
				rs.beforeFirst();
				conResult = new String[resultCount];
			}
			else
			{
				conResult = new String[1];
				conResult[0] = "";
			}
			System.out.println("getItemId length "+conResult.length);
			System.out.println("getItemId Query:"+queryToGetRefNumber);
			try{


				while( rs.next() ){

					conResult[i] = rs.getString(1);
					System.out.println("queryToGetRefNumber Array"+conResult[i]);
					i++;
				}
			}catch(Exception ee){System.out.println("Exception  :: ["+ee+"]");}
			return conResult;

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error :: "+e);
			return conResult;

		}finally{
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


	}
	public HashMap getCircle( ){
		Connection conn              = null;
		Statement stmt               = null;
		ResultSet rs                 = null;
		HashMap hm                   = null;
		int resultCount              = 0;
		int i                        = 0;
		String queryToGetCircle	     = "";

		System.out.println("DNCAIRTEL:	Inside User Details ");
		try{
			hm = new HashMap();
			conn = OracleConnectionUtilS.getConnection();
			stmt = conn.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
			//queryToGetRefNumber = "SELECT distinct DISTRIBUTOR_ID from retailer_details " ;
			queryToGetCircle = "SELECT distinct T2.NAME from telemarketer_list T1 ,CIRCLE_CODE T2 where T1.CIRCLE = T2.CIRCLE and T1.CIRCLE is not null" ;
			rs = stmt.executeQuery(queryToGetCircle );
			try{


				while( rs.next() ){
					hm.put(rs.getString(1),rs.getString(1));
					System.out.println("Value in hash map are"+hm.size());
				}
			}catch(Exception ee){System.out.println("DNCAIRTEL: Exception  :: ["+ee+"]");}
			return hm;

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error :: "+e);
			return hm;

		}finally{
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
	}

	//-----------------------------------
	public String [] getTMNumber(String circle_id)
        {
                Connection conn              = null;
                Statement stmt               = null;
                ResultSet rs                 = null;
                int resultCount              = 0;
                int i                        = 0;
                String [] conResult          = null;
                String queryToGetRefNumber   = "";
                String conResul              = "A";

                System.out.println("Inside Number Details ");
                try{
                        conn = OracleConnectionUtilS.getConnection();
                        stmt = conn.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                        queryToGetRefNumber = "SELECT distinct T1.TM_NUMBER from TELEMARKETER_LIST T1, CIRCLE_CODE T2 where T1.CIRCLE= T2.CIRCLE and T2.NAME ='"+circle_id+"' and TM_NUMBER is not null" ;
                        System.out.println("Query for queryToGetRefNumber ["+queryToGetRefNumber+"]") ;
                        rs = stmt.executeQuery(queryToGetRefNumber );
                        if(rs.next())
                        {
                                rs.last();
                                resultCount = rs.getRow();
                                System.out.println("getREF_NUMBER length"+resultCount);
                                rs.beforeFirst();
                                conResult = new String[resultCount];
                        }
                        else
                        {
                                conResult = new String[1];
                                conResult[0] = "";
                        }
                        System.out.println("getItemId length "+conResult.length);
                        System.out.println("getItemId Query:"+queryToGetRefNumber);
                        try{


                                while( rs.next() ){

                                        conResult[i] = rs.getString(1);
                                        System.out.println("queryToGetRefNumber Array"+conResult[i]);
                                        i++;
					 }
                        }catch(Exception ee){System.out.println("Exception  :: ["+ee+"]");}
                        return conResult;

                }catch(Exception e){
                        e.printStackTrace();
                        System.out.println("Error :: "+e);
                        return conResult;

                }finally{
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


        }
	public String [] getURN(String type)
        {
                Connection conn              = null;
                Statement stmt               = null;
                ResultSet rs                 = null;
                int resultCount              = 0;
                int i                        = 0;
                String [] conResult          = null;
                String queryToGetRefNumber   = "";
                String conResul              = "A";

                System.out.println("Inside Number Details ");
                try{
                        conn = OracleConnectionUtilS.getConnection();
                        stmt = conn.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                        queryToGetRefNumber = "SELECT distinct T1.TM_URN from TELEMARKETER_LIST T1 where T1.TM_TYPE ='"+type+"' and TM_URN is not null" ;
                        System.out.println("Query for queryToGetRefNumber ["+queryToGetRefNumber+"]") ;
                        rs = stmt.executeQuery(queryToGetRefNumber );
                        if(rs.next())
                        {
                                rs.last();
				resultCount = rs.getRow();
                                System.out.println("getREF_NUMBER length"+resultCount);
                                rs.beforeFirst();
                                conResult = new String[resultCount];
                        }
                        else
                        {
                                conResult = new String[1];
                                conResult[0] = "";
                        }
                        System.out.println("getItemId length "+conResult.length);
                        System.out.println("getItemId Query:"+queryToGetRefNumber);
                        try{


                                while( rs.next() ){

                                        conResult[i] = rs.getString(1);
                                        System.out.println("queryToGetRefNumber Array"+conResult[i]);
                                        i++;
                                         }
                        }catch(Exception ee){System.out.println("Exception  :: ["+ee+"]");}
                        return conResult;

                }catch(Exception e){
                        e.printStackTrace();
                        System.out.println("Error :: "+e);
                        return conResult;

                }finally{
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


        }

}	
