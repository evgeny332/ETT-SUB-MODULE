function countChar(Value)
{
        var val = Value ;
	document.ModifyTemplate.charcount.value = val.length;
        if(val.length > 640)
        {
                alert("More than 640 Charactors are not allowed in Message");
                document.ModifyTemplate.message.value  = val.substring(0,640);
                document.ModifyTemplate.charcount.value = (document.ModifyTemplate.message.value).length;
        }
}
function updateTemplate()
{	
	var IndexValue = document.ModifyTemplate.ExistTemplate.selectedIndex;
	var SelectedVal = document.ModifyTemplate.ExistTemplate.options[IndexValue].value;
	document.ModifyTemplate.message.value = SelectedVal;
	countChar(SelectedVal); 
	document.ModifyTemplate.newTemplateName.value = TemplateNameList[IndexValue]; 
	document.ModifyTemplate.OldTemplateName.value = TemplateNameList[IndexValue]; 
}
function Validate()
{

	var Msg  = document.ModifyTemplate.message.value ;
	if(Msg == "")
	{
		alert("Message Can not be Blank.");
		document.ModifyTemplate.message.focus();
		return false ; 
	} 	
	var TemplateName = document.ModifyTemplate.newTemplateName.value ;
	if (TemplateName == "")
	{
		alert("Template Name can not be blank.");
		document.ModifyTemplate.newTemplateName.focus();
		return false ; 
	}
	 if(confirm ("Are You Sure Want To Modify This Template !!"))
		{return true;}
		else
		{return false;}

}
