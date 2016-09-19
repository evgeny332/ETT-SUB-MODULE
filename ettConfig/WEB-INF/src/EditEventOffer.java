import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class EditEventOffer extends HttpServlet
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
			GetResultData(FileStatusList ,fid);
			Session.setAttribute("FileStatusList", FileStatusList);
			if(FileStatusList.size()==0)
                        {
                                response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=D009&redirect=offer_redirectEvent.jsp"));
                        }

                        else{
                                response.sendRedirect(response.encodeURL("jsp/offerEditEvent.jsp"));
                        }

		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In EditEventOffer.java:    SQLState:  " + Eb.getMessage() );
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
		SelectQuery="select ifnull(id,''),ifnull(appKey,''),ifnull(balanceCreditInDays,''),ifnull(createdTime,''),ifnull(currency,''),ifnull(eventName,''),ifnull(eventValue,''),ifnull(incomeAmount,''),ifnull(installedAmount,''),ifnull(notification,''),ifnull(offerAmount,''),ifnull(offerCat,''),ifnull(offerId,''),ifnull(pendingAmountCredit,''),ifnull(pendingRecCount,''),ifnull(pendingRecDay,''),ifnull(status,''),ifnull(payOutHoldinMin,''),ifnull(payOutHoldNotification,'') from OfferEventDetails where id='"+fid+"'";
		System.out.println("in java file SK : "+SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In EditEventOffer.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In EditEventOffer.java:    ORA Connection Created!");
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
			}
			System.out.println("in java file SK: "+FileStatusList.size()+"] [data]["+FileStatusList+"]");
			DetailsResultSet.close();
			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			System.out.println("["+GetDate()+"]"+"In EditEventOffer.java:    SQL Error" + ESQL.getMessage());
		}
		finally
		{
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("["+GetDate()+"]"+"In EditEventOffer.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
		}
	}

}

