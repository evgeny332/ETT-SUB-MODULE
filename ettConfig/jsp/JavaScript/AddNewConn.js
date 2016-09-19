function Validate()
{
	var cname = document.getElementById("ConName").value;
	var prefix = document.getElementById("Prefix").value;
	var tps = document.getElementById("TPS").value;
	var operator =document.getElementById("Operator").value;
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
	//else if(prefix == "")
	//{
//		alert("Prefix is empty or not numeric..!!!");
//		return false;
//	}
	else if(!regExpression.test(prefix))
        {
                alert("Prefix is empty or not numeric..!!!");
                return false;
        }
	else if(operator == "")
        {
                alert("Operator Field must not empty..!!!");
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
	if(kannel == "")
	{
		alert("Enter Kannel Url");
		return false;
	}
}
function checkMode()
{	
	var IndexValue = document.AddNewConn.cmode.selectedIndex;
	var SelectedVal = document.AddNewConn.cmode.options[IndexValue].value;
	if(SelectedVal == "TCP" || SelectedVal == "UDP")
	{
		document.getElementById("ciprow").style.display="";	
		document.getElementById("cportrow").style.display="";	
		document.getElementById("Kannelid").style.display="none";
	}
	else
	{
		document.getElementById("ciprow").style.display="none";	
		document.getElementById("cportrow").style.display="none";	
		document.getElementById("Kannelid").style.display="";
	}
}
