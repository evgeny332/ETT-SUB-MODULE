function Validate()
{
	var DateTime = document.DetailSummary.fromDate.value ;
	if(DateTime == "")
	{
		alert("Please select from date");
		document.DetailSummary.fromDate.focus();
		return false ; 
	}
	DateTime = document.DetailSummary.toDate.value ;
	if(DateTime == "")
	{
		alert("Please select to date");
		document.DetailSummary.toDate.focus();
		return false ; 
	}
	return true ;
}
