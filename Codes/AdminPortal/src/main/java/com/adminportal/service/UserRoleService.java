package com.adminportal.service;

import java.util.List;

import com.adminportal.model.UserRole;

public interface UserRoleService {

	UserRole getUserRole(Long id);
	
	List<UserRole> listAll();
	
	void deleteUserRole(Long id);
}
