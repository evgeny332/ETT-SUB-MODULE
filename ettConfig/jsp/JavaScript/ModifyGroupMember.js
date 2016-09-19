function updateList()
{
	var IndexValue = document.ModifyGroupMember.groupname.selectedIndex;
	var TotalMember = GroupMember[IndexValue-1];
	var DateTime    = GroupDate[IndexValue-1];
	var GroupInfo   = GroupDesc[IndexValue-1];
	
	if(IndexValue == 0)
	{
		document.getElementById("description").innerHTML= "None";
		document.getElementById("totalMember").innerHTML= "None";
		document.getElementById("creationDate").innerHTML="None";
		document.getElementById("formatedTab2").style.display = "none";
		return ;
	}
	document.ModifyGroupMember.groupId.value= IndexValue;
	document.ModifyGroupMember.action=GetListPath;
    document.ModifyGroupMember.submit();
}
function updateDescription(Index)
{

	var IndexValue = Index ; 
	document.ModifyGroupMember.groupname.options[IndexValue].selected = true ;
	var TotalMember = GroupMember[IndexValue-1];
	var DateTime    = GroupDate[IndexValue-1];
	var GroupInfo   = GroupDesc[IndexValue-1];
	if(IndexValue > 0)
	{
		document.getElementById("description").innerHTML=GroupInfo;
		document.getElementById("totalMember").innerHTML=TotalMember;
		document.getElementById("creationDate").innerHTML=DateTime;
	}
	else
	{
		document.getElementById("description").innerHTML= "None";
		document.getElementById("totalMember").innerHTML= "None";
		document.getElementById("creationDate").innerHTML="None";
		document.getElementById("formatedTab2").style.display = "none";
//		document.getElementByName("detailTable").style.display = "none";
	}
}
function Validate(action)
{
	var IndexValue = document.ModifyGroupMember.groupname.selectedIndex;
	var Name = GroupListName[IndexValue];
	var TotalMember = GroupMember[IndexValue];
	var DateTime    = GroupDate[IndexValue];
	var GroupInfo   = GroupDesc[IndexValue];
	var check = 0 ; 
	for (var i=0; i < document.ModifyGroupMember.memberid.length; i++)
	{
		if(document.ModifyGroupMember.memberid[i].checked == true)
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
	document.ModifyGroupMember.groupId.value = IndexValue;
	document.ModifyGroupMember.Action.value= action ;
}

function cofirmDelete()
{
if(confirm ("Are You Sure Want To Delete This Entry !!"))
                {return true;}
                else
                {return false;}
}

function cofirmUpdate()
{
if(confirm ("Are You Sure Want To Update This Entry !!"))
                {return true;}
                else
                {return false;}
}
function markAll()
{
	for (var i=0;i<document.ModifyGroupMember.memberid.length;i++)
	{
		document.ModifyGroupMember.memberid[i].checked = true ; 
	}
}
function unmarkAll()
{
	for (var i=0;i<document.ModifyGroupMember.memberid.length;i++)
	{
		document.ModifyGroupMember.memberid[i].checked =false ;
	}
}
