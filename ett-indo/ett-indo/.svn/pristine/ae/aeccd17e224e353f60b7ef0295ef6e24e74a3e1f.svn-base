package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.SchedulePush;

/**
 * @author ankit jain
 */
@Repository("schedulePushRepository")
public interface SchedulePushRepository extends JpaRepository<SchedulePush,Long> {
	SchedulePush findByEttId(Long ettId);
 
}
