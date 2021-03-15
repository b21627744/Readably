package com.readably.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.readably.model.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByname(String name);
}
