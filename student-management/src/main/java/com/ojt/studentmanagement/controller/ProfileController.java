package com.ojt.studentmanagement.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ojt.studentmanagement.entity.Profile;
import com.ojt.studentmanagement.entity.User;
import com.ojt.studentmanagement.repository.ProfileRepository;
import com.ojt.studentmanagement.repository.UserRepository;
import com.ojt.studentmanagement.security.CustomUserDetails;
import com.ojt.studentmanagement.security.FileUploadUtil;
import com.ojt.studentmanagement.service.ProfileService;

@Controller
@RequestMapping(value = "user")
public class ProfileController {

	@Autowired
	private ProfileRepository profileRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ProfileService profileService;

	// Go to admin sign up page
	@GetMapping("/profile")
	public String showProfileDetails(@AuthenticationPrincipal CustomUserDetails customUser, Model model) {
		User user = customUser.getUser();
		Long userId = user.getId();
		model.addAttribute("userProfile", profileRepo.findByUserId(userId));
		model.addAttribute("user", user);
		return "user/profile";
	}

	@GetMapping("/profileShow")
	public String showProfile(@AuthenticationPrincipal CustomUserDetails customUser,Model model) {
		model.addAttribute("profile", profileRepo.findByUserId(customUser.getUser().getId()));
		return "user/profile_register";
	}

	@PostMapping("/profile/save")
	public String saveProfile(Profile profile, @RequestParam("image") MultipartFile multipartFile,@RequestParam("userId") Long userId) throws IOException {
		profile.setUser(userRepo.findById(userId).get());
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			String uploadDir = "profile-image/"+ profile.getId();
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			profile.setProfileImg(fileName);
		profileService.saveProfile(profile);
		return "redirect:/user/profile";

	}

}
