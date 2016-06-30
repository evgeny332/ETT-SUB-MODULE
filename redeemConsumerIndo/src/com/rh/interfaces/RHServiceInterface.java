package com.rh.interfaces;

import com.rh.persistence.DBPersister;
import com.rh.persistence.domain.PendingRedeems;
import com.rh.persistence.domain.UserRedeem;

public interface RHServiceInterface {
	
	//India
	void setConvenienceCharge(UserRedeem userRedeem,DBPersister dbPersister);
	
	//Indonesia
	void setConvenienceCharge(PendingRedeems pendingRedeems,DBPersister dbPersister);
}