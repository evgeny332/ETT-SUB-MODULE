package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.Edr;
import com.domain.entity.User;


@Repository("edrRepository")
public interface EdrRepository extends JpaRepository<Edr,String> {

	List<Edr> findByEttId(Long ettId);
	
	@Query("select u from Edr u where u.id in (?1) and u.deviceIdFlage=true order by clickedTime desc")
	List<Edr> findOneDeviceIdFlage(String id);
    
}