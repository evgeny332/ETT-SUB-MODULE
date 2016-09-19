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
	 var groupname =document.GroupTree.groupName.value;
	if(groupname=="")
	{
		alert("Please enter Group Name");
		return false ;
	}
	return true ;
}
