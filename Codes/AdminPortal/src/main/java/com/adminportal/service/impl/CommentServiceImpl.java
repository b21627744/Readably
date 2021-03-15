package com.adminportal.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.adminportal.model.Comment;
import com.adminportal.model.Product;
import com.adminportal.model.User;
import com.adminportal.repository.CommentRepository;
import com.adminportal.repository.ProductRepository;
import com.adminportal.repository.UserRepository;
import com.adminportal.service.CommentService;


@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommentRepository commentRepository;

	@Override
	public List<Comment> findByUser(User user) {
		// TODO Auto-generated method stub
		return  commentRepository.findByUser(user);
	}

	@Override
	public List<Comment> findByProduct(Product product) {
		// TODO Auto-generated method stub
		return commentRepository.findByProduct(product);
	}
	
	@Override
	public void saveComment(User user, Product product, Comment comment) {
		commentRepository.save(comment);	
		
		for(Comment comment1 : commentRepository.findAll()) {
			if(comment.getDate().equals(comment1.getDate())) {
				product.getComment().add(comment1);
				user.getComment().add(comment1);
				productRepository.save(product);
				userRepository.save(user);
				
			}
		}
		
		
		
		
		
	}

	@Override
	public void deleteComment(Long id) {
		commentRepository.deleteById(id);
		
	}

	@Override
	public List<Comment> listAll() {
		// TODO Auto-generated method stub
		return commentRepository.findAll();
	}

	@Override
	public void updateComment(Comment comment) {
		commentRepository.save(comment);
		
	}
}
