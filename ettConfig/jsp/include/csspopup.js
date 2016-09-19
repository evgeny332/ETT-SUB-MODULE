function toggle(div_id) {
	var el = document.getElementById(div_id);
	if ( el.style.display == 'none' ) {	el.style.display = 'block';}
	else {el.style.display = 'none';}
}
function blanket_size(popUpDivVar, blanketname) {
	if (typeof window.innerWidth != 'undefined') {
		viewportheight = window.innerHeight;
	} else {
		viewportheight = document.documentElement.clientHeight;
	}
	if ((viewportheight > document.body.parentNode.scrollHeight) && (viewportheight > document.body.parentNode.clientHeight)) {
		blanket_height = viewportheight;
	} else {
		if (document.body.parentNode.clientHeight > document.body.parentNode.scrollHeight) {
			blanket_height = document.body.parentNode.clientHeight;
		} else {
			blanket_height = document.body.parentNode.scrollHeight;
		}
	}
	//var blanket = document.getElementById('blanket');
	var blanket = document.getElementById(blanketname);
	blanket.style.height = blanket_height + 'px';
	var popUpDiv = document.getElementById(popUpDivVar);
	popUpDiv_height=blanket_height/2-70;//150 is half popup's height
	popUpDiv.style.top = popUpDiv_height + 'px';
}
function window_pos(popUpDivVar) {
	if (typeof window.innerWidth != 'undefined') {
		viewportwidth = window.innerHeight;
	} else {
		viewportwidth = document.documentElement.clientHeight;
	}

	if ((viewportwidth > document.body.parentNode.scrollWidth) && (viewportwidth > document.body.parentNode.clientWidth)) {
		window_width = viewportwidth;
	} else {
		if (document.body.parentNode.clientWidth > document.body.parentNode.scrollWidth) {
			window_width = document.body.parentNode.clientWidth;
		} else {
			window_width = document.body.parentNode.scrollWidth;
		}
	}
	var popUpDiv = document.getElementById(popUpDivVar);
	//window_width=window_width/2-340;//150 is half popup's width
	if ( ( /MSIE (\d+\.\d+);/.test(navigator.userAgent) )  || ( /Opera[\/\s](\d+\.\d+)/.test(navigator.userAgent) ) || ( /Chrome[\/\s](\d+\.\d+)/.test(navigator.userAgent) ) ){
		window_width=window_width/2-280;//150 is half popup's width
	}else{
		window_width=window_width/2-50;//150 is half popup's width
	}
	popUpDiv.style.left = window_width + 'px';
}
function popup(windowname, blanketname) {
	blanket_size(windowname, blanketname);
	window_pos(windowname);
	//toggle('blanket');
	toggle(blanketname);
	toggle(windowname);		
}

function toggleSim(div_id) {
	var el = document.getElementById(div_id);
	if ( el.style.display == 'none' ) {	el.style.display = 'block';}
	else {el.style.display = 'none';}
}
function blanket_sizeSim(popUpDivVar, blanketname) {
	if (typeof window.innerWidth != 'undefined') {
		viewportheight = window.innerHeight;
	} else {
		viewportheight = document.documentElement.clientHeight;
	}
	if ((viewportheight > document.body.parentNode.scrollHeight) && (viewportheight > document.body.parentNode.clientHeight)) {
		blanket_height = viewportheight;
	} else {
		if (document.body.parentNode.clientHeight > document.body.parentNode.scrollHeight) {
			blanket_height = document.body.parentNode.clientHeight;
		} else {
			blanket_height = document.body.parentNode.scrollHeight;
		}
	}
	//var blanket = document.getElementById('blanket');
	var blanket = document.getElementById(blanketname);
	blanket.style.height = blanket_height + 'px';
	var popUpDiv = document.getElementById(popUpDivVar);
	//popUpDiv_height=blanket_height/2-70;//150 is half popup's height
	popUpDiv_height=blanket_height/2-250;//150 is half popup's height
	popUpDiv.style.top = popUpDiv_height + 'px';
}
function window_posSim(popUpDivVar) {
	if (typeof window.innerWidth != 'undefined') {
		viewportwidth = window.innerHeight;
	} else {
		viewportwidth = document.documentElement.clientHeight;
	}
	if ((viewportwidth > document.body.parentNode.scrollWidth) && (viewportwidth > document.body.parentNode.clientWidth)) {
		window_width = viewportwidth;
	} else {
		if (document.body.parentNode.clientWidth > document.body.parentNode.scrollWidth) {
			window_width = document.body.parentNode.clientWidth;
		} else {
			window_width = document.body.parentNode.scrollWidth;
		}
	}
	var popUpDiv = document.getElementById(popUpDivVar);
	if ( ( /MSIE (\d+\.\d+);/.test(navigator.userAgent) )  || ( /Opera[\/\s](\d+\.\d+)/.test(navigator.userAgent) ) || ( /Chrome[\/\s](\d+\.\d+)/.test(navigator.userAgent) ) ){
		window_width=window_width/2-380;//150 is half popup's width
	}else{
		window_width=window_width/2-120;//150 is half popup's width
	}
	popUpDiv.style.left = window_width + 'px';
}
function popupSim(windowname, blanketname) {
	blanket_sizeSim(windowname, blanketname);
	window_posSim(windowname);
	toggleSim(blanketname);
	toggleSim(windowname);		
}
