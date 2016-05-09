package com.web.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class EttVideoListDto {

	private String title;
	
	private String imageUrl;
	
	private String videoId;
	
	private Long update_id;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public Long getUpdate_id() {
		return update_id;
	}

	public void setUpdate_id(Long update_id) {
		this.update_id = update_id;
	}

	
}
