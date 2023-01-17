package com.ojt.studentmanagement.repository;



import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import com.ojt.studentmanagement.entity.Course;



public interface CourseRepository extends JpaRepository<Course, Long>{
    @Query(value = "select * from courses c where c.title like %:keyword%", nativeQuery = true)
     List<Course> findByKeyword(@Param("keyword") String keyword);
}