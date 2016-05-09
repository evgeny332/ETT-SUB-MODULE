package com.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Index;

@Entity
public class LocaleTextTemplate implements Serializable{

	public enum Locale{
		EN,
		IN
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Index(name = "locale_configKey")
	private String configKey;
	
	private Locale locale;
	
	@Column(length = 1000)
	private String value;

	public String getKey() {
		return configKey;
	}

	public void setKey(String configKey) {
		this.configKey = configKey;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "LocaleTextTemplate [id=" + id + ", configKey=" + configKey
				+ ", locale=" + locale + ", value=" + value + "]";
	}
	
}
