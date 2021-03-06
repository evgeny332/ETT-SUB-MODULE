import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class PopUpAlertUpdate extends HttpServlet
{
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException
	{
		HttpSession Session=request.getSession(true);
		String application_username     = null;
		String id		= null;
		String status		= null;
		String heading		= null;	
		String text		= null;
		String noOfButton	= null;
		String buttonsText	= null;
		String actinoUrl	= null;
		int flag 			= 0;
		int size			= -1;

		try
		{
			id		= request.getParameter("id");
			status		= request.getParameter("status");
			heading		= request.getParameter("heading");
			text		= request.getParameter("text");
			noOfButton	= request.getParameter("noOfButton");
			buttonsText	= request.getParameter("buttonsText");
			actinoUrl	= request.getParameter("actionUrl");
			application_username= (String)Session.getAttribute("userName");
			System.out.println("["+GetDate()+"]"+" heading is "+heading);
			
			if(heading == null || heading.equals(""))
			{
				flag = -1;
			}
			if(flag == 0)
			{
				size = GetResultData(id,status,heading,text,noOfButton,buttonsText,actinoUrl,application_username);
			}
			if(size == 0 )
			{
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E021&redirect=pop_redirect.jsp"));	
			}
			else
				response.sendRedirect(response.encodeURL("jsp/pop_redirect.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In PopUpAlertUpdate.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016&redirect=pop_redirect.jsp"));
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
	public int GetResultData(String id,String status,String heading,String text,String noOfButton,String buttonsText,String actinoUrl,String application_username)
	{
		String SelectQuery 		= "";
		PreparedStatement  selectStmt   = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;

		SelectQuery="update ett.PopUpAlert set status=?,heading=?,text=?,noOfButton=?,buttonsText=?,actinoUrl=? where id='"+id+"'";
		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In PopUpAlertUpdate.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In PopUpAlertUpdate.java:    ORA Connection Created! and Execute Query User ["+application_username+"] Query ["+SelectQuery+"]");
		try
		{
			selectStmt = activeConnection.prepareStatement(SelectQuery);
			selectStmt.setString(1,status);
			selectStmt.setString(2,heading);
			selectStmt.setString(3,text);
			selectStmt.setString(4,noOfButton);
			selectStmt.setString(5,buttonsText);
			selectStmt.setString(6,actinoUrl);
			
			int numRowsChanged = selectStmt.executeUpdate();
			size = 0;	
			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			size = 0;	
			System.out.println("["+GetDate()+"]"+"In PopUpAlertUpdate.java:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In PopUpAlertUpdate.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}

}

