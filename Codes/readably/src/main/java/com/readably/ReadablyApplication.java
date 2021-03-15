package com.readably;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.readably.model.User;
import com.readably.config.SecurityConfig;
import com.readably.model.Role;
import com.readably.model.UserRole;
import com.readably.service.UserService;

@SpringBootApplication
public class ReadablyApplication implements CommandLineRunner{

	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(ReadablyApplication.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {
		User user1 = new User();
		user1.setName("John");
		user1.setSurname("Adams");
		user1.setUsername("j");
		user1.setPassword(SecurityConfig.passwordEncoder("p"));
		user1.setEmail("JAdams@gmail.com");
		Set<UserRole> userRoles = new HashSet<>();
		Role role1= new Role();
		role1.setRoleId(1);
		role1.setName("ROLE_USER");
		userRoles.add(new UserRole(user1, role1));
		
		userService.createUser(user1, userRoles);
	}
}
