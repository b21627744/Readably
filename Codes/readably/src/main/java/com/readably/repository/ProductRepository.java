package com.readably.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.readably.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}

