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
function ValidateFWM()
{
	var allowd = ".txt.xls" ; 
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
	 if(!Validate1()){
                return false;
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
