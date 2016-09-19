import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;
import java.util.Date;
import java.sql.*;

public  class Login extends HttpServlet
{

	public void init(ServletConfig config)  throws ServletException
	{
		super.init(config);
	}
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws  IOException
	{
		HttpSession Session=request.getSession(true);
		String Application_Username	= "";
		String Application_Password	= "";
		String LoginType		= "";
		Statement selectStmt		= null;
		Connection activeConnection 	= null;
		ResultSet DetailsResultSet 	= null;
		try
		{
			Application_Username=request.getParameter("User_Id");
			Application_Password=request.getParameter("Password");
			try
			{		
				activeConnection  = OracleConnectionUtil.getConnection();
			}
			catch(Exception od)
			{
				System.out.println("In Login.java:   Not able to connect with ORACLE");
				response.sendRedirect(response.encodeURL("jsp/Login.jsp?ST=E016"));
				od.printStackTrace();
			}
			System.out.println("In Login.java:    ORA Connection Created!");
			selectStmt = activeConnection.createStatement();
			//String SelectQuery="SELECT TOTAL_TPS , USER_PRIVILEGE , MASTER_USER , BRANDING , ACCOUNT_TYPE , ACC_USED_SMS , ACC_EXP_DATE , TOTAL_ACC_SMS  FROM CM_USER_LOGIN_DETAILS WHERE USERNAME ='" + Application_Username + "' AND  PASSWORD ='" + Application_Password + "'";
			String SelectQuery="SELECT TOTAL_TPS , USER_PRIVILEGE , MASTER_USER , BRANDING , ACCOUNT_TYPE , ACC_USED_SMS , ACC_EXP_DATE , TOTAL_ACC_SMS ,USER_CIRCLE,HUB_ID  FROM CM_USER_LOGIN_DETAILS WHERE USERNAME ='" + Application_Username + "' AND  PASSWORD ='" + Application_Password + "'";
			System.out.println("In Login.java:    Query to be run is "+SelectQuery);
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			try
			{
				if(DetailsResultSet.next())
				{
					ArrayList UserAuthList = new ArrayList (); 
					Session.setAttribute("userName", Application_Username);

					String Total_Tps = DetailsResultSet.getString("TOTAL_TPS"); 
					String User_Priviledge = DetailsResultSet.getString("USER_PRIVILEGE"); 
					String Master_User = DetailsResultSet.getString("MASTER_USER"); 
					String Branding = DetailsResultSet.getString("BRANDING");
					String AccountType = DetailsResultSet.getString("ACCOUNT_TYPE");
					String TotalLimit = DetailsResultSet.getString("TOTAL_ACC_SMS");
					String UsedLimit  = DetailsResultSet.getString("ACC_USED_SMS");
					String ExpireDate = DetailsResultSet.getString("ACC_EXP_DATE");
					String user_circle = DetailsResultSet.getString("USER_CIRCLE");
					String HUB_ID      = DetailsResultSet.getString("HUB_ID");
					//System.out.println("User_Priviledge=="+User_Priviledge);

					if(TotalLimit == null )
						TotalLimit = "0" ; 
					if(UsedLimit == null )
						UsedLimit = "0" ;

					if(ExpireDate == null)
						ExpireDate = GetCheckDate();

					String Availlimit = "" + ( Integer.parseInt(TotalLimit) - Integer.parseInt(UsedLimit));

					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date ExpiryDate = df.parse(ExpireDate);			
					Date TodayDate = df.parse(GetCheckDate());		
					boolean Status =   TodayDate.after(ExpiryDate);
					if(Status == true)
					{
						response.sendRedirect(response.encodeURL("jsp/Login.jsp?ST=E022"));
						return ; 
					}
					Session.setAttribute("Total_Tps", Total_Tps);
					Session.setAttribute("User_Priviledge", User_Priviledge);
					Session.setAttribute("Master_User", Master_User);
					Session.setAttribute("Branding", Branding);
					Session.setAttribute("AccountType", AccountType);
					Session.setAttribute("AvailSMSLimit", Availlimit);
					Session.setAttribute("user_circle",user_circle);
					Session.setAttribute("HUB_ID",HUB_ID);

					System.out.println("UserName ["+Application_Username+"] Priviledge ["+User_Priviledge+"] Masteruser["+Master_User+"] Branding["+Master_User+"] TPS ["+Total_Tps+"]");	
					String TabDetailsQuery = "SELECT GUI_TABID , GUI_TAB_NAME , GUI_TAB_JSP_PAGE , MORE_CHILD , PARENT_TAB FROM PRIVILEGE_GUI_MAPPING ORDER BY GUI_TABID" ;

					System.out.println(TabDetailsQuery); 
					Statement selectStmtTab = activeConnection.createStatement();
					selectStmtTab = activeConnection.createStatement();
					ResultSet TabsResultSet = selectStmtTab.executeQuery(TabDetailsQuery);
					ArrayList TabDataList = new ArrayList();
					while(TabsResultSet.next())
					{
						String TabData [] =  new String[5];
						TabData[0] = TabsResultSet.getString("GUI_TABID");
						TabData[1] = TabsResultSet.getString("GUI_TAB_NAME");
						TabData[2] = TabsResultSet.getString("GUI_TAB_JSP_PAGE");
						TabData[3] = TabsResultSet.getString("MORE_CHILD");
						TabData[4] = TabsResultSet.getString("PARENT_TAB");
						TabDataList.add(TabData);
					}
					TabsResultSet.close();
					selectStmtTab.close();
					String Tab_Privlige[] = User_Priviledge.split("#");
					System.out.println("Tab_Privlige.length ["+Tab_Privlige.length+"]");	
					for(int i = 0 ; i < Tab_Privlige.length ; i++ )
					{
						String TabAllInfo[] = Tab_Privlige[i].split(",");
						ArrayList FinalTabInfoList = new ArrayList ();
						for(int tabIndex= 0 ; tabIndex < TabAllInfo.length ; tabIndex++)
						{
							//System.out.println("tabIndex ["+tabIndex+"] TabAllInfo.length ["+TabAllInfo.length+"]");	
							String Privlige = TabAllInfo[tabIndex]; 
							for(int index = 0 ; index < TabDataList.size() ; index++)
							{
								String TabData [] = (String [])TabDataList.get(index);
								if(Privlige.equals(TabData[0]))
								{
									String TabInfo[] = new String[4];
									TabInfo[0] = TabData[1];
									TabInfo[1] = TabData[2];
									TabInfo[2] = TabData[3];
									TabInfo[3] = TabData[0];
									FinalTabInfoList.add(TabInfo);
									break ;
								}
							}
						}
						UserAuthList.add(FinalTabInfoList);
					}
					Session.setAttribute("UserAuthList", UserAuthList);

					DetailsResultSet.close();
					String SenderidQuery  = "SELECT SENDERID FROM CM_USER_SENDER_ID WHERE USERNAME ='"+Application_Username+"'" ;		     				      Statement selectStmtSenderId = activeConnection.createStatement();
					selectStmtSenderId = activeConnection.createStatement();
					ResultSet SenderidResultSet = selectStmtSenderId.executeQuery(SenderidQuery);
					ArrayList SenderId = new ArrayList ();
					while(SenderidResultSet.next())
					{
						SenderId.add(SenderidResultSet.getString("SENDERID"));
					}
					SenderidResultSet.close();
					selectStmtSenderId.close();
					Session.setAttribute("AvailSenderId", SenderId);
					String TemplateQuery  = "SELECT TEMPLATEID , TEMPLATEVALUE FROM CM_USER_MESSAGE_TEMPLATE WHERE USERNAME ='"+Application_Username+"'" ;
					Statement selectStmtTemplate = activeConnection.createStatement();
					selectStmtTemplate = activeConnection.createStatement();
					ResultSet TemplateResultSet = selectStmtTemplate.executeQuery(TemplateQuery);
					ArrayList TemplateList = new ArrayList ();
					while(TemplateResultSet.next())
					{
						String Template[] = new String [2];
						Template[0] = TemplateResultSet.getString("TEMPLATEID") ; 
						Template[1] = TemplateResultSet.getString("TEMPLATEVALUE");
						System.out.println("USER ["+Application_Username+"] Template Id ["+Template[0]+"] Template Value ["+Template[1]+"]");
						TemplateList.add(Template); 
					}
					TemplateResultSet.close();
					selectStmtTemplate.close();
					Session.setAttribute("TemplateList", TemplateList);
					String GroupQuery  = "SELECT GROUPNAME , DESCRIPTION ,TOTALMEMBER , CREATION_DATE FROM CM_GROUPS_DETAIL WHERE USERNAME ='"+Application_Username+"'" ;
					System.out.println(GroupQuery);
					Statement selectStmtGroup = activeConnection.createStatement();
					selectStmtGroup = activeConnection.createStatement();
					ResultSet GroupResultSet = selectStmtGroup.executeQuery(GroupQuery);
					ArrayList GroupList = new ArrayList ();
					while(GroupResultSet.next())
					{
						String Group[] = new String [4];
						Group[0] = GroupResultSet.getString("GROUPNAME") ; 
						Group[1] = GroupResultSet.getString("TOTALMEMBER");
						Group[2] = GroupResultSet.getString("CREATION_DATE");
						Group[3] = GroupResultSet.getString("DESCRIPTION");
						System.out.println("USER ["+Application_Username+"] Group Id ["+Group[0]+"] Total Member ["+Group[1]+"] Date Of Creation ["+Group[2]+"]");
						GroupList.add(Group); 
					}
					GroupResultSet.close();
					selectStmtGroup.close();
					Session.setAttribute("GroupList", GroupList);
					ArrayList GroupDetailList = new ArrayList();
					Session.setAttribute("GroupData", GroupDetailList);

					String BrandQuery = "SELECT BRAND_NAME , BRAND_PAGE FROM CM_BRANDING_DETAILS";
					System.out.println(BrandQuery);
					Statement selectStmtBrand = activeConnection.createStatement();
					selectStmtBrand = activeConnection.createStatement();
					ResultSet BrandResultSet = selectStmtBrand.executeQuery(BrandQuery);
					ArrayList AvailBrandList = new ArrayList ();
					while(BrandResultSet.next())
					{
						String Group[] = new String [2];
						Group[0] = BrandResultSet.getString("BRAND_NAME") ;
						Group[1] = BrandResultSet.getString("BRAND_PAGE");
						System.out.println("USER ["+Application_Username+"] BRAND_NAME ["+Group[0]+"] BRAND_PAGE ["+Group[1]+"]");
						AvailBrandList.add(Group);
					}
					BrandResultSet.close();
					selectStmtBrand.close();
					Session.setAttribute("AvailBrandList", AvailBrandList);


					String SelfIdUser = "";
					String QuerySelfId = "SELECT PARENT_CHILD_ID FROM CM_USER_LOGIN_DETAILS WHERE USERNAME ='"+Application_Username+"'";
					ResultSet RsSelfId = selectStmt.executeQuery(QuerySelfId);
					if(RsSelfId.next())
						SelfIdUser = RsSelfId.getString("PARENT_CHILD_ID");
					RsSelfId.close();
			
					//************************************************************************************************************************//
					//************************************************************************************************************************//
					String QueryChildUser = "SELECT USERNAME , PARENT_CHILD_ID FROM CM_USER_LOGIN_DETAILS WHERE PARENT_CHILD_ID LIKE '"+SelfIdUser+"%' ORDER BY PARENT_CHILD_ID";

					ResultSet RsChildUser = selectStmt.executeQuery(QueryChildUser);
					ArrayList ChildUserList = new ArrayList();
					ArrayList  UserChildMapping  = new ArrayList();
					HashMap  user_de = new HashMap();
					while(RsChildUser.next())
					{
						String Info [] = new String[2];
						Info[0] = RsChildUser.getString("USERNAME");
						Info[1] = RsChildUser.getString("PARENT_CHILD_ID");
						System.out.println("USERNAME ["+Info[0]+"] PARENT_CHILD_ID ["+Info[1]+"]");
						UserChildMapping.add(Info[0]);
					}
					RsChildUser.close();
					//Array List to set the subuser of the logined enterprise user
                                        String SubUserQuery  = "SELECT USERNAME FROM CM_USER_LOGIN_DETAILS WHERE MASTER_USER ='"+Application_Username+"'" ;
                                         Statement selectStmtSubUser= activeConnection.createStatement();
//                                        selectStmtSenderId = activeConnection.createStatement();
                                        selectStmtSubUser= activeConnection.createStatement();
                                        ResultSet SubUserResultSet = selectStmtSubUser.executeQuery(SubUserQuery);
                                        ArrayList UniqSubUser = new ArrayList ();
                                        while(SubUserResultSet.next())
                                        {
                                                UniqSubUser.add(SubUserResultSet.getString("USERNAME"));
                                        }
                                        SubUserResultSet.close();
                                        selectStmtSubUser.close();
                                        Session.setAttribute("UniqSubUser", UniqSubUser);

                                        //Changes end by navjeet for the Array List to set the subuser of the logined enterprise user
					//Array list to get whitelist no of any user
					String GetWhiteList     = "select  SUBSCRIBER from CM_WHITE_LIST_DETAILS where USER_NAME='"+Application_Username+"'";
					Statement selectWhiteList= activeConnection.createStatement();
					selectWhiteList=activeConnection.createStatement();
					ResultSet WhiteList = selectWhiteList.executeQuery(GetWhiteList);
					ArrayList Wlno = new ArrayList ();
					System.out.println("GetWhiteList"+GetWhiteList);
                                        while(WhiteList.next())
                                        {
                                                Wlno.add(WhiteList.getString("SUBSCRIBER"));
						
                                        }
                                        WhiteList.close();
                                        selectWhiteList.close();
                                        Session.setAttribute("Wlno", Wlno);
					
					//work onesuccessfuly by jeet

					Statement st1 = activeConnection.createStatement();
					ResultSet RS1 = st1.executeQuery("select USERNAME, MASTER_USER from CM_USER_LOGIN_DETAILS");
					System.out.println("QUERY");
					while(RS1.next())
					{
						String un = RS1.getString("USERNAME");
						if(un == null)
							un ="";
						String ms = RS1.getString("MASTER_USER");
						if(ms == null)
							ms = "";
						System.out.println("un" + un);
						System.out.println("ms" + ms);
						user_de.put(un,ms);
					}
					System.out.println("outside of loop");


					for(int Index = 0 ; Index < UserChildMapping.size() ; Index++)
					{
						String usr = (String)UserChildMapping.get(Index);
						String final_str = "" ;
						while(true)
						{
							usr = (String )user_de.get(usr);
							System.out.println("Users [ " + usr + " ]");
							if(usr == null)
								break;
							final_str = usr + "->" + final_str;
						}
						final_str = final_str + UserChildMapping.get(Index);
						System.out.println("final_str [" +final_str + "]");
						String FinalString = final_str ;
						if( FinalString.indexOf("->") != -1 && final_str.indexOf(Application_Username) != -1)
						{
							FinalString = final_str.substring(final_str.indexOf(Application_Username));
							ChildUserList.add(FinalString);
						}
						System.out.println("final_str [" + final_str + "] ["+FinalString+"]");
					}
					Session.setAttribute("ChildUserListGlobal", ChildUserList);
					RS1.close();
					st1.close();
					System.out.println("final_str [ ] []");
					selectStmt.close();


					activeConnection.close();
					response.sendRedirect(response.encodeURL("jsp/home.jsp"));
				}
				else
				{
					response.sendRedirect(response.encodeURL("jsp/Login.jsp?ST=E017"));
				}
			}
			catch(SQLException ESQL)
			{
				ESQL.printStackTrace();
				System.out.println("In Login.java:    SQL Error" + ESQL.getMessage());
			}
			finally
			{
				try
				{
					activeConnection.close();
				}
				catch(Exception ee)
				{
					System.out.println("In Login.java:    Exception: " +ee.getMessage());
					ee.printStackTrace();
				}
			}
		}
		catch(Exception Eb)
		{
			Eb.printStackTrace();
			System.out.println("In Login.java:    SQLState:  " + Eb.getMessage() );
			try
			{
				activeConnection.close();
			}
			catch(Exception ee)
			{
				System.out.println("In Login.java:    "+ ee.getMessage());
				ee.printStackTrace();
			}
			response.sendRedirect(response.encodeURL("jsp/Login.jsp?ST=null"));
		}
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
	{
		doGet(request, response);
	}
	public String GetCheckDate()
	{
		String DATE_FORMAT = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar c1 = Calendar.getInstance();
		return sdf.format(c1.getTime());

	}


}


