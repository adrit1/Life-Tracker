package com.ayush.feeling_tracker.feeling.DTO.request;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class FeelingEntryRequestDTO {
	@Length(max=120)
	private String message;

	@NotNull
	private LocalDate date;

	@NotNull
	@Min(0)
	@Max(23)
	private Integer hour;

	@NotNull
	private Boolean isSleeping;

	@NotNull
	@Min(1)
	@Max(5)
	private Integer rating;
	
	private List<FeelingMetricRequestDTO> feelingMetrics;
	
	//Getters & Setters
	
	
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
	
	public List<FeelingMetricRequestDTO> getFeelingMetrics() {
		return feelingMetrics;
	}

	public void setFeelingMetrics(List<FeelingMetricRequestDTO> feelingMetrics) {
		this.feelingMetrics = feelingMetrics;
	}


	
	
}
