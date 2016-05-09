package com.domain.entity;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EttVideos implements Serializable {	
	
	@Id
	private Long id;
	
	private String playlist;
	
	private String playlistId;
	
	private String title;
	
	private String videoId;
	
	private String imgUrl;
	
	private int priority;
	
	private String player;
	
	private boolean status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaylist() {
		return playlist;
	}

	public void setPaylist(String paylist) {
		this.playlist = paylist;
	}

	public String getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(String playlistId) {
		this.playlistId = playlistId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVidioId() {
		return videoId;
	}

	public void setVidioId(String vidioId) {
		this.videoId = vidioId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
}