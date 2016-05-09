package com.repository.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.DynamicTabDetails;

@Repository("dynamicTabDetailsRepository")
public interface DynamicTabDetailsRepository extends JpaRepository<DynamicTabDetails,Long>{

	@Query("select u from DynamicTabDetails u where u.tabStatus=?1 order by u.priority asc")
	List<DynamicTabDetails> findByTabStatusOrderByPriority(boolean tabStatus);
	
	List<DynamicTabDetails> findByTabStatus(boolean tabStatus);
	
}
