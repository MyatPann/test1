package com.ojt.studentmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ojt.studentmanagement.entity.Course;
import com.ojt.studentmanagement.service.CourseService;

@Controller
@RequestMapping(value = "admin")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@RequestMapping(path = { "/course_list", "/course_search" })
	public String home(Model model, String keyword) {
		if (keyword != null) {
			List<Course> list = courseService.getByKeyword(keyword);
			model.addAttribute("listCourses", list);
		} else {
			List<Course> list = courseService.getAllCourse();
			model.addAttribute("listCourses", list);
		}
		return "admin/course_list";

	}

	// display list of course
	@GetMapping("/course_list")
	public String viewAllCourse(Model model) {
		model.addAttribute("listCourses", courseService.getAllCourses());
		return "admin/course_list";
	}

	@GetMapping("/showNewCourseForm")
	public String showNewCourseForm(Model model) {
		// create model attribute to bind form data
		Course course = new Course();
		model.addAttribute("course", course);
		return "admin/course_register";
	}

	@PostMapping("/saveCourse")
	public String saveCourse(Model model, Course course) {
		// save course to database
		courseService.saveCourse(course);

		List<Course> list = courseService.getAllCourse();
		model.addAttribute("listCourses", list);
		return "admin/course_list";
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

		// get course from the service
		Course course = courseService.getCourseById(id);

		// set course as a model attribute to pre-populate the form
		model.addAttribute("course", course);
		return "admin/course_edit";
	}

	@GetMapping("/deleteCourse/{id}")
	public String deleteCourse(@PathVariable(value = "id") long id, Model model) {

		// call delete employee method
		this.courseService.deleteCourseById(id);
		List<Course> list = courseService.getAllCourse();
		model.addAttribute("listCourses", list);
		return "admin/course_list";
	}
}