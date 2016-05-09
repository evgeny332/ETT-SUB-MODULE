package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.OfferDetails;


@Repository("offerDetailsRepository")
public interface OfferDetailsRepository extends JpaRepository<OfferDetails,Long> {

	OfferDetails findByOfferId(Long OfferId);

	@Query("select u from OfferDetails u where u.offerId in (?1) and u.status in(true) order by priority asc, offerAmount desc")	
	List<OfferDetails> findByOfferIdInStatus(List<Long> OfferIds);

	
	@Query("select u from OfferDetails u where u.offerId in (?1) order by priority asc, offerAmount desc")	
	List<OfferDetails> findByOfferIdIn(List<Long> OfferIds);

	@Query("select u from OfferDetails u where u.status = ?1 and category is NULL order by priority asc, offerAmount desc")
	List<OfferDetails> findByStatus(Boolean status);	


	@Query("select u from OfferDetails u where u.status = ?1 and u.category in (?2)  order by priority asc, offerAmount desc")
	List<OfferDetails> findByStatusAndCategory(Boolean status, List<String> category);

	@Query("select u from OfferDetails u where u.status = ?1 and PayoutType=?2 order by priority asc, offerAmount desc")
	List<OfferDetails> findByStatusPayoutType(Boolean status,String PayoutType);	


	OfferDetails findByAppKey(String appKey);    
	
	
}
