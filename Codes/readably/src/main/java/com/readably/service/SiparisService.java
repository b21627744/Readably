package com.readably.service;

import java.util.List;

import com.readably.model.Siparis;
import com.readably.model.User;

public interface SiparisService {
	
	List<Siparis> findByUser(User user);

	void saveSiparis(User user,Siparis siparis);
	
	void deleteSiparis(Siparis siparis);
	
	List<Siparis> listAll();
	
	public Siparis findById(Long ID) ;
}
