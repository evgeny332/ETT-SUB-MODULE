<meta http-equiv="cache-control" content="no-cache"> <!-- tells browser not to cache -->
<meta http-equiv="expires" content="0"> <!-- says that the cache expires 'now' -->
<meta http-equiv="pragma" content="no-cache"> <!-- says not to use cached stuff, if there is any -->

<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*,java.util.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<script language=javascript>
javascript:window.history.forward(1);
window.onbeforeunload = function (){}
</script>
<script language="javascript" type="text/javascript" >
<%

String userName = "" ;
String NewUser = "" ;
String UsedTps = request.getParameter("used");
String TotalTps = request.getParameter("total");
String AssignedTps = request.getParameter("assigned");
String Availlimit = request.getParameter("AvailLimit");

NewUser = request.getParameter("newUser");
ArrayList AvailSenderId = null ; 
ArrayList AvailBrandList = null ; 
int intUsedTps = 0 ; 
int intTotalTps = 0 ; 
int intAssignedTps = 0 ; 
int AvailTPS = 0 ;
ArrayList userAuthList=null;
ArrayList tabid = null;
String AccountType = "" ;
try
{
	userName =(String)session.getAttribute("userName");
	AvailSenderId = (ArrayList)session.getAttribute("AvailSenderId");
	AvailBrandList = (ArrayList)session.getAttribute("AvailBrandList");
	userAuthList = (ArrayList)session.getAttribute("UserAuthList");
	AccountType = (String)session.getAttribute("AccountType");
}	
catch(Exception Exp)
{
	Exp.printStackTrace();
	response.sendRedirect(request.getContextPath()+"/jsp/redirectpage.jsp?ST=E003");
} 
try
{
	intAssignedTps = Integer.parseInt(AssignedTps);
}
catch(Exception Exp){intAssignedTps = 0 ;}
try
{
	intUsedTps = Integer.parseInt(UsedTps);
}
catch(Exception Exp){ intUsedTps = 0 ; }
try
{	
	intTotalTps	= Integer.parseInt(TotalTps);
}
catch(Exception Exp)
{
	intTotalTps= 0 ;
}
System.out.println("UsedTps ["+intUsedTps+"] TotalTps ["+intTotalTps+"] AvailLimit["+Availlimit+"] ");
AvailTPS = (intTotalTps - intUsedTps) ;
String UrlUser = response.encodeURL(request.getContextPath()+"/checkUserName?username=");
if(userName == null)
{
	response.sendRedirect(request.getContextPath()+"/jsp/redirectpage.jsp?ST=E003");
}

%>
var availTps = <%=(intTotalTps-intUsedTps)%>;
var AvailLimit = <%=Availlimit%> ;
var AccountType= "<%=AccountType%>";
var UrlUserName = "<%=UrlUser%>"; 

</script>
<script language="javascript" src ="JavaScript/NewUser.js"></script>
<script language="javascript" src ="JavaScript/validate2.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="include/cms.css" rel="stylesheet" type="text/css" />
<title>Bulk SMS</title>
</head>
<body onload="CheckUser('<%=AccountType%>')">
<form id="CreateUser" name="CreateUser" method="post" action="<%=response.encodeURL(request.getContextPath())%>/CreateUser" >
<table width="100%" height="60%" border="0" cellpadding="0" cellspacing="0">
<tr width="100%">
<td width="10%" height="26">&nbsp;</td>
<td width="64%" align="center" valign="bottom"><img src="images/blackberry_top_bg_new.jpg" width="100%"/></td>
<td width="26%">&nbsp;</td>
</tr>
<tr>
<td>&nbsp;</td>
<td align="center" valign="bottom"><table width="99%" height="100%" border="0" cellpadding="1" cellspacing="0"  bgcolor="#F7F7F7" id="formatedTab">
<tr>
<th colspan="3" align="center" valign="top" >Create User</th>
</tr>
<tr>
<th width="44%" align="right" valign="top" >&nbsp;</th>
<td width="6%">&nbsp;</td>
<td width="50%">&nbsp;</td>
</tr>

<tr>
<th width="44%" align="right" valign="top" >Login Id</th>
<td width="6%">&nbsp;</td>
<td width="50%" align="left" valign="top"><b><%=userName%><b></td>
</tr>

