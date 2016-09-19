<%@ page language="java" session="true" import="java.util.*" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%int length=0;

ArrayList userAuthList=null;
String path = "" ;
String userName = "" ;
try
{
	userName =(String)session.getAttribute("userName");
	if(userName == null)
        {
        	response.sendRedirect(request.getContextPath()+"/jsp/redirectpage.jsp?ST=E016");
        }

	path = request.getContextPath()+"/" ;
	userAuthList=(ArrayList)session.getAttribute("UserAuthList");
	
}
catch(Exception e)
{
	e.printStackTrace();
	response.sendRedirect(request.getContextPath()+"/jsp/redirectpage.jsp?ST=E003");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SpeedSMS</title>
<link href="include/default.css" rel="stylesheet" type="text/css" />
</head>
<script language="javascript" src ="JavaScript/Leftband.js"></script>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="mainTab">
                  <tr>
                    <td><table width="100%" border="0" cellspacing="0" cellpadding="0" >
                        <tr>

                          <td width="9" background="images/pannel-lefttop-red.png">&nbsp;</td>
                          <td height="33" background="images/pannel-centertop-red.png" id="leftnevi-text">Select Menu</td>
                          <td width="9" background="images/pannel-righttop-red.png">&nbsp;</td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr>
                <td width="170" valign="top" id="leftnaviborder">
				<table width="100%" border="0" cellpadding="7" cellspacing="0" id="leftsubnev">
				
			<tr id="trhome">
					<TD class="arial11grey2" style="height: 20px;">
									<A href="<%=path%>jsp/welcome.jsp" target="mainArea">
			                        <img src="images/home_icon.jpg" id="imagehome">&nbsp;Home</A>
					 </tr>

			<% for(int index=0; index < userAuthList.size() ; index++)
			  {
				ArrayList TabInfoList  =(ArrayList)userAuthList.get(index);
				boolean IsRootDone = false ;
				boolean childTableDone = false ;
				for(int TabIndex= 0 ; TabIndex < TabInfoList.size() ; TabIndex++)
				{
						
					String TabInfo[] = (String[])TabInfoList.get(TabIndex);
					//System.out.println(" TabId ["+TabInfo[3]+"] TabInfo[0] ["+TabInfo[0]+"] TabInfo[1] ["+TabInfo[1]+"] TabInfo[2] ["+TabInfo[2]+"]");
					if(TabIndex > 0 && !childTableDone)
					{%>
						<TR id='e<%=index%>' style="display: none;">
					         <TD style="width: 166px;">
					                <TABLE id="dt<%=index%>" cellspacing="0" cellpadding="0" style="width: 166px; background-color: #FAFAFA;">
					<% 
						childTableDone = true ; 
					} 
					if(IsRootDone){
						//This is for Child Nodes
					%>
					<tr>
					<TD class="arial11grey2" style="height: 20px;">
                                        &nbsp;&nbsp;<img src="images/rd_arw1.gif">&nbsp;&nbsp;<A href="<%=path%><%=TabInfo[1]%>" target="mainArea" ><%=TabInfo[0]%></A>
					 </tr>
					<%}else{
						// This is For parent nodes
					%>
					<tr id="tr<%=index%>">
						<TD class="arial11grey2" style="height: 20px;">
						<%
							if(TabInfoList.size() > 1 )
							{%>
						                <A href="javascript:expand('<%=index%>');">
							<%}else{%>
								<A href="<%=path%><%=TabInfo[1]%>" target="mainArea">
							<%}%>
			                        <img src="images/plus.gif" id="image<%=index%>">&nbsp;<%=TabInfo[0]%></A>
					 </tr>
					<%IsRootDone= true ;}
				}
				if(childTableDone)
				{%>	
		                </TABLE></TD></TR>
				<%}
				
			  }	

			%>
			<tr id="trlogout">
					<TD class="arial11grey2" style="height: 20px;">
									<A href="<%=path%>jsp/redirectpage.jsp" target="mainArea">
			                        <img src="images/Log_Out.png" id="imagehome">&nbsp;Logout</A>
					 </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td ><table width="100%" style="height:10px;" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="9" align="left" valign="top" ><img src="images/pannel-leftbottom-gray.png" width="9" height="10" /></td>

                          <td style=" background-image:url(images/pannel-centerbottom-gray.png); background-repeat:repeat-x; vertical-align:top;">&nbsp;</td>
                          <td width="9" align="right" valign="top"><img src="images/pannel-rightbottom-gray.png" width="9" height="10" /></td>
                        </tr>
                    </table></td>
                  </tr>
                </table>

</body>
</html>
