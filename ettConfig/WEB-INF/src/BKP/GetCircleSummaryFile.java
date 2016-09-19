import com.dndbean.*;

import java.io.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class GetCircleSummaryFile 
{

        ArrayList CircleSummaryDataList = null;
        Connection theConnection = null;
        Statement theStatement   = null;
        ResultSet rs             = null;
        String query             = null;

        public ArrayList getCircleSummaryDataList( String fromDate, String circle ){

                System.out.println("[ GetCircleSummaryFile getCircleSummaryDataList(String,String) ]");
                System.out.println("fromDate"+fromDate+"toDate"+circle);
                if ( !( fromDate != null && circle != null ) || ( fromDate.equals("") || circle.equals("") ) ){
                        return CircleSummaryDataList; //It is null by default
                }
                int count = 0;
                try{
                        CircleSummaryDataList = new ArrayList();

                        //theConnection   = com.mcarbon.db.OracleConnectionUtil.getConnection();
			theConnection   = OracleConnectionUtil.getConnection();
                        theStatement    = theConnection.createStatement();
                        System.out.println("[ GetCircleSummaryFile getCircleSummaryDataList query Going to be execute ]"+query+"]");
               //         rs      = theStatement.executeQuery(query);
				CircleSummaryBean cdrb= null;
                        while(rs.next()){
                                cdrb = new CircleSummaryBean();
                                cdrb.setCircle(rs.getString(1));
                                cdrb.setDateval(rs.getString(2));
                                cdrb.setTotalcall(rs.getString(3));
                                cdrb.setCallallowed(rs.getString(4));
                                cdrb.setCallblocked(rs.getString(5));
                                CircleSummaryDataList.add(cdrb);
                        }
                }catch(Exception e){
                        System.out.println("[ GetCircleSummaryFile getCircleSummaryDataList ]"+e.getMessage());
                        e.printStackTrace();
                }finally{
                        if ( theConnection != null ){
                                try{
                                        if (rs != null )
                                                rs.close();
                                        if ( theStatement != null )
                                                theStatement.close();

                                        theConnection.close();
                                }catch(Exception e){
                                        System.out.println("[ GetCircleSummaryFile getCircleSummaryDataList ]"+e.getMessage());
                                }
                        }

                }
                System.out.println("[ GetCircleSummaryFile [ArrayList Length ] ["+CircleSummaryDataList.size()+"]");
                return CircleSummaryDataList;
        }
}
