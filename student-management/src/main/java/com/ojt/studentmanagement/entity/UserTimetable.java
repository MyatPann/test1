package com.ojt.studentmanagement.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "user_timetables")
public class UserTimetable {
	
	@EmbeddedId
	private UserTimetablePK id;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timetable_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Timetable timetable;
    
    private boolean applyFlag;
    
    private boolean approveFlag;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applayDate;
    
    @Column(name = "approve_mail")
    private String approvePersonMail;
    
    public UserTimetable() {
    	
    }

	/**
	 * @param id
	 * @param user
	 * @param timetable
	 * @param applyFlag
	 * @param approveFlag
	 */
	public UserTimetable(User user, Timetable timetable, boolean applyFlag, boolean approveFlag,LocalDate applayDate) {
		super();
		this.id = new UserTimetablePK(user.getId(), timetable.getId());
		this.user = user;
		this.timetable = timetable;
		this.applyFlag = applyFlag;
		this.approveFlag = approveFlag;
		this.applayDate = applayDate;
	}

	public UserTimetablePK getId() {
		return id;
	}

	public void setId(UserTimetablePK id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timetable getTimetable() {
		return timetable;
	}

	public void setTimetable(Timetable timetable) {
		this.timetable = timetable;
	}

	public boolean isApplyFlag() {
		return applyFlag;
	}

	public void setApplyFlag(boolean applyFlag) {
		this.applyFlag = applyFlag;
	}

	public boolean isApproveFlag() {
		return approveFlag;
	}

	public void setApproveFlag(boolean approveFlag) {
		this.approveFlag = approveFlag;
	}

	public LocalDate getApplayDate() {
		return applayDate;
	}

	public void setApplayDate(LocalDate applayDate) {
		this.applayDate = applayDate;
	}

	public String getApprovePersonMail() {
		return approvePersonMail;
	}

	public void setApprovePersonMail(String approvePersonMail) {
		this.approvePersonMail = approvePersonMail;
	}

}
