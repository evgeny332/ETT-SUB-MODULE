package com.service;

import java.util.List;

import com.domain.entity.OfferDetails;
import com.domain.entity.User;
import com.web.rest.dto.BreakingAlertDto;
import com.web.rest.dto.OffersDtoV2;
import com.web.rest.dto.OffersDtoV4;
import com.web.rest.dto.OffersStartedDto;
import com.web.rest.dto.PopUpAlertDto;
import com.web.rest.dto.ShoppingDetailsDto;

public interface OffersService {

	List<OfferDetails> getOffers(User user, boolean isCategorize,String networkType);

	byte[] compress(String str) throws Exception;

	
	void changeAmazonOfferDetails(User user, List<OfferDetails> offerDetails,
			boolean priority);

	void freshUserBonusOffer(List<OfferDetails> offerDetails, User user);

	boolean getAmazonSuspectFlage(User user, OfferDetails offerDetails);

	List<OfferDetails> getShareOffers(User user, boolean isCategorize,
			String networkType);

	void userTargetAmountBase(OffersDtoV4 offerDto, User user);
	
	void offerStartedDtoUpdateIdCheck(List<OffersStartedDto> offerStartedDto,List<String> update_id);
	
	void offerShoppingDtoUpdateIdCheck(List<ShoppingDetailsDto> shoppingDetailsDtos,List<String> update_id);
	
	OffersDtoV2 offersUpdateIdCheck(OffersDtoV2 offersDtoV2s,List<String> update_id);
	
	BreakingAlertDto breakingAlertUpdateIdCheck(BreakingAlertDto breakingAlertDto,String update_id);
	
	PopUpAlertDto popUpAlertUpdateIdCheck(PopUpAlertDto popUpAlertDto,String update_id);

}
