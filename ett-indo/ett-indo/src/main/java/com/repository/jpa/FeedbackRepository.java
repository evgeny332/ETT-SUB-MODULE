package com.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.entity.Feedback;

/**
 * @author ankit jain
 */
@Repository("feedbackRepository")
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
 
}
