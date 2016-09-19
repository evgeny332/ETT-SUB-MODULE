function Validate()
{
	var senderid = document.AddSenderId.senderid.value ; 
	if(senderid == "")
	{
		alert("SenderId can't be blank");
		document.AddSenderId.senderid.focus();
		return false ; 
	}
	else{

                if(!isInteger(senderid)){

                        if( senderid.length > 8 ){
  alert( "Sender ID Should Be 10 Digit Number Or 8 Digit Alpha Numeric " );
                                return false;
                        }
                }else{

                        if(!( senderid.length  == 10 )){

                                alert( "Sender ID Should Be 10 Digit Number Or 8 Digit Alpha Numeric " );
                                return false;
                        }
                }

        }
	for(var index =0 ; index < SenderNameList.length ; index++)
	{
			if(senderid == SenderNameList[index])
			{
				alert("SenderId Already Exist ");
				document.AddSenderId.senderid.focus();
				return false ; 
			}
	}
	var cfm  = confirm("Add new sender id "+senderid);
	return cfm ; 

}
function isInteger( senderid ){
  for (i = 0 ; i < senderid.length ; i++) {
        if ((senderid.charAt(i) < '0') || (senderid.charAt(i) > '9'))
        {
                return false;
        }else{
                return true;
        }
	}
return true;
}

