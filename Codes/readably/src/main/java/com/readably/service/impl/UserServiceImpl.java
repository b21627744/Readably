package com.readably.service.impl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readably.model.Product;
import com.readably.model.User;
import com.readably.model.UserRole;
import com.readably.model.cartItems;
import com.readably.repository.CartRepository;
import com.readably.repository.RoleRepository;
import com.readably.repository.UserRepository;
import com.readably.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public User createUser(User user, Set<UserRole> userRoles) {
		User localUser = userRepository.findByUsername(user.getUsername());

		if (localUser != null) {
			LOG.info("user {} already exists. Nothing will be done.", user.getUsername());
		} else {
			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());
			}

			user.getUserRoles().addAll(userRoles);
			
			

			localUser = userRepository.save(user);
		}

		return localUser;
	}
	
	
	

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
	
	
	
	public List<User> listAll(){
		return userRepository.findAll();
	}
	
	
	public User get(Long id) {
		return userRepository.findById(id).get();
	}
	
	
	public void delete(Long id) {
		for(cartItems item:userRepository.findById(id).get().getCart()) {
			cartRepository.delete(item);
		}
		userRepository.deleteById(id);
	}
	
	

	@Override
	public User findByUserName(String name) {
		
		for(User oneuser :userRepository.findAll()) {
			
			if(oneuser.getUsername().equals(name)){
								
				return oneuser;
			}	
			
		}
		return null;
	}

	@Override
	public List<cartItems> findMyCarts(User user) {
		
		return cartRepository.findByUser(user);
	}

	@Override
	public void saveCardItemToUser(User user, cartItems cartitem) {
		user.getCart().add(cartitem);	
		save(user);
	}
	@Override
	public void deletecartfromUser(User user,cartItems cartitem) {
		for(cartItems item:user.getCart()) {
			if(cartitem.equals(item)) {
				user.getCart().remove(item);
				
				userRepository.save(user);
				cartRepository.delete(item);
			}
		}
	}
	
	

}
