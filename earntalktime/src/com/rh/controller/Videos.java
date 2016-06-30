package com.rh.controller;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.PreparedStatement;
import com.rh.dao.DBservice;
import com.rh.model.VideoPlaylist;

@Path("/videos")
public class Videos {

	@POST
	@Path("/playlist")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPlaylist(@FormParam("player") String player) {

		ResultSet rs = null;
		try {

			String query = "select distinct(playlist) as playlist from EttVideos where player=? order by priority";
			PreparedStatement ps = DBservice.getPS(query);
			ps.setString(1, player);
			rs = ps.executeQuery();
			
			List<String> list = new ArrayList<String>();
			
			while(rs.next()){
				
				list.add(rs.getString("playlist"));
			}
			
			if(!list.isEmpty()){
				return Response.ok(list).build();
			}else {
				return Response.status(410).build();
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
	@Path("/videolist")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVideos(@FormParam("player") String player,@FormParam("playlist") String playlistName,@FormParam("page") String page) {

		int count = 0;
		int offset = Integer.parseInt(page)*25;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query = "select title,videoId,imgUrl from EttVideos where player=? and playlist=? limit ?,25";
			ps = DBservice.getPS(query);
			ps.setString(1, player);
			ps.setString(2, playlistName);
			ps.setInt(3, offset);
			
			rs = ps.executeQuery();
			
			List<VideoPlaylist> list = new ArrayList<VideoPlaylist>();
			
			while(rs.next()){
				VideoPlaylist playlist = new VideoPlaylist();
				
				playlist.setTitle(rs.getString("title"));
				playlist.setImgUrl(rs.getString("imgUrl"));
				playlist.setVideoId(rs.getString("videoId"));
				
				list.add(playlist);
			}
			
			if(!list.isEmpty()){
				return Response.ok(list).build();
			}else {
				return Response.status(410).build();
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
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		return null;
	}
}
