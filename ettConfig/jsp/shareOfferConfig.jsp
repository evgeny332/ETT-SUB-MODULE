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
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
    iosHide();
    AndroidHide();
    $('#osType').change(function () {
	var osType  = $("#osType").val();
        if(osType=="All")
        {
                iOSShow();
                AndroidShow();
        }
        else if(osType=="iOS")
        {
                iOSShow();
                AndroidHide();
        }
        else if(osType=="Android")
        {
                AndroidShow()
                iosHide()
        }
	else
	{
		AndroidHide();
		iosHide()
	}

    });
    var text_max = 350;
    $('#textarea_feedback').html(text_max + ' characters remaining');

    $('#alertText').keyup(function() {
        var text_length = $('#alertText').val().length;
        var text_remaining = text_max - text_length;

        $('#textarea_feedback').html(text_remaining + ' characters remaining');
    });

    var shareText = 105;

    $('#textarea_feedback1').html(shareText + ' characters remaining');

    $('#shareText').keyup(function() {
        var text_length = $('#shareText').val().length;
        var text_remaining = shareText - text_length;

        $('#textarea_feedback1').html(text_remaining + ' characters remaining');
    });

    var shareMessage = 105;

    $('#textarea_feedback2').html(shareMessage + ' characters remaining');

    $('#shareMessage').keyup(function() {
        var text_length = $('#shareMessage').val().length;
        var text_remaining = shareMessage - text_length;

        $('#textarea_feedback2').html(text_remaining + ' characters remaining');
    });

    var callBackNotification = 250;

    $('#textarea_feedback3').html(callBackNotification + ' characters remaining');

    $('#callBackNotification').keyup(function() {
        var text_length = $('#callBackNotification').val().length;
        var text_remaining = callBackNotification - text_length;

        $('#textarea_feedback3').html(text_remaining + ' characters remaining');
    });
	
   var description = 150;

    $('#textarea_feedback4').html(description + ' characters remaining');

    $('#description').keyup(function() {
        var text_length = $('#description').val().length;
        var text_remaining = description - text_length;

        $('#textarea_feedback4').html(text_remaining + ' characters remaining');
    });


});

</script>

</head>
<script language=javascript>
javascript:window.history.forward(1);
window.onbeforeunload = function (){}
</script>

<script>
function iosHide()
{
	$("#URLiOS").hide();
        $("#iOSVendor").hide();
	$("#AmountiOS").hide();
}
function iOSShow()
{
        $("#URLiOS").show();
        $("#iOSVendor").show();
	$("#AmountiOS").show();
}
function AndroidHide()
{
        $("#URLAndroid").hide();
        $("#AndroidVendor").hide();
	$("#AmountAndroid").hide();
}
function AndroidShow()
{
        $("#URLAndroid").show();
        $("#AndroidVendor").show();
	$("#AmountAndroid").show();
}


