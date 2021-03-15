package com.adminportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adminportal.model.User;
import com.adminportal.model.WishlistItems;

public interface WishListRepository  extends JpaRepository<WishlistItems, Long> { 
	
	List<WishlistItems> findByUser(User user);

	

}
