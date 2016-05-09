package com.web.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class BreakingAlertDto {

		private Long id;
		private String text;
		private String imageUrl;
		private int validity;
		private String clickable;
		private String onClickType;
		private long offerId;
		private String actionUrl;
		private String menuName;
		private String popUpHeading;
		private String popUpText;
		private String popUpButtonText[];
		private String popUpActionUrl[];
		private int visualCode;
		private String update_id;
		
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BreakingAlertDto other = (BreakingAlertDto) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}
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
		public String getClickable() {
			return clickable;
		}
		public void setClickable(String clickable) {
			this.clickable = clickable;
		}
		public String getOnClickType() {
			return onClickType;
		}
		public void setOnClickType(String onClickType) {
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
		public String[] getPopUpButtonText() {
			return popUpButtonText;
		}
		public void setPopUpButtonText(String[] popUpButtonText) {
			this.popUpButtonText = popUpButtonText;
		}
		public String[] getPopUpActionUrl() {
			return popUpActionUrl;
		}
		public void setPopUpActionUrl(String[] popUpActionUrl) {
			this.popUpActionUrl = popUpActionUrl;
		}
		public int getVisualCode() {
			return visualCode;
		}
		public void setVisualCode(int visualCode) {
			this.visualCode = visualCode;
		}
		public String getUpdate_id() {
			return update_id;
		}
		public void setUpdate_id(String update_id) {
			this.update_id = update_id;
		}
		
		
	}
