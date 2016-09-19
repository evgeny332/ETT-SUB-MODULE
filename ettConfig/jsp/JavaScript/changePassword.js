function Validate()
{

	var CPassword = document.changePassword.cpassword.value ;
        if(CPassword == "" )
        {
                alert("Current Password can't be blank");
                document.changePassword.cpassword.focus();
                return false;
        }

	var newPassword = document.changePassword.password.value ; 
	if(newPassword == "")
	{
		alert("Password can't be blank");
		document.changePassword.password.focus();
		return false; 
	}
	var newPassword1 = document.changePassword.password1.value ;
        if(newPassword1 != newPassword)
        {
                alert(" Password do not match");
                document.changePassword.password1.focus();
                return false;
        }

	return true; 
}
