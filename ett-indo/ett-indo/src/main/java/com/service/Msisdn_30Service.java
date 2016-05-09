package com.service;

import com.domain.entity.InviteBonusMsisdn;
import com.domain.entity.Msisdn_30;
import com.domain.entity.User;

public interface Msisdn_30Service {

	void updateAmount(User user,Msisdn_30 msisdn_30,int amount,long offerId,String oname);

	void giveInviteMoney(User user, InviteBonusMsisdn inviteBonusMsisdn);
		
}
