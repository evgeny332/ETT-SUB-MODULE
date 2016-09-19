<%@ page language="java" session="true" import="java.util.*,java.text.*,com.reports.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.dndbean.TMReportBean" %>
<%@ page import="java.util.*" %>
<%@taglib uri="/WEB-INF/displaytag-el.tld" prefix="display"%>
<jsp:useBean id="teleBean" scope="application"
class="com.dndbean.TMReportBean"/>

<%
        /*String [] menuArray   = null;
        String [] numberArray = null;*/
	HashMap hm = new HashMap();
        com.mcarbon.InsertIntoDb idb      = new com.mcarbon.InsertIntoDb();
        hm = idb.getUrnPair();
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
<script language="javascript" src ="JavaScript/cal_conf_tm.js"></script>
	<script>
function Validate()
{
	var DateTime1 = document.TMReportForm.DateTime1.value;

	if(DateTime1 == "")
	{
		alert("From Date should not be blank.");
		return false;
	}
	var DateTime2 = document.TMReportForm.DateTime2.value;
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
	var fromDate    = new Date(dateTime1[1]+"/"+dateTime1[2]+"/"+dateTime1[0]);
	var dateTime    = new Array();
	dateTime2       = document.getElementById('DateTime2').value.split("-");
	var toDate      = new Date(dateTime2[1]+"/"+dateTime2[2]+"/"+dateTime2[0]);
	var currentDate = new Date( );
	if(fromDate > currentDate )
	{
		alert("From Date should not be greater than current date.");
		return false;
	}
	else if(toDate > currentDate )
	{
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
	document.TMReportForm.action="";
	window.location.href = "<%=response.encodeURL(request.getContextPath())%>/TMReport";
}
</script>
<%String urlToList = response.encodeURL(request.getContextPath()+"/UrnManagement");%>
<script>

function call_url()
{       user_id = document.getElementById('urn_name').value;
        createXmlHttpRequest();
        document.getElementById('loadingImg').style.display = '';
       if (user_id=="-1")
         { document.TMReportForm.reset();
            document.getElementById('loadingImg').style.display = 'none';
           pagerefresh1();
         }
        else{
        if ( xmlHttp != null ){

                try{
                        xmlHttp.open("GET", "<%=urlToList%>?REQTYPE=GETNUMBER&urn=" + user_id , true);
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

function call_url1()
{       user_id = document.getElementById('name').value;
        createXmlHttpRequest();
        document.getElementById('loadingImg').style.display = '';
       if (user_id=="-1")
         { document.TMReportForm.reset();
            document.getElementById('loadingImg').style.display = 'none';
           pagerefresh();
         }
        else{
        if ( xmlHttp != null ){

                try{
                        xmlHttp.open("GET", "<%=urlToList%>?REQTYPE=GETNUMBER1&urn=" + user_id , true);
                        xmlHttp.setRequestHeader("Content-Type","text/plain");

                        xmlHttp.onreadystatechange =function(){
                                <% System.out.println("Calling Function Display");%>
                                        display1()};
                        xmlHttp.send(null);
                }
                catch(err)
                { alert("Sorry we are unable to process your request." );
                }

        }
     }

}
function pagerefresh1(){
        var selectList1 = document.getElementById("name");
        var len1        = selectList1.length;
        //alert("length is " + len);
        len1 = len1*1;
        while(len1!=0)
          { selectList1.remove(len1);
                 len1--;
          }
	var selectList = document.getElementById("number");
        var len        = selectList.length;
        //alert("length is " + len);
        len = len*1;
        while(len!=0)
          { selectList.remove(len);
                 len--;
          }

}
function pagerefresh(){
        var selectList = document.getElementById("number");
        var len        = selectList.length;
        //alert("length is " + len);
        len = len*1;
        while(len!=0)
          { selectList.remove(len);
		 len--;
          }
	var selectList1 = document.getElementById("urn_name");
        var len1        = selectList1.length;
        //alert("length is " + len);
        len1 = len1*1;
        while(len1!=0)
          { selectList1.remove(len1);
                 len1--;
          }

}


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




function display1()
{        var str="";
         var record = new Array();
         var mycurrent_row = "";
         var mycurrent_cell_1 = "";

      //alert("Inside Display" + xmlHttp.readyState);
        if ( xmlHttp.readyState == 4 ){
                try{    pagerefresh();
                        document.getElementById('loadingImg').style.display = 'none';
                        str=xmlHttp.responseText;
                        //alert("str"+str);
                        if(str!=":")
                        {
                                record = str.split(":");
                              //alert("length of record is : " + record.length);

				var value =document.getElementById("name").value;
                                //alert("value"+value);
                                <%      Set set111 = hm.entrySet();
                                        Iterator i111 = set111.iterator();
                                        while(i111.hasNext()) {
                                        Map.Entry me111 = (Map.Entry)i111.next();
                                %>
                                mycurrent_row = document.getElementById("urn_name").lastChild;
                                mycurrent_cell_1 = document.createElement("option");
                                mycurrent_cell_1.value = "<%=me111.getValue()%>" ;
                                mycurrent_cell_1.innerHTML = "<%=me111.getKey()%>";
                                if (mycurrent_cell_1.innerHTML == value)
                                {
                                mycurrent_cell_1.selected = true;
                                }
                                document.getElementById("urn_name").appendChild(mycurrent_cell_1);
                                mycurrent_row = document.getElementById("urn_name").lastChild;
                                <%
                                        }
                                %>
                                for(var i = 1;i<record.length;i++)
                                {
                                        mycurrent_row = document.getElementById("number").lastChild;
                                        mycurrent_cell_1 = document.createElement("option");
                                        mycurrent_cell_1.value = record[i] ;
                                        mycurrent_cell_1.innerHTML = record[i];
                                        document.getElementById("number").appendChild(mycurrent_cell_1);
                                        mycurrent_row    = document.getElementById("number").lastChild;
                                }

                        }
                }
                catch(err)
                { alert("Some error has occurred");
                }
        }
}

function display()
{        var str="";
         var record = new Array();
         var mycurrent_row = "";
         var mycurrent_cell_1 = "";

      //alert("Inside Display" + xmlHttp.readyState);
        if ( xmlHttp.readyState == 4 ){
                try{    pagerefresh1();
                        document.getElementById('loadingImg').style.display = 'none';
                        str=xmlHttp.responseText;
			//alert("str"+str);
                        if(str!=":")
                        {
                                record = str.split(":");
                //              alert("length of record is : " + record.length);
				var value =document.getElementById("urn_name").value;
				//alert("value"+value);
				<%	Set set11 = hm.entrySet();
                			Iterator i11 = set11.iterator();
                			while(i11.hasNext()) {
                			Map.Entry me11 = (Map.Entry)i11.next();
				%>
				mycurrent_row = document.getElementById("name").lastChild;
                                mycurrent_cell_1 = document.createElement("option");
                                mycurrent_cell_1.value = "<%=me11.getKey()%>" ;
                                mycurrent_cell_1.innerHTML = "<%=me11.getValue()%>";
				if (mycurrent_cell_1.innerHTML == value)
				{
				mycurrent_cell_1.selected = true;
				}
                                document.getElementById("name").appendChild(mycurrent_cell_1);
                                mycurrent_row = document.getElementById("name").lastChild;
				<%
					}
				%>
                                for(var i = 1;i<record.length;i++)
                                {
                                        mycurrent_row = document.getElementById("number").lastChild;
                                        mycurrent_cell_1 = document.createElement("option");
                                        mycurrent_cell_1.value = record[i] ;
                                        mycurrent_cell_1.innerHTML = record[i];
                                        document.getElementById("number").appendChild(mycurrent_cell_1);
                                        mycurrent_row    = document.getElementById("number").lastChild;
                                }
                        }
                }
                catch(err)
                { alert("Some error has occurred");
                }
        }
}
</script>


<body background-color="#dddddd" >
<form action="<%=response.encodeURL(request.getContextPath())%>/TMReport" name="TMReportForm" >
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
<td width="40%" align="center" valign="top" scope="row" ><strong><label>Tele Marketer Report</label><strong> &nbsp;</td>
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
<td width="33%" align="right" valign="top" scope="row"><label><b>URN</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select id="urn_name" name="urn_name" value="" class ="contentblack" onChange='call_url();' style="width: 105px;">
	<option value="-1" selected>All</option>
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
<td width="33%" align="right" valign="top" scope="row"><label><b>Name</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select id="name" name="name" value="" class ="contentblack" onChange='call_url1();' style="width: 105px;">
        <option value="-1" selected>All</option>
        <%
                Set set1 = hm.entrySet();
                Iterator i1 = set1.iterator();
                while(i1.hasNext()) {
                Map.Entry me1 = (Map.Entry)i1.next();
        %>
                <option value="<%=me1.getKey()%>"><%=me1.getValue()%></option>
        <%}%>
</select>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Number</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select id="number" name="number" value="" class ="contentblack" style="width: 105px;">
<option value="-1" selected>All</option>
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
</td>
</tr>

<TABLE  border="0" style="width:580px;">
<tr><td align="center" ></td></tr>
<tr bgcolor="#F7F7F7" class="contentblack">
<td>
<DIV align="center" valign="bottom" style="posision: absolute; overflow:hidden;">
<%--  data table pagination ----------------------------------%>
<display:table name="requestScope.configDataList" pagesize="5" class="dataTable" export="true" id="currentRowObject" >
<display:column property="dateval" title="Date" media="csv excel html pdf " />
<display:column property="urn" title="Urn" media="csv excel html pdf " />
<display:column property="name" title="Name" media="csv excel html pdf " />
<display:column property="circle" title="Circle" media="csv excel html pdf " />
<display:column property="total" title="Total Call" media="csv excel html pdf " />
<%--<display:column property="circle" title="Circle" media="csv excel html pdf " />--%>
<display:column property="allowed" title="Allowed" media="csv excel html pdf " />
<display:column property="blocked" title="Blocked" media="csv excel html pdf " />
<display:column property="pallowed" title="% Allowed" media="csv excel html pdf " />
<display:column property="pblocked" title="% Blocked" media="csv excel html pdf " />
<display:setProperty name="paging.banner.placement" value="top" />
<display:setProperty name="paging.banner.placement" value="bottom" />
<display:setProperty name="export.amount" value="list" />
<display:setProperty name="export.pdf" value="true" />
<display:setProperty name="export.pdf.filename" value="TMReport.pdf"/>
<display:setProperty name="export.excel.filename" value="TMReport.xls"/>
<display:setProperty name="export.csv.filename" value="TMReport.csv"/>
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


