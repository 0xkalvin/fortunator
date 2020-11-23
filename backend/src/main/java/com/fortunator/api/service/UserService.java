package com.fortunator.api.service;

import java.math.BigDecimal;
import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortunator.api.controller.entity.UpdateUser;
import com.fortunator.api.models.Balance;
import com.fortunator.api.models.Level;
import com.fortunator.api.models.LevelNameEnum;
import com.fortunator.api.models.User;
import com.fortunator.api.repository.UserRepository;
import com.fortunator.api.service.exceptions.EmailExistsException;
import com.fortunator.api.service.exceptions.UserNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User registerUser(User user) {
		if (findByEmail(user.getEmail()).isPresent()) {
			throw new EmailExistsException("Email already registered, please enter another email and try again.");
		}

		Balance balance = new Balance();
		balance.setUser(user);
		balance.setAmount(BigDecimal.valueOf(0.0));

		Level level = new Level(user, 1, LevelNameEnum.INICIANTE.getDescription(), BigDecimal.valueOf(0));
		level.setMaxLevelScore();

		user.setPassword(String.valueOf(user.getPassword().hashCode()));
		user.setBalance(balance);
		user.setScore(BigDecimal.valueOf(0.0));
		user.setLevel(level);

		return userRepository.save(user);
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User doLogin(String email, String password) throws LoginException {
		User user = findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Email not registered for any user"));

		if (user.getPassword().equals(String.valueOf(password.hashCode()))) {
			return user;
		}

		throw new LoginException("Password is incorrect.");
	}

	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}

	public User getOne(Long id) {

		User user = this.findUserById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

		return user;
	}

	public User updateUser(UpdateUser userData) {
		User user = userRepository.findById(userData.getUserId())
				.orElseThrow(() -> new UserNotFoundException("User not found"));
		if (findByEmail(userData.getEmail()).isPresent()) {
			throw new EmailExistsException("Email already registered, please enter another email and try again.");
		}

		user.setName(userData.getName());
		user.setEmail(userData.getEmail());
		user.setPassword(String.valueOf(userData.getPassword().hashCode()));
		user.getBalance().setAmount((userData.getBalance()));

		return userRepository.save(user);
	}
}
