package com.service;

import com.domain.entity.Edr;
import com.domain.entity.OfferDetails;
import com.domain.entity.OffersStarted;
import com.domain.entity.User;

public interface EDRService {
	
	boolean checkAmazonInstallStaus(Edr edr,User user,long offerId);



	
	
	void handleInstallEdrOnHold(User user,OfferDetails offerDetails);
	
	void insertInDataUsagePendingCredits(OfferDetails offerDetails,Long ettId);



	OffersStarted setOffersStartedDetails(OffersStarted offersStarted,
			OfferDetails offerDetails, Long ettId, Long offerId,
			String payoutType, String appVersion,User user);
	
}
