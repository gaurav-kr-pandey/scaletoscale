package com.s2s.scaletoscale.models.request;


public class Blog {

	public enum Tag{
		DATABASE, SYSTEM_DESIGN, SYSTEM_DESIGN_FUNDAMENTAL, DATA_STRUCTURE
	}

	private int id;
	private String description;
	private String title;
	private String body;
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
}