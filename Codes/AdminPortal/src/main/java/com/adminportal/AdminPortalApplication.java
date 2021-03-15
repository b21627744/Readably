package com.adminportal;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adminportal.model.User;
import com.adminportal.model.Role;
import com.adminportal.model.UserRole;
import com.adminportal.service.UserService;
import com.adminportal.config.SecurityConfig;

@SpringBootApplication
public class AdminPortalApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(AdminPortalApplication.class, args);
	}
	

	public void run(String... args)throws Exception{
		User user1 = new User();
		user1.setName("Remzi");
		user1.setSurname("Cicek");
		user1.setUsername("cicekremzi");
		user1.setPassword(SecurityConfig.passwordEncoder("admin"));
		user1.setEmail("admin@gmail.com");
		Set<UserRole> userRoles = new HashSet<>();
		Role role1= new Role();
		role1.setRoleId(0);
		role1.setName("ROLE_ADMIN");
		userRoles.add(new UserRole(user1, role1));

		userService.createUser(user1, userRoles);
	}

}
