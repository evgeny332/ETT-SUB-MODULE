package com.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.UserBonusCreditSummary;


@Repository("userBonusCreditSummaryRepository")
public interface UserBonusCreditSummaryRepository extends JpaRepository<UserBonusCreditSummary,Long> {
	
	List<UserBonusCreditSummary> findByEttIdOrderByIdDesc(Long ettId, Pageable pageable);    
}