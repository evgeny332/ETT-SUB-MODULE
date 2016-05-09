package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.PopUpAlert;



@Repository("popUpAlertRepository")
public interface PopUpAlertRepository extends JpaRepository<PopUpAlert,Long> {
	
	@Query("select u from PopUpAlert u where u.id in (?1) and u.status in(?2)")
	PopUpAlert findById(long id,boolean status);	

}
