package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Index;

@Entity
public class Tarrot implements Serializable{

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		
		private String tarrotName;
		
		private String detail;
		
		private Date createdTime;
		
		private Date viewDateIST;
		
		private String askValue ;
		
		private String cardName;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getTarrotName() {
			return tarrotName;
		}

		public void setTarrotName(String tarrotName) {
			this.tarrotName = tarrotName;
		}

		public String getDetail() {
			return detail;
		}

		public void setDetail(String detail) {
			this.detail = detail;
		}

		public Date getCreatedTime() {
			return createdTime;
		}

		public void setCreatedTime(Date createdTime) {
			this.createdTime = createdTime;
		}

		public Date getViewDateIST() {
			return viewDateIST;
		}

		public void setViewDateIST(Date viewDateIST) {
			this.viewDateIST = viewDateIST;
		}

		public String getAskValue() {
			return askValue;
		}

		public void setAskValue(String askValue) {
			this.askValue = askValue;
		}

		public String getCardName() {
			return cardName;
		}

		public void setCardName(String cardName) {
			this.cardName = cardName;
		}
		
		
}
