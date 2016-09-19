import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class PopUpAlertEdit extends HttpServlet
{
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException
	{
		HttpSession Session=request.getSession(true);
		String application_username     = null;
		ArrayList FileStatusList = new ArrayList ();
		String id		= "";	
		try
		{
			id=request.getParameter("id");
			System.out.println("PopUpAlertEdit id for edit new  offer : "+id);
			GetResultData(FileStatusList ,id);
			Session.setAttribute("FileStatusList", FileStatusList);
			response.sendRedirect(response.encodeURL("jsp/PopUpAlertEdit.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In PopUpAlertEdit.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
		}
	}
	public String GetDate()
	{
		Calendar cal = Calendar.getInstance();
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
	public void GetResultData(ArrayList FileStatusList, String id)
	{
		String SelectQuery 	= "";
		Statement selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		SelectQuery="select ifnull(id,''),ifnull(status,''),ifnull(heading,''),ifnull(text,''),ifnull(noOfButton,''),ifnull(buttonsText,''),ifnull(actinoUrl,'') from PopUpAlert where id='"+id+"'";
		System.out.println("in java file SK : "+SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In PopUpAlertEdit.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In PopUpAlertEdit.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			while(DetailsResultSet.next())
			{
				FileStatusList.add(DetailsResultSet.getString(1));
				FileStatusList.add(DetailsResultSet.getString(2));
				FileStatusList.add(DetailsResultSet.getString(3));
				FileStatusList.add(DetailsResultSet.getString(4));
				FileStatusList.add(DetailsResultSet.getString(5));
				FileStatusList.add(DetailsResultSet.getString(6));
				FileStatusList.add(DetailsResultSet.getString(7));
			}
			System.out.println("in java file SK: "+FileStatusList.size()+"] [data]["+FileStatusList+"]");
			DetailsResultSet.close();
			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			System.out.println("["+GetDate()+"]"+"In PopUpAlertEdit.java:    SQL Error" + ESQL.getMessage());
		}
		finally
		{
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("["+GetDate()+"]"+"In PopUpAlertEdit.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
		}
	}

}

