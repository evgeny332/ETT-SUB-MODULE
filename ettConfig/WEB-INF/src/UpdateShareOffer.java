import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class UpdateShareOffer extends HttpServlet
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
		String offerId     		= null;        
		String offerName   		= null;        
		String offerInfo   		= null;        
		String description 		= null;        
		String offerAmount 		= null;        
		String offerType   		= null;        
		String payouttype  		= null;        
		String osType      		= null;        
		String priority    		= null;        
		String status      		= null;        
		String alertText   		= null;        
		String shareText   		= null;        
		String shareMessage		= null;        
		String callBackNotification	= null;
		String imageURL    		= null;        
		String actionURLAndroid 	= null;   
		String vendorNameAndroid 	= null;  
		String actionURLiOS 		= null;       
		String vendorNameiOS 		= null;      
		String shareURL 		= null;        
		String balanceType 		= null;       
		String offer_id			= ""; 
		String iOSAmount		= ""; 
		int flag 			= 0;
		int size			= -1;

		try
		{
			offer_id                = request.getParameter("offer_id").trim();
			offerName     		= request.getParameter("offerName");      
 			offerInfo     		= request.getParameter("offerInfo");      
	 		description   		= request.getParameter("description");      
 			offerAmount  		= request.getParameter("offerAmount");       
 			offerType    		= request.getParameter("offerType");       
 			payouttype   		= request.getParameter("payouttype");       
 			osType       		= request.getParameter("osType");       
 			priority     		= request.getParameter("priority");       
 			status       		= request.getParameter("status");           
 			alertText    		= request.getParameter("alertText");       
 			shareText    		= request.getParameter("shareText");        
 			shareMessage 		= request.getParameter("shareMessage");       
 			callBackNotification 	= request.getParameter("callBackNotification");
 			imageURL          	= request.getParameter("imageURL");  
 			actionURLAndroid   	= request.getParameter("actionURLAndroid"); 
 			vendorNameAndroid  	= request.getParameter("vendorNameAndroid");  
 			actionURLiOS       	= request.getParameter("actionURLiOS"); 
 			vendorNameiOS      	= request.getParameter("vendorNameiOS"); 
 			shareURL           	= request.getParameter("shareURL"); 
 			balanceType        	= request.getParameter("balanceType"); 
			iOSAmount		= request.getParameter("iOSAmount");
			if(iOSAmount == null || iOSAmount.equals(""))
			{
				iOSAmount="0";
			}
			if(offerAmount == null || offerAmount.equals(""))
			{
				offerAmount="0";
			}
			if(actionURLiOS == null || actionURLiOS.equals(""))
                        {
                                actionURLiOS="NA";
                        }
                        if(vendorNameiOS == null || vendorNameiOS.equals(""))
                        {
                                vendorNameiOS="NA";
                        }
                        if(actionURLAndroid == null || actionURLAndroid.equals(""))
                        {
                                actionURLAndroid="NA";
                        }
                        if(vendorNameAndroid == null || vendorNameAndroid.equals(""))
                        {
                                vendorNameAndroid="NA";
                        }

			if(offerName == null || offerName.equals(""))
			{
				flag = -1;

			}
			if(flag == 0)
			{
				flag = -1;
				size = GetResultData(offer_id,offerName,offerInfo,description,offerAmount,offerType,payouttype,osType,priority,status,alertText,shareText,shareMessage,callBackNotification,imageURL,actionURLAndroid,vendorNameAndroid,actionURLiOS,vendorNameiOS,shareURL,balanceType,iOSAmount,application_username);
			}
			if(size == 0 )
			{
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E021&redirect=share_redirect.jsp"));	
			}
			else
				response.sendRedirect(response.encodeURL("jsp/share_redirect.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In UpdateShareOffer.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016&redirect=shareOfferConfig.jsp"));
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
	public int GetResultData(String offer_id,String offerName,String offerInfo,String description,String offerAmount,String offerType,String payouttype,String osType,String priority,String status,String alertText,String shareText,String shareMessage,String callBackNotification,String imageURL,String actionURLAndroid,String vendorNameAndroid,String actionURLiOS,String vendorNameiOS,String shareURL,String balanceType,String iOSAmount,String application_username)
	{
		String SelectQuery 		= "";
		PreparedStatement  selectStmt   = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;

		SelectQuery="update ShareOfferDetails set offerName=?,offerInfo=?,description=?,offerAmount=?,offerType=?,payouttype=?,osType=?,priority=?,status=?,alertText=?,shareText=?,shareMessage=?,callBackNotification=?,imageURL=?,actionURLAndroid=?,vendorNameAndroid=?,actionURLiOS=?,vendorNameiOS=?,shareURL=?,balanceType=?,updatedTime=?,iOSAmount=? where offerId="+offer_id+"";
		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In UpdateShareOffer.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In UpdateShareOffer.java:    ORA Connection Created! and Execute Query User ["+application_username+"] Query ["+SelectQuery+"]");
		try
		{
			selectStmt = activeConnection.prepareStatement(SelectQuery);
			selectStmt.setString(1,offerName);
			selectStmt.setString(2,offerInfo);
			selectStmt.setString(3,description);
			selectStmt.setString(4,offerAmount);
			selectStmt.setString(5,offerType);
			selectStmt.setString(6,payouttype);
			selectStmt.setString(7,osType);
			selectStmt.setString(8,priority);
			selectStmt.setString(9,status);
			selectStmt.setString(10,alertText);
			selectStmt.setString(11,shareText);
			selectStmt.setString(12,shareMessage);
			selectStmt.setString(13,callBackNotification);
			selectStmt.setString(14,imageURL);
                        selectStmt.setString(15,actionURLAndroid);
			selectStmt.setString(16,vendorNameAndroid);
			selectStmt.setString(17,actionURLiOS);
			selectStmt.setString(18,vendorNameiOS);
			selectStmt.setString(19,shareURL);
			selectStmt.setString(20,balanceType);
			selectStmt.setString(21,GetDate());
			selectStmt.setString(22,iOSAmount);

	
			int numRowsChanged = selectStmt.executeUpdate();
			String str="update ShareOfferDetails set offerName='"+offerName+"',offerInfo='"+offerInfo+"',description='"+description+"',offerAmount='"+offerAmount+"',offerType='"+offerType+"',payouttype='"+payouttype+"',osType='"+osType+"',priority='"+priority+"',status='"+status+"',alertText='"+alertText+"',shareText='"+shareText+"',shareMessage='"+shareMessage+"',callBackNotification='"+callBackNotification+"',imageURL='"+imageURL+"',actionURLAndroid='"+actionURLAndroid+"',vendorNameAndroid='"+vendorNameAndroid+"',actionURLiOS='"+actionURLiOS+"',vendorNameiOS='"+vendorNameiOS+"',shareURL='"+shareURL+"',balanceType='"+balanceType+"',updatedTime='"+GetDate()+"',iOSAmount='"+iOSAmount+"' where offerId=10001";
			System.out.println("["+GetDate()+"]"+"In UpdateShareOffer.java:    ORA Connection Created! and Execute Query User ["+application_username+"] Query ["+str+"]");
			size = 0;	
			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			size = 0;	
			System.out.println("["+GetDate()+"]"+"In UpdateShareOffer.java:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In UpdateShareOffer.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}

}

