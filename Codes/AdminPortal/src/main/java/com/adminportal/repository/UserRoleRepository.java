package com.adminportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adminportal.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{

}
