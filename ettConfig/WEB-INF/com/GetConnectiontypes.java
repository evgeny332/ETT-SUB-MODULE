package com;
import java.util.*;
import java.sql.*;
public class GetConnectiontypes
{
	public Vector getList()
	{
		Connection activeConnection 	= null;
                Statement st 			= null;
                ResultSet rs			= null;
                Statement conSt			= null;
                ResultSet conRs			= null;
		String query 			= null;
		String conQuery			= null;
		Vector vct 			= new Vector();
		try{
			activeConnection  = OracleConnectionUtil.getConnection();	
			st = activeConnection.createStatement();
			conSt = activeConnection.createStatement();
			query = "select distinct CONNECTION_NAME from CM_CONNECTION_DETAILS";
			rs = st.executeQuery(query);
			while(rs.next())
			{
				conQuery = "select * from CM_CONNECTION_DETAILS where CONNECTION_NAME='"+rs.getString(1)+"'";
				conRs = conSt.executeQuery(conQuery);
				while(conRs.next())
				{
					System.out.println("Creating new obj ["+conRs.getString(1)+"] int["+conRs.getInt(3)+"]");
					ConnectionDetails abc = new ConnectionDetails(conRs.getString(1),conRs.getString(2),String.valueOf(conRs.getInt(3)),conRs.getString(4));
					
					vct.add(abc);	
				}
			}
		}catch(Exception e){e.printStackTrace();}
		finally
		{
			try{
				if(activeConnection!=null)
					activeConnection.close();
				if(st!=null)
					st.close();
				if(conSt!=null)
					conSt.close();
				if(conRs!=null)
					conRs.close();
				if(rs!=null)
					rs.close();
			}catch(Exception e){e.printStackTrace();}
		}
		System.out.println("Returning vertor ... Size["+vct.size()+"]");
		return vct;	
	}
}
