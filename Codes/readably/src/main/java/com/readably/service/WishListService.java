package com.readably.service;

import java.util.List;

import com.readably.model.Product;
import com.readably.model.User;
import com.readably.model.WishlistItems;

public interface WishListService {
	List<WishlistItems> findByUser(User user);
	
	void saveWishList(User user,Product product,WishlistItems item);
	
	void deletewishlistitem(Long id);
	
}
