package com.rh.interfaces.Impl;

import java.io.IOException;

import com.rh.config.ConfigHolder;
import com.rh.interfaces.PushInterface;
import com.rh.persistence.domain.PendingRedeems;
import com.rh.persistence.domain.UserRedeem;
import com.rh.persistence.domain.UserRedeem.RedeemType;

public class PushServiceImpl implements PushInterface {

	private ConfigHolder configHolder;

	public PushServiceImpl() throws IOException {
		this.configHolder = new ConfigHolder();
	}

	/********************************************************************************************************************/
	// ***************************************************INDIA*********************************************************//

	@Override
	public String getSuccessPush(String response, UserRedeem userRedeem) {
		String pushText = "";
		if (userRedeem.getRedeemType().equals(RedeemType.EGV) || userRedeem.getType().equalsIgnoreCase("EGV")) {
			pushText = configHolder.getProperties().getProperty("FLIPKART_EGV_SUCCESS_PUSH").replace("#AMOUNT#", (userRedeem.getAmount()) + "");
			return pushText;
		}
		if (userRedeem.getRedeemType().equals(RedeemType.LOAN)) {
			pushText = configHolder.getProperties().getProperty("REDEEM_SUCCESS_PUSH").replaceFirst("#AMOUNT#", (userRedeem.getAmount()) + "");
			pushText = pushText.replaceFirst("#MOBILE_NUMBER#", userRedeem.getMsisdn() + "");
			return pushText;
		}
		if (userRedeem.getType().equalsIgnoreCase("dth")) {
			pushText = configHolder.getProperties().getProperty("REDEEM_DTH_SUCCESS_PUSH").replaceFirst("#AMOUNT#", (userRedeem.getAmount()) + "");
			pushText = pushText.replaceFirst("#DTH_NUMBER#", userRedeem.getMsisdn() + "");
			return pushText;
		}
		if (userRedeem.getType().equalsIgnoreCase("postpaid")) {
			pushText = configHolder.getProperties().getProperty("REDEEM_DTH_SUCCESS_PUSH_POSTPAID").replaceFirst("#AMOUNT#", (userRedeem.getAmount()) + "");
			pushText = pushText.replaceFirst("#MOBILE_NUMBER#", userRedeem.getMsisdn() + "");
			return pushText;
		}
		pushText = configHolder.getProperties().getProperty("REDEEM_SUCCESS_PUSH").replaceFirst("#AMOUNT#", (userRedeem.getAmount()) + "");
		pushText = pushText.replaceFirst("#MOBILE_NUMBER#", userRedeem.getMsisdn() + "");
		return pushText;
	}

	@Override
	public String getFailurePush(String response, UserRedeem userRedeem) {
		String pushText = "";
		if (userRedeem.getRedeemType().equals(RedeemType.EGV) || userRedeem.getType().equalsIgnoreCase("EGV")) {
			pushText = configHolder.getProperties().getProperty("FLIPKART_EGV_FAIL_PUSH").replace("#AMOUNT#", (userRedeem.getAmount()) + "");
			return pushText;
		}
		if (userRedeem.getRedeemType().equals(RedeemType.LOAN)) {
			pushText = configHolder.getProperties().getProperty("REDEEM_FAIL_PUSH").replaceFirst("#AMOUNT#", (userRedeem.getAmount()) + "");
			pushText = pushText.replaceFirst("#MOBILE_NUMBER#", userRedeem.getMsisdn() + "");
			return pushText;
		}
		if (userRedeem.getType().equalsIgnoreCase("dth")) {
			pushText = configHolder.getProperties().getProperty("REDEEM_DTH_FAIL_PUSH").replaceFirst("#AMOUNT#", (userRedeem.getAmount()) + "");
			pushText = pushText.replaceFirst("#DTH_NUMBER#", userRedeem.getMsisdn() + "");
			return pushText;
		}
		if (userRedeem.getType().equalsIgnoreCase("postpaid")) {
			pushText = configHolder.getProperties().getProperty("REDEEM_POSTPAID_FAIL_PUSH").replaceFirst("#AMOUNT#", (userRedeem.getAmount()) + "");
			pushText = pushText.replaceFirst("#MOBILE_NUMBER#", userRedeem.getMsisdn() + "");
			return pushText;
		}

		if (userRedeem.getOperator().equalsIgnoreCase("AIRTEL")) {
			pushText = configHolder.getProperties().getProperty("AIRTEL_REDEEM_FAIL_PUSH").replaceFirst("#AMOUNT#", (userRedeem.getAmount()) + "");
		} else {
			pushText = configHolder.getProperties().getProperty("REDEEM_FAIL_PUSH").replaceFirst("#AMOUNT#", (userRedeem.getAmount()) + "");
		}
		pushText = pushText.replaceFirst("#MOBILE_NUMBER#", userRedeem.getMsisdn() + "");
		return pushText;
	}

	/********************************************************************************************************************/
	// ************************************************INDONESIA********************************************************//

