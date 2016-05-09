package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.FirstHitDay;

@Repository("firstHitDayRepository")
public interface FirstHitDayRepository extends JpaRepository<FirstHitDay, Long>{
	
	FirstHitDay findByEttId(Long ettId);
	
	@Query("select u from FirstHitDay u where u.ettId= ?1 order by createdTime desc limit 1")
	List<FirstHitDay> findByettIdToday(Long ettId);

}
