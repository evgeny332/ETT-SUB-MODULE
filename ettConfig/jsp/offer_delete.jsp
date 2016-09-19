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
	var offerName           = $("#offerName").val();
        var actionUrl           = $("#actionUrl").val();
        var offerType           = $("#offerType").val();
        var description         = $("#description").val();
        var offerAmount         = $("#offerAmount").val()
        var appKey              = $("#appKey").val();
        var imageUrl            = $("#imageUrl").val();
        var priority            = $("#priority").val();
        var Operators           = $("#Operators").val();
        var status              = $("#status").val();
        var alertText           = $("#alertText").val();
        var networkType         = $("#networkType").val();
        var balanceType         = $("#balanceType").val();
        var isClick             = $("#isClick").val();
        var isDownload          = $("#isDownload").val();
        var isShare             = $("#isShare").val();
        var isShop              = $("#isShop").val();
        var category            = $("#category").val();
        var balanceCreditInDays = $("#balanceCreditInDays").val();
        var root                = $("#root").val();
	if(offerName == "")
	{
		alert("offer Name should not be blank.");
		return false;
	}
	if(offerType == "" || offerType=="Offer_Type")
        {
                alert("offerType should not be blank.");
                return false;
        }
	if(description == "")
        {
                alert("description should not be blank.");
                return false;
        }
	if(offerAmount  == "")
	{
		alert("offer Amount should not be blank.");
		 return false;
	}
	if(appKey == "")
	{
		alert("apps Key should not be blank.");
		return false;
	}
	if(actionUrl == "")
	{
		alert("action Url should not be blank.");
		return false;
	}
	if(imageUrl == "")
	{
		alert("image Url should not be blank.");
		return false;
	}
	if(priority =="" || priority=="priority")
	{
		alert("priority should not be blank.");
		return false;
	}
	if(Operators == "" || Operators=="Operators")
	{
		alert("oprator should not be blank.");
		return false;
	}
	if(status === "" || status=="status")
	{
		alert("status should not be blank.");
		return false;
	}
}
function cancelAction()
{
	document.AddNewOfferForm.action="";
	//return false;
	window.location.href = "<%=response.encodeURL(request.getContextPath())%>/ViewOffer";
}
</script>
<body background-color="#dddddd" >
<form action="<%=response.encodeURL(request.getContextPath())%>/DeleteOffer" name="AddNewOfferForm" >
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
<td width="40%" align="center" valign="top" scope="row" ><strong><label>Delete Offer</label><strong> &nbsp;</td>
</tr>
<%
String str1="";
String str2="";
String str3="";
String str4="";
String str5="";
String str6="";
String str7="";
String str8="";
String str9="";
String str10="";
String str11="";
String str12="";
String str13="";
String str14="";
String str15="";
String str16="";
String str17="";
String str18="";
String str19="";
String str20="";
String str21="";
String str22="";
String str23="";
String str24="";
ArrayList list=(ArrayList)session.getAttribute("FileStatusList");
str1=(String)list.get(0);
str2=(String)list.get(1);
str3=(String)list.get(2);
str4=(String)list.get(3);
str5=(String)list.get(4);
str6=(String)list.get(5);
str7=(String)list.get(6);
str8=(String)list.get(7);
str9=(String)list.get(8);
str10=(String)list.get(9);
str11=(String)list.get(10);
str12=(String)list.get(11);
str13=(String)list.get(12);
str14=(String)list.get(13);
str15=(String)list.get(14);
str16=(String)list.get(15);
str17=(String)list.get(16);
str18=(String)list.get(17);
str19=(String)list.get(18);
str20=(String)list.get(19);
str21=(String)list.get(20);
str22=(String)list.get(21);
str23=(String)list.get(22);
str24=(String)list.get(23);
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
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Name</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="offerName" type="text" id="offerName" value="<%=str8%>" class ="contentblack" readonly/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Type</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="offerType" id="offerType" class ="contentblack" style="height:25px; width:175px" readonly>
	<option value="<%=str9%>"><%=str9%></option>
</select> 
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Description</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" readonly name="description" id="description" class ="contentblack" style="width: 170px; height: 50px;" >
<%=str5%>
</textarea>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Amount</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="offerAmount" type="text" id="offerAmount" value="<%=str7%>" class ="contentblack" readonly/>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Apps Key</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="appKey" type="text" id="appKey" value="<%=str3%>" class ="contentblack" readonly />
</td>

</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Action Url</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="actionUrl" type="text" id="actionUrl" value="<%=str2%>" class ="contentblack" readonly/>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Image Url</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="imageUrl" type="text" id="imageUrl" value="<%=str6%>" class ="contentblack" readonly />
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Priority</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select  name="priority" id="priority" class ="contentblack" style="height:25px; width:175px" readonly>
	<option value="<%=str11%>"><%=str11%></option>
</select>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Operators</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select  name="Operators" id="Operators" class ="contentblack" style="height:25px; width:175px" readonly>
	<option value="<%=str10%>"><%=str10%></option>
</select>
</td>
</tr>	

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>status</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="status" id="status" class ="contentblack" style="height:25px; width:175px" readonly> 
	<%
		if(Integer.parseInt(str12)==0)
		{
	%>
			<option value="0">enable</option>
	<%
		}
		else
		{
	%>
			<option value="1">disable</option>
	<%		
		}
	%>
</select> 
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Size</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="size" type="text" id="size" value="<%=str14%>" class ="contentblack" readonly/>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Network Type</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="networkType" type="text" id="networkType" value="<%=str15%>" readonly class ="contentblack"/>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Alert Text</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="alertText" type="text" id="alertText" value="<%=str16%>" readonly class ="contentblack"/>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Balance Type</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="balanceType" type="text" id="balanceType" value="<%=str17%>" readonly class ="contentblack"/>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>isClick</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="isClick" id="isClick" class ="contentblack" style="height:25px; width:175px" readonly>
        <option value="<%=str18%>"><%=str20%></option>
</select>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>isDownload</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="isDownload" id="isDownload" class ="contentblack" style="height:25px; width:175px" readonly>
        <option value="<%=str19%>"><%=str21%></option>
</select>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>isShare</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="isShare" id="isShare" class ="contentblack" style="height:25px; width:175px" readonly>
       <option value="<%=str20%>"><%=str22%></option>
</select>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>isShop</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="isShop" id="isShop" class ="contentblack" style="height:25px; width:175px" readonly>
        <option value="<%=str21%>"><%=str23%></option>
</select>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>balanceCreditInDays</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="balanceCreditInDays" type="text" id="balanceCreditInDays" value="<%=str19%>" class ="contentblack" readonly />
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>category</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="category" type="text" id="category" value="<%=str18%>" class ="contentblack" readonly />
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>root</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">

<input name="root" type="text" id="root" value="<%=str24%>" class ="contentblack" readonly />
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
