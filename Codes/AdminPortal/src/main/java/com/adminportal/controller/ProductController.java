package com.adminportal.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.adminportal.Mail;
import com.adminportal.model.Campaign;
import com.adminportal.model.Product;
import com.adminportal.model.Siparis;
import com.adminportal.model.User;
import com.adminportal.model.Comment;
import com.adminportal.model.WishlistItems;
import com.adminportal.model.cartItems;
import com.adminportal.service.CampaignService;
import com.adminportal.service.ProductService;
import com.adminportal.service.SiparisService;
import com.adminportal.service.UserService;
import com.adminportal.service.WishListService;
import com.adminportal.service.CartService;
import com.adminportal.service.CommentService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CampaignService campaignService;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private SiparisService siparisService;
	
	@Autowired
	private WishListService wishListService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CommentService commentService;
	
	/* product */
	
	/*shows products in the productlist page*/
	@RequestMapping("/viewproduct")
	public String AllProduct(Model model) {
		List<Product> allProduct = productService.listAll();
		model.addAttribute("listProducts",allProduct);
		return "productlist";
	}
	
	/*creating a new product*/
	@RequestMapping("/newProduct")
	public String createNewProductForm(Model model,  HttpSession session) {
		Product product = new Product();
		session.setAttribute("mySessionAttribute", "(*) required fields");
		model.addAttribute("product",product);
		
		return "addProduct";
	}
	
	/*the product is registered in the system here*/
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product, HttpServletRequest request, HttpSession session) {
		
		session.setAttribute("mySessionAttribute", "(*) required fields");
		
		int flag=1;
		
		for(Product oneproduct : productService.listAll()) {
			/*checks for the same product*/
			if((product.getTitle().equals(oneproduct.getTitle()) && product.getAuthor().equals(oneproduct.getAuthor())
					&& product.getPublisher().equals(oneproduct.getPublisher()) && product.getIsbn().equals(oneproduct.getIsbn())) || (product.getIsbn().equals(oneproduct.getIsbn()))) {
				session.setAttribute("mySessionAttribute", "This book is existing !!");
				flag=0;
			}
		}
		
		if(flag==1) {
			MultipartFile productImage = product.getProductImage();
			product.setSellercount(0);
			
			product.setListPrice(Double.parseDouble(product.getListPriceTemporary()));
			product.setStockNumber(Integer.parseInt(product.getStockNumberTemporary()));
			
			double newPrice = product.getListPrice()-((product.getListPrice()*product.getDiscount())/100);
			product.setOurPrice(newPrice);
			
			productService.save(product);
			
			if(!productImage.isEmpty()) {
				try {
					byte[] bytes = productImage.getBytes();
					String name = product.getId() + ".jpg";
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(new File("../readably/src/main/resources/static/images/books/" + name)));
					stream.write(bytes);
					stream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return "redirect:/viewproduct";
		}else {
			return "addProduct";
		}
	}
	
	/*the product is registered in the system here*/
	@RequestMapping(value="/save2", method = RequestMethod.POST)
	public String saveProduct2(@ModelAttribute("product") Product product, HttpServletRequest request, HttpSession session) {
		
		session.setAttribute("mySessionAttribute", "(*) required fields");
		
		int flag=1;
		
		for(Product oneproduct : productService.listAll()) {
			/*checks for the same product*/
			if((product.getId() != oneproduct.getId() && product.getTitle().equals(oneproduct.getTitle()) && product.getAuthor().equals(oneproduct.getAuthor())
					&& product.getPublisher().equals(oneproduct.getPublisher())&& product.getIsbn().equals(oneproduct.getIsbn())) || (product.getId() != oneproduct.getId() && product.getIsbn().equals(oneproduct.getIsbn()))) {
				session.setAttribute("mySessionAttribute", "Book information with title: "+ product.getTitle() + " cannot be updated. This book already exists.!!");
				flag=0;
			}
		}
		
		if(flag==1) {
			MultipartFile productImage = product.getProductImage();
			
			product.setListPrice(Double.parseDouble(product.getListPriceTemporary()));
			product.setStockNumber(Integer.parseInt(product.getStockNumberTemporary()));
			
			double newPrice = product.getListPrice()-((product.getListPrice()*product.getDiscount())/100);
			product.setOurPrice(newPrice);
			
			productService.save(product);
			
			// eğer sorun olursa savei en son yap
			if(!productImage.isEmpty()) {
				try {
					byte[] bytes = productImage.getBytes();
					String name = product.getId() + ".jpg";
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(new File("../readably/src/main/resources/static/images/books/" + name)));
					stream.write(bytes);
					stream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			return "redirect:/viewproduct";
		}else {
			return "editProduct";
		}
	}
	
	
	@RequestMapping("/edit/{id}")
	public ModelAndView editProductForm(@PathVariable(name="id") Long id) {
		ModelAndView mav = new ModelAndView("editProduct");
		
		Product product = productService.getProduct(id);
		mav.addObject("product", product);
		return mav;
	}
	
	@RequestMapping(value="/delete/{id}")
	public String deleteProductForm(@PathVariable(name="id") Long id) {
		
		for(Comment comment: commentService.listAll()) {
			if(comment.getProduct().getId()==id) {
				commentService.deleteComment(comment.getId());
				
			}
			
		}
		for(Siparis siparisler: siparisService.listAll()) {
			for(cartItems item: siparisler.getCartitems()) {
				if(item.getProduct().getId()==id) {
					siparisService.deleteSiparis(siparisler.getId());
					
				}
			}
			
		}
		for(cartItems item: cartService.listAll()) {
			if(item.getProduct().getId()==id) {
				item.getUser().setGrandTotal(item.getUser().getGrandTotal()-item.getQuantity()*item.getProduct().getOurPrice());
				item.getUser().setCartTotal(item.getUser().getCartTotal()-item.getQuantity()*item.getProduct().getOurPrice());
				item.getUser().setBookCount(item.getUser().getBookCount()-item.getQuantity());
				cartService.delete(item.getId());
				
			}
		}
		for(WishlistItems item: wishListService.listAll()) {
			if(item.getProduct().getId()==id) {
				wishListService.deletewishlistitem(item.getId());
				
			}
		}
		
		
		
		productService.deleteProduct(id);
		
		return "redirect:/viewproduct";
	}
	
	
	
	
	/* Campaign */

	/*shows campaigns*/
	@RequestMapping("/viewCampaign")
	public String AllCampaign(Model model) {
		List<Campaign> allCampaign = campaignService.listAll();
		model.addAttribute("listCampaigns",allCampaign);
		
		return "viewCampaign";
	}
	
	/* add campaigns */
	/*creating a new product*/
	@RequestMapping("/addCampaign")
	public String createNewCampaign(Model model,  HttpSession session) {
		Campaign campaign = new Campaign();
		session.setAttribute("mySessionAttribute", "(*) required fields");
		model.addAttribute("campaign",campaign);
		
		return "addCampaign";
	}
	
	// @{/saveCampaign}
	/*the campaign is registered in the system here*/
	@RequestMapping(value="/saveCampaign", method = RequestMethod.POST)
	public String saveCampaign(@ModelAttribute("campaign") Campaign campaign, HttpServletRequest request, HttpSession session) {
		
		session.setAttribute("mySessionAttribute", "(*) required fields");
		
		int flag=1;
		
		for(Campaign c : campaignService.listAll()) {
			if(campaign.getCategory().equals(c.getCategory())) {
				session.setAttribute("mySessionAttribute", "The "+ c.getCategory() + " already exists! Please choose another category.");
				flag=0;
			}
		}
		
		if(flag == 1) {
			
			campaignService.save(campaign);
			
			List<Product> products = productService.listAll();
			
			for(Product p: products) {
				if(p.getCategory().equals(campaign.getCategory())){
						double newPrice = p.getListPrice()-((p.getListPrice()*campaign.getDiscount())/100);
						p.setOurPrice(newPrice) ;
						productService.save(p);
				}
			}
			
			List<User> alluser = userservice.listAll();
			Mail mail = new Mail();				
			mail.sendMail(alluser, campaign.getDiscount(), campaign.getCategory());
			
			return "redirect:/viewCampaign";
		}
		
		return "addCampaign";
	}
	
	/*the campaign is registered in the system here*/
	@RequestMapping(value="/saveCampaign2", method = RequestMethod.POST)
	public String saveCampaign2(@ModelAttribute("campaign") Campaign campaign, HttpServletRequest request, HttpSession session) {
		
		session.setAttribute("mySessionAttribute", "(*) required fields");
		
		int flag=1;
		
		for(Campaign c : campaignService.listAll()) {
			if(campaign.getId() != c.getId() && campaign.getCategory().equals(c.getCategory())) {
				session.setAttribute("mySessionAttribute", "The "+ c.getCategory() + " already exists! Please choose another category.");
				flag=0;
			}
		}
		
		if(flag == 1) {
			
			campaignService.save(campaign);
			
			List<Product> products = productService.listAll();
			
			for(Product p: products) {
				if(p.getCategory().equals(campaign.getCategory())){
						double newPrice = p.getListPrice()-((p.getListPrice()*campaign.getDiscount())/100);
						p.setOurPrice(newPrice) ;
						productService.save(p);
				}
			}
			
			List<User> alluser = userservice.listAll();
			Mail mail = new Mail();				
			mail.sendMail(alluser, campaign.getDiscount(), campaign.getCategory());
			
			return "redirect:/viewCampaign";
		}
		
		return "editCampaign";
	}
	
	@RequestMapping("/editCampaign/{id}")
	public ModelAndView editCampign(@PathVariable(name="id") Long id) {
		ModelAndView mav = new ModelAndView("editCampaign");
		Campaign campaign = campaignService.getCampaign(id);
		mav.addObject("campaign", campaign);
		return mav;
	}
	
}
