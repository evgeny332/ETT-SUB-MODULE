function ValidateFile()
{
	var msg_type 	= document.FileUpload.message_type.value;
	var file 	= document.FileUpload.MessageFile.value;
	var messagingServiceType = document.FileUpload.messagingService.value;
	var extension 	= file.substr(file.indexOf(".")+1); 
	//alert(extension);
	//alert(msg_type);
	//alert(messagingServiceType);
	if(msg_type=="PICTURE" || msg_type=="LOGO")
	{
		//if(extension=="png" || extension=="PNG" || extension=="jpg" || extension=="gif" || extension=="GIF" || extension=="bmp" || extension=="BMP" || extension=="jpeg" || extension=="JPEG" || extension=="JPG")
		if(messagingServiceType=="-1")
		{
			alert("Please Select Messaging Service Type.");
                        return false;
		}
		else if(msg_type=="LOGO")
		{
			if(messagingServiceType=="ems")
			{
				alert("Logo Is Allowed only For Nokia Smart Messaging");
                        	return false;
			}
		}
		if(extension=="gif" || extension=="GIF" || extension=="bmp" || extension=="BMP" || extension=="jpeg" || extension=="JPEG" || extension=="jpg" || extension=="JPG")
		{
			return true;
		}
		else if(extension=="")
                {
                        alert("Please upload Picture file.");
                        return false;
                }
		else
		{
			alert("Please upload GIF,BMP,JPEG or JPG file.");
			return false;
		}
	}
	else if(msg_type=="RINGTONE")
	{
	//	alert("RINGTONE");
		//if(extension=="txt" || extension=="SKCL" || extension=="skcl" || extension=="EMELODY" || extension=="emelody" || extension=="rtttl" || extension=="RTTTL" || extension=="MIDI" || extension=="midi")
		if(messagingServiceType=="-1")
                {
                        alert("Please Select Messaging Service Type.");
                        return false;
                }
		if(extension=="txt" || extension=="TXT" || extension=="rtttl")
		{
			return true;
		}
		else if(extension=="")
		{
			alert("Please upload Ringtone file.");
                        return false;
		}
		else
		{
			alert("Please upload txt or rtttl !!!");
			return false;
		}
	}
	else if(msg_type=="NVCARD")
        {
		if(extension=="vcf" || extension=="VCF" || extension=="vcard" || extension=="VCARD")
                        return true;
                else
                {
                        alert("Please upload correct file.");
                        return false;
                }

	}
	else
		return true;
}
function checkMessageType()
{
	var messageType = document.FileUpload.message_type.value;
	if(messageType=="PICTURE" || messageType=="RINGTONE" || messageType=="LOGO" || messageType=="NVCARD")
	{
		document.getElementById("message_file_tr").style.display="";
		document.getElementById("message_file_tr_div").style.display="";
		document.getElementById("messagingService_type_tr").style.display="";
		if(messageType=="NVCARD")
		{
			document.getElementById("message_tr").style.display="none";
			document.getElementById("messagingService_type_tr").style.display="none";
			document.getElementById("message_file_tr_div_id").innerHTML='';
		}
		if(messageType=="RINGTONE")
		{
			document.getElementById("message_file_tr_div_id").innerHTML='Please upload .RTTL or iMelody in text file format';
		}
		if(messageType=="PICTURE" ||messageType=="LOGO")
		{
			document.getElementById("message_file_tr_div_id").innerHTML='Please upload image file in BMP,GIF or JPEG (Monochrome / Black & White only) format';
		}
	}
	else
	{
		document.getElementById("message_file_tr").style.display="none";
		document.getElementById("messagingService_type_tr").style.display="none";
		document.getElementById("message_tr").style.display="";
		document.getElementById("message_file_tr_div").style.display="none";
	}
	return true;
}
function checkMessagingServiceType()
{
	var messagingType = document.FileUpload.messagingService.value;
	//alert("messageType="+messageType);
	if(messagingType=="ems")
	{
		document.getElementById("message_tr").style.display="";
	}
	else
	{
		document.getElementById("message_tr").style.display="none";
	}
	return true;
}
function initialize_campaign_id()
{
	var i,j,slashIndex;
	var FileName = document.FileUpload.DataFile.value.indexOf('.');
	var curDateTime = new Date();
	var curmonth = curDateTime.getMonth()+1;
	var curTime =(curDateTime.getDate()<10?"0":"")+curDateTime.getDate()+"/"+(curmonth<10?"0":"")+curmonth+"/"+curDateTime.getYear()+"_"+((curDateTime.getHours() < 10) ? "0" : "") + curDateTime.getHours() + ":"+ ((curDateTime.getMinutes() < 10) ? "0" : "") +curDateTime.getMinutes() + ":"+ ((curDateTime.getSeconds() < 10) ? "0" : "") + curDateTime.getSeconds()+":"+ ((curDateTime.getMilliseconds() < 10) ? "0" : "") + curDateTime.getMilliseconds();
	i=document.FileUpload.DataFile.value.lastIndexOf('.');
	slashIndex = document.FileUpload.DataFile.value.lastIndexOf("\\");
	//      document.FileUpload.campaign_name.value=document.FileUpload.DataFile.value.substring(slashIndex+1,i)+"_"+curTime;
	var Final_name = document.FileUpload.DataFile.value.substring(slashIndex+1,i)+"_"+curTime;
	while(Final_name.indexOf(" ") != -1 )
	{
		Final_name = Final_name.replace(" ","_");

	}
		 Final_name = Final_name.replace(/&/g ,"_");
                Final_name = Final_name.replace(/-/g,"_");

	document.FileUpload.campaign_name.value=Final_name;
	//alert("Final_name"+Final_name);
}
function updateTemplate()
{
	var IndexValue = document.FileUpload.template.selectedIndex;
	var SelectedVal = document.FileUpload.template.options[IndexValue].value;
	document.FileUpload.message.value = SelectedVal ;
	countChar(SelectedVal); 
}
function countChar(Value)
{
	var val = Value ;
	document.FileUpload.charcount.value = val.length;
	if(val.length > 960)
	{
		alert("More than 960 Charactors are not allowed in Message");
		document.FileUpload.message.value  = val.substring(0,960);
		document.FileUpload.charcount.value = (document.FileUpload.message.value).length;
	}
}
function setLength(str)
{
	if(str.length < 2)
		return "0"+str;
	else
		return str;
}
function trim(str)
{
	return 	str.replace("/ /g","");
}
function TrimMessage(str)
{
	while (str.substring(0,1) == ' ')
	{
		str = str.substring(1, str.length);
	}
	while(str.substring(str.length-1,str.length) == ' ')
	{
		str = str.substring(0,str.length-1);
	}
	return str;
}
function valideDateTime(DateTime)
{
	var SelDay = trim(DateTime.substring(0,DateTime.indexOf("-")));
	SelDay = setLength(SelDay);
	DateTime = DateTime.substring(DateTime.indexOf("-")+1);

	var SelMon = trim(DateTime.substring(0,DateTime.indexOf("-")));
	SelMon = setLength(SelMon);
	DateTime = DateTime.substring(DateTime.indexOf("-")+1);

	var SelYear = trim(DateTime.substring(0,DateTime.indexOf(" ")));
	DateTime = DateTime.substring(DateTime.indexOf(" ")+1);

	var SelHour = trim(DateTime.substring(0,DateTime.indexOf(":")));
	SelHour = setLength(SelHour);

	DateTime = DateTime.substring(DateTime.indexOf(":")+1);
	var SelMin = trim(DateTime.substring(0,DateTime.indexOf(":")));
	SelMin = setLength(SelMin);

	DateTime = DateTime.substring(DateTime.indexOf(":")+1);
	var SelSec = trim(DateTime) ;
	SelSec = setLength(SelSec);

	var SelectedDate = new Date(SelYear,SelMon,SelDay,SelHour,SelMin,SelSec);
	var SelectedDateNow = SelYear+SelMon+SelDay+SelHour+SelMin+SelSec;
	//	alert(ServerDateTime + " - " + SelectedDateNow);
	if(SelectedDateNow < ServerDateTime )
	{
		alert("Invalid Shedule Time");
		return false ;
	}
	if(SelHour > 23 || SelHour < 0 || SelMin > 59 || SelMin < 0 || SelSec > 59 || SelSec < 0)
	{
		alert("Invalid Shedule Time");
		return false ;
	}
	return true;

}
function Validate(button)
{
	var checkflag=document.getElementById('flag5').value;
	var allowd = ".txt.xls.TXT";
	var file = document.FileUpload.DataFile.value ; 
	//alert("button in Validate="+button);
	var msg_type_van    = document.FileUpload.message_type.value;
	//alert(msg_type_van);
	if(checkflag=="2")
	{
		if(file == "")
		{
			alert("Please select msisdn file");
			document.FileUpload.DataFile.focus(); 
			return false ; 
		}
		var File_Ext = file.substring(file.lastIndexOf("."));
		if(File_Ext == "" || allowd.indexOf(File_Ext) == -1 )
		{
			alert("Invalid file format . Please select txt or xls");
			return false ;
		}
	}
	else
	{
		document.getElementById('selectfile').value="";
		var nos="";
		nos =TrimMessage(document.getElementById('msisdnno').value);
		var counts =new Array();
		counts =nos.split("\n");
	        //alert(counts.length);
		var len =counts.length;
		var more =len-20;
		if(counts.length  > "20")
		{
		alert("You have entered "+len+" numbers. There could be no more than 20 Test numbers. Please delete "+more+" numbers from textbox!!");
		return false;
		}



	}
	if(!Validate1()){
	return false;
	}

	var DateTime = document.FileUpload.DateTime.value;
	if(checkflag=="2")
	{
	if(DateTime == "")
	{
		var cfm  = confirm("Time is not selected . Current Time will be selected");
		if(cfm == false)	
		{
			return false ; 
		}
	}
	else if (valideDateTime(DateTime) == false)
	{
		return false ;
	}
	}
	var campaign_name = TrimMessage(document.FileUpload.campaign_name.value);
	if(campaign_name == "")
	{
		alert("Blank Campaign Name is not allowed") ;
		return false ;  
	}
	var message = TrimMessage(document.FileUpload.message.value);
	//alert("message "+message);
	if(msg_type_van == "TEXT")
	{
		if(message == "")
		{
			//alert("alert1");
			alert("Blank Message is not allowed!!") ;
			return false ;
		}
	}
	if(msg_type_van == "FLASH")
	{
		if(message == "")
		{
			//alert("alert2");
			alert("Blank Message is not allowed!!") ;
			return false ;
		}
	}
	if(msg_type_van == "WAP_PUSH")
	{
		if( message == "" )
		{
			//alert("alert3");
			alert("Blank Message is not allowed!!") ;
			return false ;
		}
	}
	var messagingService = document.FileUpload.messagingService.value;
	if(messagingService == "ems")
	{
		if(msg_type_van!="NVCARD")
		{
			if(message == "")
			{
				alert("Blank Message is not allowed!") ;
				//alert("alert4");
				return false ;  
			}
		}
	}
	if(msg_type_van == "VERNACULAR")
	{
		if(message == "")
		{
			//alert("alert5");
			alert("Blank Message is not allowed!") ;
			return false ;
		}

		var msgval = document.FileUpload.message.value;
		displayResults( msgval, document.getElementById("message").id );
		//alert("I Am Converting to Unicode SMS");
	}
	else
	{
		if(Check_Ascii() == false)
		{
			document.FileUpload.message.focus();
			return false;
		}
	}
	
	callAjax(message,checkflag)
	//alert(checkflag+"ankit");
/*
	if(checkflag=="2")
	{
		var result = ValidateFile();
		if(result==true)
		{
			alert("tomar");
				CallUrl(checkflag);
				document.FileUpload.submitClickVal.value="CLICKED";
				//alert("you are in if main ho bhai");
		}
	}
	else
	{
			CallUrl ( checkflag );
			document.FileUpload.submitClickVal.value="";
			//alert("you are in else main ho bhai");
	}
*/
}

