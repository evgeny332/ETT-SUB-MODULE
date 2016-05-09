package com.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.UserGameCreditSummary;


@Repository("userGameCreditSummaryRepository")
public interface UserGameCreditSummaryRepository extends JpaRepository<UserGameCreditSummary,Long> {
	
	List<UserGameCreditSummary> findByEttIdOrderByIdDesc(Long ettId, Pageable pageable);    
}