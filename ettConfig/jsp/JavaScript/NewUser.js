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
        xmlHttp.open('POST', strURL, true);
        xmlHttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xmlHttp.onreadystatechange = function(){
	if (xmlHttp.readyState == 4)
	{ 
		var  msg=xmlHttp.responseText;
		changecontent(msg);
	}}
        xmlHttp.send(strURL);
}
function changecontent(msg)
{
	document.CreateUser.userExist.value=msg;
}
function CheckUserName(UrlUser , username)
{
        var url = UrlUser  + username;
        postRequest(url);
}
function Validate(UrlUserName)
{
	var user = document.CreateUser.username.value ; 
	if(user == "")
	{
		alert("Username can't be blank");
		document.CreateUser.username.focus();
		return false ; 
	}	
	var passwd = document.CreateUser.password.value; 
	if(passwd == "")
	{
		alert("Password can't be blank");
		document.CreateUser.password.focus();
		return false ; 
	}
	/*var HUBID = document.CreateUser.hubid.value;
        if(HUBID == "")
        {
                alert("HUB ID can't be blank");
                document.CreateUser.hubid.focus();
                return false ;
        }*/
	var length = document.CreateUser.Account.length ;
	var account  ; 
	for(var i=0 ; i < length ; i++)
	{
		if(document.CreateUser.Account[i].checked == true)
		{
			account = document.CreateUser.Account[i].value;
		}
	}
	if(account == "Prepaid")
	{
		var slimit = document.CreateUser.limit.value ;
		if(slimit == "")
		{
			alert("For prepaid user SMS limit can't be blank");
	                document.CreateUser.limit.focus();
        	        return false ;
		}
		if(isNaN(slimit))
		{
			alert("Non numeric SMS Limit is not allowed");
			document.CreateUser.limit.focus();
			return false ;
		}
	}
	var allocatedtps = document.CreateUser.allocatedtps.value;
	/* 
	if(allocatedtps == "")
	{
		alert("Assign TPS can't be blank");
		document.CreateUser.allocatedtps.focus();
		return false ; 
	}
	if(isNaN(allocatedtps))
	{	
		alert("Non numeric Assign TPS are not allowed");
		document.CreateUser.allocatedtps.focus();
		return false ;
	}
	if(allocatedtps <= 0)
	{
		alert("Assign TPS should more than 0");
		document.CreateUser.allocatedtps.focus();
		return false ;
	}*/
	if(allocatedtps > availTps)
	{
		alert("Assign TPS can not more than available TPS");
		document.CreateUser.allocatedtps.focus();
		return false ;
	}
	var checked = 0 ;
	var SenderId = document.getElementById("SenderName");
	var selectedIndex = SenderId.selectedIndex;
	if(selectedIndex == -1)
	{
		alert("Please select sender id");
		document.CreateUser.SenderName.focus();
		return false ;

	}
	var permission = document.getElementById("setPermission");
	if(permission.options.length <= 0 )
	{	
		alert("Assign permission first");
		document.CreateUser.SenderName.focus();
		return false ;
	}
	else
	{
		var Value = "" ;
		for(var index = 0 ; index < permission.options.length ; index ++ )
		{
			Value = Value +  permission.options[index].value+ "," ; 	
		}
		document.CreateUser.permission.value=Value;

	}
	document.CreateUser.userExist.value = "0";
	CheckUserName(UrlUserName, user);
	var isExist = document.CreateUser.userExist.value ;
	if(isExist > 0)
	{
		alert("Username already exist. please enter another name");
		document.CreateUser.username.focus();
		return false ; 
	}
	return true; 
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
function moveToRight()
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
function enableLimit()
{
	document.CreateUser.limit.disabled=false;
	document.CreateUser.validity.disabled=false;
	document.CreateUser.limit.focus();
}
function disableLimit()
{
	document.CreateUser.limit.value = "";
	document.CreateUser.limit.disabled=true;
	document.CreateUser.validity.value = "";
	document.CreateUser.validity.disabled=true;
	document.CreateUser.allocatedtps.focus();
}
function CheckUser(AccountType)
{
	if(AccountType == "Prepaid")
	{
		enableLimit();
		document.CreateUser.allocatedtps.disabled = true;
	}
}
