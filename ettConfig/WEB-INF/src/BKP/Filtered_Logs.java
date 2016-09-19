import com.dndbean.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date.*;
import java.text.*;
import java.sql.*;


public class Filtered_Logs extends HttpServlet
{
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException
	{

		HttpSession Session=request.getSession(true);
		String application_username     = null;
		String fromDateTime             = null;
		String toDateTime               = null;
		String showDate                 = null;
		String user_circle              = null;
		String circleselected		= null;
		int dateflage                   = 0;
		ArrayList FileStatusList = new ArrayList ();
		String HubID = "";//get this from session
		try
		{
			HubID =(String)Session.getAttribute("HUB_ID");
			if(HubID == null || HubID.equals(""))
			{
				HubID = "1";
			}
			user_circle =(String)Session.getAttribute("user_circle");
	
			circleselected  =request.getParameter("circle");
                        System.out.println("circleselected"+circleselected);
		
			fromDateTime    = request.getParameter("DateTime1");
			System.out.println("["+GetDate()+"]"+"fromDateTime is "+fromDateTime);
			if(fromDateTime == null || fromDateTime.equals(""))
			{
				dateflage = -1;
			}

			toDateTime    = request.getParameter("DateTime2");
			System.out.println("["+GetDate()+"]"+"toDateTime is "+toDateTime);
			if(toDateTime == null || toDateTime.equals(""))
			{
				dateflage = -1;
			}
			if(dateflage == 0)
			{
				GetResultData(fromDateTime, toDateTime, circleselected, HubID, FileStatusList );
			}
			Session.setAttribute("FileStatusList", FileStatusList);
			response.sendRedirect(response.encodeURL("jsp/Filtered_Logs.jsp"));
			System.out.println("query has been executed...!!!");
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In Filtered_Logs.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
		}
	}
	public void GetResultData(String fromDateTime, String toDateTime,String circleselected,String HubID,ArrayList FileStatusList)
	{
		String TableName = "";
		String SelectQuery = "";
		Statement selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;

		TableName = "DND_CALL_REPORT";

		if(circleselected.equals("0"))
                {
                        SelectQuery="select T1.DATEVAL ,T2.NAME,T1.ACTION1,T1.ACTION2,T1.ACTION3,T1.ACTION4,T1.TOTAL ,T1.HUBID from "+TableName+" T1 , CIRCLE_CODE T2 where T1.DATEVAL >= '"+fromDateTime+"' AND T1.DATEVAL <='"+toDateTime+"' AND T1.CIRCLE = T2.CIRCLE";
                }
                else
                {
                        SelectQuery="select T1.DATEVAL ,T2.NAME,T1.ACTION1,T1.ACTION2,T1.ACTION3,T1.ACTION4,T1.TOTAL ,T1.HUBID from "+TableName+" T1 , CIRCLE_CODE T2 where T1.DATEVAL >= '"+fromDateTime+"' AND T1.DATEVAL <='"+toDateTime+"' AND T1.CIRCLE = T2.CIRCLE and T1.circle='"+circleselected+"'";
                }
		
		System.out.println("["+GetDate()+"]"+"In Filtered_Logs.java:    Query to be run is "+SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In Filtered_Logs.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In Filtered_Logs.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			Filtered_LogsBean dbean = null;
			while(DetailsResultSet.next())
			{
				dbean = new Filtered_LogsBean();
				int disconnected = Integer.parseInt(DetailsResultSet.getString(4))+Integer.parseInt(DetailsResultSet.getString(5))+Integer.parseInt(DetailsResultSet.getString(6));
                                String disconnectedstring = Integer.toString(disconnected);

                                dbean.setDatetime(DetailsResultSet.getString(1));
                                dbean.setCircle(DetailsResultSet.getString(2));
                                dbean.setTotal(DetailsResultSet.getString(7));
                                dbean.setConnected(DetailsResultSet.getString(3));
                                dbean.setHubid(DetailsResultSet.getString(8));
                                dbean.setDisconnected(disconnectedstring);
				
				FileStatusList.add(dbean);
			}
			System.out.println("in Filtered_Logs.java : "+FileStatusList.size());
			selectStmt.close();
			activeConnection.close();
		}
		catch(SQLException ESQL)
		{
			System.out.println("["+GetDate()+"]"+"In Filtered_Logs.java:    SQL Error" + ESQL.getMessage());
		}
		finally
		{
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("["+GetDate()+"]"+"In Filtered_Logs.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
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

}

