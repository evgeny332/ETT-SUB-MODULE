package com.rh.interfaces.Impl;

import java.io.IOException;

import com.rh.config.ConfigHolder;
import com.rh.interfaces.RHServiceInterface;
import com.rh.persistence.DBPersister;
import com.rh.persistence.domain.PendingRedeems;
import com.rh.persistence.domain.RedeemAmountConfig;
import com.rh.persistence.domain.UserAccount;
import com.rh.persistence.domain.UserRedeem;

public class RHServiceImpl implements RHServiceInterface {

	private ConfigHolder configHolder;

	public RHServiceImpl() throws IOException {
		this.configHolder = new ConfigHolder();
	}

	@Override
	public void setConvenienceCharge(UserRedeem userRedeem, DBPersister dbPersister) {
		RedeemAmountConfig redeemAmountConfig = dbPersister.getRedeemAmountConfig(userRedeem.getType(), userRedeem.getOperator());
		if (redeemAmountConfig != null) {
			String amountArray[] = redeemAmountConfig.getAmount().split(",");
			String feeArray[] = redeemAmountConfig.getFee().split(",");
			int icount = 0;
			System.out.println("[inside the ConvenienceCharge][amountArray][" + redeemAmountConfig.getAmount() + "][feeArray][" + redeemAmountConfig.getFee() + "]");
			for (String amt : amountArray) {
				if (amt.equals((int) userRedeem.getAmount() + "")) {
					int fee = Integer.parseInt(feeArray[icount]);
					userRedeem.setFee(fee);
					UserAccount userAccount = dbPersister.getUserAccount(userRedeem.getEttId());
					
					/*if((int) userAccount.getCurrentBalance() >= fee){
						
						dbPersister.updateUserAccountFee(userRedeem);
						dbPersister.setProcessFee(userRedeem);
						return;
					}else{
						userRedeem.setStatus("FAILED");
						return;
					}*/
					if (fee > 0 && (int) userAccount.getCurrentBalance() >= 1 && (int) userAccount.getCurrentBalance() < fee) {

						fee = (int) userAccount.getCurrentBalance();
						userRedeem.setFee(fee);

					}
					if ((int) userAccount.getCurrentBalance() < 1) {
						fee = 0;
						userRedeem.setFee(fee);
					}	

				}
				icount++;
			}
		}
	}

	/********************************************************************************************************************/
	// ************************************************INDONESIA********************************************************//

	@Override
	public void setConvenienceCharge(PendingRedeems pendingRedeems, DBPersister dbPersister) {

		RedeemAmountConfig redeemAmountConfig = dbPersister.getRedeemAmountConfig(pendingRedeems.getType(), pendingRedeems.getOperator());
		if (redeemAmountConfig != null) {
			String amountArray[] = redeemAmountConfig.getAmount().split(",");
			String feeArray[] = redeemAmountConfig.getFee().split(",");
			int icount = 0;
			System.out.println("[inside the ConvenienceCharge][amountArray][" + redeemAmountConfig.getAmount() + "][feeArray][" + redeemAmountConfig.getFee() + "]");
			for (String amt : amountArray) {
				// System.out.println("[amt]["+amt+"] [amountArray]["+amountArray[icount]+"] [(int)pendingRedeems.getAmount()]["+(int)pendingRedeems.getAmount()+"]");
				if (amt.equals((int) pendingRedeems.getAmount() + "")) {
					// System.out.println("[amt]["+amt+"] [amountArray]["+amountArray[icount]+"] [(int)pendingRedeems.getAmount()]["+(int)pendingRedeems.getAmount()+"] inside the if");
					int fee = Integer.parseInt(feeArray[icount]);
					pendingRedeems.setFee(fee);
					UserAccount userAccount = dbPersister.getUserAccount(pendingRedeems.getEttId());
					if (fee > 0 && (int) userAccount.getCurrentBalance() >= 1 && (int) userAccount.getCurrentBalance() < fee) {

						fee = (int) userAccount.getCurrentBalance();
						pendingRedeems.setFee(fee);

					}
					if ((int) userAccount.getCurrentBalance() < 1) {
						fee = 0;
						pendingRedeems.setFee(fee);

					}
					dbPersister.updateUserAccountFee(pendingRedeems);
					dbPersister.setProcessFee(pendingRedeems);
					return;

				}
				icount++;
			}
		}
	}
}
