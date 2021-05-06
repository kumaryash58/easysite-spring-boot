package com.easybuild.site.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_template")
public class Templates {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "template_name")
	private String tempName;
	
	@Column(name = "folder_id")
	private String folderId;
	
	@Column(name = "temp_snapshot_url")
	private String tempSnapshotUrl;
	
	@Lob
	@Column(name = "temp_structure")
	private String tempStructure;
	
	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	protected Templates() { }

	public Templates(String tempName, String folderId, String tempSnapshotUrl, String tempStructure,
			Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.tempName = tempName;
		this.folderId = folderId;
		this.tempSnapshotUrl = tempSnapshotUrl;
		this.tempStructure = tempStructure;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getTempSnapshotUrl() {
		return tempSnapshotUrl;
	}

	public void setTempSnapshotUrl(String tempSnapshotUrl) {
		this.tempSnapshotUrl = tempSnapshotUrl;
	}

	public String getTempStructure() {
		return tempStructure;
	}

	public void setTempStructure(String tempStructure) {
		this.tempStructure = tempStructure;
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
