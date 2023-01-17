package com.ojt.studentmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ojt.studentmanagement.controller.UserNotFoundException;
import com.ojt.studentmanagement.entity.User;
import com.ojt.studentmanagement.repository.UserRepository;

@Service
public class UserValidateService {
	@Autowired
	private UserRepository userRepo;

	public String validateUser(User user) {

		String message = "";
		User userDB = userRepo.findByEmail(user.getEmail());

		if (userDB != null) {

			message = "Duplicate Email " + user.getEmail();

		}
		return message;
	}

	public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
		User user = userRepo.findByEmail(email);
		if (user != null) {
			user.setResetPasswordToken(token);
			userRepo.save(user);
		} else {
			throw new UserNotFoundException("Could not find any customer with the email " + email);
		}
	}

	public User getByResetPasswordToken(String token) {
		return userRepo.findByResetPasswordToken(token);
	}

	public void updatePassword(User user, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);
		user.setPassword(encodedPassword);

		user.setResetPasswordToken(null);
		userRepo.save(user);
	}

}
