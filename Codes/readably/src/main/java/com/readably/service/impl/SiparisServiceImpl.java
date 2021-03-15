package com.readably.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readably.model.Siparis;
import com.readably.model.User;
import com.readably.model.cartItems;
import com.readably.repository.CartRepository;
import com.readably.repository.ProductRepository;
import com.readably.repository.SiparisRepository;
import com.readably.repository.UserRepository;
import com.readably.service.SiparisService;

@Service
public class SiparisServiceImpl implements SiparisService {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private SiparisRepository siparisRepository;
	@Override
	public List<Siparis> findByUser(User user) {
		
		return null;
	}
	@Override
	public Siparis findById(Long id) {
		
		return siparisRepository.findByid(id);
	}

	@Override
	public void saveSiparis(User user, Siparis siparis) {
		siparisRepository.save(siparis);
		for(cartItems item : user.getCart()) {
			item.setUser(null);
			item.setSiparis(siparis);
			item.setTotal();
			cartRepository.save(item);
			siparis.getCartitems().add(item);
		}
		
		siparis.setUser(user);

		siparis.setDate();
		
		siparis.setGrandTotal(user.getGrandTotal());
		
		siparis.setCartTotal(user.getCartTotal());
		
		user.setGrandTotal(3);
		
		user.setCartTotal(0);
		
		siparisRepository.save(siparis);

		
		user.getSiparis().add(siparis);
		
		user.setBookCount(0);
		
		userRepository.save(user);
		
	}

	@Override
	public void deleteSiparis(Siparis siparis) {
		
		List<cartItems> silinecek=siparis.getCartitems();
		
		siparis.setCartitems(null);
		
		System.out.println("hopala");
		
		for(cartItems item:silinecek) {
			System.out.println("caritem silindi");
			
			item.setProduct(null);
			
			item.setUser(null);
			
			cartRepository.delete(item);
		}
		System.out.println("for loop bitti");
		User user=userRepository.findByUsername(siparis.getUser().getUsername());
		
		
		
		for(Siparis user_siparis	:	user.getSiparis()){
			
			if(user_siparis.getId().equals(siparis.getId())) {
			
				user.getSiparis().remove(user_siparis);
				userRepository.save(user);
				System.out.println(user.getName());
				System.out.println(user_siparis.getId());
				break;
			}
		}
		System.out.println("fordan ciktik");
		
		siparis.setUser(null);
		
		siparisRepository.delete(siparis);
		
		System.out.println("siparis deleted");
		
	}

	@Override
	public List<Siparis> listAll() {
		// TODO Auto-generated method stub
		return siparisRepository.findAll();
	}

}
