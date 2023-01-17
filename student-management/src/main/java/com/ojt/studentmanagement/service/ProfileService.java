package com.ojt.studentmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojt.studentmanagement.entity.Profile;
import com.ojt.studentmanagement.repository.ProfileRepository;

@Service
public class ProfileService {

	@Autowired
	private ProfileRepository profileRepo;

	public void saveProfile(Profile profile) {
		profileRepo.save(profile);
	}
}