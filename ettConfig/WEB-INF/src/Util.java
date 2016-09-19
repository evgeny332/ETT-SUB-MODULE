import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.*;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
public class Util
{
	
	public static int ExecuteQuery(String Query)
        {
                Statement  selectStmt           = null;
                Connection activeConnection     = null;
                int Result = 0;
                try
                {
                        activeConnection  = OracleConnectionUtil.getConnection();
                }
                catch(Exception od)
                {
                        System.out.println("[ETT REFUND] ["+GetDate()+"] In Util.java:   Not able to connect with MYSQL");
                        od.printStackTrace();
                }
                try
                {
                        selectStmt = activeConnection.createStatement();
                        Result = selectStmt.executeUpdate(Query);
                        selectStmt.close();
                        activeConnection.close();
                        System.out.println("[ETT REFUND ["+GetDate()+"] query["+Query+"] has been executed...!!!"+Result);
                }
                catch(SQLException ESQL)
                {
                        System.out.println("[ETT REFUND] ["+GetDate()+"] In Util.java:    SQL Error" + ESQL.getMessage());
                }
                finally
                {
                        try
                        {
                                selectStmt.close();

                                activeConnection.close();
                        }
                        catch(Exception ee)
                        {
                                System.out.println("[ETT REFUND] ["+GetDate()+"] In Util.java:    Exception: " +ee.getMessage());
                                ee.printStackTrace();
                        }
                }
                return Result;
        }
	public static String GetDate()
        {
                Calendar cal = Calendar.getInstance();
                String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
                return sdf.format(cal.getTime());
        }

}
