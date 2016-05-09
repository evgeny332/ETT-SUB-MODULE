package com.domain.entity;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DynamicTabDetails implements Serializable {	
	
	
	@Id
	private Long id;
	
	private boolean tabStatus;
	
	private String tabName;
	
	private String tabActionUrl;
	
	private String tabPopupText;

	private Long offerId;
	
	private String category;
	
	private Long tabId;
	
	private String version;
	
	private boolean isRedirection;
	
	private boolean isShowAdds;
	
	private String tabType;
	
	private int priority;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isTabStatus() {
		return tabStatus;
	}

	public void setTabStatus(boolean tabStatus) {
		this.tabStatus = tabStatus;
	}

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

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public Long getTabId() {
		return tabId;
	}

	public void setTabId(Long tabId) {
		this.tabId = tabId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isRedirection() {
		return isRedirection;
	}

	public void setRedirection(boolean isRedirection) {
		this.isRedirection = isRedirection;
	}

	public boolean isShowAdds() {
		return isShowAdds;
	}

	public void setShowAdds(boolean isShowAdds) {
		this.isShowAdds = isShowAdds;
	}

	public String getTabType() {
		return tabType;
	}

	public void setTabType(String tabType) {
		this.tabType = tabType;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	
	
}