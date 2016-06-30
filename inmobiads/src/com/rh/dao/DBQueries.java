package com.rh.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.rh.entity.JsonFormat;

public class DBQueries {

	public String getAds() {

		ResultSet rs = null;
		
		String query = "select * from Advt where status=1 order by priority";
		rs = DBservice.selectdata(query);

		String resp = "[]";
		List<JsonFormat> list = new ArrayList<JsonFormat>();
		

		try {

			while (rs.next()) {
				JsonFormat jf = new JsonFormat();
				jf.setOfferName(rs.getString("offerName"));
				jf.setActionUrl(rs.getString("actionUrl"));
				jf.setDescription(rs.getString("description"));
				jf.setOfferButtonText(rs.getString("buttonText"));
				jf.setImageUrl(rs.getString("imageUrl"));
				jf.setNamespace(rs.getString("title"));
				jf.setScript("script");
				jf.setRating(rs.getString("rating"));

				list.add(jf);
			}
			
			Gson json = new Gson();
			resp = json.toJson(list);
			return resp;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(rs != null){
					rs.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
