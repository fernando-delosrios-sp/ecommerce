package com.example.demo;

import java.util.List;

import com.example.demo.controllers.ItemController;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

// @RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SareetaApplicationTests {
	@Autowired
	private UserController userController;

	@Autowired
	private ItemController itemController;

	private String username = "mytestuser";
	private String password = "mypassword";
	private Long id = 1L;
	private String itemName = "Round Widget";

	@Test
	@Order(1)
	public void createUser() {

		CreateUserRequest createUserRequest = new CreateUserRequest();
		createUserRequest.setUsername(username);
		createUserRequest.setPassword(password);
		createUserRequest.setConfirmPassword(password);

		ResponseEntity<User> response = this.userController.createUser(createUserRequest);
		Assertions.assertEquals(response.getBody().getUsername(), username);
	}

	@Test
	@Order(2)
	public void findByUserId() {
		ResponseEntity<User> response = this.userController.findById(id);
		Assertions.assertEquals(id, response.getBody().getId());
	}

	@Test
	@Order(3)
	public void findByUserName() {
		ResponseEntity<User> response = this.userController.findByUserName(username);
		Assertions.assertEquals(username, response.getBody().getUsername());
	}

	@Test
	@Order(4)
	public void getItems() {
		ResponseEntity<List<Item>> response = itemController.getItems();
		Assertions.assertTrue(response.getBody().size() > 0);
	}

	@Test
	@Order(5)
	public void getItemById() {
		ResponseEntity<Item> response = itemController.getItemById(id);
		Assertions.assertEquals(id, response.getBody().getId());
	}

	@Test
	@Order(6)
	public void getItemsByName() {
		ResponseEntity<List<Item>> response = itemController.getItemsByName(itemName);
		Assertions.assertEquals(itemName, response.getBody().get(0).getName());
	}
}
