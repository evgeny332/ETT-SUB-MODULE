import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class UpdateOffer extends HttpServlet
{
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
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
		String offer_id	   = null;
		String networkType = null;
		String alertText   = null;
		String balanceType = null;
		String category         = null;
                String balanceCreditInDays = null;
                String isClick          = null;
                String isDownload = null;
                String isShare= null;
                String isShop= null;
                String root= null;

		int flag 			= 0;
		int size			= -1;
		ArrayList OperatorList = new ArrayList ();
		ArrayList OfferList = new ArrayList();

		try
		{
			offer_id	= request.getParameter("offer_id");
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
			networkType	= request.getParameter("networkType");
			alertText	= request.getParameter("alertText");
			balanceType	= request.getParameter("balanceType");
			isClick     	= request.getParameter("isClick");
                        isDownload    	= request.getParameter("isDownload");
                        isShare     	= request.getParameter("isShare");
                        isShop     	= request.getParameter("isShop");
                        balanceCreditInDays     = request.getParameter("balanceCreditInDays");
                        category     	= request.getParameter("category");
                        root   	  	= request.getParameter("root");
			System.out.println("["+GetDate()+"]"+" offerName ["+offerName+"], actionUrl ["+actionUrl+"], offerType ["+offerType+"], description ["+description+"], offerAmount ["+offerAmount+"], appKey ["+appKey+"], imageUrl ["+imageUrl+"], priority ["+priority+"], Operators ["+Operators+"], status ["+status+"]");
			System.out.println("["+GetDate()+"]"+" offerName is "+offerName);
			
			if(offerName == null || offerName.equals(""))
			{
				flag = -1;
			}
			if(flag == 0)
			{
				size = GetResultData(offer_id,offerName,actionUrl,offerType,description,offerAmount,appKey,imageUrl,priority,Operators,status,size_data,networkType,alertText,balanceType,category,balanceCreditInDays,isClick,isDownload,isShare,isShop,root);
			}
			if(size == 0 )
			{
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E021&redirect=offer_redirect.jsp"));	
			}
			else
				response.sendRedirect(response.encodeURL("jsp/offer_redirectNew.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In UpdateOffer.java:    SQLState:  " + Eb.getMessage() );
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
	public int GetResultData(String fid,String offerName,String actionUrl,String offerType,String description,String offerAmount,String appKey,String imageUrl,String priority,String Operators,String status,String size_data, String networkType, String alertText, String balanceType,String category,String balanceCreditInDays,String isClick,String isDownload,String isShare,String isShop,String root)
	{
		String SelectQuery 		= "";
		PreparedStatement  selectStmt   = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;

		//SelectQuery="insert into OfferDetails(actionUrl,appKey,createdTime,description,imageUrl,offerAmount,offerName,offerType,priority,status,updatedTime,operator,size) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		SelectQuery="update OfferDetails set actionUrl=?,appKey=?,description=?,imageUrl=?,offerAmount=?,offerName=?,offerType=?,priority=?,status=?,updatedTime=?,operator=?,size=? ,networkType=?, alertText=?, balanceType=?,balanceCreditInDays=?,isClick=?,isDownload=?,isShare=?,isShop=?,root=?  where offerId='"+fid+"'";
		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In UpdateOffer.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In UpdateOffer.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.prepareStatement(SelectQuery);
			selectStmt.setString(1,actionUrl);
			selectStmt.setString(2,appKey);
			selectStmt.setString(3,description);
			selectStmt.setString(4,imageUrl);
			selectStmt.setString(5,offerAmount);
			selectStmt.setString(6,offerName);
			selectStmt.setString(7,offerType);
			selectStmt.setString(8,priority);
			selectStmt.setString(9,status);
			selectStmt.setString(10,GetDate());
			selectStmt.setString(11,Operators);
			selectStmt.setString(12,size_data);
			selectStmt.setString(13,networkType);
                        selectStmt.setString(14,alertText);
			selectStmt.setString(15,balanceType);
			//selectStmt.setString(16,category);
			selectStmt.setString(16,balanceCreditInDays);
			selectStmt.setString(17,isClick);
			selectStmt.setString(18,isDownload);
			selectStmt.setString(19,isShare);
			selectStmt.setString(20,isShop);
			selectStmt.setString(21,root);
			
			int numRowsChanged = selectStmt.executeUpdate();
			size = 0;	
			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			size = 0;	
			System.out.println("["+GetDate()+"]"+"In UpdateOffer.java:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In UpdateOffer.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}

}

