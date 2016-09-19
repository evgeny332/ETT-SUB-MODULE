package com;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import oracle.jdbc.driver.*;

public  class UpdateUser extends HttpServlet
{
        public void init(ServletConfig config)  throws ServletException
        {
                super.init(config);
        }
        public void doGet(HttpServletRequest request,HttpServletResponse response) throws  IOException
        {
                PrintWriter out                 = response.getWriter();
                System.out.println("Executing DeleteUser Servlet.........");
                String query1                    = null;
                String userName                 = null;
                Connection activeConnection     = null;
		Statement       stmt            = null;
		String Assign_TPS		= null;
		String Connection		= null;
                HttpSession Session             = request.getSession(true);

                int flag = 0;
	

                try
                {
                        userName          = request.getParameter("hidd_userName").trim();
			
			String[] selectMember=request.getParameterValues("usrmemberid");
			for(int i=0;i<=selectMember.length;i++)
				{
					String total =selectMember[i];
					System.out.println("total"+total);
				}
			String conn_name=
			System.out.println("selectMember"+selectMember[0]);
			System.out.println("selectMember"+selectMember[1]);
                        System.out.println("website_oracle :: CreateUser.java :: cons :: ["+userName+"]");
                        System.out.println("Going to connect");
                        activeConnection        = OracleConnectionUtil.getConnection();
                        stmt                    = activeConnection.createStatement();
			ResultSet rs,rs1 = null;
/*			query ="select sum(CONNECTION_TPS) from CM_USER_CONNECTION_DETAILS where USERNAME='"+userName+"';


//                        query = "update CM_USER_CONNECTION_DETAILS set CONNECTION_NAME='"+Connection+"',CONNECTION_TPS='"+Assign_TPS+"'where USERNAME='"+userName+"'";
                        System.out.println("website_oracle :: CreateUser.java :: query1 :: ["+query+"]");
       	                rs	 =stmt.executeQuery(query);
			while (RS.next())
                        {
				String pretotal=String.valueOf(RS.getInt(1));

			}*/

			query1="delete from CM_USER_CONNECTION_DETAILS where USERNAME='"+userName+"'";
			rs1       =stmt.executeQuery(query1);
//			query2 ="insert into CM_USER_CONNECTION_DETAILS values ('"+userName+"',)
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


