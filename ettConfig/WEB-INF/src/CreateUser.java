import java.io.*;
import java.net.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;
import java.sql.*;
public  class CreateUser extends HttpServlet
{
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws  IOException
	{
		HttpSession Session=request.getSession(true);
		String Application_Username	= "";
		Statement selectStmt		= null;
		Connection activeConnection 	= null;
		String permission		= "";
		String description		= "";
		String Action			= "";
		String newUser			= "";
		String Password			= "";
		String AssignTps		= "";
		String Brand_Logo		= "";
		String Account 			= "";
		String Limit 			= "";
		String validity			= "";
		String MasterAccount		= "";
		String URLPUSH			= "";
		String SMPP			= "";
		String GATEWAY			= "";
		String HUBID			= "";
		String CIRCLE			= "";
		try
		{
			Application_Username= (String)Session.getAttribute("userName");
			if(Application_Username == null)
                        {
                                response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
                                return ;
                        }
			MasterAccount = (String)Session.getAttribute("AccountType");
			newUser = request.getParameter("username");
			Brand_Logo = request.getParameter("brand_logo");
			Password = request.getParameter("password");
			AssignTps = request.getParameter("allocatedtps");
			permission = request.getParameter("permission");
			String[] SenderIdList = request.getParameterValues("SenderName");
			String[] permissions  = permission.split(",");
			Account = request.getParameter("Account");
			Limit = request.getParameter("limit");
			validity = request.getParameter("validity");
			SMPP = request.getParameter("smpp");
			URLPUSH = request.getParameter("urlpush");
			GATEWAY =request.getParameter("gateway");
		 	HUBID 	=request.getParameter("hubid");
			CIRCLE 	=request.getParameter("circle");
			
			String FinalDateValid = "" ;
			String DATE_FORMAT = "yyyy-MM-dd";
		        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		        Calendar cal = Calendar.getInstance();
			if(Account.equals("Prepaid"))
			{
				if(validity == null || validity.equals(""))
					validity ="365";
			}
			else
			{
				validity = ""+(99*365);				
			}
			cal.add(Calendar.DATE, Integer.parseInt(validity));
	                FinalDateValid = sdf.format(cal.getTime());
			
			System.out.println("Acount Type ["+Account+"] Limit ["+Limit+"] validity["+validity+"] FinalDateValid ["+FinalDateValid+"]");
			activeConnection  = OracleConnectionUtil.getConnection();
			System.out.println("In CreateUser.java:    ORA Connection Created!");
			try
			{
				selectStmt = activeConnection.createStatement();
				
				String QueryUserExist = "SELECT COUNT(*) FROM CM_USER_LOGIN_DETAILS WHERE USERNAME ='"+newUser+"'";
				System.out.println(QueryUserExist);
				ResultSet RsUserExist = selectStmt.executeQuery(QueryUserExist);
				int isExist = 0 ; 
				if(RsUserExist.next())
					isExist = RsUserExist.getInt(1);
				
				RsUserExist.close();
	
				if(isExist >  0 )
				{
					System.out.println("User Already Exist ["+newUser+"]");
					response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E023&redirect=../GetUserDetails&camp="+newUser));			
					return ;	
				}							

				Arrays.sort(permissions);
				System.out.println("permissions.length["+permission+"]");
				String PermStr = createPriviledgeString(permissions);
				System.out.println("permissions.length["+permissions+"]");

				String UserId = "SELECT PARENT_CHILD_ID FROM CM_USER_LOGIN_DETAILS WHERE USERNAME ='"+Application_Username+"'";
				ResultSet UserIDRs = selectStmt.executeQuery(UserId);				
				String ParentId = ""; 
				if(UserIDRs.next())
					ParentId = UserIDRs.getString(1);
				UserIDRs.close();	
				System.out.println("ParentId["+ParentId+"]");
				 
				String ChildUserIdQuery = "SELECT MAX(PARENT_CHILD_ID) FROM  CM_USER_LOGIN_DETAILS WHERE MASTER_USER ='"+Application_Username+"'";
				ResultSet ChildUserIDRs = selectStmt.executeQuery(ChildUserIdQuery);
				String ChildMaxId = null; 
				if(ChildUserIDRs.next())
					ChildMaxId = ChildUserIDRs.getString(1);

				ChildUserIDRs.close();
				System.out.println("ChildMaxId["+ChildMaxId+"]");
				String ChildsParentId = "" ;
				if(ChildMaxId != null)
				{
					String beforeLastValue = ChildMaxId.substring(0,ChildMaxId.lastIndexOf("."));
					String LastPosValue = ChildMaxId.substring(ChildMaxId.lastIndexOf(".")+1); 
					
					int nextIdValue = Integer.parseInt(LastPosValue) + 1;
					ChildsParentId = beforeLastValue + "." + nextIdValue ;
					System.out.println("beforeLastValue["+beforeLastValue+"] LastPosValue["+LastPosValue+"] ["+nextIdValue+"]");
				}
				else
				{
						ChildsParentId = ParentId + ".1" ;	
				}
				if( AssignTps == null || AssignTps.equals(""))
					AssignTps = "0";
				System.out.println("ChildsParentId["+ChildsParentId+"]");
				String CreateUserQuery = "INSERT INTO CM_USER_LOGIN_DETAILS ( USERNAME , PASSWORD , TOTAL_TPS, USER_PRIVILEGE , MASTER_USER , BRANDING , INIT_TPS , PARENT_CHILD_ID , ACCOUNT_TYPE , TOTAL_ACC_SMS , ACC_EXP_DATE,SMPP,URLPUSH,GATEWAY,user_circle,HUB_ID  ) VALUES ('"+newUser+"','"+Password+"','"+AssignTps+"','"+PermStr+"','"+Application_Username+"','"+Brand_Logo+"','"+AssignTps+"','"+ChildsParentId+"','"+Account+"',"+Limit+",'"+FinalDateValid+"','"+SMPP+"','"+URLPUSH+"','"+GATEWAY+"','"+CIRCLE+"','"+HUBID+"')";
				System.out.println(CreateUserQuery);
				selectStmt.executeUpdate(CreateUserQuery);
				for (int index = 0; index < SenderIdList.length; index++)
				{	
					String SenderId = SenderIdList[index];
					String QuerySenderId = "INSERT INTO CM_USER_SENDER_ID(USERNAME , SENDERID ) VALUES ('"+newUser+"','"+SenderId+"')";	
					selectStmt.executeUpdate(QuerySenderId);
				}
				String UpdateMasterAvail = "" ; 
				if(MasterAccount.equals("Prepaid"))
				{
					UpdateMasterAvail = "UPDATE CM_USER_LOGIN_DETAILS SET TOTAL_ACC_SMS = TOTAL_ACC_SMS - "+Limit+" WHERE USERNAME = '"+Application_Username+"'";
				}
				else
				{
					UpdateMasterAvail = "UPDATE CM_USER_LOGIN_DETAILS SET TOTAL_TPS = TOTAL_TPS - "+AssignTps+" WHERE USERNAME = '"+Application_Username+"'";
				}
				System.out.println(UpdateMasterAvail);
				selectStmt.executeUpdate(UpdateMasterAvail);
				selectStmt.close();
				activeConnection.close();
//***************************************************************Packet TO LOADBALANCER**************************

				SendTriggerToLoadBalancer(newUser,AssignTps, "1");
				SendTriggerToLoadBalancer(newUser,AssignTps, "2");

//***************************************************************Packet TO LOADBALANCER***************************
				SendTriggerToFramework("00038#5667799#ADP_RELOAD#9810766562#Reload#");
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E008&redirect=welcome.jsp&camp="+newUser));
			}
			catch(SQLException ESQL)
			{
				ESQL.printStackTrace();
				System.out.println("In CreateUser.java:    SQL Error" + ESQL.getMessage());
			}
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("In CreateUser.java:    SQLState:  " + Eb.getMessage() );
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("In CreateUser.java:    "+ ee.getMessage());
				ee.printStackTrace();
			}
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E006"));
		}
		finally
		{
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("In CreateUser.java:    Exception: " +ee.getMessage());
				ee.printStackTrace();
			}
		}
	}
	public String GetDate()
	{
		String DATE_FORMAT = "dd-MMM-yyyy HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar c1 = Calendar.getInstance();
		String CurrentDateTime = sdf.format(c1.getTime());
		return CurrentDateTime;
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
	{
		doGet(request, response);
	}
	public String createPriviledgeString(String[] permissions)
	{
		String DataString = "#" ;
		for(int index=0 ; index < permissions.length ; index++)
		{
			String FirstParameter = permissions[index]; 
			DataString = DataString + FirstParameter.charAt(0) + "," ;
			for(int InnerIndex=0 ; InnerIndex < permissions.length ; InnerIndex++)
			{
				if(FirstParameter.charAt(0) == permissions[InnerIndex].charAt(0))
				{
					DataString = DataString + permissions[InnerIndex] +"," ;
					index++;
				}
			}
			index--;
			DataString = DataString.substring(0,DataString.lastIndexOf(","));
			DataString = DataString + "#";
		}
		System.out.println("After Outer Loop ["+DataString+"]");
		return DataString ;
	}
	public void SendTriggerToLoadBalancer_AL(String username , String TPS)
	{
		try
		{
			ResourceBundle myResources = ResourceBundle.getBundle("config");
			String IP =myResources.getString("CM_IP");
			String PORT1 =myResources.getString("CM_PORT");
			int PORT=Integer.parseInt(PORT1);
			String strFinal = "#5667799#ADP_RELOAD#"+username+"#"+TPS+"#";
			String LengthSize ="00000";
			strFinal = LengthSize.substring( (""+strFinal.length()).length() ) + strFinal.length() + strFinal ;
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
	public void SendTriggerToLoadBalancer(String username , String TPS, String LBIP)
	{
		try
		{
			ResourceBundle myResources = ResourceBundle.getBundle("config");
			String IP =myResources.getString("LOAD_BALANCER_IP"+LBIP);
			String PORT1 =myResources.getString("LOAD_BALANCER_PORT");

			int PORT=Integer.parseInt(PORT1);
			String strFinal = "#ADD#"+username+"#"+TPS+"#";
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
public void SendTriggerToFramework(String msg)
        {
                try
                {  DatagramSocket clientSocket = new DatagramSocket();
                         InetAddress IPAddress =InetAddress.getByName("203.92.40.186");
                        int len1=msg.length();
                        DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(),len1,IPAddress,17000);
                        clientSocket.send(sendPacket);
                        System.out.println("IP:203.92.40.186 PORT: " + 17000);
                        clientSocket.close();

                }
                 catch(Exception e)
                {
                        e.printStackTrace();
                }


                }


}



