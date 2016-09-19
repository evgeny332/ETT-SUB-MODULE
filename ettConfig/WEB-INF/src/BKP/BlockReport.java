import com.dndbean.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class BlockReport extends HttpServlet
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
		String showDate                 = null;
		String circleselected		= null;
		String user_circle              = null;
		int dateflage                   = 0;
		ArrayList FileStatusList = new ArrayList ();
		String HubID = "";//get this from session
		String number			= null;
		int size                        = -1;
		try
		{	
			number   = request.getParameter("TMNumber");
			System.out.println("number:"+number);	
			HubID =(String)Session.getAttribute("HUB_ID");
			if(HubID == null || HubID.equals(""))
			{
				HubID = "1";
			}
			user_circle =(String)Session.getAttribute("user_circle");
			fromDateTime    = request.getParameter("DateTime1");

			circleselected  =request.getParameter("circle_id");
			System.out.println("circleselected"+circleselected);

			System.out.println("["+GetDate()+"]"+"fromDateTime is "+fromDateTime);
			if(fromDateTime == null || fromDateTime.equals(""))
			{
				dateflage = -1;
			}
			if(dateflage == -1)
			{}
			else
			{
				String currentdate = NewGetDate();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date current  = df.parse(currentdate);
				Date stdate = df.parse(fromDateTime);


				if(stdate.before(current))
				{
					size = GetResultData(fromDateTime, circleselected, number,0, FileStatusList );
				}
				if(stdate.equals(current))
				{
					size = GetResultData(fromDateTime, circleselected, number,1, FileStatusList );
				}
			}
			Session.setAttribute("FileStatusList", FileStatusList);
			if(size == 0 )
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E111&redirect=blockreport.jsp"));
			else
				response.sendRedirect(response.encodeURL("jsp/blockreport.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In BlockReport.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
		}
	}
	public int GetResultData(String fromDateTime, String circle,String number,int Tablecheck, ArrayList FileStatusList)
	{
		String Tablename = "";
		String SelectQuery = "";
		String query1			= "";
		Statement selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;
		String status			= null;
		String hubstatus		= null;
		if(Tablecheck == 0)
		{
			Tablename = "DND_CALL_DETAILS_HUB";
		}
		else
		{
			Tablename = "DND_CALL_DETAILS_CURRENT_HUB";
		}
		query1 ="select T1.HUBSTATUS,T2.DBLINKNAME from circle_code T1,dblink_detail T2 WHERE T1.STATUS =T2.STATUS and T1.NAME = '"+circle+"'";

		System.out.println("query1::"+query1);

		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In BlockReport.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In BlockReport.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(query1);
			while(DetailsResultSet.next())
			{
				hubstatus = DetailsResultSet.getString(1);
				status    = DetailsResultSet.getString(2);
			}

			if(hubstatus == null)
			{
				Tablename = Tablename + "1";
				if(status == null || status.equals(""))
				{
					Tablename = Tablename;
				}
				else
					Tablename = Tablename + "@" + status;

			}
			else if (hubstatus.equals("1") || hubstatus.equals("2"))
			{
				Tablename = Tablename + hubstatus;
				if(status == null || status.equals(""))
				{
					Tablename = Tablename;
				}
				else
					Tablename = Tablename + "@" + status;

			}

			System.out.println("Table Name after processing :: "+Tablename);
			DetailsResultSet.close();
			selectStmt.close();
			activeConnection.close();
		}
		catch(SQLException ESQL)
		{
			System.out.println("["+GetDate()+"]"+"In BlockReport.java:    SQL Error" + ESQL.getMessage());
		}
		catch(Exception e)
		{
			System.out.println("["+GetDate()+"]"+"In BlockReport.java: "+e.getMessage()); 
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
				System.out.println("["+GetDate()+"]"+"In BlockReport.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
		}

		if(number == null || number.equals("-1"))
		{
			SelectQuery = "select T1.TM_URN,T1.TM_NAME,T2.APARTY,T2.DATEVAL,T2.TIMEVAL,T3.NAME,T2.BPARTY,NVL(decode(T2.CALLACTION,'1','Allowed','2','BLock DND Call','3','OBD Block DND Call','4','System Error','5','Not Allowed'),'-')  from telemarketer_list T1 ,"+Tablename+" T2,CIRCLE_CODE T3 where T2.APARTY = T1.TM_NUMBER and T2.CIRCLE = T3.CIRCLE and T2.DATEVAL ='"+fromDateTime+"' and T3.NAME = '"+circle+"' and T2.CALLACTION IN (2,3,4,5) order by dateval,timeval";
		}
		else
		{
			SelectQuery = "select T1.TM_URN,T1.TM_NAME,T2.APARTY,T2.DATEVAL,T2.TIMEVAL,T3.NAME,T2.BPARTY,NVL(decode(T2.CALLACTION,'1','Allowed','2','BLock DND Call','3','OBD Block DND Call','4','System Error','5','Not Allowed'),'-')  from telemarketer_list T1 ,"+Tablename+" T2,CIRCLE_CODE T3 where T2.APARTY = T1.TM_NUMBER and T2.CIRCLE = T3.CIRCLE and T2.DATEVAL ='"+fromDateTime+"' and T3.NAME = '"+circle+"' and T2.CALLACTION IN (2,3,4,5) and T1.TM_NUMBER ="+number+" order by dateval ,timeval";
		}
		System.out.println("["+GetDate()+"]"+"In BlockReport.java:    Query to be run is "+SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In BlockReport.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In BlockReport.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			BlockReportBean dbean = null;
			while(DetailsResultSet.next())
			{
				dbean = new BlockReportBean();
				dbean.setAparty(DetailsResultSet.getString(3));
				dbean.setBparty(DetailsResultSet.getString(7));
				dbean.setDateval(DetailsResultSet.getString(4));
				dbean.setTimeval(DetailsResultSet.getString(5));
				dbean.setAction(DetailsResultSet.getString(8));
				dbean.setCircle(DetailsResultSet.getString(6));
				dbean.setUrn(DetailsResultSet.getString(1));
				dbean.setName(DetailsResultSet.getString(2));

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
			System.out.println("["+GetDate()+"]"+"In BlockReport.java:    SQL Error" + ESQL.getMessage());
			size	= 0;
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
				System.out.println("["+GetDate()+"]"+"In BlockReport.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
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

}

