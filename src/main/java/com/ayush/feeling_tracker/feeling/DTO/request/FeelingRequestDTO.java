package com.ayush.feeling_tracker.feeling.DTO.request;

public class FeelingRequestDTO {
	private String title;
	private Long categoryId;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
}
