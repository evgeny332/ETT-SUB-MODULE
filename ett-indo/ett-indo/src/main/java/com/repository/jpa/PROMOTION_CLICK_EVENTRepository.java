package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.PROMOTION_CLICK_EVENT;


@Repository("PROMOTION_CLICK_EVENTRepository")
public interface PROMOTION_CLICK_EVENTRepository extends JpaRepository<PROMOTION_CLICK_EVENT,String> {

	@Query("select u from PROMOTION_CLICK_EVENT u where u.click_id in(?1)")
	List<PROMOTION_CLICK_EVENT> findByClick_idPromotion(List<String> click_id);
    
}