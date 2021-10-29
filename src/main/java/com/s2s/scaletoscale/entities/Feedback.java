package com.s2s.scaletoscale.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the feedback database table.
 * 
 */
@Entity
@Table(name="feedback")
@NamedQuery(name="Feedback.findAll", query="SELECT f FROM Feedback f")
public class Feedback implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=255)
	private String email;

	@Column(name="full_name", nullable=false, length=255)
	private String fullName;

	@Column(nullable=false, length=5000)
	private String message;

	@Column(name="phone_number", nullable=false, length=255)
	private String phoneNumber;

	//bi-directional many-to-one association to UserProfile
	@ManyToOne
	@JoinColumn(name="user_profile_id")
	private UserProfile userProfile;

	public Feedback() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public UserProfile getUserProfile() {
		return this.userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

}