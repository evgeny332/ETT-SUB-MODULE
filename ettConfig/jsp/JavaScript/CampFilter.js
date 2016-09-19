function Validate()
{
        var DateTime = document.CampaignFilter.fromDate.value ;

        if(DateTime == "")
        {
                alert("Please select date");
                document.CampaignFilter.fromDate.focus();
                return false ;
        }
        return true;
}
function updateDate()
{
        document.CampaignFilter.fromDate.value=DateValue;
}
function showMessage(camp , msg)
{
        alert("Campaign Name : "+camp +"\nMessage :" +msg);
}
