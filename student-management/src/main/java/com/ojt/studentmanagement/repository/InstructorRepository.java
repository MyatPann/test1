package com.ojt.studentmanagement.repository;



import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import com.ojt.studentmanagement.entity.Instructor;



public interface InstructorRepository extends JpaRepository<Instructor, Long>{
    @Query(value = "select * from instructors i where i.name like %:keyword%", nativeQuery = true)
     List<Instructor> findByKeyword(@Param("keyword") String keyword);



}