<meta http-equiv="cache-control" content="no-cache"> <!-- tells browser not to cache -->
<meta http-equiv="expires" content="0"> <!-- says that the cache expires 'now' -->
<meta http-equiv="pragma" content="no-cache"> <!-- says not to use cached stuff, if there is any -->

<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
        String userName = "" ;
        String Msisdn = "";
        try
        {
                userName =(String)session.getAttribute("userName");
                if(userName == null)
                {
                        response.sendRedirect(request.getContextPath()+"/jsp/redirectpage.jsp?ST=E016");
                }
        }
        catch(Exception Exp)
        {
                Exp.printStackTrace();
        }
%>
<head>
<script language=javascript>
javascript:window.history.forward(1);
window.onbeforeunload = function (){}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<link href="include/cms.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
<!--
.style1 {font-family: Arial, Helvetica, sans-serif}
-->
</style>
</head>
<body>
<table width="100%" height ="100%" border="0" cellpadding="0" cellspacing="0" id="formatedTab">
  <tr height ="30%" >
    <th scope="row">&nbsp;</th>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr height ="40%">
    <th scope="row" height ="40%">&nbsp;</th>
    <td align="center"><h3><span class="style1">Welcome to Configuration management</b></span></h3></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <th scope="row" height ="30%">&nbsp;</th>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>

