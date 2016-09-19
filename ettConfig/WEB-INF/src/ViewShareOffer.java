import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class ViewShareOffer extends HttpServlet
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
			response.sendRedirect(response.encodeURL("jsp/shareOfferDetails.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In ViewShareOffer.java:    SQLState:  " + Eb.getMessage() );
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
		SelectQuery="select offerId,offerName,offerInfo,description,offerAmount,offerType,payouttype,osType,priority,status,alertText,shareText,shareMessage,callBackNotification,imageURL,actionURLAndroid,vendorNameAndroid,actionURLiOS,vendorNameiOS,shareURL,balanceType,createdTime,updatedTime from ShareOfferDetails";
		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In ViewShareOffer.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In ViewShareOffer.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			ShareOfferDetailsEntity dbean = null;
			while(DetailsResultSet.next())
			{
				dbean = new ShareOfferDetailsEntity();
				dbean.setOfferId(DetailsResultSet.getString(1));
				dbean.setOfferName(DetailsResultSet.getString(2));
				dbean.setOfferInfo(DetailsResultSet.getString(3));
				dbean.setDescription(DetailsResultSet.getString(4));
				dbean.setOfferAmount(DetailsResultSet.getString(5));
				dbean.setOfferType(DetailsResultSet.getString(6));
				dbean.setPayouttype(DetailsResultSet.getString(7));
				dbean.setOsType(DetailsResultSet.getString(8));
				dbean.setPriority(DetailsResultSet.getString(9));
				dbean.setStatus(DetailsResultSet.getString(10));
				dbean.setAlertText(DetailsResultSet.getString(11));
				dbean.setShareText(DetailsResultSet.getString(12));
				dbean.setShareMessage(DetailsResultSet.getString(13));
				dbean.setCallBackNotification(DetailsResultSet.getString(14));
				dbean.setImageURL(DetailsResultSet.getString(15));
				dbean.setActionURLAndroid(DetailsResultSet.getString(16));
                                dbean.setVendorNameAndroid(DetailsResultSet.getString(17));
				dbean.setActionURLiOS(DetailsResultSet.getString(18));
				dbean.setVendorNameiOS(DetailsResultSet.getString(19));
				dbean.setShareURL(DetailsResultSet.getString(20));
				dbean.setBalanceType(DetailsResultSet.getString(21));
				dbean.setCreatedTime(DetailsResultSet.getString(22));
				dbean.setUpdatedTime(DetailsResultSet.getString(23));

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
			System.out.println("["+GetDate()+"]"+"In ViewShareOffer.java:    SQL Error" + ESQL.getMessage());
		}
		finally
		{
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("["+GetDate()+"]"+"In ViewShareOffer.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
		}
	}

}

