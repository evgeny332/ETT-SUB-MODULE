import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class EditOfferDNew extends HttpServlet
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
			System.out.println("offer_id for delete new  offer : "+fid);
			GetResultData(FileStatusList ,fid);
			Session.setAttribute("FileStatusList", FileStatusList);
			response.sendRedirect(response.encodeURL("jsp/offer_deleteNew.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In EditOfferD.java:    SQLState:  " + Eb.getMessage() );
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
		SelectQuery="select * from OfferDetails where offerId='"+fid+"'";
		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In EditOfferD.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In EditOfferD.java:    ORA Connection Created!");
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
				FileStatusList.add(DetailsResultSet.getString(26));
				FileStatusList.add(DetailsResultSet.getString(27));
				FileStatusList.add(DetailsResultSet.getString(28));
				FileStatusList.add(DetailsResultSet.getString(29));
				FileStatusList.add(DetailsResultSet.getString(30));
				FileStatusList.add(DetailsResultSet.getString(31));
				FileStatusList.add(DetailsResultSet.getString(33));
			}
			System.out.println("in java file : "+FileStatusList.size());
			DetailsResultSet.close();
			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		}
		catch(SQLException ESQL)
		{
			System.out.println("["+GetDate()+"]"+"In EditOfferD.java:    SQL Error" + ESQL.getMessage());
		}
		finally
		{
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("["+GetDate()+"]"+"In EditOfferD.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
		}
	}

}

