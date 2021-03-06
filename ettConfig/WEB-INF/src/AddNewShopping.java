import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class AddNewShopping extends HttpServlet
{
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException
        {
		doGet(request,response);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException
	{
		HttpSession Session=request.getSession(true);
		String application_username     = null;
		application_username= (String)Session.getAttribute("userName");
		String offerId     = null;
		String offerName   = null;
 		String actionUrl   = null;
 		String description = null;
 		String imageUrl    = null;
 		String offerType   = null;
 		String priority    = null;
 		String status      = null;
 		String category    = null;
 		String vendor      = null;
 		String title       = null;
 		String ButtonText  = null;
 		String offerAmount = null;
 		String createdTime = null;
		String gridText		=null;
		String gridImage	=null;
		String gridColorCode	=null;
		String gridTextUrl	=null;
		String offerSource	="";
		String affiliateUrl	="";
		int flag 			= 0;
		int size			= -1;
		try
		{
			offerName		= request.getParameter("offerName");
			actionUrl		= request.getParameter("actionUrl");
			offerType		= request.getParameter("offerType");
			description		= request.getParameter("description");
			offerAmount		= request.getParameter("offerAmount");
			imageUrl		= request.getParameter("imageUrl");
			priority		= request.getParameter("priority");
			status			= request.getParameter("status");
			ButtonText     		= request.getParameter("ButtonText");
			vendor    		= request.getParameter("vendor");
			title			= request.getParameter("title");
			gridText		= request.getParameter("gridText");
			gridImage		= request.getParameter("gridImage");
			gridColorCode		= request.getParameter("gridColorCode");
			gridTextUrl		= request.getParameter("gridTextUrl");
			offerSource		= request.getParameter("offerSource");
			affiliateUrl		= request.getParameter("affiliateUrl");
			System.out.println("["+GetDate()+"]"+" offerName ["+offerName+"], actionUrl ["+actionUrl+"], offerType ["+offerType+"], description ["+description+"], offerAmount ["+offerAmount+"], imageUrl ["+imageUrl+"], priority ["+priority+"], status ["+status+"]");
			System.out.println("["+GetDate()+"]"+" offerName is "+offerName);
			
			if(offerName == null || offerName.equals(""))
			{
				flag = -1;

			}
			if(flag == 0)
			{
				flag = -1;
				size = GetResultData(offerName,actionUrl,offerType,description,offerAmount,imageUrl,priority,status,ButtonText,vendor,title,gridText,gridImage,gridColorCode,gridTextUrl,offerSource,affiliateUrl,application_username);
			}
			if(size == 0 )
			{
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E021&redirect=shoping_config.jsp"));	
			}
			else
				response.sendRedirect(response.encodeURL("jsp/shoping_config.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In AddNewShopping.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016&redirect=shoping_config.jsp"));
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
	public int GetResultData(String offerName,String actionUrl,String offerType,String description,String offerAmount,String imageUrl,String priority,String status,String ButtonText,String vendor,String title,String gridText,String gridImage,String gridColorCode,String gridTextUrl,String offerSource,String affiliateUrl,String application_username)
	{
		String SelectQuery 		= "";
		PreparedStatement  selectStmt   = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;

		SelectQuery="insert into ShoppingDetails(offerName,actionUrl,description,imageUrl,offerType,priority,status,vendor,title,ButtonText,amount,createdTime,gridText,gridImage,gridColorCode,gridTextUrl,offerSource,affiliateUrl) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In AddNewShopping.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In AddNewShopping.java:    ORA Connection Created! and Execute Query User ["+application_username+"] Query ["+SelectQuery+"]");
		try
		{
			selectStmt = activeConnection.prepareStatement(SelectQuery);
			selectStmt.setString(1,offerName);
			selectStmt.setString(2,actionUrl);
			selectStmt.setString(3,description);
			selectStmt.setString(4,imageUrl);
			selectStmt.setString(5,offerType);
			selectStmt.setString(6,priority);
			selectStmt.setString(7,status);
			selectStmt.setString(8,vendor);
			selectStmt.setString(9,title);
			selectStmt.setString(10,ButtonText);
			selectStmt.setString(11,offerAmount);
			selectStmt.setString(12,GetDate());
			selectStmt.setString(13,gridText);
			selectStmt.setString(14,gridImage);
			selectStmt.setString(15,gridColorCode);
			selectStmt.setString(16,gridTextUrl);
			selectStmt.setString(17,offerSource);
			selectStmt.setString(18,affiliateUrl);
			int numRowsChanged = selectStmt.executeUpdate();
			size = 0;	
			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			size = 0;	
			System.out.println("["+GetDate()+"]"+"In AddNewShopping.java:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In AddNewShopping.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}

}

