package com.ojt.studentmanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojt.studentmanagement.entity.UserTimetable;
import com.ojt.studentmanagement.service.UserTimetableService;
import com.ojt.studentmanagement.repository.UserTimetableRepository;

@Service
public class UserTimetableServiceImpl implements UserTimetableService {

	@Autowired
	UserTimetableRepository usertimetableRepo;

	@Override
	public UserTimetable getUserTimetableById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserTimetable> userlisttimeTable() {
		// TODO Auto-generated method stub
		return usertimetableRepo.findAll();
	}

}