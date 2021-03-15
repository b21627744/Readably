package com.adminportal.repository;

import com.adminportal.model.User;
import com.adminportal.model.cartItems;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;



public interface CartRepository extends JpaRepository<cartItems, Long> {

	List<cartItems> findByUser(User user);
	

}
