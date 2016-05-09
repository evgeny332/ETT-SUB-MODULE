package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.InviteeMsisdn;

@Repository("inviteeMsisdnRepository")
public interface InviteeMsisdnRepository extends JpaRepository<InviteeMsisdn, Long> {

	@Query("select i.msisdn from InviteeMsisdn i where i.ettId = ?1")
	String findMsisdnByEttId(long ettId);
	
	InviteeMsisdn findByEttId(long ettId);
}
