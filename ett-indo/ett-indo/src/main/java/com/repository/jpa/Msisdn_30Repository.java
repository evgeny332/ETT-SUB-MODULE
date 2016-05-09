package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.entity.Msisdn_30;

/**
 * @author ankur
 */
@Repository("msisdn_30Repository")
public interface Msisdn_30Repository extends JpaRepository<Msisdn_30,Long> {
  
	@Query("select u from Msisdn_30 u where u.ettId= ?1 and u.status=?2")
    Msisdn_30 findByettIdStatusType(Long ettId,int status);
    
    @Transactional
	@Modifying
	@Query("update Msisdn_30 u set u.status= ?1 where u.ettId= ?2")
	void updateStatus(int status, Long ettLong);
}