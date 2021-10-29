package com.s2s.scaletoscale.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the user_profile database table.
 * 
 */
@Entity
@Table(name="user_profile")
@NamedQuery(name="UserProfile.findAll", query="SELECT u FROM UserProfile u")
public class UserProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=100)
	private String email;

	@Column(name="first_name", nullable=false, length=50)
	private String firstName;

	@Column(name="last_name", nullable=false, length=50)
	private String lastName;

	@Column(nullable=false, length=100)
	private String password;

	@Column(nullable=false, length=1)
	private String role;

	//bi-directional many-to-one association to Blog
	@OneToMany(mappedBy="userProfile")
	private Set<Blog> blogs;

	//bi-directional many-to-one association to Cours
	@OneToMany(mappedBy="userProfile")
	private Set<Course> courses;

	//bi-directional many-to-one association to UserLike
	@OneToMany(mappedBy="userProfile")
	private Set<UserLike> userLikes;

	//bi-directional many-to-one association to Feedback
	@OneToMany(mappedBy="userProfile")
	private Set<Feedback> feedbacks;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="userProfile")
	private Set<Comment> comments;

	public UserProfile() {
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

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Blog> getBlogs() {
		return this.blogs;
	}

	public void setBlogs(Set<Blog> blogs) {
		this.blogs = blogs;
	}

	public Blog addBlog(Blog blog) {
		getBlogs().add(blog);
		blog.setUserProfile(this);

		return blog;
	}

	public Blog removeBlog(Blog blog) {
		getBlogs().remove(blog);
		blog.setUserProfile(null);

		return blog;
	}

	public Set<Course> getCourses() {
		return this.courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public Course addCourses(Course course) {
		getCourses().add(course);
		course.setUserProfile(this);

		return course;
	}

	public Course removeCourse(Course course) {
		getCourses().remove(course);
		course.setUserProfile(null);

		return course;
	}

	public Set<UserLike> getUserLikes() {
		return this.userLikes;
	}

	public void setUserLikes(Set<UserLike> userLikes) {
		this.userLikes = userLikes;
	}

	public UserLike addUserLike(UserLike userLike) {
		getUserLikes().add(userLike);
		userLike.setUserProfile(this);

		return userLike;
	}

	public UserLike removeUserLike(UserLike userLike) {
		getUserLikes().remove(userLike);
		userLike.setUserProfile(null);

		return userLike;
	}

	public Set<Feedback> getFeedbacks() {
		return this.feedbacks;
	}

	public void setFeedbacks(Set<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public Feedback addFeedback(Feedback feedback) {
		getFeedbacks().add(feedback);
		feedback.setUserProfile(this);

		return feedback;
	}

	public Feedback removeFeedback(Feedback feedback) {
		getFeedbacks().remove(feedback);
		feedback.setUserProfile(null);

		return feedback;
	}

	public Set<Comment> getComments() {
		return this.comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Comment addComment(Comment comment) {
		getComments().add(comment);
		comment.setUserProfile(this);

		return comment;
	}

	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
		comment.setUserProfile(null);

		return comment;
	}

}