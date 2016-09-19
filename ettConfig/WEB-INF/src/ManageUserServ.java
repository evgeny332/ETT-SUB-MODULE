import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;

public  class ManageUserServ extends HttpServlet
{

        public void init(ServletConfig config)  throws ServletException
        {
                super.init(config);
        }
        public void doGet(HttpServletRequest request,HttpServletResponse response)throws  IOException
        {
                HttpSession Session=request.getSession(true);
                String Application_Username     = "";
                String Application_Password     = "";
                String ManageUserServType                = "";
                Statement selectStmt            = null;
                Connection activeConnection     = null;
                ResultSet DetailsResultSet      = null;
		ArrayList UserAuthList          = new ArrayList ();
		ArrayList tabid 		= new ArrayList ();
		String tps			="";
		String senderid			=	"";
		ArrayList sender		=new ArrayList();

                
                        try
                        {
                                activeConnection  = OracleConnectionUtil.getConnection();
                        }
                        catch(Exception od)
                        {
                                System.out.println("In ManageUserServ.java:   Not able to connect with ORACLE");
                                response.sendRedirect(response.encodeURL("jsp/login.jsp?ST=OD"));
                                od.printStackTrace();
                        }
			try
			{
                        	System.out.println("In ManageUserServ.java:    ORA Connection Created!");
                        	selectStmt = activeConnection.createStatement();
	                        String SelectQuery="SELECT GUI_TABID,GUI_TAB_NAME from PRIVILEGE_GUI_MAPPING where MORE_CHILD='N'";
	                        System.out.println("In ManageUserServ.java:    Query to be run is "+SelectQuery);
	                        DetailsResultSet = selectStmt.executeQuery(SelectQuery);
	                   	Application_Username = "admin"; 
                        
				  while(DetailsResultSet.next())
                                {
					String GUI_TAB_NAME = DetailsResultSet.getString("GUI_TAB_NAME");
					String GUI_TABID = DetailsResultSet.getString("GUI_TABID");
					
					System.out.println("GUI_TAB_NAME ["+GUI_TAB_NAME+"]");
					UserAuthList.add(GUI_TAB_NAME);
					tabid.add(GUI_TABID);
					
		
				}
						System.out.println("-------------b/w------------");
						Session.setAttribute("UserAuthList",UserAuthList);
						Session.setAttribute("tabid",tabid);
						

 String SelectQuery2 = " select TOTAL_TPS -(select SUM(TOTAL_TPS) from CM_USER_LOGIN_DETAILS where MASTER_USER='"+Application_Username+"')from CM_USER_LOGIN_DETAILS where  USERNAME='"+Application_Username+"'";
						 System.out.println("In ManageUserServ.java:    Query to be run is "+SelectQuery2);

						DetailsResultSet=selectStmt.executeQuery(SelectQuery2);
					  	while(DetailsResultSet.next())
                                		{
							tps=DetailsResultSet.getString(1);						
						}
						Session.setAttribute("tps",tps);
	
	
String SelectQuery3 = "select SENDERID from CM_USER_SENDER_ID where   USERNAME='"+Application_Username+"'";
                                                System.out.println("In ManageUserServ.java:    Query to be run is "+SelectQuery3);
						DetailsResultSet=selectStmt.executeQuery(SelectQuery3);
                                          	while(DetailsResultSet.next())
                                		{
                                                	senderid=DetailsResultSet.getString("SENDERID");
							System.out.println("sender id   ["+senderid+"]");
							sender.add(senderid);
                                		}
							Session.setAttribute("sender",sender);
				 			response.sendRedirect(response.encodeURL("jsp/ManageUser.jsp"));
			}	
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}	
   public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
        {
                doGet(request, response);
        }

}

