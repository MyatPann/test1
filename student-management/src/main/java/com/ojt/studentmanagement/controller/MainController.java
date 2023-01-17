package com.ojt.studentmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ojt.studentmanagement.entity.Profile;
import com.ojt.studentmanagement.entity.Timetable;
import com.ojt.studentmanagement.entity.User;
import com.ojt.studentmanagement.repository.TimetableRepository;
import com.ojt.studentmanagement.repository.UserRepository;
import com.ojt.studentmanagement.service.TimetableService;
import com.ojt.studentmanagement.service.UserValidateService;

@Controller
public class MainController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserValidateService validateService;

	@Autowired
	private TimetableRepository timeTableRepo;
	
	@Autowired
	private TimetableService timetableService;

	@GetMapping("/applyList")
	public String viewChatsPage(Model model) {
		return "admin/apply_list";
	}

	@GetMapping("/")
	public String viewHomePage(Model model) {
		List<Timetable> listtimeTable = timeTableRepo.findAll();
		model.addAttribute("listtimeTable", listtimeTable);
		return "user/timetable_list";
	}

	// Go to login page
	@GetMapping("/login")
	public String viewLoginPage() {
		return "user/login";
	}

	// Go to sign up page
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "user/signup_form";
	}


	@PostMapping("/process_register")
	public String processRegister(User user, BindingResult result) {

		Profile userProfile = new Profile();
		String err = validateService.validateUser(user);
		if (!err.isEmpty()) {
			ObjectError error = new ObjectError("globalError", err);
			result.addError(error);
		}
		if (result.hasErrors()) {
			return "user/signup_form";
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setRole("User");
		userProfile.setUser(user);
		user.setProfile(userProfile);
		userRepo.save(user);

		return "user/register_success";
	}

	@RequestMapping(path = { "/timetable_search" })
	public String home(Timetable timetable, Model model, String keyword) {
		if (keyword != null) {
			List<Timetable> list = timetableService.getByKeyword(keyword);
			model.addAttribute("listtimeTable", list);
		} else {
			List<Timetable> list = timetableService.getAllTimetable();
			model.addAttribute("listtimeTable", list);
		}
		return "user/timetable_list";
	}

}
