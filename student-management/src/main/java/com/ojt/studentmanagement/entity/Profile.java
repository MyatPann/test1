package com.ojt.studentmanagement.entity;

import java.beans.Transient;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "profiles")
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ph_number")
	private String phNumber;

	private String address;

	private String gender;

	private int age;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	// picture
	private String profileImg;

	@Transient
	public String getProfileImagePath() {
		if (profileImg == null || id == null)
			return null;

		return "/profile-image/" + id + "/" + profileImg;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "birth_date")
	private LocalDate birthDate;

	public Profile() {

	}

	/**
	 * @param phNumber
	 * @param address
	 * @param gender
	 * @param age
	 * @param user
	 * @param profileImg
	 * @param birthdate
	 */
	public Profile(String phNumber, String address, String gender, int age, User user, String profileImg,
			LocalDate birthdate) {
		super();
		this.phNumber = phNumber;
		this.address = address;
		this.gender = gender;
		this.age = age;
		this.user = user;
		this.profileImg = profileImg;
		this.birthDate = birthdate;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhNumber() {
		return phNumber;
	}

	public void setPhNumber(String phNumber) {
		this.phNumber = phNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}