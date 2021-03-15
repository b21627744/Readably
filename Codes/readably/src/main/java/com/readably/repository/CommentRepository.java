package com.readably.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readably.model.Comment;
import com.readably.model.Product;
import com.readably.model.User;




public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByUser(User user);
	List<Comment> findByProduct(Product product);
}
