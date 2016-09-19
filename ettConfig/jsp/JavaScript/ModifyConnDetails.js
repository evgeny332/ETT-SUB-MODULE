/*function Validate()
{
	var cname = document.getElementById("ConName").value;
	var prefix = document.getElementById("Prefix").value;
	var tps = document.getElementById("TPS").value;
	var kannel =document.getElementById("Kannel").value;
	if(cname == "")
	{
		alert("Connection Name is empty..!!!");
		return false;
	}
	else if(tps == "" || isNaN(tps))
	{
		alert("TPS is empty or not numeric..!!!");
		return false;
	}
	else if(kannel == "")
	{
		alert("Enter Kannel Url");
		return false;
	}
}*/
function Validate()
{
        var cname = document.getElementById("ConName").value;
        var prefix = document.getElementById("Prefix").value;
        var tps = document.getElementById("TPS").value;
        var kannel =document.getElementById("Kannel").value;
        var cmode =document.getElementById("cmode").value;
	var regExpression = /^[A-Za-z\-]*$/;
        if(cname == "")
        {
                alert("Connection Name is empty..!!!");
                return false;
        }
        else if(tps == "" || isNaN(tps))
        {
                alert("TPS is empty or not numeric..!!!");
                return false;
        }
/*        else if(prefix == "")
        {
                alert("Prefix is empty or not numeric..!!!");
                return false;
        }*/
	else if(!regExpression.test(prefix))
	{
		alert("Prefix is empty or not numeric..!!! Ankit");
                return false;
	}
        if(cmode == "")
        {
                alert("Connection mode must not empty..!!!");
                return false;
        }
        else if(cmode=="HTTP")
        {
                var Kannel =document.getElementById("Kannel").value;
                if(Kannel == "")
                {
                        alert("Please specify Connection URL  !!!");
                        return false ;
                }
        }
	else
        {
                var cip =document.getElementById("cip").value;
                var cport =document.getElementById("cport").value;
                if(cip == "")
                {
                        alert("Please specify connection ip !!!");
                        return false ;
                }
                else if(isNaN(cip))
                {
                        alert("Please specify Numeric connection ip !!!");
                        return false;
                }
                if(cport == "")
                {
                        alert("Please specify connection port !!!");
                        return false ;
                }
                else if(isNaN(cport))
                {
                        alert("Please specify Numeric connection port !!!");
                        return false;
                }
        }
}
function OnloadUpdate()
{
	document.getElementById("formatedTab2").style.display = "none";
}
function updateValues(name,tps,prefix,url, cmode, ip, cp)
{
	document.getElementById("formatedTab2").style.display = "";
	var cname =document.getElementById("ConName").value = name;
	var prefix =document.getElementById("Prefix").value = prefix;
	var tps =document.getElementById("TPS").value = tps;
	var kannel =document.getElementById("Kannel").value = url;
	var selectobject=document.getElementById("cmode")
	for (var i=0; i<selectobject.length; i++){
		//alert(selectobject.options[i].text+" "+selectobject.options[i].value)
		//alert("cmode"+cmode);
		if(selectobject.options[i].value==cmode)
		{
			//alert(selectobject.options[i].text+" "+selectobject.options[i].value)
			ModifyConnDetails.cmode.options[i].selected=true;
			if( (cmode=="UDP") || (cmode=="TCP") )
			{
				//alert(selectobject.options[i].text+" "+selectobject.options[i].value)
				document.ModifyConnDetails.cport.value=cp;
				document.ModifyConnDetails.cip.value=ip;
				document.getElementById("ciid").style.display = "";
				document.getElementById("cpid").style.display = "";
			}
			else
			{
				document.getElementById("ciid").style.display = "none";
                                document.getElementById("cpid").style.display = "none";
			}
		}
	}

	if(cname == "")
        {
                alert("Connection Name is empty..!!!");
                return false;
        }
        else if(tps == "" || isNaN(tps))
        {
                alert("TPS is empty or not numeric..!!!");
                return false;
        }
        else if(kannel == "")
        {
                alert("Enter Kannel Url");
                return false;
        }
}
function DeleteConn(name)
{
	var cfm  = confirm("Delete "+name+" connection");
	if(cfm)
	{
		var FullUrl = deletePath +name ;
		window.location.href=FullUrl;
	}
}
function countChar(Value)
{
        var val = Value ;
	document.AddTemplate.charcount.value = val.length;
        if(val.length > 159)
        {
                alert("More than 160 Charactors are not allowed in Message");
                document.FileUpload.message.value  = val.substring(0,160);
                document.FileUpload.charcount.value = (document.FileUpload.message.value).length;
        }
}
function checkMode()
{
        var IndexValue = document.ModifyConnDetails.cmode.selectedIndex;
        var SelectedVal = document.ModifyConnDetails.cmode.options[IndexValue].value;
        if(SelectedVal == "TCP" || SelectedVal == "UDP")
        {
                document.getElementById("cpid").style.display="";
                document.getElementById("ciid").style.display="";
        }
        else
        {
                document.getElementById("cpid").style.display="none";
                document.getElementById("ciid").style.display="none";
        }
}
