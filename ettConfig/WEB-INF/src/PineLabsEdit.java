import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PineLabsEdit extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		HttpSession Session = request.getSession(true);
		String application_username = null;
		ArrayList FileStatusList = new ArrayList();
		String id = "";
		try {
			id = request.getParameter("Brandid");
			System.out.println("PineLabsEdit id for edit new  offer : " + id);
			GetResultData(FileStatusList, id);
			Session.setAttribute("FileStatusList", FileStatusList);
			response.sendRedirect(response.encodeURL("jsp/PineLabsEdit.jsp"));
		} catch (Exception Eb) {
			Eb.printStackTrace();
			System.out.println("[" + GetDate() + "]" + "In PineLabsEdit.java:    SQLState:  " + Eb.getMessage());
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
		}
	}

	public String GetDate() {
		Calendar cal = Calendar.getInstance();
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

	public void GetResultData(ArrayList FileStatusList, String id) {
		String SelectQuery = "";
		Statement selectStmt = null;
		Connection activeConnection = null;
		ResultSet DetailsResultSet = null;
		SelectQuery = "select id,status  from PineLabsDetails where id='"+ id + "'";
		System.out.println("in java file SK : " + SelectQuery);
		try {
			activeConnection = OracleConnectionUtil.getConnection();
		} catch (Exception od) {
			System.out.println("[" + GetDate() + "]" + "In PineLabsEdit.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("[" + GetDate() + "]" + "In PineLabsEdit.java:    ORA Connection Created!");
		try {
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			while (DetailsResultSet.next()) {
				FileStatusList.add(DetailsResultSet.getString("id"));
				FileStatusList.add(DetailsResultSet.getString("status"));
			}
			System.out.println("in java file SK: " + FileStatusList.size() + "] [data][" + FileStatusList + "]");
			DetailsResultSet.close();
			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		} catch (SQLException ESQL) {
			System.out.println("[" + GetDate() + "]" + "In PineLabsEdit.java:    SQL Error" + ESQL.getMessage());
		} finally {
			try {
				activeConnection.close();
			} catch (Exception ee) {
				System.out.println("[" + GetDate() + "]" + "In PineLabsEdit.java:    Exception: " + ee.getMessage());
				ee.printStackTrace();
			}
		}
	}

}
