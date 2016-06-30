package com.rh.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.rh.dao.DBservice1;
import com.rh.model.Challenges;
import com.rh.model.ContestResult;
import com.rh.model.LuckyDraw;

@Path("/challenge")
public class Challenge {

	final static Logger log = Logger.getLogger(Challenge.class);

	@OPTIONS
	public Response getOptions() {
		return Response.ok().header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
				.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Authorization").build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTab(String data) {

		log.info(data);

		ResultSet rs = null;
		try {

			List<Challenges> list = new ArrayList<Challenges>();
			String query = "select * from Challenges where status=1 order by priority";
			rs = DBservice1.selectdata(query);

			while (rs.next()) {

				Challenges challenge = new Challenges();

				challenge.setCategory(rs.getString("category"));
				challenge.setOfferName(rs.getString("offerName"));
				challenge.setOfferCategory(rs.getString("offerCategory"));
				challenge.setDescription(rs.getString("description"));
				challenge.setDetailedInstructions(rs.getString("detailedInstructions"));
				challenge.setImageUrl(rs.getString("imageUrl"));
				challenge.setUrl(rs.getString("resultUrl"));
				list.add(challenge);

			}

			if (list.size() < 1) {
				return Response.noContent().header("Access-Control-Allow-Origin", "*").build();
			} else {
				return Response.ok(list).header("Access-Control-Allow-Origin", "*").build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().header("Access-Control-Allow-Origin", "*").build();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
					log.error("Error in Challenge", e2);
					e2.printStackTrace();
				}
			}
		}
	}

	@OPTIONS
	@Path("/results/")
	public Response getOptions2() {
		return Response.ok().header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
				.header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Authorization").build();
	}

	@POST
	@Path("/results/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResults(String data) {

		try {

			List<ContestResult> list = new ArrayList<ContestResult>();
			String query = "select * from ContestResult";
			ResultSet rs = DBservice1.selectdata(query);

			while (rs.next()) {
				
				ContestResult result = new ContestResult();
				result.setId(rs.getInt("id"));
				result.setEttId(rs.getLong("ettId"));
				result.setMsisdn(rs.getString("msisdn"));
				result.setImageUrl(rs.getString("imageUrl"));
				
				list.add(result);
				
			}
			
			return Response.ok(list).header("Access-Control-Allow-Origin", "*").build();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@POST
	@Path("/ldraw/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLucky(String data) {

		log.info(data);
		System.out.println(data);

		Long ettId = null;
		boolean call = false;
		if (data != null && !data.equals("")) {

			try {
				if (data.contains("&")) {
					String key[] = data.split("&");

					for (String data1 : key) {

						String values[] = data1.split("=");
						if (values[0].equals("ettId")) {
							ettId = Long.parseLong(values[1]);
							call = true;
						}
					}
				} else {
					if (data.contains("=")) {
						String values[] = data.split("=");
						if (values[0].equals("ettId")) {
							ettId = Long.parseLong(values[1]);
							call = true;
						}
					}
				}

			} catch (Exception e) {
				log.error("Error in getting parameter", e);
				e.printStackTrace();
			}
		}

		ResultSet rs = null;

		try {

			List<LuckyDraw> list = new ArrayList<LuckyDraw>();
			int point = 0;

			log.info("EttId: " + ettId + "| call: " + call);
			if (call) {

				if (data.contains("one")) {
					String quer2 = "select * from LuckyDraw where ettId=? and status=1";
					PreparedStatement ps = DBservice1.getPS(quer2);
					ps.setLong(1, ettId);
					rs = ps.executeQuery();

					while (rs.next()) {
						LuckyDraw draw = new LuckyDraw();
						point = rs.getInt("point");
						draw.setPoints(point + "");
						list.add(draw);
					}

					if (point < 1) {
						return Response.noContent().build();
					}
					return Response.ok(list).build();

				} else if (data.contains("two")) {

					System.out.println("Hello");
					String quer2 = "select * from EdrLuckyDraw where ettId=?";
					PreparedStatement ps = DBservice1.getPS(quer2);
					ps.setLong(1, ettId);
					rs = ps.executeQuery();

					while (rs.next()) {
						LuckyDraw draw = new LuckyDraw();
						// point = ;
						if (rs.getString("remarks").equals("UNINSTALL")) {
							draw.setPoints(rs.getInt("app_point") + "");
							draw.setRemarks(rs.getString("remarks"));
							draw.setAppKey(rs.getString("appKey"));

							list.add(draw);
						}
					}

					if (list.size() < 1) {
						return Response.noContent().build();
					}
					return Response.ok(list).build();

				} else {
					return Response.noContent().build();
				}

			} else {
				return Response.noContent().build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
					log.error("Error in Challenge", e2);
					e2.printStackTrace();
				}
			}
		}
	}
}
