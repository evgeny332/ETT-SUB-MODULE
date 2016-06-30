var isValid = false;

function getFiles() {

	$.ajax({
		type : 'post',
		url : "api/file/list/",
		data : {
			"file" : "q"
		},
		success : function(data) {

			$('#hidtable table tbody').html('');
			$.each(data, function(key, value) {

				$('#hidtable table tbody').append(
						"<tr><td id='p1' ><a href='http://54.209.220.78:8888/images/"
								+ key + "' >" + key + "</a></td><td>" + value
								+ "</td></tr>");
			});
			$('#hidtable').show();

			// console.log(data);
			// $('#p1 a').miniPreview({ prefetch: 'none' });
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("error: " + textStatus + " (" + errorThrown + ")");
		},
	});
}

// $(function(){
// $('#p1 a').miniPreview({ prefetch: 'parenthover' });
// });

function getSize() {

	$("#uploads").html('');
	var len = $("#myFile")[0].files.length;
	var files = $("#myFile")[0].files;
	console.log(len);
	for (var i = 0; i < len; i++) {
		var file = files[i];
		var fileType = file.type;
		var ValidImageTypes = [ "image/gif", "image/jpeg", "image/png",
				"image/svg", "image/jpg", "image/tif" ];

		if ($.inArray(fileType, ValidImageTypes) > 0) {
			if (typeof file != "undefined") {
				var size = parseFloat(file.size / 1024).toFixed(2);

				if (size >= 50.0) {
					alert("size exceeding than required limit : " + size
							+ " kb");
				} else {
					isValid = true
					$("#uploads").append(
							"<li>" + file.name + "&nbsp;&nbsp;&nbsp;&nbsp;"
									+ size + "&nbsp;kb</li>");
				}

			} else {
				alert("This browser does not support HTML5.");
				isValid = false;
			}
		} else {
			alert("Invalid File Type");
			isValid = false;
		}
	}
}

function getTable() {

	if (isValid === true) {
		var fd = new FormData(document.querySelector("form"));

		$.ajax({
			type : 'post',
			url : "api/file/upload/",
			data : fd,
			processData : false,
			contentType : false,
			success : function(data) {

				$('#messages').append(data);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("error: " + textStatus + " (" + errorThrown + ")");
			},
		});
	} else {
		alert("Please a select file or a file of valid type");
	}
}