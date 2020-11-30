package com.fortunator.api.service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	private static final String HOST = "smtp.gmail.com";

	@Value("${suport.email}")
    private String suportEmail;
	
	@Value("${suport.email.password}")
    private String suportPassword;
	
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

	public void resetPassword(String email) throws AddressException, MessagingException {
		User user = findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Email not registered for any user"));

		String newPass = generateNewPass(user);
		sendEmailWithPass(newPass, email);
	}

	private String generateNewPass(User user) {
		String generatedString = UUID.randomUUID().toString();

		user.setPassword(String.valueOf(generatedString.substring(0, 7).hashCode()));
		userRepository.save(user);

		return generatedString.substring(0, 7);
	}

	private void sendEmailWithPass(String newPass, String email) throws AddressException, MessagingException {
		Properties prop = System.getProperties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", HOST);
		prop.put("mail.smtp.port", 587);
		prop.put("mail.smtp.ssl.trust", HOST);

		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(suportEmail, suportPassword);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(suportEmail));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
		message.setSubject("Sua nova senha");

		String msg = "Use esta senha para realizar o login no fortunator: " + newPass;

		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(msg, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);

		message.setContent(multipart);

		Transport.send(message);
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
