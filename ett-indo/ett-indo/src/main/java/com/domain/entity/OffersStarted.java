package com.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Index;

@Entity
public class OffersStarted implements Serializable {	


	/*public enum PayOutType {
		INSTALL,
		 SHARE,
		 DEFFERED,
	}*/
	
	@Id
	private String id;
	
	@Index(name="ettId")
	private Long ettId;
	
	private Long offerId;
	
	private String imageUrl;
	private String offerName;
	
	private Date installedTime;
	
	private Date callbackTime;
	
	/*private PayOutType payOutType;*/
	private String payOutType;
	private String  offerLifeCycle;
		
	private String approveInfoText;
	
	private String criticalInfo;

	private String earnInfo;
	
	private String userDeferedInfo;
	
	private String deferedPaymentFinalInfor;
	
	private boolean status;
	
	private String instructions;
	
	private String offerCategory;
	
	private String packageName;

	private String payoutOn;
	
	private int update_triger_id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getEttId() {
		return ettId;
	}

	public void setEttId(Long ettId) {
		this.ettId = ettId;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	
	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public Date getInstalledTime() {
		return installedTime;
	}

	public void setInstalledTime(Date installedTime) {
		this.installedTime = installedTime;
	}

	public Date getCallbackTime() {
		return callbackTime;
	}

	public void setCallbackTime(Date callbackTime) {
		this.callbackTime = callbackTime;
	}

	/*public PayOutType getPayoutType() {
		return payOutType;
	}*/

	/*public void setPayoutType(PayOutType payoutType) {
		//System.out.println("inside the bean "+payoutType+","+PayOutTypeStart.INSTALL);
		this.payOutType = payoutType.name();
	}*/

	public String getOfferLifeCycle() {
		return offerLifeCycle;
	}

	public void setOfferLifeCycle(String offerLifeCycle) {
		this.offerLifeCycle = offerLifeCycle;
	}

	
	public String getApproveInfoText() {
		return approveInfoText;
	}

	public void setApproveInfoText(String approveInfoText) {
		this.approveInfoText = approveInfoText;
	}

	public String getCriticalInfo() {
		return criticalInfo;
	}

	public void setCriticalInfo(String criticalInfo) {
		this.criticalInfo = criticalInfo;
	}

	public String getEarnInfo() {
		return earnInfo;
	}

	public void setEarnInfo(String earnInfo) {
		this.earnInfo = earnInfo;
	}

	public String getDeferedPaymentFinalInfor() {
		return deferedPaymentFinalInfor;
	}

	public void setDeferedPaymentFinalInfor(String deferedPaymentFinalInfor) {
		this.deferedPaymentFinalInfor = deferedPaymentFinalInfor;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUserDeferedInfo() {
		return userDeferedInfo;
	}

	public void setUserDeferedInfo(String userDeferedInfo) {
		this.userDeferedInfo = userDeferedInfo;
	}

	public String getOfferCategory() {
		return offerCategory;
	}

	public void setOfferCategory(String offerCategory) {
		this.offerCategory = offerCategory;
	}

	public String getPayOutType() {
		return payOutType;
	}

	public void setPayOutType(String payOutType) {
		this.payOutType = payOutType;
	}

	public String getPayoutOn() {
		return payoutOn;
	}

	public void setPayoutOn(String payoutOn) {
		this.payoutOn = payoutOn;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public int getUpdate_triger_id() {
		return update_triger_id;
	}

	public void setUpdate_triger_id(int update_triger_id) {
		this.update_triger_id = update_triger_id;
	}
	
	
}