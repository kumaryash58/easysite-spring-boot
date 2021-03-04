package com.easybuild.site.bean;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_post")
public class Post {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	// image bytes can have large lengths so we specify a value
	// which is more than the default length for picByte column
	@Column(name = "picByte", length = 1000)
	private byte[] picByte;

	@Column(name = "fileName")
	private String fileName;

	@Column(name = "fileType")
	private String fileType;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_user_id")
	private User user;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	protected Post() {
	}

	public Post(String title, String description, String fileName, String fileType, byte[] picByte) {
		this.title = title;
		this.description = description;
		this.fileName = fileName;
		this.fileType = fileType;
		this.picByte = picByte;
	}

	public Post(String title, String description, String fileName, String fileType, byte[] picByte, User user,
			Timestamp createdAt, Timestamp updatedAt) {
		this.title = title;
		this.description = description;
		this.picByte = picByte;
		this.fileName = fileName;
		this.fileType = fileType;
		this.user = user;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

}
