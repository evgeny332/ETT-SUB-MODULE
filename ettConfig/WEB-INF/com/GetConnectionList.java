package com;
import java.util.*;
import java.sql.*;
public class GetConnectionList
{
        public String[] getConnList()
        {
                Connection activeConnection     = null;
                Statement st                    = null;
                ResultSet rs                    = null;
                String query                    = null;
                String conQuery                 = null;
                Vector vct                      = new Vector(1,1);
                String[] connList               = null;
                try{
                        activeConnection  = OracleConnectionUtil.getConnection();
                        st = activeConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                        query = "select distinct CONNECTION_NAME from CM_CONNECTION_DETAILS";
                        //System.out.println("website_oracle :: GetConnectionList.java :: query :: ["+query+"]");
                        rs = st.executeQuery(query);
                        int size = 0;
                        if(rs.next())
                        {
                                rs.last();
                                size=rs.getRow();
                                rs.beforeFirst();
                        }
                        //System.out.println("website_oracle :: GetConnectionList.java :: size :: ["+size+"]");
                        connList = new String[size];
                        for(int i=0;rs.next();i++)
                        {
                                connList[i] = rs.getString(1);
                        }
                }catch(Exception e){e.printStackTrace();}
                finally
                {
                        try{
                        activeConnection.close();
                        st.close();
                        rs.close();
                        }catch(Exception e){e.printStackTrace();}
		 }
                //System.out.println("website_oracle :: GetConnectionList.java :: connList.length :: ["+connList.length+"]");
                return connList;
        }
}

