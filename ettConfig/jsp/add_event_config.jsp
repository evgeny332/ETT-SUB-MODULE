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
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Name</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="offerName" type="text" id="offerName" value="" class ="contentblack"/>
</td>
</tr>


<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Type</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="offerType" id="offerType" class ="contentblack" style="height:25px; width:175px">
	<option value="Offer_Type">select offer type </option>
	<option value="app">app</option>
	<option value="web">web</option>
	<option value="iframe">iframe</option>
</select> 
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Description</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="description" id="description" class ="contentblack" style="width: 170px; height: 50px;" >
</textarea>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Amount</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="offerAmount" type="text" id="offerAmount" value="" class ="contentblack"/>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Apps Key</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="appKey" id="appKey" class ="contentblack" style="width: 170px; height: 50px;" ></textarea>
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
<td width="33%" align="right" valign="top" scope="row"><label><b>Image Url</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="imageUrl" type="text" id="imageUrl" value="" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Priority</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select  name="priority" id="priority" class ="contentblack" style="height:25px; width:175px">
	<option value="priority">please select priority</option>
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
<td width="33%" align="right" valign="top" scope="row"><label><b>Operators</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select  name="Operators" id="Operators" class ="contentblack" style="height:25px; width:175px">
	<option value="Operators">please select Operators</option>
	<option value="All">All</option>
	<option value="Airtel">Airtel</option>
        <option value="Aircel">Aircel</option>
        <option value="Uninar">Uninar</option>
        <option value="MTNL">MTNL</option>

</select>
</td>
</tr>	

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>status</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="status" id="status" class ="contentblack" style="height:25px; width:175px"> 
  	<option value="status">please select status</option>
	<option value="1">enable</option>
    	<option value="0">disable</option>
</select> 
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Size</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="size" type="text" id="size" value="" class ="contentblack"/>
</td>
</tr>



<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Network Type</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="networkType" type="text" id="networkType" value="" class ="contentblack"/>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Alert Text</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="alertText" id="alertText" class ="contentblack" maxlength="350" style="width: 170px; height: 50px;" ></textarea>
<div id="textarea_feedback"></div>
<%--<input name="alertText" type="text" id="alertText" value="" class ="contentblack"/>--%>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Balance Type</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<%--<input name="balanceType" type="text" id="balanceType" value="" class ="contentblack"/>--%>
<select name="balanceType" id="balanceType" class ="contentblack" style="height:25px; width:175px">
        <option value="balanceType">please select balance type</option>
        <option value="0">Call Back</option>
        <option value="1">Install</option>
	<option value="2">Any</option>
</select>
</td>
</tr>
<tr style="display:none;">
<td width="33%" align="right" valign="top" scope="row"><label><b>category</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="category" type="text" id="category" value="" class ="contentblack"/>optional
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Category</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<%--<input name="root" type="text" id="root" value="" class ="contentblack"/>--%>
<select name="root" id="root" class ="contentblack" style="height:25px; width:175px">
        <option value="download & earn">download & earn</option>
        <option value="send invite text to share">send invite text to share</option>
        <option value="download, use & earn">download use & earn</option>
	<option value="use & earn">use & earn</option>

        <option value="invite & earn">invite & earn</option>
        <option value="share & earn">share & earn</option>
        <option value="download, register & earn">download register & earn</option>
       <option value="Ask a Question & Earn">Ask a Question & Earn</option> 
	<option value="fill survey & earn">fill survey & earn</option>
        <option value="download, register, use & earn">download register use & earn</option>
	<option value="special offer">special offer</option>

	<option value="register & earn">register & earn</option>
	<option value="Loan Service">Loan Service</option>

</select>

</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>balanceCreditInDays</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="balanceCreditInDays" type="text" id="balanceCreditInDays" onchange="ChangeBalanceCreditInDay()" value="0" class="contentblack" />
</td>
</tr>

<tr id="balanceCreditInDays1">
<td width="33%" align="right" valign="top" scope="row"><label><b>Pending Amt Credit</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="pendingAmountCredit" type="text" id="pendingAmountCredit" value="" class ="contentblack"  />
</td>
</tr>

