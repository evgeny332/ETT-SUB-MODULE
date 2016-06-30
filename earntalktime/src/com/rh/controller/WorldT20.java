package com.rh.controller;

import java.net.URLDecoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.rh.dao.DBservice1;
import com.rh.model.T20;

/*@Path("/t20")*/
public class WorldT20{
	
	final static Logger log = Logger.getLogger(WorldT20.class);
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTab(String data) {

		log.info(data);
		long ettId = 0;
		boolean call= false;
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
		
		if(call){
			try {

				List<T20> list = new ArrayList<T20>();
//				String query = "select *,0 as selected from WorldT20;";
				String query = "select a.*,ifnull(b.winner,0) selected from WorldT20 a left join WorldCupT20Winner b on a.id=b.matchId and b.ettId="+ettId+"";
				rs = DBservice1.selectdata(query);

				while (rs.next()) {

					T20 t20 = new T20();
					
					t20.setId(rs.getInt("id"));
					t20.setDate(rs.getTimestamp("date"));
					t20.setStage(rs.getString("stage"));
					t20.setTeamA(rs.getString("teamA"));
					t20.setTeamB(rs.getString("teamB"));
					t20.setVenue(rs.getString("venue"));
					t20.setWinMargin(rs.getString("winMargin"));
					t20.setWinner(rs.getString("winner"));
					t20.setResultUrl(rs.getString("resultUrl"));
					t20.setStatus(rs.getBoolean("status"));
					t20.setSelected(rs.getString("selected"));
					t20.setIsPayout(rs.getInt("isPayout"));
					list.add(t20);
				}

				if (list.size() < 1) {
					return Response.noContent().build();
				} else {
					return Response.ok(list).build();
				}
			} catch (Exception e) {
				log.error("Error in Challenge", e);
				e.printStackTrace();
				return Response.serverError().build();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (Exception e2) {
						log.error("Error in T20", e2);
						e2.printStackTrace();
					}
				}
			}
		}
		return Response.noContent().build();
	}

	@POST
	/*@Path("/updateChance")*/
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateChance(String data) {

		log.info(data);
//		System.out.println(data);

		long ettId = 0;
		int matchId = 0;
		String winner = "";
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
						}else if(values[0].equals("winner")){
							winner = URLDecoder.decode(values[1],"utf-8");
						}else if(values[0].equals("matchId")){
							matchId = Integer.parseInt(values[1]);
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

			int point = 0;
			boolean check=false;
			log.info("EttId: " + ettId + "| call: " + call + " | winner: "+winner+ " | matchId: "+ matchId);
			if (call) {

				if (data.contains("get")) {
//					System.out.println("get");
					String quer2 = "select chance from WorldCupT20Chance where ettId=?";
					PreparedStatement ps = DBservice1.getPS(quer2);
					ps.setLong(1, ettId);
					rs = ps.executeQuery();

					while (rs.next()) {
						point= rs.getInt("chance");
						check=true;
					}
					
					if(!check && point==0){
						String query = "insert into WorldCupT20Chance (ettId,chance,updateDate) values ("+ettId+",5,now())";
						DBservice1.UpdateData(query);
						point=5;
						log.info("Insert 5 chances: "+query+"");
					}
					return Response.ok(point).build();

				} else if (data.contains("update")) {
					
//					System.out.println("update");
					String quer2 = "update WorldCupT20Chance set chance=chance-1 where ettId="+ettId+"";
					String quer3 = "insert into WorldCupT20Winner (ettId,matchId,winner,createdTime,updatedTime) values ("+ettId+","+matchId+",'"+winner+"',now(),now()) on duplicate key update winner='"+winner+"',updatedTime=now()";
					
					log.info(quer2+" | "+ quer3);
					int rows = DBservice1.UpdateData(quer2);
					if(rows > 0){
						DBservice1.UpdateData(quer3);
						return Response.ok().build();
					}else {
						return Response.noContent().build();
					}
				} else {
					return Response.noContent().build();
				}
			} else {
				return Response.noContent().build();
			}

		} catch (Exception e) {
			log.error("Error in Challenge", e);
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

	private void getDate() {

		SimpleDateFormat fdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();

	}
}
