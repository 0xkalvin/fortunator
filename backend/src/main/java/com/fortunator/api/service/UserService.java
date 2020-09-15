package com.fortunator.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortunator.api.models.User;
import com.fortunator.api.repository.UserRepository;
import com.fortunator.api.service.exceptions.EmailExistsException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;	
	
	public User registerUser(User user) {
		if(userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new EmailExistsException("Email alredy registered, please enter another email and try again.");
		}
		user.setPassword(String.valueOf(user.getPassword().hashCode()));
		return userRepository.save(user);
	}

}
