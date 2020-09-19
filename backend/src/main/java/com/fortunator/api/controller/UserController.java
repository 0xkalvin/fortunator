package com.fortunator.api.controller;

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

import com.fortunator.api.dto.LoginDTO;
import com.fortunator.api.models.User;
import com.fortunator.api.service.UserService;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@CrossOrigin
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public User registerUser(@Valid @RequestBody User user) {
		return userService.registerUser(user);
	}
	
	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity<Void> doLogin(@Valid @RequestBody LoginDTO login) {
		try {
			userService.doLogin(login.getEmail(), login.getPassword());
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (LoginException e) {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
	}
}
