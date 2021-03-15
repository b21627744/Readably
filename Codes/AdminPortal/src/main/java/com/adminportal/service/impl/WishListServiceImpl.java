package com.adminportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.model.Product;
import com.adminportal.model.User;
import com.adminportal.model.WishlistItems;
import com.adminportal.model.cartItems;
import com.adminportal.repository.UserRepository;
import com.adminportal.repository.WishListRepository;
import com.adminportal.service.WishListService;
import com.adminportal.service.UserService;

@Service
public class WishListServiceImpl implements WishListService {
	@Autowired
	private WishListRepository wishlistRepository;
		
	@Autowired
	private UserRepository userRepository;
	

	@Override
	public List<WishlistItems> findByUser(User user) {
		
	        return wishlistRepository.findByUser(user);
	    
	}
	@Override
	public List<WishlistItems> listAll() {
		return wishlistRepository.findAll();
	}

	@Override
	public void saveWishList(User user,Product product,WishlistItems item) {
		item.setProduct(product);
		item.setUser(user);
		wishlistRepository.save(item);
		user.getWishlist().add(item);
		userRepository.save(user);
	}
	@Override
	public void deletewishlistitem(Long id) {
		wishlistRepository.deleteById(id);
		
	}
	

}
