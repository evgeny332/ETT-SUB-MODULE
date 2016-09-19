<!--<%@ page contentType="text/html;charset=windows-1252"%>
 <%@ page import="java.io.*" %>
 <%@ page import="javax.http.servlet.*" %>
 <%@ page import="javax.servlet.*" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>

-->

 <%   
	WorkerThread   worker   =(WorkerThread)   
	session.getAttribute("worker");   
	if(worker==null)   
	{   
		String userID   =   request.getParameter("User_Id");   
		String password   =   request.getParameter("Password");   
		worker  =   new   WorkerThread(userID,password);   
		session.setAttribute("worker",worker);   
	}   
	if(!worker.isDone())   
	{   
		String url = HttpUtils.getRequestURL(request).toString();   
		url = response.encodeURL(url);   
 %>   
  <html>   
  <head>   
  <title>   
  please   waitting.....</title>   
  <META   HTTP-EQUIV="REFRESH"   CONTENT="2";   URL="<%=url%>">   
  <body>   
  please   wait......   
  </body>   
  </html>   
  
  <%   
	}   
	else   
	{   
  %>   
  <html>   
  <head><title>   
  done!</title>   
  </head>   
  <body>   
  authentication   complete.   
  <%=worker.isAuthenticated()?   "you   pass!":"you   fail!"%>   
  </body>   
  </html>   

  <%   
    session=request.getSession(false);
	if(session != null)
	{
		session.invalidate(); 
		response.sendRedirect("default.jsp");
	}
  }   
  %>   
    
  <%!   
  public   class   WorkerThread   implements   Runnable   {   
    
  private   boolean   done;   
  private   boolean   authenticated;   
  private   Thread   kicker;   
    
  public   WorkerThread(String   userID,String   password)   {   
  done   =   false;   
  authenticated   =   false;   
  kicker   =   new   Thread(this);   
  kicker.start();   
    
  }   
    
  public   boolean   isDone()   {   
  return   done;   
  }   
    
  public   boolean   isAuthenticated()   {   
  return   authenticated;   
  }   
    
  public   void   run()   {   
  try   {   
  for(int   i=0;i<5;i++)   
  Thread.sleep(300);   
  done   =   true;   
  }   
  catch(InterruptedException   e)   {}   
  finally   {   
  kicker   =   null;   
  }   
  }   
  }   
  %> 

  <%
		//String sss=session.getAttribute("user");
		String sss=request.getParameter("Password");
        Cookie killMyCookie = new Cookie("Password", sss);
		killMyCookie.setMaxAge(0);
		killMyCookie.setPath("/");
		response.addCookie(killMyCookie);
		

	/*	private void deleteCookie(String cookieName)
		{
			Cookie[] cookies =request.getCookies();
			if (cookies != null)
			{   
				for (int i=0; i<cookies.length; i++)
				{
					if (cookies[i].getName().equals(cookieName));
					cookies[i].setMaxAge(0);
				}
			}
		}
	*/

%>




<%
	/*	session=request.getSession(false);
		if(session !=null)
		{
			session.invalidate();
			response.sendRedirect("default.jsp");
		}
		else
		{
			session=request.getSession(true);
		}
	*/
%>


 <%
		/*
		String login_user=(String)session.getAttribute("user");
			if(login_user !=null)
			{
				session=request.getSession();
				
				//out.println("pleae wait.......");
				response.sendRedirect("default.jsp");
				session.invalidate();
				 
			}
		*/		
			
 %>



<%

		/*String login_user=(String)session.getAttribute("user");
		if(login_user == null)
		{
			session.invalidate();
			response.sendRedirect("default.jsp");
		}
		*/
%>

  
