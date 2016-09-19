function Validate()
{
	var DateTime = document.DetailSummaryView.fromDate.value ;
	if(DateTime == "")
	{
		alert("Please select from date");
		document.DetailSummaryView.fromDate.focus();
		return false ; 
	}
	DateTime = document.DetailSummaryView.toDate.value ;
	if(DateTime == "")
	{
		alert("Please select to date");
		document.DetailSummaryView.toDate.focus();
		return false ; 
	}
	return true ;
}
