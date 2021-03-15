package com.readably.repository;

import com.readably.model.User;
import com.readably.model.cartItems;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;



public interface CartRepository extends JpaRepository<cartItems, Long> {

	List<cartItems> findByUser(User user);
	

}
