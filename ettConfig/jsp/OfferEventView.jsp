<%@ page language="java" session="true" import="java.util.*,java.text.*,com.reports.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.earntt.OfferEventDetails" %>
<%@ page import="java.util.*" %>
<%@taglib uri="/WEB-INF/displaytag-el.tld" prefix="display"%>
<jsp:useBean id="OfferEventDetails" scope="application" class="com.earntt.OfferEventDetails"/>

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
<title>Config Manager</title>
</head>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script language=javascript>
var ServerDateTime = "<%=SystemDate%>";
javascript:window.history.forward(1);
window.onbeforeunload = function (){}
function getOferId()
{
	var id=$("#offerId").val();
	//alert(id);
	if(id == "" || id=='Offer_Type')
	{
		alert("Please Select/Enter Offer ID");
		return false;
	}
	else{
		window.location.href="<%=response.encodeURL(request.getContextPath())%>/ViewOfferEvent?offerId="+id+"";
	}
}
function addEvent()
{
	var id=$("#offerId").val();
	if(id == "" || id=='Offer_Type')
        {
                alert("Please Select/Enter Offer ID");
                return false;
        }

	window.location.href="<%=response.encodeURL(request.getContextPath())%>/AddOfferEvent?EventofferId="+id+"";
}
function deleteEvent(id)
{
	
	 window.location.href="<%=response.encodeURL(request.getContextPath())%>/DeleteEvent?offer_id="+id+"";
}
function refresh()
{
	window.location.href="<%=response.encodeURL(request.getContextPath())%>/ViewOfferNew";
	
}
function Edit(id)
{
        window.location.href="<%=response.encodeURL(request.getContextPath())%>/EditEvent?offer_id="+id+"";
}

</script>

<%--<script language="javascript" type="text/javascript" src="JavaScript/datetimepicker.js">
</script>--%>
<script language="javascript" src ="JavaScript/cal.js"></script>
<script language="javascript" src ="JavaScript/cal_conf_sreport.js"></script>
<body background-color="#dddddd" >
<%--<form action="<%=response.encodeURL(request.getContextPath())%>/EarntTT_CDR" name="SMS_ReportForm" >--%>
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
				<td width="40%" align="center" valign="top" scope="row" ><strong><label>Offer Event Details</label><strong> &nbsp;</td>
			</tr>

	</table>			




</td>
<td width="14%">&nbsp;</td>
<tr>
<td width="10%" >&nbsp;</td>
<td width="76%" align="center" valign="bottom">
<TABLE  border="0" style="width:580px;">
<tr><td align="center" ></td></tr>
<tr bgcolor="#F7F7F7" >
<td>
<div>
<%
	ArrayList list=(ArrayList)session.getAttribute("listEventId");
%>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
<%
	if(list.size()==0)
	{ 
%>
	<td valign="top" scope="row"><label><b>Offer Id</label>&nbsp;<input name="offerId" type="text" id="offerId" value="" class ="contentblack"  />&nbsp;&nbsp;<label> 
<%} else {
%>
	<td valign="top" scope="row"><label><b>Offer Id</label>&nbsp;
	<select name="offerId" id="offerId"  class ="contentblack" style="height:25px; width:175px">
        <option value="Offer_Type">select offer id </option>
	<%
		for(Object str:list)
		{
	%>
			<option value="<%=str.toString()%>"><%=str.toString()%></option>
	<%
		}
	%>
	</select>

	<%--<input name="offerId" type="text" id="offerId" value="" class ="contentblack"  />--%>
	&nbsp;&nbsp;<label>
<%
	}
%>
<input type="submit" name="submit" id="submitO" value="Search Event" onclick=" getOferId();" style="width: 108px"/> </label> &nbsp;&nbsp;<label>
<input type="submit" name="submit" id="submitAddE" value="Add Event" onclick="addEvent();" style="width: 108px"/> </label></td>

</td>
</tr>


</table>


</div>
<DIV align="center" valign="bottom" style="posision: absolute; overflow:hidden;">
<%--  data table pagination ----------------------------------%>
<display:table name="requestScope.configDataList" pagesize="20" class="dataTable" export="true" id="currentRowObject" sort="list">
<display:column title="Action">
<a href="#" onclick="Edit('${currentRowObject.id}')" style="color:#4477AA">Edit</a>&nbsp;|&nbsp;<a href="#" onclick="deleteEvent('${currentRowObject.id}')" style="color:#4477AA">Delete</a>
<%--&nbsp;&nbsp;<a href="#" onclick="Edit('${currentRowObject.id}')" style="color:#4477AA">Edit</a>&nbsp;&nbsp;--%>
</display:column>


<display:column property="offerId" title="Offer Id" media="csv excel html" />
<display:column property="appKey" title="App Key" media="csv excel html" />
<display:column property="balanceCreditInDays" title="Bal.Credit In Days" media="csv excel html " />
<display:column property="currency" title="Currency" media="csv excel html " />
<display:column property="eventName" title="Event Name" media="csv excel html" />
<display:column property="eventValue" title="Event Value" media="csv excel html " />
<display:column property="incomeAmount" title="Income Amount" media="csv excel html " />
<display:column property="installedAmount" title="Installed Amount" media="csv excel html " />
<display:column property="offerAmount" title="Offer Amount" media="csv excel html " />
<display:column property="status" title="Status" media="csv excel html " />


<%--
<display:column title="Action">
<a href="#" onclick="Edit('${currentRowObject.offerId}')" style="color:#4477AA">Edit</a>&nbsp;|&nbsp;<a href="#" onclick="deleet('${currentRowObject.offerId}')" style="color:#4477AA">Delete</a>
</display:column>--%>
<display:setProperty name="paging.banner.placement" value="top" />
<display:setProperty name="paging.banner.placement" value="bottom" />
<display:setProperty name="export.amount" value="list" />
<display:setProperty name="export.pdf" value="false" />
<display:setProperty name="export.excel.filename" value="OfferEventReport.xls"/>
<display:setProperty name="export.csv.filename" value="OfferEventReport.csv"/>
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
<%--</form>--%>
</body>
</html>
