package com;

import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import oracle.jdbc.driver.*;

public  class InsertSenderId extends HttpServlet
{
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws  IOException
	{
		PrintWriter out 		= response.getWriter();
		System.out.println("Executing Login Servlet.........");
		String query			= null;
		String sId			= null;
		String connListValues		= null;
		String uname                      = null;
		String connList[] 		= null;
		Statement selectQuery		= null;
		Connection activeConnection 	= null;
		HttpSession Session		= request.getSession(true);

		String flag = "";

		try
		{
			System.out.println("Going to connect");
			sId=request.getParameter("senderName").trim();
			uname=request.getParameter("u_name").trim();
			System.out.println("uname website_oracle"+uname);
			System.out.println("website_oracle :: InsertSenderId.java :: SenderId :: ["+sId+"]");
			connListValues		= request.getParameter("checkBoxValues").trim();
			connList 		= connListValues.split(",");
			System.out.println("website_oracle :: InsertSenderId.java :: connList.length :: ["+connList.length+"]");
			activeConnection  	= OracleConnectionUtil.getConnection();
			selectQuery 		= activeConnection.createStatement();
			for(int i=0; i<connList.length; i++)
			{
				query = "insert into cm_sender_id (USER_NAME,SENDER_ID,CONNECTIONS)values('"+uname+"','"+sId+"','"+connList[i]+"')";
				selectQuery.executeUpdate(query);
				System.out.println("query"+query);
			}
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
		}
		finally
		{
			try{
				selectQuery.close();
				activeConnection.close();
			}catch(Exception e){e.printStackTrace();}
		}
		response.sendRedirect("jsp/manageSenderId.jsp");

	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}


