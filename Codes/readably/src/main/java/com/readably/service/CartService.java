package com.readably.service;

import java.util.List;
import java.util.Set;

import com.readably.model.Product;
import com.readably.model.User;
import com.readably.model.UserRole;
import com.readably.model.cartItems;

public interface CartService {
	
	List<cartItems> findByUser(User user);
	
	cartItems saveNewCartItem(cartItems cartItem,User user,Product product,int quantity);
	
	cartItems saveRegisteredCartItem(cartItems cartItem,User user,Product product);
	
	List<cartItems> listAll();
	
	cartItems get(Long id);
	
	void delete(Long id);
	
	
	
	
}
