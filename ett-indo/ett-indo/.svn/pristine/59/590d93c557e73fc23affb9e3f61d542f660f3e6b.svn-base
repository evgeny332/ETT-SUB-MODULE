package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.OfferDetailsNew;


@Repository("offerDetailsNewRepository")
public interface OfferDetailsNewRepository extends JpaRepository<OfferDetailsNew,Long> {

	OfferDetailsNew findByOfferId(Long OfferId);

	@Query("select u from OfferDetailsNew u where u.offerId in (?1) order by offerAmount desc, priority asc")	
	List<OfferDetailsNew> findByOfferIdIn(List<Long> OfferIds);

	@Query("select u from OfferDetailsNew u where u.status = ?1 and category is NULL order by offerAmount desc, priority asc")
	List<OfferDetailsNew> findByStatus(Boolean status);	


	@Query("select u from OfferDetailsNew u where u.status = ?1 and u.category in(?2)  order by offerAmount desc, priority asc")
	List<OfferDetailsNew> findByStatusAndCategory(Boolean status, List<String> category);


	OfferDetailsNew findByAppKey(String appKey);    
	
	
}

