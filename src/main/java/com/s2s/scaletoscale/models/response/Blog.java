package com.s2s.scaletoscale.models.response;


public class Blog {

	public enum Tag{
		DATABASE, SYSTEM_DESIGN, SYSTEM_DESIGN_FUNDAMENTAL, DATA_STRUCTURE
	}
	private int id;
	private String description;
	private String media;
	private Tag tag;
	private String title;

	public Blog() {
	}

	public Blog(int id, String description, String media, Tag tag, String title) {
		this.id = id;
		this.description = description;
		this.media = media;
		this.tag = tag;
		this.title = title;
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

	public String getMedia() {
		return this.media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public Tag getTag() {
		return this.tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}