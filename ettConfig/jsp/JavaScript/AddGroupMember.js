function updateDescription()

{

        var IndexValue = document.AddGroupMember.groupname.selectedIndex;

        if(IndexValue == -1)

        {

                        alert("No Group is created.");

                        document.AddGroupMember.DataFile.disabled = true ;

                        document.AddGroupMember.submit.disabled =       true ;

        }

}

function Validate()

{

        var IndexValue = document.AddGroupMember.groupname.selectedIndex;

        var Filename = document.AddGroupMember.DataFile.value ;

        if(IndexValue == -1)

        {

                        alert("No Group is Available.");

                        return false;

        }

        if(Filename == "")

        {

                        alert("Select File First");

                        return false;

        }

}


