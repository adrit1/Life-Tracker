package com.ayush.feeling_tracker.feeling.DTO.response;

import java.time.LocalDate;
import java.util.List;

public class FeelingEntryResponseDTO {
	
	private String message;
	private LocalDate date;
	private Integer hour;
	private Boolean isSleeping;
	private Integer rating;
	private List<FeelingMetricResponseDTO> feelingMetrics;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Boolean getIsSleeping() {
		return isSleeping;
	}

	public void setIsSleeping(Boolean isSleeping) {
		this.isSleeping = isSleeping;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
	
	public List<FeelingMetricResponseDTO> getFeelingMetrics() {
		return feelingMetrics;
	}

	public void setFeelingMetrics(List<FeelingMetricResponseDTO> feelingMetrics) {
		this.feelingMetrics = feelingMetrics;
	}


	
	
}
