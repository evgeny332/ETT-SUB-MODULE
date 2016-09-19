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
{	/*var offerName		= $("#offerName").val();
	var actionUrl		= $("#actionUrl").val();
	var offerType 		= $("#offerType").val();
	var description 	= $("#description").val();
	var offerAmount 	= $("#offerAmount").val()
	var appKey		= $("#appKey").val();
	var imageUrl		= $("#imageUrl").val();
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
	if(networkType == "")
        {
                alert("networkType should not be blank.");
                return false;
        }
	if(alertText == "" )
        {
                alert("alertText should not be blank.");
                return false;
        }
	if(balanceType == "" || balanceType=="balanceType")
	{
		alert("Balance Type should not be blank");
		return false;
	}
	if(venderName == "" )
        {
                alert("Vendor Name should not be blank.");
                return false;
        }

	if(jQuery.isNumeric(balanceType) == false){
        	alert('Please enter numeric value');
        	return false;
    	}
	*/

}
function cancelAction()
{
	document.AddNewOfferForm.action="";
	//return false;
	window.location.href = "<%=response.encodeURL(request.getContextPath())%>/PopUpAlertAdd";
}
</script>
<body background-color="#dddddd" >
<form action="<%=response.encodeURL(request.getContextPath())%>/PopUpAlertAdd" name="AddNewOfferForm" method="post">
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
<td width="40%" align="center" valign="top" scope="row" ><strong><label>New PopUp Alert Configured</label><strong> &nbsp;</td>
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
<td width="33%" align="right" valign="top" scope="row"><label><b>Status</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="status" type="text" id="status" value="" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Heading</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="heading" type="text" id="heading" value="" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Text</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="text" type="text" id="text" value="" class ="contentblack"/>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>No Of Button</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="noOfButton" type="text" id="noOfButton" value="" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Buttons Text</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="buttonsText" type="buttonsText" id="size" value="" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Action Url</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="actionUrl" type="text" id="actionUrl" value="" class ="contentblack"/>
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
