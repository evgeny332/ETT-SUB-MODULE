package com.rh.utility;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.rh.dbo.DBservice;
import com.rh.dbo.DBservice1;

public class OfferStatus {
	static ArrayList<String> packageName = new ArrayList<String>();
	static ArrayList<String> vendorName = new ArrayList<String>();
	static Logger logger = Logger.getLogger(OfferStatus.class);

	public static void changeOfferStatus() {
		logger.info("Start Fetching packageNAme From Advt table");
		String query = "select packageName,vendor from Advt where status=1;";
		PreparedStatement ps = DBservice1.getPS(query);
		ResultSet rs;
		try {
			rs = ps.executeQuery();

			while (rs.next()) {
				packageName.add(rs.getString(1));
				vendorName.add(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < packageName.size(); i++) {
			String query1 = "update ApiOfferData set Status=1 where packageName='" + packageName.get(i) + "' and vendorName='" + vendorName.get(i) + "' ";
			DBservice.UpdateData(query1);
		}
		logger.info("Done changing status in Live OfferApi table");
	}
}
