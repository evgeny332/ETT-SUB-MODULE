
$(document).ready(function() {
	//alert("hi");
	myGetData("");
	$.ajax({
	    url:'/etx/v1/report/selectDate',
	    type:'GET',
	    data: 'q=',
	    dataType: 'json',
	    success: function( json ) {
	        $.each(json, function(i, value) {
	            $('#myselect').append($('<option>').text(value).attr('value', value));
	        });
	    }
	});
});

$('#myselect').on('change', function() {
	  //alert( this.value ); // or $(this).val()
		myGetData(this.value);
	});

function myGetData(date1){
	//alert(date1);
	var table = $('#example').DataTable({
		
		'destroy' : true,
		"ajax": {
	        'type': 'GET',
	        'url': '/etx/v1/report',
	        'data': {
	           'selectDate': date1,
	        },
	    },
		
	    'sAjaxDataProp' : "",
	    "oLanguage": {
	         "sInfo": ""
	       },
	    "lengthMenu" : [[10, 100, 3000, 5000, -1], [10, 100, 3000, 5000, "All"]],
	    iDisplayLength: -1,
		columns : [
		           { data : 'id' },
		           { data : 'ettId' }, 
		           { data : 'msisdn' }, 
		           { data : 'createdTime' }, 
		           { data : 'createdTimeIST' }, 
		          /* { data : 'msg' },*/ 
		           { data : 'respCode' }, 
		           { data : 'vendor' }, 
		           { data : 'status'}],
		           "dom": "<'row'<'col-md-6'l><'col-md-6'f>><'row'<'col-md-6'><'col-md-6'p>><'row'<'col-md-12't>><'row'<'col-md-12'iB>>",
		           buttons: [
		          
		           'copy', 'csv', 'excel', 'pdf', 'print'
		           ],
		          
	});
	
}