package com.ojt.studentmanagement.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojt.studentmanagement.entity.Course;
import com.ojt.studentmanagement.repository.CourseRepository;
import com.ojt.studentmanagement.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Override
	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}

	@Override
	public void saveCourse(Course course) {
		this.courseRepository.save(course);
	}

	@Override
	public Course getCourseById(long id) {
		Optional<Course> optional = courseRepository.findById(id);
		Course course = null;
		if (optional.isPresent()) {
			course = optional.get();
		} else {
			throw new RuntimeException(" Course not found :: " + id);
		}
		return course;
	}

	@Override
	public void deleteCourseById(long id) {
		this.courseRepository.deleteById(id);
	}

	@Override
	public List<Course> getByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return courseRepository.findByKeyword(keyword);
	}

	@Override
	public List<Course> getAllCourse() {
		// TODO Auto-generated method stub
		return courseRepository.findAll();
	}

}