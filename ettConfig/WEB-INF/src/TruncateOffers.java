import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class TruncateOffers extends HttpServlet
{
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException
	{
		//HttpSession Session=request.getSession(true);
		//String application_username     = null;
		String FirstHitDay              = null;
		String TodaysOffers             = null;
		int dateflag 			= 0;
		int size			= -1;

		try
		{
				String query="truncate table TodaysOffers";
                                size=GetResultData(query);
			/*if(size == 0 )
			{
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E111&redirect=EditFirstHitDay.jsp"));	
			}
			else*/
			response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E021"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In TruncateOffers.java:    SQLState:  " + Eb.getMessage() );
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

	public int GetResultData(String SelectQuery)
	{
		Statement selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;
		System.out.println("["+GetDate()+"]"+"In TruncateOffers.java: Delete Query : "+SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In TruncateOffers.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In TruncateOffers.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
                        size = selectStmt.executeUpdate(SelectQuery);

			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			size = 0;	
			System.out.println("["+GetDate()+"]"+"In TruncateOffers.java:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In TruncateOffers.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}

}

