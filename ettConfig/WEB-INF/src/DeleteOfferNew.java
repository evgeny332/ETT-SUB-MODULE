import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;
import java.sql.PreparedStatement;

public class DeleteOfferNew extends HttpServlet
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
		PrintWriter out=response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		HttpSession Session=request.getSession(true);
		String application_username     = null;
		String offer_id			= "";
		int flag 			= 0;
		int size			= -1;
		try
		{
			offer_id    = request.getParameter("offer_id");
			System.out.println("offer_id : "+offer_id);
			if(offer_id == null || offer_id.equals(""))
			{
				flag = -1;
			}
			size=GetResultData(offer_id);
			if(size == 0 )
			{
				response.sendRedirect(response.encodeURL("jsp/offer_redirectNew.jsp"));	
			}
			else
				response.sendRedirect(response.encodeURL("jsp/offer_redirectNew.jsp"));
			
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In DeleteOffer.java:    SQLState:  " + Eb.getMessage() );
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

	public int GetResultData(String offer_id)
	{
		String SelectQuery 	= "";
		Connection activeConnection     = null;
		int size			= -1;

		SelectQuery="delete from OfferDetails where offerId=?";
		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In DeleteOffer.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In DeleteOffer.java:    ORA Connection Created!");
		try
		{
			PreparedStatement preparedStatement = activeConnection.prepareStatement(SelectQuery);
			preparedStatement.setString(1, offer_id);
			int deleteCount = preparedStatement.executeUpdate();	
			size = 0;
			System.out.println("Deleted value row successfully : " + deleteCount);
			preparedStatement.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			size = 0;	
			System.out.println("["+GetDate()+"]"+"In DeleteOffer.java:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In DeleteOffer.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}

}

