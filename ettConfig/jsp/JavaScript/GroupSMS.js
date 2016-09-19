function updateList(IndexValue)
{
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
	}
}
function updateDescription(Index)
{
	var IndexValue = Index ; 
	document.GroupSMS.groupname.options[IndexValue].selected = true ;
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
	}
}
function Validate()
{
	var IndexValue = document.GroupSMS.groupname.selectedIndex;
	if(IndexValue == 0 )
	{
			alert("Please Select Group");
			document.GroupSMS.groupname.focus();
			return false;
	}
	var Msg = document.GroupSMS.message.value ; 
	if(Msg == "")
	{
			alert("Message Can not be blank");
			document.GroupSMS.message.focus();
			return false;
	}
}
function markAll()
{
	for (var i=0;i<document.GroupSMS.memberid.length;i++)
	{
		document.GroupSMS.memberid[i].checked = true ; 
	}
}
function unmarkAll()
{
	for (var i=0;i<document.GroupSMS.memberid.length;i++)
	{
		document.GroupSMS.memberid[i].checked =false ;
	}
}
function updateTemplate(IndexValue)
{
	//var IndexValue = document.FileUpload.template.selectedIndex;
	var SelectedVal = document.GroupSMS.template.options[IndexValue].value;
	document.GroupSMS.message.value = SelectedVal ;
	countChar(SelectedVal); 
}
function countChar(Value)
{
        var val = Value ;
		document.GroupSMS.charcount.value = val.length;
       // if(val.length > 159)	//tushar increase it to 640
	if(val.length > 639)
        {	
			//tushar comment
               // alert("More than 160 Charactors are not allowed in Message");
	       // document.GroupSMS.message.value  = val.substring(0,160);
	
		alert("More than 640 Charactors are not allowed in Message");
                document.GroupSMS.message.value  = val.substring(0,640);
                document.GroupSMS.charcount.value = (document.GroupSMS.message.value).length;
        }
}
