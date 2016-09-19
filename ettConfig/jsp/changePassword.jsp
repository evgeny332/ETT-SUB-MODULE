<meta http-equiv="cache-control" content="no-cache"> <!-- tells browser not to cache -->
<meta http-equiv="expires" content="0"> <!-- says that the cache expires 'now' -->
<meta http-equiv="pragma" content="no-cache"> <!-- says not to use cached stuff, if there is any -->

<%@ page language="java" session="true" import="java.util.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%--window.opener.location.href='Url' --%>
<script language=javascript>
javascript:window.history.forward(1);
window.onbeforeunload = function (){}
</script>
<script language="javascript" type="text/javascript">
<%
String userName = "" ;
ArrayList UserList = null;
try
{
	userName =(String)session.getAttribute("userName");
	UserList =(ArrayList)session.getAttribute("UserListExist");
}	
catch(Exception Exp)
{
	Exp.printStackTrace();
	response.sendRedirect(request.getContextPath()+"/jsp/redirectpage.jsp?ST=E003");
} 
if(userName == null)
{
	response.sendRedirect(request.getContextPath()+"/jsp/redirectpage.jsp?ST=E003");
}

%>

</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="include/cms.css" rel="stylesheet" type="text/css"/>
<title>Earn Talk Time</title>
</head>
<script language="javascript" type="text/javascript" src="JavaScript/changePassword.js">
</script>
<body>
<form action="<%=response.encodeURL(request.getContextPath())%>/changePassword" method="get" enctype="multipart/form-data"		name="changePassword" id="changePassword">
<table width="100%" height="40%" border="0" cellpadding="0" cellspacing="0">
<tr width="100%">
<td width="10%" height="26">&nbsp;</td>
<td width="76%" align="center" valign="bottom"><img src="images/blackberry_top_bg_new.jpg" width="100%"/></td>
<td width="14%">&nbsp;</td>
</tr>
<tr>
<td>&nbsp;</td>
<td align="center" valign="bottom"><table width="100%" height ="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#F7F7F7" id="formatedTab">
<tr>
<th colspan="3" scope="row" align="center" >Change Password</th>
</tr>
<tr>
<th width="37%" scope="row">&nbsp;</th>
<td width="3%" scope="row">&nbsp;</td>
<td width="60%" scope="row">&nbsp;</td>
</tr>
<tr>
<th align="right" scope="row">User Name</th>
<td scope="row">&nbsp;</td>
<td scope="row"><label>
<input type="text" name="username" id="username" value="<%=userName%>" class ="contentblack" readonly/>
</label></td>
</tr>

<tr>
<th align="right" scope="row">Current Password</th>
<td scope="row">&nbsp;</td>
<td scope="row"><label>
<input type="password" name="cpassword" id="cpassword" maxlength="10" class ="contentblack">
</label></td>
</tr>

<tr>
<th align="right" scope="row">New Password</th>
<td scope="row">&nbsp;</td>
<td scope="row"><label>
<input type="password" name="password" id="password" maxlength="10" class ="contentblack">
</label></td>
</tr>

<tr>
<th align="right" scope="row">Confirm New Password</th>
<td scope="row">&nbsp;</td>
<td scope="row"><label>
<input type="password" name="password1" id="password1" maxlength="10" class ="contentblack">
</label></td>
</tr>

<tr>
<th scope="row">&nbsp;</th>
<td scope="row">&nbsp;</td>
<td scope="row">&nbsp;</td>
</tr>
<tr>
<th scope="row">&nbsp;</th>
<td scope="row">&nbsp;</td>
<td scope="row"><label><input type="submit" name="submit" id="submit" value="Submit" onclick="javascript: return Validate();">
</label></td>
</tr>
<!-- Write Your code Here-->

<tr>
<th colspan="3" scope="row" align="center">&nbsp;</th>
</tr>
</table></td>
<td>&nbsp;</td>
</tr>
<tr>
<td width="10%">&nbsp;</td>
<td width="57%" align="center" valign="top"><img src="images/blackberry_bottom.gif" width="100%"/></td>
<td width="33%">&nbsp;</td>
</tr>
</table>
</form>
</body>
</html>
