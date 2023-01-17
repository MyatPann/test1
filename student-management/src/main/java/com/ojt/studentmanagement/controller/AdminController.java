package com.ojt.studentmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ojt.studentmanagement.entity.User;
import com.ojt.studentmanagement.repository.UserRepository;
import com.ojt.studentmanagement.service.UserService;
import com.ojt.studentmanagement.service.UserValidateService;

@Controller
@RequestMapping(value = "admin")
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserValidateService validateService;

	// Go to admin sign up page
	@GetMapping("/admin_register")
	public String showAdminRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "admin/admin_register";
	}

	// Go to admin list page
	@GetMapping("/admin_list")
	public String viewAdminList(Model model) {
		List<User> listAdmin = userRepo.findAll();
		model.addAttribute("listAdmin", listAdmin);
		return "admin/admin_list";
	}

	// Go to admin Update Page
	@GetMapping("/admin/edit/{id}")
	public String showAdminUpdateForm(@PathVariable("id") long id, Model model) {
		User user = userRepo.findById(id).get();
		model.addAttribute("user", user);
		return "admin/admin_update";
	}

	// Save Updated Admin
	@PostMapping("process_update_admin")
	public String saveUpdateAdmin(User user, Model model) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setRole("Admin");
		userRepo.save(user);
		List<User> listAdmin = userRepo.findAll();
		model.addAttribute("listAdmin", listAdmin);
		return "admin/admin_list";
	}

	@GetMapping("/admin/delete/{id}")
	public String deleteAdmin(@PathVariable(value = "id") long id, Model model) {
		// call delete
		this.userRepo.deleteById(id);
		List<User> listAdmin = userRepo.findAll();
		model.addAttribute("listAdmin", listAdmin);
		return "admin/admin_list";
	}

	// search function
	@RequestMapping(path = { "/admin_list", "/search_admin" })
	public String search(Model model, String keyword) {
		System.out.println(keyword);
		if (keyword != null) {
			List<User> adminList = userService.getByKeyword(keyword);
			model.addAttribute("listAdmin", adminList);
			System.out.println(adminList.size());
		} else {
			List<User> listAdmin = userRepo.findAll();
			model.addAttribute("listAdmin", listAdmin);
		}
		return "admin/admin_list";
	}

	// Save New Admin
	@PostMapping("/process_register_admin")
	public String processRegister(User user, BindingResult result, Model model) {
		String err = validateService.validateUser(user);
		if (!err.isEmpty()) {
			ObjectError error = new ObjectError("globalError", err);
			result.addError(error);
		}
		if (result.hasErrors()) {
			return "admin/admin_list";
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setRole("Admin");
		userRepo.save(user);
		List<User> listAdmin = userRepo.findAll();
		model.addAttribute("listAdmin", listAdmin);
		return "admin/admin_list";
	}
}