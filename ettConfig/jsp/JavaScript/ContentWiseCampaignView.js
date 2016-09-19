function Validate()
{
	var DateTime = document.ContentWiseCampaignView.fromDate.value ;
	if(DateTime == "")
	{
		alert("Please select from date");
		document.ContentWiseCampaignView.fromDate.focus();
		return false ; 
	}
	DateTime = document.ContentWiseCampaignView.toDate.value ;
	if(DateTime == "")
	{
		alert("Please select to date");
		document.ContentWiseCampaignView.toDate.focus();
		return false ; 
	}
	return true ;
}
