package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.Index;

import com.domain.entity.LocaleTextTemplate.Locale;

@Entity
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ettId;

	@Index(name = "deviceId")
	private String deviceId;

	@Index(name = "msisdn")
	private String msisdn;

	private String location;

	private String appVersion;

	private boolean isVerified;

	private Date createdTime;

	private Date updatedTime;

	private Date regDate;

	private DeviceToken.DeviceType deviceType;

	private int timeZoneInSeconds;

	private String operator;

	private int otp;

	private int tempOtp;

	private int status;

	private int redeemCount;

	//2G/3G/WIFI
	private String networkType;

	private String operatorCircle;

	private boolean isDownloadedFirstApp;

	private boolean isFirstLogin;
	private int totlNoOfDLoadApp;
	private boolean flage;

	private int isDeviceSupport;

	private Locale locale;

	@Index(name = "category")
	private String category;

	public User() {
	}

	@PrePersist
	protected void insertDates() {
		if (createdTime == null) {
			createdTime = new Date();
			updatedTime = new Date();
		}
	}

	@PreUpdate
	protected void updateDates() {
		if (updatedTime == null) {
			updatedTime = new Date();
		}
	}

	public int getTempOtp() {
		return tempOtp;
	}

	public void setTempOtp(int tempOtp) {
		this.tempOtp = tempOtp;
	}

	public Long getEttId() {
		return ettId;
	}

	public boolean isDownloadedFirstApp() {
		return isDownloadedFirstApp;
	}

	public void setDownloadedFirstApp(boolean isDownloadedFirstApp) {
		this.isDownloadedFirstApp = isDownloadedFirstApp;
	}

	public boolean isFirstLogin() {
		return isFirstLogin;
	}

	public void setFirstLogin(boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	public void setEttId(Long ettId) {
		this.ettId = ettId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public DeviceToken.DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceToken.DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public int getTimeZoneInSeconds() {
		return timeZoneInSeconds;
	}

	public void setTimeZoneInSeconds(int timeZoneInSeconds) {
		this.timeZoneInSeconds = timeZoneInSeconds;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getOperatorCircle() {
		return operatorCircle;
	}

	public void setTotlNoOfDLoadApp(int totlNoOfDLoadApp) {
		this.totlNoOfDLoadApp = totlNoOfDLoadApp;
	}

	public int getTotlNoOfDLoadApp() {
		return totlNoOfDLoadApp;
	}

	public void setOperatorCircle(String operatorCircle) {
		this.operatorCircle = operatorCircle;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getIsDeviceSupport() {
		return isDeviceSupport;
	}

	public void setIsDeviceSupport(int isDeviceSupport) {
		this.isDeviceSupport = isDeviceSupport;
	}

	public boolean isFlage() {
		return flage;
	}

	public void setFlage(boolean flage) {
		this.flage = flage;
	}

	public int getRedeemCount() {
		return redeemCount;
	}

	public void setRedeemCount(int redeemCount) {
		this.redeemCount = redeemCount;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public static class Builder {
		private Long ettId;
		private String deviceId;
		private String msisdn;
		private String location;
		private String appVersion;
		private boolean isVerified;
		private Date createdTime;
		private Date updatedTime;
		private DeviceToken.DeviceType deviceType;
		private int timeZoneInSeconds;
		private String operator;
		private int otp;
		private int tempOtp;
		private int status;
		private String networkType;
		private String operatorCircle;
		private boolean isDownloadedFirstApp;
		private Locale locale;
		private String category;

		public Builder ettId(Long ettId) {
			this.ettId = ettId;
			return this;
		}

		public Builder deviceId(String deviceId) {
			this.deviceId = deviceId;
			return this;
		}

		public Builder msisdn(String msisdn) {
			this.msisdn = msisdn;
			return this;
		}

		public Builder location(String location) {
			this.location = location;
			return this;
		}

		public Builder appVersion(String appVersion) {
			this.appVersion = appVersion;
			return this;
		}

		public Builder isVerified(boolean isVerified) {
			this.isVerified = isVerified;
			return this;
		}

		public Builder createdTime(Date createdTime) {
			this.createdTime = createdTime;
			return this;
		}

		public Builder updatedTime(Date updatedTime) {
			this.updatedTime = updatedTime;
			return this;
		}

		public Builder deviceType(DeviceToken.DeviceType deviceType) {
			this.deviceType = deviceType;
			return this;
		}

		public Builder timeZoneInSeconds(int timeZoneInSeconds) {
			this.timeZoneInSeconds = timeZoneInSeconds;
			return this;
		}

		public Builder operator(String operator) {
			this.operator = operator;
			return this;
		}

		public Builder otp(int otp) {
			this.otp = otp;
			return this;
		}

		public Builder tempOtp(int tempOtp) {
			this.tempOtp = tempOtp;
			return this;
		}

		public Builder status(int status) {
			this.status = status;
			return this;
		}

		public Builder networkType(String networkType) {
			this.networkType = networkType;
			return this;
		}

		public Builder operatorCircle(String operatorCircle) {
			this.operatorCircle = operatorCircle;
			return this;
		}

		public Builder isDownloadedFirstApp(boolean isDownloadedFirstApp) {
			this.isDownloadedFirstApp = isDownloadedFirstApp;
			return this;
		}

		public Builder locale(Locale locale) {
			this.locale = locale;
			return this;
		}

		public Builder category(String category) {
			this.category = category;
			return this;
		}

		public User build() {
			return new User(this);
		}
	}

	private User(Builder builder) {
		this.ettId = builder.ettId;
		this.deviceId = builder.deviceId;
		this.msisdn = builder.msisdn;
		this.location = builder.location;
		this.appVersion = builder.appVersion;
		this.isVerified = builder.isVerified;
		this.createdTime = builder.createdTime;
		this.updatedTime = builder.updatedTime;
		this.deviceType = builder.deviceType;
		this.timeZoneInSeconds = builder.timeZoneInSeconds;
		this.operator = builder.operator;
		this.otp = builder.otp;
		this.tempOtp = builder.tempOtp;
		this.status = builder.status;
		this.networkType = builder.networkType;
		this.operatorCircle = builder.operatorCircle;
		this.isDownloadedFirstApp = builder.isDownloadedFirstApp;
		this.locale = builder.locale;
		this.category = builder.category;
	}
}