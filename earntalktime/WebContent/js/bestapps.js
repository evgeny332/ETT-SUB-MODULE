var ettId;

window.onload = function() {

	ettId = getUrlParameter("ettId");
	$.ajax({
		type : "POST",
		url : "api/bestapps/show",
		data : {
			"id" : "23"
		},
		success : function(result) {

			$('#head').html("");
			$('#head').html("<div class=\"heading\"><p>Trending Apps</p></div>" + result);
			
			$('[ontap]').each(function(){
				//alert("hello");
			    $(this).on("click touchstart", new Function($(this).attr("ontap")));
			});
		},
		error : function(jqXHR, textStatus, errorThrown) {

			// alert("error");
			// alert("error: " + textStatus + " (" + errorThrown + ")");
		},
	});
}

function click12(url,title){

	//alert("Hello");
	$.ajax({
		type : "POST",
		url : "api/bestapps/click",
		data : {
			"ettId" : ettId,
			"url" : url,
			"title" : title
		},
		success : function(result) {

			if(result === "true"){
				//alert(url);
				window.location.href = url;
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {

			// alert("error");
			// alert("error: " + textStatus + " (" + errorThrown + ")");
		},
	});
}

function getUrlParameter(sParam) {
	var sPageURL = decodeURIComponent(window.location.search.substring(1)), sURLVariables = sPageURL.split('&'), sParameterName, i;

	for (i = 0; i < sURLVariables.length; i++) {
		sParameterName = sURLVariables[i].split('=');

		if (sParameterName[0] === sParam) {
			return sParameterName[1] === undefined ? true : sParameterName[1];
		}
	}
};