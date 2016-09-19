import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
public  class GetUserDetails extends HttpServlet
{
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws  IOException
	{
		HttpSession Session=request.getSession(true);
		String Application_Username	= "";
		String Application_Password	= "";
		String LoginType		= "";
		Statement selectStmt		= null;
		Connection activeConnection 	= null;
		ResultSet DetailsResultSet 	= null;
		int Total_tps = 0 ; 
		int Assigned_tps = 0 ; 
		int AvailTps = 0 ; 
		int TotalSms = 0 ; 
		int usedSms = 0 ; 
		try
		{
			Application_Username = (String)Session.getAttribute("userName");
			if(Application_Username == null)
                        {
                                response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
                                return ;
                        }
			System.out.println("GetUserDetails :: Application_Username ["+Application_Username+"]");
			if(Application_Username == null)
			{
				response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=OD"));
				return;
			}
			try
			{		
				activeConnection  = OracleConnectionUtil.getConnection();
			}
			catch(Exception od)
			{
				System.out.println("In GetUserDetails.java:   Not able to connect with ORACLE");
				response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=OD"));
				od.printStackTrace();
			}
			System.out.println("In GetUserDetails.java:    ORA Connection Created!");
			selectStmt = activeConnection.createStatement();
			String GetTotalQuery = "SELECT TOTAL_TPS , INIT_TPS , ACCOUNT_TYPE , ACC_USED_SMS , ACC_EXP_DATE , TOTAL_ACC_SMS FROM CM_USER_LOGIN_DETAILS WHERE USERNAME = '"+Application_Username+"'";
			System.out.println("In GetUserDetails.java:    Query to be run is "+GetTotalQuery);
			ResultSet TotalResultSet = selectStmt.executeQuery(GetTotalQuery);
			if(TotalResultSet.next())
			{
				AvailTps = TotalResultSet.getInt(2);	
				Total_tps = TotalResultSet.getInt(1);			
				String Tsms = TotalResultSet.getString("TOTAL_ACC_SMS");
				String Usms = TotalResultSet.getString("ACC_USED_SMS");
				if(Tsms == null)
					TotalSms = 0 ; 
				else
					TotalSms  = Integer.parseInt(Tsms);
				
				if(Usms == null)
					usedSms = 0 ;
				else 
					usedSms = Integer.parseInt(Usms);
			}
			TotalResultSet.close();
			System.out.println("In GetUserDetails.java: INIT TPS :: ["+Total_tps + "] AVAIL TPS ["+AvailTps+"]");	
			Assigned_tps = Total_tps - AvailTps ;
			int AvailLimit = TotalSms - usedSms ;
			String selectAvailSenderId = "SELECT SENDERID FROM CM_USER_SENDER_ID WHERE  USERNAME='"+Application_Username+"'";
			
			ArrayList AvailSenderId	= new ArrayList();
			ResultSet SenderIdResultSet = selectStmt.executeQuery(selectAvailSenderId);			
			while(SenderIdResultSet.next())
			{
				String AvailSender = SenderIdResultSet.getString("SENDERID");
				System.out.println("User ["+Application_Username+"] SenderId ["+AvailSender+"]");
				AvailSenderId.add(AvailSender);
			}
			Session.setAttribute("AvailSenderId",AvailSenderId);
			SenderIdResultSet.close();
			selectStmt.close();
			activeConnection.close();
			response.sendRedirect(response.encodeURL("jsp/CreateUser.jsp?total="+Total_tps+"&AvailLimit="+AvailLimit+"&used="+Assigned_tps));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("In GetUserDetails.java:    SQLState:  " + Eb.getMessage() );
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("In GetUserDetails.java:    "+ ee.getMessage());
				ee.printStackTrace();
			}
			response.sendRedirect(("jsp/redirectpage.jsp?ST=E006"));
		}
		finally
		{
			try
			{
				if(activeConnection != null)
				{
					activeConnection.close();
				}
			}
			catch(Exception ee)
			{
				System.out.println("In GetUserDetails.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
		}
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
	{
		doGet(request, response);
	}

}



