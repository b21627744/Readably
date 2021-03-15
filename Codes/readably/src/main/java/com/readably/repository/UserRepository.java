package com.readably.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.readably.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
