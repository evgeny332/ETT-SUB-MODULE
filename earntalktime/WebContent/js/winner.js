$(document).ready(function() {
	
	var type=$('#type').text();
	
//	alert(type);
	var minDate;
	if(type == "invite"){
		minDate="2015-12-26";
	}else {
		minDate="2015-12-12";
	}
	
	$("#sdate").datepicker({
		showOn : "button",
		buttonImage : "img/calendar-icon.png",
		buttonImageOnly : true,
		buttonText : "Select date",
		defaultDate : -7,
		changeMonth : true,
		// changeYear : true,
		minDate : ''+minDate+'',
		maxDate : 0,
		dateFormat : "yy-mm-dd",
		onSelect : function(date) {
			$.ajax({
				type : "POST",
				url : "Winner",
				data : {"date":date , "type":type},
				success : function(result) {
					
					$('#tbody').html("");
					for(var i=0; i< result["dto"].length; i++){
						
						$('#tbody').append("<tr><td>" + result["dto"][i] + "</tr></td>");
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert("error: " + textStatus + " (" + errorThrown + ")");
				},
			});
		}
	});
});