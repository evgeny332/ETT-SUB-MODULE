import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class ViewOfferNew extends HttpServlet
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
			response.sendRedirect(response.encodeURL("jsp/offer_viewNew.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In ViewOffer.java:    SQLState:  " + Eb.getMessage() );
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
		SelectQuery="select * from OfferDetails";
		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In ViewOffer.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In ViewOffer.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			OfferDetails dbean = null;
			while(DetailsResultSet.next())
			{
				dbean = new OfferDetails();
				dbean.setOfferId(DetailsResultSet.getString(1));
				dbean.setOfferName(DetailsResultSet.getString(8));
				dbean.setOfferType(DetailsResultSet.getString(9));

				dbean.setAppKey(DetailsResultSet.getString(3));
				dbean.setImageUrl(DetailsResultSet.getString(6));
				dbean.setActionUrl(DetailsResultSet.getString(2));
				dbean.setOfferAmount(DetailsResultSet.getString(7));
				dbean.setDescription(DetailsResultSet.getString(5));
				dbean.setStatus(DetailsResultSet.getString(12));
				dbean.setOperator(DetailsResultSet.getString(10));
				dbean.setPriority(DetailsResultSet.getString(11));
				dbean.setCreatedTime(DetailsResultSet.getString(4));
				dbean.setUpdatedTime(DetailsResultSet.getString(13));
				dbean.setSize(DetailsResultSet.getString(14));
				dbean.setNetworkType(DetailsResultSet.getString(15));
                                dbean.setAlertText(DetailsResultSet.getString(16));
				dbean.setBalanceType(DetailsResultSet.getString(17));
				dbean.setCategory(DetailsResultSet.getString(18));
				dbean.setBalanceCreditInDays(DetailsResultSet.getString(19));
				dbean.setIsClick(DetailsResultSet.getString(20));
				dbean.setIsDownload(DetailsResultSet.getString(21));
				dbean.setIsShare(DetailsResultSet.getString(22));
				dbean.setIsShop(DetailsResultSet.getString(23));
				dbean.setRoot(DetailsResultSet.getString(24));
				dbean.setPendingAmountCredit(DetailsResultSet.getString(26));
				dbean.setPendingRecCount(DetailsResultSet.getString(27));
				dbean.setPendingRecDay(DetailsResultSet.getString(28));
				dbean.setCallbackNotification(DetailsResultSet.getString(29));
				dbean.setInstalledAmount(DetailsResultSet.getString(30));
				dbean.setInstalledNotification(DetailsResultSet.getString(31));
				dbean.setVendor(DetailsResultSet.getString(33));
				dbean.setGender(DetailsResultSet.getString(34));
				dbean.setOperatorCircle(DetailsResultSet.getString(35));
				dbean.setOfferCat(DetailsResultSet.getString(36));
				dbean.setAgeLimit(DetailsResultSet.getString(37));
				dbean.setPlayStoreDetails(DetailsResultSet.getString(38));
				dbean.setRating(DetailsResultSet.getString(39));
				dbean.setInstructions(DetailsResultSet.getString(40));
				dbean.setAppDescription(DetailsResultSet.getString(41));
				dbean.setDetailedInstructions(DetailsResultSet.getString(42));
				dbean.setPayOutOn(DetailsResultSet.getString(43));
				dbean.setPayoutType(DetailsResultSet.getString(44));
				dbean.setUserDeferedInfo(DetailsResultSet.getString(45));
				dbean.setOfferCategory(DetailsResultSet.getString(46));
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
			System.out.println("["+GetDate()+"]"+"In ViewOffer.java:    SQL Error" + ESQL.getMessage());
		}
		finally
		{
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("["+GetDate()+"]"+"In ViewOffer.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
		}
	}

}

