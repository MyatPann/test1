package com.ojt.studentmanagement.service;

import java.util.List;

import com.ojt.studentmanagement.entity.UserTimetable;

public interface UserTimetableService {
	List<UserTimetable> userlisttimeTable();

	UserTimetable getUserTimetableById(long id);
}
