package com.adminportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.adminportal.model.Siparis;
import com.adminportal.model.User;

public interface SiparisRepository extends JpaRepository<Siparis, Long> {
	Siparis findByid(Long id);
	List<Siparis> findByUser(User user);
}
