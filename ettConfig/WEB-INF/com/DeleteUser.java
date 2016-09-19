package com;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import oracle.jdbc.driver.*;

public  class DeleteUser extends HttpServlet
{
        public void init(ServletConfig config)  throws ServletException
        {
                super.init(config);
        }
        public void doGet(HttpServletRequest request,HttpServletResponse response) throws  IOException
        {
                PrintWriter out                 = response.getWriter();
                System.out.println("Executing DeleteUser Servlet.........");
                String query                    = null;
                String userName                 = null;
                Connection activeConnection     = null;
	Statement       stmt            = null;
                HttpSession Session             = request.getSession(true);

                int flag = 0;

                try
                {
                        userName          = request.getParameter("hidd_userName").trim();

                        System.out.println("website_oracle :: CreateUser.java :: cons :: ["+userName+"]");
                        System.out.println("Going to connect");
                        activeConnection        = OracleConnectionUtil.getConnection();
                        stmt                    = activeConnection.createStatement();
                        query = "DELETE FROM CM_USER_DETAILS where USERNAME='"+userName+"'";
                        System.out.println("website_oracle :: CreateUser.java :: query1 :: ["+query+"]");
                        flag =stmt.executeUpdate(query);
			System.out.println("Flag is"+flag);
              		  if(flag==1)
				{
                		  System.out.println("DATA DELETED FROM CM_USER_DETAILS TABLE");
               			 }
                }
                	catch(Exception Eb)
                	{
                        	Eb.printStackTrace();
                	}
                finally
                {
                        try	
			{
                              stmt.close();
                              activeConnection.close();
                        }
			catch(Exception e){e.printStackTrace();}
                }
                response.sendRedirect("jsp/manageUser.jsp");

        }
        public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
		 {
                doGet(request, response);
        }

}


