package com.adminportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.adminportal.model.Product;
import com.adminportal.model.User;
import com.adminportal.model.UserRole;
import com.adminportal.model.cartItems;
import com.adminportal.repository.CartRepository;
import com.adminportal.repository.RoleRepository;
import com.adminportal.repository.UserRepository;
import com.adminportal.service.CartService;
import com.adminportal.service.UserService;

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