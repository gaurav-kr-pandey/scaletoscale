package com.s2s.scaletoscale.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Lob
	@Column(nullable=false)
	private String body;

	@Column(nullable=false, length=5000)
	private String description;

	@Column(nullable=false, length=255)
	private String title;

	@Column(nullable=true, length=500)
	private String media_url;

	@Column
	private long post_time;

	@Column
	private byte visibility;

	//bi-directional many-to-one association to UserProfile
	@ManyToOne
	@JoinColumn(name="user_profile_id")
	private UserProfile userProfile;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="blog")
	private Set<Comment> comments;

	//bi-directional many-to-many association to Course
	@ManyToMany(mappedBy="blogs")
	private List<Course> courses;

	//bi-directional many-to-one association to Media
	@OneToMany(mappedBy="blog")
	private Set<Media> medias;

	//bi-directional many-to-one association to UserLike
	@OneToMany(mappedBy="blog")
	private Set<UserLike> userLikes;

	//bi-directional many-to-one association to Tag
	@OneToMany(mappedBy="blog")
	private Set<Tag> tags;

	public Blog() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte getVisibility() {
		return this.visibility;
	}

	public void setVisibility(byte visibility) {
		this.visibility = visibility;
	}

	public UserProfile getUserProfile() {
		return this.userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
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

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public Set<Media> getMedias() {
		return this.medias;
	}

	public void setMedias(Set<Media> medias) {
		this.medias = medias;
	}

	public Media addMedia(Media media) {
		getMedias().add(media);
		media.setBlog(this);

		return media;
	}

	public Media removeMedia(Media media) {
		getMedias().remove(media);
		media.setBlog(null);

		return media;
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

	public Set<Tag> getTags() {
		return this.tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Tag addTag(Tag tag) {
		getTags().add(tag);
		tag.setBlog(this);

		return tag;
	}

	public Tag removeTag(Tag tag) {
		getTags().remove(tag);
		tag.setBlog(null);

		return tag;
	}

	public String getMedia_url() {
		return media_url;
	}

	public void setMedia_url(String media_url) {
		this.media_url = media_url;
	}

	public long getPost_time() {
		return post_time;
	}

	public void setPost_time(long post_time) {
		this.post_time = post_time;
	}
}