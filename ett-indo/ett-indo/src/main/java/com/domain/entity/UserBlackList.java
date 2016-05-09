package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserBlackList implements Serializable{

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long ettId;
		
		private int blackListCounter;
		
		private int type;
		
		private Date updatedTime;

		public Long getEttId() {
			return ettId;
		}

		public void setEttId(Long ettId) {
			this.ettId = ettId;
		}

		public int getBlackListCounter() {
			return blackListCounter;
		}

		public void setBlackListCounter(int blackListCounter) {
			this.blackListCounter = blackListCounter;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public Date getUpdatedTime() {
			return updatedTime;
		}

		public void setUpdatedTime(Date updatedTime) {
			this.updatedTime = updatedTime;
		}

				
		
}
