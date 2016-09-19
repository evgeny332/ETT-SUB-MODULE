import com.dndbean.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date.*;
import java.text.*;
import java.sql.*;


public class de_CircleWiseReport extends HttpServlet
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
		String circleselected		= null;
		String user_circle              = null;
		int dateflage                   = 0;
		int size			= -1;
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
			circleselected  =request.getParameter("circle");
			System.out.println("circleselected"+circleselected);
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
                                response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E111&redirect=de_circlewisereport.jsp"));
                        else
                                response.sendRedirect(response.encodeURL("jsp/de_circlewisereport.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In de_CircleWiseReport.java:    SQLState:  " + Eb.getMessage() );
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
		TableName ="summary_report_hub"+HubID;
		/*
		if(circleselected.equals("0"))
                {
                        SelectQuery="select T1.FILE_NAME,T1.DATEVAL,T1.TIMEVAL,T2.name,T1.TOTAL,T1.SUCCESS,T1.FAIL from "+TableName+" T1 INNER JOIN CIRCLE_CODE T2 using(CIRCLE) where T1.DATEVAL >= '"+fromDateTime+"'AND T1.DATEVAL <= '"+toDateTime+"' ";
                }
                else
                {
                        SelectQuery="select T1.FILE_NAME,T1.DATEVAL,T1.TIMEVAL,T2.name,T1.TOTAL,T1.SUCCESS,T1.FAIL from "+TableName+" T1 , CIRCLE_CODE T2 where T1.DATEVAL >= '"+fromDateTime+"'AND T1.DATEVAL <= '"+toDateTime+"' AND T1.circle='"+circleselected+"' AND T1.circle =T2.circle";
                }
		*/
		/*SelectQuery="select T1.DATEVAL,T1.TIMEVAL,T1.FILE_NAME,T1.TOTAL,T1.SUCCESS,T1.FAIL,T1.UP_DATE,T1.UP_TIME from registrationreport T1 where T1.DATEVAL >= '"+fromDateTime+"'AND T1.DATEVAL <= '"+toDateTime+"'AND T1.TYPE='D' order by T1.DATEVAL desc ,T1.TIMEVAL desc";
*/

SelectQuery = "select to_char(T1.SDATETIME,'yyyy-mm-dd HH24:mi:ss') , T1.FILENAME,T1.TOTALCOUNT,T1.SUCCESSCOUNT,T1.FAILCOUNT,to_char(T1.EDATETIME,'yyyy-mm-dd HH24:mi:ss') from FILEDETAILS T1 where SUBSTR(to_char(T1.SDATETIME,'yyyy-mm-dd HH24:mi:ss'),1,10)>= SUBSTR(to_char(to_date('"+fromDateTime+"','yyyy-mm-dd HH24:mi:ss'),'yyyy-mm-dd HH24:mi:ss'),1,10) AND SUBSTR(to_char(T1.SDATETIME,'yyyy-mm-dd HH24:mi:ss'),1,10)<= SUBSTR(to_char(to_date('"+toDateTime+"','yyyy-mm-dd HH24:mi:ss'),'yyyy-mm-dd HH24:mi:ss'),1,10) AND T1.FILEACTION = 'DELETE' order by T1.SDATETIME desc";







		System.out.println("["+GetDate()+"]"+"In de_CircleWiseReport.java:    Query to be run is "+SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In de_CircleWiseReport.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In de_CircleWiseReport.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			de_CircleWiseReportBean dbean = null;
			while(DetailsResultSet.next())
			{


                                dbean = new de_CircleWiseReportBean();
                                dbean.setFilename(DetailsResultSet.getString(2));
                                dbean.setSdatetime(DetailsResultSet.getString(1));
                        //      dbean.setTimeval(DetailsResultSet.getString(2));
                                dbean.setTotalcount(DetailsResultSet.getString(3));
                                dbean.setSuccesscount(DetailsResultSet.getString(4));
                                dbean.setFailcount(DetailsResultSet.getString(5));
                                dbean.setEdatetime(DetailsResultSet.getString(6));
                        //      dbean.setUpdatetime(DetailsResultSet.getString(8));
                                FileStatusList.add(dbean);
/*

				dbean = new de_CircleWiseReportBean();
				dbean.setFile(DetailsResultSet.getString(3));
				dbean.setDateval(DetailsResultSet.getString(1));
				dbean.setTimeval(DetailsResultSet.getString(2));
				//dbean.setCircle(DetailsResultSet.getString(4));
				dbean.setTotal(DetailsResultSet.getString(4));
				dbean.setSuccess(DetailsResultSet.getString(5));
				dbean.setFail(DetailsResultSet.getString(6));
				dbean.setUpdatedate(DetailsResultSet.getString(7));
                                dbean.setUpdatetime(DetailsResultSet.getString(8));
				FileStatusList.add(dbean); */
			}
			System.out.println("in java file : "+FileStatusList.size());
			if(FileStatusList.size() == 0)
                                size = 0;
			DetailsResultSet.close();
                        selectStmt.close();
			activeConnection.close();
		}
		catch(SQLException ESQL)
		{
			System.out.println("["+GetDate()+"]"+"In de_CircleWiseReport.java:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In de_CircleWiseReport.java:    Exception: " +ee.getMessage());
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

