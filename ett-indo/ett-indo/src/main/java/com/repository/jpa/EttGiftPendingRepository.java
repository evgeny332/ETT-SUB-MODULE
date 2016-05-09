package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.entity.EttGiftPending;

/**
 * @author ankur
 */
@Repository("ettGiftPendingRepository")
public interface EttGiftPendingRepository extends JpaRepository<EttGiftPending,Long> {
  
   
	@Query("select u from EttGiftPending u where msisdn=? and status=?")
	List<EttGiftPending> findByMsisdnInStatus(String msisdn,Boolean status);
	
	@Transactional
	@Modifying
	@Query("update EttGiftPending u set status = 1 where u.msisdn in(?1)")
	void updateStatus(String msisdn);
}