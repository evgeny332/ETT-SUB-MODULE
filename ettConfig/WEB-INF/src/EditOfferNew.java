import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class EditOfferNew extends HttpServlet
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
		String fid		= "";	
		try
		{
			fid=request.getParameter("offer_id");
			System.out.println("offer_id for edit new  offer : "+fid);
			GetResultData(FileStatusList ,fid);
			Session.setAttribute("FileStatusList", FileStatusList);
			 if(FileStatusList.size()==0)
                        {
                                response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=D009&redirect=offer_redirectNew.jsp"));
                        }

                        else{
                                response.sendRedirect(response.encodeURL("jsp/offer_editNew.jsp"));
                        }

		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In EditOffer.java:    SQLState:  " + Eb.getMessage() );
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
	public void GetResultData(ArrayList FileStatusList, String fid)
	{
		String SelectQuery 	= "";
		Statement selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		//SelectQuery="select offerId,offerName,offerType,description,offerAmount,appKey,actionUrl,imageUrl,priority,operator,status,size,networkType,alertText,balanceType,balanceCreditInDays,category,offerCategory,pendingAmountCredit,pendingRecCount,pendingRecDay,callbackNotification,installedNotification,vendor,gender,operatorCircle,ageLimit,playStoreDetails,rating,instructions,appDescription,payOutOn,payoutType,userDeferedInfo from OfferDetails where offerId='"+fid+"'";
		//SelectQuery="select ifnull(offerId,''),ifnull(offerName,''),ifnull(offerType,''),ifnull(description,''),ifnull(offerAmount,''),ifnull(appKey,''),ifnull(actionUrl,''),ifnull(imageUrl,''),ifnull(priority,''),ifnull(operator,''),ifnull(status,''),ifnull(size,''),ifnull(networkType,''),ifnull(alertText,''),ifnull(balanceType,''),ifnull(balanceCreditInDays,''),ifnull(category,''),ifnull(offerCategory,''),ifnull(pendingAmountCredit,''),ifnull(pendingRecCount,''),ifnull(pendingRecDay,''),ifnull(callbackNotification,''),ifnull(installedNotification,''),ifnull(vendor,''),ifnull(gender,''),ifnull(operatorCircle,''),ifnull(ageLimit,''),ifnull(playStoreDetails,''),ifnull(rating,''),ifnull(instructions,''),ifnull(appDescription,''),ifnull(payOutOn,''),ifnull(payoutType,''),ifnull(userDeferedInfo,''),ifnull(packageName,''),ifnull(offerPaymentType,'',maxCreditLimit,maxCreditPerDayLimit,amountPerDataThreshold,dataThreshold,dataUsageEligibleDays,timeIntervalOfRecheck) from OfferDetails where offerId='"+fid+"'";
		SelectQuery="select ifnull(offerId,''),ifnull(offerName,''),ifnull(offerType,''),ifnull(description,''),ifnull(offerAmount,''),ifnull(appKey,''),ifnull(actionUrl,''),ifnull(imageUrl,''),ifnull(priority,''),ifnull(operator,''),ifnull(status,''),ifnull(size,''),ifnull(networkType,''),ifnull(alertText,''),ifnull(balanceType,''),ifnull(balanceCreditInDays,''),ifnull(category,''),ifnull(offerCategory,''),ifnull(pendingAmountCredit,''),ifnull(pendingRecCount,''),ifnull(pendingRecDay,''),ifnull(callbackNotification,''),ifnull(installedNotification,''),ifnull(vendor,''),ifnull(gender,''),ifnull(operatorCircle,''),ifnull(ageLimit,''),ifnull(playStoreDetails,''),ifnull(rating,''),ifnull(instructions,''),ifnull(appDescription,''),ifnull(payOutOn,''),ifnull(payoutType,''),ifnull(userDeferedInfo,''),ifnull(packageName,''),ifnull(offerPaymentType,''),ifnull(maxCreditLimit,''),ifnull(maxCreditPerDayLimit,''),ifnull(amountPerDataThreshold,''),ifnull(dataThreshold,''),ifnull(dataUsageEligibleDays,''),ifnull(timeIntervalOfRecheck,'') ,ifnull(isWifiDataAccepted,''),ifnull(dataOfferType,''),ifnull(version,''),ifnull(detailedInstructions,''),ifnull(winbackUrl,''),ifnull(deepLinkUrl,''),ifnull(isRepeatOffer,''),ifnull(city,''),ifnull(pincode,''),ifnull(state,''),ifnull(offerAmountShow,''),ifnull(multipleEventOffer,''),ifnull(payOutHoldinMin,''),ifnull(payOutHoldNotification,''), ifnull(requisitePackages,''), ifnull(deviceBasedOffer,'')  from OfferDetails where offerId='"+fid+"'";
		System.out.println("in java file SK : "+SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In EditOffer.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In EditOffer.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			while(DetailsResultSet.next())
			{
				FileStatusList.add(DetailsResultSet.getString(1));
				FileStatusList.add(DetailsResultSet.getString(2));
				FileStatusList.add(DetailsResultSet.getString(3));
				FileStatusList.add(DetailsResultSet.getString(4));
				FileStatusList.add(DetailsResultSet.getString(5));
				FileStatusList.add(DetailsResultSet.getString(6));
				FileStatusList.add(DetailsResultSet.getString(7));
				FileStatusList.add(DetailsResultSet.getString(8));
				FileStatusList.add(DetailsResultSet.getString(9));
				FileStatusList.add(DetailsResultSet.getString(10));
				FileStatusList.add(DetailsResultSet.getString(11));
				FileStatusList.add(DetailsResultSet.getString(12));
				FileStatusList.add(DetailsResultSet.getString(13));
				FileStatusList.add(DetailsResultSet.getString(14));
				FileStatusList.add(DetailsResultSet.getString(15));
                                FileStatusList.add(DetailsResultSet.getString(16));
				FileStatusList.add(DetailsResultSet.getString(17));
				FileStatusList.add(DetailsResultSet.getString(18));
				FileStatusList.add(DetailsResultSet.getString(19));
				FileStatusList.add(DetailsResultSet.getString(20));
				FileStatusList.add(DetailsResultSet.getString(21));
				FileStatusList.add(DetailsResultSet.getString(22));
				FileStatusList.add(DetailsResultSet.getString(23));
				FileStatusList.add(DetailsResultSet.getString(24));
				FileStatusList.add(DetailsResultSet.getString(25));
				FileStatusList.add(DetailsResultSet.getString(26));
				FileStatusList.add(DetailsResultSet.getString(27));
				FileStatusList.add(DetailsResultSet.getString(28));
				FileStatusList.add(DetailsResultSet.getString(29));
				FileStatusList.add(DetailsResultSet.getString(30));
				FileStatusList.add(DetailsResultSet.getString(31));
				FileStatusList.add(DetailsResultSet.getString(32));
				FileStatusList.add(DetailsResultSet.getString(33));
				FileStatusList.add(DetailsResultSet.getString(34));
				FileStatusList.add(DetailsResultSet.getString(35));
				FileStatusList.add(DetailsResultSet.getString(36));
				FileStatusList.add(DetailsResultSet.getString(37));
				FileStatusList.add(DetailsResultSet.getString(38));
				FileStatusList.add(DetailsResultSet.getString(39));
				FileStatusList.add(DetailsResultSet.getString(40));
				FileStatusList.add(DetailsResultSet.getString(41));
				FileStatusList.add(DetailsResultSet.getString(42));
				FileStatusList.add(DetailsResultSet.getString(43));
				FileStatusList.add(DetailsResultSet.getString(44));
				FileStatusList.add(DetailsResultSet.getString(45));
				FileStatusList.add(DetailsResultSet.getString(46));
				FileStatusList.add(DetailsResultSet.getString(47));
				FileStatusList.add(DetailsResultSet.getString(48));
                                FileStatusList.add(DetailsResultSet.getString(49));
                                FileStatusList.add(DetailsResultSet.getString(50));
                                FileStatusList.add(DetailsResultSet.getString(51));
                                FileStatusList.add(DetailsResultSet.getString(52));
				FileStatusList.add(DetailsResultSet.getString(53));
				FileStatusList.add(DetailsResultSet.getString(54));
				FileStatusList.add(DetailsResultSet.getString(55));
                                FileStatusList.add(DetailsResultSet.getString(56));
				FileStatusList.add(DetailsResultSet.getString(57));
				FileStatusList.add(DetailsResultSet.getString(58));
			}
			System.out.println("in java file SK: "+FileStatusList.size()+"] [data]["+FileStatusList+"]");
			DetailsResultSet.close();
			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			System.out.println("["+GetDate()+"]"+"In EditOffer.java:    SQL Error" + ESQL.getMessage());
		}
		finally
		{
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("["+GetDate()+"]"+"In EditOffer.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
		}
	}

}

