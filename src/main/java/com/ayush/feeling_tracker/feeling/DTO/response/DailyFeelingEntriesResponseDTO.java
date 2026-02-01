package com.ayush.feeling_tracker.feeling.DTO.response;

import java.time.LocalDate;
import java.util.List;

public class DailyFeelingEntriesResponseDTO {
	private LocalDate date;
	private List<FeelingEntryResponseDTO> entries;
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public List<FeelingEntryResponseDTO> getEntries() {
		return entries;
	}
	public void setEntries(List<FeelingEntryResponseDTO> entries) {
		this.entries = entries;
	}
	
}
