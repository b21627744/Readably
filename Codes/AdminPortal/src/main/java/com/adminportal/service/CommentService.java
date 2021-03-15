package com.adminportal.service;

import java.util.List;

import com.adminportal.model.Comment;
import com.adminportal.model.Product;
import com.adminportal.model.User;
import com.adminportal.model.cartItems;


public interface CommentService {
	
	List<Comment> findByUser(User user);
	
	List<Comment> findByProduct(Product product);
	
	void saveComment(User user,Product product,Comment comment);
	
	void deleteComment(Long id);
	
	List<Comment> listAll();
	
	void updateComment(Comment comment);
	
}
