package com.ojt.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ojt.studentmanagement.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	@Query(value = "SELECT b.* FROM profiles b WHERE b.user_id = ?1", nativeQuery = true)
	Profile findByUserId(Long userId);

}