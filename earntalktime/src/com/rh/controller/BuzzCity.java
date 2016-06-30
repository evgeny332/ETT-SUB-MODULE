package com.rh.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.rh.dao.DBservice1;

@Path("/buzzcity")
public class BuzzCity {

	final static Logger logger = Logger.getLogger(BuzzCity.class);

	// @POST
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	// public Response service(@FormParam("temp") String ettId) {
	//
	// try {
	// logger.debug("BuzzCity temp: " + ettId );
	//
	// JsonArray array = new JsonArray();
	//
	// String query = "select * from EarnCuts where status=1 order by priority";
	// ResultSet rs = DBservice1.selectdata(query);
	//
	// while(rs.next()){
	// JsonObject obj = new JsonObject();
	// obj.addProperty("name", rs.getString("name"));
	// obj.addProperty("imgUrl", rs.getString("imgUrl"));
	// obj.addProperty("actionUrl", rs.getString("actionUrl"));
	// array.add(obj);
	// }
	//
	// String json = array.toString();
	//
	// if(json.length()<3){
	// return Response.status(204).build();
	// }
	//
	// return Response.ok(json).build();
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// logger.error("Error: ", e);
	// return Response.status(500).build();
	// }
	// }

	@POST
	@Path("/click")
	@Produces(MediaType.TEXT_HTML)
	public Response getData1(String data) {

		logger.debug(data);

//		System.out.println("Hello");
//		return Response.ok("true").build();
		ResultSet rs = null;
		try {

			String query = "insert into BuzzCity (data,createdTime) values (?,now())";
			PreparedStatement ps = DBservice1.getPS(query);
			ps.setString(1, data);
			ps.executeUpdate();

			System.out.println("Query: " + query);
			return Response.ok("true").build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(204).build();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
}
