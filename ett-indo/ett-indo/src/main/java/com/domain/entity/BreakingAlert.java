package com.domain.entity;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BreakingAlert implements Serializable {	
	
	public enum Clickable {
		YES,
		NO,
	}
	
	public enum OnClickType {
		Offer,
		Web,
		Menu,
		PopUp,
		API,
		INVITE,
		TAB,
	}
	
	@Id
	private Long id;
	
	private boolean status;
	
	private String text;
	
	private String imageUrl;
	
	private int validity;
	
	private Clickable clickable;
	
	private OnClickType onClickType;
	
	private long offerId;
	
	private String actionUrl;
	
	private String menuName;
	
	private String popUpHeading;
	
	private String popUpText;
	
	private String popUpButtonText;
	
	private String popUpActionUrl;
	
	private int visualCode;

	private String version;
	
	private int update_triger_id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getValidity() {
		return validity;
	}

	public void setValidity(int validity) {
		this.validity = validity;
	}

	public Clickable getClickable() {
		return clickable;
	}

	public void setClickable(Clickable clickable) {
		this.clickable = clickable;
	}

	public OnClickType getOnClickType() {
		return onClickType;
	}

	public void setOnClickType(OnClickType onClickType) {
		this.onClickType = onClickType;
	}

	public long getOfferId() {
		return offerId;
	}

	public void setOfferId(long offerId) {
		this.offerId = offerId;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getPopUpHeading() {
		return popUpHeading;
	}

	public void setPopUpHeading(String popUpHeading) {
		this.popUpHeading = popUpHeading;
	}

	public String getPopUpText() {
		return popUpText;
	}

	public void setPopUpText(String popUpText) {
		this.popUpText = popUpText;
	}

	public String getPopUpButtonText() {
		return popUpButtonText;
	}

	public void setPopUpButtonText(String popUpButtonText) {
		this.popUpButtonText = popUpButtonText;
	}

	public String getPopUpActionUrl() {
		return popUpActionUrl;
	}

	public void setPopUpActionUrl(String popUpActionUrl) {
		this.popUpActionUrl = popUpActionUrl;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getVisualCode() {
		return visualCode;
	}

	public void setVisualCode(int visualCode) {
		this.visualCode = visualCode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getUpdate_triger_id() {
		return update_triger_id;
	}

	public void setUpdate_triger_id(int update_triger_id) {
		this.update_triger_id = update_triger_id;
	}

	
	
}