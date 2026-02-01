package com.ayush.feeling_tracker.feeling.DTO.response;

public class FeelingMetricResponseDTO {
	private Long feelingId;
	private String feelingName;
	private Integer intensity;
	
	public Long getFeelingId() {
		return feelingId;
	}
	public void setFeelingId(Long feelingId) {
		this.feelingId = feelingId;
	}
	public String getFeelingName() {
		return feelingName;
	}
	public void setFeelingName(String feelingName) {
		this.feelingName = feelingName;
	}
	public Integer getIntensity() {
		return intensity;
	}
	public void setIntensity(Integer intensity) {
		this.intensity = intensity;
	}
	
	
}
