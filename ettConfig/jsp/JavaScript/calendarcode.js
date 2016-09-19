var childObj;
var formObject;
var isFunctionToBeCalled=false;
var functionName;
var windowobj;
var x;
var y;
var screenx;
var screeny;
var fieldDD=fieldDD;
var fieldMM=fieldMM;
var fieldYY=fieldYY;
var maxYearList;
var minYearList;
var IsUsingMinMax;
var todayDate = new Date;
var curDate = new Date;
var ppcIE=((navigator.appName == "Microsoft Internet Explorer") || ((navigator.appName == "Netscape") && (parseInt(navigator.appVersion)==5)));
var ppcNN6=((navigator.appName == "Netscape") && (parseInt(navigator.appVersion)==5));
//var ppcIE=(navigator.appName == "Microsoft Internet Explorer");
var ppcNN=((navigator.appName == "Netscape")&&(document.layers));
//minYearList=2000;
IsUsingMinMax = false;
//maxYearList=todayDate.getFullYear()+1;
minYearList=todayDate.getFullYear()-10;
maxYearList=todayDate.getFullYear()+10;
var names     = new makeArray0('January','February','March','April','May','June','July','August','September','October','November','December');
var days      = new makeArray0(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
var dow       = new makeArray0('Su','Mo','Tu','We','Th','Fr','Sa');
function makeArray0() {
    for (i = 0; i<makeArray0.arguments.length; i++)
        this[i] = makeArray0.arguments[i];
}
function isDayToday(isDay) {
    if ((curDate.getFullYear() == todayDate.getFullYear()) && (curDate.getMonth() == todayDate.getMonth()) && (isDay == todayDate.getDate())) {
        return true;
    }
    else {
        return false;
    }
}

function getDayLink(linkDay,isGreyDate,linkMonth,linkYear) {
    var templink;
    if (!(IsUsingMinMax)) {
        if (isGreyDate) {
            templink='<td align="center" class="cal-GreyDate">' + linkDay + '<\/td>';
        }
        else {
            if (isDayToday(linkDay)) {
                templink='<td align="center" class="cal-DayCell">' + '<a class="cal-TodayLink" onmouseover="self.status=\' \';return true" href="javascript:opener.changeDay(' + linkDay + ');">' + linkDay + '<\/a>' +'<\/td>';
            }
            else {
                templink='<td align="center" class="cal-DayCell">' + '<a class="cal-DayLink" onmouseover="self.status=\' \';return true" href="javascript:opener.changeDay(' + linkDay + ');">' + linkDay + '<\/a>' +'<\/td>';
            }
        }
    }
    else {
        if (isDayValid(linkDay,linkMonth,linkYear)) {

            if (isGreyDate){
                templink='<td align="center" class="cal-GreyDate">' + linkDay + '<\/td>';
            }
            else {
                if (isDayToday(linkDay)) {
                    templink='<td align="center" class="cal-DayCell">' + '<a class="cal-TodayLink" onmouseover="self.status=\' \';return true" href="javascript:opener.changeDay(' + linkDay + ');">' + linkDay + '<\/a>' +'<\/td>';
                }
                else {
                    templink='<td align="center" class="cal-DayCell">' + '<a class="cal-DayLink" onmouseover="self.status=\' \';return true" href="javascript:opener.changeDay(' + linkDay + ');">' + linkDay + '<\/a>' +'<\/td>';
                }
            }
        }
        else {
            templink='<td align="center" class="cal-GreyInvalidDate">'+ linkDay + '<\/td>';
        }
    }
    return templink;
}

function Calendar(whatMonth,whatYear) {
	names     = new makeArray0('January','February','March','April','May','June','July','August','September','October','November','December');
	days      = new makeArray0(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	dow       = new makeArray0('Su','Mo','Tu','We','Th','Fr','Sa');

    var output = '';
    var datecolwidth;
    var startMonth;
    var startYear;
    startMonth=whatMonth;
    startYear=whatYear;

    curDate.setMonth(whatMonth);
    curDate.setFullYear(whatYear);
    curDate.setDate(todayDate.getDate());
    if (ppcNN6) {
        output += '<form name="Cal"><table width="100" border="0" class="cal-Table" cellspacing="0" cellpadding="0"><tr>';
    }
    else {
        output += '<table width="100" border="0" class="cal-Table" cellspacing="0" cellpadding="0"><form name="Cal"><tr>';
    }

    output += '<td class="cal-HeadCell" align="center"  width="100%"><a href="javascript:opener.scrollMonth(-1,self);" class="cal-DayLink"><img src="./images/back.gif" align=absmiddle border=0></a>&nbsp;<SELECT class="cal-TextBox" NAME="cboMonth" onChange="opener.changeMonth(self);">';
    for (month=0; month<12; month++) {
        if (month == whatMonth) output += '<OPTION VALUE="' + month + '" SELECTED>' + names[month] + '<\/OPTION>';
        else                output += '<OPTION VALUE="' + month + '">'          + names[month] + '<\/OPTION>';
    }

    output += '<\/SELECT><SELECT class="cal-TextBox" NAME="cboYear" onChange="opener.changeYear(self);">';

    for (year=minYearList; year<maxYearList; year++) {
        if (year == whatYear) output += '<OPTION VALUE="' + year + '" SELECTED>' + year + '<\/OPTION>';
        else              output += '<OPTION VALUE="' + year + '">'          + year + '<\/OPTION>';
    }

    output += '<\/SELECT>&nbsp;<a href="javascript:opener.scrollMonth(1,self);" class="cal-DayLink"><img src="./images/front.gif" align=absmiddle border=0></a><\/td><\/tr><tr><td width="100%" align="center">';

    firstDay = new Date(whatYear,whatMonth,1);
    startDay = firstDay.getDay();

    if (((whatYear % 4 == 0) && (whatYear % 100 != 0)) || (whatYear % 400 == 0))
         days[1] = 29;
    else
         days[1] = 28;

    output += '<table width="185" cellspacing="1" cellpadding="2" border="0"><tr>';

    for (i=0; i<7; i++) {
        if (i==0 || i==6) {
            datecolwidth="15%"
        }
        else
        {
            datecolwidth="14%"
        }
        output += '<td class="cal-HeadCell" width="' + datecolwidth + '" align="center" valign="middle"><font color=#000080>'+ dow[i] +'<\/td>';
    }

    output += '<\/tr><tr>';

    var column = 0;
    var lastMonth = whatMonth - 1;
    var lastYear = whatYear;
    if (lastMonth == -1) { lastMonth = 11; lastYear=lastYear-1;}

    for (i=0; i<startDay; i++, column++) {
        output += getDayLink((days[lastMonth]-startDay+i+1),true,lastMonth,lastYear);
    }

    for (i=1; i<=days[whatMonth]; i++, column++) {
        output += getDayLink(i,false,whatMonth,whatYear);
        if (column == 6) {
            output += '<\/tr><tr>';
            column = -1;
        }
    }

    var nextMonth = whatMonth+1;
    var nextYear = whatYear;
    if (nextMonth==12) { nextMonth=0; nextYear=nextYear+1;}

    if (column > 0) {
        for (i=1; column<7; i++, column++) {
            output +=  getDayLink(i,true,nextMonth,nextYear);
        }
        output += '<\/tr><\/table><\/td><\/tr>';
    }
    else {
        output = output.substr(0,output.length-4); // remove the <tr> from the end if there's no last row
        output += '<\/table><\/td><\/tr>';
    }

    if (ppcNN6) {
        output += '<\/table><\/form>';
    }
    else {
        output += '<\/form><\/table>';
    }
    curDate.setDate(1);
    curDate.setMonth(startMonth);
    curDate.setFullYear(startYear);
	return output;
}

function changeMonth(obj) {
        curDate.setMonth(obj.document.Cal.cboMonth.options[obj.document.Cal.cboMonth.selectedIndex].value);
        showCal(obj,curDate.getMonth(),curDate.getFullYear());
}

function changeYear(obj) {
      curDate.setFullYear(obj.document.Cal.cboYear.options[obj.document.Cal.cboYear.selectedIndex].value);
      showCal(obj,curDate.getMonth(),curDate.getFullYear());
}

function scrollMonth(amount,obj) {
    var monthCheck;
    var yearCheck;
    monthCheck = obj.document.Cal.cboMonth.selectedIndex + amount;
    
    if (monthCheck < 0) {
        yearCheck = curDate.getFullYear() - 1;
        if ( yearCheck < minYearList ) {
            yearCheck = minYearList;
            monthCheck = 0;
        }
        else {
            monthCheck = 11;
        }
        curDate.setFullYear(yearCheck);
    }
    else if (monthCheck >11) {
        yearCheck = curDate.getFullYear() + 1;
        if ( yearCheck > maxYearList-1 ) {
            yearCheck = maxYearList-1;
            monthCheck = 11;
        }
        else {
            monthCheck = 0;
        }      
        curDate.setFullYear(yearCheck);
    }
    
    curDate.setMonth(obj.document.Cal.cboMonth.options[monthCheck].value);
    showCal(obj,curDate.getMonth(),curDate.getFullYear());
}
function setMultiTextBox(formName,fieldDD,fieldMM,fieldYY){
	//alert(arguments.length);
	if(arguments.length==4){
		isMultiTextBox=true;
		this.fieldDD=fieldDD;
		this.fieldMM=fieldMM;
		this.fieldYY=fieldYY;
	}else{
		isMultiTextBox=false;
		this.fieldDD=fieldDD;
	}
	this.formObject=formName;

}


function showCal(obj,whatMonth,whatYear){
	windowobj=obj;
	if(!whatMonth){
	   if(whatMonth!=0)
	   {
	     whatMonth = this.todayDate.getMonth();
	   }	

	}
	if(!whatYear){
		whatYear = this.todayDate.getFullYear();
	}
	var str = Calendar(whatMonth,whatYear);
	obj.document.open();
	obj.document.write("<html>");
	obj.document.write("<head>");
	obj.document.write("<title>Calendar");
	obj.document.write("</title>");
	obj.document.write("<LINK href='calendar.css' rel=stylesheet type='text/css'>");
	obj.document.write("</head>");
	obj.document.write("<body  style='margin-top: 0px; margin-left: 0px' leftmargin=0 marginwidth='0' marginheight='0'>");
	obj.document.write(str);
	obj.document.write("</body>");
	obj.document.write("</html>");
	obj.document.close();
	
}
function changeDay(whatDay) {
    curDate.setDate(whatDay);
    if(isMultiTextBox==true){
        eval("document."+formObject+"."+fieldDD+".value='"+(curDate.getDate()<10?"0"+curDate.getDate():curDate.getDate())+"'")
        eval("document."+formObject+"."+fieldMM+".value='"+((curDate.getMonth()+1)<10 ? "0"+(curDate.getMonth()+1):(curDate.getMonth()+1))+"'")
        eval("document."+formObject+"."+fieldYY+".value='"+curDate.getFullYear()+"'")
    }else{
    	eval("document."+formObject+"."+fieldDD+".value='"+curDate.getDate()+"/"+(curDate.getMonth()+1)+"/"+curDate.getFullYear()+"'")
    }
    hideCalendar();
    if(isFunctionToBeCalled==true){
    	eval(functionName+"()");
    }
 //   var dt = curDate.getDate();
 //   var mn = curDate.getMonth()+1;
 //   var yr = curDate.getFullYear();    
 //   document.dataGrid.date.value = dt;
 //   document.dataGrid.month.value = mn;
 //   document.dataGrid.year.value = yr;       	
    	
}
function hideCalendar(){
    IsCalendarVisible = false;
    windowobj.close();
}

function showCalendar(formName,fieldDD,fieldMM,fieldYY){
	if(arguments.length>=4){
		isMultiTextBox=true;
		this.fieldDD=fieldDD;
		this.fieldMM=fieldMM;
		this.fieldYY=fieldYY;
	}else{
		isMultiTextBox=false;
		this.fieldDD=fieldDD;
	}
	this.formObject=formName;
	//var windowprop="width=185,height=150,screenx="+x+",screeny="+y+",status=no,menubar=no,scrollbars=no,toolbar=no";
	var windowprop="width=185,height=150,"+this.screenx+"="+x+","+this.screeny+"="+y+",status=no,menubar=no,scrollbars=no,toolbar=no";
	//alert('windowprop = '+windowprop);
	var win = window.open("../jsp/calendar.html","calendar",windowprop);
	win.focus();

}

function show(obj){
	var today = new Date
	showCal(obj);
}
function setPosition(x,y){
	this.x=x;
	this.y=y;
	if(ppcNN6){
	  this.screenx="screenX";
	  this.screeny="screenY";
	  return;
	}
	
	if(ppcIE){
	  this.screenx="left";
	  this.screeny="top";
	  return;
	}
	
	if(ppcNN){
	  this.screenx="screenX";
	  this.screeny="screenY";
	  return;
	}
	
	
	
	
}
function calClick(){
        window.focus();
}

function setYearStart(start){
	minYearList=start;
	maxYearList=todayDate.getFullYear()+1;
	window.focus();
}
function setFunctionToBeCalled(boolVal){
	this.isFunctionToBeCalled=boolVal;
}
function setFunctionName(functionName){
	this.functionName=functionName;	
}
