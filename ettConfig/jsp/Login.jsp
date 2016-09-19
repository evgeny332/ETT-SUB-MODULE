
<%@ page language="java" import="java.util.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%--window.opener.location.href='Url' --%>
<script language="javascript" type="text/javascript">
</script>
<%

	String msg= "";
	msg=request.getParameter("ST");
	if(msg == null || msg.equals("null"))
		msg = "E099";
	String JspPath =request.getContextPath()+"/jsp/" ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0"> 
<meta http-equiv="pragma" content="no-cache"> 
<link href="<%=JspPath%>include/cms.css" rel="stylesheet" type="text/css"/>
<link href="<%=JspPath%>images/Url-logo.png" rel="icon" type="text/css"/>

<title>Configuration Management</title>
</head>
<script language="javascript" type="text/javascript" src="<%=JspPath%>JavaScript/Message.js">
</script>
<script language="javascript" type="text/javascript">
function doSubmit()
{
	if(document.Login.User_Id.value=="")
	{
		alert("Kindly enter the user id ");
		document.Login.User_Id.focus();	
		return false;
	}
	else if(document.Login.Password.value=="")
	{
		alert("Kindly enter the password");
		document.Login.Password.focus();	
                return false;
	}
}
</script>
<body onLoad="show_msg('<%=msg%>','')">
<form action="<%=request.getContextPath()%>/Login" method="post" name="Login" id="Login" >
   <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
	   <tr width="100%" height="35%">

	      <td width="35%" height="26">&nbsp;</td>
		  <td align="center" valign="bottom"><img src="<%=JspPath%>images/blackberry_top_bg_new.jpg" width="100%"/></td>
	      <td width="34%">&nbsp;</td>
	 </tr>

	 <tr>
	   <td>&nbsp;</td>

	      <td align="center" valign="bottom"><table width="100%" height ="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#F7F7F7" id="formatedTab">
            <tr>
              <th colspan="3" scope="row" align="center" ><label style="padding-left: 0px; " NAME="msg_board" ROWS="5" COLS="70" id="msg_board" style="color: #800000"></label></th>
            </tr>
            <tr>
              <th width="34%" scope="row">&nbsp;</th>
              <td width="6%" scope="row">&nbsp;</td>
              <td width="60%" scope="row">&nbsp;</td>
            </tr>
            <!-- Write Your code Here-->
	    <tr>
              <th width="34%" scope="row" align="right">User Name</th>
              <td width="6%" scope="row">&nbsp;</td>
              <td width="60%" scope="row">
              <input id= "User_Id"  name="User_Id" maxlength="15"  type="text"  class ="contentblack1"/>
		</td>
            </tr>
		
	<tr>
              <th width="34%" scope="row" align="right">Password</th>
              <td width="6%" scope="row">&nbsp;</td>
              <td width="60%" scope="row">

              <input name="Password" id="Password"  maxlength="15" type="password" class ="contentblack1" /></td>
	</tr>
	<tr>
              <td colspan="3" width="60%" align="center" scope="row">&nbsp;</td>
	</tr>

	<tr>
              <td colspan="3" width="60%" align="center" scope="row"><input type="submit" value="Submit" onsubmit="return doSubmit();"/></td>
	</tr>

           <tr>
              <th colspan="3" scope="row" align="center">&nbsp;</th>
            </tr>
          </table></td>
	      <td>&nbsp;</td>
	 </tr>
    <tr height="35%">
      <td width="35%%">&nbsp;</td>
      <td width="" align="center" valign="top"><img src="<%=JspPath%>images/blackberry_bottom.gif" width="100%"/></td>
      <td width="33%">&nbsp;</td>
    </tr>
  </table>
</form>
</body>
</html>
