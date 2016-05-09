package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.BreakingAlert;



@Repository("breakingAlertRepository")
public interface BreakingAlertRepository extends JpaRepository<BreakingAlert,Long> {
	
	@Query("select u from BreakingAlert u where u.status in(?1) and u.id in(?2)")
	List<BreakingAlert> findById(boolean status,long id);	

}
