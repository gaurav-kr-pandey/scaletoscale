package com.s2s.scaletoscale.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tag database table.
 * 
 */
@Entity
@Table(name="tag")
@NamedQuery(name="Tag.findAll", query="SELECT t FROM Tag t")
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(unique = true, nullable=false, length=255)
	private String tag;

	//bi-directional many-to-one association to Blog
	@ManyToOne
	@JoinColumn(name="blogs_id")
	private Blog blog;

	public Tag() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Blog getBlog() {
		return this.blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

}