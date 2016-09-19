function countChar(Value)
{
        var val = Value ;
	document.CopyTemplate.charcount.value = val.length;
        if(val.length > 640)
        {
                alert("More than 640 Charactors are not allowed in Message");
                document.CopyTemplate.message.value  = val.substring(0,640);
                document.CopyTemplate.charcount.value = (document.CopyTemplate.message.value).length;
        }
}
function updateTemplate()
{
	var IndexValue = document.CopyTemplate.ExistTemplate.selectedIndex;
	var SelectedVal = document.CopyTemplate.ExistTemplate.options[IndexValue].value;
	document.CopyTemplate.message.value = SelectedVal;
	
	countChar(SelectedVal);
	/*document.CopyTemplate.newTemplateName.value = TemplateNameList[IndexValue]; 
	document.CopyTemplate.OldTemplateName.value = TemplateNameList[IndexValue]; */
}
function Validate()
{
	var templateName    = document.CopyTemplate.templateName.value;
	var Msg  = document.CopyTemplate.message.value ;
	var OldTemplateName = document.CopyTemplate.OldTemplateName.value ;
	
	if(templateName == "")
	{
		alert("Template Name Can not be Blank.");
		document.CopyTemplate.templateName.focus();
		return false ;
	}
	 if(Msg == "")
	{
		alert("Message Can not be Blank.");
		alert("\n For copy message select Template from Template list");
		document.CopyTemplate.message.focus();
		return false ; 
	}
	if(confirm ("Are You Sure Want To Copy This Template !!"))
                {return true;}
                else
                {return false;}
}
