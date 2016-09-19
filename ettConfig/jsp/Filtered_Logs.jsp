<%@ page language="java" session="true" import="java.util.*,java.text.*,com.reports.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.dndbean.Filtered_LogsBean" %>
<%@ page import="java.util.*" %>
<%@taglib uri="/WEB-INF/displaytag-el.tld" prefix="display"%>
<jsp:useBean id="Filtered_LogsBean" scope="application"
class="com.dndbean.Filtered_LogsBean"/>

<%--window.opener.location.href='Url' --%>
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
<script language="javascript" src ="JavaScript/cal_conf_fl.js"></script>
<script>
function Validate()
{
        var DateTime1 = document.Filtered_LogsForm.DateTime1.value;

        if(DateTime1 == "")
        {
                alert("From Date should not be blank !");
                return false;
        }
        var DateTime2 = document.Filtered_LogsForm.DateTime2.value;
        if(DateTime2 == "")
        {
                alert("To Date should not be blank !");
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
        dateTime2       = document.getElementById('DateTime2').value.split("-");
        var toDate      = new Date(dateTime2[1]+"/"+dateTime2[2]+"/"+dateTime2[0]);
        var currentDate = new Date( );

        if(fromDate > toDate)
        {
                alert("To Date should not be less than From Date !");
                return false;
        }
        else if(fromDate > currentDate )
        {
                alert("From Date should not be greater than Current Date !");
                return false;
        }
        else if(toDate > currentDate )
        {
                alert("To Date should not be greater than Current Date !");
                return false;
        }

        return true;
}
function cancelAction()
{
        document.Filtered_LogsForm.action="";
        location.reload(true);
}
</script>
<body background-color="#dddddd" >
<form action="<%=response.encodeURL(request.getContextPath())%>/Filtered_Logs" name="Filtered_LogsForm" >
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
<td width="40%" align="center" valign="top" scope="row" ><strong><label>Filtered Logs</label><strong> &nbsp;</td>
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
<td width="33%" align="right" valign="top" scope="row"><label><b>Circle</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<SELECT NAME="circle" id ="circle"  class ="contentblack">
<OPTION  VALUE="0">All
<OPTION  VALUE="1">Andhra Pradesh
<OPTION  VALUE="2">Assam
<OPTION  VALUE="3">Bihar
<OPTION  VALUE="4">Delhi
<OPTION  VALUE="5">Gujarat
<OPTION  VALUE="6">Haryana
<OPTION  VALUE="7">Himanchal Pradesh
<OPTION  VALUE="8">Jammu
<OPTION  VALUE="9">Karnataka
<OPTION  VALUE="10">Kerala
<OPTION  VALUE="11">Kolkata
<OPTION  VALUE="12">Madhya Pradesh
<OPTION  VALUE="13">Maharashtra
<OPTION  VALUE="14">Mumbai
<OPTION  VALUE="15">Orissa
<OPTION  VALUE="16">Punjab
<OPTION  VALUE="17">Tamil Nadu
<OPTION  VALUE="18">Uttar Pradesh(E)
<OPTION  VALUE="19">Uttar Pradesh(W)
<OPTION  VALUE="20">West Bengal
<OPTION  VALUE="21">North East
<OPTION  VALUE="22">Rajasthan
</SELECT>
</td>
</tr>

<tr>
<td width="33%" scope="row" align="right" valign="top" ><label></label>&nbsp;&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="58%" scope="row">&nbsp;</td>
</tr>
</table>
<table>
<tr>
<td width="20%" scope="row" align="right" valign="top" ><label></label>&nbsp;&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="58%" align="left" valign="top">
<label>
<input type="submit" name="submit" id="submit" value="Submit" onclick="javascript: return Validate();" />&nbsp;&nbsp;&nbsp; <input type="button" name="Cancel" value="Cancel" onclick="cancelAction();"></Button>
</label>
</td>
</tr>

<tr>
<td>&nbsp;</td>
</tr>

<%
ArrayList al = (ArrayList)request.getAttribute( "configDataList");
try{
              //out.println(al.size());
        if(al.size()== 0 ){
                //out.println("Record Not Found");
        }
        else
        {
                // out.println(al.size()+"and");
        }
}catch(Exception e){

}
%>
</td>
</tr>

<TABLE  border="0" style="width:580px;">
<tr><td align="center" ></td></tr>
<tr bgcolor="#F7F7F7" class="contentblack">
<td>
<DIV align="center" valign="bottom" style="posision: absolute; overflow:hidden;">
<%--  data table pagination ----------------------------------%>
<display:table name="requestScope.configDataList" pagesize="5" class="dataTable" export="true" id="currentRowObject" >
<display:column property="datetime" title="Date" media="csv excel html pdf " />
<display:column property="circle" title="TM Circle" media="csv excel html pdf " />
<display:column property="total" title="Total" media="csv excel html pdf " />
<display:column property="connected" title="Allowed" media="csv excel html pdf " />
<display:column property="disconnected" title="Blocked" media="csv excel html pdf " />
<display:setProperty name="paging.banner.placement" value="top" />
<display:setProperty name="paging.banner.placement" value="bottom" />
<display:setProperty name="export.amount" value="list" />
<display:setProperty name="export.pdf" value="true" />
<display:setProperty name="export.pdf.filename" value="conformance_report.pdf"/>
<display:setProperty name="export.excel.filename" value="conformance_report.xls"/>
<display:setProperty name="export.csv.filename" value="conformance_report.csv"/>
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


