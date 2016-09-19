package com;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import oracle.jdbc.driver.*;

public  class UpdateCMConnection extends HttpServlet
{
        public void doGet(HttpServletRequest request,HttpServletResponse response)
        {
                Connection activeConnection = null;
		String cname=null;
		String ctps=null;
		String cprefix=null;
		String ctype=null;
		String curl=null;
		String cuname=null;
		String cpassword=null;
		String cip=null;
		String cport=null;
		String cSys_id = null;
		String cthrottle = null; 
		String Qry = "";
		String action=null;
		System.out.println("In UpdateCMConnection");
                try
                {
			action = request.getParameter("action").trim();
			cname = request.getParameter("cname").trim();
			if(action.equals("delete"))
			{
				Qry = "Delete from CM_CONNECTION_DETAILS where CONNECTION_NAME='"+cname+"'";
			}
			else if (action.equals("update"))
			{
					curl =request.getParameter("ckannel").trim();
					ctps = request.getParameter("ctps").trim();
					Qry = "update CM_CONNECTION_DETAILS set CONNECTION_TPS='"+ctps+"',CONNECTION_URL='"+curl+"' where CONNECTION_NAME='"+cname+"'";
			}
			else
			{
			System.out.println("action not defiend");
			}
                        System.out.println("Going to connect");
                        activeConnection  = OracleConnectionUtil.getConnection();
                        System.out.println("ORA Connection Created!");
			Statement stmt = activeConnection.createStatement();
			System.out.println("Update/Delete qry for CM Connection ["+Qry+"]");
			stmt.executeUpdate(Qry);
			activeConnection.commit();
			stmt.close();
			activeConnection.close();
			response.sendRedirect(response.encodeURL("jsp/addNewConn.jsp"));
                }
		catch(Exception e)
                {
			e.printStackTrace();
                }
        }

        public void doPost(HttpServletRequest request,HttpServletResponse response)
        {
                doGet(request, response);
        }

}


