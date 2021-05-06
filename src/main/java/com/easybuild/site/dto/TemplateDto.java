package com.easybuild.site.dto;


public class TemplateDto {
	
	private String tempName;
	
	private String folderId;
	
	private String tempSnapshotUrl;
	
	private String tempStructure;
	
	protected TemplateDto() { }

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

}
