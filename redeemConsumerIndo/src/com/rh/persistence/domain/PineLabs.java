package com.rh.persistence.domain;

import java.io.Serializable;

public class PineLabs implements Serializable{

	private static final long serialVersionUID = -1274555976751356956L;
	
	private int brandId;
	private String brandName;
	private int schemeId;
	private String schemeName;
	private String purchaseValue;
	private String faceValue;
	
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public int getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(int schemeId) {
		this.schemeId = schemeId;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public String getPurchaseValue() {
		return purchaseValue;
	}
	public void setPurchaseValue(String purchaseValue) {
		this.purchaseValue = purchaseValue;
	}
	public String getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}
	
	@Override
	public String toString() {
		return "PineLabs [brandId=" + brandId + ", brandName=" + brandName + ", schemeId=" + schemeId + ", schemeName=" + schemeName + ", purchaseValue=" + purchaseValue + ", faceValue=" + faceValue + "]";
	}
}
