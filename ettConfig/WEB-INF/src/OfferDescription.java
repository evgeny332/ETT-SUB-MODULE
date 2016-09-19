import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class OfferDescription extends HttpServlet
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
		 response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
		String application_username     = null;
	       	String offerType   = null;
        	String description = null;
		int flag 			= 0;
		int size			= -1;
		ArrayList OperatorList = new ArrayList ();
		ArrayList OfferList = new ArrayList();

		try
		{
			offerType	= request.getParameter("offerType");
			description	= GetOfferType(offerType);
			out.println(description);
		}
		catch(Exception ex)
		{
			System.out.println("Exception get offerdescription : "+ex);
		}

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
	public String GetOfferType(String  OfferName)
	{
		String SelectQuery              = "";
                Statement selectStmt            = null;
                Connection activeConnection     = null;
                ResultSet DetailsResultSet      = null;
		String desc			= "";
                SelectQuery="select offer_type_desc from offertypedetials where offer_type='"+OfferName+"'";
                System.out.println(SelectQuery);
                try
                {
                        activeConnection  = OracleConnectionUtil.getConnection();
                }
                catch(Exception od)
                {
                        System.out.println("["+GetDate()+"]"+"In OfferDescription.java:   Not able to connect with ORACLE");
                        od.printStackTrace();
                }
                System.out.println("["+GetDate()+"]"+"In OfferDescription.java:    ORA Connection Created!");
                try
                {
                        selectStmt = activeConnection.createStatement();
                        DetailsResultSet = selectStmt.executeQuery(SelectQuery);
                        while(DetailsResultSet.next())
                        {
				desc=DetailsResultSet.getString(1);
                        }
                        DetailsResultSet.close();
                        selectStmt.close();
                        activeConnection.close();
                        System.out.println("query has been executed...!!!");
                }
                catch(SQLException ESQL)
                {
                        System.out.println("["+GetDate()+"]"+"In OfferDescription.java:    SQL Error" + ESQL.getMessage());
                }
                finally
                {
                        try
                        {
                                activeConnection.close();
			}
                        catch(Exception ee)
                        {
                                System.out.println("["+GetDate()+"]"+"In OfferDescription.java:    Exception: " +ee.getMessage());
                                ee.printStackTrace();
                        }
                }
		return desc;	
	}
}
