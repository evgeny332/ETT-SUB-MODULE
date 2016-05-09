package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.BreakingAlert;
import com.domain.entity.EttTabDetails;
import com.domain.entity.EttVideos;



@Repository("ettVideosRepository")
public interface EttVideosRepository extends JpaRepository<EttVideos,Long> {

	@Query("select distinct(u.playlist) from EttVideos u where u.status in (true) order by u.priority")
	List <String>  findPlayList();
	
	@Query("select u from EttVideos u where u.playlist in(?1) and u.status in (true)")
	List <EttVideos>  findByPlayList(String playList);
	
}