function CallUrl(checkflag)
{
		var url="";
		if(checkflag=="1")
		{
		//alert("button in if="+checkflag);
			url = "../UploadFileOfTestNumbers";
			if(!ValidateFile()){
        			return false;
        		}
	//		alert(url);
//			var cfm =confirm("Your Campaign name is "+document.FileUpload.campaign_name.value+" and message is "+document.FileUpload.message.value+"Your message type is "+document.FileUpload.message_type.value+"");
		//	if(cfm)
//			return cfm;
			
			document.FileUpload.action=url;
			document.FileUpload.submit();
		}
		else
		{
			//alert("button in else="+checkflag);
			url = "../UploadFile";
			if(!askuser())
			{
       				return false;
       			}
			 var clickVal = document.FileUpload.submitClickVal.value;
//			if(clickVal=="CLICKED")
//		        {
  //              	alert("Please Wait Your Request Is Already Submitted!!");
    //            	return false;
      //  		}
			document.FileUpload.action=url;
			document.FileUpload.submit();
		}
}

function askuser()
{
var s1=document.FileUpload.senderid.value;
var s2 =document.FileUpload.senderidnew.value;
var senderidtoshow ="";
var dt=document.FileUpload.DateTime.value;
if(s1=="-1")
senderidtoshow=s2;
else
senderidtoshow=s1;
var camptype ="";
var camp =document.FileUpload.camp_type.value;
if(camp=="P")
camptype="Promotional";
else
camptype="Service";
if(dt=="")
dt="Current time";


 if(confirm("Your Campaign details are:\n\nCampaign Name: "+document.FileUpload.campaign_name.value+" \nCampaign type: "+camptype+"\nmessage: "+document.FileUpload.message.value+"\nSenderid id: "+senderidtoshow+"\nMessage Type: "+document.FileUpload.message_type.value+" \nTime of execution: "+dt+"\n\n Do you want to submit this Campaign?"))
{return true;}else
{
document.FileUpload.submitClickVal.value="";
return false;
}
}

