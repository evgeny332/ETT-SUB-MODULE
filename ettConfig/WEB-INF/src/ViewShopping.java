import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class ViewShopping extends HttpServlet
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
			response.sendRedirect(response.encodeURL("jsp/viewShopping.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In ViewShopping.java:    SQLState:  " + Eb.getMessage() );
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
		SelectQuery="select * from ShoppingDetails";
		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In ViewShopping.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In ViewShopping.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			ShoppingDetailsBean dbean = null;
			while(DetailsResultSet.next())
			{
				dbean = new ShoppingDetailsBean();
				dbean.setOfferId(DetailsResultSet.getString(1));
				dbean.setOfferName(DetailsResultSet.getString(2));
				dbean.setActionUrl(DetailsResultSet.getString(3));
				dbean.setDescription(DetailsResultSet.getString(4));
				dbean.setImageUrl(DetailsResultSet.getString(5));
				dbean.setOfferType(DetailsResultSet.getString(6));
				dbean.setPriority(DetailsResultSet.getString(7));
				dbean.setStatus(DetailsResultSet.getString(8));
				dbean.setCategory(DetailsResultSet.getString(9));

				dbean.setVendor(DetailsResultSet.getString(10));
				dbean.setTitle(DetailsResultSet.getString(11));
				dbean.setButtonText(DetailsResultSet.getString(12));
				dbean.setAmount(DetailsResultSet.getString(13));
				dbean.setCreatedTime(DetailsResultSet.getString(15));
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
			System.out.println("["+GetDate()+"]"+"In ViewShopping.java:    SQL Error" + ESQL.getMessage());
		}
		finally
		{
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("["+GetDate()+"]"+"In ViewShopping.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
		}
	}

}

