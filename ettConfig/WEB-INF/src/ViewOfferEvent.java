import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class ViewOfferEvent extends HttpServlet
{
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException
	{
		HttpSession Session=request.getSession(true);
		String application_username     = null;
		ArrayList FileStatusList = new ArrayList ();
		ArrayList listEventId = new ArrayList ();
		String offerId	=null;
		String	keyValues="";
		try
		{
			offerId=request.getParameter("offerId");	
				
			if(offerId== null || offerId.equals(""))
			{
				//keyValues=(String)getAppKey(offerId);
                                //Session.setAttribute("keyValues", keyValues);
				offerId="0";
			}
			GetResultData(FileStatusList,offerId );
			keyValues=(String)getAppKey(offerId);

			getOfferId(listEventId);
			Session.setAttribute("listEventId", listEventId);
			Session.setAttribute("FileStatusList", FileStatusList);
			
			if(FileStatusList.size()==0 && Integer.parseInt(offerId) !=0)
                        {
				Session.setAttribute("keyValues", keyValues);
                                //response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=D009&redirect=offer_redirectEvent.jsp"));
                                System.out.print("OfferDetails [jsp/message.jsp?ST=D004&redirect=../AddOfferEvent?EventofferId="+offerId+"]");
				request.setAttribute(offerId,offerId );
                                response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=D004&redirect=../AddOfferEvent?EventofferId="+offerId+""));
                        }

                        else{
                                response.sendRedirect(response.encodeURL("jsp/OfferEventView.jsp"));
                        }

			//response.sendRedirect(response.encodeURL("jsp/OfferEventView.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In ViewOffer.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
		}
	}
	public String GetDate()
	{
		Calendar cal = Calendar.getInstance();
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
	public void GetResultData(ArrayList FileStatusList,String offerId)
	{
		String SelectQuery 	= "";
		Statement selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		if(offerId==null || offerId.equals("") || offerId.equals("0"))
		{
			SelectQuery="select * from OfferEventDetails";
		}
		else{
			SelectQuery="select * from OfferEventDetails where offerId="+offerId+"";
		}
		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In ViewOffer.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In ViewOffer.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			OfferEventDetails dbean = null;
			while(DetailsResultSet.next())
			{
				dbean = new OfferEventDetails();
				dbean.setId(DetailsResultSet.getString(1));
				dbean.setAppKey(DetailsResultSet.getString(2));
				dbean.setBalanceCreditInDays(DetailsResultSet.getString(3));
				dbean.setCreatedTime(DetailsResultSet.getString(4));

				dbean.setCurrency(DetailsResultSet.getString(5));
				dbean.setEventName(DetailsResultSet.getString(6));
				dbean.setEventValue(DetailsResultSet.getString(7));
				dbean.setIncomeAmount(DetailsResultSet.getString(8));
				dbean.setInstalledAmount(DetailsResultSet.getString(9));
				dbean.setNotification(DetailsResultSet.getString(10));
				dbean.setOfferAmount(DetailsResultSet.getString(11));
				dbean.setOfferCat(DetailsResultSet.getString(12));
				dbean.setOfferId(DetailsResultSet.getString(13));
				dbean.setPendingAmountCredit(DetailsResultSet.getString(14));
				dbean.setPendingRecCount(DetailsResultSet.getString(15));
				dbean.setPendingRecDay(DetailsResultSet.getString(16));
                                dbean.setStatus(DetailsResultSet.getString(17));

				FileStatusList.add(dbean);
			}
			System.out.println("in java file : "+FileStatusList.size());
			DetailsResultSet.close();
			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			System.out.println("["+GetDate()+"]"+"In ViewOffer.java:    SQL Error" + ESQL.getMessage());
		}
		finally
		{
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("["+GetDate()+"]"+"In ViewOffer.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
		}
	}
	public void getOfferId(ArrayList listEventId)
        {
                String SelectQuery              = "";
                Statement selectStmt            = null;
                Connection activeConnection     = null;
                ResultSet DetailsResultSet      = null;
                SelectQuery="select offerId from OfferDetails where multipleEventOffer=1";
                System.out.println(SelectQuery);
                try
                {
                        activeConnection  = OracleConnectionUtil.getConnection();
                }
                catch(Exception od)
                {
                        System.out.println("["+GetDate()+"]"+"In ViewOfferEvent.java:   Not able to connect with ORACLE");
                        od.printStackTrace();
                }
                System.out.println("["+GetDate()+"]"+"In ViewOfferEvent.java:    ORA Connection Created!");
                try
                {
                        selectStmt = activeConnection.createStatement();
                        DetailsResultSet = selectStmt.executeQuery(SelectQuery);
						while(DetailsResultSet.next())
                        {
                                
                                listEventId.add(DetailsResultSet.getString(1));
                        }
                        System.out.println("listEventId in java file : "+listEventId.size());
                        DetailsResultSet.close();
                        selectStmt.close();
                        activeConnection.close();
                        System.out.println("query has been executed...!!!");
                }
				catch(SQLException ESQL)
                {
                        System.out.println("["+GetDate()+"]"+"In ViewOfferEvent.java:    SQL Error" + ESQL.getMessage());
                }
                finally
                {
                        try
                        {
                                activeConnection.close();
                        }
                        catch(Exception ee)
                        {
                                System.out.println("["+GetDate()+"]"+"In ViewOfferEvent.java:    Exception: " +ee.getMessage());
                                ee.printStackTrace();
                        }
                }


        }
	public String getAppKey(String offerId)
        {
                String SelectQuery              = "";
                Statement selectStmt            = null;
                Connection activeConnection     = null;
                ResultSet DetailsResultSet      = null;
		String result			= "";
                SelectQuery="select offerId,appKey from OfferDetails where offerId="+offerId+"";
                try
                {
                        activeConnection  = OracleConnectionUtil.getConnection();
                }
                catch(Exception od)
                {
                        System.out.println("["+GetDate()+"]"+"In ViewOfferEvent.java:   Not able to connect with ORACLE");
                        od.printStackTrace();
                }
                System.out.println("["+GetDate()+"]"+"In ViewOfferEvent.java:    ORA Connection Created!");
                try
                {
                        selectStmt = activeConnection.createStatement();
                        DetailsResultSet = selectStmt.executeQuery(SelectQuery);
                                                while(DetailsResultSet.next())
                        {

                                result=DetailsResultSet.getString(1)+"#"+DetailsResultSet.getString(2);
                        }
                        //System.out.println("listEventId in java file : "+listEventId.size());
                        DetailsResultSet.close();
                        selectStmt.close();
                        activeConnection.close();
                        System.out.println("query has been executed...!!!");
                }
                                catch(SQLException ESQL)
                {
                        System.out.println("["+GetDate()+"]"+"In ViewOfferEvent.java:    SQL Error" + ESQL.getMessage());
                }
                finally
                {
			try
                        {
                                activeConnection.close();
                        }
                        catch(Exception ee)
                        {
                                System.out.println("["+GetDate()+"]"+"In ViewOfferEvent.java:    Exception: " +ee.getMessage());
                                ee.printStackTrace();
                        }
                }
		return result;


        }


}

