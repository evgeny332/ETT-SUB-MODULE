package com.web.rest.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class DynamicTabDetailsDto {

	private String tabName;
	
	private String tabActionUrl;
	
	private String tabPopupText;
	
	private Long tabId;
	
	private boolean isRedirection;

	private boolean isShowAds;
	
	private String tabType;
	
	
	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getTabActionUrl() {
		return tabActionUrl;
	}

	public void setTabActionUrl(String tabActionUrl) {
		this.tabActionUrl = tabActionUrl;
	}

	public String getTabPopupText() {
		return tabPopupText;
	}

	public void setTabPopupText(String tabPopupText) {
		this.tabPopupText = tabPopupText;
	}

	public Long getTabId() {
		return tabId;
	}

	public void setTabId(Long tabId) {
		this.tabId = tabId;
	}

	public boolean isRedirection() {
		return isRedirection;
	}

	public void setRedirection(boolean isRedirection) {
		this.isRedirection = isRedirection;
	}

	public boolean isShowAdds() {
		return isShowAds;
	}

	public void setShowAdds(boolean isShowAdds) {
		this.isShowAds = isShowAdds;
	}

	public String getTabType() {
		return tabType;
	}

	public void setTabType(String tabType) {
		this.tabType = tabType;
	}

	
	
}
