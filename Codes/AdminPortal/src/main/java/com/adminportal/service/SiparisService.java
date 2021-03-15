package com.adminportal.service;

import java.util.List;

import com.adminportal.model.Siparis;
import com.adminportal.model.User;

public interface SiparisService {

	
	public Siparis findById(Long id) ;
	
	List<Siparis> listAll();
	
	void save(Siparis siparis);

	void deleteSiparis(Long id);


	
}
