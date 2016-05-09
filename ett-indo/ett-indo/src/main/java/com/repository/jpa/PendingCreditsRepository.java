package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.entity.PendingCredits;


@Repository("pendingCreditsRepository")
public interface PendingCreditsRepository extends JpaRepository<PendingCredits,String> {
 
	@Transactional
	@Modifying
	@Query("update PendingCredits u set u.ettId= ?1 where u.ettId= ?2")
	void updateEttId(Long ettLong, Long ettLong2);
}