package com.rh.controller;

import java.net.URI;
import java.net.URLDecoder;
import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.rh.dao.DBservice;

@Path("/bestapps")
public class BestApps {

	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getHtml(@Context UriInfo uriInfo) {

		System.out.println("Welcome...   ");
		URI uri = uriInfo.getBaseUriBuilder().path("../topapps.html").build();
		return Response.seeOther(uri).build();
	}

	@POST
	@Path("/show")
	@Produces(MediaType.TEXT_HTML)
	public Response getData(@FormParam("id") String id) {

		ResultSet rs = null;
		try {

			System.out.println("Id : " + id);

			int count = 1;
			boolean close = false;

			StringBuilder builder = new StringBuilder();

			String query = "select * from BestApps where status=1 order by priority";
			rs = DBservice.selectdata(query);

			while (rs.next()) {

				String title = rs.getString("title");
				int rating = rs.getInt("rating");
				String lowtext = rs.getString("text");
				String url = "";

				if (rs.getBoolean("urlStatus") == true) {
					url = rs.getString("url1");
				} else {
					url = rs.getString("url2");
				}

				String img = rs.getString("imgUrl");

				if (count == 1) {
					builder.append("<div class=\"section group\">");
					builder.append("<div class=\"col span_1_of_3\"><a href=# ontap=\"click12('" + url +"','"+ title + "');\" ><div class=\"icon\"><img src=\"" + img + "\"></div><div class=\"text\"><div class=\"title\"><p>" + title
							+ "</p></div><div class=\"left\"><p><span class=\"stars\"><span style=\"width:" + rating + "px\"></span></span></p></div><div class=\"right\"><p>" + lowtext + "</p></div></div></a></div>");
					count++;
					close = false;

				} else if (count == 3) {
					builder.append("<div class=\"col span_1_of_3\"><a href=# ontap=\"click12('" + url +"','"+ title + "');\" ><div class=\"icon\"><img src=\"" + img + "\"></div><div class=\"text\"><div class=\"title\"><p>" + title
							+ "</p></div><div class=\"left\"><p><span class=\"stars\"><span style=\"width:" + rating + "px\"></span></span></p></div><div class=\"right\"><p>" + lowtext + "</p></div></div></a></div>");
					builder.append("</div>");
					count = 1;
					close = true;
				} else {

					builder.append("<div class=\"col span_1_of_3\"><a href=# ontap=\"click12('" + url +"','"+ title + "');\" ><div class=\"icon\"><img src=\"" + img + "\"></div><div class=\"text\"><div class=\"title\"><p>" + title
							+ "</p></div><div class=\"left\"><p><span class=\"stars\"><span style=\"width:" + rating + "px\"></span></span></p></div><div class=\"right\"><p>" + lowtext + "</p></div></div></a></div>");
					count++;
				}
			}

			if (!close) {
				builder.append("</div>");
			}

			if (builder.length() < 1) {
				return Response.status(204).build();
			} else {
				return Response.ok(builder.toString()).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}

		return null;
	}

	@POST
	@Path("/click")
	@Produces(MediaType.TEXT_HTML)
	public Response getData1(String data) {

		System.out.println(data);
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

			System.out.println("ettId : " + ettId+"| url : "+url+" | title : "+title);
			

			String query = "insert into TrendingClicks (ettId,createdTime,url,appName) values ("+ettId+",now(),'"+url+"','"+title+"')";
			DBservice.UpdateData(query);

			
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
