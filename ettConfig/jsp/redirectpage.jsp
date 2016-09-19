<meta http-equiv="cache-control" content="no-cache"> <!-- tells browser not to cache -->
<meta http-equiv="expires" content="0"> <!-- says that the cache expires 'now' -->
<meta http-equiv="pragma" content="no-cache"> <!-- says not to use cached stuff, if there is any -->

<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*,java.util.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script language=javascript>
javascript:window.history.forward(1);
window.onbeforeunload = function (){}

</script>
<script langauge=javascript>
<%
    //	session=request.getSession(false);
	String ST = request.getParameter("ST");
	if(ST == null)
        {
                ST = "E160";
        }
	String Url = "Login.jsp?ST="+ST;
	Enumeration enum12 = session.getAttributeNames();
	while (enum12.hasMoreElements())
	{
        	String attribute = (String) enum12.nextElement();
	        System.out.println(attribute);
		session.removeAttribute(attribute);
        }
	if(session != null)
	{
		session.invalidate();
	}

%>
function redirect()
{
	parent.window.location.href="<%=Url%>";
}
</script>
<html>
<meta http-equiv="Content-Language" content="en-us">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="expires" CONTENT="0">
<head>
<link rel="stylesheet" type="text/css" href="../css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body background-color="#dddddd" onload="redirect()">
</body>
</html>
