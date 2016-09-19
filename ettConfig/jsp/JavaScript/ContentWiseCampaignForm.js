function Validate()
{
	var DateTime = document.ContentWiseCampaignForm.fromDate.value ;
	if(DateTime == "")
	{
		alert("Please select from date");
		document.ContentWiseCampaignForm.fromDate.focus();
		return false ; 
	}
	DateTime = document.ContentWiseCampaignForm.toDate.value ;
	if(DateTime == "")
	{
		alert("Please select to date");
		document.ContentWiseCampaignForm.toDate.focus();
		return false ; 
	}
	return true ;
}
