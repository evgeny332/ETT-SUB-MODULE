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
	var status = $("#status").val();
        var text = $("#text").val();
        var imageUrl = $("#imageUrl").val();
        var validity = $("#validity").val()
        var clickable = $("#clickable").val();
        var onClickType = $("#onClickType").val();
	var offerId	 = $("#offerId").val();
        var actionUrl = $("#actionUrl").val();
        var menuName = $("#menuName").val();
        var popUpHeading = $("#popUpHeading").val();
        var popUpText = $("#popUpText").val();
        var popUpButtonText = $("#popUpButtonText").val();
        var popUpActionUrl = $("#popUpActionUrl").val();
	if(status == ""  || status=="status")
        {
                alert("status should not be blank.");
                return false;
        }
	if(text == "" )
        {
                alert("text should not be blank.");
                return false;
        }
	/*if(imageUrl == "")
        {
                alert("image Url should not be blank.");
                return false;
        }*/
	if(validity == "")
        {
                alert("image Url should not be blank.");
                return false;
        }
	if(clickable == "" || clickable=="clickable")
        {
                alert("clickable should not be blank.");
                return false;
        }
	if(onClickType == "" || onClickType=="onClick")
        {
                alert("onClickType should not be blank.");
                return false;
        }
	if(offerId == "" )
        {
                alert("offerId should not be blank.");
                return false;
        }

	if(actionUrl == "" )
        {
                alert("actionUrl should not be blank.");
                return false;
        }
/*	if(menuName == "" )
        {
                alert("menuName should not be blank.");
                return false;
        }*/
	if(popUpHeading == "" )
        {
                alert("popUpHeading should not be blank.");
                return false;
        }
	if(popUpText == "" )
        {
                alert("popUpText should not be blank.");
                return false;
        }
	if(popUpButtonText == "" )
        {
                alert("popUpButtonText should not be blank.");
                return false;
        }
	if(popUpActionUrl == "" )
        {
                alert("popUpActionUrl should not be blank.");
                return false;
        }





}
function cancelAction()
{
	document.AddNewOfferForm.action="";
	//return false;
	window.location.href = "<%=response.encodeURL(request.getContextPath())%>/BreakingAlert";
}
</script>
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
%>
<body background-color="#dddddd" >
<form action="<%=response.encodeURL(request.getContextPath())%>/UpdateBreakingAlert" name="AddNewOfferForm" method="post">
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
<td width="40%" align="center" valign="top" scope="row" ><strong><label>Edit Breaking Alert</label><strong> &nbsp;</td>
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
<input name="id" type="text" id="id" value="<%=str1%>" class ="contentblack"/>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>status</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="status" id="status" class ="contentblack" style="height:25px; width:175px">
	 <%
                int n=Integer.parseInt(str2);
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
<td width="33%" align="right" valign="top" scope="row"><label><b>Text</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="text" id="text" class ="contentblack" style="width: 170px; height: 50px;" ><%=str3%>
</textarea>
</td>
</tr>

<%--<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Action Url</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="actionUrl" type="text" id="actionUrl" value="" class ="contentblack"/>
</td>--%>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Image Url</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="imageUrl" type="text" id="imageUrl" value="<%=str4%>" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>validity</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="validity" type="validity" id="size" value="<%=str5%>" class ="contentblack"/>
</td>
</tr>


<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>clickable</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select  name="clickable" id="clickable" class ="contentblack" style="height:25px; width:175px">
	<%--<option value="clickable">select clickable</option>--%>
	<option value="<%=str6%>"><%=str6%></option>
	<option value="YES">YES</option>
	<option value="NO">NO</option>

</select>
</td>
</tr>	

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>onClickType</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="onClickType" id="onClickType" class ="contentblack" style="height:25px; width:175px"> 
  	<%--<option value="onClick">select onClick Type</option>--%>
	<option value="<%=str7%>"><%=str7%></option>
	<option value="Offer">Offer</option>
    	<option value="Web">Web</option>
	<option value="Menu">Menu</option>
	<option value="PopUp">PopUp</option>
</select> 
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>offerId</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="offerId" type="text" id="offerId" value="<%=str8%>" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Action Url</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="actionUrl" type="text" id="actionUrl" value="<%=str9%>" class ="contentblack"/>
</td>
</tr>
<tr>


<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Menu Name</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="menuName" type="text" id="menuName" value="<%=str10%>" class ="contentblack"/>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Pop Up Heading</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="popUpHeading" id="popUpHeading" class ="contentblack" style="width: 170px; height: 50px;" ><%=str11%></textarea>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Pop Up Text</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="popUpText" id="popUpText" class ="contentblack" style="width: 170px; height: 50px;" ><%=str12%></textarea>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>PopUpButtonText</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="popUpButtonText" id="popUpButtonText" class ="contentblack" style="width: 170px; height: 50px;" ><%=str13%></textarea>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>PopUpActionUrl</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="popUpActionUrl" type="text" id="popUpActionUrl" value="<%=str14%>" class ="contentblack"/>
</td>
</tr>


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
