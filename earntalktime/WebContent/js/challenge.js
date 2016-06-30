window.onload = function() {
	
	var param = getUrlParameter("type");
	var ettId = getUrlParameter("ettId");
			$.ajax({
				type : "POST",
				url : "api/challenge/",
				data : {"type" : param},
				
				success : function(result) {
					
					$('#accordion').html("");
					
					var html = "";
					for(var i=0 ; i<result.length ; i++ ){
						var url = result[i].url.replace("#ETTID#",ettId);
						if(result[i].category === "two"){
							html = html + "<div class=\"col\"><div class=\"click\"><div class=\"icon\"><img src=\""+result[i].imageUrl+"\"></div>"
							+"<div class=\"text\"><span class=\"offerName\"><p>"+result[i].offerName+"</p></span><span><p>"+result[i].offerCategory+"</p></span><span><p>"+result[i].description+"</p></span><span id=\"points\"></span>"
							+"<span class=\"clickOn\" id=\"points2\">Know more</span></div></div><div class=\"detailed\"><p>"+result[i].detailedInstructions+"</p><p style=\"text-align: center;\">"+url+"</p></div></div>";	
						}else if(result[i].category === "one"){
							html = html + "<div class=\"col\"><div class=\"click\"><div class=\"icon\"><img src=\""+result[i].imageUrl+"\"></div>"
							+"<div class=\"text\"><span class=\"offerName\"><p>"+result[i].offerName+"</p></span><span><p>"+result[i].offerCategory+"</p></span><span><p>"+result[i].description+"</p></span>"
							+"<span class=\"clickOn\">Know more</span></div></div><div class=\"detailed\"><p>"+result[i].detailedInstructions+"</p><p style=\"text-align: center;\">"+url+"</p></div></div>";
						}else{
							html = html + "<div class=\"col\"><div class=\"click\"><div class=\"icon\"><img src=\""+result[i].imageUrl+"\"></div>"
                                                        +"<div class=\"text\"><span class=\"offerName\"><p>"+result[i].offerName+"</p></span><span><p>"+result[i].offerCategory+"</p></span><span><p>"+result[i].description+"</p></span>"
                                                        +"<span class=\"clickOn\">Know more</span></div></div><div class=\"detailed\"><p>"+result[i].detailedInstructions+"</p></div></div>";
						}
					}
					
					$('#accordion').append(html);
					
					getPoints();
					
					$(".col").click(function() {
						
						$(this).children(".detailed").slideToggle();
						$(this).children(".click").children(".text").children(".clickOn").text($(this).children(".click").children(".text").children(".clickOn").text() == 'Tap to close' ? 'Know more' : 'Tap to close');
					    $(".detailed").not($(this).children(".detailed")).slideUp();
					    $(".clickOn").not($(this).children(".click").children(".text").children(".clickOn")).html("Know more");
					});
					
					if(param != undefined && param != ""){
						$('.col:nth-child('+param+')').click();
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					
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

function getPoints(){
	
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
					$('#points').html("You have " + result[i].points + " ticket(s),&nbsp;");
//					$('.points').show();
				}
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			
			alert("error");
			alert("error: " + textStatus + " (" + errorThrown + ")");
		},
	});
}
