package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.InviteBonusMsisdn;
import com.domain.entity.OfferDetails;


@Repository("inviteBonusMsisdnRepository")
public interface InviteBonusMsisdnRepository extends JpaRepository<InviteBonusMsisdn,String> {

	InviteBonusMsisdn findByMsisdn(String msisdn);
	//InviteBonusMsisdn findByMsisdnAndStatus(String msisdn,int status);
	
}
