import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

import java.net.*;
public class OffersPayoutAmount extends HttpServlet
{
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException
        {
		doGet(request,response);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException
	{
		HttpSession Session=request.getSession(true);
		String application_username     = null;
		String offerId			=null;
		String offerName		=null;
		String offerAmount		=null;
		String EttId			=null;
		String notification		=null;
		String vendor			=null;
		String Remarks			=null;
		int flag 			= 0;
		int size			= -1;
		try
		{
			offerId		= request.getParameter("offerId"); 
			offerName	= request.getParameter("offerName");
			EttId		= request.getParameter("EttID");
			offerAmount	= request.getParameter("offerAmount");
			notification	= request.getParameter("notification");
			vendor		= request.getParameter("vendor");
			Remarks		= request.getParameter("Remarks");
			application_username= (String)Session.getAttribute("userName");	
			System.out.println("["+GetDate()+"]"+" EttID ["+EttId+"], offerId ["+offerId+"], Amount ["+offerAmount+"], notification["+notification+"] PAYOUT_USER_DEATILS ["+application_username+"]");
			
			if(EttId == null || EttId.equals(""))
			{
				flag = -1;

			}
			if(offerId == null || offerId.equals(""))
                        {
                                flag = -1;

                        }
			if(notification == null || notification.equals(""))
                        {
                                flag = -1;

                        }


			if(flag == 0)
			{
				SendUDP("OFFER_PAYOUT#"+offerId+"#"+offerName+"#"+EttId+"#"+offerAmount+"#"+notification+"#"+vendor+"#"+Remarks,"54.209.220.78","9000");
				size=0;
			}
			if(size == 0 )
			{
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E021&redirect=offers_payout.jsp"));	
			}
			else
				response.sendRedirect(response.encodeURL("jsp/offers_payout.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In RefundAmt.java :    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016&redirect=refundBulkAmt.jsp"));
		}

	}
	public String SendUDP(String strFinal,String IP,String port)
        {
                System.out.println("[THIRD PARTY PAYOUT AMOUNT] FINAL String "+strFinal+":::"+IP+"::"+port);
                String resp="";
                try
                {
                        DatagramSocket  clientSocket = new DatagramSocket();
                        int localport=clientSocket.getLocalPort();
                        String portn=localport+"";
                        strFinal=strFinal.replace("LPORT",portn);
                        InetAddress IPAddress=InetAddress.getByName(IP);
                        DatagramPacket sendPacket = new DatagramPacket(strFinal.getBytes(),strFinal.getBytes().length,IPAddress,Integer.parseInt(port));
                        clientSocket.send(sendPacket);
                        System.out.println("[THIRD PARTY PAYOUT AMOUNT] SendUDP["+strFinal+"] IP:"+IP+" Port:"+port);
                        clientSocket.close();
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                        System.out.println("[THIRD PARTY PROMOTION] Exception When send Packet!!!!!!!!!! "+e);
                }
                return resp;
        }
	public String GetDateDef( String DATE_FORMAT_NOW)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
	public String GetDate()
	{
		Calendar cal = Calendar.getInstance();
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
	public String NewGetDate()
	{
		Calendar cal = Calendar.getInstance();
		String DATE_FORMAT_NOW = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}


}

