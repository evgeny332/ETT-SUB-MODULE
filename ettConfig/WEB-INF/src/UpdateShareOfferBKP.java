import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class UpdateOfferNew extends HttpServlet
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
                String root= null;
		String pendingAmountCredit= null;
                String pendingRecCount= null;
                String pendingRecDay= null;
                String callbackNotification= null;
                String installedNotification= null;
		String vendor=null;
		String gender            = null;
                String operatorCircle   = null;
                String ageLimit          = null;
                String playStoreDetails  = null;
                String rating            = null;
                String instructions      = null;
                String appDescription    = null;
                String payOutOn            = null;
                String payoutType          = null;
		 String maxCreditLimit           = null;
                String maxCreditPerDayLimit     = null;
                String amountPerDataThreshold   = null;
                String dataThreshold            = null;
                String dataUsageEligibleDays    = null;
                String timeIntervalOfRecheck    = null;
		String isWifiDataAccepted       = null;
                String dataOfferType            = null;
                String version                  = null;
                String detailedInstructions     = null;

		int flag 			= 0;
		int size			= -1;
		ArrayList OperatorList = new ArrayList ();
		ArrayList OfferList = new ArrayList();

		try
		{
			application_username= (String)Session.getAttribute("userName");
			offer_id		= request.getParameter("offer_id").trim();
			offerName		= request.getParameter("offerName").trim();
			actionUrl		= request.getParameter("actionUrl").trim();
			offerType		= request.getParameter("offerType").trim();
			description		= request.getParameter("description").trim();
			offerAmount		= request.getParameter("offerAmount").trim();
			appKey			= request.getParameter("appKey").trim();
			imageUrl		= request.getParameter("imageUrl").trim();
			priority		= request.getParameter("priority").trim();
			Operators		= request.getParameter("Operators").trim();
			status			= request.getParameter("status").trim();
			size_data		= request.getParameter("size");
			networkType		= request.getParameter("networkType");
			alertText		= request.getParameter("alertText").trim();
			balanceType		= request.getParameter("balanceType");
                        balanceCreditInDays     = request.getParameter("balanceCreditInDays");
                        category     		= request.getParameter("category");
                        root   	  		= request.getParameter("root");
			vendor            	= request.getParameter("venderName");
			pendingAmountCredit    = request.getParameter("pendingAmountCredit");
                        pendingRecCount         = request.getParameter("pendingRecCount");
                        pendingRecDay           = request.getParameter("pendingRecDay");
                        callbackNotification    = request.getParameter("callbackNotification");
                        installedNotification   = request.getParameter("installedNotification");
			gender            	= request.getParameter("gender");
                        operatorCircle  	= request.getParameter("operatorCircle");
                        ageLimit          	= request.getParameter("ageLimit");
                        playStoreDetails  	= request.getParameter("playStoreDetails");
                        rating            	= request.getParameter("rating");
                        instructions      	= request.getParameter("instructions");
                        appDescription    	= request.getParameter("appDescription");
                        payOutOn            	= request.getParameter("payOutOn");
                        payoutType          	= request.getParameter("payoutType");
			String userDeferedInfo	= request.getParameter("userDeferedInfo");
			String packageName	= request.getParameter("packageName");
			String offerPaymentType = request.getParameter("offerPaymentType");

			 maxCreditLimit           =request.getParameter("maxCreditLimit");
                        maxCreditPerDayLimit     =request.getParameter("maxCreditPerDayLimit");
                        amountPerDataThreshold   =request.getParameter("amountPerDataThreshold");
                        dataThreshold            =request.getParameter("dataThreshold");
                        dataUsageEligibleDays    =request.getParameter("dataUsageEligibleDays");
                        timeIntervalOfRecheck    =request.getParameter("timeIntervalOfRecheck");
			isWifiDataAccepted   =request.getParameter("isWifiDataAccepted");
                        dataOfferType            =request.getParameter("dataOfferType");
                        version    =request.getParameter("version");
                        detailedInstructions    =request.getParameter("detailedInstructions");
			String winBackURL       =request.getParameter("winBackURL");
			System.out.println("["+GetDate()+"]"+" offerName ["+offerName+"], actionUrl ["+actionUrl+"], offerType ["+offerType+"], description ["+description+"], offerAmount ["+offerAmount+"], appKey ["+appKey+"], imageUrl ["+imageUrl+"], priority ["+priority+"], Operators ["+Operators+"], status ["+status+"]");
			System.out.println("["+GetDate()+"]"+" offerName is "+offerName);
			if(version == null || version.equals(""))
                        {
                                version = "All";
                        }
			if(winBackURL == null || winBackURL.equals(""))
                        {
                                winBackURL = "";
                        }

			if(dataOfferType == null || dataOfferType.equals(""))
                        {
                                dataOfferType ="0";
                        }

			if(isWifiDataAccepted == null || isWifiDataAccepted.equals(""))
                        {
                                isWifiDataAccepted ="0";
                        }


	
			if(offerName == null || offerName.equals(""))
			{
				flag = -1;
			}
			if(flag == 0)
			{
				size = GetResultData(offer_id,offerName,actionUrl,offerType,description,offerAmount,appKey,imageUrl,priority,Operators,status,size_data,networkType,alertText,balanceType,category,balanceCreditInDays,root,pendingAmountCredit,pendingRecCount,pendingRecDay,callbackNotification,installedNotification,vendor,gender,operatorCircle,ageLimit,playStoreDetails,rating,instructions,appDescription,payOutOn,payoutType,userDeferedInfo,packageName,offerPaymentType,maxCreditLimit,maxCreditPerDayLimit,amountPerDataThreshold,dataThreshold,dataUsageEligibleDays,timeIntervalOfRecheck,isWifiDataAccepted,dataOfferType,version,detailedInstructions,winBackURL,application_username);
			}
			if(size == 0 )
			{
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E021&redirect=offer_redirectNew.jsp"));	
			}
			else
				response.sendRedirect(response.encodeURL("jsp/offer_redirectNew.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In UpdateOffer.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016&redirect=offer_configNew.jsp"));
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
	public int GetResultData(String fid,String offerName,String actionUrl,String offerType,String description,String offerAmount,String appKey,String imageUrl,String priority,String Operators,String status,String size_data, String networkType, String alertText, String balanceType,String category,String balanceCreditInDays,String root,String pendingAmountCredit,String pendingRecCount,String pendingRecDay,String callbackNotification,String installedNotification,String vendor,String gender,String operatorCircle,String ageLimit,String playStoreDetails,String rating,String instructions,String appDescription,String payOutOn,String payoutType,String userDeferedInfo,String packageName,String offerPaymentType,String maxCreditLimit,String maxCreditPerDayLimit,String amountPerDataThreshold,String dataThreshold,String dataUsageEligibleDays,String timeIntervalOfRecheck,String isWifiDataAccepted,String dataOfferType,String version,String detailedInstructions,String winBackURL,String application_username)
	{
		String SelectQuery 		= "";
		PreparedStatement  selectStmt   = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;

		SelectQuery="update ett.OfferDetails set actionUrl=?,appKey=?,description=?,imageUrl=?,offerAmount=?,offerName=?,offerType=?,priority=?,status=?,updatedTime=?,operator=?,size=? ,networkType=?, alertText=?, balanceType=?,balanceCreditInDays=?,offerCategory=?,pendingAmountCredit=?,pendingRecCount=?,pendingRecDay=?,callbackNotification=?,installedNotification=?,vendor=? ,gender=?,operatorCircle=?,ageLimit=?,playStoreDetails=?,rating=?,instructions=?,appDescription=?,payOutOn=?,payoutType=?,userDeferedInfo=?,packageName=?,offerPaymentType=?,maxCreditLimit=?,maxCreditPerDayLimit=?,amountPerDataThreshold=?,dataThreshold=?,dataUsageEligibleDays=?,timeIntervalOfRecheck=?, isWifiDataAccepted=?,dataOfferType=?,version=?,detailedInstructions=?,winbackUrl=? where offerId='"+fid+"'";
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
		System.out.println("["+GetDate()+"]"+"In UpdateOffer.java:    ORA Connection Created! and Execute Query User ["+application_username+"] Query ["+SelectQuery+"]");
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
			selectStmt.setString(16,balanceCreditInDays);
			selectStmt.setString(17,root);
			selectStmt.setString(18,pendingAmountCredit);
			selectStmt.setString(19,pendingRecCount);
			selectStmt.setString(20,pendingRecDay);
			selectStmt.setString(21,callbackNotification);
			selectStmt.setString(22,installedNotification);
			selectStmt.setString(23,vendor);
			selectStmt.setString(24,gender);
                        selectStmt.setString(25,operatorCircle);
                        selectStmt.setString(26,ageLimit);
                        selectStmt.setString(27,playStoreDetails);
                        selectStmt.setFloat(28,Float.parseFloat(rating));
                        selectStmt.setString(29,instructions);
                        selectStmt.setString(30,appDescription);
                        selectStmt.setString(31,payOutOn);
                        selectStmt.setString(32,payoutType);
                        selectStmt.setString(33,userDeferedInfo);
			selectStmt.setString(34,packageName);
			selectStmt.setString(35,offerPaymentType);
							
			selectStmt.setString(36,maxCreditLimit);
			selectStmt.setString(37,maxCreditPerDayLimit);

			selectStmt.setString(38,amountPerDataThreshold);

			//selectStmt.setFloat(36,Float.parseFloat(maxCreditLimit));
                        //selectStmt.setFloat(37,Float.parseFloat(maxCreditPerDayLimit));
                        //selectStmt.setFloat(38,Float.parseFloat(amountPerDataThreshold));
                        selectStmt.setString(39,dataThreshold);
                        selectStmt.setString(40,dataUsageEligibleDays);
                        selectStmt.setString(41,timeIntervalOfRecheck);
			selectStmt.setString(42,isWifiDataAccepted);
                        selectStmt.setString(43,dataOfferType);
                        selectStmt.setString(44,version);
                        selectStmt.setString(45,detailedInstructions);
			selectStmt.setString(46,winBackURL);
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

