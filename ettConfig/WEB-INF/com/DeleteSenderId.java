package com;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import oracle.jdbc.driver.*;

public  class DeleteSenderId extends HttpServlet
{
        public void init(ServletConfig config)  throws ServletException
        {
                super.init(config);
        }

        public void doGet(HttpServletRequest request,HttpServletResponse response) throws  IOException
        {
                PrintWriter out                 = response.getWriter();
                System.out.println("Executing Login Servlet.........");
                String query                    = null;
                String oldSenderId              = null;
                Statement selectQuery           = null;
                Connection activeConnection     = null;
                HttpSession Session             = request.getSession(true);

                String flag = "";

                try
                {
                        System.out.println("Going to connect");
                        oldSenderId             = request.getParameter("oldSenderId").trim();
                        System.out.println("website_oracle :: InsertSenderId.java :: oldSenderId :: ["+oldSenderId+"]");
                        activeConnection        = OracleConnectionUtil.getConnection();
                        selectQuery             = activeConnection.createStatement();
                        query = "delete from cm_sender_id where sender_id='"+oldSenderId+"'";
                        selectQuery.executeUpdate(query);
                        activeConnection.commit();
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
                //out.write("done");

        }
        public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
        {
                doGet(request, response);
        }

}

