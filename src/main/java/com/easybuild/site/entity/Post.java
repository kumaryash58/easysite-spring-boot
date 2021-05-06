package com.easybuild.site.entity;

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
	
	@Column(name = "post_body")
	private String postBody;

	@Column(name = "fileUrl")
	private String fileUrl;

	@Column(name = "fileName")
	private String fileName;

	@Column(name = "fileType")
	private String fileType;
	
	@Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT")
	private boolean isDeleted;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_user_id")
	private User user;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	protected Post() {
	}

	public Post(String title, String description, String fileName, String fileType, String fileUrl, boolean isDeleted) {
		this.title = title;
		this.description = description;
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileUrl = fileUrl;
		this.isDeleted = isDeleted;
	}

	public Post(String title, String description, String postBody, String fileName, String fileType, String fileUrl, User user,
			boolean isDeleted, Timestamp createdAt, Timestamp updatedAt) {
		this.title = title;
		this.description = description;
		this.postBody = postBody;
		this.fileUrl = fileUrl;
		this.fileName = fileName;
		this.fileType = fileType;
		this.user = user;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isDeleted = isDeleted;
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

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
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

	public String getPostBody() {
		return postBody;
	}

	public void setPostBody(String postBody) {
		this.postBody = postBody;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

}
