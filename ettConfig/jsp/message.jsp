<meta http-equiv="cache-control" content="no-cache"> <!-- tells browser not to cache -->
<meta http-equiv="expires" content="0"> <!-- says that the cache expires 'now' -->
<meta http-equiv="pragma" content="no-cache"> <!-- says not to use cached stuff, if there is any -->

<%@ page language="java" session="true" import="java.util.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
String msg= "";
String Base= "";
String campid="";
try
{
	msg=request.getParameter("ST");	
	Base=request.getParameter("redirect");
	
	if(msg.equals("E100"))
        {
                Base="changePassword.jsp";

        }
	if(Base == null || Base.equals(""))
		Base="welcome.jsp";	
	campid = request.getParameter("camp");	
	System.out.println("Base MSG ["+msg+"] Base ["+Base+"] Camp ["+campid+"]");
	//response.sendRedirect(request.getContextPath()+"/jsp/redirectpage.jsp?ST=E003");
	
	if(campid ==null)
		campid ="";
}
catch(Exception exp)
{
	response.sendRedirect(request.getContextPath()+"/jsp/redirectpage.jsp?ST=E003");
	exp.printStackTrace();
}
%>
<script language=javascript>
javascript:window.history.forward(1);
window.onbeforeunload = function (){}
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="include/cms.css" rel="stylesheet" type="text/css"/>
<title>SpeedSMS - Message</title>
</head>
<script language="javascript" type="text/javascript">

</script>

<script language="javascript" type="text/javascript" src="JavaScript/Message.js">
</script>
<body onload="show_msg('<%=msg%>','<%=campid%>')">
<form name="MessagePad" action="<%=response.encodeURL(request.getContextPath())%>/jsp/<%=Base%>" id="MessagePad">
  <table width="100%" height="291" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <th width="11%" scope="row">&nbsp;</th>
      <td width="80%">&nbsp;</td>
      <td width="9%">&nbsp;</td>
    </tr>
    <tr>
      <th height="59" scope="row">&nbsp;</th>
      <td align="center">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" id="formatedTab">
        <tr>
          <th scope="row" align="center"><label style="padding-left: 0px; " NAME="msg_board" ROWS="5" COLS="70" id="msg_board" style="color: 
		  
		  #800000"></label></th>
        </tr>
<tr>
          <th width="100%" scope="row" align="center">&nbsp;</th>
       </tr>


        <tr>
          <th width="100%" scope="row" align="center"><label>
            <input type="submit" name="Ok" id="Ok" value=" Done " />
          </label></th>
       </tr>
      </table></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <th scope="row">&nbsp;</th>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
  </table>
</form>
</body>
</html>
