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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){

$("#pop").click(function(){
  $("#overlay_form").fadeIn(1000);
  positionPopup();
});


$("#close").click(function(){
        $("#overlay_form").fadeOut(500);
});
});
function positionPopup(){
  if(!$("#overlay_form").is(':visible')){
    return;
  }
  $("#overlay_form").css({
      left: ($(window).width() - $('#overlay_form').width()) / 2,
      top: ($(window).width() - $('#overlay_form').width()) / 7,
      position:'absolute'
  });
}


$(window).bind('resize',positionPopup);

</script>
</head>
<style>
#overlay_form{
        position: absolute;
        border: 2px solid gray;
        padding: 10px;
        background: white;
        width: 290px;
        height: 376px;
}
#pop{
        width: 65px;
        text-align: center;
        padding: 6px;
        border-radius: 5px;
        text-decoration: none;
        margin: 0 auto;
}
</style>

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
	var appKey		= $("#appKey").val();
	var imageUrl		= $("#imageUrl").val();
	var priority		= $("#priority").val();
	var Operators		= $("#Operators").val();
	var status		= $("#status").val();
	var alertText		= $("#alertText").val();
	var networkType		= $("#networkType").val();
	var balanceType		= $("#balanceType").val();
	var category      	= $("#category").val();
	var balanceCreditInDays = $("#balanceCreditInDays").val();
	var root      		= $("#root").val();	
	var venderName      		= $("#venderName").val();
	var reason              =$("#reason").val();

        if(reason == "")
        {
                alert("Please select reason.");
                return false;
        }	
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

}
function cancelAction()
{
	document.AddNewOfferForm.action="";
	//return false;
	window.location.href = "<%=response.encodeURL(request.getContextPath())%>/AddNewOfferNew";
}
</script>
<body background-color="#dddddd" >
<form action="<%=response.encodeURL(request.getContextPath())%>/AddNewOfferNew" name="AddNewOfferForm" method="post">
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
<td width="40%" align="center" valign="top" scope="row" ><strong><label>Add New Event Offer</label><strong> &nbsp;</td>
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
<td width="33%" align="right" valign="top" scope="row"><label><b>Please Select OfferId</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="offerType" id="offerType" class ="contentblack" style="height:25px; width:175px">
	<option value="Offer_Type">select offer id</option>
</select> 
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
<form id="overlay_form" style="display:none">
        <h2 style="text-align: center;margin-top: -1px;">Why update offer </h2>
        <table>
        <tr>
                <td>
			<input type="radio" id="rb2" name="reason"value="Offer configuration"><label>Offer configuration</label>

                </td>

        </tr>
        <tr>
                <td>
			<input type="radio" id="rb2" name="reason"value="Offer Started (it should be default one if we start any offer from main GUI)"><label>Offer Started (it should be default one if we start any offer from main GUI</label>

                </td>
        </tr>
        <tr>
                <td>
			<input type="radio" id="rb2" name="reason"value="Changes in config in order to change Tracking URL/price /Payout model"><label>Changes in config in order to change Tracking URL/price /Payout model</label>

                </td>
        </tr>
        <tr>
                <td>
			<input type="radio" id="rb2" name="reason"value="cap got reduced"><label>cap got reduced</label>

                </td>
        </tr>
	<tr>
                <td>
			<input type="radio" id="rb2" name="reason"value="Daily caps achieved (this should be default if we stop offer from GUI)"><label>Daily caps achieved (this should be default if we stop offer from GUI)</label>

                </td>
        </tr>
	<tr>
                <td>
			<input type="radio" id="rb2" name="reason"value="Offer paused due to CR issuse/Tracking issuse"><label>Offer paused due to CR issuse/Tracking issuse</label>

                </td>
        </tr>
	<tr>
                <td>
			<input type="radio" id="rb2" name="reason"value="Offer paused as per client instructions"><label>Offer paused as per client instructions</label>

                </td>
        </tr>
	<tr>
                <td>
			<input type="radio" id="rb2" name="reason"value="Over all budgets finished"><label> Over all budgets finished</label>

                </td>
        </tr>
	<tr>
                <td>
			<input type="radio" id="rb2" name="reason"value="Non Incent(soft incent)"><label>Non Incent(soft incent)</label>

                </td>
        </tr>
	<tr>
                <td>
			<input type="radio" id="rb2" name="reason"value="Testing purpose"><label>Testing purpose</label>

                </td>
        </tr>


        </table>
        <table style="width: 270px;text-align: center;">
                <tr><td><%--<input type="button" value="Login" />--%><br/>
        <a href="#" id="close" >Close</a></td></tr>
        </table>

</form>

</body>
</html>
