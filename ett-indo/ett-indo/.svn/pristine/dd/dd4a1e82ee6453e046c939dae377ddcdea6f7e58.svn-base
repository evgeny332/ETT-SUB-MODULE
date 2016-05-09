package com.repository.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.entity.UserAccountSummary;


@Repository("userAccountSummaryRepository")
public interface UserAccountSummaryRepository extends JpaRepository<UserAccountSummary,Long> {
	
	List<UserAccountSummary> findByEttIdOrderByIdDesc(Long ettId, Pageable pageable); 
	
	//UserAccountSummary findByEttIdAndOfferId(Long ettId, Long offerId);
	
	List<UserAccountSummary> findByEttIdAndOfferId(Long ettId, Long offerId);
	List<UserAccountSummary> findByEttIdAndOfferId(List<Long> ettId, Long offerId);
	
	List<UserAccountSummary> findByEttIdAndOfferIdOrderByIdDesc(Long ettId, Long offerId, Pageable pageable);
	
	List<UserAccountSummary> findByOfferId(Long offerId);
	List<UserAccountSummary> findByEttId(Long ettId);
	
	@Query("select u from UserAccountSummary u where u.ettId=?1 and u.offerId=?2 and u.offerName=?3")
	UserAccountSummary findByEttIdAndOfferIdAndOfferName(Long ettId, Long offerId,String offerName);
	
	@Query("select u from UserAccountSummary u where u.ettId=?1 and u.offerId=?2 and u.remarks=?3")
	List<UserAccountSummary> findByEttIdAndOfferIdAndRemarks(Long ettId,Long offerId,String remarks);
	
	@Query("select u  from UserAccountSummary u where u.ettId=?1 and u.offerId=?2 and u.createdTime>=?3")
	 List<UserAccountSummary> getTodaysGift(Long ettId,Long offerId,Date date);
	
	@Transactional
	@Modifying
	@Query("update UserAccountSummary u set u.ettId= ?1 where u.ettId= ?2")
	void updateEttId(Long ettLong, Long ettLong2);
	
	@Transactional
	@Modifying
	@Query("delete from UserAccountSummary u where u.ettId= ?1 and u.offerId= ?2 and u.offerName= ?3")
	void deleteEttId_ref(Long ettId, Long offerId, String offerName);
	
	
	
}