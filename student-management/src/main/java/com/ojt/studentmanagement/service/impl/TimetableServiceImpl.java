package com.ojt.studentmanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojt.studentmanagement.entity.Timetable;
import com.ojt.studentmanagement.repository.TimetableRepository;
import com.ojt.studentmanagement.service.TimetableService;

@Service
public class TimetableServiceImpl implements TimetableService {

	@Autowired
	private TimetableRepository timetableRepo;

	@Override
	public void saveTimetable(Timetable timetable) {
		// TODO Auto-generated method stub
		this.timetableRepo.save(timetable);
	}

	@Override
	public List<Timetable> getByKeyword(String keyword) {
		// TODO Auto-generated method stub

		return timetableRepo.findByKeyword(keyword);
	}

	@Override
	public List<Timetable> getAllTimetable() {
		// TODO Auto-generated method stub
		return timetableRepo.findAll();
	}

}