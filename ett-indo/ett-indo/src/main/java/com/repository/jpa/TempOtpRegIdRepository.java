package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.TempOtpRegId;

@Repository("tempOtpRegIdRepository")
public interface TempOtpRegIdRepository extends JpaRepository<TempOtpRegId,Long>{
	
	@Query("select u from TempOtpRegId u where u.ettId=?1 and u.status=1")
	List<TempOtpRegId> findByettId_status(Long ettId);

}
