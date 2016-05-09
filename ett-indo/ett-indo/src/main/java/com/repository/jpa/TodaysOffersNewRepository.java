package com.repository.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.TodaysOffersNew;


@Repository("todaysOffersNewRepository")
public interface TodaysOffersNewRepository extends JpaRepository<TodaysOffersNew,Long> {
	List<TodaysOffersNew> findByEttId(Long ettId);
	
	List<TodaysOffersNew> findByEttIdAndCreatedTimeGreaterThanAndOfferDetailsNew_Status(Long ettId, Date date, Boolean status);
	//List<TodaysOffersNew> findByEttIdAndCreatedTimeGreaterThanAndOfferDetailsNew_StatusOrderByOfferDetailsNew_Priority(Long ettId, Date date, Boolean status);
	
}