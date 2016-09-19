import com.dndbean.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date.*;
import java.text.*;
import java.sql.*;


public class SummaryReport extends HttpServlet
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
				GetResultData(fromDateTime, toDateTime, user_circle, HubID, FileStatusList );
			}

			Session.setAttribute("FileStatusList", FileStatusList);
			response.sendRedirect(response.encodeURL("jsp/summary.jsp"));
			System.out.println("query has been executed...!!!");
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In SummaryReport.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
		}
	}
	public void GetResultData(String fromDateTime, String toDateTime,String user_circle,String HubID,ArrayList FileStatusList)
	{
		String TableName = "";
		String SelectQuery = "";
		Statement selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;

		TableName = "SUMMARY_REPORT_HUB"+HubID;

		if(user_circle == null || user_circle.equals("") || user_circle.equals("-") )
		{
			SelectQuery="select T1.FILE_NAME,T1.DATEVAL,T1.TIMEVAL,T1.TOTAL,T1.SUCCESS,T1.FAIL from "+TableName+" T1 where T1.DATEVAL >= '"+fromDateTime+"'AND T1.DATEVAL <= '"+toDateTime+"'";
		}
		else
		{
			SelectQuery="select T1.FILE_NAME,T1.DATEVAL,T1.TIMEVAL,T1.TOTAL,T1.SUCCESS,T1.FAIL from "+TableName+" T1 where T1.DATEVAL >= '"+fromDateTime+"'AND T1.DATEVAL <= '"+toDateTime+"'";
		}
		//SelectQuery="select * from summary_report";
		System.out.println("["+GetDate()+"]"+"In SummaryReport.java:    Query to be run is "+SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In SummaryReport.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In SummaryReport.java:    ORA Connection Created!");
		try
		{
		selectStmt = activeConnection.createStatement();

		DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			SummaryReportBean dbean = null;
			//ArrayList FileStatusList = new ArrayList ();
			while(DetailsResultSet.next())
			{
				dbean = new SummaryReportBean();
				dbean.setFile(DetailsResultSet.getString(1));
				dbean.setDateval(DetailsResultSet.getString(2));
				dbean.setTimeval(DetailsResultSet.getString(3));
				dbean.setTotal(DetailsResultSet.getString(4));
				dbean.setSuccess(DetailsResultSet.getString(5));
				dbean.setFail(DetailsResultSet.getString(6));
				FileStatusList.add(dbean);
			}
			System.out.println("in SummaryReport.java file : "+FileStatusList.size());
			selectStmt.close();
			activeConnection.close();
		}
		catch(SQLException ESQL)
		{
			System.out.println("["+GetDate()+"]"+"In SummaryReport.java:    SQL Error" + ESQL.getMessage());
		}
		finally
		{
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("["+GetDate()+"]"+"In SummaryReport.java:    Exception: " +ee.getMessage());
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

