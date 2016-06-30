window.onload = function() {
	
	var ettId = getUrlParameter("ettId");
	$('#type').html(ettId);
	$.ajax({
		type : "POST",
		url : "api/challenge/ldraw",
		data : {"ettId" : ettId, "type":"one"},
		success : function(result,statusText, xhr) {
			
			var status = xhr.status;
			
			if (status === 200){
				var html = "";
				for(var i=0 ; i<result.length ; i++ ) {
					$('#points').html("Your have been allocated " + result[i].points + " ticket(s). Keep Downloading apps & invite friends to earn more and get more ticket(s).");
					$('.table-responsive').show();
				}
			}
			else {
				$('#points').html("You have not collected any ticket(s).<br> Download apps to add ticket(s), get ticket(s) for every <b>&#8377 10</b> earned <br> &#42 If you have recently earned money, come back in an hour to see your ticket(s)");
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			
			alert("error");
			alert("error: " + textStatus + " (" + errorThrown + ")");
		},
	});
}

function getCancel(){
	
	var ettId = $('#type').text();
	$.ajax({
		type : "POST",
		url : "api/challenge/ldraw",
		data : {"ettId" : ettId,"type": "two"},
		
		success : function(result,statusText, xhr) {
			
			var status = xhr.status;
			
			if (status === 200){
				var html = "";
				html = "<thead><tr><th colspan=\"3\">Your cancelled Ticket(s)</th></tr></thead><tbody ></tbody>";
				for(var i=0 ; i<result.length ; i++ ) {
					html = html + "<tr><td>" + result[i].points + "</td><td>"+result[i].appKey+"</td><td>"+result[i].remarks+"</td></tr>";
				}
				html = html + "</tbody>";
				$('#tbody').html(html);
			}
			else {
				$('#cancel').html("You have no cancelled ticket(s) yet");
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			
			alert("error");
			alert("error: " + textStatus + " (" + errorThrown + ")");
		},
	});
}

function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};
