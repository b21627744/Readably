package com.readably.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.readably.model.Siparis;
import com.readably.model.User;

public interface SiparisRepository extends JpaRepository<Siparis, Long> {
	Siparis findByid(Long id);
	List<Siparis> findByUser(User user);
}
