<%@ page language="java" session="true" contentType="text/html;charset=UTF-8"%>
<%@ page import="java.text.* "%>
<%@page import="java.util.*, com.utility.*"%>
<%@taglib uri="/WEB-INF/displaytag-el.tld" prefix="display"%>
<jsp:useBean id="SearchBean" scope="application" class="com.dndbean.SearchBean"/>
<%@ page import="java.sql.*,java.text.*" %>

<%@ page language="java" contentType="text/html;charset=UTF-8"%>

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
<script language="javascript" src ="JavaScript/cal_conf2.js"></script>
<script language="javascript" src ="JavaScript/validate2.js"></script>
<script>
function Validate()
{
	var msisdn = document.searchmsisdn.msisdn.value;

	if(msisdn == "")
	{
		alert("MSISDN should not be blank.");
		return false;
	}
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
	document.searchmsisdn.showDataFlag.value = "true";
	document.searchmsisdn.submit();
}
function cancelAction()
{
	document.searchmsisdn.action="";
	window.location.href ="<%=response.encodeURL(request.getContextPath())%>/SearchMsisdn";
}
</script>
<body background-color="#dddddd" >
<form action="<%=response.encodeURL(request.getContextPath())%>/SearchMsisdn" name="searchmsisdn" method="GET" >
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
<td width="40%" align="center" valign="top" scope="row" ><strong><label>Search DNC Subscriber</label><strong> &nbsp;</td>
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
ArrayList al = (ArrayList)request.getAttribute( "configDataList");
try{
              //out.println(al.size());
        if(al.size()== 0 ){
                //out.println(al+ "******and");
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
<display:column property="msisdn" title="MSISDN" media="csv excel html pdf " />
<display:column property="filename" title="File Name" media="csv excel html pdf " />
<display:column property="interfaceuse" title="Interface" media="csv excel html pdf " />
<display:column property="recdateval" title="Receive Date" media="csv excel html pdf " />
<display:column property="updateval" title="Update Date" media="csv excel html pdf " />
<display:setProperty name="paging.banner.placement" value="top" />
<display:setProperty name="paging.banner.placement" value="bottom" />
<display:setProperty name="export.amount" value="list" />
<display:setProperty name="export.pdf" value="true" />
<display:setProperty name="export.pdf.filename" value="searchmsisdn.pdf"/>
<display:setProperty name="export.excel.filename" value="searchmsisdn.xls"/>
<display:setProperty name="export.csv.filename" value="searchmsisdn.csv"/>
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
