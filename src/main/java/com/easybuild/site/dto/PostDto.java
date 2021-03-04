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

}
