package com.example.demo.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {
	
    @GetMapping("/")
    public String home(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "home-page";
    }
    
    @ControllerAdvice
    public class GlobalControllerAdvice {

        @ModelAttribute("role")
        public String addRoleToModel(Authentication authentication) {
            if (authentication == null || authentication.getAuthorities().isEmpty()) {
                return null;
            }
            return authentication.getAuthorities().iterator().next().getAuthority();
        }
    }

}
