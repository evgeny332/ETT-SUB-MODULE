import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class UpdateBreakingAlert extends HttpServlet
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
		String status 		= null;
		String text 		= null;
		String imageUrl 	= null;
		String validity 	= null;
		String clickable 	= null;
		String onClickType 	= null;
		String actionUrl 	= null;
		String menuName 	= null;
		String popUpHeading 	= null;
		String popUpText 	= null;
		String popUpButtonText 	= null;
		String popUpActionUrl 	= null;
		String offerId		= null;
		String id		= null;

		int flag 			= 0;
		int size			= -1;

		try
		{
			actionUrl	= request.getParameter("actionUrl");
			text		= request.getParameter("text");
			validity	= request.getParameter("validity");
			if(validity==null || validity.equalsIgnoreCase("null")){
				validity="";
			}

                        clickable	= request.getParameter("clickable");
			onClickType	= request.getParameter("onClickType");
			imageUrl	= request.getParameter("imageUrl");
			 if(imageUrl==null || imageUrl.equalsIgnoreCase("null")){
                                imageUrl="";
                        }

                        menuName	= request.getParameter("menuName");
                        if(menuName==null || menuName.equalsIgnoreCase("null")){
                                menuName="";
                        }

			popUpHeading	= request.getParameter("popUpHeading");
			status		= request.getParameter("status");
			popUpText	= request.getParameter("popUpText");
			popUpButtonText = request.getParameter("popUpButtonText");
                        popUpActionUrl  = request.getParameter("popUpActionUrl");
			offerId		= request.getParameter("offerId");
			id		= request.getParameter("id");
			
			if(text == null || text.equals(""))
			{
				flag = -1;

			}
			if(flag == 0)
			{
				flag = -1;
				size = GetResultData(id,status,text,imageUrl,validity,clickable,onClickType,offerId,actionUrl,menuName,popUpHeading,popUpText,popUpButtonText,popUpActionUrl);
			}
			if(size == 0 )
			{
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E021&redirect=BrackingRedirect.jsp"));	
			}
			else
				response.sendRedirect(response.encodeURL("jsp/BrackingRedirect.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In UpdateBreakingAlert.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016&redirect=UpdateBreakingAlert.jsp"));
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
	public int GetResultData(String id,String status,String text,String imageUrl,String validity,String clickable,String onClickType,String offerId,String actionUrl,String menuName,String popUpHeading,String popUpText,String popUpButtonText,String popUpActionUrl)
	{
		String SelectQuery 	= "";
		PreparedStatement  selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;
		SelectQuery="update ett.BreakingAlert set status=?,text=?,imageUrl=?,validity=?,clickable=?,onClickType=?,offerId=?,actionUrl=?,menuName=?,popUpHeading=?,popUpText=?,popUpButtonText=?,popUpActionUrl=? where id="+id+"";
		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In UpdateBreakingAlert.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In UpdateBreakingAlert.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.prepareStatement(SelectQuery);
			selectStmt.setString(1,status);
			selectStmt.setString(2,text);
			selectStmt.setString(3,imageUrl);
			selectStmt.setString(4,validity);
			selectStmt.setString(5,clickable);
			selectStmt.setString(6,onClickType);
			selectStmt.setString(7,offerId);
			selectStmt.setString(8,actionUrl);
			selectStmt.setString(9,menuName);
			selectStmt.setString(10,popUpHeading);
			selectStmt.setString(11,popUpText);
			selectStmt.setString(12,popUpButtonText);
			selectStmt.setString(13,popUpActionUrl);

			
			int numRowsChanged = selectStmt.executeUpdate();
			size = 0;	
			selectStmt.close();
			activeConnection.close();
			System.out.println("["+GetDate()+"]"+"In UpdateBreakingAlert.java: query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			size = 0;	
			System.out.println("["+GetDate()+"]"+"In UpdateBreakingAlert.java:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In UpdateBreakingAlert.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}

}

