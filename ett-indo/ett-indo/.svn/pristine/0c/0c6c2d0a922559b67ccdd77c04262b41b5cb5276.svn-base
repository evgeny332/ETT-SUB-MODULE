package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.entity.InstalledAppsFirstTime;

@Repository("installedAppsFirstTimeRepository")
public interface InstalledAppsFirstTimeRepository extends JpaRepository<InstalledAppsFirstTime,String> {
	
	List<InstalledAppsFirstTime> findByEttId(Long ettId);

	@Query("select i.appKey from InstalledAppsFirstTime i where i.ettId = ?1")
	List<String> findAppkeyByEttId(Long ettId);
	
	   @Modifying
	   @Transactional
	   @Query("delete from InstalledAppsFirstTime t where t.ettId = ?1")
	   void deleteIAFTRByEttId(Long ettId);
}
