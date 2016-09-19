function Validate()
{
	var DateTime = document.FileStatus.fromDate.value ;
	 
	if(DateTime == "")
	{
		alert("Please select date");
		document.FileStatus.fromDate.focus();
		return false ; 
	}	
	return true; 
}
function updateDate()
{
	document.FileStatus.fromDate.value=DateValue;
}
function showMessage(camp , msg)
{
	alert("Campaign Name : "+camp +"\nMessage :" +msg);
}
