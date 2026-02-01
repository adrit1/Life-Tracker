package com.ayush.feeling_tracker.feeling.DTO.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FeelingMetricRequestDTO {
	

	@NotNull
	private Long feelingId;
	
	@NotBlank
	private String feelingName;
	
	@NotNull
	@Min(0)
	@Max(5)
	private Integer intensity;
	
	//getters & setters
	public Long getFeelingId() {
		return feelingId;
	}

	public void setFeelingId(Long feelingId) {
		this.feelingId = feelingId;
	}

	public Integer getIntensity() {
		return intensity;
	}

	public void setIntensity(Integer value) {
		this.intensity = value;
	}

	public String getFeelingName() {
		return feelingName;
	}

	public void setFeelingName(String feelingName) {
		this.feelingName = feelingName;
	}
	
}
