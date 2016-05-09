package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.RedeemThreshold;


@Repository("redeemThresholdRepository")
public interface RedeemThresholdRepository extends JpaRepository<RedeemThreshold,Long> {
	RedeemThreshold findById(Integer Id);
    
}
