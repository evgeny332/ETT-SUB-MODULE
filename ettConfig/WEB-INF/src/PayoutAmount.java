import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class PayoutAmount extends HttpServlet
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
		application_username= (String)Session.getAttribute("userName");
		String fromDate   		= null;
        	String vendor   		= null;
	       	String amount   		= null;
		int flag 			= 0;
		int size			= -1;
		ArrayList OperatorList = new ArrayList ();
		ArrayList OfferList = new ArrayList();

		try
		{
			fromDate		= request.getParameter("DateTime1");
			vendor			= request.getParameter("vendor");
			amount			= request.getParameter("amount");
			
			if(fromDate == null || fromDate.equals(""))
			{
				flag = -1;

			}
			if(flag == 0)
			{
				flag = -1;
				size = GetResultData(fromDate,vendor,amount,application_username);
			}
			if(size == 0 )
			{
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E021&redirect=AmountPayout.jsp"));	
			}
			else
				response.sendRedirect(response.encodeURL("jsp/AmountPayout.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In PayoutAmount:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016&redirect=offer_configNew.jsp"));
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
	public void GetOfferType(ArrayList OfferList)
	{
		String SelectQuery              = "";
                Statement selectStmt            = null;
                Connection activeConnection     = null;
                ResultSet DetailsResultSet      = null;
                SelectQuery="select offer_type from offertypedetials";
                System.out.println(SelectQuery);
                try
                {
                        activeConnection  = OracleConnectionUtil.getConnection();
                }
                catch(Exception od)
                {
                        System.out.println("["+GetDate()+"]"+"In PayoutAmount:   Not able to connect with ORACLE");
                        od.printStackTrace();
                }
                System.out.println("["+GetDate()+"]"+"In PayoutAmount:    ORA Connection Created!");
                try
                {
                        selectStmt = activeConnection.createStatement();
                        DetailsResultSet = selectStmt.executeQuery(SelectQuery);
                        OfferTypeBean dbean = null;
                        while(DetailsResultSet.next())
                        {
                                dbean = new OfferTypeBean();
                                dbean.setOffer_type(DetailsResultSet.getString(1));
                                //OfferList.add(dbean);
                                OfferList.add(DetailsResultSet.getString(1));
                        }
                        System.out.println("OfferList in java file : "+OfferList.size());
                        DetailsResultSet.close();
                        selectStmt.close();
                        activeConnection.close();
                        System.out.println("query has been executed...!!!");
                }
                catch(SQLException ESQL)
                {
                        System.out.println("["+GetDate()+"]"+"In PayoutAmount:    SQL Error" + ESQL.getMessage());
                }
                finally
                {
                        try
                        {
                                activeConnection.close();
			}
                        catch(Exception ee)
                        {
                                System.out.println("["+GetDate()+"]"+"In PayoutAmount:    Exception: " +ee.getMessage());
                                ee.printStackTrace();
                        }
                }
	
	}
	public void GetOperators(ArrayList OperatorList)
	{
		String SelectQuery      	= "";
                Statement selectStmt            = null;
                Connection activeConnection     = null;
                ResultSet DetailsResultSet      = null;
		SelectQuery="select name from operator";
                System.out.println(SelectQuery);
                try
                {
                        activeConnection  = OracleConnectionUtil.getConnection();
                }
                catch(Exception od)
                {
                        System.out.println("["+GetDate()+"]"+"In PayoutAmount:   Not able to connect with ORACLE");
                        od.printStackTrace();
                }
                System.out.println("["+GetDate()+"]"+"In PayoutAmount:    ORA Connection Created!");
                try
                {
			selectStmt = activeConnection.createStatement();
                        DetailsResultSet = selectStmt.executeQuery(SelectQuery);
                        OperatorsBean dbean = null;
                        while(DetailsResultSet.next())
                        {
                                dbean = new OperatorsBean();
                                dbean.setName(DetailsResultSet.getString(1));
				//OperatorList.add(dbean);
				OperatorList.add(DetailsResultSet.getString(1));
                        }
                        System.out.println("OperatorList in java file : "+OperatorList.size());
                        DetailsResultSet.close();
                        selectStmt.close();
                        activeConnection.close();
                        System.out.println("query has been executed...!!!");
                }
                catch(SQLException ESQL)
                {
                        System.out.println("["+GetDate()+"]"+"In PayoutAmount:    SQL Error" + ESQL.getMessage());
                }
                finally
                {
                        try
                        {
                                activeConnection.close();
                        }
                        catch(Exception ee)
                        {
                                System.out.println("["+GetDate()+"]"+"In PayoutAmount:    Exception: " +ee.getMessage());
                                ee.printStackTrace();
                        }
                }

		
	}
	public int GetResultData(String fromDate,String vendor,String amount,String application_username)
	{
		String SelectQuery 		= "";
		Statement selectStmt		= null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;

		SelectQuery="insert into RechargeTracker(createdTime,date,vender,type,balance) values (now(),'"+fromDate+"','"+vendor+"','Deposit','"+amount+"')";
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In PayoutAmount:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In PayoutAmount:    ORA Connection Created! and Execute Query User ["+application_username+"] Query ["+SelectQuery+"]");
		try
		{
			selectStmt = activeConnection.createStatement();

			int Result = selectStmt.executeUpdate(SelectQuery);
			size = 0;	
			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			size = 0;	
			System.out.println("["+GetDate()+"]"+"In PayoutAmount:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In PayoutAmount:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}

}

