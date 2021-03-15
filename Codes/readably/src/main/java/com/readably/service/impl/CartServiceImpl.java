package com.readably.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.readably.model.Product;
import com.readably.model.User;
import com.readably.model.UserRole;
import com.readably.model.cartItems;
import com.readably.repository.CartRepository;
import com.readably.repository.RoleRepository;
import com.readably.repository.UserRepository;
import com.readably.service.CartService;
import com.readably.service.UserService;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserRepository userRepository;
		
	
	
	

	@Override
	public List<cartItems> listAll() {
		return cartRepository.findAll();
	}

	@Override
	public cartItems get(Long id) {
		return cartRepository.findById(id).get();
	}

	@Override
	public void delete(Long id) {
		cartRepository.deleteById(id);
		
	}

	@Override
	public cartItems saveNewCartItem(cartItems cartItem,User user,Product product,int quantity) {
		
		cartItem.setUser(user);
		cartItem.setProduct(product);
		cartItem.setQuantity(quantity);
		cartRepository.save(cartItem);
		user.getCart().add(cartItem);
		user.setGrandTotal(user.getGrandTotal() + cartItem.getProduct().getOurPrice());	
		user.setCartTotal(user.getCartTotal() + cartItem.getProduct().getOurPrice());
		userRepository.save(user);
		return cartItem;
	}
	
	@Override
	public cartItems saveRegisteredCartItem(cartItems cartItem,User user,Product product) {
		//user.setCartTotal(user.getCartTotal() + cartItem.getProduct().getOurPrice());
		//user.setGrandTotal(user.getGrandTotal() + cartItem.getProduct().getOurPrice());	
		cartRepository.save(cartItem);
		userRepository.save(user);
		
		return cartItem;
	}

	@Override
	public List<cartItems> findByUser(User user) {
		
	        return cartRepository.findByUser(user);
	    
	}

	
	

	
	
	
}