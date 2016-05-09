package com.web.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.ALWAYS)
public class PopUpAlertDto {

		private Long id;
		private String heading;
		private String text;
		private int noOfButton;
		private String buttonsText[];
		private String actinoUrl[];
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
			PopUpAlertDto other = (PopUpAlertDto) obj;
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
		public String getHeading() {
			return heading;
		}
		public void setHeading(String heading) {
			this.heading = heading;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public int getNoOfButton() {
			return noOfButton;
		}
		public void setNoOfButton(int noOfButton) {
			this.noOfButton = noOfButton;
		}
		public String[] getButtonsText() {
			return buttonsText;
		}
		public void setButtonsText(String[] buttonsText) {
			this.buttonsText = buttonsText;
		}
		public String[] getActinoUrl() {
			return actinoUrl;
		}
		public void setActinoUrl(String[] actinoUrl) {
			this.actinoUrl = actinoUrl;
		}
		public String getUpdate_id() {
			return update_id;
		}
		public void setUpdate_id(String update_id) {
			this.update_id = update_id;
		}
		
		
	}
