
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

import com.earntt.PineLabsBean;


public class PineLabsDetails extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		HttpSession Session = request.getSession(true);
		ArrayList FileStatusList = new ArrayList();
		try {
			GetResultData(FileStatusList);

			Session.setAttribute("FileStatusList", FileStatusList);

			if (FileStatusList.size() == 0 ) {
			}

			else {
				response.sendRedirect(response.encodeURL("jsp/PineLabsDetails.jsp"));
			}

			// response.sendRedirect(response.encodeURL("jsp/OfferEventView.jsp"));
		} catch (Exception Eb) {
			Eb.printStackTrace();
			System.out.println("[" + GetDate() + "]" + "In PineLabsDetails.java:    SQLState:  " + Eb.getMessage());
			response.sendRedirect(response.encodeURL("jsp/redirectpage.jsp?ST=E016"));
		}
	}

	public String GetDate() {
		Calendar cal = Calendar.getInstance();
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

	public void GetResultData(ArrayList<PineLabsBean> FileStatusList) {
		String SelectQuery = "";
		Statement selectStmt = null;
		Connection activeConnection = null;
		ResultSet DetailsResultSet = null;

			SelectQuery = "select id,brandName,schemeName,faceValue,status,fee from PineLabsDetails where faceValue<=150";

		System.out.println(SelectQuery);
		try {
			activeConnection = OracleConnectionUtil.getConnection();
		} catch (Exception od) {
			System.out.println("[" + GetDate() + "]" + "In PineLabsDetails.java: Not able to connect with ORACLE");
			od.printStackTrace();
		}
		System.out.println("[" + GetDate() + "]" + "In PineLabsDetails.java: OR A Connection Created!");
		try {
			selectStmt = activeConnection.createStatement();
			DetailsResultSet = selectStmt.executeQuery(SelectQuery);
			PineLabsBean dbean = null;
			while (DetailsResultSet.next()) {
				dbean = new PineLabsBean();
				dbean.setId(DetailsResultSet.getString("id"));
				dbean.setBrandName(DetailsResultSet.getString("brandName"));
				dbean.setSchemeName(DetailsResultSet.getString("schemeName"));
				dbean.setFaceValue(DetailsResultSet.getString("faceValue"));
				dbean.setStatus(DetailsResultSet.getString("status"));
				dbean.setFee(DetailsResultSet.getString("fee"));
				FileStatusList.add(dbean);
			}
			System.out.println("in java file : " + FileStatusList.size());
			DetailsResultSet.close();
			selectStmt.close();
			activeConnection.close();
			System.out.println("query has been executed...!!!");
		} catch (SQLException ESQL) {
			System.out.println("[" + GetDate() + "]" + "In PineLabsDetails.java:    SQL Error" + ESQL.getMessage());
		} finally {
			try {
				activeConnection.close();
			} catch (Exception ee) {
				System.out.println("[" + GetDate() + "]" + "In PineLabsDetails.java:    Exception: " + ee.getMessage());
				ee.printStackTrace();
			}
		}
	}

}
