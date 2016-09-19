<%@ page language="java" session="true" import="java.util.*,java.text.*,com.reports.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.dndbean.BlockReportBean" %>
<%@ page import="java.util.*" %>
<%@taglib uri="/WEB-INF/displaytag-el.tld" prefix="display"%>
<jsp:useBean id="dbBlockReportBean" scope="application"
class="com.dndbean.BlockReportBean"/>
<%
HashMap hm = new HashMap();
com.mcarbon.InsertIntoDb idb      = new com.mcarbon.InsertIntoDb();
hm = idb.getCircle();
%>
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
<script language="javascript" src ="JavaScript/cal_confblo.js"></script>
<script language="javascript" src ="JavaScript/validate2.js"></script>

<script language="javascript">
<!--
addCalendar("Date10", "cal1", "DateTime1", "BlockReportForm");
addCalendar("Date20", "cal2", "DateTime2", "BlockReportForm");
//-->
</script>

<script>

function Validate()
{
	var DateTime1 = document.BlockReportForm.DateTime1.value;
	var circle_id = document.getElementById('circle_id').value;
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
	var circle_id = document.getElementById('circle_id').value;	
	if(fromDate > currentDate )
	{
		alert("Date should not be greater than current date.");
		return false;
	}
	if(circle_id == -1)
        {
                alert("Please select Circle.");
                return false;
        }
	return true;
}
function cancelAction()
{
	document.BlockReportForm.action="";
	window.location.href = "<%=response.encodeURL(request.getContextPath())%>/BlockReport";
}
</script>
<%String urlToList = response.encodeURL(request.getContextPath()+"/UrnManagement");%>
<script>
var xmlHttp;// global instance of XMLHttpRequest
function createXmlHttpRequest()
{
        if(window.ActiveXObject)
        {
                xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
        }
        else if(window.XMLHttpRequest)
        {
                xmlHttp=new XMLHttpRequest();
        }
}

function call_url()
{       circle_id = document.getElementById('circle_id').value;
	var d=new Date();
        var transID=d.getTime();
        createXmlHttpRequest();
	//alert("circle_id:"+circle_id);
        document.getElementById('loadingImg').style.display = '';
       if (circle_id=="-1")
         { document.BlockReportForm.reset();
            document.getElementById('loadingImg').style.display = 'none';
           pagerefresh();
         }
        else{
		document.getElementById('TMNumber').value="";
        if ( xmlHttp != null ){

                try{
                        xmlHttp.open("GET", "<%=urlToList%>?REQTYPE=GETTMNUMBER&circle_id=" + circle_id +"&transID="+transID, true);
                        xmlHttp.setRequestHeader("Content-Type","text/plain");

                        xmlHttp.onreadystatechange =function(){
                                <% System.out.println("Calling Function Display");%>
                                        display()};
					 xmlHttp.send(null);
                }
                catch(err)
                { alert("Sorry we are unable to process your request." );
                }

        }
     }

}

function pagerefresh(){
	var selectList = document.getElementById('TMNumber');
	var len        = selectList.length;
	//alert("length is " + len);
	len = len*1;
	while(len!=0)
	{ selectList.remove(len);
		len--;
	}

}

function display()
{        var str="";
	var record = new Array();
	var mycurrent_row = "";
	var mycurrent_cell_1 = "";

	  //    alert("Inside Display" + xmlHttp.readyState);
	if ( xmlHttp.readyState == 4 ){
		try{    pagerefresh();
			document.getElementById('loadingImg').style.display = 'none';
			str=xmlHttp.responseText;
			if(str!=":")
			{
				record = str.split(":");
	//			alert("record   ::::"+record);
	//			              alert("length of record is : " + record.length);
				for(var i = 1;i<record.length;i++)
				{
					mycurrent_row = document.getElementById("TMNumber").lastChild;
					mycurrent_cell_1 = document.createElement("option");
					mycurrent_cell_1.value = record[i] ;
					mycurrent_cell_1.innerHTML = record[i];
					document.getElementById("TMNumber").appendChild(mycurrent_cell_1);
					mycurrent_row    = document.getElementById("TMNumber").lastChild;
				}
			}
		}
		catch(err)
		{ alert("Some Error Has Occurred");
		}
	}
}

</script>














<body background-color="#dddddd" >
<form action="<%=response.encodeURL(request.getContextPath())%>/BlockReport" name="BlockReportForm" >
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
<td width="40%" align="center" valign="top" scope="row" ><strong><label>Block Report Download</label><strong> &nbsp;</td>
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
<!--	<td width="33%" scope="row" align="right" valign="top" ><label><B>Message Type</label>&nbsp;&nbsp;</td> -->
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Select Date</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="58%" align="left" valign="top">
<input name="DateTime1" type="text" id="DateTime1" size="12" value="" class ="contentblack" readonly="yes" >&nbsp;<a href="javascript:showCal('Date10')"><img src="images/cal.gif" width="19" height="16" border="0" alt="Pick a date"></a><span id="cal1" style="position: relative">&nbsp;</span>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Circle</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select id="circle_id"  name="circle_id" value="" class ="contentblack" style="width: 105px;" onChange="call_url();">
<option value="-1" selected>Select</option>
<%
Set set = hm.entrySet();
Iterator i = set.iterator();
while(i.hasNext()) {
	Map.Entry me = (Map.Entry)i.next();
	%>
		<option value="<%=me.getValue()%>"><%=me.getKey()%></option>
		<%}%>
</select>
</td>
<td id='loadingImg' style="display: none;">
<img src="img/spinner3-greenie.gif" alt="Loading..." border="0">
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Number</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select id="TMNumber" name="TMNumber" value="" class ="contentblack" style="width: 105px;">
<option value="-1" selected>All</option>
		<%--<option value="1204244701">1204244701</option>
		<option value="7714222700">7714222700</option>--%>
</select>
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
<TABLE  border="0" style="width:580px;">
<tr><td align="center" ></td></tr>
<tr bgcolor="#F7F7F7" class="contentblack">
<td>
<DIV align="center" valign="bottom" style="posision: absolute;">
<%--  data table pagination ----------------------------------%>
<display:table name="requestScope.configDataList" pagesize="5" class="dataTable" export="true" id="currentRowObject" >
<display:column property="urn" title="Urn" media="csv excel html pdf " />
<display:column property="name" title="Name" media="csv excel html pdf " />
<display:column property="aparty" title="TM Number" media="csv excel html pdf " />
<display:column property="dateval" title="Date" media="csv excel html pdf " />
<display:column property="timeval" title="Time" media="csv excel html pdf " />
<display:column property="circle" title="TM Circle" media="csv excel html pdf " />
<display:column property="bparty" title="Called Number" media="csv excel html pdf " />
<display:column property="action" title="Call Action" media="csv excel html pdf " />
<display:setProperty name="paging.banner.placement" value="top" />
<display:setProperty name="paging.banner.placement" value="bottom" />
<display:setProperty name="export.amount" value="list" />
<display:setProperty name="export.pdf" value="true" />
<display:setProperty name="export.pdf.filename" value="BlockReport.pdf"/>
<display:setProperty name="export.excel.filename" value="BlockReport.xls"/>
<display:setProperty name="export.csv.filename" value="BlockReport.csv"/>
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

