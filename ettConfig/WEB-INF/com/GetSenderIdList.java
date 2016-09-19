package com;

import java.util.*;
import java.sql.*;
public class GetSenderIdList
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
		Vector vct 			= new Vector(1,1);
		try{
			activeConnection  = OracleConnectionUtil.getConnection();	
			st = activeConnection.createStatement();
			conSt = activeConnection.createStatement();
			query = "select distinct sender_id,user_name from cm_sender_id";
			rs = st.executeQuery(query);
			while(rs.next())
			{
				String senderIdData[] = new String[3];
				conQuery = "select distinct connections from cm_sender_id where sender_id='"+rs.getString(1)+"'";
				System.out.println("vec query"+conQuery);
				conRs = conSt.executeQuery(conQuery);
				String conns = null;
				while(conRs.next())
				{
					if(conns==null)
						conns = conRs.getString(1);
					else
						conns = conns+","+conRs.getString(1);
				}
				//conRs = "";
				senderIdData[0] = rs.getString(1);
				senderIdData[1] = conns;
				senderIdData[2] =rs.getString(2);
				System.out.println("vec0 sidlength"+senderIdData[0].length());
				System.out.println("vec0 connection length"+senderIdData[1].length());
				System.out.println("vec0 users  length"+senderIdData[2]);
				vct.add(senderIdData);	
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
		return vct;	
	}
}
