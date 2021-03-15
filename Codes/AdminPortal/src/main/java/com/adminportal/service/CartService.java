package com.adminportal.service;

import java.util.List;
import java.util.Set;

import com.adminportal.model.Product;
import com.adminportal.model.User;
import com.adminportal.model.UserRole;
import com.adminportal.model.cartItems;

public interface CartService {
	
	List<cartItems> findByUser(User user);
	
	cartItems saveNewCartItem(cartItems cartItem,User user,Product product,int quantity);
	
	cartItems saveRegisteredCartItem(cartItems cartItem,User user,Product product);
	
	List<cartItems> listAll();
	
	cartItems get(Long id);
	
	void delete(Long id);
	
	
	
	
}
