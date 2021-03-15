package com.readably.service;

import java.util.List;
import java.util.Set;

import com.readably.model.User;
import com.readably.model.UserRole;
import com.readably.model.cartItems;
import com.readably.model.WishlistItems;


public interface UserService {
	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	User save(User user);
	
	List<User> listAll();
	
	User get(Long id);
	
	void delete(Long id);
	
	User findByUserName(String name);							//find user by name
	
	List<cartItems> findMyCarts(User user);						//returns all carts of user
	
	void saveCardItemToUser(User user,cartItems cartitem);		//attaches the given card to user
	
	void deletecartfromUser(User user,cartItems cartitem);		//delete cart from user and cartRepository
}
