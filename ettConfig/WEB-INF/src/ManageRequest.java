import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import java.net.*;
public  class ManageRequest extends HttpServlet
{
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws  IOException
	{
		System.out.println("I have called cancel");
		HttpSession Session=request.getSession(true);
		String Application_Username	= "";
		String Application_Password	= "";
		String LoginType		= "";
		String camp_id			= "";
		String action			= "";
		Statement selectStmt		= null;
		Connection activeConnection 	= null;
		ResultSet rs , rs1		= null ;
		String fromDate = "" ;
		String showDate = "" ;
		try
		{

			HttpSession session = request.getSession(true);
			String user_name= (String)session.getAttribute("userName");
			if(user_name == null)
			{
				response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
				return ;
			}
			camp_id	= request.getParameter("camp_id");
			action	= request.getParameter("action1");
			fromDate =  request.getParameter("fromDate");
			System.out.println("fromDate is "+fromDate);
			if(fromDate == null)
			{
				fromDate = GetDate("yyyy-MM-dd");
				showDate = GetDate("dd-MM-yyyy");
				System.out.println("fromDate if fromDate is null"+fromDate);
			}
			else
			{	
				showDate = fromDate ; 
				String[] TimeData = fromDate.split("-");
				fromDate = TimeData[0]+"-"+TimeData[1]+"-"+TimeData[2];
			}

			try
			{		
				activeConnection  = OracleConnectionUtil.getConnection();
				System.out.println("Connection Created Successfully");
			}
			catch(Exception od)
			{
				System.out.println("In FileStatus.java:   Not able to connect with ORACLE");
				response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=OD"));
				od.printStackTrace();
			}
			System.out.println("In FileStatus.java:    ORA Connection Created!");
			selectStmt = activeConnection.createStatement();
			String gateway = "" ; 
			String Query = "SELECT GATEWAY FROM CM_USER_LOGIN_DETAILS WHERE USERNAME='"+user_name+"'";
			rs = selectStmt.executeQuery(Query);
			String msgCode = "E032";
			while(rs.next())
			{
				gateway = rs.getString("GATEWAY");
			}
			if(action.equals("Canceled"))
			{
				SendTriggerToLoadBalancer( "CANCEL"  , camp_id , gateway );
				Thread.sleep(5000);

			}
			else if (action.equals("Pause"))
			{
				SendTriggerToLoadBalancer( "PAUSE"  , camp_id ,  gateway );
				Thread.sleep(5000);
			}
			else if (action.equals("Resume"))
			{
				SendTriggerToLoadBalancer( "RESUME"  , camp_id , gateway );
				Thread.sleep(5000);
			}
			int total_tps = getTotalTps (user_name);
			EtaWindowMinuteBase cew = new EtaWindowMinuteBase();
			cew.refreshWindowSize(user_name , fromDate , total_tps );
			msgCode = getMsgCode(camp_id , action);  	
			response.sendRedirect(response.encodeURL(request.getContextPath()+"/jsp/message.jsp?ST="+msgCode+"&redirect=../FileStatus?fromDate="+fromDate+"&camp="+camp_id));
			
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E006"));
		}
		finally
			{
				try
				{
					if(activeConnection != null)
					{
						if(selectStmt!= null)
							selectStmt.close();
						activeConnection.close();
					}
				}
				catch(Exception ee)
				{
					ee.printStackTrace();
				}
			}
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
	{
		doGet(request, response);
	}
	public String GetDate(String DATE_FORMAT_NOW)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
	public String getMsgCode(String camp_id , String action )
	{
		Statement selectStmt	= null;
		Connection activeConnection 	= null;
		ResultSet rs1			= null ;
		String status = "";
		String msgCode = "E032" ;
		try
		{
			activeConnection  = OracleConnectionUtil.getConnection();
			selectStmt 	= activeConnection.createStatement();
			String Query = "SELECT CAMPAIGN_STATUS FROM PROMOTIONAL_MESSAGE_INFO WHERE CAMPAIGN_NAME='"+camp_id+"'";
			System.out.println(Query);
			rs1 = selectStmt.executeQuery(Query);
			while(rs1.next())
			{
				status = rs1.getString("CAMPAIGN_STATUS");
				System.out.println("Campaign Status ["+status+"] ");
			}
			if(status != null)
			{
				if( status.equals("Canceled") && action.equals("Canceled") )
					msgCode = "E029";
				else if( status.equals("Paused") && action.equals("Pause") )
					msgCode = "E030";
				else if( status.equals("inprogress") && action.equals("Resume") )
					msgCode = "E031";
			}
		}
		catch(Exception e)
		{
		}finally
			{
				try
				{
					if(activeConnection != null)
					{
						if(rs1 != null)
							rs1.close();
						if(selectStmt!= null)
							selectStmt.close();
						activeConnection.close();
					}
				}
				catch(Exception ee)
				{
					ee.printStackTrace();
				}
			}
		System.out.println("campaign ["+camp_id+"] action ["+action+"] status ["+status+"] msgcode ["+msgCode+"]");	
		return msgCode ; 	
	}
	public void SendTriggerToLoadBalancer(String action  , String campaign , String gateway)
	{
		try
		{
			ResourceBundle myResources = ResourceBundle.getBundle("config");
			String IP =myResources.getString(gateway);
			String PORT1 =myResources.getString("LOAD_BALANCER_PORT");
			int PORT=Integer.parseInt(PORT1);
			String strFinal = "#"+action+"#"+campaign+"#CANCEL#";
			System.out.println(strFinal);
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress =InetAddress.getByName(IP);
			int len1=strFinal.length();
			DatagramPacket sendPacket = new DatagramPacket(strFinal.getBytes(),len1,IPAddress,PORT);
			clientSocket.send(sendPacket);
			System.out.println("IP:" + IP+ " PORT:" + PORT);
			clientSocket.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public int getTotalTps(String user)
	{
		Connection activeConnection = null;
		Statement st1 = null;
		int count = 0 ;
		try
		{
			activeConnection = OracleConnectionUtil.getConnection();
			st1=activeConnection.createStatement();
			String  qur_insert1="SELECT TOTAL_TPS FROM CM_USER_LOGIN_DETAILS WHERE USERNAME = '"+user+"'";
			//System.out.println("["+GetDate()+"]"+user_name+" -----UploadServlet :: Query :: "+ qur_insert1);
			ResultSet  rs  = st1.executeQuery(qur_insert1);
			if(rs.next())
				count = rs.getInt(1);
			else
				count = 0 ;
			//System.out.println("["+GetDate()+"]"+"count ["+count+"]");
			rs.close();
			st1.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(activeConnection != null )
				{
					activeConnection.close();
				}
			}
			catch(Exception dbclose){}
		}
		return count ;
	}

}


