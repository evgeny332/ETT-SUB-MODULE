import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class AddOfferEvent extends HttpServlet
{
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException
	{
		HttpSession Session=request.getSession(true);
		String EventofferId     = request.getParameter("EventofferId");
		String application_username     = null;
		String id 			= null;
	 	String appKey 			= null;
	 	String balanceCreditInDays 	=null;
	 	String createdTime 		=null;
	 	String currency 		=null;
	 	String eventName 		=null;
	 	String eventValue 		=null;
	 	String incomeAmount 		=null;
	 	String installedAmount 		=null;
	 	String notification 		=null;
	 	String offerAmount 		=null;
	 	String offerCat 		=null;
	 	String offerId 			=null;
	 	String pendingAmountCredit 	=null;
	 	String pendingRecCount 		=null;
	 	String pendingRecDay 		=null;
	 	String status 			=null;
		String payOutHoldinMin          = "";
		String payOutHoldNotification   = null;
		int flag 			= 0;
		int size			= -1;

		try
		{
			application_username= (String)Session.getAttribute("userName");
                        appKey                  = request.getParameter("appKey");
                        balanceCreditInDays     = request.getParameter("balanceCreditInDays");
                        currency                = request.getParameter("currency");
                        eventName               = request.getParameter("eventName");
                        eventValue              = request.getParameter("eventValue");
                        incomeAmount            = request.getParameter("incomeAmount");
                        installedAmount         = request.getParameter("installedAmount");
                        notification            = request.getParameter("notification");
                        offerAmount             = request.getParameter("offerAmount");
                        offerCat                = request.getParameter("offerCat");
                        offerId                 = request.getParameter("offerId");
                        pendingAmountCredit     = request.getParameter("pendingAmountCredit");
                        pendingRecCount         = request.getParameter("pendingRecCount");
                        pendingRecDay           = request.getParameter("pendingRecDay");
                        status                  = request.getParameter("status");
                        String reason           ="";
                        String Source           ="";
			try{
			
				offerId		= request.getParameter("offerId");
				if(EventofferId==null || EventofferId.equals(""))
                		{
                        		EventofferId=(String)request.getAttribute(offerId);
                		}

				String keyValues=(String)getAppKey(EventofferId);
				Session.setAttribute("keyValues", keyValues);
			}
                        catch(Exception ex)
                        {
                        }

                        try
                        {
                                Source          =request.getParameter("Source");
                                reason          =request.getParameter("reason");
				payOutHoldinMin =request.getParameter("payOutHoldinMin");
				payOutHoldNotification =request.getParameter("payOutHoldNotification");
                        }
                        catch(Exception ex)
                        {
                        }
			if(payOutHoldinMin ==null ||payOutHoldinMin.equals(""))
                        {
                                payOutHoldinMin="0";
                        }
                        if(payOutHoldNotification ==null || payOutHoldNotification.equals(""))
                        { payOutHoldNotification ="";
                        }
                        if(eventName == null || eventName.equals(""))
                        {
                                flag = -1;
                        }
                        if(flag == 0)
                        {
                                size = GetResultData(appKey,balanceCreditInDays,currency,eventName,eventValue,incomeAmount,installedAmount,notification,offerAmount,offerCat,offerId,pendingAmountCredit,pendingRecCount,pendingRecDay,status,reason,Source,payOutHoldinMin,payOutHoldNotification,application_username);
                        }

			if(size == 0 )
			{
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E021&redirect=offer_redirectEvent.jsp"));	
			}
			else
			{
				response.sendRedirect(response.encodeURL("jsp/offerAddEvent.jsp"));
			}
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In AddOfferEvent.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016&redirect=offer_redirectEvent.jsp"));
		}

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
	public int GetResultData(String appKey,String balanceCreditInDays,String currency,String eventName,String eventValue,String incomeAmount,String installedAmount,String notification,String offerAmount,String offerCat,String offerId,String pendingAmountCredit,String pendingRecCount,String pendingRecDay,String status,String reason,String Source,String payOutHoldinMin,String payOutHoldNotification,String application_username)
	{
		String SelectQuery 		= "";
		PreparedStatement  selectStmt   = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;

		SelectQuery="insert into ett.OfferEventDetails(appKey,balanceCreditInDays,currency,eventName,eventValue,incomeAmount,installedAmount,notification,offerAmount,offerCat,offerId,pendingAmountCredit ,pendingRecCount, pendingRecDay, status,createdTime,payOutHoldinMin,payOutHoldNotification) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In AddOfferEvent.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		try
		{
			selectStmt = activeConnection.prepareStatement(SelectQuery);
			selectStmt.setString(1,appKey);
			selectStmt.setString(2,balanceCreditInDays);
			selectStmt.setString(3,currency);
			selectStmt.setString(4,eventName);
			selectStmt.setString(5,eventValue);
			selectStmt.setString(6,incomeAmount);
			selectStmt.setString(7,installedAmount);
			selectStmt.setString(8,notification);
			selectStmt.setString(9,offerAmount);
			selectStmt.setString(10,offerCat);
			selectStmt.setString(11,offerId);
			selectStmt.setString(12,pendingAmountCredit);
			selectStmt.setString(13,pendingRecCount);
                        selectStmt.setString(14,pendingRecDay);
			selectStmt.setString(15,status);
			selectStmt.setString(16,GetDate());
			selectStmt.setString(17,payOutHoldinMin);
                        selectStmt.setString(18,payOutHoldNotification);
			int numRowsChanged = selectStmt.executeUpdate();

			size = 0;	
			selectStmt.close();
			activeConnection.close();
			String insertQ="insert into ett.OfferEventDetails(appKey,balanceCreditInDays,currency,eventName,eventValue,incomeAmount,installedAmount,notification,offerAmount,offerCat,offerId,pendingAmountCredit ,pendingRecCount, pendingRecDay, status,createdTime,payOutHoldinMin,payOutHoldNotification) values('"+appKey+"',"+balanceCreditInDays+","+currency+",'"+eventName+"','"+eventValue+"','"+incomeAmount+"','"+installedAmount+"','"+notification+"',"+offerAmount+",'"+offerCat+"',"+offerId+",'"+pendingAmountCredit+"','"+pendingRecCount+"','"+pendingRecDay+"','"+status+"','"+GetDate()+"',"+payOutHoldinMin+",'"+payOutHoldNotification+"')";

			System.out.println("["+GetDate()+"] ETT_CONFIG_DETAILS || Source ["+Source+"] || UserName ["+application_username+"] || which reason ["+reason+"] || Execute Query [ "+insertQ.replaceAll("\n"," ")+" ]");
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			size = 0;	
			System.out.println("["+GetDate()+"]"+"In AddOfferEvent.java:    SQL Error" + ESQL.getMessage());
			return size;
		}
		finally
		{
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("["+GetDate()+"]"+"In AddOfferEvent.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}
	  public String getAppKey(String offerId)
        {
                String SelectQuery              = "";
                Statement selectStmt            = null;
                Connection activeConnection     = null;
                ResultSet DetailsResultSet      = null;
                String result                   = "";
                SelectQuery="select offerId,appKey from OfferDetails where offerId="+offerId+"";
                try
                {
                        activeConnection  = OracleConnectionUtil.getConnection();
                }
                catch(Exception od)
                {
                        System.out.println("["+GetDate()+"]"+"In AddOfferEvent.java:   Not able to connect with ORACLE");
                        od.printStackTrace();
                }
                System.out.println("["+GetDate()+"]"+"In AddOfferEvent.java:    ORA Connection Created! SelectQuery ["+SelectQuery+"]");
                try
                {
                        selectStmt = activeConnection.createStatement();
                        DetailsResultSet = selectStmt.executeQuery(SelectQuery);
                                                while(DetailsResultSet.next())
                        {

                                result=DetailsResultSet.getString(1)+"#"+DetailsResultSet.getString(2);
                        }
                        DetailsResultSet.close();
                        selectStmt.close();
                        activeConnection.close();
                        System.out.println("query has been executed...!!!");
                }
                                catch(SQLException ESQL)
                {
                        System.out.println("["+GetDate()+"]"+"In AddOfferEvent.java:    SQL Error" + ESQL.getMessage());
                }
                finally
                {
                        try
                        {
				activeConnection.close();
                        }
                        catch(Exception ee)
                        {
                                System.out.println("["+GetDate()+"]"+"In AddOfferEvent.java:    Exception: " +ee.getMessage());
                                ee.printStackTrace();
                        }
                }
                return result;


        }



}

