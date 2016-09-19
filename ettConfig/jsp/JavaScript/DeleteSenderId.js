function Validate()
{
	var selectbox =document.getElementById("sendername");
	var index=selectbox.selectedIndex ;
	senderid = selectbox.options[index].value ; 
	if(index == -1)
	{
		alert("Please select SenderId");
		document.DeleteSenderId.sendername.focus();
		return false ; 
	}
	var cfm  = confirm("Delete sender id " + senderid);
	if(cfm)
	{
		document.DeleteSenderId.submit();
	}
	else
		return cfm ; 
	
}
function updateList(IndexValue)
{
	var SelectedVal = document.getElementById("sendername").options[IndexValue].value;
	var flagvalue = document.DeleteSenderId.flag.value="1" ;
	document.DeleteSenderId.action = GetListPath1;
	document.DeleteSenderId.submit();
}
