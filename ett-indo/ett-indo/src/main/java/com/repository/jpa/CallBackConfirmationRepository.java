package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.entity.CallBackConfirmation;

/**
 * @author ankur
 */

@Repository("callBackConfirmationRepository")
public interface CallBackConfirmationRepository extends JpaRepository<CallBackConfirmation, Long> {
	List<CallBackConfirmation> findByIdIn(List<String> ids);
	List<CallBackConfirmation> findByEttId(Long ettId);
	
	@Modifying
	@Transactional
	@Query("delete from CallBackConfirmation c where c.ettId = ?1")
	void deleteCallBackConfirmationByEttId(Long ettId);
	
	@Query("select u from CallBackConfirmation u where u.id in (?1) and u.deviceIdFlage=true")
	List<CallBackConfirmation>  findByIdDeviceIdFlage(String id);
}