function hideShow()
{
	var osType  = $("#osType").val();
	if(osType=="All")
	{
		iOSShow();
		AndroidShow();
	}
	else if(osType=="iOS")
	{
		iOSShow();
		AndroidHide();
	}
	else if(osType=="Android")
	{
		AndroidShow()
		iosHide()
	}
	
}
function Validate()
{	var offerName		= $("#offerName").val();
	var offerInfo           = $("#offerInfo").val();
	var description 	= $("#description").val();
	var offerAmount 	= $("#offerAmount").val()
	var offerType           = $("#offerType").val();
	var payouttype          = $("#payouttype").val();
	var osType		= $("#osType").val();
	var priority		= $("#priority").val();
	var status		= $("#status").val();
	var alertText		= $("#alertText").val();
	var shareText           = $("#shareText").val();
	var shareMessage        = $("#shareMessage").val();
	var callBackNotification= $("#callBackNotification").val();
	var imageURL		= $("#imageURL").val();
	var actionURLAndroid	= $("#actionURLAndroid").val();
	var vendorNameAndroid	= $("#vendorNameAndroid").val();
	var actionURLiOS 	= $("#actionURLiOS").val();
	var vendorNameiOS	= $("#vendorNameiOS").val();
	var shareURL		= $("#shareURL").val();
	var balanceType		= $("#balanceType").val();
	var iOSAmount	        = $("#iOSAmount").val()
	if(offerName == "")
	{
		alert("offer Name should not be blank.");
		return false;
	}
	if(offerInfo == "")
        {
                alert("offer Info should not be blank.");
                return false;
        }

	if(description == "")
        {
                alert("description should not be blank.");
                return false;
        }
/*	if(offerAmount  == "")
	{
		alert("offer Amount should not be blank.");
		 return false;
	}  */
	if(offerType == "" || offerType=="Offer_Type")
        {
                alert("offerType should not be blank.");
                return false;
        }

	if(payouttype == "" || payouttype=="Offer_Pay") 
	{
		alert("Payout type should not be blank.");
		return false;
	}
	if(osType == "" || osType=="Offer_os")
	{
		alert("OS Type should not be blank.");
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
	if(alertText == "" )
        {
                alert("alertText should not be blank.");
                return false;
        }
	if(shareText == "")
	{
		alert("Share Text should not be blank.");
                return false;
	}
	if(shareMessage == "")
        {
                alert("Share Message should not be blank.");
                return false;
        }
	if(callBackNotification == "")
        {
                alert("CallBack Notification should not be blank.");
                return false;
        }
	if(imageURL == "")
        {
                alert("Image URL should not be blank.");
                return false;
        }
	if(osType == "All")
	{
		if(actionURLAndroid == "")
        	{
                	alert("Action URL Android should not be blank.");
                	return false;
        	}
		if(vendorNameAndroid == "")
        	{
                	alert("Vendor Name Android should not be blank.");
                	return false;
        	}
		if(actionURLiOS == "")
                {
                        alert("Action URL iOS should not be blank.");
                        return false;
                }

                if(vendorNameiOS == "")
                {
                        alert("Vendor Name iOS should not be blank.");
                        return false;
                }
		if(offerAmount  == "")
	        {
        	        alert("Android Amount should not be blank.");
        	         return false;
        	} 
		
		if(iOSAmount  == "")
        	{
                	alert("iOS Amount should not be blank.");
                	 return false;
        	} 


	}
	if(osType == "Android")
        {
		if(offerAmount  == "")
                {
                        alert("Android Amount should not be blank.");
                         return false;
                }

		if(actionURLAndroid == "")
                {
                        alert("Action URL Android should not be blank.");
                        return false;
                }
                if(vendorNameAndroid == "")
                {
                        alert("Vendor Name Android should not be blank.");
                        return false;
                }
	
	}
	if(osType == "iOS")
        {
		if(iOSAmount  == "")
                {
                        alert("iOS Amount should not be blank.");
                         return false;
                }

		if(actionURLiOS == "")
                {
                        alert("Action URL iOS should not be blank.");
                        return false;
                }

                if(vendorNameiOS == "")
                {
                        alert("Vendor Name iOS should not be blank.");
                        return false;
                }

	}
	if(shareURL == "")
        {
                alert("Share URL should not be blank.");
                return false;
        }

	if(balanceType == "" || balanceType=="balanceType")
	{
		alert("Balance Type should not be blank");
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
	window.location.href = "<%=response.encodeURL(request.getContextPath())%>/AddShareOffer";
}
</script>
<body background-color="#dddddd" >
<form action="<%=response.encodeURL(request.getContextPath())%>/AddShareOffer" name="AddNewOfferForm" method="post">
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
<td width="40%" align="center" valign="top" scope="row" ><strong><label>New Share Offer Configured</label><strong> &nbsp;</td>
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
<td width="33%" align="right" valign="top" scope="row"><label><b>offer Info</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="offerInfo" type="text" id="offerInfo" value="" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Description</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="description" id="description" class ="contentblack" maxlength="150" style="width: 170px; height: 50px;" >
</textarea>
<div id="textarea_feedback4"></div>
</td>
</tr>
<%--<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Amount</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="offerAmount" type="text" id="offerAmount" value="" class ="contentblack"/>
</td>
</tr>--%>


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
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Payment Type</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="payouttype" id="payouttype" class ="contentblack" style="height:25px; width:175px">
	<option value="Offer_Pay">select Payment type </option>
        <option value="CPI">CPI</option>
        <option value="CPR">CPR</option>
</select>

</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>OS Type</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="osType" id="osType" class ="contentblack" style="height:25px; width:175px">
	<option value="Offer_os">select OS type </option>
        <option value="iOS">iOS</option>
        <option value="Android">Android</option>
	<option value="All">All</option>
</select>

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
<td width="33%" align="right" valign="top" scope="row"><label><b>Alert Text</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="alertText" id="alertText" class ="contentblack" maxlength="350" style="width: 170px; height: 50px;" ></textarea>
<div id="textarea_feedback"></div>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Share Text</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="shareText" id="shareText" class ="contentblack" maxlength="250" style="width: 170px; height: 50px;" ></textarea>
<div id="textarea_feedback1"></div>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Share Message</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="shareMessage" id="shareMessage" class ="contentblack" maxlength="250" style="width: 170px; height: 50px;" ></textarea>
<div id="textarea_feedback2"></div>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>CallBack Notification</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="callBackNotification" id="callBackNotification" class ="contentblack" maxlength="250" style="width: 170px; height: 50px;" ></textarea>
<div id="textarea_feedback3"></div>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Image URL</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="imageURL" type="text" id="imageURL" value="" class ="contentblack"/>
<a href="http://54.209.220.78:8888/earntalktime/imageupload.html" target="_blank" id="multiple_images" style="text-decoration: underline;">click here upload images</a>
<br/>Please copy image url then paste in textbox

</td>
</tr>

<tr id="AmountAndroid">
<td width="33%" align="right" valign="top" scope="row"><label><b>Android Amount</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="offerAmount" type="text" id="offerAmount" value="" class ="contentblack"/>
</td>
</tr>

<tr id="URLAndroid">
<td width="33%" align="right" valign="top" scope="row"><label><b>Action URL Android</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="actionURLAndroid" type="text" id="actionURLAndroid" value="" class ="contentblack"/>
</td>
</tr>

<tr id="AndroidVendor">
<td width="33%" align="right" valign="top" scope="row"><label><b>Vendor Name Android</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="vendorNameAndroid" type="text" id="vendorNameAndroid" value="" class ="contentblack"/>
</td>
</tr>

<tr id="AmountiOS">
<td width="33%" align="right" valign="top" scope="row"><label><b>iOS Amount</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="iOSAmount" type="text" id="iOSAmount" value="" class ="contentblack"/>
</td>
</tr>


<tr  id="URLiOS">
<td width="33%" align="right" valign="top" scope="row"><label><b>Action URL iOS</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="actionURLiOS" type="text" id="actionURLiOS" value="" class ="contentblack"/>
</td>
</tr>

<tr id="iOSVendor">
<td width="33%" align="right" valign="top" scope="row"><label><b>Vendor Name iOS</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="vendorNameiOS" type="text" id="vendorNameiOS" value="" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Share URL</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="shareURL" type="text" id="shareURL" value="" class ="contentblack"/>
</td>
</tr>



<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Balance Type</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="balanceType" id="balanceType" class ="contentblack" style="height:25px; width:175px">
        <option value="balanceType">please select balance type</option>
        <option value="0">Call Back</option>
        <option value="1">Install</option>
	<option value="2">Any</option>
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
</body>
</html>
