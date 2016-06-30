package com.rh.utility;

import java.util.List;

import com.rh.entity.Astrology;
import com.rh.entity.CallBackConfirmation;
import com.rh.entity.OfferDetails;
import com.rh.entity.RedeemStatus;
import com.rh.entity.UserAccount;
import com.rh.entity.UserAccountSummary;
import com.rh.entity.UserRedeem;

public class BuildString {

	public static StringBuilder buildCallBack(List<CallBackConfirmation> lis) {

		// logger.debug("buildCallBack: " + lis.size());
		int total = 0;
		int totalCount = 0;

		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < lis.size(); k++) {

			total++;
			totalCount = totalCount + lis.get(k).getCount();
			sb.append(lis.get(k).getVendor() + "," + lis.get(k).getOfferId() + "," + lis.get(k).getCount() + "#");
		}
		sb.append("Total," + total + "," + totalCount);
		// logger.debug(sb);
		return sb;
	}

	public static StringBuilder buildRedeem(List<UserRedeem> lis) {

		// logger.debug("buildRedeem: " + lis.size());
		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < lis.size(); k++) {

			sb.append(lis.get(k).getOperator() + "," + lis.get(k).getStatus() + "," + lis.get(k).getCount() + "#");
		}
		sb.setLength(sb.length() - 1);
		// logger.debug(sb);
		return sb;
	}

	public static StringBuilder buildUserAccount(List<UserAccount> lis) {

		// logger.debug("UserAccount: " + lis.size());
		int total = 0;
		int totalCount = 0;
		int totalAmount = 0;
		int tConv = 0;
		int tConvAmount = 0;

		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < lis.size(); k++) {

			if(lis.get(k).getOfferId() == 0 && lis.get(k).getOfferName().equalsIgnoreCase("conv redemption")){
				
				tConv = tConv + lis.get(k).getCount();
				tConvAmount = tConvAmount + lis.get(k).getSum();
				
				if(k == 0){
					continue;
				}
			}
			if (lis.get(k).getOfferId() != 0 && lis.get(k).getOfferId() != 121 && lis.get(k).getOfferId() != 223 && lis.get(k).getOfferId() != 921 && lis.get(k).getOfferId() != 920 && lis.get(k).getOfferId() != 239 && lis.get(k).getOfferId() != 8884) {

				total++;
				totalCount = totalCount + lis.get(k).getCount();
				totalAmount = totalAmount + lis.get(k).getSum();
			}
			
			if(k>0){
				if(k==1){
					if(lis.get(k).getOfferName().equalsIgnoreCase("conv redemption")){
						sb.append(0 + "," + lis.get(k).getOfferName() + "," + tConv + "," + tConvAmount + "#");
					}else {
						sb.append(lis.get(0).getOfferId() + "," + lis.get(0).getOfferName() + "," + lis.get(0).getCount() + "," + lis.get(0).getSum() + "#");
						sb.append(lis.get(k).getOfferId() + "," + lis.get(k).getOfferName() + "," + lis.get(k).getCount() + "," + lis.get(k).getSum() + "#");
					}
				}else{
					sb.append(lis.get(k).getOfferId() + "," + lis.get(k).getOfferName() + "," + lis.get(k).getCount() + "," + lis.get(k).getSum() + "#");
				}
			}
		}
		sb.append("Offers=" + total + ",Total," + totalCount + "," + totalAmount);
		// logger.debug(sb);
		return sb;
	}

	public static StringBuilder buildInstallCB(List<UserAccountSummary> lis) {

		// logger.debug("InstallCB: " + lis.size());
		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < lis.size(); k++) {

			sb.append(lis.get(k).getOfferId() + "," + lis.get(k).getAppKey() + "," + lis.get(k).getInstallCount() + "," + lis.get(k).getInstallAmount() + "," + lis.get(k).getCallbackCount() + ","
					+ lis.get(k).getCallbackAmount() + "#");
		}

		sb.setLength(sb.length() - 1);
		// logger.debug(sb);
		return sb;
	}

	public static StringBuilder buildOffers(List<OfferDetails> lis) {

		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < lis.size(); k++) {

			sb.append(lis.get(k).getOfferId() + "," + lis.get(k).getOfferName() + "," + lis.get(k).getOfferAmount() + "#");
		}

		sb.setLength(sb.length() - 1);
		return sb;
	}

	public static StringBuilder buildDeferred(List<OfferDetails> lis) {

		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < lis.size(); k++) {

			sb.append(lis.get(k).getOfferName() + "," + lis.get(k).getPriority() + "," + lis.get(k).getOfferAmount() + "," + lis.get(k).getDeferredPayout() + "," + lis.get(k).getUpfrontPayout() + ","
					+ lis.get(k).getOfferId() + "#");
		}

		sb.setLength(sb.length() - 1);
		return sb;
	}

	public static StringBuilder buildRedeemStatus(List<RedeemStatus> rstatus2) {

		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < rstatus2.size(); k++) {

			sb.append(rstatus2.get(k).getStatus() + "," + rstatus2.get(k).getCount() + "#");
		}

		// System.out.print(sb);
		sb.setLength(sb.length() - 1);
		// logger.debug(sb);
		return sb;
	}

	public static StringBuilder buildUserDetails(int[] array) {

		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < array.length; k++) {

			sb.append(array[k] + "#");
		}

		sb.setLength(sb.length() - 1);
		// logger.debug(sb);
		return sb;
	}

	public static StringBuilder buildAstrology(List<Astrology> astro) {

		// int total = 0;
		// int totalCount = 0;

		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < astro.size(); k++) {

			// total++;
			// totalCount = totalCount + astro.get(k).getCount();
			sb.append(astro.get(k).getSunshine() + "," + astro.get(k).getCount() + "#");
		}

		sb.setLength(sb.length() - 1);
		// sb.append("Total=" + total + "," + totalCount);
		return sb;
	}
}
