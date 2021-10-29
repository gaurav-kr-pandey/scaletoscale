package com.s2s.scaletoscale.models.request;


public class Blog {

	public enum Tag{
		DATABASE, SYSTEM_DESIGN, SYSTEM_DESIGN_FUNDAMENTAL, DATA_STRUCTURE
	}

	private int id;
	private String body;
	private String description;
	private String title;
	private String media_url;
	private long post_time;
	private byte visibility;

	public Blog() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public byte getVisibility() {
		return visibility;
	}

	public void setVisibility(byte visibility) {
		this.visibility = visibility;
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