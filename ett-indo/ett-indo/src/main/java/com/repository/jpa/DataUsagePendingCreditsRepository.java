package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.DataUsagePendingCredits;
import com.domain.entity.OffersStarted;

@Repository("dataUsagePendingCreditsRepository")
public interface DataUsagePendingCreditsRepository extends JpaRepository<DataUsagePendingCredits, Long>{
	
	DataUsagePendingCredits findById(Long id);
	
	@Query("select u from DataUsagePendingCredits u where ettId in(?1) and eligibleStatus=1")
	List<DataUsagePendingCredits> findByEttId(Long ettId);
	
	
	//List<DataUsagePendingCredits> findById(Long id);
	
}
