var Open = "";
var Close = "";

function expand(sec)
{
	//alert(sec);
        //thisSec = eval('e'+sec);
        //if ( sec == 1 )
        //      div_blkListHome();
        //else if ( sec == 2 )
        //      div_selListHome();
        thisSec = eval("document.getElementById('e"+sec+"')");
        if (thisSec != null)
        {
                if (thisSec.length)
                {
                        if (thisSec[0].style.display != 'none')
                        {
                                for (var i=0;i<thisSec.length;i++)
                                {
                                        thisSec[i].style.display = 'none'
                                        Open = new Image(9,9);
                                        Close = new Image(9,9);
                                        document.getElementById('image'+sec).src = "images/plus.gif";
                                        document.getElementById('tr'+sec).bgColor="#F5F5F5";
                                        document.getElementById('mainTab').style.position="absolute";
                                        document.getElementById('mainTab').style.top="186px;";
                                }
                        }
                        else
                        {
                                for (var i=0;i<thisSec.length;i++)
                                {
                                        thisSec[i].style.display = 'inline'
                                        Open = new Image(9,9);
                                        Close = new Image(9,9);
                                        document.getElementById('image'+sec).src = "images/minus.gif";
                                        document.getElementById('tr'+sec).bgColor="#F5F5F5";
                                        document.getElementById('dt'+sec).bgColor="#FAFAFA";
                                        document.getElementById('mainTab').style.position="fixed";
                                        document.getElementById('mainTab').bgColor="#FAFAFA";
 }
                        }
                }
                else
                {
                        if (thisSec.style.display != 'none')
                        {
                                thisSec.style.display = 'none'
                                Open = new Image(9,9);
                                Close = new Image(9,9);
                                document.getElementById('image'+sec).src = "images/plus.gif";
                                document.getElementById('tr'+sec).bgColor="#F5F5F5";
                                document.getElementById('mainTab').style.position="relative";
                                var brow = navigator.userAgent.toLowerCase();
                                if ( brow.indexOf('firefox') != -1 )
                                        {  document.getElementById('mainTab').style.top="155px;";
                                        }
                        }
                        else
                        {
                                thisSec.style.display = 'inline'
                                Open = new Image(9,9);
                                Close = new Image(9,9);
                                document.getElementById('image'+sec).src = "images/minus.gif";
                                document.getElementById('tr'+sec).style.bgColor="#F5F5F5";
                                document.getElementById('dt'+sec).style.bgColor="#FAFAFA";
                                document.getElementById('mainTab').style.position="relative";
                                document.getElementById('mainTab').style.bgColor="#FAFAFA";
                        }
                }
        }
}

//function to get current page
function getLeaf(url)
{
        return url.substring(url.lastIndexOf("/")+1);
}

//function to highlight current menu item
function highlightCurrentMenuItem()
{ 	var currentLocation = getLeaf(document.location.href);
        var menu 	    = document.getElementById("mainTab");
        links		    = menu.getElementsByTagName("A");
        for (var i=0; i<links.length; i++)
        { var currentHref = links[i].getAttribute("href");
                var currentLeafName = getLeaf(currentHref);
                if (currentLeafName==currentLocation)
                { // Setting class is neded for Mozilla compatibility - className appears to be correct
                        // according to the DOM spec
                        links[i].setAttribute("class", "current");
                        links[i].setAttribute("className", "current");
                        // More obvious to use parentNode.parentNode.firstChild, but this
                        // may give a whitespace text node.

                        var menuHeader = links[i].parentNode.parentNode.getElementsByTagName("TD").item(0);
                        //alert("menuHeader " + links[i].parentNode.parentNode.parentNode.parentNode.id);
                        var id = links[i].id;

                        menuHeader.setAttribute("class", "current");
                        menuHeader.setAttribute("className", "current");
			expand(id);
                }
        }
}
