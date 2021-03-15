package com.adminportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adminportal.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
