package com.example.demo;

import java.util.List;

import com.example.demo.controllers.CartController;
import com.example.demo.controllers.ItemController;
import com.example.demo.controllers.OrderController;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// @RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SareetaApplicationTests {
	@Autowired
	private UserController userController;

	@Autowired
	private ItemController itemController;

	@Autowired
	private CartController cartController;

	@Autowired
	private OrderController orderController;

	private String username = "mytestuser";
	private String password = "mypassword";
	private Long userId = 1L;
	private Long itemId = 1L;
	private Long notFoundItemId = 3L;
	private String itemName = "Round Widget";

	@Test
	@Order(1)
	public void createUser() {
		CreateUserRequest request = new CreateUserRequest();
		request.setUsername(username);
		request.setPassword(password);
		request.setConfirmPassword(password);

		ResponseEntity<User> response = this.userController.createUser(request);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(response.getBody().getUsername(), username);
	}

	@Test
	@Order(2)
	public void findByUserId() {
		ResponseEntity<User> response = this.userController.findById(userId);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(userId, response.getBody().getId());
	}

	@Test
	@Order(3)
	public void findByUserName() {
		ResponseEntity<User> response = this.userController.findByUserName(username);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(username, response.getBody().getUsername());
	}

	@Test
	@Order(4)
	public void getItems() {
		ResponseEntity<List<Item>> response = itemController.getItems();
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertTrue(response.getBody().size() > 0);
	}

	@Test
	@Order(5)
	public void getItemById() {
		ResponseEntity<Item> response = itemController.getItemById(itemId);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(itemId, response.getBody().getId());
	}

	@Test
	@Order(6)
	public void getItemsByName() {
		ResponseEntity<List<Item>> response = itemController.getItemsByName(itemName);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(itemName, response.getBody().get(0).getName());
	}
	
	@Test
	@Order(7)
	public void addTocart() {
		ModifyCartRequest request = new ModifyCartRequest();
		request.setUsername(username);
		request.setItemId(itemId);
		request.setQuantity(2);

		ResponseEntity<Cart> response = cartController.addTocart(request);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(2, response.getBody().getItems().size());
	}

	@Test
	@Order(8)
	public void removeFromcart() {
		ModifyCartRequest request = new ModifyCartRequest();
		request.setUsername(username);
		request.setItemId(itemId);
		request.setQuantity(1);

		ResponseEntity<Cart> response = cartController.removeFromcart(request);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(1, response.getBody().getItems().size());
	}

	@Test
	@Order(9)
	public void addTocartFailure() {
		ModifyCartRequest request = new ModifyCartRequest();
		request.setUsername(username);
		request.setItemId(notFoundItemId);
		request.setQuantity(1);

		ResponseEntity<Cart> response = cartController.addTocart(request);
		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	@Order(10)
	public void submit() {
		ResponseEntity<UserOrder> response = orderController.submit(username);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(1, response.getBody().getItems().size());
	}

	@Test
	@Order(11)
	public void getOrdersForUser() {
		ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser(username);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(1, response.getBody().size());
	}
}
