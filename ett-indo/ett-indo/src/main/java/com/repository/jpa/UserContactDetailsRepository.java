package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.UserContactDetails;

@Repository("userContactDetailsRepository")
public interface UserContactDetailsRepository extends JpaRepository<UserContactDetails, Long> {

	List<UserContactDetails> findByEttId(Long ettId);
	
}
