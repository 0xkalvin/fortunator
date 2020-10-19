package com.fortunator.api.service;

import java.math.BigDecimal;
import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortunator.api.models.Balance;
import com.fortunator.api.models.User;
import com.fortunator.api.repository.UserRepository;
import com.fortunator.api.service.exceptions.EmailExistsException;
import com.fortunator.api.service.exceptions.UserNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;	
	
	public User registerUser(User user) {
		if(findByEmail(user.getEmail()).isPresent()) {
			throw new EmailExistsException("Email already registered, please enter another email and try again.");
		}
		user.setPassword(String.valueOf(user.getPassword().hashCode()));
		Balance balance = new Balance();
		balance.setUser(user);
		balance.setAmount(new BigDecimal(0.0));
		user.setBalance(balance);
		return userRepository.save(user);
	}
	
	public Optional<User> findByEmail(String email){
		return userRepository.findByEmail(email);
	}
	
	public User doLogin(String email, String password) throws LoginException {
		User user = findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Email not registered for any user")
		);
		
		if(user.getPassword().equals(String.valueOf(password.hashCode()))){
			return user;
		}
		throw new LoginException("Password is incorrect.");
	}
	
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}
}
