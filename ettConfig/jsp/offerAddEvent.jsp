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
<%
	String keyValues=(String)session.getAttribute("keyValues");
	//out.println("Surendra : "+keyValues);
	String key[]=keyValues.split("#");
%>
<script type="text/javascript">
$(document).ready(function(){

$("input[type='radio']").click(function(){
     var radioValue = $("input[name='reason']:checked").val();
     if(radioValue){
	$("#reason").val(radioValue);
        $("#overlay_form").fadeOut(500);

     }
 });

 var text_max = 350;
    $('#textarea_feedback').html(text_max + ' characters remaining');

    $('#notification').keyup(function() {
        var text_length = $('#notification').val().length;
        var text_remaining = text_max - text_length;

        $('#textarea_feedback').html(text_remaining + ' characters remaining');
    });
var payOutHold_max = 250;
    $('#textarea_payOutHoldNotification').html(payOutHold_max + ' characters remaining');

    $('#payOutHoldNotification').keyup(function() {
        var text_length = $('#payOutHoldNotification').val().length;
        var text_remaining = payOutHold_max - text_length;

        $('#textarea_payOutHoldNotification').html(text_remaining + ' characters remaining');
    });


});

</script>
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
	/*display: block;
	border: 1px solid gray;*/
	width: 65px;
	text-align: center;
	padding: 6px;
	border-radius: 5px;
	text-decoration: none;
	margin: 0 auto;
}
</style>
<script language=javascript>
javascript:window.history.forward(1);
window.onbeforeunload = function (){}
</script>

