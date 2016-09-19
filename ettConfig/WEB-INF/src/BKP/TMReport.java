import com.dndbean.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date.*;
import java.text.*;
import java.sql.*;


public class TMReport extends HttpServlet
{
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException
	{
		HttpSession Session=request.getSession(true);
		String application_username     = null;
		String fromDateTime             = null;
		String toDateTime               = null;
		String showDate                 = null;
		String circleselected		= null;
		String user_circle              = null;
		int dateflage                   = 0;
		ArrayList FileStatusList = new ArrayList ();
		String HubID = "";//get this from session
		String urn_name			= null;
		String number			= null;
		int size                        = -1;
		try
		{	
			urn_name = request.getParameter("urn_name"); 
			number   = request.getParameter("number");
			System.out.println("urn_name:"+urn_name+"number:"+number+"HUB_ID:"+HubID+"user_circle:"+user_circle);	
			HubID =(String)Session.getAttribute("HUB_ID");
			if(HubID == null || HubID.equals(""))
			{
				HubID = "1";
			}
			user_circle =(String)Session.getAttribute("user_circle");
			fromDateTime    = request.getParameter("DateTime1");

			circleselected  =request.getParameter("circle");
			System.out.println("circleselected"+circleselected);

			System.out.println("["+GetDate()+"]"+"fromDateTime is "+fromDateTime);
			if(fromDateTime == null || fromDateTime.equals(""))
			{
				dateflage = -1;
			}

			toDateTime    = request.getParameter("DateTime2");
			System.out.println("["+GetDate()+"]"+"toDateTime is "+toDateTime);
			if(toDateTime == null || toDateTime.equals(""))
			{
				dateflage = -1;
			}
			if(urn_name == null || urn_name.equals(""))
                        {
                                if(number == null || number.equals(""))
				{
					dateflage = -1;
				}
                        }	
			else if(number.equals("-1") )
			{
				if(urn_name.equals("-1"))
					dateflage = 0;
				else
					dateflage = 1;
			}
			else
				dateflage = 2;
			
			System.out.println("Value of dateflage::"+dateflage);	
			if(dateflage == 0)
			{
				size = GetResultData(fromDateTime, toDateTime, urn_name, number, dateflage, FileStatusList );
			}
			if(dateflage == 1)
			{
				size = GetResultData(fromDateTime, toDateTime, urn_name, number, dateflage, FileStatusList );
			}
			if(dateflage == 2)
			{
				size = GetResultData(fromDateTime, toDateTime, urn_name, number, dateflage, FileStatusList );
			}
			Session.setAttribute("FileStatusList", FileStatusList);
			if(size == 0 )
                                response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E111&redirect=telemarketer.jsp"));
                        else
                                response.sendRedirect(response.encodeURL("jsp/telemarketer.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In TMReport.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
		}
	}
	public int GetResultData(String fromDateTime, String toDateTime,String urn_name,String number,int flag,ArrayList FileStatusList)
	{
		String TableName = "";
		String SelectQuery = "";
		Statement selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;
		if(flag == 0)
		{
			SelectQuery="select T1.DATEVAL ,T2.TM_URN,T2.TM_NAME,T3.NAME,sum(T1.TOTAL) as TOTAL,sum(T1.ACTION1) as ALLOWED,sum((T1.ACTION2+T1.ACTION3+T1.ACTION4+T1.ACTION5)) as BLOCKED,round (((sum(T1.ACTION1)/sum(T1.TOTAL))*100),2) as PALLOWED,round (((sum((T1.ACTION2+T1.ACTION3+T1.ACTION4+T1.ACTION5))/sum(T1.TOTAL))*100),2) as PBLOCKED from TM_DETAIL_REPORT T1,telemarketer_list T2,CIRCLE_CODE T3 WHERE T1.TM_NUMBER=T2.TM_NUMBER and T1.DATEVAL >='"+fromDateTime+"' and T1.DATEVAL <= '"+toDateTime+"' and T3.CIRCLE =T2.CIRCLE group by T1.DATEVAL,T2.TM_URN,T2.TM_NAME,T3.NAME order by T1.DATEVAL desc";	
		}

	//round(((ACTION2/(ACTION1+ACTION2))*100),2)
		else if(flag == 1)
		{
			SelectQuery="select T1.DATEVAL ,T2.TM_URN,T2.TM_NAME,T3.NAME,sum(T1.TOTAL) as TOTAL,sum(T1.ACTION1) as ALLOWED,sum((T1.ACTION2+T1.ACTION3+T1.ACTION4+T1.ACTION5)) as BLOCKED ,round (((sum(T1.ACTION1)/sum(T1.TOTAL))*100),2) as PALLOWED,round (((sum((T1.ACTION2+T1.ACTION3+T1.ACTION4+T1.ACTION5))/sum(T1.TOTAL))*100),2) as PBLOCKED from TM_DETAIL_REPORT T1,telemarketer_list T2,CIRCLE_CODE T3 WHERE T1.TM_NUMBER=T2.TM_NUMBER and T2.TM_NAME ='"+urn_name+"' and T1.DATEVAL >='"+fromDateTime+"' and T1.DATEVAL <= '"+toDateTime+"'and T3.CIRCLE =T2.CIRCLE group by T1.DATEVAL,T2.TM_URN,T2.TM_NAME,T3.NAME order by T1.DATEVAL desc";
		}
		else if(flag == 2)
		{	
			SelectQuery="select T1.DATEVAL ,T2.TM_URN,T2.TM_NAME,T3.NAME,T1.TOTAL,T1.ACTION1 as allowed ,(T1.ACTION2+T1.ACTION3+T1.ACTION4+T1.ACTION5) as blocked,round (((T1.ACTION1/T1.TOTAL)*100),2) as PALLOWED,round ((((T1.ACTION2+T1.ACTION3+T1.ACTION4+T1.ACTION5)/T1.TOTAL)*100),2) as PBLOCKED from TM_DETAIL_REPORT T1,telemarketer_list T2,CIRCLE_CODE T3 WHERE T1.TM_NUMBER=T2.TM_NUMBER and T3.CIRCLE =T2.CIRCLE and T1.TM_NUMBER ="+number+" and  T1.DATEVAL >='"+fromDateTime+"' and T1.DATEVAL <= '"+toDateTime+"' order by T1.DATEVAL desc";
		}
		System.out.println("["+GetDate()+"]"+"In TMReport.java:    Query to be run is "+SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In TMReport.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In TMReport.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			TMReportBean dbean = null;
			while(DetailsResultSet.next())
			{
				dbean = new TMReportBean();
				dbean.setName(DetailsResultSet.getString(3));
				dbean.setUrn(DetailsResultSet.getString(2));
				dbean.setDateval(DetailsResultSet.getString(1));
				dbean.setCircle(DetailsResultSet.getString(4));
				dbean.setTotal(DetailsResultSet.getString(5));
				dbean.setAllowed(DetailsResultSet.getString(6));
				dbean.setBlocked(DetailsResultSet.getString(7));
				dbean.setPallowed(DetailsResultSet.getString(8));
				dbean.setPblocked(DetailsResultSet.getString(9));
				FileStatusList.add(dbean);
			}
			System.out.println("in java file : "+FileStatusList.size());
			if(FileStatusList.size() == 0)
                                size = 0;
			System.out.println("query has been executed...!!!");
			DetailsResultSet.close();
                        selectStmt.close();
			activeConnection.close();
		}
		catch(SQLException ESQL)
		{
			System.out.println("["+GetDate()+"]"+"In TMReport.java:    SQL Error" + ESQL.getMessage());
		}
		finally
		{
			try
			{
				DetailsResultSet.close();
                       		selectStmt.close();
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("["+GetDate()+"]"+"In TMReport.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
		}
		return size;
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

}

