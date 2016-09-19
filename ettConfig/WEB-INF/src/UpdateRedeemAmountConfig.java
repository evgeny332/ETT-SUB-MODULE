import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateRedeemAmountConfig extends HttpServlet
{
	private final String USER_AGENT = "Mozilla/5.0";
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException
	{
		HttpSession Session=request.getSession(true);
		String application_username     = null;
		application_username= (String)Session.getAttribute("userName");
		int flag 			= 0;
		int size			= -1;

		try
		{
			String Id=	request.getParameter("id");
			String Operator=request.getParameter("Operator");
			String Amount=request.getParameter("Amount");
			String Status=request.getParameter("Status");
			String Type=request.getParameter("Type");
			String Fee=request.getParameter("Fee");
			String IstRedeemAmount=request.getParameter("IstRedeemAmount");
			String threshold=request.getParameter("threshold");
			String IstThreshold=request.getParameter("IstThreshold");
			
			
			if(Id == null || Id.equals(""))
			{
				flag = -1;
			}
			if(Operator == null || Operator.equals(""))
                        {
                                flag = -1;
                        }

			if(flag == 0)
			{
				size = GetResultData(Id,Operator,Amount,Status,Type,Fee,IstRedeemAmount,threshold,IstThreshold,application_username);
			}
			if(size == 0 )
			{
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E021&redirect=feedbackRedirect.jsp"));	
			}
			else
				response.sendRedirect(response.encodeURL("jsp/feedbackRedirect.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In UpdateRedeemAmountConfig.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016&redirect=offer_config.jsp"));
		}

	}
	 private void sendPost(String ETTID, String Msg) throws Exception {

                String url = "http://www.earntalktime.com/ett/api/v1/user/push/?";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

                String urlParameters = "ettIds="+ETTID+"&text="+Msg;

                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                }
                in.close();

                System.out.println(response.toString());

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
	public int GetResultData(String Id,String Operator,String Amount,String Status,String Type,String Fee,String IstRedeemAmount,String threshold,String IstThreshold,String application_username)
	{
		String SelectQuery 		= "";
		PreparedStatement  selectStmt   = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;

		SelectQuery="update RedeemAmountConfig set operator=?,amount=?,status=? ,type=?,fee=?, IstRedeemAmount=?,threshold=?,IstThreshold=?  where id='"+Id+"'";
		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In UpdateRedeemAmountConfig.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In UpdateRedeemAmountConfig.java:    ORA Connection Created! and Execute Query User ["+application_username+"] Query ["+SelectQuery+"]");
		try
		{
			selectStmt = activeConnection.prepareStatement(SelectQuery);
			selectStmt.setString(1,Operator);
			selectStmt.setString(2,Amount);
			selectStmt.setString(3,Status);
			selectStmt.setString(4,Type);
			selectStmt.setString(5,Fee);
			selectStmt.setString(6,IstRedeemAmount);
			selectStmt.setString(7,threshold);
			selectStmt.setString(8,IstThreshold);

		
			
			int numRowsChanged = selectStmt.executeUpdate();
			size = 0;	
			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			size = 0;	
			System.out.println("["+GetDate()+"]"+"In UpdateRedeemAmountConfig.java:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In UpdateRedeemAmountConfig.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}
	public int UserFeedbackSummary(String id,String ettid,String msg,String reply_mag)
        {
                String SelectQuery              = "";
                PreparedStatement  selectStmt   = null;
                Connection activeConnection     = null;
                ResultSet DetailsResultSet      = null;
                int size                        = -1;

                SelectQuery="insert into Feedback_Status(status_id,createdTime,ettId,feedback,reply) values(?,?,?,?,?) ";
                System.out.println(SelectQuery);
                try
                {
                        activeConnection  = OracleConnectionUtil.getConnection();
                }
                catch(Exception od)
                {
                        System.out.println("["+GetDate()+"]"+"In UpdateRedeemAmountConfig.java:   Not able to connect with ORACLE");
                        od.printStackTrace();
                }
                System.out.println("["+GetDate()+"]"+"In UpdateRedeemAmountConfig.java:    ORA Connection Created!");
                try
                {
			selectStmt = activeConnection.prepareStatement(SelectQuery);
			selectStmt.setString(1,id);
			selectStmt.setString(2,NewGetDate());
			selectStmt.setString(3,ettid);
			selectStmt.setString(4,msg);
			selectStmt.setString(5,reply_mag);
			int numRowsChanged = selectStmt.executeUpdate();
                        size = 0;
                        selectStmt.close();
                        activeConnection.close();
                        System.out.println("query has been executed...!!!");
                }
                catch(SQLException ESQL)
                {
                        size = 0;
                        System.out.println("["+GetDate()+"]"+"In UpdateRedeemAmountConfig.java:    SQL Error" + ESQL.getMessage());
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
							System.out.println("["+GetDate()+"]"+"In UpdateRedeemAmountConfig.java:    Exception: " +ee.getMessage());
                                ee.printStackTrace();
                        }
                        return size;
                }
        }


}

