function initialize_campaign_id()
{
	var i,j,slashIndex;
	var FileName = document.excelWildCard.DataFile.value.indexOf('.');
        var curDateTime = new Date();
        var curmonth = curDateTime.getMonth()+1;
        var curTime =(curDateTime.getDate()<10?"0":"")+curDateTime.getDate()+"/"+(curmonth<10?"0":"")+curmonth+"/"+curDateTime.getYear()+"_"+((curDateTime.getHours() < 10) ? "0" : "") + curDateTime.getHours() + ":"+ ((curDateTime.getMinutes() < 10) ? "0" : "") +curDateTime.getMinutes() + ":"+ ((curDateTime.getSeconds() < 10) ? "0" : "") + curDateTime.getSeconds()+":"+ ((curDateTime.getMilliseconds() < 10) ? "0" : "") + curDateTime.getMilliseconds();
        i=document.excelWildCard.DataFile.value.lastIndexOf('.');
	slashIndex = document.excelWildCard.DataFile.value.lastIndexOf("\\");
       document.excelWildCard.campaign_name.value=document.excelWildCard.DataFile.value.substring(slashIndex+1,i)+"_"+curTime;
}
function updateTemplate()
{
	var IndexValue = document.excelWildCard.template.selectedIndex;
	var SelectedVal = document.excelWildCard.template.options[IndexValue].value;
	document.excelWildCard.message.value = SelectedVal ;
	countChar(SelectedVal); 
}
function countChar(Value)
{
        var val = Value ;
	document.excelWildCard.charcount.value = val.length;
        if(val.length > 159)
        {
                alert("More than 160 Charactors are not allowed in Message");
                document.excelWildCard.message.value  = val.substring(0,160);
                document.excelWildCard.charcount.value = (document.excelWildCard.message.value).length;
        }
}
function Validate()
{
	var allowd = ".txt.xls.xlsx" ; 
	var file = document.excelWildCard.DataFile.value ; 
	if(file == "")
	{
		alert("Please select msisdn file");
		document.excelWildCard.DataFile.focus(); 
		return false ; 
	}
	var File_Ext = file.substring(file.lastIndexOf("."));
	if(File_Ext == "" || allowd.indexOf(File_Ext) == -1 )
	{
		alert("Invalid file format . Please select txt or xls");
		return false ;
	}
	if(!Validate1()){
                //alert("Final check");
                return false;
        }

	var DateTime = document.excelWildCard.DateTime.value;
	if(DateTime == "")
        {
		alert("Time is not selected . Current Time will be selected");
	}
	//var campaign_name = document.excelWildCard.campaign_name.value;
	var campaign_name = TrimMessage(document.excelWildCard.campaign_name.value);
	if(campaign_name == "")
        {
		alert("Blank Campaign Name is not allowed") ;
		return false ;  
	}
	//var message = document.excelWildCard.message.value;
	var message = TrimMessage(document.excelWildCard.message.value);
	if(message == "")
        {
		alert("Blank Campaign Name is not allowed") ;
		return false ;  
	}
	return true ;
}
function ValidateFileWithMsg()
{
	alert("ValidateFileWithMsg");
	var allowd = ".txt.xls.xlsx" ; 
	var file = document.excelWildCard.DataFile.value ; 
	if(file == "")
	{
		alert("Please select msisdn file");
		document.excelWildCard.DataFile.focus(); 
		return false ; 
	}
	var File_Ext = file.substring(file.lastIndexOf("."));
	if(File_Ext == "" || allowd.indexOf(File_Ext) == -1 )
	{
		alert("Invalid file format . Please select txt or xls");
		return false ;
	}
	var DateTime = document.excelWildCard.DateTime.value;
	if(DateTime == "")
        {
		alert("Time is not selected . Current Time will be selected");
	}
	var campaign_name = document.excelWildCard.campaign_name.value;
	if(campaign_name == "")
        {
		alert("Blank Campaign Name is not allowed") ;
		return false ;  
	}
	return true ;
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
