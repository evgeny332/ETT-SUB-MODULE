<%@ page language="java" session="true" import="java.util.*,java.text.*,com.reports.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="./include/displaytagex.css">
<link href="./include/cms.css" rel="stylesheet" type="text/css"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache"/> 
<meta http-equiv="expires" content="0"/> 
<meta http-equiv="pragma" content="no-cache"/> 
<title>Earn Talk Time Report</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
</head>
<script language=javascript>
javascript:window.history.forward(1);
window.onbeforeunload = function (){}
</script>

<script>
function Validate()
{
	var offerAmount = $("#Amount").val()
	if(offerAmount  == "")
	{
		alert("Threshold Amount should not be blank.");
		 return false;
	}
}
function cancelAction()
{
	document.AddNewOfferForm.action="";
	//return false;
	window.location.href = "<%=response.encodeURL(request.getContextPath())%>/RedeemThreshold";
}
</script>
<body background-color="#dddddd" >
<form action="<%=response.encodeURL(request.getContextPath())%>/RedeemThresholdUpdate" name="AddNewOfferForm" >
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
<td width="40%" align="center" valign="top" scope="row" ><strong><label>Update RedeemThreshold Amount</label><strong> &nbsp;</td>
</tr>
<%
String str1="";
String str2="";
String str3="";
String str4="";
ArrayList list=(ArrayList)session.getAttribute("FileStatusList");
out.println(list);
str1=(String)list.get(0);
str2=(String)list.get(1);
str3=(String)list.get(2);
str4=(String)list.get(3);
%>
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
<tr style="display:none">
<td width="33%" align="right" valign="top" scope="row"><label><b>fid</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="offer_id" type="text" id="offer_id" value="<%=str1%>" class ="contentblack"/>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Threshold Amount</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="Amount" type="text" id="Amount" value="<%=str2%>" class ="contentblack"/>
</td>
</tr>


<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Threshold First Time Redeem</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="FirstAmount" type="text" id="FirstAmount" value="<%=str4%>" class ="contentblack"/>
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
