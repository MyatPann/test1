package com.ojt.studentmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ojt.studentmanagement.entity.UserTimetable;
import com.ojt.studentmanagement.entity.UserTimetablePK;
import com.ojt.studentmanagement.repository.UserTimetableRepository;
import com.ojt.studentmanagement.security.CustomUserDetails;

@Controller
@RequestMapping(value = "admin")
public class UserTimetableController {

	@Autowired
	private UserTimetableRepository userTimeRepo;

	@GetMapping("/applyList")
	public String viewUserTableList(Model model) {
		List<UserTimetable> userlisttimeTable = userTimeRepo.findAll();
		model.addAttribute("userlisttimeTable", userlisttimeTable);
		return "admin/apply_list";
	}

	@PostMapping("/ApproveUserTimetable/")
	public String ApproveUserTimetable(@RequestParam("uid") long uid, @RequestParam("tid") long tid,
			@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
		UserTimetablePK utid = new UserTimetablePK(uid, tid);
		UserTimetable usertimetable = userTimeRepo.findById(utid).get();

		usertimetable.setApproveFlag(true);
		usertimetable.setApprovePersonMail(userDetails.getUsername());
		model.addAttribute("userTimetable", usertimetable);
		userTimeRepo.save(usertimetable);

		return "redirect:/admin/applyList";
	}
}