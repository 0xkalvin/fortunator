package com.fortunator.api.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import javax.security.auth.login.LoginException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import com.fortunator.api.controller.entity.UpdateUser;
import com.fortunator.api.models.Balance;
import com.fortunator.api.models.User;
import com.fortunator.api.repository.UserRepository;
import com.fortunator.api.service.exceptions.EmailExistsException;
import com.fortunator.api.service.exceptions.UserNotFoundException;

@ContextConfiguration(classes = { UserService.class })
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	private static final String EMAIL = "ze@gmail.com";
	private static final String NAME = "Ze";
	private static final String PASSWORD = "pass";
	private static final BigDecimal BALANCE = BigDecimal.valueOf(10);
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private User user;
	
	@Mock
	private UpdateUser userData;
	
	@Mock
	private Balance balance;

	@InjectMocks
	private UserService userService;

	@Test
	public void shouldRegisterEmailSuccessfully() throws Exception {
		final User user = new User(null, "roger", "roger@email.com", "senhadoroger");

		doReturn(Optional.empty()).when(userRepository).findByEmail("roger@email.com");
		doReturn(user).when(userRepository).save(user);

		User savedUser = userService.registerUser(user);

		assertTrue(savedUser.equals(user));
	}

	@Test
	public void shouldNotRegisterSameEmailTwice() throws Exception {
		final User user = new User(null, "roger", "roger@email.com", "senhadoroger");

		doReturn(Optional.of(user)).when(userRepository).findByEmail("roger@email.com");

		EmailExistsException thrown = assertThrows(EmailExistsException.class, () -> userService.registerUser(user));

		assertTrue(thrown.getMessage().contains("Email already registered"));
	}

	@Test
	public void shouldNotLoginIfNotRegistered() throws Exception {
		String email = "joao@email.com";
		String password = "senhadojoao";

		doReturn(Optional.empty()).when(userRepository).findByEmail("joao@email.com");

		UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> userService.doLogin(email, password));

		assertTrue(thrown.getMessage().contains("Email not registered for any user"));
	}

	@Test
	public void shouldNotLoginWhenIncorrectPassword() throws Exception {
		String email = "joao@email.com";
		String password = "errou!";

		final User user = new User(null, "joao", email, "senhadojoao");

		doReturn(Optional.of(user)).when(userRepository).findByEmail("joao@email.com");

		LoginException thrown = assertThrows(LoginException.class, () -> userService.doLogin(email, password));

		assertTrue(thrown.getMessage().contains("Password is incorrect."));
	}
	
	@Test
	public void shouldUpdateUser() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(userRepository.findByEmail(eq(EMAIL))).thenReturn(Optional.empty());
		when(userData.getEmail()).thenReturn(EMAIL);
		when(userData.getPassword()).thenReturn(PASSWORD);
		when(userData.getName()).thenReturn(NAME);
		when(userData.getBalance()).thenReturn(BALANCE);
		when(user.getBalance()).thenReturn(balance);
		
		userService.updateUser(userData);
		
		verify(user).setName(anyString());
		verify(user).setEmail(anyString());
		verify(user).setPassword(anyString());
		verify(user).getBalance();
		verify(balance).setAmount(any(BigDecimal.class));
	}
	
}
