package com.readably.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.readably.service.impl.UserSecurityService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Autowired
	private UserSecurityService userSecurityService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	private static final String[] PUBLIC_MATCHERS = {
			"/css/**",
			"/change-page/{i}",
			"/sort1",
			"/sort2",
			"/sort3",
			"/sort4",
			 "/js/**",
			 "/images/**",
			 "/",
			 "/Register",
			 "/search",
			 "/forgot-password",
			 "/forgotPassword",
			 "/forgotPassword2",
			 "/reset-password",
			 "/resetPassword",
			 "/resetPassword2",
			 "/shop-grid",
			 "/team",
			 "/about",
			 "/blog-details",
			 "/blog",
			 "/error404",
			 "/faq",
			 "/portfolio",
			 "/portfolio-details",
			 "/shop-grid/{category}",
			 "/shop-grid-category",
			 "/single-product/{id}",
			 "/single-product",
			 "/single-product2",
			 "/createAccount",
			 "/filterbyprice",
			 "/style.css",
			 "/login",
			 "/fonts/**"
			
	};
	
	// generate encrypted password 
	public static String passwordEncoder(String namePassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodePassword = encoder.encode(namePassword);
		return encodePassword;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests().
		/*	antMatchers("/**").*/
			antMatchers(PUBLIC_MATCHERS).
			permitAll().anyRequest().authenticated();

		http
			.csrf().disable().cors().disable()
			.formLogin().failureUrl("/login?error")
			.defaultSuccessUrl("/")
			.loginPage("/login").permitAll()
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
			.and()
			.rememberMe();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}

}
