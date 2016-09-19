<%@ page language="java" session="true" import="java.io.*, java.util.*, java.sql.*,oracle.jdbc.driver.*" contentType="text/html;charset=UTF-8"%>
<%@ page import="com.*" %>
<html>
<head>
<meta http-equiv="Content-Language" content="en-us">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="expires" CONTENT="0">
<title>Miss Caller Notification</title>
<link rel="stylesheet" type="text/css" href="include/service_creation.css">
<style type="text/css">
</style>
<script type="text/javascript" SRC="JavaScript/msg.js">
</script>
<SCRIPT LANGUAGE="Javascript">
<%



function prin()
 {
<%if(request.getParameter("error")!=null)
{%>
alert("inserted successfully");
<%}%> }
                        	function initialize_campaign_id()
                                {
                                        var i,j;
                                        i=document.form_1.up_load.value.indexOf('.');
                                        for(j=i;document.form_1.up_load.value.charAt(j)!='\\';j--);
                                        document.form_1.campaign_id.value=document.form_1.up_load.value.substring(j+1,i);
                          		document.form_1.campaign_id.value=document.form_1.up_load.value.substring(j+1,i);
                                }

</SCRIPT>
</head>
<BODY style="BACKGROUND-IMAGE: url(res/images/homebackground_image.gif?MOD=AJPERES)" onload="loadTime();">
<jsp:include page="topband.jsp"/>

