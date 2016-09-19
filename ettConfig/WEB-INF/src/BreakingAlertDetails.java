import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class BreakingAlertDetails extends HttpServlet
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

		try
		{
			GetResultData(FileStatusList );
			Session.setAttribute("FileStatusList", FileStatusList);
			response.sendRedirect(response.encodeURL("jsp/BreakingAlertView.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In BreakingAlertDetails.java:    SQLState:  " + Eb.getMessage() );
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
	public void GetResultData(ArrayList FileStatusList)
	{
		String SelectQuery 	= "";
		Statement selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		SelectQuery="select * from BreakingAlert";
		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In BreakingAlertDetails.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In BreakingAlertDetails.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			BreakingAlertBean dbean = null;
			while(DetailsResultSet.next())
			{
				dbean = new BreakingAlertBean();
				dbean.setId(DetailsResultSet.getString(1));
				dbean.setStatus(DetailsResultSet.getString(2));
				dbean.setText(DetailsResultSet.getString(3));
				dbean.setImageUrl(DetailsResultSet.getString(4));
				dbean.setValidity(DetailsResultSet.getString(5));
				dbean.setClickable(DetailsResultSet.getString(6));
				dbean.setOnClickType(DetailsResultSet.getString(7));
				dbean.setOfferId(DetailsResultSet.getString(8));
				dbean.setActionUrl(DetailsResultSet.getString(9));
				dbean.setMenuName(DetailsResultSet.getString(10));
				dbean.setPopUpHeading(DetailsResultSet.getString(11));
				dbean.setPopUpText(DetailsResultSet.getString(12));
				dbean.setPopUpButtonText(DetailsResultSet.getString(13));
				dbean.setPopUpActionUrl(DetailsResultSet.getString(14));
				FileStatusList.add(dbean);
			}
			System.out.println("in java file : "+FileStatusList.size());
			DetailsResultSet.close();
			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			System.out.println("["+GetDate()+"]"+"In BreakingAlertDetails.java:    SQL Error" + ESQL.getMessage());
		}
		finally
		{
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("["+GetDate()+"]"+"In BreakingAlertDetails.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
		}
	}

}

