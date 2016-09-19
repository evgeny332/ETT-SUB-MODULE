<%@ page language="java" session="true" import="java.util.*,java.text.*,com.reports.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.earntt.EarnTTCDR" %>
<%@ page import="java.util.*" %>
<%@taglib uri="/WEB-INF/displaytag-el.tld" prefix="display"%>
<jsp:useBean id="EarnTTCDR" scope="application"
class="com.earntt.EarnTTCDR"/>

<%--window.opener.location.href='Url' --%>
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
//int    counter          = 0; 
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
<title>Earn Talk Time Report</title>
</head>
<script language=javascript>
var ServerDateTime = "<%=SystemDate%>";
javascript:window.history.forward(1);
window.onbeforeunload = function (){}
</script>

<%--<script language="javascript" type="text/javascript" src="JavaScript/datetimepicker.js">
</script>--%>
<script language="javascript" src ="JavaScript/cal.js"></script>
<script language="javascript" src ="JavaScript/cal_conf_sreport.js"></script>
	<script>
function Validate()
{
	var DateTime1 = document.SMS_ReportForm.DateTime1.value;
	var DateTime2 = document.SMS_ReportForm.DateTime2.value;

	if(DateTime1 == "")
	{
		alert("From Date should not be blank.");
		return false;
	}
	if(DateTime2 == "")
        {
                alert("To Date should not be blank.");
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
	dateTime2	= document.getElementById('DateTime2').value.split("-");
	var fromDate    = new Date(dateTime1[1]+"/"+dateTime1[2]+"/"+dateTime1[0]);
	var toDate      = new Date(dateTime2[1]+"/"+dateTime2[2]+"/"+dateTime2[0]);
	var currentDate = new Date( );
	if(fromDate > currentDate ){
		alert("From Date should not be greater than current date.");
		return false;
	}
	else if(toDate > currentDate ){
                alert("To Date should not be greater than current date.");
                return false;
        }
	else if(fromDate > toDate)
        {
                alert("To Date should not be less than From Date.");
                return false;
        }
	return true;
}
function cancelAction()
{
	document.SMS_ReportForm.action="";
	window.location.href = "<%=response.encodeURL(request.getContextPath())%>/EarntTT_CDR";
}
</script>
<body background-color="#dddddd" >
<form action="<%=response.encodeURL(request.getContextPath())%>/EarntTT_CDR" name="SMS_ReportForm" >
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
<td width="40%" align="center" valign="top" scope="row" ><strong><label>Earn Talk Time Report</label><strong> &nbsp;</td>
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
<td width="33%" align="right" valign="top" scope="row"><label><b>From Date</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="DateTime1" type="text" id="DateTime1" size="12" value="" class ="contentblack" readonly="yes" onfocus="this.blur(); showCal('Date10');"/>&nbsp;<a href="javascript:showCal('Date10')"><img src="images/cal.gif" width="19" height="16" border="0" alt="Pick a date"></a><span id="cal1" style="position: relative">&nbsp;</span>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>To Date</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="DateTime2" type="text" id="DateTime2" size="12" value="" class ="contentblack" readonly="yes" onfocus="this.blur(); showCal('Date20');"/>&nbsp;<a href="javascript:showCal('Date20')"><img src="images/cal.gif" width="19" height="16" border="0" alt="Pick a date"></a><span id="cal2" style="position: relative">&nbsp;</span>
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

</table>

<TABLE  border="0" style="width:580px;">
<tr><td align="center" ></td></tr>
<tr bgcolor="#F7F7F7" >
<td>
<DIV align="center" valign="bottom" style="posision: absolute; overflow:hidden;">
<%--  data table pagination ----------------------------------%>
<display:table name="requestScope.configDataList" pagesize="20" class="dataTable" export="true" id="currentRowObject" >
<display:column property="date_time" title="Date" media="csv excel html" />
<display:column property="report_id" title="Report ID" media="csv excel html" />
<display:column property="tid" title="TID" media="csv excel html " />
<display:column property="msisdn" title="MSISDN" media="csv excel html " />
<display:column property="emailid" title="Email ID" media="csv excel html" />
<display:column property="os_type" title="OP Type" media="csv excel html" />
<display:column property="type" title="Type" media="csv excel html " />
<display:column property="status" title="Status" media="csv excel html " />
<display:column property="status_desc" title="Status Description" media="csv excel html " />
<display:setProperty name="paging.banner.placement" value="top" />
<display:setProperty name="paging.banner.placement" value="bottom" />
<display:setProperty name="export.amount" value="list" />
<display:setProperty name="export.pdf" value="false" />
<display:setProperty name="export.excel.filename" value="EttReport.xls"/>
<display:setProperty name="export.csv.filename" value="EttReport.csv"/>
<display:setProperty name="export.excel.include_header" value="true" />
<display:setProperty name="paging.banner.group_size" value="8" />
<display:setProperty name="paging.banner.item_name" value="" />
<display:setProperty name="paging.banner.items_name" value="" />
<display:setProperty name="paging.banner.some_items_found">
<%--
<![CDATA[
--%>
<%--i
]]>
--%>
</display:setProperty>
<display:setProperty name="paging.banner.full">
<%--
<![CDATA[
--%>
<%-- <span class="pagelinks"> --%>
<span>
[<a href="{1}">First</a>/<a href="{2}">Prev</a>]
{0}
[<a href="{3}">Next</a>/<a href="{4}">Last</a>]
</span>
<%--
]]>
--%>
</display:setProperty>
</display:table>


</DIV>
</td>
</tr>


</TABLE>
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
