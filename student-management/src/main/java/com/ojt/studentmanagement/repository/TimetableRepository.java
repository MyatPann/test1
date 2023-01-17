package com.ojt.studentmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ojt.studentmanagement.entity.Timetable;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {

	@Query(value = "SELECT * FROM public.timetables t "
			+ "WHERE Start_date >= current_date ORDER BY t.id ASC", nativeQuery = true)
	List<Timetable> findAll();

	@Query(value = "SELECT * FROM timetables s where cast (s.start_date as varchar)  >= :keyword", nativeQuery = true)
	List<Timetable> findByKeyword(@Param("keyword") String keyword);

	@Query(value = "select c.title,count(c.id) from timetables t "
			+ "JOIN courses c "
			+ "on c.id = t.course_id group by c.id", nativeQuery = true)
	List<String[]> findCount();
}