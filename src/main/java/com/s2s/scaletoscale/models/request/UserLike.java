package com.s2s.scaletoscale.models.request;


public class UserLike {

	private int id;
	private byte liked;
	private int userProfileId;

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

}