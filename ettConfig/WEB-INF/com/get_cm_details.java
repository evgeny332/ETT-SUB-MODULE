package com;
import java.awt.Window;
import java.io.*;
import java.util.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import oracle.jdbc.driver.*;
public class get_cm_details extends HttpServlet
{
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);

	}
	public void doPost(HttpServletRequest request, HttpServletResponse res) throws  ServletException,IOException
	{
		//PrintWriter out=request.getWriter();
		Connection activeConnection = null;
		HttpSession Session=request.getSession(true);
		Statement st = null;
		Statement st_avail = null;
		String cm_user=request.getParameter("cm_user");
		ResultSet rs=null;
		ResultSet rs_avail=null;
		int total_row = 0 ;
		String msg="<form name='Form' method='post' action='../Update_Tps_Allocation'onsubmit='return Check_Submit()'><table id='cm_detail_table' border='0' class='contentblack'>";
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
			boolean is_Exist = false ;
			if(activeConnection != null)
			{
				st = activeConnection.createStatement();
				String query_update= "SELECT CONNECTION_NAME , CONNECTION_URL , CONNECTION_TPS , SENDER_ID  FROM CM_USER_DETAILS WHERE USERNAME ='"+cm_user+"'";
				System.out.println("query_update"+query_update);
				rs=st.executeQuery(query_update);
				while(rs.next())
				{		
					total_row++ ;
					if(!is_Exist)
					{	
						msg = msg + "<TR>";
        	                                msg = msg + "<TD>Connection Name</TD>";
	                                        msg = msg + "<TD>Connection Url</TD>";
                	                        msg = msg + "<TD>Available TPS</TD>";
                	                        msg = msg + "<TD>Granted TPS</TD>";
                        	    //            msg = msg + "<TD>Sender Id</TD>";
                                	        msg = msg + "</TR>";
						is_Exist =true ; 
					}
					msg = msg + "<TR>";
					msg = msg + "<TD><INPUT class='contentblack' TYPE='text' ID='Conn_Name_"+total_row+"'  NAME='Conn_Name_"+total_row+"' value ='"+rs.getString("CONNECTION_NAME")+"'></TD>";
					msg = msg + "<TD><INPUT class='contentblack' TYPE='text' ID='Conn_Url_"+total_row+"' NAME='Conn_Url_"+total_row+"' value ='"+rs.getString("CONNECTION_URL")+"'></TD>";
					String Avail_Query = "SELECT SUM(CONNECTION_TPS) FROM CM_USER_DETAILS WHERE CONNECTION_NAME='"+rs.getString("CONNECTION_NAME")+"'";
					System.out.println(Avail_Query);
					st_avail = activeConnection.createStatement();
					rs_avail = st_avail.executeQuery(Avail_Query);
					while(rs_avail.next())
					{
               	                        	msg = msg +"<TD><INPUT class='contentblack' TYPE='text' ID='Conn_available_"+total_row+"' NAME='Conn_available_"+total_row+"' value ='"+rs_avail.getInt(1)+"'></TD>";
					}
					rs_avail.close();
					st_avail.close();
					msg = msg + "<TD><INPUT class='contentblack' TYPE='text'ID='conn_tps_"+total_row+"' NAME='conn_tps_"+total_row+"' value='"+rs.getString("CONNECTION_TPS")+"'></TD>";
				//	msg = msg + "<TD><INPUT class='contentblack' TYPE='text' ID='sender_id_"+total_row+"' NAME='sender_id_"+total_row+"' value='"+rs.getString("SENDER_ID")+"'></TD>";
					msg = msg + "</TR>";
				}
				System.out.println(msg);
			}
			else
			{
				System.out.println("Connection not created");

			}
			res.setContentType("html/text");
			if(is_Exist)
				msg= msg +"<TR><TD colspan='5' align='center'><INPUT TYPE='submit' Value='Submit'></TD></TR>";
			msg= msg +"<input type='hidden' value='"+total_row+"' name='totalRow' id ='totalRow'/><input type='hidden' value='"+cm_user+"' name='cm_user' id ='cm_user'/>" ;
			msg= msg +"</table></form>";
			System.out.println("CM :: "+msg);
			res.getWriter().write(msg);
		} 
		catch(Exception e)
		{
			System.out.println("exception addActionValue: " + e);
		}
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, IOException
		{
			doGet(request, response);
		}
}
