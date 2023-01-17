package com.ojt.studentmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ojt.studentmanagement.entity.Instructor;
import com.ojt.studentmanagement.service.InstructorService;

@Controller
@RequestMapping(value = "admin")
public class InstructorController {

	@Autowired
	private InstructorService instructorService;

	@RequestMapping(path = { "/instructor_list", "/instructor_search" })
	public String home(Model model, String keyword) {
		if (keyword != null) {
			List<Instructor> list = instructorService.getByKeyword(keyword);
			model.addAttribute("listInstructor", list);
		} else {
			List<Instructor> list = instructorService.getAllInstructors();
			model.addAttribute("listInstructor", list);
		}
		return "admin/instructor_list";
	}

	// display list of instructor
	@GetMapping("/instructor_list")
	public String viewAllInstructor(Model model) {
		model.addAttribute("listInstructor", instructorService.getAllInstructors());
		return "admin/instructor_list";
	}

	@GetMapping("/showNewInstructorForm")
	public String showNewInstructorForm(Model model) {
		// create model attribute to bind form data
		Instructor instructor = new Instructor();
		model.addAttribute("instructor", instructor);
		return "admin/instructor_register";
	}

	@PostMapping("/saveInstructor")
	public String saveInstructor(Model model, Instructor instructor) {
		// save instructor to database
		instructorService.saveInstructor(instructor);
		List<Instructor> list = instructorService.getAllInstructors();
		model.addAttribute("listInstructor", list);
		return "admin/instructor_list";
	}

	@GetMapping("/showInstructorForUpdate/{id}")
	public String showInstructorForUpdate(@PathVariable(value = "id") long id, Model model) {

		// get instructor from the service
		Instructor instructor = instructorService.getInstructorById(id);

		// set instructor as a model attribute to pre-populate the form
		model.addAttribute("instructor", instructor);
		return "admin/instructor_edit";
	}

	@GetMapping("/deleteInstructor/{id}")
	public String deleteInstructor(@PathVariable(value = "id") long id, Model model) {

		// call delete instructor method
		this.instructorService.deleteInstructorById(id);
		List<Instructor> list = instructorService.getAllInstructors();
		model.addAttribute("listInstructor", list);
		return "admin/instructor_list";
	}
}