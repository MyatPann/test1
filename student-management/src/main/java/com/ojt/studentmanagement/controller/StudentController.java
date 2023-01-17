package com.ojt.studentmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ojt.studentmanagement.entity.Course;
import com.ojt.studentmanagement.entity.Instructor;
import com.ojt.studentmanagement.entity.Timetable;
import com.ojt.studentmanagement.entity.User;
import com.ojt.studentmanagement.entity.UserTimetable;
import com.ojt.studentmanagement.repository.CourseRepository;
import com.ojt.studentmanagement.repository.InstructorRepository;
import com.ojt.studentmanagement.repository.TimetableRepository;
import com.ojt.studentmanagement.repository.UserTimetableRepository;
import com.ojt.studentmanagement.security.CustomUserDetails;

@Controller
@RequestMapping(value = "user")
public class StudentController {

	@Autowired
	private TimetableRepository timetableRepo;

	@Autowired
	private UserTimetableRepository utimetableRepo;
	@Autowired
	private CourseRepository courseRepo;
	@Autowired
	private InstructorRepository instructorRepo;

	@GetMapping("/dashboard")
	public String viewHomePage(Model model) {
		ArrayList<String> label = new ArrayList<String>();
		ArrayList<Integer> data = new ArrayList<Integer>();
		List<String[]> objs = timetableRepo.findCount();

		for (String[] obj : objs) {
			label.add(obj[0].toString());
			System.out.println(obj[0] + "*******" + obj[1]);
		}

		return "dashboard";
	}

	@GetMapping("/showTimetableForm")
	public String showTimetableForm(Model model) {
		Timetable timetable = new Timetable();
		model.addAttribute("timetable", timetable);
		List<Course> courseList = courseRepo.findAll();
		model.addAttribute("courseList", courseList);
		List<Instructor> instructorList = instructorRepo.findAll();
		model.addAttribute("instructorList", instructorList);
		return "user/timetable_register";
	}

	@PostMapping("/saveTimetable")
	public String saveTimetable(@ModelAttribute("timetable") Timetable timetable) {
		timetableRepo.save(timetable);
		return "user/student_timetable";
	}

	@RequestMapping("/timetable")
	public String home(Timetable timetable, Model model, String keyword) {
		if (keyword != "" && keyword != null) {
			List<Timetable> tList = timetableRepo.findByKeyword(keyword);
			model.addAttribute("listtimeTable", tList);
		} else {
			List<Timetable> tList = timetableRepo.findAll();
			model.addAttribute("listtimeTable", tList);
		}
		return "user/student_timetable";
	}

	@RequestMapping("/applied_timetable")
	public String searchApplied(@AuthenticationPrincipal CustomUserDetails userDetails, Model model, String keyword) {

		User user = userDetails.getUser();
		Long userId = user.getId();

		if (keyword != "" && keyword != null) {
			List<UserTimetable> aList = utimetableRepo.appliedSearchByKeyword(keyword, userId);
			model.addAttribute("appliedList", aList);
		} else {
			model.addAttribute("appliedList", utimetableRepo.findByLoginUserId(userId));
		}

		return "user/applied_timetable";
	}

}