<script>
function Validate()
{
	var appKey           = $("#appKey").val();
        var balanceCreditInDays = $("#balanceCreditInDays").val();
        var currency            = $("#currency").val();
        var eventName           = $("#eventName").val();
        var eventValue          = $("#eventValue").val()
        var incomeAmount        = $("#incomeAmount").val();
        var installedAmount     = $("#installedAmount").val();
        var notification        = $("#notification").val();
        var offerAmount         = $("#offerAmount").val();
        var offerCat            = $("#offerCat").val();
        var offerId		= $("#offerId").val();
        var pendingAmountCredit	= $("#pendingAmountCredit").val();
        var pendingRecCount	= $("#pendingRecCount").val();
        var pendingRecDay	= $("#pendingRecDay").val();
        var status		= $("#status").val();
	
	var reason		=$("#reason").val();
	
	if(reason == "")
        {
                alert("Please select reason.");
                return false;
        }

	if(appKey == "")
	{
		alert("App Key should not be blank.");
		return false;
	}
	if(balanceCreditInDays == "" )
        {
                alert("Balance Credit In Days should not be blank.");
                return false;
        }
	if(jQuery.isNumeric(balanceCreditInDays) == false){
                alert('Please enter balanceCreditInDays numeric value');
                return false;
        }

	if(currency == "")
        {
                alert("currency should not be blank.");
                return false;
        }
	if(jQuery.isNumeric(currency) == false){
                alert('Please enter numeric value in currency');
                return false;
        }

	if(eventName  == "")
	{
		alert("event name should not be blank.");
		 return false;
	}
	if(eventValue == "")
	{
		alert("event value should not be blank.");
		return false;
	}
	if(jQuery.isNumeric(eventValue) == false){
                alert('Please enter numeric value in event ');
                return false;
        }

	if(incomeAmount == "")
	{
		alert("income amount should not be blank.");
		return false;
	}
	if(jQuery.isNumeric(incomeAmount) == false){
                alert('Please enter numeric value in incomeAmount ');
                return false;
        }

	if(installedAmount == "")
	{
		alert("installed amount should not be blank.");
		return false;
	}
	if(jQuery.isNumeric(installedAmount) == false){
                alert('Please enter numeric value in installedAmount ');
                return false;
        }

	if(notification =="" )
	{
		alert("notification should not be blank.");
		return false;
	}
	if(offerAmount == "")
	{
		alert("offer amount should not be blank.");
		return false;
	}
	 if(jQuery.isNumeric(offerAmount) == false){
                alert('Please enter numeric value in offerAmount');
                return false;
        }

	/*if(offerCat == "")
        {
                alert("offer cat should not be blank.");
                return false;
        }*/

	if(offerId == "")
        {
                alert("offerId should not be blank.");
                return false;
        }
	if(jQuery.isNumeric(offerId) == false){
                alert('Please enter numeric value in offerId');
                return false;
        }

        if(pendingAmountCredit  == "" )
        {
                alert("pendingAmountCredit should not be blank.");
                return false;
        }
	if(jQuery.isNumeric(pendingAmountCredit) == false){
                alert('Please enter numeric value in pendingAmountCredit');
                return false;
        }

	if(pendingRecCount == "")
	{
		alert("pendingRecCount should not be blank.");
                return false;
	}
	if(jQuery.isNumeric(pendingRecCount) == false){
                alert('Please enter numeric value in pendingRecCount');
                return false;
        }
	if(pendingRecDay == "" )
        {
                alert("pendingRecDay Name should not be blank.");
                return false;
        }
	if(jQuery.isNumeric(pendingRecDay) == false){
                alert('Please enter numeric value in pendingRecDay');
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
       
	window.location.href="<%=response.encodeURL(request.getContextPath())%>/ViewOfferEvent";
}
</script>
<body background-color="#dddddd" >
<form action="<%=response.encodeURL(request.getContextPath())%>/AddOfferEvent" name="AddNewOfferForm" >
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
<td width="40%" align="center" valign="top" scope="row" ><strong><label>Add New Offer Event</label><strong> &nbsp;</td>
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
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Source</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input type="checkbox" name="Source" value="NON API" checked="checked"> NON API <input type="checkbox" name="Source" value="API"> API 
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Why update offer</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea readonly rows="2" cols="19" name="reason" id="reason" class ="contentblack" style="width: 270px; height: 30px;" >
</textarea><a href="#" id="pop" style="text-decoration: underline;">click here</a>
</td>
</tr>


<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>App Key</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="appKey" type="text" id="appKey" value="<%=key[1]%>" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Balance Credit In Days</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="balanceCreditInDays" type="text" id="balanceCreditInDays" value="0" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Currency</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="currency" type="text" id="currency" value="0" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Event Name</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="eventName" type="text" id="eventName" value="" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Event Value</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="eventValue" type="text" id="eventValue" value="" class ="contentblack"/>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Income Amount</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="incomeAmount" type="text" id="incomeAmount" value="" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Installed Amount</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="installedAmount" type="text" id="installedAmount" value="" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Notification</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="notification" id="notification" class ="contentblack" style="width: 170px; height: 50px;" maxlength="350" ></textarea>
<div id="textarea_feedback"></div>
</td>
</tr>


<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>offer Amount</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="offerAmount" type="text" id="offerAmount" value="" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>offer Cat</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="offerCat" type="text" id="offerCat" value="" class ="contentblack"/>
</td>
</tr>


<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>offer Id</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="offerId" type="text" id="offerId" value="<%=key[0]%>" class ="contentblack"/>
</td>
</tr>


<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Pending Amount Credit</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="pendingAmountCredit" type="text" id="pendingAmountCredit" value="" class ="contentblack"/>
</td>
</tr>


<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Pending Rec Count</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="pendingRecCount" type="text" id="pendingRecCount" value="" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Pending Rec Day</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="pendingRecDay" type="text" id="pendingRecDay" value="" class ="contentblack"/>
</td>
</tr>


<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>status</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
	<select name="status" id="status" class ="contentblack" style="height:25px; width:175px">
		<option value="0">disable</option>
		<option value="1">enable</option>
	</select> 
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>PayOut Holdin Min</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="payOutHoldinMin"  type="number"  min="0" id="offerAmountShow" value="" class ="contentblack"  /> Only Number
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>PayOut Hold Notification</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="payOutHoldNotification" id="payOutHoldNotification" class ="contentblack" style="width: 170px; height: 50px;" ></textarea>
<div id="textarea_payOutHoldNotification"></div>
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
<input type="submit" name="submit" id="submit" value="Submit" onclick="javascript: return Validate();"/></label>&nbsp;&nbsp;&nbsp;<label><input type="button" name="Cancel" value="Cancel" onclick="cancelAction()"></Button>
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
                        <input type="radio" id="rb2" name="reason"value="Offer Started (it should be default one if we start any offer from main GUI)"><label>Offer Started (it should be default one if we start any offer from main GUI)</label>

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