<tr  id="balanceCreditInDays2">
<td width="33%" align="right" valign="top" scope="row"><label><b>Pending Rec Count</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="pendingRecCount" type="text" id="pendingRecCount" value="" class ="contentblack"  />
</td>
</tr>

<tr id="balanceCreditInDays3">
<td width="33%" align="right" valign="top" scope="row"><label><b>Pending Rec Day</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="pendingRecDay" type="text" id="pendingRecDay" value="" class ="contentblack"  />
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Callback Notification</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="callbackNotification" id="callbackNotification" class ="contentblack" style="width: 170px; height: 50px;" ></textarea>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Installed Notification</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="installedNotification" id="installedNotification" class ="contentblack" style="width: 170px; height: 50px;" ></textarea>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Vendor Name</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="venderName" type="text" id="venderName" value="" class ="contentblack"  />
</td>
</tr>



<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Gender</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="gender" id="gender" class ="contentblack" style="height:25px; width:175px">
        <option value="All">please select gender</option>
        <option value="Male">Male</option>
        <option value="Female">Female</option>
</select>

</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Operator Circle</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="operatorCircle" type="text" id="operatorCircle" value="" class ="contentblack"  />
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Age Limit</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="ageLimit" type="text" id="ageLimit" value="" class ="contentblack"  />eg- 20-40
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Play StoreDetails</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="playStoreDetails" type="text" id="playStoreDetails" value="" class ="contentblack"  />
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Rating</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="rating" type="text" id="rating" value="" class ="contentblack"  />
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Instructions</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<%--<input name="instructions" type="text" id="instructions" value="" class ="contentblack"  />--%>
<textarea rows="2" cols="19" name="instructions" id="instructions" class ="contentblack" style="width: 170px; height: 50px;" ></textarea>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>App Description</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="appDescription" id="appDescription" class ="contentblack" style="width: 170px; height: 50px;" ></textarea>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>PayOutOn</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="payOutOn" id="payOutOn" class ="contentblack" style="height:25px; width:175px">
        <option value="use">select Payout on</option>
        <option value="open">open</option>
        <option value="register">register</option>
        <option value="use">use</option>
        <option value="healthQuery">healthQuery</option>
	
</select>

</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>PayoutType</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="payoutType" id="payoutType" class ="contentblack" style="height:25px; width:175px">
        <option value="INSTALL">select Payout Type</option>
        <option value="INSTALL">INSTALL</option>
        <option value="DEFFERED">DEFFERED</option>
	<option value="SHARE">SHARE</option>
	<option value="INVITE">INVITE</option>
	<option value="SURVEY">SURVEY</option>
	<option value="SHAREDIRECT">SHAREDIRECT</option>
	 <option value="LOANSERVICE">LOANSERVICE</option>
	 <option value="SPECIALOFFER">SPECIALOFFER</option>
	<option value="CLICK">CLICK</option>
</select>

</td>
</tr>
<tr id="payoutType1">
<td width="33%" align="right" valign="top" scope="row"><label><b>User Defered Info</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="userDeferedInfo" id="userDeferedInfo" class ="contentblack" style="width: 170px; height: 50px;" ></textarea>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Package Name</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="packageName" type="text" id="packageName" value="" class ="contentblack"  />
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Payment Type</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="offerPaymentType" id="offerPaymentType" class ="contentblack" style="height:25px; width:175px">
        <option value="CPI">CPI</option>
        <option value="CPR">CPR</option>
</select>

</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>maxCreditLimit</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="maxCreditLimit" type="text" id="maxCreditLimit" value="" onChange="onChange_maxCreditLimit();" class ="contentblack"  />
</td>
</tr>

<tr id="maxCreditLimit1">
<td width="33%" align="right" valign="top" scope="row"><label><b>maxCreditPerDayLimit</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="maxCreditPerDayLimit" type="text" id="maxCreditPerDayLimit" value="" class ="contentblack"  />
</td>
</tr>

<tr id="maxCreditLimit2">
<td width="33%" align="right" valign="top" scope="row"><label><b>amountPerDataThreshold</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="amountPerDataThreshold" type="text" id="amountPerDataThreshold" value="" class ="contentblack"  />
</td>
</tr>

