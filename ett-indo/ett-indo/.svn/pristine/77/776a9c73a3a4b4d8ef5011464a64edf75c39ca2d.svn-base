package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.OffersStarted;


@Repository("offersStartedRepository")
public interface OffersStartedRepository extends JpaRepository<OffersStarted,String> {

	@Query("select u from OffersStarted u where ettId in(?1) and status=true")
	List<OffersStarted> findByEttId(Long ettId);
    
}