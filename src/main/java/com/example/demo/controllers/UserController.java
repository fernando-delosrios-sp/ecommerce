package com.example.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@RequestMapping("/api/user")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		try {
			return ResponseEntity.of(userRepository.findById(id));
		} catch (Exception e) {
			log.error("Exception found.", e);
			throw e;
		}
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) {
		try {
			User user = userRepository.findByUsername(username);
			return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
		} catch (Exception e) {
			log.error("Exception found.", e);
			throw e;
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		try {
			User user = new User();
			user.setUsername(createUserRequest.getUsername());
			Cart cart = new Cart();
			cartRepository.save(cart);
			user.setCart(cart);
			if(createUserRequest.getPassword().length()<7 ||
					!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())){
				log.error("Error - Either length is less than 7 or pass and conf pass do not match. Unable to create ", createUserRequest.getUsername());
				return ResponseEntity.badRequest().build();
			}
			user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
			userRepository.save(user);
			log.info("User {} created successfully", createUserRequest.getUsername());
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			log.error("Exception found.", e);
			throw e;
		}
	}
}
