<meta http-equiv="cache-control" content="no-cache"> <!-- tells browser not to cache -->
<meta http-equiv="expires" content="0"> <!-- says that the cache expires 'now' -->
<meta http-equiv="pragma" content="no-cache"> <!-- says not to use cached stuff, if there is any -->

<%@ page language="java" session="true" import="java.io.*, java.util.*, java.sql.*,oracle.jdbc.driver.*" contentType="text/html;charset=UTF-8"%>
<html>
<script language=javascript>
javascript:window.history.forward(1);
window.onbeforeunload = function (){}
</script>
<link href="images/Url-logo.png" rel="icon" type="text/css"/>
<head>
<title>Configuration Management</title>
</head>
<%
	String branding=""; 
	ArrayList userAuthList=null;
	try
	{
		branding = (String)session.getAttribute("Branding");
		userAuthList=(ArrayList)session.getAttribute("UserAuthList");
	}
	catch(Exception e)
	{
		e.printStackTrace();
		response.sendRedirect(request.getContextPath()+"/jsp/redirectpage.jsp?ST=E003");
	}
%>
<frameset  rows="14%,*" border="no" >
<frame id="frame1"  NORESIZE src="<%=branding%>" name="branding" scrolling="no" frameborder="0">
<frameset   cols="18%,*" border="no">
<frame id="frame2"  NORESIZE src="Leftband.jsp" name="leftband" frameborder="0">
<frame id="frame3" NORESIZE src="welcome.jsp"  name="mainArea" frameborder="0" align="top">
</frameset>
</frameset>
</html>
