package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.RedeemAmountConfig;


@Repository("redeemAmountConfigRepository")
public interface RedeemAmountConfigRepository extends JpaRepository<RedeemAmountConfig,String> {
	
	List <RedeemAmountConfig> findAll();
	
	@Query("select u from RedeemAmountConfig u where type='GIFT'")
	RedeemAmountConfig findGift();
	
}