<tr id="maxCreditLimit3">
<td width="33%" align="right" valign="top" scope="row"><label><b>dataThreshold</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="dataThreshold" type="text" id="dataThreshold" value="" class ="contentblack"  />
</td>
</tr>

<tr id="maxCreditLimit4">
<td width="33%" align="right" valign="top" scope="row"><label><b>dataUsageEligibleDays</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="dataUsageEligibleDays" type="text" id="dataUsageEligibleDays" value="" class ="contentblack"  />
</td>
</tr>

<tr id="maxCreditLimit5">
<td width="33%" align="right" valign="top" scope="row"><label><b>timeIntervalOfRecheck</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="timeIntervalOfRecheck" type="text" id="timeIntervalOfRecheck" value="" class ="contentblack"  />
</td>
</tr>




<tr>


<%--<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Detailed Instructions</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">

<textarea rows="2" cols="19" name="detailedInstructions" id="detailedInstructions" class ="contentblack" style="width: 170px; height: 50px;" ></textarea> Only
 Used for Invite OfferID=230
</td>
</tr>--%>

<tr id="maxCreditLimit6">
<td width="33%" align="right" valign="top" scope="row"><label><b>Is WifiDataAccepted</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="isWifiDataAccepted" type="number" id="isWifiDataAccepted" value="" class ="contentblack"  min="0"/> 0=Mobile Data,1=both
</td>
</tr>

<tr id="maxCreditLimit7">
<td width="33%" align="right" valign="top" scope="row"><label><b>Data OfferType</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="dataOfferType" type="number" id="dataOfferType" value="" class ="contentblack" min="0" onchange="getDataOfferType()" /> 1=type 1 | 2=type 2 offer
</td>
</tr>

<tr id="maxCreditLimit8">
<td width="33%" align="right" valign="top" scope="row"><label><b>WinBack Url</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="winBackURL" type="text" id="winBackURL" value="" class ="contentblack"/> Only use type 2 offer
</td>
</tr>


<%--<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Version</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="version" type="text" id="version" value="" class ="contentblack"  />
</td>
</tr>--%>

<tr id="maxCreditLimit9">
<td width="33%" align="right" valign="top" scope="row"><label><b>DeepLink Url</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="deepLinkUrl" type="text" id="deepLinkUrl" value="" class ="contentblack"  />
</td>
</tr>

<tr id="maxCreditLimit10">
<td width="33%" align="right" valign="top" scope="row"><label><b>is RepeatOffer</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="isRepeatOffer" type="number" id="isRepeatOffer" value="" class ="contentblack" min="0" /> 0=Not Repeat | 1=Repeat
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Detailed Instructions</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">

<textarea rows="2" cols="19" name="detailedInstructions" id="detailedInstructions" class ="contentblack" style="width: 170px; height: 50px;" ></textarea> Only
 Used for Invite OfferID=230
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Version</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="version" type="text" id="version" value="" class ="contentblack"  />
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Select Location</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top" id="GeoLoaction">
<input type="radio" name="location" value="Non" checked>Non
<input type="radio" name="location" value="City">City
<input type="radio" name="location" value="Pin Code">Pin Code
<input type="radio" name="location" value="State">State
</td>
</tr>




<tr id="GeoLoaction_city">
<td width="33%" align="right" valign="top" scope="row"><label><b>City</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="city" type="text" id="city" value="" class ="contentblack"  />
</td>
</tr>
<tr id="GeoLoaction_pin">
<td width="33%" align="right" valign="top" scope="row"><label><b>Pin Code</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="pincode" type="number" id="pincode" value="" class ="contentblack" min="0" />
</td>
</tr>

<tr id="GeoLoaction_state">
<td width="33%" align="right" valign="top" scope="row"><label><b>State</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="state" type="text" id="state" value="" class ="contentblack"  />
</td>
</tr>



<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Amount Show</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="offerAmountShow" type="number" step="any" id="offerAmountShow" value="" class ="contentblack"  /> Only Number/Float
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Multiple Event Offer</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="multipleEventOffer"  type="number" step="any" id="multipleEventOffer" value="0" class ="contentblack"  />0= No Event | 1=multiple Event
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
