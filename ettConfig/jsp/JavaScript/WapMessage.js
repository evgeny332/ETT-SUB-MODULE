function initialize_campaign_id()
{
	var i,j,slashIndex;
	var FileName = document.WapMessage.DataFile.value.indexOf('.');
        var curDateTime = new Date();
        var curmonth = curDateTime.getMonth()+1;
        var curTime =(curDateTime.getDate()<10?"0":"")+curDateTime.getDate()+"/"+(curmonth<10?"0":"")+curmonth+"/"+curDateTime.getYear()+"_"+((curDateTime.getHours() < 10) ? "0" : "") + curDateTime.getHours() + ":"+ ((curDateTime.getMinutes() < 10) ? "0" : "") +curDateTime.getMinutes() + ":"+ ((curDateTime.getSeconds() < 10) ? "0" : "") + curDateTime.getSeconds()+":"+ ((curDateTime.getMilliseconds() < 10) ? "0" : "") + curDateTime.getMilliseconds();
        i=document.WapMessage.DataFile.value.lastIndexOf('.');
	slashIndex = document.WapMessage.DataFile.value.lastIndexOf("\\");
 //      document.WapMessage.campaign_name.value=document.FileUpload.DataFile.value.substring(slashIndex+1,i)+"_"+curTime;
	var Final_name = document.WapMessage.DataFile.value.substring(slashIndex+1,i)+"_"+curTime;
	while(Final_name.indexOf(" ") != -1 )
	{
		Final_name = Final_name.replace(" ","_");
	}
	document.WapMessage.campaign_name.value=Final_name;
}
function updateTemplate()
{
	var IndexValue = document.WapMessage.template.selectedIndex;
	var SelectedVal = document.WapMessage.template.options[IndexValue].value;
	document.WapMessage.message.value = SelectedVal ;
	countChar(SelectedVal); 
}
function countChar(Value)
{
        var val = Value ;
	document.WapMessage.charcount.value = val.length;
        if(val.length > 960)
        {
                alert("More than 960 Charactors are not allowed in Message");
                document.WapMessage.message.value  = val.substring(0,960);
                document.WapMessage.charcount.value = (document.FileUpload.message.value).length;
        }
}
function countChar1(Value)
{
        var val = Value ;
        document.WapMessage.charcount1.value = val.length;
        if(val.length > 960)
        {
                alert("More than 960 Charactors are not allowed in Message");
                document.WapMessage.message.value  = val.substring(0,960);
                document.WapMessage.charcount1.value = (document.FileUpload.message.value).length;
        }
}

function Validate()
{
	var allowd = ".txt.xls.xlsx" ; 
	var file = document.WapMessage.DataFile.value ; 
	if(file == "")
	{
		alert("Please select msisdn file");
		document.WapMessage.DataFile.focus(); 
		return false ; 
	}
	var File_Ext = file.substring(file.lastIndexOf("."));
	if(File_Ext == "" || allowd.indexOf(File_Ext) == -1 )
	{
		alert("Invalid file format . Please select txt or xls");
		return false ;
	}
	var DateTime = document.WapMessage.DateTime.value;

	if(DateTime == "")
        {
		var cfm  = confirm("Time is not selected . Current Time will be selected");
		if(cfm == false)	
		{
			return false ; 
		}
	}
	var exd = new Date();
        var y1 =exd.getYear();
	var m1 =exd.getMonth();
	m1=m1+1;
	var d1 =exd.getUTCDate();
	var h1 =exd.getHours();
	var mi =exd.getMinutes();
	var se =exd.getSeconds();
        var date1 = DateTime.split(" ",1);
	var date1 =DateTime;
//	alert(date1);
	var date2 =d1+"-"+m1+"-"+y1+" "+h1+":"+mi+":"+se;
	
//	alert(date2);
	if(date1<DateTime)
	{
//	alert(" galat date hai");
	return false
	}
	var campaign_name = document.WapMessage.campaign_name.value;
	if(campaign_name == "")
        {
		alert("Blank Campaign Name is not allowed") ;
		return false ;  
	}
	var message = document.WapMessage.message.value;
	if(message == "")
        {
		alert("Blank Title is not allowed") ;
		return false ;  
	}
	 var link = document.WapMessage.link.value;
        if(link == "")
        {
                alert("Blank Link is not allowed") ;
                return false ;
        }

	return true ;
}
function ValidateFileWithMsg()
{
	alert("ValidateFileWithMsg");
	var allowd = ".txt.xls.xlsx" ; 
	var file = document.WapMessage.DataFile.value ; 
	if(file == "")
	{
		alert("Please select msisdn file");
		document.WapMessage.DataFile.focus(); 
		return false ; 
	}
	var File_Ext = file.substring(file.lastIndexOf("."));
	if(File_Ext == "" || allowd.indexOf(File_Ext) == -1 )
	{
		alert("Invalid file format . Please select txt or xls");
		return false ;
	}
	var DateTime = document.WapMessage.DateTime.value;
	if(DateTime == "")
        {
		alert("Time is not selected . Current Time will be selected");
	}
	var campaign_name = document.WapMessage.campaign_name.value;
	if(campaign_name == "")
        {
		alert("Blank Campaign Name is not allowed") ;
		return false ;  
	}
	return true ;
}
