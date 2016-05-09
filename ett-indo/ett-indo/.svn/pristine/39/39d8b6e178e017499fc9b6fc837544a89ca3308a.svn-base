package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.AppKeyMap;
import com.domain.entity.EttTabDetails;


@Repository("ettTabDetailsRepository")
public interface EttTabDetailsRepository extends JpaRepository<EttTabDetails,Long> {
 
	@Query("select u from EttTabDetails u where u.tabStatus in (true)")
	List <EttTabDetails>  findByStatus();
	
}