package com.cp.project.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cp.project.demo.model.forUser.User;
import com.cp.project.demo.service.WebClientUserService;

import reactor.core.publisher.Mono;

@Controller
public class UserController {

	@Autowired
	WebClientUserService userService;
	
	public UserController(WebClientUserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/web/view/account")
	public String viewAllAccount(Model model) {
	    List<User> users = userService.getUsers().collectList().block();
	    if (users == null) {
	        model.addAttribute("users", new ArrayList<>()); // ส่งเป็น list ว่างถ้าไม่พบ
	    } else {
	        model.addAttribute("users", users);
	    }
	    return "viewaccount";
	}
	
	@GetMapping("/web/register")
	public String createUserForm(Model model) {
		model.addAttribute("users", new User());
		return "registerForm";
	}

	@PostMapping("/web/createuser")
	public Mono<String> createUser(@ModelAttribute User userRequest, Model model) {
		return userService.createUser(userRequest).map(users -> {
			model.addAttribute("users", users);
			return "redirect:/web/login";
		}).onErrorResume(error -> {
			return Mono.just("redirect:/web/register?error=" + error.getMessage());
		});
	}

	@GetMapping("/web/login")
	public String loginForm(Model model) {
		model.addAttribute("users", new User());
		return "loginForm";
	}

	@PostMapping("/web/loginuser")
	public Mono<String> loginUser(@ModelAttribute User userRequest, Model model) {
	    return userService.userLogin(userRequest.getUserEmail(), userRequest.getUserPassword())
	        .map(user -> {
	            model.addAttribute("users", user);
	            // Check user role
	            if ("admin".equalsIgnoreCase(user.getUserRole())) {
	                return "dashboard";
	            } else {
	                return "homepage_login";
	            }
	        })
	        .onErrorResume(error -> {
	            return Mono.just("redirect:/web/login?error=" + error.getMessage()); // Redirect back to login with an error message
	        });
	}
}
