function Validate()
{
	var Msg  = document.AddTemplate.message.value ;
	if(Msg == "")
	{
		alert("Message Can not be Blank.");
		document.AddTemplate.message.focus();
		return false ; 
	} 	
	var TemplateName = document.AddTemplate.templateName.value ;
	if (TemplateName == "")
	{
		alert("Template Name can not be blank.");
		document.AddTemplate.templateName.focus();
		return false ; 
	}
	for(var index=0 ; index < TemplateNameList.length ; index++)
	{
		if(TemplateNameList[index] == TemplateName)
		{
			alert("This Template Name is already exist .");
			document.AddTemplate.templateName.focus();
			return false ;
		}
	}
	return true ; 
}
function countChar(Value)
{
        var val = Value ;
	document.AddTemplate.charcount.value = val.length;
        if(val.length > 640)
        {
                alert("More than 640 Charactors are not allowed in Message");
                document.FileUpload.message.value  = val.substring(0,640);
                document.FileUpload.charcount.value = (document.FileUpload.message.value).length;
        }
}
