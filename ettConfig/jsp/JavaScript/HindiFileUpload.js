function setup() {
document.write('<scr' + 'ipt type="text/javascript" src="conversionfunctions.js"></scr' + 'ipt>');
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
       document.FileUpload.campaign_name.value=document.FileUpload.DataFile.value.substring(slashIndex+1,i)+"_"+curTime;
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
        if(val.length > 159)
        {
                alert("More than 160 Charactors are not allowed in Message");
                document.FileUpload.message.value  = val.substring(0,160);
                document.FileUpload.charcount.value = (document.FileUpload.message.value).length;
        }
}
function Validate()
{
	var allowd = ".txt.xls.xlsx" ; 
	var file = document.FileUpload.DataFile.value ; 
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
	var DateTime = document.FileUpload.DateTime.value;
	if(DateTime == "")
        {
		alert("Time is not selected . Current Time will be selected");
	}
	var campaign_name = document.FileUpload.campaign_name.value;
	if(campaign_name == "")
        {
		alert("Blank Campaign Name is not allowed") ;
		return false ; 
	}
	//var message = document.FileUpload.message.value;
//	var message = document.FileUpload.escapedInput.value;
	var message = document.FileUpload.chars.value;
	if(message == "")
        {
		alert("Blank Campaign Name is not allowed") ;
		return false ;  
	}
//	alert( displayResults( convertAllEscapes(document.getElementById("escapedInput").value, 'none') , document.getElementById("escapedInput").id) );
//	displayResults( convertAllEscapes(document.getElementById("escapedInput").value, 'none') , document.getElementById("escapedInput").id);
	displayResults( document.getElementById("chars").value, document.getElementById("chars").id );
	return true ;
//	return false;
}
function ValidateFileWithMsg()
{
	alert("ValidateFileWithMsg");
	var allowd = ".txt.xls.xlsx" ; 
	var file = document.FileUpload.DataFile.value ; 
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
	var DateTime = document.FileUpload.DateTime.value;
	if(DateTime == "")
        {
		alert("Time is not selected . Current Time will be selected");
	}
	var campaign_name = document.FileUpload.campaign_name.value;
	if(campaign_name == "")
        {
		alert("Blank Campaign Name is not allowed") ;
		return false ;  
	}
	return true ;
}
