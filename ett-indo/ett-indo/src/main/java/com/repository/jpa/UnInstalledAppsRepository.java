package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.UnInstalledApps;




@Repository("unInstalledAppsRepository")
public interface UnInstalledAppsRepository extends JpaRepository<UnInstalledApps,String> {
   List<UnInstalledApps> findByIdIn(List<String> id);
}