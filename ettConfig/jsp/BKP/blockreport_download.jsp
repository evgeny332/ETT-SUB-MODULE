<%@ page language="java" session="true" import="java.util.*,java.text.*,com.reports.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@taglib uri="/WEB-INF/displaytag-el.tld" prefix="display"%>
<%
String userName 	= "";
String JspPath 		= "";
String SystemDate	= "";
String year  		= "";
int 	month		= 0;
String Date		= "";
String Hour		= "";
String Min		= "";
String Sec		= "";
//int counter		= 0;
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
	Calendar cal 		= Calendar.getInstance();
	String DATE_FORMAT_NOW 	= "dd-MM-yyyy hh:mm:ss";
	SimpleDateFormat sdf 	= new SimpleDateFormat(DATE_FORMAT_NOW);
	month 			= cal.get(Calendar.MONTH);
	month			= month + 1;
	cal.add(Calendar.MONTH,-1);
	SystemDate 		= sdf.format(cal.getTime());
	year  			= ""+cal.get(Calendar.YEAR);
	Date 			= ""+cal.get(Calendar.DATE);
	Hour 			= ""+cal.get(Calendar.HOUR_OF_DAY);
	Min 			= ""+cal.get(Calendar.MINUTE);	
	Sec 			= ""+cal.get(Calendar.SECOND);
}catch(Exception e){
	System.out.println("[Exception][ Date/Time ][Calculate ]"+e.getMessage());
	response.sendRedirect(request.getContextPath()+"/jsp/redirectpage.jsp?ST=E003");
}

%>
<% request.setAttribute( "configDataList",(ArrayList)session.getAttribute("FileStatusList"));%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="include/displaytagex.css">
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
<script language="javascript" src ="JavaScript/cal_block.js"></script>
<script language="javascript" src ="JavaScript/validate2.js"></script>

<script language="javascript">
<!--
addCalendar("Date10", "cal1", "DateTime1", "BlockReport_downloadForm");
addCalendar("Date20", "cal2", "DateTime2", "BlockReport_downloadForm");
//-->
</script>

<script>

function Validate()
{
	var DateTime1 = document.BlockReport_downloadForm.DateTime1.value;
	if(DateTime1 == "")
	{
		alert("Date should not be blank.");
		return false;
	}
	if(!validDateTime()){
		return false;	
	}
	return true;
}
function validDateTime(){

	var dateTime    = new Array();
	dateTime1       = document.getElementById('DateTime1').value.split("-");
	var fromDate    = new Date(dateTime1[1]+"/"+dateTime1[2]+"/"+dateTime1[0]);
	var dateTime    = new Array();
	var currentDate = new Date( );
	if(fromDate > currentDate )
	{
		alert("Date should not be greater than current date.");
		return false;
	}
	return true;
}
function cancelAction()
{
	document.BlockReport_downloadForm.action="";
	window.location.href = "<%=response.encodeURL(request.getContextPath())%>/BlockReport_download";
}
</script>

<body background-color="#dddddd" >
<form action="<%=response.encodeURL(request.getContextPath())%>/BlockReport_download" name="BlockReport_downloadForm" >
<table width="100%" border="0" cellpadding="0" cellspacing="0">
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
<td width="40%" align="center" valign="top" scope="row" ><strong><label>Blocked Call Report Download</label><strong> &nbsp;</td>
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
<input type="hidden" name="requestType" value="DETAILED_REPORT" />
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Select Date</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="58%" align="left" valign="top">
<input name="DateTime1" type="text" id="DateTime1" size="12" value="" class ="contentblack" readonly="yes" >&nbsp;<a href="javascript:showCal('Date10')"><img src="images/cal.gif" width="19" height="16" border="0" alt="Pick a date"></a><span id="cal1" style="position: relative">&nbsp;</span>
</td>
</tr>

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
<input type="submit" name="submit" id="submit" value="Submit" onclick="javascript: return Validate();"/></label>&nbsp;&nbsp;&nbsp;<label><input type="button" name="Cancel" value="Cancel" onclick="cancelAction();"></Button>
</label>
</td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<%
        ArrayList data = (ArrayList)session.getAttribute("FileStatusList");
        int size = data.size();
        if(size > 0)
        {
%>
		<TABLE align="center" width="100%"><TR>Please click here to download reports.</TR></TABLE>
<%
                Iterator itr = data.iterator();
                while(itr.hasNext())
                {
                        String filename = (String)itr.next();
                        //String filepath = request.getContextPath()+"/reports/"+filename;
			String path = (String)itr.next();
                        String filepath = request.getContextPath()+path+filename;

%>
                        <table align ="center">
                        <TR>
			<td align="center" valign="top"><B><a href="<%=filepath%>"><%=filename%></a></B></td>
                        </TR>

<%
                }
        }
%>

</table>
</table>
<tr width="100%">
<td width="10%" height="26">&nbsp;</td>
<td width="76%" align="center" valign="top"><img src="images/blackberry_bottom.gif" width="100%"/></td>
<td width="14%">&nbsp;</td>
</td>
</tr>
</table>
</tr>
</form>
</body>
</html>

