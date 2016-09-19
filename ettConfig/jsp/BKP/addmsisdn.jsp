<%@ page language="java" session="true" contentType="text/html;charset=UTF-8"%>
<%@ page import="java.text.* "%>
<%@page import="java.util.*, com.utility.*"%>
<%@taglib uri="/WEB-INF/displaytag-el.tld" prefix="display"%>
<%@ page import="java.sql.*,java.text.*" %>

<%@ page language="java" contentType="text/html;charset=UTF-8"%>


<%@ page import="com.dndbean.CircleSummaryBean" %>
<jsp:useBean id="CircleBean" scope="request" class="com.dndbean.CircleSummaryBean"/>


<%

String label    = "";
/*DateFormat df = new SimpleDateFormat("YYYY-MM-DD");
  String formattedDate = df.format(new java.util.Date());*/
String showDataFlag  = "false";
String msisdn        = null;
String bool	     = "false";
try
{
	showDataFlag = (String)request.getParameter( "showDataFlag" ).trim();
	//	out.println(" showDataFlag in 1"+showDataFlag);

}
catch(Exception e)
{
	showDataFlag = "false";
	label = "";
}


try
{
	msisdn = (String)request.getParameter("msisdn").trim();
}catch(Exception e){
	msisdn= "";
	label = "";
}
if ( showDataFlag.equals( "true" ) ){
	com.mcarbon.AddMsisdn am = new com.mcarbon.AddMsisdn();
	request.setAttribute("bool", am.CheckMsisdn(msisdn));
//	out.println(request.getAttribute("bool"));
}
%>




