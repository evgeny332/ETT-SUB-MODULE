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
        String fromDate      = null;
	String circle	     = null;
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
                fromDate = (String)request.getParameter("DateTime1").trim();
                circle   = (String)request.getParameter("circle").trim();
		out.println("fromDate"+fromDate);
		out.println("circle"+circle);
        }catch(Exception e){
                fromDate = "";
                circle   = "";
                label = "";
        }
        if ( showDataFlag.equals( "true" ) ){
		//request.setAttribute( "configDataList",(ArrayList)session.getAttribute("FileStatusList"));		
       		request.setAttribute( "configDataList", new GetCircleSummaryFile().getCircleSummaryDataList(fromDate,circle));
		
	       	//request.setAttribute( "configDataList", fromDate);
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
<script>
function Validate()
{
        var DateTime1 = document.circlesummary.DateTime1.value;
	var circle    =document.circlesummary.circle.value;

        if(DateTime1 == "")
        {
                alert("From Date should not be blank !");
                return false;
        }
	if(circle == "" )
	{
		alert("Circle should not be blank !");
	}
        if(!validDateTime()){
                return false;
        }
        return true;
}


function validDateTime()
{
	dateTime1       = document.getElementById('DateTime1').value.split("-");
	var fromDate    = new Date(dateTime1[1]+"/"+dateTime1[2]+"/"+dateTime1[0]);
	var circle      = document.getElementById('circle').value;
	var currentDate = new Date( );
	if(fromDate > currentDate || circle > 99)
	{
        	if(fromDate > currentDate)
        	{
                	alert("From Date can't be greater than To Date");
                	return false;
        	}
		if(circle > 99 )
		{
			alert("Circle should not be greater than 99");
			return false;
		}
	}
	else
	{
                document.circlesummary.showDataFlag.value = "true";
		alert("submit");
                document.circlesummary.submit();
        }
}
function cancelAction()
{
        document.circlesummary.action="";
        location.reload(true);
}
</script>
<body background-color="#dddddd" >
<form action="circlesummary.jsp" name="circlesummary" method="POST" >
<table width="100%" height="60%" border="0" cellpadding="0" cellspacing="0">
<tr width="100%">
<td width="10%" >&nbsp;</td>
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
<td width="40%" align="center" valign="top" scope="row" ><strong><label>Circle Wise Summary Report</label><strong> &nbsp;</td>
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
<input type="hidden" name="showDataFlag" value=""/>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Circle</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="circle" type="text" id="circle" size="12" value="" class ="contentblack"/>
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
<input type="submit" name="submit" id="submit" value="Submit" onclick="Validate();" />&nbsp;&nbsp;&nbsp; <input type="button" name="Cancel" value="Cancel" onclick="cancelAction();"/></label>
</td>
</tr>

<tr>
<td>&nbsp;</td>
</tr>

<%
                ArrayList al = (ArrayList)request.getAttribute( "configDataList");
                try{
                                //out.println(al.size());
                        if(al.size()== 0 && showDataFlag.equals( "true" )){
                                //out.println(al+ "and"+showDataFlag);
                                label = "Record Not Found";
                        }else if(showDataFlag.equals( "false") && al.size()== 0 && al==null){
                                //out.println(al.size()+"and"+showDataFlag);
                                label = "***";
                        }else{
                                //out.println(al.size()+"and"+showDataFlag);
                                label  = "Circle Wise Summary Report";
                        }
                }catch(Exception e){
                        label = "";

                }
        %>

<tr>
<td width="14%">&nbsp;</td>
</tr>
</table>
</table>
<tr width="100%">
<td width="10%" height="26">&nbsp;</td>
<td width="76%" align="center" valign="top"><img src="images/blackberry_bottom.gif" width="100%"/></td>
<td width="14%">&nbsp;</td>
</tr>
</table>
</table>
</form>
</body>
</html>

