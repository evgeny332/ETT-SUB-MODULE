import com.earntt.*;
import java.io.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class UpdatePineLabs extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		HttpSession Session = request.getSession(true);
		String application_username = null;
		String status = null;
		String id = null;

		int flag = 0;
		int size = -1;

		try {
			status = request.getParameter("status");
			id = request.getParameter("offer_id");

			if (status == null) {
				flag = -1;

			}
			if (flag == 0) {
				flag = -1;
				size = GetResultData(id, status);
			}
			if (size == 0) {
				response.sendRedirect(response.encodeURL("jsp/message.jsp?ST=E021&redirect=PineLabsRedirect.jsp"));
			} else
				response.sendRedirect(response.encodeURL("jsp/PineLabsRedirect.jsp"));
		} catch (Exception Eb) {
			Eb.printStackTrace();
			System.out.println("[" + GetDate() + "]" + "In UpdatePineLabs.java:    SQLState:  " + Eb.getMessage());
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016&redirect=UpdatePineLabs.jsp"));
		}

	}

	public String GetDateDef(String DATE_FORMAT_NOW) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

	public String GetDate() {
		Calendar cal = Calendar.getInstance();
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

	public String NewGetDate() {
		Calendar cal = Calendar.getInstance();
		String DATE_FORMAT_NOW = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

	public int GetResultData(String id, String status) {
		String SelectQuery = "";
		PreparedStatement selectStmt = null;
		Connection activeConnection = null;
		ResultSet DetailsResultSet = null;
		int size = -1;
		SelectQuery = "update PineLabsDetails set status=? where id="+ id + "";
		System.out.println(SelectQuery);
		try {
			activeConnection = OracleConnectionUtil.getConnection();
		} catch (Exception od) {
			System.out
					.println("[" + GetDate() + "]" + "In UpdatePineLabs.java:   Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("[" + GetDate() + "]" + "In UpdatePineLabs.java:    ORA Connection Created!");
		try {
			selectStmt = activeConnection.prepareStatement(SelectQuery);
			selectStmt.setInt(1, Integer.parseInt(status));

			int numRowsChanged = selectStmt.executeUpdate();
			size = 0;
			selectStmt.close();
			activeConnection.close();
			System.out.println("[" + GetDate() + "]" + "In UpdatePineLabs.java: query has been executed...!!!");
		} catch (SQLException ESQL) {
			size = 0;
			System.out.println("[" + GetDate() + "]" + "In UpdatePineLabs.java:    SQL Error" + ESQL.getMessage());
			return size;
		} finally {
			try {
				activeConnection.close();
			} catch (Exception ee) {
				System.out.println(
						"[" + GetDate() + "]" + "In UpdatePineLabs.java:    Exception: " + ee.getMessage());
				ee.printStackTrace();
			}
			return size;
		}
	}

}
