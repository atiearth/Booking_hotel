package com.cp.project.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cp.project.demo.model.forUser.User;
import com.cp.project.demo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;
	
	public List<User> getUsers() {
		return (List<User>) userRepo.findAll();
	}
	
	public User getUserById(Integer id) {
	    return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
	}

	
	public User addUser(User user) {
		return userRepo.save(user);
	}
	
	public User editUser(Integer id, User user) {
		User existUser = userRepo.findById(id).get();
		existUser.setUserFirstName(user.getUserFirstName());
		existUser.setUserLastName(user.getUserLastName());
		existUser.setUserEmail(user.getUserEmail());
		existUser.setUserPassword(user.getUserPassword());
		existUser.setPhoneNumber(user.getPhoneNumber());
		return userRepo.save(existUser);
	}
	
	
}
