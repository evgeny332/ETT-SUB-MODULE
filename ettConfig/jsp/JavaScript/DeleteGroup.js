function updateDescription()
{
	var IndexValue = document.DeleteGroup.groupname.selectedIndex;
	var TotalMember = GroupMember[IndexValue];
	var DateTime    = GroupDate[IndexValue];
	var GroupInfo   = GroupDesc[IndexValue];
	if(IndexValue != -1)
	{
		document.DeleteGroup.description.value = GroupInfo ;
		document.getElementById("totalMember").innerHTML=TotalMember;
		document.getElementById("creationDate").innerHTML=DateTime;
	}
	else
	{
			alert("No Group is created.");
			document.DeleteGroup.submit.disabled =	true ;
			document.DeleteGroup.description.value = "" ;
			document.getElementById("totalMember").innerHTML="None";
			document.getElementById("creationDate").innerHTML="None";
	}
}
function Validate()
{

	var IndexValue = document.DeleteGroup.groupname.selectedIndex;
	var Name = GroupListName[IndexValue];
	var TotalMember = GroupMember[IndexValue];
	var DateTime    = GroupDate[IndexValue];
	var GroupInfo   = GroupDesc[IndexValue];
	var msg = "Do want to delete \nGroup : "+Name + "\nGroupInfo : "+GroupInfo;
	confirm(msg);

}
