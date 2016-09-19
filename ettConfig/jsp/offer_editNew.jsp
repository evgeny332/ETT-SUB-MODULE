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
var balanceCreditInDays=$("#balanceCreditInDays").val();
var payoutType=$("#payoutType").val();
var maxCreditLimit=$("#maxCreditLimit").val();
onChange_maxCreditLimit();

if(balanceCreditInDays==0)
{
         balanceCreditInDaysHide();
}
else{
         balanceCreditInDaysShow();
}
if(payoutType=="DEFFERED")
{
        $("#payoutType1").show();
}
else
{
        $("#payoutType1").hide();
}

   $('#payoutType').change(function() {

        var payoutType=$("#payoutType").val();
        if(payoutType=="DEFFERED")
        {
                $("#payoutType1").show();
                $("#payoutType1 .contentblack").css("border","1px solid rgb(0, 255, 0)");
        }
        else
        {
                $("#payoutType1").hide();
        }

    });


var payOutHold_max = 250;
    $('#textarea_payOutHoldNotification').html(payOutHold_max + ' characters remaining');

    $('#payOutHoldNotification').keyup(function() {
        var text_length = $('#payOutHoldNotification').val().length;
        var text_remaining = payOutHold_max - text_length;

        $('#textarea_payOutHoldNotification').html(text_remaining + ' characters remaining');
    });

    var text_max = 350;
    $('#textarea_feedback').html(text_max + ' characters remaining');

    $('#alertText').keyup(function() {
        var text_length = $('#alertText').val().length;
        var text_remaining = text_max - text_length;

        $('#textarea_feedback').html(text_remaining + ' characters remaining');
    });
var description_max = 160;
    $('#textarea_description').html(description_max + ' characters remaining');

    $('#description').keyup(function() {
        var text_length = $('#description').val().length;
        var text_remaining = description_max - text_length;

        $('#textarea_description').html(text_remaining + ' characters remaining');
    });
var callBack_max = 250;
    $('#textarea_callBack').html(callBack_max + ' characters remaining');

    $('#callbackNotification').keyup(function() {
        var text_length = $('#callbackNotification').val().length;
        var text_remaining = callBack_max - text_length;

        $('#textarea_callBack').html(text_remaining + ' characters remaining');
    });
var install_max = 250;
    $('#textarea_install').html(install_max + ' characters remaining');

    $('#installedNotification').keyup(function() {
        var text_length = $('#installedNotification').val().length;
        var text_remaining = install_max - text_length;

        $('#textarea_install').html(text_remaining + ' characters remaining');
    });
var instructions_max = 350;
    $('#textarea_instructions').html(instructions_max + ' characters remaining');

    $('#instructions').keyup(function() {
        var text_length = $('#instructions').val().length;
        var text_remaining = instructions_max - text_length;

        $('#textarea_instructions').html(text_remaining + ' characters remaining');
    });
var appDescription_max = 250;
    $('#textarea_appDescription').html(appDescription_max + ' characters remaining');

    $('#appDescription').keyup(function() {
        var text_length = $('#appDescription').val().length;
        var text_remaining = appDescription_max - text_length;

        $('#textarea_appDescription').html(text_remaining + ' characters remaining');
    });
var userDeferedInfo_max = 250;
    $('#textarea_userDeferedInfo').html(userDeferedInfo_max + ' characters remaining');

    $('#userDeferedInfo').keyup(function() {
        var text_length = $('#userDeferedInfo').val().length;
        var text_remaining = userDeferedInfo_max - text_length;

        $('#textarea_userDeferedInfo').html(text_remaining + ' characters remaining');
    });

});

function onChange_balanceCreditInDay()
{
        var balanceCreditInDays=$("#balanceCreditInDays").val();
        if(balanceCreditInDays==0)
        {
                 balanceCreditInDaysHide();
        }
        else{
                 balanceCreditInDaysShow();
        }

}
function balanceCreditInDaysHide()
{
        $("#balanceCreditInDays1").hide();
        $("#balanceCreditInDays2").hide();
        $("#balanceCreditInDays3").hide();

}

