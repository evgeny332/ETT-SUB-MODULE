import com.dndbean.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date.*;
import java.text.*;
import java.sql.*;


public class SearchMsisdn extends HttpServlet
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
		String user_circle              = null;
		String msisdn 			= null;
		int dateflage                   = 0;
		int size			= -1;
		ArrayList FileStatusList = new ArrayList ();
		String HubID = "";//get this from session
		try
		{
			HubID =(String)Session.getAttribute("HUB_ID");
			msisdn = request.getParameter("msisdn");
			if(msisdn == null || msisdn.equals(""))
                        {
                                dateflage = -1;
                        }
			if(HubID == null || HubID.equals(""))
			{
				HubID = "1";
			}
			user_circle =(String)Session.getAttribute("user_circle");
	
		
			if(dateflage == 0)
			{
				size = GetResultData( msisdn,HubID,FileStatusList );
			}
			Session.setAttribute("FileStatusList", FileStatusList);
			if(size == 0)
                                response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E111&redirect=searchmsisdn.jsp"));
                        else
                                response.sendRedirect(response.encodeURL("jsp/searchmsisdn.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In SearchMsisdn.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
		}
	}
	public int GetResultData(String msisdn,String HubID,ArrayList FileStatusList)
	{
		String SelectQuery = "";
		Statement selectStmt            = null;
		Connection activeConnection     = null;
		ResultSet DetailsResultSet      = null;
		int size			= -1;
		String interfaceused		= null;

                SelectQuery="select T2.MSISDN,T1.FILENAME,TO_CHAR(T1.SDATETIME,'yyyy-mm-dd hh:MI:SS') as receivetime ,TO_CHAR(T1.EDATETIME,'yyyy-mm-dd hh:MI:SS') as updatetime from DND_DATA T2,FILEDETAILS T1 where T1.FID = T2.FID and T2.MSISDN = "+msisdn+"";
		
		System.out.println("["+GetDate()+"]"+"In SearchMsisdn.java:    Query to be run is "+SelectQuery);
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
		}
		catch(Exception od)
		{
			System.out.println("["+GetDate()+"]"+"In SearchMsisdn.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("["+GetDate()+"]"+"In SearchMsisdn.java:    ORA Connection Created!");
		try
		{
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			SearchBean dbean = null;
			while(DetailsResultSet.next())
			{
				String file = DetailsResultSet.getString(2);
			//	int index   = file.indexOf("_ADD") || file.indexOf("_DEL")	
				if((file.indexOf("_ADD") == -1) && (file.indexOf("_DEL") == -1) )
				
					interfaceused = "File";
				else 
					interfaceused = "Web";
				
				dbean = new SearchBean();
				dbean.setMsisdn(DetailsResultSet.getString(1));
				dbean.setFilename(DetailsResultSet.getString(2));
				dbean.setRecdateval(DetailsResultSet.getString(3));
				dbean.setInterfaceuse(interfaceused);
				dbean.setUpdateval(DetailsResultSet.getString(4));
				FileStatusList.add(dbean);
			}
			System.out.println("in SearchMsisdn.java : "+FileStatusList.size());
			if(FileStatusList.size() == 0)
                                size = 0;
			
			DetailsResultSet.close();	
			selectStmt.close();
			activeConnection.close();
		}
		catch(SQLException ESQL)
		{
			System.out.println("["+GetDate()+"]"+"In SearchMsisdn.java:    SQL Error" + ESQL.getMessage());
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
				System.out.println("["+GetDate()+"]"+"In SearchMsisdn.java:    Exception: " +ee.getMessage());
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

