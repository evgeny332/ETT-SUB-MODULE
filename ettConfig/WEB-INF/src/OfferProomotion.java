import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OfferProomotion extends HttpServlet
{
	public static ArrayList ETTID = null;
	public static ArrayList ZeroETTID = null;
	public static ArrayList AllETTID = null;
	public static ArrayList REDEEM_FAIL = null;
        public static HashMap OFFER = null;
	public static final String USER_AGENT = "Mozilla/5.0";
	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException
        {
		doGet(request,response);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, java.io.IOException
	{
		HttpSession Session=request.getSession(true);
		String application_username     = null;
		ArrayList FileStatusList= new ArrayList ();
		String OfferName	= null;
		String msg		= null;
		ETTID 			= new ArrayList ();
		ZeroETTID		= new ArrayList ();
		AllETTID		= new ArrayList ();
		REDEEM_FAIL		= new ArrayList ();
		OFFER 			= new HashMap();
		int  flag			= 0;	
		try
		{
			OfferName 	=	request.getParameter("offer_name");
			msg		=	request.getParameter("msg");
			System.out.println("Offer Name : ["+OfferName+"], Text Message : ["+msg+"]");
			if(OfferName == null || OfferName.equals(""))
			{
				flag=-1;
				
			}
			if(msg == null || msg.equals(""))
			{
				flag=-1;
				getOfferName(FileStatusList);
			}
			if(flag == 0)
			{
				if(OfferName.equals("All"))
				{
					getETT_ID(AllETTID,OfferName);
					sendMSG(msg,OfferName);
                                        AllETTID.clear();
				}
				else if(OfferName.equals("one"))
				{
					getETT_ID(ETTID,OfferName);
					sendMSG(msg,OfferName);
                                        ETTID.clear();
				}
				else if(OfferName.equals("zero"))
                                {
					getETT_ID(ZeroETTID,OfferName);
					sendMSG(msg,OfferName);
                                        ZeroETTID.clear();
                                }
				else if(OfferName.equals("Airtel") || OfferName.equals("Airtel") || OfferName.equals("Loop Mobile") || OfferName.equals("Tata Docomo") || OfferName.equals("Vodafone") || OfferName.equals("MTS") || OfferName.equals("Uninor") || OfferName.equals("Aircel") || OfferName.equals("Idea") || OfferName.equals("Reliance") || OfferName.equals("BSNL"))
				{
					getETT_ID_REDEEM(REDEEM_FAIL,OfferName);
					sendMSG(msg,OfferName);
                                        REDEEM_FAIL.clear();
				}
				else
				{
					getETT_ID(ETTID,OfferName);
                                	getOFFER(OfferName,OFFER);
					sendMSG(msg,OfferName);
					OFFER.clear();
					ETTID.clear();
				}
			}
			Session.setAttribute("FileStatusList", FileStatusList);
			response.sendRedirect(response.encodeURL("jsp/OfferProomotion.jsp"));
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("["+GetDate()+"]"+"In OfferProomotion.java:    SQLState:  " + Eb.getMessage() );
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
		}
	}
	public static void getETT_ID_REDEEM(ArrayList REDEEM_FAIL,String OfferName)
        {
                String SelectQuery              = "";
                String SelectQuery1             = "";
                Statement  selectStmt           = null;
                Connection activeConnection     = null;
                ResultSet DetailsResultSet      = null;
                
                SelectQuery="SELECT DISTINCT ettId FROM UserRedeem where operator='"+OfferName+"' and status='FAILED'";
                
                try
                {
                        activeConnection  = OracleConnectionUtil.getConnection();
                }
                catch(Exception od)
                {
                        System.out.println("[ETT] []In OfferPromo.java:   Not able to connect with MYSQL");
                        od.printStackTrace();
                }
                System.out.println("[ETT] []In OfferPromo.java:    MYSQL Connection Created!");
                try
                {
                        selectStmt = activeConnection.createStatement();
                        DetailsResultSet = selectStmt.executeQuery(SelectQuery);
                        while(DetailsResultSet.next())
                        {

                               REDEEM_FAIL.add(DetailsResultSet.getString(1));
                               System.out.println("REDEEM_FAIL ETTID="+DetailsResultSet.getString(1));
                        }
						DetailsResultSet.close();
                        selectStmt.close();
                        activeConnection.close();
                        System.out.println("query has been executed...!!!");
                }
                catch(SQLException ESQL)
                {
                        System.out.println("[ETT] [] In OfferPromo.java:    SQL Error" + ESQL.getMessage());
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
                                System.out.println("[ETT] []In OfferPromo.java:    Exception: " +ee.getMessage());
                                ee.printStackTrace();
                        }
                }

        }

	public static void getOfferName(ArrayList FileStatusList)
        {
                String SelectQuery              = "";
                Statement  selectStmt           = null;
                Connection activeConnection     = null;
                ResultSet DetailsResultSet      = null;
                SelectQuery="select appKey from OfferDetails";
                try
                {
                        activeConnection  = OracleConnectionUtil.getConnection();
                }
                catch(Exception od)
                {
                        System.out.println("[ETT] []In OfferPromo.java:   Not able to connect with MYSQL");
                        od.printStackTrace();
                }
                System.out.println("[ETT] []In OfferPromo.java:    MYSQL Connection Created!");
                try
                {
                        selectStmt = activeConnection.createStatement();
                        DetailsResultSet = selectStmt.executeQuery(SelectQuery);
                        while(DetailsResultSet.next())
                        {

                                FileStatusList.add(DetailsResultSet.getString(1));
                                System.out.println("Offer Name ="+DetailsResultSet.getString(1));
                        }

                        DetailsResultSet.close();
                        selectStmt.close();
                        activeConnection.close();
                        System.out.println("query has been executed...!!!");
                }
                catch(SQLException ESQL)
                {
                        System.out.println("[ETT] [] In OfferPromo.java:    SQL Error" + ESQL.getMessage());
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
                                System.out.println("[ETT] []In OfferPromo.java:    Exception: " +ee.getMessage());
                                ee.printStackTrace();
                        }
                }

        }

	public static void getETT_ID(ArrayList ETT_ID,String OfferName)
        {
                String SelectQuery              = "";
                String SelectQuery1             = "";
                Statement  selectStmt           = null;
                Connection activeConnection     = null;
                ResultSet DetailsResultSet      = null;
		if(OfferName.equals("All"))
		{
			SelectQuery="select ettId from User";
		}
		else if(OfferName.equals("one"))
		{
			SelectQuery="select ettId from User where isVerified=1";
		}
		else if(OfferName.equals("zero"))
		{
			SelectQuery="select ettId from User where isVerified=0";
		}
		else
		{
			SelectQuery="select ettId from User where isVerified=1";
		}
                try
                {
                        activeConnection  = OracleConnectionUtil.getConnection();
                }
                catch(Exception od)
                {
                        System.out.println("[ETT] []In OfferPromo.java:   Not able to connect with MYSQL");
                        od.printStackTrace();
                }
                System.out.println("[ETT] []In OfferPromo.java:    MYSQL Connection Created!");
                try
                {
                        selectStmt = activeConnection.createStatement();
                        DetailsResultSet = selectStmt.executeQuery(SelectQuery);
                        while(DetailsResultSet.next())
                        {

                               ETT_ID.add(DetailsResultSet.getString(1));
                                System.out.println("Data ETTID="+DetailsResultSet.getString(1));
                        }

                        DetailsResultSet.close();
                        selectStmt.close();
                        activeConnection.close();
                        System.out.println("query has been executed...!!!");
                }
                catch(SQLException ESQL)
                {
                        System.out.println("[ETT] [] In OfferPromo.java:    SQL Error" + ESQL.getMessage());
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
                                System.out.println("[ETT] []In OfferPromo.java:    Exception: " +ee.getMessage());
                                ee.printStackTrace();
                        }
                }

        }
        public static void getOFFER(String offerName,HashMap appKey)
        {
                String SelectQuery              = "";

                Statement  selectStmt           = null;
                Connection activeConnection     = null;
                ResultSet DetailsResultSet      = null;

                SelectQuery="select ettId,appKey from InstalledApps where appKey='"+offerName+"'";
                try
                {
                        activeConnection  = OracleConnectionUtil.getConnection();
                }
                catch(Exception od)
                {
                        System.out.println("[ETT] []In OfferPromo.java:   Not able to connect with MYSQL");
                        od.printStackTrace();
                }
                System.out.println("[ETT] []In OfferPromo.java:    MYSQL Connection Created!");
                try
                {
                        selectStmt = activeConnection.createStatement();
                        DetailsResultSet = selectStmt.executeQuery(SelectQuery);
                        while(DetailsResultSet.next())
                        {


                                appKey.put(DetailsResultSet.getString(1),DetailsResultSet.getString(2));
                                System.out.println("Data ETTID Offer="+DetailsResultSet.getString(1)+" OfferNme="+DetailsResultSet.getString(2));
                        }

                        DetailsResultSet.close();
                        selectStmt.close();

                        activeConnection.close();
                        System.out.println("query has been executed...!!!");
                }
                catch(SQLException ESQL)
                {
                        System.out.println("[ETT] [] In OfferPromo.java:    SQL Error" + ESQL.getMessage());
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
                                System.out.println("[ETT] []In OfferPromo.java:    Exception: " +ee.getMessage());
                                ee.printStackTrace();
                        }
                }

        }


        public static void sendMSG(String msg,String offer_flag)
        {
                String offer    = null;
                try
                {
			if(offer_flag.equals("All"))
			{
				for(int i=0; i<AllETTID.size(); i++)
                                {
					String ETT=(String) AllETTID.get(i);
					sendPost(ETT,msg);
				}
			}
			else if(offer_flag.equals("one"))
			{
				for(int i=0; i<ETTID.size(); i++)
                                {
                                        String ETT=(String) ETTID.get(i);
                                        sendPost(ETT,msg);
                                }

			}
			else if(offer_flag.equals("zero"))
			{
				for(int i=0; i<ZeroETTID.size(); i++)
                                {
                                        String ETT=(String) ZeroETTID.get(i);
                                        sendPost(ETT,msg);
                                }

			}
			else if(offer_flag.equals("Airtel") || offer_flag.equals("Airtel") || offer_flag.equals("Loop Mobile") || offer_flag.equals("Tata Docomo") || offer_flag.equals("Vodafone") || offer_flag.equals("MTS") || offer_flag.equals("Uninor") || offer_flag.equals("Aircel") || offer_flag.equals("Idea") || offer_flag.equals("Reliance") || offer_flag.equals("BSNL"))
			{
				for(int i=0; i<REDEEM_FAIL.size(); i++)
                                {
                                        String ETT=(String) REDEEM_FAIL.get(i);
                                        sendPost(ETT,msg);
                                }

			}
			else
			{
                		for(int i=0; i<ETTID.size(); i++)
                		{
                        		String ETT=(String) ETTID.get(i);
                        		offer =(String) OFFER.get(ETT);
                        		if(offer == null )
                        		{
                                		sendPost(ETT,msg);
                                		System.out.println("SUCCESS ETTID="+ETT+" msg="+msg+"offer : "+offer);
                        		}
                        		else
                        		{
                                		System.out.println("FATLS ETTID="+ETT+" msg="+msg);
                        		}
                		}
			}
                }
                catch(Exception ee)
                {
                        System.out.println("[ETT] []In OfferPromo.java:    Exception: iwhen send msg " +ee.getMessage());
                }

        }
        public static void sendPost(String ETTID,String Msg) throws Exception {

                String url = "http://www.earntalktime.com/ett/api/v1/user/push/?";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

                String urlParameters = "ettIds="+ETTID+"&text="+Msg;

                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                }
                in.close();

                System.out.println(response.toString());

        }
	public String GetDate()
	{
		Calendar cal = Calendar.getInstance();
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

}

