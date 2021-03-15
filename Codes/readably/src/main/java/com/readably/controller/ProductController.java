package com.readably.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.readably.model.Comment;
import com.readably.model.Product;
import com.readably.model.User;
import com.readably.model.UserValidator;
import com.readably.service.CommentService;
import com.readably.service.ProductService;
import com.readably.service.UserService;
import com.readably.Mail;
import com.readably.config.SecurityConfig;


@SuppressWarnings("serial")
@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private CommentService commentService;
	
	
	
	/*shows products in the productlist page*/
	@RequestMapping("/")
	public String AllProduct(Model model,HttpSession session,Principal principal) {
		init(principal,session);
		List<Product> allProduct = productService.listAll();
		model.addAttribute("listProducts",allProduct);
		Product searcheditem= new Product();
		model.addAttribute("searcheditem",searcheditem);
		Mail mail = new Mail();
		model.addAttribute("mail",mail);
		User userForResetPassword = new User();
		model.addAttribute("userForResetPassword",userForResetPassword);
		Comment comment = new Comment();
		model.addAttribute("comment",comment);
		
		if(principal!=null) {
	       	
	        User entered_user=userService.findByUserName(principal.getName());
	    	session.setAttribute("user", entered_user);
	    	session.setAttribute("cartItems", entered_user.getCart());
	    	session.setAttribute("WishlistItems", entered_user.getWishlist());
	        	
	    }
		
		
			
			List<Product> bestsellers = new ArrayList<Product>();
			List<Product> bestsellers2 = new ArrayList<Product>();
			
			List<Product> ratedmost = new ArrayList<Product>();
			List<Product> ratedmost2 = new ArrayList<Product>();
			
			for(Product product : productService.listAll()) {
				bestsellers.add(product);
				ratedmost.add(product);
			}
			
			
			Collections.sort(bestsellers, new Comparator<Product>() {
		        @Override
		        public int compare(Product o1, Product o2) {
		            return Double.compare(o1.getSellercount(), o2.getSellercount());
		        }
		    });
			
			if(bestsellers.size()>6) {
				for(int i =bestsellers.size()-1 ;i>bestsellers.size()-7; i--) {
					bestsellers2.add(bestsellers.get(i));
				
				}
			}
			else {
				for(int i =bestsellers.size()-1 ;i>-1; i--) {
					bestsellers2.add(bestsellers.get(i));			
				}
			}
			
			Collections.sort(ratedmost, new Comparator<Product>() {
		        @Override
		        public int compare(Product o1, Product o2) {
		            return Double.compare(o1.getRate(), o2.getRate());
		        }
		    });
			if(ratedmost.size()>6) {
				for(int i =ratedmost.size()-1 ;i>ratedmost.size()-7; i--) {
					ratedmost2.add(ratedmost.get(i));
				
				}
			}
			else {
				for(int i =ratedmost.size()-1 ;i>-1; i--) {
					ratedmost2.add(ratedmost.get(i));			
				}
			}
			
			session.setAttribute("ratedmost", ratedmost2);
			
			
			
			
			session.setAttribute("listProduct", allProduct);
			session.setAttribute("bestsellers2", bestsellers2);
		
		
			session.setAttribute("count", Math.ceil(allProduct.size()/9.0));
			session.setAttribute("selected",1);
		
		return "index";
	}
	
	@RequestMapping(value="/change-page/{i}",  method = RequestMethod.GET)
	public String ShopGridCategory(@PathVariable("i") int id,Model model, @ModelAttribute("searcheditem") Product searcheditem,HttpSession session,Principal principal) {
		init(principal,session);
		
		
		session.setAttribute("selected",id);
		return "redirect:/shop-grid-category";
	}
	
	@RequestMapping(value="/see-more",  method = RequestMethod.GET)
	public String seeMore( @ModelAttribute("searcheditem") Product searcheditem,HttpSession session,Principal principal) {
		init(principal,session);
		
		int my_session=(int) session.getAttribute("selected");
		Product product=(Product) session.getAttribute("searcheditem2");
		
		if(product.getComment().size() <= my_session*4) {
			session.setAttribute("selected",my_session);
		}
		else {
			session.setAttribute("selected",my_session+1);
		}
		
		
		session.setAttribute("rateyapamaz", "");
		return "redirect:/commentToSingleProduct";
	}
	
	
	@RequestMapping(value="/see-less",  method = RequestMethod.GET)
	public String seeLess( @ModelAttribute("searcheditem") Product searcheditem,HttpSession session,Principal principal) {
		init(principal,session);
		
		int my_session=(int) session.getAttribute("selected");
		Product product=(Product) session.getAttribute("searcheditem2");
		
		if(4<= (my_session-1)*4) {
			session.setAttribute("selected",my_session-1);
		}
		else {
			session.setAttribute("selected",my_session);
		}

		
		
		session.setAttribute("rateyapamaz", "");
		return "redirect:/commentToSingleProduct";
	}
	

	@RequestMapping(value="/commentToSingleProduct",  method = RequestMethod.GET)
	public String CommentlerdenSingleproducta( @ModelAttribute("searcheditem") Product searcheditem,HttpSession session,Principal principal,@ModelAttribute("comment") Comment comment) {
		init(principal,session);
		
		session.setAttribute("rateyapamaz", "");
		return "single-product2";
	}
	
	@RequestMapping(value="/sort1", method = RequestMethod.GET)
	public String sort1(@ModelAttribute("searcheditem") Product searcheditem,@ModelAttribute("userValidator") UserValidator userValidator, 
						  HttpServletRequest request, 
						  HttpSession session,
						  Principal principal
						  ) {
		List<Product> ourlist = (List<Product>) session.getAttribute("listProduct");
		session.removeAttribute("listProduct");
		Collections.sort(ourlist, new Comparator<Product>() {
	        @Override
	        
	        public int compare(Product o1, Product o2) {
	            return o2.getTitle().compareToIgnoreCase(o1.getTitle());
	        }
	    });
		List<Product> bestsellers2 = new ArrayList<Product>();
		for(int i =ourlist.size()-1 ;i>-1; i--) {
			bestsellers2.add(ourlist.get(i));			
		}
		System.out.println(bestsellers2.get(0));
		
		session.setAttribute("listProduct", bestsellers2);
		session.setAttribute("count", Math.ceil(bestsellers2.size()/9.0));
		session.setAttribute("selected",1);
		
		return "shop-grid";
	}
	@RequestMapping(value="/sort2", method = RequestMethod.GET)
	public String sort2(@ModelAttribute("searcheditem") Product searcheditem,@ModelAttribute("userValidator") UserValidator userValidator, 
						  HttpServletRequest request, 
						  HttpSession session,
						  Principal principal
						  ) {
		List<Product> ourlist = (List<Product>) session.getAttribute("listProduct");
		Collections.sort(ourlist, new Comparator<Product>() {
	        @Override
	        
	        public int compare(Product o1, Product o2) {
	            return Double.compare(o1.getOurPrice(), o2.getOurPrice());
	        }
	    });
		
		
		
		session.setAttribute("listProduct", ourlist);
		session.setAttribute("count", Math.ceil(ourlist.size()/9.0));
		session.setAttribute("selected",1);
		return "shop-grid";
	}
	@RequestMapping(value="/sort3", method = RequestMethod.GET)
	public String sort3(@ModelAttribute("searcheditem") Product searcheditem,@ModelAttribute("userValidator") UserValidator userValidator, 
						  HttpServletRequest request, 
						  HttpSession session,
						  Principal principal
						  ) {
		List<Product> ourlist = (List<Product>) session.getAttribute("listProduct");
		Collections.sort(ourlist, new Comparator<Product>() {
	        @Override
	        
	        public int compare(Product o1, Product o2) {
	            return Double.compare(o1.getOurPrice(), o2.getOurPrice());
	        }
	    });
		List<Product> bestsellers2 = new ArrayList<Product>();
		for(int i =ourlist.size()-1 ;i>-1; i--) {
			bestsellers2.add(ourlist.get(i));			
		}
		
		
		session.setAttribute("listProduct", bestsellers2);
		session.setAttribute("count", Math.ceil(bestsellers2.size()/9.0));
		session.setAttribute("selected",1);
		return "shop-grid";
	}
	@RequestMapping(value="/sort4", method = RequestMethod.GET)
	public String sort4(@ModelAttribute("searcheditem") Product searcheditem,@ModelAttribute("userValidator") UserValidator userValidator, 
						  HttpServletRequest request, 
						  HttpSession session,
						  Principal principal
						  ) {
		
		List<Product> ourlist = (List<Product>) session.getAttribute("listProduct");
		
		List<Product> bestsellers = new ArrayList<Product>();
	
		
		
		Collections.sort(ourlist, new Comparator<Product>() {
	        @Override
	        public int compare(Product o1, Product o2) {
	            return Double.compare(o1.getSellercount(), o2.getSellercount());
	        }
	    });
		
		
		for(int i =ourlist.size()-1 ;i>-1; i--) {
			bestsellers.add(ourlist.get(i));			
		}
		
		session.setAttribute("listProduct", bestsellers);
		session.setAttribute("count", Math.ceil(bestsellers.size()/9.0));
		session.setAttribute("selected",1);
		
		return "shop-grid";
	}
	
	
	@RequestMapping(value="/sendMail", method = RequestMethod.POST)
	public String sendMail(Model model,@ModelAttribute("mail") Mail mail,
			HttpSession session,@ModelAttribute("searcheditem") Product searcheditem,Principal principal) {
		init(principal,session);
			System.out.println(mail.getFirstname()+"\n");
			mail.sendMail(mail.getFirstname(),mail.getLastname(),mail.getMailAddress(),mail.getReview(), mail.getSubject());
			
			
			return "redirect:/";
	}
	@RequestMapping("/forgotPassword")
	public String forgotPassword(Model model, HttpSession session,@ModelAttribute("searcheditem") Product searcheditem, @ModelAttribute("mail") Mail mail,Principal principal) {
		init(principal,session);
		session.removeAttribute("myySessionAttribute");
		return "forgot-password";
	}
	
	@RequestMapping("/forgotPassword2")
	public String forgotPassword2(Model model, HttpSession session,@ModelAttribute("searcheditem") Product searcheditem,@ModelAttribute("mail") Mail mail,Principal principal) {
		init(principal,session);
		for(User user : userService.listAll()) {
			if(user.getUsername().equals(mail.getFirstname()) && user.getEmail().equals(mail.getMailAddress())) {
				
				session.setAttribute("myySessionAttribute", "Check your mailbox");
				mail.sendResetMail(mail.getMailAddress());
			}
			else {
				session.setAttribute("myySessionAttribute", "Invalid Username or email");
			}
		}
		
			return "forgot-password";
	}
	@RequestMapping("/resetPassword")
	public String resetPassword(Model model, HttpSession session,@ModelAttribute("searcheditem") Product searcheditem, @ModelAttribute("userForResetPassword") User userForResetPassword,Principal principal) {
		init(principal,session);
		session.removeAttribute("myySessionAttribute");
		return "reset-password";
	}
	@RequestMapping("/resetPassword2")
	public String resetPassword2(Model model, HttpSession session,@ModelAttribute("searcheditem") Product searcheditem, @ModelAttribute("userForResetPassword") User userForResetPassword,Principal principal) {
		init(principal,session);
		for(User user : userService.listAll()) {
			if(user.getUsername().equals(userForResetPassword.getUsername()) && userForResetPassword.getPassword().length()>7 ) {
				
				user.setPassword(SecurityConfig.passwordEncoder(userForResetPassword.getPassword()));
				userService.save(user);
				session.setAttribute("myySessionAttribute", "You can login with your new password");
				
			}
			else {
				session.setAttribute("myySessionAttribute", "Invalid username or password. Password must be greater than 7 letters");
			}
		}
		
		
		return "reset-password";
	}
	

	@RequestMapping("/shop-grid")
	public String ShopGrid(Model model, @ModelAttribute("searcheditem") Product searcheditem,HttpSession session,Principal principal) {
		init(principal,session);
		List<Product> allProduct = productService.listAll();
	
		session.setAttribute("listProduct", allProduct);
		session.setAttribute("count", Math.ceil(allProduct.size()/9.0));
		session.setAttribute("selected",1);
		return "shop-grid";
	}
	
	@RequestMapping("/shop-grid-category")
	public String ShopGrid2(Model model, @ModelAttribute("searcheditem") Product searcheditem,HttpSession session,Principal principal) {
		init(principal,session);
		
		return "shop-grid";
	}
	
	@RequestMapping(value="/shop-grid/{category}",  method = RequestMethod.GET)
	public String ShopGridCategory(@PathVariable("category") String category,Model model, @ModelAttribute("searcheditem") Product searcheditem,HttpSession session,Principal principal) {
		init(principal,session);
		List<Product> categoryProduct = new ArrayList<Product>();
			for(Product oneproduct : productService.listAll()) {
				if(oneproduct.getCategory().equalsIgnoreCase(category)) {
					
					categoryProduct.add(oneproduct);
				}
			}
		
		session.setAttribute("listProduct", categoryProduct);
		session.setAttribute("count", Math.ceil(categoryProduct.size()/9.0));
		session.setAttribute("selected",1);
		return "redirect:/shop-grid-category";
	}
	
	@RequestMapping(value="/single-product/{id}",  method = RequestMethod.GET)
	public String SingleProduct(@PathVariable("id") Long id, Model model, @ModelAttribute("searcheditem") Product searcheditem, HttpSession session,Principal principal,@ModelAttribute("comment") Comment comment) {
		
		
		init(principal,session);
		Product searcheditem2 = productService.getProduct(id);
		
		List<Product> categoryProduct = new ArrayList<Product>();
			for(Product oneproduct : productService.listAll()) {
				if(oneproduct.getId()!=searcheditem2.getId() && oneproduct.getCategory().equalsIgnoreCase(searcheditem2.getCategory())) {
					
					categoryProduct.add(oneproduct);
				}
			}
		
		session.setAttribute("listProduct", categoryProduct);
		
		session.setAttribute("searcheditem2",searcheditem2);
	
				
		return "redirect:/single-product2";
		
		
	}
	
	@RequestMapping(value="/filterbyprice",  method = RequestMethod.POST)
	public String filterbyprice(Model model,
							@ModelAttribute("searcheditem") Product searcheditem,
							HttpSession session,
							Principal principal) {
		
		init(principal,session);
		//System.out.print(searcheditem.getTitle());
		//$110 - $400
		String[] currencies = searcheditem.getTitle().split("-");
		System.out.print(currencies[0].substring(1));
		double altsinir= Double.parseDouble(currencies[0].substring(1));
		double ustsinir=Double.parseDouble(currencies[1].substring(2));
		
		List<Product> filterProduct = new ArrayList<Product>();
		for(Product oneproduct : productService.listAll()) {
			if(altsinir<=oneproduct.getOurPrice() && ustsinir>=oneproduct.getOurPrice()) {
				
				filterProduct.add(oneproduct);
			}
		}
	
		session.setAttribute("listProduct", filterProduct);
		return "shop-grid";
		
	}
		
	@RequestMapping(value="/search",  method = RequestMethod.POST)
	public String searchitem(Model model,@ModelAttribute("comment") Comment comment,
							@ModelAttribute("searcheditem") Product searcheditem,
							HttpSession session,
							Principal principal) {
		init(principal,session);
		
		Product temp=new Product();
		List<Product> categoryProduct = new ArrayList<Product>();
		
		List<Product> authorList=new ArrayList<Product>();
		
		
		int controller=1;
		
		for(Product oneproduct : productService.listAll()) {
			if(searcheditem.getTitle().equalsIgnoreCase(oneproduct.getTitle())){
				temp=oneproduct;
				controller=0;
			}
			
		}
		if(controller==0) {
			for(Product oneproduct : productService.listAll()) {
				if(oneproduct.getId()!=temp.getId() && oneproduct.getCategory().equalsIgnoreCase(temp.getCategory())) {
					categoryProduct.add(oneproduct);
				}
			}
			
			session.setAttribute("listProduct", categoryProduct);
			
			session.setAttribute("searcheditem2",temp);
			
			session.setAttribute("commentList",temp.getComment());
			
			session.setAttribute("selected",1);
			
			
			return "single-product2";
		}
		else {
			controller=0;
			for(Product oneproduct : productService.listAll()) {
				if(searcheditem.getTitle().equalsIgnoreCase(oneproduct.getAuthor())){
					authorList.add(oneproduct);
					controller=1;
				}
				
			}
			if(controller==1) {
				session.setAttribute("listProduct", authorList);
				session.setAttribute("count", Math.ceil(authorList.size()/9.0));
				session.setAttribute("selected",1);
				return "shop-grid";
			}
			else {
				controller=0;
				for(Product oneproduct : productService.listAll()) {
					if(searcheditem.getTitle().equalsIgnoreCase(oneproduct.getIsbn())){
						temp=oneproduct;
						controller=1;
					}
					
				}
				if(controller==1) {
					for(Product oneproduct : productService.listAll()) {
						if(oneproduct.getId()!=temp.getId() && oneproduct.getCategory().equalsIgnoreCase(temp.getCategory())) {
							categoryProduct.add(oneproduct);
						}
					}
					session.setAttribute("listProduct", categoryProduct);
					
					session.setAttribute("searcheditem2",temp);
					
					session.setAttribute("commentList",temp.getComment());
					
					session.setAttribute("selected",1);
					
					return "single-product2";
				}
				
			}
			
			
		}
		
		
		
		return "redirect:/error404";
	}
	
	
	@RequestMapping("single-product2")
	public String SingleProduct2 (@ModelAttribute("searcheditem") Product searcheditem3,HttpSession session,Principal principal,@ModelAttribute("comment") Comment comment) {
		init(principal,session);
		Product searcheditem = (Product) session.getAttribute("searcheditem2");
		
		List<Product> categoryProduct = new ArrayList<Product>();
		for(Product oneproduct : productService.listAll()) {
			
			if(oneproduct.getId()!=searcheditem.getId() && oneproduct.getCategory().equalsIgnoreCase(searcheditem.getCategory())) {
				
				categoryProduct.add(oneproduct);
			}
		}
		
		session.setAttribute("listProduct", categoryProduct);
		session.setAttribute("commentList",searcheditem.getComment());
		session.setAttribute("rateyapamaz", "");
		session.setAttribute("selected",1);
		return "single-product2";
	}
	
	@RequestMapping(value="comment-book",  method = RequestMethod.POST)
	public String CommentBook(@ModelAttribute("searcheditem") Product searcheditem3, @ModelAttribute("comment") Comment comment1, Principal principal,HttpSession session, Model model ) {
		init(principal,session);
		if(comment1.getText().equals("") || comment1.getText().equals(" ") || comment1.getText().equals("	")) {
			session.setAttribute("rateyapamaz", "Do you Think this is really a comment?");
			return"single-product2";
		}
		
		Product searcheditem2 = (Product) session.getAttribute("searcheditem2");
		Comment comment= new Comment();
	
		comment.setText(comment1.getText());
		
		boolean madecommentbefore=false;
		
		int onceden_yapilan_rate=0;
		System.out.println(userService.findByUserName(principal.getName()).getUsername());
		
		if(comment1.getRate()!=0) {
			for(Comment allcomments : searcheditem2.getComment()) {
				
				if(allcomments.getUser().getUsername().equals(userService.findByUserName(principal.getName()).getUsername())) {
					
					if(allcomments.getRate()!=0) {
						madecommentbefore=true;
						onceden_yapilan_rate=allcomments.getRate();
					}
				}
			}
			if(madecommentbefore) {
				//rate yapamaz
				
				session.setAttribute("rateyapamaz", "You just rate once");
				comment.setRate(onceden_yapilan_rate);
			}
			else {
				
				comment.setRate(comment1.getRate());
				
				int total_rate	;
			
				
				List<User> userlist = new ArrayList<User>();
				
				userlist.add(userService.findByUserName(principal.getName()));
				
				total_rate=comment1.getRate();
				
				//onceden yorum yapip rate yapmadiysa onceki yorumlarinin rate ini degistir
				for(Comment allcomments : searcheditem2.getComment()) {
					
					if(allcomments.getUser().getUsername().equals(userService.findByUserName(principal.getName()).getUsername())) {
					
						allcomments.setRate(comment1.getRate());
						
						commentService.updateComment(allcomments);
					}
					int controller_for_user=1;
					
					for(User user : userlist) {
					
						if(user.getUsername().equals(allcomments.getUser().getUsername())) {
						
							controller_for_user=0;
						}
					}
					if(controller_for_user	==	1 && allcomments.getRate()!=0 ) {
						
						userlist.add(allcomments.getUser());
						
						total_rate+=allcomments.getRate();
					}
					
				}
				
				searcheditem2.setRate(total_rate/userlist.size());
				
			}
		}
		session.removeAttribute("searcheditem2");
		comment.setProduct(searcheditem2);		
		User user=userService.findByUserName(principal.getName());
		comment.setUser(user);
		comment.setDate();
		
		commentService.saveComment(user,searcheditem2,comment);
		session.setAttribute("searcheditem2",searcheditem2);
		session.setAttribute("commentList",searcheditem2.getComment());
		return "single-product2";
		
	}
	
	
	public void init(Principal principal,HttpSession session) {
		List<Product> campaignlist = new ArrayList<Product>();
		int allcount=0;
		int Sciencecount=0;
		int Historycount=0;
		int Romancecount=0;
		int Biographycount=0;
		int Fictioncount=0;
		
			for(Product oneproduct : productService.listAll()) {
				
				if(oneproduct.getListPrice()!=oneproduct.getOurPrice()) {
					
					campaignlist.add(oneproduct);
					
				}
				allcount++;
				if(oneproduct.getCategory().equalsIgnoreCase("Science")) {
					Sciencecount++;
					
				}
				else if(oneproduct.getCategory().equals("History")) {
					Historycount++;
					
				}
				else if(oneproduct.getCategory().equals("Romance")) {
					Romancecount++;
					
				}
				else if(oneproduct.getCategory().equals("Biography")) {
					Biographycount++;
					
				}
				else if(oneproduct.getCategory().equals("Fiction")) {
					Fictioncount++;
					
				}
			}
			session.setAttribute("allcount", allcount);
			session.setAttribute("Sciencecount", Sciencecount);
			session.setAttribute("Historycount", Historycount);
			session.setAttribute("Romancecount", Romancecount);
			session.setAttribute("Biographycount", Biographycount);
			session.setAttribute("Fictioncount", Fictioncount);
			session.setAttribute("campaignlist", campaignlist);	
			
		 if(principal!=null) {
	    		
	            User entered_user=userService.findByUserName(principal.getName());
	            
	            session.setAttribute("user", entered_user);
	           
	            session.setAttribute("cartItems", entered_user.getCart());
	            
	            session.setAttribute("WishlistItems", entered_user.getWishlist());
	            
	            session.setAttribute("Orderitems", entered_user.getSiparis());
	        }
	}
	
	
	

	
	
}
