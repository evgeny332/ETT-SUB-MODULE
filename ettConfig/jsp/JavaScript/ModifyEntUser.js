function updateList()
{
	var IndexValue = document.ModifyEntUser.groupname.selectedIndex;
	var TotalMember = GroupMember[IndexValue-1];
	var DateTime    = GroupDate[IndexValue-1];
	var GroupInfo   = GroupDesc[IndexValue-1];
	if(IndexValue == 0)
	{
		document.getElementById("description").innerHTML= "None";
		document.getElementById("totalMember").innerHTML= "None";
		document.getElementById("creationDate").innerHTML="None";
		document.getElementById("formatedRow").style.display = "none";
		return ;
	}
	document.ModifyEntUser.groupId.value= IndexValue;
	document.ModifyEntUser.action=GetListPath;
    document.ModifyEntUser.submit();
}
function updateDescription(Index)
{

	var IndexValue = Index ; 
	document.ModifyEntUser.groupname.options[IndexValue].selected = true ;
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
		document.getElementById("formatedRow").style.display = "none";
//		document.getElementByName("detailTable").style.display = "none";
	}
}
function Validate(action)
{
	var check = 0 ; 
	var AssignedVal ;
	var selectVal ; 
	for (var i=0; i < document.ModifyEntUser.connListCheckBox.length; i++)
	{
		if(document.ModifyEntUser.connListCheckBox[i].checked == true)
		{
			check = check+1;
			AssignedVal = document.getElementById("assignedTps"+i).value;
			selectVal = document.ModifyEntUser.connListCheckBox[i].value; 
			var values = selectVal.substring(selectVal.lastIndexOf("#")+1);
			if( AssignedVal > parseInt(values))
			{
				alert("Not Allowed");
				return false ;
			}
			if(NaN(AssignedVal))
			{
				alert("Non - numeric tps is not allowed");
				document.getElementById("assignedTps"+i).focus();
				return false ; 
			}
			if( AssignedVal  <= 0)
			{
				alert("Assigned TPS should more than 0");
				document.getElementById("assignedTps"+i).focus();
				return false ; 

			}
		}
	}
	if(check == 0)
	{
		alert("Please select any connection");
		return false ;
	}

}
function markAll()
{
	for (var i=0;i<document.ModifyEntUser.memberid.length;i++)
	{
		document.ModifyEntUser.memberid[i].checked = true ; 
	}
}
function unmarkAll()
{
	for (var i=0;i<document.ModifyEntUser.memberid.length;i++)
	{
		document.ModifyEntUser.memberid[i].checked =false ;
	}
}
function checkData()
{
	if( userNameDetails == null || userNameDetails == "null")
	{
		document.getElementById("formatedTab2").style.display = "none";
	}
	else
	{
		var users = document.ModifyEntUser.username;
		for(var index = 0 ; index < users.options.length ; index ++ )
		{
			if(userNameDetails == users.options[index].value)
			{
				users.options[index].selected = true;
				break;
			}
		}
	}
}

function updateList()
{
	var IndexValue = document.ModifyEntUser.username.selectedIndex;
	if(IndexValue == 0)
	{
	
		document.getElementById("formatedTab2").style.display = "none";
		return ;
	}
	document.ModifyEntUser.action=GetListPath;
    document.ModifyEntUser.submit();
}
