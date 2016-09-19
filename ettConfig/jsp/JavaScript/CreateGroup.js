function updateDescription()
{
	var IndexValue = document.CreateGroup.groupname.selectedIndex;
	var TotalMember = GroupMember[IndexValue];
	var DateTime    = GroupDate[IndexValue];
	var GroupInfo   = GroupDesc[IndexValue];
	if(IndexValue != -1)
	{
		document.CreateGroup.description.value = GroupInfo ;
		document.getElementById("totalMember").innerHTML=TotalMember;
		document.getElementById("creationDate").innerHTML=DateTime;
	}
	else
	{
			alert("No Group is created.");
			document.CreateGroup.submit.disabled =	true ;
			document.CreateGroup.description.value = "" ;
			document.getElementById("totalMember").innerHTML="None";
			document.getElementById("creationDate").innerHTML="None";
	}
}
function Validate()
{
	var Value = document.CreateGroup.groupName.value;
	var Desc = document.CreateGroup.description.value;
	if(Value == "")
	{
		alert("Group Name can't be blank.");
		document.CreateGroup.groupName.focus();
		return false ; 
	}
	if(Desc == "")
	{
		alert("Description  can't be blank.");
		document.CreateGroup.description.focus();
		return false ; 
	}
}
