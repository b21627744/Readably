package com.adminportal.service;

import java.util.List;

import com.adminportal.model.Product;
import com.adminportal.model.User;
import com.adminportal.model.WishlistItems;
import com.adminportal.model.cartItems;

public interface WishListService {
	
	List<WishlistItems> listAll();
	
	List<WishlistItems> findByUser(User user);
	
	void saveWishList(User user,Product product,WishlistItems item);
	
	void deletewishlistitem(Long id);
	
}
