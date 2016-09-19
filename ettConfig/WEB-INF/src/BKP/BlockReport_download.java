import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class BlockReport_download extends HttpServlet
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
		String showDate                 = null;
		String user_circle              = null;
		int dateflage                   = 0;
		ArrayList FileStatusList = new ArrayList ();
		String HubID = "";
		int size                        = -1;
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
			if(dateflage == -1)
			{}
			else
			{
					size = GetResultData(fromDateTime, FileStatusList );
			}
			Session.setAttribute("FileStatusList", FileStatusList);
			if(size == 0 )
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E111&redirect=blockreport_download.jsp"));
			else
				response.sendRedirect(response.encodeURL("jsp/blockreport_download.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In BlockReport_download.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
		}
	}
	public int GetResultData(String fromDateTime, ArrayList FileStatusList)
	{
		String SelectQuery = "";
		Statement selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;

		SelectQuery = "select FILENAME ,PATH FROM BLOCKED_REPORT WHERE DATE_CREATED ='"+fromDateTime+"' and FILE_TYPE='voice'";
		System.out.println("["+GetDate()+"]"+"In BlockReport_download.java:    Query to be run is "+SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In BlockReport_download.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In BlockReport_download.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			while(DetailsResultSet.next())
			{
				FileStatusList.add(DetailsResultSet.getString(1));
				FileStatusList.add(DetailsResultSet.getString(2));
			}
			System.out.println("in java file : "+FileStatusList.size());
			if(FileStatusList.size() == 0)
				size = 0;
			System.out.println("query has been executed...!!!");
			DetailsResultSet.close();
			selectStmt.close();
			activeConnection.close();
		}
		catch(SQLException ESQL)
		{
			System.out.println("["+GetDate()+"]"+"In BlockReport_download.java:    SQL Error" + ESQL.getMessage());
			size	= 0;
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
				System.out.println("["+GetDate()+"]"+"In BlockReport_download.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}
	public String GetDate()
	{
		Calendar cal = Calendar.getInstance();
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

}

