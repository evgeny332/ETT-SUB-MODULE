package com.domain.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Index;

@Entity
public class CountryDetails implements Serializable{

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		
		@Index(name="iso_cc1")
		private String iso_cc1;
		
		@Index(name="Iso_cc2")
		private String Iso_cc2;
		
		private String cc;
		
		private String name;
		
		private String domainName;

		private String currency;
		
		
		
		public String getIso_cc1() {
			return iso_cc1;
		}

		public void setIso_cc1(String iso_cc1) {
			this.iso_cc1 = iso_cc1;
		}

		public String getIso_cc2() {
			return Iso_cc2;
		}

		public void setIso_cc2(String iso_cc2) {
			Iso_cc2 = iso_cc2;
		}

		public String getCc() {
			return cc;
		}

		public void setCc(String cc) {
			this.cc = cc;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDomainName() {
			return domainName;
		}

		public void setDomainName(String domainName) {
			this.domainName = domainName;
		}

		@Override
		public String toString() {
			return "CountryDetails [id=" + id + ", iso_cc1=" + iso_cc1
					+ ", Iso_cc2=" + Iso_cc2 + ", cc=" + cc + ", name=" + name
					+ ", domainName=" + domainName + ", currency=" + currency
					+ "]";
		}

		
		
		
}
