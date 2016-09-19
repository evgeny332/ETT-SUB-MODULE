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
	var file = document.GroupTree.DataFile1.value ; 
	if(file == "")
	{
		alert("Please select msisdn file");
		document..DataFile1.focus(); 
		return false ; 
	}
	var File_Ext = file.substring(file.lastIndexOf("."));
	if(File_Ext == "" || allowd.indexOf(File_Ext) == -1 )
	{
		alert("Invalid file format . Please select txt or xls");
		return false ;
	}
	var parentid =document.GroupTree.parentId.value;
	if(parentid =="")
	{
		alert("Please enter Parent Name");
		return false ;
	}
	 var groupname =document.GroupTree.groupName.value;
	if(groupname=="")
	{
		alert("Please enter Group Name");
		return false ;
	}
	var groupDesc =document.GroupTree.groupDesc.value;
	if(groupDesc=="")
	{
		alert("Please enter Group Name");
                return false ;
	}

	return true ;
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
