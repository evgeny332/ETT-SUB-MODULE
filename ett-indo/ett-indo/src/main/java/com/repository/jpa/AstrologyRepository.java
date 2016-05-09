package com.repository.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.Astrology;

@Repository("astrologyRepository")
public interface AstrologyRepository extends JpaRepository<Astrology, Integer>{

	List<Astrology> findById(Integer id);
	
	@Query("select a from Astrology a where a.viewDateIST=?1")
	List<Astrology> findAllAstro(Date date);
}
