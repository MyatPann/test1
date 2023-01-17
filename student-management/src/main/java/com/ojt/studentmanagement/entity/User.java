package com.ojt.studentmanagement.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 45)
	private String email;

	@Column(nullable = false, length = 64)
	private String password;

	@Column(name = "name", nullable = false, length = 20)
	private String name;

	@Column(name = "role")
	private String role;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Profile profile;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserTimetable> usertimetables = new HashSet<>();
	
    @Column(name = "reset_password_token")
    private String resetPasswordToken;

	public User() {
	}

	/**
	 * @param email
	 * @param password
	 * @param firstName
	 * @param role
	 */
	public User(String email, String password, String firstName, String role) {
		super();
		this.email = email;
		this.password = password;
		this.name = firstName;
		this.role = role;
	}
	
	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Set<UserTimetable> getUsertimetables() {
		return usertimetables;
	}

	public void setUsertimetables(Set<UserTimetable> usertimetables) {
		this.usertimetables = usertimetables;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
