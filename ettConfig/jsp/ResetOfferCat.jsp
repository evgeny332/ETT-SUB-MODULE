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
	$("input[type='radio']").click(function(){

                var radioValue = $("input[name='reason']:checked").val();

                if(radioValue){
                        $("#reason").val(radioValue);
                        $("#overlay_form").fadeOut(500);

                }

            });

	$('#GeoLoaction input').on('change', function() {
        	var loc=$('input[name=location]:checked', '#GeoLoaction').val();
        	if(loc=='EttId')
        	{
                	$('#GeoLoaction_city').show();
                	$('#GeoLoaction_pin').hide();
                	$('#GeoLoaction_state').hide();
        	}
        	if(loc=='Offer')
        	{
                	$('#GeoLoaction_city').hide();
                	$('#GeoLoaction_pin').show();
                	$('#GeoLoaction_state').hide();
        	}
        	if(loc=='Truncate')
        	{
                	$('#GeoLoaction_city').hide();
                	$('#GeoLoaction_pin').hide();
                	$('#GeoLoaction_state').show();
        	}
        	if(loc=='Dynamic')
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

}
function cancelAction()
{
	document.AddNewOfferForm.action="";
	//return false;
	//window.location.href = "<%=response.encodeURL(request.getContextPath())%>/AddNewOfferNew";
}
</script>
<body background-color="#dddddd" >
<form action="<%=response.encodeURL(request.getContextPath())%>/ResetCategory" name="AddNewOfferForm" method="post">
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
<td width="40%" align="center" valign="top" scope="row" ><strong><label>Set/Reset Category And Truncate Table</label><strong> &nbsp;</td>
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
<td width="33%" align="right" valign="top" scope="row"><label><b>Why Set/Reset Category</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<textarea readonly rows="2" cols="19" name="reason" id="reason" class ="contentblack" style="width: 270px; height: 30px;" >
</textarea><a href="#" id="pop" style="text-decoration: underline;">click here</a>
</td>
</tr>

<tr>
<td width="33%">&nbsp;</td>
<td width="2%">&nbsp;&nbsp;</td>
<td width="14%">&nbsp;</td>
</tr>



<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>What do you want</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top" id="GeoLoaction">
<input type="radio" name="location" value="EttId" checked>EttId Category
<input type="radio" name="location" value="Offer">Offer Category
<input type="radio" name="location" value="Truncate">Truncate TodayOffer
<input type="radio" name="location" value="Dynamic">Dynamic Tab Category
</td>
</tr>


<tr>
<td width="33%">&nbsp;</td>
<td width="2%">&nbsp;&nbsp;</td>
<td width="14%">&nbsp;</td>
</tr>


<tr id="EttIdCategory">
<td width="33%" align="right" valign="top" scope="row"><label><b>Ett Id</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="ettId" type="text" id="ettId" value="" class ="contentblack"  />
</td>
</tr>

<tr id="offerIdCategory">
<td width="33%" align="right" valign="top" scope="row"><label><b>Offer Id</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="offerId" type="text" id="offerId" value="" class ="contentblack"  />
</td>
</tr>

<tr id="TabIdCategory">
<td width="33%" align="right" valign="top" scope="row"><label><b>Tab Id</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="tabId" type="text" id="tabId" value="" class ="contentblack"  />
</td>
</tr>



<tr>
<td width="33%" align="right" valign="top" scope="row"><label><b>Category</label>&nbsp;</td>
<td width="2%" scope="row">&nbsp;</td>
<td width="14%" align="left" valign="top">
<input name="category" type="text" id="category" value="" class ="contentblack"  />
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
