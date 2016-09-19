<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.*, com.utility.*"%>
<%@taglib uri="/WEB-INF/displaytag-el.tld" prefix="display"%>
<%@ page import="java.sql.*,java.text.*"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="common/css/displaytagex.css">
</head>
<body>
<%-------------------------------- report data table pagination ----------------------------------%>
        <% request.setAttribute( "configDataList", new GetSmsDeliveryReport().getBalanceReportData(); %>
        <%--<display:table name="requestScope.configDataList" pagesize="5" class="dataTable"  id="currentRowObject">
        <display:column property="AParty" title="A Party"/>
        <display:column property="BParty" title="B Party"/>
        <display:column property="CParty" title="C Party" media="csv excel html pdf " sortable="true"/>
        <display:column property="startTime" title="Start Time" media="csv excel html pdf " sortable="true"/>
        <display:column property="endTime" title="End Time" media="csv excel html pdf " sortable="true"/>
        <display:column property="callCost" title="Call Cost" media="csv excel html pdf " sortable="true"/>
        <display:column property="callAnswerTime" title="Call Answer Time" media="csv excel html pdf " />
        <display:column property="duration" title="Duration" media="csv excel html pdf " sortable="true"/>
       <display:setProperty name="paging.banner.placement" value="top" />
        <display:setProperty name="paging.banner.placement" value="bottom" />
       <display:setProperty name="export.amount" value="list" />
        <display:setProperty name="export.pdf" value="true" />
        <display:setProperty name="export.pdf.filename" value="BalanceReport.pdf"/>
        <display:setProperty name="export.excel.filename" value="BalanceReport.xls"/>
        <display:setProperty name="export.csv.filename" value="BalanceReport.csv"/>
        <display:setProperty name="export.excel.include_header" value="true" />
        <display:setProperty name="paging.banner.group_size" value="8" />
        <display:setProperty name="paging.banner.item_name" value="" />
        <display:setProperty name="paging.banner.items_name" value="" />
        <display:setProperty name="paging.banner.some_items_found">  --%>
<%--
      <![CDATA[
--%>
<%--i
      ]]>
--%>
<%-- </display:setProperty>
    <display:setProperty name="paging.banner.full">
        <%--
      <![CDATA[
--%>
     <%--       <span class="pagelinks">
        [<a href="{1}">First</a>/<a href="{2}">Prev</a>]
 {0}
        [<a href="{3}">Next</a>/<a href="{4}">Last</a>]
<%--
      ]]>
--%>
      <%-- </display:setProperty> 
        </display:table> --%>

</body>
</html>
                 
