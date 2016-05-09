package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.Edr;
import com.domain.entity.User;
import com.domain.entity.UserTargetAmountBase;


@Repository("userTargetAmountBaseRepository")
public interface UserTargetAmountBaseRepository extends JpaRepository<UserTargetAmountBase,String> {

	UserTargetAmountBase findByEttId(Long ettId);
	
	
}