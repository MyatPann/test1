package com.ojt.studentmanagement.entity;

import java.io.Serializable;

public class UserTimetablePK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long user_id;
	
	private Long timetable_id;
	
	public UserTimetablePK() {
		
	}

	/**
	 * @param user_id
	 * @param timetable_id
	 */
	public UserTimetablePK(Long user_id, Long timetable_id) {
		super();
		this.user_id = user_id;
		this.timetable_id = timetable_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getTimetable_id() {
		return timetable_id;
	}

	public void setTimetable_id(Long timetable_id) {
		this.timetable_id = timetable_id;
	}
	
}
