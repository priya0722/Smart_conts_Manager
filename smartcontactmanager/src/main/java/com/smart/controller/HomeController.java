package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.entities.contact;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/home")
	public String home(Model model) {
		model.addAttribute("title","Home - Smart Contact Manage");
		return "home";
	}
	@GetMapping("/signup")
	public String signuppage(Model model) {
		model.addAttribute("title","Register - Smart Contact Manager");
		model.addAttribute("user",new User());
		return "signup";
	}
	
	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title","About - Smart Contact Manager");
		return "about";
	}
	@GetMapping("/base")
	public String base(Model model) {
		model.addAttribute("title","Base - Smart Contact Manager");
		return "base";
	}

	@PostMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result1,
	                           @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, 
	                         Model model,  HttpSession session) {
	     try {

	 	    if (!agreement) {
//	 	         System.out.println("You must agree to the terms and conditions.");
	 	        throw new Exception("You must agree to the terms and conditions");
	 	        //return "signup";  // Stay on the same page and show error;
	 	    }

	 	    if(result1.hasErrors()) {
	 	    	System.out.println("ERROR"+result1.toString());
	 	    	model.addAttribute("user",user);
	 	    	return "signup";
	 	    }
	 	    
	 	    // Set default role and enable user
	 	    user.setRole("ROLE_USER");
	 	    user.setEnabled(true);
	 	    user.setImageurl("default.png");
	 	    user.setPassword(passwordEncoder.encode(user.getPassword()));

	 	    // Save user to the repository
	 	    User result = this.userRepository.save(user);

	 	    model.addAttribute("user", new User());
	 	   session.setAttribute("message", new Message("Sucessfully Registered !!", " alert-success"));
	 	   return "signup";
	 	   // Add saved user back to model
//	 	    model.addAttribute("user", result);  // Use lowercase "user" for consistency with form
	     
	     } catch (Exception e) {
	    	 e.printStackTrace();
	    	 model.addAttribute("user",user);
	    	session.setAttribute("message", new Message("Somthing went wrong !!" + e.getMessage(), " alert-danger"));
	    	if (session.getAttribute("message") != null) {
	            // Add the message to the model
	            model.addAttribute("message", session.getAttribute("message"));
	            // Remove the message from the session after it's displayed
	            session.removeAttribute("message");
	        }
	    	return "signup";
	     }
	}

//	    return "signup_success";  // Redirect to a success page or confirmation

//HANDLER FOR CUSTOM LOHIN
@GetMapping("/signin")
public String customLogin(Model model)

{
	model.addAttribute("title","LOGIN Page- Smart Contact Manager");
	return "login";
}
	
	
}
	

	
	
	
	
	
	
//	@Autowired
//	private UserRepository userRepository;
//	
//	@GetMapping("test") 
//	@ResponseBody
//	public String test() {
//		User user = new User();
//		user.setName("Priya sinha");
//		user.setEmail("Priyasinha@1");
//		contact contact= new contact();
//		user.getContacts().add(contact);
//		userRepository.save(user);
//		return "working";
		
//	} 
	


