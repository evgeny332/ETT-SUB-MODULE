var msg;
function trigger()
{
	callAjaxForTrigger();
}
function callAjaxForTrigger()
{
        var xmlhttp;
        if (window.XMLHttpRequest)
        {// code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp=new XMLHttpRequest();
        }
        else
        {// code for IE6, IE5
                xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange=function()
        {
                if (xmlhttp.readyState==4 && xmlhttp.status==200)
                {
                        msg = xmlhttp.responseText;
                }
        }
        url = "../SendToUDP";
        xmlhttp.open("GET",url,false);
        xmlhttp.send(null);
}
function delPopup()
{
	var flag = 0;
        var dataToDelete;
        for(i=0;i<document.editScheduling.editData.length;i++)
        {
                if(document.editScheduling.editData[i].checked==true)
                {
                        dataToDelete = document.editScheduling.editData[i].value;
                        flag = 1;
                }
                if(flag==1)
                {
                        break;
                }
        }
	if(flag == 0)
	{
		if(document.editScheduling.editData.checked==true)
			dataToDelete = document.editScheduling.editData.value;
		else
			alert("please select to delete");
	}
        var data	= new Array();
        data            = dataToDelete.split("$");
        callAjaxToDelete(data[0]);
	if(msg=="done")
	{
		window.close();
	}
}
function callAjaxToDelete(data)
{

        var xmlhttp;
	if (window.XMLHttpRequest)
        {// code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp=new XMLHttpRequest();
        }
        else
        {// code for IE6, IE5
                xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange=function()
        {
                if (xmlhttp.readyState==4 && xmlhttp.status==200)
                {
                        msg = xmlhttp.responseText;
			//alert("msg="+msg)
                }
        }
        url = "../DeleteScheduledData?data="+data;
        xmlhttp.open("GET",url,false);
        xmlhttp.send(null);
}
function dayCheck()
{
	var count = 0;
	for(i=0;i<document.scheduling.days.length;i++)
	{
		if(document.scheduling.days.options[i].selected==true)
		{
			count++;
		}
	}
	if(document.scheduling.blockDay.checked==true)
	{
		document.getElementById("days_tr").style.display="none";
		document.getElementById("st1_tr").style.display="none";
		document.getElementById("et1_tr").style.display="none";
		document.getElementById("st2_tr").style.display="none";
		document.getElementById("et2_tr").style.display="none";
		document.getElementById("date_tr").style.display="";
	}
	else if(document.scheduling.days.options[0].selected==true && count>1)
	{
		document.getElementById("days_tr").style.display="";
		document.getElementById("st1_tr").style.display="";
		document.getElementById("et1_tr").style.display="";
		document.getElementById("st2_tr").style.display="none";
		document.getElementById("et2_tr").style.display="none";
		document.getElementById("date_tr").style.display="none";
		document.scheduling.blockDay.checked=false;
		alert("Sunday can't be selected with days.");
	}
	else if(document.scheduling.days.options[0].selected==true && count==1)
	{
		document.getElementById("days_tr").style.display="";
                document.getElementById("st1_tr").style.display="";
                document.getElementById("et1_tr").style.display="";
                document.getElementById("st2_tr").style.display="";
                document.getElementById("et2_tr").style.display="";
                document.getElementById("date_tr").style.display="none";
	}
	else if(document.scheduling.days.options[0].selected==false)
	{
		document.getElementById("days_tr").style.display="";
		document.getElementById("st1_tr").style.display="";
		document.getElementById("et1_tr").style.display="";
		document.getElementById("st2_tr").style.display="none";
		document.getElementById("et2_tr").style.display="none";
		document.getElementById("date_tr").style.display="none";
	}
	else 
	{
		document.getElementById("days_tr").style.display="";
		document.getElementById("st1_tr").style.display="";
		document.getElementById("et1_tr").style.display="";
		document.getElementById("st2_tr").style.display="none";
		document.getElementById("et2_tr").style.display="none";
		document.getElementById("date_tr").style.display="none";
		//callAjaxToCheckScheduledDay();
	}
}
function ValidateConfig()
{
	var desc = document.getElementById("description").value;
	desc = desc.replace(/ /g,"");
	desc = desc.replace(/null/g,"");
	var val = document.getElementById("values").value;
	val = val.replace(/ /g,"");
	val = val.replace(/null/g,"");
	if(document.getElementById("description").value==null || desc=="")
	{
		alert("Please enter some description...");
		document.getElementById("description").value="";
		document.getElementById("description").focus();
		return false;
	}
	else if(document.getElementById("values").value==null || val=="")
	{
		alert("Please enter some value...");
		document.getElementById("values").value="";
		document.getElementById("values").focus();
		return false;
	}
	else
		return true;
	
}
function trim(str)
{
	return str.replace("/ /g","");
}
function valideDate(DateTime)
{
	var currentTime = new Date();
	curDate = currentTime.getDate()+"-"+(currentTime.getMonth() + 1)+"-"+currentTime.getFullYear();
	dateTime = new Date(DateTime);
	currentDate = new Date(curDate);
	if(dateTime<currentDate)
	{
		alert("Can't Scheduled on a Elapsed Date");
		return false ;
	}
	else
		return true;

}
function valideTime(DateTime, st, et)
{
	alert("in valideTime DateTime =["+DateTime+"] and st =["+st+"] and et=["+et+"]");
        var currentTime = new Date();
        curDate = currentTime.getDate()+"-"+(currentTime.getMonth() + 1)+"-"+currentTime.getFullYear();
	ct = currentTime.getHours()+":"+currentTime.getMinutes();
	var specifiedDate = new Date(DateTime);
	var currentDate = new Date(curDate);
	var startTime = new Date(DateTime+" "+st);
	var curTime = new Date(DateTime+" "+ct);
	alert("startTime = ["+startTime.getHours()+"] and curTime=["+curTime.getHours()+"]");
        if(specifiedDate==currentDate)
        {
		alert("scheduling for today !!!!");
		alert("ok=="+curTime.getHours()<startTime.getHours());
		if(curTime.getHours()<startTime.getHours())
		{
                	return true ;
		}
		else
		{
			alert("selected start time can't be less than current time.");
                	return false ;
		}
        }
	else if(specifiedDate<currentDate)
	{
		alert("selected date can't be less than current date.");
        	return false;
	}
	else
	{
		alert("specifiedDate=["+specifiedDate+"] and currentDate=["+currentDate+"]");
		return false;
	}

}
function createHTTPObject(){
        if ( window.ActiveXObject ){
                return new ActiveXObject("Microsoft.XMLHTTP");
        }else if ( window.XMLHttpRequest ){
                return new XMLHttpRequest();
        }else{
                alert("Your browser does not support AJAX.");
                return null;
        }
}

var count;
function callAjaxToCountData()
{
	/*var xmlhttp;
        alert("msg in callAjaxToCountData ="+count);
        if (window.XMLHttpRequest)
        {// code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp=new XMLHttpRequest();
        }
        else
        {// code for IE6, IE5
                xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
        }*/
	httpObject = createHTTPObject();
        httpObject.onreadystatechange=function()
        {
		//alert("xmlhttp.readyState="+httpObject.readyState);
                if (httpObject.readyState==4 && httpObject.status==200)
                {
                        count = httpObject.responseText;
			//alert("count="+count);
                }
        }
	var d = new Date();
        url = "../DataCount?mili="+d.getMilliseconds();
        httpObject.open("GET",url,false);
        httpObject.send(null);
}
function editPopup(userName)
{
	callAjaxToCountData();
	//alert("count in editPopup="+count);
	if(count>0)
	{
		var DateTime    = document.scheduling.DateTime.value;
	        var day         = document.scheduling.days.value;
        	var blockDay    = document.scheduling.blockDay.checked;
		document.scheduling.editFlag.value="1";
		my_window = window.open("editScheduling.jsp","edit");
	}
	else
	{
		alert("not scheduled for user '"+userName+"'");
	}
}
function Validate()
{
	var DateTime 	= document.scheduling.DateTime.value;
	var day 	= document.scheduling.days.value;
	var blockDay 	= document.scheduling.blockDay.checked;

	if(blockDay==false)
	{
		var table = document.getElementById("timeTable");
	        var rowCount = table.rows.length;
		rowCount1 = (rowCount/2);
		for(i=0;i<rowCount1;i++)
		{
			var st = document.getElementById("st"+(i+1));
			var et = document.getElementById("et"+(i+1));
			var st1 = document.getElementById("st"+(i+2));
			var et1 = document.getElementById("et"+(i+2));
			
			if(st.value=="" || st.value==null)
			{
				alert("Plese insert start time.");
				st.focus();
				return false;
			}
			else if(et.value=="" || et.value==null)
			{
				alert("Plese insert end time.");
				et.focus();
				return false;
			}
			else if(et.value<=st.value)
			{
				alert("end time must be greater then start time");
				return false;
			}
			else
			{
				if(DateTime==null || DateTime=="")
				{
					if(day==null || day=="")
                                        {
                                                alert("either day or date must be selected.");
                                                return false;
                                        }
				}
			}
				
		}
	}
	else(blockDay==true)
	{
		if(DateTime==null || DateTime=="")
                {
                	if(day==null || day=="")
                        {
                        	alert("either day or date must be selected.");
                                return false;
                        }
               }
	}
	if (valideDate(DateTime) == false)
	{
		return false ;
	}
	else
		return true ;
	//return false;
}
