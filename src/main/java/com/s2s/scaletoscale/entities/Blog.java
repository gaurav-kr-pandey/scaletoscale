package com.s2s.scaletoscale.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the blogs database table.
 * 
 */
@Entity
@Table(name="blogs")
@NamedQuery(name="Blog.findAll", query="SELECT b FROM Blog b")
public class Blog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false)
	@Lob
	private Object description;

	@Column(length=255)
	private String media;

	@Column(length=1)
	private String tag;

	@Column(nullable=false, length=255)
	private String title;

	//bi-directional many-to-one association to UserProfile
	@ManyToOne
	@JoinColumn(name="user_profile_id")
	private UserProfile userProfile;

	//bi-directional many-to-many association to Cours
	@ManyToMany(mappedBy="blogs")
	private Set<Course> courses;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="blog")
	private Set<Comment> comments;

	//bi-directional many-to-one association to UserLike
	@OneToMany(mappedBy="blog")
	private Set<UserLike> userLikes;

	public Blog() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Object getDescription() {
		return this.description;
	}

	public void setDescription(Object description) {
		this.description = description;
	}

	public String getMedia() {
		return this.media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UserProfile getUserProfile() {
		return this.userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public Set<Course> getCourses() {
		return this.courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public Set<Comment> getComments() {
		return this.comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Comment addComment(Comment comment) {
		getComments().add(comment);
		comment.setBlog(this);

		return comment;
	}

	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
		comment.setBlog(null);

		return comment;
	}

	public Set<UserLike> getUserLikes() {
		return this.userLikes;
	}

	public void setUserLikes(Set<UserLike> userLikes) {
		this.userLikes = userLikes;
	}

	public UserLike addUserLike(UserLike userLike) {
		getUserLikes().add(userLike);
		userLike.setBlog(this);

		return userLike;
	}

	public UserLike removeUserLike(UserLike userLike) {
		getUserLikes().remove(userLike);
		userLike.setBlog(null);

		return userLike;
	}

}