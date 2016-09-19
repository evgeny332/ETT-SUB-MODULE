<%@ page language="java" session="true" import="java.util.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%--window.opener.location.href='Url' --%>
<script language="javascript" type="text/javascript">
var userList = new Array();

<%
	String userName = "" ;
	int OverALLtotalTps = 0 ;
	int OverALLAvailTps = 0 ;
	ArrayList UserListExist = null ; 
	try
	{
		userName =(String)session.getAttribute("userName");
		String childUser = request.getParameter("newUser");
		UserListExist = (ArrayList)session.getAttribute("AllUserList");
		System.out.println("UserListExist Size ::" + UserListExist.size());
		for(int index =0 ; index <  UserListExist.size() ; index++)
		{ 
			String users= (String)UserListExist.get(index);
			System.out.println("UserListExist Size ::" + UserListExist.get(index));
			%>
				userList[<%=index%>] = "<%=users%>";
			<%
		}	
	}
	catch(Exception Exp)
	{
		Exp.printStackTrace();
		response.sendRedirect(request.getContextPath()+"/jsp/redirectpage.jsp?ST=E003");
	} 
%>
var userNameDetails = "<%=childUser%>";
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="include/cms.css" rel="stylesheet" type="text/css"/>
<title>Untitled Document</title>
</head>
<script language="javascript" type="text/javascript" src="JavaScript/ModifyEntUser.js">
</script>
<body onload="checkData()">
<form action="<%=response.encodeURL(request.getContextPath())%>/CreateEntUser" method="get" enctype="multipart/form-data"		name="ModifyEntUser" id="ModifyEntUser">
   <table width="100%" height="40%" border="0" cellpadding="0" cellspacing="0">
	   <tr width="100%">
	      <td width="10%" height="26">&nbsp;</td>
		  <td width="56%" align="center" valign="bottom"><img src="images/blackberry_top_bg_new.jpg" width="100%"/></td>
	      <td width="34%">&nbsp;</td>
	 </tr>
	 <tr>
	   <td>&nbsp;</td>
	      <td align="center" valign="bottom"><table width="100%" height ="100%" border="0" cellpadding="0" cellspacing="0"  id="formatedTab">
            <tr bgcolor="#F7F7F7">
              <th colspan="5" scope="row" align="center" >Create Entrerprise USer</th>
            </tr>
            <!-- Write Your code Here-->
			
			<tr bgcolor="#F7F7F7">
              <td colspan="5" scope="row">&nbsp;</td>
			  </tr>
			<tr  bgcolor="#F7F7F7" > 
				    <td width="10%" scope="row"></td>	
					<th width="40%" scope="row">Connection Name</th>
			        <td width="20%" scope="row">Total Tps</td>
		            <td width="20%" scope="row">Available Tps</td>
					<td width="10%" scope="row">Assigned Tps</td>
			</tr>
			<tr  bgcolor="#F7F7F7" > 
				    <td width="10%" scope="row">&nbsp;</td>	
					<th width="40%" scope="row">&nbsp;</th>
			        <td width="20%" scope="row">&nbsp;</td>
		            <td width="20%" scope="row">&nbsp;</td>
					<td width="10%" scope="row">&nbsp;</td>
			</tr>
			<%
			try
			{
				ArrayList ConnStatusList = (ArrayList)session.getAttribute("EntUserList");
				System.out.println("ModifyEntUser.jsp ConnStatusList  :: " + ConnStatusList.size());
				boolean changeRow = false ;
				for(int index =0 ; index <  ConnStatusList.size() ; index++)
				{

					String StatusData[] = (String [])ConnStatusList.get(index);
					System.out.println("ModifyEntUser.jsp StatusData[0]  :: " + StatusData[0]);
					int availTps = ( Integer.parseInt(StatusData[1]) - Integer.parseInt(StatusData[2]));
					OverALLtotalTps =  OverALLtotalTps + Integer.parseInt(StatusData[1]) ;
					OverALLAvailTps =  OverALLAvailTps + Integer.parseInt(StatusData[2]) ;
					if(changeRow)
					{%>
					    <tr class ="contentblack">
					<%
						changeRow= false ;
					}
					else
					{%>
					    <tr bgcolor="#F7F7F7" class ="contentblack">
					<%
						changeRow = true ; 
					}
					%>
					<td align="center"><input type="checkBox" name="connListCheckBox" value="<%=StatusData[0]%>#<%=index%>#<%=availTps%>"></td>

		            <th width="40%" scope="row" align="center"><%=StatusData[0]%></th>
			        <td width="20%" scope="row" align="center"><%=StatusData[1]%></td>
		            <td width="20%" scope="row" align="center"><%=availTps%></td>
					<td width="10%" scope="row" align="center"><input type="text" id = "assignedTps<%=index%>" name ="assignedTps<%=index%>" value ="0" class="contentblack"></td>
					</tr>
					<%
				}
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
		%>
            <tr>
              <th colspan="5" scope="row" align="center"><INPUT TYPE="hidden" NAME="TotalTps" value ="<%=OverALLtotalTps%>"><INPUT TYPE="hidden" NAME="UsedTps" value ="<%=OverALLAvailTps%>">&nbsp;</th>
            </tr>

		<tr bgcolor="#F7F7F7">
              <td colspan="2" scope="row" align="right" >Enter user name</td>
  			  <td  scope="row" align="left" width="5%">&nbsp;</td>
			  <td colspan="2" scope="row" align="left" ><INPUT TYPE="text" id="NewUserName" name ="NewUserName" value =""></td>
		  </tr>

			<tr bgcolor="#F7F7F7">
              <td colspan="5" scope="row" align="center" ><INPUT TYPE="submit" value ="Submit" onclick="javascript: return Validate();"></td>
		  </tr>
          </table></td>
	      <td>&nbsp;</td>
	 </tr>
    <tr>
      <td width="10%">&nbsp;</td>
      <td width="57%" align="center" valign="top"><img src="images/blackberry_bottom.gif" width="100%"/></td>
      <td width="33%">&nbsp;</td>
    </tr>
  </table>
</form>
</body>
</html>
