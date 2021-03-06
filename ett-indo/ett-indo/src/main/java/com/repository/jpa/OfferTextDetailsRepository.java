package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.OfferTextDetails;

@Repository("offerTextDetailsRepository")
public interface OfferTextDetailsRepository extends JpaRepository<OfferTextDetails, String> {
	
	List<OfferTextDetails> findByIdIn(List<String> id);
	
	OfferTextDetails findByOfferId(long offerId);
	
	OfferTextDetails findById(String id);

}
