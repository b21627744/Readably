package com.adminportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adminportal.model.Comment;
import com.adminportal.model.Product;
import com.adminportal.model.User;




public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByUser(User user);
	List<Comment> findByProduct(Product product);
}
