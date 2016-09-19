import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class AddNewOffer extends HttpServlet
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
		String offerName   = null;
        	String actionUrl   = null;
	       	String offerType   = null;
        	String description = null;
        	String offerAmount = null;
        	String appKey      = null;
        	String imageUrl    = null;
        	String priority    = null;
        	String Operators   = null;
        	String status      = null;
		String size_data   = null;
		String networkType = null;
                String alertText   = null;
		String balanceType = null;
		String category		= null; 
		String balanceCreditInDays = null;
		String isClick		= null;
		String isDownload = null;
		String isShare= null;
		String isShop= null;
		String root= null;
		String venderName	=null;

		int flag 			= 0;
		int size			= -1;
		ArrayList OperatorList = new ArrayList ();
		ArrayList OfferList = new ArrayList();

		try
		{
			offerName	= request.getParameter("offerName");
			actionUrl	= request.getParameter("actionUrl");
			offerType	= request.getParameter("offerType");
			description	= request.getParameter("description");
			offerAmount	= request.getParameter("offerAmount");
			appKey		= request.getParameter("appKey");
			imageUrl	= request.getParameter("imageUrl");
			priority	= request.getParameter("priority");
			Operators	= request.getParameter("Operators");
			status		= request.getParameter("status");
			size_data	= request.getParameter("size");
			networkType     = request.getParameter("networkType");
                        alertText       = request.getParameter("alertText");
			balanceType     = request.getParameter("balanceType");
			isClick     = request.getParameter("isClick");
			isDownload     = request.getParameter("isDownload");
			isShare     = request.getParameter("isShare");
			isShop     = request.getParameter("isShop");
			balanceCreditInDays     = request.getParameter("balanceCreditInDays");
			category     = request.getParameter("category");
			root     = request.getParameter("root");
			venderName=request.getParameter("venderName");

			System.out.println("["+GetDate()+"]"+" offerName ["+offerName+"], actionUrl ["+actionUrl+"], offerType ["+offerType+"], description ["+description+"], offerAmount ["+offerAmount+"], appKey ["+appKey+"], imageUrl ["+imageUrl+"], priority ["+priority+"], Operators ["+Operators+"], status ["+status+"]");
			System.out.println("["+GetDate()+"]"+" offerName is "+offerName);
			
			if(offerName == null || offerName.equals(""))
			{
				flag = -1;
				GetOperators(OperatorList);
                                GetOfferType(OfferList);

			}
			if(flag == 0)
			{
				flag = -1;
				size = GetResultData(offerName,actionUrl,offerType,description,offerAmount,appKey,imageUrl,priority,Operators,status,size_data,networkType,alertText,balanceType,category,balanceCreditInDays,isClick,isDownload,isShare,isShop,root,venderName);
				//GetOperators(OperatorList);
				//GetOfferType(OfferList);
			}
			Session.setAttribute("OperatorList", OperatorList);
			Session.setAttribute("OfferList", OfferList);
			if(size == 0 )
			{
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E021&redirect=offer_config.jsp"));	
			}
			else
				response.sendRedirect(response.encodeURL("jsp/offer_config.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In AddNewOffer.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016&redirect=offer_config.jsp"));
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
                        System.out.println("["+GetDate()+"]"+"In AddNewOffer.java:   Not able to connect with ORACLE");
                        od.printStackTrace();
                }
                System.out.println("["+GetDate()+"]"+"In AddNewOffer.java:    ORA Connection Created!");
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
                        System.out.println("["+GetDate()+"]"+"In AddNewOffer.java:    SQL Error" + ESQL.getMessage());
                }
                finally
                {
                        try
                        {
                                activeConnection.close();
			}
                        catch(Exception ee)
                        {
                                System.out.println("["+GetDate()+"]"+"In AddNewOffer.java:    Exception: " +ee.getMessage());
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
                        System.out.println("["+GetDate()+"]"+"In AddNewOffer.java:   Not able to connect with ORACLE");
                        od.printStackTrace();
                }
                System.out.println("["+GetDate()+"]"+"In AddNewOffer.java:    ORA Connection Created!");
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
                        System.out.println("["+GetDate()+"]"+"In AddNewOffer.java:    SQL Error" + ESQL.getMessage());
                }
                finally
                {
                        try
                        {
                                activeConnection.close();
                        }
                        catch(Exception ee)
                        {
                                System.out.println("["+GetDate()+"]"+"In AddNewOffer.java:    Exception: " +ee.getMessage());
                                ee.printStackTrace();
                        }
                }

		
	}
	public int GetResultData(String offerName,String actionUrl,String offerType,String description,String offerAmount,String appKey,String imageUrl,String priority,String Operators,String status,String size_data, String networkType, String alertText, String balanceType,String category,String balanceCreditInDays,String isClick,String isDownload,String isShare,String isShop,String root,String venderName)
	{
		String SelectQuery 	= "";
		PreparedStatement  selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;

		SelectQuery="insert into OfferDetails(actionUrl,appKey,createdTime,description,imageUrl,offerAmount,offerName,offerType,priority,status,updatedTime,operator,size,networkType,alertText,balanceType,balanceCreditInDays,isClick,isDownload,isShare,isShop,root,vendor) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In AddNewOffer.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In AddNewOffer.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.prepareStatement(SelectQuery);
			selectStmt.setString(1,actionUrl);
			selectStmt.setString(2,appKey);
			selectStmt.setString(3,GetDate());
			selectStmt.setString(4,description);
			selectStmt.setString(5,imageUrl);
			selectStmt.setString(6,offerAmount);
			selectStmt.setString(7,offerName);
			selectStmt.setString(8,offerType);
			selectStmt.setString(9,priority);
			selectStmt.setString(10,status);
			selectStmt.setString(11,GetDate());
			selectStmt.setString(12,Operators);
			selectStmt.setString(13,size_data);
			selectStmt.setString(14,networkType);
                        selectStmt.setString(15,alertText);
			selectStmt.setString(16,balanceType);
			//selectStmt.setString(17,category);
			selectStmt.setString(17,balanceCreditInDays);
			selectStmt.setString(18,isClick);
			selectStmt.setString(19,isDownload);
			selectStmt.setString(20,isShare);
			selectStmt.setString(21,isShop);
			selectStmt.setString(22,root);
			selectStmt.setString(23,venderName);

			System.out.println("insert into OfferDetails(actionUrl,appKey,createdTime,description,imageUrl,offerAmount,offerName,offerType,priority,status,updatedTime,operator,size,networkType,alertText,balanceType,balanceCreditInDays,isClick,isDownload,isShare,isShop,root,vendor) values ('"+actionUrl+"','"+appKey+"','"+GetDate()+"','"+description+"','"+imageUrl+"','"+offerAmount+"','"+offerName+"','"+offerType+"','"+priority+"','"+status+"','"+GetDate()+"','"+Operators+"','"+size_data+"','"+networkType+"','"+alertText+"','"+balanceType+"','"+balanceCreditInDays+"','"+isClick+"','"+isDownload+"','"+isShare+"','"+isShop+"','"+root+"','"+venderName+"')";	
			int numRowsChanged = selectStmt.executeUpdate();
			size = 0;	
			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			size = 0;	
			System.out.println("["+GetDate()+"]"+"In AddNewOffer.java:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In AddNewOffer.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}

}

