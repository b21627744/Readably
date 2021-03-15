package com.adminportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.model.UserRole;
import com.adminportal.repository.UserRoleRepository;
import com.adminportal.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	private UserRoleRepository userRoleRepository;
	
	public UserRole getUserRole(Long id) {
		return userRoleRepository.findById(id).get();
	}

	@Override
	public List<UserRole> listAll() {
		return userRoleRepository.findAll();
	}

	@Override
	public void deleteUserRole(Long id) {
		userRoleRepository.deleteById(id);
	}
}
