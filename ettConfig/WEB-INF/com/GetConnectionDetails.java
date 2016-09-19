package com;
import java.util.*;
import java.sql.*;
public class GetConnectionDetails 
{
	public String[] conDetails()
	{
		Connection activeConnection 	= null;
                Statement st 			= null;
                Statement detailSt		= null;
                ResultSet rs			= null;
                ResultSet detailRs		= null;
		String query 			= null;
		String conQuery			= null;
		String[] connDetails		= null;
		try{
			activeConnection  = OracleConnectionUtil.getConnection();	
			st = activeConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			detailSt = activeConnection.createStatement();
			query = "select distinct connection_name from CM_CONNECTION_DETAILS";
			System.out.println("website_oracle :: GetConnectionDetails.java :: query2 :: ["+query+"]");
			rs = st.executeQuery(query);
			int size = 0;
			if(rs.next())
			{
				rs.last();
				size=rs.getRow();
				rs.beforeFirst();
			}
			//System.out.println("website_oracle :: GetConnectionList.java :: size :: ["+size+"]");
			connDetails = new String[size];
			
			for(int i=0;rs.next();i++)
			{
				query="select distinct CONNECTION_TPS from CM_CONNECTION_DETAILS where CONNECTION_NAME='"+rs.getString(1)+"'";
				System.out.println("website_oracle :: GetConnectionDetails :: query1 :: ["+query+"]");
				connDetails[i] = rs.getString(1);
				detailRs = detailSt.executeQuery(query);
				while(detailRs.next())
				{
					connDetails[i] = connDetails[i]+","+detailRs.getString(1);
				}
				query="select nvl(sum(CONNECTION_TPS),0) from CM_USER_CONNECTION_DETAILS where CONNECTION_NAME='"+rs.getString(1)+"'";
				System.out.println("website_oracle :: GetConnectionDetails :: query :: ["+query+"]");
				detailRs = detailSt.executeQuery(query);
				while(detailRs.next())
                                {
					int availableTPS=Integer.parseInt(connDetails[i].substring(connDetails[i].indexOf(",")+1))-detailRs.getInt(1);
					System.out.println("availableTPS"+availableTPS);
                                        connDetails[i] = connDetails[i]+","+availableTPS;
                                }
			}
		}catch(Exception e){e.printStackTrace();}
		finally
		{
			try{
			activeConnection.close();
			st.close();
			rs.close();
			detailRs.close();
			}catch(Exception e){e.printStackTrace();}
		}
		//System.out.println("website_oracle :: GetConnectionList.java :: connList.length :: ["+connList.length+"]");
		return connDetails;	
	}
}
