package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SareetaApplicationTests {
	@Autowired
	private UserController userController;

	@Test
	public void contextLoads() {
	}

	@Test
	public void createUser() {
		String username = "mytestuser";
		String password = "mypassword";
		CreateUserRequest createUserRequest = CreateUserRequest(username, password);

		ResponseEntity<User> response = userController.createUser(createUserRequest);
		Assertions.assertEquals(response.getBody().getUsername(), username);
	}

	private CreateUserRequest CreateUserRequest(String username, String password) {
		CreateUserRequest createUserRequest = new CreateUserRequest();
		createUserRequest.setUsername(username);
		createUserRequest.setPassword(password);
		createUserRequest.setConfirmPassword(password);

		return createUserRequest;
	}

}
