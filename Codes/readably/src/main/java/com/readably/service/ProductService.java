package com.readably.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.readably.model.Product;
import com.readably.model.cartItems;
import com.readably.repository.CartRepository;
import com.readably.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	
	
	public List<Product> listAll(){
		return productRepository.findAll();
	}
	
	public void save (Product product) {
		productRepository.save(product);
	}
	
	public Product getProduct(Long id) {
		return productRepository.findById(id).get();
	}
	
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
	
	
}
