package com.adminportal.controller;

import java.security.Principal;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.adminportal.Mail;
import com.adminportal.model.Product;
import com.adminportal.model.Siparis;
import com.adminportal.model.User;
import com.adminportal.model.UserRole;
import com.adminportal.model.cartItems;
import com.adminportal.service.SiparisService;
import com.adminportal.service.UserRoleService;
import com.adminportal.service.UserService;
import com.adminportal.service.ProductService;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private  SiparisService siparisService;
	
	@Autowired
	private  ProductService productService;
	
	@RequestMapping("/")
	public String index(){
		return "redirect:/adminpage";
	}
	
	@RequestMapping("/adminpage")
	public String home(){
		return "adminpage";
	}
	
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	
	/* User information */
	
	/*shows users in the userlist page*/
	@RequestMapping("/viewuser")
	public String AllUser(Model model) {
		List<User> allUser = userService.listAll();
		List<User> receivedUser = new ArrayList<User>();
		List<UserRole> userRoleList = userRoleService.listAll();
		
		for(User u : allUser) {
			for (UserRole ur : userRoleList) {
				if(u.getId() == ur.getUser().getId() && ur.getRole().getRoleId()==1) {
					receivedUser.add(u);
				}
			}
		}
		
		model.addAttribute("listUsers",receivedUser);
		return "userlist";
	}
	
	/* user delete */
	@RequestMapping(value="/userdelete/{id}")
	public String deleteUserForm(@PathVariable(name="id") Long id) {
		List<UserRole> userRoleList = userRoleService.listAll();
		for (UserRole ur : userRoleList) {
			if(id == ur.getUser().getId()) {
				userRoleService.deleteUserRole(ur.getUserRoleId());
			}
		}
		userService.deleteUser(id);
		
		return "redirect:/viewuser";
	}
	
	
	/* Order */
	
	/*show orders waiting*/
	@RequestMapping("/viewOrder")
	public String AllOrder(Model model) {
		List<Siparis> receivedOrder = new ArrayList<Siparis>();
		
		for(Siparis s: siparisService.listAll()) {
			if(s.getStatus().equals("wait")) {
				receivedOrder.add(s);
			}
		}
		
		model.addAttribute("listOrders",receivedOrder);
		return "orderlist";
	}
	
	@RequestMapping("/detail/{id}")
	public String editOrder(@PathVariable(name="id") Long id, HttpSession session,Principal principal,Model model) {
		//System.out.println(1);
		Siparis siparis = siparisService.findById(id);
		//System.out.println(2);
		session.setAttribute("siparis", siparis);
		
		//System.out.println(3);
		model.addAttribute("order",siparis);
		return "editOrder";
	}
	
	/*save butonu olacak burada değişiklikler kaydedilecek */
	@RequestMapping(value="/saveOrder", method = RequestMethod.POST)
	public String saveOrder(@ModelAttribute("order") Siparis ordersiparis, HttpServletRequest request, HttpSession session) {
		Siparis oursiparis=(Siparis) session.getAttribute("siparis");
		session.removeAttribute("siparis");
		oursiparis.setStatus(ordersiparis.getStatus());
		String siparisProducts = "";
		if(oursiparis.getStatus().equals("rejected")) {
			for(cartItems cartitem : oursiparis.getCartitems()) {
				cartitem.getProduct().setStockNumber(cartitem.getProduct().getStockNumber()+cartitem.getQuantity());
				productService.save(cartitem.getProduct());
			}
			
			
		}
		else {
			for(cartItems cartitem : oursiparis.getCartitems()) {
				cartitem.getProduct().setSellercount(cartitem.getProduct().getSellercount()+cartitem.getQuantity());
				siparisProducts += "<tr>"+"<td>"+cartitem.getProduct().getTitle() +"</td>"
				+"<td>"+cartitem.getQuantity()+ " x $" + cartitem.getProduct().getOurPrice() + " = "  + "$" + cartitem.getProduct().getOurPrice() * cartitem.getQuantity()+"</td>"+"</tr>";
				productService.save(cartitem.getProduct());
				
				
			}
			
			
			
			String html="<html>" + 
					"<head>" + 
					"    <meta charset=\"utf-8\">" + 
					"    <title>A simple, clean, and responsive HTML invoice template</title>" + 
					"    " + 
					"    <style>" + 
					"    .invoice-box {" + 
					"        max-width: 800px;" + 
					"        margin: auto;" + 
					"        padding: 30px;" + 
					"        border: 1px solid #eee;" + 
					"        box-shadow: 0 0 10px rgba(0, 0, 0, .15);" + 
					"        font-size: 16px;" + 
					"        line-height: 24px;" + 
					"        font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;" + 
					"        color: #555;" + 
					"    }" + 
					"    " + 
					"    .invoice-box table {" + 
					"        width: 100%;" + 
					"        line-height: inherit;" + 
					"        text-align: left;" + 
					"    }" + 
					"    " + 
					"    .invoice-box table td {" + 
					"        padding: 5px;" + 
					"        vertical-align: top;" + 
					"    }" + 
					"    " + 
					"    .invoice-box table tr td:nth-child(2) {" + 
					"        text-align: right;" + 
					"    }" + 
					"    " + 
					"    .invoice-box table tr.top table td {" + 
					"        padding-bottom: 20px;" + 
					"    }" + 
					"    " + 
					"    .invoice-box table tr.top table td.title {" + 
					"        font-size: 45px;" + 
					"        line-height: 45px;" + 
					"        color: #333;" + 
					"    }" + 
					"    " + 
					"    .invoice-box table tr.information table td {" + 
					"        padding-bottom: 40px;" + 
					"    }" + 
					"    " + 
					"    .invoice-box table tr.heading td {" + 
					"        background: #eee;" + 
					"        border-bottom: 1px solid #ddd;" + 
					"        font-weight: bold;" + 
					"    }" + 
					"    " + 
					"    .invoice-box table tr.details td {" + 
					"        padding-bottom: 20px;" + 
					"    }" + 
					"    " + 
					"    .invoice-box table tr.item td{" + 
					"        border-bottom: 1px solid #eee;" + 
					"    }" + 
					"    " + 
					"    .invoice-box table tr.item.last td {" + 
					"        border-bottom: none;" + 
					"    }" + 
					"    " + 
					"    .invoice-box table tr.total td:nth-child(2) {" + 
					"        border-top: 2px solid #eee;" + 
					"        font-weight: bold;" + 
					"    }" + 
					"    " + 
					"    @media only screen and (max-width: 600px) {" + 
					"        .invoice-box table tr.top table td {" + 
					"            width: 100%;" + 
					"            display: block;" + 
					"            text-align: center;" + 
					"        }" + 
					"        " + 
					"        .invoice-box table tr.information table td {" + 
					"            width: 100%;" + 
					"            display: block;" + 
					"            text-align: center;" + 
					"        }" + 
					"    }" + 
					"    " + 
					"    /** RTL **/" + 
					"    .rtl {" + 
					"        direction: rtl;" + 
					"        font-family: Tahoma, 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;" + 
					"    }" + 
					"    " + 
					"    .rtl table {" + 
					"        text-align: right;" + 
					"    }" + 
					"    " + 
					"    .rtl table tr td:nth-child(2) {" + 
					"        text-align: left;" + 
					"    }" + 
					"    </style>" + 
					"</head>"+
					"<body>" + 
					"    <div class=\"invoice-box\">" + 
					"        <table cellpadding=\"0\" cellspacing=\"0\">" + 
					"            <tr class=\"top\">" + 
					"                <td colspan=\"2\">" + 
					"                    <table>" + 
					"                        <tr>" + 
					"                            <td class=\"title\">" + 					
					"                                 <font color = \" #0066ff\" face = \"Times New Roman\">READABLY</font>" + 
					"                            </td>" + 
					"                            <td>" + 

					"                            <p>"+oursiparis.getDate()+"</p>" + 
					"                            </td>" + 
					"                        </tr>" + 
					"                    </table>" + 
					"                </td>" + 
					"            </tr>" + 
					"            " + 
					"            <tr class=\"information\">" + 
					"                <td colspan=\"3\">" + 
					"                    <table>" + 
					"                        <tr>" + 
					"                            <td>" + 
					"                                <p th:text=\"${session.receipt.address}\">"+oursiparis.getAddress()+"<br></p>" + 
					"                                 <p th:text=\"${session.receipt.city}\">"+oursiparis.getCity()+"<br></p>" + 
					"                                <p th:text=\"${session.receipt.country}\">"+oursiparis.getCountry()+"</p>" + 
					"                                " + 
					"                            </td>" + 
					"                             " + 
					"                            <td><p th:text=\"${session.receipt.email}\">"+oursiparis.getEmail()+" </p>" + 
					"                             <p th:text=\"${session.receipt.first_name}\">"+oursiparis.getFirst_name()+"</p>" + 
					"                       		 <p th:text=\"${session.receipt.second_name}\">"+oursiparis.getSecond_name()+"</p>" + 
					"                                 " + 
					"                            </td>" + 
					"                        </tr>" + 
					"                    </table>" + 
					"                </td>" + 
					"            </tr>" + 
					"            " + 
					"            <tr class=\"heading\">" + 
					"                <td>" + 
					"                    Payment Method" + 
					"                </td>" + 
					"                	" + 
					"                <td>" + 
					"                     Credit Cart No" + 
					"                </td>" + 
					"            </tr>" + 
					"            " + 
					"               <tr class=\"details\">" + 
					"                <td>Credit Cart</td>" + 
					"                " + 
					"                    " + 
					"                    <td th:text=\"${session.receipt.cart_num}\">"+oursiparis.getCart_num()+"</td>" + 
					"                     " + 
					"               </tr>" + 
					"            " + 
					"               <tr class=\"heading\">" + 
					"                   <td>Product</td>" + 
					"                	" + 
					"                   <td> Price</td>" + 
					"              </tr>" + 
					"             " + 
					"              <tr >" + 
									siparisProducts+
					
					"              	" + 
					"              </tr>" + 
					"              " + 
					"           		<tr class=\"heading\">" + 
					"                   <td>"+"Shipment Cost"+"</td>" + 
					"                " + 
					"                   <td></td>" + 
					"              </tr>" + 
					"            " + 
					"             <tr class=\"item\" >" + 
					"              " + 
					"                 <td ></td> " + 
					"                <td th:text=\"${' $'+session.receipt.shipment_cost}\">"+"$"+oursiparis.getShipment_cost()+"</td> " + 
					"                 " + 
					"			  </tr>" + 
					"            " + 
					"            " + 
					"            " + 
					"            " + 
					"            " + 
					"            <tr class=\"total\">" + 
					"                <td>Total:</td>" + 
					"                " + 
					"                <td th:text=\"${'Total: $'+session.receipt.grandTotal}\">"+"$"+oursiparis.getGrandTotal()+"</td> " + 
					"            </tr>" + 
					"            <tr class=\"total\">" + 
					"                <td></td>" + 
					"                " + 
					"                <td>" +
					"                </td>" + 
					"            </tr>" + 
					"        </table>" + 
					"    </div>" + 
					"</body>" + 
					"" + 
					"" + 
					"</html>"
					;
			
			
			
			String orderReceipt = "Here is your order receipt:\n\n"+oursiparis.getFirst_name()+" "+oursiparis.getSecond_name()+"\n\n"
											+siparisProducts +"\n"+"\tGrand Total:\t$"+oursiparis.getGrandTotal()	;
			
			Mail mail = new Mail();
			mail.sendReceiptMail(oursiparis.getEmail(), html);
			
		}
		
		session.setAttribute("siparis", oursiparis);
		siparisService.save(oursiparis);
		
		return "redirect:/viewOrder";
	}
}
