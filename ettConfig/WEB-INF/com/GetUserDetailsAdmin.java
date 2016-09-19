package com;
import java.util.*;
import java.sql.*;
public class GetUserDetailsAdmin 
{
	public ArrayList userDetails()
	{
		Connection activeConnection 	= null;
                Statement st 			= null;
                ResultSet rs			= null;
		String query 			= null;
		ArrayList userDetails		= null;
		try{
			userDetails	= new ArrayList();
			activeConnection  = OracleConnectionUtil.getConnection();	
			st = activeConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			query = "select distinct username from CM_USER_CONNECTION_DETAILS";
			System.out.println("website_oracle :: GetConnectionDetails.java :: query2 :: ["+query+"]");
			rs = st.executeQuery(query);
			while(rs.next())
                        {
				userDetails.add(rs.getString(1));				
                        }
		}catch(Exception e){e.printStackTrace();}
		finally
		{
			try{
			activeConnection.close();
			st.close();
			rs.close();
			}catch(Exception e){e.printStackTrace();}
		}
		//System.out.println("website_oracle :: GetConnectionList.java :: connList.length :: ["+connList.length+"]");
		return userDetails;	
	}
}
