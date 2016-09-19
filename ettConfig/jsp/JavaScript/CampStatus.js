function Validate()
{
	var DateTime = document.CampStatus.fromDate.value ;
	 
	if(DateTime == "")
	{
		alert("Please select date");
		document.CampStatus.fromDate.focus();
		return false ; 
	}	
	return true; 
}
function updateDate()
{
	document.CampStatus.fromDate.value=DateValue;
}
function showMessage(camp , msg)
{
	alert("Campaign Name : "+camp +"\nMessage :" +msg);
}