<%
String userName         = "";
String JspPath          = "";
String SystemDate       = "";
String year             = "";
int     month           = 0;
String Date             = "";
String Hour             = "";
String Min              = "";
String Sec              = "";
try
{
	userName =(String)session.getAttribute("userName");
	if(userName == null)
	{
		response.sendRedirect(request.getContextPath()+"/jsp/redirectpage.jsp?ST=E016");
	}
	JspPath =request.getContextPath()+"/jsp/";
}
catch(Exception exp)
{
	System.out.println("[Exception][SESSION ][Expire ]"+exp.getMessage());
	response.sendRedirect(request.getContextPath()+"/jsp/redirectpage.jsp?ST=E003");
}
try{
	Calendar cal            = Calendar.getInstance();
	String DATE_FORMAT_NOW  = "dd-MM-yyyy hh:mm:ss";
	SimpleDateFormat sdf    = new SimpleDateFormat(DATE_FORMAT_NOW);
	month                   = cal.get(Calendar.MONTH);
	month                   = month + 1;
	cal.add(Calendar.MONTH,-1);
	SystemDate              = sdf.format(cal.getTime());
	year                    = ""+cal.get(Calendar.YEAR);
	Date                    = ""+cal.get(Calendar.DATE);
	Hour                    = ""+cal.get(Calendar.HOUR_OF_DAY);
	Min                     = ""+cal.get(Calendar.MINUTE);
	Sec                     = ""+cal.get(Calendar.SECOND);
}catch(Exception e){
	System.out.println("[Exception][ Date/Time ][Calculate ]"+e.getMessage());
	response.sendRedirect(request.getContextPath()+"/jsp/redirectpage.jsp?ST=E003");
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="<%=JspPath%>include/cms.css" rel="stylesheet" type="text/css"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="pragma" content="no-cache"/>
<title>Vodafone DNC</title>
</head>
<script language=javascript>
var ServerDateTime = "<%=SystemDate%>";
javascript:window.history.forward(1);
window.onbeforeunload = function (){}
</script>

<%--<script language="javascript" type="text/javascript" src="JavaScript/datetimepicker.js">
</script>--%>
<script language="javascript" src ="JavaScript/cal.js"></script>
<script language="javascript" src ="JavaScript/cal_conf2.js"></script>
<script language="javascript" src ="JavaScript/validate2.js"></script>
<script>
function Validate()
{
	var msisdn = document.addmsisdn.msisdn.value;

	if(msisdn == "")
	{
		alert("MSISDN should not be blank.");
		return false;
	}
	var msisdn = document.getElementById("msisdn").value;
	if(msisdn.length < 10 )
	{
		alert("MSISDN should be 10 digit.");
		return false;
	}	
	if(!validDateTime()){
		return false;
	}
	return true;
}


function validDateTime()
{
	document.addmsisdn.showDataFlag.value = "true";
	document.addmsisdn.submit();
}
function cancelAction()
{
	document.addmsisdn.action="";
	window.location.href = "addmsisdn.jsp";
}
</script>
<body background-color="#dddddd" >
<form action="addmsisdn.jsp" name="addmsisdn" method="POST" >
<table width="100%" height="60%" border="0" cellpadding="0" cellspacing="0">
<tr width="100%">
<td width="10%" height="26">&nbsp;</td>
<td width="56%" align="center" valign="bottom"><img src="images/blackberry_top_bg_new.jpg" width="100%"/></td>
<td width="14%">&nbsp;</td>
</tr>
<tr>
<td width="10%" >&nbsp;</td>
<td width="76%" align="center" valign="bottom">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0"  bgcolor="#F7F7F7" id="formatedTab">
<tr>
<td width="10%" >&nbsp;</td>
<td width="76%" align="center" valign="bottom">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
<td width="40%" align="center" valign="top" scope="row" ><strong><label>Add MSISDN</label><strong> &nbsp;</td>
</tr>

</table>
</td>
<td width="14%">&nbsp;</td>
</tr>
<tr>
<td width="10%" >&nbsp;</td>
<td width="76%" align="center" valign="bottom">&nbsp;&nbsp;</td>
<td width="14%">&nbsp;</td>
</tr>
<tr>
<td width="10%" >&nbsp;</td>
<td width="76%" align="center" valign="bottom">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>MSISDN</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="msisdn" type="text" id="msisdn" size="10" value="" maxlength ="10" class ="contentblack" onblur="extractNumber(this,0,false);"
onkeyup="extractNumber(this,0,false);"
onkeypress="return blockNonNumbers(this, event, false , false);"/>
</td>
</tr>
<input type="hidden" name="bool" value=""/>
<input type="hidden" name="showDataFlag" value=""/>
<tr>
<td width="33%" scope="row" align="right" valign="top" ><label></label>&nbsp;&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="58%" scope="row">&nbsp;</td>
</tr>
</table>
<table width="100%">
<tr>
<td width="33%" scope="row" align="right" valign="top" ><label></label>&nbsp;&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="65%" align="left" valign="top">
<label>
<input type="submit" name="submit" id="submit" value="submit" onclick="Validate();"/></label>&nbsp;&nbsp;&nbsp;<label><input type="button" name="Cancel" value="Cancel" onclick="cancelAction();"/></label>
</td>
</tr>
<tr>
<td width="14%">&nbsp;</td>
</tr>
<%
	if ( showDataFlag.equals("true") ) {
	String b = (String)request.getAttribute( "bool");
	if(b.equals("true"))
	{
%>
<table>
<tr>
<td width="58%" align="center" valign="middle"><label class ="contentblack">Your request will be processed in next two hours.</label>
</td>
<%
}


       else if ( b.equals("false")  ) {
%>
<table>
<tr>
<td width="58%" align="center" valign="middle"><label class ="contentblack">MSISDN already exist in database.</label>
</td>
<%
}
}
%>


</tr>
</table>
</table>
<tr width="100%">
<td width="10%" height="26">&nbsp;</td>
<td width="76%" align="center" valign="top"><img src="images/blackberry_bottom.gif" width="100%"/></td>
<td width="14%">&nbsp;</td>
</td>
</tr>
</table>
</table>
</tr>
</form>
</body>
</html>
