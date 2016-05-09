package com.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.entity.ShoppingDetails;


@Repository("shoppingDetailsRepository")
public interface ShoppingDetailsRepository extends JpaRepository<ShoppingDetails,Long> {

	@Query("select u from ShoppingDetails u where u.status in (?1)")
	List<ShoppingDetails> findByStatus(int status);
    
}