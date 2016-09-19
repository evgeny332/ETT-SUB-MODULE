var map1 = new Object();
map1.E001="Your request submit Successfully.";
map1.E002="Your File has been uploaded succesfully . Your Campaign Id is <<CAMP_ID>>";
map1.E003="Your File with Message has been uploaded succesfully . Your Campaign Id is <<CAMP_ID>>";
map1.E004="<<CAMP_ID>> group has been created Successfully.";
map1.E005="<<CAMP_ID>> group has been deleted Successfully.";
map1.E006="Sender Id <<CAMP_ID>> added Successfully.";
map1.E007="Sender Id <<CAMP_ID>> deleted Successfully.";
map1.E008="<<CAMP_ID>> user details added Successfully.";
map1.E009="<<CAMP_ID>> user details deleted Successfully.";
map1.E010="<<CAMP_ID>> user password updated Successfully.";
map1.E011="<<CAMP_ID>> user details updated Successfully.";
map1.E012="<<CAMP_ID>> template added Successfully.";
map1.E013="<<CAMP_ID>> template deleted Successfully.";
map1.E014="<<CAMP_ID>> template modify Successfully.";
map1.E015="Member of group <<CAMP_ID>> added Successfully.";
map1.E016="Your session has been expired. Please login again";
map1.E017="Invalid Login id/Password . Please try again";
map1.E018="<<CAMP_ID>> delete successfully.";
map1.E019="<<CAMP_ID>> Connection added successfully.";
map1.E020="<<CAMP_ID>> Connection details updated successfully.";
map1.E021="Your request submitted successfully . Your Campaign Id is <<CAMP_ID>>";
map1.E022="Your account has been expired . Please contact your service provider";
map1.E023="Username <<CAMP_ID>> is not available .Please select another username";
map1.E099="Welcome to Bulk SMS";
map1.E024="WhiteList File Uploaded Successfully";
map1.E025="Blacklist File Uploaded Successfully";
map1.E026="Your File has been uploaded succesfully . Your File Name is <<CAMP_ID>>";
map1.E027="This File exists in database . Your File Name is <<CAMP_ID>>";

function show_msg(val,campid)
{
	var chk="map1."+val;
	var msg1 = "";
	var msg =  "" ;
	msg1 = eval(chk); 
	var dayString = campid.split("#");
	for(i=0;i<dayString.length;i++)
		alert("dayString["+i+"]=="+dayString[i]);
	//msg = msg1.replace("<<CAMP_ID>>",campid);
	//document.getElementById("msg_board").innerHTML=msg;
}