	@Override
	public String getSuccessPush(int locale, PendingRedeems pendingRedeems) {
		String pushText = "";

		if (locale == 0) {
			if (pendingRedeems.getRedeemType().equals(RedeemType.LOAN)) {
				pushText = configHolder.getProperties().getProperty("REDEEM_SUCCESS_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
				pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
				return pushText;
			}
			if (pendingRedeems.getType().equalsIgnoreCase("dth")) {
				pushText = configHolder.getProperties().getProperty("REDEEM_DTH_SUCCESS_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
				pushText = pushText.replaceFirst("#DTH_NUMBER#", pendingRedeems.getMsisdn() + "");
				return pushText;
			}
			if (pendingRedeems.getType().equalsIgnoreCase("postpaid")) {
				pushText = configHolder.getProperties().getProperty("REDEEM_DTH_SUCCESS_PUSH_POSTPAID").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
				pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
				return pushText;
			}
			pushText = configHolder.getProperties().getProperty("REDEEM_SUCCESS_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
			pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
			return pushText;
			
		} else {
			if (pendingRedeems.getRedeemType().equals(RedeemType.LOAN)) {
				pushText = configHolder.getProperties().getProperty("BHASHA_REDEEM_SUCCESS_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
				pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
				return pushText;
			}
			if (pendingRedeems.getType().equalsIgnoreCase("dth")) {
				pushText = configHolder.getProperties().getProperty("BHASHA_REDEEM_DTH_SUCCESS_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
				pushText = pushText.replaceFirst("#DTH_NUMBER#", pendingRedeems.getMsisdn() + "");
				return pushText;
			}
			if (pendingRedeems.getType().equalsIgnoreCase("postpaid")) {
				pushText = configHolder.getProperties().getProperty("BHASHA_REDEEM_DTH_SUCCESS_PUSH_POSTPAID").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
				pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
				return pushText;
			}
			pushText = configHolder.getProperties().getProperty("BHASHA_REDEEM_SUCCESS_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
			pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
			return pushText;
		}

	}

	@Override
	public String getFailurePush(int locale, PendingRedeems pendingRedeems) {
		String pushText = "";

		if (locale == 0) {
			if (pendingRedeems.getRedeemType().equals(RedeemType.LOAN)) {
				pushText = configHolder.getProperties().getProperty("REDEEM_FAIL_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
				pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
				return pushText;
			}
			if (pendingRedeems.getType().equalsIgnoreCase("dth")) {
				pushText = configHolder.getProperties().getProperty("REDEEM_DTH_FAIL_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
				pushText = pushText.replaceFirst("#DTH_NUMBER#", pendingRedeems.getMsisdn() + "");
				return pushText;
			}
			if (pendingRedeems.getType().equalsIgnoreCase("postpaid")) {
				pushText = configHolder.getProperties().getProperty("REDEEM_POSTPAID_FAIL_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
				pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
				return pushText;
			}
			
			pushText = configHolder.getProperties().getProperty("BHASHA_REDEEM_FAIL_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
			pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
			return pushText;

		} else {
			if (pendingRedeems.getRedeemType().equals(RedeemType.LOAN)) {
				pushText = configHolder.getProperties().getProperty("BHASHA_REDEEM_FAIL_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
				pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
				return pushText;
			}
			if (pendingRedeems.getType().equalsIgnoreCase("dth")) {
				pushText = configHolder.getProperties().getProperty("BHASHA_REDEEM_DTH_FAIL_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
				pushText = pushText.replaceFirst("#DTH_NUMBER#", pendingRedeems.getMsisdn() + "");
				return pushText;
			}
			if (pendingRedeems.getType().equalsIgnoreCase("postpaid")) {
				pushText = configHolder.getProperties().getProperty("BHASHA_REDEEM_POSTPAID_FAIL_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
				pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
				return pushText;
			}
			
			pushText = configHolder.getProperties().getProperty("BHASHA_REDEEM_FAIL_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
			pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
			return pushText;
		}
	}

	@Override
	public String getProcessingPush(int locale, PendingRedeems pendingRedeems) {
		String pushText = "";

		if (locale == 0) {
			if (pendingRedeems.getRedeemType().equals(RedeemType.LOAN)) {
				pushText = configHolder.getProperties().getProperty("REDEEM_PROCESSING_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
				pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
				return pushText;
			}
			if (pendingRedeems.getType().equalsIgnoreCase("dth")) {
				pushText = configHolder.getProperties().getProperty("REDEEM_PROCESSING_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
				pushText = pushText.replaceFirst("#DTH_NUMBER#", pendingRedeems.getMsisdn() + "");
				return pushText;
			}
			if (pendingRedeems.getType().equalsIgnoreCase("postpaid")) {
				pushText = configHolder.getProperties().getProperty("REDEEM_PROCESSING_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
				pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
				return pushText;
			}
			pushText = configHolder.getProperties().getProperty("REDEEM_PROCESSING_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
			pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
			return pushText;

		} else {

			if (pendingRedeems.getRedeemType().equals(RedeemType.LOAN)) {
				pushText = configHolder.getProperties().getProperty("BHASHA_REDEEM_PROCESSING_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
				pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
				return pushText;
			}
			if (pendingRedeems.getType().equalsIgnoreCase("dth")) {
				pushText = configHolder.getProperties().getProperty("BHASHA_REDEEM_PROCESSING_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
				pushText = pushText.replaceFirst("#DTH_NUMBER#", pendingRedeems.getMsisdn() + "");
				return pushText;
			}
			if (pendingRedeems.getType().equalsIgnoreCase("postpaid")) {
				pushText = configHolder.getProperties().getProperty("BHASHA_REDEEM_PROCESSING_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
				pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
				return pushText;
			}
			pushText = configHolder.getProperties().getProperty("BHASHA_REDEEM_PROCESSING_PUSH").replaceFirst("#AMOUNT#", (pendingRedeems.getAmount()) + "");
			pushText = pushText.replaceFirst("#MOBILE_NUMBER#", pendingRedeems.getMsisdn() + "");
			return pushText;
		}
	}

}
