import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;
import java.sql.*;
public  class CreateGroup extends HttpServlet
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
		String groupName		= "";
		String description			= "";
		try
		{
			Application_Username= (String)Session.getAttribute("userName");
			if(Application_Username == null)
			{
				response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
				return ;
			}
			groupName = request.getParameter("groupName");
			description = request.getParameter("description");
			System.out.println("groupName ["+groupName+"] description["+description+"]");
			try
			{		
				activeConnection  = OracleConnectionUtil.getConnection();
			}
			catch(Exception od)
			{
				System.out.println("In CreateGroup.java:   Not able to connect with ORACLE");
				response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=OD"));
				od.printStackTrace();
			}
			System.out.println("In CreateGroup.java:    ORA Connection Created!");
			try
			{
				selectStmt = activeConnection.createStatement();
				String inserQuery = "INSERT INTO CM_GROUPS_DETAIL (USERNAME , GROUPNAME , DESCRIPTION , TOTALMEMBER ,CREATION_DATE ) VALUES ('"+Application_Username+"','"+groupName+"','"+description+"','0','"+GetDate()+"')";
				System.out.println(inserQuery);
				selectStmt.executeUpdate(inserQuery);
				selectStmt.close();
				String GroupQuery  = "SELECT GROUPNAME , DESCRIPTION ,TOTALMEMBER , CREATION_DATE FROM CM_GROUPS_DETAIL WHERE USERNAME ='"+Application_Username+"'" ;
				System.out.println(GroupQuery);
				Statement selectStmtGroup = activeConnection.createStatement();
				selectStmtGroup = activeConnection.createStatement();
				ResultSet GroupResultSet = selectStmtGroup.executeQuery(GroupQuery);
				ArrayList GroupList = new ArrayList ();
				while(GroupResultSet.next())
				{
					String Group[] = new String [4];
					Group[0] = GroupResultSet.getString("GROUPNAME") ; 
					Group[1] = GroupResultSet.getString("TOTALMEMBER");
					Group[2] = GroupResultSet.getString("CREATION_DATE");
					Group[3] = GroupResultSet.getString("DESCRIPTION");
					System.out.println("USER ["+Application_Username+"] Group Id ["+Group[0]+"] Total Member ["+Group[1]+"] Date Of Creation ["+Group[2]+"]");
					GroupList.add(Group); 
				}
				GroupResultSet.close();
				selectStmtGroup.close();
				Session.setAttribute("GroupList", GroupList);
				activeConnection.close();
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E004&redirect=CreateGroup.jsp&camp="+groupName));
			}
			catch(SQLException ESQL)
			{
				ESQL.printStackTrace();
				System.out.println("In CreateGroup.java:    SQL Error" + ESQL.getMessage());
			}
			finally
			{
				try
				{
					activeConnection.close();
				}
				catch(Exception ee)
				{
					System.out.println("In CreateGroup.java:    Exception: " +ee.getMessage());
					ee.printStackTrace();
				}
			}
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("In CreateGroup.java:    SQLState:  " + Eb.getMessage() );
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("In CreateGroup.java:    "+ ee.getMessage());
				ee.printStackTrace();
			}
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E006"));
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

}


