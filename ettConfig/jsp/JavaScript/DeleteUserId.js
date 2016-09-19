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
        xmlHttp.onreadystatechange = function(){if (xmlHttp.readyState == 4){  var  msg=xmlHttp.responseText;                                                                                                                                                  	 changecontent(msg);
	}}
    xmlHttp.send(strURL);
}
function changecontent(msg)
{
	var userSelectbox = document.getElementById("SubUserList");
	for(var i = userSelectbox.options.length-1 ; i >= 0 ; i--)
	{
		userSelectbox.remove(i);
	}
	if(msg.length > 0)
	{
		while(msg.indexOf(",") != -1)
		{
			var value  = msg.substring(0,msg.indexOf(","));
			msg = msg.substring(msg.indexOf(",")+1);
			var optn = document.createElement("OPTION");
			optn.text = value;
			optn.value = value;
			userSelectbox.options.add(optn);
		}
	}
	else
	{
			var optn = document.createElement("OPTION");
			optn.text = "None";
			optn.value = "None";
			userSelectbox.options.add(optn);
	}
}
function getSubUsers()
{
		var userSelectbox = document.getElementById("userList");
		var userIndex=userSelectbox.selectedIndex;
		var username = userSelectbox.options[userIndex].value
	        var url = UrlUserName + username;
	        postRequest(url);
}
function Validate()
{
	var userSelectbox = document.getElementById("userList");
	var userIndex=userSelectbox.selectedIndex;
	if(userIndex == -1)
	{
		alert("Please select user");
		document.DeleteSenderId.userList.focus();
		return false ; 
	}
}