<%
if(AccountType.equals("Postpaid"))
{
%>

<tr>
<th width="44%" align="right" valign="top" scope="row">Total TPS</th>
<td width="6%">&nbsp;</td>
<td width="50%"><%=TotalTps%>&nbsp;</td>
</tr>

<tr>
<th width="44%" align="right" valign="top" scope="row">Available TPS</th>
<td width="6%">&nbsp;</td>
<td width="50%"><%=AvailTPS%>&nbsp;</td>
</tr>

<%
}
else
{
%>
<tr>
<th width="44%" align="right" valign="top" scope="row">Available Limit</th>
<td width="6%">&nbsp;</td>
<td width="50%"><%=Availlimit%>&nbsp;</td>
</tr>

<%}%>
<tr>
<th width="44%" align="right" valign="top" scope="row">UserName</th>
<td>&nbsp;</td>
<td width="50%"><label>
	<%
if(NewUser  == null)
{%>
	<input name="username" type="text" id="username" size="20" maxlength="50" class ="contentblack" />

		<%}
		else
{%>

	<input name="username" type="text" id="username" size="20" value ="<%=NewUser%>" class ="contentblack" maxlength="50" readonly/>


		<%}%>
		</label>
		<INPUT TYPE="hidden" NAME="userExist" value ="0">
		</td>
		</tr>

		<tr>
		<th width="44%" align="right" valign="top" scope="row">Password</th>
		<td>&nbsp;</td>
		<td width="50%"><label>
		<input name="password" type="password" class ="contentblack" id="password" size="20" maxlength="50"/>
		</label></td>
		</tr>

		<tr>
		<th width="44%" align="right" valign="top" scope="row">Account Type</th>
		<td>&nbsp;</td>
		<td width="50%">
		<%
		if(AccountType.equals("Postpaid"))
		{%>
		<INPUT TYPE="radio" NAME="Account" Value="Postpaid" checked onclick="disableLimit()"><label>Postpaid</label>
		<INPUT TYPE="radio" NAME="Account" Value="Prepaid" onclick="enableLimit()"><label>Prepaid</label>
		<%}
		else
		{ %>
			<INPUT TYPE="radio" NAME="Account" Value="Prepaid" checked onclick="enableLimit()"><label>Prepaid</label>
		<%	
		}
		%>
		</td>
		</tr>
		 <tr>
                <th width="44%" align="right" valign="top" scope="row">URLPUSH</th>
                <td>&nbsp;</td>
		<td width="50%">
		 <select name="urlpush" id="urlpush" class ="contentblack" >
                <option value="ENABLE">ENABLE</option>
                <option value="DISABLE">DISABLE</option>
                </select>
		</td>
                </tr>

		 <tr>
                <th width="44%" align="right" valign="top" scope="row">SMPP</th>
                <td>&nbsp;</td>
                <td width="50%">
                 <select name="smpp" id="smpp" class ="contentblack" >
		<option value="ENABLE">ENABLE</option>
                <option value="DISABLE">DISABLE</option>
                </select>
                </td>
                </tr>
		
		<tr>
                <th width="44%" align="right" valign="top" scope="row">GATEWAY</th>
                <td>&nbsp;</td>
                <td width="50%">
                 <select name="gateway" id="gateway" class ="contentblack" >
                <option value="GATEWAY1">GATEWAY1</option>
                <option value="GATEWAY2">GATEWAY2</option>
                </select>
                </td>
                </tr>

		<tr>
		<th width="44%" align="right" valign="top" scope="row">SMS Limit</th>
		<td>&nbsp;</td>
		<td width="50%">
		<input name="limit" type="text" id="limit" size="20" maxlength="50" class ="contentblack" disabled />
		</td>
		</tr>

		<tr>
		<th width="44%" align="right" valign="top" scope="row">Validity Period</th>
		<td>&nbsp;</td>
		<td width="50%">
		 <input name="validity" type="text" id="validity" size="20" maxlength="50" class ="contentblack" disabled />days
		</td>
		</tr>
		


		<tr>
		<th width="44%" align="right" valign="top" scope="row">Assign TPS</th>
		<td>&nbsp;</td>
		<td width="50%" align="left"><label>
	<%
