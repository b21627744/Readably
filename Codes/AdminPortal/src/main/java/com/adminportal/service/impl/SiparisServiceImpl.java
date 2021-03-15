package com.adminportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.model.Siparis;
import com.adminportal.repository.SiparisRepository;
import com.adminportal.repository.UserRepository;
import com.adminportal.service.SiparisService;

@Service
public class SiparisServiceImpl implements SiparisService {

	@Autowired
	private SiparisRepository siparisRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Siparis findById(Long id) {
		return siparisRepository.findById(id).get();
	}

	@Override
	public List<Siparis> listAll() {
		// TODO Auto-generated method stub
		return siparisRepository.findAll();
	}
	
	@Override
	public void save(Siparis siparis) {
		siparisRepository.save(siparis);
	}
	
	

	@Override
	public void deleteSiparis(Long id) {
		siparisRepository.deleteById(id);
		
		
	}
	

}
