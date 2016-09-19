import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
public  class ModifyTemplate extends HttpServlet
{
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws  IOException
	{
		HttpSession Session=request.getSession(true);
		String Application_Username	= "";
		String Application_Password	= "";
		String LoginType		= "";
		Statement selectStmt		= null;
		Connection activeConnection 	= null;
		String newTemplateName		= "";
		String oldTemplateName		= "";
		String Message			= "";
		try
		{
			Application_Username= (String)Session.getAttribute("userName");
			if(Application_Username == null)
			{
				response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
				return ;
			}
			newTemplateName = request.getParameter("newTemplateName");
			oldTemplateName = request.getParameter("OldTemplateName");
			Message = request.getParameter("message");
			System.out.println("newTemplateName"+newTemplateName);
			System.out.println("oldTemplateName"+oldTemplateName);
			System.out.println("Message"+Message);
			try
			{		
				activeConnection  = OracleConnectionUtil.getConnection();
			}
			catch(Exception od)
			{
				System.out.println("In ModifyTemplate.java:   Not able to connect with ORACLE");
				response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=OD"));
				od.printStackTrace();
			}
			System.out.println("In ModifyTemplate.java:    ORA Connection Created!");
			try
			{
				selectStmt = activeConnection.createStatement();
				String Query = "UPDATE CM_USER_MESSAGE_TEMPLATE SET TEMPLATEID ='"+newTemplateName+"' , TEMPLATEVALUE ='"+Message+"' WHERE TEMPLATEID ='"+oldTemplateName+"' AND USERNAME ='"+Application_Username+"'";
				System.out.println("Query for update in modify template"+Query);
				selectStmt.executeUpdate(Query);
				selectStmt.close();
				String TemplateQuery  = "SELECT TEMPLATEID , TEMPLATEVALUE FROM CM_USER_MESSAGE_TEMPLATE WHERE USERNAME ='"+Application_Username+"'" ;
				System.out.println("TemplateQuery in modify template"+TemplateQuery);
				Statement selectStmtTemplate = activeConnection.createStatement();
				selectStmtTemplate = activeConnection.createStatement();
				ResultSet TemplateResultSet = selectStmtTemplate.executeQuery(TemplateQuery);
				ArrayList TemplateList = new ArrayList ();
				while(TemplateResultSet.next())
				{
					String Template[] = new String [2];
					Template[0] = TemplateResultSet.getString("TEMPLATEID") ; 
					Template[1] = TemplateResultSet.getString("TEMPLATEVALUE");
					System.out.println("USER ["+Application_Username+"] Template Id ["+Template[0]+"] Template Value ["+Template[1]+"]");
					TemplateList.add(Template); 
				}
				TemplateResultSet.close();
				selectStmtTemplate.close();
				Session.setAttribute("TemplateList", TemplateList);
				activeConnection.close();
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E001&redirect=welcome.jsp"));
			}
			catch(SQLException ESQL)
			{
				System.out.println("In ModifyTemplate.java:    SQL Error" + ESQL.getMessage());
			}
			finally
			{
				try
				{
					activeConnection.close();
				}
				catch(Exception ee)
				{
					System.out.println("In ModifyTemplate.java:    Exception: " +ee.getMessage());
					ee.printStackTrace();
				}
			}
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("In ModifyTemplate.java:    SQLState:  " + Eb.getMessage() );
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("In ModifyTemplate.java:    "+ ee.getMessage());
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