function balanceCreditInDaysShow()
{
        $("#balanceCreditInDays1").show();
        $("#balanceCreditInDays2").show();
        $("#balanceCreditInDays3").show();
        $("#balanceCreditInDays1 .contentblack").css("border","1px solid rgb(0, 255, 0)");
        $("#balanceCreditInDays2 .contentblack").css("border","1px solid rgb(0, 255, 0)");
        $("#balanceCreditInDays3 .contentblack").css("border","1px solid rgb(0, 255, 0)");

}
function onChange_maxCreditLimit()
{
        var maxCreditLimit=$("#maxCreditLimit").val();
        if(maxCreditLimit==0)
        {
                 maxCreditLimitHide();
        }
        else{
                 maxCreditLimitShow();
        }

}

function maxCreditLimitHide()
{
	getDataOfferType();
	$("#maxCreditLimit1").hide();
	$("#maxCreditLimit1").hide();
	$("#maxCreditLimit2").hide();
	$("#maxCreditLimit3").hide();
	$("#maxCreditLimit4").hide();
	$("#maxCreditLimit5").hide();
	$("#maxCreditLimit6").hide();
	$("#maxCreditLimit7").hide();
	/* $("#maxCreditLimit8").hide();
	$("#maxCreditLimit9").hide();
	$("#maxCreditLimit10").hide();*/
}
function maxCreditLimitShow()
{
	getDataOfferType();
        $("#maxCreditLimit1").show();
        $("#maxCreditLimit1").show();
        $("#maxCreditLimit2").show();
        $("#maxCreditLimit3").show();
        $("#maxCreditLimit4").show();
        $("#maxCreditLimit5").show();
        $("#maxCreditLimit6").show();
        $("#maxCreditLimit7").show();
        /*$("#maxCreditLimit8").show();
        $("#maxCreditLimit9").show();
        $("#maxCreditLimit10").show();*/
	$("#maxCreditLimit1 .contentblack").css("border","1px solid rgb(0, 255, 0)");
	$("#maxCreditLimit2 .contentblack").css("border","1px solid rgb(0, 255, 0)");
	$("#maxCreditLimit3 .contentblack").css("border","1px solid rgb(0, 255, 0)");
	$("#maxCreditLimit4 .contentblack").css("border","1px solid rgb(0, 255, 0)");
	$("#maxCreditLimit5 .contentblack").css("border","1px solid rgb(0, 255, 0)");
	$("#maxCreditLimit6 .contentblack").css("border","1px solid rgb(0, 255, 0)");
	$("#maxCreditLimit7 .contentblack").css("border","1px solid rgb(0, 255, 0)");
	/*$("#maxCreditLimit8 .contentblack").css("border","1px solid rgb(0, 255, 0)");
	$("#maxCreditLimit9 .contentblack").css("border","1px solid rgb(0, 255, 0)");
	$("#maxCreditLimit10 .contentblack").css("border","1px solid rgb(0, 255, 0)");*/
}
function getDataOfferType()
{
	var dataOfferType=$("#dataOfferType").val();
	var maxCreditLimit=$("#maxCreditLimit").val();
	//alert(dataOfferType)

	if(dataOfferType ==1 )
	{
        	$("#maxCreditLimit8").hide();
        	$("#maxCreditLimit9").hide();
        	$("#maxCreditLimit10").hide();
	}
	else if(dataOfferType ==2 && maxCreditLimit >0)
	{
        	$("#maxCreditLimit8").show();
        	$("#maxCreditLimit9").show();
        	$("#maxCreditLimit10").show();
		$("#maxCreditLimit8 .contentblack").css("border","1px solid rgb(0, 255, 0)");
        	$("#maxCreditLimit9 .contentblack").css("border","1px solid rgb(0, 255, 0)");
        	$("#maxCreditLimit10 .contentblack").css("border","1px solid rgb(0, 255, 0)");
	}
	else
	{
		$("#maxCreditLimit8").hide();
                $("#maxCreditLimit9").hide();
                $("#maxCreditLimit10").hide();
	}
	
}


