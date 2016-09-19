function updateMessage()
{
	var IndexValue  =  document.DeleteTemplate.Template.selectedIndex;
	var SelectedVal = TemplateNameList[IndexValue];
	document.DeleteTemplate.message.value = SelectedVal ;
}
function Validate()
{
	var IndexValue = document.DeleteTemplate.Template.selectedIndex;
	var SelectedVal = document.DeleteTemplate.Template.options[IndexValue].value;
	if(SelectedVal == "")
	{
		alert("Please select any Template");
		return false ; 	
	}
	if(confirm ("Are You Sure Want To Delete This Template !!"))
                {return true;}
                else
                {return false;}
}
