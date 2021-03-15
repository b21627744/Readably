package com.adminportal.service;

import java.util.List;
import java.util.Set;

import com.adminportal.model.Product;
import com.adminportal.model.User;
import com.adminportal.model.UserRole;


public interface UserService {
	
	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	List<User> listAll();
	
	public User findById(Long id);
	
	void deleteUser(Long id);
}
