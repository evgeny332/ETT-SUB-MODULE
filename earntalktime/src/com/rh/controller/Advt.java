package com.rh.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;

import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.rh.dao.DBservice1;

@Path("/ads")
public class Advt {

	@OPTIONS
	@Path("/update/")
	public Response getOptions() {
		return Response.ok().header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
				.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Authorization").build();
	}

	@POST
	@Path("/update/")
	public Response Start(MultivaluedMap<String, String> form) {

		String require = form.getFirst("require");
		System.out.println(require);
		
		if(require.equals("start")){
			try {

				String query = "insert into Advt (createdTime,updatedTime,offerId,offerName,category,appKey,description,imageUrl,offerAmount,offerType,operator,priority,status,size,vendor,gender,operatorCircle,rating,packageName,title,appVersion,city,actionUrl) values (now(),now(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) on duplicate key update status=1";
				PreparedStatement ps = DBservice1.getPS(query);
				ps.setInt(1, Integer.parseInt(form.getFirst("offerId")));
				ps.setString(2, form.getFirst("offerName"));
				ps.setString(3, form.getFirst("category"));
				ps.setString(4, form.getFirst("appKey"));
				ps.setString(5, form.getFirst("description"));
				ps.setString(6, form.getFirst("imageUrl"));
				ps.setFloat(7, Float.parseFloat(form.getFirst("offerAmount")));
				ps.setString(8, form.getFirst("offerType"));
				ps.setString(9, null);
				ps.setInt(10, 1);
				ps.setInt(11, 1);
				ps.setString(12, form.getFirst("size"));
				ps.setString(13, form.getFirst("vendor"));
				ps.setString(14, form.getFirst("gender"));
				ps.setString(15, null);
				ps.setString(16, form.getFirst("rating"));
				ps.setString(17, form.getFirst("packageName"));
				ps.setString(18, form.getFirst("title"));
				ps.setString(19, form.getFirst("appVersion") == null ? "All" : form.getFirst("appVersion"));
				ps.setString(20, null);
				ps.setString(21, form.getFirst("actionUrl"));

				ps.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
				return Response.status(500).header("Access-Control-Allow-Origin", "*").build();
			}

			SendDirect("http://108.161.133.119/LiveOfferResponseAPI/OfferLive?vendorName="+form.getFirst("vendor")+"&packageName="+form.getFirst("packageName")+"&status=1");
			return Response.status(200).header("Access-Control-Allow-Origin", "*").build();
			
		}else if(require.equals("stop")) {
			
			System.out.println(form.getFirst("vendor") + ", " + form.getFirst("packageName"));

			try {

				String quer = "update Advt set status=0 where vendor='" + form.getFirst("vendor") + "' and packageName='" + form.getFirst("packageName") + "'";
				DBservice1.UpdateData(quer);

			} catch (Exception e) {
				e.printStackTrace();
				return Response.status(500).header("Access-Control-Allow-Origin", "*").build();
			}
			SendDirect("http://108.161.133.119/LiveOfferResponseAPI/OfferLive?vendorName="+form.getFirst("vendor")+"&packageName="+form.getFirst("packageName")+"&status=0");
			return Response.status(200).header("Access-Control-Allow-Origin", "*").build();
		}
		
		return null;
	}

	public void SendDirect(String URL_str) {
		
		URLConnection theURLconn =  null;
		try {
			
			System.out.println("Url: "+ URL_str);
			
			URL url = new URL(URL_str);
			theURLconn = url.openConnection();
			BufferedReader BR = new BufferedReader(new InputStreamReader(theURLconn.getInputStream()));
			String show = null;
			String toprt = "";
			while ((show = BR.readLine()) != null) {
				toprt = toprt + show;
				System.out.println(toprt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}