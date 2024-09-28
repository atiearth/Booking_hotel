package com.cp.project.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cp.project.demo.model.forUser.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WebClientUserService {
	@Autowired
	WebClient webClient;

	public WebClientUserService(WebClient webClient) {
		this.webClient = webClient;
	}

	public Flux<User> getUsers() {
		return webClient.get()
		        .uri("/users")
		        .retrieve()
		        .bodyToFlux(User.class)
		        .doOnNext(user -> System.out.println("Fetched user: " + user))
		        .doOnError(error -> System.err.println("Error fetching users: " + error.getMessage()));
	}

	public Mono<User> getUserById(Integer id) {
		return webClient.get().uri("/users/{id}", id).retrieve().bodyToMono(User.class);
	}

	public Mono<User> createUser(User user) {
	    // Validate password
	    if (user.getUserPassword() == null || user.getUserPassword().length() < 8) {
	        return Mono.error(new RuntimeException("Password must be at least 8 characters long."));
	    }

	    // Check password & confirm password
	    if (!user.getUserPassword().equals(user.getConfirmPassword())) {
	        return Mono.error(new RuntimeException("Passwords do not match. Please try again."));
	    }

	    // Set default role if none provided
	    if (user.getUserRole() == null || user.getUserRole().isEmpty()) {
	        user.setUserRole("user"); // Set default role as "user"
	    }

	    // Check if email already exists in the database
	    return emailExists(user.getUserEmail()).flatMap(exists -> {
	        if (exists) {
	            return Mono.error(new RuntimeException("Email already exists."));
	        }

	        // Proceed to create user
	        return webClient.post().uri("/users").bodyValue(user).retrieve()
	                .onStatus(status -> status.is4xxClientError(), clientResponse -> {
	                    return Mono.error(new RuntimeException("Client error"));
	                }).onStatus(status -> status.is5xxServerError(), clientResponse -> {
	                    return Mono.error(new RuntimeException("Server error"));
	                }).bodyToMono(User.class)
	                .doOnNext(savedUser -> System.out.println("User created: " + savedUser))
	                .doOnError(error -> System.err.println("Error creating user: " + error.getMessage()));
	    });
	}

	private Mono<Boolean> emailExists(String email) {
		// Fetch all users and check if email exists in the database
		return getUsers().filter(existingUser -> existingUser.getUserEmail().equalsIgnoreCase(email)).hasElements();
	}

	public Mono<User> updateUser(Integer id, User user) {
	    // Validate password (if updating password)
	    if (user.getUserPassword() != null && user.getUserPassword().length() < 8) {
	        return Mono.error(new RuntimeException("Password must be at least 8 characters long."));
	    }

	    // Check if email already exists in the database (except the current user)
	    return emailExists(user.getUserEmail()).flatMap(exists -> {
	        if (exists) {
	            return Mono.error(new RuntimeException("Email already exists."));
	        }

	        // Proceed to update user
	        return webClient.put().uri("/users/{id}", id).bodyValue(user).retrieve().bodyToMono(User.class)
	                .doOnNext(updatedUser -> System.out.println("User updated: " + updatedUser))
	                .doOnError(error -> System.err.println("Error updating user: " + error.getMessage()));
	    });
	}
	
	public Mono<User> userLogin(String email, String password) {
	    return getUserByEmail(email)
	            .flatMap(user -> {
	                // Check if the password matches
	                if (!user.getUserPassword().equals(password)) {
	                    return Mono.error(new RuntimeException("Invalid password."));
	                }
	                // Return the user if login is successful
	                return Mono.just(user);
	            })
	            .switchIfEmpty(Mono.error(new RuntimeException("User with this email does not exist.")))
	            .doOnSubscribe(subscription -> System.out.println("Attempting to login with email: " + email))
	            .doOnError(error -> System.out.println("Login error: " + error.getMessage()));
	}

	public Mono<User> getUserByEmail(String email) {
	    return getUsers()
	            .filter(user -> user.getUserEmail().equalsIgnoreCase(email))
	            .singleOrEmpty()
	            .doOnSubscribe(subscription -> System.out.println("Fetching user with email: " + email))
	            .doOnError(error -> System.out.println("Error fetching user: " + error.getMessage()));
	}

}
