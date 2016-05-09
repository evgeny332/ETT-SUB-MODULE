package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.PopUpSheduled;



@Repository("popUpSheduledRepository")
public interface PopUpSheduledRepository extends JpaRepository<PopUpSheduled,Long> {
	
	
	@Query("select u from PopUpSheduled u where u.ettId in (?1) and u.status in(?2)")
	List<PopUpSheduled> findByEttIdStatus(Long ettId,boolean status);	

}
