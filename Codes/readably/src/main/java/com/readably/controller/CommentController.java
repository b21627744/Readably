package com.readably.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.readably.model.Product;
import com.readably.model.User;
import com.readably.service.CartService;
import com.readably.service.ProductService;
import com.readably.service.UserService;
import com.readably.service.WishListService;

@SuppressWarnings("serial")
@Controller
public class CommentController {
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private WishListService wishListService;
	
	/*
	@RequestMapping("/SubmitComment")
	public String CommentSubmit(@ModelAttribute("searcheditem") Product searcheditem,HttpSession session,Principal principal) {
		User entered_user=userService.findByUserName(principal.getName());
		
		session.setAttribute("WishlistItems", entered_user.getWishlist());
		
		return "wishlist";
		
	}
	*/
}
