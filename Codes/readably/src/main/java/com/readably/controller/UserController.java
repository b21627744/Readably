package com.readably.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.readably.Mail;
import com.readably.config.SecurityConfig;
import com.readably.model.Product;
import com.readably.model.Role;
import com.readably.model.User;
import com.readably.model.UserRole;
import com.readably.model.UserValidator;
import com.readably.model.cartItems;
import com.readably.model.Siparis;
import com.readably.service.UserService;
import com.readably.service.SiparisService;

@SuppressWarnings("serial")
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private SiparisService siparisService;
	
	//bu degisken login yapamadigimiz icin edit profile i kontrol amaclı olusturulmustur.Login kismindan sonra silinecektir --kaan
	
	
	
	
	//create accounta basildiginda calisan ilk fonksiyon
	@RequestMapping("/createAccount")
	public String createNewProductForm(Model model,  HttpSession session,@ModelAttribute("searcheditem") Product searcheditem,Principal principal) {
		
		init(principal,session);
		
		UserValidator userValidator = new UserValidator();
		
		model.addAttribute("userValidator",userValidator);
		return "create-an-account";
	}
	
	
	//user olusturan fonksiyondur
	@RequestMapping(value="/Register", method = RequestMethod.POST)
	public String saveAcc(@ModelAttribute("searcheditem") Product searcheditem,@ModelAttribute("userValidator") UserValidator userValidator, 
						  HttpServletRequest request, 
						  HttpSession session,
						  Principal principal
						  ) throws Exception {
		init(principal,session);
		
		if(!userValidator.areMailsEqual()) {
			session.setAttribute("mySessionAttribute", "Mails are not matched");
			return "create-an-account";
		}
		if(!userValidator.arePasswordsEqual()) {
			session.setAttribute("mySessionAttribute", "Passwords are not matched");
			return "create-an-account";
		}
		if(userValidator.getAge()<=5) {
			session.setAttribute("mySessionAttribute", "Age must be greater than 5");
			return "create-an-account";
		}
		if(userValidator.getPassword().length()<8 ) {
			session.setAttribute("mySessionAttribute", "Password length must be greater than 7 letters");
			return "create-an-account";
		}
	
		
		for(User oneuser : userService.listAll()) {
			
			if(userValidator.getUser_id().equals(oneuser.getUsername())){
				session.setAttribute("mySessionAttribute", "This user name is used !!");
				return "create-an-account";
			}	
			else if(userValidator.getEmail().equals(oneuser.getEmail())) {
				session.setAttribute("mySessionAttribute", "This Email is used !!");
				return "create-an-account";
			}
		}
		
		User user=new User();
		if(userValidator.createUserSuccessfully(user)) {
			Set<UserRole> userRoles = new HashSet<>();
			Role role1= new Role();
			role1.setRoleId(1);
			role1.setName("ROLE_USER");
			userRoles.add(new UserRole(user, role1));
			
			userService.createUser(user, userRoles);
			
			session.removeAttribute("mySessionAttribute");
			
			return "sign-in";
		}
		return "create-an-account";
	
	}
	
    //login sayfasina yonlendirir
    @RequestMapping("/login")
    public String Login(Model model,@ModelAttribute("searcheditem") Product searcheditem,HttpSession session,Principal principal) {
        model.addAttribute("classActiveLogin", true);
        init(principal,session);
        
        
		
        return "sign-in";
    }
    
	
    //
    @RequestMapping("/checkout")
    public String Checkout(@ModelAttribute("searcheditem") Product searcheditem,HttpSession session,Principal principal,Model model) {
    	init(principal,session);
    	
    	User entered_user=userService.findByUserName(principal.getName());
    	if(entered_user.getCart().size()==0) return "redirect:/";
    	for(cartItems cartitem:entered_user.getCart()) {
    		if(cartitem.getProduct().getStockNumber()<cartitem.getQuantity()) {
    			session.setAttribute("carttayok", cartitem.getProduct().getTitle() + " is out of stock.You cant continue.");
    			return "cart";

    		}
    	}
    	
    	
        model.addAttribute("siparis", new Siparis());
         
        return "checkout";
    }
    
    @RequestMapping(value="/goToOrders")
    public String goToOrders(@ModelAttribute("searcheditem") Product searcheditem, 
			@ModelAttribute("siparis") Siparis siparis,
            HttpServletRequest request, 
            HttpSession session,
            Principal principal) {
    	
    	init(principal,session);
    	
    	
    	return "orders";
    }
    
    
    //for form of siparis
    @RequestMapping(value="/CreateSiparis", method = RequestMethod.POST)
    public String CreateSiparis(@ModelAttribute("searcheditem") Product searcheditem, 
    						@ModelAttribute("siparis") Siparis siparis,
                          HttpServletRequest request, 
                          HttpSession session,
                          Principal principal
                          ) {
    	
    	if(principal!=null) {
    		
            User entered_user=userService.findByUserName(principal.getName());
            
            session.setAttribute("user", entered_user);
            
            for(cartItems cartitem : entered_user.getCart()) {
            	cartitem.getProduct().setStockNumber(cartitem.getProduct().getStockNumber()-cartitem.getQuantity());
            }
            
            session.setAttribute("cartItems", entered_user.getCart());
            
            session.setAttribute("WishlistItems", entered_user.getWishlist());
            
            siparisService.saveSiparis(entered_user,siparis);
            
            session.setAttribute("Orderitems", entered_user.getSiparis());
        }
    	
        return "orders";
    }
	
	
	//for receipt
	@RequestMapping("/CreateSiparis/{id}")
	public String ShowReceipt(@ModelAttribute("searcheditem") Product searcheditem,
								Principal principal,
								HttpSession session,
								@PathVariable("id") Long id) {
		init(principal,session);
		Siparis siparis=siparisService.findById(id);
		
		session.setAttribute("receipt", siparis);
		
		session.setAttribute("cartItemsofSiparis", siparis.getCartitems());
		

	return "redirect:/shopping-page";
	}
	
	@RequestMapping("shopping-page")
	public String ShowReceiptSon(@ModelAttribute("searcheditem") Product searcheditem,
										Principal principal,
											HttpSession session) {		
		return "shopping-page";
	}
	
	
	
	@RequestMapping("clearHistoryrejected")
	public String clearHistory1(@ModelAttribute("searcheditem") Product searcheditem,
								Principal principal,
								HttpSession session) {
		init(principal,session);
		User entered_user=userService.findByUserName(principal.getName());
		 session.removeAttribute("showHistory");
		 session.removeAttribute("Orderitems");
		 session.removeAttribute("receipt");
		 session.removeAttribute("cartItemsSiparis");
		 
		 List<Siparis> sipariswillbedelete = entered_user.getSiparis();
		 
		 boolean controller=true;
		 
		 while(controller) {
			 
			 controller=deleteSiparis(sipariswillbedelete,"rejected");
			 
			 sipariswillbedelete = entered_user.getSiparis();
		 }
		 session.setAttribute("Orderitems", entered_user.getSiparis());
	return "orders";
	}
	
	@RequestMapping("clearHistoryconfirmed")
	public String clearHistory(@ModelAttribute("searcheditem") Product searcheditem,
										Principal principal,
											HttpSession session) {
		init(principal,session);
		 User entered_user=userService.findByUserName(principal.getName());
		 session.removeAttribute("showHistory");
		 session.removeAttribute("Orderitems");
		 session.removeAttribute("receipt");
		 session.removeAttribute("cartItemsSiparis");
		 
		 List<Siparis> sipariswillbedelete = entered_user.getSiparis();
		 
		 boolean controller=true;
		 
		 while(controller) {
			 
			 controller=deleteSiparis(sipariswillbedelete,"confirmed");
			 
			 sipariswillbedelete = entered_user.getSiparis();
		 }
		 
		 session.setAttribute("Orderitems", entered_user.getSiparis());
		return "orders";
	}
	
	public boolean deleteSiparis(List<Siparis> sipariswillbedelete , String silinecek) {
		for(Siparis siparis : sipariswillbedelete) {
			
			 if(siparis.getStatus().equals(silinecek)) {
				 siparisService.deleteSiparis(siparis);
				 return true;
			 }
			 
		 }
		return false;
	}


	
	@RequestMapping("/error404")
	public String Error404(@ModelAttribute("searcheditem") Product searcheditem,Principal principal,HttpSession session) {
		init(principal,session);
		 
		 
		return "error404";
	}
	
	@RequestMapping("/Shopping-History")
	public String shoppingHistory(@ModelAttribute("searcheditem") Product searcheditem,Principal principal,HttpSession session) {
		
		init(principal,session);
		
		User entered_user=userService.findByUserName(principal.getName());
		
		List<cartItems> cartArray = new ArrayList <cartItems>();
		
		for(Siparis item : entered_user.getSiparis()) {
			if(item.getStatus().equals("confirmed")) {
				for(cartItems alinankitaplar : item.getCartitems()) {
					cartArray.add(alinankitaplar);
				}
			}
		}
		
		session.setAttribute("showHistory", cartArray);
		
		return "shopping-history";
	}
	@RequestMapping("/team")
	public String Team(@ModelAttribute("searcheditem") Product searcheditem,Principal principal,HttpSession session) {
		init(principal,session);
		return "team";
	}
	
	
	@RequestMapping("/contact")
	public String Contact(@ModelAttribute("searcheditem") Product searcheditem,@ModelAttribute("mail") Mail mail,HttpSession session,Principal principal) {
		init(principal,session);
		return "contact";
	}
	
	
	//kendi profilimizi görmek icin tikladigimizda calisan ilk fonksiyon icinde set edilen degerler login olmadigindan deneme amacli set edilmistir daha sonradan degistirilecektri
	@RequestMapping("/myaccount")
	public String view_my_acc(Model model,
								@ModelAttribute("searcheditem") Product searcheditem,HttpSession session,
								Principal principal) {
		init(principal,session);
		model.addAttribute("denemeforloginsiz",userService.findByUserName(principal.getName()));
		
		return "my-account";
	}
	
	//kendi profilimize girdikten sonra edit yapmak istersek ilk calisak olan fonksiyondur
	@RequestMapping("/edit-button")
	public String edit_profile(Model model,
							HttpSession session,
							@ModelAttribute("searcheditem") Product searcheditem,
							Principal principal) {
		init(principal,session);
		
		UserValidator userValidator = new UserValidator();
		
	
		
		model.addAttribute("userValidator",userValidator);
		
		return "edit-profile";
	}
	
	
	//edit profile kismi gerceklestirilmektedir.
	@RequestMapping(value="/EditProfile", method = RequestMethod.POST)
	public String editProfile(@ModelAttribute("userValidator") UserValidator userValidator, 
								@ModelAttribute("searcheditem") Product searcheditem,
								Model model,
								Principal principal,
						  HttpServletRequest request, 
						  HttpSession session) {
		init(principal,session);
		
		
		User entered_user=userService.findByUserName(principal.getName());
		userValidator.setId(entered_user.getId());
		
		// formdan alınan image usera kaydedildi. denemeforloginsiz kısımdaki image control true yapılacak
		
		
		MultipartFile userImage = userValidator.getUserImage();
		
		if(!userValidator.getName().equals("")) {
			
			entered_user.setName(userValidator.getName());
			
		}
		if(!userValidator.getSurname().equals("")) {
			
			entered_user.setSurname(userValidator.getSurname());
		}
		if(!userValidator.getEmail().equals("")) {
			entered_user.setEmail(userValidator.getEmail());
		}
		if(!userValidator.getAddress().equals("")) {
			entered_user.setAddress(userValidator.getAddress());
		}
		if(!userValidator.getPassword().equals("")) {
			if(userValidator.arePasswordsEqual() ) {
				entered_user.setPassword(SecurityConfig.passwordEncoder(userValidator.getPassword()));
			}
			else {
				session.setAttribute("editprofile","passwords are not equal");
				return "edit-profile";
			}
		}
		if(!userValidator.getPhoneNumber().equals("")) {
			if(userValidator.getPhoneNumber().equals(userValidator.getConfirm_email())) {
				entered_user.setPhoneNumber(userValidator.getPhoneNumber());
			}
			else {
				session.setAttribute("editprofile", "Phone numbers");
				return "edit-profile";
			}
		}
		
		if(!userImage.isEmpty()) {
			entered_user.setUserImage(userValidator.getUserImage());
			try {
				byte[] bytes = userImage.getBytes();
				String name = entered_user.getId() + ".jpg";
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/images/user/" + name)));
				
				entered_user.setImageControl(true);
				userValidator.setImageControl(true);
				
				stream.write(bytes);
				stream.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		userService.save(entered_user);
		
		model.addAttribute("denemeforloginsiz",entered_user);
		
		
		session.removeAttribute("editprofile");
		return "my-account";
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
