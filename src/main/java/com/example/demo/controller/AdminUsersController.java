package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.MyAuthority;
import com.example.demo.model.MyUser;
import com.example.demo.repo.IMyAuthorityRepo;
import com.example.demo.service.IAdminUsersService;


@Controller
@RequestMapping("/admin/user")
public class AdminUsersController {
		
		@Autowired
		private IAdminUsersService userService;		
		
		@Autowired
		private IMyAuthorityRepo authRepo;
		
		@GetMapping("/all") // localhost:8080/admin/user/all
		public String getControllerAllUserRecords(Model model) {
			model.addAttribute("users", userService.retrieveAllUsers());
			return "admin/user-list-page";
		}
		
	    @GetMapping("/create") // localhost:8080/admin/user/create
	    public String getControllerUserCreate(Model model) {
	    	MyUser user = new MyUser();
	    	model.addAttribute("authorities", authRepo.findAll());
	        model.addAttribute("user", user);
	        return "admin/user-create-page";
	    }

	    @PostMapping("/create")
	    public String postControllerUserCreate(@ModelAttribute("user") MyUser user, @RequestParam String confirmPassword, @RequestParam Long authorityId,
	    		BindingResult result, Model model, RedirectAttributes redirectAttrs) {    	
	        if (result.hasErrors()) {
	            return "admin/user-create-page";
	        }
	        
	        if (!user.getPassword().equals(confirmPassword)) {
	            redirectAttrs.addFlashAttribute("error", "Passwords do not match.");
	            return "redirect:/admin/user/create";
	        }

	        try {
	            userService.createUserAccount(user.getUsername(), user.getPassword(), authRepo.findById(authorityId).get());
	            return "redirect:/admin/user/all";

	        } catch (Exception e) {
	            model.addAttribute("registrationError", e.getMessage());
	            return "admin/user-create-page";
	        }
	    }
	    
		@GetMapping("/details/{id}") // localhost:8080/admin/user/details/{id}
		public String getControllerVisitorRecordById(@PathVariable("id") long id, Model model) {
			try {
				model.addAttribute("user", userService.retrieveUserById(id));
				return "admin/user-record-page";
			} catch (Exception e) {
				model.addAttribute("error", e.getMessage());
					return "error-page";
			}
		}

		@GetMapping("/update/{id}") // localhost:8080/admin/user/update/{id}
		public String editVisitor(@PathVariable("id") long id, Model model) {
		    try {
		        MyUser user = userService.retrieveUserById(id);
		    	model.addAttribute("authorities", authRepo.findAll());
		        model.addAttribute("user", user);
		        return "admin/user-update-page";
		    } catch (Exception e) {
		        model.addAttribute("error", e.getMessage());
		        return "error-page";
		    }	
		}

		@PostMapping("/update/{id}")
		public String updateVisitor(@PathVariable("id") long id, 
				@ModelAttribute("user") MyUser user, @RequestParam Long authorityId, BindingResult result, Model model) {
			
			if(result.hasErrors()) {
				return "admin/user-update-page";
			}
			
		    try {
		    	userService.updateUserById(id, user.getUsername(), user.getPassword(), authRepo.findById(authorityId).get());
		        return "redirect:/admin/user/all";
		    } catch (Exception e) {
		        model.addAttribute("error", e.getMessage());
		        return "error-page";
		    }
		}

	    @GetMapping("/delete/{id}")  // localhost:8080/admin/user/delete/{id}
	    public String deleteAccount(@PathVariable("id") long id, Model model) {
	    	try {
	            userService.deleteUserById(id);
	            return "redirect:/admin/user/all";
	        } catch (Exception e) {
	            model.addAttribute("error", e.getMessage());
	            return "error-page";
	        }
	    }	
	
}
