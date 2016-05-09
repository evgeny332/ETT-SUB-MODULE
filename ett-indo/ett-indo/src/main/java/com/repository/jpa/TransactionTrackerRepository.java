package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.entity.TransactionTracker;


@Repository("transactionTrackerRepository")
public interface TransactionTrackerRepository extends JpaRepository<TransactionTracker,String> {
   TransactionTracker findById(String id);
   
   List<TransactionTracker> findByIdIn(List<String> id);
   
   @Modifying
   @Transactional
   @Query("delete from TransactionTracker t where t.ettId = ?1")
   void deleteTransactionTrackerByEttId(Long ettId);
}