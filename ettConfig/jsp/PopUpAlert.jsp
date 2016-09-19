<%@ page language="java" session="true" import="java.util.*,java.text.*,com.reports.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.earntt.PopUpAlertBean" %>
<%@ page import="java.util.*" %>
<%@taglib uri="/WEB-INF/displaytag-el.tld" prefix="display"%>
<jsp:useBean id="PopUpAlertBean" scope="application" class="com.earntt.OfferDetails"/>

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
	var id=$("#id").val()
	//alert(id);
	window.location.href="<%=response.encodeURL(request.getContextPath())%>/PopUpAlertEdit?id="+id+"";
}
function deleet(id)
{
	//alert(id);
	 //window.location.href="<%=response.encodeURL(request.getContextPath())%>/EditOfferDNew?offer_id="+id+"";
}
function refresh()
{
	window.location.href="<%=response.encodeURL(request.getContextPath())%>/PopUpAlert";
	
}
function Edit(id)
{
	//alert(id);
        window.location.href="<%=response.encodeURL(request.getContextPath())%>/PopUpAlertEdit?id="+id+"";
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
				<td width="40%" align="center" valign="top" scope="row" ><strong><label>Pop Up Alert</label><strong> &nbsp;</td>
			</tr>

	</table>			




</td>
<td width="14%">&nbsp;</td>
<tr>
<td width="10%" >&nbsp;</td>
<td width="76%" align="center" valign="bottom">
<TABLE  border="0" style="width:880px;">
<tr><td align="center" ></td></tr>
<tr bgcolor="#F7F7F7" >
<td>
<div>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
<td valign="top" scope="row"><label><b>PopUpAlert Id</label>&nbsp;<input name="id" type="text" id="id" value="" class ="contentblack"  />&nbsp;&nbsp;<label>
<input type="submit" name="submit" id="submitO" value="Submit" onclick=" getOferId();"/></label></td>
</td>
</tr>

</table>


</div>
<DIV align="center" valign="bottom" style="posision: absolute; overflow:hidden;">
<%--  data table pagination ----------------------------------%>
<display:table name="requestScope.configDataList" pagesize="20" class="dataTable" export="true" id="currentRowObject" sort="list" style="width:99%">
<display:column title="Action">
<%--<a href="#" onclick="Edit('${currentRowObject.offerId}')" style="color:#4477AA">Edit</a>&nbsp;|&nbsp;<a href="#" onclick="deleet('${currentRowObject.offerId}')" style="color:#4477AA">Delete</a>--%>
&nbsp;&nbsp;<a href="#" onclick="Edit('${currentRowObject.id}')" style="color:#4477AA">Edit</a>&nbsp;&nbsp;
</display:column>


<display:column property="id" title="Pop Id" media="csv excel html" />
<display:column property="status" title="Status" media="csv excel html" />
<display:column property="heading" title="Heading" media="csv excel html " />
<display:column property="text" title="Text" media="csv excel html " />
<display:column property="noOfButton" title="No. Of Button" media="csv excel html" />
<display:column property="buttonsText" title="Buttons Text" media="csv excel html " />
<display:column property="actinoUrl" title="Actino Url" media="csv excel html " />

<%--
<display:column title="Action">
<a href="#" onclick="Edit('${currentRowObject.offerId}')" style="color:#4477AA">Edit</a>&nbsp;|&nbsp;<a href="#" onclick="deleet('${currentRowObject.offerId}')" style="color:#4477AA">Delete</a>
</display:column>--%>
<display:setProperty name="paging.banner.placement" value="top" />
<display:setProperty name="paging.banner.placement" value="bottom" />
<display:setProperty name="export.amount" value="list" />
<display:setProperty name="export.pdf" value="false" />
<display:setProperty name="export.excel.filename" value="PopupReport.xls"/>
<display:setProperty name="export.csv.filename" value="PopupReport.csv"/>
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
