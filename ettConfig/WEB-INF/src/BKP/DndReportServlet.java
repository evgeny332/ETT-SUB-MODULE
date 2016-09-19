import com.dndbean.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date.*;
import java.text.*;
import java.sql.*;


public class DndReportServlet extends HttpServlet
{
	//      private static int count   ;
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException
	{
		HttpSession Session=request.getSession(true);
		String application_username     = null;
		String fromDateTime             = null;
		String toDateTime             	= null;
		String user_circle              = null;
		String SelectQuery              = null;
		String HubID 			= "";
		int dataflag 			= 0;
		int size                        = -1;
		ArrayList FileStatusList 	= new ArrayList ();
		try
		{
			HubID =(String)Session.getAttribute("HUB_ID");
			if(HubID == null || HubID.equals(""))
			{
				HubID = "1";
			}
			user_circle =(String)Session.getAttribute("user_circle");
			fromDateTime    = request.getParameter("DateTime1");
			toDateTime    = request.getParameter("DateTime2");
			System.out.println("["+GetDate()+"]"+"fromDateTime is "+fromDateTime);
			System.out.println("["+GetDate()+"]"+"toDateTime is "+toDateTime);
			if(fromDateTime == null || fromDateTime.equals(""))
			{
				dataflag = -1;
			}

			if(toDateTime == null || toDateTime.equals(""))
			{
				dataflag = -1;
			}

			if(dataflag == 0)
			{
				size = GetResultData(fromDateTime,toDateTime,user_circle,FileStatusList,HubID);	
			}





			Session.setAttribute("FileStatusList", FileStatusList);
			if (size == 0 )
                                response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E111&redirect=dnd_report.jsp"));
                        else
                                response.sendRedirect(response.encodeURL("jsp/dnd_report.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In DndReportServlet.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
		}
	}

	public int GetResultData(String startdate, String enddate, String user_circle, ArrayList FileStatusList, String HubID)
	{
		String TableName = "";
		String SelectQuery = "";
		Statement selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;

		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In DndReportServlet.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In DndReportServlet.java:    ORA Connection Created!");


		TableName = "DND_CALL_REPORT";

		if(user_circle == null || user_circle.equals("") || user_circle.equals("-") )
		{
			SelectQuery="select T1.DATEVAL ,T2.NAME,T1.ACTION1,(T1.ACTION2+T1.ACTION3+T1.ACTION4+T1.ACTION5),T1.TOTAL ,round (((ACTION1/T1.TOTAL)*100),2),round ((((T1.ACTION2+T1.ACTION3+T1.ACTION4+T1.ACTION5)/T1.TOTAL)*100),2) from "+TableName+" T1 , CIRCLE_CODE T2 where T1.DATEVAL >='"+startdate+"' and T1.DATEVAL <= '"+enddate+"' AND T1.CIRCLE = T2.CIRCLE order by T1.DATEVAL desc";
		}
		else
		{
			SelectQuery="select T1.DATEVAL ,T2.NAME,T1.ACTION1,(T1.ACTION2+T1.ACTION3+T1.ACTION4+T1.ACTION5),T1.TOTAL ,round (((ACTION1/T1.TOTAL)*100),2),round ((((T1.ACTION2+T1.ACTION3+T1.ACTION4+T1.ACTION5)/T1.TOTAL)*100),2) from "+TableName+" T1 , CIRCLE_CODE T2 where T1.DATEVAL >='"+startdate+"' and T1.DATEVAL <= '"+enddate+"' AND T1.CIRCLE = T2.CIRCLE and T1.circle='"+user_circle+"' order by T1.DATEVAL desc";
		}
		System.out.println("["+GetDate()+"]"+"In DndReportServlet.java:    Query to be run is "+SelectQuery);
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			DndReportBean dbean = null;
			while(DetailsResultSet.next())
			{
				dbean = new DndReportBean();
				dbean.setDatetime(DetailsResultSet.getString(1));
				dbean.setCircle(DetailsResultSet.getString(2));
				dbean.setTotal(DetailsResultSet.getString(5));
				dbean.setConnected(DetailsResultSet.getString(3));
				dbean.setDisconnected(DetailsResultSet.getString(4));
				dbean.setPconnected(DetailsResultSet.getString(6));
				dbean.setPdisconnected(DetailsResultSet.getString(7));
				FileStatusList.add(dbean);
			}
			System.out.println("in java file : "+FileStatusList.size());
			if(FileStatusList.size() == 0)
                        {
                                size = 0;
                        }
			DetailsResultSet.close();
			selectStmt.close();
			activeConnection.close();
		}
		catch(SQLException ESQL)
		{
			System.out.println("["+GetDate()+"]"+"In DndReportServlet.java:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In DndReportServlet.java:    Exception: " +ee.getMessage());
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
