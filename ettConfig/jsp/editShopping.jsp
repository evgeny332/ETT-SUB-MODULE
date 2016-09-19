<%@ page language="java" session="true" import="java.util.*,java.text.*,com.reports.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
%>
<link rel="stylesheet" type="text/css" href="./include/displaytagex.css">
<link href="./include/cms.css" rel="stylesheet" type="text/css"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache"/> 
<meta http-equiv="expires" content="0"/> 
<meta http-equiv="pragma" content="no-cache"/> 
<title>Earn Talk Time Report</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
    var text_max = 350;
    $('#textarea_feedback').html(text_max + ' characters remaining');

    $('#alertText').keyup(function() {
        var text_length = $('#alertText').val().length;
        var text_remaining = text_max - text_length;

        $('#textarea_feedback').html(text_remaining + ' characters remaining');
    });
});

</script>

</head>
<script language=javascript>
javascript:window.history.forward(1);
window.onbeforeunload = function (){}
</script>

<script>
function Validate()
{	var offerName		= $("#offerName").val();
	var actionUrl		= $("#actionUrl").val();
	var offerType 		= $("#offerType").val();
	var description 	= $("#description").val();
	var offerAmount 	= $("#offerAmount").val()
	var imageUrl		= $("#imageUrl").val();
	var priority		= $("#priority").val();
	var status		= $("#status").val();
	var venderName      	= $("#vendor").val();	
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
	if(status === "" || status=="status")
	{
		alert("status should not be blank.");
		return false;
	}
	if(venderName == "" )
        {
                alert("Vendor Name should not be blank.");
                return false;
        }


}
function cancelAction()
{
	document.AddNewOfferForm.action="";
	//return false;
	window.location.href = "<%=response.encodeURL(request.getContextPath())%>/ViewShopping";
}
</script>
<body background-color="#dddddd" >
<form action="<%=response.encodeURL(request.getContextPath())%>/UpdateShopping" name="AddNewOfferForm" method="post">
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
<td width="40%" align="center" valign="top" scope="row" ><strong><label>Update Shopping Offer Configured</label><strong> &nbsp;</td>
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
<input name="offerName" type="text" id="offerName" value="<%=str2%>" class ="contentblack"/>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Source</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="offerSource" id="offerSource" class ="contentblack" style="height:25px; width:175px">
	<option value="<%=str17%>"><%=str17%></option>
        <option value="SHOPPING_WEB">SHOPPING_WEB</option>
        <option value="SHOPPING_IFRAME">SHOPPING_IFRAME</option>
</select>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Type</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="offerType" type="text" id="offerType" value="<%=str3%>" class ="contentblack"/>
<%--<select name="offerType" id="offerType" class ="contentblack" style="height:25px; width:175px">
	 <%
                if(str3.equals("app"))
                {
        %>
                <option value="app">app</option>
                <option value="web">web</option>
                <option value="iframe">iframe</option>
        <%
                }
                else if(str3.equals("web"))
                {
        %>
                <option value="web">web</option>
                <option value="app">app</option>
                <option value="iframe">iframe</option>
        <%
                }
                else{
        %>
                <option value="iframe">iframe</option>
                <option value="app">app</option>
                <option value="web">web</option>
        <%
        }
        %>

</select> --%>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Description</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="description" id="description" class ="contentblack" style="width: 170px; height: 50px;" >
<%=str4%>
</textarea>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Amount</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="offerAmount" type="text" id="offerAmount" value="<%=str5%>" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Action Url</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="actionUrl" type="text" id="actionUrl" value="<%=str6%>" class ="contentblack"/>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Image Url</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="imageUrl" type="text" id="imageUrl" value="<%=str7%>" class ="contentblack"/>
<a href="http://54.209.220.78:8888/earntalktime/imageupload.html" target="_blank" id="multiple_event_check" style="text-decoration: underline;">click here upload images</a>
<br/>Please copy image url then paste in textbox
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Priority</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select  name="priority" id="priority" class ="contentblack" style="height:25px; width:175px">
	<option value="<%=str8%>"><%=str8%></option>
	<option value="1">1</option>
	<option value="2">2</option>
	<option value="3">3</option>
	<option value="4">4</option>
	<option value="5">5</option>
	<option value="6">6</option>
	<option value="7">7</option>
	<option value="8">8</option>
	<option value="9">9</option>
	<option value="10">10</option>
</select>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>status</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="status" id="status" class ="contentblack" style="height:25px; width:175px">
	<%
                int n=Integer.parseInt(str9);
                if(n==1)
                {
        %>
                 <option value="1">enable</option>
                 <option value="0">disable</option>
         <% } if(n==0){%>
                 <option value="0">disable</option>
                 <option value="1">enable</option>
         <%}%> 
</select> 
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Button Text</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="ButtonText" type="text" id="ButtonText" value="<%=str10%>" class ="contentblack"/>
</td>
</tr>



<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Title</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="title" type="text" id="title" value="<%=str11%>" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Vendor</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="vendor" type="text" id="vendor" value="<%=str12%>" class ="contentblack"  />
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Grid Text</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="gridText" type="text" id="gridText" value="<%=str13%>" class ="contentblack"  />
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Grid Image</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="gridImage" type="text" id="gridImage" value="<%=str14%>" class ="contentblack"  />
<a href="http://54.209.220.78:8888/earntalktime/imageupload.html" target="_blank" id="multiple_event_check" style="text-decoration: underline;">click here upload images</a>
<br/>Please copy image url then paste in textbox
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Grid Color Code</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="gridColorCode" type="text" id="gridColorCode" value="<%=str15%>" class ="contentblack"  />
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Grid Text Url</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="gridTextUrl" type="text" id="gridTextUrl" value="<%=str16%>" class ="contentblack"  />
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Affiliate Url</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="affiliateUrl" type="text" id="affiliateUrl" value="<%=str18%>" class ="contentblack"  />
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
