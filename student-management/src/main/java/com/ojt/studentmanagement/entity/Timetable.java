package com.ojt.studentmanagement.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "timetables")
public class Timetable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	
	private String classRoom;

	private String branch;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;

	private String description;
	
	private double fees;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course courseId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "instructor_id")
	private Instructor instructorId;

	@OneToMany(mappedBy = "timetable", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserTimetable> usertimetables = new HashSet<>();

	public Timetable() {

	}

	/**
	 * @param classRoom
	 * @param branch
	 * @param startDate
	 * @param endDate
	 * @param courseId
	 * @param instructorId
	 */
	public Timetable(String classRoom, String branch, LocalDate startDate, LocalDate endDate, Course courseId,
			Instructor instructorId) {
		super();
		this.classRoom = classRoom;
		this.branch = branch;
		this.startDate = startDate;
		this.endDate = endDate;
	
		this.instructorId = instructorId;
	}

	public Set<UserTimetable> getUsertimetables() {
		return usertimetables;
	}

	public void setUsertimetables(Set<UserTimetable> usertimetables) {
		this.usertimetables = usertimetables;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(String classRoom) {
		this.classRoom = classRoom;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Course getCourseId() {
		return courseId;
	}

	public void setCourseId(Course courseId) {
		this.courseId = courseId;
	}

	public Instructor getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(Instructor instructorId) {
		this.instructorId = instructorId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getFees() {
		return fees;
	}

	public void setFees(double fees) {
		this.fees = fees;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
