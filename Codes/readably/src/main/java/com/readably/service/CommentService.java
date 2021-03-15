package com.readably.service;

import java.util.List;

import com.readably.model.Comment;
import com.readably.model.Product;
import com.readably.model.User;
import com.readably.model.cartItems;


public interface CommentService {
	
	List<Comment> findByUser(User user);
	
	List<Comment> findByProduct(Product product);
	
	void saveComment(User user,Product product,Comment comment);
	
	void deleteComment(Long id);
	
	List<Comment> listAll();
	
	void updateComment(Comment comment);
	
}
