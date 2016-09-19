function checklist()
{
		
	var username;
	var password;

	username=document.Form1.username.value;
	password=document.Form1.password.value;
	if (Trim(document.Form1.username.value)=="")
	{   
		document.Form1.username.focus();
		return false;
	}

	tt=username+':::'+password;
	document.cookie = "itemnoS" + "=" + tt + "; path=/";
	document.Form1.username.value="";
	document.Form1.password.value="";
	//window.open('logcheck.aspx','','height=600,width=750');
	//   window.open('logcheck.aspx','','height=800,width=850','location=1,status=1,scrollbars=1,toolbars=1');
	//window.open('logcheck.aspx','mywindow','width=800,height=750,toolbar=yes,location=yes,directories=yes,status=yes,menubar=yes,scrollbars=yes,copyhistory=yes,resizable=yes'); 
	window.open('logcheck.aspx','','scrollbars=yes,resizable=yes,width=780,height=540,left=50,top=50,menubar=0'); 
}
        
function Trim(TRIM_VALUE){
	if(TRIM_VALUE.length < 1){
		return"";
	}
	TRIM_VALUE = RTrim(TRIM_VALUE);
	TRIM_VALUE = LTrim(TRIM_VALUE);
	if(TRIM_VALUE==""){
		return "";
	}
	else{
		return TRIM_VALUE;
	}
}

function RTrim(VALUE){
	var w_space = String.fromCharCode(32);
	var v_length = VALUE.length;
	var strTemp = "";
	if(v_length < 0){
		return"";
	}
	var iTemp = v_length -1;
	
	while(iTemp > -1){
		if(VALUE.charAt(iTemp) == w_space){
		}
		else{
			strTemp = VALUE.substring(0,iTemp +1);
			break;
		}
		iTemp = iTemp-1;
		
	} //End While
	return strTemp;
	
} //End Function

function LTrim(VALUE){
	var w_space = String.fromCharCode(32);
	if(v_length < 1){
		return"";
	}
	var v_length = VALUE.length;
	var strTemp = "";
	
	var iTemp = 0;
	
	while(iTemp < v_length){
		if(VALUE.charAt(iTemp) == w_space){
		}
		else{
			strTemp = VALUE.substring(iTemp,v_length);
			break;
		}
		iTemp = iTemp + 1;
	} //End While
	return strTemp;
} //End Function

function LeftTrim(){
	if(CheckEmpty(document.form1.username)){
		return;
	}
	document.form1.username.value = LTrim(document.form1.username.value);
}

function RightTrim(){
	if(CheckEmpty(document.form1.username)){
		return;
	}
	document.form1.username.value = RTrim(document.form1.username.value);
}

function AllTrim(){
	if(CheckEmpty(document.form1.username)){
		return false;
	}
	document.form1.username.value = Trim(document.form1.username.value);
	return true;
	//document.form1.submit();
}

function CheckEmpty(CONTROL){
	if(Trim(CONTROL.value)==""){
		CONTROL.value="";
		CONTROL.focus();
		return true;
	}
	else{
		return false;
	}
}