package com.easybuild.site.dto;

public class PostDto {

	/**
	 * Default constructor
	 */
	protected PostDto() {
	}

	public PostDto(String title, String description) {
		this.title = title;
		this.description = description;
	}

	private String title;

	private String description;

	private String postBody;
	
	private String fileUrl;

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
