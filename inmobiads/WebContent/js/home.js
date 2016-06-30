function Send() {
		$.ajax({
			type : 'get',
			url : "InMobi",
			success : function(data) {

				$('#AdName').text(data.AdName);
				$('#image').attr("src",data.AdIcon.url);
				$('#description').text(data.Description);
				$('#rating').text("Rating: "+data.rating);
				$('#ActionUrl').attr("href",data.Url);
				$('#ActionUrl').text(data.cta);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("error");
			},
		});
	}