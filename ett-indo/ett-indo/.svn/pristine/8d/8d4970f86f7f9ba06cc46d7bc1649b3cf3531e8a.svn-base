package com.repository.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.entity.TodaysOffers;


@Repository("todaysOffersRepository")
public interface TodaysOffersRepository extends JpaRepository<TodaysOffers,Long> {
	List<TodaysOffers> findByEttId(Long ettId);
	
	List<TodaysOffers> findByEttIdAndCreatedTimeGreaterThanAndOfferDetails_Status(Long ettId, Date date, Boolean status);
	//List<TodaysOffers> findByEttIdAndCreatedTimeGreaterThanAndOfferDetails_StatusOrderByOfferDetailsNew_Priority(Long ettId, Date date, Boolean status);
	
	@Transactional
	@Modifying
	@Query("delete from TodaysOffers u where u.ettId in(?1)")
	void deleteEttIdOfferId(Long ettId);
}