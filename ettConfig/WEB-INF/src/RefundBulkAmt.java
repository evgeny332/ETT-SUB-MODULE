import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class RefundBulkAmt extends HttpServlet
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
		String EttId  			= null;
        	String requestDate   		= null;
        	String redeemAmount 		= null;
		String msg			= null;
		String redeemQuery		= null;
		int flag 			= 0;
		int size			= -1;
		try
		{
			EttId		= request.getParameter("EttID");
			requestDate	= request.getParameter("RequestDate");
			redeemAmount	= request.getParameter("offerAmount");
			System.out.println("["+GetDate()+"]"+" EttID ["+EttId+"], Request Date ["+requestDate+"], Redeem Amount ["+redeemAmount+"]");
			
			if(EttId == null || EttId.equals(""))
			{
				flag = -1;

			}
			if(requestDate == null || requestDate.equals(""))
                        {
                                flag = -1;

                        }
			if(redeemAmount == null || redeemAmount.equals(""))
                        {
                                flag = -1;

                        }


			if(flag == 0)
			{
				flag = -1;
				msg="Dear User, we have reversed your amount for failed transaction done on "+requestDate+". Your account Summary is updated to reflect this amount of Rs."+redeemAmount+". Please Rate us 5 Star on Play Store to server you better.";
				String ettIDArray[]=EttId.split(",");
				String EttID="";
				for(int i=0;i<ettIDArray.length;i++)
				{
					System.out.println("Values : ["+ettIDArray[i]+"]");
					EttID=ettIDArray[i];
					/*redeemQuery="update UserAccount set currentBalance=currentBalance+"+redeemAmount+" where ettid="+EttID+"";
					size = GetResultData(EttID,requestDate,redeemAmount);
					if(size == 0 )
					{
						Util.ExecuteQuery(redeemQuery);
						SendPushMSG.sendPost(EttID,msg);
					}*/
					String remarks="REVERSED REDEEM AMT";
					String vender="RH";
					String offerName="REVERSED REDEEM AMT";
					ExecuteProcedure(redeemAmount,Long.parseLong(EttID),Long.parseLong("1000000"),remarks,offerName,vender);
					SendPushMSG.sendPost(EttID,msg);

				}
				//redeemQuery="update UserAccount set currentBalance=currentBalance+"+redeemAmount+" where ettid="+EttId+"";
				//size = GetResultData(EttId,requestDate,redeemAmount);
			}
			if(size == 0 )
			{
				//Util.ExecuteQuery(redeemQuery);
				//SendPushMSG.sendPost(EttId,msg);
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E021&redirect=refundBulkAmt.jsp"));	
			}
			else
				response.sendRedirect(response.encodeURL("jsp/refundBulkAmt.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In RefundAmt.java :    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016&redirect=refundBulkAmt.jsp"));
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
	public int GetResultData(String EttId,String requestDate,String redeemAmount)
	{
		String SelectQuery 		= "";
		PreparedStatement  selectStmt   = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;

		SelectQuery="insert into ett.UserAccountSummary(amount,createdTime,ettId,offerId,remarks,offerName) values (?,?,?,?,?,?)";
		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In RefundAmt.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In RefundAmt.java:    MYSQL Connection Created!");
		try
		{
			selectStmt = activeConnection.prepareStatement(SelectQuery);
			selectStmt.setString(1,redeemAmount);
			selectStmt.setString(2,GetDate());
			selectStmt.setString(3,EttId);
			selectStmt.setString(4,"1000000");
			selectStmt.setString(5,"REVERSED REDEEM AMT");
			selectStmt.setString(6,"REVERSED REDEEM AMT");
			
			int numRowsChanged = selectStmt.executeUpdate();
			size = 0;	
			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			size = 0;	
			System.out.println("["+GetDate()+"]"+"In RefundAmt.java:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In RefundAmt.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}
	private static java.sql.Date getCurrentDate() {
                java.util.Date today = new java.util.Date();
                return new java.sql.Date(today.getTime());
        }
 	public static int ExecuteProcedure(String affer_amt,long ettId,long offerId,String remarks,String offerName,String vendor)
        {
                float amount=Float.parseFloat(affer_amt);
                Connection activeConnection     = null;
                int Result = 0;
                try
                {
                        activeConnection  = OracleConnectionUtil.getConnection();
                }
                catch(Exception od)
                {
                        System.out.println("[ETT] []In RefundBulkAmt:   Not able to connect with MYSQL");
                        od.printStackTrace();
                }
                try
                {
                         CallableStatement cStmt = activeConnection.prepareCall("{call UserAccountUpdate(?,?,?,?,?,?,?,?,?,?)}");
                         cStmt.setFloat(1, amount);
                        cStmt.setDate(2, getCurrentDate());
                         cStmt.setLong(3, ettId);
                         cStmt.setLong(4, offerId);
                         cStmt.setString(5, remarks);
                         cStmt.setString(6, offerName);
                         cStmt.setInt(7, 0);
                         cStmt.setString(8, vendor);
                         cStmt.setString(9, "");
                         cStmt.setInt(10, 1);
                         cStmt.execute();
                        cStmt.close();

                        activeConnection.close();
                        System.out.println("query has been executed...!!!"+Result);
                }
                catch(Exception ESQL)
                {
                        System.out.println("[ETT]  In RefundBulkAmt:    SQL Error" + ESQL.getMessage());
                }
				finally
                {
                        try
                        {

                                activeConnection.close();
                        }
                        catch(Exception ee)
                        {
                                System.out.println("[ETT] []In RefundBulkAmt:    Exception: " +ee.getMessage());
                                ee.printStackTrace();
                        }
                }
                return Result;
        }


}

