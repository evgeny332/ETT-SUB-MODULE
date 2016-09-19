import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class RedeemAmountConfig extends HttpServlet
{
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException
	{
		HttpSession Session=request.getSession(true);
		int dateflag 			= 0;
		int size			= -1;
		ArrayList FileStatusList = new ArrayList ();

		try
		{
			size=GetResultData(FileStatusList );
			Session.setAttribute("FileStatusList", FileStatusList);
			if(size == 0 )
			{
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E111&redirect=RedeemAmountConfig.jsp"));	
			}
			else
				response.sendRedirect(response.encodeURL("jsp/RedeemAmountConfig.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In RedeemAmountConfig.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
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

	public int GetResultData(ArrayList FileStatusList)
	{
		String SelectQuery 	= "";
		Statement selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;
		SelectQuery="select id,operator,amount,status,type,fee,IstRedeemAmount,threshold,IstThreshold from ett.RedeemAmountConfig";
		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In RedeemAmountConfig.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In RedeemAmountConfig.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			RedeemAmountConfigBean dbean = null;
			while(DetailsResultSet.next())
			{
				dbean = new RedeemAmountConfigBean();
				dbean.setId(DetailsResultSet.getString(1));
				dbean.setOperator(DetailsResultSet.getString(2));
				dbean.setAmount(DetailsResultSet.getString(3));
				dbean.setStatus(DetailsResultSet.getString(4));
				dbean.setType(DetailsResultSet.getString(5));
				dbean.setFee(DetailsResultSet.getString(6));
				dbean.setIstRedeemAmount(DetailsResultSet.getString(7));
                                dbean.setThreshold(DetailsResultSet.getString(8));
                                dbean.setIstThreshold(DetailsResultSet.getString(9));

				FileStatusList.add(dbean);
			}
			System.out.println("in java file : "+FileStatusList.size());
			if(FileStatusList.size() == 0)
			{
				size = 0;
			}
			DetailsResultSet.close();
			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			size = 0;	
			System.out.println("["+GetDate()+"]"+"In RedeemAmountConfig.java:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In RedeemAmountConfig.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}

}

