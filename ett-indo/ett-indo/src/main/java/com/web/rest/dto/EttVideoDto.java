package com.web.rest.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class EttVideoDto {

	private List<EttVideoListDto> ettVideoListDto;
	private boolean nextAvailable;
	public List<EttVideoListDto> getEttVideoListDto() {
		return ettVideoListDto;
	}
	public void setEttVideoListDto(List<EttVideoListDto> ettVideoListDto) {
		this.ettVideoListDto = ettVideoListDto;
	}
	public boolean isNextAvailable() {
		return nextAvailable;
	}
	public void setNextAvailable(boolean nextAvailable) {
		this.nextAvailable = nextAvailable;
	}

	
}
