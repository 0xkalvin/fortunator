package com.fortunator.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fortunator.api.dto.LoginDto;
import com.fortunator.api.models.User;
import com.fortunator.api.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {

	private static final int SC_OK = 200;
	private static final int SC_BAD_REQUEST = 400;
	private static final int SC_CONFLICT = 409;
	private static final int SC_UNAUTHORIZED = 401;
	private static final int SC_NOT_FOUND = 404;
	
	@Autowired
	private UserService userService;

	@ApiOperation(value = "Register a new user ")
	@ApiResponses(value = { @ApiResponse(code = SC_CONFLICT, message = "Email alredy registered"),
			@ApiResponse(code = SC_BAD_REQUEST, message = "One or more fields were filled in incorrectly") })
	@CrossOrigin
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User registerUser(@Valid @RequestBody User user) {
		return userService.registerUser(user);
	}

	@ApiOperation(value = "Do login")
	@ApiResponses(value = { @ApiResponse(code = SC_OK, message = "ok", examples = @Example(value = {@ExampleProperty(mediaType = "application/json", value="{'userId': 0")})),
			@ApiResponse(code = SC_UNAUTHORIZED, message = "Password is incorrect."),
			@ApiResponse(code = SC_BAD_REQUEST, message = "One or more fields were filled in incorrectly"),
			@ApiResponse(code = SC_NOT_FOUND, message = "Email not found.") })
	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity<User> doLogin(@Valid @RequestBody LoginDto login) {
		try {
			User user = userService.doLogin(login.getEmail(), login.getPassword());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (LoginException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}