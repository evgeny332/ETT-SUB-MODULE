package com;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import oracle.jdbc.driver.*;

public  class CreateEntUser extends HttpServlet
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
		String userName			= null;
		String password			= null;
		String totalTPS			= null;
		String userType			= null;
		String usertps			= null;
		String conWithTPS[]		= null;
		String cons	 		= null;
		Statement selectQuery		= null;
		Connection activeConnection 	= null;
		HttpSession Session		= request.getSession(true);
		response.setContentType("text/xml");
		System.out.println("cons"+cons);
		System.out.println("userName"+userName);
		System.out.println("totalTPS"+totalTPS);
		String flag = "";
		try
		{
			cons	   = request.getParameter("assignedTPSforConn").trim();
			System.out.println("website_oracle :: CreateUser.java :: cons :: ["+cons+"]");
			conWithTPS = cons.split("#");
			userName = request.getParameter("userName").trim();
//			password = request.getParameter("password").trim();
			totalTPS = request.getParameter("totalTPS").trim();
			System.out.println("userName"+userName);
			System.out.println("totalTPS"+totalTPS);
//			userType = request.getParameter("userType").trim();
			System.out.println("Going to connect");
			activeConnection  	= OracleConnectionUtil.getConnection();
			selectQuery 		= activeConnection.createStatement();
			System.out.println("website_oracle :: CreateUser.java :: conWithTPS.length :: ["+conWithTPS.length+"]");
			for(int i=0;i<conWithTPS.length;i++)
			{
				String[] conn = conWithTPS[i].split(",");
				query = "insert into CM_USER_CONNECTION_DETAILS (USERNAME,CONNECTION_NAME,CONNECTION_TPS) values('"+userName+"','"+conn[0]+"','"+conn[3]+"')";
				usertps = conn[3];
				System.out.println("website_oracle :: CreateUser.java :: query1 :: ["+query+"]");
				selectQuery.executeUpdate(query);
			}
//			query = "insert into GR_USERS (USERNAME,USERPASSWORD)values('"+userName+"','"+password+"')";
//			selectQuery.executeUpdate(query);
			//out.write("done");
		}
		catch(Exception Eb)
		{
			//out.write("fail");
			Eb.printStackTrace();
		}
		finally
		{
			try{
				selectQuery.close();
				activeConnection.close();
			}catch(Exception e){e.printStackTrace();}
		}
//		response.sendRedirect("jsp/manageUser.jsp");
		response.sendRedirect("jsp/manageUser.jsp?USERNAME="+userName+"&CONNECTION_TPS="+usertps);
			System.out.println("going at parveen jsp");

	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}


