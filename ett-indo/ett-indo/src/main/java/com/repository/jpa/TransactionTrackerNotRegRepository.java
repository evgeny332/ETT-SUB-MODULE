package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.TransactionTracker;
import com.domain.entity.TransactionTrackerNotReg;


@Repository("transactionTrackerNotRegRepository")
public interface TransactionTrackerNotRegRepository extends JpaRepository<TransactionTrackerNotReg,String> {
   TransactionTracker findById(String id);
   
   List<TransactionTracker> findByIdIn(List<String> id);
}