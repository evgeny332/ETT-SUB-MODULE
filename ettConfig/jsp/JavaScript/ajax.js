var httpObject = null;

function createHTTPObject(){
	if ( window.ActiveXObject ){
		return new ActiveXObject("Microsoft.XMLHTTP");
	}else if ( window.XMLHttpRequest ){
		return new XMLHttpRequest();
	}else{
		alert("Your browser does not support AJAX.");
		return null;
	}
}

function callAjax( textToTranslate, fromLang, toLang ){
	var urlToCall = "bkd_translate.jsp?inputText="+textToTranslate+"&fromLang="+fromLang+"&toLang="+toLang;
	httpObject = createHTTPObject();
	if ( httpObject != null ){
		httpObject.onreadystatechange = callBackFunction;
		httpObject.open("GET", urlToCall, true);
		httpObject.send(null);
	}
}
