package com.ojt.studentmanagement.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojt.studentmanagement.entity.Instructor;
import com.ojt.studentmanagement.repository.InstructorRepository;
import com.ojt.studentmanagement.service.InstructorService;

@Service
public class InstructorServiceImpl implements InstructorService {

	@Autowired
	private InstructorRepository instructorRepository;

	@Override
	public List<Instructor> getAllInstructors() {
		return instructorRepository.findAll();
	}

	@Override
	public void saveInstructor(Instructor instructor) {
		this.instructorRepository.save(instructor);
	}

	@Override
	public Instructor getInstructorById(long id) {
		Optional<Instructor> optional = instructorRepository.findById(id);
		Instructor instructor = null;
		if (optional.isPresent()) {
			instructor = optional.get();
		} else {
			throw new RuntimeException(" Course not found :: " + id);
		}
		return instructor;
	}

	@Override
	public void deleteInstructorById(long id) {
		this.instructorRepository.deleteById(id);
	}

	@Override
	public List<Instructor> getByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return instructorRepository.findByKeyword(keyword);
	}

	@Override
	public List<Instructor> getAllInstructor() {
		// TODO Auto-generated method stub
		return instructorRepository.findAll();
	}

}