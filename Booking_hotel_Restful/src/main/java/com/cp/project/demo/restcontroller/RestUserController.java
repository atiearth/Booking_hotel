package com.cp.project.demo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cp.project.demo.model.forUser.User;
import com.cp.project.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class RestUserController {
	@Autowired
	UserService userService;
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = userService.getUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
		User user = userService.getUserById(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@PostMapping("/users")
	public User createUser(@RequestBody User user) {
		User createUser = userService.addUser(user);
		return createUser;
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> editUser(@PathVariable("id") Integer id, @RequestBody User user) {
		User updateUser = userService.editUser(id, user);
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
	}
	
}
