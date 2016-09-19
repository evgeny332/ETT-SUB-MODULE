import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class AddNewOfferNew extends HttpServlet
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
		String offerName   		= null;
        	String actionUrl   		= null;
	       	String offerType   		= null;
        	String description 		= null;
        	String offerAmount 		= null;
        	String appKey      		= null;
        	String imageUrl    		= null;
        	String priority    		= null;
        	String Operators   		= null;
        	String status      		= null;
		String size_data   		= null;
		String networkType 		= null;
                String alertText   		= null;
		String balanceType 		= null;
		String category			= null; 
		String balanceCreditInDays 	= null;
		String root			= null;
		String pendingAmountCredit	= null;
		String pendingRecCount		= null;
		String pendingRecDay		= null;
		String callbackNotification	= null;
		String installedNotification	= null;
		String redirectText		=null;
		String venderName		=null;
		String gender            	= null;   
		String operatorCircle		= null;    
		String offerCat          	= null;   
		String ageLimit          	= null;
		String playStoreDetails  	= null;
		String rating            	= null;
		String instructions      	= null;
		String appDescription    	= null;
		String payOutOn            	= null; 
		String payoutType          	= null;
		String userDeferedInfo		=null;
		String packageName		= null;
		String offerPaymentType		= null;
		String maxCreditLimit		= null;
	 	String maxCreditPerDayLimit	= null;
	 	String amountPerDataThreshold	= null;
	 	String dataThreshold		= null;
	 	String dataUsageEligibleDays	= null;
	 	String timeIntervalOfRecheck	= null;
		String isWifiDataAccepted	= null;
		String dataOfferType		= null;
		String version			= null;
		String detailedInstructions	= null;
		String winBackURL		= "";
		String reason			= "";
		String deepLinkUrl		= "";
		String isRepeatOffer		= "";
		String city			= "";
		String pincode			= "";
		String state			= "";
		String payOutHoldinMin		= "";
		String payOutHoldNotification	= null;
		String requisitePackages	= "";
		String deviceBasedOffer		= "";
 
		int flag 			= 0;
		int size			= -1;
		ArrayList OperatorList = new ArrayList ();
		ArrayList OfferList = new ArrayList();

		try
		{
			offerName		= request.getParameter("offerName");
			actionUrl		= request.getParameter("actionUrl");
			offerType		= request.getParameter("offerType");
			description		= request.getParameter("description");
			offerAmount		= request.getParameter("offerAmount");
			appKey			= request.getParameter("appKey");
			imageUrl		= request.getParameter("imageUrl");
			priority		= request.getParameter("priority");
			Operators		= request.getParameter("Operators");
			status			= request.getParameter("status");
			size_data		= request.getParameter("size");
			networkType     	= request.getParameter("networkType");
                        alertText       	= request.getParameter("alertText");
			balanceType     	= request.getParameter("balanceType");
			balanceCreditInDays     = request.getParameter("balanceCreditInDays");
			category     		= request.getParameter("category");
			root     		= request.getParameter("root");
			pendingAmountCredit	= request.getParameter("pendingAmountCredit");
			pendingRecCount		= request.getParameter("pendingRecCount");
			pendingRecDay		= request.getParameter("pendingRecDay");
			callbackNotification	= request.getParameter("callbackNotification");
			installedNotification	= request.getParameter("installedNotification");
			venderName		= request.getParameter("venderName");
			gender            	= request.getParameter("gender");  
			operatorCircle		= request.getParameter("operatorCircle");   
			ageLimit          	= request.getParameter("ageLimit");
			playStoreDetails  	= request.getParameter("playStoreDetails");
			rating            	= request.getParameter("rating");
			instructions      	= request.getParameter("instructions");
			appDescription    	= request.getParameter("appDescription");
			payOutOn            	= request.getParameter("payOutOn");
			payoutType          	= request.getParameter("payoutType");
			userDeferedInfo    	= request.getParameter("userDeferedInfo");
			packageName         	= request.getParameter("packageName");
			offerPaymentType        = request.getParameter("offerPaymentType");
			maxCreditLimit           =request.getParameter("maxCreditLimit");
			maxCreditPerDayLimit     =request.getParameter("maxCreditPerDayLimit");
			amountPerDataThreshold   =request.getParameter("amountPerDataThreshold");
			dataThreshold            =request.getParameter("dataThreshold");
			dataUsageEligibleDays    =request.getParameter("dataUsageEligibleDays");
			timeIntervalOfRecheck    =request.getParameter("timeIntervalOfRecheck");
			isWifiDataAccepted   	=request.getParameter("isWifiDataAccepted");
                        dataOfferType           =request.getParameter("dataOfferType");
			winBackURL		=request.getParameter("winBackURL");
                        version    		=request.getParameter("version");
                        detailedInstructions    =request.getParameter("detailedInstructions");
			String offerAmountShow  =request.getParameter("offerAmountShow");
			String Source           ="";
			String multipleEventOffer="";
			try
			{
				multipleEventOffer=request.getParameter("multipleEventOffer");
				deepLinkUrl     =request.getParameter("deepLinkUrl");
				isRepeatOffer   =request.getParameter("isRepeatOffer");
				city		=request.getParameter("city");
				pincode		=request.getParameter("pincode");
				state		=request.getParameter("state");
				Source		=request.getParameter("Source");
				reason		=request.getParameter("reason");
				payOutHoldinMin =request.getParameter("payOutHoldinMin");
                                payOutHoldNotification =request.getParameter("payOutHoldNotification");
				requisitePackages =request.getParameter("requisitePackages");
				deviceBasedOffer =request.getParameter("deviceBasedOffer"); 
			}
			catch(Exception ex)
			{
			}
			if(requisitePackages ==null || requisitePackages.equals(""))
			{
				requisitePackages="";
			}
			if(deviceBasedOffer == null || deviceBasedOffer.equals(""))
			{
				deviceBasedOffer="0";
			}
			if(payOutHoldinMin ==null ||payOutHoldinMin.equals(""))
			{
				payOutHoldinMin="0";
			}
			if(payOutHoldNotification ==null || payOutHoldNotification.equals(""))
			{ payOutHoldNotification =""; 
			}
			if(multipleEventOffer == null || multipleEventOffer.equals(""))
                        {
                                multipleEventOffer="0";
                        }
			if(offerAmountShow == null || offerAmountShow.equals(""))
			{
				offerAmountShow="0";
			}
			if(deepLinkUrl == null || deepLinkUrl.equals(""))
                        {
                                deepLinkUrl = "";
                        }
			if(isRepeatOffer ==null || isRepeatOffer.equals(""))
			{
				isRepeatOffer="0";
			}
			if(city == null || city.equals(""))
			{
				city="";
			}
			if(pincode == null || pincode.equals(""))
			{
				pincode="";

			}
			if(state == null || state.equals(""))
			{
				state="";
			}
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

			if(rating == null || rating.equals(""))
                        {
				rating="0";
			}
			if(maxCreditPerDayLimit==null || maxCreditPerDayLimit.equals(""))	
			{
				maxCreditPerDayLimit="0";
			}
			if(amountPerDataThreshold == null || amountPerDataThreshold.equals(""))
			{
				amountPerDataThreshold="0";
			}
			if(dataThreshold == null || dataThreshold.equals(""))
			{
				dataThreshold="0";
			}		
			if(dataUsageEligibleDays==null ||dataUsageEligibleDays.equals(""))
			{
				dataUsageEligibleDays="0";
			}
			if(timeIntervalOfRecheck == null || timeIntervalOfRecheck.equals(""))
			{
				timeIntervalOfRecheck="0";
			}
			if(pendingAmountCredit == null || pendingAmountCredit.equals(""))
			{
				pendingAmountCredit="0";
			}
			if(offerName == null || offerName.equals(""))
			{
				flag = -1;
				GetOperators(OperatorList);
                                GetOfferType(OfferList);

			}
			if(flag == 0)
			{
				flag = -1;
				size = GetResultData(offerName,actionUrl,offerType,description,offerAmount,appKey,imageUrl,priority,Operators,status,size_data,networkType,alertText,balanceType,category,balanceCreditInDays,root,pendingAmountCredit,pendingRecCount,pendingRecDay,callbackNotification,installedNotification,venderName,gender,operatorCircle,ageLimit,playStoreDetails,rating,instructions,appDescription,payOutOn,payoutType,userDeferedInfo,packageName,offerPaymentType,maxCreditLimit,maxCreditPerDayLimit,amountPerDataThreshold,dataThreshold,dataUsageEligibleDays,timeIntervalOfRecheck,isWifiDataAccepted,dataOfferType,version,detailedInstructions,winBackURL,application_username, reason,Source,deepLinkUrl,isRepeatOffer,city,pincode,state,offerAmountShow,multipleEventOffer,payOutHoldinMin,payOutHoldNotification,requisitePackages,deviceBasedOffer);
				//GetOperators(OperatorList);
				//GetOfferType(OfferList);
			}
			Session.setAttribute("OperatorList", OperatorList);
			Session.setAttribute("OfferList", OfferList);
			if(size == 0 )
			{
				if(multipleEventOffer.equals("1"))
				{
					response.sendRedirect(response.encodeURL("AddOfferEvent"));
				}
				else{
					response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E021&redirect=offer_configNew.jsp"));	
				}
			}
			else
				response.sendRedirect(response.encodeURL("jsp/offer_configNew.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In AddNewOfferNew.java:    SQLState:  " + Eb.getMessage() );
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
                        System.out.println("["+GetDate()+"]"+"In AddNewOfferNew.java:   Not able to connect with ORACLE");
                        od.printStackTrace();
                }
                System.out.println("["+GetDate()+"]"+"In AddNewOfferNew.java:    ORA Connection Created!");
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
                        System.out.println("["+GetDate()+"]"+"In AddNewOfferNew.java:    SQL Error" + ESQL.getMessage());
                }
                finally
                {
                        try
                        {
                                activeConnection.close();
			}
                        catch(Exception ee)
                        {
                                System.out.println("["+GetDate()+"]"+"In AddNewOfferNew.java:    Exception: " +ee.getMessage());
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
                        System.out.println("["+GetDate()+"]"+"In AddNewOfferNew.java:   Not able to connect with ORACLE");
                        od.printStackTrace();
                }
                System.out.println("["+GetDate()+"]"+"In AddNewOfferNew.java:    ORA Connection Created!");
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
                        System.out.println("["+GetDate()+"]"+"In AddNewOfferNew.java:    SQL Error" + ESQL.getMessage());
                }
                finally
                {
                        try
                        {
                                activeConnection.close();
                        }
                        catch(Exception ee)
                        {
                                System.out.println("["+GetDate()+"]"+"In AddNewOfferNew.java:    Exception: " +ee.getMessage());
                                ee.printStackTrace();
                        }
                }

		
	}
	public int GetResultData(String offerName,String actionUrl,String offerType,String description,String offerAmount,String appKey,String imageUrl,String priority,String Operators,String status,String size_data, String networkType, String alertText, String balanceType,String category,String balanceCreditInDays,String root,String pendingAmountCredit,String pendingRecCount,String pendingRecDay,String callbackNotification,String installedNotification,String venderName,String gender,String operatorCircle,String ageLimit,String playStoreDetails,String rating,String instructions,String appDescription,String payOutOn,String payoutType,String userDeferedInfo,String packageName,String offerPaymentType,String maxCreditLimit,String maxCreditPerDayLimit,String amountPerDataThreshold,String dataThreshold,String dataUsageEligibleDays,String timeIntervalOfRecheck,String isWifiDataAccepted,String dataOfferType,String version,String detailedInstructions,String winBackURL,String application_username,String reason,String Source,String deepLinkUrl,String isRepeatOffer,String city,String pincode,String state,String offerAmountShow,String multipleEventOffer,String payOutHoldinMin,String payOutHoldNotification,String requisitePackages,String deviceBasedOffer)
	{
		String SelectQuery 		= "";
		PreparedStatement  selectStmt   = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;

		SelectQuery="insert into OfferDetails(actionUrl,appKey,createdTime,description,imageUrl,offerAmount,offerName,offerType,priority,status,updatedTime,operator,size,networkType,alertText,balanceType,balanceCreditInDays,offerCategory,pendingAmountCredit,pendingRecCount,pendingRecDay,callbackNotification,installedNotification,vendor,gender,operatorCircle,ageLimit,playStoreDetails,rating,instructions,appDescription,payOutOn,payoutType,userDeferedInfo,packageName,offerPaymentType,maxCreditLimit,maxCreditPerDayLimit,amountPerDataThreshold,dataThreshold,dataUsageEligibleDays,timeIntervalOfRecheck,isWifiDataAccepted,dataOfferType,version,detailedInstructions,winbackUrl,deepLinkUrl,isRepeatOffer,city,pincode,state,offerAmountShow,multipleEventOffer,payOutHoldinMin,payOutHoldNotification,requisitePackages,deviceBasedOffer) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		//System.out.println("Query OfferDetails "+SelectQuery);
		String insertQ="insert into OfferDetails(actionUrl,appKey,createdTime,description,imageUrl,offerAmount,offerName,offerType,priority,status,updatedTime,operator,size,networkType,alertText,balanceType,balanceCreditInDays,offerCategory,pendingAmountCredit,pendingRecCount,pendingRecDay,callbackNotification,installedNotification,vendor,gender,operatorCircle,ageLimit,playStoreDetails,rating,instructions,appDescription,payOutOn,payoutType,userDeferedInfo,packageName,offerPaymentType,maxCreditLimit,maxCreditPerDayLimit,amountPerDataThreshold,dataThreshold,dataUsageEligibleDays,timeIntervalOfRecheck,isWifiDataAccepted,dataOfferType,version,detailedInstructions,winbackUrl,deepLinkUrl,isRepeatOffer,city,pincode,state,offerAmountShow,multipleEventOffer,payOutHoldinMin,payOutHoldNotification,requisitePackages,deviceBasedOffer) values ('"+actionUrl+"','"+appKey+"','"+GetDate()+"','"+description+"','"+imageUrl+"','"+offerAmount+"','"+offerName+"','"+offerType+"','"+priority+"','"+status+"','"+GetDate()+"','"+Operators+"','"+size_data+"','"+networkType+"','"+alertText+"','"+balanceType+"','"+balanceCreditInDays+"','"+root+"','"+pendingAmountCredit+"','"+pendingRecCount+"','"+pendingRecDay+"','"+callbackNotification+"','"+installedNotification+"','"+venderName+"','"+gender+"','"+operatorCircle+"','"+ageLimit+"','"+playStoreDetails+"',"+rating+",'"+instructions+"','"+appDescription+"','"+payOutOn+"','"+payoutType+"','"+userDeferedInfo+"','"+packageName+"','"+offerPaymentType+"',"+maxCreditLimit+","+maxCreditPerDayLimit+","+amountPerDataThreshold+",'"+dataThreshold+"','"+dataUsageEligibleDays+"','"+timeIntervalOfRecheck+"','"+isWifiDataAccepted+"','"+dataOfferType+"','"+version+"','"+detailedInstructions+"','"+winBackURL+"','"+deepLinkUrl+"',"+isRepeatOffer+",'"+city+"','"+pincode+"','"+state+"',"+offerAmountShow+","+multipleEventOffer+","+payOutHoldinMin+",'"+payOutHoldNotification+"','"+requisitePackages+"',"+deviceBasedOffer+")";
		System.out.println("Query OfferDetails "+insertQ);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In AddNewOfferNew.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		//System.out.println("["+GetDate()+"]"+" In AddNewOfferNew.java:    ORA Connection Created! and Execute Query User ["+application_username+"] Query ["+SelectQuery+"]");
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
			selectStmt.setString(17,balanceCreditInDays);
			selectStmt.setString(18,root);
			selectStmt.setString(19,pendingAmountCredit);
			selectStmt.setString(20,pendingRecCount);
			selectStmt.setString(21,pendingRecDay);
			selectStmt.setString(22,callbackNotification);
			selectStmt.setString(23,installedNotification);
			selectStmt.setString(24,venderName);
			selectStmt.setString(25,gender);
			selectStmt.setString(26,operatorCircle);
			selectStmt.setString(27,ageLimit);
			selectStmt.setString(28,playStoreDetails);
			selectStmt.setFloat(29,Float.parseFloat(rating));
			selectStmt.setString(30,instructions);
			selectStmt.setString(31,appDescription);
			selectStmt.setString(32,payOutOn);
			selectStmt.setString(33,payoutType);	
			selectStmt.setString(34,userDeferedInfo);
			selectStmt.setString(35,packageName);
			selectStmt.setString(36,offerPaymentType);
			selectStmt.setFloat(37,Float.parseFloat(maxCreditLimit));
			selectStmt.setFloat(38,Float.parseFloat(maxCreditPerDayLimit));
			selectStmt.setFloat(39,Float.parseFloat(amountPerDataThreshold));
			selectStmt.setString(40,dataThreshold);
		
			selectStmt.setString(41,dataUsageEligibleDays);
			selectStmt.setString(42,timeIntervalOfRecheck);
		

			selectStmt.setString(43,isWifiDataAccepted);
			selectStmt.setString(44,dataOfferType);
			selectStmt.setString(45,version);
			selectStmt.setString(46,detailedInstructions);
			selectStmt.setString(47,winBackURL);
			selectStmt.setString(48,deepLinkUrl);
			selectStmt.setString(49,isRepeatOffer);
			selectStmt.setString(50,city);
			selectStmt.setString(51,pincode);
			selectStmt.setString(52,state);
			selectStmt.setString(53,offerAmountShow);
			selectStmt.setString(54,multipleEventOffer);
			selectStmt.setString(55,payOutHoldinMin);
                        selectStmt.setString(56,payOutHoldNotification);
			selectStmt.setString(57,requisitePackages);
                        selectStmt.setString(58,deviceBasedOffer);
		//	String insertQ="insert into OfferDetails(actionUrl,appKey,createdTime,description,imageUrl,offerAmount,offerName,offerType,priority,status,updatedTime,operator,size,networkType,alertText,balanceType,balanceCreditInDays,offerCategory,pendingAmountCredit,pendingRecCount,pendingRecDay,callbackNotification,installedNotification,vendor,gender,operatorCircle,ageLimit,playStoreDetails,rating,instructions,appDescription,payOutOn,payoutType,userDeferedInfo,packageName,offerPaymentType,maxCreditLimit,maxCreditPerDayLimit,amountPerDataThreshold,dataThreshold,dataUsageEligibleDays,timeIntervalOfRecheck,isWifiDataAccepted,dataOfferType,version,detailedInstructions,winbackUrl,deepLinkUrl,isRepeatOffer,city,pincode,state,offerAmountShow,multipleEventOffer,payOutHoldinMin,payOutHoldNotification) values ('"+actionUrl+"','"+appKey+"','"+GetDate()+"','"+description+"','"+imageUrl+"','"+offerAmount+"','"+offerName+"','"+offerType+"','"+priority+"','"+status+"','"+GetDate()+"','"+Operators+"','"+size_data+"','"+networkType+"','"+alertText+"','"+balanceType+"','"+balanceCreditInDays+"','"+root+"','"+pendingAmountCredit+"','"+pendingRecCount+"','"+pendingRecDay+"','"+callbackNotification+"','"+installedNotification+"','"+venderName+"','"+gender+"','"+operatorCircle+"','"+ageLimit+"','"+playStoreDetails+"',"+rating+",'"+instructions+"','"+appDescription+"','"+payOutOn+"','"+payoutType+"','"+userDeferedInfo+"','"+packageName+"','"+offerPaymentType+"',"+maxCreditLimit+","+maxCreditPerDayLimit+","+amountPerDataThreshold+",'"+dataThreshold+"','"+dataUsageEligibleDays+"','"+timeIntervalOfRecheck+"','"+isWifiDataAccepted+"','"+dataOfferType+"','"+version+"','"+detailedInstructions+"','"+winBackURL+"','"+deepLinkUrl+"',"+isRepeatOffer+",'"+city+"','"+pincode+"','"+state+"',"+offerAmountShow+","+multipleEventOffer+","+payOutHoldinMin+",'"+payOutHoldNotification+"')";

			System.out.println("["+GetDate()+"] ETT_CONFIG_DETAILS || Source ["+Source+"] || UserName ["+application_username+"] || which reason ["+reason+"] || Execute Query [ "+insertQ.replaceAll("\n"," ")+" ]");
	
			int numRowsChanged = selectStmt.executeUpdate();
			size = 0;	
			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			size = 0;	
			System.out.println("["+GetDate()+"]"+"In AddNewOfferNew.java:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In AddNewOfferNew.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}

}

