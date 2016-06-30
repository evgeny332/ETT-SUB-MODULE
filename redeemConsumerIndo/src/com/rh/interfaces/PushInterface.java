package com.rh.interfaces;

import com.rh.persistence.domain.PendingRedeems;
import com.rh.persistence.domain.UserRedeem;

public interface PushInterface {

	//India
	String getSuccessPush(String response, UserRedeem userRedeem);
	String getFailurePush(String response, UserRedeem userRedeem);
	
	//Indonesia
	String getSuccessPush(int locale, PendingRedeems pendingRedeems);
	String getFailurePush(int locale, PendingRedeems pendingRedeems);
	String getProcessingPush(int locale, PendingRedeems pendingRedeems);

}
