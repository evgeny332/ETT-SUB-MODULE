package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.Rate;

/**
 * @author ankit jain
 */
@Repository("rateRepository")
public interface RateRepository extends JpaRepository<Rate,Long> {
	Rate findByEttId(Long ettId);
 
}