</script>
<script type="text/javascript">
$(document).ready(function(){
	var pincode=$("#pincode").val();
	var state =$("#state").val();
	var city  =$("#city").val();
	/*alert(pincode.length +"#"+state.length+"#"+city.length+"#");*/
	if(pincode.length>0)	
	{
		$( "#PinCode1" ).prop( "checked", true );
		$('#GeoLoaction_pin').show();
		$('#GeoLoaction_state').hide();
		$('#GeoLoaction_city').hide();
	}
	else if(state.length>0 )
	{
		$( "#State1" ).prop( "checked", true );
		$('#GeoLoaction_state').show();
		$('#GeoLoaction_pin').hide();
                $('#GeoLoaction_city').hide();
	}
	else if(city.length>0)
	{
		$( "#City1" ).prop( "checked", true );
		$('#GeoLoaction_city').show();
		$('#GeoLoaction_state').hide();
                $('#GeoLoaction_pin').hide();
	}
	else
	{
		$('#GeoLoaction_state').hide();
                $('#GeoLoaction_pin').hide();
                $('#GeoLoaction_city').hide();
		
	}
	
            $("input[type='radio']").click(function(){

                var radioValue = $("input[name='reason']:checked").val();

                if(radioValue){
			$("#reason").val(radioValue);
                    	$("#overlay_form").fadeOut(500);

                }

            });

      $('#GeoLoaction input').on('change', function() {
	
	var loc=$('input[name=location]:checked', '#GeoLoaction').val();
	if(loc=='City')
	{
		$('#GeoLoaction_city').show();
		$('#GeoLoaction_pin').hide();
		$('#GeoLoaction_state').hide();
	}
	if(loc=='Pin Code')
	{
		$('#GeoLoaction_city').hide();
		$('#GeoLoaction_pin').show();
		$('#GeoLoaction_state').hide();
	}
	if(loc=='State')
	{
		$('#GeoLoaction_city').hide();
		$('#GeoLoaction_pin').hide();
		$('#GeoLoaction_state').show();
	}
	if(loc=='Non')
	{
		$('#GeoLoaction_city').hide();
		$('#GeoLoaction_pin').hide();
		$('#GeoLoaction_state').hide();
	}

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
        var category            = $("#category").val();
        var balanceCreditInDays = $("#balanceCreditInDays").val();
        var root                = $("#root").val();
        var venderName          = $("#venderName").val();
	var reason		=$("#reason").val();
	var balanceCreditInDays	=$("#balanceCreditInDays").val();
        var pendingAmountCredit	=$("#pendingAmountCredit").val();
        var pendingRecCount	=$("#pendingRecCount").val();
        var pendingRecDay	=$("#pendingRecDay").val();
        var maxCreditLimit	=$("#maxCreditLimit").val();
        var maxCreditPerDayLimit=$("#maxCreditPerDayLimit").val();
        var amountPerDataThreshold=$("#amountPerDataThreshold").val();
        var dataThreshold	=$("#dataThreshold").val();
        var dataUsageEligibleDays=$("#dataUsageEligibleDays").val();
        var timeIntervalOfRecheck=$("#timeIntervalOfRecheck").val();
        var isWifiDataAccepted=$("#isWifiDataAccepted").val();
        var dataOfferType	=$("#dataOfferType").val();
        var winBackURL		=$("#winBackURL").val();
        var deepLinkUrl		=$("#deepLinkUrl").val();
        var isRepeatOffer	=$("#isRepeatOffer").val();
        var offerAmountShow	=$("#offerAmountShow").val();
        var multipleEventOffer	=$("#multipleEventOffer").val();	
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
	if(balanceType == "")
	{
		alert("Balance Type should not be blank.");
                return false;
	}
	if(jQuery.isNumeric(balanceType) == false){
                alert('Please enter numeric value in balanceType');
                return false;
        }
	if(balanceCreditInDays == "")
        {
                alert("balanceCreditInDays should not be blank");
                return false;
        }
        if(jQuery.isNumeric(balanceCreditInDays) == false){
                alert('Please enter numeric value');
                return false;
        }
        if(balanceCreditInDays >0)
        {
                if(pendingAmountCredit =="")
                {
                        alert("pendingAmountCredit should not be blank");
                        return false;
                }
                if(pendingRecCount =="")
                {
                        alert("pendingRecCount should not be blank");
                        return false;
                }
                 if(pendingRecDay =="")
                {
                        alert("pendingRecDay should not be blank");
                        return false;
                }

        }

	if(venderName == "" )
        {
                alert("Vendor Name should not be blank.");
                return false;
        }
	if(maxCreditLimit == "")
        {
                alert("maxCreditLimit should not be blank.");
                return false;
        }
        if(jQuery.isNumeric(maxCreditLimit) == false){
                alert('Please enter numeric value in maxCreditLimit');
                return false;
        }
        if(maxCreditLimit>0)
        {
                if(maxCreditPerDayLimit =="")
                {
                        alert("maxCreditPerDayLimit should not be blank");
                        return false;
                }
                if(amountPerDataThreshold =="")
                {
                        alert("amountPerDataThreshold should not be blank");
                        return false;
                }
                if(dataThreshold =="")
                {
                        alert("dataThreshold should not be blank");
                        return false;
                }
                if(dataUsageEligibleDays =="")
                {
                        alert("dataUsageEligibleDays should not be blank");
                        return false;
                }
                if(timeIntervalOfRecheck =="")
                {
                        alert("timeIntervalOfRecheck should not be blank");
                        return false;
                }
                if(isWifiDataAccepted =="")
                {
                        alert("isWifiDataAccepted should not be blank");
                        return false;
                }
		if(dataOfferType =="")
                {
                        alert("dataOfferType should not be blank");
                       return false;
                }
                if(jQuery.isNumeric(dataOfferType) == false)
                {
                        alert('Please enter numeric value in dataOfferType');

                }
                if(dataOfferType ==2)
                {
                        if(winBackURL =="")
                        {
                                alert("winBackURL should not be blank");
                                return false;
                        }
                        if(isRepeatOffer =="")
                        {
                                alert("isRepeatOffer should not be blank");
                                return false;
                        }
                }
        }
        if(offerAmountShow =="")
        {
                alert("offerAmountShow should not be blank");
                return false;
        }
        if(multipleEventOffer == "")
        {
                alert("multipleEventOffer should not be blank");
                return false;
        }
	if(jQuery.isNumeric(multipleEventOffer) == false){
                alert('Please enter numeric value in multipleEventOffer');
                return false;
        }


}
function cancelAction()
{
	document.AddNewOfferForm.action="";
        //return false;
        //window.location.href = "<%=response.encodeURL(request.getContextPath())%>/ViewOffer";
       
	window.location.href="<%=response.encodeURL(request.getContextPath())%>/ViewOfferNew";
}
</script>
<body background-color="#dddddd" >
<form action="<%=response.encodeURL(request.getContextPath())%>/UpdateOfferNew" name="AddNewOfferForm" >
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
<td width="40%" align="center" valign="top" scope="row" ><strong><label>Update Offer</label><strong> &nbsp;</td>
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
String str25="";
String str26="";
String str27="";
String str28="";
String str29="";
String str30="";
String str31="";
String str32="";
String str33="";
String str34="";
String str35="";
String str36="";
String str37="";
String str38="";
String str39="";
String str40="";
String str41="";
String str42="";
String str43="";
String str44="";
String str45="";
String str46="";
String str47="";

String str48="";
String str49="";
String str50="";
String str51="";
String str52="";
String str53="";
String str54="";
String str55="";
String str56="";
String str57="";
String str58="";
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
str25=(String)list.get(24);
str26=(String)list.get(25);
str27=(String)list.get(26);
str28=(String)list.get(27);
str29=(String)list.get(28);
str30=(String)list.get(29);
str31=(String)list.get(30);
str32=(String)list.get(31);
str33=(String)list.get(32);
str34=(String)list.get(33);
str35=(String)list.get(34);
str36=(String)list.get(35);

str37=(String)list.get(36);
str38=(String)list.get(37);
str39=(String)list.get(38);
str40=(String)list.get(39);
str41=(String)list.get(40);
str42=(String)list.get(41);

str43=(String)list.get(42);
str44=(String)list.get(43);
str45=(String)list.get(44);
str46=(String)list.get(45);
str47=(String)list.get(46);
str48=(String)list.get(47);
str49=(String)list.get(48);
str50=(String)list.get(49);
str51=(String)list.get(50);
str52=(String)list.get(51);
str53=(String)list.get(52);
str54=(String)list.get(53);
str55=(String)list.get(54);
str56=(String)list.get(55);

str57=(String)list.get(56);
str58=(String)list.get(57);
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
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Name</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="offerName" type="text" id="offerName" value="<%=str2%>" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Type</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="offerType" id="offerType" class ="contentblack" style="height:25px; width:175px">
	<option value="<%=str3%>"><%=str3%></option>
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
<%=str4%>
</textarea>
<div id="textarea_description"></div>
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
<td width="33%" align="right" valign="top" scope="row"><label><b>Apps Key</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="appKey" id="appKey" class ="contentblack" style="width: 170px; height: 50px;" ><%=str6%></textarea>
</td>

</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Action Url</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="actionUrl" type="text" id="actionUrl" value="<%=str7%>" class ="contentblack"/>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Image Url</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="imageUrl" type="text" id="imageUrl" value="<%=str8%>" class ="contentblack"/>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Priority</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select  name="priority" id="priority" class ="contentblack" style="height:25px; width:175px">
	<option value="<%=str9%>"><%=str9%></option>
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
	<option value="<%=str10%>"><%=str10%></option>
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
	 <%
                int n=Integer.parseInt(str11);
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
<td width="33%" align="right" valign="top" scope="row"><label><b>Size</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="size" type="text" id="size" value="<%=str12%>" class ="contentblack"/>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Network Type</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="networkType" type="text" id="networkType" value="<%=str13%>" class ="contentblack"/>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Alert Text</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="alertText" id="alertText" class ="contentblack" style="width: 170px; height: 50px;" maxlength="350" ><%=str14%></textarea>
<div id="textarea_feedback"></div>
<%--<input name="alertText" type="text" id="alertText" value="<%=str16%>" class ="contentblack"/>--%>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Balance Type</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<%--<input name="balanceType" type="text" id="balanceType" value="<%=str17%>"  class ="contentblack"/>--%>

<select name="balanceType" id="balanceType" class ="contentblack" style="height:25px; width:175px">
	<%
		int i=Integer.parseInt(str15);
		if(i==0)
		{
	%>
			<option value="0" selected>Call Back</option>
			<option value="1">Install</option>
        		<option value="2">Any</option>
	<%
		}
		if(i==1)
		{
	%>
			<option value="1" selected>Install</option>
			<option value="0">Call Back</option>
			<option value="2">Any</option>
			
	<%
		}
		if(i==2)
		{
	%>
			<option value="2" selected>Any</option>
			<option value="0">Call Back</option>
			<option value="1">Install</option>
	<%
		}
	%>
</select>

</td>

</tr>
<%---<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>balanceCreditInDays</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="balanceCreditInDays" type="text" id="balanceCreditInDays" value="<%=str16%>" class ="contentblack"/>optional
</td>
</tr>--%>

<tr style="display:none;">
<td width="33%" align="right" valign="top" scope="row"><label><b>category</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="category" type="text" id="category" value="<%=str17%>" class ="contentblack"/>optional
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Category</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<%--<input name="root" type="text" id="root" value="<%=str18%>" class ="contentblack"/>--%>
<select name="root" id="root" class ="contentblack" style="height:25px; width:175px">
	<option value="<%=str18%>"><%=str18%></option>
        <option value="download & earn">download & earn</option>
        <option value="send invite text to share">send invite text to share</option>
	<option value="use & earn">use & earn</option>
        <option value="download, use & earn">download use & earn</option>
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
<input name="balanceCreditInDays" type="text" id="balanceCreditInDays" value="<%=str16%>" onChange="onChange_balanceCreditInDay();" class ="contentblack"/>optional
</td>
</tr>

<tr id="balanceCreditInDays1">
<td width="33%" align="right" valign="top" scope="row"><label><b>Pending Amt Credit</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="pendingAmountCredit" type="text" id="pendingAmountCredit" value="<%=str19%>" class ="contentblack"  />
</td>
</tr>

<tr id="balanceCreditInDays2">
<td width="33%" align="right" valign="top" scope="row"><label><b>Pending Rec Count</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="pendingRecCount" type="text" id="pendingRecCount" value="<%=str20%>" class ="contentblack"  />
</td>
</tr>

<tr id="balanceCreditInDays3">
<td width="33%" align="right" valign="top" scope="row"><label><b>Pending Rec Day</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="pendingRecDay" type="text" id="pendingRecDay" value="<%=str21%>" class ="contentblack"  />
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Callback Notification</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="callbackNotification" id="callbackNotification" class ="contentblack" style="width: 170px; height: 50px;" ><%=str22%></textarea>
<div id="textarea_callBack"></div>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Installed Notification</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="installedNotification" id="installedNotification" class ="contentblack" style="width: 170px; height: 50px;" ><%=str23%></textarea>
<div id="textarea_install"></div>
</td>
</tr>


<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Vendor Name</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="venderName" type="text" id="venderName" value="<%=str24%>" class ="contentblack"  />
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Gender</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="gender" id="gender" class ="contentblack" style="height:25px; width:175px">
        <option value="<%=str25%>"><%=str25%></option>
	<option value="All">All</option>
        <option value="Male">Male</option>
        <option value="Female">Female</option>
</select>

</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Operator Circle</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="operatorCircle" type="text" id="operatorCircle" value="<%=str26%>" class ="contentblack"  />
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Age Limit</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="ageLimit" type="text" id="ageLimit" value="<%=str27%>" class ="contentblack"  />
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Play StoreDetails</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="playStoreDetails" type="text" id="playStoreDetails" value="<%=str28%>" class ="contentblack"  />
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Rating</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="rating" type="text" id="rating" value="<%=str29%>" class ="contentblack"  />
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Instructions</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="instructions" id="instructions" class ="contentblack" style="width: 170px; height: 50px;" ><%=str30%></textarea>
<div id="textarea_instructions"></div>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>App Description</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<%--<input name="appDescription" type="text" id="appDescription" value="<%=str39%>" class ="contentblack"  />--%>
<textarea rows="2" cols="19" name="appDescription" id="appDescription" class ="contentblack" style="width: 170px; height: 50px;" ><%=str31%></textarea>
<div id="textarea_appDescription"></div>
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>PayOutOn</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<%--<input name="payOutOn" type="text" id="payOutOn" value="<%=str41%>" class ="contentblack"  />--%>
<select name="payOutOn" id="payOutOn" class ="contentblack" style="height:25px; width:175px">
        <option value="<%=str32%>"><%=str32%></option>
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
        <option value="<%=str33%>"><%=str33%></option>
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
<textarea rows="2" cols="19" name="userDeferedInfo" id="userDeferedInfo" class ="contentblack" style="width: 170px; height: 50px;" ><%=str34%></textarea>
<div id="textarea_userDeferedInfo"></div>
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Package Name</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="packageName" type="text" id="packageName" value="<%=str35%>" class ="contentblack"  />
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Payment Type</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<select name="offerPaymentType" id="offerPaymentType" class ="contentblack" style="height:25px; width:175px">
	<option value="<%=str36%>"><%=str36%></option>
        <option value="CPI">CPI</option>
        <option value="CPR">CPR</option>
</select>

</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>maxCreditLimit</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="maxCreditLimit" type="text" id="maxCreditLimit" value="<%=str37%>" class ="contentblack" onChange="onChange_maxCreditLimit();" />
</td>
</tr>

<tr id="maxCreditLimit1">
<td width="33%" align="right" valign="top" scope="row"><label><b>maxCreditPerDayLimit</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="maxCreditPerDayLimit" type="text" id="maxCreditPerDayLimit" value="<%=str38%>" class ="contentblack"  />
</td>
</tr>

<tr id="maxCreditLimit2">
<td width="33%" align="right" valign="top" scope="row"><label><b>amountPerDataThreshold</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="amountPerDataThreshold" type="text" id="amountPerDataThreshold" value="<%=str39%>" class ="contentblack"  />
</td>
</tr>

<tr id="maxCreditLimit3">
<td width="33%" align="right" valign="top" scope="row"><label><b>dataThreshold</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="dataThreshold" type="text" id="dataThreshold" value="<%=str40%>" class ="contentblack"  />
</td>
</tr>

<tr id="maxCreditLimit4"> 
<td width="33%" align="right" valign="top" scope="row"><label><b>dataUsageEligibleDays</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="dataUsageEligibleDays" type="text" id="dataUsageEligibleDays" value="<%=str41%>" class ="contentblack"  />
</td>
</tr>
<tr id="maxCreditLimit5">
<td width="33%" align="right" valign="top" scope="row"><label><b>timeIntervalOfRecheck</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="timeIntervalOfRecheck" type="text" id="timeIntervalOfRecheck" value="<%=str42%>" class ="contentblack"  />
</td>
</tr>



<tr id="maxCreditLimit6">
<td width="33%" align="right" valign="top" scope="row"><label><b>Is WifiDataAccepted</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="isWifiDataAccepted" type="number" id="isWifiDataAccepted" value="<%=str43%>" class ="contentblack"  min="0" />  0=Mobile Data,1=both
</td>
</tr>

<tr id="maxCreditLimit7">
<td width="33%" align="right" valign="top" scope="row"><label><b>Data OfferType</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="dataOfferType" type="number" id="dataOfferType" value="<%=str44%>" class ="contentblack" min="0" onChange="getDataOfferType();"/> 1=type 1 | 2=type 2 offer
</td>
</tr>
 <tr id="maxCreditLimit8">
<td width="33%" align="right" valign="top" scope="row"><label><b>WinBack Url</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="winBackURL" type="text" id="winBackURL" value="<%=str47%>" class ="contentblack"/> Only use type 2 offer
</td>
</tr>



<tr id="maxCreditLimit9">
<td width="33%" align="right" valign="top" scope="row"><label><b>DeepLink Url</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="deepLinkUrl" type="text" id="deepLinkUrl" value="<%=str48%>" class ="contentblack"  />
</td>
</tr>

<tr id="maxCreditLimit10">
<td width="33%" align="right" valign="top" scope="row"><label><b>is RepeatOffer</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="isRepeatOffer" type="number" id="isRepeatOffer" value="<%=str49%>" class ="contentblack" min="0" /> 0=Not Repeat | 1=Repeat
</td>
</tr>


<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Version</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="version" type="text" id="version" value="<%=str45%>" class ="contentblack"  />
</td>
</tr>
<%
	if(str1.equals("230"))
	{
%>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Detailed Instructions</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">

<textarea rows="2" cols="19" name="detailedInstructions" id="detailedInstructions" class ="contentblack" style="width: 170px; height: 50px;" ><%=str46%></textarea> Only Used for Invite OfferID=230
</td>
</tr>
<%
}
%>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Select Location</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top" id="GeoLoaction">
<input type="radio" name="location" id="Non1" value="Non" checked>Non
<input type="radio" name="location" id="City1" value="City">City
<input type="radio" name="location" id="PinCode1" value="Pin Code">Pin Code
<input type="radio" name="location" id="State1" value="State">State
</td>
</tr>





<tr id="GeoLoaction_city">
<td width="33%" align="right" valign="top" scope="row"><label><b>City</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="city" type="text" id="city" value="<%=str50%>" class ="contentblack"  />
</td>
</tr>
<tr id="GeoLoaction_pin">
<td width="33%" align="right" valign="top" scope="row"><label><b>Pin Code</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="pincode" type="number" id="pincode" value="<%=str51%>" class ="contentblack" min="0" />
</td>
</tr>

<tr id="GeoLoaction_state">
<td width="33%" align="right" valign="top" scope="row"><label><b>State</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="state" type="text" id="state" value="<%=str52%>" class ="contentblack"  />
</td>
</tr>
<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Amount Show</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="offerAmountShow"  type="number"  step="any" id="offerAmountShow" value="<%=str53%>" class ="contentblack"  /> Only Number/Float
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Multiple Event Offer</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="multipleEventOffer"  type="number" min="0" id="multipleEventOffer" value="<%=str54%>" class ="contentblack"  />0= No Event | 1=multiple Event
<br/>
<a href="http://108.161.134.70/ettConfig/ViewOfferEvent?offerId=<%=str1%>" target="_blank" id="multiple_event_check" style="text-decoration: underline;">click here to check multiple event</a>
</td>
</tr>


<tr>
<td width="33%" scope="row" align="right" valign="top" ><label></label>&nbsp;&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="58%" scope="row">&nbsp;</td>
</tr>


<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>PayOut Holdin Min</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="payOutHoldinMin"  type="number"  min="0" id="offerAmountShow" value="<%=str55%>" class ="contentblack"  /> Only Number
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>PayOut Hold Notification</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea rows="2" cols="19" name="payOutHoldNotification" id="payOutHoldNotification" class ="contentblack" style="width: 170px; height: 50px;" ><%=str56%></textarea>
<div id="textarea_payOutHoldNotification"></div>
</td>
</tr>


<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Requisite Packages</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="requisitePackages"  type="text" id="requisitePackages" value="<%=str57%>" class ="contentblack"  />
</td>
</tr>

<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Device Based Offer</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="deviceBasedOffer"  type="number"  id="deviceBasedOffer" value="<%=str58%>" class ="contentblack"  />0= Not Checked | 1=Checked
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
