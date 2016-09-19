import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;

public  class ManageUserPrivilege extends HttpServlet
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

		System.out.println("----------------------------------- ManageUserPrivilege");
                String user_privilege           = "";
                Statement selectStmt            = null;
                Connection activeConnection     = null;
                ResultSet DetailsResultSet      = null;
		ArrayList UserAuthList          = new ArrayList ();
		ArrayList tabid 		= new ArrayList ();
		  String newUser                  = request.getParameter("newuser");
                String pwd                      =       request.getParameter("pwd");
                String m_user                   =request.getParameter("m_user");
		String ttps		=	request.getParameter("assign_tps"); 
		String branding = "";
		String senderid	=	request.getParameter("senderid");
		int total_tps = Integer.parseInt(ttps);
		System.out.println("newUser--------------["+newUser+"] pwd--["+pwd+"]total_tps---------["+total_tps+"]");
                
                        try
                        {
                                activeConnection  = OracleConnectionUtil.getConnection();
                        }
                        catch(Exception od)
                        {
                                System.out.println("In ManageUserPrivilege.java:   Not able to connect with ORACLE");
                                response.sendRedirect(response.encodeURL("jsp/login.jsp?ST=OD"));
                                od.printStackTrace();
                        }
			try{
			user_privilege = request.getParameter("user_privilege");
			System.out.println("user_privilege-----------["+user_privilege+"]");
		 	user_privilege = user_p(user_privilege);
                        System.out.println("In ManageUserPrivilege.java:    ORA Connection Created!");
                        selectStmt = activeConnection.createStatement();
			String qry ="select NVL(BRANDING,NULL) from CM_USER_BRANDING where USERNAME ='"+newUser+"'";	
			DetailsResultSet = selectStmt.executeQuery(qry);			
			while(DetailsResultSet.next())
				{
				branding = DetailsResultSet.getString("BRANDING");
				}
                       String SelectQuery="insert into CM_USER_LOGIN_DETAILS (USERNAME,PASSWORD,TOTAL_TPS,USER_PRIVILEGE,MASTER_USER,BRANDING,Usertype)values ('"+newUser+"','"+pwd+"',"+total_tps+",'"+user_privilege+"','"+m_user+"','"+branding+"','a')";

                        System.out.println("In ManageUserPrivilege.java:    Query to be run is "+SelectQuery);
			int updation = selectStmt.executeUpdate(SelectQuery);
			System.out.println ("updated "+updation);

			
			System.out.println("sender id"+senderid);
			String senderidArr[] = senderid.split("\\^");
			for(int s = 0 ;s<senderidArr.length ; s++)
			{
			String insertQuery="insert into CM_USER_SENDER_ID (username ,senderid )values ('"+newUser+"','"+senderidArr[s]+"')";
			 System.out.println("In ManageUserPrivilege.java:    Query to be run is "+insertQuery);
			updation = selectStmt.executeUpdate(insertQuery);
			
			 System.out.println ("updated "+updation);
			}
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
	

	public String user_p(String str)

		{
System.out.println("called");
		String strarr[]=str.split("\\^");
		for(int i=0;i<strarr.length;i++)
		{
			System.out.println(strarr[i]);
			
		}
		System.out.println("-----------------------");	
		Arrays.sort(strarr);
		
		for(int i=0;i<strarr.length;i++)
		{
			System.out.println(strarr[i]);
			
		}
		
		int len=strarr.length;
		String fstr="";
		ArrayList ar= new ArrayList();
		
		int j=1;
		for(int i=0;i<len;i++)
		
			{
				
				char fst	=	strarr[i].charAt(0);
				char sec	=	strarr[j].charAt(0);
				System.out.println("fst----["+fst+"]sec---["+sec+"]");
				if(fst==sec )
				{
					System.out.println("equal");
					ar.add(strarr[i]);
					ar.add(",");
				}
					
				else
				{
					System.out.println("not equal");
					ar.add(strarr[i]);
					
					ar.add("#");
					//int index=ar.lastIndexOf(",");
					//ar.remove(index);
				}
			
				System.out.print("fstr"+ar);
				j=j+1;
				System.out.println("len"+len+"j"+j);
				if(j==len)
				{
					ar.add(strarr[j-1]);
					System.out.print("time to exit");
					break;
				}
			
			}
			
		
		System.out.print("fstr"+ar);
		String makestr="";
		for(int k=0;k<ar.size();k++)
		{
			makestr=makestr+ar.get(k);
			
		}
		
		System.out.println("makestr"+makestr);
		/*String makearr[]=makestr.split("#");
		for(int l=1;l<makestr.length();l++)
		{
			String setParent[] = makearr[l].split(",");
			
			String p[] = setParent[0].split(".");
			if(p.length==1)
			{
				makearr[l]=makearr[l];
				
			}
			else{
				makearr[l]=p[0]+","+makearr[l];
				
			}
			System.out.println("new makearr"+makearr[l]);	
		}*/
	String s="";
		String makearr[]=makestr.split("#");
		for(int l=0;l<makearr.length;l++)
		{
			
			
			String setParent[] = makearr[l].split(",");
			System.out.println("zzzzzzzzzz"+setParent.length);
			String p[] = setParent[0].split("\\.");
			System.out.println("len"+p[0].length());
			System.out.println("p[0]"+p[0]);
			if(p.length==1)
			{
				makearr[l]=makearr[l];
				
			}
			else{
				makearr[l]=p[0]+","+makearr[l];
				
			}
			//s=s+makearr[l]+"#";
			System.out.println("new makearr"+makearr[l]);	
		}
		for(int z=0;z<makearr.length;z++)
		{
			s=s+makearr[z]+"#";

		}
		s="#"+s;
		System.out.println("s"+s);
		
	return s;	
//return makestr;
	}  	

}

