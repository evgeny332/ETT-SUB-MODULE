package com;

import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import oracle.jdbc.driver.*;

public  class InsertConnection extends HttpServlet
{
        public void doGet(HttpServletRequest request,HttpServletResponse response)
        {
                Connection activeConnection = null;
		String cname=null;
		String ctps=null;
		String cprefix=null;
		String ckannel= null;
		String Qry = "";
		System.out.println("In InsertConnection");
                try
                {
			cname = request.getParameter("ConName").trim();
			ctps = request.getParameter("TPS").trim();
			cprefix = request.getParameter("Prefix").trim();
			ckannel =request.getParameter("Kannel").trim();
				System.out.println("New Conn Details cname["+cname+"] ctps["+ctps+"] cprefix["+cprefix+"] ckannel["+ckannel+"]");
				Qry = "insert into CM_CONNECTION_DETAILS (CONNECTION_NAME,CONNECTION_TPS,CONNECTION_PREFIX,CONNECTION_URL) values('"+cname+"',"+ctps+",'"+cprefix+"','"+ckannel+"')";
                        System.out.println("Going to connect");
                        activeConnection  = OracleConnectionUtil.getConnection();
                        System.out.println("ORA Connection Created!");
			Statement stmt = activeConnection.createStatement();
			System.out.println("Insert qry for new Connection ["+Qry+"]");
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


