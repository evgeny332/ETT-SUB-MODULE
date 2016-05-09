package com.web.rest.dto;




import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Ankur
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EarnMoreCoinsDto {

	private String title;
	private String url;
	private String text;
	private String buttonName;
	
	
	public String getButtonName() {
		return buttonName;
	}
	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}	
}