<TABLE style="WIDTH: 90%; BACKGROUND-COLOR: #ffffff; height: 79%;" align="center" border='0'>
 <TBODY>
  <TR valign="top">
   <TD>


		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>&nbsp;</tr>
                        <tr valign="left">
                        <td class="moduleheadingh1" style="width: 150px;"><img src="images/spacer.gif" width="0" height="1">&nbsp;A<span style="margin: 0 0.07em 0 -0.07em">d<span style="margin: 0 0.07em 0 -0.15em">v<span style="margin: 0 0.07em 0 -0.07em">e<span style="margin: 0 0.07em 0 -0.07em">r<span style="margin: 0 0.07em 0 -0.05em">t<span style="margin: 0 0.07em 0 -0.10em">i<span style="margin: 0 0.07em 0 -0.05em">s<span style="margin: 0 0.07em 0 -0.05em">m<span style="margin: 0 0.07em 0 -0.05em">e<span style="margin: 0 0.07em 0 -0.05em">n<span style="margin: 0 0.07em 0 -0.05em">t</span></span></span></span></span></span></span></span></span>

                        </td>
                        </tr>

			
			<tr>
				<td width='20%' valign="top" style="padding-left: 10px;">&nbsp;<jsp:include page="sideMenu_admin.jsp"/></td>
				<TD width='63%' valign="top"  class="moduleheadingh1" style="padding-top: 30px;">
					<table style="BACKGROUND-COLOR:#F7F7F7;" cellpadding="0" cellspacing="1" border="0"  valign="center" width ="82%">
						<form name="form_expire" method="get">
                                                                </form>
						<FORM name="form_1" action="<%=response.encodeURL(request.getContextPath()+"/InsertData")%>"  method="get" onsubmit="return save();" >
						<tr style="BACKGROUND-IMAGE: url(res/images/blackberry_top_bg_new.jpg);BACKGROUND-REPEAT: no-repeat; height: 12px;">
							<td colspan="3">&nbsp;
							</td>
						</tr>
						<tr >
						<td width="10%">&nbsp;</td>
						<td align="center" ><b><label style="padding-left: 0px; " NAME="msg" ROWS="5" COLS="70" id='msg_board' class="arial11red" size ="9"></label></b>
						</td>
						<td>&nbsp;</td>
						</tr>
						<tr>
							<td>&nbsp;
							</td>
						</tr>
						<tr valign="center">
						<td>&nbsp;</td>

						<td  align="left" style="padding-left: 12px;"  class="arial11red" >Circle<img src="images/spacer.png" width="80" height="1" /><select  NAME="circle" id = "circle" Onchange="get_state()" class="contentblack" >
        					<%

                				ArrayList aList=(ArrayList)session.getAttribute("group_message_status");
						Iterator itr = aList.iterator();
						while(itr.hasNext())
        					{
         					String[] serviceData = (String[])itr.next();
         					for(int i=0;i<serviceData.length;i++)
                				{
                       				System.out.println(serviceData[i]);
                        			String str=serviceData[i];
                        			%><option value="<%=str%>" ><%=str%></option><%

                				}

        					}

						%>
						</select>

						</td>
						<td>&nbsp;</td>
						</tr>
                				<TR>
						<td>&nbsp;</td>
						<td style="padding-left: 12px;" align="left" class="arial11red"  >Campaign ID<img src="images/spacer.png" width="48" height="1" /><select NAME="campaginid" onchange="get_state()" class="contentblack" width="60" >
						<%


                				ArrayList aList1=(ArrayList)session.getAttribute("group_message_status1");
                				Iterator itr1 = aList1.iterator();
               					while(itr1.hasNext())
                				{
                				String[] servData = (String[])itr1.next();
                				for(int i=0;i<servData.length;i++)
                				{
                        			System.out.println(servData[i]);
                        			String str1=servData[i];
                        			%><option value="<%=str1%>" width="60" ><%=str1%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option><%

                				}

        					}

						%>
						</select>
						<img src="images/spacer.png" width="30" height="1" />Maximum Exposure<img src="images/spacer.png" width="20" height="1" /><INPUT TYPE="text" NAME="limit" class="contentblack" size="2" onkeyup="char_count_limit()">
						</TD>
						<td>&nbsp;</td>
        					</tr>
                				<TR>
						<td>&nbsp;</td>
					
						<td style="padding-left: 12px;" align="left" class="arial11red" >Campaign Name<img src="images/spacer.png" width="30" height="1" /><INPUT TYPE="text" NAME="campaginname" class="contentblack" ></TD>
						<td>&nbsp;</td>
        					</tr>          
		   				<TR>
						<td>&nbsp;</td>
                				<TD style="padding-left: 12px;" align="left" class="arial11red" >Status<img src="images/spacer.png" width="80" height="1" />Active <INPUT TYPE="radio" NAME="status" VALUE="active" id = "radio1" checked>
                                		Deactive <INPUT TYPE="radio" NAME="status" VALUE="deactive" id = "radio2"></TD>
						<td>&nbsp;</td>
        					</tr>
                				<TR>
						<td>&nbsp;</td>
						<td style="padding-left: 12px;" align="top" class="arial11red" > Message<img src="images/spacer.png" width="63" height="1" /><textarea name="message_box" rows="4" cols="50" onkeyup="char_count()" class="contentblack" ></textarea></TD>
						<td>&nbsp;</td>
						</tr>
						<tr>
						<td>&nbsp;</td>
						<td align="left" class="arial11red" ><img src="images/spacer.png" width="280" height="1" />Total Character : <font color="red" ><b><label id ="cnt_lbl" name="count_label" color ="red">0</label></b></font></td>
						<td>&nbsp;</td>
        					</tr>
                				<TR>
						<td>&nbsp;</td>
                				<TD align="center"><INPUT TYPE="submit" VALUE="Submit" name="do" align="center" ></TD>
						<td>&nbsp;</td>
        					</tr>
						
						<tr style="BACKGROUND-IMAGE: url(res/images/blackberry_bottom.gif);BACKGROUND-REPEAT: no-repeat; height: 16;BACKGROUND-COLOR: #F7F7F7;">
							<td colspan = "9"></td>
						</tr>
					</FORM>
					</table>
				</TD>
				<td width="40%">&nbsp;</td>
			</TR>
		</table>
   </TD>
  </TR>
 </TBODY>
</TABLE>
<jsp:include page="botamband.jsp"/>

</BODY>
</HTML>
</html>
<%
}
%>
