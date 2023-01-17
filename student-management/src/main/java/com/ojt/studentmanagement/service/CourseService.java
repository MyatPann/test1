package com.ojt.studentmanagement.service;



import java.util.List;




import com.ojt.studentmanagement.entity.Course;



public interface CourseService {
    List<Course> getAllCourses();
    void saveCourse(Course course);
    Course getCourseById(long id);
    void deleteCourseById(long id);
    List<Course> getByKeyword(String keyword);
    List<Course> getAllCourse();
}