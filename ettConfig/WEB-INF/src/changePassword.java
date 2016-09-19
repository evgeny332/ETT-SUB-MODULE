import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
public  class changePassword extends HttpServlet
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
		Statement selectStmt1            = null;
		Connection activeConnection 	= null;
		String password			= "";
		String cpassword                = "";
		String User			= "";
		ResultSet rs 			= null;
		String pass 			= "";
		try
		{
			Application_Username= (String)Session.getAttribute("userName");
			if(Application_Username == null)
			{
				response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
				return ;
			}
			password	= request.getParameter("password");
			cpassword       = request.getParameter("cpassword");
			User		= request.getParameter("username");
			cpassword 	= cpassword.trim();
			System.out.println("In changePassword.java :: "+User);

			try
			{		
				activeConnection  = OracleConnectionUtil.getConnection();
			}
			catch(Exception od)
			{
				System.out.println("In changePassword.java:   Not able to connect with ORACLE");
				response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=OD"));
				od.printStackTrace();
			}
			System.out.println("In changePassword.java:    ORA Connection Created!");
			try
			{
				selectStmt1 = activeConnection.createStatement();

				String UserQuery1 = "select password from CM_USER_LOGIN_DETAILS where username ='"+User+"'";
				System.out.println("In changePassword.java :: "+UserQuery1);
				rs = selectStmt1.executeQuery(UserQuery1);
				while (rs.next())
				{
					pass = rs.getString(1);
				}

				if(cpassword.equals(pass))
				{

					String UserQuery = "UPDATE CM_USER_LOGIN_DETAILS  SET PASSWORD ='"+password+"' WHERE USERNAME='"+User+"' ";			
					try
					{
						selectStmt = activeConnection.createStatement();
						System.out.println("In changePassword.java :: "+UserQuery);
						selectStmt.executeUpdate(UserQuery);	

						selectStmt.close();
						activeConnection.close();
						response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E010&redirect=welcome.jsp&camp="+User));				
					}
					catch (SQLException ESQL)
					{

						System.out.println("In changePassword.java:    SQL Error" + ESQL.getMessage());
						response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=OD"));
					}
				}
				else 
				{
					selectStmt1.close();
					activeConnection.close();
					response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E100&redirect=welcome.jsp&camp="+User));				
				}

			}
			catch(SQLException ESQL)
			{
				System.out.println("In changePassword.java:    SQL Error" + ESQL.getMessage());
				response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=OD"));
			}
			finally
			{
				try
				{
					activeConnection.close();
				}
				catch(Exception ee)
				{
					System.out.println("In changePassword.java:    Exception: " +ee.getMessage());
					ee.printStackTrace();
				}
			}
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("In changePassword.java:    SQLState:  " + Eb.getMessage() );
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("In changePassword.java:    "+ ee.getMessage());
				ee.printStackTrace();
			}
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E006"));
		}
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
	{
		doGet(request, response);
	}

}


