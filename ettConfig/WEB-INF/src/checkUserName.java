import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;
import java.sql.*;
public  class checkUserName extends HttpServlet
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
		String newUser			= "";
		int isExist = 0 ; 
		try
		{
			newUser = request.getParameter("username");
			System.out.println("In checkUserName.java:    ORA Connection Created!");
			try
			{
				activeConnection  = OracleConnectionUtil.getConnection();
				selectStmt = activeConnection.createStatement();
				String SelectQuery  = "SELECT COUNT(*) FROM CM_USER_LOGIN_DETAILS WHERE USERNAME ='"+newUser+"'";
				System.out.println("checkUserName :: ["+SelectQuery+"]");
	
				ResultSet RsGetData = selectStmt.executeQuery(SelectQuery);
				if(RsGetData.next())
					isExist = RsGetData.getInt(1);	
	
				selectStmt.close();
				activeConnection.close();
				System.out.println("checkUserName :: isExist["+isExist+"]");
				response.setContentType("html/text");
	                        response.getWriter().write(isExist);
			}
			catch(SQLException ESQL)
			{
				ESQL.printStackTrace();
				System.out.println("In checkUserName.java:    SQL Error" + ESQL.getMessage());
			}
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("In checkUserName.java:    SQLState:  " + Eb.getMessage() );
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("In checkUserName.java:    "+ ee.getMessage());
				ee.printStackTrace();
			}
		}
		finally
			{
				try
				{
					activeConnection.close();
				}
				catch(Exception ee)
				{
					System.out.println("In checkUserName.java:    Exception: " +ee.getMessage());
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

}


