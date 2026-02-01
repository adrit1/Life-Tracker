package com.ayush.feeling_tracker.feeling.DTO.response;

import java.util.List;

public class FeelingCategoryDTO {

    private Long id;
    private String name;
    private List<FeelingItemDTO> feelings;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<FeelingItemDTO> getFeelings() {
		return feelings;
	}
	public void setFeelings(List<FeelingItemDTO> feelings) {
		this.feelings = feelings;
	}

    
}