if(AssignedTps == null )
{ %>
	<input name="allocatedtps" type="text" id="allocatedtps" size="5" maxlength="5" class ="contentblack" />
		<%}
		else
{%>
	<input name="allocatedtps" type="text" id="allocatedtps" size="5" maxlength="5" value ="<%=AssignedTps%>" class ="contentblack" readonly />
		<%}%>

		</label></td>
		</tr>

		<tr>
		<th width="44%" align="right" valign="top" scope="row">Brand Logo</th>
		<td width="6%">&nbsp;</td>
		<td width="50%">
		<select name="brand_logo" id="brand_logo" class ="contentblack">
		<%
		for (int index= 0 ; index < AvailBrandList.size() ; index++ )
		{	
			String BrandInfo[] = (String[])AvailBrandList.get(index);
			
		%>
		<option value="<%=BrandInfo[1]%>"><%=BrandInfo[0]%></option>
		<%}
		%>
		</select>
		&nbsp;</td>

		</tr>



		<tr>
		<th width="44%" align="right" valign="top" scope="row">Sender ID</th>
		<td width="6%">&nbsp;</td>
		<td width="50%">
		<select name="SenderName" id="SenderName" multiple size="5" class ="contentblack">
		<%
		for (int index= 0 ; index < AvailSenderId.size() ; index++ )
		{	
			String sendername = (String)AvailSenderId.get(index) ;
			%>
		<option value="<%=sendername%>"><%=sendername%></option>
		<%}
		%>
		</select>
		&nbsp;</td>

		</tr>
		<tr>
                <th width="44%" align="right" valign="top" scope="row">HUB ID</th>
                <td width="6%">&nbsp;</td>
                <td width="50%">
		<input type ="text" id ="hubid" name="hubid" value="" size="20" maxlength="1" class ="contentblack" onblur="extractNumber(this,0,false);"
onkeyup="extractNumber(this,0,false);"
onkeypress="return blockNonNumbers(this, event, false , false);"/>
		</tr>
		<tr>
                <th width="44%" align="right" valign="top" scope="row">Circle</th>
                <td width="6%">&nbsp;</td>
                <td width="50%">
                <input type ="text" name ="circle" id ="circle" value="" size="20" maxlength="2" class ="contentblack" onblur="extractNumber(this,0,false);"
onkeyup="extractNumber(this,0,false);"
onkeypress="return blockNonNumbers(this, event, false , false);"/>
                </tr>	

	
		<tr>
		<th width="44%" align="right" valign="top" scope="row">Available Permission</th>
		<td>&nbsp;</td>
		<td width="50%"><b>Assigned Permission</b></td>
		</tr>
		<tr>
		<th width="44%" align="right" valign="top" scope="row">
		<select name="AllPermission" id="AllPermission" multiple size="5" class ="contentblack">
		<%
		for(int index=0; index < userAuthList.size() ; index++)
{
	ArrayList TabInfoList  =(ArrayList)userAuthList.get(index);
	boolean IsRootDone = false ;
	boolean childTableDone = false ;
	for(int TabIndex= 0 ; TabIndex < TabInfoList.size() ; TabIndex++)
	{
		String TabInfo[] = (String[])TabInfoList.get(TabIndex);
		System.out.println("TabId ["+TabInfo[3]+"] TabInfo[0] ["+TabInfo[0]+"] TabInfo[1] ["+TabInfo[1]+"] TabInfo[2] ["+TabInfo[2]+"]");
		if(TabInfo[1] != null)
		{
			%>
				<option value="<%=TabInfo[3]%>"><%=TabInfo[0]%></option>
				<%}

	}
}
%>
</select>			</th>
<td width="6%" align="center"><p>
<input type="button" name="redirect" id="redirect" value=">" onclick="moveToRight()"/>
</p>
<p>
<input type="button" name="re" id="re" value="<" onclick="leftDirection()"/>
</p>			</td>
<td width="50%">
<select name="setPermission" id="setPermission" multiple size="5" class ="contentblack">
</select>
<INPUT TYPE="hidden" NAME="permission">
</td>
</tr>

<tr>
<th colspan="3" align="center" valign="top" scope="row"><input type="submit" name="submit" id="submit" value="Submit" onclick="javascript: return Validate('<%=UrlUser%>');" /></th>
</tr>
</table></td>
<td>&nbsp;</td>
</tr>
<tr>
<td width="10%" height="26">&nbsp;</td>
<td width="64%" align="center" valign="top"><img src="images/blackberry_bottom.gif" width="100%"/></td>
<td width="26%">&nbsp;</td>
</tr>
</table>
</form>
</body>
</html>
