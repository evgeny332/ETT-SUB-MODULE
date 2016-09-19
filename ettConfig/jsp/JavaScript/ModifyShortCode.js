function updateList()
{
	var IndexValue = document.EditDeleteShortCode.shortcode.selectedIndex;
	
	if(IndexValue == 0)
	{
		//document.getElementById("description").innerHTML= "None";
		//document.getElementById("totalMember").innerHTML= "None";
		//document.getElementById("creationDate").innerHTML="None";
		//document.getElementById("formatedTab2").style.display = "none";
		return ;
	}
	document.EditDeleteShortCode.shortcodeId.value= IndexValue;
	document.EditDeleteShortCode.action=GetListPath;
	document.EditDeleteShortCode.submit();
}
function updateListUser()
{
        var IndexValue = document.EditDeleteShortCode.User.selectedIndex;

	//alert("changig user"+IndexValue);
        if(IndexValue == 0)
        {
                //document.getElementById("description").innerHTML= "None";
                //document.getElementById("totalMember").innerHTML= "None";
                //document.getElementById("creationDate").innerHTML="None";
                //document.getElementById("formatedTab2").style.display = "none";
                //return ;
        }
        document.EditDeleteShortCode.User.value= IndexValue;
        document.EditDeleteShortCode.action=GetListPath;
    document.EditDeleteShortCode.submit();
}
function updateDescription(Index)
{

	var IndexValue = Index ; 
	document.EditDeleteShortCode.shortcode.options[IndexValue].selected = true ;
	if(IndexValue > 0)
	{
		document.getElementById("description").innerHTML=GroupInfo;
		document.getElementById("totalMember").innerHTML=TotalMember;
		document.getElementById("creationDate").innerHTML=DateTime;
	}
	else
	{
		document.getElementById("formatedTab2").style.display = "none";
//		document.getElementByName("detailTable").style.display = "none";
	}
}
function Validate(action)
{
	var IndexValue = document.EditDeleteShortCode.shortcode.selectedIndex;
	var Name = ShortCodeListName[IndexValue];
	var check = 0 ; 
	for (var i=0; i < document.EditDeleteShortCode.shortcodeid.length; i++)
	{
		if(document.EditDeleteShortCode.shortcodeid[i].checked == true)
		{
				check = check+1 ;
				break;
		}
	}
	if(check == 0)
	{
		alert("Please select any record");
		return false ;
	}
	input_box=confirm("Click Cancel or OK to Continue");
	if (input_box==true)
	{	 
		// Output when OK is clicked
		//alert ("You clicked OK"); 
	}else
	{	
		// Output when Cancel is clicked
		//alert ("You clicked cancel");
		return false ;
	}
	document.EditDeleteShortCode.shortcodeId.value = IndexValue;
	document.EditDeleteShortCode.Action.value= action ;
}
function markAll()
{
	for (var i=0;i<document.EditDeleteShortCode.shortcodeid.length;i++)
	{
		document.EditDeleteShortCode.shortcodeid[i].checked = true ; 
	}
}
function unmarkAll()
{
	for (var i=0;i<document.EditDeleteShortCode.shortcodeid.length;i++)
	{
		document.EditDeleteShortCode.shortcodeid[i].checked =false ;
	}
}
