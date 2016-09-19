import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class BreakingAlert extends HttpServlet
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

		int flag 			= 0;
		int size			= -1;
		ArrayList OperatorList = new ArrayList ();
		ArrayList OfferList = new ArrayList();

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
			
			if(text == null || text.equals(""))
			{
				flag = -1;
				GetOperators(OperatorList);
                                GetOfferType(OfferList);

			}
			if(flag == 0)
			{
				flag = -1;
				size = GetResultData(status,text,imageUrl,validity,clickable,onClickType,offerId,actionUrl,menuName,popUpHeading,popUpText,popUpButtonText,popUpActionUrl);
				//GetOperators(OperatorList);
				//GetOfferType(OfferList);
			}
			Session.setAttribute("OperatorList", OperatorList);
			Session.setAttribute("OfferList", OfferList);
			if(size == 0 )
			{
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E021&redirect=BreakingAlert.jsp"));	
			}
			else
				response.sendRedirect(response.encodeURL("jsp/BreakingAlert.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In BreakingAlert.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016&redirect=BreakingAlert.jsp"));
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
	public void GetOfferType(ArrayList OfferList)
	{
		String SelectQuery              = "";
                Statement selectStmt            = null;
                Connection activeConnection     = null;
                ResultSet DetailsResultSet      = null;
                SelectQuery="select offer_type from offertypedetials";
                System.out.println(SelectQuery);
                try
                {
                        activeConnection  = OracleConnectionUtil.getConnection();
                }
                catch(Exception od)
                {
                        System.out.println("["+GetDate()+"]"+"In BreakingAlert.java:   Not able to connect with ORACLE");
                        od.printStackTrace();
                }
                System.out.println("["+GetDate()+"]"+"In BreakingAlert.java:    ORA Connection Created!");
                try
                {
                        selectStmt = activeConnection.createStatement();
                        DetailsResultSet = selectStmt.executeQuery(SelectQuery);
                        OfferTypeBean dbean = null;
                        while(DetailsResultSet.next())
                        {
                                dbean = new OfferTypeBean();
                                dbean.setOffer_type(DetailsResultSet.getString(1));
                                //OfferList.add(dbean);
                                OfferList.add(DetailsResultSet.getString(1));
                        }
                        System.out.println("OfferList in java file : "+OfferList.size());
                        DetailsResultSet.close();
                        selectStmt.close();
                        activeConnection.close();
                        System.out.println("query has been executed...!!!");
                }
                catch(SQLException ESQL)
                {
                        System.out.println("["+GetDate()+"]"+"In BreakingAlert.java:    SQL Error" + ESQL.getMessage());
                }
                finally
                {
                        try
                        {
                                activeConnection.close();
			}
                        catch(Exception ee)
                        {
                                System.out.println("["+GetDate()+"]"+"In BreakingAlert.java:    Exception: " +ee.getMessage());
                                ee.printStackTrace();
                        }
                }
	
	}
	public void GetOperators(ArrayList OperatorList)
	{
		String SelectQuery      	= "";
                Statement selectStmt            = null;
                Connection activeConnection     = null;
                ResultSet DetailsResultSet      = null;
		SelectQuery="select name from operator";
                System.out.println(SelectQuery);
                try
                {
                        activeConnection  = OracleConnectionUtil.getConnection();
                }
                catch(Exception od)
                {
                        System.out.println("["+GetDate()+"]"+"In BreakingAlert.java:   Not able to connect with ORACLE");
                        od.printStackTrace();
                }
                System.out.println("["+GetDate()+"]"+"In BreakingAlert.java:    ORA Connection Created!");
                try
                {
			selectStmt = activeConnection.createStatement();
                        DetailsResultSet = selectStmt.executeQuery(SelectQuery);
                        OperatorsBean dbean = null;
                        while(DetailsResultSet.next())
                        {
                                dbean = new OperatorsBean();
                                dbean.setName(DetailsResultSet.getString(1));
				//OperatorList.add(dbean);
				OperatorList.add(DetailsResultSet.getString(1));
                        }
                        System.out.println("OperatorList in java file : "+OperatorList.size());
                        DetailsResultSet.close();
                        selectStmt.close();
                        activeConnection.close();
                        System.out.println("query has been executed...!!!");
                }
                catch(SQLException ESQL)
                {
                        System.out.println("["+GetDate()+"]"+"In BreakingAlert.java:    SQL Error" + ESQL.getMessage());
                }
                finally
                {
                        try
                        {
                                activeConnection.close();
                        }
                        catch(Exception ee)
                        {
                                System.out.println("["+GetDate()+"]"+"In BreakingAlert.java:    Exception: " +ee.getMessage());
                                ee.printStackTrace();
                        }
                }

		
	}
	public int GetResultData(String status,String text,String imageUrl,String validity,String clickable,String onClickType,String offerId,String actionUrl,String menuName,String popUpHeading,String popUpText,String popUpButtonText,String popUpActionUrl)
	{
		String SelectQuery 	= "";
		PreparedStatement  selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;
		//System.out.println("["+GetDate()+"]"+"In BreakingAlert.java : DATA ["+status+","+String+","+text+","+imageUrl+","+validity+","+clickable+","+onClickType+","+offerId+","+actionUrl+","+menuName+","+popUpHeading+","+popUpText+","+popUpButtonText+","+popUpActionUrl+"]");
		SelectQuery="insert into ett.BreakingAlert(status,text,imageUrl,validity,clickable,onClickType,offerId,actionUrl,menuName,popUpHeading,popUpText,popUpButtonText,popUpActionUrl) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In BreakingAlert.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In BreakingAlert.java:    ORA Connection Created!");
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
			System.out.println("["+GetDate()+"]"+"In BreakingAlert.java: query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			size = 0;	
			System.out.println("["+GetDate()+"]"+"In BreakingAlert.java:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In BreakingAlert.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}

}