function callAjax(message,checkflag)
{
        var msg;
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
                	//alert("xmlhttp.readyState="+xmlhttp.readyState);
                        msg = xmlhttp.responseText;
                        //alert("msg===="+msg);
                        if( msg!="ok"){
                                alert(msg);
                                return false;
                        }
			else
			{
				if(checkflag=="2")
			        {
                			var result = ValidateFile();
		        	        if(result==true)
                			{		
                        			//alert("tomar");
		                                CallUrl(checkflag);
        		                        document.FileUpload.submitClickVal.value="CLICKED";
               			                //alert("you are in if main ho bhai");
                			}
        			}
        			else
        			{
	                        	CallUrl ( checkflag );
		                        document.FileUpload.submitClickVal.value="";
        		                //alert("you are in else main ho bhai");
        			}
			}
		}
        }
        url = "../CheckFilteringContent?message="+message;
        xmlhttp.open("GET",url,true);
        xmlhttp.send();
        //alert("msg="+msg);
}

function Check_Ascii()
{
	var val = document.FileUpload.message.value;
	var i = 0;
	var len = val.length;
	for(i =0 ; i < len ; i++)
	{
		var  valu = val.charCodeAt(i);
		if( valu >126 || valu ==94)
		{
			alert("Character ["+val.charAt(i)+"] at position - ["+(i+1)+"] is not supported ");
			return false ;
		}
	}
	return true;
}

function hexdigit(v) {
	symbs = "0123456789ABCDEF";
	return symbs.charAt(v & 0x0f);
}

function hexval(v) {
	return hexdigit(v >>> 12) + hexdigit(v >>> 8) + hexdigit(v >>> 4) + hexdigit(v);
}

function uni2j(val) {
	if (val == 10) return "\\n"
	else if (val == 13) return "\\r"
	else if (val == 92) return "\\\\"
	else if (val == 34) return "\\\""
	else if (val < 32 || val > 126) return "\\u" + hexval(val)
	else return String.fromCharCode(val);
}

function uni2java(uni, fld_java) {
	lit = '';
	for (i = 0; i < uni.length; i++) {
		v = uni.charCodeAt(i);
		lit = lit + uni2j(v);
	}

	fld_java.value = lit + '';
}

function inchange() {
	uni2java(document.FileUpload.message.value, document.FileUpload.outunicodemessage);
}


