package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.UserGameSummary;


@Repository("userGameSummaryRepository")
public interface UserGameSummaryRepository extends JpaRepository<UserGameSummary,Long> {
   
}
