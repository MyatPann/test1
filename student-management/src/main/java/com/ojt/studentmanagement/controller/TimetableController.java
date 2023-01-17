package com.ojt.studentmanagement.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ojt.studentmanagement.entity.Timetable;
import com.ojt.studentmanagement.entity.User;
import com.ojt.studentmanagement.entity.UserTimetable;
import com.ojt.studentmanagement.entity.UserTimetablePK;
import com.ojt.studentmanagement.repository.TimetableRepository;
import com.ojt.studentmanagement.repository.UserTimetableRepository;
import com.ojt.studentmanagement.security.CustomUserDetails;

@Controller
@RequestMapping(value = "user")
public class TimetableController {

	@Autowired
	private TimetableRepository timeTableRepo;

	@Autowired
	private UserTimetableRepository userTimeRepo;

	@GetMapping("/timetable_list/views/{id}")
	public String viewTableList(@PathVariable("id") Long id, Model model,
			@AuthenticationPrincipal CustomUserDetails customUser) {
		Timetable timetable = timeTableRepo.findById(id).get();

		// find UserTimetable
		UserTimetablePK utid = new UserTimetablePK(customUser.getUser().getId(), id);
		UserTimetable ut = new UserTimetable();
		if (userTimeRepo.findById(utid).isPresent()) {
			ut = userTimeRepo.findById(utid).get();
		} else {
			ut.setId(utid);
		}

		model.addAttribute("userTimetable", ut);
		model.addAttribute("timetable", timetable);
		return "user/timetable_display";
	}

	@PostMapping("/process_Timetable")
	public String saveApplyDisplay(@RequestParam("tid") Long timetableid, Model model) {

		CustomUserDetails customUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = customUser.getUser();
		Long uid = user.getId();

		UserTimetablePK utid = new UserTimetablePK(uid, timetableid);
		 UserTimetable userTimetable;

         if(userTimeRepo.findById(utid).isEmpty()) {
             userTimetable  = new UserTimetable();
             userTimetable.setId(utid);
         }else {
             userTimetable = userTimeRepo.findById(utid).get();
         }

		if (userTimetable.isApplyFlag()) {
			userTimetable.setApplyFlag(false);
			userTimetable.setApplayDate(null);

		} else {
			userTimetable.setApplyFlag(true);
			userTimetable.setApplayDate(LocalDate.now());

		}
		userTimeRepo.save(userTimetable);
		model.addAttribute("userTimetable", userTimetable);
		return "redirect:/user/timetable_list/views/" + timetableid;
	}

}