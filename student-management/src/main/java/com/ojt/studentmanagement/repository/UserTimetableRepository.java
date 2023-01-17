package com.ojt.studentmanagement.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ojt.studentmanagement.entity.UserTimetable;
import com.ojt.studentmanagement.entity.UserTimetablePK;
 
public interface UserTimetableRepository extends JpaRepository<UserTimetable, UserTimetablePK> {
	
    @Query(value = "SELECT g.* FROM user_timetables g WHERE g.user_id= ?1 AND g.apply_flag = true", nativeQuery = true)
    List<UserTimetable> findByLoginUserId(Long userId);

    //search with applyFlag & loginUserId validation
    @Query(value = "SELECT * FROM user_timetables ut LEFT OUTER JOIN timetables t ON t.id = ut.timetable_id WHERE cast (t.start_date as varchar) >= :keyword AND ut.user_id = :userId AND ut.apply_flag = true", nativeQuery = true)
    List<UserTimetable> appliedSearchByKeyword(@Param("keyword") String keyword, Long userId);
}