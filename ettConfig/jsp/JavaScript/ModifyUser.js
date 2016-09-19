function postRequest(strURL)
{
		
        var xmlHttp;
	    if(window.XMLHttpRequest)
        {
                var xmlHttp = new XMLHttpRequest();
        }
        else if(window.ActiveXObject)
        {
                var xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlHttp.open('GET', strURL, true);
        xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xmlHttp.onreadystatechange = function(){if (xmlHttp.readyState == 4){  var  msg=xmlHttp.responseText;                                                                                                                                                  	 changecontent(msg);
	}}
        xmlHttp.send(strURL);
}
function changecontent(msg)
{
	document.ModifyUser.userExist.value=msg;
}
function CheckUserName(username)
{
        var url =UrlUserName+username;
        postRequest(url);
}
function Validate(userType,Username)
{
	if(userType == "Prepaid")
	{
	}
	else
	{
		var allocatedtps = document.ModifyUser.allocatedtps.value; 
		if(allocatedtps == "")
		{
			alert("Assign TPS can't be blank");
			document.ModifyUser.allocatedtps.focus();
			return false ; 
		}
		if(isNaN(allocatedtps))
		{	
			alert("Non numeric Assign TPS are not allowed");
			document.ModifyUser.allocatedtps.focus();
			return false ;
		}
		/*if(allocatedtps <= 0)
		{
			alert("Assign TPS should more than 0");
			document.ModifyUser.allocatedtps.focus();
			return false ;
		}*/
		//alert(" availTps "+availTps +" allocatedtps "+allocatedtps);
		if(allocatedtps > availTps)
		{
			alert("Assign TPS can not more than available TPS");
			document.ModifyUser.allocatedtps.focus();
			return false ;
		}
		if(allocatedtps < ChildUsedTps)
		{
			alert("Assign TPS can not less than already used TPS by user.");
			document.ModifyUser.allocatedtps.focus();
			return false ;
		}
	}
	var checked = 0 ;
	var SenderId = document.getElementById("AllotedSenderName");
	if(SenderId.options.length <= 0 )
	{	
		alert("Please select sender id");
		document.ModifyUser.AllotedSenderName.focus();
		return false ;
	}
	else
	{
		var SenderValue = "" ;
		for(var index = 0 ; index < SenderId.options.length ; index ++ )
		{
			
			SenderValue = SenderValue +  SenderId.options[index].value + "," ; 	
		}
		document.ModifyUser.SenderNames.value=SenderValue;
	}
	var permission = document.getElementById("setPermission");
	if(permission.options.length <= 0 )
	{	
		alert("Assign permission first");
		document.ModifyUser.SenderName.focus();
		return false ;
	}
	else
	{
		var Value = "" ;
		for(var index = 0 ; index < permission.options.length ; index ++ )
		{
			Value = Value +  permission.options[index].value+ "," ; 	
		}
		document.ModifyUser.permission.value=Value;
	}
	var cfm  = confirm(" Update "+Username+" user details");
	return cfm; 
}


function leftDirection()
{
	var selectbox = document.getElementById("setPermission");
	var index=selectbox.selectedIndex;
	var elementToMove=selectbox.options[index].text;
	var elementToMove_val = selectbox.options[index].value;
	selectbox.remove(index);
	var selectbox2 = document.getElementById("AllPermission");
	var optn = document.createElement("OPTION");
	optn.text = elementToMove;
	optn.value = elementToMove_val;
	selectbox2.options.add(optn);
}
function moveToLeftPermission()
{
	var selectbox = document.getElementById("setPermission");
	var selectbox2 = document.getElementById("AllPermission");
	var selected = new Array();

	for (var index = 0; index < selectbox.options.length ; index++)
	{
		if (selectbox.options[index].selected) 
		{
			var elementToMove=selectbox.options[index].text;
			var elementToMove_val = selectbox.options[index].value;
			var optn = document.createElement("OPTION");
			optn.text = elementToMove;
			optn.value = elementToMove_val;
			selectbox2.options.add(optn);
			selected.push(elementToMove_val);
		}
	}
	for(var index = 0 ; index < selected.length ; index ++ )
	{
		selectbox = document.getElementById("setPermission");
		for (var indexAll = 0; indexAll < selectbox.options.length ; indexAll++)
		{
			var elementToMove_val = selectbox.options[indexAll].value;
			if(selected[index] == elementToMove_val)
			{
				selectbox.remove(indexAll);	
				break ;
			}
		}
	}
}
function moveToRightPermission()
{
	var selectbox = document.getElementById("AllPermission");
	var selectbox2 = document.getElementById("setPermission");
	var selected = new Array();
	for (var index = 0; index < selectbox.options.length ; index++)
	{
		if (selectbox.options[index].selected) 
		{
			var elementToMove=selectbox.options[index].text;
			var elementToMove_val = selectbox.options[index].value;
			var optn = document.createElement("OPTION");
			optn.text = elementToMove;
			optn.value = elementToMove_val;
			selectbox2.options.add(optn);
			selected.push(elementToMove_val);
		}
	}
	for(var index = 0 ; index < selected.length ; index ++ )
	{
		selectbox = document.getElementById("AllPermission");
		for (var indexAll = 0; indexAll < selectbox.options.length ; indexAll++)
		{
			var elementToMove_val = selectbox.options[indexAll].value;
			if(selected[index] == elementToMove_val)
			{
				selectbox.remove(indexAll);	
				break ;
			}
		}
	}
}
function moveToLeftSenderId()
{
	var selectbox = document.getElementById("AllotedSenderName");
	var selectbox2 = document.getElementById("AllSenderName");
	var selected = new Array();

	for (var index = 0; index < selectbox.options.length ; index++)
	{
		if (selectbox.options[index].selected) 
		{
			var elementToMove=selectbox.options[index].text;
			var elementToMove_val = selectbox.options[index].value;
			var optn = document.createElement("OPTION");
			optn.text = elementToMove;
			optn.value = elementToMove_val;
			selectbox2.options.add(optn);
			selected.push(elementToMove_val);
		}
	}
	for(var index = 0 ; index < selected.length ; index ++ )
	{
		selectbox = document.getElementById("AllotedSenderName");
		for (var indexAll = 0; indexAll < selectbox.options.length ; indexAll++)
		{
			var elementToMove_val = selectbox.options[indexAll].value;
			if(selected[index] == elementToMove_val)
			{
				selectbox.remove(indexAll);	
				break ;
			}
		}
	}
}
function moveToRightSenderId()
{
	var selectbox = document.getElementById("AllSenderName");
	var selectbox2 = document.getElementById("AllotedSenderName");
	var selected = new Array();
	for (var index = 0; index < selectbox.options.length ; index++)
	{
		if (selectbox.options[index].selected) 
		{
			var elementToMove=selectbox.options[index].text;
			var elementToMove_val = selectbox.options[index].value;
			var optn = document.createElement("OPTION");
			optn.text = elementToMove;
			optn.value = elementToMove_val;
			selectbox2.options.add(optn);
			selected.push(elementToMove_val);
		}
	}
	for(var index = 0 ; index < selected.length ; index ++ )
	{
		selectbox = document.getElementById("AllSenderName");
		for (var indexAll = 0; indexAll < selectbox.options.length ; indexAll++)
		{
			var elementToMove_val = selectbox.options[indexAll].value;
			if(selected[index] == elementToMove_val)
			{
				selectbox.remove(indexAll);	
				break ;
			}
		}
	}
}
function UpdateData(User)
{
	if(User == null || User ==  "null" || User == "None")
	{
		document.getElementById("formatedTab2").style.display = "none";
	}
	else
	{
		document.getElementById("formatedTab2").style.display = "";
		var selectbox = document.getElementById("userList");
		for (var index = 0; index < selectbox.options.length ; index++)
		{
			if (selectbox.options[index].text == User) 
			{
				selectbox.options[index].selected= true ;
				break;
			}
		}
	}
}
function getUserInfo()
{
	var selectbox = document.getElementById("userList");
	var index=selectbox.selectedIndex;
	var user = selectbox.options[index].value;
	if(user == "none" )
	{
		document.getElementById("formatedTab2").style.display = "none";
		return false ;
	}
	else
	{
		window.location.href=UrlUserName+user;
	}
}

