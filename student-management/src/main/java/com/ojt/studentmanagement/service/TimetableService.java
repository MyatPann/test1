package com.ojt.studentmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ojt.studentmanagement.entity.Timetable;

@Service
public interface TimetableService {

	void saveTimetable(Timetable timetable);

	List<Timetable> getByKeyword(String keyword);

	List<Timetable> getAllTimetable();

}