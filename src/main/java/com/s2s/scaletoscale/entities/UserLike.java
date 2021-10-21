package com.s2s.scaletoscale.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_likes database table.
 * 
 */
@Entity
@Table(name="user_likes")
@NamedQuery(name="UserLike.findAll", query="SELECT u FROM UserLike u")
public class UserLike implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	private byte liked;

	@Column(name="user_profile_id")
	private int userProfileId;

	//bi-directional many-to-one association to Blog
	@ManyToOne
	@JoinColumn(name="blogs_id")
	private Blog blog;

	public UserLike() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getLiked() {
		return this.liked;
	}

	public void setLiked(byte liked) {
		this.liked = liked;
	}

	public int getUserProfileId() {
		return this.userProfileId;
	}

	public void setUserProfileId(int userProfileId) {
		this.userProfileId = userProfileId;
	}

	public Blog getBlog() {
		return this.blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

}