package com.rh.controller;

import java.net.URLDecoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rh.dao.DBservice1;

@Path("/earncuts")
public class EarnCuts {
	
	final static Logger logger = Logger.getLogger(EarnCuts.class);
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response service(@FormParam("ettId") String ettId,@FormParam("otp") String otp) {
		
		try {
			logger.debug("Earncuts ettId: " + ettId+" , otp :"+otp);
			
			JsonArray array = new JsonArray();
			
			String query = "select * from EarnCuts where status=1 order by priority";
			ResultSet rs = DBservice1.selectdata(query);
			
			while(rs.next()){
				JsonObject obj = new JsonObject();
				obj.addProperty("name", rs.getString("name"));
				obj.addProperty("imgUrl", rs.getString("imgUrl"));
				obj.addProperty("actionUrl", rs.getString("actionUrl"));
				array.add(obj);
			}
			
			String json = array.toString();
			
			if(json.length()<3){
				return Response.status(204).build();
			}
			
			return Response.ok(json).build();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: ", e);
			return Response.status(500).build();
		}
	}
	
	@POST
	@Path("/click")
	@Produces(MediaType.TEXT_HTML)
	public Response getData1(String data) {

		logger.debug(data);
		String ettId = "";
		String url = "";
		String title = "";
		
		if (data != null && !data.equals("")) {
			try {
				if (data.contains("&")) {
					String key[] = data.split("&");

					for (String data1 : key) {

						String values[] = data1.split("=");
						if (values[0].equals("ettId")) {
							ettId = values[1];
						}else if (values[0].equals("url")){
							url = URLDecoder.decode(values[1],"utf-8");
						}else if(values[0].equals("title")){
							title = values[1];
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ResultSet rs = null;
		try {

			logger.debug("ettId : " + ettId+"| url : "+url+" | title : "+title);

			String query = "insert into EarnCutsClicks (ettId,createdTime,url,appName) values (?,now(),?,?)";
			PreparedStatement ps = DBservice1.getPS(query);
			ps.setLong(1, Long.parseLong(ettId));
			ps.setString(2, url);
			ps.setString(3, title);
			ps.executeUpdate();
			
			System.out.println("Query: "+ query);
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
