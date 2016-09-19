function updateList()
{
	var IndexValue = document.AddEntUser.groupname.selectedIndex;
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
	document.AddEntUser.groupId.value= IndexValue;
	document.AddEntUser.action=GetListPath;
    document.AddEntUser.submit();
}
function updateDescription(Index)
{

	var IndexValue = Index ; 
	document.AddEntUser.groupname.options[IndexValue].selected = true ;
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
	var username = document.AddEntUser.NewUserName.value ; 
	if(username == "")
	{
		alert("username can't be blank");
		document.AddEntUser.NewUserName.focus();
		return false ; 
	}
	for(var index =0 ; index < userList.length ; index++)
	{
			if(username == userList[index])
			{
				alert("username Already Exist ");
				document.AddEntUser.NewUserName.focus();
				return false ; 
			}
	}
	var check = 0 ; 
	var AssignedVal ;
	var selectVal ; 
	for (var i=0; i < document.AddEntUser.connListCheckBox.length; i++)
	{
		if(document.AddEntUser.connListCheckBox[i].checked == true)
		{
				check = check+1;
				AssignedVal = document.getElementById("assignedTps"+i).value;
				selectVal = document.AddEntUser.connListCheckBox[i].value ; 
				var values = selectVal.substring(selectVal.lastIndexOf("#")+1);
				if( AssignedVal > parseInt(values))
				{
					alert("Not Allowed");
					return false ;
				}
				if(NaN(AssignedVal))
				{
					alert("Non - Numeric TPS is not allowed");
					document.getElementById("assignedTps"+i).focus();
                                        return false ;
				}		
				if(AssignedVal <= 0 )
				{	
					alert("Assign TPS should more than 0");
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
	for (var i=0;i<document.AddEntUser.memberid.length;i++)
	{
		document.AddEntUser.memberid[i].checked = true ; 
	}
}
function unmarkAll()
{
	for (var i=0;i<document.AddEntUser.memberid.length;i++)
	{
		document.AddEntUser.memberid[i].checked =false ;
	}
}

