function Validate(action)
{
        var check = 0 ;
        for (var i=0; i < document.MOdifyWhiteList.memberid.length; i++)
        {
                if(document.MOdifyWhiteList.memberid[i].checked == true)
                {
                                check = check+1 ;
                                break;
                }
        }
        if(check == 0)
        {
                alert("Please select any record");
                return false ;
        }
        document.MOdifyWhiteList.Action.value= action ;
}

function cofirmDelete()
{
if(confirm ("Are You Sure Want To Delete This Entry !!"))
                {return true;}
                else
                {return false;}
}

function cofirmUpdate()
{
if(confirm ("Are You Sure Want To Update This Entry !!"))
                {return true;}
                else
  {return false;}
}
function markAll()
{
        for (var i=0;i<document.MOdifyWhiteList.memberid.length;i++)
        {
                document.MOdifyWhiteList.memberid[i].checked = true ;
        }
}
function unmarkAll()
{
        for (var i=0;i<document.MOdifyWhiteList.memberid.length;i++)
        {
                document.MOdifyWhiteList.memberid[i].checked =false ;
        }
}

