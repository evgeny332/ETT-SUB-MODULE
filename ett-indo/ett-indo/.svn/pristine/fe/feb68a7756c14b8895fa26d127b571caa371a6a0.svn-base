package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.entity.UserAccount;


@Repository("userAccountRepository")
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {
	UserAccount findByEttId(Long ettId);  
    
	@Transactional
	@Modifying
	@Query("update UserAccount u set u.ettId= ?1 where u.ettId= ?2")
	void updateEttId(Long ettLong, Long ettLong2);
}
