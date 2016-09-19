import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class EarntTT_CDR extends HttpServlet
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
		int dateflag 			= 0;
		int size			= -1;
		ArrayList FileStatusList = new ArrayList ();

		try
		{
			fromDateTime    = request.getParameter("DateTime1");
			System.out.println("["+GetDate()+"]"+"fromDateTime is "+fromDateTime);
			if(fromDateTime == null || fromDateTime.equals(""))
			{
				dateflag = -1;
			}
			toDateTime    = request.getParameter("DateTime2");
			System.out.println("["+GetDate()+"]"+"toDateTime is "+toDateTime);
			if(toDateTime == null || toDateTime.equals(""))
			{
				dateflag = -1;
			}
			if(dateflag == 0)
			{
				GetResultData(fromDateTime, toDateTime,FileStatusList );
			}
			Session.setAttribute("FileStatusList", FileStatusList);
			if(size == 0 )
			{
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E111&redirect=earnTT_cdr.jsp"));	
			}
			else
				response.sendRedirect(response.encodeURL("jsp/earnTT_cdr.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In EarntTT_CDR.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
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

	public int GetResultData(String fromDateTime, String toDateTime, ArrayList FileStatusList)
	{
		String SelectQuery 	= "";
		Statement selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;

		SelectQuery="select * from EarnTTCDR where date_time >= '"+fromDateTime+"'AND date_time <= '"+toDateTime+"'";
		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In EarntTT_CDR.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In EarntTT_CDR.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			EarnTTCDR dbean = null;
			while(DetailsResultSet.next())
			{
				dbean = new EarnTTCDR();
				dbean.setReport_id(DetailsResultSet.getString(1));
				dbean.setMsisdn(DetailsResultSet.getString(2));
				dbean.setTid(DetailsResultSet.getString(3));
				dbean.setEmailid(DetailsResultSet.getString(4));
				dbean.setOs_type(DetailsResultSet.getString(5));
				dbean.setType(DetailsResultSet.getString(6));
				dbean.setStatus(DetailsResultSet.getString(7));
				dbean.setStatus_desc(DetailsResultSet.getString(8));
				dbean.setDate_time(DetailsResultSet.getString(9));
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
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			size = 0;	
			System.out.println("["+GetDate()+"]"+"In EarntTT_CDR.java:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In EarntTT_CDR.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}

}

