package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.CountryDetails;

@Repository("countryDetailsRepository")
public interface CountryDetailsRepository extends JpaRepository<CountryDetails,Long>{
	
	@Query("select u from CountryDetails u where u.iso_cc1= ?1")
	CountryDetails findByIso_cc1(String iso_cc1);
	//CountryDetails findByIs_cc2(String iso_cc2);
	

}
