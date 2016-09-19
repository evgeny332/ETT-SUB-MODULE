import com.dndbean.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;


public class DndSearchServlet extends HttpServlet
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
		String aparty                   = null;
		String bparty                   = null;
		String toDateTime               = null;
		String user_circle              = null;
		Statement selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int dateflag 			= 0;
		int circleflag	 		= 0;
		int size			= -1;
		String Table			= "";
		String query1           	= "";
		String status           	= "";
		String hubstatus        	= "";
		ArrayList FileStatusList = new ArrayList ();

		String HubID = "";//get this from session
		try
		{
			HubID =(String)Session.getAttribute("HUB_ID");
			if(HubID == null || HubID.equals(""))
			{
				HubID = "1";
			}
			user_circle =(String)Session.getAttribute("user_circle");
			if(user_circle== null || user_circle.equals(""))
			{
				circleflag = -1;
			}
			else
			{
				circleflag = Integer.parseInt(user_circle);
			}
			fromDateTime    = request.getParameter("DateTime1");
			System.out.println("["+GetDate()+"]"+"fromDateTime is "+fromDateTime);
			if(fromDateTime == null || fromDateTime.equals(""))
			{
				dateflag = -1;
			}
			toDateTime    = request.getParameter("DateTime2");
			System.out.println("["+GetDate()+"]"+"toDateTime is "+toDateTime);
			if(toDateTime == null || toDateTime.equals(""))
			{
				dateflag = -1;
			}

			aparty        = request.getParameter("AParty");
			bparty        = request.getParameter("BParty");

			if(dateflag == -1)
			{
			}
			else
			{
				String currentdate = NewGetDate();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date current  = df.parse(currentdate);
				Date stdate = df.parse(fromDateTime);
				Date enddate = df.parse(toDateTime);

				//---------------------


				query1 ="select T1.HUBSTATUS,T3.DBLINKNAME from circle_code T1,TELEMARKETER_LIST T2,dblink_detail T3 WHERE T1.CIRCLE = T2.CIRCLE and T3.STATUS =T1.STATUS and T2.TM_NUMBER="+aparty+"";

				System.out.println("query1::"+query1);

				try
				{
					activeConnection  = OracleConnectionUtil.getConnection();
				}
				catch(Exception od)
				{
					System.out.println("["+GetDate()+"]"+"In DndSearchServlet.java:   Not able to connect with ORACLE");
					od.printStackTrace();
				}
				System.out.println("["+GetDate()+"]"+"In DndSearchServlet.java:    ORA Connection Created!");
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
						Table = Table + "1";
						if(status == null || status.equals(""))
						{
							Table = Table;
						}
						else 
							Table = Table + "@" + status;

					}
					else if (hubstatus.equals("1") || hubstatus.equals("2"))
					{
						Table = Table + hubstatus;
						if(status == null || status.equals(""))
                                                {
                                                        Table = Table;
                                                }
						else 
							Table = Table + "@" + status;

					}
					System.out.println("TableName for hubstatus::"+Table);
					DetailsResultSet.close();
					selectStmt.close();
					activeConnection.close();
				}
				catch(SQLException ESQL)
				{
					System.out.println("["+GetDate()+"]"+"In DndSearchServlet.java:    SQL Error" + ESQL.getMessage());
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
						System.out.println("["+GetDate()+"]"+"In DndSearchServlet.java:    Exception: " +ee.getMessage());
						ee.printStackTrace();
					}
				}
				//------------------			



				if(enddate.before(current))
				{
					size = GetResultData(fromDateTime,toDateTime,aparty,bparty,0,Table,FileStatusList);
				}
				else if(stdate.equals(current) && enddate.equals(current))
				{
					size = GetResultData(fromDateTime,toDateTime,aparty,bparty,1,Table,FileStatusList);
				}
				else
				{
					size = GetResultData(fromDateTime,toDateTime,aparty,bparty,0,Table,FileStatusList);
					size = GetResultData(currentdate,currentdate,aparty,bparty,1,Table,FileStatusList);

				}


			}
			Session.setAttribute("FileStatusList", FileStatusList);
			if(size == 0 )
			{
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E111&redirect=dnd_search.jsp"));	
			}
			else
				response.sendRedirect(response.encodeURL("jsp/dnd_search.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In DndSearchServlet.java:    SQLState:  " + Eb.getMessage() );
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

	public int GetResultData(String startdate, String enddate, String aparty, String bparty, int flag, String Table, ArrayList FileStatusList)
	{
		String TableName 	= "";
		String SelectQuery 	= "";
		String WhereCondition 	= "";
		Statement selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;

		// Get Table Name
		if(flag == 0)
		{
			TableName = "DND_CALL_DETAILS_HUB";
		}
		else
		{
			TableName = "DND_CALL_DETAILS_CURRENT_HUB";
		}
		TableName = TableName + Table;
		WhereCondition = "and T1.APARTY =" + aparty + " and T1.BPARTY =" + bparty ;


		SelectQuery ="SELECT T1.APARTY ,T1.BPARTY ,T1.DATEVAL ,T1.TIMEVAL ,T1.MSC ,NVL(decode(T1.CALLACTION,'1','Allowed','2','BLock DND Call','3','OBD Block DND Call','4','System Error','5','Not Allowed'),'-'),T1.HUBID ,T2.NAME FROM "+TableName+" T1 ,CIRCLE_CODE T2 WHERE DATEVAL >= '"+startdate+"' AND DATEVAL <= '"+enddate+"' "+WhereCondition+" AND T1.CIRCLE = T2.CIRCLE order by T1.DATEVAL desc"; 

		System.out.println(SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In DndSearchServlet.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In DndSearchServlet.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			DndSearchBean dbean = null;
			while(DetailsResultSet.next())
			{
				dbean = new DndSearchBean();
				dbean.setAparty(DetailsResultSet.getString(1));
				dbean.setBparty(DetailsResultSet.getString(2));
				dbean.setDatetime(DetailsResultSet.getString(3));
				dbean.setTimeval(DetailsResultSet.getString(4));
				dbean.setMscnumber(DetailsResultSet.getString(5));
				dbean.setCallaction(DetailsResultSet.getString(6));
				dbean.setScpid(DetailsResultSet.getString(7));
				dbean.setCircle(DetailsResultSet.getString(8));
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
			System.out.println("["+GetDate()+"]"+"In DndSearchServlet.java:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In DndSearchServlet.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}

}

