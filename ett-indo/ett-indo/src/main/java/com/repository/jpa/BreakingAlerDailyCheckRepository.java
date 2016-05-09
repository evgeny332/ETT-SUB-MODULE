package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.BreakingAlerDailyCheck;

@Repository("breakingAlerDailyCheckRepository")
public interface BreakingAlerDailyCheckRepository extends JpaRepository<BreakingAlerDailyCheck, Long>{
	
	BreakingAlerDailyCheck findByEttId(Long ettId);
	
	@Query("select u from BreakingAlerDailyCheck u where u.ettId= ?1 order by createdTime desc limit 1")
	List<BreakingAlerDailyCheck> findByettIdBreakingAlerDailyCheck(Long ettId);

}
