package com.service;

import java.util.List;

import com.domain.entity.OfferDetailsNew;
import com.domain.entity.User;

public interface OffersServiceNew {

	List<OfferDetailsNew> getOffers(User user, boolean isCategorize,String networkType);

	
}
