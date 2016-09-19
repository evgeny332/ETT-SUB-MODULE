        var xreq;
function rightDirection()
{
        var selectbox = document.getElementById("Select1");
        var index=selectbox.selectedIndex;
        var elementToMove=selectbox.options[index].text;
        var elementToMove_val = selectbox.options[index].value;
        selectbox.remove(index);


        var selectbox2 = document.getElementById("Select2");
        var optn = document.createElement("OPTION");
        optn.text = elementToMove;
        optn.value = elementToMove_val;
        selectbox2.options.add(optn);
}


function leftDirection()
{
        var selectbox = document.getElementById("Select2");
        var index=selectbox.selectedIndex;
        var elementToMove=selectbox.options[index].text;
        var elementToMove_val = selectbox.options[index].value;
        alert("elementToMove"+elementToMove);
        selectbox.remove(index);

        var selectbox2 = document.getElementById("Select1");
        var optn = document.createElement("OPTION");
        optn.text = elementToMove;
         optn.value = elementToMove_val;
        selectbox2.options.add(optn);
}
function update_privilege()
{
        var user_privilege = "";
        var selectbox = document.getElementById("Select2");
        for(var i=selectbox.options.length-1;i>=0;i--)
        {

                var privilege=selectbox.options[i].text;
                var privilege_tabid = selectbox.options[i].value;
                alert(privilege);
                alert(privilege_tabid);
                user_privilege=user_privilege+privilege_tabid+"^";
        }
        user_privilege=user_privilege;
        alert("user_privilege"+user_privilege);
        var newuser=document.getElementById("newuser").value;
        alert("newuser"+newuser);
        var pwd=document.getElementById("pwd").value;
        alert("pwd"+pwd);
        var m_user=document.getElementById("m_user").value;
        alert("muser"+m_user);
        var total_tps=document.getElementById("tps").value;
        alert("totaltps"+total_tps);
        var assign_tps=document.getElementById("assign_tps").value;
        if (window.XMLHttpRequest)
        {

                try
                {
                        alert("str"+user_privilege);
                        xreq=new XMLHttpRequest();
                                xreq.onreadystatechange=function(){if(xreq.readyState==4)
                                                                  {
                                                                        if(xreq.status==200)
                                                                        {
                                                                                alert("Done");
                                                                        }
                                                                  }
                                                                }
                        xreq.open("GET","<%=request.getContextPath()%>/campaign_manager/ManageUserPrivilege?user_privilege="+user_privilege+"&newuser="+newuser+"&pwd="+pwd+"&m_user="+m_user+"&total_tps="+total_tps+"&assign_tps="+assign_tps,true);
                        xreq.send(null);

                }
                catch(e)
  		{
                        alert(e);
                }

        }

        else if (window.ActiveXObject)
        {
                xreq=new ActiveXObject("Microsoft.XMLHTTP");
                if(xreq)
                {

                        alert("inside IE");
                        alert("user_privilege   ["+user_privilege+"]");
                        xreq.onreadystatechange=function(){if(xreq.readyState==4)
                                                                  {
                                                                        if(xreq.status==200)
                                                                        {
                                                                                alert("Done");
                                                                        }
                                                                  }
                                                                }

                        xreq.open("GET","<%=request.getContextPath()%>/ManageUserPrivilege?user_privilege="+user_privilege+"&newuser="+newuser+"&pwd="+pwd+"&m_user="+m_user+"&total_tps="+total_tps+"&assign_tps="+assign_tps,true);

                        xreq.send(null);
                }
        }
        else
        {
                alert("Your browser does not support XMLHTTP!");
        }


}

