import com.dndbean.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date.*;
import java.text.*;
import java.sql.*;


public class Conformance_Report extends HttpServlet
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
		int size                        = -1;
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
				size = GetResultData(fromDateTime, toDateTime, circleselected, HubID, FileStatusList );
			}
			Session.setAttribute("FileStatusList", FileStatusList);
			if(size == 0)
                                response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E111&redirect=conformance_report.jsp"));
                        else
                                response.sendRedirect(response.encodeURL("jsp/conformance_report.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In Conformance_Report.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
		}
	}
	public int GetResultData(String fromDateTime, String toDateTime,String circleselected,String HubID,ArrayList FileStatusList)
	{
		String TableName = "";
		String SelectQuery = "";
		Statement selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;
		TableName = "DND_CALL_REPORT";

		if(circleselected.equals("0"))
                {
                        SelectQuery="select T1.DATEVAL ,T2.NAME,T1.ACTION1,(T1.ACTION2+T1.ACTION3+T1.ACTION4+T1.ACTION5),T1.TOTAL ,round (((ACTION1/T1.TOTAL)*100),2),round ((((T1.ACTION2+T1.ACTION3+T1.ACTION4+T1.ACTION5)/T1.TOTAL)*100),2) from "+TableName+" T1 , CIRCLE_CODE T2 where T1.DATEVAL >= '"+fromDateTime+"' AND T1.DATEVAL <='"+toDateTime+"' AND T1.CIRCLE = T2.CIRCLE order by T1.DATEVAL desc";
                }
                else
                {
                        SelectQuery="select T1.DATEVAL ,T2.NAME,T1.ACTION1,(T1.ACTION2+T1.ACTION3+T1.ACTION4+T1.ACTION5),T1.TOTAL ,round (((ACTION1/T1.TOTAL)*100),2),round ((((T1.ACTION2+T1.ACTION3+T1.ACTION4+T1.ACTION5)/T1.TOTAL)*100),2) from "+TableName+" T1 , CIRCLE_CODE T2 where T1.DATEVAL >= '"+fromDateTime+"' AND T1.DATEVAL <='"+toDateTime+"' AND T1.CIRCLE = T2.CIRCLE and T1.circle='"+circleselected+"' order by T1.DATEVAL desc";
                }
		
		System.out.println("["+GetDate()+"]"+"In Conformance_Report.java:    Query to be run is "+SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In Conformance_Report.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In Conformance_Report.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			Conformance_ReportBean dbean = null;
			while(DetailsResultSet.next())
			{
				dbean = new Conformance_ReportBean();
				//int disconnected = Integer.parseInt(DetailsResultSet.getString(4))+Integer.parseInt(DetailsResultSet.getString(5))+Integer.parseInt(DetailsResultSet.getString(6));
                               // String disconnectedstring = Integer.toString(disconnected);

				dbean.setDatetime(DetailsResultSet.getString(1));
                                        dbean.setCircle(DetailsResultSet.getString(2));
                                        dbean.setTotal(DetailsResultSet.getString(5));
                                        dbean.setConnected(DetailsResultSet.getString(3));
                                        //dbean.setHubid(DetailsResultSet.getString(8));
                                        dbean.setDisconnected(DetailsResultSet.getString(4));
                                        dbean.setPconnected(DetailsResultSet.getString(6));
                                        dbean.setPdisconnected(DetailsResultSet.getString(7));
				FileStatusList.add(dbean);
			}
			System.out.println("in Conformance_Report.java : "+FileStatusList.size());
			if(FileStatusList.size()== 0)
                                size = 0;
		
			DetailsResultSet.close();
                        selectStmt.close();
			activeConnection.close();
		}
		catch(SQLException ESQL)
		{
			System.out.println("["+GetDate()+"]"+"In Conformance_Report.java:    SQL Error" + ESQL.getMessage());
		}
		finally
		{
			try
			{
				DetailsResultSet.close();
                        	selectStmt.close();
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("["+GetDate()+"]"+"In Conformance_Report.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
		}
		return size;
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

