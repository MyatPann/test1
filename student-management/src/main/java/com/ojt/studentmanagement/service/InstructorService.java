package com.ojt.studentmanagement.service;



import java.util.List;



import com.ojt.studentmanagement.entity.Instructor;



public interface InstructorService {
    List<Instructor> getAllInstructors();
    void saveInstructor(Instructor instructor);
    Instructor getInstructorById(long id);
    void deleteInstructorById(long id);
    List<Instructor> getByKeyword(String keyword);
    List<Instructor> getAllInstructor();
}