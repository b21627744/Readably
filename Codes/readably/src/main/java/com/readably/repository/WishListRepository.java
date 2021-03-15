package com.readably.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readably.model.User;
import com.readably.model.WishlistItems;

public interface WishListRepository  extends JpaRepository<WishlistItems, Long> { 
	
	List<WishlistItems> findByUser(User user);

	

}
