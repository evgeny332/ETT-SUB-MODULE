//package com.mcarbon.db;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.net.*;
import java.util.*;
import com.mcarbon.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class UrnManagement extends HttpServlet
{
        public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {

                System.out.println("-----------------Inside UserManagement.java ------------");
                HttpSession session = request.getSession(true);
                String username     = null;
                try
                {
                        username    = (String)session.getAttribute("username");
                        //username    = username.trim();
                }catch(Exception e)
                {
                        response.sendRedirect(response.encodeURL("jsp/login.jsp?ST=E004"));
                        e.printStackTrace();
                }
                String name         = "";
                String pass         = "";
                String REQTYPE      = request.getParameter("REQTYPE");
                String result       = "";
                String TM_URN       = "";
		String circle_id    = "";
		String type	    = "";
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                System.out.println("awts_reports ::REQTYPE is "+ REQTYPE);
                try{

                        if(REQTYPE.equals("GETNUMBER"))
                        {       String [] res = null;
                                try{
                                        TM_URN           = request.getParameter("urn").trim();
                                        System.out.println("queryToGetRefNumber GETRETAILERDATA dis_id["+TM_URN+"]");
                                }catch(Exception e){
                                        System.out.println("queryToGetRefNumber GETRETAILERDATA dis_id["+TM_URN+"]");
                                }
                                try{
                                        result = "";
                                        com.mcarbon.InsertIntoDb idb = new com.mcarbon.InsertIntoDb();
                                        res              = idb.getNumber(TM_URN);
                                        System.out.println("queryToGetRefNumber file has been executed in DB");
                                        for (int i=0;i<res.length;i++)
                                        {
                                                result =result+":"+ res[i];
                                        }
                                        out.write(result);
                                }
                                catch(Exception e)
                                {
                                        response.sendRedirect(response.encodeURL("jsp/displayMessage.jsp?ST=Please Try after Some Time"));
                                        System.out.println("file not in update in DB issue"+e);
                                }
                        }


			else if(REQTYPE.equals("GETNUMBER1"))
                        {       String [] res = null;
                                try{
                                        TM_URN           = request.getParameter("urn").trim();
                                        System.out.println("queryToGetRefNumber GETRETAILERDATA dis_id["+TM_URN+"]");
                                }catch(Exception e){
                                        System.out.println("queryToGetRefNumber GETRETAILERDATA dis_id["+TM_URN+"]");
                                }
                                try{
                                        result = "";
                                        com.mcarbon.InsertIntoDb idb = new com.mcarbon.InsertIntoDb();
                                        res              = idb.getNumber1(TM_URN);
                                        System.out.println("queryToGetRefNumber file has been executed in DB");
                                        for (int i=0;i<res.length;i++)
                                        {
                                                result =result+":"+ res[i];
                                        }
                                        out.write(result);
                                }
                                catch(Exception e)
                                {
                                        response.sendRedirect(response.encodeURL("jsp/displayMessage.jsp?ST=Please Try after Some Time"));
                                        System.out.println("file not in update in DB issue"+e);
                                }
                        }

			//GETNAME
			else if(REQTYPE.equals("GETNAME"))
                        {       String [] res = null;
                                try{
                                        TM_URN           = request.getParameter("URN").trim();
                                        System.out.println("queryToGetRefNumber GETRETAILERDATA dis_id["+TM_URN+"]");
                                }catch(Exception e){
                                        System.out.println("queryToGetRefNumber GETRETAILERDATA dis_id["+TM_URN+"]");
                                }
                                try{
                                        result = "";
                                        com.mcarbon.InsertIntoDb idb = new com.mcarbon.InsertIntoDb();
                                        res              = idb.getName(TM_URN);
                                        System.out.println("queryToGetRefNumber file has been executed in DB");
                                        for (int i=0;i<res.length;i++)
                                        {
                                                result =result+":"+ res[i];
                                        }
                                        out.write(result);
                                }
                                catch(Exception e)
                                {
                                        response.sendRedirect(response.encodeURL("jsp/displayMessage.jsp?ST=Please Try after Some Time"));
                                        System.out.println("file not in update in DB issue"+e);
                                }
                        }
			else if(REQTYPE.equals("GETTMNUMBER"))
                        {       String [] res = null;
                                try{
                                        circle_id           = request.getParameter("circle_id").trim();
                                        System.out.println("queryToGetTMNumber ["+circle_id+"]");
                                }catch(Exception e){
                                        System.out.println("queryToGetTMNumber ["+circle_id+"]");
                                }
                                try{
                                        result = "";
                                        com.mcarbon.InsertIntoDb idb = new com.mcarbon.InsertIntoDb();
                                        res              = idb.getTMNumber(circle_id);
                                        System.out.println("queryToTMNumber file has been executed in DB");
                                        for (int i=0;i<res.length;i++)
                                        {
                                                result =result+":"+ res[i];
                                        }
                                        out.write(result);
                                }
                                catch(Exception e)
                                {
                                        response.sendRedirect(response.encodeURL("jsp/displayMessage.jsp?ST=Please Try after Some Time"));
                                        System.out.println("file not in update in DB issue"+e);
                                }
                        }
			else if(REQTYPE.equals("GETURN"))
                        {       String [] res = null;
                                try{
                                        type           = request.getParameter("type_id").trim();
					System.out.println("query to get urn :::"+type);
                                        System.out.println("queryToGetTMNumber ["+type+"]");
                                }catch(Exception e){
                                        System.out.println("queryToGetTMNumber ["+type+"]");
                                }
                                try{
                                        result = "";
                                        com.mcarbon.InsertIntoDb idb = new com.mcarbon.InsertIntoDb();
                                        res              = idb.getURN(type);
                                        System.out.println("query To get urn has been executed in DB");
                                        for (int i=0;i<res.length;i++)
                                        {
                                                result =result+":"+ res[i];
                                        }
                                        out.write(result);
                                }
                                catch(Exception e)
                                {
                                        response.sendRedirect(response.encodeURL("jsp/displayMessage.jsp?ST=Please Try after Some Time"));
                                        System.out.println("file not in update in DB issue"+e);
                                }
                        }


        }
	catch(Exception e)
	{
		System.out.println(e);
	}
	}
        public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
                doGet( request, response );
        }


}

