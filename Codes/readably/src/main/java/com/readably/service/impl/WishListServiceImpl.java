package com.readably.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readably.model.Product;
import com.readably.model.User;
import com.readably.model.WishlistItems;
import com.readably.model.cartItems;
import com.readably.repository.CartRepository;
import com.readably.repository.UserRepository;
import com.readably.repository.WishListRepository;
import com.readably.service.WishListService;
import com.readably.service.UserService;

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
