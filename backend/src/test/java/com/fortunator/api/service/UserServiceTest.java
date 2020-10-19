package com.fortunator.api.service;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import javax.security.auth.login.LoginException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.fortunator.api.models.User;
import com.fortunator.api.repository.UserRepository;
import com.fortunator.api.service.exceptions.*;

@ContextConfiguration(classes = { UserService.class })
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@Mock
	private UserRepository userRepository;

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
	
}
