package com.readably.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.readably.model.cartItems;
import com.readably.model.Product;
import com.readably.model.User;
import com.readably.model.WishlistItems;
import com.readably.service.CartService;
import com.readably.service.ProductService;
import com.readably.service.UserService;
import com.readably.service.WishListService;

@SuppressWarnings("serial")
@Controller
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private WishListService wishListService;
	
	
	@RequestMapping("/cart")

	public String Cart(@ModelAttribute("searcheditem") Product searcheditem,HttpSession session,Principal principal) {
		init(principal,session);
		session.setAttribute("carttayok", "");
		return "cart";
	}
	

	@RequestMapping("/mywishlist")
	public String MyWishlist(@ModelAttribute("searcheditem") Product searcheditem,HttpSession session,Principal principal) {
		init(principal,session);
		return "wishlist";
		
	}
	
	@RequestMapping(value="/quickview/book/{id}",  method = RequestMethod.GET)
	public String quickView(@PathVariable("id") Long id, Model model, @ModelAttribute("searcheditem") Product searcheditem, HttpSession session,Principal principal) {
		
		init(principal,session);
		
	
		System.out.println("skjnxla");
		
		return "redirect:/single-product2";
		
	}
	
	@RequestMapping("/cart/buy/{id}")
	public String buy(

		@PathVariable("id") Long id, Model model, HttpSession session,@ModelAttribute("searcheditem") Product searcheditem,Principal principal) {
		
		
		init(principal,session);
		
		
		User entered_user=userService.findByUserName(principal.getName());
		entered_user.setBookCount(entered_user.getBookCount()+1);
	
		if(entered_user.getCart().isEmpty()) {
			
			cartItems cartitem=new cartItems(productService.getProduct((long) id),1);
			//entered_user.setCartTotal(entered_user.getCartTotal() + cartitem.getProduct().getOurPrice());
			
			cartService.saveNewCartItem(cartitem, entered_user, productService.getProduct((long) id),1);
			
		}

		else {
			int index = isExists(id, entered_user.getCart());

			if(index == -1) {
				cartItems cartitem2=new cartItems(productService.getProduct((long) id),1);
				cartService.saveNewCartItem(cartitem2, entered_user, productService.getProduct((long) id),1);
			} else {
				
				int quantity = entered_user.getCart().get(index).getQuantity() + 1;
				entered_user.getCart().get(index).setQuantity(quantity);
				entered_user.setGrandTotal(entered_user.getGrandTotal() + entered_user.getCart().get(index).getProduct().getOurPrice());	
				entered_user.setCartTotal(entered_user.getCartTotal() + entered_user.getCart().get(index).getProduct().getOurPrice());
				cartService.saveRegisteredCartItem(entered_user.getCart().get(index),entered_user,productService.getProduct((long) id));
			}
			
		}
		
		return "redirect:/cart";
		
	}
	
	@RequestMapping("/cart/delete/{id}")
	public String DeletefromCart(

		@PathVariable("id") Long id, Model model, HttpSession session,@ModelAttribute("searcheditem") Product searcheditem,Principal principal) {
		
		init(principal,session);
		
		
		User entered_user=userService.findByUserName(principal.getName());
		entered_user.setBookCount(entered_user.getBookCount()-1);
		int index = isExists(id, entered_user.getCart());
		if(index != -1) {
			
			if(entered_user.getCart().get(index).getQuantity()==1) {
				
				long tempid=entered_user.getCart().get(index).getId();
				entered_user.setGrandTotal(entered_user.getGrandTotal() - entered_user.getCart().get(index).getProduct().getOurPrice());	
				entered_user.setCartTotal(entered_user.getCartTotal() - entered_user.getCart().get(index).getProduct().getOurPrice());
				entered_user.getCart().remove(entered_user.getCart().get(index));
				userService.save(entered_user);
				cartService.delete(tempid);
				
				
			}
			else {
				int quantity = entered_user.getCart().get(index).getQuantity() - 1;
				entered_user.getCart().get(index).setQuantity(quantity);
				entered_user.setGrandTotal(entered_user.getGrandTotal() - entered_user.getCart().get(index).getProduct().getOurPrice());	
				entered_user.setCartTotal(entered_user.getCartTotal() - entered_user.getCart().get(index).getProduct().getOurPrice());
				cartService.saveRegisteredCartItem(entered_user.getCart().get(index),entered_user,productService.getProduct((long) id));
			}
		}
	
		
		return "redirect:/cart";
	}
	
	private int isExists(Long id, List<cartItems> cart) {
		for (int i=0; i< cart.size() ; i++) {
			if(cart.get(i).getProduct().getId() == id) {
				return i;
			}
		}
		return -1;
	}
	@RequestMapping("/wishlist/add/{id}")
	public String AddtoWishlist(

		@PathVariable("id") Long id, Model model, HttpSession session,@ModelAttribute("searcheditem") Product searcheditem,Principal principal) {
		
		
		init(principal,session);
		
		User entered_user=userService.findByUserName(principal.getName());
		
		if(entered_user.getWishlist().isEmpty()) {
		
			WishlistItems wishlistitem=new WishlistItems(productService.getProduct((long) id));
			
			wishListService.saveWishList(entered_user, productService.getProduct((long) id), wishlistitem);
			
		}
		else {
			int index = isExists2(id, entered_user.getWishlist());

			if(index == -1) {
				WishlistItems wishlistitem2=new WishlistItems(productService.getProduct((long) id));
				wishListService.saveWishList(entered_user, productService.getProduct((long) id), wishlistitem2);
			} else {
			
			}
		}

		return "redirect:/mywishlist";
	}
	
	@RequestMapping("/wishlist/delete/{id}")
	public String DeletetoWishlist(

		@PathVariable("id") Long id, Model model, HttpSession session,@ModelAttribute("searcheditem") Product searcheditem,Principal principal) {
		
		
		init(principal,session);
		
		
		User entered_user=userService.findByUserName(principal.getName());
		int index = isExists2(id, entered_user.getWishlist());
		if(index != -1) {
		
			long tempid2=entered_user.getWishlist().get(index).getId();
			
			entered_user.getWishlist().remove(entered_user.getWishlist().get(index));
			userService.save(entered_user);
			wishListService.deletewishlistitem(tempid2);
		
			
		}
		
		return "redirect:/mywishlist";
	}
	
	private int isExists2(Long id, List<WishlistItems> wishlist) {
		for (int i=0; i< wishlist.size() ; i++) {
			if(wishlist.get(i).getProduct().getId() == id) {
				return i;
			}
		}
		return -1;
	}
	
	public void init(Principal principal,HttpSession session) {
		 if(principal!=null) {
	    		
	            User entered_user=userService.findByUserName(principal.getName());
	            
	            session.setAttribute("user", entered_user);
	           
	            session.setAttribute("cartItems", entered_user.getCart());
	            
	            session.setAttribute("WishlistItems", entered_user.getWishlist());
	            
	            session.setAttribute("Orderitems", entered_user.getSiparis());
	        }
	}
	

	
}
