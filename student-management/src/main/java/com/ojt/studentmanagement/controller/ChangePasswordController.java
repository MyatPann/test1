package com.ojt.studentmanagement.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ojt.studentmanagement.entity.User;
import com.ojt.studentmanagement.repository.UserRepository;
import com.ojt.studentmanagement.security.Utility;
import com.ojt.studentmanagement.service.UserValidateService;

import net.bytebuddy.utility.RandomString;

@Controller
public class ChangePasswordController {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UserValidateService userService;
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/forgot_password")
	public String showForgotPasswordForm(Model model) {
		model.addAttribute("pageTitle", "Forgot Password");
		return "user/forgot_password_form";

	}

	@PostMapping("/forgot_password")
	public String processForgotPassword(HttpServletRequest request, Model model) {
		String email = request.getParameter("email");
		String token = RandomString.make(30);

		try {
			userService.updateResetPasswordToken(token, email);
			String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
			System.out.println(resetPasswordLink + "Link...........");
			sendEmail(email, resetPasswordLink);
			model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

		} catch (UserNotFoundException ex) {
			model.addAttribute("error", ex.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Error while sending email");
		}

		return "user/forgot_password_form";
	}

	public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("noreply@gmail.com", "Shopme Support");
		helper.setTo(recipientEmail);
		System.out.println(recipientEmail + "Mail...........");
		String subject = "Here's the link to reset your password";
		
		System.out.println(link+"LLLLLLLLLLLLL");

		String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + link
				+ "\">Change my password</a></p>" + "<br>" + "<p>Ignore this email if you do remember your password, "
				+ "or you have not made the request.</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);
	}

	@GetMapping("/reset_password")
	public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
		User user = userService.getByResetPasswordToken(token);
		model.addAttribute("token", token);

		if (user == null) {
			model.addAttribute("message", "Invalid Token");
			return "message";
		}

		return "user/reset_password_form";
	}

	@PostMapping("/reset_password")
	public String processResetPassword(HttpServletRequest request, Model model) {
		String token = request.getParameter("token");
		String password = request.getParameter("password");

		User user = userService.getByResetPasswordToken(token);
		model.addAttribute("title", "Reset your password");

		if (user == null) {
			model.addAttribute("message", "Invalid Token");
			return "reset_password_form";
		} else {
			userService.updatePassword(user, password);

			model.addAttribute("message", "You have successfully changed your password.");
		}

		return "user/login";

	}

	@ModelAttribute
	private void userDetails(Model m, Principal p) {
		if (p != null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}
	}

	@GetMapping("/changePass")
	public String showUserUpdateForm(Model model) {
		return "user/change_password";
	}

	@PostMapping("/updatePassword")
	public String changePassword(Principal p, @RequestParam("oldPass") String oldPass,
			@RequestParam("newPass") String newPass, HttpSession session) {

		String email = p.getName();

		User loginUser = userRepo.findByEmail(email);

		boolean f = passwordEncoder.matches(oldPass, loginUser.getPassword());

		if (f) {
			loginUser.setPassword(passwordEncoder.encode(newPass));
			User updatePasswordUser = userRepo.save(loginUser);

			if (updatePasswordUser != null) {
				session.setAttribute("msg", "Password Change Success");
			} else {
				session.setAttribute("msg", "Something went wrong on Server");
			}
		} else {
			session.setAttribute("msg", "Old Password is incorrect");
		}

		return "redirect:/changePass";
	}

}
